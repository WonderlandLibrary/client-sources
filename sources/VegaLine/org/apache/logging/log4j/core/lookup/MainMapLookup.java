/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.lookup;

import java.util.Map;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.lookup.MapLookup;

@Plugin(name="main", category="Lookup")
public class MainMapLookup
extends MapLookup {
    static final MapLookup MAIN_SINGLETON = new MapLookup(MapLookup.newMap(0));

    public MainMapLookup() {
    }

    public MainMapLookup(Map<String, String> map) {
        super(map);
    }

    public static void setMainArguments(String ... args) {
        if (args == null) {
            return;
        }
        MainMapLookup.initMap(args, MAIN_SINGLETON.getMap());
    }

    @Override
    public String lookup(LogEvent event, String key) {
        return MAIN_SINGLETON.getMap().get(key);
    }

    @Override
    public String lookup(String key) {
        return MAIN_SINGLETON.getMap().get(key);
    }
}

