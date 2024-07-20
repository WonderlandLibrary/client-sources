/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.shorts.Short2ByteFunction;
import it.unimi.dsi.fastutil.shorts.ShortSet;
import java.util.Map;

public interface Short2ByteMap
extends Short2ByteFunction,
Map<Short, Byte> {
    @Override
    public ObjectSet<Map.Entry<Short, Byte>> entrySet();

    public ObjectSet<Entry> short2ByteEntrySet();

    public ShortSet keySet();

    public ByteCollection values();

    public boolean containsValue(byte var1);

    public static interface Entry
    extends Map.Entry<Short, Byte> {
        @Override
        @Deprecated
        public Short getKey();

        public short getShortKey();

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

