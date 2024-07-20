/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.longs.Long2BooleanMap;
import it.unimi.dsi.fastutil.longs.Long2BooleanMaps;
import it.unimi.dsi.fastutil.longs.Long2BooleanSortedMap;
import it.unimi.dsi.fastutil.longs.LongComparator;
import it.unimi.dsi.fastutil.longs.LongSortedSet;
import it.unimi.dsi.fastutil.longs.LongSortedSets;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectSortedSets;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;
import java.util.NoSuchElementException;

public class Long2BooleanSortedMaps {
    public static final EmptySortedMap EMPTY_MAP = new EmptySortedMap();

    private Long2BooleanSortedMaps() {
    }

    public static Comparator<? super Map.Entry<Long, ?>> entryComparator(final LongComparator comparator) {
        return new Comparator<Map.Entry<Long, ?>>(){

            @Override
            public int compare(Map.Entry<Long, ?> x, Map.Entry<Long, ?> y) {
                return comparator.compare(x.getKey(), y.getKey());
            }
        };
    }

    public static Long2BooleanSortedMap singleton(Long key, Boolean value) {
        return new Singleton(key, value);
    }

    public static Long2BooleanSortedMap singleton(Long key, Boolean value, LongComparator comparator) {
        return new Singleton(key, value, comparator);
    }

    public static Long2BooleanSortedMap singleton(long key, boolean value) {
        return new Singleton(key, value);
    }

    public static Long2BooleanSortedMap singleton(long key, boolean value, LongComparator comparator) {
        return new Singleton(key, value, comparator);
    }

    public static Long2BooleanSortedMap synchronize(Long2BooleanSortedMap m) {
        return new SynchronizedSortedMap(m);
    }

    public static Long2BooleanSortedMap synchronize(Long2BooleanSortedMap m, Object sync) {
        return new SynchronizedSortedMap(m, sync);
    }

    public static Long2BooleanSortedMap unmodifiable(Long2BooleanSortedMap m) {
        return new UnmodifiableSortedMap(m);
    }

    public static class UnmodifiableSortedMap
    extends Long2BooleanMaps.UnmodifiableMap
    implements Long2BooleanSortedMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Long2BooleanSortedMap sortedMap;

        protected UnmodifiableSortedMap(Long2BooleanSortedMap m) {
            super(m);
            this.sortedMap = m;
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
        public Long2BooleanSortedMap subMap(long from, long to) {
            return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
        }

        @Override
        public Long2BooleanSortedMap headMap(long to) {
            return new UnmodifiableSortedMap(this.sortedMap.headMap(to));
        }

        @Override
        public Long2BooleanSortedMap tailMap(long from) {
            return new UnmodifiableSortedMap(this.sortedMap.tailMap(from));
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
        public Long firstKey() {
            return (Long)this.sortedMap.firstKey();
        }

        @Override
        public Long lastKey() {
            return (Long)this.sortedMap.lastKey();
        }

        @Override
        public Long2BooleanSortedMap subMap(Long from, Long to) {
            return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
        }

        @Override
        public Long2BooleanSortedMap headMap(Long to) {
            return new UnmodifiableSortedMap(this.sortedMap.headMap(to));
        }

        @Override
        public Long2BooleanSortedMap tailMap(Long from) {
            return new UnmodifiableSortedMap(this.sortedMap.tailMap(from));
        }
    }

    public static class SynchronizedSortedMap
    extends Long2BooleanMaps.SynchronizedMap
    implements Long2BooleanSortedMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Long2BooleanSortedMap sortedMap;

        protected SynchronizedSortedMap(Long2BooleanSortedMap m, Object sync) {
            super(m, sync);
            this.sortedMap = m;
        }

        protected SynchronizedSortedMap(Long2BooleanSortedMap m) {
            super(m);
            this.sortedMap = m;
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
        public Long2BooleanSortedMap subMap(long from, long to) {
            return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
        }

        @Override
        public Long2BooleanSortedMap headMap(long to) {
            return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
        }

        @Override
        public Long2BooleanSortedMap tailMap(long from) {
            return new SynchronizedSortedMap(this.sortedMap.tailMap(from), this.sync);
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
        public Long firstKey() {
            Object object = this.sync;
            synchronized (object) {
                return (Long)this.sortedMap.firstKey();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Long lastKey() {
            Object object = this.sync;
            synchronized (object) {
                return (Long)this.sortedMap.lastKey();
            }
        }

        @Override
        public Long2BooleanSortedMap subMap(Long from, Long to) {
            return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
        }

        @Override
        public Long2BooleanSortedMap headMap(Long to) {
            return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
        }

        @Override
        public Long2BooleanSortedMap tailMap(Long from) {
            return new SynchronizedSortedMap(this.sortedMap.tailMap(from), this.sync);
        }
    }

    public static class Singleton
    extends Long2BooleanMaps.Singleton
    implements Long2BooleanSortedMap,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final LongComparator comparator;

        protected Singleton(long key, boolean value, LongComparator comparator) {
            super(key, value);
            this.comparator = comparator;
        }

        protected Singleton(long key, boolean value) {
            this(key, value, null);
        }

        final int compare(long k1, long k2) {
            return this.comparator == null ? Long.compare(k1, k2) : this.comparator.compare(k1, k2);
        }

        @Override
        public LongComparator comparator() {
            return this.comparator;
        }

        @Override
        public ObjectSortedSet<Long2BooleanMap.Entry> long2BooleanEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.singleton(new Long2BooleanMaps.Singleton.SingletonEntry(), Long2BooleanSortedMaps.entryComparator(this.comparator));
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
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
        public Long2BooleanSortedMap subMap(long from, long to) {
            if (this.compare(from, this.key) <= 0 && this.compare(this.key, to) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Long2BooleanSortedMap headMap(long to) {
            if (this.compare(this.key, to) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Long2BooleanSortedMap tailMap(long from) {
            if (this.compare(from, this.key) <= 0) {
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
        public Long2BooleanSortedMap headMap(Long oto) {
            return this.headMap((long)oto);
        }

        @Override
        @Deprecated
        public Long2BooleanSortedMap tailMap(Long ofrom) {
            return this.tailMap((long)ofrom);
        }

        @Override
        @Deprecated
        public Long2BooleanSortedMap subMap(Long ofrom, Long oto) {
            return this.subMap((long)ofrom, (long)oto);
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
    }

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
        public ObjectSortedSet<Map.Entry<Long, Boolean>> entrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        public LongSortedSet keySet() {
            return LongSortedSets.EMPTY_SET;
        }

        @Override
        public Long2BooleanSortedMap subMap(long from, long to) {
            return EMPTY_MAP;
        }

        @Override
        public Long2BooleanSortedMap headMap(long to) {
            return EMPTY_MAP;
        }

        @Override
        public Long2BooleanSortedMap tailMap(long from) {
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
        public Long2BooleanSortedMap headMap(Long oto) {
            return this.headMap((long)oto);
        }

        @Override
        @Deprecated
        public Long2BooleanSortedMap tailMap(Long ofrom) {
            return this.tailMap((long)ofrom);
        }

        @Override
        @Deprecated
        public Long2BooleanSortedMap subMap(Long ofrom, Long oto) {
            return this.subMap((long)ofrom, (long)oto);
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
    }
}

