/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.AbstractByte2DoubleMap;
import it.unimi.dsi.fastutil.bytes.Byte2DoubleMap;
import it.unimi.dsi.fastutil.bytes.Byte2DoubleMaps;
import it.unimi.dsi.fastutil.bytes.Byte2DoubleSortedMap;
import it.unimi.dsi.fastutil.bytes.ByteComparator;
import it.unimi.dsi.fastutil.bytes.ByteSet;
import it.unimi.dsi.fastutil.bytes.ByteSortedSet;
import it.unimi.dsi.fastutil.bytes.ByteSortedSets;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterable;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectSortedSets;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedMap;

public final class Byte2DoubleSortedMaps {
    public static final EmptySortedMap EMPTY_MAP = new EmptySortedMap();

    private Byte2DoubleSortedMaps() {
    }

    public static Comparator<? super Map.Entry<Byte, ?>> entryComparator(ByteComparator byteComparator) {
        return (arg_0, arg_1) -> Byte2DoubleSortedMaps.lambda$entryComparator$0(byteComparator, arg_0, arg_1);
    }

    public static ObjectBidirectionalIterator<Byte2DoubleMap.Entry> fastIterator(Byte2DoubleSortedMap byte2DoubleSortedMap) {
        ObjectSet objectSet = byte2DoubleSortedMap.byte2DoubleEntrySet();
        return objectSet instanceof Byte2DoubleSortedMap.FastSortedEntrySet ? ((Byte2DoubleSortedMap.FastSortedEntrySet)objectSet).fastIterator() : objectSet.iterator();
    }

    public static ObjectBidirectionalIterable<Byte2DoubleMap.Entry> fastIterable(Byte2DoubleSortedMap byte2DoubleSortedMap) {
        ObjectSet objectSet = byte2DoubleSortedMap.byte2DoubleEntrySet();
        return objectSet instanceof Byte2DoubleSortedMap.FastSortedEntrySet ? ((Byte2DoubleSortedMap.FastSortedEntrySet)objectSet)::fastIterator : objectSet;
    }

    public static Byte2DoubleSortedMap singleton(Byte by, Double d) {
        return new Singleton(by, d);
    }

    public static Byte2DoubleSortedMap singleton(Byte by, Double d, ByteComparator byteComparator) {
        return new Singleton(by, d, byteComparator);
    }

    public static Byte2DoubleSortedMap singleton(byte by, double d) {
        return new Singleton(by, d);
    }

    public static Byte2DoubleSortedMap singleton(byte by, double d, ByteComparator byteComparator) {
        return new Singleton(by, d, byteComparator);
    }

    public static Byte2DoubleSortedMap synchronize(Byte2DoubleSortedMap byte2DoubleSortedMap) {
        return new SynchronizedSortedMap(byte2DoubleSortedMap);
    }

    public static Byte2DoubleSortedMap synchronize(Byte2DoubleSortedMap byte2DoubleSortedMap, Object object) {
        return new SynchronizedSortedMap(byte2DoubleSortedMap, object);
    }

    public static Byte2DoubleSortedMap unmodifiable(Byte2DoubleSortedMap byte2DoubleSortedMap) {
        return new UnmodifiableSortedMap(byte2DoubleSortedMap);
    }

