/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.AbstractDouble2IntMap;
import it.unimi.dsi.fastutil.doubles.Double2IntMap;
import it.unimi.dsi.fastutil.doubles.Double2IntMaps;
import it.unimi.dsi.fastutil.doubles.Double2IntSortedMap;
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

public final class Double2IntSortedMaps {
    public static final EmptySortedMap EMPTY_MAP = new EmptySortedMap();

    private Double2IntSortedMaps() {
    }

    public static Comparator<? super Map.Entry<Double, ?>> entryComparator(DoubleComparator doubleComparator) {
        return (arg_0, arg_1) -> Double2IntSortedMaps.lambda$entryComparator$0(doubleComparator, arg_0, arg_1);
    }

    public static ObjectBidirectionalIterator<Double2IntMap.Entry> fastIterator(Double2IntSortedMap double2IntSortedMap) {
        ObjectSet objectSet = double2IntSortedMap.double2IntEntrySet();
        return objectSet instanceof Double2IntSortedMap.FastSortedEntrySet ? ((Double2IntSortedMap.FastSortedEntrySet)objectSet).fastIterator() : objectSet.iterator();
    }

    public static ObjectBidirectionalIterable<Double2IntMap.Entry> fastIterable(Double2IntSortedMap double2IntSortedMap) {
        ObjectSet objectSet = double2IntSortedMap.double2IntEntrySet();
        return objectSet instanceof Double2IntSortedMap.FastSortedEntrySet ? ((Double2IntSortedMap.FastSortedEntrySet)objectSet)::fastIterator : objectSet;
    }

    public static Double2IntSortedMap singleton(Double d, Integer n) {
        return new Singleton(d, n);
    }

    public static Double2IntSortedMap singleton(Double d, Integer n, DoubleComparator doubleComparator) {
        return new Singleton(d, n, doubleComparator);
    }

    public static Double2IntSortedMap singleton(double d, int n) {
        return new Singleton(d, n);
    }

    public static Double2IntSortedMap singleton(double d, int n, DoubleComparator doubleComparator) {
        return new Singleton(d, n, doubleComparator);
    }

    public static Double2IntSortedMap synchronize(Double2IntSortedMap double2IntSortedMap) {
        return new SynchronizedSortedMap(double2IntSortedMap);
    }

    public static Double2IntSortedMap synchronize(Double2IntSortedMap double2IntSortedMap, Object object) {
        return new SynchronizedSortedMap(double2IntSortedMap, object);
    }

    public static Double2IntSortedMap unmodifiable(Double2IntSortedMap double2IntSortedMap) {
        return new UnmodifiableSortedMap(double2IntSortedMap);
    }

