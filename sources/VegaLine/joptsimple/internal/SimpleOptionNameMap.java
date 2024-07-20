/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package joptsimple.internal;

import java.util.HashMap;
import java.util.Map;
import joptsimple.internal.OptionNameMap;

public class SimpleOptionNameMap<V>
implements OptionNameMap<V> {
    private final Map<String, V> map = new HashMap<String, V>();

    @Override
    public boolean contains(String key) {
        return this.map.containsKey(key);
    }

    @Override
    public V get(String key) {
        return this.map.get(key);
    }

    @Override
    public void put(String key, V newValue) {
        this.map.put(key, newValue);
    }

    @Override
    public void putAll(Iterable<String> keys, V newValue) {
        for (String each : keys) {
            this.map.put(each, newValue);
        }
    }

    @Override
    public void remove(String key) {
        this.map.remove(key);
    }

    @Override
    public Map<String, V> toJavaUtilMap() {
        return new HashMap<String, V>(this.map);
    }
}

