package edu.nju.hermc.forward.game.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.nju.hermc.forward.game.command.*;
import edu.nju.hermc.forward.game.creature.Creature;
import edu.nju.hermc.forward.game.creature.Enemy;
import edu.nju.hermc.forward.game.creature.Player;
import edu.nju.hermc.forward.game.creature.state.MoveState;
import edu.nju.hermc.forward.game.creature.state.State;
import edu.nju.hermc.forward.game.fight.Fight;
import edu.nju.hermc.forward.game.map.World;
import edu.nju.hermc.forward.game.utils.Constants;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Sharable
public class FightHandler {

    private static final Logger logger = LoggerFactory.getLogger(FightHandler.class);

    private static final ObjectMapper parser = new ObjectMapper();


    public void fight(Channel cl, Command command) throws Exception {
        switch (command.getCode()) {
            case Constants.FIGHT_INIT:
                fightInit(cl, command);
                break;
            case Constants.FIGHT_ACTING:
                fighting(cl, command);
                break;
            default:
                break;
        }
    }

    public synchronized void fightInit(Channel cl, Command wrapper) throws Exception {
        World WORLD = World.getInstance();

        FightInitCommand command = parser.readValue(parser.writeValueAsString(wrapper.getData()), FightInitCommand.class);

        Creature initiator = WORLD.getCreatures().get(command.getInitiator());
        Creature target = WORLD.getCreatures().get(command.getTarget());

        System.out.println(initiator.getState());
        System.out.println(target.getState());

        if (initiator == null || target == null) {
            wrapper.setCode(Constants.FIGHT_INIT_FAILED);
            cl.writeAndFlush(new TextWebSocketFrame(parser.writeValueAsString(wrapper)));
            return;
        }

        State save = target.getState();
        if (!target.getState().doCollide(target)) {
            wrapper.setCode(Constants.FIGHT_INIT_FAILED);
            cl.writeAndFlush(new TextWebSocketFrame(parser.writeValueAsString(wrapper)));
            return;
        }

        if (!initiator.getState().doCollide(initiator)) {
            target.setState(save);
            wrapper.setCode(Constants.FIGHT_INIT_FAILED);
            cl.writeAndFlush(new TextWebSocketFrame(parser.writeValueAsString(wrapper)));
            return;
        }

        Fight fight = new Fight(UUID.randomUUID().toString(), initiator, target);
        WORLD.getFights().put(fight.getFightId(), fight);

        FightInfoCommand fi1 = new FightInfoCommand();
        fi1.setFightId(fight.getFightId());
        fi1.setOurSide(initiator);
        fi1.setOtherSide(target);

        wrapper.setCode(Constants.FIGHT_INIT);
        wrapper.setData(fi1);
        cl.writeAndFlush(new TextWebSocketFrame(parser.writeValueAsString(wrapper)));


        if (target instanceof Player) {
            System.out.println(((Player) target).getCareer());
            FightInfoCommand fi2 = new FightInfoCommand();
            fi2.setFightId(fight.getFightId());
            fi2.setOurSide(target);
            fi2.setOtherSide(initiator);

            String cl2 = WORLD.getClients().get(target.getObjectId());
            for (Channel c: WorldHandler.clients) {
                if (c.id().asLongText().equals(cl2)) {
                    wrapper.setCode(Constants.FIGHT_INIT);
                    wrapper.setData(fi2);
                    c.writeAndFlush(new TextWebSocketFrame(parser.writeValueAsString(wrapper)));
                    break;
                }
            }

        }

        WORLD.getCreatures().put(initiator.getObjectId(), initiator);
        WORLD.getCreatures().put(target.getObjectId(), target);
    }

