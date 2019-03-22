package edu.nju.hermc.forward.game.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.nju.hermc.forward.game.command.Command;
import edu.nju.hermc.forward.game.utils.Constants;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Sharable
public class WorldHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private static final Logger logger = LoggerFactory.getLogger(WorldHandler.class);

    private ObjectMapper parser = new ObjectMapper();
    // 记录和管理所有客户端channel
    protected static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Autowired
    private AuthHandler authHandler;
    @Autowired
    private MoveHandler moveHandler;
    @Autowired
    private FightHandler fightHandler;
    @Autowired
    private StateHandler stateHandler;
    @Autowired
    private StrengthenHandler strengthenHandler;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        //获取客户端传输过来的信息
        String content = msg.text();
        System.out.println("接收到的数据: " + content);

        Command command = parser.readValue(content, Command.class);

        switch (command.getCode() - command.getCode() % 1000) {
            case Constants.AUTHORIZATION:
                authHandler.authorization(ctx.channel(), command);
                break;
            case Constants.MOVE:
                moveHandler.move(ctx.channel(), command);
                break;
            case Constants.FIGHT:
                fightHandler.fight(ctx.channel(), command);
                break;
            case Constants.STATE:
                stateHandler.getState(ctx.channel(), command);
                break;
            case Constants.STRENGTHEN:
                strengthenHandler.update(ctx.channel(), command);
                break;
            default:
                logger.info("无效指令: " + command);
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        logger.info("客户端建立连接: " + ctx.channel().id().asLongText());
        clients.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        logger.info("客户端断开, channel对应的长id为: " + ctx.channel().id().asLongText());
        authHandler.loseAuth(ctx.channel());
    }
}
