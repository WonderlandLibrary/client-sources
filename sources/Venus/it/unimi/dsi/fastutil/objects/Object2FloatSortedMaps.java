/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.AbstractObject2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatMaps;
import it.unimi.dsi.fastutil.objects.Object2FloatSortedMap;
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

public final class Object2FloatSortedMaps {
    public static final EmptySortedMap EMPTY_MAP = new EmptySortedMap();

    private Object2FloatSortedMaps() {
    }

    public static <K> Comparator<? super Map.Entry<K, ?>> entryComparator(Comparator<? super K> comparator) {
        return (arg_0, arg_1) -> Object2FloatSortedMaps.lambda$entryComparator$0(comparator, arg_0, arg_1);
    }

    public static <K> ObjectBidirectionalIterator<Object2FloatMap.Entry<K>> fastIterator(Object2FloatSortedMap<K> object2FloatSortedMap) {
        ObjectSet objectSet = object2FloatSortedMap.object2FloatEntrySet();
        return objectSet instanceof Object2FloatSortedMap.FastSortedEntrySet ? ((Object2FloatSortedMap.FastSortedEntrySet)objectSet).fastIterator() : objectSet.iterator();
    }

    public static <K> ObjectBidirectionalIterable<Object2FloatMap.Entry<K>> fastIterable(Object2FloatSortedMap<K> object2FloatSortedMap) {
        ObjectSet objectSet = object2FloatSortedMap.object2FloatEntrySet();
        return objectSet instanceof Object2FloatSortedMap.FastSortedEntrySet ? ((Object2FloatSortedMap.FastSortedEntrySet)objectSet)::fastIterator : objectSet;
    }

    public static <K> Object2FloatSortedMap<K> emptyMap() {
        return EMPTY_MAP;
    }

    public static <K> Object2FloatSortedMap<K> singleton(K k, Float f) {
        return new Singleton<K>(k, f.floatValue());
    }

    public static <K> Object2FloatSortedMap<K> singleton(K k, Float f, Comparator<? super K> comparator) {
        return new Singleton<K>(k, f.floatValue(), comparator);
    }

    public static <K> Object2FloatSortedMap<K> singleton(K k, float f) {
        return new Singleton<K>(k, f);
    }

    public static <K> Object2FloatSortedMap<K> singleton(K k, float f, Comparator<? super K> comparator) {
        return new Singleton<K>(k, f, comparator);
    }

    public static <K> Object2FloatSortedMap<K> synchronize(Object2FloatSortedMap<K> object2FloatSortedMap) {
        return new SynchronizedSortedMap<K>(object2FloatSortedMap);
    }

    public static <K> Object2FloatSortedMap<K> synchronize(Object2FloatSortedMap<K> object2FloatSortedMap, Object object) {
        return new SynchronizedSortedMap<K>(object2FloatSortedMap, object);
    }

    public static <K> Object2FloatSortedMap<K> unmodifiable(Object2FloatSortedMap<K> object2FloatSortedMap) {
        return new UnmodifiableSortedMap<K>(object2FloatSortedMap);
    }

