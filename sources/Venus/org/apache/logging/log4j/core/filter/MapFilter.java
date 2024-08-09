/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.filter.AbstractFilter;
import org.apache.logging.log4j.core.util.KeyValuePair;
import org.apache.logging.log4j.message.MapMessage;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.util.BiConsumer;
import org.apache.logging.log4j.util.IndexedReadOnlyStringMap;
import org.apache.logging.log4j.util.IndexedStringMap;
import org.apache.logging.log4j.util.PerformanceSensitive;
import org.apache.logging.log4j.util.ReadOnlyStringMap;
import org.apache.logging.log4j.util.SortedArrayStringMap;

@Plugin(name="MapFilter", category="Core", elementType="filter", printObject=true)
@PerformanceSensitive(value={"allocation"})
public class MapFilter
extends AbstractFilter {
    private final IndexedStringMap map;
    private final boolean isAnd;

    protected MapFilter(Map<String, List<String>> map, boolean bl, Filter.Result result, Filter.Result result2) {
        super(result, result2);
        this.isAnd = bl;
        Objects.requireNonNull(map, "map cannot be null");
        this.map = new SortedArrayStringMap(map.size());
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            this.map.putValue(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, Message message, Throwable throwable) {
        if (message instanceof MapMessage) {
            return this.filter((MapMessage)message) ? this.onMatch : this.onMismatch;
        }
        return Filter.Result.NEUTRAL;
    }

    @Override
    public Filter.Result filter(LogEvent logEvent) {
        Message message = logEvent.getMessage();
        if (message instanceof MapMessage) {
            return this.filter((MapMessage)message) ? this.onMatch : this.onMismatch;
        }
        return Filter.Result.NEUTRAL;
    }

    protected boolean filter(MapMessage mapMessage) {
        boolean bl = false;
        for (int i = 0; i < this.map.size(); ++i) {
            String string = mapMessage.get(this.map.getKeyAt(i));
            boolean bl2 = bl = string != null && ((List)this.map.getValueAt(i)).contains(string);
            if (!this.isAnd && bl || this.isAnd && !bl) break;
        }
        return bl;
    }

    protected boolean filter(Map<String, String> map) {
        boolean bl = false;
        for (int i = 0; i < this.map.size(); ++i) {
            String string = map.get(this.map.getKeyAt(i));
            boolean bl2 = bl = string != null && ((List)this.map.getValueAt(i)).contains(string);
            if (!this.isAnd && bl || this.isAnd && !bl) break;
        }
        return bl;
    }

    protected boolean filter(ReadOnlyStringMap readOnlyStringMap) {
        boolean bl = false;
        for (int i = 0; i < this.map.size(); ++i) {
            String string = (String)readOnlyStringMap.getValue(this.map.getKeyAt(i));
            boolean bl2 = bl = string != null && ((List)this.map.getValueAt(i)).contains(string);
            if (!this.isAnd && bl || this.isAnd && !bl) break;
        }
        return bl;
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object object) {
        return Filter.Result.NEUTRAL;
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object object, Object object2) {
        return Filter.Result.NEUTRAL;
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object object, Object object2, Object object3) {
        return Filter.Result.NEUTRAL;
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4) {
        return Filter.Result.NEUTRAL;
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5) {
        return Filter.Result.NEUTRAL;
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6) {
        return Filter.Result.NEUTRAL;
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7) {
        return Filter.Result.NEUTRAL;
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8) {
        return Filter.Result.NEUTRAL;
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9) {
        return Filter.Result.NEUTRAL;
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10) {
        return Filter.Result.NEUTRAL;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("isAnd=").append(this.isAnd);
        if (this.map.size() > 0) {
            stringBuilder.append(", {");
            for (int i = 0; i < this.map.size(); ++i) {
                List list;
                if (i > 0) {
                    stringBuilder.append(", ");
                }
                String string = (list = (List)this.map.getValueAt(i)).size() > 1 ? (String)list.get(0) : list.toString();
                stringBuilder.append(this.map.getKeyAt(i)).append('=').append(string);
            }
            stringBuilder.append('}');
        }
        return stringBuilder.toString();
    }

    protected boolean isAnd() {
        return this.isAnd;
    }

    @Deprecated
    protected Map<String, List<String>> getMap() {
        HashMap<String, List<String>> hashMap = new HashMap<String, List<String>>(this.map.size());
        this.map.forEach(new BiConsumer<String, List<String>>(this, hashMap){
            final Map val$result;
            final MapFilter this$0;
            {
                this.this$0 = mapFilter;
                this.val$result = map;
            }

            @Override
            public void accept(String string, List<String> list) {
                this.val$result.put(string, list);
            }

            @Override
            public void accept(Object object, Object object2) {
                this.accept((String)object, (List)object2);
            }
        });
        return hashMap;
    }

    protected IndexedReadOnlyStringMap getStringMap() {
        return this.map;
    }

    @PluginFactory
    public static MapFilter createFilter(@PluginElement(value="Pairs") KeyValuePair[] keyValuePairArray, @PluginAttribute(value="operator") String string, @PluginAttribute(value="onMatch") Filter.Result result, @PluginAttribute(value="onMismatch") Filter.Result result2) {
        if (keyValuePairArray == null || keyValuePairArray.length == 0) {
            LOGGER.error("keys and values must be specified for the MapFilter");
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
            LOGGER.error("MapFilter is not configured with any valid key value pairs");
            return null;
        }
        boolean bl = string == null || !string.equalsIgnoreCase("or");
        return new MapFilter(hashMap, bl, result, result2);
    }
}

