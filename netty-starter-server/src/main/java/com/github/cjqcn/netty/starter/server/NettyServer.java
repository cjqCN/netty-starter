package com.github.cjqcn.netty.starter.server;

import com.github.cjqcn.netty.starter.server.internal.BasicResourceManager;
import com.github.cjqcn.netty.starter.server.internal.NettyChannelInitializer;
import com.github.cjqcn.netty.starter.server.internal.ResourceManager;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @description:
 * @author: chenjinquan
 * @create: 2018-09-25 14:34
 **/
public class NettyServer {

    private static final Logger LOG = LoggerFactory.getLogger(NettyServer.class);

    private EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    private EventLoopGroup workerGroup = new NioEventLoopGroup();

    private ServerBootstrap bootstrap = new ServerBootstrap();

    private ResourceManager resourceManager;

    private static final int DEFAULT_PORT = 8007;

    private int port = DEFAULT_PORT;

    public static void main(String[] args) throws Exception {
        NettyServer server = new NettyServer(new BasicResourceManager());
        server.start();
    }

    public NettyServer(ResourceManager resourceManager) {
        this.resourceManager = resourceManager;
    }

    public synchronized void start() throws Exception {
        // Configure the server.
        try {
            long start = System.currentTimeMillis();
            LOG.info("Starting NettyServer at port: {}", DEFAULT_PORT);
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .handler(new LoggingHandler(LogLevel.DEBUG))
                    .childHandler(new NettyChannelInitializer(resourceManager));
            // Start the server.
            ChannelFuture f = bootstrap.bind(port).sync();
            long end = System.currentTimeMillis();
            LOG.info("Started NettyServer at port: {}, cost {}ms", DEFAULT_PORT, end - start);
            // Wait until the server socket is closed.
            f.channel().closeFuture().sync();
        } finally {
            // Shut down all event loops to terminate all threads.
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }


}
