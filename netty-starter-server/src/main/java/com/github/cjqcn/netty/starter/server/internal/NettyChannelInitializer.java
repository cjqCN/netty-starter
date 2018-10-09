package com.github.cjqcn.netty.starter.server.internal;

import com.github.cjqcn.netty.starter.common.codec.KryoDecoder;
import com.github.cjqcn.netty.starter.common.codec.KryoEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * @description:
 * @author: chenjinquan
 * @create: 2018-09-25 14:39
 **/
public class NettyChannelInitializer extends ChannelInitializer<SocketChannel> {

    private final ResourceManager resourceManager;

    public NettyChannelInitializer(ResourceManager resourceManager) {
        this.resourceManager = resourceManager;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        ch.pipeline().addLast("decoder", new KryoDecoder());
        ch.pipeline().addLast("encoder", new KryoEncoder());
        pipeline.addLast(new IdleStateHandler(6, 0, 0));
        pipeline.addLast(new ServerContextHandler(resourceManager));
    }
}
