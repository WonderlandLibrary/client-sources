/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.async;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.async.AsyncLoggerConfigDelegate;
import org.apache.logging.log4j.core.async.EventRoute;
import org.apache.logging.log4j.core.config.AppenderRef;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginConfiguration;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.jmx.RingBufferAdmin;
import org.apache.logging.log4j.core.util.Booleans;

@Plugin(name="asyncLogger", category="Core", printObject=true)
public class AsyncLoggerConfig
extends LoggerConfig {
    private final AsyncLoggerConfigDelegate delegate;

    protected AsyncLoggerConfig(String name, List<AppenderRef> appenders, Filter filter, Level level, boolean additive, Property[] properties, Configuration config, boolean includeLocation) {
        super(name, appenders, filter, level, additive, properties, config, includeLocation);
        this.delegate = config.getAsyncLoggerConfigDelegate();
        this.delegate.setLogEventFactory(this.getLogEventFactory());
    }

    @Override
    protected void callAppenders(LogEvent event) {
        this.populateLazilyInitializedFields(event);
        if (!this.delegate.tryEnqueue(event, this)) {
            EventRoute eventRoute = this.delegate.getEventRoute(event.getLevel());
            eventRoute.logMessage(this, event);
        }
    }

    private void populateLazilyInitializedFields(LogEvent event) {
        event.getSource();
        event.getThreadName();
    }

    void callAppendersInCurrentThread(LogEvent event) {
        super.callAppenders(event);
    }

    void callAppendersInBackgroundThread(LogEvent event) {
        this.delegate.enqueueEvent(event, this);
    }

    void asyncCallAppenders(LogEvent event) {
        super.callAppenders(event);
    }

    private String displayName() {
        return "".equals(this.getName()) ? "root" : this.getName();
    }

    @Override
    public void start() {
        LOGGER.trace("AsyncLoggerConfig[{}] starting...", (Object)this.displayName());
        super.start();
    }

    @Override
    public boolean stop(long timeout, TimeUnit timeUnit) {
        this.setStopping();
        super.stop(timeout, timeUnit, false);
        LOGGER.trace("AsyncLoggerConfig[{}] stopping...", (Object)this.displayName());
        this.setStopped();
        return true;
    }

    public RingBufferAdmin createRingBufferAdmin(String contextName) {
        return this.delegate.createRingBufferAdmin(contextName, this.getName());
    }

    @PluginFactory
    public static LoggerConfig createLogger(@PluginAttribute(value="additivity") String additivity, @PluginAttribute(value="level") String levelName, @PluginAttribute(value="name") String loggerName, @PluginAttribute(value="includeLocation") String includeLocation, @PluginElement(value="AppenderRef") AppenderRef[] refs, @PluginElement(value="Properties") Property[] properties, @PluginConfiguration Configuration config, @PluginElement(value="Filter") Filter filter) {
        Level level;
        if (loggerName == null) {
            LOGGER.error("Loggers cannot be configured without a name");
            return null;
        }
        List<AppenderRef> appenderRefs = Arrays.asList(refs);
        try {
            level = Level.toLevel(levelName, Level.ERROR);
        } catch (Exception ex) {
            LOGGER.error("Invalid Log level specified: {}. Defaulting to Error", (Object)levelName);
            level = Level.ERROR;
        }
        String name = loggerName.equals("root") ? "" : loggerName;
        boolean additive = Booleans.parseBoolean(additivity, true);
        return new AsyncLoggerConfig(name, appenderRefs, filter, level, additive, properties, config, AsyncLoggerConfig.includeLocation(includeLocation));
    }

    protected static boolean includeLocation(String includeLocationConfigValue) {
        return Boolean.parseBoolean(includeLocationConfigValue);
    }

    @Plugin(name="asyncRoot", category="Core", printObject=true)
    public static class RootLogger
    extends LoggerConfig {
        @PluginFactory
        public static LoggerConfig createLogger(@PluginAttribute(value="additivity") String additivity, @PluginAttribute(value="level") String levelName, @PluginAttribute(value="includeLocation") String includeLocation, @PluginElement(value="AppenderRef") AppenderRef[] refs, @PluginElement(value="Properties") Property[] properties, @PluginConfiguration Configuration config, @PluginElement(value="Filter") Filter filter) {
            Level level;
            List<AppenderRef> appenderRefs = Arrays.asList(refs);
            try {
                level = Level.toLevel(levelName, Level.ERROR);
            } catch (Exception ex) {
                LOGGER.error("Invalid Log level specified: {}. Defaulting to Error", (Object)levelName);
                level = Level.ERROR;
            }
            boolean additive = Booleans.parseBoolean(additivity, true);
            return new AsyncLoggerConfig("", appenderRefs, filter, level, additive, properties, config, AsyncLoggerConfig.includeLocation(includeLocation));
        }
    }
}

