/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.ints.Int2ByteFunction;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Map;

public interface Int2ByteMap
extends Int2ByteFunction,
Map<Integer, Byte> {
    @Override
    public ObjectSet<Map.Entry<Integer, Byte>> entrySet();

    public ObjectSet<Entry> int2ByteEntrySet();

    public IntSet keySet();

    public ByteCollection values();

    public boolean containsValue(byte var1);

    public static interface Entry
    extends Map.Entry<Integer, Byte> {
        @Override
        @Deprecated
        public Integer getKey();

        public int getIntKey();

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

