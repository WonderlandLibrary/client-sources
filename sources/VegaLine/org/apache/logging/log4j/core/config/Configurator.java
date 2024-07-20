/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.config;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.impl.Log4jContextFactory;
import org.apache.logging.log4j.core.util.NetUtils;
import org.apache.logging.log4j.spi.LoggerContextFactory;
import org.apache.logging.log4j.status.StatusLogger;
import org.apache.logging.log4j.util.Strings;

public final class Configurator {
    private static final String FQCN = Configurator.class.getName();
    private static final Logger LOGGER = StatusLogger.getLogger();

    private static Log4jContextFactory getFactory() {
        LoggerContextFactory factory = LogManager.getFactory();
        if (factory instanceof Log4jContextFactory) {
            return (Log4jContextFactory)factory;
        }
        if (factory != null) {
            LOGGER.error("LogManager returned an instance of {} which does not implement {}. Unable to initialize Log4j.", (Object)factory.getClass().getName(), (Object)Log4jContextFactory.class.getName());
            return null;
        }
        LOGGER.fatal("LogManager did not return a LoggerContextFactory. This indicates something has gone terribly wrong!");
        return null;
    }

    public static LoggerContext initialize(ClassLoader loader, ConfigurationSource source) {
        return Configurator.initialize(loader, source, null);
    }

    public static LoggerContext initialize(ClassLoader loader, ConfigurationSource source, Object externalContext) {
        try {
            Log4jContextFactory factory = Configurator.getFactory();
            return factory == null ? null : factory.getContext(FQCN, loader, externalContext, false, source);
        } catch (Exception ex) {
            LOGGER.error("There was a problem obtaining a LoggerContext using the configuration source [{}]", (Object)source, (Object)ex);
            return null;
        }
    }

    public static LoggerContext initialize(String name, ClassLoader loader, String configLocation) {
        return Configurator.initialize(name, loader, configLocation, null);
    }

    public static LoggerContext initialize(String name, ClassLoader loader, String configLocation, Object externalContext) {
        if (Strings.isBlank(configLocation)) {
            return Configurator.initialize(name, loader, (URI)null, externalContext);
        }
        if (configLocation.contains(",")) {
            String[] parts = configLocation.split(",");
            String scheme = null;
            ArrayList<URI> uris = new ArrayList<URI>(parts.length);
            for (String part : parts) {
                URI uri = NetUtils.toURI(scheme != null ? scheme + ":" + part.trim() : part.trim());
                if (scheme == null && uri.getScheme() != null) {
                    scheme = uri.getScheme();
                }
                uris.add(uri);
            }
            return Configurator.initialize(name, loader, uris, externalContext);
        }
        return Configurator.initialize(name, loader, NetUtils.toURI(configLocation), externalContext);
    }

    public static LoggerContext initialize(String name, ClassLoader loader, URI configLocation) {
        return Configurator.initialize(name, loader, configLocation, null);
    }

    public static LoggerContext initialize(String name, ClassLoader loader, URI configLocation, Object externalContext) {
        try {
            Log4jContextFactory factory = Configurator.getFactory();
            return factory == null ? null : factory.getContext(FQCN, loader, externalContext, false, configLocation, name);
        } catch (Exception ex) {
            LOGGER.error("There was a problem initializing the LoggerContext [{}] using configuration at [{}].", (Object)name, (Object)configLocation, (Object)ex);
            return null;
        }
    }

    public static LoggerContext initialize(String name, ClassLoader loader, List<URI> configLocations, Object externalContext) {
        try {
            Log4jContextFactory factory = Configurator.getFactory();
            return factory == null ? null : factory.getContext(FQCN, loader, externalContext, false, configLocations, name);
        } catch (Exception ex) {
            LOGGER.error("There was a problem initializing the LoggerContext [{}] using configurations at [{}].", (Object)name, (Object)configLocations, (Object)ex);
            return null;
        }
    }

    public static LoggerContext initialize(String name, String configLocation) {
        return Configurator.initialize(name, null, configLocation);
    }

    public static LoggerContext initialize(Configuration configuration) {
        return Configurator.initialize(null, configuration, null);
    }

    public static LoggerContext initialize(ClassLoader loader, Configuration configuration) {
        return Configurator.initialize(loader, configuration, null);
    }

    public static LoggerContext initialize(ClassLoader loader, Configuration configuration, Object externalContext) {
        try {
            Log4jContextFactory factory = Configurator.getFactory();
            return factory == null ? null : factory.getContext(FQCN, loader, externalContext, false, configuration);
        } catch (Exception ex) {
            LOGGER.error("There was a problem initializing the LoggerContext using configuration {}", (Object)configuration.getName(), (Object)ex);
            return null;
        }
    }

    public static void setAllLevels(String parentLogger, Level level) {
        LoggerContext loggerContext = LoggerContext.getContext(false);
        Configuration config = loggerContext.getConfiguration();
        boolean set = Configurator.setLevel(parentLogger, level, config);
        for (Map.Entry<String, LoggerConfig> entry : config.getLoggers().entrySet()) {
            if (!entry.getKey().startsWith(parentLogger)) continue;
            set |= Configurator.setLevel(entry.getValue(), level);
        }
        if (set) {
            loggerContext.updateLoggers();
        }
    }

    private static boolean setLevel(LoggerConfig loggerConfig, Level level) {
        boolean set;
        boolean bl = set = !loggerConfig.getLevel().equals(level);
        if (set) {
            loggerConfig.setLevel(level);
        }
        return set;
    }

    public static void setLevel(Map<String, Level> levelMap) {
        LoggerContext loggerContext = LoggerContext.getContext(false);
        Configuration config = loggerContext.getConfiguration();
        boolean set = false;
        for (Map.Entry<String, Level> entry : levelMap.entrySet()) {
            String loggerName = entry.getKey();
            Level level = entry.getValue();
            set |= Configurator.setLevel(loggerName, level, config);
        }
        if (set) {
            loggerContext.updateLoggers();
        }
    }

    public static void setLevel(String loggerName, Level level) {
        LoggerContext loggerContext = LoggerContext.getContext(false);
        if (Strings.isEmpty(loggerName)) {
            Configurator.setRootLevel(level);
        } else if (Configurator.setLevel(loggerName, level, loggerContext.getConfiguration())) {
            loggerContext.updateLoggers();
        }
    }

    private static boolean setLevel(String loggerName, Level level, Configuration config) {
        boolean set;
        LoggerConfig loggerConfig = config.getLoggerConfig(loggerName);
        if (!loggerName.equals(loggerConfig.getName())) {
            loggerConfig = new LoggerConfig(loggerName, level, true);
            config.addLogger(loggerName, loggerConfig);
            loggerConfig.setLevel(level);
            set = true;
        } else {
            set = Configurator.setLevel(loggerConfig, level);
        }
        return set;
    }

    public static void setRootLevel(Level level) {
        LoggerContext loggerContext = LoggerContext.getContext(false);
        LoggerConfig loggerConfig = loggerContext.getConfiguration().getRootLogger();
        if (!loggerConfig.getLevel().equals(level)) {
            loggerConfig.setLevel(level);
            loggerContext.updateLoggers();
        }
    }

    public static void shutdown(LoggerContext ctx) {
        if (ctx != null) {
            ctx.stop();
        }
    }

    public static boolean shutdown(LoggerContext ctx, long timeout, TimeUnit timeUnit) {
        if (ctx != null) {
            return ctx.stop(timeout, timeUnit);
        }
        return true;
    }

    private Configurator() {
    }
}

