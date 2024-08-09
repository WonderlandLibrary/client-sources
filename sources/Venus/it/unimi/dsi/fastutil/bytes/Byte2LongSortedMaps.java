/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.AbstractByte2LongMap;
import it.unimi.dsi.fastutil.bytes.Byte2LongMap;
import it.unimi.dsi.fastutil.bytes.Byte2LongMaps;
import it.unimi.dsi.fastutil.bytes.Byte2LongSortedMap;
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

public final class Byte2LongSortedMaps {
    public static final EmptySortedMap EMPTY_MAP = new EmptySortedMap();

    private Byte2LongSortedMaps() {
    }

    public static Comparator<? super Map.Entry<Byte, ?>> entryComparator(ByteComparator byteComparator) {
        return (arg_0, arg_1) -> Byte2LongSortedMaps.lambda$entryComparator$0(byteComparator, arg_0, arg_1);
    }

    public static ObjectBidirectionalIterator<Byte2LongMap.Entry> fastIterator(Byte2LongSortedMap byte2LongSortedMap) {
        ObjectSet objectSet = byte2LongSortedMap.byte2LongEntrySet();
        return objectSet instanceof Byte2LongSortedMap.FastSortedEntrySet ? ((Byte2LongSortedMap.FastSortedEntrySet)objectSet).fastIterator() : objectSet.iterator();
    }

    public static ObjectBidirectionalIterable<Byte2LongMap.Entry> fastIterable(Byte2LongSortedMap byte2LongSortedMap) {
        ObjectSet objectSet = byte2LongSortedMap.byte2LongEntrySet();
        return objectSet instanceof Byte2LongSortedMap.FastSortedEntrySet ? ((Byte2LongSortedMap.FastSortedEntrySet)objectSet)::fastIterator : objectSet;
    }

    public static Byte2LongSortedMap singleton(Byte by, Long l) {
        return new Singleton(by, l);
    }

    public static Byte2LongSortedMap singleton(Byte by, Long l, ByteComparator byteComparator) {
        return new Singleton(by, l, byteComparator);
    }

    public static Byte2LongSortedMap singleton(byte by, long l) {
        return new Singleton(by, l);
    }

    public static Byte2LongSortedMap singleton(byte by, long l, ByteComparator byteComparator) {
        return new Singleton(by, l, byteComparator);
    }

    public static Byte2LongSortedMap synchronize(Byte2LongSortedMap byte2LongSortedMap) {
        return new SynchronizedSortedMap(byte2LongSortedMap);
    }

    public static Byte2LongSortedMap synchronize(Byte2LongSortedMap byte2LongSortedMap, Object object) {
        return new SynchronizedSortedMap(byte2LongSortedMap, object);
    }

    public static Byte2LongSortedMap unmodifiable(Byte2LongSortedMap byte2LongSortedMap) {
        return new UnmodifiableSortedMap(byte2LongSortedMap);
    }

