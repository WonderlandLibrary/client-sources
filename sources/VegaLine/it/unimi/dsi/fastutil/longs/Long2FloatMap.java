/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.longs.Long2FloatFunction;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Map;

public interface Long2FloatMap
extends Long2FloatFunction,
Map<Long, Float> {
    @Override
    public ObjectSet<Map.Entry<Long, Float>> entrySet();

    public ObjectSet<Entry> long2FloatEntrySet();

    public LongSet keySet();

    public FloatCollection values();

    public boolean containsValue(float var1);

    public static interface Entry
    extends Map.Entry<Long, Float> {
        @Override
        @Deprecated
        public Long getKey();

        public long getLongKey();

        @Override
        @Deprecated
        public Float getValue();

        @Override
        public float setValue(float var1);

        public float getFloatValue();
    }

    public static interface FastEntrySet
    extends ObjectSet<Entry> {
        public ObjectIterator<Entry> fastIterator();
    }
}

