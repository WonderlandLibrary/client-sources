/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.AbstractDouble2ObjectMap;
import it.unimi.dsi.fastutil.doubles.Double2ObjectMap;
import it.unimi.dsi.fastutil.doubles.Double2ObjectMaps;
import it.unimi.dsi.fastutil.doubles.Double2ObjectSortedMap;
import it.unimi.dsi.fastutil.doubles.DoubleComparator;
import it.unimi.dsi.fastutil.doubles.DoubleSet;
import it.unimi.dsi.fastutil.doubles.DoubleSortedSet;
import it.unimi.dsi.fastutil.doubles.DoubleSortedSets;
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

public final class Double2ObjectSortedMaps {
    public static final EmptySortedMap EMPTY_MAP = new EmptySortedMap();

    private Double2ObjectSortedMaps() {
    }

    public static Comparator<? super Map.Entry<Double, ?>> entryComparator(DoubleComparator doubleComparator) {
        return (arg_0, arg_1) -> Double2ObjectSortedMaps.lambda$entryComparator$0(doubleComparator, arg_0, arg_1);
    }

    public static <V> ObjectBidirectionalIterator<Double2ObjectMap.Entry<V>> fastIterator(Double2ObjectSortedMap<V> double2ObjectSortedMap) {
        ObjectSet objectSet = double2ObjectSortedMap.double2ObjectEntrySet();
        return objectSet instanceof Double2ObjectSortedMap.FastSortedEntrySet ? ((Double2ObjectSortedMap.FastSortedEntrySet)objectSet).fastIterator() : objectSet.iterator();
    }

    public static <V> ObjectBidirectionalIterable<Double2ObjectMap.Entry<V>> fastIterable(Double2ObjectSortedMap<V> double2ObjectSortedMap) {
        ObjectSet objectSet = double2ObjectSortedMap.double2ObjectEntrySet();
        return objectSet instanceof Double2ObjectSortedMap.FastSortedEntrySet ? ((Double2ObjectSortedMap.FastSortedEntrySet)objectSet)::fastIterator : objectSet;
    }

    public static <V> Double2ObjectSortedMap<V> emptyMap() {
        return EMPTY_MAP;
    }

    public static <V> Double2ObjectSortedMap<V> singleton(Double d, V v) {
        return new Singleton<V>(d, v);
    }

    public static <V> Double2ObjectSortedMap<V> singleton(Double d, V v, DoubleComparator doubleComparator) {
        return new Singleton<V>(d, v, doubleComparator);
    }

    public static <V> Double2ObjectSortedMap<V> singleton(double d, V v) {
        return new Singleton<V>(d, v);
    }

    public static <V> Double2ObjectSortedMap<V> singleton(double d, V v, DoubleComparator doubleComparator) {
        return new Singleton<V>(d, v, doubleComparator);
    }

    public static <V> Double2ObjectSortedMap<V> synchronize(Double2ObjectSortedMap<V> double2ObjectSortedMap) {
        return new SynchronizedSortedMap<V>(double2ObjectSortedMap);
    }

    public static <V> Double2ObjectSortedMap<V> synchronize(Double2ObjectSortedMap<V> double2ObjectSortedMap, Object object) {
        return new SynchronizedSortedMap<V>(double2ObjectSortedMap, object);
    }

    public static <V> Double2ObjectSortedMap<V> unmodifiable(Double2ObjectSortedMap<V> double2ObjectSortedMap) {
        return new UnmodifiableSortedMap<V>(double2ObjectSortedMap);
    }

