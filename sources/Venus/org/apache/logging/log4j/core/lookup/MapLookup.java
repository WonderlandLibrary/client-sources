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

    static Map<String, String> initMap(String[] stringArray, Map<String, String> map) {
        for (int i = 0; i < stringArray.length; ++i) {
            int n = i + 1;
            String string = stringArray[i];
            map.put(Integer.toString(i), string);
            map.put(string, n < stringArray.length ? stringArray[n] : null);
        }
        return map;
    }

    static HashMap<String, String> newMap(int n) {
        return new HashMap<String, String>(n);
    }

    @Deprecated
    public static void setMainArguments(String ... stringArray) {
        MainMapLookup.setMainArguments(stringArray);
    }

    static Map<String, String> toMap(List<String> list) {
        if (list == null) {
            return null;
        }
        int n = list.size();
        return MapLookup.initMap(list.toArray(new String[n]), MapLookup.newMap(n));
    }

    static Map<String, String> toMap(String[] stringArray) {
        if (stringArray == null) {
            return null;
        }
        return MapLookup.initMap(stringArray, MapLookup.newMap(stringArray.length));
    }

    protected Map<String, String> getMap() {
        return this.map;
    }

    @Override
    public String lookup(LogEvent logEvent, String string) {
        String string2;
        boolean bl;
        boolean bl2 = bl = logEvent != null && logEvent.getMessage() instanceof MapMessage;
        if (this.map == null && !bl) {
            return null;
        }
        if (this.map != null && this.map.containsKey(string) && (string2 = this.map.get(string)) != null) {
            return string2;
        }
        if (bl) {
            return ((MapMessage)logEvent.getMessage()).get(string);
        }
        return null;
    }

    @Override
    public String lookup(String string) {
        if (this.map == null) {
            return null;
        }
        return this.map.get(string);
    }
}

