/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.Float2IntMap;
import it.unimi.dsi.fastutil.floats.FloatComparator;
import it.unimi.dsi.fastutil.floats.FloatSortedSet;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Map;
import java.util.SortedMap;

public interface Float2IntSortedMap
extends Float2IntMap,
SortedMap<Float, Integer> {
    @Override
    public ObjectSortedSet<Map.Entry<Float, Integer>> entrySet();

    public ObjectSortedSet<Float2IntMap.Entry> float2IntEntrySet();

    @Override
    public FloatSortedSet keySet();

    @Override
    public IntCollection values();

    public FloatComparator comparator();

    public Float2IntSortedMap subMap(Float var1, Float var2);

    public Float2IntSortedMap headMap(Float var1);

    public Float2IntSortedMap tailMap(Float var1);

    public Float2IntSortedMap subMap(float var1, float var2);

    public Float2IntSortedMap headMap(float var1);

    public Float2IntSortedMap tailMap(float var1);

    public float firstFloatKey();

    public float lastFloatKey();

    public static interface FastSortedEntrySet
    extends ObjectSortedSet<Float2IntMap.Entry>,
    Float2IntMap.FastEntrySet {
        public ObjectBidirectionalIterator<Float2IntMap.Entry> fastIterator(Float2IntMap.Entry var1);
    }
}

