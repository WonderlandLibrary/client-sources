package com.google.common.collect;

import com.google.common.annotations.*;
import java.util.*;

@GwtCompatible
public interface SortedMapDifference<K, V> extends MapDifference<K, V>
{
    SortedMap<K, V> entriesOnlyOnLeft();
    
    SortedMap<K, V> entriesOnlyOnRight();
    
    SortedMap<K, V> entriesInCommon();
    
    SortedMap<K, ValueDifference<V>> entriesDiffering();
}
