/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.AbstractDouble2CharMap;
import it.unimi.dsi.fastutil.doubles.Double2CharMap;
import it.unimi.dsi.fastutil.doubles.Double2CharMaps;
import it.unimi.dsi.fastutil.doubles.Double2CharSortedMap;
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

public final class Double2CharSortedMaps {
    public static final EmptySortedMap EMPTY_MAP = new EmptySortedMap();

    private Double2CharSortedMaps() {
    }

    public static Comparator<? super Map.Entry<Double, ?>> entryComparator(DoubleComparator doubleComparator) {
        return (arg_0, arg_1) -> Double2CharSortedMaps.lambda$entryComparator$0(doubleComparator, arg_0, arg_1);
    }

    public static ObjectBidirectionalIterator<Double2CharMap.Entry> fastIterator(Double2CharSortedMap double2CharSortedMap) {
        ObjectSet objectSet = double2CharSortedMap.double2CharEntrySet();
        return objectSet instanceof Double2CharSortedMap.FastSortedEntrySet ? ((Double2CharSortedMap.FastSortedEntrySet)objectSet).fastIterator() : objectSet.iterator();
    }

    public static ObjectBidirectionalIterable<Double2CharMap.Entry> fastIterable(Double2CharSortedMap double2CharSortedMap) {
        ObjectSet objectSet = double2CharSortedMap.double2CharEntrySet();
        return objectSet instanceof Double2CharSortedMap.FastSortedEntrySet ? ((Double2CharSortedMap.FastSortedEntrySet)objectSet)::fastIterator : objectSet;
    }

    public static Double2CharSortedMap singleton(Double d, Character c) {
        return new Singleton(d, c.charValue());
    }

    public static Double2CharSortedMap singleton(Double d, Character c, DoubleComparator doubleComparator) {
        return new Singleton(d, c.charValue(), doubleComparator);
    }

    public static Double2CharSortedMap singleton(double d, char c) {
        return new Singleton(d, c);
    }

    public static Double2CharSortedMap singleton(double d, char c, DoubleComparator doubleComparator) {
        return new Singleton(d, c, doubleComparator);
    }

    public static Double2CharSortedMap synchronize(Double2CharSortedMap double2CharSortedMap) {
        return new SynchronizedSortedMap(double2CharSortedMap);
    }

    public static Double2CharSortedMap synchronize(Double2CharSortedMap double2CharSortedMap, Object object) {
        return new SynchronizedSortedMap(double2CharSortedMap, object);
    }

    public static Double2CharSortedMap unmodifiable(Double2CharSortedMap double2CharSortedMap) {
        return new UnmodifiableSortedMap(double2CharSortedMap);
    }

