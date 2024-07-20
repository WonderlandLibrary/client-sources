/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.Float2FloatMap;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.floats.FloatComparator;
import it.unimi.dsi.fastutil.floats.FloatSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Map;
import java.util.SortedMap;

public interface Float2FloatSortedMap
extends Float2FloatMap,
SortedMap<Float, Float> {
    @Override
    public ObjectSortedSet<Map.Entry<Float, Float>> entrySet();

    public ObjectSortedSet<Float2FloatMap.Entry> float2FloatEntrySet();

    @Override
    public FloatSortedSet keySet();

    @Override
    public FloatCollection values();

    public FloatComparator comparator();

    public Float2FloatSortedMap subMap(Float var1, Float var2);

    public Float2FloatSortedMap headMap(Float var1);

    public Float2FloatSortedMap tailMap(Float var1);

    public Float2FloatSortedMap subMap(float var1, float var2);

    public Float2FloatSortedMap headMap(float var1);

    public Float2FloatSortedMap tailMap(float var1);

    public float firstFloatKey();

    public float lastFloatKey();

    public static interface FastSortedEntrySet
    extends ObjectSortedSet<Float2FloatMap.Entry>,
    Float2FloatMap.FastEntrySet {
        public ObjectBidirectionalIterator<Float2FloatMap.Entry> fastIterator(Float2FloatMap.Entry var1);
    }
}

