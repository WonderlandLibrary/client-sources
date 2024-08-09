/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.longs.AbstractLong2ShortMap;
import it.unimi.dsi.fastutil.longs.Long2ShortMap;
import it.unimi.dsi.fastutil.longs.Long2ShortMaps;
import it.unimi.dsi.fastutil.longs.Long2ShortSortedMap;
import it.unimi.dsi.fastutil.longs.LongComparator;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.longs.LongSortedSet;
import it.unimi.dsi.fastutil.longs.LongSortedSets;
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

public final class Long2ShortSortedMaps {
    public static final EmptySortedMap EMPTY_MAP = new EmptySortedMap();

    private Long2ShortSortedMaps() {
    }

    public static Comparator<? super Map.Entry<Long, ?>> entryComparator(LongComparator longComparator) {
        return (arg_0, arg_1) -> Long2ShortSortedMaps.lambda$entryComparator$0(longComparator, arg_0, arg_1);
    }

    public static ObjectBidirectionalIterator<Long2ShortMap.Entry> fastIterator(Long2ShortSortedMap long2ShortSortedMap) {
        ObjectSet objectSet = long2ShortSortedMap.long2ShortEntrySet();
        return objectSet instanceof Long2ShortSortedMap.FastSortedEntrySet ? ((Long2ShortSortedMap.FastSortedEntrySet)objectSet).fastIterator() : objectSet.iterator();
    }

    public static ObjectBidirectionalIterable<Long2ShortMap.Entry> fastIterable(Long2ShortSortedMap long2ShortSortedMap) {
        ObjectSet objectSet = long2ShortSortedMap.long2ShortEntrySet();
        return objectSet instanceof Long2ShortSortedMap.FastSortedEntrySet ? ((Long2ShortSortedMap.FastSortedEntrySet)objectSet)::fastIterator : objectSet;
    }

    public static Long2ShortSortedMap singleton(Long l, Short s) {
        return new Singleton(l, s);
    }

    public static Long2ShortSortedMap singleton(Long l, Short s, LongComparator longComparator) {
        return new Singleton(l, s, longComparator);
    }

    public static Long2ShortSortedMap singleton(long l, short s) {
        return new Singleton(l, s);
    }

    public static Long2ShortSortedMap singleton(long l, short s, LongComparator longComparator) {
        return new Singleton(l, s, longComparator);
    }

    public static Long2ShortSortedMap synchronize(Long2ShortSortedMap long2ShortSortedMap) {
        return new SynchronizedSortedMap(long2ShortSortedMap);
    }

    public static Long2ShortSortedMap synchronize(Long2ShortSortedMap long2ShortSortedMap, Object object) {
        return new SynchronizedSortedMap(long2ShortSortedMap, object);
    }

    public static Long2ShortSortedMap unmodifiable(Long2ShortSortedMap long2ShortSortedMap) {
        return new UnmodifiableSortedMap(long2ShortSortedMap);
    }

