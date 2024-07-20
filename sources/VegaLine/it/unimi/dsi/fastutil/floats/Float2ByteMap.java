/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.floats.Float2ByteFunction;
import it.unimi.dsi.fastutil.floats.FloatSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Map;

public interface Float2ByteMap
extends Float2ByteFunction,
Map<Float, Byte> {
    @Override
    public ObjectSet<Map.Entry<Float, Byte>> entrySet();

    public ObjectSet<Entry> float2ByteEntrySet();

    public FloatSet keySet();

    public ByteCollection values();

    public boolean containsValue(byte var1);

    public static interface Entry
    extends Map.Entry<Float, Byte> {
        @Override
        @Deprecated
        public Float getKey();

        public float getFloatKey();

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

