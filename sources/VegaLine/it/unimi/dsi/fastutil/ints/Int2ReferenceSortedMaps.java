/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.Int2ReferenceMap;
import it.unimi.dsi.fastutil.ints.Int2ReferenceMaps;
import it.unimi.dsi.fastutil.ints.Int2ReferenceSortedMap;
import it.unimi.dsi.fastutil.ints.IntComparator;
import it.unimi.dsi.fastutil.ints.IntSortedSet;
import it.unimi.dsi.fastutil.ints.IntSortedSets;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectSortedSets;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;
import java.util.NoSuchElementException;

public class Int2ReferenceSortedMaps {
    public static final EmptySortedMap EMPTY_MAP = new EmptySortedMap();

    private Int2ReferenceSortedMaps() {
    }

    public static Comparator<? super Map.Entry<Integer, ?>> entryComparator(final IntComparator comparator) {
        return new Comparator<Map.Entry<Integer, ?>>(){

            @Override
            public int compare(Map.Entry<Integer, ?> x, Map.Entry<Integer, ?> y) {
                return comparator.compare(x.getKey(), y.getKey());
            }
        };
    }

    public static <V> Int2ReferenceSortedMap<V> emptyMap() {
        return EMPTY_MAP;
    }

    public static <V> Int2ReferenceSortedMap<V> singleton(Integer key, V value) {
        return new Singleton<V>(key, value);
    }

    public static <V> Int2ReferenceSortedMap<V> singleton(Integer key, V value, IntComparator comparator) {
        return new Singleton<V>(key, value, comparator);
    }

    public static <V> Int2ReferenceSortedMap<V> singleton(int key, V value) {
        return new Singleton<V>(key, value);
    }

    public static <V> Int2ReferenceSortedMap<V> singleton(int key, V value, IntComparator comparator) {
        return new Singleton<V>(key, value, comparator);
    }

    public static <V> Int2ReferenceSortedMap<V> synchronize(Int2ReferenceSortedMap<V> m) {
        return new SynchronizedSortedMap<V>(m);
    }

    public static <V> Int2ReferenceSortedMap<V> synchronize(Int2ReferenceSortedMap<V> m, Object sync) {
        return new SynchronizedSortedMap<V>(m, sync);
    }

    public static <V> Int2ReferenceSortedMap<V> unmodifiable(Int2ReferenceSortedMap<V> m) {
        return new UnmodifiableSortedMap<V>(m);
    }

    public static class UnmodifiableSortedMap<V>
    extends Int2ReferenceMaps.UnmodifiableMap<V>
    implements Int2ReferenceSortedMap<V>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Int2ReferenceSortedMap<V> sortedMap;

        protected UnmodifiableSortedMap(Int2ReferenceSortedMap<V> m) {
            super(m);
            this.sortedMap = m;
        }

        @Override
        public IntComparator comparator() {
            return this.sortedMap.comparator();
        }

        @Override
        public ObjectSortedSet<Int2ReferenceMap.Entry<V>> int2ReferenceEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.unmodifiable(this.sortedMap.int2ReferenceEntrySet());
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        public ObjectSortedSet<Map.Entry<Integer, V>> entrySet() {
            return this.int2ReferenceEntrySet();
        }

        @Override
        public IntSortedSet keySet() {
            if (this.keys == null) {
                this.keys = IntSortedSets.unmodifiable(this.sortedMap.keySet());
            }
            return (IntSortedSet)this.keys;
        }

        @Override
        public Int2ReferenceSortedMap<V> subMap(int from, int to) {
            return new UnmodifiableSortedMap<V>(this.sortedMap.subMap(from, to));
        }

        @Override
        public Int2ReferenceSortedMap<V> headMap(int to) {
            return new UnmodifiableSortedMap<V>(this.sortedMap.headMap(to));
        }

        @Override
        public Int2ReferenceSortedMap<V> tailMap(int from) {
            return new UnmodifiableSortedMap<V>(this.sortedMap.tailMap(from));
        }

