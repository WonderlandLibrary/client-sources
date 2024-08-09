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
    public boolean contains(String string) {
        return this.map.containsKey(string);
    }

    @Override
    public V get(String string) {
        return this.map.get(string);
    }

    @Override
    public void put(String string, V v) {
        this.map.put(string, v);
    }

    @Override
    public void putAll(Iterable<String> iterable, V v) {
        for (String string : iterable) {
            this.map.put(string, v);
        }
    }

    @Override
    public void remove(String string) {
        this.map.remove(string);
    }

    @Override
    public Map<String, V> toJavaUtilMap() {
        return new HashMap<String, V>(this.map);
    }
}

