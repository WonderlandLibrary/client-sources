/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.longs.Long2ByteFunction;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Map;

public interface Long2ByteMap
extends Long2ByteFunction,
Map<Long, Byte> {
    @Override
    public ObjectSet<Map.Entry<Long, Byte>> entrySet();

    public ObjectSet<Entry> long2ByteEntrySet();

    public LongSet keySet();

    public ByteCollection values();

    public boolean containsValue(byte var1);

    public static interface Entry
    extends Map.Entry<Long, Byte> {
        @Override
        @Deprecated
        public Long getKey();

        public long getLongKey();

        @Override
        @Deprecated
        public Byte getValue();

        @Override
        public byte setValue(byte var1);

        public byte getByteValue();
    }

    public static interface FastEntrySet
    extends ObjectSet<Entry> {
        public ObjectIterator<Entry> fastIterator();
    }
}

