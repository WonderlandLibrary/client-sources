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

    protected AsyncLoggerConfig(String string, List<AppenderRef> list, Filter filter, Level level, boolean bl, Property[] propertyArray, Configuration configuration, boolean bl2) {
        super(string, list, filter, level, bl, propertyArray, configuration, bl2);
        this.delegate = configuration.getAsyncLoggerConfigDelegate();
        this.delegate.setLogEventFactory(this.getLogEventFactory());
    }

    @Override
    protected void callAppenders(LogEvent logEvent) {
        this.populateLazilyInitializedFields(logEvent);
        if (!this.delegate.tryEnqueue(logEvent, this)) {
            EventRoute eventRoute = this.delegate.getEventRoute(logEvent.getLevel());
            eventRoute.logMessage(this, logEvent);
        }
    }

    private void populateLazilyInitializedFields(LogEvent logEvent) {
        logEvent.getSource();
        logEvent.getThreadName();
    }

    void callAppendersInCurrentThread(LogEvent logEvent) {
        super.callAppenders(logEvent);
    }

    void callAppendersInBackgroundThread(LogEvent logEvent) {
        this.delegate.enqueueEvent(logEvent, this);
    }

    void asyncCallAppenders(LogEvent logEvent) {
        super.callAppenders(logEvent);
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
    public boolean stop(long l, TimeUnit timeUnit) {
        this.setStopping();
        super.stop(l, timeUnit, false);
        LOGGER.trace("AsyncLoggerConfig[{}] stopping...", (Object)this.displayName());
        this.setStopped();
        return false;
    }

    public RingBufferAdmin createRingBufferAdmin(String string) {
        return this.delegate.createRingBufferAdmin(string, this.getName());
    }

    @PluginFactory
    public static LoggerConfig createLogger(@PluginAttribute(value="additivity") String string, @PluginAttribute(value="level") String string2, @PluginAttribute(value="name") String string3, @PluginAttribute(value="includeLocation") String string4, @PluginElement(value="AppenderRef") AppenderRef[] appenderRefArray, @PluginElement(value="Properties") Property[] propertyArray, @PluginConfiguration Configuration configuration, @PluginElement(value="Filter") Filter filter) {
        Level level;
        if (string3 == null) {
            LOGGER.error("Loggers cannot be configured without a name");
            return null;
        }
        List<AppenderRef> list = Arrays.asList(appenderRefArray);
        try {
            level = Level.toLevel(string2, Level.ERROR);
        } catch (Exception exception) {
            LOGGER.error("Invalid Log level specified: {}. Defaulting to Error", (Object)string2);
            level = Level.ERROR;
        }
        String string5 = string3.equals("root") ? "" : string3;
        boolean bl = Booleans.parseBoolean(string, true);
        return new AsyncLoggerConfig(string5, list, filter, level, bl, propertyArray, configuration, AsyncLoggerConfig.includeLocation(string4));
    }

    protected static boolean includeLocation(String string) {
        return Boolean.parseBoolean(string);
    }

    @Plugin(name="asyncRoot", category="Core", printObject=true)
    public static class RootLogger
    extends LoggerConfig {
        @PluginFactory
        public static LoggerConfig createLogger(@PluginAttribute(value="additivity") String string, @PluginAttribute(value="level") String string2, @PluginAttribute(value="includeLocation") String string3, @PluginElement(value="AppenderRef") AppenderRef[] appenderRefArray, @PluginElement(value="Properties") Property[] propertyArray, @PluginConfiguration Configuration configuration, @PluginElement(value="Filter") Filter filter) {
            Level level;
            List<AppenderRef> list = Arrays.asList(appenderRefArray);
            try {
                level = Level.toLevel(string2, Level.ERROR);
            } catch (Exception exception) {
                LOGGER.error("Invalid Log level specified: {}. Defaulting to Error", (Object)string2);
                level = Level.ERROR;
            }
            boolean bl = Booleans.parseBoolean(string, true);
            return new AsyncLoggerConfig("", list, filter, level, bl, propertyArray, configuration, AsyncLoggerConfig.includeLocation(string3));
        }
    }
}

