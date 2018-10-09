package com.github.cjqcn.netty.starter.client;

import com.github.cjqcn.netty.starter.common.ClientType;
import com.github.cjqcn.netty.starter.common.Msg;
import com.github.cjqcn.netty.starter.common.Req;
import com.github.cjqcn.netty.starter.common.Sender;
import com.github.cjqcn.netty.starter.common.handler.MesExchangeHandler;
import com.github.cjqcn.netty.starter.common.req.InitReq;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * @description:
 * @author: chenjinquan
 * @create: 2018-10-08 09:44
 **/
@Slf4j
public class ClientHandler extends MesExchangeHandler implements Sender<Req> {

    ClientHandler(Channel channel) {
        super(channel);
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.writeAndFlush(new InitReq(ClientType.MORMAL, "Client1"));
    }

    /**
     * 响应
     *
     * @param msg 接收信息
     */
    @Override
    public void receive(Msg msg) {
        log.debug("<----{}", msg);
    }

}
