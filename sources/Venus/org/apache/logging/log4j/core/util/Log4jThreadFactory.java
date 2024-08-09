/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.util;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.logging.log4j.core.util.Log4jThread;

public class Log4jThreadFactory
implements ThreadFactory {
    private static final String PREFIX = "TF-";
    private static final AtomicInteger FACTORY_NUMBER = new AtomicInteger(1);
    private static final AtomicInteger THREAD_NUMBER = new AtomicInteger(1);
    private final boolean daemon;
    private final ThreadGroup group;
    private final int priority;
    private final String threadNamePrefix;

    public static Log4jThreadFactory createDaemonThreadFactory(String string) {
        return new Log4jThreadFactory(string, true, 5);
    }

    public static Log4jThreadFactory createThreadFactory(String string) {
        return new Log4jThreadFactory(string, false, 5);
    }

    public Log4jThreadFactory(String string, boolean bl, int n) {
        this.threadNamePrefix = PREFIX + FACTORY_NUMBER.getAndIncrement() + "-" + string + "-";
        this.daemon = bl;
        this.priority = n;
        SecurityManager securityManager = System.getSecurityManager();
        this.group = securityManager != null ? securityManager.getThreadGroup() : Thread.currentThread().getThreadGroup();
    }

    @Override
    public Thread newThread(Runnable runnable) {
        Log4jThread log4jThread = new Log4jThread(this.group, runnable, this.threadNamePrefix + THREAD_NUMBER.getAndIncrement(), 0L);
        if (log4jThread.isDaemon() != this.daemon) {
            log4jThread.setDaemon(this.daemon);
        }
        if (log4jThread.getPriority() != this.priority) {
            log4jThread.setPriority(this.priority);
        }
        return log4jThread;
    }
}

