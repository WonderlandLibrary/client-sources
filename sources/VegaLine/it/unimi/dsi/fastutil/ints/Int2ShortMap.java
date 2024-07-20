/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.Int2ShortFunction;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
import java.util.Map;

public interface Int2ShortMap
extends Int2ShortFunction,
Map<Integer, Short> {
    @Override
    public ObjectSet<Map.Entry<Integer, Short>> entrySet();

    public ObjectSet<Entry> int2ShortEntrySet();

    public IntSet keySet();

    public ShortCollection values();

    public boolean containsValue(short var1);

    public static interface Entry
    extends Map.Entry<Integer, Short> {
        @Override
        @Deprecated
        public Integer getKey();

        public int getIntKey();

        @Override
        @Deprecated
        public Short getValue();

        @Override
        public short setValue(short var1);

        public short getShortValue();
    }

    public static interface FastEntrySet
    extends ObjectSet<Entry> {
        public ObjectIterator<Entry> fastIterator();
    }
}

