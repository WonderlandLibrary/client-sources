/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.Float2ShortFunction;
import it.unimi.dsi.fastutil.floats.FloatSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
import java.util.Map;

public interface Float2ShortMap
extends Float2ShortFunction,
Map<Float, Short> {
    @Override
    public ObjectSet<Map.Entry<Float, Short>> entrySet();

    public ObjectSet<Entry> float2ShortEntrySet();

    public FloatSet keySet();

    public ShortCollection values();

    public boolean containsValue(short var1);

    public static interface Entry
    extends Map.Entry<Float, Short> {
        @Override
        @Deprecated
        public Float getKey();

        public float getFloatKey();

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

