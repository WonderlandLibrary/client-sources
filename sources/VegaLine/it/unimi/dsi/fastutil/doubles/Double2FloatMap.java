/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.Double2FloatFunction;
import it.unimi.dsi.fastutil.doubles.DoubleSet;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Map;

public interface Double2FloatMap
extends Double2FloatFunction,
Map<Double, Float> {
    @Override
    public ObjectSet<Map.Entry<Double, Float>> entrySet();

    public ObjectSet<Entry> double2FloatEntrySet();

    public DoubleSet keySet();

    public FloatCollection values();

    public boolean containsValue(float var1);

    public static interface Entry
    extends Map.Entry<Double, Float> {
        @Override
        @Deprecated
        public Double getKey();

        public double getDoubleKey();

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

