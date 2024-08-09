/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.Float2LongMap;
import it.unimi.dsi.fastutil.floats.FloatComparator;
import it.unimi.dsi.fastutil.floats.FloatSet;
import it.unimi.dsi.fastutil.floats.FloatSortedSet;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface Float2LongSortedMap
extends Float2LongMap,
SortedMap<Float, Long> {
    public Float2LongSortedMap subMap(float var1, float var2);

    public Float2LongSortedMap headMap(float var1);

    public Float2LongSortedMap tailMap(float var1);

    public float firstFloatKey();

    public float lastFloatKey();

    @Deprecated
    default public Float2LongSortedMap subMap(Float f, Float f2) {
        return this.subMap(f.floatValue(), f2.floatValue());
    }

    @Deprecated
    default public Float2LongSortedMap headMap(Float f) {
        return this.headMap(f.floatValue());
    }

    @Deprecated
    default public Float2LongSortedMap tailMap(Float f) {
        return this.tailMap(f.floatValue());
    }

    @Override
    @Deprecated
    default public Float firstKey() {
        return Float.valueOf(this.firstFloatKey());
    }

    @Override
    @Deprecated
    default public Float lastKey() {
        return Float.valueOf(this.lastFloatKey());
    }

    @Override
    @Deprecated
    default public ObjectSortedSet<Map.Entry<Float, Long>> entrySet() {
        return this.float2LongEntrySet();
    }

    public ObjectSortedSet<Float2LongMap.Entry> float2LongEntrySet();

    @Override
    public FloatSortedSet keySet();

    @Override
    public LongCollection values();

    public FloatComparator comparator();

    @Override
    default public FloatSet keySet() {
        return this.keySet();
    }

    @Override
    @Deprecated
    default public ObjectSet entrySet() {
        return this.entrySet();
    }

    default public ObjectSet float2LongEntrySet() {
        return this.float2LongEntrySet();
    }

    @Override
    @Deprecated
    default public Set entrySet() {
        return this.entrySet();
    }

    @Override
    default public Collection values() {
        return this.values();
    }

    @Override
    default public Set keySet() {
        return this.keySet();
    }

    @Override
    @Deprecated
    default public Object lastKey() {
        return this.lastKey();
    }

    @Override
    @Deprecated
    default public Object firstKey() {
        return this.firstKey();
    }

    @Override
    @Deprecated
    default public SortedMap tailMap(Object object) {
        return this.tailMap((Float)object);
    }

    @Override
    @Deprecated
    default public SortedMap headMap(Object object) {
        return this.headMap((Float)object);
    }

    @Override
    @Deprecated
    default public SortedMap subMap(Object object, Object object2) {
        return this.subMap((Float)object, (Float)object2);
    }

    @Override
    default public Comparator comparator() {
        return this.comparator();
    }

    public static interface FastSortedEntrySet
    extends ObjectSortedSet<Float2LongMap.Entry>,
    Float2LongMap.FastEntrySet {
        public ObjectBidirectionalIterator<Float2LongMap.Entry> fastIterator();

        public ObjectBidirectionalIterator<Float2LongMap.Entry> fastIterator(Float2LongMap.Entry var1);

        default public ObjectIterator fastIterator() {
            return this.fastIterator();
        }
    }
}