    private static int lambda$entryComparator$0(DoubleComparator doubleComparator, Map.Entry entry, Map.Entry entry2) {
        return doubleComparator.compare((double)((Double)entry.getKey()), (double)((Double)entry2.getKey()));
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableSortedMap<V>
    extends Double2ObjectMaps.UnmodifiableMap<V>
    implements Double2ObjectSortedMap<V>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Double2ObjectSortedMap<V> sortedMap;

        protected UnmodifiableSortedMap(Double2ObjectSortedMap<V> double2ObjectSortedMap) {
            super(double2ObjectSortedMap);
            this.sortedMap = double2ObjectSortedMap;
        }

        @Override
        public DoubleComparator comparator() {
            return this.sortedMap.comparator();
        }

        @Override
        public ObjectSortedSet<Double2ObjectMap.Entry<V>> double2ObjectEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.unmodifiable(this.sortedMap.double2ObjectEntrySet());
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Double, V>> entrySet() {
            return this.double2ObjectEntrySet();
        }

        @Override
        public DoubleSortedSet keySet() {
            if (this.keys == null) {
                this.keys = DoubleSortedSets.unmodifiable(this.sortedMap.keySet());
            }
            return (DoubleSortedSet)this.keys;
        }

        @Override
        public Double2ObjectSortedMap<V> subMap(double d, double d2) {
            return new UnmodifiableSortedMap<V>(this.sortedMap.subMap(d, d2));
        }

        @Override
        public Double2ObjectSortedMap<V> headMap(double d) {
            return new UnmodifiableSortedMap<V>(this.sortedMap.headMap(d));
        }

        @Override
        public Double2ObjectSortedMap<V> tailMap(double d) {
            return new UnmodifiableSortedMap<V>(this.sortedMap.tailMap(d));
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
        @Deprecated
        public Double firstKey() {
            return this.sortedMap.firstKey();
        }

        @Override
        @Deprecated
        public Double lastKey() {
            return this.sortedMap.lastKey();
        }

        @Override
        @Deprecated
        public Double2ObjectSortedMap<V> subMap(Double d, Double d2) {
            return new UnmodifiableSortedMap<V>(this.sortedMap.subMap(d, d2));
        }

        @Override
        @Deprecated
        public Double2ObjectSortedMap<V> headMap(Double d) {
            return new UnmodifiableSortedMap<V>(this.sortedMap.headMap(d));
        }

        @Override
        @Deprecated
        public Double2ObjectSortedMap<V> tailMap(Double d) {
            return new UnmodifiableSortedMap<V>(this.sortedMap.tailMap(d));
        }

        @Override
        public DoubleSet keySet() {
            return this.keySet();
        }

        @Override
        @Deprecated
        public ObjectSet entrySet() {
            return this.entrySet();
        }

        @Override
        public ObjectSet double2ObjectEntrySet() {
            return this.double2ObjectEntrySet();
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
            return this.tailMap((Double)object);
        }

        @Override
        @Deprecated
        public SortedMap headMap(Object object) {
            return this.headMap((Double)object);
        }

        @Override
        @Deprecated
        public SortedMap subMap(Object object, Object object2) {
            return this.subMap((Double)object, (Double)object2);
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
    extends Double2ObjectMaps.SynchronizedMap<V>
    implements Double2ObjectSortedMap<V>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Double2ObjectSortedMap<V> sortedMap;

        protected SynchronizedSortedMap(Double2ObjectSortedMap<V> double2ObjectSortedMap, Object object) {
            super(double2ObjectSortedMap, object);
            this.sortedMap = double2ObjectSortedMap;
        }

        protected SynchronizedSortedMap(Double2ObjectSortedMap<V> double2ObjectSortedMap) {
            super(double2ObjectSortedMap);
            this.sortedMap = double2ObjectSortedMap;
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
        public ObjectSortedSet<Double2ObjectMap.Entry<V>> double2ObjectEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.synchronize(this.sortedMap.double2ObjectEntrySet(), this.sync);
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Double, V>> entrySet() {
            return this.double2ObjectEntrySet();
        }

        @Override
        public DoubleSortedSet keySet() {
            if (this.keys == null) {
                this.keys = DoubleSortedSets.synchronize(this.sortedMap.keySet(), this.sync);
            }
            return (DoubleSortedSet)this.keys;
        }

        @Override
        public Double2ObjectSortedMap<V> subMap(double d, double d2) {
            return new SynchronizedSortedMap<V>(this.sortedMap.subMap(d, d2), this.sync);
        }

        @Override
        public Double2ObjectSortedMap<V> headMap(double d) {
            return new SynchronizedSortedMap<V>(this.sortedMap.headMap(d), this.sync);
        }

        @Override
        public Double2ObjectSortedMap<V> tailMap(double d) {
            return new SynchronizedSortedMap<V>(this.sortedMap.tailMap(d), this.sync);
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
        @Deprecated
        public Double firstKey() {
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
        public Double lastKey() {
            Object object = this.sync;
            synchronized (object) {
                return this.sortedMap.lastKey();
            }
        }

        @Override
        @Deprecated
        public Double2ObjectSortedMap<V> subMap(Double d, Double d2) {
            return new SynchronizedSortedMap<V>(this.sortedMap.subMap(d, d2), this.sync);
        }

        @Override
        @Deprecated
        public Double2ObjectSortedMap<V> headMap(Double d) {
            return new SynchronizedSortedMap<V>(this.sortedMap.headMap(d), this.sync);
        }

        @Override
        @Deprecated
        public Double2ObjectSortedMap<V> tailMap(Double d) {
            return new SynchronizedSortedMap<V>(this.sortedMap.tailMap(d), this.sync);
        }

        @Override
        public DoubleSet keySet() {
            return this.keySet();
        }

        @Override
        @Deprecated
        public ObjectSet entrySet() {
            return this.entrySet();
        }

        @Override
        public ObjectSet double2ObjectEntrySet() {
            return this.double2ObjectEntrySet();
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
            return this.tailMap((Double)object);
        }

        @Override
        @Deprecated
        public SortedMap headMap(Object object) {
            return this.headMap((Double)object);
        }

        @Override
        @Deprecated
        public SortedMap subMap(Object object, Object object2) {
            return this.subMap((Double)object, (Double)object2);
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
    extends Double2ObjectMaps.Singleton<V>
    implements Double2ObjectSortedMap<V>,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final DoubleComparator comparator;

        protected Singleton(double d, V v, DoubleComparator doubleComparator) {
            super(d, v);
            this.comparator = doubleComparator;
        }

        protected Singleton(double d, V v) {
            this(d, v, null);
        }

        final int compare(double d, double d2) {
            return this.comparator == null ? Double.compare(d, d2) : this.comparator.compare(d, d2);
        }

        @Override
        public DoubleComparator comparator() {
            return this.comparator;
        }

        @Override
        public ObjectSortedSet<Double2ObjectMap.Entry<V>> double2ObjectEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.singleton(new AbstractDouble2ObjectMap.BasicEntry<Object>(this.key, this.value), Double2ObjectSortedMaps.entryComparator(this.comparator));
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Double, V>> entrySet() {
            return this.double2ObjectEntrySet();
        }

        @Override
        public DoubleSortedSet keySet() {
            if (this.keys == null) {
                this.keys = DoubleSortedSets.singleton(this.key, this.comparator);
            }
            return (DoubleSortedSet)this.keys;
        }

        @Override
        public Double2ObjectSortedMap<V> subMap(double d, double d2) {
            if (this.compare(d, this.key) <= 0 && this.compare(this.key, d2) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Double2ObjectSortedMap<V> headMap(double d) {
            if (this.compare(this.key, d) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Double2ObjectSortedMap<V> tailMap(double d) {
            if (this.compare(d, this.key) <= 0) {
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
        public Double2ObjectSortedMap<V> headMap(Double d) {
            return this.headMap((double)d);
        }

        @Override
        @Deprecated
        public Double2ObjectSortedMap<V> tailMap(Double d) {
            return this.tailMap((double)d);
        }

        @Override
        @Deprecated
        public Double2ObjectSortedMap<V> subMap(Double d, Double d2) {
            return this.subMap((double)d, (double)d2);
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

        @Override
        public DoubleSet keySet() {
            return this.keySet();
        }

        @Override
        @Deprecated
        public ObjectSet entrySet() {
            return this.entrySet();
        }

        @Override
        public ObjectSet double2ObjectEntrySet() {
            return this.double2ObjectEntrySet();
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
            return this.tailMap((Double)object);
        }

        @Override
        @Deprecated
        public SortedMap headMap(Object object) {
            return this.headMap((Double)object);
        }

        @Override
        @Deprecated
        public SortedMap subMap(Object object, Object object2) {
            return this.subMap((Double)object, (Double)object2);
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
    extends Double2ObjectMaps.EmptyMap<V>
    implements Double2ObjectSortedMap<V>,
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
        public ObjectSortedSet<Double2ObjectMap.Entry<V>> double2ObjectEntrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Double, V>> entrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        public DoubleSortedSet keySet() {
            return DoubleSortedSets.EMPTY_SET;
        }

        @Override
        public Double2ObjectSortedMap<V> subMap(double d, double d2) {
            return EMPTY_MAP;
        }

        @Override
        public Double2ObjectSortedMap<V> headMap(double d) {
            return EMPTY_MAP;
        }

        @Override
        public Double2ObjectSortedMap<V> tailMap(double d) {
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
        public Double2ObjectSortedMap<V> headMap(Double d) {
            return this.headMap((double)d);
        }

        @Override
        @Deprecated
        public Double2ObjectSortedMap<V> tailMap(Double d) {
            return this.tailMap((double)d);
        }

        @Override
        @Deprecated
        public Double2ObjectSortedMap<V> subMap(Double d, Double d2) {
            return this.subMap((double)d, (double)d2);
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

        @Override
        public DoubleSet keySet() {
            return this.keySet();
        }

        @Override
        public ObjectSet double2ObjectEntrySet() {
            return this.double2ObjectEntrySet();
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
            return this.tailMap((Double)object);
        }

        @Override
        @Deprecated
        public SortedMap headMap(Object object) {
            return this.headMap((Double)object);
        }

        @Override
        @Deprecated
        public SortedMap subMap(Object object, Object object2) {
            return this.subMap((Double)object, (Double)object2);
        }

        @Override
        public Comparator comparator() {
            return this.comparator();
        }
    }
}

