/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.Double2ReferenceFunction;
import it.unimi.dsi.fastutil.doubles.DoubleSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ReferenceCollection;
import java.util.Map;

public interface Double2ReferenceMap<V>
extends Double2ReferenceFunction<V>,
Map<Double, V> {
    @Override
    public ObjectSet<Map.Entry<Double, V>> entrySet();

    public ObjectSet<Entry<V>> double2ReferenceEntrySet();

    public DoubleSet keySet();

    @Override
    public ReferenceCollection<V> values();

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

