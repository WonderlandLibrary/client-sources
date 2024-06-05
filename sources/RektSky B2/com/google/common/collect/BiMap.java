package com.google.common.collect;

import com.google.common.annotations.*;
import javax.annotation.*;
import java.util.*;

@GwtCompatible
public interface BiMap<K, V> extends Map<K, V>
{
    V put(@Nullable final K p0, @Nullable final V p1);
    
    V forcePut(@Nullable final K p0, @Nullable final V p1);
    
    void putAll(final Map<? extends K, ? extends V> p0);
    
    Set<V> values();
    
    BiMap<V, K> inverse();
}
