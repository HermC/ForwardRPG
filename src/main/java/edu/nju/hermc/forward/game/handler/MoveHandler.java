package edu.nju.hermc.forward.game.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.nju.hermc.forward.game.command.Command;
import edu.nju.hermc.forward.game.command.MoveCommand;
import edu.nju.hermc.forward.game.creature.Player;
import edu.nju.hermc.forward.game.map.World;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Sharable
public class MoveHandler {

    private static final Logger logger = LoggerFactory.getLogger(MoveHandler.class);

    private ObjectMapper parser = new ObjectMapper();

    public void move(Channel cl, Command wrapper) throws Exception {
        World WORLD = World.getInstance();

        MoveCommand command = parser.readValue(parser.writeValueAsString(wrapper.getData()), MoveCommand.class);
        command.setClientId(cl.id().asLongText());
        if (WORLD.getPlayers().containsKey(cl.id().asLongText())) {
            String username = WORLD.getPlayers().get(cl.id().asLongText());
            Player player = (Player) WORLD.getCreatures().get(username);
            player.setX(command.getX());
            player.setY(command.getY());
            WORLD.getCreatures().put(username, player);
        } else {

        }

        wrapper.setData(command);
        for (Channel channel : WorldHandler.clients) {
            if (channel.equals(cl)) continue;
            channel.writeAndFlush(new TextWebSocketFrame(parser.writeValueAsString(wrapper)));
        }
    }

}
