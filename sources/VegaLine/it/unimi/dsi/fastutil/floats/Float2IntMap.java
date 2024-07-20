/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.Float2IntFunction;
import it.unimi.dsi.fastutil.floats.FloatSet;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Map;

public interface Float2IntMap
extends Float2IntFunction,
Map<Float, Integer> {
    @Override
    public ObjectSet<Map.Entry<Float, Integer>> entrySet();

    public ObjectSet<Entry> float2IntEntrySet();

    public FloatSet keySet();

    public IntCollection values();

    public boolean containsValue(int var1);

    public static interface Entry
    extends Map.Entry<Float, Integer> {
        @Override
        @Deprecated
        public Float getKey();

        public float getFloatKey();

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

