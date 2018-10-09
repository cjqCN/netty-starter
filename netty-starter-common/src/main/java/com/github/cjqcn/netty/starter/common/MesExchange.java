package com.github.cjqcn.netty.starter.common;

public interface MesExchange<T, O> extends Sender<O> {

    /**
     * 响应
     *
     * @param t 接收信息
     * @return R != null 时，回复R
     */
    void receive(T t);
}
