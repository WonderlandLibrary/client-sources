package com.google.common.collect;

import com.google.common.annotations.*;
import javax.annotation.*;
import java.util.*;

@GwtCompatible
public interface SortedSetMultimap<K, V> extends SetMultimap<K, V>
{
    SortedSet<V> get(@Nullable final K p0);
    
    SortedSet<V> removeAll(@Nullable final Object p0);
    
    SortedSet<V> replaceValues(final K p0, final Iterable<? extends V> p1);
    
    Map<K, Collection<V>> asMap();
    
    Comparator<? super V> valueComparator();
}
