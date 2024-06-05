package com.google.common.cache;

import com.google.common.annotations.*;
import javax.annotation.*;
import com.google.common.collect.*;
import java.util.*;
import java.util.concurrent.*;

@Beta
@GwtCompatible
public interface Cache<K, V>
{
    @Nullable
    V getIfPresent(final Object p0);
    
    V get(final K p0, final Callable<? extends V> p1) throws ExecutionException;
    
    ImmutableMap<K, V> getAllPresent(final Iterable<?> p0);
    
    void put(final K p0, final V p1);
    
    void putAll(final Map<? extends K, ? extends V> p0);
    
    void invalidate(final Object p0);
    
    void invalidateAll(final Iterable<?> p0);
    
    void invalidateAll();
    
    long size();
    
    CacheStats stats();
    
    ConcurrentMap<K, V> asMap();
    
    void cleanUp();
}
