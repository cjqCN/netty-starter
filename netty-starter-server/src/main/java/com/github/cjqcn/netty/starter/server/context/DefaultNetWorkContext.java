package com.github.cjqcn.netty.starter.server.context;

import com.github.cjqcn.netty.starter.common.ClientType;
import com.github.cjqcn.netty.starter.common.Msg;
import com.github.cjqcn.netty.starter.common.Req;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @description:
 * @author: chenjinquan
 * @create: 2018-10-04 09:43
 **/
public class DefaultNetWorkContext extends AbstractNetWorkContext implements NetWorkContext<Msg, Req> {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultNetWorkContext.class);
    private final String clientName;

    public DefaultNetWorkContext(final String clientName, ChannelHandlerContext ctx) {
        this.clientName = clientName;
        online(ctx);
    }


    @Override
    public ClientType type() {
        return ClientType.MORMAL;
    }

    @Override
    public String clientName() {
        return clientName;
    }

    @Override
    public void receive(Msg nettyMsg) {
        LOG.info("{}", nettyMsg);
    }


}
