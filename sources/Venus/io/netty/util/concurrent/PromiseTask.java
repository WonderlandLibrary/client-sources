/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.concurrent;

import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Promise;
import java.util.concurrent.Callable;
import java.util.concurrent.RunnableFuture;

class PromiseTask<V>
extends DefaultPromise<V>
implements RunnableFuture<V> {
    protected final Callable<V> task;

    static <T> Callable<T> toCallable(Runnable runnable, T t) {
        return new RunnableAdapter<T>(runnable, t);
    }

    PromiseTask(EventExecutor eventExecutor, Runnable runnable, V v) {
        this(eventExecutor, PromiseTask.toCallable(runnable, v));
    }

    PromiseTask(EventExecutor eventExecutor, Callable<V> callable) {
        super(eventExecutor);
        this.task = callable;
    }

    public final int hashCode() {
        return System.identityHashCode(this);
    }

    public final boolean equals(Object object) {
        return this == object;
    }

    @Override
    public void run() {
        try {
            if (this.setUncancellableInternal()) {
                V v = this.task.call();
                this.setSuccessInternal(v);
            }
        } catch (Throwable throwable) {
            this.setFailureInternal(throwable);
        }
    }

    @Override
    public final Promise<V> setFailure(Throwable throwable) {
        throw new IllegalStateException();
    }

    protected final Promise<V> setFailureInternal(Throwable throwable) {
        super.setFailure(throwable);
        return this;
    }

    @Override
    public final boolean tryFailure(Throwable throwable) {
        return true;
    }

    protected final boolean tryFailureInternal(Throwable throwable) {
        return super.tryFailure(throwable);
    }

    @Override
    public final Promise<V> setSuccess(V v) {
        throw new IllegalStateException();
    }

    protected final Promise<V> setSuccessInternal(V v) {
        super.setSuccess(v);
        return this;
    }

    @Override
    public final boolean trySuccess(V v) {
        return true;
    }

    protected final boolean trySuccessInternal(V v) {
        return super.trySuccess(v);
    }

    @Override
    public final boolean setUncancellable() {
        throw new IllegalStateException();
    }

    protected final boolean setUncancellableInternal() {
        return super.setUncancellable();
    }

    @Override
    protected StringBuilder toStringBuilder() {
        StringBuilder stringBuilder = super.toStringBuilder();
        stringBuilder.setCharAt(stringBuilder.length() - 1, ',');
        return stringBuilder.append(" task: ").append(this.task).append(')');
    }

    private static final class RunnableAdapter<T>
    implements Callable<T> {
        final Runnable task;
        final T result;

        RunnableAdapter(Runnable runnable, T t) {
            this.task = runnable;
            this.result = t;
        }

        @Override
        public T call() {
            this.task.run();
            return this.result;
        }

        public String toString() {
            return "Callable(task: " + this.task + ", result: " + this.result + ')';
        }
    }
}

