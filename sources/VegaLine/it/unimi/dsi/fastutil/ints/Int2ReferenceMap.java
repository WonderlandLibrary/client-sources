/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.Int2ReferenceFunction;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ReferenceCollection;
import java.util.Map;

public interface Int2ReferenceMap<V>
extends Int2ReferenceFunction<V>,
Map<Integer, V> {
    @Override
    public ObjectSet<Map.Entry<Integer, V>> entrySet();

    public ObjectSet<Entry<V>> int2ReferenceEntrySet();

    public IntSet keySet();

    @Override
    public ReferenceCollection<V> values();

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

