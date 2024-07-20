/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.Float2ReferenceFunction;
import it.unimi.dsi.fastutil.floats.FloatSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ReferenceCollection;
import java.util.Map;

public interface Float2ReferenceMap<V>
extends Float2ReferenceFunction<V>,
Map<Float, V> {
    @Override
    public ObjectSet<Map.Entry<Float, V>> entrySet();

    public ObjectSet<Entry<V>> float2ReferenceEntrySet();

    public FloatSet keySet();

    @Override
    public ReferenceCollection<V> values();

    public static interface Entry<V>
    extends Map.Entry<Float, V> {
        @Override
        @Deprecated
        public Float getKey();

        public float getFloatKey();
    }

    public static interface FastEntrySet<V>
    extends ObjectSet<Entry<V>> {
        public ObjectIterator<Entry<V>> fastIterator();
    }
}

