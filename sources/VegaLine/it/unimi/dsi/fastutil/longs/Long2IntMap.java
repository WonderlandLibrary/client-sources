/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.longs.Long2IntFunction;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Map;

public interface Long2IntMap
extends Long2IntFunction,
Map<Long, Integer> {
    @Override
    public ObjectSet<Map.Entry<Long, Integer>> entrySet();

    public ObjectSet<Entry> long2IntEntrySet();

    public LongSet keySet();

    public IntCollection values();

    public boolean containsValue(int var1);

    public static interface Entry
    extends Map.Entry<Long, Integer> {
        @Override
        @Deprecated
        public Long getKey();

        public long getLongKey();

        @Override
        @Deprecated
        public Integer getValue();

        @Override
        public int setValue(int var1);

        public int getIntValue();
    }

    public static interface FastEntrySet
    extends ObjectSet<Entry> {
        public ObjectIterator<Entry> fastIterator();
    }
}

