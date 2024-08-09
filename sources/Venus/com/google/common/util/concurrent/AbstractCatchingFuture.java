/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.util.concurrent;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.AbstractFuture;
import com.google.common.util.concurrent.AsyncFunction;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.Platform;
import com.google.errorprone.annotations.ForOverride;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;

@GwtCompatible
abstract class AbstractCatchingFuture<V, X extends Throwable, F, T>
extends AbstractFuture.TrustedFuture<V>
implements Runnable {
    @Nullable
    ListenableFuture<? extends V> inputFuture;
    @Nullable
    Class<X> exceptionType;
    @Nullable
    F fallback;

    static <X extends Throwable, V> ListenableFuture<V> create(ListenableFuture<? extends V> listenableFuture, Class<X> clazz, Function<? super X, ? extends V> function) {
        CatchingFuture<? extends V, ? super X> catchingFuture = new CatchingFuture<V, X>(listenableFuture, clazz, function);
        listenableFuture.addListener(catchingFuture, MoreExecutors.directExecutor());
        return catchingFuture;
    }

    static <V, X extends Throwable> ListenableFuture<V> create(ListenableFuture<? extends V> listenableFuture, Class<X> clazz, Function<? super X, ? extends V> function, Executor executor) {
        CatchingFuture<? extends V, ? super X> catchingFuture = new CatchingFuture<V, X>(listenableFuture, clazz, function);
        listenableFuture.addListener(catchingFuture, MoreExecutors.rejectionPropagatingExecutor(executor, catchingFuture));
        return catchingFuture;
    }

    static <X extends Throwable, V> ListenableFuture<V> create(ListenableFuture<? extends V> listenableFuture, Class<X> clazz, AsyncFunction<? super X, ? extends V> asyncFunction) {
        AsyncCatchingFuture<? extends V, ? super X> asyncCatchingFuture = new AsyncCatchingFuture<V, X>(listenableFuture, clazz, asyncFunction);
        listenableFuture.addListener(asyncCatchingFuture, MoreExecutors.directExecutor());
        return asyncCatchingFuture;
    }

    static <X extends Throwable, V> ListenableFuture<V> create(ListenableFuture<? extends V> listenableFuture, Class<X> clazz, AsyncFunction<? super X, ? extends V> asyncFunction, Executor executor) {
        AsyncCatchingFuture<? extends V, ? super X> asyncCatchingFuture = new AsyncCatchingFuture<V, X>(listenableFuture, clazz, asyncFunction);
        listenableFuture.addListener(asyncCatchingFuture, MoreExecutors.rejectionPropagatingExecutor(executor, asyncCatchingFuture));
        return asyncCatchingFuture;
    }

    AbstractCatchingFuture(ListenableFuture<? extends V> listenableFuture, Class<X> clazz, F f) {
        this.inputFuture = Preconditions.checkNotNull(listenableFuture);
        this.exceptionType = Preconditions.checkNotNull(clazz);
        this.fallback = Preconditions.checkNotNull(f);
    }

    @Override
    public final void run() {
        T t;
        F f;
        Class<X> clazz;
        ListenableFuture<? extends V> listenableFuture = this.inputFuture;
        if (listenableFuture == null | (clazz = this.exceptionType) == null | (f = this.fallback) == null | this.isCancelled()) {
            return;
        }
        this.inputFuture = null;
        this.exceptionType = null;
        this.fallback = null;
        Object v = null;
        Throwable throwable = null;
        try {
            v = Futures.getDone(listenableFuture);
        } catch (ExecutionException executionException) {
            throwable = Preconditions.checkNotNull(executionException.getCause());
        } catch (Throwable throwable2) {
            throwable = throwable2;
        }
        if (throwable == null) {
            this.set(v);
            return;
        }
        if (!Platform.isInstanceOfThrowableClass(throwable, clazz)) {
            this.setException(throwable);
            return;
        }
        Throwable throwable3 = throwable;
        try {
            t = this.doFallback(f, throwable3);
        } catch (Throwable throwable4) {
            this.setException(throwable4);
            return;
        }
        this.setResult(t);
    }

    @Nullable
    @ForOverride
    abstract T doFallback(F var1, X var2) throws Exception;

    @ForOverride
    abstract void setResult(@Nullable T var1);

    @Override
    protected final void afterDone() {
        this.maybePropagateCancellation(this.inputFuture);
        this.inputFuture = null;
        this.exceptionType = null;
        this.fallback = null;
    }

    private static final class CatchingFuture<V, X extends Throwable>
    extends AbstractCatchingFuture<V, X, Function<? super X, ? extends V>, V> {
        CatchingFuture(ListenableFuture<? extends V> listenableFuture, Class<X> clazz, Function<? super X, ? extends V> function) {
            super(listenableFuture, clazz, function);
        }

        @Override
        @Nullable
        V doFallback(Function<? super X, ? extends V> function, X x) throws Exception {
            return function.apply(x);
        }

        @Override
        void setResult(@Nullable V v) {
            this.set(v);
        }

        @Override
        @Nullable
        Object doFallback(Object object, Throwable throwable) throws Exception {
            return this.doFallback((Function)object, (X)throwable);
        }
    }

    private static final class AsyncCatchingFuture<V, X extends Throwable>
    extends AbstractCatchingFuture<V, X, AsyncFunction<? super X, ? extends V>, ListenableFuture<? extends V>> {
        AsyncCatchingFuture(ListenableFuture<? extends V> listenableFuture, Class<X> clazz, AsyncFunction<? super X, ? extends V> asyncFunction) {
            super(listenableFuture, clazz, asyncFunction);
        }

        @Override
        ListenableFuture<? extends V> doFallback(AsyncFunction<? super X, ? extends V> asyncFunction, X x) throws Exception {
            ListenableFuture<? extends V> listenableFuture = asyncFunction.apply(x);
            Preconditions.checkNotNull(listenableFuture, "AsyncFunction.apply returned null instead of a Future. Did you mean to return immediateFuture(null)?");
            return listenableFuture;
        }

        @Override
        void setResult(ListenableFuture<? extends V> listenableFuture) {
            this.setFuture(listenableFuture);
        }

        @Override
        void setResult(Object object) {
            this.setResult((ListenableFuture)object);
        }

        @Override
        Object doFallback(Object object, Throwable throwable) throws Exception {
            return this.doFallback((AsyncFunction)object, (X)throwable);
        }
    }
}

