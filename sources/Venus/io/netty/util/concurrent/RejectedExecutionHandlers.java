/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.concurrent;

import io.netty.util.concurrent.RejectedExecutionHandler;
import io.netty.util.concurrent.SingleThreadEventExecutor;
import io.netty.util.internal.ObjectUtil;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public final class RejectedExecutionHandlers {
    private static final RejectedExecutionHandler REJECT = new RejectedExecutionHandler(){

        @Override
        public void rejected(Runnable runnable, SingleThreadEventExecutor singleThreadEventExecutor) {
            throw new RejectedExecutionException();
        }
    };

    private RejectedExecutionHandlers() {
    }

    public static RejectedExecutionHandler reject() {
        return REJECT;
    }

    public static RejectedExecutionHandler backoff(int n, long l, TimeUnit timeUnit) {
        ObjectUtil.checkPositive(n, "retries");
        long l2 = timeUnit.toNanos(l);
        return new RejectedExecutionHandler(n, l2){
            final int val$retries;
            final long val$backOffNanos;
            {
                this.val$retries = n;
                this.val$backOffNanos = l;
            }

            @Override
            public void rejected(Runnable runnable, SingleThreadEventExecutor singleThreadEventExecutor) {
                if (!singleThreadEventExecutor.inEventLoop()) {
                    for (int i = 0; i < this.val$retries; ++i) {
                        singleThreadEventExecutor.wakeup(true);
                        LockSupport.parkNanos(this.val$backOffNanos);
                        if (!singleThreadEventExecutor.offerTask(runnable)) continue;
                        return;
                    }
                }
                throw new RejectedExecutionException();
            }
        };
    }
}

