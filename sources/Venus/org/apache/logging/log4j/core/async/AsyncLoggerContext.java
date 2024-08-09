/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.async;

import java.net.URI;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.async.AsyncLogger;
import org.apache.logging.log4j.core.async.AsyncLoggerDisruptor;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.DefaultConfiguration;
import org.apache.logging.log4j.core.jmx.RingBufferAdmin;
import org.apache.logging.log4j.message.MessageFactory;
import org.apache.logging.log4j.status.StatusLogger;

public class AsyncLoggerContext
extends LoggerContext {
    private final AsyncLoggerDisruptor loggerDisruptor;

    public AsyncLoggerContext(String string) {
        super(string);
        this.loggerDisruptor = new AsyncLoggerDisruptor(string);
    }

    public AsyncLoggerContext(String string, Object object) {
        super(string, object);
        this.loggerDisruptor = new AsyncLoggerDisruptor(string);
    }

    public AsyncLoggerContext(String string, Object object, URI uRI) {
        super(string, object, uRI);
        this.loggerDisruptor = new AsyncLoggerDisruptor(string);
    }

    public AsyncLoggerContext(String string, Object object, String string2) {
        super(string, object, string2);
        this.loggerDisruptor = new AsyncLoggerDisruptor(string);
    }

    @Override
    protected Logger newInstance(LoggerContext loggerContext, String string, MessageFactory messageFactory) {
        return new AsyncLogger(loggerContext, string, messageFactory, this.loggerDisruptor);
    }

    @Override
    public void setName(String string) {
        super.setName("AsyncContext[" + string + "]");
        this.loggerDisruptor.setContextName(string);
    }

    @Override
    public void start() {
        this.loggerDisruptor.start();
        super.start();
    }

    @Override
    public void start(Configuration configuration) {
        this.maybeStartHelper(configuration);
        super.start(configuration);
    }

    private void maybeStartHelper(Configuration configuration) {
        if (configuration instanceof DefaultConfiguration) {
            StatusLogger.getLogger().debug("[{}] Not starting Disruptor for DefaultConfiguration.", (Object)this.getName());
        } else {
            this.loggerDisruptor.start();
        }
    }

    @Override
    public boolean stop(long l, TimeUnit timeUnit) {
        this.setStopping();
        this.loggerDisruptor.stop(l, timeUnit);
        super.stop(l, timeUnit);
        return false;
    }

    public RingBufferAdmin createRingBufferAdmin() {
        return this.loggerDisruptor.createRingBufferAdmin(this.getName());
    }

    public void setUseThreadLocals(boolean bl) {
        this.loggerDisruptor.setUseThreadLocals(bl);
    }
}