    private static int lambda$entryComparator$0(ByteComparator byteComparator, Map.Entry entry, Map.Entry entry2) {
        return byteComparator.compare((byte)((Byte)entry.getKey()), (byte)((Byte)entry2.getKey()));
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableSortedMap
    extends Byte2LongMaps.UnmodifiableMap
    implements Byte2LongSortedMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Byte2LongSortedMap sortedMap;

        protected UnmodifiableSortedMap(Byte2LongSortedMap byte2LongSortedMap) {
            super(byte2LongSortedMap);
            this.sortedMap = byte2LongSortedMap;
        }

        @Override
        public ByteComparator comparator() {
            return this.sortedMap.comparator();
        }

        @Override
        public ObjectSortedSet<Byte2LongMap.Entry> byte2LongEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.unmodifiable(this.sortedMap.byte2LongEntrySet());
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Byte, Long>> entrySet() {
            return this.byte2LongEntrySet();
        }

        @Override
        public ByteSortedSet keySet() {
            if (this.keys == null) {
                this.keys = ByteSortedSets.unmodifiable(this.sortedMap.keySet());
            }
            return (ByteSortedSet)this.keys;
        }

        @Override
        public Byte2LongSortedMap subMap(byte by, byte by2) {
            return new UnmodifiableSortedMap(this.sortedMap.subMap(by, by2));
        }

        @Override
        public Byte2LongSortedMap headMap(byte by) {
            return new UnmodifiableSortedMap(this.sortedMap.headMap(by));
        }

        @Override
        public Byte2LongSortedMap tailMap(byte by) {
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
        public Byte2LongSortedMap subMap(Byte by, Byte by2) {
            return new UnmodifiableSortedMap(this.sortedMap.subMap(by, by2));
        }

        @Override
        @Deprecated
        public Byte2LongSortedMap headMap(Byte by) {
            return new UnmodifiableSortedMap(this.sortedMap.headMap(by));
        }

        @Override
        @Deprecated
        public Byte2LongSortedMap tailMap(Byte by) {
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
        public ObjectSet byte2LongEntrySet() {
            return this.byte2LongEntrySet();
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
    extends Byte2LongMaps.SynchronizedMap
    implements Byte2LongSortedMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Byte2LongSortedMap sortedMap;

        protected SynchronizedSortedMap(Byte2LongSortedMap byte2LongSortedMap, Object object) {
            super(byte2LongSortedMap, object);
            this.sortedMap = byte2LongSortedMap;
        }

        protected SynchronizedSortedMap(Byte2LongSortedMap byte2LongSortedMap) {
            super(byte2LongSortedMap);
            this.sortedMap = byte2LongSortedMap;
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
        public ObjectSortedSet<Byte2LongMap.Entry> byte2LongEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.synchronize(this.sortedMap.byte2LongEntrySet(), this.sync);
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Byte, Long>> entrySet() {
            return this.byte2LongEntrySet();
        }

        @Override
        public ByteSortedSet keySet() {
            if (this.keys == null) {
                this.keys = ByteSortedSets.synchronize(this.sortedMap.keySet(), this.sync);
            }
            return (ByteSortedSet)this.keys;
        }

        @Override
        public Byte2LongSortedMap subMap(byte by, byte by2) {
            return new SynchronizedSortedMap(this.sortedMap.subMap(by, by2), this.sync);
        }

        @Override
        public Byte2LongSortedMap headMap(byte by) {
            return new SynchronizedSortedMap(this.sortedMap.headMap(by), this.sync);
        }

        @Override
        public Byte2LongSortedMap tailMap(byte by) {
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
        public Byte2LongSortedMap subMap(Byte by, Byte by2) {
            return new SynchronizedSortedMap(this.sortedMap.subMap(by, by2), this.sync);
        }

        @Override
        @Deprecated
        public Byte2LongSortedMap headMap(Byte by) {
            return new SynchronizedSortedMap(this.sortedMap.headMap(by), this.sync);
        }

        @Override
        @Deprecated
        public Byte2LongSortedMap tailMap(Byte by) {
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
        public ObjectSet byte2LongEntrySet() {
            return this.byte2LongEntrySet();
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
    extends Byte2LongMaps.Singleton
    implements Byte2LongSortedMap,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final ByteComparator comparator;

        protected Singleton(byte by, long l, ByteComparator byteComparator) {
            super(by, l);
            this.comparator = byteComparator;
        }

        protected Singleton(byte by, long l) {
            this(by, l, null);
        }

        final int compare(byte by, byte by2) {
            return this.comparator == null ? Byte.compare(by, by2) : this.comparator.compare(by, by2);
        }

        @Override
        public ByteComparator comparator() {
            return this.comparator;
        }

        @Override
        public ObjectSortedSet<Byte2LongMap.Entry> byte2LongEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.singleton(new AbstractByte2LongMap.BasicEntry(this.key, this.value), Byte2LongSortedMaps.entryComparator(this.comparator));
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Byte, Long>> entrySet() {
            return this.byte2LongEntrySet();
        }

        @Override
        public ByteSortedSet keySet() {
            if (this.keys == null) {
                this.keys = ByteSortedSets.singleton(this.key, this.comparator);
            }
            return (ByteSortedSet)this.keys;
        }

        @Override
        public Byte2LongSortedMap subMap(byte by, byte by2) {
            if (this.compare(by, this.key) <= 0 && this.compare(this.key, by2) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Byte2LongSortedMap headMap(byte by) {
            if (this.compare(this.key, by) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Byte2LongSortedMap tailMap(byte by) {
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
        public Byte2LongSortedMap headMap(Byte by) {
            return this.headMap((byte)by);
        }

        @Override
        @Deprecated
        public Byte2LongSortedMap tailMap(Byte by) {
            return this.tailMap((byte)by);
        }

        @Override
        @Deprecated
        public Byte2LongSortedMap subMap(Byte by, Byte by2) {
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
        public ObjectSet byte2LongEntrySet() {
            return this.byte2LongEntrySet();
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
    extends Byte2LongMaps.EmptyMap
    implements Byte2LongSortedMap,
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
        public ObjectSortedSet<Byte2LongMap.Entry> byte2LongEntrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Byte, Long>> entrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        public ByteSortedSet keySet() {
            return ByteSortedSets.EMPTY_SET;
        }

        @Override
        public Byte2LongSortedMap subMap(byte by, byte by2) {
            return EMPTY_MAP;
        }

        @Override
        public Byte2LongSortedMap headMap(byte by) {
            return EMPTY_MAP;
        }

        @Override
        public Byte2LongSortedMap tailMap(byte by) {
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
        public Byte2LongSortedMap headMap(Byte by) {
            return this.headMap((byte)by);
        }

        @Override
        @Deprecated
        public Byte2LongSortedMap tailMap(Byte by) {
            return this.tailMap((byte)by);
        }

        @Override
        @Deprecated
        public Byte2LongSortedMap subMap(Byte by, Byte by2) {
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
        public ObjectSet byte2LongEntrySet() {
            return this.byte2LongEntrySet();
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