        @Override
        public int firstIntKey() {
            return this.sortedMap.firstIntKey();
        }

        @Override
        public int lastIntKey() {
            return this.sortedMap.lastIntKey();
        }

        @Override
        public Integer firstKey() {
            return (Integer)this.sortedMap.firstKey();
        }

        @Override
        public Integer lastKey() {
            return (Integer)this.sortedMap.lastKey();
        }

        @Override
        public Int2ReferenceSortedMap<V> subMap(Integer from, Integer to) {
            return new UnmodifiableSortedMap<V>(this.sortedMap.subMap(from, to));
        }

        @Override
        public Int2ReferenceSortedMap<V> headMap(Integer to) {
            return new UnmodifiableSortedMap<V>(this.sortedMap.headMap(to));
        }

        @Override
        public Int2ReferenceSortedMap<V> tailMap(Integer from) {
            return new UnmodifiableSortedMap<V>(this.sortedMap.tailMap(from));
        }
    }

    public static class SynchronizedSortedMap<V>
    extends Int2ReferenceMaps.SynchronizedMap<V>
    implements Int2ReferenceSortedMap<V>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Int2ReferenceSortedMap<V> sortedMap;

        protected SynchronizedSortedMap(Int2ReferenceSortedMap<V> m, Object sync) {
            super(m, sync);
            this.sortedMap = m;
        }

        protected SynchronizedSortedMap(Int2ReferenceSortedMap<V> m) {
            super(m);
            this.sortedMap = m;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public IntComparator comparator() {
            Object object = this.sync;
            synchronized (object) {
                return this.sortedMap.comparator();
            }
        }

        @Override
        public ObjectSortedSet<Int2ReferenceMap.Entry<V>> int2ReferenceEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.synchronize(this.sortedMap.int2ReferenceEntrySet(), this.sync);
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        public ObjectSortedSet<Map.Entry<Integer, V>> entrySet() {
            return this.int2ReferenceEntrySet();
        }

        @Override
        public IntSortedSet keySet() {
            if (this.keys == null) {
                this.keys = IntSortedSets.synchronize(this.sortedMap.keySet(), this.sync);
            }
            return (IntSortedSet)this.keys;
        }

        @Override
        public Int2ReferenceSortedMap<V> subMap(int from, int to) {
            return new SynchronizedSortedMap<V>(this.sortedMap.subMap(from, to), this.sync);
        }

        @Override
        public Int2ReferenceSortedMap<V> headMap(int to) {
            return new SynchronizedSortedMap<V>(this.sortedMap.headMap(to), this.sync);
        }

        @Override
        public Int2ReferenceSortedMap<V> tailMap(int from) {
            return new SynchronizedSortedMap<V>(this.sortedMap.tailMap(from), this.sync);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int firstIntKey() {
            Object object = this.sync;
            synchronized (object) {
                return this.sortedMap.firstIntKey();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int lastIntKey() {
            Object object = this.sync;
            synchronized (object) {
                return this.sortedMap.lastIntKey();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Integer firstKey() {
            Object object = this.sync;
            synchronized (object) {
                return (Integer)this.sortedMap.firstKey();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Integer lastKey() {
            Object object = this.sync;
            synchronized (object) {
                return (Integer)this.sortedMap.lastKey();
            }
        }

        @Override
        public Int2ReferenceSortedMap<V> subMap(Integer from, Integer to) {
            return new SynchronizedSortedMap<V>(this.sortedMap.subMap(from, to), this.sync);
        }

        @Override
        public Int2ReferenceSortedMap<V> headMap(Integer to) {
            return new SynchronizedSortedMap<V>(this.sortedMap.headMap(to), this.sync);
        }

        @Override
        public Int2ReferenceSortedMap<V> tailMap(Integer from) {
            return new SynchronizedSortedMap<V>(this.sortedMap.tailMap(from), this.sync);
        }
    }

    public static class Singleton<V>
    extends Int2ReferenceMaps.Singleton<V>
    implements Int2ReferenceSortedMap<V>,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final IntComparator comparator;

        protected Singleton(int key, V value, IntComparator comparator) {
            super(key, value);
            this.comparator = comparator;
        }

        protected Singleton(int key, V value) {
            this(key, value, null);
        }

        final int compare(int k1, int k2) {
            return this.comparator == null ? Integer.compare(k1, k2) : this.comparator.compare(k1, k2);
        }

        @Override
        public IntComparator comparator() {
            return this.comparator;
        }

        @Override
        public ObjectSortedSet<Int2ReferenceMap.Entry<V>> int2ReferenceEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.singleton(new Int2ReferenceMaps.Singleton.SingletonEntry(), Int2ReferenceSortedMaps.entryComparator(this.comparator));
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        public ObjectSortedSet<Map.Entry<Integer, V>> entrySet() {
            return this.int2ReferenceEntrySet();
        }

        @Override
        public IntSortedSet keySet() {
            if (this.keys == null) {
                this.keys = IntSortedSets.singleton(this.key, this.comparator);
            }
            return (IntSortedSet)this.keys;
        }

        @Override
        public Int2ReferenceSortedMap<V> subMap(int from, int to) {
            if (this.compare(from, this.key) <= 0 && this.compare(this.key, to) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Int2ReferenceSortedMap<V> headMap(int to) {
            if (this.compare(this.key, to) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Int2ReferenceSortedMap<V> tailMap(int from) {
            if (this.compare(from, this.key) <= 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public int firstIntKey() {
            return this.key;
        }

        @Override
        public int lastIntKey() {
            return this.key;
        }

        @Override
        @Deprecated
        public Int2ReferenceSortedMap<V> headMap(Integer oto) {
            return this.headMap((int)oto);
        }

        @Override
        @Deprecated
        public Int2ReferenceSortedMap<V> tailMap(Integer ofrom) {
            return this.tailMap((int)ofrom);
        }

        @Override
        @Deprecated
        public Int2ReferenceSortedMap<V> subMap(Integer ofrom, Integer oto) {
            return this.subMap((int)ofrom, (int)oto);
        }

        @Override
        @Deprecated
        public Integer firstKey() {
            return this.firstIntKey();
        }

        @Override
        @Deprecated
        public Integer lastKey() {
            return this.lastIntKey();
        }
    }

    public static class EmptySortedMap<V>
    extends Int2ReferenceMaps.EmptyMap<V>
    implements Int2ReferenceSortedMap<V>,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptySortedMap() {
        }

        @Override
        public IntComparator comparator() {
            return null;
        }

        @Override
        public ObjectSortedSet<Int2ReferenceMap.Entry<V>> int2ReferenceEntrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        public ObjectSortedSet<Map.Entry<Integer, V>> entrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        public IntSortedSet keySet() {
            return IntSortedSets.EMPTY_SET;
        }

        @Override
        public Int2ReferenceSortedMap<V> subMap(int from, int to) {
            return EMPTY_MAP;
        }

        @Override
        public Int2ReferenceSortedMap<V> headMap(int to) {
            return EMPTY_MAP;
        }

        @Override
        public Int2ReferenceSortedMap<V> tailMap(int from) {
            return EMPTY_MAP;
        }

        @Override
        public int firstIntKey() {
            throw new NoSuchElementException();
        }

        @Override
        public int lastIntKey() {
            throw new NoSuchElementException();
        }

        @Override
        @Deprecated
        public Int2ReferenceSortedMap<V> headMap(Integer oto) {
            return this.headMap((int)oto);
        }

        @Override
        @Deprecated
        public Int2ReferenceSortedMap<V> tailMap(Integer ofrom) {
            return this.tailMap((int)ofrom);
        }

        @Override
        @Deprecated
        public Int2ReferenceSortedMap<V> subMap(Integer ofrom, Integer oto) {
            return this.subMap((int)ofrom, (int)oto);
        }

        @Override
        @Deprecated
        public Integer firstKey() {
            return this.firstIntKey();
        }

        @Override
        @Deprecated
        public Integer lastKey() {
            return this.lastIntKey();
        }
    }
}

