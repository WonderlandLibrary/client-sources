/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.config;

import java.util.Objects;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.config.ReliabilityStrategy;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.util.Supplier;

public class DefaultReliabilityStrategy
implements ReliabilityStrategy {
    private final LoggerConfig loggerConfig;

    public DefaultReliabilityStrategy(LoggerConfig loggerConfig) {
        this.loggerConfig = Objects.requireNonNull(loggerConfig, "loggerConfig is null");
    }

    @Override
    public void log(Supplier<LoggerConfig> reconfigured, String loggerName, String fqcn, Marker marker, Level level, Message data, Throwable t) {
        this.loggerConfig.log(loggerName, fqcn, marker, level, data, t);
    }

    @Override
    public void log(Supplier<LoggerConfig> reconfigured, LogEvent event) {
        this.loggerConfig.log(event);
    }

    @Override
    public LoggerConfig getActiveLoggerConfig(Supplier<LoggerConfig> next) {
        return this.loggerConfig;
    }

    @Override
    public void afterLogEvent() {
    }

    @Override
    public void beforeStopAppenders() {
    }

    @Override
    public void beforeStopConfiguration(Configuration configuration) {
    }
}

