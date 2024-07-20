/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.objects.Reference2ShortMap;
import it.unimi.dsi.fastutil.objects.ReferenceSortedSet;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
import java.util.Comparator;
import java.util.Map;
import java.util.SortedMap;

public interface Reference2ShortSortedMap<K>
extends Reference2ShortMap<K>,
SortedMap<K, Short> {
    @Override
    public ObjectSortedSet<Map.Entry<K, Short>> entrySet();

    @Override
    public ObjectSortedSet<Reference2ShortMap.Entry<K>> reference2ShortEntrySet();

    @Override
    public ReferenceSortedSet<K> keySet();

    @Override
    public ShortCollection values();

    @Override
    public Comparator<? super K> comparator();

    public Reference2ShortSortedMap<K> subMap(K var1, K var2);

    public Reference2ShortSortedMap<K> headMap(K var1);

    public Reference2ShortSortedMap<K> tailMap(K var1);

    public static interface FastSortedEntrySet<K>
    extends ObjectSortedSet<Reference2ShortMap.Entry<K>>,
    Reference2ShortMap.FastEntrySet<K> {
        public ObjectBidirectionalIterator<Reference2ShortMap.Entry<K>> fastIterator(Reference2ShortMap.Entry<K> var1);
    }
}