    private static int lambda$entryComparator$0(DoubleComparator doubleComparator, Map.Entry entry, Map.Entry entry2) {
        return doubleComparator.compare((double)((Double)entry.getKey()), (double)((Double)entry2.getKey()));
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableSortedMap
    extends Double2IntMaps.UnmodifiableMap
    implements Double2IntSortedMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Double2IntSortedMap sortedMap;

        protected UnmodifiableSortedMap(Double2IntSortedMap double2IntSortedMap) {
            super(double2IntSortedMap);
            this.sortedMap = double2IntSortedMap;
        }

        @Override
        public DoubleComparator comparator() {
            return this.sortedMap.comparator();
        }

        @Override
        public ObjectSortedSet<Double2IntMap.Entry> double2IntEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.unmodifiable(this.sortedMap.double2IntEntrySet());
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Double, Integer>> entrySet() {
            return this.double2IntEntrySet();
        }

        @Override
        public DoubleSortedSet keySet() {
            if (this.keys == null) {
                this.keys = DoubleSortedSets.unmodifiable(this.sortedMap.keySet());
            }
            return (DoubleSortedSet)this.keys;
        }

        @Override
        public Double2IntSortedMap subMap(double d, double d2) {
            return new UnmodifiableSortedMap(this.sortedMap.subMap(d, d2));
        }

        @Override
        public Double2IntSortedMap headMap(double d) {
            return new UnmodifiableSortedMap(this.sortedMap.headMap(d));
        }

        @Override
        public Double2IntSortedMap tailMap(double d) {
            return new UnmodifiableSortedMap(this.sortedMap.tailMap(d));
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
        public Double2IntSortedMap subMap(Double d, Double d2) {
            return new UnmodifiableSortedMap(this.sortedMap.subMap(d, d2));
        }

        @Override
        @Deprecated
        public Double2IntSortedMap headMap(Double d) {
            return new UnmodifiableSortedMap(this.sortedMap.headMap(d));
        }

        @Override
        @Deprecated
        public Double2IntSortedMap tailMap(Double d) {
            return new UnmodifiableSortedMap(this.sortedMap.tailMap(d));
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
        public ObjectSet double2IntEntrySet() {
            return this.double2IntEntrySet();
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
    public static class SynchronizedSortedMap
    extends Double2IntMaps.SynchronizedMap
    implements Double2IntSortedMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Double2IntSortedMap sortedMap;

        protected SynchronizedSortedMap(Double2IntSortedMap double2IntSortedMap, Object object) {
            super(double2IntSortedMap, object);
            this.sortedMap = double2IntSortedMap;
        }

        protected SynchronizedSortedMap(Double2IntSortedMap double2IntSortedMap) {
            super(double2IntSortedMap);
            this.sortedMap = double2IntSortedMap;
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
        public ObjectSortedSet<Double2IntMap.Entry> double2IntEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.synchronize(this.sortedMap.double2IntEntrySet(), this.sync);
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Double, Integer>> entrySet() {
            return this.double2IntEntrySet();
        }

        @Override
        public DoubleSortedSet keySet() {
            if (this.keys == null) {
                this.keys = DoubleSortedSets.synchronize(this.sortedMap.keySet(), this.sync);
            }
            return (DoubleSortedSet)this.keys;
        }

        @Override
        public Double2IntSortedMap subMap(double d, double d2) {
            return new SynchronizedSortedMap(this.sortedMap.subMap(d, d2), this.sync);
        }

        @Override
        public Double2IntSortedMap headMap(double d) {
            return new SynchronizedSortedMap(this.sortedMap.headMap(d), this.sync);
        }

        @Override
        public Double2IntSortedMap tailMap(double d) {
            return new SynchronizedSortedMap(this.sortedMap.tailMap(d), this.sync);
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
        public Double2IntSortedMap subMap(Double d, Double d2) {
            return new SynchronizedSortedMap(this.sortedMap.subMap(d, d2), this.sync);
        }

        @Override
        @Deprecated
        public Double2IntSortedMap headMap(Double d) {
            return new SynchronizedSortedMap(this.sortedMap.headMap(d), this.sync);
        }

        @Override
        @Deprecated
        public Double2IntSortedMap tailMap(Double d) {
            return new SynchronizedSortedMap(this.sortedMap.tailMap(d), this.sync);
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
        public ObjectSet double2IntEntrySet() {
            return this.double2IntEntrySet();
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
    public static class Singleton
    extends Double2IntMaps.Singleton
    implements Double2IntSortedMap,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final DoubleComparator comparator;

        protected Singleton(double d, int n, DoubleComparator doubleComparator) {
            super(d, n);
            this.comparator = doubleComparator;
        }

        protected Singleton(double d, int n) {
            this(d, n, null);
        }

        final int compare(double d, double d2) {
            return this.comparator == null ? Double.compare(d, d2) : this.comparator.compare(d, d2);
        }

        @Override
        public DoubleComparator comparator() {
            return this.comparator;
        }

        @Override
        public ObjectSortedSet<Double2IntMap.Entry> double2IntEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.singleton(new AbstractDouble2IntMap.BasicEntry(this.key, this.value), Double2IntSortedMaps.entryComparator(this.comparator));
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Double, Integer>> entrySet() {
            return this.double2IntEntrySet();
        }

        @Override
        public DoubleSortedSet keySet() {
            if (this.keys == null) {
                this.keys = DoubleSortedSets.singleton(this.key, this.comparator);
            }
            return (DoubleSortedSet)this.keys;
        }

        @Override
        public Double2IntSortedMap subMap(double d, double d2) {
            if (this.compare(d, this.key) <= 0 && this.compare(this.key, d2) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Double2IntSortedMap headMap(double d) {
            if (this.compare(this.key, d) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Double2IntSortedMap tailMap(double d) {
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
        public Double2IntSortedMap headMap(Double d) {
            return this.headMap((double)d);
        }

        @Override
        @Deprecated
        public Double2IntSortedMap tailMap(Double d) {
            return this.tailMap((double)d);
        }

        @Override
        @Deprecated
        public Double2IntSortedMap subMap(Double d, Double d2) {
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
        public ObjectSet double2IntEntrySet() {
            return this.double2IntEntrySet();
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
    public static class EmptySortedMap
    extends Double2IntMaps.EmptyMap
    implements Double2IntSortedMap,
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
        public ObjectSortedSet<Double2IntMap.Entry> double2IntEntrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Double, Integer>> entrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        public DoubleSortedSet keySet() {
            return DoubleSortedSets.EMPTY_SET;
        }

        @Override
        public Double2IntSortedMap subMap(double d, double d2) {
            return EMPTY_MAP;
        }

        @Override
        public Double2IntSortedMap headMap(double d) {
            return EMPTY_MAP;
        }

        @Override
        public Double2IntSortedMap tailMap(double d) {
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
        public Double2IntSortedMap headMap(Double d) {
            return this.headMap((double)d);
        }

        @Override
        @Deprecated
        public Double2IntSortedMap tailMap(Double d) {
            return this.tailMap((double)d);
        }

        @Override
        @Deprecated
        public Double2IntSortedMap subMap(Double d, Double d2) {
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
        public ObjectSet double2IntEntrySet() {
            return this.double2IntEntrySet();
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

