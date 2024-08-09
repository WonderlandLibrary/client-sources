/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.appender;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LoggingException;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.AppenderControl;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAliases;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginConfiguration;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.util.Booleans;

@Plugin(name="Failover", category="Core", elementType="appender", printObject=true)
public final class FailoverAppender
extends AbstractAppender {
    private static final int DEFAULT_INTERVAL_SECONDS = 60;
    private final String primaryRef;
    private final String[] failovers;
    private final Configuration config;
    private AppenderControl primary;
    private final List<AppenderControl> failoverAppenders = new ArrayList<AppenderControl>();
    private final long intervalNanos;
    private volatile long nextCheckNanos = 0L;

    private FailoverAppender(String string, Filter filter, String string2, String[] stringArray, int n, Configuration configuration, boolean bl) {
        super(string, filter, null, bl);
        this.primaryRef = string2;
        this.failovers = stringArray;
        this.config = configuration;
        this.intervalNanos = TimeUnit.MILLISECONDS.toNanos(n);
    }

    @Override
    public void start() {
        Map<String, Appender> map = this.config.getAppenders();
        int n = 0;
        Appender appender = map.get(this.primaryRef);
        if (appender != null) {
            this.primary = new AppenderControl(appender, null, null);
        } else {
            LOGGER.error("Unable to locate primary Appender " + this.primaryRef);
            ++n;
        }
        for (String string : this.failovers) {
            Appender appender2 = map.get(string);
            if (appender2 != null) {
                this.failoverAppenders.add(new AppenderControl(appender2, null, null));
                continue;
            }
            LOGGER.error("Failover appender " + string + " is not configured");
        }
        if (this.failoverAppenders.isEmpty()) {
            LOGGER.error("No failover appenders are available");
            ++n;
        }
        if (n == 0) {
            super.start();
        }
    }

    @Override
    public void append(LogEvent logEvent) {
        if (!this.isStarted()) {
            this.error("FailoverAppender " + this.getName() + " did not start successfully");
            return;
        }
        long l = this.nextCheckNanos;
        if (l == 0L || System.nanoTime() - l > 0L) {
            this.callAppender(logEvent);
        } else {
            this.failover(logEvent, null);
        }
    }

    private void callAppender(LogEvent logEvent) {
        try {
            this.primary.callAppender(logEvent);
            this.nextCheckNanos = 0L;
        } catch (Exception exception) {
            this.nextCheckNanos = System.nanoTime() + this.intervalNanos;
            this.failover(logEvent, exception);
        }
    }

    private void failover(LogEvent logEvent, Exception exception) {
        LoggingException loggingException = exception != null ? (exception instanceof LoggingException ? (LoggingException)exception : new LoggingException(exception)) : null;
        boolean bl = false;
        Exception exception2 = null;
        for (AppenderControl appenderControl : this.failoverAppenders) {
            try {
                appenderControl.callAppender(logEvent);
                bl = true;
                break;
            } catch (Exception exception3) {
                if (exception2 != null) continue;
                exception2 = exception3;
            }
        }
        if (!bl && !this.ignoreExceptions()) {
            if (loggingException != null) {
                throw loggingException;
            }
            throw new LoggingException("Unable to write to failover appenders", exception2);
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(this.getName());
        stringBuilder.append(" primary=").append(this.primary).append(", failover={");
        boolean bl = true;
        for (String string : this.failovers) {
            if (!bl) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(string);
            bl = false;
        }
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    @PluginFactory
    public static FailoverAppender createAppender(@PluginAttribute(value="name") String string, @PluginAttribute(value="primary") String string2, @PluginElement(value="Failovers") String[] stringArray, @PluginAliases(value={"retryInterval"}) @PluginAttribute(value="retryIntervalSeconds") String string3, @PluginConfiguration Configuration configuration, @PluginElement(value="Filter") Filter filter, @PluginAttribute(value="ignoreExceptions") String string4) {
        int n;
        if (string == null) {
            LOGGER.error("A name for the Appender must be specified");
            return null;
        }
        if (string2 == null) {
            LOGGER.error("A primary Appender must be specified");
            return null;
        }
        if (stringArray == null || stringArray.length == 0) {
            LOGGER.error("At least one failover Appender must be specified");
            return null;
        }
        int n2 = FailoverAppender.parseInt(string3, 60);
        if (n2 >= 0) {
            n = n2 * 1000;
        } else {
            LOGGER.warn("Interval " + string3 + " is less than zero. Using default");
            n = 60000;
        }
        boolean bl = Booleans.parseBoolean(string4, true);
        return new FailoverAppender(string, filter, string2, stringArray, n, configuration, bl);
    }
}

