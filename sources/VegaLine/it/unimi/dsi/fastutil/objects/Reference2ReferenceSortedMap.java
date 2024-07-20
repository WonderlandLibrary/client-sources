/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.objects.Reference2ReferenceMap;
import it.unimi.dsi.fastutil.objects.ReferenceCollection;
import it.unimi.dsi.fastutil.objects.ReferenceSortedSet;
import java.util.Comparator;
import java.util.Map;
import java.util.SortedMap;

public interface Reference2ReferenceSortedMap<K, V>
extends Reference2ReferenceMap<K, V>,
SortedMap<K, V> {
    @Override
    public ObjectSortedSet<Map.Entry<K, V>> entrySet();

    @Override
    public ObjectSortedSet<Reference2ReferenceMap.Entry<K, V>> reference2ReferenceEntrySet();

    @Override
    public ReferenceSortedSet<K> keySet();

    @Override
    public ReferenceCollection<V> values();

    @Override
    public Comparator<? super K> comparator();

    @Override
    public Reference2ReferenceSortedMap<K, V> subMap(K var1, K var2);

    @Override
    public Reference2ReferenceSortedMap<K, V> headMap(K var1);

    @Override
    public Reference2ReferenceSortedMap<K, V> tailMap(K var1);

    public static interface FastSortedEntrySet<K, V>
    extends ObjectSortedSet<Reference2ReferenceMap.Entry<K, V>>,
    Reference2ReferenceMap.FastEntrySet<K, V> {
        public ObjectBidirectionalIterator<Reference2ReferenceMap.Entry<K, V>> fastIterator(Reference2ReferenceMap.Entry<K, V> var1);
    }
}

