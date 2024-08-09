/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.filter.MapFilter;
import org.apache.logging.log4j.core.util.KeyValuePair;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.StructuredDataMessage;
import org.apache.logging.log4j.util.IndexedReadOnlyStringMap;
import org.apache.logging.log4j.util.PerformanceSensitive;
import org.apache.logging.log4j.util.StringBuilders;

@Plugin(name="StructuredDataFilter", category="Core", elementType="filter", printObject=true)
@PerformanceSensitive(value={"allocation"})
public final class StructuredDataFilter
extends MapFilter {
    private static final int MAX_BUFFER_SIZE = 2048;
    private static ThreadLocal<StringBuilder> threadLocalStringBuilder = new ThreadLocal();

    private StructuredDataFilter(Map<String, List<String>> map, boolean bl, Filter.Result result, Filter.Result result2) {
        super(map, bl, result, result2);
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, Message message, Throwable throwable) {
        if (message instanceof StructuredDataMessage) {
            return this.filter((StructuredDataMessage)message);
        }
        return Filter.Result.NEUTRAL;
    }

    @Override
    public Filter.Result filter(LogEvent logEvent) {
        Message message = logEvent.getMessage();
        if (message instanceof StructuredDataMessage) {
            return this.filter((StructuredDataMessage)message);
        }
        return super.filter(logEvent);
    }

    protected Filter.Result filter(StructuredDataMessage structuredDataMessage) {
        boolean bl = false;
        IndexedReadOnlyStringMap indexedReadOnlyStringMap = this.getStringMap();
        for (int i = 0; i < indexedReadOnlyStringMap.size(); ++i) {
            StringBuilder stringBuilder = this.getValue(structuredDataMessage, indexedReadOnlyStringMap.getKeyAt(i));
            bl = stringBuilder != null ? this.listContainsValue((List)indexedReadOnlyStringMap.getValueAt(i), stringBuilder) : false;
            if (!this.isAnd() && bl || this.isAnd() && !bl) break;
        }
        return bl ? this.onMatch : this.onMismatch;
    }

    private StringBuilder getValue(StructuredDataMessage structuredDataMessage, String string) {
        StringBuilder stringBuilder = this.getStringBuilder();
        if (string.equalsIgnoreCase("id")) {
            structuredDataMessage.getId().formatTo(stringBuilder);
            return stringBuilder;
        }
        if (string.equalsIgnoreCase("id.name")) {
            return this.appendOrNull(structuredDataMessage.getId().getName(), stringBuilder);
        }
        if (string.equalsIgnoreCase("type")) {
            return this.appendOrNull(structuredDataMessage.getType(), stringBuilder);
        }
        if (string.equalsIgnoreCase("message")) {
            structuredDataMessage.formatTo(stringBuilder);
            return stringBuilder;
        }
        return this.appendOrNull(structuredDataMessage.get(string), stringBuilder);
    }

    private StringBuilder getStringBuilder() {
        StringBuilder stringBuilder = threadLocalStringBuilder.get();
        if (stringBuilder == null) {
            stringBuilder = new StringBuilder();
            threadLocalStringBuilder.set(stringBuilder);
        }
        if (stringBuilder.length() > 2048) {
            stringBuilder.setLength(2048);
            stringBuilder.trimToSize();
        }
        stringBuilder.setLength(0);
        return stringBuilder;
    }

    private StringBuilder appendOrNull(String string, StringBuilder stringBuilder) {
        if (string == null) {
            return null;
        }
        stringBuilder.append(string);
        return stringBuilder;
    }

    private boolean listContainsValue(List<String> list, StringBuilder stringBuilder) {
        if (stringBuilder == null) {
            for (int i = 0; i < list.size(); ++i) {
                String string = list.get(i);
                if (string != null) continue;
                return false;
            }
        } else {
            for (int i = 0; i < list.size(); ++i) {
                String string = list.get(i);
                if (string == null) {
                    return true;
                }
                if (!StringBuilders.equals(string, 0, string.length(), stringBuilder, 0, stringBuilder.length())) continue;
                return false;
            }
        }
        return true;
    }

    @PluginFactory
    public static StructuredDataFilter createFilter(@PluginElement(value="Pairs") KeyValuePair[] keyValuePairArray, @PluginAttribute(value="operator") String string, @PluginAttribute(value="onMatch") Filter.Result result, @PluginAttribute(value="onMismatch") Filter.Result result2) {
        if (keyValuePairArray == null || keyValuePairArray.length == 0) {
            LOGGER.error("keys and values must be specified for the StructuredDataFilter");
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
            LOGGER.error("StructuredDataFilter is not configured with any valid key value pairs");
            return null;
        }
        boolean bl = string == null || !string.equalsIgnoreCase("or");
        return new StructuredDataFilter(hashMap, bl, result, result2);
    }
}

