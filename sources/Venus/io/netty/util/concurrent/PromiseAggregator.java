/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.concurrent;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.Promise;
import java.util.LinkedHashSet;
import java.util.Set;

@Deprecated
public class PromiseAggregator<V, F extends Future<V>>
implements GenericFutureListener<F> {
    private final Promise<?> aggregatePromise;
    private final boolean failPending;
    private Set<Promise<V>> pendingPromises;

    public PromiseAggregator(Promise<Void> promise, boolean bl) {
        if (promise == null) {
            throw new NullPointerException("aggregatePromise");
        }
        this.aggregatePromise = promise;
        this.failPending = bl;
    }

    public PromiseAggregator(Promise<Void> promise) {
        this(promise, true);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @SafeVarargs
    public final PromiseAggregator<V, F> add(Promise<V> ... promiseArray) {
        if (promiseArray == null) {
            throw new NullPointerException("promises");
        }
        if (promiseArray.length == 0) {
            return this;
        }
        PromiseAggregator promiseAggregator = this;
        synchronized (promiseAggregator) {
            if (this.pendingPromises == null) {
                int n = promiseArray.length > 1 ? promiseArray.length : 2;
                this.pendingPromises = new LinkedHashSet<Promise<V>>(n);
            }
            for (Promise<V> promise : promiseArray) {
                if (promise == null) continue;
                this.pendingPromises.add(promise);
                promise.addListener(this);
            }
        }
        return this;
    }

    @Override
    public synchronized void operationComplete(F f) throws Exception {
        if (this.pendingPromises == null) {
            this.aggregatePromise.setSuccess(null);
        } else {
            this.pendingPromises.remove(f);
            if (!f.isSuccess()) {
                Throwable throwable = f.cause();
                this.aggregatePromise.setFailure(throwable);
                if (this.failPending) {
                    for (Promise<V> promise : this.pendingPromises) {
                        promise.setFailure(throwable);
                    }
                }
            } else if (this.pendingPromises.isEmpty()) {
                this.aggregatePromise.setSuccess(null);
            }
        }
    }
}

