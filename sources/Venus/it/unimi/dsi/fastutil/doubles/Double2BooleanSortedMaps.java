/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.AbstractDouble2BooleanMap;
import it.unimi.dsi.fastutil.doubles.Double2BooleanMap;
import it.unimi.dsi.fastutil.doubles.Double2BooleanMaps;
import it.unimi.dsi.fastutil.doubles.Double2BooleanSortedMap;
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

public final class Double2BooleanSortedMaps {
    public static final EmptySortedMap EMPTY_MAP = new EmptySortedMap();

    private Double2BooleanSortedMaps() {
    }

    public static Comparator<? super Map.Entry<Double, ?>> entryComparator(DoubleComparator doubleComparator) {
        return (arg_0, arg_1) -> Double2BooleanSortedMaps.lambda$entryComparator$0(doubleComparator, arg_0, arg_1);
    }

    public static ObjectBidirectionalIterator<Double2BooleanMap.Entry> fastIterator(Double2BooleanSortedMap double2BooleanSortedMap) {
        ObjectSet objectSet = double2BooleanSortedMap.double2BooleanEntrySet();
        return objectSet instanceof Double2BooleanSortedMap.FastSortedEntrySet ? ((Double2BooleanSortedMap.FastSortedEntrySet)objectSet).fastIterator() : objectSet.iterator();
    }

    public static ObjectBidirectionalIterable<Double2BooleanMap.Entry> fastIterable(Double2BooleanSortedMap double2BooleanSortedMap) {
        ObjectSet objectSet = double2BooleanSortedMap.double2BooleanEntrySet();
        return objectSet instanceof Double2BooleanSortedMap.FastSortedEntrySet ? ((Double2BooleanSortedMap.FastSortedEntrySet)objectSet)::fastIterator : objectSet;
    }

    public static Double2BooleanSortedMap singleton(Double d, Boolean bl) {
        return new Singleton(d, bl);
    }

    public static Double2BooleanSortedMap singleton(Double d, Boolean bl, DoubleComparator doubleComparator) {
        return new Singleton(d, bl, doubleComparator);
    }

    public static Double2BooleanSortedMap singleton(double d, boolean bl) {
        return new Singleton(d, bl);
    }

    public static Double2BooleanSortedMap singleton(double d, boolean bl, DoubleComparator doubleComparator) {
        return new Singleton(d, bl, doubleComparator);
    }

    public static Double2BooleanSortedMap synchronize(Double2BooleanSortedMap double2BooleanSortedMap) {
        return new SynchronizedSortedMap(double2BooleanSortedMap);
    }

    public static Double2BooleanSortedMap synchronize(Double2BooleanSortedMap double2BooleanSortedMap, Object object) {
        return new SynchronizedSortedMap(double2BooleanSortedMap, object);
    }

    public static Double2BooleanSortedMap unmodifiable(Double2BooleanSortedMap double2BooleanSortedMap) {
        return new UnmodifiableSortedMap(double2BooleanSortedMap);
    }

