/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.longs.AbstractLong2FloatMap;
import it.unimi.dsi.fastutil.longs.Long2FloatMap;
import it.unimi.dsi.fastutil.longs.Long2FloatMaps;
import it.unimi.dsi.fastutil.longs.Long2FloatSortedMap;
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

public final class Long2FloatSortedMaps {
    public static final EmptySortedMap EMPTY_MAP = new EmptySortedMap();

    private Long2FloatSortedMaps() {
    }

    public static Comparator<? super Map.Entry<Long, ?>> entryComparator(LongComparator longComparator) {
        return (arg_0, arg_1) -> Long2FloatSortedMaps.lambda$entryComparator$0(longComparator, arg_0, arg_1);
    }

    public static ObjectBidirectionalIterator<Long2FloatMap.Entry> fastIterator(Long2FloatSortedMap long2FloatSortedMap) {
        ObjectSet objectSet = long2FloatSortedMap.long2FloatEntrySet();
        return objectSet instanceof Long2FloatSortedMap.FastSortedEntrySet ? ((Long2FloatSortedMap.FastSortedEntrySet)objectSet).fastIterator() : objectSet.iterator();
    }

    public static ObjectBidirectionalIterable<Long2FloatMap.Entry> fastIterable(Long2FloatSortedMap long2FloatSortedMap) {
        ObjectSet objectSet = long2FloatSortedMap.long2FloatEntrySet();
        return objectSet instanceof Long2FloatSortedMap.FastSortedEntrySet ? ((Long2FloatSortedMap.FastSortedEntrySet)objectSet)::fastIterator : objectSet;
    }

    public static Long2FloatSortedMap singleton(Long l, Float f) {
        return new Singleton(l, f.floatValue());
    }

    public static Long2FloatSortedMap singleton(Long l, Float f, LongComparator longComparator) {
        return new Singleton(l, f.floatValue(), longComparator);
    }

    public static Long2FloatSortedMap singleton(long l, float f) {
        return new Singleton(l, f);
    }

    public static Long2FloatSortedMap singleton(long l, float f, LongComparator longComparator) {
        return new Singleton(l, f, longComparator);
    }

    public static Long2FloatSortedMap synchronize(Long2FloatSortedMap long2FloatSortedMap) {
        return new SynchronizedSortedMap(long2FloatSortedMap);
    }

    public static Long2FloatSortedMap synchronize(Long2FloatSortedMap long2FloatSortedMap, Object object) {
        return new SynchronizedSortedMap(long2FloatSortedMap, object);
    }

    public static Long2FloatSortedMap unmodifiable(Long2FloatSortedMap long2FloatSortedMap) {
        return new UnmodifiableSortedMap(long2FloatSortedMap);
    }

