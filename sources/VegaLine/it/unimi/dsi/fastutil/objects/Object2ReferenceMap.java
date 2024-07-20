/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.Object2ReferenceFunction;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ReferenceCollection;
import java.util.Map;

public interface Object2ReferenceMap<K, V>
extends Object2ReferenceFunction<K, V>,
Map<K, V> {
    @Override
    public ObjectSet<Map.Entry<K, V>> entrySet();

    public ObjectSet<Entry<K, V>> object2ReferenceEntrySet();

    @Override
    public ObjectSet<K> keySet();

    @Override
    public ReferenceCollection<V> values();

    public static interface Entry<K, V>
    extends Map.Entry<K, V> {
    }

    public static interface FastEntrySet<K, V>
    extends ObjectSet<Entry<K, V>> {
        public ObjectIterator<Entry<K, V>> fastIterator();
    }
}

