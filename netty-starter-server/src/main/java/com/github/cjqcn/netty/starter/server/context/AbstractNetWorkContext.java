package com.github.cjqcn.netty.starter.server.context;


import com.github.cjqcn.netty.starter.common.Msg;
import com.github.cjqcn.netty.starter.common.Req;
import com.github.cjqcn.netty.starter.common.util.DateUtil;
import io.netty.channel.ChannelHandlerContext;

/**
 * @description:
 * @author: chenjinquan
 * @create: 2018-09-27 14:27
 **/
public abstract class AbstractNetWorkContext implements NetWorkContext<Msg, Req> {

    private ChannelHandlerContext ctx;

    private long connectionTime;

    private long outlineTime;

    public void online(ChannelHandlerContext ctx) {
        this.ctx = ctx;
        connectionTime = DateUtil.curTime();
    }

    public synchronized void outline() {
        if (active()) {
            ctx.channel().close();
            ctx = null;
            outlineTime = DateUtil.curTime();
        }
    }

    @Override
    public synchronized String addr() {
        if (active()) {
            return ctx.channel().remoteAddress().toString();
        } else {
            return null;
        }
    }

    @Override
    public synchronized boolean active() {
        return ctx != null && ctx.channel().isOpen();
    }

    @Override
    public String openId() {
        return OpenIdHelper.openId(this);
    }

    @Override
    public long connectionTime() {
        return connectionTime;
    }

    @Override
    public long outlineTime() {
        return outlineTime;
    }

    @Override
    public synchronized void send(Req msg) throws Exception {
        if (active()) {
            ctx.writeAndFlush(msg);
        } else {
            throw new Exception("连接已断开");
        }
    }

}
