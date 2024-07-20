/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.objects.Reference2CharMap;
import it.unimi.dsi.fastutil.objects.ReferenceSortedSet;
import java.util.Comparator;
import java.util.Map;
import java.util.SortedMap;

public interface Reference2CharSortedMap<K>
extends Reference2CharMap<K>,
SortedMap<K, Character> {
    @Override
    public ObjectSortedSet<Map.Entry<K, Character>> entrySet();

    @Override
    public ObjectSortedSet<Reference2CharMap.Entry<K>> reference2CharEntrySet();

    @Override
    public ReferenceSortedSet<K> keySet();

    @Override
    public CharCollection values();

    @Override
    public Comparator<? super K> comparator();

    public Reference2CharSortedMap<K> subMap(K var1, K var2);

    public Reference2CharSortedMap<K> headMap(K var1);

    public Reference2CharSortedMap<K> tailMap(K var1);

    public static interface FastSortedEntrySet<K>
    extends ObjectSortedSet<Reference2CharMap.Entry<K>>,
    Reference2CharMap.FastEntrySet<K> {
        public ObjectBidirectionalIterator<Reference2CharMap.Entry<K>> fastIterator(Reference2CharMap.Entry<K> var1);
    }
}

