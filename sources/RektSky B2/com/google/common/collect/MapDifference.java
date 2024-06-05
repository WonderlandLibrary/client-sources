package com.google.common.collect;

import com.google.common.annotations.*;
import java.util.*;
import javax.annotation.*;

@GwtCompatible
public interface MapDifference<K, V>
{
    boolean areEqual();
    
    Map<K, V> entriesOnlyOnLeft();
    
    Map<K, V> entriesOnlyOnRight();
    
    Map<K, V> entriesInCommon();
    
    Map<K, ValueDifference<V>> entriesDiffering();
    
    boolean equals(@Nullable final Object p0);
    
    int hashCode();
    
    public interface ValueDifference<V>
    {
        V leftValue();
        
        V rightValue();
        
        boolean equals(@Nullable final Object p0);
        
        int hashCode();
    }
}
