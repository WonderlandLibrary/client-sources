/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.AbstractReference2ReferenceMap;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterable;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectSortedSets;
import it.unimi.dsi.fastutil.objects.Reference2ReferenceMap;
import it.unimi.dsi.fastutil.objects.Reference2ReferenceMaps;
import it.unimi.dsi.fastutil.objects.Reference2ReferenceSortedMap;
import it.unimi.dsi.fastutil.objects.ReferenceSet;
import it.unimi.dsi.fastutil.objects.ReferenceSortedSet;
import it.unimi.dsi.fastutil.objects.ReferenceSortedSets;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedMap;

public final class Reference2ReferenceSortedMaps {
    public static final EmptySortedMap EMPTY_MAP = new EmptySortedMap();

    private Reference2ReferenceSortedMaps() {
    }

    public static <K> Comparator<? super Map.Entry<K, ?>> entryComparator(Comparator<? super K> comparator) {
        return (arg_0, arg_1) -> Reference2ReferenceSortedMaps.lambda$entryComparator$0(comparator, arg_0, arg_1);
    }

    public static <K, V> ObjectBidirectionalIterator<Reference2ReferenceMap.Entry<K, V>> fastIterator(Reference2ReferenceSortedMap<K, V> reference2ReferenceSortedMap) {
        ObjectSet objectSet = reference2ReferenceSortedMap.reference2ReferenceEntrySet();
        return objectSet instanceof Reference2ReferenceSortedMap.FastSortedEntrySet ? ((Reference2ReferenceSortedMap.FastSortedEntrySet)objectSet).fastIterator() : objectSet.iterator();
    }

    public static <K, V> ObjectBidirectionalIterable<Reference2ReferenceMap.Entry<K, V>> fastIterable(Reference2ReferenceSortedMap<K, V> reference2ReferenceSortedMap) {
        ObjectSet objectSet = reference2ReferenceSortedMap.reference2ReferenceEntrySet();
        return objectSet instanceof Reference2ReferenceSortedMap.FastSortedEntrySet ? ((Reference2ReferenceSortedMap.FastSortedEntrySet)objectSet)::fastIterator : objectSet;
    }

    public static <K, V> Reference2ReferenceSortedMap<K, V> emptyMap() {
        return EMPTY_MAP;
    }

    public static <K, V> Reference2ReferenceSortedMap<K, V> singleton(K k, V v) {
        return new Singleton<K, V>(k, v);
    }

    public static <K, V> Reference2ReferenceSortedMap<K, V> singleton(K k, V v, Comparator<? super K> comparator) {
        return new Singleton<K, V>(k, v, comparator);
    }

    public static <K, V> Reference2ReferenceSortedMap<K, V> synchronize(Reference2ReferenceSortedMap<K, V> reference2ReferenceSortedMap) {
        return new SynchronizedSortedMap<K, V>(reference2ReferenceSortedMap);
    }

    public static <K, V> Reference2ReferenceSortedMap<K, V> synchronize(Reference2ReferenceSortedMap<K, V> reference2ReferenceSortedMap, Object object) {
        return new SynchronizedSortedMap<K, V>(reference2ReferenceSortedMap, object);
    }

    public static <K, V> Reference2ReferenceSortedMap<K, V> unmodifiable(Reference2ReferenceSortedMap<K, V> reference2ReferenceSortedMap) {
        return new UnmodifiableSortedMap<K, V>(reference2ReferenceSortedMap);
    }

