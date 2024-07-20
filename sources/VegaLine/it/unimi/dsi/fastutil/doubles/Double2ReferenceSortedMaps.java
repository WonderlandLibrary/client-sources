/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.Double2ReferenceMap;
import it.unimi.dsi.fastutil.doubles.Double2ReferenceMaps;
import it.unimi.dsi.fastutil.doubles.Double2ReferenceSortedMap;
import it.unimi.dsi.fastutil.doubles.DoubleComparator;
import it.unimi.dsi.fastutil.doubles.DoubleSortedSet;
import it.unimi.dsi.fastutil.doubles.DoubleSortedSets;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectSortedSets;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;
import java.util.NoSuchElementException;

public class Double2ReferenceSortedMaps {
    public static final EmptySortedMap EMPTY_MAP = new EmptySortedMap();

    private Double2ReferenceSortedMaps() {
    }

    public static Comparator<? super Map.Entry<Double, ?>> entryComparator(final DoubleComparator comparator) {
        return new Comparator<Map.Entry<Double, ?>>(){

            @Override
            public int compare(Map.Entry<Double, ?> x, Map.Entry<Double, ?> y) {
                return comparator.compare(x.getKey(), y.getKey());
            }
        };
    }

    public static <V> Double2ReferenceSortedMap<V> emptyMap() {
        return EMPTY_MAP;
    }

    public static <V> Double2ReferenceSortedMap<V> singleton(Double key, V value) {
        return new Singleton<V>(key, value);
    }

    public static <V> Double2ReferenceSortedMap<V> singleton(Double key, V value, DoubleComparator comparator) {
        return new Singleton<V>(key, value, comparator);
    }

    public static <V> Double2ReferenceSortedMap<V> singleton(double key, V value) {
        return new Singleton<V>(key, value);
    }

    public static <V> Double2ReferenceSortedMap<V> singleton(double key, V value, DoubleComparator comparator) {
        return new Singleton<V>(key, value, comparator);
    }

    public static <V> Double2ReferenceSortedMap<V> synchronize(Double2ReferenceSortedMap<V> m) {
        return new SynchronizedSortedMap<V>(m);
    }

    public static <V> Double2ReferenceSortedMap<V> synchronize(Double2ReferenceSortedMap<V> m, Object sync) {
        return new SynchronizedSortedMap<V>(m, sync);
    }

    public static <V> Double2ReferenceSortedMap<V> unmodifiable(Double2ReferenceSortedMap<V> m) {
        return new UnmodifiableSortedMap<V>(m);
    }

    public static class UnmodifiableSortedMap<V>
    extends Double2ReferenceMaps.UnmodifiableMap<V>
    implements Double2ReferenceSortedMap<V>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Double2ReferenceSortedMap<V> sortedMap;

        protected UnmodifiableSortedMap(Double2ReferenceSortedMap<V> m) {
            super(m);
            this.sortedMap = m;
        }

        @Override
        public DoubleComparator comparator() {
            return this.sortedMap.comparator();
        }

        @Override
        public ObjectSortedSet<Double2ReferenceMap.Entry<V>> double2ReferenceEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.unmodifiable(this.sortedMap.double2ReferenceEntrySet());
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        public ObjectSortedSet<Map.Entry<Double, V>> entrySet() {
            return this.double2ReferenceEntrySet();
        }

        @Override
        public DoubleSortedSet keySet() {
            if (this.keys == null) {
                this.keys = DoubleSortedSets.unmodifiable(this.sortedMap.keySet());
            }
            return (DoubleSortedSet)this.keys;
        }

        @Override
        public Double2ReferenceSortedMap<V> subMap(double from, double to) {
            return new UnmodifiableSortedMap<V>(this.sortedMap.subMap(from, to));
        }

        @Override
        public Double2ReferenceSortedMap<V> headMap(double to) {
            return new UnmodifiableSortedMap<V>(this.sortedMap.headMap(to));
        }

        @Override
        public Double2ReferenceSortedMap<V> tailMap(double from) {
            return new UnmodifiableSortedMap<V>(this.sortedMap.tailMap(from));
        }

