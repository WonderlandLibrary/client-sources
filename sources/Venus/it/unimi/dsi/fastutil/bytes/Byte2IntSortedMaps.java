/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.AbstractByte2IntMap;
import it.unimi.dsi.fastutil.bytes.Byte2IntMap;
import it.unimi.dsi.fastutil.bytes.Byte2IntMaps;
import it.unimi.dsi.fastutil.bytes.Byte2IntSortedMap;
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

public final class Byte2IntSortedMaps {
    public static final EmptySortedMap EMPTY_MAP = new EmptySortedMap();

    private Byte2IntSortedMaps() {
    }

    public static Comparator<? super Map.Entry<Byte, ?>> entryComparator(ByteComparator byteComparator) {
        return (arg_0, arg_1) -> Byte2IntSortedMaps.lambda$entryComparator$0(byteComparator, arg_0, arg_1);
    }

    public static ObjectBidirectionalIterator<Byte2IntMap.Entry> fastIterator(Byte2IntSortedMap byte2IntSortedMap) {
        ObjectSet objectSet = byte2IntSortedMap.byte2IntEntrySet();
        return objectSet instanceof Byte2IntSortedMap.FastSortedEntrySet ? ((Byte2IntSortedMap.FastSortedEntrySet)objectSet).fastIterator() : objectSet.iterator();
    }

    public static ObjectBidirectionalIterable<Byte2IntMap.Entry> fastIterable(Byte2IntSortedMap byte2IntSortedMap) {
        ObjectSet objectSet = byte2IntSortedMap.byte2IntEntrySet();
        return objectSet instanceof Byte2IntSortedMap.FastSortedEntrySet ? ((Byte2IntSortedMap.FastSortedEntrySet)objectSet)::fastIterator : objectSet;
    }

    public static Byte2IntSortedMap singleton(Byte by, Integer n) {
        return new Singleton(by, n);
    }

    public static Byte2IntSortedMap singleton(Byte by, Integer n, ByteComparator byteComparator) {
        return new Singleton(by, n, byteComparator);
    }

    public static Byte2IntSortedMap singleton(byte by, int n) {
        return new Singleton(by, n);
    }

    public static Byte2IntSortedMap singleton(byte by, int n, ByteComparator byteComparator) {
        return new Singleton(by, n, byteComparator);
    }

    public static Byte2IntSortedMap synchronize(Byte2IntSortedMap byte2IntSortedMap) {
        return new SynchronizedSortedMap(byte2IntSortedMap);
    }

    public static Byte2IntSortedMap synchronize(Byte2IntSortedMap byte2IntSortedMap, Object object) {
        return new SynchronizedSortedMap(byte2IntSortedMap, object);
    }

    public static Byte2IntSortedMap unmodifiable(Byte2IntSortedMap byte2IntSortedMap) {
        return new UnmodifiableSortedMap(byte2IntSortedMap);
    }

