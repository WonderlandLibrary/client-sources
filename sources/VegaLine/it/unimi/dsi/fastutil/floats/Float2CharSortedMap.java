/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.floats.Float2CharMap;
import it.unimi.dsi.fastutil.floats.FloatComparator;
import it.unimi.dsi.fastutil.floats.FloatSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Map;
import java.util.SortedMap;

public interface Float2CharSortedMap
extends Float2CharMap,
SortedMap<Float, Character> {
    @Override
    public ObjectSortedSet<Map.Entry<Float, Character>> entrySet();

    public ObjectSortedSet<Float2CharMap.Entry> float2CharEntrySet();

    @Override
    public FloatSortedSet keySet();

    @Override
    public CharCollection values();

    public FloatComparator comparator();

    public Float2CharSortedMap subMap(Float var1, Float var2);

    public Float2CharSortedMap headMap(Float var1);

    public Float2CharSortedMap tailMap(Float var1);

    public Float2CharSortedMap subMap(float var1, float var2);

    public Float2CharSortedMap headMap(float var1);

    public Float2CharSortedMap tailMap(float var1);

    public float firstFloatKey();

    public float lastFloatKey();

    public static interface FastSortedEntrySet
    extends ObjectSortedSet<Float2CharMap.Entry>,
    Float2CharMap.FastEntrySet {
        public ObjectBidirectionalIterator<Float2CharMap.Entry> fastIterator(Float2CharMap.Entry var1);
    }
}

