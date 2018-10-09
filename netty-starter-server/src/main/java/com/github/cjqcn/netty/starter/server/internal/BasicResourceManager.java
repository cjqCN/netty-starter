package com.github.cjqcn.netty.starter.server.internal;

import com.github.cjqcn.netty.starter.server.context.NetWorkContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description:
 * @author: chenjinquan
 * @create: 2018-10-04 10:26
 **/
public class BasicResourceManager implements ResourceManager {

    private static final Logger LOG = LoggerFactory.getLogger(BasicResourceManager.class);

    private Map<String, NetWorkContext> map;


    public BasicResourceManager() {
        map = new ConcurrentHashMap<>();
    }

    @Override
    public void register(String openId, NetWorkContext netWorkContext) {
        LOG.info("netWorkContext is registering, openId is {}", openId);
        map.put(openId, netWorkContext);
        LOG.info("netWorkContext is registered, openId is {}", openId);
    }

    @Override
    public void unregister(String openId) {
        LOG.info("netWorkContext is unregistering, openId is {}", openId);
        map.remove(openId);
        LOG.info("netWorkContext is unregistered, openId is {}", openId);
    }

    @Override
    public Map<String, NetWorkContext> getNetWorkContexts() {
        return map;
    }

    @Override
    public NetWorkContext get(String openId) {
        return map.get(openId);
    }
}
