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

    public ThreadContextMapFilter(Map<String, List<String>> map, boolean bl, Filter.Result result, Filter.Result result2) {
        super(map, bl, result, result2);
        if (map.size() == 1) {
            Iterator<Map.Entry<String, List<String>>> iterator2 = map.entrySet().iterator();
            Map.Entry<String, List<String>> entry = iterator2.next();
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
    public Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object ... objectArray) {
        return this.filter();
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, Object object, Throwable throwable) {
        return this.filter();
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, Message message, Throwable throwable) {
        return this.filter();
    }

    private Filter.Result filter() {
        boolean bl = false;
        if (this.useMap) {
            ReadOnlyStringMap readOnlyStringMap = null;
            IndexedReadOnlyStringMap indexedReadOnlyStringMap = this.getStringMap();
            for (int i = 0; i < indexedReadOnlyStringMap.size(); ++i) {
                String string;
                if (readOnlyStringMap == null) {
                    readOnlyStringMap = this.currentContextData();
                }
                boolean bl2 = bl = (string = (String)readOnlyStringMap.getValue(indexedReadOnlyStringMap.getKeyAt(i))) != null && ((List)indexedReadOnlyStringMap.getValueAt(i)).contains(string);
                if (!(!this.isAnd() && bl || this.isAnd() && !bl)) {
                    continue;
                }
                break;
            }
        } else {
            bl = this.value.equals(this.currentContextData().getValue(this.key));
        }
        return bl ? this.onMatch : this.onMismatch;
    }

    private ReadOnlyStringMap currentContextData() {
        return this.injector.rawContextData();
    }

    @Override
    public Filter.Result filter(LogEvent logEvent) {
        return super.filter(logEvent.getContextData()) ? this.onMatch : this.onMismatch;
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object object) {
        return this.filter();
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object object, Object object2) {
        return this.filter();
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object object, Object object2, Object object3) {
        return this.filter();
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4) {
        return this.filter();
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5) {
        return this.filter();
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6) {
        return this.filter();
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7) {
        return this.filter();
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8) {
        return this.filter();
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9) {
        return this.filter();
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10) {
        return this.filter();
    }

    @PluginFactory
    public static ThreadContextMapFilter createFilter(@PluginElement(value="Pairs") KeyValuePair[] keyValuePairArray, @PluginAttribute(value="operator") String string, @PluginAttribute(value="onMatch") Filter.Result result, @PluginAttribute(value="onMismatch") Filter.Result result2) {
        if (keyValuePairArray == null || keyValuePairArray.length == 0) {
            LOGGER.error("key and value pairs must be specified for the ThreadContextMapFilter");
            return null;
        }
        HashMap<String, List<String>> hashMap = new HashMap<String, List<String>>();
        for (KeyValuePair keyValuePair : keyValuePairArray) {
            String string2 = keyValuePair.getKey();
            if (string2 == null) {
                LOGGER.error("A null key is not valid in MapFilter");
                continue;
            }
            String string3 = keyValuePair.getValue();
            if (string3 == null) {
                LOGGER.error("A null value for key " + string2 + " is not allowed in MapFilter");
                continue;
            }
            ArrayList<String> arrayList = (ArrayList<String>)hashMap.get(keyValuePair.getKey());
            if (arrayList != null) {
                arrayList.add(string3);
                continue;
            }
            arrayList = new ArrayList<String>();
            arrayList.add(string3);
            hashMap.put(keyValuePair.getKey(), arrayList);
        }
        if (hashMap.isEmpty()) {
            LOGGER.error("ThreadContextMapFilter is not configured with any valid key value pairs");
            return null;
        }
        boolean bl = string == null || !string.equalsIgnoreCase("or");
        return new ThreadContextMapFilter(hashMap, bl, result, result2);
    }
}

