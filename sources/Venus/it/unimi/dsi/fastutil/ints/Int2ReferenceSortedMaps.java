/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.AbstractInt2ReferenceMap;
import it.unimi.dsi.fastutil.ints.Int2ReferenceMap;
import it.unimi.dsi.fastutil.ints.Int2ReferenceMaps;
import it.unimi.dsi.fastutil.ints.Int2ReferenceSortedMap;
import it.unimi.dsi.fastutil.ints.IntComparator;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.ints.IntSortedSet;
import it.unimi.dsi.fastutil.ints.IntSortedSets;
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

public final class Int2ReferenceSortedMaps {
    public static final EmptySortedMap EMPTY_MAP = new EmptySortedMap();

    private Int2ReferenceSortedMaps() {
    }

    public static Comparator<? super Map.Entry<Integer, ?>> entryComparator(IntComparator intComparator) {
        return (arg_0, arg_1) -> Int2ReferenceSortedMaps.lambda$entryComparator$0(intComparator, arg_0, arg_1);
    }

    public static <V> ObjectBidirectionalIterator<Int2ReferenceMap.Entry<V>> fastIterator(Int2ReferenceSortedMap<V> int2ReferenceSortedMap) {
        ObjectSet objectSet = int2ReferenceSortedMap.int2ReferenceEntrySet();
        return objectSet instanceof Int2ReferenceSortedMap.FastSortedEntrySet ? ((Int2ReferenceSortedMap.FastSortedEntrySet)objectSet).fastIterator() : objectSet.iterator();
    }

    public static <V> ObjectBidirectionalIterable<Int2ReferenceMap.Entry<V>> fastIterable(Int2ReferenceSortedMap<V> int2ReferenceSortedMap) {
        ObjectSet objectSet = int2ReferenceSortedMap.int2ReferenceEntrySet();
        return objectSet instanceof Int2ReferenceSortedMap.FastSortedEntrySet ? ((Int2ReferenceSortedMap.FastSortedEntrySet)objectSet)::fastIterator : objectSet;
    }

    public static <V> Int2ReferenceSortedMap<V> emptyMap() {
        return EMPTY_MAP;
    }

    public static <V> Int2ReferenceSortedMap<V> singleton(Integer n, V v) {
        return new Singleton<V>(n, v);
    }

    public static <V> Int2ReferenceSortedMap<V> singleton(Integer n, V v, IntComparator intComparator) {
        return new Singleton<V>(n, v, intComparator);
    }

    public static <V> Int2ReferenceSortedMap<V> singleton(int n, V v) {
        return new Singleton<V>(n, v);
    }

    public static <V> Int2ReferenceSortedMap<V> singleton(int n, V v, IntComparator intComparator) {
        return new Singleton<V>(n, v, intComparator);
    }

    public static <V> Int2ReferenceSortedMap<V> synchronize(Int2ReferenceSortedMap<V> int2ReferenceSortedMap) {
        return new SynchronizedSortedMap<V>(int2ReferenceSortedMap);
    }

    public static <V> Int2ReferenceSortedMap<V> synchronize(Int2ReferenceSortedMap<V> int2ReferenceSortedMap, Object object) {
        return new SynchronizedSortedMap<V>(int2ReferenceSortedMap, object);
    }

    public static <V> Int2ReferenceSortedMap<V> unmodifiable(Int2ReferenceSortedMap<V> int2ReferenceSortedMap) {
        return new UnmodifiableSortedMap<V>(int2ReferenceSortedMap);
    }

