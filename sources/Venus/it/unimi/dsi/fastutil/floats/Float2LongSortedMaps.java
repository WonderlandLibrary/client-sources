/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.AbstractFloat2LongMap;
import it.unimi.dsi.fastutil.floats.Float2LongMap;
import it.unimi.dsi.fastutil.floats.Float2LongMaps;
import it.unimi.dsi.fastutil.floats.Float2LongSortedMap;
import it.unimi.dsi.fastutil.floats.FloatComparator;
import it.unimi.dsi.fastutil.floats.FloatSet;
import it.unimi.dsi.fastutil.floats.FloatSortedSet;
import it.unimi.dsi.fastutil.floats.FloatSortedSets;
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

public final class Float2LongSortedMaps {
    public static final EmptySortedMap EMPTY_MAP = new EmptySortedMap();

    private Float2LongSortedMaps() {
    }

    public static Comparator<? super Map.Entry<Float, ?>> entryComparator(FloatComparator floatComparator) {
        return (arg_0, arg_1) -> Float2LongSortedMaps.lambda$entryComparator$0(floatComparator, arg_0, arg_1);
    }

    public static ObjectBidirectionalIterator<Float2LongMap.Entry> fastIterator(Float2LongSortedMap float2LongSortedMap) {
        ObjectSet objectSet = float2LongSortedMap.float2LongEntrySet();
        return objectSet instanceof Float2LongSortedMap.FastSortedEntrySet ? ((Float2LongSortedMap.FastSortedEntrySet)objectSet).fastIterator() : objectSet.iterator();
    }

    public static ObjectBidirectionalIterable<Float2LongMap.Entry> fastIterable(Float2LongSortedMap float2LongSortedMap) {
        ObjectSet objectSet = float2LongSortedMap.float2LongEntrySet();
        return objectSet instanceof Float2LongSortedMap.FastSortedEntrySet ? ((Float2LongSortedMap.FastSortedEntrySet)objectSet)::fastIterator : objectSet;
    }

    public static Float2LongSortedMap singleton(Float f, Long l) {
        return new Singleton(f.floatValue(), l);
    }

    public static Float2LongSortedMap singleton(Float f, Long l, FloatComparator floatComparator) {
        return new Singleton(f.floatValue(), l, floatComparator);
    }

    public static Float2LongSortedMap singleton(float f, long l) {
        return new Singleton(f, l);
    }

    public static Float2LongSortedMap singleton(float f, long l, FloatComparator floatComparator) {
        return new Singleton(f, l, floatComparator);
    }

    public static Float2LongSortedMap synchronize(Float2LongSortedMap float2LongSortedMap) {
        return new SynchronizedSortedMap(float2LongSortedMap);
    }

    public static Float2LongSortedMap synchronize(Float2LongSortedMap float2LongSortedMap, Object object) {
        return new SynchronizedSortedMap(float2LongSortedMap, object);
    }

    public static Float2LongSortedMap unmodifiable(Float2LongSortedMap float2LongSortedMap) {
        return new UnmodifiableSortedMap(float2LongSortedMap);
    }

