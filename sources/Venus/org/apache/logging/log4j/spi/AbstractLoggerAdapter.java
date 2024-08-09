/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.spi;

import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.spi.LoggerAdapter;
import org.apache.logging.log4j.spi.LoggerContext;
import org.apache.logging.log4j.util.LoaderUtil;

public abstract class AbstractLoggerAdapter<L>
implements LoggerAdapter<L> {
    protected final Map<LoggerContext, ConcurrentMap<String, L>> registry = new WeakHashMap<LoggerContext, ConcurrentMap<String, L>>();
    private final ReadWriteLock lock = new ReentrantReadWriteLock(true);

    @Override
    public L getLogger(String string) {
        LoggerContext loggerContext = this.getContext();
        ConcurrentMap<String, L> concurrentMap = this.getLoggersInContext(loggerContext);
        Object v = concurrentMap.get(string);
        if (v != null) {
            return (L)v;
        }
        concurrentMap.putIfAbsent(string, this.newLogger(string, loggerContext));
        return (L)concurrentMap.get(string);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public ConcurrentMap<String, L> getLoggersInContext(LoggerContext loggerContext) {
        ConcurrentMap<String, L> concurrentMap;
        this.lock.readLock().lock();
        try {
            concurrentMap = this.registry.get(loggerContext);
        } finally {
            this.lock.readLock().unlock();
        }
        if (concurrentMap != null) {
            return concurrentMap;
        }
        this.lock.writeLock().lock();
        try {
            concurrentMap = this.registry.get(loggerContext);
            if (concurrentMap == null) {
                concurrentMap = new ConcurrentHashMap<String, L>();
                this.registry.put(loggerContext, concurrentMap);
            }
            ConcurrentMap<String, L> concurrentMap2 = concurrentMap;
            return concurrentMap2;
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    protected abstract L newLogger(String var1, LoggerContext var2);

    protected abstract LoggerContext getContext();

    protected LoggerContext getContext(Class<?> clazz) {
        ClassLoader classLoader = null;
        if (clazz != null) {
            classLoader = clazz.getClassLoader();
        }
        if (classLoader == null) {
            classLoader = LoaderUtil.getThreadContextClassLoader();
        }
        return LogManager.getContext(classLoader, false);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void close() {
        this.lock.writeLock().lock();
        try {
            this.registry.clear();
        } finally {
            this.lock.writeLock().unlock();
        }
    }
}

