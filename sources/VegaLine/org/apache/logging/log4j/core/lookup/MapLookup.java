/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.lookup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.lookup.MainMapLookup;
import org.apache.logging.log4j.core.lookup.StrLookup;
import org.apache.logging.log4j.message.MapMessage;

@Plugin(name="map", category="Lookup")
public class MapLookup
implements StrLookup {
    private final Map<String, String> map;

    public MapLookup() {
        this.map = null;
    }

    public MapLookup(Map<String, String> map) {
        this.map = map;
    }

    static Map<String, String> initMap(String[] srcArgs, Map<String, String> destMap) {
        for (int i = 0; i < srcArgs.length; ++i) {
            int next = i + 1;
            String value = srcArgs[i];
            destMap.put(Integer.toString(i), value);
            destMap.put(value, next < srcArgs.length ? srcArgs[next] : null);
        }
        return destMap;
    }

    static HashMap<String, String> newMap(int initialCapacity) {
        return new HashMap<String, String>(initialCapacity);
    }

    @Deprecated
    public static void setMainArguments(String ... args) {
        MainMapLookup.setMainArguments(args);
    }

    static Map<String, String> toMap(List<String> args) {
        if (args == null) {
            return null;
        }
        int size = args.size();
        return MapLookup.initMap(args.toArray(new String[size]), MapLookup.newMap(size));
    }

    static Map<String, String> toMap(String[] args) {
        if (args == null) {
            return null;
        }
        return MapLookup.initMap(args, MapLookup.newMap(args.length));
    }

    protected Map<String, String> getMap() {
        return this.map;
    }

    @Override
    public String lookup(LogEvent event, String key) {
        String obj;
        boolean isMapMessage;
        boolean bl = isMapMessage = event != null && event.getMessage() instanceof MapMessage;
        if (this.map == null && !isMapMessage) {
            return null;
        }
        if (this.map != null && this.map.containsKey(key) && (obj = this.map.get(key)) != null) {
            return obj;
        }
        if (isMapMessage) {
            return ((MapMessage)event.getMessage()).get(key);
        }
        return null;
    }

    @Override
    public String lookup(String key) {
        if (this.map == null) {
            return null;
        }
        return this.map.get(key);
    }
}

