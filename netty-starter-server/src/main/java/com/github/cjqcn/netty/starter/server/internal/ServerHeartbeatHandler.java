package com.github.cjqcn.netty.starter.server.internal;

import com.github.cjqcn.netty.starter.common.Heartbeat;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerHeartbeatHandler extends ChannelInboundHandlerAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(ServerHeartbeatHandler.class);

    private int counter;

    private static final int CLOSE_MAX_LOSING_HEART_BEAT = 3;

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        LOG.debug("--- Client is active ---");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        LOG.debug("--- Client is inactive ---");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        // 判断接收到的包类型
        if (msg instanceof Heartbeat) {
            handleHeartbreat(ctx);
        } else {
            handleData(ctx, msg);
        }
        ReferenceCountUtil.release(msg);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            // 空闲6s之后触发 (心跳包丢失)
            if (counter >= CLOSE_MAX_LOSING_HEART_BEAT) {
                // 连续丢失3个心跳包 (断开连接)
                closeConnection(ctx, evt);
                LOG.debug("已与Client断开连接");
            } else {
                counter++;
                LOG.debug("丢失了第 " + counter + " 个心跳包");
            }
        }
    }


    protected void closeConnection(ChannelHandlerContext ctx, Object evt) throws InterruptedException {
        ctx.channel().close().sync();
        close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOG.debug("连接出现异常", cause);
    }

    /**
     * 处理心跳包
     *
     * @param ctx
     */
    private void handleHeartbreat(ChannelHandlerContext ctx) {
        LOG.debug("<-----ping pong");
        resetHeartbreatCounter();
    }

    /**
     * 将心跳丢失计数器置为0
     */
    private void resetHeartbreatCounter() {
        counter = 0;
    }

    protected void handleData(ChannelHandlerContext ctx, Object o) {
        resetHeartbreatCounter();
        handleData0(ctx, o);
    }

    protected void close() {

    }

    protected void handleData0(ChannelHandlerContext ctx, Object msg) {
    }
}
