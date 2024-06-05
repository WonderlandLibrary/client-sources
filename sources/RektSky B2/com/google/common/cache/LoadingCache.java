package com.google.common.cache;

import com.google.common.base.*;
import com.google.common.annotations.*;
import com.google.common.collect.*;
import java.util.concurrent.*;

@Beta
@GwtCompatible
public interface LoadingCache<K, V> extends Cache<K, V>, Function<K, V>
{
    V get(final K p0) throws ExecutionException;
    
    V getUnchecked(final K p0);
    
    ImmutableMap<K, V> getAll(final Iterable<? extends K> p0) throws ExecutionException;
    
    @Deprecated
    V apply(final K p0);
    
    void refresh(final K p0);
    
    ConcurrentMap<K, V> asMap();
}
