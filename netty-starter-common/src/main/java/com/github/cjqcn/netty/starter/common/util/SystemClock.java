package com.github.cjqcn.netty.starter.common.util;

import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class SystemClock {
    private final long period;
    private final AtomicLong now;

    private SystemClock(long period) {
        this.period = period;
        this.now = new AtomicLong(Instant.now().toEpochMilli());
        scheduleClockUpdating();
    }

    private static class InstanceHolder {
        public static final SystemClock INSTANCE = new SystemClock(1);
    }

    private static SystemClock instance() {
        return InstanceHolder.INSTANCE;
    }

    private void scheduleClockUpdating() {

        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(x -> {
                    Thread thread = new Thread(x, "SystemPo Clock");
                    thread.setDaemon(true);
                    return thread;
                }
        );

        scheduler.scheduleAtFixedRate(() -> now.set(Instant.now().toEpochMilli()),
                period, period, TimeUnit.MILLISECONDS);

    }

    private long currentTimeMillis() {
        return now.get();
    }

    public static long now() {
        return instance().currentTimeMillis();
    }

}

