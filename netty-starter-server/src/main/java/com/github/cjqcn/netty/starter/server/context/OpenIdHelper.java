package com.github.cjqcn.netty.starter.server.context;

import com.github.cjqcn.netty.starter.common.ClientType;

/**
 * @description:
 * @author: chenjinquan
 * @create: 2018-10-04 09:58
 **/
public class OpenIdHelper {

    public static final String openId(String addr, ClientType type, String clientName) {
        return addr + "/" + type + "/" + clientName;
    }


    public static final String openId(NetWorkContext netWorkContext) {
        return netWorkContext.addr() + "/"
                + netWorkContext.type() + "/"
                + netWorkContext.clientName();
    }
}
