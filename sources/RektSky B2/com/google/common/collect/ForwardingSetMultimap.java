package com.google.common.collect;

import com.google.common.annotations.*;
import javax.annotation.*;
import java.util.*;

@GwtCompatible
public abstract class ForwardingSetMultimap<K, V> extends ForwardingMultimap<K, V> implements SetMultimap<K, V>
{
    @Override
    protected abstract SetMultimap<K, V> delegate();
    
    @Override
    public Set<Map.Entry<K, V>> entries() {
        return this.delegate().entries();
    }
    
    @Override
    public Set<V> get(@Nullable final K key) {
        return this.delegate().get(key);
    }
    
    @Override
    public Set<V> removeAll(@Nullable final Object key) {
        return this.delegate().removeAll(key);
    }
    
    @Override
    public Set<V> replaceValues(final K key, final Iterable<? extends V> values) {
        return this.delegate().replaceValues(key, values);
    }
}
