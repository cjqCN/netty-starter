package com.github.cjqcn.netty.starter.common.req;


import com.github.cjqcn.netty.starter.common.ClientType;
import com.github.cjqcn.netty.starter.common.Req;

/**
 * @description:
 * @author: chenjinquan
 * @create: 2018-09-28 09:46
 **/
public class InitReq implements Req {

    private ClientType type;

    private String clientName;

    public InitReq(ClientType type, String clientName) {
        this.type = type;
        this.clientName = clientName;
    }

    public InitReq() {
    }

    public ClientType getType() {
        return type;
    }

    public String getClientName() {
        return clientName;
    }

    @Override
    public String toString() {
        return "InitReq{" +
                "type=" + type +
                ", clientName='" + clientName + '\'' +
                '}';
    }
}
