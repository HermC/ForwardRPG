package edu.nju.hermc.forward.netty;

import edu.nju.hermc.forward.game.handler.WorldHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WSServerInitializer extends ChannelInitializer<SocketChannel> {

    @Autowired
    private WorldHandler worldHandler;

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        // ws基于http协议，添加http编码器
        pipeline.addLast(new HttpServerCodec());
        // 对写大数据流的支持
        pipeline.addLast(new ChunkedWriteHandler());

        pipeline.addLast(new HttpObjectAggregator(1024 * 64));


        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        pipeline.addLast(worldHandler);
    }

}
