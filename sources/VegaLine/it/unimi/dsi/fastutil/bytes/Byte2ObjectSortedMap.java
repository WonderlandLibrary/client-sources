/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.Byte2ObjectMap;
import it.unimi.dsi.fastutil.bytes.ByteComparator;
import it.unimi.dsi.fastutil.bytes.ByteSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Map;
import java.util.SortedMap;

public interface Byte2ObjectSortedMap<V>
extends Byte2ObjectMap<V>,
SortedMap<Byte, V> {
    @Override
    public ObjectSortedSet<Map.Entry<Byte, V>> entrySet();

    @Override
    public ObjectSortedSet<Byte2ObjectMap.Entry<V>> byte2ObjectEntrySet();

    @Override
    public ByteSortedSet keySet();

    @Override
    public ObjectCollection<V> values();

    public ByteComparator comparator();

    public Byte2ObjectSortedMap<V> subMap(Byte var1, Byte var2);

    public Byte2ObjectSortedMap<V> headMap(Byte var1);

    public Byte2ObjectSortedMap<V> tailMap(Byte var1);

    public Byte2ObjectSortedMap<V> subMap(byte var1, byte var2);

    public Byte2ObjectSortedMap<V> headMap(byte var1);

    public Byte2ObjectSortedMap<V> tailMap(byte var1);

    public byte firstByteKey();

    public byte lastByteKey();

    public static interface FastSortedEntrySet<V>
    extends ObjectSortedSet<Byte2ObjectMap.Entry<V>>,
    Byte2ObjectMap.FastEntrySet<V> {
        public ObjectBidirectionalIterator<Byte2ObjectMap.Entry<V>> fastIterator(Byte2ObjectMap.Entry<V> var1);
    }
}

