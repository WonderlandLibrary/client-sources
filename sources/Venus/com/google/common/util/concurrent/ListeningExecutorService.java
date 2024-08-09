/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@CanIgnoreReturnValue
@GwtIncompatible
public interface ListeningExecutorService
extends ExecutorService {
    public <T> ListenableFuture<T> submit(Callable<T> var1);

    public ListenableFuture<?> submit(Runnable var1);

    public <T> ListenableFuture<T> submit(Runnable var1, T var2);

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> var1) throws InterruptedException;

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> var1, long var2, TimeUnit var4) throws InterruptedException;

    default public Future submit(Runnable runnable) {
        return this.submit(runnable);
    }

    default public Future submit(Runnable runnable, Object object) {
        return this.submit(runnable, object);
    }

    default public Future submit(Callable callable) {
        return this.submit(callable);
    }
}

