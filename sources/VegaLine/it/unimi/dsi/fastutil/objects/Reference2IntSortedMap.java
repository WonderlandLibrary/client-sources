/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.objects.Reference2IntMap;
import it.unimi.dsi.fastutil.objects.ReferenceSortedSet;
import java.util.Comparator;
import java.util.Map;
import java.util.SortedMap;

public interface Reference2IntSortedMap<K>
extends Reference2IntMap<K>,
SortedMap<K, Integer> {
    @Override
    public ObjectSortedSet<Map.Entry<K, Integer>> entrySet();

    @Override
    public ObjectSortedSet<Reference2IntMap.Entry<K>> reference2IntEntrySet();

    @Override
    public ReferenceSortedSet<K> keySet();

    @Override
    public IntCollection values();

    @Override
    public Comparator<? super K> comparator();

    public Reference2IntSortedMap<K> subMap(K var1, K var2);

    public Reference2IntSortedMap<K> headMap(K var1);

    public Reference2IntSortedMap<K> tailMap(K var1);

    public static interface FastSortedEntrySet<K>
    extends ObjectSortedSet<Reference2IntMap.Entry<K>>,
    Reference2IntMap.FastEntrySet<K> {
        public ObjectBidirectionalIterator<Reference2IntMap.Entry<K>> fastIterator(Reference2IntMap.Entry<K> var1);
    }
}

