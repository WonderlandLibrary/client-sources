package com.google.common.collect;

import com.google.common.annotations.*;
import java.util.*;

@GwtCompatible
abstract class AbstractSortedKeySortedSetMultimap<K, V> extends AbstractSortedSetMultimap<K, V>
{
    AbstractSortedKeySortedSetMultimap(final SortedMap<K, Collection<V>> map) {
        super(map);
    }
    
    @Override
    public SortedMap<K, Collection<V>> asMap() {
        return (SortedMap<K, Collection<V>>)(SortedMap)super.asMap();
    }
    
    @Override
    SortedMap<K, Collection<V>> backingMap() {
        return (SortedMap<K, Collection<V>>)(SortedMap)super.backingMap();
    }
    
    @Override
    public SortedSet<K> keySet() {
        return (SortedSet<K>)(SortedSet)super.keySet();
    }
}
