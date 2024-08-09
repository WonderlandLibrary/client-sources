/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.longs.AbstractLong2BooleanMap;
import it.unimi.dsi.fastutil.longs.Long2BooleanMap;
import it.unimi.dsi.fastutil.longs.Long2BooleanMaps;
import it.unimi.dsi.fastutil.longs.Long2BooleanSortedMap;
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

public final class Long2BooleanSortedMaps {
    public static final EmptySortedMap EMPTY_MAP = new EmptySortedMap();

    private Long2BooleanSortedMaps() {
    }

    public static Comparator<? super Map.Entry<Long, ?>> entryComparator(LongComparator longComparator) {
        return (arg_0, arg_1) -> Long2BooleanSortedMaps.lambda$entryComparator$0(longComparator, arg_0, arg_1);
    }

    public static ObjectBidirectionalIterator<Long2BooleanMap.Entry> fastIterator(Long2BooleanSortedMap long2BooleanSortedMap) {
        ObjectSet objectSet = long2BooleanSortedMap.long2BooleanEntrySet();
        return objectSet instanceof Long2BooleanSortedMap.FastSortedEntrySet ? ((Long2BooleanSortedMap.FastSortedEntrySet)objectSet).fastIterator() : objectSet.iterator();
    }

    public static ObjectBidirectionalIterable<Long2BooleanMap.Entry> fastIterable(Long2BooleanSortedMap long2BooleanSortedMap) {
        ObjectSet objectSet = long2BooleanSortedMap.long2BooleanEntrySet();
        return objectSet instanceof Long2BooleanSortedMap.FastSortedEntrySet ? ((Long2BooleanSortedMap.FastSortedEntrySet)objectSet)::fastIterator : objectSet;
    }

    public static Long2BooleanSortedMap singleton(Long l, Boolean bl) {
        return new Singleton(l, bl);
    }

    public static Long2BooleanSortedMap singleton(Long l, Boolean bl, LongComparator longComparator) {
        return new Singleton(l, bl, longComparator);
    }

    public static Long2BooleanSortedMap singleton(long l, boolean bl) {
        return new Singleton(l, bl);
    }

    public static Long2BooleanSortedMap singleton(long l, boolean bl, LongComparator longComparator) {
        return new Singleton(l, bl, longComparator);
    }

    public static Long2BooleanSortedMap synchronize(Long2BooleanSortedMap long2BooleanSortedMap) {
        return new SynchronizedSortedMap(long2BooleanSortedMap);
    }

    public static Long2BooleanSortedMap synchronize(Long2BooleanSortedMap long2BooleanSortedMap, Object object) {
        return new SynchronizedSortedMap(long2BooleanSortedMap, object);
    }

    public static Long2BooleanSortedMap unmodifiable(Long2BooleanSortedMap long2BooleanSortedMap) {
        return new UnmodifiableSortedMap(long2BooleanSortedMap);
    }

