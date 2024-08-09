/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.config;

import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.async.AsyncLoggerConfigDelegate;
import org.apache.logging.log4j.core.config.ConfigurationListener;
import org.apache.logging.log4j.core.config.ConfigurationScheduler;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.CustomLevelConfig;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.config.Node;
import org.apache.logging.log4j.core.config.ReliabilityStrategy;
import org.apache.logging.log4j.core.filter.Filterable;
import org.apache.logging.log4j.core.lookup.StrSubstitutor;
import org.apache.logging.log4j.core.net.Advertiser;
import org.apache.logging.log4j.core.script.ScriptManager;
import org.apache.logging.log4j.core.util.NanoClock;
import org.apache.logging.log4j.core.util.WatchManager;

public interface Configuration
extends Filterable {
    public static final String CONTEXT_PROPERTIES = "ContextProperties";

    public String getName();

    public LoggerConfig getLoggerConfig(String var1);

    public <T extends Appender> T getAppender(String var1);

    public Map<String, Appender> getAppenders();

    public void addAppender(Appender var1);

    public Map<String, LoggerConfig> getLoggers();

    public void addLoggerAppender(Logger var1, Appender var2);

    public void addLoggerFilter(Logger var1, Filter var2);

    public void setLoggerAdditive(Logger var1, boolean var2);

    public void addLogger(String var1, LoggerConfig var2);

    public void removeLogger(String var1);

    public List<String> getPluginPackages();

    public Map<String, String> getProperties();

    public LoggerConfig getRootLogger();

    public void addListener(ConfigurationListener var1);

    public void removeListener(ConfigurationListener var1);

    public StrSubstitutor getStrSubstitutor();

    public void createConfiguration(Node var1, LogEvent var2);

    public <T> T getComponent(String var1);

    public void addComponent(String var1, Object var2);

    public void setAdvertiser(Advertiser var1);

    public Advertiser getAdvertiser();

    public boolean isShutdownHookEnabled();

    public long getShutdownTimeoutMillis();

    public ConfigurationScheduler getScheduler();

    public ConfigurationSource getConfigurationSource();

    public List<CustomLevelConfig> getCustomLevels();

    public ScriptManager getScriptManager();

    public AsyncLoggerConfigDelegate getAsyncLoggerConfigDelegate();

    public WatchManager getWatchManager();

    public ReliabilityStrategy getReliabilityStrategy(LoggerConfig var1);

    public NanoClock getNanoClock();

    public void setNanoClock(NanoClock var1);

    public LoggerContext getLoggerContext();
}

