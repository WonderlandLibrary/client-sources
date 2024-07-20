/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Comparator;
import java.util.Map;
import java.util.SortedMap;

public interface Object2FloatSortedMap<K>
extends Object2FloatMap<K>,
SortedMap<K, Float> {
    @Override
    public ObjectSortedSet<Map.Entry<K, Float>> entrySet();

    @Override
    public ObjectSortedSet<Object2FloatMap.Entry<K>> object2FloatEntrySet();

    @Override
    public ObjectSortedSet<K> keySet();

    @Override
    public FloatCollection values();

    @Override
    public Comparator<? super K> comparator();

    public Object2FloatSortedMap<K> subMap(K var1, K var2);

    public Object2FloatSortedMap<K> headMap(K var1);

    public Object2FloatSortedMap<K> tailMap(K var1);

    public static interface FastSortedEntrySet<K>
    extends ObjectSortedSet<Object2FloatMap.Entry<K>>,
    Object2FloatMap.FastEntrySet<K> {
        public ObjectBidirectionalIterator<Object2FloatMap.Entry<K>> fastIterator(Object2FloatMap.Entry<K> var1);
    }
}

