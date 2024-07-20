/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.objects.Object2LongFunction;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Map;

public interface Object2LongMap<K>
extends Object2LongFunction<K>,
Map<K, Long> {
    @Override
    public ObjectSet<Map.Entry<K, Long>> entrySet();

    public ObjectSet<Entry<K>> object2LongEntrySet();

    @Override
    public ObjectSet<K> keySet();

    public LongCollection values();

    public boolean containsValue(long var1);

    public static interface Entry<K>
    extends Map.Entry<K, Long> {
        @Override
        @Deprecated
        public Long getValue();

        @Override
        public long setValue(long var1);

        public long getLongValue();
    }

    public static interface FastEntrySet<K>
    extends ObjectSet<Entry<K>> {
        public ObjectIterator<Entry<K>> fastIterator();
    }
}

