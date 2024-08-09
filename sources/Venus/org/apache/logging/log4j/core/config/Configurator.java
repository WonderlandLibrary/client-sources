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
        LoggerContextFactory loggerContextFactory = LogManager.getFactory();
        if (loggerContextFactory instanceof Log4jContextFactory) {
            return (Log4jContextFactory)loggerContextFactory;
        }
        if (loggerContextFactory != null) {
            LOGGER.error("LogManager returned an instance of {} which does not implement {}. Unable to initialize Log4j.", (Object)loggerContextFactory.getClass().getName(), (Object)Log4jContextFactory.class.getName());
            return null;
        }
        LOGGER.fatal("LogManager did not return a LoggerContextFactory. This indicates something has gone terribly wrong!");
        return null;
    }

    public static LoggerContext initialize(ClassLoader classLoader, ConfigurationSource configurationSource) {
        return Configurator.initialize(classLoader, configurationSource, null);
    }

    public static LoggerContext initialize(ClassLoader classLoader, ConfigurationSource configurationSource, Object object) {
        try {
            Log4jContextFactory log4jContextFactory = Configurator.getFactory();
            return log4jContextFactory == null ? null : log4jContextFactory.getContext(FQCN, classLoader, object, false, configurationSource);
        } catch (Exception exception) {
            LOGGER.error("There was a problem obtaining a LoggerContext using the configuration source [{}]", (Object)configurationSource, (Object)exception);
            return null;
        }
    }

    public static LoggerContext initialize(String string, ClassLoader classLoader, String string2) {
        return Configurator.initialize(string, classLoader, string2, null);
    }

    public static LoggerContext initialize(String string, ClassLoader classLoader, String string2, Object object) {
        if (Strings.isBlank(string2)) {
            return Configurator.initialize(string, classLoader, (URI)null, object);
        }
        if (string2.contains(",")) {
            String[] stringArray = string2.split(",");
            String string3 = null;
            ArrayList<URI> arrayList = new ArrayList<URI>(stringArray.length);
            for (String string4 : stringArray) {
                URI uRI = NetUtils.toURI(string3 != null ? string3 + ":" + string4.trim() : string4.trim());
                if (string3 == null && uRI.getScheme() != null) {
                    string3 = uRI.getScheme();
                }
                arrayList.add(uRI);
            }
            return Configurator.initialize(string, classLoader, arrayList, object);
        }
        return Configurator.initialize(string, classLoader, NetUtils.toURI(string2), object);
    }

    public static LoggerContext initialize(String string, ClassLoader classLoader, URI uRI) {
        return Configurator.initialize(string, classLoader, uRI, null);
    }

    public static LoggerContext initialize(String string, ClassLoader classLoader, URI uRI, Object object) {
        try {
            Log4jContextFactory log4jContextFactory = Configurator.getFactory();
            return log4jContextFactory == null ? null : log4jContextFactory.getContext(FQCN, classLoader, object, false, uRI, string);
        } catch (Exception exception) {
            LOGGER.error("There was a problem initializing the LoggerContext [{}] using configuration at [{}].", (Object)string, (Object)uRI, (Object)exception);
            return null;
        }
    }

    public static LoggerContext initialize(String string, ClassLoader classLoader, List<URI> list, Object object) {
        try {
            Log4jContextFactory log4jContextFactory = Configurator.getFactory();
            return log4jContextFactory == null ? null : log4jContextFactory.getContext(FQCN, classLoader, object, false, list, string);
        } catch (Exception exception) {
            LOGGER.error("There was a problem initializing the LoggerContext [{}] using configurations at [{}].", (Object)string, (Object)list, (Object)exception);
            return null;
        }
    }

    public static LoggerContext initialize(String string, String string2) {
        return Configurator.initialize(string, null, string2);
    }

    public static LoggerContext initialize(Configuration configuration) {
        return Configurator.initialize(null, configuration, null);
    }

    public static LoggerContext initialize(ClassLoader classLoader, Configuration configuration) {
        return Configurator.initialize(classLoader, configuration, null);
    }

    public static LoggerContext initialize(ClassLoader classLoader, Configuration configuration, Object object) {
        try {
            Log4jContextFactory log4jContextFactory = Configurator.getFactory();
            return log4jContextFactory == null ? null : log4jContextFactory.getContext(FQCN, classLoader, object, false, configuration);
        } catch (Exception exception) {
            LOGGER.error("There was a problem initializing the LoggerContext using configuration {}", (Object)configuration.getName(), (Object)exception);
            return null;
        }
    }

    public static void setAllLevels(String string, Level level) {
        LoggerContext loggerContext = LoggerContext.getContext(false);
        Configuration configuration = loggerContext.getConfiguration();
        boolean bl = Configurator.setLevel(string, level, configuration);
        for (Map.Entry<String, LoggerConfig> entry : configuration.getLoggers().entrySet()) {
            if (!entry.getKey().startsWith(string)) continue;
            bl |= Configurator.setLevel(entry.getValue(), level);
        }
        if (bl) {
            loggerContext.updateLoggers();
        }
    }

    private static boolean setLevel(LoggerConfig loggerConfig, Level level) {
        boolean bl;
        boolean bl2 = bl = !loggerConfig.getLevel().equals(level);
        if (bl) {
            loggerConfig.setLevel(level);
        }
        return bl;
    }

    public static void setLevel(Map<String, Level> map) {
        LoggerContext loggerContext = LoggerContext.getContext(false);
        Configuration configuration = loggerContext.getConfiguration();
        boolean bl = false;
        for (Map.Entry<String, Level> entry : map.entrySet()) {
            String string = entry.getKey();
            Level level = entry.getValue();
            bl |= Configurator.setLevel(string, level, configuration);
        }
        if (bl) {
            loggerContext.updateLoggers();
        }
    }

    public static void setLevel(String string, Level level) {
        LoggerContext loggerContext = LoggerContext.getContext(false);
        if (Strings.isEmpty(string)) {
            Configurator.setRootLevel(level);
        } else if (Configurator.setLevel(string, level, loggerContext.getConfiguration())) {
            loggerContext.updateLoggers();
        }
    }

    private static boolean setLevel(String string, Level level, Configuration configuration) {
        boolean bl;
        LoggerConfig loggerConfig = configuration.getLoggerConfig(string);
        if (!string.equals(loggerConfig.getName())) {
            loggerConfig = new LoggerConfig(string, level, true);
            configuration.addLogger(string, loggerConfig);
            loggerConfig.setLevel(level);
            bl = true;
        } else {
            bl = Configurator.setLevel(loggerConfig, level);
        }
        return bl;
    }

    public static void setRootLevel(Level level) {
        LoggerContext loggerContext = LoggerContext.getContext(false);
        LoggerConfig loggerConfig = loggerContext.getConfiguration().getRootLogger();
        if (!loggerConfig.getLevel().equals(level)) {
            loggerConfig.setLevel(level);
            loggerContext.updateLoggers();
        }
    }

    public static void shutdown(LoggerContext loggerContext) {
        if (loggerContext != null) {
            loggerContext.stop();
        }
    }

    public static boolean shutdown(LoggerContext loggerContext, long l, TimeUnit timeUnit) {
        if (loggerContext != null) {
            return loggerContext.stop(l, timeUnit);
        }
        return false;
    }

    private Configurator() {
    }
}

