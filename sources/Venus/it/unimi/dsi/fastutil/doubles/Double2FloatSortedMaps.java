/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.AbstractDouble2FloatMap;
import it.unimi.dsi.fastutil.doubles.Double2FloatMap;
import it.unimi.dsi.fastutil.doubles.Double2FloatMaps;
import it.unimi.dsi.fastutil.doubles.Double2FloatSortedMap;
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

public final class Double2FloatSortedMaps {
    public static final EmptySortedMap EMPTY_MAP = new EmptySortedMap();

    private Double2FloatSortedMaps() {
    }

    public static Comparator<? super Map.Entry<Double, ?>> entryComparator(DoubleComparator doubleComparator) {
        return (arg_0, arg_1) -> Double2FloatSortedMaps.lambda$entryComparator$0(doubleComparator, arg_0, arg_1);
    }

    public static ObjectBidirectionalIterator<Double2FloatMap.Entry> fastIterator(Double2FloatSortedMap double2FloatSortedMap) {
        ObjectSet objectSet = double2FloatSortedMap.double2FloatEntrySet();
        return objectSet instanceof Double2FloatSortedMap.FastSortedEntrySet ? ((Double2FloatSortedMap.FastSortedEntrySet)objectSet).fastIterator() : objectSet.iterator();
    }

    public static ObjectBidirectionalIterable<Double2FloatMap.Entry> fastIterable(Double2FloatSortedMap double2FloatSortedMap) {
        ObjectSet objectSet = double2FloatSortedMap.double2FloatEntrySet();
        return objectSet instanceof Double2FloatSortedMap.FastSortedEntrySet ? ((Double2FloatSortedMap.FastSortedEntrySet)objectSet)::fastIterator : objectSet;
    }

    public static Double2FloatSortedMap singleton(Double d, Float f) {
        return new Singleton(d, f.floatValue());
    }

    public static Double2FloatSortedMap singleton(Double d, Float f, DoubleComparator doubleComparator) {
        return new Singleton(d, f.floatValue(), doubleComparator);
    }

    public static Double2FloatSortedMap singleton(double d, float f) {
        return new Singleton(d, f);
    }

    public static Double2FloatSortedMap singleton(double d, float f, DoubleComparator doubleComparator) {
        return new Singleton(d, f, doubleComparator);
    }

    public static Double2FloatSortedMap synchronize(Double2FloatSortedMap double2FloatSortedMap) {
        return new SynchronizedSortedMap(double2FloatSortedMap);
    }

    public static Double2FloatSortedMap synchronize(Double2FloatSortedMap double2FloatSortedMap, Object object) {
        return new SynchronizedSortedMap(double2FloatSortedMap, object);
    }

    public static Double2FloatSortedMap unmodifiable(Double2FloatSortedMap double2FloatSortedMap) {
        return new UnmodifiableSortedMap(double2FloatSortedMap);
    }

