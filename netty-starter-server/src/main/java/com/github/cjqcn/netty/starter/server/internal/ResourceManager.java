package com.github.cjqcn.netty.starter.server.internal;


import com.github.cjqcn.netty.starter.server.context.NetWorkContext;

import java.util.Map;

/**
 * @description:
 * @author: chenjinquan
 * @create: 2018-09-27 15:15
 **/
public interface ResourceManager {

    void register(String openId, NetWorkContext netWorkContext);

    void unregister(String openId);

    Map<String, NetWorkContext> getNetWorkContexts();

    NetWorkContext get(String openId);
}
