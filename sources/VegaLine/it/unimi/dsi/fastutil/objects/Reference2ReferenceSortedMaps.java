/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectSortedSets;
import it.unimi.dsi.fastutil.objects.Reference2ReferenceMap;
import it.unimi.dsi.fastutil.objects.Reference2ReferenceMaps;
import it.unimi.dsi.fastutil.objects.Reference2ReferenceSortedMap;
import it.unimi.dsi.fastutil.objects.ReferenceSortedSet;
import it.unimi.dsi.fastutil.objects.ReferenceSortedSets;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;
import java.util.NoSuchElementException;

public class Reference2ReferenceSortedMaps {
    public static final EmptySortedMap EMPTY_MAP = new EmptySortedMap();

    private Reference2ReferenceSortedMaps() {
    }

    public static <K> Comparator<? super Map.Entry<K, ?>> entryComparator(final Comparator<K> comparator) {
        return new Comparator<Map.Entry<K, ?>>(){

            @Override
            public int compare(Map.Entry<K, ?> x, Map.Entry<K, ?> y) {
                return comparator.compare(x.getKey(), y.getKey());
            }
        };
    }

    public static <K, V> Reference2ReferenceSortedMap<K, V> emptyMap() {
        return EMPTY_MAP;
    }

    public static <K, V> Reference2ReferenceSortedMap<K, V> singleton(K key, V value) {
        return new Singleton<K, V>(key, value);
    }

    public static <K, V> Reference2ReferenceSortedMap<K, V> singleton(K key, V value, Comparator<? super K> comparator) {
        return new Singleton<K, V>(key, value, comparator);
    }

    public static <K, V> Reference2ReferenceSortedMap<K, V> synchronize(Reference2ReferenceSortedMap<K, V> m) {
        return new SynchronizedSortedMap<K, V>(m);
    }

    public static <K, V> Reference2ReferenceSortedMap<K, V> synchronize(Reference2ReferenceSortedMap<K, V> m, Object sync) {
        return new SynchronizedSortedMap<K, V>(m, sync);
    }

    public static <K, V> Reference2ReferenceSortedMap<K, V> unmodifiable(Reference2ReferenceSortedMap<K, V> m) {
        return new UnmodifiableSortedMap<K, V>(m);
    }

    public static class UnmodifiableSortedMap<K, V>
    extends Reference2ReferenceMaps.UnmodifiableMap<K, V>
    implements Reference2ReferenceSortedMap<K, V>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Reference2ReferenceSortedMap<K, V> sortedMap;

        protected UnmodifiableSortedMap(Reference2ReferenceSortedMap<K, V> m) {
            super(m);
            this.sortedMap = m;
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
        public Reference2ReferenceSortedMap<K, V> subMap(K from, K to) {
            return new UnmodifiableSortedMap<K, V>(this.sortedMap.subMap(from, to));
        }

        @Override
        public Reference2ReferenceSortedMap<K, V> headMap(K to) {
            return new UnmodifiableSortedMap<K, V>(this.sortedMap.headMap(to));
        }

        @Override
        public Reference2ReferenceSortedMap<K, V> tailMap(K from) {
            return new UnmodifiableSortedMap<K, V>(this.sortedMap.tailMap(from));
        }

        @Override
        public K firstKey() {
            return this.sortedMap.firstKey();
        }

        @Override
        public K lastKey() {
            return this.sortedMap.lastKey();
        }
    }

    public static class SynchronizedSortedMap<K, V>
    extends Reference2ReferenceMaps.SynchronizedMap<K, V>
    implements Reference2ReferenceSortedMap<K, V>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Reference2ReferenceSortedMap<K, V> sortedMap;

        protected SynchronizedSortedMap(Reference2ReferenceSortedMap<K, V> m, Object sync) {
            super(m, sync);
            this.sortedMap = m;
        }

        protected SynchronizedSortedMap(Reference2ReferenceSortedMap<K, V> m) {
            super(m);
            this.sortedMap = m;
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
        public Reference2ReferenceSortedMap<K, V> subMap(K from, K to) {
            return new SynchronizedSortedMap<K, V>(this.sortedMap.subMap(from, to), this.sync);
        }

        @Override
        public Reference2ReferenceSortedMap<K, V> headMap(K to) {
            return new SynchronizedSortedMap<K, V>(this.sortedMap.headMap(to), this.sync);
        }

        @Override
        public Reference2ReferenceSortedMap<K, V> tailMap(K from) {
            return new SynchronizedSortedMap<K, V>(this.sortedMap.tailMap(from), this.sync);
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
    }

    public static class Singleton<K, V>
    extends Reference2ReferenceMaps.Singleton<K, V>
    implements Reference2ReferenceSortedMap<K, V>,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Comparator<? super K> comparator;

        protected Singleton(K key, V value, Comparator<? super K> comparator) {
            super(key, value);
            this.comparator = comparator;
        }

        protected Singleton(K key, V value) {
            this(key, value, null);
        }

        final int compare(K k1, K k2) {
            return this.comparator == null ? ((Comparable)k1).compareTo(k2) : this.comparator.compare(k1, k2);
        }

        @Override
        public Comparator<? super K> comparator() {
            return this.comparator;
        }

        @Override
        public ObjectSortedSet<Reference2ReferenceMap.Entry<K, V>> reference2ReferenceEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.singleton(new Reference2ReferenceMaps.Singleton.SingletonEntry(), Reference2ReferenceSortedMaps.entryComparator(this.comparator));
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
        public Reference2ReferenceSortedMap<K, V> subMap(K from, K to) {
            if (this.compare(from, this.key) <= 0 && this.compare(this.key, to) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Reference2ReferenceSortedMap<K, V> headMap(K to) {
            if (this.compare(this.key, to) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Reference2ReferenceSortedMap<K, V> tailMap(K from) {
            if (this.compare(from, this.key) <= 0) {
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
    }

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
        public Reference2ReferenceSortedMap<K, V> subMap(K from, K to) {
            return EMPTY_MAP;
        }

        @Override
        public Reference2ReferenceSortedMap<K, V> headMap(K to) {
            return EMPTY_MAP;
        }

        @Override
        public Reference2ReferenceSortedMap<K, V> tailMap(K from) {
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
    }
}

