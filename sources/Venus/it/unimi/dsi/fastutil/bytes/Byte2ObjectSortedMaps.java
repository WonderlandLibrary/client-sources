/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.AbstractByte2ObjectMap;
import it.unimi.dsi.fastutil.bytes.Byte2ObjectMap;
import it.unimi.dsi.fastutil.bytes.Byte2ObjectMaps;
import it.unimi.dsi.fastutil.bytes.Byte2ObjectSortedMap;
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

public final class Byte2ObjectSortedMaps {
    public static final EmptySortedMap EMPTY_MAP = new EmptySortedMap();

    private Byte2ObjectSortedMaps() {
    }

    public static Comparator<? super Map.Entry<Byte, ?>> entryComparator(ByteComparator byteComparator) {
        return (arg_0, arg_1) -> Byte2ObjectSortedMaps.lambda$entryComparator$0(byteComparator, arg_0, arg_1);
    }

    public static <V> ObjectBidirectionalIterator<Byte2ObjectMap.Entry<V>> fastIterator(Byte2ObjectSortedMap<V> byte2ObjectSortedMap) {
        ObjectSet objectSet = byte2ObjectSortedMap.byte2ObjectEntrySet();
        return objectSet instanceof Byte2ObjectSortedMap.FastSortedEntrySet ? ((Byte2ObjectSortedMap.FastSortedEntrySet)objectSet).fastIterator() : objectSet.iterator();
    }

    public static <V> ObjectBidirectionalIterable<Byte2ObjectMap.Entry<V>> fastIterable(Byte2ObjectSortedMap<V> byte2ObjectSortedMap) {
        ObjectSet objectSet = byte2ObjectSortedMap.byte2ObjectEntrySet();
        return objectSet instanceof Byte2ObjectSortedMap.FastSortedEntrySet ? ((Byte2ObjectSortedMap.FastSortedEntrySet)objectSet)::fastIterator : objectSet;
    }

    public static <V> Byte2ObjectSortedMap<V> emptyMap() {
        return EMPTY_MAP;
    }

    public static <V> Byte2ObjectSortedMap<V> singleton(Byte by, V v) {
        return new Singleton<V>(by, v);
    }

    public static <V> Byte2ObjectSortedMap<V> singleton(Byte by, V v, ByteComparator byteComparator) {
        return new Singleton<V>(by, v, byteComparator);
    }

    public static <V> Byte2ObjectSortedMap<V> singleton(byte by, V v) {
        return new Singleton<V>(by, v);
    }

    public static <V> Byte2ObjectSortedMap<V> singleton(byte by, V v, ByteComparator byteComparator) {
        return new Singleton<V>(by, v, byteComparator);
    }

    public static <V> Byte2ObjectSortedMap<V> synchronize(Byte2ObjectSortedMap<V> byte2ObjectSortedMap) {
        return new SynchronizedSortedMap<V>(byte2ObjectSortedMap);
    }

    public static <V> Byte2ObjectSortedMap<V> synchronize(Byte2ObjectSortedMap<V> byte2ObjectSortedMap, Object object) {
        return new SynchronizedSortedMap<V>(byte2ObjectSortedMap, object);
    }

    public static <V> Byte2ObjectSortedMap<V> unmodifiable(Byte2ObjectSortedMap<V> byte2ObjectSortedMap) {
        return new UnmodifiableSortedMap<V>(byte2ObjectSortedMap);
    }

