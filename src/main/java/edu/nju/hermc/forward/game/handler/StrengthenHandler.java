package edu.nju.hermc.forward.game.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.nju.hermc.forward.game.command.Command;
import edu.nju.hermc.forward.game.utils.Constants;
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
public class StrengthenHandler {

    private static final Logger logger = LoggerFactory.getLogger(StrengthenHandler.class);

    private ObjectMapper parser = new ObjectMapper();

    @Autowired
    private RedisUtils redisUtils;

    public void update(Channel cl, Command wrapper) throws Exception {
        wrapper.setCode(Constants.STRENGTHEN_FAILED);
        cl.writeAndFlush(new TextWebSocketFrame(parser.writeValueAsString(wrapper)));
    }

}
