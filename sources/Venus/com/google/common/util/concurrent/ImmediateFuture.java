/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.util.concurrent;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.AbstractFuture;
import com.google.common.util.concurrent.CheckedFuture;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;

@GwtCompatible(emulated=true)
abstract class ImmediateFuture<V>
implements ListenableFuture<V> {
    private static final Logger log = Logger.getLogger(ImmediateFuture.class.getName());

    ImmediateFuture() {
    }

    @Override
    public void addListener(Runnable runnable, Executor executor) {
        Preconditions.checkNotNull(runnable, "Runnable was null.");
        Preconditions.checkNotNull(executor, "Executor was null.");
        try {
            executor.execute(runnable);
        } catch (RuntimeException runtimeException) {
            log.log(Level.SEVERE, "RuntimeException while executing runnable " + runnable + " with executor " + executor, runtimeException);
        }
    }

    @Override
    public boolean cancel(boolean bl) {
        return true;
    }

    @Override
    public abstract V get() throws ExecutionException;

    @Override
    public V get(long l, TimeUnit timeUnit) throws ExecutionException {
        Preconditions.checkNotNull(timeUnit);
        return this.get();
    }

    @Override
    public boolean isCancelled() {
        return true;
    }

    @Override
    public boolean isDone() {
        return false;
    }

    @GwtIncompatible
    static class ImmediateFailedCheckedFuture<V, X extends Exception>
    extends ImmediateFuture<V>
    implements CheckedFuture<V, X> {
        private final X thrown;

        ImmediateFailedCheckedFuture(X x) {
            this.thrown = x;
        }

        @Override
        public V get() throws ExecutionException {
            throw new ExecutionException((Throwable)this.thrown);
        }

        @Override
        public V checkedGet() throws X {
            throw this.thrown;
        }

        @Override
        public V checkedGet(long l, TimeUnit timeUnit) throws X {
            Preconditions.checkNotNull(timeUnit);
            throw this.thrown;
        }
    }

    static final class ImmediateCancelledFuture<V>
    extends AbstractFuture.TrustedFuture<V> {
        ImmediateCancelledFuture() {
            this.cancel(true);
        }
    }

    static final class ImmediateFailedFuture<V>
    extends AbstractFuture.TrustedFuture<V> {
        ImmediateFailedFuture(Throwable throwable) {
            this.setException(throwable);
        }
    }

    @GwtIncompatible
    static class ImmediateSuccessfulCheckedFuture<V, X extends Exception>
    extends ImmediateFuture<V>
    implements CheckedFuture<V, X> {
        @Nullable
        private final V value;

        ImmediateSuccessfulCheckedFuture(@Nullable V v) {
            this.value = v;
        }

        @Override
        public V get() {
            return this.value;
        }

        @Override
        public V checkedGet() {
            return this.value;
        }

        @Override
        public V checkedGet(long l, TimeUnit timeUnit) {
            Preconditions.checkNotNull(timeUnit);
            return this.value;
        }
    }

    static class ImmediateSuccessfulFuture<V>
    extends ImmediateFuture<V> {
        static final ImmediateSuccessfulFuture<Object> NULL = new ImmediateSuccessfulFuture<Object>(null);
        @Nullable
        private final V value;

        ImmediateSuccessfulFuture(@Nullable V v) {
            this.value = v;
        }

        @Override
        public V get() {
            return this.value;
        }
    }
}

