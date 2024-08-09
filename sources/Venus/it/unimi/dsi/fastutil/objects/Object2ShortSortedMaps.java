/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.AbstractObject2ShortMap;
import it.unimi.dsi.fastutil.objects.Object2ShortMap;
import it.unimi.dsi.fastutil.objects.Object2ShortMaps;
import it.unimi.dsi.fastutil.objects.Object2ShortSortedMap;
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

public final class Object2ShortSortedMaps {
    public static final EmptySortedMap EMPTY_MAP = new EmptySortedMap();

    private Object2ShortSortedMaps() {
    }

    public static <K> Comparator<? super Map.Entry<K, ?>> entryComparator(Comparator<? super K> comparator) {
        return (arg_0, arg_1) -> Object2ShortSortedMaps.lambda$entryComparator$0(comparator, arg_0, arg_1);
    }

    public static <K> ObjectBidirectionalIterator<Object2ShortMap.Entry<K>> fastIterator(Object2ShortSortedMap<K> object2ShortSortedMap) {
        ObjectSet objectSet = object2ShortSortedMap.object2ShortEntrySet();
        return objectSet instanceof Object2ShortSortedMap.FastSortedEntrySet ? ((Object2ShortSortedMap.FastSortedEntrySet)objectSet).fastIterator() : objectSet.iterator();
    }

    public static <K> ObjectBidirectionalIterable<Object2ShortMap.Entry<K>> fastIterable(Object2ShortSortedMap<K> object2ShortSortedMap) {
        ObjectSet objectSet = object2ShortSortedMap.object2ShortEntrySet();
        return objectSet instanceof Object2ShortSortedMap.FastSortedEntrySet ? ((Object2ShortSortedMap.FastSortedEntrySet)objectSet)::fastIterator : objectSet;
    }

    public static <K> Object2ShortSortedMap<K> emptyMap() {
        return EMPTY_MAP;
    }

    public static <K> Object2ShortSortedMap<K> singleton(K k, Short s) {
        return new Singleton<K>(k, s);
    }

    public static <K> Object2ShortSortedMap<K> singleton(K k, Short s, Comparator<? super K> comparator) {
        return new Singleton<K>(k, s, comparator);
    }

    public static <K> Object2ShortSortedMap<K> singleton(K k, short s) {
        return new Singleton<K>(k, s);
    }

    public static <K> Object2ShortSortedMap<K> singleton(K k, short s, Comparator<? super K> comparator) {
        return new Singleton<K>(k, s, comparator);
    }

    public static <K> Object2ShortSortedMap<K> synchronize(Object2ShortSortedMap<K> object2ShortSortedMap) {
        return new SynchronizedSortedMap<K>(object2ShortSortedMap);
    }

    public static <K> Object2ShortSortedMap<K> synchronize(Object2ShortSortedMap<K> object2ShortSortedMap, Object object) {
        return new SynchronizedSortedMap<K>(object2ShortSortedMap, object);
    }

    public static <K> Object2ShortSortedMap<K> unmodifiable(Object2ShortSortedMap<K> object2ShortSortedMap) {
        return new UnmodifiableSortedMap<K>(object2ShortSortedMap);
    }

