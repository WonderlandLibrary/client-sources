/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.ints.Int2BooleanMap;
import it.unimi.dsi.fastutil.ints.IntComparator;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.ints.IntSortedSet;
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
public interface Int2BooleanSortedMap
extends Int2BooleanMap,
SortedMap<Integer, Boolean> {
    public Int2BooleanSortedMap subMap(int var1, int var2);

    public Int2BooleanSortedMap headMap(int var1);

    public Int2BooleanSortedMap tailMap(int var1);

    public int firstIntKey();

    public int lastIntKey();

    @Deprecated
    default public Int2BooleanSortedMap subMap(Integer n, Integer n2) {
        return this.subMap((int)n, (int)n2);
    }

    @Deprecated
    default public Int2BooleanSortedMap headMap(Integer n) {
        return this.headMap((int)n);
    }

    @Deprecated
    default public Int2BooleanSortedMap tailMap(Integer n) {
        return this.tailMap((int)n);
    }

    @Override
    @Deprecated
    default public Integer firstKey() {
        return this.firstIntKey();
    }

    @Override
    @Deprecated
    default public Integer lastKey() {
        return this.lastIntKey();
    }

    @Override
    @Deprecated
    default public ObjectSortedSet<Map.Entry<Integer, Boolean>> entrySet() {
        return this.int2BooleanEntrySet();
    }

    public ObjectSortedSet<Int2BooleanMap.Entry> int2BooleanEntrySet();

    @Override
    public IntSortedSet keySet();

    @Override
    public BooleanCollection values();

    public IntComparator comparator();

    @Override
    default public IntSet keySet() {
        return this.keySet();
    }

    @Override
    @Deprecated
    default public ObjectSet entrySet() {
        return this.entrySet();
    }

    default public ObjectSet int2BooleanEntrySet() {
        return this.int2BooleanEntrySet();
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
        return this.tailMap((Integer)object);
    }

    @Override
    @Deprecated
    default public SortedMap headMap(Object object) {
        return this.headMap((Integer)object);
    }

    @Override
    @Deprecated
    default public SortedMap subMap(Object object, Object object2) {
        return this.subMap((Integer)object, (Integer)object2);
    }

    @Override
    default public Comparator comparator() {
        return this.comparator();
    }

    public static interface FastSortedEntrySet
    extends ObjectSortedSet<Int2BooleanMap.Entry>,
    Int2BooleanMap.FastEntrySet {
        public ObjectBidirectionalIterator<Int2BooleanMap.Entry> fastIterator();

        public ObjectBidirectionalIterator<Int2BooleanMap.Entry> fastIterator(Int2BooleanMap.Entry var1);

        default public ObjectIterator fastIterator() {
            return this.fastIterator();
        }
    }
}

