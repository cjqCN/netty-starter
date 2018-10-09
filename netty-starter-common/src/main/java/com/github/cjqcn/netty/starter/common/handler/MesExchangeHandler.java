package com.github.cjqcn.netty.starter.common.handler;


import com.github.cjqcn.netty.starter.common.MesExchange;
import com.github.cjqcn.netty.starter.common.Msg;
import com.github.cjqcn.netty.starter.common.Req;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @description:
 * @author: chenjinquan
 * @create: 2018-10-08 09:44
 **/
public abstract class MesExchangeHandler extends ClientHeartbeatHandler implements MesExchange<Msg, Req> {

    private static final Logger LOG = LoggerFactory.getLogger(MesExchangeHandler.class);

    final Channel channel;

    public MesExchangeHandler(Channel channel) {
        this.channel = channel;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        // 判断接收到的包类型
        if (msg instanceof Msg) {
            receive((Msg) msg);
        }
    }

    /**
     * 发送信息
     *
     * @param req
     */
    @Override
    public synchronized void send(Req req) throws Exception {
        if (channel.isOpen()) {
            channel.writeAndFlush(req);
        } else {
            throw new Exception("连接已断开");
        }
    }


    @Override
    public boolean isSharable() {
        return false;
    }
}
