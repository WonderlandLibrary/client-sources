/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.AbstractReference2CharMap;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterable;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectSortedSets;
import it.unimi.dsi.fastutil.objects.Reference2CharMap;
import it.unimi.dsi.fastutil.objects.Reference2CharMaps;
import it.unimi.dsi.fastutil.objects.Reference2CharSortedMap;
import it.unimi.dsi.fastutil.objects.ReferenceSet;
import it.unimi.dsi.fastutil.objects.ReferenceSortedSet;
import it.unimi.dsi.fastutil.objects.ReferenceSortedSets;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedMap;

public final class Reference2CharSortedMaps {
    public static final EmptySortedMap EMPTY_MAP = new EmptySortedMap();

    private Reference2CharSortedMaps() {
    }

    public static <K> Comparator<? super Map.Entry<K, ?>> entryComparator(Comparator<? super K> comparator) {
        return (arg_0, arg_1) -> Reference2CharSortedMaps.lambda$entryComparator$0(comparator, arg_0, arg_1);
    }

    public static <K> ObjectBidirectionalIterator<Reference2CharMap.Entry<K>> fastIterator(Reference2CharSortedMap<K> reference2CharSortedMap) {
        ObjectSet objectSet = reference2CharSortedMap.reference2CharEntrySet();
        return objectSet instanceof Reference2CharSortedMap.FastSortedEntrySet ? ((Reference2CharSortedMap.FastSortedEntrySet)objectSet).fastIterator() : objectSet.iterator();
    }

    public static <K> ObjectBidirectionalIterable<Reference2CharMap.Entry<K>> fastIterable(Reference2CharSortedMap<K> reference2CharSortedMap) {
        ObjectSet objectSet = reference2CharSortedMap.reference2CharEntrySet();
        return objectSet instanceof Reference2CharSortedMap.FastSortedEntrySet ? ((Reference2CharSortedMap.FastSortedEntrySet)objectSet)::fastIterator : objectSet;
    }

    public static <K> Reference2CharSortedMap<K> emptyMap() {
        return EMPTY_MAP;
    }

    public static <K> Reference2CharSortedMap<K> singleton(K k, Character c) {
        return new Singleton<K>(k, c.charValue());
    }

    public static <K> Reference2CharSortedMap<K> singleton(K k, Character c, Comparator<? super K> comparator) {
        return new Singleton<K>(k, c.charValue(), comparator);
    }

    public static <K> Reference2CharSortedMap<K> singleton(K k, char c) {
        return new Singleton<K>(k, c);
    }

    public static <K> Reference2CharSortedMap<K> singleton(K k, char c, Comparator<? super K> comparator) {
        return new Singleton<K>(k, c, comparator);
    }

    public static <K> Reference2CharSortedMap<K> synchronize(Reference2CharSortedMap<K> reference2CharSortedMap) {
        return new SynchronizedSortedMap<K>(reference2CharSortedMap);
    }

    public static <K> Reference2CharSortedMap<K> synchronize(Reference2CharSortedMap<K> reference2CharSortedMap, Object object) {
        return new SynchronizedSortedMap<K>(reference2CharSortedMap, object);
    }

    public static <K> Reference2CharSortedMap<K> unmodifiable(Reference2CharSortedMap<K> reference2CharSortedMap) {
        return new UnmodifiableSortedMap<K>(reference2CharSortedMap);
    }

