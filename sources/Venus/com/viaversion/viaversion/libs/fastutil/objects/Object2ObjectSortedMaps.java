/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectSortedMaps$SynchronizedSortedMap
 *  com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectSortedMaps$UnmodifiableSortedMap
 */
package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectMaps;
import com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectSortedMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectSortedMaps;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectBidirectionalIterable;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectBidirectionalIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSet;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSortedSet;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSortedSets;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedMap;

public final class Object2ObjectSortedMaps {
    public static final EmptySortedMap EMPTY_MAP = new EmptySortedMap();

    private Object2ObjectSortedMaps() {
    }

    public static <K> Comparator<? super Map.Entry<K, ?>> entryComparator(Comparator<? super K> comparator) {
        return (arg_0, arg_1) -> Object2ObjectSortedMaps.lambda$entryComparator$0(comparator, arg_0, arg_1);
    }

    public static <K, V> ObjectBidirectionalIterator<Object2ObjectMap.Entry<K, V>> fastIterator(Object2ObjectSortedMap<K, V> object2ObjectSortedMap) {
        ObjectSet objectSet = object2ObjectSortedMap.object2ObjectEntrySet();
        return objectSet instanceof Object2ObjectSortedMap.FastSortedEntrySet ? ((Object2ObjectSortedMap.FastSortedEntrySet)objectSet).fastIterator() : objectSet.iterator();
    }

    public static <K, V> ObjectBidirectionalIterable<Object2ObjectMap.Entry<K, V>> fastIterable(Object2ObjectSortedMap<K, V> object2ObjectSortedMap) {
        ObjectSet objectSet = object2ObjectSortedMap.object2ObjectEntrySet();
        return objectSet instanceof Object2ObjectSortedMap.FastSortedEntrySet ? ((Object2ObjectSortedMap.FastSortedEntrySet)objectSet)::fastIterator : objectSet;
    }

    public static <K, V> Object2ObjectSortedMap<K, V> emptyMap() {
        return EMPTY_MAP;
    }

    public static <K, V> Object2ObjectSortedMap<K, V> singleton(K k, V v) {
        return new Singleton<K, V>(k, v);
    }

    public static <K, V> Object2ObjectSortedMap<K, V> singleton(K k, V v, Comparator<? super K> comparator) {
        return new Singleton<K, V>(k, v, comparator);
    }

    public static <K, V> Object2ObjectSortedMap<K, V> synchronize(Object2ObjectSortedMap<K, V> object2ObjectSortedMap) {
        return new SynchronizedSortedMap(object2ObjectSortedMap);
    }

    public static <K, V> Object2ObjectSortedMap<K, V> synchronize(Object2ObjectSortedMap<K, V> object2ObjectSortedMap, Object object) {
        return new SynchronizedSortedMap(object2ObjectSortedMap, object);
    }

    public static <K, V> Object2ObjectSortedMap<K, V> unmodifiable(Object2ObjectSortedMap<K, ? extends V> object2ObjectSortedMap) {
        return new UnmodifiableSortedMap(object2ObjectSortedMap);
    }

    private static int lambda$entryComparator$0(Comparator comparator, Map.Entry entry, Map.Entry entry2) {
        return comparator.compare(entry.getKey(), entry2.getKey());
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class EmptySortedMap<K, V>
    extends Object2ObjectMaps.EmptyMap<K, V>
    implements Object2ObjectSortedMap<K, V>,
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
        public ObjectSortedSet<Object2ObjectMap.Entry<K, V>> object2ObjectEntrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        public ObjectSortedSet<Map.Entry<K, V>> entrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        public ObjectSortedSet<K> keySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        public Object2ObjectSortedMap<K, V> subMap(K k, K k2) {
            return EMPTY_MAP;
        }

        @Override
        public Object2ObjectSortedMap<K, V> headMap(K k) {
            return EMPTY_MAP;
        }

        @Override
        public Object2ObjectSortedMap<K, V> tailMap(K k) {
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
        public ObjectSet object2ObjectEntrySet() {
            return this.object2ObjectEntrySet();
        }

        @Override
        public ObjectSet entrySet() {
            return this.entrySet();
        }

        @Override
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
    public static class Singleton<K, V>
    extends Object2ObjectMaps.Singleton<K, V>
    implements Object2ObjectSortedMap<K, V>,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Comparator<? super K> comparator;

        protected Singleton(K k, V v, Comparator<? super K> comparator) {
            super(k, v);
            this.comparator = comparator;
        }

        protected Singleton(K k, V v) {
            this(k, v, null);
        }

        final int compare(K k, K k2) {
            return this.comparator == null ? ((Comparable)k).compareTo(k2) : this.comparator.compare(k, k2);
        }

        @Override
        public Comparator<? super K> comparator() {
            return this.comparator;
        }

        @Override
        public ObjectSortedSet<Object2ObjectMap.Entry<K, V>> object2ObjectEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.singleton(new AbstractObject2ObjectMap.BasicEntry<Object, Object>(this.key, this.value), Object2ObjectSortedMaps.entryComparator(this.comparator));
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        public ObjectSortedSet<Map.Entry<K, V>> entrySet() {
            return this.object2ObjectEntrySet();
        }

        @Override
        public ObjectSortedSet<K> keySet() {
            if (this.keys == null) {
                this.keys = ObjectSortedSets.singleton(this.key, this.comparator);
            }
            return (ObjectSortedSet)this.keys;
        }

        @Override
        public Object2ObjectSortedMap<K, V> subMap(K k, K k2) {
            if (this.compare(k, this.key) <= 0 && this.compare(this.key, k2) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Object2ObjectSortedMap<K, V> headMap(K k) {
            if (this.compare(this.key, k) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Object2ObjectSortedMap<K, V> tailMap(K k) {
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
        public ObjectSet entrySet() {
            return this.entrySet();
        }

        @Override
        public ObjectSet object2ObjectEntrySet() {
            return this.object2ObjectEntrySet();
        }

        @Override
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

