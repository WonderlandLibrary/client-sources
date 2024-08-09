/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.concurrent;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.Promise;
import io.netty.util.internal.ObjectUtil;

public final class PromiseCombiner {
    private int expectedCount;
    private int doneCount;
    private boolean doneAdding;
    private Promise<Void> aggregatePromise;
    private Throwable cause;
    private final GenericFutureListener<Future<?>> listener = new GenericFutureListener<Future<?>>(this){
        final PromiseCombiner this$0;
        {
            this.this$0 = promiseCombiner;
        }

        @Override
        public void operationComplete(Future<?> future) throws Exception {
            PromiseCombiner.access$004(this.this$0);
            if (!future.isSuccess() && PromiseCombiner.access$100(this.this$0) == null) {
                PromiseCombiner.access$102(this.this$0, future.cause());
            }
            if (PromiseCombiner.access$000(this.this$0) == PromiseCombiner.access$200(this.this$0) && PromiseCombiner.access$300(this.this$0)) {
                PromiseCombiner.access$400(this.this$0);
            }
        }
    };

    @Deprecated
    public void add(Promise promise) {
        this.add((Future)promise);
    }

    public void add(Future future) {
        this.checkAddAllowed();
        ++this.expectedCount;
        future.addListener(this.listener);
    }

    @Deprecated
    public void addAll(Promise ... promiseArray) {
        this.addAll((Future[])promiseArray);
    }

    public void addAll(Future ... futureArray) {
        for (Future future : futureArray) {
            this.add(future);
        }
    }

    public void finish(Promise<Void> promise) {
        if (this.doneAdding) {
            throw new IllegalStateException("Already finished");
        }
        this.doneAdding = true;
        this.aggregatePromise = ObjectUtil.checkNotNull(promise, "aggregatePromise");
        if (this.doneCount == this.expectedCount) {
            this.tryPromise();
        }
    }

    private boolean tryPromise() {
        return this.cause == null ? this.aggregatePromise.trySuccess(null) : this.aggregatePromise.tryFailure(this.cause);
    }

    private void checkAddAllowed() {
        if (this.doneAdding) {
            throw new IllegalStateException("Adding promises is not allowed after finished adding");
        }
    }

    static int access$004(PromiseCombiner promiseCombiner) {
        return ++promiseCombiner.doneCount;
    }

    static Throwable access$100(PromiseCombiner promiseCombiner) {
        return promiseCombiner.cause;
    }

    static Throwable access$102(PromiseCombiner promiseCombiner, Throwable throwable) {
        promiseCombiner.cause = throwable;
        return promiseCombiner.cause;
    }

    static int access$000(PromiseCombiner promiseCombiner) {
        return promiseCombiner.doneCount;
    }

    static int access$200(PromiseCombiner promiseCombiner) {
        return promiseCombiner.expectedCount;
    }

    static boolean access$300(PromiseCombiner promiseCombiner) {
        return promiseCombiner.doneAdding;
    }

    static boolean access$400(PromiseCombiner promiseCombiner) {
        return promiseCombiner.tryPromise();
    }
}

