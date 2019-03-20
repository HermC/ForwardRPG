package edu.nju.hermc.forward.game.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.nju.hermc.forward.game.command.Command;
import edu.nju.hermc.forward.game.utils.Constants;
import edu.nju.hermc.forward.mapper.UserMapper;
import edu.nju.hermc.forward.model.User;
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
    private UserMapper userMapper;
    @Autowired
    private RedisUtils redisUtils;

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
        User user = parser.readValue(parser.writeValueAsString(wrapper.getData()), User.class);
        if (redisUtils.hasKey(user.getUsername())) {
            wrapper.setCode(Constants.AUTHORIZATION_ACTIVE);
            cl.writeAndFlush(new TextWebSocketFrame(parser.writeValueAsString(wrapper)));
            return;
        }
        User realUser = userMapper.findByUsername(user.getUsername());
        if (realUser == null) {
            wrapper.setCode(Constants.AUTHORIZATION_CHOOSE_CAREER);
            redisUtils.set(cl.id().asLongText(), user.getUsername());
            cl.writeAndFlush(new TextWebSocketFrame(parser.writeValueAsString(wrapper)));
            return;
        }
        // TODO: 加载数据库用户

        redisUtils.set(user.getUsername(), cl.id().asLongText());
        redisUtils.set(cl.id().asLongText(), user.getUsername());
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

        // TODO: 创建并存储用户
        String username = (String) userData;
        System.out.println(username);
        System.out.println(career);
    }

    public void loseAuth(Channel cl) {

    }

}
