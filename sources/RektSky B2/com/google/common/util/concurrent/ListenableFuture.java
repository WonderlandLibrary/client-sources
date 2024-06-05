package com.google.common.util.concurrent;

import java.util.concurrent.*;

public interface ListenableFuture<V> extends Future<V>
{
    void addListener(final Runnable p0, final Executor p1);
}
