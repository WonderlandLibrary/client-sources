/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.ints.Int2DoubleFunction;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Map;

public interface Int2DoubleMap
extends Int2DoubleFunction,
Map<Integer, Double> {
    @Override
    public ObjectSet<Map.Entry<Integer, Double>> entrySet();

    public ObjectSet<Entry> int2DoubleEntrySet();

    public IntSet keySet();

    public DoubleCollection values();

    public boolean containsValue(double var1);

    public static interface Entry
    extends Map.Entry<Integer, Double> {
        @Override
        @Deprecated
        public Integer getKey();

        public int getIntKey();

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

