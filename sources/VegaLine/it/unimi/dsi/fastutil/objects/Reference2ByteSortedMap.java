/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.objects.Reference2ByteMap;
import it.unimi.dsi.fastutil.objects.ReferenceSortedSet;
import java.util.Comparator;
import java.util.Map;
import java.util.SortedMap;

public interface Reference2ByteSortedMap<K>
extends Reference2ByteMap<K>,
SortedMap<K, Byte> {
    @Override
    public ObjectSortedSet<Map.Entry<K, Byte>> entrySet();

    @Override
    public ObjectSortedSet<Reference2ByteMap.Entry<K>> reference2ByteEntrySet();

    @Override
    public ReferenceSortedSet<K> keySet();

    @Override
    public ByteCollection values();

    @Override
    public Comparator<? super K> comparator();

    public Reference2ByteSortedMap<K> subMap(K var1, K var2);

    public Reference2ByteSortedMap<K> headMap(K var1);

    public Reference2ByteSortedMap<K> tailMap(K var1);

    public static interface FastSortedEntrySet<K>
    extends ObjectSortedSet<Reference2ByteMap.Entry<K>>,
    Reference2ByteMap.FastEntrySet<K> {
        public ObjectBidirectionalIterator<Reference2ByteMap.Entry<K>> fastIterator(Reference2ByteMap.Entry<K> var1);
    }
}

