package com.github.cjqcn.netty.starter.server.context;


import com.github.cjqcn.netty.starter.common.ClientType;
import com.github.cjqcn.netty.starter.common.MesExchange;
import com.github.cjqcn.netty.starter.common.Msg;

/**
 * @description:
 * @author: chenjinquan
 * @create: 2018-09-25 15:08
 **/
public interface NetWorkContext<T extends Msg, O extends Msg> extends MesExchange<T, O> {

    /**
     * 唯一标识
     *
     * @return
     */
    String openId();

    /**
     * 类型
     *
     * @return
     */
    ClientType type();

    /**
     * 地址
     *
     * @return
     */
    String addr();

    /**
     * 名
     *
     * @return
     */
    String clientName();

    /**
     * 是否在线
     *
     * @return
     */
    boolean active();

    /**
     * 接入时间
     *
     * @return
     */
    long connectionTime();

    /**
     * 上一次离线时间
     *
     * @return
     */
    long outlineTime();

}
