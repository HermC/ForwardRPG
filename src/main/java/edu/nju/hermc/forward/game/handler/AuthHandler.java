package edu.nju.hermc.forward.game.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.nju.hermc.forward.game.command.Command;
import edu.nju.hermc.forward.game.creature.Creature;
import edu.nju.hermc.forward.game.creature.PlayerFactory;
import edu.nju.hermc.forward.game.utils.Constants;
import edu.nju.hermc.forward.mapper.PlayerMapper;
import edu.nju.hermc.forward.model.PlayerInfo;
import edu.nju.hermc.forward.utils.RedisUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Sharable
public class AuthHandler {

    private static final Logger logger = LoggerFactory.getLogger(AuthHandler.class);

    private static final ObjectMapper parser = new ObjectMapper();

    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private PlayerMapper playerMapper;

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
        PlayerInfo user = parser.readValue(parser.writeValueAsString(wrapper.getData()), PlayerInfo.class);
        if (redisUtils.hasKey(user.getUsername())) {
            wrapper.setCode(Constants.AUTHORIZATION_ACTIVE);
            cl.writeAndFlush(new TextWebSocketFrame(parser.writeValueAsString(wrapper)));
            return;
        }
        PlayerInfo realUser = playerMapper.find(user.getUsername());
        if (realUser == null) {
            wrapper.setCode(Constants.AUTHORIZATION_CHOOSE_CAREER);
            redisUtils.set(cl.id().asLongText(), user.getUsername());
            cl.writeAndFlush(new TextWebSocketFrame(parser.writeValueAsString(wrapper)));
            return;
        }

        redisUtils.hset(user.getUsername(), Creature.class.getName(), PlayerFactory.getPlayer(realUser));
        redisUtils.set(cl.id().asLongText(), user.getUsername());

        wrapper.setCode(Constants.AUTHORIZATION_SUCCESS);
        wrapper.setData(realUser);
        cl.writeAndFlush(new TextWebSocketFrame(parser.writeValueAsString(wrapper)));
    }

    public void chooseCareer(Channel cl, Command wrapper) throws Exception {
        String career = (String) wrapper.getData();
        Object userData = redisUtils.get(cl.id().asLongText());
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
        playerMapper.insert(info);

        redisUtils.del(username);
        redisUtils.hset(username, Creature.class.getName(), PlayerFactory.getPlayer(info));

        wrapper.setCode(Constants.AUTHORIZATION_SUCCESS);
        wrapper.setData(PlayerFactory.getPlayer(info));
        cl.writeAndFlush(new TextWebSocketFrame(parser.writeValueAsString(wrapper)));
        return;
    }

    public void loseAuth(Channel cl) throws Exception {
        String username = (String) redisUtils.get(cl.id().asLongText());
        if (username == null) {
            return;
        }
        Creature player = (Creature) redisUtils.hget(username, Creature.class.getName());
        PlayerInfo info = playerMapper.find(username);
        info.setX(player.getX());
        info.setY(player.getY());
        info.setLevel(player.getLevel());
        info.setHp(player.getHp());
        info.setMp(player.getMp());
        info.setAp(player.getAp());
        playerMapper.update(info);

        redisUtils.del(username);
        redisUtils.del(cl.id().asLongText());
    }

}
