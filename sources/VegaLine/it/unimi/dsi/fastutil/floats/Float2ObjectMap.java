/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.Float2ObjectFunction;
import it.unimi.dsi.fastutil.floats.FloatSet;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Map;

public interface Float2ObjectMap<V>
extends Float2ObjectFunction<V>,
Map<Float, V> {
    @Override
    public ObjectSet<Map.Entry<Float, V>> entrySet();

    public ObjectSet<Entry<V>> float2ObjectEntrySet();

    public FloatSet keySet();

    @Override
    public ObjectCollection<V> values();

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

