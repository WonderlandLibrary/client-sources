/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.math;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.math.DoubleUtils;
import com.google.common.math.Stats;
import com.google.common.primitives.Doubles;
import java.util.Iterator;

@Beta
@GwtIncompatible
public final class StatsAccumulator {
    private long count = 0L;
    private double mean = 0.0;
    private double sumOfSquaresOfDeltas = 0.0;
    private double min = Double.NaN;
    private double max = Double.NaN;

    public void add(double d) {
        if (this.count == 0L) {
            this.count = 1L;
            this.mean = d;
            this.min = d;
            this.max = d;
            if (!Doubles.isFinite(d)) {
                this.sumOfSquaresOfDeltas = Double.NaN;
            }
        } else {
            ++this.count;
            if (Doubles.isFinite(d) && Doubles.isFinite(this.mean)) {
                double d2 = d - this.mean;
                this.mean += d2 / (double)this.count;
                this.sumOfSquaresOfDeltas += d2 * (d - this.mean);
            } else {
                this.mean = StatsAccumulator.calculateNewMeanNonFinite(this.mean, d);
                this.sumOfSquaresOfDeltas = Double.NaN;
            }
            this.min = Math.min(this.min, d);
            this.max = Math.max(this.max, d);
        }
    }

    public void addAll(Iterable<? extends Number> iterable) {
        for (Number number : iterable) {
            this.add(number.doubleValue());
        }
    }

    public void addAll(Iterator<? extends Number> iterator2) {
        while (iterator2.hasNext()) {
            this.add(iterator2.next().doubleValue());
        }
    }

    public void addAll(double ... dArray) {
        for (double d : dArray) {
            this.add(d);
        }
    }

    public void addAll(int ... nArray) {
        for (int n : nArray) {
            this.add(n);
        }
    }

    public void addAll(long ... lArray) {
        for (long l : lArray) {
            this.add(l);
        }
    }

    public void addAll(Stats stats) {
        if (stats.count() == 0L) {
            return;
        }
        if (this.count == 0L) {
            this.count = stats.count();
            this.mean = stats.mean();
            this.sumOfSquaresOfDeltas = stats.sumOfSquaresOfDeltas();
            this.min = stats.min();
            this.max = stats.max();
        } else {
            this.count += stats.count();
            if (Doubles.isFinite(this.mean) && Doubles.isFinite(stats.mean())) {
                double d = stats.mean() - this.mean;
                this.mean += d * (double)stats.count() / (double)this.count;
                this.sumOfSquaresOfDeltas += stats.sumOfSquaresOfDeltas() + d * (stats.mean() - this.mean) * (double)stats.count();
            } else {
                this.mean = StatsAccumulator.calculateNewMeanNonFinite(this.mean, stats.mean());
                this.sumOfSquaresOfDeltas = Double.NaN;
            }
            this.min = Math.min(this.min, stats.min());
            this.max = Math.max(this.max, stats.max());
        }
    }

    public Stats snapshot() {
        return new Stats(this.count, this.mean, this.sumOfSquaresOfDeltas, this.min, this.max);
    }

    public long count() {
        return this.count;
    }

    public double mean() {
        Preconditions.checkState(this.count != 0L);
        return this.mean;
    }

    public final double sum() {
        return this.mean * (double)this.count;
    }

    public final double populationVariance() {
        Preconditions.checkState(this.count != 0L);
        if (Double.isNaN(this.sumOfSquaresOfDeltas)) {
            return Double.NaN;
        }
        if (this.count == 1L) {
            return 0.0;
        }
        return DoubleUtils.ensureNonNegative(this.sumOfSquaresOfDeltas) / (double)this.count;
    }

    public final double populationStandardDeviation() {
        return Math.sqrt(this.populationVariance());
    }

    public final double sampleVariance() {
        Preconditions.checkState(this.count > 1L);
        if (Double.isNaN(this.sumOfSquaresOfDeltas)) {
            return Double.NaN;
        }
        return DoubleUtils.ensureNonNegative(this.sumOfSquaresOfDeltas) / (double)(this.count - 1L);
    }

    public final double sampleStandardDeviation() {
        return Math.sqrt(this.sampleVariance());
    }

    public double min() {
        Preconditions.checkState(this.count != 0L);
        return this.min;
    }

    public double max() {
        Preconditions.checkState(this.count != 0L);
        return this.max;
    }

    double sumOfSquaresOfDeltas() {
        return this.sumOfSquaresOfDeltas;
    }

    static double calculateNewMeanNonFinite(double d, double d2) {
        if (Doubles.isFinite(d)) {
            return d2;
        }
        if (Doubles.isFinite(d2) || d == d2) {
            return d;
        }
        return Double.NaN;
    }
}

