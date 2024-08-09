/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.shorts.Short2LongMap;
import it.unimi.dsi.fastutil.shorts.ShortComparator;
import it.unimi.dsi.fastutil.shorts.ShortSet;
import it.unimi.dsi.fastutil.shorts.ShortSortedSet;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface Short2LongSortedMap
extends Short2LongMap,
SortedMap<Short, Long> {
    public Short2LongSortedMap subMap(short var1, short var2);

    public Short2LongSortedMap headMap(short var1);

    public Short2LongSortedMap tailMap(short var1);

    public short firstShortKey();

    public short lastShortKey();

    @Deprecated
    default public Short2LongSortedMap subMap(Short s, Short s2) {
        return this.subMap((short)s, (short)s2);
    }

    @Deprecated
    default public Short2LongSortedMap headMap(Short s) {
        return this.headMap((short)s);
    }

    @Deprecated
    default public Short2LongSortedMap tailMap(Short s) {
        return this.tailMap((short)s);
    }

    @Override
    @Deprecated
    default public Short firstKey() {
        return this.firstShortKey();
    }

    @Override
    @Deprecated
    default public Short lastKey() {
        return this.lastShortKey();
    }

    @Override
    @Deprecated
    default public ObjectSortedSet<Map.Entry<Short, Long>> entrySet() {
        return this.short2LongEntrySet();
    }

    public ObjectSortedSet<Short2LongMap.Entry> short2LongEntrySet();

    @Override
    public ShortSortedSet keySet();

    @Override
    public LongCollection values();

    public ShortComparator comparator();

    @Override
    default public ShortSet keySet() {
        return this.keySet();
    }

    @Override
    @Deprecated
    default public ObjectSet entrySet() {
        return this.entrySet();
    }

    default public ObjectSet short2LongEntrySet() {
        return this.short2LongEntrySet();
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
        return this.tailMap((Short)object);
    }

    @Override
    @Deprecated
    default public SortedMap headMap(Object object) {
        return this.headMap((Short)object);
    }

    @Override
    @Deprecated
    default public SortedMap subMap(Object object, Object object2) {
        return this.subMap((Short)object, (Short)object2);
    }

    @Override
    default public Comparator comparator() {
        return this.comparator();
    }

    public static interface FastSortedEntrySet
    extends ObjectSortedSet<Short2LongMap.Entry>,
    Short2LongMap.FastEntrySet {
        public ObjectBidirectionalIterator<Short2LongMap.Entry> fastIterator();

        public ObjectBidirectionalIterator<Short2LongMap.Entry> fastIterator(Short2LongMap.Entry var1);

        default public ObjectIterator fastIterator() {
            return this.fastIterator();
        }
    }
}