    public void fighting(Channel cl, Command wrapper) throws Exception {
        World WORLD = World.getInstance();

        FightCommand command = parser.readValue(parser.writeValueAsString(wrapper.getData()), FightCommand.class);
        if (!WORLD.getFights().containsKey(command.getFightId())) {
            return;
        }
        Fight fight = WORLD.getFights().get(command.getFightId());
        if (fight == null) {
            return;
        }

        String username = WORLD.getPlayers().get(cl.id().asLongText());
        Creature player = WORLD.getCreatures().get(username);
        String r = fight.playerFight(player.getObjectId(), player.getSkillList().get(command.getSkillId()));
        if (r == null) {
            wrapper.setCode(Constants.FIGHT_NOT_YOUR_TURN);
            cl.writeAndFlush(new TextWebSocketFrame(parser.writeValueAsString(wrapper)));
            return;
        }

        Creature[] creatures = fight.getCreature();
        Creature ourSide = creatures[0].getObjectId().equals(username) ? creatures[0] : creatures[1];
        Creature otherSide = creatures[0].getObjectId().equals(username) ? creatures[1] : creatures[0];

        FightResultCommand result = new FightResultCommand();
        result.setFightId(fight.getFightId());
        result.setOurSide(ourSide);
        result.setOtherSide(otherSide);
        result.setResult(r);

        if (fight.isEnd()) {
            wrapper.setCode(Constants.FIGHT_END);
            wrapper.setData(result);
            cl.writeAndFlush(new TextWebSocketFrame(parser.writeValueAsString(wrapper)));

            result.setOurSide(otherSide);
            result.setOtherSide(ourSide);
            wrapper.setData(result);

            String cl2 = WORLD.getClients().get(otherSide.getObjectId());
            for (Channel c: WorldHandler.clients) {
                if (c.id().asLongText().equals(cl2)) {
                    c.writeAndFlush(new TextWebSocketFrame(parser.writeValueAsString(wrapper)));
                    break;
                }
            }

            this.refreshCreature(ourSide);
            this.refreshCreature(otherSide);
            this.refreshFight(fight);
            WORLD.getFights().remove(fight.getFightId());
            return;
        }

        if (creatures[1] instanceof Enemy) {
            r = fight.autoFight() + "<br>" + r;

            result.setResult(r);

            if (fight.isEnd()) {
                wrapper.setCode(Constants.FIGHT_END);
                wrapper.setData(result);
                cl.writeAndFlush(new TextWebSocketFrame(parser.writeValueAsString(wrapper)));

                this.refreshCreature(ourSide);
                this.refreshCreature(otherSide);

                WORLD.getFights().remove(fight.getFightId());
                return;
            } else {
                wrapper.setCode(Constants.FIGHT_ACTING);
                wrapper.setData(result);
                cl.writeAndFlush(new TextWebSocketFrame(parser.writeValueAsString(wrapper)));
            }
        } else {
            wrapper.setData(result);

            wrapper.setCode(Constants.FIGHT_ACTING);
            cl.writeAndFlush(new TextWebSocketFrame(parser.writeValueAsString(wrapper)));

            result.setOurSide(otherSide);
            result.setOtherSide(ourSide);
            wrapper.setData(result);

            String cl2 = WORLD.getClients().get(otherSide.getObjectId());
            for (Channel c: WorldHandler.clients) {
                if (c.id().asLongText().equals(cl2)) {
                    c.writeAndFlush(new TextWebSocketFrame(parser.writeValueAsString(wrapper)));
                    break;
                }
            }
        }
        this.refreshFight(fight);
    }

    private void refreshCreature(Creature c) {
        World WORLD = World.getInstance();

        c.setCurrent_hp(c.getHp());
        c.setCurrent_mp(c.getMp());
        c.setCurrent_ap(c.getAp());

        c.setBuff(null);

        c.getState().doFightEnd(c);

        WORLD.getCreatures().put(c.getObjectId(), c);
    }

    private void refreshFight(Fight fight) {
        World WORLD = World.getInstance();

        WORLD.getFights().put(fight.getFightId(), fight);
    }

}