    private static int lambda$entryComparator$0(DoubleComparator doubleComparator, Map.Entry entry, Map.Entry entry2) {
        return doubleComparator.compare((double)((Double)entry.getKey()), (double)((Double)entry2.getKey()));
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableSortedMap
    extends Double2CharMaps.UnmodifiableMap
    implements Double2CharSortedMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Double2CharSortedMap sortedMap;

        protected UnmodifiableSortedMap(Double2CharSortedMap double2CharSortedMap) {
            super(double2CharSortedMap);
            this.sortedMap = double2CharSortedMap;
        }

        @Override
        public DoubleComparator comparator() {
            return this.sortedMap.comparator();
        }

        @Override
        public ObjectSortedSet<Double2CharMap.Entry> double2CharEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.unmodifiable(this.sortedMap.double2CharEntrySet());
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Double, Character>> entrySet() {
            return this.double2CharEntrySet();
        }

        @Override
        public DoubleSortedSet keySet() {
            if (this.keys == null) {
                this.keys = DoubleSortedSets.unmodifiable(this.sortedMap.keySet());
            }
            return (DoubleSortedSet)this.keys;
        }

        @Override
        public Double2CharSortedMap subMap(double d, double d2) {
            return new UnmodifiableSortedMap(this.sortedMap.subMap(d, d2));
        }

        @Override
        public Double2CharSortedMap headMap(double d) {
            return new UnmodifiableSortedMap(this.sortedMap.headMap(d));
        }

        @Override
        public Double2CharSortedMap tailMap(double d) {
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
        public Double2CharSortedMap subMap(Double d, Double d2) {
            return new UnmodifiableSortedMap(this.sortedMap.subMap(d, d2));
        }

        @Override
        @Deprecated
        public Double2CharSortedMap headMap(Double d) {
            return new UnmodifiableSortedMap(this.sortedMap.headMap(d));
        }

        @Override
        @Deprecated
        public Double2CharSortedMap tailMap(Double d) {
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
        public ObjectSet double2CharEntrySet() {
            return this.double2CharEntrySet();
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
    extends Double2CharMaps.SynchronizedMap
    implements Double2CharSortedMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Double2CharSortedMap sortedMap;

        protected SynchronizedSortedMap(Double2CharSortedMap double2CharSortedMap, Object object) {
            super(double2CharSortedMap, object);
            this.sortedMap = double2CharSortedMap;
        }

        protected SynchronizedSortedMap(Double2CharSortedMap double2CharSortedMap) {
            super(double2CharSortedMap);
            this.sortedMap = double2CharSortedMap;
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
        public ObjectSortedSet<Double2CharMap.Entry> double2CharEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.synchronize(this.sortedMap.double2CharEntrySet(), this.sync);
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Double, Character>> entrySet() {
            return this.double2CharEntrySet();
        }

        @Override
        public DoubleSortedSet keySet() {
            if (this.keys == null) {
                this.keys = DoubleSortedSets.synchronize(this.sortedMap.keySet(), this.sync);
            }
            return (DoubleSortedSet)this.keys;
        }

        @Override
        public Double2CharSortedMap subMap(double d, double d2) {
            return new SynchronizedSortedMap(this.sortedMap.subMap(d, d2), this.sync);
        }

        @Override
        public Double2CharSortedMap headMap(double d) {
            return new SynchronizedSortedMap(this.sortedMap.headMap(d), this.sync);
        }

        @Override
        public Double2CharSortedMap tailMap(double d) {
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
        public Double2CharSortedMap subMap(Double d, Double d2) {
            return new SynchronizedSortedMap(this.sortedMap.subMap(d, d2), this.sync);
        }

        @Override
        @Deprecated
        public Double2CharSortedMap headMap(Double d) {
            return new SynchronizedSortedMap(this.sortedMap.headMap(d), this.sync);
        }

        @Override
        @Deprecated
        public Double2CharSortedMap tailMap(Double d) {
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
        public ObjectSet double2CharEntrySet() {
            return this.double2CharEntrySet();
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
    extends Double2CharMaps.Singleton
    implements Double2CharSortedMap,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final DoubleComparator comparator;

        protected Singleton(double d, char c, DoubleComparator doubleComparator) {
            super(d, c);
            this.comparator = doubleComparator;
        }

        protected Singleton(double d, char c) {
            this(d, c, null);
        }

        final int compare(double d, double d2) {
            return this.comparator == null ? Double.compare(d, d2) : this.comparator.compare(d, d2);
        }

        @Override
        public DoubleComparator comparator() {
            return this.comparator;
        }

        @Override
        public ObjectSortedSet<Double2CharMap.Entry> double2CharEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.singleton(new AbstractDouble2CharMap.BasicEntry(this.key, this.value), Double2CharSortedMaps.entryComparator(this.comparator));
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Double, Character>> entrySet() {
            return this.double2CharEntrySet();
        }

        @Override
        public DoubleSortedSet keySet() {
            if (this.keys == null) {
                this.keys = DoubleSortedSets.singleton(this.key, this.comparator);
            }
            return (DoubleSortedSet)this.keys;
        }

        @Override
        public Double2CharSortedMap subMap(double d, double d2) {
            if (this.compare(d, this.key) <= 0 && this.compare(this.key, d2) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Double2CharSortedMap headMap(double d) {
            if (this.compare(this.key, d) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Double2CharSortedMap tailMap(double d) {
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
        public Double2CharSortedMap headMap(Double d) {
            return this.headMap((double)d);
        }

        @Override
        @Deprecated
        public Double2CharSortedMap tailMap(Double d) {
            return this.tailMap((double)d);
        }

        @Override
        @Deprecated
        public Double2CharSortedMap subMap(Double d, Double d2) {
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
        public ObjectSet double2CharEntrySet() {
            return this.double2CharEntrySet();
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
    extends Double2CharMaps.EmptyMap
    implements Double2CharSortedMap,
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
        public ObjectSortedSet<Double2CharMap.Entry> double2CharEntrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Double, Character>> entrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        public DoubleSortedSet keySet() {
            return DoubleSortedSets.EMPTY_SET;
        }

        @Override
        public Double2CharSortedMap subMap(double d, double d2) {
            return EMPTY_MAP;
        }

        @Override
        public Double2CharSortedMap headMap(double d) {
            return EMPTY_MAP;
        }

        @Override
        public Double2CharSortedMap tailMap(double d) {
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
        public Double2CharSortedMap headMap(Double d) {
            return this.headMap((double)d);
        }

        @Override
        @Deprecated
        public Double2CharSortedMap tailMap(Double d) {
            return this.tailMap((double)d);
        }

        @Override
        @Deprecated
        public Double2CharSortedMap subMap(Double d, Double d2) {
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
        public ObjectSet double2CharEntrySet() {
            return this.double2CharEntrySet();
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

