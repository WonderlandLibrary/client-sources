package com.google.common.collect;

import com.google.common.annotations.*;
import com.google.common.base.*;
import java.util.*;

@GwtCompatible
interface FilteredMultimap<K, V> extends Multimap<K, V>
{
    Multimap<K, V> unfiltered();
    
    Predicate<? super Map.Entry<K, V>> entryPredicate();
}
