/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.Reference2ReferenceFunction;
import it.unimi.dsi.fastutil.objects.ReferenceCollection;
import it.unimi.dsi.fastutil.objects.ReferenceSet;
import java.util.Map;

public interface Reference2ReferenceMap<K, V>
extends Reference2ReferenceFunction<K, V>,
Map<K, V> {
    @Override
    public ObjectSet<Map.Entry<K, V>> entrySet();

    public ObjectSet<Entry<K, V>> reference2ReferenceEntrySet();

    @Override
    public ReferenceSet<K> keySet();

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

