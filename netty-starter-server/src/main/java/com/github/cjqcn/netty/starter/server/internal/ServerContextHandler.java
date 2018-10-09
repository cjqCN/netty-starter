package com.github.cjqcn.netty.starter.server.internal;


import com.github.cjqcn.netty.starter.common.ClientType;
import com.github.cjqcn.netty.starter.common.Msg;
import com.github.cjqcn.netty.starter.common.req.InitReq;
import com.github.cjqcn.netty.starter.server.context.AbstractNetWorkContext;
import com.github.cjqcn.netty.starter.server.context.DefaultNetWorkContext;
import com.github.cjqcn.netty.starter.server.context.OpenIdHelper;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @description:
 * @author: chenjinquan
 * @create: 2018-09-27 14:40
 **/
public class ServerContextHandler extends ServerHeartbeatHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ServerContextHandler.class);

    private AbstractNetWorkContext abstractNetWorkContext;
    private final ResourceManager resourceManager;

    ServerContextHandler(ResourceManager resourceManager) {
        this.resourceManager = resourceManager;
    }

    @Override
    protected void close() {
        if (bindNetWorkContext()) {
            abstractNetWorkContext.outline();
        }
    }

    @Override
    protected void handleData0(ChannelHandlerContext ctx, Object msg) {
        if (!(msg instanceof Msg)) {
            return;
        }
        if (bindNetWorkContext()) {
            abstractNetWorkContext.receive((Msg) msg);
        } else {
            if (msg instanceof InitReq) {
                initNetWorkContext(ctx, (InitReq) msg);
            }
        }
    }

    private void initNetWorkContext(ChannelHandlerContext ctx, InitReq initReq) {
        LOG.info("initNetWorkContext:{}", initReq);
        String clientName = initReq.getClientName();
        ClientType type = initReq.getType();
        String addr = ctx.channel().remoteAddress().toString();
        String openId = OpenIdHelper.openId(addr, type, clientName);
        AbstractNetWorkContext netWorkContext = (AbstractNetWorkContext) resourceManager.get(openId);
        if (netWorkContext != null) {
            netWorkContext.online(ctx);
        } else {
            if (type == ClientType.MORMAL) {
                netWorkContext = new DefaultNetWorkContext(clientName, ctx);
            }
        }
        this.abstractNetWorkContext = netWorkContext;
    }

    private boolean bindNetWorkContext() {
        return abstractNetWorkContext != null;
    }

    @Override
    public boolean isSharable() {
        return false;
    }

}
