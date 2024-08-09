/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.config;

import java.util.Objects;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.config.ReliabilityStrategy;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.util.Supplier;

public class LockingReliabilityStrategy
implements ReliabilityStrategy {
    private final LoggerConfig loggerConfig;
    private final ReadWriteLock reconfigureLock = new ReentrantReadWriteLock();
    private volatile boolean isStopping = false;

    public LockingReliabilityStrategy(LoggerConfig loggerConfig) {
        this.loggerConfig = Objects.requireNonNull(loggerConfig, "loggerConfig was null");
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
        this.reconfigureLock.readLock().lock();
        if (this.isStopping) {
            this.reconfigureLock.readLock().unlock();
            return true;
        }
        return false;
    }

    @Override
    public void afterLogEvent() {
        this.reconfigureLock.readLock().unlock();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void beforeStopAppenders() {
        this.reconfigureLock.writeLock().lock();
        try {
            this.isStopping = true;
        } finally {
            this.reconfigureLock.writeLock().unlock();
        }
    }

    @Override
    public void beforeStopConfiguration(Configuration configuration) {
    }
}

