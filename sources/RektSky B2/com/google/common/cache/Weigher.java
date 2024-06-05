package com.google.common.cache;

import com.google.common.annotations.*;

@Beta
@GwtCompatible
public interface Weigher<K, V>
{
    int weigh(final K p0, final V p1);
}
