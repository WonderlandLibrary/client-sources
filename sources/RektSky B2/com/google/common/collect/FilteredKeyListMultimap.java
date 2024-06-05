package com.google.common.collect;

import com.google.common.annotations.*;
import com.google.common.base.*;
import javax.annotation.*;
import java.util.*;

@GwtCompatible
final class FilteredKeyListMultimap<K, V> extends FilteredKeyMultimap<K, V> implements ListMultimap<K, V>
{
    FilteredKeyListMultimap(final ListMultimap<K, V> unfiltered, final Predicate<? super K> keyPredicate) {
        super(unfiltered, keyPredicate);
    }
    
    @Override
    public ListMultimap<K, V> unfiltered() {
        return (ListMultimap<K, V>)(ListMultimap)super.unfiltered();
    }
    
    @Override
    public List<V> get(final K key) {
        return (List<V>)(List)super.get(key);
    }
    
    @Override
    public List<V> removeAll(@Nullable final Object key) {
        return (List<V>)(List)super.removeAll(key);
    }
    
    @Override
    public List<V> replaceValues(final K key, final Iterable<? extends V> values) {
        return (List<V>)(List)super.replaceValues(key, values);
    }
}
