/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectSortedSets;
import it.unimi.dsi.fastutil.shorts.Short2LongMap;
import it.unimi.dsi.fastutil.shorts.Short2LongMaps;
import it.unimi.dsi.fastutil.shorts.Short2LongSortedMap;
import it.unimi.dsi.fastutil.shorts.ShortComparator;
import it.unimi.dsi.fastutil.shorts.ShortSortedSet;
import it.unimi.dsi.fastutil.shorts.ShortSortedSets;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;
import java.util.NoSuchElementException;

public class Short2LongSortedMaps {
    public static final EmptySortedMap EMPTY_MAP = new EmptySortedMap();

    private Short2LongSortedMaps() {
    }

    public static Comparator<? super Map.Entry<Short, ?>> entryComparator(final ShortComparator comparator) {
        return new Comparator<Map.Entry<Short, ?>>(){

            @Override
            public int compare(Map.Entry<Short, ?> x, Map.Entry<Short, ?> y) {
                return comparator.compare(x.getKey(), y.getKey());
            }
        };
    }

    public static Short2LongSortedMap singleton(Short key, Long value) {
        return new Singleton(key, value);
    }

    public static Short2LongSortedMap singleton(Short key, Long value, ShortComparator comparator) {
        return new Singleton(key, value, comparator);
    }

    public static Short2LongSortedMap singleton(short key, long value) {
        return new Singleton(key, value);
    }

    public static Short2LongSortedMap singleton(short key, long value, ShortComparator comparator) {
        return new Singleton(key, value, comparator);
    }

    public static Short2LongSortedMap synchronize(Short2LongSortedMap m) {
        return new SynchronizedSortedMap(m);
    }

    public static Short2LongSortedMap synchronize(Short2LongSortedMap m, Object sync) {
        return new SynchronizedSortedMap(m, sync);
    }

    public static Short2LongSortedMap unmodifiable(Short2LongSortedMap m) {
        return new UnmodifiableSortedMap(m);
    }

    public static class UnmodifiableSortedMap
    extends Short2LongMaps.UnmodifiableMap
    implements Short2LongSortedMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Short2LongSortedMap sortedMap;

        protected UnmodifiableSortedMap(Short2LongSortedMap m) {
            super(m);
            this.sortedMap = m;
        }

        @Override
        public ShortComparator comparator() {
            return this.sortedMap.comparator();
        }

        @Override
        public ObjectSortedSet<Short2LongMap.Entry> short2LongEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.unmodifiable(this.sortedMap.short2LongEntrySet());
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        public ObjectSortedSet<Map.Entry<Short, Long>> entrySet() {
            return this.short2LongEntrySet();
        }

        @Override
        public ShortSortedSet keySet() {
            if (this.keys == null) {
                this.keys = ShortSortedSets.unmodifiable(this.sortedMap.keySet());
            }
            return (ShortSortedSet)this.keys;
        }

        @Override
        public Short2LongSortedMap subMap(short from, short to) {
            return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
        }

        @Override
        public Short2LongSortedMap headMap(short to) {
            return new UnmodifiableSortedMap(this.sortedMap.headMap(to));
        }

        @Override
        public Short2LongSortedMap tailMap(short from) {
            return new UnmodifiableSortedMap(this.sortedMap.tailMap(from));
        }

        @Override
        public short firstShortKey() {
            return this.sortedMap.firstShortKey();
        }

        @Override
        public short lastShortKey() {
            return this.sortedMap.lastShortKey();
        }

        @Override
        public Short firstKey() {
            return (Short)this.sortedMap.firstKey();
        }

        @Override
        public Short lastKey() {
            return (Short)this.sortedMap.lastKey();
        }

        @Override
        public Short2LongSortedMap subMap(Short from, Short to) {
            return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
        }

        @Override
        public Short2LongSortedMap headMap(Short to) {
            return new UnmodifiableSortedMap(this.sortedMap.headMap(to));
        }

