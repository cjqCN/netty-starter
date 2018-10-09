package com.github.cjqcn.netty.starter.client;


import com.github.cjqcn.netty.starter.common.Req;
import com.github.cjqcn.netty.starter.common.Sender;
import com.github.cjqcn.netty.starter.common.req.HelloReq;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: chenjinquan
 * @create: 2018-09-25 14:41
 **/
public class NettyClient implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(NettyClient.class);

    static final String HOST = System.getProperty("host", "127.0.0.1");
    static final int PORT = Integer.parseInt(System.getProperty("port", "8007"));
    ScheduledExecutorService service;
    volatile Sender sender;

    public synchronized void start() throws Exception {
        service = Executors.newScheduledThreadPool(1);
        service.scheduleAtFixedRate(this, 0, 10, TimeUnit.SECONDS);
    }

    public static void main(String[] args) throws Exception {
        NettyClient client = new NettyClient();
        client.start();
        Thread.sleep(1000L);
        client.sender().send(new HelloReq());
    }

    @Override
    public void run() {
        // Configure the client.
        LOG.info("正在连接服务器----");
        Bootstrap b = new Bootstrap();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            b.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new NettyChannelInitializer());

            // Start the client.
            ChannelFuture f = b.connect(HOST, PORT).sync();
            LOG.info("连接服务器成功----");
            // Init sender
            ClientHandler clientHandler = (ClientHandler) f.channel().pipeline().get("clientHandler");
            sender = clientHandler;

            // Wait until the connection is closed.
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Shut down the event loop to terminate all threads.
            workerGroup.shutdownGracefully();
        }
    }

    public Sender<Req> sender() {
        return sender;
    }
}
