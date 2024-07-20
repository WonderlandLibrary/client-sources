/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.Object2ReferenceMap;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.objects.ReferenceCollection;
import java.util.Comparator;
import java.util.Map;
import java.util.SortedMap;

public interface Object2ReferenceSortedMap<K, V>
extends Object2ReferenceMap<K, V>,
SortedMap<K, V> {
    @Override
    public ObjectSortedSet<Map.Entry<K, V>> entrySet();

    @Override
    public ObjectSortedSet<Object2ReferenceMap.Entry<K, V>> object2ReferenceEntrySet();

    @Override
    public ObjectSortedSet<K> keySet();

    @Override
    public ReferenceCollection<V> values();

    @Override
    public Comparator<? super K> comparator();

    @Override
    public Object2ReferenceSortedMap<K, V> subMap(K var1, K var2);

    @Override
    public Object2ReferenceSortedMap<K, V> headMap(K var1);

    @Override
    public Object2ReferenceSortedMap<K, V> tailMap(K var1);

    public static interface FastSortedEntrySet<K, V>
    extends ObjectSortedSet<Object2ReferenceMap.Entry<K, V>>,
    Object2ReferenceMap.FastEntrySet<K, V> {
        public ObjectBidirectionalIterator<Object2ReferenceMap.Entry<K, V>> fastIterator(Object2ReferenceMap.Entry<K, V> var1);
    }
}

