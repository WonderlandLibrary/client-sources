/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.Byte2ByteMap;
import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteComparator;
import it.unimi.dsi.fastutil.bytes.ByteSet;
import it.unimi.dsi.fastutil.bytes.ByteSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface Byte2ByteSortedMap
extends Byte2ByteMap,
SortedMap<Byte, Byte> {
    public Byte2ByteSortedMap subMap(byte var1, byte var2);

    public Byte2ByteSortedMap headMap(byte var1);

    public Byte2ByteSortedMap tailMap(byte var1);

    public byte firstByteKey();

    public byte lastByteKey();

    @Deprecated
    default public Byte2ByteSortedMap subMap(Byte by, Byte by2) {
        return this.subMap((byte)by, (byte)by2);
    }

    @Deprecated
    default public Byte2ByteSortedMap headMap(Byte by) {
        return this.headMap((byte)by);
    }

    @Deprecated
    default public Byte2ByteSortedMap tailMap(Byte by) {
        return this.tailMap((byte)by);
    }

    @Override
    @Deprecated
    default public Byte firstKey() {
        return this.firstByteKey();
    }

    @Override
    @Deprecated
    default public Byte lastKey() {
        return this.lastByteKey();
    }

    @Override
    @Deprecated
    default public ObjectSortedSet<Map.Entry<Byte, Byte>> entrySet() {
        return this.byte2ByteEntrySet();
    }

    public ObjectSortedSet<Byte2ByteMap.Entry> byte2ByteEntrySet();

    @Override
    public ByteSortedSet keySet();

    @Override
    public ByteCollection values();

    public ByteComparator comparator();

    @Override
    default public ByteSet keySet() {
        return this.keySet();
    }

    @Override
    @Deprecated
    default public ObjectSet entrySet() {
        return this.entrySet();
    }

    default public ObjectSet byte2ByteEntrySet() {
        return this.byte2ByteEntrySet();
    }

    @Override
    @Deprecated
    default public Set entrySet() {
        return this.entrySet();
    }

    @Override
    default public Collection values() {
        return this.values();
    }

    @Override
    default public Set keySet() {
        return this.keySet();
    }

    @Override
    @Deprecated
    default public Object lastKey() {
        return this.lastKey();
    }

    @Override
    @Deprecated
    default public Object firstKey() {
        return this.firstKey();
    }

    @Override
    @Deprecated
    default public SortedMap tailMap(Object object) {
        return this.tailMap((Byte)object);
    }

    @Override
    @Deprecated
    default public SortedMap headMap(Object object) {
        return this.headMap((Byte)object);
    }

    @Override
    @Deprecated
    default public SortedMap subMap(Object object, Object object2) {
        return this.subMap((Byte)object, (Byte)object2);
    }

    @Override
    default public Comparator comparator() {
        return this.comparator();
    }

    public static interface FastSortedEntrySet
    extends ObjectSortedSet<Byte2ByteMap.Entry>,
    Byte2ByteMap.FastEntrySet {
        public ObjectBidirectionalIterator<Byte2ByteMap.Entry> fastIterator();

        public ObjectBidirectionalIterator<Byte2ByteMap.Entry> fastIterator(Byte2ByteMap.Entry var1);

        default public ObjectIterator fastIterator() {
            return this.fastIterator();
        }
    }
}

