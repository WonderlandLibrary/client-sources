/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.AbstractFloat2FloatMap;
import it.unimi.dsi.fastutil.floats.Float2FloatMap;
import it.unimi.dsi.fastutil.floats.Float2FloatMaps;
import it.unimi.dsi.fastutil.floats.Float2FloatSortedMap;
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

public final class Float2FloatSortedMaps {
    public static final EmptySortedMap EMPTY_MAP = new EmptySortedMap();

    private Float2FloatSortedMaps() {
    }

    public static Comparator<? super Map.Entry<Float, ?>> entryComparator(FloatComparator floatComparator) {
        return (arg_0, arg_1) -> Float2FloatSortedMaps.lambda$entryComparator$0(floatComparator, arg_0, arg_1);
    }

    public static ObjectBidirectionalIterator<Float2FloatMap.Entry> fastIterator(Float2FloatSortedMap float2FloatSortedMap) {
        ObjectSet objectSet = float2FloatSortedMap.float2FloatEntrySet();
        return objectSet instanceof Float2FloatSortedMap.FastSortedEntrySet ? ((Float2FloatSortedMap.FastSortedEntrySet)objectSet).fastIterator() : objectSet.iterator();
    }

    public static ObjectBidirectionalIterable<Float2FloatMap.Entry> fastIterable(Float2FloatSortedMap float2FloatSortedMap) {
        ObjectSet objectSet = float2FloatSortedMap.float2FloatEntrySet();
        return objectSet instanceof Float2FloatSortedMap.FastSortedEntrySet ? ((Float2FloatSortedMap.FastSortedEntrySet)objectSet)::fastIterator : objectSet;
    }

    public static Float2FloatSortedMap singleton(Float f, Float f2) {
        return new Singleton(f.floatValue(), f2.floatValue());
    }

    public static Float2FloatSortedMap singleton(Float f, Float f2, FloatComparator floatComparator) {
        return new Singleton(f.floatValue(), f2.floatValue(), floatComparator);
    }

    public static Float2FloatSortedMap singleton(float f, float f2) {
        return new Singleton(f, f2);
    }

    public static Float2FloatSortedMap singleton(float f, float f2, FloatComparator floatComparator) {
        return new Singleton(f, f2, floatComparator);
    }

    public static Float2FloatSortedMap synchronize(Float2FloatSortedMap float2FloatSortedMap) {
        return new SynchronizedSortedMap(float2FloatSortedMap);
    }

    public static Float2FloatSortedMap synchronize(Float2FloatSortedMap float2FloatSortedMap, Object object) {
        return new SynchronizedSortedMap(float2FloatSortedMap, object);
    }

    public static Float2FloatSortedMap unmodifiable(Float2FloatSortedMap float2FloatSortedMap) {
        return new UnmodifiableSortedMap(float2FloatSortedMap);
    }

