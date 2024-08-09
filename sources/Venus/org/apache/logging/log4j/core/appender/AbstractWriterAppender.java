/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.appender;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.StringLayout;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.appender.AbstractManager;
import org.apache.logging.log4j.core.appender.AppenderLoggingException;
import org.apache.logging.log4j.core.appender.WriterManager;

public abstract class AbstractWriterAppender<M extends WriterManager>
extends AbstractAppender {
    protected final boolean immediateFlush;
    private final M manager;
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Lock readLock = this.readWriteLock.readLock();

    protected AbstractWriterAppender(String string, StringLayout stringLayout, Filter filter, boolean bl, boolean bl2, M m) {
        super(string, filter, stringLayout, bl);
        this.manager = m;
        this.immediateFlush = bl2;
    }

    @Override
    public void append(LogEvent logEvent) {
        this.readLock.lock();
        try {
            String string = (String)this.getStringLayout().toSerializable(logEvent);
            if (string.length() > 0) {
                ((WriterManager)this.manager).write(string);
                if (this.immediateFlush || logEvent.isEndOfBatch()) {
                    ((WriterManager)this.manager).flush();
                }
            }
        } catch (AppenderLoggingException appenderLoggingException) {
            this.error("Unable to write " + ((AbstractManager)this.manager).getName() + " for appender " + this.getName() + ": " + appenderLoggingException);
            throw appenderLoggingException;
        } finally {
            this.readLock.unlock();
        }
    }

    public M getManager() {
        return this.manager;
    }

    public StringLayout getStringLayout() {
        return (StringLayout)this.getLayout();
    }

    @Override
    public void start() {
        if (this.getLayout() == null) {
            LOGGER.error("No layout set for the appender named [{}].", (Object)this.getName());
        }
        if (this.manager == null) {
            LOGGER.error("No OutputStreamManager set for the appender named [{}].", (Object)this.getName());
        }
        super.start();
    }

    @Override
    public boolean stop(long l, TimeUnit timeUnit) {
        this.setStopping();
        boolean bl = super.stop(l, timeUnit, false);
        this.setStopped();
        return bl &= ((AbstractManager)this.manager).stop(l, timeUnit);
    }
}

