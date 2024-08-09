/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.appender.db;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.apache.logging.log4j.LoggingException;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.appender.AbstractManager;
import org.apache.logging.log4j.core.appender.AppenderLoggingException;
import org.apache.logging.log4j.core.appender.db.AbstractDatabaseManager;

public abstract class AbstractDatabaseAppender<T extends AbstractDatabaseManager>
extends AbstractAppender {
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = this.lock.readLock();
    private final Lock writeLock = this.lock.writeLock();
    private T manager;

    protected AbstractDatabaseAppender(String string, Filter filter, boolean bl, T t) {
        super(string, filter, null, bl);
        this.manager = t;
    }

    public final Layout<LogEvent> getLayout() {
        return null;
    }

    public final T getManager() {
        return this.manager;
    }

    @Override
    public final void start() {
        if (this.getManager() == null) {
            LOGGER.error("No AbstractDatabaseManager set for the appender named [{}].", (Object)this.getName());
        }
        super.start();
        if (this.getManager() != null) {
            ((AbstractDatabaseManager)this.getManager()).startup();
        }
    }

    @Override
    public boolean stop(long l, TimeUnit timeUnit) {
        this.setStopping();
        boolean bl = super.stop(l, timeUnit, false);
        if (this.getManager() != null) {
            bl &= ((AbstractManager)this.getManager()).stop(l, timeUnit);
        }
        this.setStopped();
        return bl;
    }

    @Override
    public final void append(LogEvent logEvent) {
        this.readLock.lock();
        try {
            ((AbstractDatabaseManager)this.getManager()).write(logEvent);
        } catch (LoggingException loggingException) {
            LOGGER.error("Unable to write to database [{}] for appender [{}].", (Object)((AbstractManager)this.getManager()).getName(), (Object)this.getName(), (Object)loggingException);
            throw loggingException;
        } catch (Exception exception) {
            LOGGER.error("Unable to write to database [{}] for appender [{}].", (Object)((AbstractManager)this.getManager()).getName(), (Object)this.getName(), (Object)exception);
            throw new AppenderLoggingException("Unable to write to database in appender: " + exception.getMessage(), exception);
        } finally {
            this.readLock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected final void replaceManager(T t) {
        this.writeLock.lock();
        try {
            T t2 = this.getManager();
            if (!((AbstractDatabaseManager)t).isRunning()) {
                ((AbstractDatabaseManager)t).startup();
            }
            this.manager = t;
            ((AbstractManager)t2).close();
        } finally {
            this.writeLock.unlock();
        }
    }
}

