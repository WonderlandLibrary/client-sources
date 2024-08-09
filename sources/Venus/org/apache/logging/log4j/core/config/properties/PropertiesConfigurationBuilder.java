/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.config.properties;

import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.ConfigurationException;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.builder.api.AppenderComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.AppenderRefComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilderFactory;
import org.apache.logging.log4j.core.config.builder.api.FilterComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.FilterableComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.LayoutComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.LoggableComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.LoggerComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.RootLoggerComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ScriptComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ScriptFileComponentBuilder;
import org.apache.logging.log4j.core.config.properties.PropertiesConfiguration;
import org.apache.logging.log4j.core.util.Builder;
import org.apache.logging.log4j.util.PropertiesUtil;
import org.apache.logging.log4j.util.Strings;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class PropertiesConfigurationBuilder
extends ConfigurationBuilderFactory
implements Builder<PropertiesConfiguration> {
    private static final String ADVERTISER_KEY = "advertiser";
    private static final String STATUS_KEY = "status";
    private static final String SHUTDOWN_HOOK = "shutdownHook";
    private static final String SHUTDOWN_TIMEOUT = "shutdownTimeout";
    private static final String VERBOSE = "verbose";
    private static final String DEST = "dest";
    private static final String PACKAGES = "packages";
    private static final String CONFIG_NAME = "name";
    private static final String MONITOR_INTERVAL = "monitorInterval";
    private static final String CONFIG_TYPE = "type";
    private final ConfigurationBuilder<PropertiesConfiguration> builder = PropertiesConfigurationBuilder.newConfigurationBuilder(PropertiesConfiguration.class);
    private LoggerContext loggerContext;
    private Properties rootProperties;

    public PropertiesConfigurationBuilder setRootProperties(Properties properties) {
        this.rootProperties = properties;
        return this;
    }

    public PropertiesConfigurationBuilder setConfigurationSource(ConfigurationSource configurationSource) {
        this.builder.setConfigurationSource(configurationSource);
        return this;
    }

    @Override
    public PropertiesConfiguration build() {
        Object object;
        Object object22;
        String string;
        Object object3;
        Object object42;
        for (String object72 : this.rootProperties.stringPropertyNames()) {
            if (object72.contains(".")) continue;
            this.builder.addRootProperty(object72, this.rootProperties.getProperty(object72));
        }
        this.builder.setStatusLevel(Level.toLevel(this.rootProperties.getProperty(STATUS_KEY), Level.ERROR)).setShutdownHook(this.rootProperties.getProperty(SHUTDOWN_HOOK)).setShutdownTimeout(Long.parseLong(this.rootProperties.getProperty(SHUTDOWN_TIMEOUT, "0")), TimeUnit.MILLISECONDS).setVerbosity(this.rootProperties.getProperty(VERBOSE)).setDestination(this.rootProperties.getProperty(DEST)).setPackages(this.rootProperties.getProperty(PACKAGES)).setConfigurationName(this.rootProperties.getProperty(CONFIG_NAME)).setMonitorInterval(this.rootProperties.getProperty(MONITOR_INTERVAL, "0")).setAdvertiser(this.rootProperties.getProperty(ADVERTISER_KEY));
        Properties properties = PropertiesUtil.extractSubset(this.rootProperties, "property");
        for (String string2 : properties.stringPropertyNames()) {
            this.builder.addProperty(string2, properties.getProperty(string2));
        }
        Map<String, Properties> map = PropertiesUtil.partitionOnCommonPrefixes(PropertiesUtil.extractSubset(this.rootProperties, "script"));
        for (Map.Entry<String, Properties> entry : map.entrySet()) {
            object42 = entry.getValue();
            object3 = (String[])((Hashtable)object42).remove(CONFIG_TYPE);
            if (object3 == null) {
                throw new ConfigurationException("No type provided for script - must be Script or ScriptFile");
            }
            if (((String)object3).equalsIgnoreCase("script")) {
                this.builder.add(this.createScript((Properties)object42));
                continue;
            }
            this.builder.add(this.createScriptFile((Properties)object42));
        }
        Properties properties2 = PropertiesUtil.extractSubset(this.rootProperties, "customLevel");
        if (properties2.size() > 0) {
            for (Object object42 : properties2.stringPropertyNames()) {
                this.builder.add(this.builder.newCustomLevel((String)object42, Integer.parseInt(properties2.getProperty((String)object42))));
            }
        }
        if ((string = this.rootProperties.getProperty("filters")) != null) {
            object3 = object42 = string.split(",");
            int n = ((String[])object3).length;
            for (int i = 0; i < n; ++i) {
                Object object5 = object3[i];
                object22 = ((String)object5).trim();
                this.builder.add(this.createFilter((String)object22, PropertiesUtil.extractSubset(this.rootProperties, "filter." + (String)object22)));
            }
        } else {
            object42 = PropertiesUtil.partitionOnCommonPrefixes(PropertiesUtil.extractSubset(this.rootProperties, "filter"));
            object3 = object42.entrySet().iterator();
            while (object3.hasNext()) {
                Map.Entry entry = (Map.Entry)object3.next();
                this.builder.add(this.createFilter(((String)entry.getKey()).trim(), (Properties)entry.getValue()));
            }
        }
        if ((object42 = this.rootProperties.getProperty("appenders")) != null) {
            for (Object object22 : object3 = ((String)object42).split(",")) {
                String string3 = ((String)object22).trim();
                this.builder.add(this.createAppender(((String)object22).trim(), PropertiesUtil.extractSubset(this.rootProperties, "appender." + string3)));
            }
        } else {
            object3 = PropertiesUtil.partitionOnCommonPrefixes(PropertiesUtil.extractSubset(this.rootProperties, "appender"));
            for (Map.Entry entry : object3.entrySet()) {
                this.builder.add(this.createAppender(((String)entry.getKey()).trim(), (Properties)entry.getValue()));
            }
        }
        if ((object3 = this.rootProperties.getProperty("loggers")) != null) {
            object = ((String)object3).split(",");
            for (String string3 : object) {
                String string4 = string3.trim();
                if (string4.equals("root")) continue;
                this.builder.add(this.createLogger(string4, PropertiesUtil.extractSubset(this.rootProperties, "logger." + string4)));
            }
        } else {
            object = PropertiesUtil.partitionOnCommonPrefixes(PropertiesUtil.extractSubset(this.rootProperties, "logger"));
            for (Map.Entry entry : object.entrySet()) {
                object22 = ((String)entry.getKey()).trim();
                if (((String)object22).equals("root")) continue;
                this.builder.add(this.createLogger((String)object22, (Properties)entry.getValue()));
            }
        }
        if (((Hashtable)(object = PropertiesUtil.extractSubset(this.rootProperties, "rootLogger"))).size() > 0) {
            this.builder.add(this.createRootLogger((Properties)object));
        }
        this.builder.setLoggerContext(this.loggerContext);
        return this.builder.build(false);
    }

    private ScriptComponentBuilder createScript(Properties properties) {
        String string = (String)properties.remove(CONFIG_NAME);
        String string2 = (String)properties.remove("language");
        String string3 = (String)properties.remove("text");
        ScriptComponentBuilder scriptComponentBuilder = this.builder.newScript(string, string2, string3);
        return PropertiesConfigurationBuilder.processRemainingProperties(scriptComponentBuilder, properties);
    }

    private ScriptFileComponentBuilder createScriptFile(Properties properties) {
        String string = (String)properties.remove(CONFIG_NAME);
        String string2 = (String)properties.remove("path");
        ScriptFileComponentBuilder scriptFileComponentBuilder = this.builder.newScriptFile(string, string2);
        return PropertiesConfigurationBuilder.processRemainingProperties(scriptFileComponentBuilder, properties);
    }

    private AppenderComponentBuilder createAppender(String string, Properties properties) {
        String string2 = (String)properties.remove(CONFIG_NAME);
        if (Strings.isEmpty(string2)) {
            throw new ConfigurationException("No name attribute provided for Appender " + string);
        }
        String string3 = (String)properties.remove(CONFIG_TYPE);
        if (Strings.isEmpty(string3)) {
            throw new ConfigurationException("No type attribute provided for Appender " + string);
        }
        AppenderComponentBuilder appenderComponentBuilder = this.builder.newAppender(string2, string3);
        this.addFiltersToComponent(appenderComponentBuilder, properties);
        Properties properties2 = PropertiesUtil.extractSubset(properties, "layout");
        if (properties2.size() > 0) {
            appenderComponentBuilder.add(this.createLayout(string2, properties2));
        }
        return PropertiesConfigurationBuilder.processRemainingProperties(appenderComponentBuilder, properties);
    }

    private FilterComponentBuilder createFilter(String string, Properties properties) {
        String string2 = (String)properties.remove(CONFIG_TYPE);
        if (Strings.isEmpty(string2)) {
            throw new ConfigurationException("No type attribute provided for Appender " + string);
        }
        String string3 = (String)properties.remove("onMatch");
        String string4 = (String)properties.remove("onMisMatch");
        FilterComponentBuilder filterComponentBuilder = this.builder.newFilter(string2, string3, string4);
        return PropertiesConfigurationBuilder.processRemainingProperties(filterComponentBuilder, properties);
    }

    private AppenderRefComponentBuilder createAppenderRef(String string, Properties properties) {
        String string2 = (String)properties.remove("ref");
        if (Strings.isEmpty(string2)) {
            throw new ConfigurationException("No ref attribute provided for AppenderRef " + string);
        }
        AppenderRefComponentBuilder appenderRefComponentBuilder = this.builder.newAppenderRef(string2);
        String string3 = (String)properties.remove("level");
        if (!Strings.isEmpty(string3)) {
            appenderRefComponentBuilder.addAttribute("level", string3);
        }
        return this.addFiltersToComponent(appenderRefComponentBuilder, properties);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private LoggerComponentBuilder createLogger(String string, Properties properties) {
        LoggerComponentBuilder loggerComponentBuilder;
        String string2 = (String)properties.remove(CONFIG_NAME);
        String string3 = (String)properties.remove("includeLocation");
        if (Strings.isEmpty(string2)) {
            throw new ConfigurationException("No name attribute provided for Logger " + string);
        }
        String string4 = (String)properties.remove("level");
        String string5 = (String)properties.remove(CONFIG_TYPE);
        if (string5 != null) {
            if (!string5.equalsIgnoreCase("asyncLogger")) throw new ConfigurationException("Unknown Logger type " + string5 + " for Logger " + string2);
            if (string3 != null) {
                boolean bl = Boolean.parseBoolean(string3);
                loggerComponentBuilder = this.builder.newAsyncLogger(string2, string4, bl);
            } else {
                loggerComponentBuilder = this.builder.newAsyncLogger(string2, string4);
            }
        } else if (string3 != null) {
            boolean bl = Boolean.parseBoolean(string3);
            loggerComponentBuilder = this.builder.newLogger(string2, string4, bl);
        } else {
            loggerComponentBuilder = this.builder.newLogger(string2, string4);
        }
        this.addLoggersToComponent(loggerComponentBuilder, properties);
        this.addFiltersToComponent(loggerComponentBuilder, properties);
        String string6 = (String)properties.remove("additivity");
        if (Strings.isEmpty(string6)) return loggerComponentBuilder;
        loggerComponentBuilder.addAttribute("additivity", string6);
        return loggerComponentBuilder;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private RootLoggerComponentBuilder createRootLogger(Properties properties) {
        RootLoggerComponentBuilder rootLoggerComponentBuilder;
        String string = (String)properties.remove("level");
        String string2 = (String)properties.remove(CONFIG_TYPE);
        String string3 = (String)properties.remove("includeLocation");
        if (string2 != null) {
            if (!string2.equalsIgnoreCase("asyncRoot")) throw new ConfigurationException("Unknown Logger type for root logger" + string2);
            if (string3 != null) {
                boolean bl = Boolean.parseBoolean(string3);
                rootLoggerComponentBuilder = this.builder.newAsyncRootLogger(string, bl);
            } else {
                rootLoggerComponentBuilder = this.builder.newAsyncRootLogger(string);
            }
        } else if (string3 != null) {
            boolean bl = Boolean.parseBoolean(string3);
            rootLoggerComponentBuilder = this.builder.newRootLogger(string, bl);
        } else {
            rootLoggerComponentBuilder = this.builder.newRootLogger(string);
        }
        this.addLoggersToComponent(rootLoggerComponentBuilder, properties);
        return this.addFiltersToComponent(rootLoggerComponentBuilder, properties);
    }

    private LayoutComponentBuilder createLayout(String string, Properties properties) {
        String string2 = (String)properties.remove(CONFIG_TYPE);
        if (Strings.isEmpty(string2)) {
            throw new ConfigurationException("No type attribute provided for Layout on Appender " + string);
        }
        LayoutComponentBuilder layoutComponentBuilder = this.builder.newLayout(string2);
        return PropertiesConfigurationBuilder.processRemainingProperties(layoutComponentBuilder, properties);
    }

    private static <B extends ComponentBuilder<B>> ComponentBuilder<B> createComponent(ComponentBuilder<?> componentBuilder, String string, Properties properties) {
        String string2 = (String)properties.remove(CONFIG_NAME);
        String string3 = (String)properties.remove(CONFIG_TYPE);
        if (Strings.isEmpty(string3)) {
            throw new ConfigurationException("No type attribute provided for component " + string);
        }
        ComponentBuilder componentBuilder2 = componentBuilder.getBuilder().newComponent(string2, string3);
        return PropertiesConfigurationBuilder.processRemainingProperties(componentBuilder2, properties);
    }

    private static <B extends ComponentBuilder<?>> B processRemainingProperties(B b, Properties properties) {
        while (properties.size() > 0) {
            String string = properties.stringPropertyNames().iterator().next();
            int n = string.indexOf(46);
            if (n > 0) {
                String string2 = string.substring(0, n);
                Properties properties2 = PropertiesUtil.extractSubset(properties, string2);
                b.addComponent(PropertiesConfigurationBuilder.createComponent(b, string2, properties2));
                continue;
            }
            b.addAttribute(string, properties.getProperty(string));
            properties.remove(string);
        }
        return b;
    }

    private <B extends FilterableComponentBuilder<? extends ComponentBuilder<?>>> B addFiltersToComponent(B b, Properties properties) {
        Map<String, Properties> map = PropertiesUtil.partitionOnCommonPrefixes(PropertiesUtil.extractSubset(properties, "filter"));
        for (Map.Entry<String, Properties> entry : map.entrySet()) {
            b.add(this.createFilter(entry.getKey().trim(), entry.getValue()));
        }
        return b;
    }

    private <B extends LoggableComponentBuilder<? extends ComponentBuilder<?>>> B addLoggersToComponent(B b, Properties properties) {
        Map<String, Properties> map = PropertiesUtil.partitionOnCommonPrefixes(PropertiesUtil.extractSubset(properties, "appenderRef"));
        for (Map.Entry<String, Properties> entry : map.entrySet()) {
            b.add(this.createAppenderRef(entry.getKey().trim(), entry.getValue()));
        }
        return b;
    }

    public PropertiesConfigurationBuilder setLoggerContext(LoggerContext loggerContext) {
        this.loggerContext = loggerContext;
        return this;
    }

    public LoggerContext getLoggerContext() {
        return this.loggerContext;
    }

    @Override
    public Object build() {
        return this.build();
    }
}

