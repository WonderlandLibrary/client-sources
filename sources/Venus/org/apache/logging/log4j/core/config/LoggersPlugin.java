/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.config;

import java.util.concurrent.ConcurrentHashMap;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.config.Loggers;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;

@Plugin(name="loggers", category="Core")
public final class LoggersPlugin {
    private LoggersPlugin() {
    }

    @PluginFactory
    public static Loggers createLoggers(@PluginElement(value="Loggers") LoggerConfig[] loggerConfigArray) {
        ConcurrentHashMap<String, LoggerConfig> concurrentHashMap = new ConcurrentHashMap<String, LoggerConfig>();
        LoggerConfig loggerConfig = null;
        for (LoggerConfig loggerConfig2 : loggerConfigArray) {
            if (loggerConfig2 == null) continue;
            if (loggerConfig2.getName().isEmpty()) {
                loggerConfig = loggerConfig2;
            }
            concurrentHashMap.put(loggerConfig2.getName(), loggerConfig2);
        }
        return new Loggers(concurrentHashMap, loggerConfig);
    }
}

