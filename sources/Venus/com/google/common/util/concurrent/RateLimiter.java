/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import com.google.common.util.concurrent.SmoothRateLimiter;
import com.google.common.util.concurrent.Uninterruptibles;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
@Beta
@GwtIncompatible
public abstract class RateLimiter {
    private final SleepingStopwatch stopwatch;
    private volatile Object mutexDoNotUseDirectly;

    public static RateLimiter create(double d) {
        return RateLimiter.create(SleepingStopwatch.createFromSystemTimer(), d);
    }

    @VisibleForTesting
    static RateLimiter create(SleepingStopwatch sleepingStopwatch, double d) {
        SmoothRateLimiter.SmoothBursty smoothBursty = new SmoothRateLimiter.SmoothBursty(sleepingStopwatch, 1.0);
        smoothBursty.setRate(d);
        return smoothBursty;
    }

    public static RateLimiter create(double d, long l, TimeUnit timeUnit) {
        Preconditions.checkArgument(l >= 0L, "warmupPeriod must not be negative: %s", l);
        return RateLimiter.create(SleepingStopwatch.createFromSystemTimer(), d, l, timeUnit, 3.0);
    }

    @VisibleForTesting
    static RateLimiter create(SleepingStopwatch sleepingStopwatch, double d, long l, TimeUnit timeUnit, double d2) {
        SmoothRateLimiter.SmoothWarmingUp smoothWarmingUp = new SmoothRateLimiter.SmoothWarmingUp(sleepingStopwatch, l, timeUnit, d2);
        smoothWarmingUp.setRate(d);
        return smoothWarmingUp;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Object mutex() {
        Object object = this.mutexDoNotUseDirectly;
        if (object == null) {
            RateLimiter rateLimiter = this;
            synchronized (rateLimiter) {
                object = this.mutexDoNotUseDirectly;
                if (object == null) {
                    this.mutexDoNotUseDirectly = object = new Object();
                }
            }
        }
        return object;
    }

    RateLimiter(SleepingStopwatch sleepingStopwatch) {
        this.stopwatch = Preconditions.checkNotNull(sleepingStopwatch);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void setRate(double d) {
        Preconditions.checkArgument(d > 0.0 && !Double.isNaN(d), "rate must be positive");
        Object object = this.mutex();
        synchronized (object) {
            this.doSetRate(d, this.stopwatch.readMicros());
        }
    }

    abstract void doSetRate(double var1, long var3);

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final double getRate() {
        Object object = this.mutex();
        synchronized (object) {
            return this.doGetRate();
        }
    }

    abstract double doGetRate();

    @CanIgnoreReturnValue
    public double acquire() {
        return this.acquire(1);
    }

    @CanIgnoreReturnValue
    public double acquire(int n) {
        long l = this.reserve(n);
        this.stopwatch.sleepMicrosUninterruptibly(l);
        return 1.0 * (double)l / (double)TimeUnit.SECONDS.toMicros(1L);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    final long reserve(int n) {
        RateLimiter.checkPermits(n);
        Object object = this.mutex();
        synchronized (object) {
            return this.reserveAndGetWaitLength(n, this.stopwatch.readMicros());
        }
    }

    public boolean tryAcquire(long l, TimeUnit timeUnit) {
        return this.tryAcquire(1, l, timeUnit);
    }

    public boolean tryAcquire(int n) {
        return this.tryAcquire(n, 0L, TimeUnit.MICROSECONDS);
    }

    public boolean tryAcquire() {
        return this.tryAcquire(1, 0L, TimeUnit.MICROSECONDS);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean tryAcquire(int n, long l, TimeUnit timeUnit) {
        long l2;
        long l3 = Math.max(timeUnit.toMicros(l), 0L);
        RateLimiter.checkPermits(n);
        Object object = this.mutex();
        synchronized (object) {
            long l4 = this.stopwatch.readMicros();
            if (!this.canAcquire(l4, l3)) {
                return false;
            }
            l2 = this.reserveAndGetWaitLength(n, l4);
        }
        this.stopwatch.sleepMicrosUninterruptibly(l2);
        return false;
    }

    private boolean canAcquire(long l, long l2) {
        return this.queryEarliestAvailable(l) - l2 <= l;
    }

    final long reserveAndGetWaitLength(int n, long l) {
        long l2 = this.reserveEarliestAvailable(n, l);
        return Math.max(l2 - l, 0L);
    }

    abstract long queryEarliestAvailable(long var1);

    abstract long reserveEarliestAvailable(int var1, long var2);

    public String toString() {
        return String.format(Locale.ROOT, "RateLimiter[stableRate=%3.1fqps]", this.getRate());
    }

    private static void checkPermits(int n) {
        Preconditions.checkArgument(n > 0, "Requested permits (%s) must be positive", n);
    }

    static abstract class SleepingStopwatch {
        protected SleepingStopwatch() {
        }

        protected abstract long readMicros();

        protected abstract void sleepMicrosUninterruptibly(long var1);

        public static final SleepingStopwatch createFromSystemTimer() {
            return new SleepingStopwatch(){
                final Stopwatch stopwatch = Stopwatch.createStarted();

                @Override
                protected long readMicros() {
                    return this.stopwatch.elapsed(TimeUnit.MICROSECONDS);
                }

                @Override
                protected void sleepMicrosUninterruptibly(long l) {
                    if (l > 0L) {
                        Uninterruptibles.sleepUninterruptibly(l, TimeUnit.MICROSECONDS);
                    }
                }
            };
        }
    }
}

