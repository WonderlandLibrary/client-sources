/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.AbstractReference2ByteMap;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterable;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectSortedSets;
import it.unimi.dsi.fastutil.objects.Reference2ByteMap;
import it.unimi.dsi.fastutil.objects.Reference2ByteMaps;
import it.unimi.dsi.fastutil.objects.Reference2ByteSortedMap;
import it.unimi.dsi.fastutil.objects.ReferenceSet;
import it.unimi.dsi.fastutil.objects.ReferenceSortedSet;
import it.unimi.dsi.fastutil.objects.ReferenceSortedSets;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedMap;

public final class Reference2ByteSortedMaps {
    public static final EmptySortedMap EMPTY_MAP = new EmptySortedMap();

    private Reference2ByteSortedMaps() {
    }

    public static <K> Comparator<? super Map.Entry<K, ?>> entryComparator(Comparator<? super K> comparator) {
        return (arg_0, arg_1) -> Reference2ByteSortedMaps.lambda$entryComparator$0(comparator, arg_0, arg_1);
    }

    public static <K> ObjectBidirectionalIterator<Reference2ByteMap.Entry<K>> fastIterator(Reference2ByteSortedMap<K> reference2ByteSortedMap) {
        ObjectSet objectSet = reference2ByteSortedMap.reference2ByteEntrySet();
        return objectSet instanceof Reference2ByteSortedMap.FastSortedEntrySet ? ((Reference2ByteSortedMap.FastSortedEntrySet)objectSet).fastIterator() : objectSet.iterator();
    }

    public static <K> ObjectBidirectionalIterable<Reference2ByteMap.Entry<K>> fastIterable(Reference2ByteSortedMap<K> reference2ByteSortedMap) {
        ObjectSet objectSet = reference2ByteSortedMap.reference2ByteEntrySet();
        return objectSet instanceof Reference2ByteSortedMap.FastSortedEntrySet ? ((Reference2ByteSortedMap.FastSortedEntrySet)objectSet)::fastIterator : objectSet;
    }

    public static <K> Reference2ByteSortedMap<K> emptyMap() {
        return EMPTY_MAP;
    }

    public static <K> Reference2ByteSortedMap<K> singleton(K k, Byte by) {
        return new Singleton<K>(k, by);
    }

    public static <K> Reference2ByteSortedMap<K> singleton(K k, Byte by, Comparator<? super K> comparator) {
        return new Singleton<K>(k, by, comparator);
    }

    public static <K> Reference2ByteSortedMap<K> singleton(K k, byte by) {
        return new Singleton<K>(k, by);
    }

    public static <K> Reference2ByteSortedMap<K> singleton(K k, byte by, Comparator<? super K> comparator) {
        return new Singleton<K>(k, by, comparator);
    }

    public static <K> Reference2ByteSortedMap<K> synchronize(Reference2ByteSortedMap<K> reference2ByteSortedMap) {
        return new SynchronizedSortedMap<K>(reference2ByteSortedMap);
    }

    public static <K> Reference2ByteSortedMap<K> synchronize(Reference2ByteSortedMap<K> reference2ByteSortedMap, Object object) {
        return new SynchronizedSortedMap<K>(reference2ByteSortedMap, object);
    }

    public static <K> Reference2ByteSortedMap<K> unmodifiable(Reference2ByteSortedMap<K> reference2ByteSortedMap) {
        return new UnmodifiableSortedMap<K>(reference2ByteSortedMap);
    }

