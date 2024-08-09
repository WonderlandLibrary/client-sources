/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.util.concurrent.WrappingExecutorService;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@CanIgnoreReturnValue
@GwtIncompatible
abstract class WrappingScheduledExecutorService
extends WrappingExecutorService
implements ScheduledExecutorService {
    final ScheduledExecutorService delegate;

    protected WrappingScheduledExecutorService(ScheduledExecutorService scheduledExecutorService) {
        super(scheduledExecutorService);
        this.delegate = scheduledExecutorService;
    }

    @Override
    public final ScheduledFuture<?> schedule(Runnable runnable, long l, TimeUnit timeUnit) {
        return this.delegate.schedule(this.wrapTask(runnable), l, timeUnit);
    }

    @Override
    public final <V> ScheduledFuture<V> schedule(Callable<V> callable, long l, TimeUnit timeUnit) {
        return this.delegate.schedule(this.wrapTask(callable), l, timeUnit);
    }

    @Override
    public final ScheduledFuture<?> scheduleAtFixedRate(Runnable runnable, long l, long l2, TimeUnit timeUnit) {
        return this.delegate.scheduleAtFixedRate(this.wrapTask(runnable), l, l2, timeUnit);
    }

    @Override
    public final ScheduledFuture<?> scheduleWithFixedDelay(Runnable runnable, long l, long l2, TimeUnit timeUnit) {
        return this.delegate.scheduleWithFixedDelay(this.wrapTask(runnable), l, l2, timeUnit);
    }
}

