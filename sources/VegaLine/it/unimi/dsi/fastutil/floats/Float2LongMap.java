/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.Float2LongFunction;
import it.unimi.dsi.fastutil.floats.FloatSet;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Map;

public interface Float2LongMap
extends Float2LongFunction,
Map<Float, Long> {
    @Override
    public ObjectSet<Map.Entry<Float, Long>> entrySet();

    public ObjectSet<Entry> float2LongEntrySet();

    public FloatSet keySet();

    public LongCollection values();

    public boolean containsValue(long var1);

    public static interface Entry
    extends Map.Entry<Float, Long> {
        @Override
        @Deprecated
        public Float getKey();

        public float getFloatKey();

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