    private static int lambda$entryComparator$0(DoubleComparator doubleComparator, Map.Entry entry, Map.Entry entry2) {
        return doubleComparator.compare((double)((Double)entry.getKey()), (double)((Double)entry2.getKey()));
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableSortedMap
    extends Double2FloatMaps.UnmodifiableMap
    implements Double2FloatSortedMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Double2FloatSortedMap sortedMap;

        protected UnmodifiableSortedMap(Double2FloatSortedMap double2FloatSortedMap) {
            super(double2FloatSortedMap);
            this.sortedMap = double2FloatSortedMap;
        }

        @Override
        public DoubleComparator comparator() {
            return this.sortedMap.comparator();
        }

        @Override
        public ObjectSortedSet<Double2FloatMap.Entry> double2FloatEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.unmodifiable(this.sortedMap.double2FloatEntrySet());
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Double, Float>> entrySet() {
            return this.double2FloatEntrySet();
        }

        @Override
        public DoubleSortedSet keySet() {
            if (this.keys == null) {
                this.keys = DoubleSortedSets.unmodifiable(this.sortedMap.keySet());
            }
            return (DoubleSortedSet)this.keys;
        }

        @Override
        public Double2FloatSortedMap subMap(double d, double d2) {
            return new UnmodifiableSortedMap(this.sortedMap.subMap(d, d2));
        }

        @Override
        public Double2FloatSortedMap headMap(double d) {
            return new UnmodifiableSortedMap(this.sortedMap.headMap(d));
        }

        @Override
        public Double2FloatSortedMap tailMap(double d) {
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
        public Double2FloatSortedMap subMap(Double d, Double d2) {
            return new UnmodifiableSortedMap(this.sortedMap.subMap(d, d2));
        }

        @Override
        @Deprecated
        public Double2FloatSortedMap headMap(Double d) {
            return new UnmodifiableSortedMap(this.sortedMap.headMap(d));
        }

        @Override
        @Deprecated
        public Double2FloatSortedMap tailMap(Double d) {
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
        public ObjectSet double2FloatEntrySet() {
            return this.double2FloatEntrySet();
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
    extends Double2FloatMaps.SynchronizedMap
    implements Double2FloatSortedMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Double2FloatSortedMap sortedMap;

        protected SynchronizedSortedMap(Double2FloatSortedMap double2FloatSortedMap, Object object) {
            super(double2FloatSortedMap, object);
            this.sortedMap = double2FloatSortedMap;
        }

        protected SynchronizedSortedMap(Double2FloatSortedMap double2FloatSortedMap) {
            super(double2FloatSortedMap);
            this.sortedMap = double2FloatSortedMap;
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
        public ObjectSortedSet<Double2FloatMap.Entry> double2FloatEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.synchronize(this.sortedMap.double2FloatEntrySet(), this.sync);
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Double, Float>> entrySet() {
            return this.double2FloatEntrySet();
        }

        @Override
        public DoubleSortedSet keySet() {
            if (this.keys == null) {
                this.keys = DoubleSortedSets.synchronize(this.sortedMap.keySet(), this.sync);
            }
            return (DoubleSortedSet)this.keys;
        }

        @Override
        public Double2FloatSortedMap subMap(double d, double d2) {
            return new SynchronizedSortedMap(this.sortedMap.subMap(d, d2), this.sync);
        }

        @Override
        public Double2FloatSortedMap headMap(double d) {
            return new SynchronizedSortedMap(this.sortedMap.headMap(d), this.sync);
        }

        @Override
        public Double2FloatSortedMap tailMap(double d) {
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
        public Double2FloatSortedMap subMap(Double d, Double d2) {
            return new SynchronizedSortedMap(this.sortedMap.subMap(d, d2), this.sync);
        }

        @Override
        @Deprecated
        public Double2FloatSortedMap headMap(Double d) {
            return new SynchronizedSortedMap(this.sortedMap.headMap(d), this.sync);
        }

        @Override
        @Deprecated
        public Double2FloatSortedMap tailMap(Double d) {
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
        public ObjectSet double2FloatEntrySet() {
            return this.double2FloatEntrySet();
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
    extends Double2FloatMaps.Singleton
    implements Double2FloatSortedMap,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final DoubleComparator comparator;

        protected Singleton(double d, float f, DoubleComparator doubleComparator) {
            super(d, f);
            this.comparator = doubleComparator;
        }

        protected Singleton(double d, float f) {
            this(d, f, null);
        }

        final int compare(double d, double d2) {
            return this.comparator == null ? Double.compare(d, d2) : this.comparator.compare(d, d2);
        }

        @Override
        public DoubleComparator comparator() {
            return this.comparator;
        }

        @Override
        public ObjectSortedSet<Double2FloatMap.Entry> double2FloatEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.singleton(new AbstractDouble2FloatMap.BasicEntry(this.key, this.value), Double2FloatSortedMaps.entryComparator(this.comparator));
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Double, Float>> entrySet() {
            return this.double2FloatEntrySet();
        }

        @Override
        public DoubleSortedSet keySet() {
            if (this.keys == null) {
                this.keys = DoubleSortedSets.singleton(this.key, this.comparator);
            }
            return (DoubleSortedSet)this.keys;
        }

        @Override
        public Double2FloatSortedMap subMap(double d, double d2) {
            if (this.compare(d, this.key) <= 0 && this.compare(this.key, d2) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Double2FloatSortedMap headMap(double d) {
            if (this.compare(this.key, d) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Double2FloatSortedMap tailMap(double d) {
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
        public Double2FloatSortedMap headMap(Double d) {
            return this.headMap((double)d);
        }

        @Override
        @Deprecated
        public Double2FloatSortedMap tailMap(Double d) {
            return this.tailMap((double)d);
        }

        @Override
        @Deprecated
        public Double2FloatSortedMap subMap(Double d, Double d2) {
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
        public ObjectSet double2FloatEntrySet() {
            return this.double2FloatEntrySet();
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
    extends Double2FloatMaps.EmptyMap
    implements Double2FloatSortedMap,
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
        public ObjectSortedSet<Double2FloatMap.Entry> double2FloatEntrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Double, Float>> entrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        public DoubleSortedSet keySet() {
            return DoubleSortedSets.EMPTY_SET;
        }

        @Override
        public Double2FloatSortedMap subMap(double d, double d2) {
            return EMPTY_MAP;
        }

        @Override
        public Double2FloatSortedMap headMap(double d) {
            return EMPTY_MAP;
        }

        @Override
        public Double2FloatSortedMap tailMap(double d) {
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
        public Double2FloatSortedMap headMap(Double d) {
            return this.headMap((double)d);
        }

        @Override
        @Deprecated
        public Double2FloatSortedMap tailMap(Double d) {
            return this.tailMap((double)d);
        }

        @Override
        @Deprecated
        public Double2FloatSortedMap subMap(Double d, Double d2) {
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
        public ObjectSet double2FloatEntrySet() {
            return this.double2FloatEntrySet();
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

