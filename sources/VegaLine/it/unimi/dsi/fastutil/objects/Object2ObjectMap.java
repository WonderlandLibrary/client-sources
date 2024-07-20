/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.Object2ObjectFunction;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Map;

public interface Object2ObjectMap<K, V>
extends Object2ObjectFunction<K, V>,
Map<K, V> {
    @Override
    public ObjectSet<Map.Entry<K, V>> entrySet();

    public ObjectSet<Entry<K, V>> object2ObjectEntrySet();

    @Override
    public ObjectSet<K> keySet();

    @Override
    public ObjectCollection<V> values();

    public static interface Entry<K, V>
    extends Map.Entry<K, V> {
    }

    public static interface FastEntrySet<K, V>
    extends ObjectSet<Entry<K, V>> {
        public ObjectIterator<Entry<K, V>> fastIterator();
    }
}

