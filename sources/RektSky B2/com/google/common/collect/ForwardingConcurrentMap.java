package com.google.common.collect;

import java.util.concurrent.*;
import com.google.common.annotations.*;
import java.util.*;

@GwtCompatible
public abstract class ForwardingConcurrentMap<K, V> extends ForwardingMap<K, V> implements ConcurrentMap<K, V>
{
    protected ForwardingConcurrentMap() {
    }
    
    @Override
    protected abstract ConcurrentMap<K, V> delegate();
    
    @Override
    public V putIfAbsent(final K key, final V value) {
        return this.delegate().putIfAbsent(key, value);
    }
    
    @Override
    public boolean remove(final Object key, final Object value) {
        return this.delegate().remove(key, value);
    }
    
    @Override
    public V replace(final K key, final V value) {
        return this.delegate().replace(key, value);
    }
    
    @Override
    public boolean replace(final K key, final V oldValue, final V newValue) {
        return this.delegate().replace(key, oldValue, newValue);
    }
}
