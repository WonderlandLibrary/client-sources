/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Map;

public interface Int2IntMap
extends Int2IntFunction,
Map<Integer, Integer> {
    @Override
    public ObjectSet<Map.Entry<Integer, Integer>> entrySet();

    public ObjectSet<Entry> int2IntEntrySet();

    public IntSet keySet();

    public IntCollection values();

    public boolean containsValue(int var1);

    public static interface Entry
    extends Map.Entry<Integer, Integer> {
        @Override
        @Deprecated
        public Integer getKey();

        public int getIntKey();

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