        @Override
        public Short2LongSortedMap tailMap(Short from) {
            return new UnmodifiableSortedMap(this.sortedMap.tailMap(from));
        }
    }

    public static class SynchronizedSortedMap
    extends Short2LongMaps.SynchronizedMap
    implements Short2LongSortedMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Short2LongSortedMap sortedMap;

        protected SynchronizedSortedMap(Short2LongSortedMap m, Object sync) {
            super(m, sync);
            this.sortedMap = m;
        }

        protected SynchronizedSortedMap(Short2LongSortedMap m) {
            super(m);
            this.sortedMap = m;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public ShortComparator comparator() {
            Object object = this.sync;
            synchronized (object) {
                return this.sortedMap.comparator();
            }
        }

        @Override
        public ObjectSortedSet<Short2LongMap.Entry> short2LongEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.synchronize(this.sortedMap.short2LongEntrySet(), this.sync);
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        public ObjectSortedSet<Map.Entry<Short, Long>> entrySet() {
            return this.short2LongEntrySet();
        }

        @Override
        public ShortSortedSet keySet() {
            if (this.keys == null) {
                this.keys = ShortSortedSets.synchronize(this.sortedMap.keySet(), this.sync);
            }
            return (ShortSortedSet)this.keys;
        }

        @Override
        public Short2LongSortedMap subMap(short from, short to) {
            return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
        }

        @Override
        public Short2LongSortedMap headMap(short to) {
            return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
        }

        @Override
        public Short2LongSortedMap tailMap(short from) {
            return new SynchronizedSortedMap(this.sortedMap.tailMap(from), this.sync);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public short firstShortKey() {
            Object object = this.sync;
            synchronized (object) {
                return this.sortedMap.firstShortKey();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public short lastShortKey() {
            Object object = this.sync;
            synchronized (object) {
                return this.sortedMap.lastShortKey();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Short firstKey() {
            Object object = this.sync;
            synchronized (object) {
                return (Short)this.sortedMap.firstKey();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Short lastKey() {
            Object object = this.sync;
            synchronized (object) {
                return (Short)this.sortedMap.lastKey();
            }
        }

        @Override
        public Short2LongSortedMap subMap(Short from, Short to) {
            return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
        }

        @Override
        public Short2LongSortedMap headMap(Short to) {
            return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
        }

        @Override
        public Short2LongSortedMap tailMap(Short from) {
            return new SynchronizedSortedMap(this.sortedMap.tailMap(from), this.sync);
        }
    }

    public static class Singleton
    extends Short2LongMaps.Singleton
    implements Short2LongSortedMap,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final ShortComparator comparator;

        protected Singleton(short key, long value, ShortComparator comparator) {
            super(key, value);
            this.comparator = comparator;
        }

        protected Singleton(short key, long value) {
            this(key, value, null);
        }

        final int compare(short k1, short k2) {
            return this.comparator == null ? Short.compare(k1, k2) : this.comparator.compare(k1, k2);
        }

        @Override
        public ShortComparator comparator() {
            return this.comparator;
        }

        @Override
        public ObjectSortedSet<Short2LongMap.Entry> short2LongEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.singleton(new Short2LongMaps.Singleton.SingletonEntry(), Short2LongSortedMaps.entryComparator(this.comparator));
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        public ObjectSortedSet<Map.Entry<Short, Long>> entrySet() {
            return this.short2LongEntrySet();
        }

        @Override
        public ShortSortedSet keySet() {
            if (this.keys == null) {
                this.keys = ShortSortedSets.singleton(this.key, this.comparator);
            }
            return (ShortSortedSet)this.keys;
        }

        @Override
        public Short2LongSortedMap subMap(short from, short to) {
            if (this.compare(from, this.key) <= 0 && this.compare(this.key, to) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Short2LongSortedMap headMap(short to) {
            if (this.compare(this.key, to) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Short2LongSortedMap tailMap(short from) {
            if (this.compare(from, this.key) <= 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public short firstShortKey() {
            return this.key;
        }

        @Override
        public short lastShortKey() {
            return this.key;
        }

        @Override
        @Deprecated
        public Short2LongSortedMap headMap(Short oto) {
            return this.headMap((short)oto);
        }

        @Override
        @Deprecated
        public Short2LongSortedMap tailMap(Short ofrom) {
            return this.tailMap((short)ofrom);
        }

        @Override
        @Deprecated
        public Short2LongSortedMap subMap(Short ofrom, Short oto) {
            return this.subMap((short)ofrom, (short)oto);
        }

        @Override
        @Deprecated
        public Short firstKey() {
            return this.firstShortKey();
        }

        @Override
        @Deprecated
        public Short lastKey() {
            return this.lastShortKey();
        }
    }

    public static class EmptySortedMap
    extends Short2LongMaps.EmptyMap
    implements Short2LongSortedMap,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptySortedMap() {
        }

        @Override
        public ShortComparator comparator() {
            return null;
        }

        @Override
        public ObjectSortedSet<Short2LongMap.Entry> short2LongEntrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        public ObjectSortedSet<Map.Entry<Short, Long>> entrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        public ShortSortedSet keySet() {
            return ShortSortedSets.EMPTY_SET;
        }

        @Override
        public Short2LongSortedMap subMap(short from, short to) {
            return EMPTY_MAP;
        }

        @Override
        public Short2LongSortedMap headMap(short to) {
            return EMPTY_MAP;
        }

        @Override
        public Short2LongSortedMap tailMap(short from) {
            return EMPTY_MAP;
        }

        @Override
        public short firstShortKey() {
            throw new NoSuchElementException();
        }

        @Override
        public short lastShortKey() {
            throw new NoSuchElementException();
        }

        @Override
        @Deprecated
        public Short2LongSortedMap headMap(Short oto) {
            return this.headMap((short)oto);
        }

        @Override
        @Deprecated
        public Short2LongSortedMap tailMap(Short ofrom) {
            return this.tailMap((short)ofrom);
        }

        @Override
        @Deprecated
        public Short2LongSortedMap subMap(Short ofrom, Short oto) {
            return this.subMap((short)ofrom, (short)oto);
        }

        @Override
        @Deprecated
        public Short firstKey() {
            return this.firstShortKey();
        }

        @Override
        @Deprecated
        public Short lastKey() {
            return this.lastShortKey();
        }
    }
}