    private static int lambda$entryComparator$0(ByteComparator byteComparator, Map.Entry entry, Map.Entry entry2) {
        return byteComparator.compare((byte)((Byte)entry.getKey()), (byte)((Byte)entry2.getKey()));
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableSortedMap
    extends Byte2DoubleMaps.UnmodifiableMap
    implements Byte2DoubleSortedMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Byte2DoubleSortedMap sortedMap;

        protected UnmodifiableSortedMap(Byte2DoubleSortedMap byte2DoubleSortedMap) {
            super(byte2DoubleSortedMap);
            this.sortedMap = byte2DoubleSortedMap;
        }

        @Override
        public ByteComparator comparator() {
            return this.sortedMap.comparator();
        }

        @Override
        public ObjectSortedSet<Byte2DoubleMap.Entry> byte2DoubleEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.unmodifiable(this.sortedMap.byte2DoubleEntrySet());
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Byte, Double>> entrySet() {
            return this.byte2DoubleEntrySet();
        }

        @Override
        public ByteSortedSet keySet() {
            if (this.keys == null) {
                this.keys = ByteSortedSets.unmodifiable(this.sortedMap.keySet());
            }
            return (ByteSortedSet)this.keys;
        }

        @Override
        public Byte2DoubleSortedMap subMap(byte by, byte by2) {
            return new UnmodifiableSortedMap(this.sortedMap.subMap(by, by2));
        }

        @Override
        public Byte2DoubleSortedMap headMap(byte by) {
            return new UnmodifiableSortedMap(this.sortedMap.headMap(by));
        }

        @Override
        public Byte2DoubleSortedMap tailMap(byte by) {
            return new UnmodifiableSortedMap(this.sortedMap.tailMap(by));
        }

        @Override
        public byte firstByteKey() {
            return this.sortedMap.firstByteKey();
        }

        @Override
        public byte lastByteKey() {
            return this.sortedMap.lastByteKey();
        }

        @Override
        @Deprecated
        public Byte firstKey() {
            return this.sortedMap.firstKey();
        }

        @Override
        @Deprecated
        public Byte lastKey() {
            return this.sortedMap.lastKey();
        }

        @Override
        @Deprecated
        public Byte2DoubleSortedMap subMap(Byte by, Byte by2) {
            return new UnmodifiableSortedMap(this.sortedMap.subMap(by, by2));
        }

        @Override
        @Deprecated
        public Byte2DoubleSortedMap headMap(Byte by) {
            return new UnmodifiableSortedMap(this.sortedMap.headMap(by));
        }

        @Override
        @Deprecated
        public Byte2DoubleSortedMap tailMap(Byte by) {
            return new UnmodifiableSortedMap(this.sortedMap.tailMap(by));
        }

        @Override
        public ByteSet keySet() {
            return this.keySet();
        }

        @Override
        @Deprecated
        public ObjectSet entrySet() {
            return this.entrySet();
        }

        @Override
        public ObjectSet byte2DoubleEntrySet() {
            return this.byte2DoubleEntrySet();
        }

        @Override
        @Deprecated
        public Set entrySet() {
            return this.entrySet();
        }

        @Override
        public Set keySet() {
            return this.keySet();
        }

        @Override
        @Deprecated
        public Object lastKey() {
            return this.lastKey();
        }

        @Override
        @Deprecated
        public Object firstKey() {
            return this.firstKey();
        }

        @Override
        @Deprecated
        public SortedMap tailMap(Object object) {
            return this.tailMap((Byte)object);
        }

        @Override
        @Deprecated
        public SortedMap headMap(Object object) {
            return this.headMap((Byte)object);
        }

        @Override
        @Deprecated
        public SortedMap subMap(Object object, Object object2) {
            return this.subMap((Byte)object, (Byte)object2);
        }

        @Override
        public Comparator comparator() {
            return this.comparator();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class SynchronizedSortedMap
    extends Byte2DoubleMaps.SynchronizedMap
    implements Byte2DoubleSortedMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Byte2DoubleSortedMap sortedMap;

        protected SynchronizedSortedMap(Byte2DoubleSortedMap byte2DoubleSortedMap, Object object) {
            super(byte2DoubleSortedMap, object);
            this.sortedMap = byte2DoubleSortedMap;
        }

        protected SynchronizedSortedMap(Byte2DoubleSortedMap byte2DoubleSortedMap) {
            super(byte2DoubleSortedMap);
            this.sortedMap = byte2DoubleSortedMap;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public ByteComparator comparator() {
            Object object = this.sync;
            synchronized (object) {
                return this.sortedMap.comparator();
            }
        }

        @Override
        public ObjectSortedSet<Byte2DoubleMap.Entry> byte2DoubleEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.synchronize(this.sortedMap.byte2DoubleEntrySet(), this.sync);
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Byte, Double>> entrySet() {
            return this.byte2DoubleEntrySet();
        }

        @Override
        public ByteSortedSet keySet() {
            if (this.keys == null) {
                this.keys = ByteSortedSets.synchronize(this.sortedMap.keySet(), this.sync);
            }
            return (ByteSortedSet)this.keys;
        }

        @Override
        public Byte2DoubleSortedMap subMap(byte by, byte by2) {
            return new SynchronizedSortedMap(this.sortedMap.subMap(by, by2), this.sync);
        }

        @Override
        public Byte2DoubleSortedMap headMap(byte by) {
            return new SynchronizedSortedMap(this.sortedMap.headMap(by), this.sync);
        }

        @Override
        public Byte2DoubleSortedMap tailMap(byte by) {
            return new SynchronizedSortedMap(this.sortedMap.tailMap(by), this.sync);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public byte firstByteKey() {
            Object object = this.sync;
            synchronized (object) {
                return this.sortedMap.firstByteKey();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public byte lastByteKey() {
            Object object = this.sync;
            synchronized (object) {
                return this.sortedMap.lastByteKey();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Byte firstKey() {
            Object object = this.sync;
            synchronized (object) {
                return this.sortedMap.firstKey();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Byte lastKey() {
            Object object = this.sync;
            synchronized (object) {
                return this.sortedMap.lastKey();
            }
        }

        @Override
        @Deprecated
        public Byte2DoubleSortedMap subMap(Byte by, Byte by2) {
            return new SynchronizedSortedMap(this.sortedMap.subMap(by, by2), this.sync);
        }

        @Override
        @Deprecated
        public Byte2DoubleSortedMap headMap(Byte by) {
            return new SynchronizedSortedMap(this.sortedMap.headMap(by), this.sync);
        }

        @Override
        @Deprecated
        public Byte2DoubleSortedMap tailMap(Byte by) {
            return new SynchronizedSortedMap(this.sortedMap.tailMap(by), this.sync);
        }

        @Override
        public ByteSet keySet() {
            return this.keySet();
        }

        @Override
        @Deprecated
        public ObjectSet entrySet() {
            return this.entrySet();
        }

        @Override
        public ObjectSet byte2DoubleEntrySet() {
            return this.byte2DoubleEntrySet();
        }

        @Override
        @Deprecated
        public Set entrySet() {
            return this.entrySet();
        }

        @Override
        public Set keySet() {
            return this.keySet();
        }

        @Override
        @Deprecated
        public Object lastKey() {
            return this.lastKey();
        }

        @Override
        @Deprecated
        public Object firstKey() {
            return this.firstKey();
        }

        @Override
        @Deprecated
        public SortedMap tailMap(Object object) {
            return this.tailMap((Byte)object);
        }

        @Override
        @Deprecated
        public SortedMap headMap(Object object) {
            return this.headMap((Byte)object);
        }

        @Override
        @Deprecated
        public SortedMap subMap(Object object, Object object2) {
            return this.subMap((Byte)object, (Byte)object2);
        }

        @Override
        public Comparator comparator() {
            return this.comparator();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Singleton
    extends Byte2DoubleMaps.Singleton
    implements Byte2DoubleSortedMap,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final ByteComparator comparator;

        protected Singleton(byte by, double d, ByteComparator byteComparator) {
            super(by, d);
            this.comparator = byteComparator;
        }

        protected Singleton(byte by, double d) {
            this(by, d, null);
        }

        final int compare(byte by, byte by2) {
            return this.comparator == null ? Byte.compare(by, by2) : this.comparator.compare(by, by2);
        }

        @Override
        public ByteComparator comparator() {
            return this.comparator;
        }

        @Override
        public ObjectSortedSet<Byte2DoubleMap.Entry> byte2DoubleEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.singleton(new AbstractByte2DoubleMap.BasicEntry(this.key, this.value), Byte2DoubleSortedMaps.entryComparator(this.comparator));
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Byte, Double>> entrySet() {
            return this.byte2DoubleEntrySet();
        }

        @Override
        public ByteSortedSet keySet() {
            if (this.keys == null) {
                this.keys = ByteSortedSets.singleton(this.key, this.comparator);
            }
            return (ByteSortedSet)this.keys;
        }

        @Override
        public Byte2DoubleSortedMap subMap(byte by, byte by2) {
            if (this.compare(by, this.key) <= 0 && this.compare(this.key, by2) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Byte2DoubleSortedMap headMap(byte by) {
            if (this.compare(this.key, by) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Byte2DoubleSortedMap tailMap(byte by) {
            if (this.compare(by, this.key) <= 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public byte firstByteKey() {
            return this.key;
        }

        @Override
        public byte lastByteKey() {
            return this.key;
        }

        @Override
        @Deprecated
        public Byte2DoubleSortedMap headMap(Byte by) {
            return this.headMap((byte)by);
        }

        @Override
        @Deprecated
        public Byte2DoubleSortedMap tailMap(Byte by) {
            return this.tailMap((byte)by);
        }

        @Override
        @Deprecated
        public Byte2DoubleSortedMap subMap(Byte by, Byte by2) {
            return this.subMap((byte)by, (byte)by2);
        }

        @Override
        @Deprecated
        public Byte firstKey() {
            return this.firstByteKey();
        }

        @Override
        @Deprecated
        public Byte lastKey() {
            return this.lastByteKey();
        }

        @Override
        public ByteSet keySet() {
            return this.keySet();
        }

        @Override
        @Deprecated
        public ObjectSet entrySet() {
            return this.entrySet();
        }

        @Override
        public ObjectSet byte2DoubleEntrySet() {
            return this.byte2DoubleEntrySet();
        }

        @Override
        @Deprecated
        public Set entrySet() {
            return this.entrySet();
        }

        @Override
        public Set keySet() {
            return this.keySet();
        }

        @Override
        @Deprecated
        public Object lastKey() {
            return this.lastKey();
        }

        @Override
        @Deprecated
        public Object firstKey() {
            return this.firstKey();
        }

        @Override
        @Deprecated
        public SortedMap tailMap(Object object) {
            return this.tailMap((Byte)object);
        }

        @Override
        @Deprecated
        public SortedMap headMap(Object object) {
            return this.headMap((Byte)object);
        }

        @Override
        @Deprecated
        public SortedMap subMap(Object object, Object object2) {
            return this.subMap((Byte)object, (Byte)object2);
        }

        @Override
        public Comparator comparator() {
            return this.comparator();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class EmptySortedMap
    extends Byte2DoubleMaps.EmptyMap
    implements Byte2DoubleSortedMap,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptySortedMap() {
        }

        @Override
        public ByteComparator comparator() {
            return null;
        }

        @Override
        public ObjectSortedSet<Byte2DoubleMap.Entry> byte2DoubleEntrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Byte, Double>> entrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        public ByteSortedSet keySet() {
            return ByteSortedSets.EMPTY_SET;
        }

        @Override
        public Byte2DoubleSortedMap subMap(byte by, byte by2) {
            return EMPTY_MAP;
        }

        @Override
        public Byte2DoubleSortedMap headMap(byte by) {
            return EMPTY_MAP;
        }

        @Override
        public Byte2DoubleSortedMap tailMap(byte by) {
            return EMPTY_MAP;
        }

        @Override
        public byte firstByteKey() {
            throw new NoSuchElementException();
        }

        @Override
        public byte lastByteKey() {
            throw new NoSuchElementException();
        }

        @Override
        @Deprecated
        public Byte2DoubleSortedMap headMap(Byte by) {
            return this.headMap((byte)by);
        }

        @Override
        @Deprecated
        public Byte2DoubleSortedMap tailMap(Byte by) {
            return this.tailMap((byte)by);
        }

        @Override
        @Deprecated
        public Byte2DoubleSortedMap subMap(Byte by, Byte by2) {
            return this.subMap((byte)by, (byte)by2);
        }

        @Override
        @Deprecated
        public Byte firstKey() {
            return this.firstByteKey();
        }

        @Override
        @Deprecated
        public Byte lastKey() {
            return this.lastByteKey();
        }

        @Override
        public ByteSet keySet() {
            return this.keySet();
        }

        @Override
        public ObjectSet byte2DoubleEntrySet() {
            return this.byte2DoubleEntrySet();
        }

        @Override
        @Deprecated
        public ObjectSet entrySet() {
            return this.entrySet();
        }

        @Override
        @Deprecated
        public Set entrySet() {
            return this.entrySet();
        }

        @Override
        public Set keySet() {
            return this.keySet();
        }

        @Override
        @Deprecated
        public Object lastKey() {
            return this.lastKey();
        }

        @Override
        @Deprecated
        public Object firstKey() {
            return this.firstKey();
        }

        @Override
        @Deprecated
        public SortedMap tailMap(Object object) {
            return this.tailMap((Byte)object);
        }

        @Override
        @Deprecated
        public SortedMap headMap(Object object) {
            return this.headMap((Byte)object);
        }

        @Override
        @Deprecated
        public SortedMap subMap(Object object, Object object2) {
            return this.subMap((Byte)object, (Byte)object2);
        }

        @Override
        public Comparator comparator() {
            return this.comparator();
        }
    }
}

