/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.util.concurrent.ForwardingExecutorService;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@CanIgnoreReturnValue
@GwtIncompatible
public abstract class ForwardingListeningExecutorService
extends ForwardingExecutorService
implements ListeningExecutorService {
    protected ForwardingListeningExecutorService() {
    }

    @Override
    protected abstract ListeningExecutorService delegate();

    @Override
    public <T> ListenableFuture<T> submit(Callable<T> callable) {
        return this.delegate().submit((Callable)callable);
    }

    @Override
    public ListenableFuture<?> submit(Runnable runnable) {
        return this.delegate().submit(runnable);
    }

    @Override
    public <T> ListenableFuture<T> submit(Runnable runnable, T t) {
        return this.delegate().submit(runnable, (Object)t);
    }

    @Override
    public Future submit(Runnable runnable, Object object) {
        return this.submit(runnable, object);
    }

    @Override
    public Future submit(Runnable runnable) {
        return this.submit(runnable);
    }

    @Override
    public Future submit(Callable callable) {
        return this.submit(callable);
    }

    @Override
    protected ExecutorService delegate() {
        return this.delegate();
    }

    @Override
    protected Object delegate() {
        return this.delegate();
    }
}

