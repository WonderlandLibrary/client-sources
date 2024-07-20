/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.objects.Object2LongMap;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Comparator;
import java.util.Map;
import java.util.SortedMap;

public interface Object2LongSortedMap<K>
extends Object2LongMap<K>,
SortedMap<K, Long> {
    @Override
    public ObjectSortedSet<Map.Entry<K, Long>> entrySet();

    @Override
    public ObjectSortedSet<Object2LongMap.Entry<K>> object2LongEntrySet();

    @Override
    public ObjectSortedSet<K> keySet();

    @Override
    public LongCollection values();

    @Override
    public Comparator<? super K> comparator();

    public Object2LongSortedMap<K> subMap(K var1, K var2);

    public Object2LongSortedMap<K> headMap(K var1);

    public Object2LongSortedMap<K> tailMap(K var1);

    public static interface FastSortedEntrySet<K>
    extends ObjectSortedSet<Object2LongMap.Entry<K>>,
    Object2LongMap.FastEntrySet<K> {
        public ObjectBidirectionalIterator<Object2LongMap.Entry<K>> fastIterator(Object2LongMap.Entry<K> var1);
    }
}