    private static int lambda$entryComparator$0(LongComparator longComparator, Map.Entry entry, Map.Entry entry2) {
        return longComparator.compare((long)((Long)entry.getKey()), (long)((Long)entry2.getKey()));
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableSortedMap
    extends Long2ShortMaps.UnmodifiableMap
    implements Long2ShortSortedMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Long2ShortSortedMap sortedMap;

        protected UnmodifiableSortedMap(Long2ShortSortedMap long2ShortSortedMap) {
            super(long2ShortSortedMap);
            this.sortedMap = long2ShortSortedMap;
        }

        @Override
        public LongComparator comparator() {
            return this.sortedMap.comparator();
        }

        @Override
        public ObjectSortedSet<Long2ShortMap.Entry> long2ShortEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.unmodifiable(this.sortedMap.long2ShortEntrySet());
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Long, Short>> entrySet() {
            return this.long2ShortEntrySet();
        }

        @Override
        public LongSortedSet keySet() {
            if (this.keys == null) {
                this.keys = LongSortedSets.unmodifiable(this.sortedMap.keySet());
            }
            return (LongSortedSet)this.keys;
        }

        @Override
        public Long2ShortSortedMap subMap(long l, long l2) {
            return new UnmodifiableSortedMap(this.sortedMap.subMap(l, l2));
        }

        @Override
        public Long2ShortSortedMap headMap(long l) {
            return new UnmodifiableSortedMap(this.sortedMap.headMap(l));
        }

        @Override
        public Long2ShortSortedMap tailMap(long l) {
            return new UnmodifiableSortedMap(this.sortedMap.tailMap(l));
        }

        @Override
        public long firstLongKey() {
            return this.sortedMap.firstLongKey();
        }

        @Override
        public long lastLongKey() {
            return this.sortedMap.lastLongKey();
        }

        @Override
        @Deprecated
        public Long firstKey() {
            return this.sortedMap.firstKey();
        }

        @Override
        @Deprecated
        public Long lastKey() {
            return this.sortedMap.lastKey();
        }

        @Override
        @Deprecated
        public Long2ShortSortedMap subMap(Long l, Long l2) {
            return new UnmodifiableSortedMap(this.sortedMap.subMap(l, l2));
        }

        @Override
        @Deprecated
        public Long2ShortSortedMap headMap(Long l) {
            return new UnmodifiableSortedMap(this.sortedMap.headMap(l));
        }

        @Override
        @Deprecated
        public Long2ShortSortedMap tailMap(Long l) {
            return new UnmodifiableSortedMap(this.sortedMap.tailMap(l));
        }

        @Override
        public LongSet keySet() {
            return this.keySet();
        }

        @Override
        @Deprecated
        public ObjectSet entrySet() {
            return this.entrySet();
        }

        @Override
        public ObjectSet long2ShortEntrySet() {
            return this.long2ShortEntrySet();
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
            return this.tailMap((Long)object);
        }

        @Override
        @Deprecated
        public SortedMap headMap(Object object) {
            return this.headMap((Long)object);
        }

        @Override
        @Deprecated
        public SortedMap subMap(Object object, Object object2) {
            return this.subMap((Long)object, (Long)object2);
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
    extends Long2ShortMaps.SynchronizedMap
    implements Long2ShortSortedMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Long2ShortSortedMap sortedMap;

        protected SynchronizedSortedMap(Long2ShortSortedMap long2ShortSortedMap, Object object) {
            super(long2ShortSortedMap, object);
            this.sortedMap = long2ShortSortedMap;
        }

        protected SynchronizedSortedMap(Long2ShortSortedMap long2ShortSortedMap) {
            super(long2ShortSortedMap);
            this.sortedMap = long2ShortSortedMap;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public LongComparator comparator() {
            Object object = this.sync;
            synchronized (object) {
                return this.sortedMap.comparator();
            }
        }

        @Override
        public ObjectSortedSet<Long2ShortMap.Entry> long2ShortEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.synchronize(this.sortedMap.long2ShortEntrySet(), this.sync);
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Long, Short>> entrySet() {
            return this.long2ShortEntrySet();
        }

        @Override
        public LongSortedSet keySet() {
            if (this.keys == null) {
                this.keys = LongSortedSets.synchronize(this.sortedMap.keySet(), this.sync);
            }
            return (LongSortedSet)this.keys;
        }

        @Override
        public Long2ShortSortedMap subMap(long l, long l2) {
            return new SynchronizedSortedMap(this.sortedMap.subMap(l, l2), this.sync);
        }

        @Override
        public Long2ShortSortedMap headMap(long l) {
            return new SynchronizedSortedMap(this.sortedMap.headMap(l), this.sync);
        }

        @Override
        public Long2ShortSortedMap tailMap(long l) {
            return new SynchronizedSortedMap(this.sortedMap.tailMap(l), this.sync);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long firstLongKey() {
            Object object = this.sync;
            synchronized (object) {
                return this.sortedMap.firstLongKey();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long lastLongKey() {
            Object object = this.sync;
            synchronized (object) {
                return this.sortedMap.lastLongKey();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Long firstKey() {
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
        public Long lastKey() {
            Object object = this.sync;
            synchronized (object) {
                return this.sortedMap.lastKey();
            }
        }

        @Override
        @Deprecated
        public Long2ShortSortedMap subMap(Long l, Long l2) {
            return new SynchronizedSortedMap(this.sortedMap.subMap(l, l2), this.sync);
        }

        @Override
        @Deprecated
        public Long2ShortSortedMap headMap(Long l) {
            return new SynchronizedSortedMap(this.sortedMap.headMap(l), this.sync);
        }

        @Override
        @Deprecated
        public Long2ShortSortedMap tailMap(Long l) {
            return new SynchronizedSortedMap(this.sortedMap.tailMap(l), this.sync);
        }

        @Override
        public LongSet keySet() {
            return this.keySet();
        }

        @Override
        @Deprecated
        public ObjectSet entrySet() {
            return this.entrySet();
        }

        @Override
        public ObjectSet long2ShortEntrySet() {
            return this.long2ShortEntrySet();
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
            return this.tailMap((Long)object);
        }

        @Override
        @Deprecated
        public SortedMap headMap(Object object) {
            return this.headMap((Long)object);
        }

        @Override
        @Deprecated
        public SortedMap subMap(Object object, Object object2) {
            return this.subMap((Long)object, (Long)object2);
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
    extends Long2ShortMaps.Singleton
    implements Long2ShortSortedMap,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final LongComparator comparator;

        protected Singleton(long l, short s, LongComparator longComparator) {
            super(l, s);
            this.comparator = longComparator;
        }

        protected Singleton(long l, short s) {
            this(l, s, null);
        }

        final int compare(long l, long l2) {
            return this.comparator == null ? Long.compare(l, l2) : this.comparator.compare(l, l2);
        }

        @Override
        public LongComparator comparator() {
            return this.comparator;
        }

        @Override
        public ObjectSortedSet<Long2ShortMap.Entry> long2ShortEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.singleton(new AbstractLong2ShortMap.BasicEntry(this.key, this.value), Long2ShortSortedMaps.entryComparator(this.comparator));
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Long, Short>> entrySet() {
            return this.long2ShortEntrySet();
        }

        @Override
        public LongSortedSet keySet() {
            if (this.keys == null) {
                this.keys = LongSortedSets.singleton(this.key, this.comparator);
            }
            return (LongSortedSet)this.keys;
        }

        @Override
        public Long2ShortSortedMap subMap(long l, long l2) {
            if (this.compare(l, this.key) <= 0 && this.compare(this.key, l2) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Long2ShortSortedMap headMap(long l) {
            if (this.compare(this.key, l) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Long2ShortSortedMap tailMap(long l) {
            if (this.compare(l, this.key) <= 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public long firstLongKey() {
            return this.key;
        }

        @Override
        public long lastLongKey() {
            return this.key;
        }

        @Override
        @Deprecated
        public Long2ShortSortedMap headMap(Long l) {
            return this.headMap((long)l);
        }

        @Override
        @Deprecated
        public Long2ShortSortedMap tailMap(Long l) {
            return this.tailMap((long)l);
        }

        @Override
        @Deprecated
        public Long2ShortSortedMap subMap(Long l, Long l2) {
            return this.subMap((long)l, (long)l2);
        }

        @Override
        @Deprecated
        public Long firstKey() {
            return this.firstLongKey();
        }

        @Override
        @Deprecated
        public Long lastKey() {
            return this.lastLongKey();
        }

        @Override
        public LongSet keySet() {
            return this.keySet();
        }

        @Override
        @Deprecated
        public ObjectSet entrySet() {
            return this.entrySet();
        }

        @Override
        public ObjectSet long2ShortEntrySet() {
            return this.long2ShortEntrySet();
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
            return this.tailMap((Long)object);
        }

        @Override
        @Deprecated
        public SortedMap headMap(Object object) {
            return this.headMap((Long)object);
        }

        @Override
        @Deprecated
        public SortedMap subMap(Object object, Object object2) {
            return this.subMap((Long)object, (Long)object2);
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
    extends Long2ShortMaps.EmptyMap
    implements Long2ShortSortedMap,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptySortedMap() {
        }

        @Override
        public LongComparator comparator() {
            return null;
        }

        @Override
        public ObjectSortedSet<Long2ShortMap.Entry> long2ShortEntrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Long, Short>> entrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        public LongSortedSet keySet() {
            return LongSortedSets.EMPTY_SET;
        }

        @Override
        public Long2ShortSortedMap subMap(long l, long l2) {
            return EMPTY_MAP;
        }

        @Override
        public Long2ShortSortedMap headMap(long l) {
            return EMPTY_MAP;
        }

        @Override
        public Long2ShortSortedMap tailMap(long l) {
            return EMPTY_MAP;
        }

        @Override
        public long firstLongKey() {
            throw new NoSuchElementException();
        }

        @Override
        public long lastLongKey() {
            throw new NoSuchElementException();
        }

        @Override
        @Deprecated
        public Long2ShortSortedMap headMap(Long l) {
            return this.headMap((long)l);
        }

        @Override
        @Deprecated
        public Long2ShortSortedMap tailMap(Long l) {
            return this.tailMap((long)l);
        }

        @Override
        @Deprecated
        public Long2ShortSortedMap subMap(Long l, Long l2) {
            return this.subMap((long)l, (long)l2);
        }

        @Override
        @Deprecated
        public Long firstKey() {
            return this.firstLongKey();
        }

        @Override
        @Deprecated
        public Long lastKey() {
            return this.lastLongKey();
        }

        @Override
        public LongSet keySet() {
            return this.keySet();
        }

        @Override
        public ObjectSet long2ShortEntrySet() {
            return this.long2ShortEntrySet();
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
            return this.tailMap((Long)object);
        }

        @Override
        @Deprecated
        public SortedMap headMap(Object object) {
            return this.headMap((Long)object);
        }

        @Override
        @Deprecated
        public SortedMap subMap(Object object, Object object2) {
            return this.subMap((Long)object, (Long)object2);
        }

        @Override
        public Comparator comparator() {
            return this.comparator();
        }
    }
}