    private static int lambda$entryComparator$0(LongComparator longComparator, Map.Entry entry, Map.Entry entry2) {
        return longComparator.compare((long)((Long)entry.getKey()), (long)((Long)entry2.getKey()));
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableSortedMap
    extends Long2FloatMaps.UnmodifiableMap
    implements Long2FloatSortedMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Long2FloatSortedMap sortedMap;

        protected UnmodifiableSortedMap(Long2FloatSortedMap long2FloatSortedMap) {
            super(long2FloatSortedMap);
            this.sortedMap = long2FloatSortedMap;
        }

        @Override
        public LongComparator comparator() {
            return this.sortedMap.comparator();
        }

        @Override
        public ObjectSortedSet<Long2FloatMap.Entry> long2FloatEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.unmodifiable(this.sortedMap.long2FloatEntrySet());
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Long, Float>> entrySet() {
            return this.long2FloatEntrySet();
        }

        @Override
        public LongSortedSet keySet() {
            if (this.keys == null) {
                this.keys = LongSortedSets.unmodifiable(this.sortedMap.keySet());
            }
            return (LongSortedSet)this.keys;
        }

        @Override
        public Long2FloatSortedMap subMap(long l, long l2) {
            return new UnmodifiableSortedMap(this.sortedMap.subMap(l, l2));
        }

        @Override
        public Long2FloatSortedMap headMap(long l) {
            return new UnmodifiableSortedMap(this.sortedMap.headMap(l));
        }

        @Override
        public Long2FloatSortedMap tailMap(long l) {
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
        public Long2FloatSortedMap subMap(Long l, Long l2) {
            return new UnmodifiableSortedMap(this.sortedMap.subMap(l, l2));
        }

        @Override
        @Deprecated
        public Long2FloatSortedMap headMap(Long l) {
            return new UnmodifiableSortedMap(this.sortedMap.headMap(l));
        }

        @Override
        @Deprecated
        public Long2FloatSortedMap tailMap(Long l) {
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
        public ObjectSet long2FloatEntrySet() {
            return this.long2FloatEntrySet();
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
    extends Long2FloatMaps.SynchronizedMap
    implements Long2FloatSortedMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Long2FloatSortedMap sortedMap;

        protected SynchronizedSortedMap(Long2FloatSortedMap long2FloatSortedMap, Object object) {
            super(long2FloatSortedMap, object);
            this.sortedMap = long2FloatSortedMap;
        }

        protected SynchronizedSortedMap(Long2FloatSortedMap long2FloatSortedMap) {
            super(long2FloatSortedMap);
            this.sortedMap = long2FloatSortedMap;
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
        public ObjectSortedSet<Long2FloatMap.Entry> long2FloatEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.synchronize(this.sortedMap.long2FloatEntrySet(), this.sync);
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Long, Float>> entrySet() {
            return this.long2FloatEntrySet();
        }

        @Override
        public LongSortedSet keySet() {
            if (this.keys == null) {
                this.keys = LongSortedSets.synchronize(this.sortedMap.keySet(), this.sync);
            }
            return (LongSortedSet)this.keys;
        }

        @Override
        public Long2FloatSortedMap subMap(long l, long l2) {
            return new SynchronizedSortedMap(this.sortedMap.subMap(l, l2), this.sync);
        }

        @Override
        public Long2FloatSortedMap headMap(long l) {
            return new SynchronizedSortedMap(this.sortedMap.headMap(l), this.sync);
        }

        @Override
        public Long2FloatSortedMap tailMap(long l) {
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
        public Long2FloatSortedMap subMap(Long l, Long l2) {
            return new SynchronizedSortedMap(this.sortedMap.subMap(l, l2), this.sync);
        }

        @Override
        @Deprecated
        public Long2FloatSortedMap headMap(Long l) {
            return new SynchronizedSortedMap(this.sortedMap.headMap(l), this.sync);
        }

        @Override
        @Deprecated
        public Long2FloatSortedMap tailMap(Long l) {
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
        public ObjectSet long2FloatEntrySet() {
            return this.long2FloatEntrySet();
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
    extends Long2FloatMaps.Singleton
    implements Long2FloatSortedMap,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final LongComparator comparator;

        protected Singleton(long l, float f, LongComparator longComparator) {
            super(l, f);
            this.comparator = longComparator;
        }

        protected Singleton(long l, float f) {
            this(l, f, null);
        }

        final int compare(long l, long l2) {
            return this.comparator == null ? Long.compare(l, l2) : this.comparator.compare(l, l2);
        }

        @Override
        public LongComparator comparator() {
            return this.comparator;
        }

        @Override
        public ObjectSortedSet<Long2FloatMap.Entry> long2FloatEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.singleton(new AbstractLong2FloatMap.BasicEntry(this.key, this.value), Long2FloatSortedMaps.entryComparator(this.comparator));
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Long, Float>> entrySet() {
            return this.long2FloatEntrySet();
        }

        @Override
        public LongSortedSet keySet() {
            if (this.keys == null) {
                this.keys = LongSortedSets.singleton(this.key, this.comparator);
            }
            return (LongSortedSet)this.keys;
        }

        @Override
        public Long2FloatSortedMap subMap(long l, long l2) {
            if (this.compare(l, this.key) <= 0 && this.compare(this.key, l2) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Long2FloatSortedMap headMap(long l) {
            if (this.compare(this.key, l) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Long2FloatSortedMap tailMap(long l) {
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
        public Long2FloatSortedMap headMap(Long l) {
            return this.headMap((long)l);
        }

        @Override
        @Deprecated
        public Long2FloatSortedMap tailMap(Long l) {
            return this.tailMap((long)l);
        }

        @Override
        @Deprecated
        public Long2FloatSortedMap subMap(Long l, Long l2) {
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
        public ObjectSet long2FloatEntrySet() {
            return this.long2FloatEntrySet();
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
    extends Long2FloatMaps.EmptyMap
    implements Long2FloatSortedMap,
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
        public ObjectSortedSet<Long2FloatMap.Entry> long2FloatEntrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Long, Float>> entrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        public LongSortedSet keySet() {
            return LongSortedSets.EMPTY_SET;
        }

        @Override
        public Long2FloatSortedMap subMap(long l, long l2) {
            return EMPTY_MAP;
        }

        @Override
        public Long2FloatSortedMap headMap(long l) {
            return EMPTY_MAP;
        }

        @Override
        public Long2FloatSortedMap tailMap(long l) {
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
        public Long2FloatSortedMap headMap(Long l) {
            return this.headMap((long)l);
        }

        @Override
        @Deprecated
        public Long2FloatSortedMap tailMap(Long l) {
            return this.tailMap((long)l);
        }

        @Override
        @Deprecated
        public Long2FloatSortedMap subMap(Long l, Long l2) {
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
        public ObjectSet long2FloatEntrySet() {
            return this.long2FloatEntrySet();
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

