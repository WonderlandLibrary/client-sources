/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.AbstractFuture;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.annotation.Nullable;

@GwtIncompatible
final class TimeoutFuture<V>
extends AbstractFuture.TrustedFuture<V> {
    @Nullable
    private ListenableFuture<V> delegateRef;
    @Nullable
    private Future<?> timer;

    static <V> ListenableFuture<V> create(ListenableFuture<V> listenableFuture, long l, TimeUnit timeUnit, ScheduledExecutorService scheduledExecutorService) {
        TimeoutFuture<V> timeoutFuture = new TimeoutFuture<V>(listenableFuture);
        Fire<V> fire = new Fire<V>(timeoutFuture);
        timeoutFuture.timer = scheduledExecutorService.schedule(fire, l, timeUnit);
        listenableFuture.addListener(fire, MoreExecutors.directExecutor());
        return timeoutFuture;
    }

    private TimeoutFuture(ListenableFuture<V> listenableFuture) {
        this.delegateRef = Preconditions.checkNotNull(listenableFuture);
    }

    @Override
    protected void afterDone() {
        this.maybePropagateCancellation(this.delegateRef);
        Future<?> future = this.timer;
        if (future != null) {
            future.cancel(false);
        }
        this.delegateRef = null;
        this.timer = null;
    }

    static ListenableFuture access$000(TimeoutFuture timeoutFuture) {
        return timeoutFuture.delegateRef;
    }

    private static final class Fire<V>
    implements Runnable {
        @Nullable
        TimeoutFuture<V> timeoutFutureRef;

        Fire(TimeoutFuture<V> timeoutFuture) {
            this.timeoutFutureRef = timeoutFuture;
        }

        @Override
        public void run() {
            TimeoutFuture<V> timeoutFuture = this.timeoutFutureRef;
            if (timeoutFuture == null) {
                return;
            }
            ListenableFuture listenableFuture = TimeoutFuture.access$000(timeoutFuture);
            if (listenableFuture == null) {
                return;
            }
            this.timeoutFutureRef = null;
            if (listenableFuture.isDone()) {
                timeoutFuture.setFuture(listenableFuture);
            } else {
                try {
                    timeoutFuture.setException(new TimeoutException("Future timed out: " + listenableFuture));
                } finally {
                    listenableFuture.cancel(true);
                }
            }
        }
    }
}

