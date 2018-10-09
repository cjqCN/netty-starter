package com.github.cjqcn.netty.starter.common;

public interface Sender<T> {

    /**
     * 发送信息
     *
     * @param t
     */
    void send(T t) throws Exception;
}