    private static int lambda$entryComparator$0(IntComparator intComparator, Map.Entry entry, Map.Entry entry2) {
        return intComparator.compare((int)((Integer)entry.getKey()), (int)((Integer)entry2.getKey()));
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableSortedMap<V>
    extends Int2ReferenceMaps.UnmodifiableMap<V>
    implements Int2ReferenceSortedMap<V>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Int2ReferenceSortedMap<V> sortedMap;

        protected UnmodifiableSortedMap(Int2ReferenceSortedMap<V> int2ReferenceSortedMap) {
            super(int2ReferenceSortedMap);
            this.sortedMap = int2ReferenceSortedMap;
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
        @Deprecated
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
        public Int2ReferenceSortedMap<V> subMap(int n, int n2) {
            return new UnmodifiableSortedMap<V>(this.sortedMap.subMap(n, n2));
        }

        @Override
        public Int2ReferenceSortedMap<V> headMap(int n) {
            return new UnmodifiableSortedMap<V>(this.sortedMap.headMap(n));
        }

        @Override
        public Int2ReferenceSortedMap<V> tailMap(int n) {
            return new UnmodifiableSortedMap<V>(this.sortedMap.tailMap(n));
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
        @Deprecated
        public Integer firstKey() {
            return this.sortedMap.firstKey();
        }

        @Override
        @Deprecated
        public Integer lastKey() {
            return this.sortedMap.lastKey();
        }

        @Override
        @Deprecated
        public Int2ReferenceSortedMap<V> subMap(Integer n, Integer n2) {
            return new UnmodifiableSortedMap<V>(this.sortedMap.subMap(n, n2));
        }

        @Override
        @Deprecated
        public Int2ReferenceSortedMap<V> headMap(Integer n) {
            return new UnmodifiableSortedMap<V>(this.sortedMap.headMap(n));
        }

        @Override
        @Deprecated
        public Int2ReferenceSortedMap<V> tailMap(Integer n) {
            return new UnmodifiableSortedMap<V>(this.sortedMap.tailMap(n));
        }

        @Override
        public IntSet keySet() {
            return this.keySet();
        }

        @Override
        @Deprecated
        public ObjectSet entrySet() {
            return this.entrySet();
        }

        @Override
        public ObjectSet int2ReferenceEntrySet() {
            return this.int2ReferenceEntrySet();
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
            return this.tailMap((Integer)object);
        }

        @Override
        @Deprecated
        public SortedMap headMap(Object object) {
            return this.headMap((Integer)object);
        }

        @Override
        @Deprecated
        public SortedMap subMap(Object object, Object object2) {
            return this.subMap((Integer)object, (Integer)object2);
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
    extends Int2ReferenceMaps.SynchronizedMap<V>
    implements Int2ReferenceSortedMap<V>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Int2ReferenceSortedMap<V> sortedMap;

        protected SynchronizedSortedMap(Int2ReferenceSortedMap<V> int2ReferenceSortedMap, Object object) {
            super(int2ReferenceSortedMap, object);
            this.sortedMap = int2ReferenceSortedMap;
        }

        protected SynchronizedSortedMap(Int2ReferenceSortedMap<V> int2ReferenceSortedMap) {
            super(int2ReferenceSortedMap);
            this.sortedMap = int2ReferenceSortedMap;
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
        @Deprecated
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
        public Int2ReferenceSortedMap<V> subMap(int n, int n2) {
            return new SynchronizedSortedMap<V>(this.sortedMap.subMap(n, n2), this.sync);
        }

        @Override
        public Int2ReferenceSortedMap<V> headMap(int n) {
            return new SynchronizedSortedMap<V>(this.sortedMap.headMap(n), this.sync);
        }

        @Override
        public Int2ReferenceSortedMap<V> tailMap(int n) {
            return new SynchronizedSortedMap<V>(this.sortedMap.tailMap(n), this.sync);
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
        @Deprecated
        public Integer firstKey() {
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
        public Integer lastKey() {
            Object object = this.sync;
            synchronized (object) {
                return this.sortedMap.lastKey();
            }
        }

        @Override
        @Deprecated
        public Int2ReferenceSortedMap<V> subMap(Integer n, Integer n2) {
            return new SynchronizedSortedMap<V>(this.sortedMap.subMap(n, n2), this.sync);
        }

        @Override
        @Deprecated
        public Int2ReferenceSortedMap<V> headMap(Integer n) {
            return new SynchronizedSortedMap<V>(this.sortedMap.headMap(n), this.sync);
        }

        @Override
        @Deprecated
        public Int2ReferenceSortedMap<V> tailMap(Integer n) {
            return new SynchronizedSortedMap<V>(this.sortedMap.tailMap(n), this.sync);
        }

        @Override
        public IntSet keySet() {
            return this.keySet();
        }

        @Override
        @Deprecated
        public ObjectSet entrySet() {
            return this.entrySet();
        }

        @Override
        public ObjectSet int2ReferenceEntrySet() {
            return this.int2ReferenceEntrySet();
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
            return this.tailMap((Integer)object);
        }

        @Override
        @Deprecated
        public SortedMap headMap(Object object) {
            return this.headMap((Integer)object);
        }

        @Override
        @Deprecated
        public SortedMap subMap(Object object, Object object2) {
            return this.subMap((Integer)object, (Integer)object2);
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
    extends Int2ReferenceMaps.Singleton<V>
    implements Int2ReferenceSortedMap<V>,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final IntComparator comparator;

        protected Singleton(int n, V v, IntComparator intComparator) {
            super(n, v);
            this.comparator = intComparator;
        }

        protected Singleton(int n, V v) {
            this(n, v, null);
        }

        final int compare(int n, int n2) {
            return this.comparator == null ? Integer.compare(n, n2) : this.comparator.compare(n, n2);
        }

        @Override
        public IntComparator comparator() {
            return this.comparator;
        }

        @Override
        public ObjectSortedSet<Int2ReferenceMap.Entry<V>> int2ReferenceEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.singleton(new AbstractInt2ReferenceMap.BasicEntry<Object>(this.key, this.value), Int2ReferenceSortedMaps.entryComparator(this.comparator));
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
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
        public Int2ReferenceSortedMap<V> subMap(int n, int n2) {
            if (this.compare(n, this.key) <= 0 && this.compare(this.key, n2) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Int2ReferenceSortedMap<V> headMap(int n) {
            if (this.compare(this.key, n) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Int2ReferenceSortedMap<V> tailMap(int n) {
            if (this.compare(n, this.key) <= 0) {
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
        public Int2ReferenceSortedMap<V> headMap(Integer n) {
            return this.headMap((int)n);
        }

        @Override
        @Deprecated
        public Int2ReferenceSortedMap<V> tailMap(Integer n) {
            return this.tailMap((int)n);
        }

        @Override
        @Deprecated
        public Int2ReferenceSortedMap<V> subMap(Integer n, Integer n2) {
            return this.subMap((int)n, (int)n2);
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

        @Override
        public IntSet keySet() {
            return this.keySet();
        }

        @Override
        @Deprecated
        public ObjectSet entrySet() {
            return this.entrySet();
        }

        @Override
        public ObjectSet int2ReferenceEntrySet() {
            return this.int2ReferenceEntrySet();
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
            return this.tailMap((Integer)object);
        }

        @Override
        @Deprecated
        public SortedMap headMap(Object object) {
            return this.headMap((Integer)object);
        }

        @Override
        @Deprecated
        public SortedMap subMap(Object object, Object object2) {
            return this.subMap((Integer)object, (Integer)object2);
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
        @Deprecated
        public ObjectSortedSet<Map.Entry<Integer, V>> entrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        public IntSortedSet keySet() {
            return IntSortedSets.EMPTY_SET;
        }

        @Override
        public Int2ReferenceSortedMap<V> subMap(int n, int n2) {
            return EMPTY_MAP;
        }

        @Override
        public Int2ReferenceSortedMap<V> headMap(int n) {
            return EMPTY_MAP;
        }

        @Override
        public Int2ReferenceSortedMap<V> tailMap(int n) {
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
        public Int2ReferenceSortedMap<V> headMap(Integer n) {
            return this.headMap((int)n);
        }

        @Override
        @Deprecated
        public Int2ReferenceSortedMap<V> tailMap(Integer n) {
            return this.tailMap((int)n);
        }

        @Override
        @Deprecated
        public Int2ReferenceSortedMap<V> subMap(Integer n, Integer n2) {
            return this.subMap((int)n, (int)n2);
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

        @Override
        public IntSet keySet() {
            return this.keySet();
        }

        @Override
        public ObjectSet int2ReferenceEntrySet() {
            return this.int2ReferenceEntrySet();
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
            return this.tailMap((Integer)object);
        }

        @Override
        @Deprecated
        public SortedMap headMap(Object object) {
            return this.headMap((Integer)object);
        }

        @Override
        @Deprecated
        public SortedMap subMap(Object object, Object object2) {
            return this.subMap((Integer)object, (Integer)object2);
        }

        @Override
        public Comparator comparator() {
            return this.comparator();
        }
    }
}