    private static int lambda$entryComparator$0(Comparator comparator, Map.Entry entry, Map.Entry entry2) {
        return comparator.compare(entry.getKey(), entry2.getKey());
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableSortedMap<K>
    extends Reference2ByteMaps.UnmodifiableMap<K>
    implements Reference2ByteSortedMap<K>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Reference2ByteSortedMap<K> sortedMap;

        protected UnmodifiableSortedMap(Reference2ByteSortedMap<K> reference2ByteSortedMap) {
            super(reference2ByteSortedMap);
            this.sortedMap = reference2ByteSortedMap;
        }

        @Override
        public Comparator<? super K> comparator() {
            return this.sortedMap.comparator();
        }

        @Override
        public ObjectSortedSet<Reference2ByteMap.Entry<K>> reference2ByteEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.unmodifiable(this.sortedMap.reference2ByteEntrySet());
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<K, Byte>> entrySet() {
            return this.reference2ByteEntrySet();
        }

        @Override
        public ReferenceSortedSet<K> keySet() {
            if (this.keys == null) {
                this.keys = ReferenceSortedSets.unmodifiable(this.sortedMap.keySet());
            }
            return (ReferenceSortedSet)this.keys;
        }

        @Override
        public Reference2ByteSortedMap<K> subMap(K k, K k2) {
            return new UnmodifiableSortedMap<K>(this.sortedMap.subMap((Object)k, (Object)k2));
        }

        @Override
        public Reference2ByteSortedMap<K> headMap(K k) {
            return new UnmodifiableSortedMap<K>(this.sortedMap.headMap((Object)k));
        }

        @Override
        public Reference2ByteSortedMap<K> tailMap(K k) {
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
        public ReferenceSet keySet() {
            return this.keySet();
        }

        @Override
        @Deprecated
        public ObjectSet entrySet() {
            return this.entrySet();
        }

        @Override
        public ObjectSet reference2ByteEntrySet() {
            return this.reference2ByteEntrySet();
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
    extends Reference2ByteMaps.SynchronizedMap<K>
    implements Reference2ByteSortedMap<K>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Reference2ByteSortedMap<K> sortedMap;

        protected SynchronizedSortedMap(Reference2ByteSortedMap<K> reference2ByteSortedMap, Object object) {
            super(reference2ByteSortedMap, object);
            this.sortedMap = reference2ByteSortedMap;
        }

        protected SynchronizedSortedMap(Reference2ByteSortedMap<K> reference2ByteSortedMap) {
            super(reference2ByteSortedMap);
            this.sortedMap = reference2ByteSortedMap;
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
        public ObjectSortedSet<Reference2ByteMap.Entry<K>> reference2ByteEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.synchronize(this.sortedMap.reference2ByteEntrySet(), this.sync);
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<K, Byte>> entrySet() {
            return this.reference2ByteEntrySet();
        }

        @Override
        public ReferenceSortedSet<K> keySet() {
            if (this.keys == null) {
                this.keys = ReferenceSortedSets.synchronize(this.sortedMap.keySet(), this.sync);
            }
            return (ReferenceSortedSet)this.keys;
        }

        @Override
        public Reference2ByteSortedMap<K> subMap(K k, K k2) {
            return new SynchronizedSortedMap<K>(this.sortedMap.subMap((Object)k, (Object)k2), this.sync);
        }

        @Override
        public Reference2ByteSortedMap<K> headMap(K k) {
            return new SynchronizedSortedMap<K>(this.sortedMap.headMap((Object)k), this.sync);
        }

        @Override
        public Reference2ByteSortedMap<K> tailMap(K k) {
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
        public ReferenceSet keySet() {
            return this.keySet();
        }

        @Override
        @Deprecated
        public ObjectSet entrySet() {
            return this.entrySet();
        }

        @Override
        public ObjectSet reference2ByteEntrySet() {
            return this.reference2ByteEntrySet();
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
    extends Reference2ByteMaps.Singleton<K>
    implements Reference2ByteSortedMap<K>,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Comparator<? super K> comparator;

        protected Singleton(K k, byte by, Comparator<? super K> comparator) {
            super(k, by);
            this.comparator = comparator;
        }

        protected Singleton(K k, byte by) {
            this(k, by, null);
        }

        final int compare(K k, K k2) {
            return this.comparator == null ? ((Comparable)k).compareTo(k2) : this.comparator.compare(k, k2);
        }

        @Override
        public Comparator<? super K> comparator() {
            return this.comparator;
        }

        @Override
        public ObjectSortedSet<Reference2ByteMap.Entry<K>> reference2ByteEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.singleton(new AbstractReference2ByteMap.BasicEntry<Object>(this.key, this.value), Reference2ByteSortedMaps.entryComparator(this.comparator));
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<K, Byte>> entrySet() {
            return this.reference2ByteEntrySet();
        }

        @Override
        public ReferenceSortedSet<K> keySet() {
            if (this.keys == null) {
                this.keys = ReferenceSortedSets.singleton(this.key, this.comparator);
            }
            return (ReferenceSortedSet)this.keys;
        }

        @Override
        public Reference2ByteSortedMap<K> subMap(K k, K k2) {
            if (this.compare(k, this.key) <= 0 && this.compare(this.key, k2) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Reference2ByteSortedMap<K> headMap(K k) {
            if (this.compare(this.key, k) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Reference2ByteSortedMap<K> tailMap(K k) {
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
        @Deprecated
        public ObjectSet entrySet() {
            return this.entrySet();
        }

        @Override
        public ObjectSet reference2ByteEntrySet() {
            return this.reference2ByteEntrySet();
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
    extends Reference2ByteMaps.EmptyMap<K>
    implements Reference2ByteSortedMap<K>,
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
        public ObjectSortedSet<Reference2ByteMap.Entry<K>> reference2ByteEntrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<K, Byte>> entrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        public ReferenceSortedSet<K> keySet() {
            return ReferenceSortedSets.EMPTY_SET;
        }

        @Override
        public Reference2ByteSortedMap<K> subMap(K k, K k2) {
            return EMPTY_MAP;
        }

        @Override
        public Reference2ByteSortedMap<K> headMap(K k) {
            return EMPTY_MAP;
        }

        @Override
        public Reference2ByteSortedMap<K> tailMap(K k) {
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
        public ObjectSet reference2ByteEntrySet() {
            return this.reference2ByteEntrySet();
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

