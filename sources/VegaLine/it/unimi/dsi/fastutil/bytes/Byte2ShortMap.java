/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.Byte2ShortFunction;
import it.unimi.dsi.fastutil.bytes.ByteSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
import java.util.Map;

public interface Byte2ShortMap
extends Byte2ShortFunction,
Map<Byte, Short> {
    @Override
    public ObjectSet<Map.Entry<Byte, Short>> entrySet();

    public ObjectSet<Entry> byte2ShortEntrySet();

    public ByteSet keySet();

    public ShortCollection values();

    public boolean containsValue(short var1);

    public static interface Entry
    extends Map.Entry<Byte, Short> {
        @Override
        @Deprecated
        public Byte getKey();

        public byte getByteKey();

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

