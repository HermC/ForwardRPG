package edu.nju.hermc.forward;

import edu.nju.hermc.forward.netty.NettyServer;
import io.netty.channel.ChannelFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.InetSocketAddress;

@SpringBootApplication
public class ForwardApplication implements CommandLineRunner {

    @Value("${n.port}")
    private int port;

    @Value("${n.url}")
    private String url;

    @Autowired
    private NettyServer socketServer;

    public static void main(String[] args) {
        SpringApplication.run(ForwardApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        InetSocketAddress address = new InetSocketAddress(url, port);
        ChannelFuture future = socketServer.run(address);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> socketServer.destroy()));
        future.channel().closeFuture().syncUninterruptibly();
    }

}
