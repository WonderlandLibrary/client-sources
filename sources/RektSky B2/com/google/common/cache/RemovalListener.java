package com.google.common.cache;

import com.google.common.annotations.*;

@Beta
@GwtCompatible
public interface RemovalListener<K, V>
{
    void onRemoval(final RemovalNotification<K, V> p0);
}
