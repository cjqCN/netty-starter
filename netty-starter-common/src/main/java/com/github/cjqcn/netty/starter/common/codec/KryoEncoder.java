package com.github.cjqcn.netty.starter.common.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @description:
 * @author: chenjinquan
 * @create: 2018-09-28 10:06
 **/
public class KryoEncoder extends MessageToByteEncoder {
    @Override
    protected void encode(ChannelHandlerContext ctx, Object message, ByteBuf out) {
        KryoSerializer.serialize(message, out);
        ctx.flush();
    }
}