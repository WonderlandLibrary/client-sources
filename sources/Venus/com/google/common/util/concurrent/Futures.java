/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Queues;
import com.google.common.util.concurrent.AbstractCatchingFuture;
import com.google.common.util.concurrent.AbstractCheckedFuture;
import com.google.common.util.concurrent.AbstractFuture;
import com.google.common.util.concurrent.AbstractTransformFuture;
import com.google.common.util.concurrent.AsyncCallable;
import com.google.common.util.concurrent.AsyncFunction;
import com.google.common.util.concurrent.CheckedFuture;
import com.google.common.util.concurrent.CollectionFuture;
import com.google.common.util.concurrent.CombinedFuture;
import com.google.common.util.concurrent.ExecutionError;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.FuturesGetChecked;
import com.google.common.util.concurrent.GwtFuturesCatchingSpecialization;
import com.google.common.util.concurrent.ImmediateFuture;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.Partially;
import com.google.common.util.concurrent.SerializingExecutor;
import com.google.common.util.concurrent.SettableFuture;
import com.google.common.util.concurrent.TimeoutFuture;
import com.google.common.util.concurrent.UncheckedExecutionException;
import com.google.common.util.concurrent.Uninterruptibles;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.annotation.Nullable;

@Beta
@GwtCompatible(emulated=true)
public final class Futures
extends GwtFuturesCatchingSpecialization {
    private static final AsyncFunction<ListenableFuture<Object>, Object> DEREFERENCER = new AsyncFunction<ListenableFuture<Object>, Object>(){

        @Override
        public ListenableFuture<Object> apply(ListenableFuture<Object> listenableFuture) {
            return listenableFuture;
        }

        @Override
        public ListenableFuture apply(Object object) throws Exception {
            return this.apply((ListenableFuture)object);
        }
    };

    private Futures() {
    }

    @GwtIncompatible
    public static <V, X extends Exception> CheckedFuture<V, X> makeChecked(ListenableFuture<V> listenableFuture, Function<? super Exception, X> function) {
        return new MappingCheckedFuture<V, X>(Preconditions.checkNotNull(listenableFuture), function);
    }

    public static <V> ListenableFuture<V> immediateFuture(@Nullable V v) {
        if (v == null) {
            ImmediateFuture.ImmediateSuccessfulFuture<Object> immediateSuccessfulFuture = ImmediateFuture.ImmediateSuccessfulFuture.NULL;
            return immediateSuccessfulFuture;
        }
        return new ImmediateFuture.ImmediateSuccessfulFuture<V>(v);
    }

    @GwtIncompatible
    public static <V, X extends Exception> CheckedFuture<V, X> immediateCheckedFuture(@Nullable V v) {
        return new ImmediateFuture.ImmediateSuccessfulCheckedFuture(v);
    }

    public static <V> ListenableFuture<V> immediateFailedFuture(Throwable throwable) {
        Preconditions.checkNotNull(throwable);
        return new ImmediateFuture.ImmediateFailedFuture(throwable);
    }

    public static <V> ListenableFuture<V> immediateCancelledFuture() {
        return new ImmediateFuture.ImmediateCancelledFuture();
    }

    @GwtIncompatible
    public static <V, X extends Exception> CheckedFuture<V, X> immediateFailedCheckedFuture(X x) {
        Preconditions.checkNotNull(x);
        return new ImmediateFuture.ImmediateFailedCheckedFuture(x);
    }

    @Partially.GwtIncompatible(value="AVAILABLE but requires exceptionType to be Throwable.class")
    public static <V, X extends Throwable> ListenableFuture<V> catching(ListenableFuture<? extends V> listenableFuture, Class<X> clazz, Function<? super X, ? extends V> function) {
        return AbstractCatchingFuture.create(listenableFuture, clazz, function);
    }

    @Partially.GwtIncompatible(value="AVAILABLE but requires exceptionType to be Throwable.class")
    public static <V, X extends Throwable> ListenableFuture<V> catching(ListenableFuture<? extends V> listenableFuture, Class<X> clazz, Function<? super X, ? extends V> function, Executor executor) {
        return AbstractCatchingFuture.create(listenableFuture, clazz, function, executor);
    }

    @CanIgnoreReturnValue
    @Partially.GwtIncompatible(value="AVAILABLE but requires exceptionType to be Throwable.class")
    public static <V, X extends Throwable> ListenableFuture<V> catchingAsync(ListenableFuture<? extends V> listenableFuture, Class<X> clazz, AsyncFunction<? super X, ? extends V> asyncFunction) {
        return AbstractCatchingFuture.create(listenableFuture, clazz, asyncFunction);
    }

    @CanIgnoreReturnValue
    @Partially.GwtIncompatible(value="AVAILABLE but requires exceptionType to be Throwable.class")
    public static <V, X extends Throwable> ListenableFuture<V> catchingAsync(ListenableFuture<? extends V> listenableFuture, Class<X> clazz, AsyncFunction<? super X, ? extends V> asyncFunction, Executor executor) {
        return AbstractCatchingFuture.create(listenableFuture, clazz, asyncFunction, executor);
    }

    @GwtIncompatible
    public static <V> ListenableFuture<V> withTimeout(ListenableFuture<V> listenableFuture, long l, TimeUnit timeUnit, ScheduledExecutorService scheduledExecutorService) {
        return TimeoutFuture.create(listenableFuture, l, timeUnit, scheduledExecutorService);
    }

    public static <I, O> ListenableFuture<O> transformAsync(ListenableFuture<I> listenableFuture, AsyncFunction<? super I, ? extends O> asyncFunction) {
        return AbstractTransformFuture.create(listenableFuture, asyncFunction);
    }

    public static <I, O> ListenableFuture<O> transformAsync(ListenableFuture<I> listenableFuture, AsyncFunction<? super I, ? extends O> asyncFunction, Executor executor) {
        return AbstractTransformFuture.create(listenableFuture, asyncFunction, executor);
    }

    public static <I, O> ListenableFuture<O> transform(ListenableFuture<I> listenableFuture, Function<? super I, ? extends O> function) {
        return AbstractTransformFuture.create(listenableFuture, function);
    }

    public static <I, O> ListenableFuture<O> transform(ListenableFuture<I> listenableFuture, Function<? super I, ? extends O> function, Executor executor) {
        return AbstractTransformFuture.create(listenableFuture, function, executor);
    }

    @GwtIncompatible
    public static <I, O> Future<O> lazyTransform(Future<I> future, Function<? super I, ? extends O> function) {
        Preconditions.checkNotNull(future);
        Preconditions.checkNotNull(function);
        return new Future<O>(future, function){
            final Future val$input;
            final Function val$function;
            {
                this.val$input = future;
                this.val$function = function;
            }

            @Override
            public boolean cancel(boolean bl) {
                return this.val$input.cancel(bl);
            }

            @Override
            public boolean isCancelled() {
                return this.val$input.isCancelled();
            }

            @Override
            public boolean isDone() {
                return this.val$input.isDone();
            }

            @Override
            public O get() throws InterruptedException, ExecutionException {
                return this.applyTransformation(this.val$input.get());
            }

            @Override
            public O get(long l, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
                return this.applyTransformation(this.val$input.get(l, timeUnit));
            }

            private O applyTransformation(I i) throws ExecutionException {
                try {
                    return this.val$function.apply(i);
                } catch (Throwable throwable) {
                    throw new ExecutionException(throwable);
                }
            }
        };
    }

    public static <V> ListenableFuture<V> dereference(ListenableFuture<? extends ListenableFuture<? extends V>> listenableFuture) {
        return Futures.transformAsync(listenableFuture, DEREFERENCER);
    }

    @SafeVarargs
    @Beta
    public static <V> ListenableFuture<List<V>> allAsList(ListenableFuture<? extends V> ... listenableFutureArray) {
        return new CollectionFuture.ListFuture<V>(ImmutableList.copyOf(listenableFutureArray), true);
    }

    @Beta
    public static <V> ListenableFuture<List<V>> allAsList(Iterable<? extends ListenableFuture<? extends V>> iterable) {
        return new CollectionFuture.ListFuture(ImmutableList.copyOf(iterable), true);
    }

    @SafeVarargs
    public static <V> FutureCombiner<V> whenAllComplete(ListenableFuture<? extends V> ... listenableFutureArray) {
        return new FutureCombiner(false, ImmutableList.copyOf(listenableFutureArray), null);
    }

    public static <V> FutureCombiner<V> whenAllComplete(Iterable<? extends ListenableFuture<? extends V>> iterable) {
        return new FutureCombiner(false, ImmutableList.copyOf(iterable), null);
    }

    @SafeVarargs
    public static <V> FutureCombiner<V> whenAllSucceed(ListenableFuture<? extends V> ... listenableFutureArray) {
        return new FutureCombiner(true, ImmutableList.copyOf(listenableFutureArray), null);
    }

    public static <V> FutureCombiner<V> whenAllSucceed(Iterable<? extends ListenableFuture<? extends V>> iterable) {
        return new FutureCombiner(true, ImmutableList.copyOf(iterable), null);
    }

    public static <V> ListenableFuture<V> nonCancellationPropagating(ListenableFuture<V> listenableFuture) {
        if (listenableFuture.isDone()) {
            return listenableFuture;
        }
        return new NonCancellationPropagatingFuture<V>(listenableFuture);
    }

    @SafeVarargs
    @Beta
    public static <V> ListenableFuture<List<V>> successfulAsList(ListenableFuture<? extends V> ... listenableFutureArray) {
        return new CollectionFuture.ListFuture<V>(ImmutableList.copyOf(listenableFutureArray), false);
    }

    @Beta
    public static <V> ListenableFuture<List<V>> successfulAsList(Iterable<? extends ListenableFuture<? extends V>> iterable) {
        return new CollectionFuture.ListFuture(ImmutableList.copyOf(iterable), false);
    }

    @Beta
    @GwtIncompatible
    public static <T> ImmutableList<ListenableFuture<T>> inCompletionOrder(Iterable<? extends ListenableFuture<? extends T>> iterable) {
        ConcurrentLinkedQueue concurrentLinkedQueue = Queues.newConcurrentLinkedQueue();
        ImmutableList.Builder builder = ImmutableList.builder();
        SerializingExecutor serializingExecutor = new SerializingExecutor(MoreExecutors.directExecutor());
        for (ListenableFuture<T> listenableFuture : iterable) {
            SettableFuture settableFuture = SettableFuture.create();
            concurrentLinkedQueue.add(settableFuture);
            listenableFuture.addListener(new Runnable(concurrentLinkedQueue, listenableFuture){
                final ConcurrentLinkedQueue val$delegates;
                final ListenableFuture val$future;
                {
                    this.val$delegates = concurrentLinkedQueue;
                    this.val$future = listenableFuture;
                }

                @Override
                public void run() {
                    ((SettableFuture)this.val$delegates.remove()).setFuture(this.val$future);
                }
            }, serializingExecutor);
            builder.add(settableFuture);
        }
        return builder.build();
    }

    public static <V> void addCallback(ListenableFuture<V> listenableFuture, FutureCallback<? super V> futureCallback) {
        Futures.addCallback(listenableFuture, futureCallback, MoreExecutors.directExecutor());
    }

    public static <V> void addCallback(ListenableFuture<V> listenableFuture, FutureCallback<? super V> futureCallback, Executor executor) {
        Preconditions.checkNotNull(futureCallback);
        Runnable runnable = new Runnable(listenableFuture, futureCallback){
            final ListenableFuture val$future;
            final FutureCallback val$callback;
            {
                this.val$future = listenableFuture;
                this.val$callback = futureCallback;
            }

            @Override
            public void run() {
                Object v;
                try {
                    v = Futures.getDone(this.val$future);
                } catch (ExecutionException executionException) {
                    this.val$callback.onFailure(executionException.getCause());
                    return;
                } catch (RuntimeException runtimeException) {
                    this.val$callback.onFailure(runtimeException);
                    return;
                } catch (Error error2) {
                    this.val$callback.onFailure(error2);
                    return;
                }
                this.val$callback.onSuccess(v);
            }
        };
        listenableFuture.addListener(runnable, executor);
    }

    @CanIgnoreReturnValue
    public static <V> V getDone(Future<V> future) throws ExecutionException {
        Preconditions.checkState(future.isDone(), "Future was expected to be done: %s", future);
        return Uninterruptibles.getUninterruptibly(future);
    }

    @CanIgnoreReturnValue
    @GwtIncompatible
    public static <V, X extends Exception> V getChecked(Future<V> future, Class<X> clazz) throws X {
        return FuturesGetChecked.getChecked(future, clazz);
    }

    @CanIgnoreReturnValue
    @GwtIncompatible
    public static <V, X extends Exception> V getChecked(Future<V> future, Class<X> clazz, long l, TimeUnit timeUnit) throws X {
        return FuturesGetChecked.getChecked(future, clazz, l, timeUnit);
    }

    @CanIgnoreReturnValue
    @GwtIncompatible
    public static <V> V getUnchecked(Future<V> future) {
        Preconditions.checkNotNull(future);
        try {
            return Uninterruptibles.getUninterruptibly(future);
        } catch (ExecutionException executionException) {
            Futures.wrapAndThrowUnchecked(executionException.getCause());
            throw new AssertionError();
        }
    }

    @GwtIncompatible
    private static void wrapAndThrowUnchecked(Throwable throwable) {
        if (throwable instanceof Error) {
            throw new ExecutionError((Error)throwable);
        }
        throw new UncheckedExecutionException(throwable);
    }

    @GwtIncompatible
    private static class MappingCheckedFuture<V, X extends Exception>
    extends AbstractCheckedFuture<V, X> {
        final Function<? super Exception, X> mapper;

        MappingCheckedFuture(ListenableFuture<V> listenableFuture, Function<? super Exception, X> function) {
            super(listenableFuture);
            this.mapper = Preconditions.checkNotNull(function);
        }

        @Override
        protected X mapException(Exception exception) {
            return (X)((Exception)this.mapper.apply(exception));
        }
    }

    private static final class NonCancellationPropagatingFuture<V>
    extends AbstractFuture.TrustedFuture<V> {
        NonCancellationPropagatingFuture(ListenableFuture<V> listenableFuture) {
            listenableFuture.addListener(new Runnable(this, listenableFuture){
                final ListenableFuture val$delegate;
                final NonCancellationPropagatingFuture this$0;
                {
                    this.this$0 = nonCancellationPropagatingFuture;
                    this.val$delegate = listenableFuture;
                }

                @Override
                public void run() {
                    this.this$0.setFuture(this.val$delegate);
                }
            }, MoreExecutors.directExecutor());
        }
    }

    @Beta
    @CanIgnoreReturnValue
    @GwtCompatible
    public static final class FutureCombiner<V> {
        private final boolean allMustSucceed;
        private final ImmutableList<ListenableFuture<? extends V>> futures;

        private FutureCombiner(boolean bl, ImmutableList<ListenableFuture<? extends V>> immutableList) {
            this.allMustSucceed = bl;
            this.futures = immutableList;
        }

        public <C> ListenableFuture<C> callAsync(AsyncCallable<C> asyncCallable, Executor executor) {
            return new CombinedFuture<C>(this.futures, this.allMustSucceed, executor, asyncCallable);
        }

        public <C> ListenableFuture<C> callAsync(AsyncCallable<C> asyncCallable) {
            return this.callAsync(asyncCallable, MoreExecutors.directExecutor());
        }

        @CanIgnoreReturnValue
        public <C> ListenableFuture<C> call(Callable<C> callable, Executor executor) {
            return new CombinedFuture<C>(this.futures, this.allMustSucceed, executor, callable);
        }

        @CanIgnoreReturnValue
        public <C> ListenableFuture<C> call(Callable<C> callable) {
            return this.call(callable, MoreExecutors.directExecutor());
        }

        FutureCombiner(boolean bl, ImmutableList immutableList, 1 var3_3) {
            this(bl, immutableList);
        }
    }
}

