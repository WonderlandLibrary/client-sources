/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.async.AsyncLoggerContextSelector;
import org.apache.logging.log4j.core.config.AppenderControl;
import org.apache.logging.log4j.core.config.AppenderControlArraySet;
import org.apache.logging.log4j.core.config.AppenderRef;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.DefaultReliabilityStrategy;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.core.config.ReliabilityStrategy;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginConfiguration;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
import org.apache.logging.log4j.core.filter.AbstractFilterable;
import org.apache.logging.log4j.core.impl.DefaultLogEventFactory;
import org.apache.logging.log4j.core.impl.Log4jLogEvent;
import org.apache.logging.log4j.core.impl.LogEventFactory;
import org.apache.logging.log4j.core.impl.ReusableLogEventFactory;
import org.apache.logging.log4j.core.util.Booleans;
import org.apache.logging.log4j.core.util.Constants;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.util.LoaderUtil;
import org.apache.logging.log4j.util.PerformanceSensitive;
import org.apache.logging.log4j.util.PropertiesUtil;
import org.apache.logging.log4j.util.Strings;

@Plugin(name="logger", category="Core", printObject=true)
public class LoggerConfig
extends AbstractFilterable {
    public static final String ROOT = "root";
    private static LogEventFactory LOG_EVENT_FACTORY = null;
    private List<AppenderRef> appenderRefs = new ArrayList<AppenderRef>();
    private final AppenderControlArraySet appenders = new AppenderControlArraySet();
    private final String name;
    private LogEventFactory logEventFactory = LOG_EVENT_FACTORY;
    private Level level;
    private boolean additive = true;
    private boolean includeLocation = true;
    private LoggerConfig parent;
    private Map<Property, Boolean> propertiesMap;
    private final List<Property> properties;
    private final boolean propertiesRequireLookup;
    private final Configuration config;
    private final ReliabilityStrategy reliabilityStrategy;

    public LoggerConfig() {
        this.level = Level.ERROR;
        this.name = "";
        this.properties = null;
        this.propertiesRequireLookup = false;
        this.config = null;
        this.reliabilityStrategy = new DefaultReliabilityStrategy(this);
    }

    public LoggerConfig(String name, Level level, boolean additive) {
        this.name = name;
        this.level = level;
        this.additive = additive;
        this.properties = null;
        this.propertiesRequireLookup = false;
        this.config = null;
        this.reliabilityStrategy = new DefaultReliabilityStrategy(this);
    }

    protected LoggerConfig(String name, List<AppenderRef> appenders, Filter filter, Level level, boolean additive, Property[] properties, Configuration config, boolean includeLocation) {
        super(filter);
        this.name = name;
        this.appenderRefs = appenders;
        this.level = level;
        this.additive = additive;
        this.includeLocation = includeLocation;
        this.config = config;
        this.properties = properties != null && properties.length > 0 ? Collections.unmodifiableList(Arrays.asList(Arrays.copyOf(properties, properties.length))) : null;
        this.propertiesRequireLookup = LoggerConfig.containsPropertyRequiringLookup(properties);
        this.reliabilityStrategy = config.getReliabilityStrategy(this);
    }

    private static boolean containsPropertyRequiringLookup(Property[] properties) {
        if (properties == null) {
            return false;
        }
        for (int i = 0; i < properties.length; ++i) {
            if (!properties[i].isValueNeedsLookup()) continue;
            return true;
        }
        return false;
    }

    @Override
    public Filter getFilter() {
        return super.getFilter();
    }

    public String getName() {
        return this.name;
    }

    public void setParent(LoggerConfig parent) {
        this.parent = parent;
    }

    public LoggerConfig getParent() {
        return this.parent;
    }

    public void addAppender(Appender appender, Level level, Filter filter) {
        this.appenders.add(new AppenderControl(appender, level, filter));
    }

    public void removeAppender(String name) {
        AppenderControl removed = null;
        while ((removed = this.appenders.remove(name)) != null) {
            this.cleanupFilter(removed);
        }
    }

    public Map<String, Appender> getAppenders() {
        return this.appenders.asMap();
    }

    protected void clearAppenders() {
        do {
            AppenderControl[] original;
            for (AppenderControl ctl : original = this.appenders.clear()) {
                this.cleanupFilter(ctl);
            }
        } while (!this.appenders.isEmpty());
    }

    private void cleanupFilter(AppenderControl ctl) {
        Filter filter = ctl.getFilter();
        if (filter != null) {
            ctl.removeFilter(filter);
            filter.stop();
        }
    }

    public List<AppenderRef> getAppenderRefs() {
        return this.appenderRefs;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Level getLevel() {
        return this.level == null ? this.parent.getLevel() : this.level;
    }

    public LogEventFactory getLogEventFactory() {
        return this.logEventFactory;
    }

    public void setLogEventFactory(LogEventFactory logEventFactory) {
        this.logEventFactory = logEventFactory;
    }

    public boolean isAdditive() {
        return this.additive;
    }

    public void setAdditive(boolean additive) {
        this.additive = additive;
    }

    public boolean isIncludeLocation() {
        return this.includeLocation;
    }

    @Deprecated
    public Map<Property, Boolean> getProperties() {
        if (this.properties == null) {
            return null;
        }
        if (this.propertiesMap == null) {
            HashMap<Property, Boolean> result = new HashMap<Property, Boolean>(this.properties.size() * 2);
            for (int i = 0; i < this.properties.size(); ++i) {
                result.put(this.properties.get(i), this.properties.get(i).isValueNeedsLookup());
            }
            this.propertiesMap = Collections.unmodifiableMap(result);
        }
        return this.propertiesMap;
    }

    public List<Property> getPropertyList() {
        return this.properties;
    }

    public boolean isPropertiesRequireLookup() {
        return this.propertiesRequireLookup;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @PerformanceSensitive(value={"allocation"})
    public void log(String loggerName, String fqcn, Marker marker, Level level, Message data, Throwable t) {
        List<Property> props = null;
        if (!this.propertiesRequireLookup) {
            props = this.properties;
        } else if (this.properties != null) {
            props = new ArrayList<Property>(this.properties.size());
            Log4jLogEvent event = Log4jLogEvent.newBuilder().setMessage(data).setMarker(marker).setLevel(level).setLoggerName(loggerName).setLoggerFqcn(fqcn).setThrown(t).build();
            for (int i = 0; i < this.properties.size(); ++i) {
                Property prop = this.properties.get(i);
                String value = prop.isValueNeedsLookup() ? this.config.getStrSubstitutor().replace((LogEvent)event, prop.getValue()) : prop.getValue();
                props.add(Property.createProperty(prop.getName(), value));
            }
        }
        LogEvent logEvent = this.logEventFactory.createEvent(loggerName, marker, fqcn, level, data, props, t);
        try {
            this.log(logEvent);
        } finally {
            ReusableLogEventFactory.release(logEvent);
        }
    }

    public void log(LogEvent event) {
        if (!this.isFiltered(event)) {
            this.processLogEvent(event);
        }
    }

    public ReliabilityStrategy getReliabilityStrategy() {
        return this.reliabilityStrategy;
    }

    private void processLogEvent(LogEvent event) {
        event.setIncludeLocation(this.isIncludeLocation());
        this.callAppenders(event);
        this.logParent(event);
    }

    private void logParent(LogEvent event) {
        if (this.additive && this.parent != null) {
            this.parent.log(event);
        }
    }

    @PerformanceSensitive(value={"allocation"})
    protected void callAppenders(LogEvent event) {
        AppenderControl[] controls = this.appenders.get();
        for (int i = 0; i < controls.length; ++i) {
            controls[i].callAppender(event);
        }
    }

    public String toString() {
        return Strings.isEmpty(this.name) ? ROOT : this.name;
    }

    @Deprecated
    public static LoggerConfig createLogger(String additivity, Level level, @PluginAttribute(value="name") String loggerName, String includeLocation, AppenderRef[] refs, Property[] properties, @PluginConfiguration Configuration config, Filter filter) {
        if (loggerName == null) {
            LOGGER.error("Loggers cannot be configured without a name");
            return null;
        }
        List<AppenderRef> appenderRefs = Arrays.asList(refs);
        String name = loggerName.equals(ROOT) ? "" : loggerName;
        boolean additive = Booleans.parseBoolean(additivity, true);
        return new LoggerConfig(name, appenderRefs, filter, level, additive, properties, config, LoggerConfig.includeLocation(includeLocation));
    }

    @PluginFactory
    public static LoggerConfig createLogger(@PluginAttribute(value="additivity", defaultBoolean=true) boolean additivity, @PluginAttribute(value="level") Level level, @Required(message="Loggers cannot be configured without a name") @PluginAttribute(value="name") String loggerName, @PluginAttribute(value="includeLocation") String includeLocation, @PluginElement(value="AppenderRef") AppenderRef[] refs, @PluginElement(value="Properties") Property[] properties, @PluginConfiguration Configuration config, @PluginElement(value="Filter") Filter filter) {
        String name = loggerName.equals(ROOT) ? "" : loggerName;
        return new LoggerConfig(name, Arrays.asList(refs), filter, level, additivity, properties, config, LoggerConfig.includeLocation(includeLocation));
    }

    protected static boolean includeLocation(String includeLocationConfigValue) {
        if (includeLocationConfigValue == null) {
            boolean sync = !AsyncLoggerContextSelector.isSelected();
            return sync;
        }
        return Boolean.parseBoolean(includeLocationConfigValue);
    }

    static {
        String factory = PropertiesUtil.getProperties().getStringProperty("Log4jLogEventFactory");
        if (factory != null) {
            try {
                Class<?> clazz = LoaderUtil.loadClass(factory);
                if (clazz != null && LogEventFactory.class.isAssignableFrom(clazz)) {
                    LOG_EVENT_FACTORY = (LogEventFactory)clazz.newInstance();
                }
            } catch (Exception ex) {
                LOGGER.error("Unable to create LogEventFactory {}", (Object)factory, (Object)ex);
            }
        }
        if (LOG_EVENT_FACTORY == null) {
            LOG_EVENT_FACTORY = Constants.ENABLE_THREADLOCALS ? new ReusableLogEventFactory() : new DefaultLogEventFactory();
        }
    }

    @Plugin(name="root", category="Core", printObject=true)
    public static class RootLogger
    extends LoggerConfig {
        @PluginFactory
        public static LoggerConfig createLogger(@PluginAttribute(value="additivity") String additivity, @PluginAttribute(value="level") Level level, @PluginAttribute(value="includeLocation") String includeLocation, @PluginElement(value="AppenderRef") AppenderRef[] refs, @PluginElement(value="Properties") Property[] properties, @PluginConfiguration Configuration config, @PluginElement(value="Filter") Filter filter) {
            List<AppenderRef> appenderRefs = Arrays.asList(refs);
            Level actualLevel = level == null ? Level.ERROR : level;
            boolean additive = Booleans.parseBoolean(additivity, true);
            return new LoggerConfig("", appenderRefs, filter, actualLevel, additive, properties, config, RootLogger.includeLocation(includeLocation));
        }
    }
}

