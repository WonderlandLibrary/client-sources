/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.shorts.Short2ObjectMap;
import it.unimi.dsi.fastutil.shorts.ShortComparator;
import it.unimi.dsi.fastutil.shorts.ShortSortedSet;
import java.util.Map;
import java.util.SortedMap;

public interface Short2ObjectSortedMap<V>
extends Short2ObjectMap<V>,
SortedMap<Short, V> {
    @Override
    public ObjectSortedSet<Map.Entry<Short, V>> entrySet();

    @Override
    public ObjectSortedSet<Short2ObjectMap.Entry<V>> short2ObjectEntrySet();

    @Override
    public ShortSortedSet keySet();

    @Override
    public ObjectCollection<V> values();

    public ShortComparator comparator();

    public Short2ObjectSortedMap<V> subMap(Short var1, Short var2);

    public Short2ObjectSortedMap<V> headMap(Short var1);

    public Short2ObjectSortedMap<V> tailMap(Short var1);

    public Short2ObjectSortedMap<V> subMap(short var1, short var2);

    public Short2ObjectSortedMap<V> headMap(short var1);

    public Short2ObjectSortedMap<V> tailMap(short var1);

    public short firstShortKey();

    public short lastShortKey();

    public static interface FastSortedEntrySet<V>
    extends ObjectSortedSet<Short2ObjectMap.Entry<V>>,
    Short2ObjectMap.FastEntrySet<V> {
        public ObjectBidirectionalIterator<Short2ObjectMap.Entry<V>> fastIterator(Short2ObjectMap.Entry<V> var1);
    }
}

