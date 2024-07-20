/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.longs.Long2ShortFunction;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
import java.util.Map;

public interface Long2ShortMap
extends Long2ShortFunction,
Map<Long, Short> {
    @Override
    public ObjectSet<Map.Entry<Long, Short>> entrySet();

    public ObjectSet<Entry> long2ShortEntrySet();

    public LongSet keySet();

    public ShortCollection values();

    public boolean containsValue(short var1);

    public static interface Entry
    extends Map.Entry<Long, Short> {
        @Override
        @Deprecated
        public Long getKey();

        public long getLongKey();

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

