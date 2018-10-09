package com.github.cjqcn.netty.starter.common.codec;

import com.esotericsoftware.kryo.Kryo;

/**
 * @description:
 * @author: chenjinquan
 * @create: 2018-09-28 10:05
 **/
public class ThreadLocalKryoFactory extends KryoFactory {
    private final ThreadLocal<Kryo> holder = new ThreadLocal<Kryo>() {
        @Override
        protected Kryo initialValue() {
            return createKryo();
        }
    };

    public Kryo getKryo() {
        return holder.get();
    }
}
