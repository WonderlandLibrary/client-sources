/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.objects.Reference2BooleanMap;
import it.unimi.dsi.fastutil.objects.ReferenceSortedSet;
import java.util.Comparator;
import java.util.Map;
import java.util.SortedMap;

public interface Reference2BooleanSortedMap<K>
extends Reference2BooleanMap<K>,
SortedMap<K, Boolean> {
    @Override
    public ObjectSortedSet<Map.Entry<K, Boolean>> entrySet();

    @Override
    public ObjectSortedSet<Reference2BooleanMap.Entry<K>> reference2BooleanEntrySet();

    @Override
    public ReferenceSortedSet<K> keySet();

    @Override
    public BooleanCollection values();

    @Override
    public Comparator<? super K> comparator();

    public Reference2BooleanSortedMap<K> subMap(K var1, K var2);

    public Reference2BooleanSortedMap<K> headMap(K var1);

    public Reference2BooleanSortedMap<K> tailMap(K var1);

    public static interface FastSortedEntrySet<K>
    extends ObjectSortedSet<Reference2BooleanMap.Entry<K>>,
    Reference2BooleanMap.FastEntrySet<K> {
        public ObjectBidirectionalIterator<Reference2BooleanMap.Entry<K>> fastIterator(Reference2BooleanMap.Entry<K> var1);
    }
}

