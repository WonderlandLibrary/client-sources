/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.floats.Float2BooleanMap;
import it.unimi.dsi.fastutil.floats.FloatComparator;
import it.unimi.dsi.fastutil.floats.FloatSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Map;
import java.util.SortedMap;

public interface Float2BooleanSortedMap
extends Float2BooleanMap,
SortedMap<Float, Boolean> {
    @Override
    public ObjectSortedSet<Map.Entry<Float, Boolean>> entrySet();

    public ObjectSortedSet<Float2BooleanMap.Entry> float2BooleanEntrySet();

    @Override
    public FloatSortedSet keySet();

    @Override
    public BooleanCollection values();

    public FloatComparator comparator();

    public Float2BooleanSortedMap subMap(Float var1, Float var2);

    public Float2BooleanSortedMap headMap(Float var1);

    public Float2BooleanSortedMap tailMap(Float var1);

    public Float2BooleanSortedMap subMap(float var1, float var2);

    public Float2BooleanSortedMap headMap(float var1);

    public Float2BooleanSortedMap tailMap(float var1);

    public float firstFloatKey();

    public float lastFloatKey();

    public static interface FastSortedEntrySet
    extends ObjectSortedSet<Float2BooleanMap.Entry>,
    Float2BooleanMap.FastEntrySet {
        public ObjectBidirectionalIterator<Float2BooleanMap.Entry> fastIterator(Float2BooleanMap.Entry var1);
    }
}