    private static int lambda$entryComparator$0(DoubleComparator doubleComparator, Map.Entry entry, Map.Entry entry2) {
        return doubleComparator.compare((double)((Double)entry.getKey()), (double)((Double)entry2.getKey()));
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableSortedMap
    extends Double2BooleanMaps.UnmodifiableMap
    implements Double2BooleanSortedMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Double2BooleanSortedMap sortedMap;

        protected UnmodifiableSortedMap(Double2BooleanSortedMap double2BooleanSortedMap) {
            super(double2BooleanSortedMap);
            this.sortedMap = double2BooleanSortedMap;
        }

        @Override
        public DoubleComparator comparator() {
            return this.sortedMap.comparator();
        }

        @Override
        public ObjectSortedSet<Double2BooleanMap.Entry> double2BooleanEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.unmodifiable(this.sortedMap.double2BooleanEntrySet());
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Double, Boolean>> entrySet() {
            return this.double2BooleanEntrySet();
        }

        @Override
        public DoubleSortedSet keySet() {
            if (this.keys == null) {
                this.keys = DoubleSortedSets.unmodifiable(this.sortedMap.keySet());
            }
            return (DoubleSortedSet)this.keys;
        }

        @Override
        public Double2BooleanSortedMap subMap(double d, double d2) {
            return new UnmodifiableSortedMap(this.sortedMap.subMap(d, d2));
        }

        @Override
        public Double2BooleanSortedMap headMap(double d) {
            return new UnmodifiableSortedMap(this.sortedMap.headMap(d));
        }

        @Override
        public Double2BooleanSortedMap tailMap(double d) {
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
        public Double2BooleanSortedMap subMap(Double d, Double d2) {
            return new UnmodifiableSortedMap(this.sortedMap.subMap(d, d2));
        }

        @Override
        @Deprecated
        public Double2BooleanSortedMap headMap(Double d) {
            return new UnmodifiableSortedMap(this.sortedMap.headMap(d));
        }

        @Override
        @Deprecated
        public Double2BooleanSortedMap tailMap(Double d) {
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
        public ObjectSet double2BooleanEntrySet() {
            return this.double2BooleanEntrySet();
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
    extends Double2BooleanMaps.SynchronizedMap
    implements Double2BooleanSortedMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Double2BooleanSortedMap sortedMap;

        protected SynchronizedSortedMap(Double2BooleanSortedMap double2BooleanSortedMap, Object object) {
            super(double2BooleanSortedMap, object);
            this.sortedMap = double2BooleanSortedMap;
        }

        protected SynchronizedSortedMap(Double2BooleanSortedMap double2BooleanSortedMap) {
            super(double2BooleanSortedMap);
            this.sortedMap = double2BooleanSortedMap;
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
        public ObjectSortedSet<Double2BooleanMap.Entry> double2BooleanEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.synchronize(this.sortedMap.double2BooleanEntrySet(), this.sync);
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Double, Boolean>> entrySet() {
            return this.double2BooleanEntrySet();
        }

        @Override
        public DoubleSortedSet keySet() {
            if (this.keys == null) {
                this.keys = DoubleSortedSets.synchronize(this.sortedMap.keySet(), this.sync);
            }
            return (DoubleSortedSet)this.keys;
        }

        @Override
        public Double2BooleanSortedMap subMap(double d, double d2) {
            return new SynchronizedSortedMap(this.sortedMap.subMap(d, d2), this.sync);
        }

        @Override
        public Double2BooleanSortedMap headMap(double d) {
            return new SynchronizedSortedMap(this.sortedMap.headMap(d), this.sync);
        }

        @Override
        public Double2BooleanSortedMap tailMap(double d) {
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
        public Double2BooleanSortedMap subMap(Double d, Double d2) {
            return new SynchronizedSortedMap(this.sortedMap.subMap(d, d2), this.sync);
        }

        @Override
        @Deprecated
        public Double2BooleanSortedMap headMap(Double d) {
            return new SynchronizedSortedMap(this.sortedMap.headMap(d), this.sync);
        }

        @Override
        @Deprecated
        public Double2BooleanSortedMap tailMap(Double d) {
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
        public ObjectSet double2BooleanEntrySet() {
            return this.double2BooleanEntrySet();
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
    extends Double2BooleanMaps.Singleton
    implements Double2BooleanSortedMap,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final DoubleComparator comparator;

        protected Singleton(double d, boolean bl, DoubleComparator doubleComparator) {
            super(d, bl);
            this.comparator = doubleComparator;
        }

        protected Singleton(double d, boolean bl) {
            this(d, bl, null);
        }

        final int compare(double d, double d2) {
            return this.comparator == null ? Double.compare(d, d2) : this.comparator.compare(d, d2);
        }

        @Override
        public DoubleComparator comparator() {
            return this.comparator;
        }

        @Override
        public ObjectSortedSet<Double2BooleanMap.Entry> double2BooleanEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.singleton(new AbstractDouble2BooleanMap.BasicEntry(this.key, this.value), Double2BooleanSortedMaps.entryComparator(this.comparator));
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Double, Boolean>> entrySet() {
            return this.double2BooleanEntrySet();
        }

        @Override
        public DoubleSortedSet keySet() {
            if (this.keys == null) {
                this.keys = DoubleSortedSets.singleton(this.key, this.comparator);
            }
            return (DoubleSortedSet)this.keys;
        }

        @Override
        public Double2BooleanSortedMap subMap(double d, double d2) {
            if (this.compare(d, this.key) <= 0 && this.compare(this.key, d2) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Double2BooleanSortedMap headMap(double d) {
            if (this.compare(this.key, d) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Double2BooleanSortedMap tailMap(double d) {
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
        public Double2BooleanSortedMap headMap(Double d) {
            return this.headMap((double)d);
        }

        @Override
        @Deprecated
        public Double2BooleanSortedMap tailMap(Double d) {
            return this.tailMap((double)d);
        }

        @Override
        @Deprecated
        public Double2BooleanSortedMap subMap(Double d, Double d2) {
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
        public ObjectSet double2BooleanEntrySet() {
            return this.double2BooleanEntrySet();
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
    extends Double2BooleanMaps.EmptyMap
    implements Double2BooleanSortedMap,
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
        public ObjectSortedSet<Double2BooleanMap.Entry> double2BooleanEntrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Double, Boolean>> entrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        public DoubleSortedSet keySet() {
            return DoubleSortedSets.EMPTY_SET;
        }

        @Override
        public Double2BooleanSortedMap subMap(double d, double d2) {
            return EMPTY_MAP;
        }

        @Override
        public Double2BooleanSortedMap headMap(double d) {
            return EMPTY_MAP;
        }

        @Override
        public Double2BooleanSortedMap tailMap(double d) {
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
        public Double2BooleanSortedMap headMap(Double d) {
            return this.headMap((double)d);
        }

        @Override
        @Deprecated
        public Double2BooleanSortedMap tailMap(Double d) {
            return this.tailMap((double)d);
        }

        @Override
        @Deprecated
        public Double2BooleanSortedMap subMap(Double d, Double d2) {
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
        public ObjectSet double2BooleanEntrySet() {
            return this.double2BooleanEntrySet();
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

