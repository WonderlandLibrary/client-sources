/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.util.concurrent.CheckedFuture;
import com.google.common.util.concurrent.ForwardingListenableFuture;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Beta
@GwtIncompatible
public abstract class AbstractCheckedFuture<V, X extends Exception>
extends ForwardingListenableFuture.SimpleForwardingListenableFuture<V>
implements CheckedFuture<V, X> {
    protected AbstractCheckedFuture(ListenableFuture<V> listenableFuture) {
        super(listenableFuture);
    }

    protected abstract X mapException(Exception var1);

    @Override
    @CanIgnoreReturnValue
    public V checkedGet() throws X {
        try {
            return this.get();
        } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
            throw this.mapException(interruptedException);
        } catch (CancellationException cancellationException) {
            throw this.mapException(cancellationException);
        } catch (ExecutionException executionException) {
            throw this.mapException(executionException);
        }
    }

    @Override
    @CanIgnoreReturnValue
    public V checkedGet(long l, TimeUnit timeUnit) throws TimeoutException, X {
        try {
            return this.get(l, timeUnit);
        } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
            throw this.mapException(interruptedException);
        } catch (CancellationException cancellationException) {
            throw this.mapException(cancellationException);
        } catch (ExecutionException executionException) {
            throw this.mapException(executionException);
        }
    }
}