        @Override
        public double firstDoubleKey() {
            return this.sortedMap.firstDoubleKey();
        }

        @Override
        public double lastDoubleKey() {
            return this.sortedMap.lastDoubleKey();
        }

        @Override
        public Double firstKey() {
            return (Double)this.sortedMap.firstKey();
        }

        @Override
        public Double lastKey() {
            return (Double)this.sortedMap.lastKey();
        }

        @Override
        public Double2ReferenceSortedMap<V> subMap(Double from, Double to) {
            return new UnmodifiableSortedMap<V>(this.sortedMap.subMap(from, to));
        }

        @Override
        public Double2ReferenceSortedMap<V> headMap(Double to) {
            return new UnmodifiableSortedMap<V>(this.sortedMap.headMap(to));
        }

        @Override
        public Double2ReferenceSortedMap<V> tailMap(Double from) {
            return new UnmodifiableSortedMap<V>(this.sortedMap.tailMap(from));
        }
    }

    public static class SynchronizedSortedMap<V>
    extends Double2ReferenceMaps.SynchronizedMap<V>
    implements Double2ReferenceSortedMap<V>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Double2ReferenceSortedMap<V> sortedMap;

        protected SynchronizedSortedMap(Double2ReferenceSortedMap<V> m, Object sync) {
            super(m, sync);
            this.sortedMap = m;
        }

        protected SynchronizedSortedMap(Double2ReferenceSortedMap<V> m) {
            super(m);
            this.sortedMap = m;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public DoubleComparator comparator() {
            Object object = this.sync;
            synchronized (object) {
                return this.sortedMap.comparator();
            }
        }

        @Override
        public ObjectSortedSet<Double2ReferenceMap.Entry<V>> double2ReferenceEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.synchronize(this.sortedMap.double2ReferenceEntrySet(), this.sync);
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        public ObjectSortedSet<Map.Entry<Double, V>> entrySet() {
            return this.double2ReferenceEntrySet();
        }

        @Override
        public DoubleSortedSet keySet() {
            if (this.keys == null) {
                this.keys = DoubleSortedSets.synchronize(this.sortedMap.keySet(), this.sync);
            }
            return (DoubleSortedSet)this.keys;
        }

        @Override
        public Double2ReferenceSortedMap<V> subMap(double from, double to) {
            return new SynchronizedSortedMap<V>(this.sortedMap.subMap(from, to), this.sync);
        }

        @Override
        public Double2ReferenceSortedMap<V> headMap(double to) {
            return new SynchronizedSortedMap<V>(this.sortedMap.headMap(to), this.sync);
        }

        @Override
        public Double2ReferenceSortedMap<V> tailMap(double from) {
            return new SynchronizedSortedMap<V>(this.sortedMap.tailMap(from), this.sync);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public double firstDoubleKey() {
            Object object = this.sync;
            synchronized (object) {
                return this.sortedMap.firstDoubleKey();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public double lastDoubleKey() {
            Object object = this.sync;
            synchronized (object) {
                return this.sortedMap.lastDoubleKey();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Double firstKey() {
            Object object = this.sync;
            synchronized (object) {
                return (Double)this.sortedMap.firstKey();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Double lastKey() {
            Object object = this.sync;
            synchronized (object) {
                return (Double)this.sortedMap.lastKey();
            }
        }

        @Override
        public Double2ReferenceSortedMap<V> subMap(Double from, Double to) {
            return new SynchronizedSortedMap<V>(this.sortedMap.subMap(from, to), this.sync);
        }

        @Override
        public Double2ReferenceSortedMap<V> headMap(Double to) {
            return new SynchronizedSortedMap<V>(this.sortedMap.headMap(to), this.sync);
        }

        @Override
        public Double2ReferenceSortedMap<V> tailMap(Double from) {
            return new SynchronizedSortedMap<V>(this.sortedMap.tailMap(from), this.sync);
        }
    }

    public static class Singleton<V>
    extends Double2ReferenceMaps.Singleton<V>
    implements Double2ReferenceSortedMap<V>,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final DoubleComparator comparator;

        protected Singleton(double key, V value, DoubleComparator comparator) {
            super(key, value);
            this.comparator = comparator;
        }

        protected Singleton(double key, V value) {
            this(key, value, null);
        }

        final int compare(double k1, double k2) {
            return this.comparator == null ? Double.compare(k1, k2) : this.comparator.compare(k1, k2);
        }

        @Override
        public DoubleComparator comparator() {
            return this.comparator;
        }

        @Override
        public ObjectSortedSet<Double2ReferenceMap.Entry<V>> double2ReferenceEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.singleton(new Double2ReferenceMaps.Singleton.SingletonEntry(), Double2ReferenceSortedMaps.entryComparator(this.comparator));
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        public ObjectSortedSet<Map.Entry<Double, V>> entrySet() {
            return this.double2ReferenceEntrySet();
        }

        @Override
        public DoubleSortedSet keySet() {
            if (this.keys == null) {
                this.keys = DoubleSortedSets.singleton(this.key, this.comparator);
            }
            return (DoubleSortedSet)this.keys;
        }

        @Override
        public Double2ReferenceSortedMap<V> subMap(double from, double to) {
            if (this.compare(from, this.key) <= 0 && this.compare(this.key, to) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Double2ReferenceSortedMap<V> headMap(double to) {
            if (this.compare(this.key, to) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Double2ReferenceSortedMap<V> tailMap(double from) {
            if (this.compare(from, this.key) <= 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public double firstDoubleKey() {
            return this.key;
        }

        @Override
        public double lastDoubleKey() {
            return this.key;
        }

        @Override
        @Deprecated
        public Double2ReferenceSortedMap<V> headMap(Double oto) {
            return this.headMap((double)oto);
        }

        @Override
        @Deprecated
        public Double2ReferenceSortedMap<V> tailMap(Double ofrom) {
            return this.tailMap((double)ofrom);
        }

        @Override
        @Deprecated
        public Double2ReferenceSortedMap<V> subMap(Double ofrom, Double oto) {
            return this.subMap((double)ofrom, (double)oto);
        }

        @Override
        @Deprecated
        public Double firstKey() {
            return this.firstDoubleKey();
        }

        @Override
        @Deprecated
        public Double lastKey() {
            return this.lastDoubleKey();
        }
    }

    public static class EmptySortedMap<V>
    extends Double2ReferenceMaps.EmptyMap<V>
    implements Double2ReferenceSortedMap<V>,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptySortedMap() {
        }

        @Override
        public DoubleComparator comparator() {
            return null;
        }

        @Override
        public ObjectSortedSet<Double2ReferenceMap.Entry<V>> double2ReferenceEntrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        public ObjectSortedSet<Map.Entry<Double, V>> entrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        public DoubleSortedSet keySet() {
            return DoubleSortedSets.EMPTY_SET;
        }

        @Override
        public Double2ReferenceSortedMap<V> subMap(double from, double to) {
            return EMPTY_MAP;
        }

        @Override
        public Double2ReferenceSortedMap<V> headMap(double to) {
            return EMPTY_MAP;
        }

        @Override
        public Double2ReferenceSortedMap<V> tailMap(double from) {
            return EMPTY_MAP;
        }

        @Override
        public double firstDoubleKey() {
            throw new NoSuchElementException();
        }

        @Override
        public double lastDoubleKey() {
            throw new NoSuchElementException();
        }

        @Override
        @Deprecated
        public Double2ReferenceSortedMap<V> headMap(Double oto) {
            return this.headMap((double)oto);
        }

        @Override
        @Deprecated
        public Double2ReferenceSortedMap<V> tailMap(Double ofrom) {
            return this.tailMap((double)ofrom);
        }

        @Override
        @Deprecated
        public Double2ReferenceSortedMap<V> subMap(Double ofrom, Double oto) {
            return this.subMap((double)ofrom, (double)oto);
        }

        @Override
        @Deprecated
        public Double firstKey() {
            return this.firstDoubleKey();
        }

        @Override
        @Deprecated
        public Double lastKey() {
            return this.lastDoubleKey();
        }
    }
}

