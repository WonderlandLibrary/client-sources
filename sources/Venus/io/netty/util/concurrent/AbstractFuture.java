/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.concurrent;

import io.netty.util.concurrent.Future;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public abstract class AbstractFuture<V>
implements Future<V> {
    @Override
    public V get() throws InterruptedException, ExecutionException {
        this.await();
        Throwable throwable = this.cause();
        if (throwable == null) {
            return this.getNow();
        }
        if (throwable instanceof CancellationException) {
            throw (CancellationException)throwable;
        }
        throw new ExecutionException(throwable);
    }

    @Override
    public V get(long l, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        if (this.await(l, timeUnit)) {
            Throwable throwable = this.cause();
            if (throwable == null) {
                return this.getNow();
            }
            if (throwable instanceof CancellationException) {
                throw (CancellationException)throwable;
            }
            throw new ExecutionException(throwable);
        }
        throw new TimeoutException();
    }
}

