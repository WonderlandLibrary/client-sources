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

    public AsyncLoggerContext(String name) {
        super(name);
        this.loggerDisruptor = new AsyncLoggerDisruptor(name);
    }

    public AsyncLoggerContext(String name, Object externalContext) {
        super(name, externalContext);
        this.loggerDisruptor = new AsyncLoggerDisruptor(name);
    }

    public AsyncLoggerContext(String name, Object externalContext, URI configLocn) {
        super(name, externalContext, configLocn);
        this.loggerDisruptor = new AsyncLoggerDisruptor(name);
    }

    public AsyncLoggerContext(String name, Object externalContext, String configLocn) {
        super(name, externalContext, configLocn);
        this.loggerDisruptor = new AsyncLoggerDisruptor(name);
    }

    @Override
    protected Logger newInstance(LoggerContext ctx, String name, MessageFactory messageFactory) {
        return new AsyncLogger(ctx, name, messageFactory, this.loggerDisruptor);
    }

    @Override
    public void setName(String name) {
        super.setName("AsyncContext[" + name + "]");
        this.loggerDisruptor.setContextName(name);
    }

    @Override
    public void start() {
        this.loggerDisruptor.start();
        super.start();
    }

    @Override
    public void start(Configuration config) {
        this.maybeStartHelper(config);
        super.start(config);
    }

    private void maybeStartHelper(Configuration config) {
        if (config instanceof DefaultConfiguration) {
            StatusLogger.getLogger().debug("[{}] Not starting Disruptor for DefaultConfiguration.", (Object)this.getName());
        } else {
            this.loggerDisruptor.start();
        }
    }

    @Override
    public boolean stop(long timeout, TimeUnit timeUnit) {
        this.setStopping();
        this.loggerDisruptor.stop(timeout, timeUnit);
        super.stop(timeout, timeUnit);
        return true;
    }

    public RingBufferAdmin createRingBufferAdmin() {
        return this.loggerDisruptor.createRingBufferAdmin(this.getName());
    }

    public void setUseThreadLocals(boolean useThreadLocals) {
        this.loggerDisruptor.setUseThreadLocals(useThreadLocals);
    }
}

