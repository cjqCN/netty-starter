package com.github.cjqcn.netty.starter.common;

/**
 * @description:
 * @author: chenjinquan
 * @create: 2018-09-28 10:10
 **/
public class Heartbeat implements Req, Res {
    public static final Heartbeat instance = new Heartbeat();

    @Override
    public String toString() {
        return "ping pong";
    }
}
