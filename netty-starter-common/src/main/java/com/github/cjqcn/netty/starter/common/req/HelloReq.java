package com.github.cjqcn.netty.starter.common.req;


import com.github.cjqcn.netty.starter.common.Req;

/**
 * @description:
 * @author: chenjinquan
 * @create: 2018-10-08 15:23
 **/
public class HelloReq implements Req {

    @Override
    public String toString() {
        return "hello world";
    }
}
