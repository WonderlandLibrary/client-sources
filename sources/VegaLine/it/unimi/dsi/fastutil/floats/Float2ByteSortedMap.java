/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.floats.Float2ByteMap;
import it.unimi.dsi.fastutil.floats.FloatComparator;
import it.unimi.dsi.fastutil.floats.FloatSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Map;
import java.util.SortedMap;

public interface Float2ByteSortedMap
extends Float2ByteMap,
SortedMap<Float, Byte> {
    @Override
    public ObjectSortedSet<Map.Entry<Float, Byte>> entrySet();

    public ObjectSortedSet<Float2ByteMap.Entry> float2ByteEntrySet();

    @Override
    public FloatSortedSet keySet();

    @Override
    public ByteCollection values();

    public FloatComparator comparator();

    public Float2ByteSortedMap subMap(Float var1, Float var2);

    public Float2ByteSortedMap headMap(Float var1);

    public Float2ByteSortedMap tailMap(Float var1);

    public Float2ByteSortedMap subMap(float var1, float var2);

    public Float2ByteSortedMap headMap(float var1);

    public Float2ByteSortedMap tailMap(float var1);

    public float firstFloatKey();

    public float lastFloatKey();

    public static interface FastSortedEntrySet
    extends ObjectSortedSet<Float2ByteMap.Entry>,
    Float2ByteMap.FastEntrySet {
        public ObjectBidirectionalIterator<Float2ByteMap.Entry> fastIterator(Float2ByteMap.Entry var1);
    }
}

