/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.AbstractReference2BooleanMap;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterable;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectSortedSets;
import it.unimi.dsi.fastutil.objects.Reference2BooleanMap;
import it.unimi.dsi.fastutil.objects.Reference2BooleanMaps;
import it.unimi.dsi.fastutil.objects.Reference2BooleanSortedMap;
import it.unimi.dsi.fastutil.objects.ReferenceSet;
import it.unimi.dsi.fastutil.objects.ReferenceSortedSet;
import it.unimi.dsi.fastutil.objects.ReferenceSortedSets;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedMap;

public final class Reference2BooleanSortedMaps {
    public static final EmptySortedMap EMPTY_MAP = new EmptySortedMap();

    private Reference2BooleanSortedMaps() {
    }

    public static <K> Comparator<? super Map.Entry<K, ?>> entryComparator(Comparator<? super K> comparator) {
        return (arg_0, arg_1) -> Reference2BooleanSortedMaps.lambda$entryComparator$0(comparator, arg_0, arg_1);
    }

    public static <K> ObjectBidirectionalIterator<Reference2BooleanMap.Entry<K>> fastIterator(Reference2BooleanSortedMap<K> reference2BooleanSortedMap) {
        ObjectSet objectSet = reference2BooleanSortedMap.reference2BooleanEntrySet();
        return objectSet instanceof Reference2BooleanSortedMap.FastSortedEntrySet ? ((Reference2BooleanSortedMap.FastSortedEntrySet)objectSet).fastIterator() : objectSet.iterator();
    }

    public static <K> ObjectBidirectionalIterable<Reference2BooleanMap.Entry<K>> fastIterable(Reference2BooleanSortedMap<K> reference2BooleanSortedMap) {
        ObjectSet objectSet = reference2BooleanSortedMap.reference2BooleanEntrySet();
        return objectSet instanceof Reference2BooleanSortedMap.FastSortedEntrySet ? ((Reference2BooleanSortedMap.FastSortedEntrySet)objectSet)::fastIterator : objectSet;
    }

    public static <K> Reference2BooleanSortedMap<K> emptyMap() {
        return EMPTY_MAP;
    }

    public static <K> Reference2BooleanSortedMap<K> singleton(K k, Boolean bl) {
        return new Singleton<K>(k, bl);
    }

    public static <K> Reference2BooleanSortedMap<K> singleton(K k, Boolean bl, Comparator<? super K> comparator) {
        return new Singleton<K>(k, bl, comparator);
    }

    public static <K> Reference2BooleanSortedMap<K> singleton(K k, boolean bl) {
        return new Singleton<K>(k, bl);
    }

    public static <K> Reference2BooleanSortedMap<K> singleton(K k, boolean bl, Comparator<? super K> comparator) {
        return new Singleton<K>(k, bl, comparator);
    }

    public static <K> Reference2BooleanSortedMap<K> synchronize(Reference2BooleanSortedMap<K> reference2BooleanSortedMap) {
        return new SynchronizedSortedMap<K>(reference2BooleanSortedMap);
    }

    public static <K> Reference2BooleanSortedMap<K> synchronize(Reference2BooleanSortedMap<K> reference2BooleanSortedMap, Object object) {
        return new SynchronizedSortedMap<K>(reference2BooleanSortedMap, object);
    }

    public static <K> Reference2BooleanSortedMap<K> unmodifiable(Reference2BooleanSortedMap<K> reference2BooleanSortedMap) {
        return new UnmodifiableSortedMap<K>(reference2BooleanSortedMap);
    }

