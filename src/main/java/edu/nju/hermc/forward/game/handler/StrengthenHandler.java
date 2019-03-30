package edu.nju.hermc.forward.game.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.nju.hermc.forward.game.command.Command;
import edu.nju.hermc.forward.game.command.StateCommand;
import edu.nju.hermc.forward.game.command.StrengthenCommand;
import edu.nju.hermc.forward.game.creature.Player;
import edu.nju.hermc.forward.game.map.World;
import edu.nju.hermc.forward.game.skill.bag.Bag;
import edu.nju.hermc.forward.game.skill.bag.Prop;
import edu.nju.hermc.forward.game.utils.Constants;
import edu.nju.hermc.forward.mapper.BagMapper;
import edu.nju.hermc.forward.model.BagInfo;
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
    private BagMapper bagMapper;


    public void update(Channel cl, Command wrapper) throws Exception {
        StrengthenCommand command = parser.readValue(parser.writeValueAsString(wrapper.getData()), StrengthenCommand.class);

        World WORLD = World.getInstance();

        Player player = (Player) WORLD.getCreatures().get(command.getUsername());

        String result = player.getBag().levelupProp();
        StateCommand stateCommand = new StateCommand();
        stateCommand.setPlayer(player);
        stateCommand.setResult(result);

        if (result.equals("升级失败，金币不足")) {
            wrapper.setCode(Constants.STRENGTHEN_FAILED);
        } else {
            wrapper.setCode(Constants.STRENGTHEN);
        }

        wrapper.setData(stateCommand);
        cl.writeAndFlush(new TextWebSocketFrame(parser.writeValueAsString(wrapper)));

        WORLD.getCreatures().put(player.getObjectId(), player);
        BagInfo bagInfo = bagMapper.find(player.getObjectId());
        bagInfo.setCoin(player.getBag().getCoin());
        bagInfo.setPropLevel(player.getBag().getMyProp().getLevel());
        bagInfo.setProp(player.getBag().getMyProp().getName());
        bagMapper.update(bagInfo);
    }

}