    private static int lambda$entryComparator$0(Comparator comparator, Map.Entry entry, Map.Entry entry2) {
        return comparator.compare(entry.getKey(), entry2.getKey());
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableSortedMap<K>
    extends Reference2CharMaps.UnmodifiableMap<K>
    implements Reference2CharSortedMap<K>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Reference2CharSortedMap<K> sortedMap;

        protected UnmodifiableSortedMap(Reference2CharSortedMap<K> reference2CharSortedMap) {
            super(reference2CharSortedMap);
            this.sortedMap = reference2CharSortedMap;
        }

        @Override
        public Comparator<? super K> comparator() {
            return this.sortedMap.comparator();
        }

        @Override
        public ObjectSortedSet<Reference2CharMap.Entry<K>> reference2CharEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.unmodifiable(this.sortedMap.reference2CharEntrySet());
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<K, Character>> entrySet() {
            return this.reference2CharEntrySet();
        }

        @Override
        public ReferenceSortedSet<K> keySet() {
            if (this.keys == null) {
                this.keys = ReferenceSortedSets.unmodifiable(this.sortedMap.keySet());
            }
            return (ReferenceSortedSet)this.keys;
        }

        @Override
        public Reference2CharSortedMap<K> subMap(K k, K k2) {
            return new UnmodifiableSortedMap<K>(this.sortedMap.subMap((Object)k, (Object)k2));
        }

        @Override
        public Reference2CharSortedMap<K> headMap(K k) {
            return new UnmodifiableSortedMap<K>(this.sortedMap.headMap((Object)k));
        }

        @Override
        public Reference2CharSortedMap<K> tailMap(K k) {
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
        public ObjectSet reference2CharEntrySet() {
            return this.reference2CharEntrySet();
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
    extends Reference2CharMaps.SynchronizedMap<K>
    implements Reference2CharSortedMap<K>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Reference2CharSortedMap<K> sortedMap;

        protected SynchronizedSortedMap(Reference2CharSortedMap<K> reference2CharSortedMap, Object object) {
            super(reference2CharSortedMap, object);
            this.sortedMap = reference2CharSortedMap;
        }

        protected SynchronizedSortedMap(Reference2CharSortedMap<K> reference2CharSortedMap) {
            super(reference2CharSortedMap);
            this.sortedMap = reference2CharSortedMap;
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
        public ObjectSortedSet<Reference2CharMap.Entry<K>> reference2CharEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.synchronize(this.sortedMap.reference2CharEntrySet(), this.sync);
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<K, Character>> entrySet() {
            return this.reference2CharEntrySet();
        }

        @Override
        public ReferenceSortedSet<K> keySet() {
            if (this.keys == null) {
                this.keys = ReferenceSortedSets.synchronize(this.sortedMap.keySet(), this.sync);
            }
            return (ReferenceSortedSet)this.keys;
        }

        @Override
        public Reference2CharSortedMap<K> subMap(K k, K k2) {
            return new SynchronizedSortedMap<K>(this.sortedMap.subMap((Object)k, (Object)k2), this.sync);
        }

        @Override
        public Reference2CharSortedMap<K> headMap(K k) {
            return new SynchronizedSortedMap<K>(this.sortedMap.headMap((Object)k), this.sync);
        }

        @Override
        public Reference2CharSortedMap<K> tailMap(K k) {
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
        public ObjectSet reference2CharEntrySet() {
            return this.reference2CharEntrySet();
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
    extends Reference2CharMaps.Singleton<K>
    implements Reference2CharSortedMap<K>,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Comparator<? super K> comparator;

        protected Singleton(K k, char c, Comparator<? super K> comparator) {
            super(k, c);
            this.comparator = comparator;
        }

        protected Singleton(K k, char c) {
            this(k, c, null);
        }

        final int compare(K k, K k2) {
            return this.comparator == null ? ((Comparable)k).compareTo(k2) : this.comparator.compare(k, k2);
        }

        @Override
        public Comparator<? super K> comparator() {
            return this.comparator;
        }

        @Override
        public ObjectSortedSet<Reference2CharMap.Entry<K>> reference2CharEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.singleton(new AbstractReference2CharMap.BasicEntry<Object>(this.key, this.value), Reference2CharSortedMaps.entryComparator(this.comparator));
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<K, Character>> entrySet() {
            return this.reference2CharEntrySet();
        }

        @Override
        public ReferenceSortedSet<K> keySet() {
            if (this.keys == null) {
                this.keys = ReferenceSortedSets.singleton(this.key, this.comparator);
            }
            return (ReferenceSortedSet)this.keys;
        }

        @Override
        public Reference2CharSortedMap<K> subMap(K k, K k2) {
            if (this.compare(k, this.key) <= 0 && this.compare(this.key, k2) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Reference2CharSortedMap<K> headMap(K k) {
            if (this.compare(this.key, k) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Reference2CharSortedMap<K> tailMap(K k) {
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
        public ObjectSet reference2CharEntrySet() {
            return this.reference2CharEntrySet();
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
    extends Reference2CharMaps.EmptyMap<K>
    implements Reference2CharSortedMap<K>,
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
        public ObjectSortedSet<Reference2CharMap.Entry<K>> reference2CharEntrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<K, Character>> entrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        public ReferenceSortedSet<K> keySet() {
            return ReferenceSortedSets.EMPTY_SET;
        }

        @Override
        public Reference2CharSortedMap<K> subMap(K k, K k2) {
            return EMPTY_MAP;
        }

        @Override
        public Reference2CharSortedMap<K> headMap(K k) {
            return EMPTY_MAP;
        }

        @Override
        public Reference2CharSortedMap<K> tailMap(K k) {
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
        public ObjectSet reference2CharEntrySet() {
            return this.reference2CharEntrySet();
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

