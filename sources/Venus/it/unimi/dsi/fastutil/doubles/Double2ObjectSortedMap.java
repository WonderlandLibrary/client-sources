/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.Double2ObjectMap;
import it.unimi.dsi.fastutil.doubles.DoubleComparator;
import it.unimi.dsi.fastutil.doubles.DoubleSet;
import it.unimi.dsi.fastutil.doubles.DoubleSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
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
public interface Double2ObjectSortedMap<V>
extends Double2ObjectMap<V>,
SortedMap<Double, V> {
    public Double2ObjectSortedMap<V> subMap(double var1, double var3);

    public Double2ObjectSortedMap<V> headMap(double var1);

    public Double2ObjectSortedMap<V> tailMap(double var1);

    public double firstDoubleKey();

    public double lastDoubleKey();

    @Deprecated
    default public Double2ObjectSortedMap<V> subMap(Double d, Double d2) {
        return this.subMap((double)d, (double)d2);
    }

    @Deprecated
    default public Double2ObjectSortedMap<V> headMap(Double d) {
        return this.headMap((double)d);
    }

    @Deprecated
    default public Double2ObjectSortedMap<V> tailMap(Double d) {
        return this.tailMap((double)d);
    }

    @Override
    @Deprecated
    default public Double firstKey() {
        return this.firstDoubleKey();
    }

    @Override
    @Deprecated
    default public Double lastKey() {
        return this.lastDoubleKey();
    }

    @Override
    @Deprecated
    default public ObjectSortedSet<Map.Entry<Double, V>> entrySet() {
        return this.double2ObjectEntrySet();
    }

    @Override
    public ObjectSortedSet<Double2ObjectMap.Entry<V>> double2ObjectEntrySet();

    @Override
    public DoubleSortedSet keySet();

    @Override
    public ObjectCollection<V> values();

    public DoubleComparator comparator();

    @Override
    default public DoubleSet keySet() {
        return this.keySet();
    }

    @Override
    @Deprecated
    default public ObjectSet entrySet() {
        return this.entrySet();
    }

    @Override
    default public ObjectSet double2ObjectEntrySet() {
        return this.double2ObjectEntrySet();
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
        return this.tailMap((Double)object);
    }

    @Override
    @Deprecated
    default public SortedMap headMap(Object object) {
        return this.headMap((Double)object);
    }

    @Override
    @Deprecated
    default public SortedMap subMap(Object object, Object object2) {
        return this.subMap((Double)object, (Double)object2);
    }

    @Override
    default public Comparator comparator() {
        return this.comparator();
    }

    public static interface FastSortedEntrySet<V>
    extends ObjectSortedSet<Double2ObjectMap.Entry<V>>,
    Double2ObjectMap.FastEntrySet<V> {
        @Override
        public ObjectBidirectionalIterator<Double2ObjectMap.Entry<V>> fastIterator();

        public ObjectBidirectionalIterator<Double2ObjectMap.Entry<V>> fastIterator(Double2ObjectMap.Entry<V> var1);

        @Override
        default public ObjectIterator fastIterator() {
            return this.fastIterator();
        }
    }
}