    private static int lambda$entryComparator$0(Comparator comparator, Map.Entry entry, Map.Entry entry2) {
        return comparator.compare(entry.getKey(), entry2.getKey());
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableSortedMap<K, V>
    extends Reference2ReferenceMaps.UnmodifiableMap<K, V>
    implements Reference2ReferenceSortedMap<K, V>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Reference2ReferenceSortedMap<K, V> sortedMap;

        protected UnmodifiableSortedMap(Reference2ReferenceSortedMap<K, V> reference2ReferenceSortedMap) {
            super(reference2ReferenceSortedMap);
            this.sortedMap = reference2ReferenceSortedMap;
        }

        @Override
        public Comparator<? super K> comparator() {
            return this.sortedMap.comparator();
        }

        @Override
        public ObjectSortedSet<Reference2ReferenceMap.Entry<K, V>> reference2ReferenceEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.unmodifiable(this.sortedMap.reference2ReferenceEntrySet());
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        public ObjectSortedSet<Map.Entry<K, V>> entrySet() {
            return this.reference2ReferenceEntrySet();
        }

        @Override
        public ReferenceSortedSet<K> keySet() {
            if (this.keys == null) {
                this.keys = ReferenceSortedSets.unmodifiable(this.sortedMap.keySet());
            }
            return (ReferenceSortedSet)this.keys;
        }

        @Override
        public Reference2ReferenceSortedMap<K, V> subMap(K k, K k2) {
            return new UnmodifiableSortedMap<K, V>(this.sortedMap.subMap((Object)k, (Object)k2));
        }

        @Override
        public Reference2ReferenceSortedMap<K, V> headMap(K k) {
            return new UnmodifiableSortedMap<K, V>(this.sortedMap.headMap((Object)k));
        }

        @Override
        public Reference2ReferenceSortedMap<K, V> tailMap(K k) {
            return new UnmodifiableSortedMap<K, V>(this.sortedMap.tailMap((Object)k));
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
        public ReferenceSet keySet() {
            return this.keySet();
        }

        @Override
        public ObjectSet entrySet() {
            return this.entrySet();
        }

        @Override
        public ObjectSet reference2ReferenceEntrySet() {
            return this.reference2ReferenceEntrySet();
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
    public static class SynchronizedSortedMap<K, V>
    extends Reference2ReferenceMaps.SynchronizedMap<K, V>
    implements Reference2ReferenceSortedMap<K, V>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Reference2ReferenceSortedMap<K, V> sortedMap;

        protected SynchronizedSortedMap(Reference2ReferenceSortedMap<K, V> reference2ReferenceSortedMap, Object object) {
            super(reference2ReferenceSortedMap, object);
            this.sortedMap = reference2ReferenceSortedMap;
        }

        protected SynchronizedSortedMap(Reference2ReferenceSortedMap<K, V> reference2ReferenceSortedMap) {
            super(reference2ReferenceSortedMap);
            this.sortedMap = reference2ReferenceSortedMap;
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
        public ObjectSortedSet<Reference2ReferenceMap.Entry<K, V>> reference2ReferenceEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.synchronize(this.sortedMap.reference2ReferenceEntrySet(), this.sync);
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        public ObjectSortedSet<Map.Entry<K, V>> entrySet() {
            return this.reference2ReferenceEntrySet();
        }

        @Override
        public ReferenceSortedSet<K> keySet() {
            if (this.keys == null) {
                this.keys = ReferenceSortedSets.synchronize(this.sortedMap.keySet(), this.sync);
            }
            return (ReferenceSortedSet)this.keys;
        }

        @Override
        public Reference2ReferenceSortedMap<K, V> subMap(K k, K k2) {
            return new SynchronizedSortedMap<K, V>(this.sortedMap.subMap((Object)k, (Object)k2), this.sync);
        }

        @Override
        public Reference2ReferenceSortedMap<K, V> headMap(K k) {
            return new SynchronizedSortedMap<K, V>(this.sortedMap.headMap((Object)k), this.sync);
        }

        @Override
        public Reference2ReferenceSortedMap<K, V> tailMap(K k) {
            return new SynchronizedSortedMap<K, V>(this.sortedMap.tailMap((Object)k), this.sync);
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
        public ReferenceSet keySet() {
            return this.keySet();
        }

        @Override
        public ObjectSet entrySet() {
            return this.entrySet();
        }

        @Override
        public ObjectSet reference2ReferenceEntrySet() {
            return this.reference2ReferenceEntrySet();
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
    extends Reference2ReferenceMaps.Singleton<K, V>
    implements Reference2ReferenceSortedMap<K, V>,
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
        public ObjectSortedSet<Reference2ReferenceMap.Entry<K, V>> reference2ReferenceEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.singleton(new AbstractReference2ReferenceMap.BasicEntry<Object, Object>(this.key, this.value), Reference2ReferenceSortedMaps.entryComparator(this.comparator));
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        public ObjectSortedSet<Map.Entry<K, V>> entrySet() {
            return this.reference2ReferenceEntrySet();
        }

        @Override
        public ReferenceSortedSet<K> keySet() {
            if (this.keys == null) {
                this.keys = ReferenceSortedSets.singleton(this.key, this.comparator);
            }
            return (ReferenceSortedSet)this.keys;
        }

        @Override
        public Reference2ReferenceSortedMap<K, V> subMap(K k, K k2) {
            if (this.compare(k, this.key) <= 0 && this.compare(this.key, k2) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Reference2ReferenceSortedMap<K, V> headMap(K k) {
            if (this.compare(this.key, k) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Reference2ReferenceSortedMap<K, V> tailMap(K k) {
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
        public ReferenceSet keySet() {
            return this.keySet();
        }

        @Override
        public ObjectSet entrySet() {
            return this.entrySet();
        }

        @Override
        public ObjectSet reference2ReferenceEntrySet() {
            return this.reference2ReferenceEntrySet();
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
    public static class EmptySortedMap<K, V>
    extends Reference2ReferenceMaps.EmptyMap<K, V>
    implements Reference2ReferenceSortedMap<K, V>,
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
        public ObjectSortedSet<Reference2ReferenceMap.Entry<K, V>> reference2ReferenceEntrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        public ObjectSortedSet<Map.Entry<K, V>> entrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        public ReferenceSortedSet<K> keySet() {
            return ReferenceSortedSets.EMPTY_SET;
        }

        @Override
        public Reference2ReferenceSortedMap<K, V> subMap(K k, K k2) {
            return EMPTY_MAP;
        }

        @Override
        public Reference2ReferenceSortedMap<K, V> headMap(K k) {
            return EMPTY_MAP;
        }

        @Override
        public Reference2ReferenceSortedMap<K, V> tailMap(K k) {
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
        public ReferenceSet keySet() {
            return this.keySet();
        }

        @Override
        public ObjectSet reference2ReferenceEntrySet() {
            return this.reference2ReferenceEntrySet();
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
}

