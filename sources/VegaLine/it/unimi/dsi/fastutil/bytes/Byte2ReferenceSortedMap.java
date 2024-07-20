/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.Byte2ReferenceMap;
import it.unimi.dsi.fastutil.bytes.ByteComparator;
import it.unimi.dsi.fastutil.bytes.ByteSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.objects.ReferenceCollection;
import java.util.Map;
import java.util.SortedMap;

public interface Byte2ReferenceSortedMap<V>
extends Byte2ReferenceMap<V>,
SortedMap<Byte, V> {
    @Override
    public ObjectSortedSet<Map.Entry<Byte, V>> entrySet();

    @Override
    public ObjectSortedSet<Byte2ReferenceMap.Entry<V>> byte2ReferenceEntrySet();

    @Override
    public ByteSortedSet keySet();

    @Override
    public ReferenceCollection<V> values();

    public ByteComparator comparator();

    public Byte2ReferenceSortedMap<V> subMap(Byte var1, Byte var2);

    public Byte2ReferenceSortedMap<V> headMap(Byte var1);

    public Byte2ReferenceSortedMap<V> tailMap(Byte var1);

    public Byte2ReferenceSortedMap<V> subMap(byte var1, byte var2);

    public Byte2ReferenceSortedMap<V> headMap(byte var1);

    public Byte2ReferenceSortedMap<V> tailMap(byte var1);

    public byte firstByteKey();

    public byte lastByteKey();

    public static interface FastSortedEntrySet<V>
    extends ObjectSortedSet<Byte2ReferenceMap.Entry<V>>,
    Byte2ReferenceMap.FastEntrySet<V> {
        public ObjectBidirectionalIterator<Byte2ReferenceMap.Entry<V>> fastIterator(Byte2ReferenceMap.Entry<V> var1);
    }
}