    private static int lambda$entryComparator$0(FloatComparator floatComparator, Map.Entry entry, Map.Entry entry2) {
        return floatComparator.compare(((Float)entry.getKey()).floatValue(), ((Float)entry2.getKey()).floatValue());
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableSortedMap
    extends Float2LongMaps.UnmodifiableMap
    implements Float2LongSortedMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Float2LongSortedMap sortedMap;

        protected UnmodifiableSortedMap(Float2LongSortedMap float2LongSortedMap) {
            super(float2LongSortedMap);
            this.sortedMap = float2LongSortedMap;
        }

        @Override
        public FloatComparator comparator() {
            return this.sortedMap.comparator();
        }

        @Override
        public ObjectSortedSet<Float2LongMap.Entry> float2LongEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.unmodifiable(this.sortedMap.float2LongEntrySet());
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Float, Long>> entrySet() {
            return this.float2LongEntrySet();
        }

        @Override
        public FloatSortedSet keySet() {
            if (this.keys == null) {
                this.keys = FloatSortedSets.unmodifiable(this.sortedMap.keySet());
            }
            return (FloatSortedSet)this.keys;
        }

        @Override
        public Float2LongSortedMap subMap(float f, float f2) {
            return new UnmodifiableSortedMap(this.sortedMap.subMap(f, f2));
        }

        @Override
        public Float2LongSortedMap headMap(float f) {
            return new UnmodifiableSortedMap(this.sortedMap.headMap(f));
        }

        @Override
        public Float2LongSortedMap tailMap(float f) {
            return new UnmodifiableSortedMap(this.sortedMap.tailMap(f));
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
        @Deprecated
        public Float firstKey() {
            return this.sortedMap.firstKey();
        }

        @Override
        @Deprecated
        public Float lastKey() {
            return this.sortedMap.lastKey();
        }

        @Override
        @Deprecated
        public Float2LongSortedMap subMap(Float f, Float f2) {
            return new UnmodifiableSortedMap(this.sortedMap.subMap(f, f2));
        }

        @Override
        @Deprecated
        public Float2LongSortedMap headMap(Float f) {
            return new UnmodifiableSortedMap(this.sortedMap.headMap(f));
        }

        @Override
        @Deprecated
        public Float2LongSortedMap tailMap(Float f) {
            return new UnmodifiableSortedMap(this.sortedMap.tailMap(f));
        }

        @Override
        public FloatSet keySet() {
            return this.keySet();
        }

        @Override
        @Deprecated
        public ObjectSet entrySet() {
            return this.entrySet();
        }

        @Override
        public ObjectSet float2LongEntrySet() {
            return this.float2LongEntrySet();
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
            return this.tailMap((Float)object);
        }

        @Override
        @Deprecated
        public SortedMap headMap(Object object) {
            return this.headMap((Float)object);
        }

        @Override
        @Deprecated
        public SortedMap subMap(Object object, Object object2) {
            return this.subMap((Float)object, (Float)object2);
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
    extends Float2LongMaps.SynchronizedMap
    implements Float2LongSortedMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Float2LongSortedMap sortedMap;

        protected SynchronizedSortedMap(Float2LongSortedMap float2LongSortedMap, Object object) {
            super(float2LongSortedMap, object);
            this.sortedMap = float2LongSortedMap;
        }

        protected SynchronizedSortedMap(Float2LongSortedMap float2LongSortedMap) {
            super(float2LongSortedMap);
            this.sortedMap = float2LongSortedMap;
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
        public ObjectSortedSet<Float2LongMap.Entry> float2LongEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.synchronize(this.sortedMap.float2LongEntrySet(), this.sync);
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Float, Long>> entrySet() {
            return this.float2LongEntrySet();
        }

        @Override
        public FloatSortedSet keySet() {
            if (this.keys == null) {
                this.keys = FloatSortedSets.synchronize(this.sortedMap.keySet(), this.sync);
            }
            return (FloatSortedSet)this.keys;
        }

        @Override
        public Float2LongSortedMap subMap(float f, float f2) {
            return new SynchronizedSortedMap(this.sortedMap.subMap(f, f2), this.sync);
        }

        @Override
        public Float2LongSortedMap headMap(float f) {
            return new SynchronizedSortedMap(this.sortedMap.headMap(f), this.sync);
        }

        @Override
        public Float2LongSortedMap tailMap(float f) {
            return new SynchronizedSortedMap(this.sortedMap.tailMap(f), this.sync);
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
        @Deprecated
        public Float firstKey() {
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
        public Float lastKey() {
            Object object = this.sync;
            synchronized (object) {
                return this.sortedMap.lastKey();
            }
        }

        @Override
        @Deprecated
        public Float2LongSortedMap subMap(Float f, Float f2) {
            return new SynchronizedSortedMap(this.sortedMap.subMap(f, f2), this.sync);
        }

        @Override
        @Deprecated
        public Float2LongSortedMap headMap(Float f) {
            return new SynchronizedSortedMap(this.sortedMap.headMap(f), this.sync);
        }

        @Override
        @Deprecated
        public Float2LongSortedMap tailMap(Float f) {
            return new SynchronizedSortedMap(this.sortedMap.tailMap(f), this.sync);
        }

        @Override
        public FloatSet keySet() {
            return this.keySet();
        }

        @Override
        @Deprecated
        public ObjectSet entrySet() {
            return this.entrySet();
        }

        @Override
        public ObjectSet float2LongEntrySet() {
            return this.float2LongEntrySet();
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
            return this.tailMap((Float)object);
        }

        @Override
        @Deprecated
        public SortedMap headMap(Object object) {
            return this.headMap((Float)object);
        }

        @Override
        @Deprecated
        public SortedMap subMap(Object object, Object object2) {
            return this.subMap((Float)object, (Float)object2);
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
    extends Float2LongMaps.Singleton
    implements Float2LongSortedMap,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final FloatComparator comparator;

        protected Singleton(float f, long l, FloatComparator floatComparator) {
            super(f, l);
            this.comparator = floatComparator;
        }

        protected Singleton(float f, long l) {
            this(f, l, null);
        }

        final int compare(float f, float f2) {
            return this.comparator == null ? Float.compare(f, f2) : this.comparator.compare(f, f2);
        }

        @Override
        public FloatComparator comparator() {
            return this.comparator;
        }

        @Override
        public ObjectSortedSet<Float2LongMap.Entry> float2LongEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.singleton(new AbstractFloat2LongMap.BasicEntry(this.key, this.value), Float2LongSortedMaps.entryComparator(this.comparator));
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Float, Long>> entrySet() {
            return this.float2LongEntrySet();
        }

        @Override
        public FloatSortedSet keySet() {
            if (this.keys == null) {
                this.keys = FloatSortedSets.singleton(this.key, this.comparator);
            }
            return (FloatSortedSet)this.keys;
        }

        @Override
        public Float2LongSortedMap subMap(float f, float f2) {
            if (this.compare(f, this.key) <= 0 && this.compare(this.key, f2) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Float2LongSortedMap headMap(float f) {
            if (this.compare(this.key, f) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Float2LongSortedMap tailMap(float f) {
            if (this.compare(f, this.key) <= 0) {
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
        public Float2LongSortedMap headMap(Float f) {
            return this.headMap(f.floatValue());
        }

        @Override
        @Deprecated
        public Float2LongSortedMap tailMap(Float f) {
            return this.tailMap(f.floatValue());
        }

        @Override
        @Deprecated
        public Float2LongSortedMap subMap(Float f, Float f2) {
            return this.subMap(f.floatValue(), f2.floatValue());
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

        @Override
        public FloatSet keySet() {
            return this.keySet();
        }

        @Override
        @Deprecated
        public ObjectSet entrySet() {
            return this.entrySet();
        }

        @Override
        public ObjectSet float2LongEntrySet() {
            return this.float2LongEntrySet();
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
            return this.tailMap((Float)object);
        }

        @Override
        @Deprecated
        public SortedMap headMap(Object object) {
            return this.headMap((Float)object);
        }

        @Override
        @Deprecated
        public SortedMap subMap(Object object, Object object2) {
            return this.subMap((Float)object, (Float)object2);
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
    extends Float2LongMaps.EmptyMap
    implements Float2LongSortedMap,
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
        public ObjectSortedSet<Float2LongMap.Entry> float2LongEntrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Float, Long>> entrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        public FloatSortedSet keySet() {
            return FloatSortedSets.EMPTY_SET;
        }

        @Override
        public Float2LongSortedMap subMap(float f, float f2) {
            return EMPTY_MAP;
        }

        @Override
        public Float2LongSortedMap headMap(float f) {
            return EMPTY_MAP;
        }

        @Override
        public Float2LongSortedMap tailMap(float f) {
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
        public Float2LongSortedMap headMap(Float f) {
            return this.headMap(f.floatValue());
        }

        @Override
        @Deprecated
        public Float2LongSortedMap tailMap(Float f) {
            return this.tailMap(f.floatValue());
        }

        @Override
        @Deprecated
        public Float2LongSortedMap subMap(Float f, Float f2) {
            return this.subMap(f.floatValue(), f2.floatValue());
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

        @Override
        public FloatSet keySet() {
            return this.keySet();
        }

        @Override
        public ObjectSet float2LongEntrySet() {
            return this.float2LongEntrySet();
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
            return this.tailMap((Float)object);
        }

        @Override
        @Deprecated
        public SortedMap headMap(Object object) {
            return this.headMap((Float)object);
        }

        @Override
        @Deprecated
        public SortedMap subMap(Object object, Object object2) {
            return this.subMap((Float)object, (Float)object2);
        }

        @Override
        public Comparator comparator() {
            return this.comparator();
        }
    }
}

