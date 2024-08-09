/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedMap;
import javax.annotation.Nullable;

@GwtIncompatible
abstract class AbstractNavigableMap<K, V>
extends Maps.IteratorBasedAbstractMap<K, V>
implements NavigableMap<K, V> {
    AbstractNavigableMap() {
    }

    @Override
    @Nullable
    public abstract V get(@Nullable Object var1);

    @Override
    @Nullable
    public Map.Entry<K, V> firstEntry() {
        return Iterators.getNext(this.entryIterator(), null);
    }

    @Override
    @Nullable
    public Map.Entry<K, V> lastEntry() {
        return Iterators.getNext(this.descendingEntryIterator(), null);
    }

    @Override
    @Nullable
    public Map.Entry<K, V> pollFirstEntry() {
        return Iterators.pollNext(this.entryIterator());
    }

    @Override
    @Nullable
    public Map.Entry<K, V> pollLastEntry() {
        return Iterators.pollNext(this.descendingEntryIterator());
    }

    @Override
    public K firstKey() {
        Map.Entry<K, V> entry = this.firstEntry();
        if (entry == null) {
            throw new NoSuchElementException();
        }
        return entry.getKey();
    }

    @Override
    public K lastKey() {
        Map.Entry<K, V> entry = this.lastEntry();
        if (entry == null) {
            throw new NoSuchElementException();
        }
        return entry.getKey();
    }

    @Override
    @Nullable
    public Map.Entry<K, V> lowerEntry(K k) {
        return this.headMap(k, true).lastEntry();
    }

    @Override
    @Nullable
    public Map.Entry<K, V> floorEntry(K k) {
        return this.headMap(k, false).lastEntry();
    }

    @Override
    @Nullable
    public Map.Entry<K, V> ceilingEntry(K k) {
        return this.tailMap(k, false).firstEntry();
    }

    @Override
    @Nullable
    public Map.Entry<K, V> higherEntry(K k) {
        return this.tailMap(k, true).firstEntry();
    }

    @Override
    public K lowerKey(K k) {
        return Maps.keyOrNull(this.lowerEntry(k));
    }

    @Override
    public K floorKey(K k) {
        return Maps.keyOrNull(this.floorEntry(k));
    }

    @Override
    public K ceilingKey(K k) {
        return Maps.keyOrNull(this.ceilingEntry(k));
    }

    @Override
    public K higherKey(K k) {
        return Maps.keyOrNull(this.higherEntry(k));
    }

    abstract Iterator<Map.Entry<K, V>> descendingEntryIterator();

    @Override
    public SortedMap<K, V> subMap(K k, K k2) {
        return this.subMap(k, true, k2, true);
    }

    @Override
    public SortedMap<K, V> headMap(K k) {
        return this.headMap(k, true);
    }

    @Override
    public SortedMap<K, V> tailMap(K k) {
        return this.tailMap(k, false);
    }

    @Override
    public NavigableSet<K> navigableKeySet() {
        return new Maps.NavigableKeySet(this);
    }

    @Override
    public Set<K> keySet() {
        return this.navigableKeySet();
    }

    @Override
    public NavigableSet<K> descendingKeySet() {
        return this.descendingMap().navigableKeySet();
    }

    @Override
    public NavigableMap<K, V> descendingMap() {
        return new DescendingMap(this, null);
    }

    private final class DescendingMap
    extends Maps.DescendingMap<K, V> {
        final AbstractNavigableMap this$0;

        private DescendingMap(AbstractNavigableMap abstractNavigableMap) {
            this.this$0 = abstractNavigableMap;
        }

        @Override
        NavigableMap<K, V> forward() {
            return this.this$0;
        }

        @Override
        Iterator<Map.Entry<K, V>> entryIterator() {
            return this.this$0.descendingEntryIterator();
        }

        DescendingMap(AbstractNavigableMap abstractNavigableMap, 1 var2_2) {
            this(abstractNavigableMap);
        }
    }
}

