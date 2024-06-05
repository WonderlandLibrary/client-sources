package com.google.common.util.concurrent;

import com.google.common.annotations.*;

@Beta
public interface FutureFallback<V>
{
    ListenableFuture<V> create(final Throwable p0) throws Exception;
}
