/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.async;

import org.apache.logging.log4j.status.StatusLogger;
import org.apache.logging.log4j.util.PropertiesUtil;

public enum ThreadNameCachingStrategy {
    CACHED{

        @Override
        public String getThreadName() {
            String string = (String)ThreadNameCachingStrategy.access$100().get();
            if (string == null) {
                string = Thread.currentThread().getName();
                ThreadNameCachingStrategy.access$100().set(string);
            }
            return string;
        }
    }
    ,
    UNCACHED{

        @Override
        public String getThreadName() {
            return Thread.currentThread().getName();
        }
    };

    private static final StatusLogger LOGGER;
    private static final ThreadLocal<String> THREADLOCAL_NAME;

    private ThreadNameCachingStrategy() {
    }

    abstract String getThreadName();

    public static ThreadNameCachingStrategy create() {
        String string = PropertiesUtil.getProperties().getStringProperty("AsyncLogger.ThreadNameStrategy", CACHED.name());
        try {
            ThreadNameCachingStrategy threadNameCachingStrategy = ThreadNameCachingStrategy.valueOf(string);
            LOGGER.debug("AsyncLogger.ThreadNameStrategy={}", (Object)threadNameCachingStrategy);
            return threadNameCachingStrategy;
        } catch (Exception exception) {
            LOGGER.debug("Using AsyncLogger.ThreadNameStrategy.CACHED: '{}' not valid: {}", (Object)string, (Object)exception.toString());
            return CACHED;
        }
    }

    ThreadNameCachingStrategy(1 var3_3) {
        this();
    }

    static ThreadLocal access$100() {
        return THREADLOCAL_NAME;
    }

    static {
        LOGGER = StatusLogger.getLogger();
        THREADLOCAL_NAME = new ThreadLocal();
    }
}

