/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.objects.ReferenceCollection;
import it.unimi.dsi.fastutil.shorts.Short2ReferenceMap;
import it.unimi.dsi.fastutil.shorts.ShortComparator;
import it.unimi.dsi.fastutil.shorts.ShortSortedSet;
import java.util.Map;
import java.util.SortedMap;

public interface Short2ReferenceSortedMap<V>
extends Short2ReferenceMap<V>,
SortedMap<Short, V> {
    @Override
    public ObjectSortedSet<Map.Entry<Short, V>> entrySet();

    @Override
    public ObjectSortedSet<Short2ReferenceMap.Entry<V>> short2ReferenceEntrySet();

    @Override
    public ShortSortedSet keySet();

    @Override
    public ReferenceCollection<V> values();

    public ShortComparator comparator();

    public Short2ReferenceSortedMap<V> subMap(Short var1, Short var2);

    public Short2ReferenceSortedMap<V> headMap(Short var1);

    public Short2ReferenceSortedMap<V> tailMap(Short var1);

    public Short2ReferenceSortedMap<V> subMap(short var1, short var2);

    public Short2ReferenceSortedMap<V> headMap(short var1);

    public Short2ReferenceSortedMap<V> tailMap(short var1);

    public short firstShortKey();

    public short lastShortKey();

    public static interface FastSortedEntrySet<V>
    extends ObjectSortedSet<Short2ReferenceMap.Entry<V>>,
    Short2ReferenceMap.FastEntrySet<V> {
        public ObjectBidirectionalIterator<Short2ReferenceMap.Entry<V>> fastIterator(Short2ReferenceMap.Entry<V> var1);
    }
}

