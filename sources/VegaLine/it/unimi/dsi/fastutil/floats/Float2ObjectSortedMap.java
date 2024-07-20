/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.Float2ObjectMap;
import it.unimi.dsi.fastutil.floats.FloatComparator;
import it.unimi.dsi.fastutil.floats.FloatSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Map;
import java.util.SortedMap;

public interface Float2ObjectSortedMap<V>
extends Float2ObjectMap<V>,
SortedMap<Float, V> {
    @Override
    public ObjectSortedSet<Map.Entry<Float, V>> entrySet();

    @Override
    public ObjectSortedSet<Float2ObjectMap.Entry<V>> float2ObjectEntrySet();

    @Override
    public FloatSortedSet keySet();

    @Override
    public ObjectCollection<V> values();

    public FloatComparator comparator();

    public Float2ObjectSortedMap<V> subMap(Float var1, Float var2);

    public Float2ObjectSortedMap<V> headMap(Float var1);

    public Float2ObjectSortedMap<V> tailMap(Float var1);

    public Float2ObjectSortedMap<V> subMap(float var1, float var2);

    public Float2ObjectSortedMap<V> headMap(float var1);

    public Float2ObjectSortedMap<V> tailMap(float var1);

    public float firstFloatKey();

    public float lastFloatKey();

    public static interface FastSortedEntrySet<V>
    extends ObjectSortedSet<Float2ObjectMap.Entry<V>>,
    Float2ObjectMap.FastEntrySet<V> {
        public ObjectBidirectionalIterator<Float2ObjectMap.Entry<V>> fastIterator(Float2ObjectMap.Entry<V> var1);
    }
}

