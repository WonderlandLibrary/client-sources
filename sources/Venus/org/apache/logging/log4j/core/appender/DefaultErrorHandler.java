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
    public void error(String string) {
        long l = System.nanoTime();
        if (l - this.lastException > EXCEPTION_INTERVAL || this.exceptionCount++ < 3) {
            LOGGER.error(string);
        }
        this.lastException = l;
    }

    @Override
    public void error(String string, Throwable throwable) {
        long l = System.nanoTime();
        if (l - this.lastException > EXCEPTION_INTERVAL || this.exceptionCount++ < 3) {
            LOGGER.error(string, throwable);
        }
        this.lastException = l;
        if (!this.appender.ignoreExceptions() && throwable != null && !(throwable instanceof AppenderLoggingException)) {
            throw new AppenderLoggingException(string, throwable);
        }
    }

    @Override
    public void error(String string, LogEvent logEvent, Throwable throwable) {
        long l = System.nanoTime();
        if (l - this.lastException > EXCEPTION_INTERVAL || this.exceptionCount++ < 3) {
            LOGGER.error(string, throwable);
        }
        this.lastException = l;
        if (!this.appender.ignoreExceptions() && throwable != null && !(throwable instanceof AppenderLoggingException)) {
            throw new AppenderLoggingException(string, throwable);
        }
    }

    public Appender getAppender() {
        return this.appender;
    }
}

