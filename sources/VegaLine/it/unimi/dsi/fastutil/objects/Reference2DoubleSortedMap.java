/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.objects.Reference2DoubleMap;
import it.unimi.dsi.fastutil.objects.ReferenceSortedSet;
import java.util.Comparator;
import java.util.Map;
import java.util.SortedMap;

public interface Reference2DoubleSortedMap<K>
extends Reference2DoubleMap<K>,
SortedMap<K, Double> {
    @Override
    public ObjectSortedSet<Map.Entry<K, Double>> entrySet();

    @Override
    public ObjectSortedSet<Reference2DoubleMap.Entry<K>> reference2DoubleEntrySet();

    @Override
    public ReferenceSortedSet<K> keySet();

    @Override
    public DoubleCollection values();

    @Override
    public Comparator<? super K> comparator();

    public Reference2DoubleSortedMap<K> subMap(K var1, K var2);

    public Reference2DoubleSortedMap<K> headMap(K var1);

    public Reference2DoubleSortedMap<K> tailMap(K var1);

    public static interface FastSortedEntrySet<K>
    extends ObjectSortedSet<Reference2DoubleMap.Entry<K>>,
    Reference2DoubleMap.FastEntrySet<K> {
        public ObjectBidirectionalIterator<Reference2DoubleMap.Entry<K>> fastIterator(Reference2DoubleMap.Entry<K> var1);
    }
}

