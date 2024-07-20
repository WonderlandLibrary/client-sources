/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.appender;

import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.ErrorHandler;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AppenderLoggingException;
import org.apache.logging.log4j.status.StatusLogger;

public class DefaultErrorHandler
implements ErrorHandler {
    private static final Logger LOGGER = StatusLogger.getLogger();
    private static final int MAX_EXCEPTIONS = 3;
    private static final long EXCEPTION_INTERVAL = TimeUnit.MINUTES.toNanos(5L);
    private int exceptionCount = 0;
    private long lastException = System.nanoTime() - EXCEPTION_INTERVAL - 1L;
    private final Appender appender;

    public DefaultErrorHandler(Appender appender) {
        this.appender = appender;
    }

    @Override
    public void error(String msg) {
        long current = System.nanoTime();
        if (current - this.lastException > EXCEPTION_INTERVAL || this.exceptionCount++ < 3) {
            LOGGER.error(msg);
        }
        this.lastException = current;
    }

    @Override
    public void error(String msg, Throwable t) {
        long current = System.nanoTime();
        if (current - this.lastException > EXCEPTION_INTERVAL || this.exceptionCount++ < 3) {
            LOGGER.error(msg, t);
        }
        this.lastException = current;
        if (!this.appender.ignoreExceptions() && t != null && !(t instanceof AppenderLoggingException)) {
            throw new AppenderLoggingException(msg, t);
        }
    }

    @Override
    public void error(String msg, LogEvent event, Throwable t) {
        long current = System.nanoTime();
        if (current - this.lastException > EXCEPTION_INTERVAL || this.exceptionCount++ < 3) {
            LOGGER.error(msg, t);
        }
        this.lastException = current;
        if (!this.appender.ignoreExceptions() && t != null && !(t instanceof AppenderLoggingException)) {
            throw new AppenderLoggingException(msg, t);
        }
    }

    public Appender getAppender() {
        return this.appender;
    }
}

