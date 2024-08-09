/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.AbstractInt2BooleanMap;
import it.unimi.dsi.fastutil.ints.Int2BooleanMap;
import it.unimi.dsi.fastutil.ints.Int2BooleanMaps;
import it.unimi.dsi.fastutil.ints.Int2BooleanSortedMap;
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

public final class Int2BooleanSortedMaps {
    public static final EmptySortedMap EMPTY_MAP = new EmptySortedMap();

    private Int2BooleanSortedMaps() {
    }

    public static Comparator<? super Map.Entry<Integer, ?>> entryComparator(IntComparator intComparator) {
        return (arg_0, arg_1) -> Int2BooleanSortedMaps.lambda$entryComparator$0(intComparator, arg_0, arg_1);
    }

    public static ObjectBidirectionalIterator<Int2BooleanMap.Entry> fastIterator(Int2BooleanSortedMap int2BooleanSortedMap) {
        ObjectSet objectSet = int2BooleanSortedMap.int2BooleanEntrySet();
        return objectSet instanceof Int2BooleanSortedMap.FastSortedEntrySet ? ((Int2BooleanSortedMap.FastSortedEntrySet)objectSet).fastIterator() : objectSet.iterator();
    }

    public static ObjectBidirectionalIterable<Int2BooleanMap.Entry> fastIterable(Int2BooleanSortedMap int2BooleanSortedMap) {
        ObjectSet objectSet = int2BooleanSortedMap.int2BooleanEntrySet();
        return objectSet instanceof Int2BooleanSortedMap.FastSortedEntrySet ? ((Int2BooleanSortedMap.FastSortedEntrySet)objectSet)::fastIterator : objectSet;
    }

    public static Int2BooleanSortedMap singleton(Integer n, Boolean bl) {
        return new Singleton(n, bl);
    }

    public static Int2BooleanSortedMap singleton(Integer n, Boolean bl, IntComparator intComparator) {
        return new Singleton(n, bl, intComparator);
    }

    public static Int2BooleanSortedMap singleton(int n, boolean bl) {
        return new Singleton(n, bl);
    }

    public static Int2BooleanSortedMap singleton(int n, boolean bl, IntComparator intComparator) {
        return new Singleton(n, bl, intComparator);
    }

    public static Int2BooleanSortedMap synchronize(Int2BooleanSortedMap int2BooleanSortedMap) {
        return new SynchronizedSortedMap(int2BooleanSortedMap);
    }

    public static Int2BooleanSortedMap synchronize(Int2BooleanSortedMap int2BooleanSortedMap, Object object) {
        return new SynchronizedSortedMap(int2BooleanSortedMap, object);
    }

    public static Int2BooleanSortedMap unmodifiable(Int2BooleanSortedMap int2BooleanSortedMap) {
        return new UnmodifiableSortedMap(int2BooleanSortedMap);
    }

