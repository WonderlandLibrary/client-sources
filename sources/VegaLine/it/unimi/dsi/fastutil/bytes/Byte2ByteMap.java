/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.Byte2ByteFunction;
import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Map;

public interface Byte2ByteMap
extends Byte2ByteFunction,
Map<Byte, Byte> {
    @Override
    public ObjectSet<Map.Entry<Byte, Byte>> entrySet();

    public ObjectSet<Entry> byte2ByteEntrySet();

    public ByteSet keySet();

    public ByteCollection values();

    public boolean containsValue(byte var1);

    public static interface Entry
    extends Map.Entry<Byte, Byte> {
        @Override
        @Deprecated
        public Byte getKey();

        public byte getByteKey();

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

