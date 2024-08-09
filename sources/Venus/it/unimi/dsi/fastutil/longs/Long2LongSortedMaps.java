/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.longs.AbstractLong2LongMap;
import it.unimi.dsi.fastutil.longs.Long2LongMap;
import it.unimi.dsi.fastutil.longs.Long2LongMaps;
import it.unimi.dsi.fastutil.longs.Long2LongSortedMap;
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

public final class Long2LongSortedMaps {
    public static final EmptySortedMap EMPTY_MAP = new EmptySortedMap();

    private Long2LongSortedMaps() {
    }

    public static Comparator<? super Map.Entry<Long, ?>> entryComparator(LongComparator longComparator) {
        return (arg_0, arg_1) -> Long2LongSortedMaps.lambda$entryComparator$0(longComparator, arg_0, arg_1);
    }

    public static ObjectBidirectionalIterator<Long2LongMap.Entry> fastIterator(Long2LongSortedMap long2LongSortedMap) {
        ObjectSet objectSet = long2LongSortedMap.long2LongEntrySet();
        return objectSet instanceof Long2LongSortedMap.FastSortedEntrySet ? ((Long2LongSortedMap.FastSortedEntrySet)objectSet).fastIterator() : objectSet.iterator();
    }

    public static ObjectBidirectionalIterable<Long2LongMap.Entry> fastIterable(Long2LongSortedMap long2LongSortedMap) {
        ObjectSet objectSet = long2LongSortedMap.long2LongEntrySet();
        return objectSet instanceof Long2LongSortedMap.FastSortedEntrySet ? ((Long2LongSortedMap.FastSortedEntrySet)objectSet)::fastIterator : objectSet;
    }

    public static Long2LongSortedMap singleton(Long l, Long l2) {
        return new Singleton(l, l2);
    }

    public static Long2LongSortedMap singleton(Long l, Long l2, LongComparator longComparator) {
        return new Singleton(l, l2, longComparator);
    }

    public static Long2LongSortedMap singleton(long l, long l2) {
        return new Singleton(l, l2);
    }

    public static Long2LongSortedMap singleton(long l, long l2, LongComparator longComparator) {
        return new Singleton(l, l2, longComparator);
    }

    public static Long2LongSortedMap synchronize(Long2LongSortedMap long2LongSortedMap) {
        return new SynchronizedSortedMap(long2LongSortedMap);
    }

    public static Long2LongSortedMap synchronize(Long2LongSortedMap long2LongSortedMap, Object object) {
        return new SynchronizedSortedMap(long2LongSortedMap, object);
    }

    public static Long2LongSortedMap unmodifiable(Long2LongSortedMap long2LongSortedMap) {
        return new UnmodifiableSortedMap(long2LongSortedMap);
    }

