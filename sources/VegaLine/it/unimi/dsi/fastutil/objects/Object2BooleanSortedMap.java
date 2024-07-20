/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Comparator;
import java.util.Map;
import java.util.SortedMap;

public interface Object2BooleanSortedMap<K>
extends Object2BooleanMap<K>,
SortedMap<K, Boolean> {
    @Override
    public ObjectSortedSet<Map.Entry<K, Boolean>> entrySet();

    @Override
    public ObjectSortedSet<Object2BooleanMap.Entry<K>> object2BooleanEntrySet();

    @Override
    public ObjectSortedSet<K> keySet();

    @Override
    public BooleanCollection values();

    @Override
    public Comparator<? super K> comparator();

    public Object2BooleanSortedMap<K> subMap(K var1, K var2);

    public Object2BooleanSortedMap<K> headMap(K var1);

    public Object2BooleanSortedMap<K> tailMap(K var1);

    public static interface FastSortedEntrySet<K>
    extends ObjectSortedSet<Object2BooleanMap.Entry<K>>,
    Object2BooleanMap.FastEntrySet<K> {
        public ObjectBidirectionalIterator<Object2BooleanMap.Entry<K>> fastIterator(Object2BooleanMap.Entry<K> var1);
    }
}

