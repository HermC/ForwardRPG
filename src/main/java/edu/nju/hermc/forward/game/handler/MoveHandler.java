package edu.nju.hermc.forward.game.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.nju.hermc.forward.game.command.Command;
import edu.nju.hermc.forward.game.command.MoveCommand;
import edu.nju.hermc.forward.game.creature.Creature;
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
public class MoveHandler {

    private static final Logger logger = LoggerFactory.getLogger(MoveHandler.class);

    @Autowired
    private RedisUtils redisUtils;

    private ObjectMapper parser = new ObjectMapper();

    public void move(Channel cl, Command wrapper) throws Exception {
        MoveCommand command = parser.readValue(parser.writeValueAsString(wrapper.getData()), MoveCommand.class);
        command.setClientId(cl.id().asLongText());
        if (redisUtils.hasKey(cl.id().asLongText())) {
            Creature player = (Creature) redisUtils.get(cl.id().asLongText());
            player.setX(command.getX());
            player.setY(command.getY());
            redisUtils.set(cl.id().asLongText(), player);
        } else {

        }

        wrapper.setData(command);
        for (Channel channel : WorldHandler.clients) {
            if (channel.equals(cl)) continue;
            channel.writeAndFlush(new TextWebSocketFrame(parser.writeValueAsString(wrapper)));
        }
    }

}