    private static int lambda$entryComparator$0(ByteComparator byteComparator, Map.Entry entry, Map.Entry entry2) {
        return byteComparator.compare((byte)((Byte)entry.getKey()), (byte)((Byte)entry2.getKey()));
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableSortedMap
    extends Byte2IntMaps.UnmodifiableMap
    implements Byte2IntSortedMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Byte2IntSortedMap sortedMap;

        protected UnmodifiableSortedMap(Byte2IntSortedMap byte2IntSortedMap) {
            super(byte2IntSortedMap);
            this.sortedMap = byte2IntSortedMap;
        }

        @Override
        public ByteComparator comparator() {
            return this.sortedMap.comparator();
        }

        @Override
        public ObjectSortedSet<Byte2IntMap.Entry> byte2IntEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.unmodifiable(this.sortedMap.byte2IntEntrySet());
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Byte, Integer>> entrySet() {
            return this.byte2IntEntrySet();
        }

        @Override
        public ByteSortedSet keySet() {
            if (this.keys == null) {
                this.keys = ByteSortedSets.unmodifiable(this.sortedMap.keySet());
            }
            return (ByteSortedSet)this.keys;
        }

        @Override
        public Byte2IntSortedMap subMap(byte by, byte by2) {
            return new UnmodifiableSortedMap(this.sortedMap.subMap(by, by2));
        }

        @Override
        public Byte2IntSortedMap headMap(byte by) {
            return new UnmodifiableSortedMap(this.sortedMap.headMap(by));
        }

        @Override
        public Byte2IntSortedMap tailMap(byte by) {
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
        public Byte2IntSortedMap subMap(Byte by, Byte by2) {
            return new UnmodifiableSortedMap(this.sortedMap.subMap(by, by2));
        }

        @Override
        @Deprecated
        public Byte2IntSortedMap headMap(Byte by) {
            return new UnmodifiableSortedMap(this.sortedMap.headMap(by));
        }

        @Override
        @Deprecated
        public Byte2IntSortedMap tailMap(Byte by) {
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
        public ObjectSet byte2IntEntrySet() {
            return this.byte2IntEntrySet();
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
    extends Byte2IntMaps.SynchronizedMap
    implements Byte2IntSortedMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Byte2IntSortedMap sortedMap;

        protected SynchronizedSortedMap(Byte2IntSortedMap byte2IntSortedMap, Object object) {
            super(byte2IntSortedMap, object);
            this.sortedMap = byte2IntSortedMap;
        }

        protected SynchronizedSortedMap(Byte2IntSortedMap byte2IntSortedMap) {
            super(byte2IntSortedMap);
            this.sortedMap = byte2IntSortedMap;
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
        public ObjectSortedSet<Byte2IntMap.Entry> byte2IntEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.synchronize(this.sortedMap.byte2IntEntrySet(), this.sync);
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Byte, Integer>> entrySet() {
            return this.byte2IntEntrySet();
        }

        @Override
        public ByteSortedSet keySet() {
            if (this.keys == null) {
                this.keys = ByteSortedSets.synchronize(this.sortedMap.keySet(), this.sync);
            }
            return (ByteSortedSet)this.keys;
        }

        @Override
        public Byte2IntSortedMap subMap(byte by, byte by2) {
            return new SynchronizedSortedMap(this.sortedMap.subMap(by, by2), this.sync);
        }

        @Override
        public Byte2IntSortedMap headMap(byte by) {
            return new SynchronizedSortedMap(this.sortedMap.headMap(by), this.sync);
        }

        @Override
        public Byte2IntSortedMap tailMap(byte by) {
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
        public Byte2IntSortedMap subMap(Byte by, Byte by2) {
            return new SynchronizedSortedMap(this.sortedMap.subMap(by, by2), this.sync);
        }

        @Override
        @Deprecated
        public Byte2IntSortedMap headMap(Byte by) {
            return new SynchronizedSortedMap(this.sortedMap.headMap(by), this.sync);
        }

        @Override
        @Deprecated
        public Byte2IntSortedMap tailMap(Byte by) {
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
        public ObjectSet byte2IntEntrySet() {
            return this.byte2IntEntrySet();
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
    extends Byte2IntMaps.Singleton
    implements Byte2IntSortedMap,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final ByteComparator comparator;

        protected Singleton(byte by, int n, ByteComparator byteComparator) {
            super(by, n);
            this.comparator = byteComparator;
        }

        protected Singleton(byte by, int n) {
            this(by, n, null);
        }

        final int compare(byte by, byte by2) {
            return this.comparator == null ? Byte.compare(by, by2) : this.comparator.compare(by, by2);
        }

        @Override
        public ByteComparator comparator() {
            return this.comparator;
        }

        @Override
        public ObjectSortedSet<Byte2IntMap.Entry> byte2IntEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.singleton(new AbstractByte2IntMap.BasicEntry(this.key, this.value), Byte2IntSortedMaps.entryComparator(this.comparator));
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Byte, Integer>> entrySet() {
            return this.byte2IntEntrySet();
        }

        @Override
        public ByteSortedSet keySet() {
            if (this.keys == null) {
                this.keys = ByteSortedSets.singleton(this.key, this.comparator);
            }
            return (ByteSortedSet)this.keys;
        }

        @Override
        public Byte2IntSortedMap subMap(byte by, byte by2) {
            if (this.compare(by, this.key) <= 0 && this.compare(this.key, by2) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Byte2IntSortedMap headMap(byte by) {
            if (this.compare(this.key, by) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Byte2IntSortedMap tailMap(byte by) {
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
        public Byte2IntSortedMap headMap(Byte by) {
            return this.headMap((byte)by);
        }

        @Override
        @Deprecated
        public Byte2IntSortedMap tailMap(Byte by) {
            return this.tailMap((byte)by);
        }

        @Override
        @Deprecated
        public Byte2IntSortedMap subMap(Byte by, Byte by2) {
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
        public ObjectSet byte2IntEntrySet() {
            return this.byte2IntEntrySet();
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
    extends Byte2IntMaps.EmptyMap
    implements Byte2IntSortedMap,
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
        public ObjectSortedSet<Byte2IntMap.Entry> byte2IntEntrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Byte, Integer>> entrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        public ByteSortedSet keySet() {
            return ByteSortedSets.EMPTY_SET;
        }

        @Override
        public Byte2IntSortedMap subMap(byte by, byte by2) {
            return EMPTY_MAP;
        }

        @Override
        public Byte2IntSortedMap headMap(byte by) {
            return EMPTY_MAP;
        }

        @Override
        public Byte2IntSortedMap tailMap(byte by) {
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
        public Byte2IntSortedMap headMap(Byte by) {
            return this.headMap((byte)by);
        }

        @Override
        @Deprecated
        public Byte2IntSortedMap tailMap(Byte by) {
            return this.tailMap((byte)by);
        }

        @Override
        @Deprecated
        public Byte2IntSortedMap subMap(Byte by, Byte by2) {
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
        public ObjectSet byte2IntEntrySet() {
            return this.byte2IntEntrySet();
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