    private static int lambda$entryComparator$0(Comparator comparator, Map.Entry entry, Map.Entry entry2) {
        return comparator.compare(entry.getKey(), entry2.getKey());
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableSortedMap<K>
    extends Reference2BooleanMaps.UnmodifiableMap<K>
    implements Reference2BooleanSortedMap<K>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Reference2BooleanSortedMap<K> sortedMap;

        protected UnmodifiableSortedMap(Reference2BooleanSortedMap<K> reference2BooleanSortedMap) {
            super(reference2BooleanSortedMap);
            this.sortedMap = reference2BooleanSortedMap;
        }

        @Override
        public Comparator<? super K> comparator() {
            return this.sortedMap.comparator();
        }

        @Override
        public ObjectSortedSet<Reference2BooleanMap.Entry<K>> reference2BooleanEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.unmodifiable(this.sortedMap.reference2BooleanEntrySet());
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<K, Boolean>> entrySet() {
            return this.reference2BooleanEntrySet();
        }

        @Override
        public ReferenceSortedSet<K> keySet() {
            if (this.keys == null) {
                this.keys = ReferenceSortedSets.unmodifiable(this.sortedMap.keySet());
            }
            return (ReferenceSortedSet)this.keys;
        }

        @Override
        public Reference2BooleanSortedMap<K> subMap(K k, K k2) {
            return new UnmodifiableSortedMap<K>(this.sortedMap.subMap((Object)k, (Object)k2));
        }

        @Override
        public Reference2BooleanSortedMap<K> headMap(K k) {
            return new UnmodifiableSortedMap<K>(this.sortedMap.headMap((Object)k));
        }

        @Override
        public Reference2BooleanSortedMap<K> tailMap(K k) {
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
        public ObjectSet reference2BooleanEntrySet() {
            return this.reference2BooleanEntrySet();
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
    extends Reference2BooleanMaps.SynchronizedMap<K>
    implements Reference2BooleanSortedMap<K>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Reference2BooleanSortedMap<K> sortedMap;

        protected SynchronizedSortedMap(Reference2BooleanSortedMap<K> reference2BooleanSortedMap, Object object) {
            super(reference2BooleanSortedMap, object);
            this.sortedMap = reference2BooleanSortedMap;
        }

        protected SynchronizedSortedMap(Reference2BooleanSortedMap<K> reference2BooleanSortedMap) {
            super(reference2BooleanSortedMap);
            this.sortedMap = reference2BooleanSortedMap;
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
        public ObjectSortedSet<Reference2BooleanMap.Entry<K>> reference2BooleanEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.synchronize(this.sortedMap.reference2BooleanEntrySet(), this.sync);
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<K, Boolean>> entrySet() {
            return this.reference2BooleanEntrySet();
        }

        @Override
        public ReferenceSortedSet<K> keySet() {
            if (this.keys == null) {
                this.keys = ReferenceSortedSets.synchronize(this.sortedMap.keySet(), this.sync);
            }
            return (ReferenceSortedSet)this.keys;
        }

        @Override
        public Reference2BooleanSortedMap<K> subMap(K k, K k2) {
            return new SynchronizedSortedMap<K>(this.sortedMap.subMap((Object)k, (Object)k2), this.sync);
        }

        @Override
        public Reference2BooleanSortedMap<K> headMap(K k) {
            return new SynchronizedSortedMap<K>(this.sortedMap.headMap((Object)k), this.sync);
        }

        @Override
        public Reference2BooleanSortedMap<K> tailMap(K k) {
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
        public ObjectSet reference2BooleanEntrySet() {
            return this.reference2BooleanEntrySet();
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
    extends Reference2BooleanMaps.Singleton<K>
    implements Reference2BooleanSortedMap<K>,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Comparator<? super K> comparator;

        protected Singleton(K k, boolean bl, Comparator<? super K> comparator) {
            super(k, bl);
            this.comparator = comparator;
        }

        protected Singleton(K k, boolean bl) {
            this(k, bl, null);
        }

        final int compare(K k, K k2) {
            return this.comparator == null ? ((Comparable)k).compareTo(k2) : this.comparator.compare(k, k2);
        }

        @Override
        public Comparator<? super K> comparator() {
            return this.comparator;
        }

        @Override
        public ObjectSortedSet<Reference2BooleanMap.Entry<K>> reference2BooleanEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.singleton(new AbstractReference2BooleanMap.BasicEntry<Object>(this.key, this.value), Reference2BooleanSortedMaps.entryComparator(this.comparator));
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<K, Boolean>> entrySet() {
            return this.reference2BooleanEntrySet();
        }

        @Override
        public ReferenceSortedSet<K> keySet() {
            if (this.keys == null) {
                this.keys = ReferenceSortedSets.singleton(this.key, this.comparator);
            }
            return (ReferenceSortedSet)this.keys;
        }

        @Override
        public Reference2BooleanSortedMap<K> subMap(K k, K k2) {
            if (this.compare(k, this.key) <= 0 && this.compare(this.key, k2) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Reference2BooleanSortedMap<K> headMap(K k) {
            if (this.compare(this.key, k) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Reference2BooleanSortedMap<K> tailMap(K k) {
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
        public ObjectSet reference2BooleanEntrySet() {
            return this.reference2BooleanEntrySet();
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
    extends Reference2BooleanMaps.EmptyMap<K>
    implements Reference2BooleanSortedMap<K>,
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
        public ObjectSortedSet<Reference2BooleanMap.Entry<K>> reference2BooleanEntrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<K, Boolean>> entrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        public ReferenceSortedSet<K> keySet() {
            return ReferenceSortedSets.EMPTY_SET;
        }

        @Override
        public Reference2BooleanSortedMap<K> subMap(K k, K k2) {
            return EMPTY_MAP;
        }

        @Override
        public Reference2BooleanSortedMap<K> headMap(K k) {
            return EMPTY_MAP;
        }

        @Override
        public Reference2BooleanSortedMap<K> tailMap(K k) {
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
        public ObjectSet reference2BooleanEntrySet() {
            return this.reference2BooleanEntrySet();
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

