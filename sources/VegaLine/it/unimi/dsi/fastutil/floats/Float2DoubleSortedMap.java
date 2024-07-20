/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.floats.Float2DoubleMap;
import it.unimi.dsi.fastutil.floats.FloatComparator;
import it.unimi.dsi.fastutil.floats.FloatSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Map;
import java.util.SortedMap;

public interface Float2DoubleSortedMap
extends Float2DoubleMap,
SortedMap<Float, Double> {
    @Override
    public ObjectSortedSet<Map.Entry<Float, Double>> entrySet();

    public ObjectSortedSet<Float2DoubleMap.Entry> float2DoubleEntrySet();

    @Override
    public FloatSortedSet keySet();

    @Override
    public DoubleCollection values();

    public FloatComparator comparator();

    public Float2DoubleSortedMap subMap(Float var1, Float var2);

    public Float2DoubleSortedMap headMap(Float var1);

    public Float2DoubleSortedMap tailMap(Float var1);

    public Float2DoubleSortedMap subMap(float var1, float var2);

    public Float2DoubleSortedMap headMap(float var1);

    public Float2DoubleSortedMap tailMap(float var1);

    public float firstFloatKey();

    public float lastFloatKey();

    public static interface FastSortedEntrySet
    extends ObjectSortedSet<Float2DoubleMap.Entry>,
    Float2DoubleMap.FastEntrySet {
        public ObjectBidirectionalIterator<Float2DoubleMap.Entry> fastIterator(Float2DoubleMap.Entry var1);
    }
}