    private static int lambda$entryComparator$0(LongComparator longComparator, Map.Entry entry, Map.Entry entry2) {
        return longComparator.compare((long)((Long)entry.getKey()), (long)((Long)entry2.getKey()));
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableSortedMap
    extends Long2BooleanMaps.UnmodifiableMap
    implements Long2BooleanSortedMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Long2BooleanSortedMap sortedMap;

        protected UnmodifiableSortedMap(Long2BooleanSortedMap long2BooleanSortedMap) {
            super(long2BooleanSortedMap);
            this.sortedMap = long2BooleanSortedMap;
        }

        @Override
        public LongComparator comparator() {
            return this.sortedMap.comparator();
        }

        @Override
        public ObjectSortedSet<Long2BooleanMap.Entry> long2BooleanEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.unmodifiable(this.sortedMap.long2BooleanEntrySet());
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Long, Boolean>> entrySet() {
            return this.long2BooleanEntrySet();
        }

        @Override
        public LongSortedSet keySet() {
            if (this.keys == null) {
                this.keys = LongSortedSets.unmodifiable(this.sortedMap.keySet());
            }
            return (LongSortedSet)this.keys;
        }

        @Override
        public Long2BooleanSortedMap subMap(long l, long l2) {
            return new UnmodifiableSortedMap(this.sortedMap.subMap(l, l2));
        }

        @Override
        public Long2BooleanSortedMap headMap(long l) {
            return new UnmodifiableSortedMap(this.sortedMap.headMap(l));
        }

        @Override
        public Long2BooleanSortedMap tailMap(long l) {
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
        public Long2BooleanSortedMap subMap(Long l, Long l2) {
            return new UnmodifiableSortedMap(this.sortedMap.subMap(l, l2));
        }

        @Override
        @Deprecated
        public Long2BooleanSortedMap headMap(Long l) {
            return new UnmodifiableSortedMap(this.sortedMap.headMap(l));
        }

        @Override
        @Deprecated
        public Long2BooleanSortedMap tailMap(Long l) {
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
        public ObjectSet long2BooleanEntrySet() {
            return this.long2BooleanEntrySet();
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
    extends Long2BooleanMaps.SynchronizedMap
    implements Long2BooleanSortedMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Long2BooleanSortedMap sortedMap;

        protected SynchronizedSortedMap(Long2BooleanSortedMap long2BooleanSortedMap, Object object) {
            super(long2BooleanSortedMap, object);
            this.sortedMap = long2BooleanSortedMap;
        }

        protected SynchronizedSortedMap(Long2BooleanSortedMap long2BooleanSortedMap) {
            super(long2BooleanSortedMap);
            this.sortedMap = long2BooleanSortedMap;
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
        public ObjectSortedSet<Long2BooleanMap.Entry> long2BooleanEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.synchronize(this.sortedMap.long2BooleanEntrySet(), this.sync);
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Long, Boolean>> entrySet() {
            return this.long2BooleanEntrySet();
        }

        @Override
        public LongSortedSet keySet() {
            if (this.keys == null) {
                this.keys = LongSortedSets.synchronize(this.sortedMap.keySet(), this.sync);
            }
            return (LongSortedSet)this.keys;
        }

        @Override
        public Long2BooleanSortedMap subMap(long l, long l2) {
            return new SynchronizedSortedMap(this.sortedMap.subMap(l, l2), this.sync);
        }

        @Override
        public Long2BooleanSortedMap headMap(long l) {
            return new SynchronizedSortedMap(this.sortedMap.headMap(l), this.sync);
        }

        @Override
        public Long2BooleanSortedMap tailMap(long l) {
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
        public Long2BooleanSortedMap subMap(Long l, Long l2) {
            return new SynchronizedSortedMap(this.sortedMap.subMap(l, l2), this.sync);
        }

        @Override
        @Deprecated
        public Long2BooleanSortedMap headMap(Long l) {
            return new SynchronizedSortedMap(this.sortedMap.headMap(l), this.sync);
        }

        @Override
        @Deprecated
        public Long2BooleanSortedMap tailMap(Long l) {
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
        public ObjectSet long2BooleanEntrySet() {
            return this.long2BooleanEntrySet();
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
    extends Long2BooleanMaps.Singleton
    implements Long2BooleanSortedMap,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final LongComparator comparator;

        protected Singleton(long l, boolean bl, LongComparator longComparator) {
            super(l, bl);
            this.comparator = longComparator;
        }

        protected Singleton(long l, boolean bl) {
            this(l, bl, null);
        }

        final int compare(long l, long l2) {
            return this.comparator == null ? Long.compare(l, l2) : this.comparator.compare(l, l2);
        }

        @Override
        public LongComparator comparator() {
            return this.comparator;
        }

        @Override
        public ObjectSortedSet<Long2BooleanMap.Entry> long2BooleanEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.singleton(new AbstractLong2BooleanMap.BasicEntry(this.key, this.value), Long2BooleanSortedMaps.entryComparator(this.comparator));
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Long, Boolean>> entrySet() {
            return this.long2BooleanEntrySet();
        }

        @Override
        public LongSortedSet keySet() {
            if (this.keys == null) {
                this.keys = LongSortedSets.singleton(this.key, this.comparator);
            }
            return (LongSortedSet)this.keys;
        }

        @Override
        public Long2BooleanSortedMap subMap(long l, long l2) {
            if (this.compare(l, this.key) <= 0 && this.compare(this.key, l2) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Long2BooleanSortedMap headMap(long l) {
            if (this.compare(this.key, l) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Long2BooleanSortedMap tailMap(long l) {
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
        public Long2BooleanSortedMap headMap(Long l) {
            return this.headMap((long)l);
        }

        @Override
        @Deprecated
        public Long2BooleanSortedMap tailMap(Long l) {
            return this.tailMap((long)l);
        }

        @Override
        @Deprecated
        public Long2BooleanSortedMap subMap(Long l, Long l2) {
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
        public ObjectSet long2BooleanEntrySet() {
            return this.long2BooleanEntrySet();
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
    extends Long2BooleanMaps.EmptyMap
    implements Long2BooleanSortedMap,
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
        public ObjectSortedSet<Long2BooleanMap.Entry> long2BooleanEntrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Long, Boolean>> entrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        public LongSortedSet keySet() {
            return LongSortedSets.EMPTY_SET;
        }

        @Override
        public Long2BooleanSortedMap subMap(long l, long l2) {
            return EMPTY_MAP;
        }

        @Override
        public Long2BooleanSortedMap headMap(long l) {
            return EMPTY_MAP;
        }

        @Override
        public Long2BooleanSortedMap tailMap(long l) {
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
        public Long2BooleanSortedMap headMap(Long l) {
            return this.headMap((long)l);
        }

        @Override
        @Deprecated
        public Long2BooleanSortedMap tailMap(Long l) {
            return this.tailMap((long)l);
        }

        @Override
        @Deprecated
        public Long2BooleanSortedMap subMap(Long l, Long l2) {
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
        public ObjectSet long2BooleanEntrySet() {
            return this.long2BooleanEntrySet();
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

