/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.floats.Float2DoubleFunction;
import it.unimi.dsi.fastutil.floats.FloatSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Map;

public interface Float2DoubleMap
extends Float2DoubleFunction,
Map<Float, Double> {
    @Override
    public ObjectSet<Map.Entry<Float, Double>> entrySet();

    public ObjectSet<Entry> float2DoubleEntrySet();

    public FloatSet keySet();

    public DoubleCollection values();

    public boolean containsValue(double var1);

    public static interface Entry
    extends Map.Entry<Float, Double> {
        @Override
        @Deprecated
        public Float getKey();

        public float getFloatKey();

        @Override
        @Deprecated
        public Double getValue();

        @Override
        public double setValue(double var1);

        public double getDoubleValue();
    }

    public static interface FastEntrySet
    extends ObjectSet<Entry> {
        public ObjectIterator<Entry> fastIterator();
    }
}

