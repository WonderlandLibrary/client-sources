/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.AbstractSortedSetMultimap;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;

@GwtCompatible
abstract class AbstractSortedKeySortedSetMultimap<K, V>
extends AbstractSortedSetMultimap<K, V> {
    AbstractSortedKeySortedSetMultimap(SortedMap<K, Collection<V>> sortedMap) {
        super(sortedMap);
    }

    @Override
    public SortedMap<K, Collection<V>> asMap() {
        return (SortedMap)super.asMap();
    }

    @Override
    SortedMap<K, Collection<V>> backingMap() {
        return (SortedMap)super.backingMap();
    }

    @Override
    public SortedSet<K> keySet() {
        return (SortedSet)super.keySet();
    }

    @Override
    public Map asMap() {
        return this.asMap();
    }

    @Override
    public Set keySet() {
        return this.keySet();
    }

    @Override
    Map backingMap() {
        return this.backingMap();
    }
}

