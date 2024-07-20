/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.Float2ShortMap;
import it.unimi.dsi.fastutil.floats.FloatComparator;
import it.unimi.dsi.fastutil.floats.FloatSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
import java.util.Map;
import java.util.SortedMap;

public interface Float2ShortSortedMap
extends Float2ShortMap,
SortedMap<Float, Short> {
    @Override
    public ObjectSortedSet<Map.Entry<Float, Short>> entrySet();

    public ObjectSortedSet<Float2ShortMap.Entry> float2ShortEntrySet();

    @Override
    public FloatSortedSet keySet();

    @Override
    public ShortCollection values();

    public FloatComparator comparator();

    public Float2ShortSortedMap subMap(Float var1, Float var2);

    public Float2ShortSortedMap headMap(Float var1);

    public Float2ShortSortedMap tailMap(Float var1);

    public Float2ShortSortedMap subMap(float var1, float var2);

    public Float2ShortSortedMap headMap(float var1);

    public Float2ShortSortedMap tailMap(float var1);

    public float firstFloatKey();

    public float lastFloatKey();

    public static interface FastSortedEntrySet
    extends ObjectSortedSet<Float2ShortMap.Entry>,
    Float2ShortMap.FastEntrySet {
        public ObjectBidirectionalIterator<Float2ShortMap.Entry> fastIterator(Float2ShortMap.Entry var1);
    }
}

