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

    protected AbstractWriterAppender(String name, StringLayout layout, Filter filter, boolean ignoreExceptions, boolean immediateFlush, M manager) {
        super(name, filter, layout, ignoreExceptions);
        this.manager = manager;
        this.immediateFlush = immediateFlush;
    }

    @Override
    public void append(LogEvent event) {
        this.readLock.lock();
        try {
            String str = (String)this.getStringLayout().toSerializable(event);
            if (str.length() > 0) {
                ((WriterManager)this.manager).write(str);
                if (this.immediateFlush || event.isEndOfBatch()) {
                    ((WriterManager)this.manager).flush();
                }
            }
        } catch (AppenderLoggingException ex) {
            this.error("Unable to write " + ((AbstractManager)this.manager).getName() + " for appender " + this.getName() + ": " + ex);
            throw ex;
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
    public boolean stop(long timeout, TimeUnit timeUnit) {
        this.setStopping();
        boolean stopped = super.stop(timeout, timeUnit, false);
        this.setStopped();
        return stopped &= ((AbstractManager)this.manager).stop(timeout, timeUnit);
    }
}

