/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.concurrent;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.apache.http.concurrent.Cancellable;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.util.Args;

public class BasicFuture<T>
implements Future<T>,
Cancellable {
    private final FutureCallback<T> callback;
    private volatile boolean completed;
    private volatile boolean cancelled;
    private volatile T result;
    private volatile Exception ex;

    public BasicFuture(FutureCallback<T> futureCallback) {
        this.callback = futureCallback;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public boolean isDone() {
        return this.completed;
    }

    private T getResult() throws ExecutionException {
        if (this.ex != null) {
            throw new ExecutionException(this.ex);
        }
        if (this.cancelled) {
            throw new CancellationException();
        }
        return this.result;
    }

    @Override
    public synchronized T get() throws InterruptedException, ExecutionException {
        while (!this.completed) {
            this.wait();
        }
        return this.getResult();
    }

    @Override
    public synchronized T get(long l, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        Args.notNull(timeUnit, "Time unit");
        long l2 = timeUnit.toMillis(l);
        long l3 = l2 <= 0L ? 0L : System.currentTimeMillis();
        long l4 = l2;
        if (this.completed) {
            return this.getResult();
        }
        if (l4 <= 0L) {
            throw new TimeoutException();
        }
        do {
            this.wait(l4);
            if (!this.completed) continue;
            return this.getResult();
        } while ((l4 = l2 - (System.currentTimeMillis() - l3)) > 0L);
        throw new TimeoutException();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean completed(T t) {
        BasicFuture basicFuture = this;
        synchronized (basicFuture) {
            if (this.completed) {
                return false;
            }
            this.completed = true;
            this.result = t;
            this.notifyAll();
        }
        if (this.callback != null) {
            this.callback.completed(t);
        }
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean failed(Exception exception) {
        BasicFuture basicFuture = this;
        synchronized (basicFuture) {
            if (this.completed) {
                return false;
            }
            this.completed = true;
            this.ex = exception;
            this.notifyAll();
        }
        if (this.callback != null) {
            this.callback.failed(exception);
        }
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean cancel(boolean bl) {
        BasicFuture basicFuture = this;
        synchronized (basicFuture) {
            if (this.completed) {
                return false;
            }
            this.completed = true;
            this.cancelled = true;
            this.notifyAll();
        }
        if (this.callback != null) {
            this.callback.cancelled();
        }
        return false;
    }

    @Override
    public boolean cancel() {
        return this.cancel(false);
    }
}

