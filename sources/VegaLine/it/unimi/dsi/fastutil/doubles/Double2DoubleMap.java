/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.Double2DoubleFunction;
import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Map;

public interface Double2DoubleMap
extends Double2DoubleFunction,
Map<Double, Double> {
    @Override
    public ObjectSet<Map.Entry<Double, Double>> entrySet();

    public ObjectSet<Entry> double2DoubleEntrySet();

    public DoubleSet keySet();

    public DoubleCollection values();

    public boolean containsValue(double var1);

    public static interface Entry
    extends Map.Entry<Double, Double> {
        @Override
        @Deprecated
        public Double getKey();

        public double getDoubleKey();

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

