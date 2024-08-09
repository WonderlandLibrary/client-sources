/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.appender;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.AbstractLifeCycle;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.ManagerFactory;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.status.StatusLogger;

public abstract class AbstractManager
implements AutoCloseable {
    protected static final Logger LOGGER = StatusLogger.getLogger();
    private static final Map<String, AbstractManager> MAP = new HashMap<String, AbstractManager>();
    private static final Lock LOCK = new ReentrantLock();
    protected int count;
    private final String name;
    private final LoggerContext loggerContext;

    protected AbstractManager(LoggerContext loggerContext, String string) {
        this.loggerContext = loggerContext;
        this.name = string;
        LOGGER.debug("Starting {} {}", (Object)this.getClass().getSimpleName(), (Object)string);
    }

    @Override
    public void close() {
        this.stop(0L, AbstractLifeCycle.DEFAULT_STOP_TIMEUNIT);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean stop(long l, TimeUnit timeUnit) {
        boolean bl = true;
        LOCK.lock();
        try {
            --this.count;
            if (this.count <= 0) {
                MAP.remove(this.name);
                LOGGER.debug("Shutting down {} {}", (Object)this.getClass().getSimpleName(), (Object)this.getName());
                bl = this.releaseSub(l, timeUnit);
                LOGGER.debug("Shut down {} {}, all resources released: {}", (Object)this.getClass().getSimpleName(), (Object)this.getName(), (Object)bl);
            }
        } finally {
            LOCK.unlock();
        }
        return bl;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static <M extends AbstractManager, T> M getManager(String string, ManagerFactory<M, T> managerFactory, T t) {
        LOCK.lock();
        try {
            AbstractManager abstractManager = MAP.get(string);
            if (abstractManager == null) {
                abstractManager = (AbstractManager)managerFactory.createManager(string, t);
                if (abstractManager == null) {
                    throw new IllegalStateException("ManagerFactory [" + managerFactory + "] unable to create manager for [" + string + "] with data [" + t + "]");
                }
                MAP.put(string, abstractManager);
            } else {
                abstractManager.updateData(t);
            }
            ++abstractManager.count;
            AbstractManager abstractManager2 = abstractManager;
            return (M)abstractManager2;
        } finally {
            LOCK.unlock();
        }
    }

    public void updateData(Object object) {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean hasManager(String string) {
        LOCK.lock();
        try {
            boolean bl = MAP.containsKey(string);
            return bl;
        } finally {
            LOCK.unlock();
        }
    }

    protected boolean releaseSub(long l, TimeUnit timeUnit) {
        return false;
    }

    protected int getCount() {
        return this.count;
    }

    public LoggerContext getLoggerContext() {
        return this.loggerContext;
    }

    @Deprecated
    public void release() {
        this.close();
    }

    public String getName() {
        return this.name;
    }

    public Map<String, String> getContentFormat() {
        return new HashMap<String, String>();
    }

    protected void log(Level level, String string, Throwable throwable) {
        Message message = LOGGER.getMessageFactory().newMessage("{} {} {}: {}", this.getClass().getSimpleName(), this.getName(), string, throwable);
        LOGGER.log(level, message, throwable);
    }

    protected void logDebug(String string, Throwable throwable) {
        this.log(Level.DEBUG, string, throwable);
    }

    protected void logError(String string, Throwable throwable) {
        this.log(Level.ERROR, string, throwable);
    }

    protected void logWarn(String string, Throwable throwable) {
        this.log(Level.WARN, string, throwable);
    }
}