    private static int lambda$entryComparator$0(IntComparator intComparator, Map.Entry entry, Map.Entry entry2) {
        return intComparator.compare((int)((Integer)entry.getKey()), (int)((Integer)entry2.getKey()));
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableSortedMap
    extends Int2BooleanMaps.UnmodifiableMap
    implements Int2BooleanSortedMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Int2BooleanSortedMap sortedMap;

        protected UnmodifiableSortedMap(Int2BooleanSortedMap int2BooleanSortedMap) {
            super(int2BooleanSortedMap);
            this.sortedMap = int2BooleanSortedMap;
        }

        @Override
        public IntComparator comparator() {
            return this.sortedMap.comparator();
        }

        @Override
        public ObjectSortedSet<Int2BooleanMap.Entry> int2BooleanEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.unmodifiable(this.sortedMap.int2BooleanEntrySet());
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Integer, Boolean>> entrySet() {
            return this.int2BooleanEntrySet();
        }

        @Override
        public IntSortedSet keySet() {
            if (this.keys == null) {
                this.keys = IntSortedSets.unmodifiable(this.sortedMap.keySet());
            }
            return (IntSortedSet)this.keys;
        }

        @Override
        public Int2BooleanSortedMap subMap(int n, int n2) {
            return new UnmodifiableSortedMap(this.sortedMap.subMap(n, n2));
        }

        @Override
        public Int2BooleanSortedMap headMap(int n) {
            return new UnmodifiableSortedMap(this.sortedMap.headMap(n));
        }

        @Override
        public Int2BooleanSortedMap tailMap(int n) {
            return new UnmodifiableSortedMap(this.sortedMap.tailMap(n));
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
        public Int2BooleanSortedMap subMap(Integer n, Integer n2) {
            return new UnmodifiableSortedMap(this.sortedMap.subMap(n, n2));
        }

        @Override
        @Deprecated
        public Int2BooleanSortedMap headMap(Integer n) {
            return new UnmodifiableSortedMap(this.sortedMap.headMap(n));
        }

        @Override
        @Deprecated
        public Int2BooleanSortedMap tailMap(Integer n) {
            return new UnmodifiableSortedMap(this.sortedMap.tailMap(n));
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
        public ObjectSet int2BooleanEntrySet() {
            return this.int2BooleanEntrySet();
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
    public static class SynchronizedSortedMap
    extends Int2BooleanMaps.SynchronizedMap
    implements Int2BooleanSortedMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Int2BooleanSortedMap sortedMap;

        protected SynchronizedSortedMap(Int2BooleanSortedMap int2BooleanSortedMap, Object object) {
            super(int2BooleanSortedMap, object);
            this.sortedMap = int2BooleanSortedMap;
        }

        protected SynchronizedSortedMap(Int2BooleanSortedMap int2BooleanSortedMap) {
            super(int2BooleanSortedMap);
            this.sortedMap = int2BooleanSortedMap;
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
        public ObjectSortedSet<Int2BooleanMap.Entry> int2BooleanEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.synchronize(this.sortedMap.int2BooleanEntrySet(), this.sync);
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Integer, Boolean>> entrySet() {
            return this.int2BooleanEntrySet();
        }

        @Override
        public IntSortedSet keySet() {
            if (this.keys == null) {
                this.keys = IntSortedSets.synchronize(this.sortedMap.keySet(), this.sync);
            }
            return (IntSortedSet)this.keys;
        }

        @Override
        public Int2BooleanSortedMap subMap(int n, int n2) {
            return new SynchronizedSortedMap(this.sortedMap.subMap(n, n2), this.sync);
        }

        @Override
        public Int2BooleanSortedMap headMap(int n) {
            return new SynchronizedSortedMap(this.sortedMap.headMap(n), this.sync);
        }

        @Override
        public Int2BooleanSortedMap tailMap(int n) {
            return new SynchronizedSortedMap(this.sortedMap.tailMap(n), this.sync);
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
        public Int2BooleanSortedMap subMap(Integer n, Integer n2) {
            return new SynchronizedSortedMap(this.sortedMap.subMap(n, n2), this.sync);
        }

        @Override
        @Deprecated
        public Int2BooleanSortedMap headMap(Integer n) {
            return new SynchronizedSortedMap(this.sortedMap.headMap(n), this.sync);
        }

        @Override
        @Deprecated
        public Int2BooleanSortedMap tailMap(Integer n) {
            return new SynchronizedSortedMap(this.sortedMap.tailMap(n), this.sync);
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
        public ObjectSet int2BooleanEntrySet() {
            return this.int2BooleanEntrySet();
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
    public static class Singleton
    extends Int2BooleanMaps.Singleton
    implements Int2BooleanSortedMap,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final IntComparator comparator;

        protected Singleton(int n, boolean bl, IntComparator intComparator) {
            super(n, bl);
            this.comparator = intComparator;
        }

        protected Singleton(int n, boolean bl) {
            this(n, bl, null);
        }

        final int compare(int n, int n2) {
            return this.comparator == null ? Integer.compare(n, n2) : this.comparator.compare(n, n2);
        }

        @Override
        public IntComparator comparator() {
            return this.comparator;
        }

        @Override
        public ObjectSortedSet<Int2BooleanMap.Entry> int2BooleanEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.singleton(new AbstractInt2BooleanMap.BasicEntry(this.key, this.value), Int2BooleanSortedMaps.entryComparator(this.comparator));
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Integer, Boolean>> entrySet() {
            return this.int2BooleanEntrySet();
        }

        @Override
        public IntSortedSet keySet() {
            if (this.keys == null) {
                this.keys = IntSortedSets.singleton(this.key, this.comparator);
            }
            return (IntSortedSet)this.keys;
        }

        @Override
        public Int2BooleanSortedMap subMap(int n, int n2) {
            if (this.compare(n, this.key) <= 0 && this.compare(this.key, n2) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Int2BooleanSortedMap headMap(int n) {
            if (this.compare(this.key, n) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Int2BooleanSortedMap tailMap(int n) {
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
        public Int2BooleanSortedMap headMap(Integer n) {
            return this.headMap((int)n);
        }

        @Override
        @Deprecated
        public Int2BooleanSortedMap tailMap(Integer n) {
            return this.tailMap((int)n);
        }

        @Override
        @Deprecated
        public Int2BooleanSortedMap subMap(Integer n, Integer n2) {
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
        public ObjectSet int2BooleanEntrySet() {
            return this.int2BooleanEntrySet();
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
    public static class EmptySortedMap
    extends Int2BooleanMaps.EmptyMap
    implements Int2BooleanSortedMap,
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
        public ObjectSortedSet<Int2BooleanMap.Entry> int2BooleanEntrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Integer, Boolean>> entrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        public IntSortedSet keySet() {
            return IntSortedSets.EMPTY_SET;
        }

        @Override
        public Int2BooleanSortedMap subMap(int n, int n2) {
            return EMPTY_MAP;
        }

        @Override
        public Int2BooleanSortedMap headMap(int n) {
            return EMPTY_MAP;
        }

        @Override
        public Int2BooleanSortedMap tailMap(int n) {
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
        public Int2BooleanSortedMap headMap(Integer n) {
            return this.headMap((int)n);
        }

        @Override
        @Deprecated
        public Int2BooleanSortedMap tailMap(Integer n) {
            return this.tailMap((int)n);
        }

        @Override
        @Deprecated
        public Int2BooleanSortedMap subMap(Integer n, Integer n2) {
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
        public ObjectSet int2BooleanEntrySet() {
            return this.int2BooleanEntrySet();
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

