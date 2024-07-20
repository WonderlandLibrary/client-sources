/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.Float2ReferenceMap;
import it.unimi.dsi.fastutil.floats.Float2ReferenceMaps;
import it.unimi.dsi.fastutil.floats.Float2ReferenceSortedMap;
import it.unimi.dsi.fastutil.floats.FloatComparator;
import it.unimi.dsi.fastutil.floats.FloatSortedSet;
import it.unimi.dsi.fastutil.floats.FloatSortedSets;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectSortedSets;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;
import java.util.NoSuchElementException;

public class Float2ReferenceSortedMaps {
    public static final EmptySortedMap EMPTY_MAP = new EmptySortedMap();

    private Float2ReferenceSortedMaps() {
    }

    public static Comparator<? super Map.Entry<Float, ?>> entryComparator(final FloatComparator comparator) {
        return new Comparator<Map.Entry<Float, ?>>(){

            @Override
            public int compare(Map.Entry<Float, ?> x, Map.Entry<Float, ?> y) {
                return comparator.compare(x.getKey(), y.getKey());
            }
        };
    }

    public static <V> Float2ReferenceSortedMap<V> emptyMap() {
        return EMPTY_MAP;
    }

    public static <V> Float2ReferenceSortedMap<V> singleton(Float key, V value) {
        return new Singleton<V>(key.floatValue(), value);
    }

    public static <V> Float2ReferenceSortedMap<V> singleton(Float key, V value, FloatComparator comparator) {
        return new Singleton<V>(key.floatValue(), value, comparator);
    }

    public static <V> Float2ReferenceSortedMap<V> singleton(float key, V value) {
        return new Singleton<V>(key, value);
    }

    public static <V> Float2ReferenceSortedMap<V> singleton(float key, V value, FloatComparator comparator) {
        return new Singleton<V>(key, value, comparator);
    }

    public static <V> Float2ReferenceSortedMap<V> synchronize(Float2ReferenceSortedMap<V> m) {
        return new SynchronizedSortedMap<V>(m);
    }

    public static <V> Float2ReferenceSortedMap<V> synchronize(Float2ReferenceSortedMap<V> m, Object sync) {
        return new SynchronizedSortedMap<V>(m, sync);
    }

    public static <V> Float2ReferenceSortedMap<V> unmodifiable(Float2ReferenceSortedMap<V> m) {
        return new UnmodifiableSortedMap<V>(m);
    }

    public static class UnmodifiableSortedMap<V>
    extends Float2ReferenceMaps.UnmodifiableMap<V>
    implements Float2ReferenceSortedMap<V>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Float2ReferenceSortedMap<V> sortedMap;

        protected UnmodifiableSortedMap(Float2ReferenceSortedMap<V> m) {
            super(m);
            this.sortedMap = m;
        }

        @Override
        public FloatComparator comparator() {
            return this.sortedMap.comparator();
        }

        @Override
        public ObjectSortedSet<Float2ReferenceMap.Entry<V>> float2ReferenceEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.unmodifiable(this.sortedMap.float2ReferenceEntrySet());
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        public ObjectSortedSet<Map.Entry<Float, V>> entrySet() {
            return this.float2ReferenceEntrySet();
        }

        @Override
        public FloatSortedSet keySet() {
            if (this.keys == null) {
                this.keys = FloatSortedSets.unmodifiable(this.sortedMap.keySet());
            }
            return (FloatSortedSet)this.keys;
        }

        @Override
        public Float2ReferenceSortedMap<V> subMap(float from, float to) {
            return new UnmodifiableSortedMap<V>(this.sortedMap.subMap(from, to));
        }

        @Override
        public Float2ReferenceSortedMap<V> headMap(float to) {
            return new UnmodifiableSortedMap<V>(this.sortedMap.headMap(to));
        }

        @Override
        public Float2ReferenceSortedMap<V> tailMap(float from) {
            return new UnmodifiableSortedMap<V>(this.sortedMap.tailMap(from));
        }

        @Override
        public float firstFloatKey() {
            return this.sortedMap.firstFloatKey();
        }

