package com.google.common.collect;

import com.google.common.annotations.*;
import java.util.*;
import javax.annotation.*;

@GwtCompatible(serializable = true, emulated = true)
final class SingletonImmutableBiMap<K, V> extends ImmutableBiMap<K, V>
{
    final transient K singleKey;
    final transient V singleValue;
    transient ImmutableBiMap<V, K> inverse;
    
    SingletonImmutableBiMap(final K singleKey, final V singleValue) {
        CollectPreconditions.checkEntryNotNull(singleKey, singleValue);
        this.singleKey = singleKey;
        this.singleValue = singleValue;
    }
    
    private SingletonImmutableBiMap(final K singleKey, final V singleValue, final ImmutableBiMap<V, K> inverse) {
        this.singleKey = singleKey;
        this.singleValue = singleValue;
        this.inverse = inverse;
    }
    
    SingletonImmutableBiMap(final Map.Entry<? extends K, ? extends V> entry) {
        this(entry.getKey(), entry.getValue());
    }
    
    @Override
    public V get(@Nullable final Object key) {
        return this.singleKey.equals(key) ? this.singleValue : null;
    }
    
    @Override
    public int size() {
        return 1;
    }
    
    @Override
    public boolean containsKey(@Nullable final Object key) {
        return this.singleKey.equals(key);
    }
    
    @Override
    public boolean containsValue(@Nullable final Object value) {
        return this.singleValue.equals(value);
    }
    
    @Override
    boolean isPartialView() {
        return false;
    }
    
    @Override
    ImmutableSet<Map.Entry<K, V>> createEntrySet() {
        return ImmutableSet.of(Maps.immutableEntry(this.singleKey, this.singleValue));
    }
    
    @Override
    ImmutableSet<K> createKeySet() {
        return ImmutableSet.of(this.singleKey);
    }
    
    @Override
    public ImmutableBiMap<V, K> inverse() {
        final ImmutableBiMap<V, K> result = this.inverse;
        if (result == null) {
            return this.inverse = (ImmutableBiMap<V, K>)new SingletonImmutableBiMap(this.singleValue, this.singleKey, (ImmutableBiMap<Object, Object>)this);
        }
        return result;
    }
}
