package com.google.common.collect;

import com.google.common.annotations.*;
import javax.annotation.*;
import java.util.*;

@GwtCompatible
public interface ListMultimap<K, V> extends Multimap<K, V>
{
    List<V> get(@Nullable final K p0);
    
    List<V> removeAll(@Nullable final Object p0);
    
    List<V> replaceValues(final K p0, final Iterable<? extends V> p1);
    
    Map<K, Collection<V>> asMap();
    
    boolean equals(@Nullable final Object p0);
}
