package com.github.cjqcn.netty.starter.common.handler;


import com.github.cjqcn.netty.starter.common.Heartbeat;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ClientHeartbeatHandler extends ChannelInboundHandlerAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(ClientHeartbeatHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        LOG.info("--- Server is active ---");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {

    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        if (evt instanceof IdleStateEvent) {
            // 不管是读事件空闲还是写事件空闲都向服务器发送心跳包
            sendHeartbeatPacket(ctx);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        LOG.info("连接出现异常");
    }

    /**
     * 发送心跳包
     *
     * @param ctx
     */
    private void sendHeartbeatPacket(ChannelHandlerContext ctx) {
        LOG.debug("----->ping pong");
        ctx.writeAndFlush(Heartbeat.instance);
    }
}
