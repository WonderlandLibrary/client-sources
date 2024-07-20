/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.longs.Long2ObjectFunction;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Map;

public interface Long2ObjectMap<V>
extends Long2ObjectFunction<V>,
Map<Long, V> {
    @Override
    public ObjectSet<Map.Entry<Long, V>> entrySet();

    public ObjectSet<Entry<V>> long2ObjectEntrySet();

    public LongSet keySet();

    @Override
    public ObjectCollection<V> values();

    public static interface Entry<V>
    extends Map.Entry<Long, V> {
        @Override
        @Deprecated
        public Long getKey();

        public long getLongKey();
    }

    public static interface FastEntrySet<V>
    extends ObjectSet<Entry<V>> {
        public ObjectIterator<Entry<V>> fastIterator();
    }
}

