/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.Byte2IntFunction;
import it.unimi.dsi.fastutil.bytes.ByteSet;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Map;

public interface Byte2IntMap
extends Byte2IntFunction,
Map<Byte, Integer> {
    @Override
    public ObjectSet<Map.Entry<Byte, Integer>> entrySet();

    public ObjectSet<Entry> byte2IntEntrySet();

    public ByteSet keySet();

    public IntCollection values();

    public boolean containsValue(int var1);

    public static interface Entry
    extends Map.Entry<Byte, Integer> {
        @Override
        @Deprecated
        public Byte getKey();

        public byte getByteKey();

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