    private static int lambda$entryComparator$0(Comparator comparator, Map.Entry entry, Map.Entry entry2) {
        return comparator.compare(entry.getKey(), entry2.getKey());
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableSortedMap<K>
    extends Object2ShortMaps.UnmodifiableMap<K>
    implements Object2ShortSortedMap<K>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Object2ShortSortedMap<K> sortedMap;

        protected UnmodifiableSortedMap(Object2ShortSortedMap<K> object2ShortSortedMap) {
            super(object2ShortSortedMap);
            this.sortedMap = object2ShortSortedMap;
        }

        @Override
        public Comparator<? super K> comparator() {
            return this.sortedMap.comparator();
        }

        @Override
        public ObjectSortedSet<Object2ShortMap.Entry<K>> object2ShortEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.unmodifiable(this.sortedMap.object2ShortEntrySet());
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<K, Short>> entrySet() {
            return this.object2ShortEntrySet();
        }

        @Override
        public ObjectSortedSet<K> keySet() {
            if (this.keys == null) {
                this.keys = ObjectSortedSets.unmodifiable(this.sortedMap.keySet());
            }
            return (ObjectSortedSet)this.keys;
        }

        @Override
        public Object2ShortSortedMap<K> subMap(K k, K k2) {
            return new UnmodifiableSortedMap<K>(this.sortedMap.subMap((Object)k, (Object)k2));
        }

        @Override
        public Object2ShortSortedMap<K> headMap(K k) {
            return new UnmodifiableSortedMap<K>(this.sortedMap.headMap((Object)k));
        }

        @Override
        public Object2ShortSortedMap<K> tailMap(K k) {
            return new UnmodifiableSortedMap<K>(this.sortedMap.tailMap((Object)k));
        }

        @Override
        public K firstKey() {
            return this.sortedMap.firstKey();
        }

        @Override
        public K lastKey() {
            return this.sortedMap.lastKey();
        }

        @Override
        public ObjectSet keySet() {
            return this.keySet();
        }

        @Override
        @Deprecated
        public ObjectSet entrySet() {
            return this.entrySet();
        }

        @Override
        public ObjectSet object2ShortEntrySet() {
            return this.object2ShortEntrySet();
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
        public SortedMap tailMap(Object object) {
            return this.tailMap(object);
        }

        @Override
        public SortedMap headMap(Object object) {
            return this.headMap(object);
        }

        @Override
        public SortedMap subMap(Object object, Object object2) {
            return this.subMap(object, object2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class SynchronizedSortedMap<K>
    extends Object2ShortMaps.SynchronizedMap<K>
    implements Object2ShortSortedMap<K>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Object2ShortSortedMap<K> sortedMap;

        protected SynchronizedSortedMap(Object2ShortSortedMap<K> object2ShortSortedMap, Object object) {
            super(object2ShortSortedMap, object);
            this.sortedMap = object2ShortSortedMap;
        }

        protected SynchronizedSortedMap(Object2ShortSortedMap<K> object2ShortSortedMap) {
            super(object2ShortSortedMap);
            this.sortedMap = object2ShortSortedMap;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Comparator<? super K> comparator() {
            Object object = this.sync;
            synchronized (object) {
                return this.sortedMap.comparator();
            }
        }

        @Override
        public ObjectSortedSet<Object2ShortMap.Entry<K>> object2ShortEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.synchronize(this.sortedMap.object2ShortEntrySet(), this.sync);
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<K, Short>> entrySet() {
            return this.object2ShortEntrySet();
        }

        @Override
        public ObjectSortedSet<K> keySet() {
            if (this.keys == null) {
                this.keys = ObjectSortedSets.synchronize(this.sortedMap.keySet(), this.sync);
            }
            return (ObjectSortedSet)this.keys;
        }

        @Override
        public Object2ShortSortedMap<K> subMap(K k, K k2) {
            return new SynchronizedSortedMap<K>(this.sortedMap.subMap((Object)k, (Object)k2), this.sync);
        }

        @Override
        public Object2ShortSortedMap<K> headMap(K k) {
            return new SynchronizedSortedMap<K>(this.sortedMap.headMap((Object)k), this.sync);
        }

        @Override
        public Object2ShortSortedMap<K> tailMap(K k) {
            return new SynchronizedSortedMap<K>(this.sortedMap.tailMap((Object)k), this.sync);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public K firstKey() {
            Object object = this.sync;
            synchronized (object) {
                return this.sortedMap.firstKey();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public K lastKey() {
            Object object = this.sync;
            synchronized (object) {
                return this.sortedMap.lastKey();
            }
        }

        @Override
        public ObjectSet keySet() {
            return this.keySet();
        }

        @Override
        @Deprecated
        public ObjectSet entrySet() {
            return this.entrySet();
        }

        @Override
        public ObjectSet object2ShortEntrySet() {
            return this.object2ShortEntrySet();
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
        public SortedMap tailMap(Object object) {
            return this.tailMap(object);
        }

        @Override
        public SortedMap headMap(Object object) {
            return this.headMap(object);
        }

        @Override
        public SortedMap subMap(Object object, Object object2) {
            return this.subMap(object, object2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Singleton<K>
    extends Object2ShortMaps.Singleton<K>
    implements Object2ShortSortedMap<K>,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Comparator<? super K> comparator;

        protected Singleton(K k, short s, Comparator<? super K> comparator) {
            super(k, s);
            this.comparator = comparator;
        }

        protected Singleton(K k, short s) {
            this(k, s, null);
        }

        final int compare(K k, K k2) {
            return this.comparator == null ? ((Comparable)k).compareTo(k2) : this.comparator.compare(k, k2);
        }

        @Override
        public Comparator<? super K> comparator() {
            return this.comparator;
        }

        @Override
        public ObjectSortedSet<Object2ShortMap.Entry<K>> object2ShortEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.singleton(new AbstractObject2ShortMap.BasicEntry<Object>(this.key, this.value), Object2ShortSortedMaps.entryComparator(this.comparator));
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<K, Short>> entrySet() {
            return this.object2ShortEntrySet();
        }

        @Override
        public ObjectSortedSet<K> keySet() {
            if (this.keys == null) {
                this.keys = ObjectSortedSets.singleton(this.key, this.comparator);
            }
            return (ObjectSortedSet)this.keys;
        }

        @Override
        public Object2ShortSortedMap<K> subMap(K k, K k2) {
            if (this.compare(k, this.key) <= 0 && this.compare(this.key, k2) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Object2ShortSortedMap<K> headMap(K k) {
            if (this.compare(this.key, k) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Object2ShortSortedMap<K> tailMap(K k) {
            if (this.compare(k, this.key) <= 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public K firstKey() {
            return (K)this.key;
        }

        @Override
        public K lastKey() {
            return (K)this.key;
        }

        @Override
        public ObjectSet keySet() {
            return this.keySet();
        }

        @Override
        @Deprecated
        public ObjectSet entrySet() {
            return this.entrySet();
        }

        @Override
        public ObjectSet object2ShortEntrySet() {
            return this.object2ShortEntrySet();
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
        public SortedMap tailMap(Object object) {
            return this.tailMap(object);
        }

        @Override
        public SortedMap headMap(Object object) {
            return this.headMap(object);
        }

        @Override
        public SortedMap subMap(Object object, Object object2) {
            return this.subMap(object, object2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class EmptySortedMap<K>
    extends Object2ShortMaps.EmptyMap<K>
    implements Object2ShortSortedMap<K>,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptySortedMap() {
        }

        @Override
        public Comparator<? super K> comparator() {
            return null;
        }

        @Override
        public ObjectSortedSet<Object2ShortMap.Entry<K>> object2ShortEntrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<K, Short>> entrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        public ObjectSortedSet<K> keySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        public Object2ShortSortedMap<K> subMap(K k, K k2) {
            return EMPTY_MAP;
        }

        @Override
        public Object2ShortSortedMap<K> headMap(K k) {
            return EMPTY_MAP;
        }

        @Override
        public Object2ShortSortedMap<K> tailMap(K k) {
            return EMPTY_MAP;
        }

        @Override
        public K firstKey() {
            throw new NoSuchElementException();
        }

        @Override
        public K lastKey() {
            throw new NoSuchElementException();
        }

        @Override
        public ObjectSet keySet() {
            return this.keySet();
        }

        @Override
        public ObjectSet object2ShortEntrySet() {
            return this.object2ShortEntrySet();
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
        public SortedMap tailMap(Object object) {
            return this.tailMap(object);
        }

        @Override
        public SortedMap headMap(Object object) {
            return this.headMap(object);
        }

        @Override
        public SortedMap subMap(Object object, Object object2) {
            return this.subMap(object, object2);
        }
    }
}

