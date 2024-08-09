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
import com.google.errorprone.annotations.ForOverride;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;

@GwtCompatible
abstract class AbstractTransformFuture<I, O, F, T>
extends AbstractFuture.TrustedFuture<O>
implements Runnable {
    @Nullable
    ListenableFuture<? extends I> inputFuture;
    @Nullable
    F function;

    static <I, O> ListenableFuture<O> create(ListenableFuture<I> listenableFuture, AsyncFunction<? super I, ? extends O> asyncFunction) {
        AsyncTransformFuture<? super I, ? extends O> asyncTransformFuture = new AsyncTransformFuture<I, O>(listenableFuture, asyncFunction);
        listenableFuture.addListener(asyncTransformFuture, MoreExecutors.directExecutor());
        return asyncTransformFuture;
    }

    static <I, O> ListenableFuture<O> create(ListenableFuture<I> listenableFuture, AsyncFunction<? super I, ? extends O> asyncFunction, Executor executor) {
        Preconditions.checkNotNull(executor);
        AsyncTransformFuture<? super I, ? extends O> asyncTransformFuture = new AsyncTransformFuture<I, O>(listenableFuture, asyncFunction);
        listenableFuture.addListener(asyncTransformFuture, MoreExecutors.rejectionPropagatingExecutor(executor, asyncTransformFuture));
        return asyncTransformFuture;
    }

    static <I, O> ListenableFuture<O> create(ListenableFuture<I> listenableFuture, Function<? super I, ? extends O> function) {
        Preconditions.checkNotNull(function);
        TransformFuture<? super I, ? extends O> transformFuture = new TransformFuture<I, O>(listenableFuture, function);
        listenableFuture.addListener(transformFuture, MoreExecutors.directExecutor());
        return transformFuture;
    }

    static <I, O> ListenableFuture<O> create(ListenableFuture<I> listenableFuture, Function<? super I, ? extends O> function, Executor executor) {
        Preconditions.checkNotNull(function);
        TransformFuture<? super I, ? extends O> transformFuture = new TransformFuture<I, O>(listenableFuture, function);
        listenableFuture.addListener(transformFuture, MoreExecutors.rejectionPropagatingExecutor(executor, transformFuture));
        return transformFuture;
    }

    AbstractTransformFuture(ListenableFuture<? extends I> listenableFuture, F f) {
        this.inputFuture = Preconditions.checkNotNull(listenableFuture);
        this.function = Preconditions.checkNotNull(f);
    }

    @Override
    public final void run() {
        T t;
        I i;
        ListenableFuture<? extends I> listenableFuture = this.inputFuture;
        F f = this.function;
        if (this.isCancelled() | listenableFuture == null | f == null) {
            return;
        }
        this.inputFuture = null;
        this.function = null;
        try {
            i = Futures.getDone(listenableFuture);
        } catch (CancellationException cancellationException) {
            this.cancel(true);
            return;
        } catch (ExecutionException executionException) {
            this.setException(executionException.getCause());
            return;
        } catch (RuntimeException runtimeException) {
            this.setException(runtimeException);
            return;
        } catch (Error error2) {
            this.setException(error2);
            return;
        }
        try {
            t = this.doTransform(f, i);
        } catch (UndeclaredThrowableException undeclaredThrowableException) {
            this.setException(undeclaredThrowableException.getCause());
            return;
        } catch (Throwable throwable) {
            this.setException(throwable);
            return;
        }
        this.setResult(t);
    }

    @Nullable
    @ForOverride
    abstract T doTransform(F var1, @Nullable I var2) throws Exception;

    @ForOverride
    abstract void setResult(@Nullable T var1);

    @Override
    protected final void afterDone() {
        this.maybePropagateCancellation(this.inputFuture);
        this.inputFuture = null;
        this.function = null;
    }

    private static final class TransformFuture<I, O>
    extends AbstractTransformFuture<I, O, Function<? super I, ? extends O>, O> {
        TransformFuture(ListenableFuture<? extends I> listenableFuture, Function<? super I, ? extends O> function) {
            super(listenableFuture, function);
        }

        @Override
        @Nullable
        O doTransform(Function<? super I, ? extends O> function, @Nullable I i) {
            return function.apply(i);
        }

        @Override
        void setResult(@Nullable O o) {
            this.set(o);
        }

        @Override
        @Nullable
        Object doTransform(Object object, @Nullable Object object2) throws Exception {
            return this.doTransform((Function)object, object2);
        }
    }

    private static final class AsyncTransformFuture<I, O>
    extends AbstractTransformFuture<I, O, AsyncFunction<? super I, ? extends O>, ListenableFuture<? extends O>> {
        AsyncTransformFuture(ListenableFuture<? extends I> listenableFuture, AsyncFunction<? super I, ? extends O> asyncFunction) {
            super(listenableFuture, asyncFunction);
        }

        @Override
        ListenableFuture<? extends O> doTransform(AsyncFunction<? super I, ? extends O> asyncFunction, @Nullable I i) throws Exception {
            ListenableFuture<? extends O> listenableFuture = asyncFunction.apply(i);
            Preconditions.checkNotNull(listenableFuture, "AsyncFunction.apply returned null instead of a Future. Did you mean to return immediateFuture(null)?");
            return listenableFuture;
        }

        @Override
        void setResult(ListenableFuture<? extends O> listenableFuture) {
            this.setFuture(listenableFuture);
        }

        @Override
        void setResult(Object object) {
            this.setResult((ListenableFuture)object);
        }

        @Override
        Object doTransform(Object object, @Nullable Object object2) throws Exception {
            return this.doTransform((AsyncFunction)object, object2);
        }
    }
}

