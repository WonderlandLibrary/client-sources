/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.shorts.Short2DoubleFunction;
import it.unimi.dsi.fastutil.shorts.ShortSet;
import java.util.Map;

public interface Short2DoubleMap
extends Short2DoubleFunction,
Map<Short, Double> {
    @Override
    public ObjectSet<Map.Entry<Short, Double>> entrySet();

    public ObjectSet<Entry> short2DoubleEntrySet();

    public ShortSet keySet();

    public DoubleCollection values();

    public boolean containsValue(double var1);

    public static interface Entry
    extends Map.Entry<Short, Double> {
        @Override
        @Deprecated
        public Short getKey();

        public short getShortKey();

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

