package com.google.common.collect;

import com.google.common.annotations.*;
import javax.annotation.*;
import java.util.*;

@Beta
public interface RangeMap<K extends Comparable, V>
{
    @Nullable
    V get(final K p0);
    
    @Nullable
    Map.Entry<Range<K>, V> getEntry(final K p0);
    
    Range<K> span();
    
    void put(final Range<K> p0, final V p1);
    
    void putAll(final RangeMap<K, V> p0);
    
    void clear();
    
    void remove(final Range<K> p0);
    
    Map<Range<K>, V> asMapOfRanges();
    
    RangeMap<K, V> subRangeMap(final Range<K> p0);
    
    boolean equals(@Nullable final Object p0);
    
    int hashCode();
    
    String toString();
}
