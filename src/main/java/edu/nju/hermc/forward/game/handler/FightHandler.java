package edu.nju.hermc.forward.game.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.nju.hermc.forward.game.command.Command;
import edu.nju.hermc.forward.game.command.FightCommand;
import edu.nju.hermc.forward.game.command.FightInitCommand;
import edu.nju.hermc.forward.game.utils.Constants;
import edu.nju.hermc.forward.model.User;
import edu.nju.hermc.forward.utils.RedisUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Sharable
public class FightHandler {

    private static final Logger logger = LoggerFactory.getLogger(FightHandler.class);

    private static final ObjectMapper parser = new ObjectMapper();

    @Autowired
    private RedisUtils redisUtils;

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

    public void fightInit(Channel cl, Command wrapper) throws Exception {
        FightInitCommand command = parser.readValue(parser.writeValueAsString(wrapper.getData()), FightInitCommand.class);
        User initiator = parser.readValue((String) redisUtils.get(command.getInitiator()), User.class);
        User targertor = parser.readValue((String) redisUtils.get(command.getInitiator()), User.class);

        if (initiator == null || targertor == null) {
            return;
        }
    }

    public void fighting(Channel cl, Command wrapper) throws Exception {
        FightCommand command = parser.readValue(parser.writeValueAsString(wrapper.getData()), FightCommand.class);
        if (!redisUtils.hasKey(command.getFightId())) {
            return;
        }
        Object fight = redisUtils.get(command.getFightId());
        if (fight == null) {
            return;
        }

    }

}
