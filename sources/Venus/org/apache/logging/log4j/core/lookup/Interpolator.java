/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.lookup;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.ConfigurationAware;
import org.apache.logging.log4j.core.config.plugins.util.PluginManager;
import org.apache.logging.log4j.core.config.plugins.util.PluginType;
import org.apache.logging.log4j.core.lookup.AbstractConfigurationAwareLookup;
import org.apache.logging.log4j.core.lookup.ContextMapLookup;
import org.apache.logging.log4j.core.lookup.DateLookup;
import org.apache.logging.log4j.core.lookup.EnvironmentLookup;
import org.apache.logging.log4j.core.lookup.JavaLookup;
import org.apache.logging.log4j.core.lookup.Log4jLookup;
import org.apache.logging.log4j.core.lookup.MainMapLookup;
import org.apache.logging.log4j.core.lookup.MapLookup;
import org.apache.logging.log4j.core.lookup.MarkerLookup;
import org.apache.logging.log4j.core.lookup.StrLookup;
import org.apache.logging.log4j.core.lookup.SystemPropertiesLookup;
import org.apache.logging.log4j.core.util.Loader;
import org.apache.logging.log4j.core.util.ReflectionUtil;
import org.apache.logging.log4j.status.StatusLogger;

public class Interpolator
extends AbstractConfigurationAwareLookup {
    private static final String LOOKUP_KEY_WEB = "web";
    private static final String LOOKUP_KEY_JNDI = "jndi";
    private static final String LOOKUP_KEY_JVMRUNARGS = "jvmrunargs";
    private static final Logger LOGGER = StatusLogger.getLogger();
    private static final char PREFIX_SEPARATOR = ':';
    private final Map<String, StrLookup> lookups = new HashMap<String, StrLookup>();
    private final StrLookup defaultLookup;

    public Interpolator(StrLookup strLookup) {
        this(strLookup, null);
    }

    public Interpolator(StrLookup strLookup, List<String> list) {
        this.defaultLookup = strLookup == null ? new MapLookup(new HashMap<String, String>()) : strLookup;
        PluginManager pluginManager = new PluginManager("Lookup");
        pluginManager.collectPlugins(list);
        Map<String, PluginType<?>> map = pluginManager.getPlugins();
        for (Map.Entry<String, PluginType<?>> entry : map.entrySet()) {
            try {
                Class<StrLookup> clazz = entry.getValue().getPluginClass().asSubclass(StrLookup.class);
                this.lookups.put(entry.getKey(), ReflectionUtil.instantiate(clazz));
            } catch (Throwable throwable) {
                this.handleError(entry.getKey(), throwable);
            }
        }
    }

    public Interpolator() {
        this((Map<String, String>)null);
    }

    public Interpolator(Map<String, String> hashMap) {
        this.defaultLookup = new MapLookup(hashMap == null ? new HashMap() : hashMap);
        this.lookups.put("log4j", new Log4jLookup());
        this.lookups.put("sys", new SystemPropertiesLookup());
        this.lookups.put("env", new EnvironmentLookup());
        this.lookups.put("main", MainMapLookup.MAIN_SINGLETON);
        this.lookups.put("marker", new MarkerLookup());
        this.lookups.put("java", new JavaLookup());
        try {
            this.lookups.put(LOOKUP_KEY_JNDI, Loader.newCheckedInstanceOf("org.apache.logging.log4j.core.lookup.JndiLookup", StrLookup.class));
        } catch (Exception | LinkageError throwable) {
            this.handleError(LOOKUP_KEY_JNDI, throwable);
        }
        try {
            this.lookups.put(LOOKUP_KEY_JVMRUNARGS, Loader.newCheckedInstanceOf("org.apache.logging.log4j.core.lookup.JmxRuntimeInputArgumentsLookup", StrLookup.class));
        } catch (Exception | LinkageError throwable) {
            this.handleError(LOOKUP_KEY_JVMRUNARGS, throwable);
        }
        this.lookups.put("date", new DateLookup());
        this.lookups.put("ctx", new ContextMapLookup());
        if (Loader.isClassAvailable("javax.servlet.ServletContext")) {
            try {
                this.lookups.put(LOOKUP_KEY_WEB, Loader.newCheckedInstanceOf("org.apache.logging.log4j.web.WebLookup", StrLookup.class));
            } catch (Exception exception) {
                this.handleError(LOOKUP_KEY_WEB, exception);
            }
        } else {
            LOGGER.debug("Not in a ServletContext environment, thus not loading WebLookup plugin.");
        }
    }

    private void handleError(String string, Throwable throwable) {
        switch (string) {
            case "jndi": {
                LOGGER.warn("JNDI lookup class is not available because this JRE does not support JNDI. JNDI string lookups will not be available, continuing configuration. Ignoring " + throwable);
                break;
            }
            case "jvmrunargs": {
                LOGGER.warn("JMX runtime input lookup class is not available because this JRE does not support JMX. JMX lookups will not be available, continuing configuration. Ignoring " + throwable);
                break;
            }
            case "web": {
                LOGGER.info("Log4j appears to be running in a Servlet environment, but there's no log4j-web module available. If you want better web container support, please add the log4j-web JAR to your web archive or server lib directory.");
                break;
            }
            default: {
                LOGGER.error("Unable to create Lookup for {}", (Object)string, (Object)throwable);
            }
        }
    }

    @Override
    public String lookup(LogEvent logEvent, String string) {
        if (string == null) {
            return null;
        }
        int n = string.indexOf(58);
        if (n >= 0) {
            String string2 = string.substring(0, n).toLowerCase(Locale.US);
            String string3 = string.substring(n + 1);
            StrLookup strLookup = this.lookups.get(string2);
            if (strLookup instanceof ConfigurationAware) {
                ((ConfigurationAware)((Object)strLookup)).setConfiguration(this.configuration);
            }
            String string4 = null;
            if (strLookup != null) {
                String string5 = string4 = logEvent == null ? strLookup.lookup(string3) : strLookup.lookup(logEvent, string3);
            }
            if (string4 != null) {
                return string4;
            }
            string = string.substring(n + 1);
        }
        if (this.defaultLookup != null) {
            return logEvent == null ? this.defaultLookup.lookup(string) : this.defaultLookup.lookup(logEvent, string);
        }
        return null;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String string : this.lookups.keySet()) {
            if (stringBuilder.length() == 0) {
                stringBuilder.append('{');
            } else {
                stringBuilder.append(", ");
            }
            stringBuilder.append(string);
        }
        if (stringBuilder.length() > 0) {
            stringBuilder.append('}');
        }
        return stringBuilder.toString();
    }
}

