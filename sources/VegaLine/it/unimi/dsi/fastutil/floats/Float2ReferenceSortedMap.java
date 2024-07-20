/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.Float2ReferenceMap;
import it.unimi.dsi.fastutil.floats.FloatComparator;
import it.unimi.dsi.fastutil.floats.FloatSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.objects.ReferenceCollection;
import java.util.Map;
import java.util.SortedMap;

public interface Float2ReferenceSortedMap<V>
extends Float2ReferenceMap<V>,
SortedMap<Float, V> {
    @Override
    public ObjectSortedSet<Map.Entry<Float, V>> entrySet();

    @Override
    public ObjectSortedSet<Float2ReferenceMap.Entry<V>> float2ReferenceEntrySet();

    @Override
    public FloatSortedSet keySet();

    @Override
    public ReferenceCollection<V> values();

    public FloatComparator comparator();

    public Float2ReferenceSortedMap<V> subMap(Float var1, Float var2);

    public Float2ReferenceSortedMap<V> headMap(Float var1);

    public Float2ReferenceSortedMap<V> tailMap(Float var1);

    public Float2ReferenceSortedMap<V> subMap(float var1, float var2);

    public Float2ReferenceSortedMap<V> headMap(float var1);

    public Float2ReferenceSortedMap<V> tailMap(float var1);

    public float firstFloatKey();

    public float lastFloatKey();

    public static interface FastSortedEntrySet<V>
    extends ObjectSortedSet<Float2ReferenceMap.Entry<V>>,
    Float2ReferenceMap.FastEntrySet<V> {
        public ObjectBidirectionalIterator<Float2ReferenceMap.Entry<V>> fastIterator(Float2ReferenceMap.Entry<V> var1);
    }
}

