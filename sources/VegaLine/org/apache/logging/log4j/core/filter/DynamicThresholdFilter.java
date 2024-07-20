/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.filter;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.ContextDataInjector;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.filter.AbstractFilter;
import org.apache.logging.log4j.core.impl.ContextDataInjectorFactory;
import org.apache.logging.log4j.core.util.KeyValuePair;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.util.PerformanceSensitive;
import org.apache.logging.log4j.util.ReadOnlyStringMap;

@Plugin(name="DynamicThresholdFilter", category="Core", elementType="filter", printObject=true)
@PerformanceSensitive(value={"allocation"})
public final class DynamicThresholdFilter
extends AbstractFilter {
    private Level defaultThreshold = Level.ERROR;
    private final String key;
    private final ContextDataInjector injector = ContextDataInjectorFactory.createInjector();
    private Map<String, Level> levelMap = new HashMap<String, Level>();

    @PluginFactory
    public static DynamicThresholdFilter createFilter(@PluginAttribute(value="key") String key, @PluginElement(value="Pairs") KeyValuePair[] pairs, @PluginAttribute(value="defaultThreshold") Level defaultThreshold, @PluginAttribute(value="onMatch") Filter.Result onMatch, @PluginAttribute(value="onMismatch") Filter.Result onMismatch) {
        HashMap<String, Level> map = new HashMap<String, Level>();
        for (KeyValuePair pair : pairs) {
            map.put(pair.getKey(), Level.toLevel(pair.getValue()));
        }
        Level level = defaultThreshold == null ? Level.ERROR : defaultThreshold;
        return new DynamicThresholdFilter(key, map, level, onMatch, onMismatch);
    }

    private DynamicThresholdFilter(String key, Map<String, Level> pairs, Level defaultLevel, Filter.Result onMatch, Filter.Result onMismatch) {
        super(onMatch, onMismatch);
        Objects.requireNonNull(key, "key cannot be null");
        this.key = key;
        this.levelMap = pairs;
        this.defaultThreshold = defaultLevel;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equalsImpl(obj)) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        DynamicThresholdFilter other = (DynamicThresholdFilter)obj;
        if (this.defaultThreshold == null ? other.defaultThreshold != null : !this.defaultThreshold.equals(other.defaultThreshold)) {
            return false;
        }
        if (this.key == null ? other.key != null : !this.key.equals(other.key)) {
            return false;
        }
        return !(this.levelMap == null ? other.levelMap != null : !this.levelMap.equals(other.levelMap));
    }

    private Filter.Result filter(Level level, ReadOnlyStringMap contextMap) {
        String value = (String)contextMap.getValue(this.key);
        if (value != null) {
            Level ctxLevel = this.levelMap.get(value);
            if (ctxLevel == null) {
                ctxLevel = this.defaultThreshold;
            }
            return level.isMoreSpecificThan(ctxLevel) ? this.onMatch : this.onMismatch;
        }
        return Filter.Result.NEUTRAL;
    }

    @Override
    public Filter.Result filter(LogEvent event) {
        return this.filter(event.getLevel(), event.getContextData());
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, Message msg, Throwable t) {
        return this.filter(level, this.currentContextData());
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, Object msg, Throwable t) {
        return this.filter(level, this.currentContextData());
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String msg, Object ... params) {
        return this.filter(level, this.currentContextData());
    }

    private ReadOnlyStringMap currentContextData() {
        return this.injector.rawContextData();
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String msg, Object p0) {
        return this.filter(level, this.currentContextData());
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1) {
        return this.filter(level, this.currentContextData());
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2) {
        return this.filter(level, this.currentContextData());
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2, Object p3) {
        return this.filter(level, this.currentContextData());
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2, Object p3, Object p4) {
        return this.filter(level, this.currentContextData());
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5) {
        return this.filter(level, this.currentContextData());
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
        return this.filter(level, this.currentContextData());
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7) {
        return this.filter(level, this.currentContextData());
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8) {
        return this.filter(level, this.currentContextData());
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9) {
        return this.filter(level, this.currentContextData());
    }

    public String getKey() {
        return this.key;
    }

    public Map<String, Level> getLevelMap() {
        return this.levelMap;
    }

    public int hashCode() {
        int prime = 31;
        int result = super.hashCodeImpl();
        result = 31 * result + (this.defaultThreshold == null ? 0 : this.defaultThreshold.hashCode());
        result = 31 * result + (this.key == null ? 0 : this.key.hashCode());
        result = 31 * result + (this.levelMap == null ? 0 : this.levelMap.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("key=").append(this.key);
        sb.append(", default=").append(this.defaultThreshold);
        if (this.levelMap.size() > 0) {
            sb.append('{');
            boolean first = true;
            for (Map.Entry<String, Level> entry : this.levelMap.entrySet()) {
                if (!first) {
                    sb.append(", ");
                    first = false;
                }
                sb.append(entry.getKey()).append('=').append(entry.getValue());
            }
            sb.append('}');
        }
        return sb.toString();
    }
}