    private static int lambda$entryComparator$0(Comparator comparator, Map.Entry entry, Map.Entry entry2) {
        return comparator.compare(entry.getKey(), entry2.getKey());
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableSortedMap<K>
    extends Object2FloatMaps.UnmodifiableMap<K>
    implements Object2FloatSortedMap<K>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Object2FloatSortedMap<K> sortedMap;

        protected UnmodifiableSortedMap(Object2FloatSortedMap<K> object2FloatSortedMap) {
            super(object2FloatSortedMap);
            this.sortedMap = object2FloatSortedMap;
        }

        @Override
        public Comparator<? super K> comparator() {
            return this.sortedMap.comparator();
        }

        @Override
        public ObjectSortedSet<Object2FloatMap.Entry<K>> object2FloatEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.unmodifiable(this.sortedMap.object2FloatEntrySet());
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<K, Float>> entrySet() {
            return this.object2FloatEntrySet();
        }

        @Override
        public ObjectSortedSet<K> keySet() {
            if (this.keys == null) {
                this.keys = ObjectSortedSets.unmodifiable(this.sortedMap.keySet());
            }
            return (ObjectSortedSet)this.keys;
        }

        @Override
        public Object2FloatSortedMap<K> subMap(K k, K k2) {
            return new UnmodifiableSortedMap<K>(this.sortedMap.subMap((Object)k, (Object)k2));
        }

        @Override
        public Object2FloatSortedMap<K> headMap(K k) {
            return new UnmodifiableSortedMap<K>(this.sortedMap.headMap((Object)k));
        }

        @Override
        public Object2FloatSortedMap<K> tailMap(K k) {
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
        public ObjectSet object2FloatEntrySet() {
            return this.object2FloatEntrySet();
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
    extends Object2FloatMaps.SynchronizedMap<K>
    implements Object2FloatSortedMap<K>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Object2FloatSortedMap<K> sortedMap;

        protected SynchronizedSortedMap(Object2FloatSortedMap<K> object2FloatSortedMap, Object object) {
            super(object2FloatSortedMap, object);
            this.sortedMap = object2FloatSortedMap;
        }

        protected SynchronizedSortedMap(Object2FloatSortedMap<K> object2FloatSortedMap) {
            super(object2FloatSortedMap);
            this.sortedMap = object2FloatSortedMap;
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
        public ObjectSortedSet<Object2FloatMap.Entry<K>> object2FloatEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.synchronize(this.sortedMap.object2FloatEntrySet(), this.sync);
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<K, Float>> entrySet() {
            return this.object2FloatEntrySet();
        }

        @Override
        public ObjectSortedSet<K> keySet() {
            if (this.keys == null) {
                this.keys = ObjectSortedSets.synchronize(this.sortedMap.keySet(), this.sync);
            }
            return (ObjectSortedSet)this.keys;
        }

        @Override
        public Object2FloatSortedMap<K> subMap(K k, K k2) {
            return new SynchronizedSortedMap<K>(this.sortedMap.subMap((Object)k, (Object)k2), this.sync);
        }

        @Override
        public Object2FloatSortedMap<K> headMap(K k) {
            return new SynchronizedSortedMap<K>(this.sortedMap.headMap((Object)k), this.sync);
        }

        @Override
        public Object2FloatSortedMap<K> tailMap(K k) {
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
        public ObjectSet object2FloatEntrySet() {
            return this.object2FloatEntrySet();
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
    extends Object2FloatMaps.Singleton<K>
    implements Object2FloatSortedMap<K>,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Comparator<? super K> comparator;

        protected Singleton(K k, float f, Comparator<? super K> comparator) {
            super(k, f);
            this.comparator = comparator;
        }

        protected Singleton(K k, float f) {
            this(k, f, null);
        }

        final int compare(K k, K k2) {
            return this.comparator == null ? ((Comparable)k).compareTo(k2) : this.comparator.compare(k, k2);
        }

        @Override
        public Comparator<? super K> comparator() {
            return this.comparator;
        }

        @Override
        public ObjectSortedSet<Object2FloatMap.Entry<K>> object2FloatEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.singleton(new AbstractObject2FloatMap.BasicEntry<Object>(this.key, this.value), Object2FloatSortedMaps.entryComparator(this.comparator));
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<K, Float>> entrySet() {
            return this.object2FloatEntrySet();
        }

        @Override
        public ObjectSortedSet<K> keySet() {
            if (this.keys == null) {
                this.keys = ObjectSortedSets.singleton(this.key, this.comparator);
            }
            return (ObjectSortedSet)this.keys;
        }

        @Override
        public Object2FloatSortedMap<K> subMap(K k, K k2) {
            if (this.compare(k, this.key) <= 0 && this.compare(this.key, k2) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Object2FloatSortedMap<K> headMap(K k) {
            if (this.compare(this.key, k) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Object2FloatSortedMap<K> tailMap(K k) {
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
        public ObjectSet object2FloatEntrySet() {
            return this.object2FloatEntrySet();
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
    extends Object2FloatMaps.EmptyMap<K>
    implements Object2FloatSortedMap<K>,
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
        public ObjectSortedSet<Object2FloatMap.Entry<K>> object2FloatEntrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<K, Float>> entrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        public ObjectSortedSet<K> keySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        public Object2FloatSortedMap<K> subMap(K k, K k2) {
            return EMPTY_MAP;
        }

        @Override
        public Object2FloatSortedMap<K> headMap(K k) {
            return EMPTY_MAP;
        }

        @Override
        public Object2FloatSortedMap<K> tailMap(K k) {
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
        public ObjectSet object2FloatEntrySet() {
            return this.object2FloatEntrySet();
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

