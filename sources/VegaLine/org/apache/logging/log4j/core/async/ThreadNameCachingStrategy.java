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
            String result = (String)THREADLOCAL_NAME.get();
            if (result == null) {
                result = Thread.currentThread().getName();
                THREADLOCAL_NAME.set(result);
            }
            return result;
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

    abstract String getThreadName();

    public static ThreadNameCachingStrategy create() {
        String name = PropertiesUtil.getProperties().getStringProperty("AsyncLogger.ThreadNameStrategy", CACHED.name());
        try {
            ThreadNameCachingStrategy result = ThreadNameCachingStrategy.valueOf(name);
            LOGGER.debug("AsyncLogger.ThreadNameStrategy={}", (Object)result);
            return result;
        } catch (Exception ex) {
            LOGGER.debug("Using AsyncLogger.ThreadNameStrategy.CACHED: '{}' not valid: {}", (Object)name, (Object)ex.toString());
            return CACHED;
        }
    }

    static {
        LOGGER = StatusLogger.getLogger();
        THREADLOCAL_NAME = new ThreadLocal();
    }
}

