/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.math.LongMath;
import com.google.common.util.concurrent.RateLimiter;
import java.util.concurrent.TimeUnit;

@GwtIncompatible
abstract class SmoothRateLimiter
extends RateLimiter {
    double storedPermits;
    double maxPermits;
    double stableIntervalMicros;
    private long nextFreeTicketMicros = 0L;

    private SmoothRateLimiter(RateLimiter.SleepingStopwatch sleepingStopwatch) {
        super(sleepingStopwatch);
    }

    @Override
    final void doSetRate(double d, long l) {
        double d2;
        this.resync(l);
        this.stableIntervalMicros = d2 = (double)TimeUnit.SECONDS.toMicros(1L) / d;
        this.doSetRate(d, d2);
    }

    abstract void doSetRate(double var1, double var3);

    @Override
    final double doGetRate() {
        return (double)TimeUnit.SECONDS.toMicros(1L) / this.stableIntervalMicros;
    }

    @Override
    final long queryEarliestAvailable(long l) {
        return this.nextFreeTicketMicros;
    }

    @Override
    final long reserveEarliestAvailable(int n, long l) {
        this.resync(l);
        long l2 = this.nextFreeTicketMicros;
        double d = Math.min((double)n, this.storedPermits);
        double d2 = (double)n - d;
        long l3 = this.storedPermitsToWaitTime(this.storedPermits, d) + (long)(d2 * this.stableIntervalMicros);
        this.nextFreeTicketMicros = LongMath.saturatedAdd(this.nextFreeTicketMicros, l3);
        this.storedPermits -= d;
        return l2;
    }

    abstract long storedPermitsToWaitTime(double var1, double var3);

    abstract double coolDownIntervalMicros();

    void resync(long l) {
        if (l > this.nextFreeTicketMicros) {
            double d = (double)(l - this.nextFreeTicketMicros) / this.coolDownIntervalMicros();
            this.storedPermits = Math.min(this.maxPermits, this.storedPermits + d);
            this.nextFreeTicketMicros = l;
        }
    }

    SmoothRateLimiter(RateLimiter.SleepingStopwatch sleepingStopwatch, 1 var2_2) {
        this(sleepingStopwatch);
    }

    static final class SmoothBursty
    extends SmoothRateLimiter {
        final double maxBurstSeconds;

        SmoothBursty(RateLimiter.SleepingStopwatch sleepingStopwatch, double d) {
            super(sleepingStopwatch, null);
            this.maxBurstSeconds = d;
        }

        @Override
        void doSetRate(double d, double d2) {
            double d3 = this.maxPermits;
            this.maxPermits = this.maxBurstSeconds * d;
            this.storedPermits = d3 == Double.POSITIVE_INFINITY ? this.maxPermits : (d3 == 0.0 ? 0.0 : this.storedPermits * this.maxPermits / d3);
        }

        @Override
        long storedPermitsToWaitTime(double d, double d2) {
            return 0L;
        }

        @Override
        double coolDownIntervalMicros() {
            return this.stableIntervalMicros;
        }
    }

    static final class SmoothWarmingUp
    extends SmoothRateLimiter {
        private final long warmupPeriodMicros;
        private double slope;
        private double thresholdPermits;
        private double coldFactor;

        SmoothWarmingUp(RateLimiter.SleepingStopwatch sleepingStopwatch, long l, TimeUnit timeUnit, double d) {
            super(sleepingStopwatch, null);
            this.warmupPeriodMicros = timeUnit.toMicros(l);
            this.coldFactor = d;
        }

        @Override
        void doSetRate(double d, double d2) {
            double d3 = this.maxPermits;
            double d4 = d2 * this.coldFactor;
            this.thresholdPermits = 0.5 * (double)this.warmupPeriodMicros / d2;
            this.maxPermits = this.thresholdPermits + 2.0 * (double)this.warmupPeriodMicros / (d2 + d4);
            this.slope = (d4 - d2) / (this.maxPermits - this.thresholdPermits);
            this.storedPermits = d3 == Double.POSITIVE_INFINITY ? 0.0 : (d3 == 0.0 ? this.maxPermits : this.storedPermits * this.maxPermits / d3);
        }

        @Override
        long storedPermitsToWaitTime(double d, double d2) {
            double d3 = d - this.thresholdPermits;
            long l = 0L;
            if (d3 > 0.0) {
                double d4 = Math.min(d3, d2);
                double d5 = this.permitsToTime(d3) + this.permitsToTime(d3 - d4);
                l = (long)(d4 * d5 / 2.0);
                d2 -= d4;
            }
            l = (long)((double)l + this.stableIntervalMicros * d2);
            return l;
        }

        private double permitsToTime(double d) {
            return this.stableIntervalMicros + d * this.slope;
        }

        @Override
        double coolDownIntervalMicros() {
            return (double)this.warmupPeriodMicros / this.maxPermits;
        }
    }
}

