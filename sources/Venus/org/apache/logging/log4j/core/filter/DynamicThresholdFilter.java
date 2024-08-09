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
    public static DynamicThresholdFilter createFilter(@PluginAttribute(value="key") String string, @PluginElement(value="Pairs") KeyValuePair[] keyValuePairArray, @PluginAttribute(value="defaultThreshold") Level level, @PluginAttribute(value="onMatch") Filter.Result result, @PluginAttribute(value="onMismatch") Filter.Result result2) {
        HashMap<String, Level> hashMap = new HashMap<String, Level>();
        for (KeyValuePair keyValuePair : keyValuePairArray) {
            hashMap.put(keyValuePair.getKey(), Level.toLevel(keyValuePair.getValue()));
        }
        Level level2 = level == null ? Level.ERROR : level;
        return new DynamicThresholdFilter(string, hashMap, level2, result, result2);
    }

    private DynamicThresholdFilter(String string, Map<String, Level> map, Level level, Filter.Result result, Filter.Result result2) {
        super(result, result2);
        Objects.requireNonNull(string, "key cannot be null");
        this.key = string;
        this.levelMap = map;
        this.defaultThreshold = level;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (!super.equalsImpl(object)) {
            return true;
        }
        if (this.getClass() != object.getClass()) {
            return true;
        }
        DynamicThresholdFilter dynamicThresholdFilter = (DynamicThresholdFilter)object;
        if (this.defaultThreshold == null ? dynamicThresholdFilter.defaultThreshold != null : !this.defaultThreshold.equals(dynamicThresholdFilter.defaultThreshold)) {
            return true;
        }
        if (this.key == null ? dynamicThresholdFilter.key != null : !this.key.equals(dynamicThresholdFilter.key)) {
            return true;
        }
        return this.levelMap == null ? dynamicThresholdFilter.levelMap != null : !this.levelMap.equals(dynamicThresholdFilter.levelMap);
    }

    private Filter.Result filter(Level level, ReadOnlyStringMap readOnlyStringMap) {
        String string = (String)readOnlyStringMap.getValue(this.key);
        if (string != null) {
            Level level2 = this.levelMap.get(string);
            if (level2 == null) {
                level2 = this.defaultThreshold;
            }
            return level.isMoreSpecificThan(level2) ? this.onMatch : this.onMismatch;
        }
        return Filter.Result.NEUTRAL;
    }

    @Override
    public Filter.Result filter(LogEvent logEvent) {
        return this.filter(logEvent.getLevel(), logEvent.getContextData());
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, Message message, Throwable throwable) {
        return this.filter(level, this.currentContextData());
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, Object object, Throwable throwable) {
        return this.filter(level, this.currentContextData());
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object ... objectArray) {
        return this.filter(level, this.currentContextData());
    }

    private ReadOnlyStringMap currentContextData() {
        return this.injector.rawContextData();
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object object) {
        return this.filter(level, this.currentContextData());
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object object, Object object2) {
        return this.filter(level, this.currentContextData());
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object object, Object object2, Object object3) {
        return this.filter(level, this.currentContextData());
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4) {
        return this.filter(level, this.currentContextData());
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5) {
        return this.filter(level, this.currentContextData());
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6) {
        return this.filter(level, this.currentContextData());
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7) {
        return this.filter(level, this.currentContextData());
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8) {
        return this.filter(level, this.currentContextData());
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9) {
        return this.filter(level, this.currentContextData());
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10) {
        return this.filter(level, this.currentContextData());
    }

    public String getKey() {
        return this.key;
    }

    public Map<String, Level> getLevelMap() {
        return this.levelMap;
    }

    public int hashCode() {
        int n = 31;
        int n2 = super.hashCodeImpl();
        n2 = 31 * n2 + (this.defaultThreshold == null ? 0 : this.defaultThreshold.hashCode());
        n2 = 31 * n2 + (this.key == null ? 0 : this.key.hashCode());
        n2 = 31 * n2 + (this.levelMap == null ? 0 : this.levelMap.hashCode());
        return n2;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("key=").append(this.key);
        stringBuilder.append(", default=").append(this.defaultThreshold);
        if (this.levelMap.size() > 0) {
            stringBuilder.append('{');
            boolean bl = true;
            for (Map.Entry<String, Level> entry : this.levelMap.entrySet()) {
                if (!bl) {
                    stringBuilder.append(", ");
                    bl = false;
                }
                stringBuilder.append(entry.getKey()).append('=').append(entry.getValue());
            }
            stringBuilder.append('}');
        }
        return stringBuilder.toString();
    }
}

