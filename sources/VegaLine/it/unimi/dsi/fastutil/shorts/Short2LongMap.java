/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.shorts.Short2LongFunction;
import it.unimi.dsi.fastutil.shorts.ShortSet;
import java.util.Map;

public interface Short2LongMap
extends Short2LongFunction,
Map<Short, Long> {
    @Override
    public ObjectSet<Map.Entry<Short, Long>> entrySet();

    public ObjectSet<Entry> short2LongEntrySet();

    public ShortSet keySet();

    public LongCollection values();

    public boolean containsValue(long var1);

    public static interface Entry
    extends Map.Entry<Short, Long> {
        @Override
        @Deprecated
        public Short getKey();

        public short getShortKey();

        @Override
        @Deprecated
        public Long getValue();

        @Override
        public long setValue(long var1);

        public long getLongValue();
    }

    public static interface FastEntrySet
    extends ObjectSet<Entry> {
        public ObjectIterator<Entry> fastIterator();
    }
}

