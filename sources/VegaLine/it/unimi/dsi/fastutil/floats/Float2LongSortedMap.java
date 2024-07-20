/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.Float2LongMap;
import it.unimi.dsi.fastutil.floats.FloatComparator;
import it.unimi.dsi.fastutil.floats.FloatSortedSet;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Map;
import java.util.SortedMap;

public interface Float2LongSortedMap
extends Float2LongMap,
SortedMap<Float, Long> {
    @Override
    public ObjectSortedSet<Map.Entry<Float, Long>> entrySet();

    public ObjectSortedSet<Float2LongMap.Entry> float2LongEntrySet();

    @Override
    public FloatSortedSet keySet();

    @Override
    public LongCollection values();

    public FloatComparator comparator();

    public Float2LongSortedMap subMap(Float var1, Float var2);

    public Float2LongSortedMap headMap(Float var1);

    public Float2LongSortedMap tailMap(Float var1);

    public Float2LongSortedMap subMap(float var1, float var2);

    public Float2LongSortedMap headMap(float var1);

    public Float2LongSortedMap tailMap(float var1);

    public float firstFloatKey();

    public float lastFloatKey();

    public static interface FastSortedEntrySet
    extends ObjectSortedSet<Float2LongMap.Entry>,
    Float2LongMap.FastEntrySet {
        public ObjectBidirectionalIterator<Float2LongMap.Entry> fastIterator(Float2LongMap.Entry var1);
    }
}