        @Override
        public float lastFloatKey() {
            return this.sortedMap.lastFloatKey();
        }

        @Override
        public Float firstKey() {
            return (Float)this.sortedMap.firstKey();
        }

        @Override
        public Float lastKey() {
            return (Float)this.sortedMap.lastKey();
        }

        @Override
        public Float2ReferenceSortedMap<V> subMap(Float from, Float to) {
            return new UnmodifiableSortedMap<V>(this.sortedMap.subMap(from, to));
        }

        @Override
        public Float2ReferenceSortedMap<V> headMap(Float to) {
            return new UnmodifiableSortedMap<V>(this.sortedMap.headMap(to));
        }

        @Override
        public Float2ReferenceSortedMap<V> tailMap(Float from) {
            return new UnmodifiableSortedMap<V>(this.sortedMap.tailMap(from));
        }
    }

    public static class SynchronizedSortedMap<V>
    extends Float2ReferenceMaps.SynchronizedMap<V>
    implements Float2ReferenceSortedMap<V>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Float2ReferenceSortedMap<V> sortedMap;

        protected SynchronizedSortedMap(Float2ReferenceSortedMap<V> m, Object sync) {
            super(m, sync);
            this.sortedMap = m;
        }

        protected SynchronizedSortedMap(Float2ReferenceSortedMap<V> m) {
            super(m);
            this.sortedMap = m;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public FloatComparator comparator() {
            Object object = this.sync;
            synchronized (object) {
                return this.sortedMap.comparator();
            }
        }

        @Override
        public ObjectSortedSet<Float2ReferenceMap.Entry<V>> float2ReferenceEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.synchronize(this.sortedMap.float2ReferenceEntrySet(), this.sync);
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        public ObjectSortedSet<Map.Entry<Float, V>> entrySet() {
            return this.float2ReferenceEntrySet();
        }

        @Override
        public FloatSortedSet keySet() {
            if (this.keys == null) {
                this.keys = FloatSortedSets.synchronize(this.sortedMap.keySet(), this.sync);
            }
            return (FloatSortedSet)this.keys;
        }

        @Override
        public Float2ReferenceSortedMap<V> subMap(float from, float to) {
            return new SynchronizedSortedMap<V>(this.sortedMap.subMap(from, to), this.sync);
        }

        @Override
        public Float2ReferenceSortedMap<V> headMap(float to) {
            return new SynchronizedSortedMap<V>(this.sortedMap.headMap(to), this.sync);
        }

        @Override
        public Float2ReferenceSortedMap<V> tailMap(float from) {
            return new SynchronizedSortedMap<V>(this.sortedMap.tailMap(from), this.sync);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public float firstFloatKey() {
            Object object = this.sync;
            synchronized (object) {
                return this.sortedMap.firstFloatKey();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public float lastFloatKey() {
            Object object = this.sync;
            synchronized (object) {
                return this.sortedMap.lastFloatKey();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Float firstKey() {
            Object object = this.sync;
            synchronized (object) {
                return (Float)this.sortedMap.firstKey();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Float lastKey() {
            Object object = this.sync;
            synchronized (object) {
                return (Float)this.sortedMap.lastKey();
            }
        }

        @Override
        public Float2ReferenceSortedMap<V> subMap(Float from, Float to) {
            return new SynchronizedSortedMap<V>(this.sortedMap.subMap(from, to), this.sync);
        }

        @Override
        public Float2ReferenceSortedMap<V> headMap(Float to) {
            return new SynchronizedSortedMap<V>(this.sortedMap.headMap(to), this.sync);
        }

        @Override
        public Float2ReferenceSortedMap<V> tailMap(Float from) {
            return new SynchronizedSortedMap<V>(this.sortedMap.tailMap(from), this.sync);
        }
    }

    public static class Singleton<V>
    extends Float2ReferenceMaps.Singleton<V>
    implements Float2ReferenceSortedMap<V>,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final FloatComparator comparator;

        protected Singleton(float key, V value, FloatComparator comparator) {
            super(key, value);
            this.comparator = comparator;
        }

        protected Singleton(float key, V value) {
            this(key, value, null);
        }

        final int compare(float k1, float k2) {
            return this.comparator == null ? Float.compare(k1, k2) : this.comparator.compare(k1, k2);
        }

        @Override
        public FloatComparator comparator() {
            return this.comparator;
        }

        @Override
        public ObjectSortedSet<Float2ReferenceMap.Entry<V>> float2ReferenceEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.singleton(new Float2ReferenceMaps.Singleton.SingletonEntry(), Float2ReferenceSortedMaps.entryComparator(this.comparator));
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        public ObjectSortedSet<Map.Entry<Float, V>> entrySet() {
            return this.float2ReferenceEntrySet();
        }

        @Override
        public FloatSortedSet keySet() {
            if (this.keys == null) {
                this.keys = FloatSortedSets.singleton(this.key, this.comparator);
            }
            return (FloatSortedSet)this.keys;
        }

        @Override
        public Float2ReferenceSortedMap<V> subMap(float from, float to) {
            if (this.compare(from, this.key) <= 0 && this.compare(this.key, to) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Float2ReferenceSortedMap<V> headMap(float to) {
            if (this.compare(this.key, to) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Float2ReferenceSortedMap<V> tailMap(float from) {
            if (this.compare(from, this.key) <= 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public float firstFloatKey() {
            return this.key;
        }

        @Override
        public float lastFloatKey() {
            return this.key;
        }

        @Override
        @Deprecated
        public Float2ReferenceSortedMap<V> headMap(Float oto) {
            return this.headMap(oto.floatValue());
        }

        @Override
        @Deprecated
        public Float2ReferenceSortedMap<V> tailMap(Float ofrom) {
            return this.tailMap(ofrom.floatValue());
        }

        @Override
        @Deprecated
        public Float2ReferenceSortedMap<V> subMap(Float ofrom, Float oto) {
            return this.subMap(ofrom.floatValue(), oto.floatValue());
        }

        @Override
        @Deprecated
        public Float firstKey() {
            return Float.valueOf(this.firstFloatKey());
        }

        @Override
        @Deprecated
        public Float lastKey() {
            return Float.valueOf(this.lastFloatKey());
        }
    }

    public static class EmptySortedMap<V>
    extends Float2ReferenceMaps.EmptyMap<V>
    implements Float2ReferenceSortedMap<V>,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptySortedMap() {
        }

        @Override
        public FloatComparator comparator() {
            return null;
        }

        @Override
        public ObjectSortedSet<Float2ReferenceMap.Entry<V>> float2ReferenceEntrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        public ObjectSortedSet<Map.Entry<Float, V>> entrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        public FloatSortedSet keySet() {
            return FloatSortedSets.EMPTY_SET;
        }

        @Override
        public Float2ReferenceSortedMap<V> subMap(float from, float to) {
            return EMPTY_MAP;
        }

        @Override
        public Float2ReferenceSortedMap<V> headMap(float to) {
            return EMPTY_MAP;
        }

        @Override
        public Float2ReferenceSortedMap<V> tailMap(float from) {
            return EMPTY_MAP;
        }

        @Override
        public float firstFloatKey() {
            throw new NoSuchElementException();
        }

        @Override
        public float lastFloatKey() {
            throw new NoSuchElementException();
        }

        @Override
        @Deprecated
        public Float2ReferenceSortedMap<V> headMap(Float oto) {
            return this.headMap(oto.floatValue());
        }

        @Override
        @Deprecated
        public Float2ReferenceSortedMap<V> tailMap(Float ofrom) {
            return this.tailMap(ofrom.floatValue());
        }

        @Override
        @Deprecated
        public Float2ReferenceSortedMap<V> subMap(Float ofrom, Float oto) {
            return this.subMap(ofrom.floatValue(), oto.floatValue());
        }

        @Override
        @Deprecated
        public Float firstKey() {
            return Float.valueOf(this.firstFloatKey());
        }

        @Override
        @Deprecated
        public Float lastKey() {
            return Float.valueOf(this.lastFloatKey());
        }
    }
}

