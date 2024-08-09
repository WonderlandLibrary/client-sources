/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.config;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.config.ReliabilityStrategy;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.util.Supplier;

public class AwaitCompletionReliabilityStrategy
implements ReliabilityStrategy {
    private static final int MAX_RETRIES = 3;
    private final AtomicInteger counter = new AtomicInteger();
    private final AtomicBoolean shutdown = new AtomicBoolean(false);
    private final Lock shutdownLock = new ReentrantLock();
    private final Condition noLogEvents = this.shutdownLock.newCondition();
    private final LoggerConfig loggerConfig;

    public AwaitCompletionReliabilityStrategy(LoggerConfig loggerConfig) {
        this.loggerConfig = Objects.requireNonNull(loggerConfig, "loggerConfig is null");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void log(Supplier<LoggerConfig> supplier, String string, String string2, Marker marker, Level level, Message message, Throwable throwable) {
        LoggerConfig loggerConfig = this.getActiveLoggerConfig(supplier);
        try {
            loggerConfig.log(string, string2, marker, level, message, throwable);
        } finally {
            loggerConfig.getReliabilityStrategy().afterLogEvent();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void log(Supplier<LoggerConfig> supplier, LogEvent logEvent) {
        LoggerConfig loggerConfig = this.getActiveLoggerConfig(supplier);
        try {
            loggerConfig.log(logEvent);
        } finally {
            loggerConfig.getReliabilityStrategy().afterLogEvent();
        }
    }

    @Override
    public LoggerConfig getActiveLoggerConfig(Supplier<LoggerConfig> supplier) {
        LoggerConfig loggerConfig = this.loggerConfig;
        if (!this.beforeLogEvent()) {
            loggerConfig = supplier.get();
            return loggerConfig.getReliabilityStrategy().getActiveLoggerConfig(supplier);
        }
        return loggerConfig;
    }

    private boolean beforeLogEvent() {
        return this.counter.incrementAndGet() > 0;
    }

    @Override
    public void afterLogEvent() {
        if (this.counter.decrementAndGet() == 0 && this.shutdown.get()) {
            this.signalCompletionIfShutdown();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void signalCompletionIfShutdown() {
        Lock lock = this.shutdownLock;
        lock.lock();
        try {
            this.noLogEvents.signalAll();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void beforeStopAppenders() {
        this.waitForCompletion();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void waitForCompletion() {
        block8: {
            this.shutdownLock.lock();
            try {
                if (!this.shutdown.compareAndSet(false, false)) break block8;
                int n = 0;
                while (!this.counter.compareAndSet(0, 1)) {
                    if (this.counter.get() < 0) {
                        return;
                    }
                    try {
                        this.noLogEvents.await(n + 1, TimeUnit.SECONDS);
                    } catch (InterruptedException interruptedException) {
                        if (++n <= 3) continue;
                        break;
                    }
                }
            } finally {
                this.shutdownLock.unlock();
            }
        }
    }

    @Override
    public void beforeStopConfiguration(Configuration configuration) {
    }
}

