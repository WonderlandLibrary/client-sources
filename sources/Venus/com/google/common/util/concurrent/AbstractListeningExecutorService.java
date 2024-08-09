/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.TrustedListenableFutureTask;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.RunnableFuture;
import javax.annotation.Nullable;

@Beta
@CanIgnoreReturnValue
@GwtIncompatible
public abstract class AbstractListeningExecutorService
extends AbstractExecutorService
implements ListeningExecutorService {
    @Override
    protected final <T> RunnableFuture<T> newTaskFor(Runnable runnable, T t) {
        return TrustedListenableFutureTask.create(runnable, t);
    }

    @Override
    protected final <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
        return TrustedListenableFutureTask.create(callable);
    }

    @Override
    public ListenableFuture<?> submit(Runnable runnable) {
        return (ListenableFuture)super.submit(runnable);
    }

    @Override
    public <T> ListenableFuture<T> submit(Runnable runnable, @Nullable T t) {
        return (ListenableFuture)super.submit(runnable, t);
    }

    @Override
    public <T> ListenableFuture<T> submit(Callable<T> callable) {
        return (ListenableFuture)super.submit(callable);
    }

    @Override
    public Future submit(Callable callable) {
        return this.submit(callable);
    }

    @Override
    public Future submit(Runnable runnable, @Nullable Object object) {
        return this.submit(runnable, object);
    }

    @Override
    public Future submit(Runnable runnable) {
        return this.submit(runnable);
    }
}