    private static int lambda$entryComparator$0(FloatComparator floatComparator, Map.Entry entry, Map.Entry entry2) {
        return floatComparator.compare(((Float)entry.getKey()).floatValue(), ((Float)entry2.getKey()).floatValue());
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableSortedMap
    extends Float2FloatMaps.UnmodifiableMap
    implements Float2FloatSortedMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Float2FloatSortedMap sortedMap;

        protected UnmodifiableSortedMap(Float2FloatSortedMap float2FloatSortedMap) {
            super(float2FloatSortedMap);
            this.sortedMap = float2FloatSortedMap;
        }

        @Override
        public FloatComparator comparator() {
            return this.sortedMap.comparator();
        }

        @Override
        public ObjectSortedSet<Float2FloatMap.Entry> float2FloatEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.unmodifiable(this.sortedMap.float2FloatEntrySet());
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Float, Float>> entrySet() {
            return this.float2FloatEntrySet();
        }

        @Override
        public FloatSortedSet keySet() {
            if (this.keys == null) {
                this.keys = FloatSortedSets.unmodifiable(this.sortedMap.keySet());
            }
            return (FloatSortedSet)this.keys;
        }

        @Override
        public Float2FloatSortedMap subMap(float f, float f2) {
            return new UnmodifiableSortedMap(this.sortedMap.subMap(f, f2));
        }

        @Override
        public Float2FloatSortedMap headMap(float f) {
            return new UnmodifiableSortedMap(this.sortedMap.headMap(f));
        }

        @Override
        public Float2FloatSortedMap tailMap(float f) {
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
        public Float2FloatSortedMap subMap(Float f, Float f2) {
            return new UnmodifiableSortedMap(this.sortedMap.subMap(f, f2));
        }

        @Override
        @Deprecated
        public Float2FloatSortedMap headMap(Float f) {
            return new UnmodifiableSortedMap(this.sortedMap.headMap(f));
        }

        @Override
        @Deprecated
        public Float2FloatSortedMap tailMap(Float f) {
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
        public ObjectSet float2FloatEntrySet() {
            return this.float2FloatEntrySet();
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
    extends Float2FloatMaps.SynchronizedMap
    implements Float2FloatSortedMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Float2FloatSortedMap sortedMap;

        protected SynchronizedSortedMap(Float2FloatSortedMap float2FloatSortedMap, Object object) {
            super(float2FloatSortedMap, object);
            this.sortedMap = float2FloatSortedMap;
        }

        protected SynchronizedSortedMap(Float2FloatSortedMap float2FloatSortedMap) {
            super(float2FloatSortedMap);
            this.sortedMap = float2FloatSortedMap;
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
        public ObjectSortedSet<Float2FloatMap.Entry> float2FloatEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.synchronize(this.sortedMap.float2FloatEntrySet(), this.sync);
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Float, Float>> entrySet() {
            return this.float2FloatEntrySet();
        }

        @Override
        public FloatSortedSet keySet() {
            if (this.keys == null) {
                this.keys = FloatSortedSets.synchronize(this.sortedMap.keySet(), this.sync);
            }
            return (FloatSortedSet)this.keys;
        }

        @Override
        public Float2FloatSortedMap subMap(float f, float f2) {
            return new SynchronizedSortedMap(this.sortedMap.subMap(f, f2), this.sync);
        }

        @Override
        public Float2FloatSortedMap headMap(float f) {
            return new SynchronizedSortedMap(this.sortedMap.headMap(f), this.sync);
        }

        @Override
        public Float2FloatSortedMap tailMap(float f) {
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
        public Float2FloatSortedMap subMap(Float f, Float f2) {
            return new SynchronizedSortedMap(this.sortedMap.subMap(f, f2), this.sync);
        }

        @Override
        @Deprecated
        public Float2FloatSortedMap headMap(Float f) {
            return new SynchronizedSortedMap(this.sortedMap.headMap(f), this.sync);
        }

        @Override
        @Deprecated
        public Float2FloatSortedMap tailMap(Float f) {
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
        public ObjectSet float2FloatEntrySet() {
            return this.float2FloatEntrySet();
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
    extends Float2FloatMaps.Singleton
    implements Float2FloatSortedMap,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final FloatComparator comparator;

        protected Singleton(float f, float f2, FloatComparator floatComparator) {
            super(f, f2);
            this.comparator = floatComparator;
        }

        protected Singleton(float f, float f2) {
            this(f, f2, null);
        }

        final int compare(float f, float f2) {
            return this.comparator == null ? Float.compare(f, f2) : this.comparator.compare(f, f2);
        }

        @Override
        public FloatComparator comparator() {
            return this.comparator;
        }

        @Override
        public ObjectSortedSet<Float2FloatMap.Entry> float2FloatEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.singleton(new AbstractFloat2FloatMap.BasicEntry(this.key, this.value), Float2FloatSortedMaps.entryComparator(this.comparator));
            }
            return (ObjectSortedSet)this.entries;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Float, Float>> entrySet() {
            return this.float2FloatEntrySet();
        }

        @Override
        public FloatSortedSet keySet() {
            if (this.keys == null) {
                this.keys = FloatSortedSets.singleton(this.key, this.comparator);
            }
            return (FloatSortedSet)this.keys;
        }

        @Override
        public Float2FloatSortedMap subMap(float f, float f2) {
            if (this.compare(f, this.key) <= 0 && this.compare(this.key, f2) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Float2FloatSortedMap headMap(float f) {
            if (this.compare(this.key, f) < 0) {
                return this;
            }
            return EMPTY_MAP;
        }

        @Override
        public Float2FloatSortedMap tailMap(float f) {
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
        public Float2FloatSortedMap headMap(Float f) {
            return this.headMap(f.floatValue());
        }

        @Override
        @Deprecated
        public Float2FloatSortedMap tailMap(Float f) {
            return this.tailMap(f.floatValue());
        }

        @Override
        @Deprecated
        public Float2FloatSortedMap subMap(Float f, Float f2) {
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
        public ObjectSet float2FloatEntrySet() {
            return this.float2FloatEntrySet();
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
    extends Float2FloatMaps.EmptyMap
    implements Float2FloatSortedMap,
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
        public ObjectSortedSet<Float2FloatMap.Entry> float2FloatEntrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        @Deprecated
        public ObjectSortedSet<Map.Entry<Float, Float>> entrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override
        public FloatSortedSet keySet() {
            return FloatSortedSets.EMPTY_SET;
        }

        @Override
        public Float2FloatSortedMap subMap(float f, float f2) {
            return EMPTY_MAP;
        }

        @Override
        public Float2FloatSortedMap headMap(float f) {
            return EMPTY_MAP;
        }

        @Override
        public Float2FloatSortedMap tailMap(float f) {
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
        public Float2FloatSortedMap headMap(Float f) {
            return this.headMap(f.floatValue());
        }

        @Override
        @Deprecated
        public Float2FloatSortedMap tailMap(Float f) {
            return this.tailMap(f.floatValue());
        }

        @Override
        @Deprecated
        public Float2FloatSortedMap subMap(Float f, Float f2) {
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
        public ObjectSet float2FloatEntrySet() {
            return this.float2FloatEntrySet();
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