    private static int lambda$entryComparator$0(ByteComparator byteComparator, Map.Entry entry, Map.Entry entry2) {
        return byteComparator.compare((byte)((Byte)entry.getKey()), (byte)((Byte)entry2.getKey()));
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableSortedMap<V>
    extends Byte2ObjectMaps.UnmodifiableMap<V>
    implements Byte2ObjectSortedMap<V>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Byte2ObjectSortedMap<V> sortedMap;

        protected UnmodifiableSortedMap(Byte2ObjectSortedMap<V> byte2ObjectSortedMap) {
            super(byte2ObjectSortedMap);
            this.sortedMap = byte2ObjectSortedMap;
        }

        @Override
        public ByteComparator comparator() {
            return this.sortedMap.comparator();
        }

        @Override
        public ObjectSortedSet<Byte2ObjectMap.Entry<V>> byte2ObjectEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.unmodifiable(this.sortedMap.byte2ObjectEntrySet());
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Byte, V>> entrySet() {
            return this.byte2ObjectEntrySet();
        }

        @Override
        public ByteSortedSet keySet() {
            if (this.keys == null) {
                this.keys = ByteSortedSets.unmodifiable(this.sortedMap.keySet());
            }
            return (ByteSortedSet)this.keys;
        }

        @Override
        public Byte2ObjectSortedMap<V> subMap(byte by, byte by2) {
            return new UnmodifiableSortedMap<V>(this.sortedMap.subMap(by, by2));
        }

        @Override
        public Byte2ObjectSortedMap<V> headMap(byte by) {
            return new UnmodifiableSortedMap<V>(this.sortedMap.headMap(by));
        }

        @Override
        public Byte2ObjectSortedMap<V> tailMap(byte by) {
            return new UnmodifiableSortedMap<V>(this.sortedMap.tailMap(by));
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
        public Byte2ObjectSortedMap<V> subMap(Byte by, Byte by2) {
            return new UnmodifiableSortedMap<V>(this.sortedMap.subMap(by, by2));
        }

        @Override
        @Deprecated
        public Byte2ObjectSortedMap<V> headMap(Byte by) {
            return new UnmodifiableSortedMap<V>(this.sortedMap.headMap(by));
        }

        @Override
        @Deprecated
        public Byte2ObjectSortedMap<V> tailMap(Byte by) {
            return new UnmodifiableSortedMap<V>(this.sortedMap.tailMap(by));
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
        public ObjectSet byte2ObjectEntrySet() {
            return this.byte2ObjectEntrySet();
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
    public static class SynchronizedSortedMap<V>
    extends Byte2ObjectMaps.SynchronizedMap<V>
    implements Byte2ObjectSortedMap<V>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Byte2ObjectSortedMap<V> sortedMap;

        protected SynchronizedSortedMap(Byte2ObjectSortedMap<V> byte2ObjectSortedMap, Object object) {
            super(byte2ObjectSortedMap, object);
            this.sortedMap = byte2ObjectSortedMap;
        }

        protected SynchronizedSortedMap(Byte2ObjectSortedMap<V> byte2ObjectSortedMap) {
            super(byte2ObjectSortedMap);
            this.sortedMap = byte2ObjectSortedMap;
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
        public ObjectSortedSet<Byte2ObjectMap.Entry<V>> byte2ObjectEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.synchronize(this.sortedMap.byte2ObjectEntrySet(), this.sync);
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Byte, V>> entrySet() {
            return this.byte2ObjectEntrySet();
        }

        @Override
        public ByteSortedSet keySet() {
            if (this.keys == null) {
                this.keys = ByteSortedSets.synchronize(this.sortedMap.keySet(), this.sync);
            }
            return (ByteSortedSet)this.keys;
        }

        @Override
        public Byte2ObjectSortedMap<V> subMap(byte by, byte by2) {
            return new SynchronizedSortedMap<V>(this.sortedMap.subMap(by, by2), this.sync);
        }

        @Override
        public Byte2ObjectSortedMap<V> headMap(byte by) {
            return new SynchronizedSortedMap<V>(this.sortedMap.headMap(by), this.sync);
        }

        @Override
        public Byte2ObjectSortedMap<V> tailMap(byte by) {
            return new SynchronizedSortedMap<V>(this.sortedMap.tailMap(by), this.sync);
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
        public Byte2ObjectSortedMap<V> subMap(Byte by, Byte by2) {
            return new SynchronizedSortedMap<V>(this.sortedMap.subMap(by, by2), this.sync);
        }

        @Override
        @Deprecated
        public Byte2ObjectSortedMap<V> headMap(Byte by) {
            return new SynchronizedSortedMap<V>(this.sortedMap.headMap(by), this.sync);
        }

        @Override
        @Deprecated
        public Byte2ObjectSortedMap<V> tailMap(Byte by) {
            return new SynchronizedSortedMap<V>(this.sortedMap.tailMap(by), this.sync);
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
        public ObjectSet byte2ObjectEntrySet() {
            return this.byte2ObjectEntrySet();
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
    public static class Singleton<V>
    extends Byte2ObjectMaps.Singleton<V>
    implements Byte2ObjectSortedMap<V>,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final ByteComparator comparator;

        protected Singleton(byte by, V v, ByteComparator byteComparator) {
            super(by, v);
            this.comparator = byteComparator;
        }

        protected Singleton(byte by, V v) {
            this(by, v, null);
        }

        final int compare(byte by, byte by2) {
            return this.comparator == null ? Byte.compare(by, by2) : this.comparator.compare(by, by2);
        }

        @Override
        public ByteComparator comparator() {
            return this.comparator;
        }

        @Override
        public ObjectSortedSet<Byte2ObjectMap.Entry<V>> byte2ObjectEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.singleton(new AbstractByte2ObjectMap.BasicEntry<Object>(this.key, this.value), Byte2ObjectSortedMaps.entryComparator(this.comparator));
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Byte, V>> entrySet() {
            return this.byte2ObjectEntrySet();
        }

        @Override
        public ByteSortedSet keySet() {
            if (this.keys == null) {
                this.keys = ByteSortedSets.singleton(this.key, this.comparator);
            }
            return (ByteSortedSet)this.keys;
        }

        @Override
        public Byte2ObjectSortedMap<V> subMap(byte by, byte by2) {
            if (this.compare(by, this.key) <= 0 && this.compare(this.key, by2) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Byte2ObjectSortedMap<V> headMap(byte by) {
            if (this.compare(this.key, by) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Byte2ObjectSortedMap<V> tailMap(byte by) {
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
        public Byte2ObjectSortedMap<V> headMap(Byte by) {
            return this.headMap((byte)by);
        }

        @Override
        @Deprecated
        public Byte2ObjectSortedMap<V> tailMap(Byte by) {
            return this.tailMap((byte)by);
        }

        @Override
        @Deprecated
        public Byte2ObjectSortedMap<V> subMap(Byte by, Byte by2) {
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
        public ObjectSet byte2ObjectEntrySet() {
            return this.byte2ObjectEntrySet();
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
    public static class EmptySortedMap<V>
    extends Byte2ObjectMaps.EmptyMap<V>
    implements Byte2ObjectSortedMap<V>,
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
        public ObjectSortedSet<Byte2ObjectMap.Entry<V>> byte2ObjectEntrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Byte, V>> entrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        public ByteSortedSet keySet() {
            return ByteSortedSets.EMPTY_SET;
        }

        @Override
        public Byte2ObjectSortedMap<V> subMap(byte by, byte by2) {
            return EMPTY_MAP;
        }

        @Override
        public Byte2ObjectSortedMap<V> headMap(byte by) {
            return EMPTY_MAP;
        }

        @Override
        public Byte2ObjectSortedMap<V> tailMap(byte by) {
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
        public Byte2ObjectSortedMap<V> headMap(Byte by) {
            return this.headMap((byte)by);
        }

        @Override
        @Deprecated
        public Byte2ObjectSortedMap<V> tailMap(Byte by) {
            return this.tailMap((byte)by);
        }

        @Override
        @Deprecated
        public Byte2ObjectSortedMap<V> subMap(Byte by, Byte by2) {
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
        public ObjectSet byte2ObjectEntrySet() {
            return this.byte2ObjectEntrySet();
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

