/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.ContextDataInjector;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAliases;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.filter.MapFilter;
import org.apache.logging.log4j.core.impl.ContextDataInjectorFactory;
import org.apache.logging.log4j.core.util.KeyValuePair;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.util.IndexedReadOnlyStringMap;
import org.apache.logging.log4j.util.PerformanceSensitive;
import org.apache.logging.log4j.util.ReadOnlyStringMap;

@Plugin(name="ThreadContextMapFilter", category="Core", elementType="filter", printObject=true)
@PluginAliases(value={"ContextMapFilter"})
@PerformanceSensitive(value={"allocation"})
public class ThreadContextMapFilter
extends MapFilter {
    private final ContextDataInjector injector = ContextDataInjectorFactory.createInjector();
    private final String key;
    private final String value;
    private final boolean useMap;

    public ThreadContextMapFilter(Map<String, List<String>> pairs, boolean oper, Filter.Result onMatch, Filter.Result onMismatch) {
        super(pairs, oper, onMatch, onMismatch);
        if (pairs.size() == 1) {
            Iterator<Map.Entry<String, List<String>>> iter = pairs.entrySet().iterator();
            Map.Entry<String, List<String>> entry = iter.next();
            if (entry.getValue().size() == 1) {
                this.key = entry.getKey();
                this.value = entry.getValue().get(0);
                this.useMap = false;
            } else {
                this.key = null;
                this.value = null;
                this.useMap = true;
            }
        } else {
            this.key = null;
            this.value = null;
            this.useMap = true;
        }
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String msg, Object ... params) {
        return this.filter();
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, Object msg, Throwable t) {
        return this.filter();
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, Message msg, Throwable t) {
        return this.filter();
    }

    private Filter.Result filter() {
        boolean match = false;
        if (this.useMap) {
            ReadOnlyStringMap currentContextData = null;
            IndexedReadOnlyStringMap map = this.getStringMap();
            for (int i = 0; i < map.size(); ++i) {
                String toMatch;
                if (currentContextData == null) {
                    currentContextData = this.currentContextData();
                }
                boolean bl = match = (toMatch = (String)currentContextData.getValue(map.getKeyAt(i))) != null && ((List)map.getValueAt(i)).contains(toMatch);
                if (!(!this.isAnd() && match || this.isAnd() && !match)) {
                    continue;
                }
                break;
            }
        } else {
            match = this.value.equals(this.currentContextData().getValue(this.key));
        }
        return match ? this.onMatch : this.onMismatch;
    }

    private ReadOnlyStringMap currentContextData() {
        return this.injector.rawContextData();
    }

    @Override
    public Filter.Result filter(LogEvent event) {
        return super.filter(event.getContextData()) ? this.onMatch : this.onMismatch;
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String msg, Object p0) {
        return this.filter();
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1) {
        return this.filter();
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2) {
        return this.filter();
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2, Object p3) {
        return this.filter();
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2, Object p3, Object p4) {
        return this.filter();
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5) {
        return this.filter();
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
        return this.filter();
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7) {
        return this.filter();
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8) {
        return this.filter();
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9) {
        return this.filter();
    }

    @PluginFactory
    public static ThreadContextMapFilter createFilter(@PluginElement(value="Pairs") KeyValuePair[] pairs, @PluginAttribute(value="operator") String oper, @PluginAttribute(value="onMatch") Filter.Result match, @PluginAttribute(value="onMismatch") Filter.Result mismatch) {
        if (pairs == null || pairs.length == 0) {
            LOGGER.error("key and value pairs must be specified for the ThreadContextMapFilter");
            return null;
        }
        HashMap<String, List<String>> map = new HashMap<String, List<String>>();
        for (KeyValuePair pair : pairs) {
            String key = pair.getKey();
            if (key == null) {
                LOGGER.error("A null key is not valid in MapFilter");
                continue;
            }
            String value = pair.getValue();
            if (value == null) {
                LOGGER.error("A null value for key " + key + " is not allowed in MapFilter");
                continue;
            }
            ArrayList<String> list = (ArrayList<String>)map.get(pair.getKey());
            if (list != null) {
                list.add(value);
                continue;
            }
            list = new ArrayList<String>();
            list.add(value);
            map.put(pair.getKey(), list);
        }
        if (map.isEmpty()) {
            LOGGER.error("ThreadContextMapFilter is not configured with any valid key value pairs");
            return null;
        }
        boolean isAnd = oper == null || !oper.equalsIgnoreCase("or");
        return new ThreadContextMapFilter(map, isAnd, match, mismatch);
    }
}

