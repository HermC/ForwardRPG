package edu.nju.hermc.forward.game.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.nju.hermc.forward.game.command.Command;
import edu.nju.hermc.forward.game.creature.Player;
import edu.nju.hermc.forward.game.creature.PlayerFactory;
import edu.nju.hermc.forward.game.map.World;
import edu.nju.hermc.forward.game.utils.Constants;
import edu.nju.hermc.forward.mapper.BagMapper;
import edu.nju.hermc.forward.mapper.PlayerMapper;
import edu.nju.hermc.forward.model.BagInfo;
import edu.nju.hermc.forward.model.PlayerInfo;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Sharable
public class AuthHandler {

    private static final Logger logger = LoggerFactory.getLogger(AuthHandler.class);

    private static final ObjectMapper parser = new ObjectMapper();

    @Autowired
    private PlayerMapper playerMapper;
    @Autowired
    private BagMapper bagMapper;

    public void authorization(Channel cl, Command command) throws Exception {
        switch (command.getCode()) {
            case Constants.AUTHORIZATION:
                login(cl, command);
                break;
            case Constants.AUTHORIZATION_CHOOSE_CAREER:
                chooseCareer(cl, command);
                break;
            default:
                break;
        }
    }

    public void login(Channel cl, Command wrapper) throws Exception {
        World WORLD = World.getInstance();

        PlayerInfo user = parser.readValue(parser.writeValueAsString(wrapper.getData()), PlayerInfo.class);
        if (WORLD.getCreatures().containsKey(user.getUsername())) {
            wrapper.setCode(Constants.AUTHORIZATION_ACTIVE);
            cl.writeAndFlush(new TextWebSocketFrame(parser.writeValueAsString(wrapper)));
            return;
        }
        PlayerInfo realUser = playerMapper.find(user.getUsername());
        if (realUser == null) {
            wrapper.setCode(Constants.AUTHORIZATION_CHOOSE_CAREER);
            WORLD.getPlayers().put(cl.id().asLongText(), user.getUsername());
            cl.writeAndFlush(new TextWebSocketFrame(parser.writeValueAsString(wrapper)));
            return;
        }

        BagInfo bagInfo = bagMapper.find(realUser.getUsername());
        Player player = PlayerFactory.getPlayer(realUser);
        WORLD.getCreatures().put(user.getUsername(), player);
        WORLD.getClients().put(user.getUsername(), cl.id().asLongText());

        WORLD.getPlayers().put(cl.id().asLongText(), user.getUsername());

        authSuccess(cl, wrapper, player);
    }

    public void chooseCareer(Channel cl, Command wrapper) throws Exception {
        World WORLD = World.getInstance();

        String career = (String) wrapper.getData();
        Object userData = WORLD.getCreatures().get(cl.id().asLongText());
        if (userData == null) {
            wrapper.setCode(Constants.AUTHORIZATION_FAIL);
            cl.writeAndFlush(new TextWebSocketFrame(parser.writeValueAsString(wrapper)));
            return;
        }
        if (!(userData instanceof String)) {
            wrapper.setCode(Constants.AUTHORIZATION_ACTIVE);
            cl.writeAndFlush(new TextWebSocketFrame(parser.writeValueAsString(wrapper)));
            return;
        }

        String username = (String) userData;
        PlayerInfo info = PlayerFactory.getPlayerInfo(username, career);
        BagInfo bagInfo = new BagInfo();
        bagInfo.setUsername(username);
        bagInfo.setCoin(100);
        bagInfo.setProp("weapon");
        bagInfo.setPropLevel(1);
        playerMapper.insert(info);
        bagMapper.insert(bagInfo);

        Player player = PlayerFactory.getPlayer(info);

        WORLD.getCreatures().remove(username);
        WORLD.getCreatures().put(username, player);
        WORLD.getClients().put(username, cl.id().asLongText());

        authSuccess(cl, wrapper, player);
    }

    private void authSuccess(Channel cl, Command wrapper, Player player) throws JsonProcessingException {
        wrapper.setCode(Constants.AUTHORIZATION_SUCCESS);
        Map<String, Object> rs = new HashMap<>();
        rs.put("player", player);
        rs.put("map", World.getInstance().getEnemies());
        wrapper.setData(rs);
        cl.writeAndFlush(new TextWebSocketFrame(parser.writeValueAsString(wrapper)));
    }

    public void loseAuth(Channel cl) throws Exception {
        World WORLD = World.getInstance();
        String username = WORLD.getPlayers().get(cl.id().asLongText());
        if (username == null) {
            return;
        }
        Player player = (Player) WORLD.getCreatures().get(username);
        PlayerInfo info = playerMapper.find(username);
        info.setX(player.getX());
        info.setY(player.getY());
        info.setLevel(player.getLevel());
        info.setHp(player.getHp());
        info.setMp(player.getMp());
        info.setAp(player.getAp());
        playerMapper.update(info);

        WORLD.getCreatures().remove(username);
        WORLD.getPlayers().remove(cl.id().asLongText());
        WORLD.getClients().remove(username);
    }

}
