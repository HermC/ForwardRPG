package edu.nju.hermc.forward.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

@Component
public class NettyServer {

    private static final Logger logger = LoggerFactory.getLogger(NettyServer.class);

    private final EventLoopGroup bossGroup = new NioEventLoopGroup();
    private final EventLoopGroup workerGroup = new NioEventLoopGroup();

    private Channel channel;

    @Autowired
    private WSServerInitializer wsServerInitializer;

    public ChannelFuture run(InetSocketAddress address) {
        ChannelFuture f = null;
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(wsServerInitializer)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            f = b.bind(address).syncUninterruptibly();
            channel = f.channel();
        } catch (Exception e) {
            logger.error("Netty Start Error: ", e);
        } finally {
            if (f != null && f.isSuccess()) {
                logger.info("Netty Server Listening " + address.getHostName()
                        + " on Port " +  address.getPort()
                        + " and Ready For Connections...");
            } else {
                logger.error("Netty Server Start Up Error!");
            }
        }
        return f;
    }

    public void destroy() {
        logger.info("Shutdown Netty Server...");
        if (channel != null) channel.close();
        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
        logger.info("Shutdown Netty Server Success!");
    }

}
