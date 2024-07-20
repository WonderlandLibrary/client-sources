/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.Int2ObjectFunction;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Map;

public interface Int2ObjectMap<V>
extends Int2ObjectFunction<V>,
Map<Integer, V> {
    @Override
    public ObjectSet<Map.Entry<Integer, V>> entrySet();

    public ObjectSet<Entry<V>> int2ObjectEntrySet();

    public IntSet keySet();

    @Override
    public ObjectCollection<V> values();

    public static interface Entry<V>
    extends Map.Entry<Integer, V> {
        @Override
        @Deprecated
        public Integer getKey();

        public int getIntKey();
    }

    public static interface FastEntrySet<V>
    extends ObjectSet<Entry<V>> {
        public ObjectIterator<Entry<V>> fastIterator();
    }
}

