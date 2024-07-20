/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.Double2ObjectFunction;
import it.unimi.dsi.fastutil.doubles.DoubleSet;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Map;

public interface Double2ObjectMap<V>
extends Double2ObjectFunction<V>,
Map<Double, V> {
    @Override
    public ObjectSet<Map.Entry<Double, V>> entrySet();

    public ObjectSet<Entry<V>> double2ObjectEntrySet();

    public DoubleSet keySet();

    @Override
    public ObjectCollection<V> values();

    public static interface Entry<V>
    extends Map.Entry<Double, V> {
        @Override
        @Deprecated
        public Double getKey();

        public double getDoubleKey();
    }

    public static interface FastEntrySet<V>
    extends ObjectSet<Entry<V>> {
        public ObjectIterator<Entry<V>> fastIterator();
    }
}

