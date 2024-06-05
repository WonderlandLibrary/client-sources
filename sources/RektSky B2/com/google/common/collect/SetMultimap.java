package com.google.common.collect;

import com.google.common.annotations.*;
import javax.annotation.*;
import java.util.*;

@GwtCompatible
public interface SetMultimap<K, V> extends Multimap<K, V>
{
    Set<V> get(@Nullable final K p0);
    
    Set<V> removeAll(@Nullable final Object p0);
    
    Set<V> replaceValues(final K p0, final Iterable<? extends V> p1);
    
    Set<Map.Entry<K, V>> entries();
    
    Map<K, Collection<V>> asMap();
    
    boolean equals(@Nullable final Object p0);
}