    private static int lambda$entryComparator$0(LongComparator longComparator, Map.Entry entry, Map.Entry entry2) {
        return longComparator.compare((long)((Long)entry.getKey()), (long)((Long)entry2.getKey()));
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableSortedMap
    extends Long2LongMaps.UnmodifiableMap
    implements Long2LongSortedMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Long2LongSortedMap sortedMap;

        protected UnmodifiableSortedMap(Long2LongSortedMap long2LongSortedMap) {
            super(long2LongSortedMap);
            this.sortedMap = long2LongSortedMap;
        }

        @Override
        public LongComparator comparator() {
            return this.sortedMap.comparator();
        }

        @Override
        public ObjectSortedSet<Long2LongMap.Entry> long2LongEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.unmodifiable(this.sortedMap.long2LongEntrySet());
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Long, Long>> entrySet() {
            return this.long2LongEntrySet();
        }

        @Override
        public LongSortedSet keySet() {
            if (this.keys == null) {
                this.keys = LongSortedSets.unmodifiable(this.sortedMap.keySet());
            }
            return (LongSortedSet)this.keys;
        }

        @Override
        public Long2LongSortedMap subMap(long l, long l2) {
            return new UnmodifiableSortedMap(this.sortedMap.subMap(l, l2));
        }

        @Override
        public Long2LongSortedMap headMap(long l) {
            return new UnmodifiableSortedMap(this.sortedMap.headMap(l));
        }

        @Override
        public Long2LongSortedMap tailMap(long l) {
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
        public Long2LongSortedMap subMap(Long l, Long l2) {
            return new UnmodifiableSortedMap(this.sortedMap.subMap(l, l2));
        }

        @Override
        @Deprecated
        public Long2LongSortedMap headMap(Long l) {
            return new UnmodifiableSortedMap(this.sortedMap.headMap(l));
        }

        @Override
        @Deprecated
        public Long2LongSortedMap tailMap(Long l) {
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
        public ObjectSet long2LongEntrySet() {
            return this.long2LongEntrySet();
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
    extends Long2LongMaps.SynchronizedMap
    implements Long2LongSortedMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Long2LongSortedMap sortedMap;

        protected SynchronizedSortedMap(Long2LongSortedMap long2LongSortedMap, Object object) {
            super(long2LongSortedMap, object);
            this.sortedMap = long2LongSortedMap;
        }

        protected SynchronizedSortedMap(Long2LongSortedMap long2LongSortedMap) {
            super(long2LongSortedMap);
            this.sortedMap = long2LongSortedMap;
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
        public ObjectSortedSet<Long2LongMap.Entry> long2LongEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.synchronize(this.sortedMap.long2LongEntrySet(), this.sync);
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Long, Long>> entrySet() {
            return this.long2LongEntrySet();
        }

        @Override
        public LongSortedSet keySet() {
            if (this.keys == null) {
                this.keys = LongSortedSets.synchronize(this.sortedMap.keySet(), this.sync);
            }
            return (LongSortedSet)this.keys;
        }

        @Override
        public Long2LongSortedMap subMap(long l, long l2) {
            return new SynchronizedSortedMap(this.sortedMap.subMap(l, l2), this.sync);
        }

        @Override
        public Long2LongSortedMap headMap(long l) {
            return new SynchronizedSortedMap(this.sortedMap.headMap(l), this.sync);
        }

        @Override
        public Long2LongSortedMap tailMap(long l) {
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
        public Long2LongSortedMap subMap(Long l, Long l2) {
            return new SynchronizedSortedMap(this.sortedMap.subMap(l, l2), this.sync);
        }

        @Override
        @Deprecated
        public Long2LongSortedMap headMap(Long l) {
            return new SynchronizedSortedMap(this.sortedMap.headMap(l), this.sync);
        }

        @Override
        @Deprecated
        public Long2LongSortedMap tailMap(Long l) {
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
        public ObjectSet long2LongEntrySet() {
            return this.long2LongEntrySet();
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
    extends Long2LongMaps.Singleton
    implements Long2LongSortedMap,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final LongComparator comparator;

        protected Singleton(long l, long l2, LongComparator longComparator) {
            super(l, l2);
            this.comparator = longComparator;
        }

        protected Singleton(long l, long l2) {
            this(l, l2, null);
        }

        final int compare(long l, long l2) {
            return this.comparator == null ? Long.compare(l, l2) : this.comparator.compare(l, l2);
        }

        @Override
        public LongComparator comparator() {
            return this.comparator;
        }

        @Override
        public ObjectSortedSet<Long2LongMap.Entry> long2LongEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.singleton(new AbstractLong2LongMap.BasicEntry(this.key, this.value), Long2LongSortedMaps.entryComparator(this.comparator));
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Long, Long>> entrySet() {
            return this.long2LongEntrySet();
        }

        @Override
        public LongSortedSet keySet() {
            if (this.keys == null) {
                this.keys = LongSortedSets.singleton(this.key, this.comparator);
            }
            return (LongSortedSet)this.keys;
        }

        @Override
        public Long2LongSortedMap subMap(long l, long l2) {
            if (this.compare(l, this.key) <= 0 && this.compare(this.key, l2) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Long2LongSortedMap headMap(long l) {
            if (this.compare(this.key, l) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Long2LongSortedMap tailMap(long l) {
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
        public Long2LongSortedMap headMap(Long l) {
            return this.headMap((long)l);
        }

        @Override
        @Deprecated
        public Long2LongSortedMap tailMap(Long l) {
            return this.tailMap((long)l);
        }

        @Override
        @Deprecated
        public Long2LongSortedMap subMap(Long l, Long l2) {
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
        public ObjectSet long2LongEntrySet() {
            return this.long2LongEntrySet();
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
    extends Long2LongMaps.EmptyMap
    implements Long2LongSortedMap,
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
        public ObjectSortedSet<Long2LongMap.Entry> long2LongEntrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Long, Long>> entrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        public LongSortedSet keySet() {
            return LongSortedSets.EMPTY_SET;
        }

        @Override
        public Long2LongSortedMap subMap(long l, long l2) {
            return EMPTY_MAP;
        }

        @Override
        public Long2LongSortedMap headMap(long l) {
            return EMPTY_MAP;
        }

        @Override
        public Long2LongSortedMap tailMap(long l) {
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
        public Long2LongSortedMap headMap(Long l) {
            return this.headMap((long)l);
        }

        @Override
        @Deprecated
        public Long2LongSortedMap tailMap(Long l) {
            return this.tailMap((long)l);
        }

        @Override
        @Deprecated
        public Long2LongSortedMap subMap(Long l, Long l2) {
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
        public ObjectSet long2LongEntrySet() {
            return this.long2LongEntrySet();
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

