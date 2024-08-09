/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.math;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.math.LinearTransformation;
import com.google.common.math.PairedStats;
import com.google.common.math.Stats;
import com.google.common.math.StatsAccumulator;
import com.google.common.primitives.Doubles;

@Beta
@GwtIncompatible
public final class PairedStatsAccumulator {
    private final StatsAccumulator xStats = new StatsAccumulator();
    private final StatsAccumulator yStats = new StatsAccumulator();
    private double sumOfProductsOfDeltas = 0.0;

    public void add(double d, double d2) {
        this.xStats.add(d);
        if (Doubles.isFinite(d) && Doubles.isFinite(d2)) {
            if (this.xStats.count() > 1L) {
                this.sumOfProductsOfDeltas += (d - this.xStats.mean()) * (d2 - this.yStats.mean());
            }
        } else {
            this.sumOfProductsOfDeltas = Double.NaN;
        }
        this.yStats.add(d2);
    }

    public void addAll(PairedStats pairedStats) {
        if (pairedStats.count() == 0L) {
            return;
        }
        this.xStats.addAll(pairedStats.xStats());
        this.sumOfProductsOfDeltas = this.yStats.count() == 0L ? pairedStats.sumOfProductsOfDeltas() : (this.sumOfProductsOfDeltas += pairedStats.sumOfProductsOfDeltas() + (pairedStats.xStats().mean() - this.xStats.mean()) * (pairedStats.yStats().mean() - this.yStats.mean()) * (double)pairedStats.count());
        this.yStats.addAll(pairedStats.yStats());
    }

    public PairedStats snapshot() {
        return new PairedStats(this.xStats.snapshot(), this.yStats.snapshot(), this.sumOfProductsOfDeltas);
    }

    public long count() {
        return this.xStats.count();
    }

    public Stats xStats() {
        return this.xStats.snapshot();
    }

    public Stats yStats() {
        return this.yStats.snapshot();
    }

    public double populationCovariance() {
        Preconditions.checkState(this.count() != 0L);
        return this.sumOfProductsOfDeltas / (double)this.count();
    }

    public final double sampleCovariance() {
        Preconditions.checkState(this.count() > 1L);
        return this.sumOfProductsOfDeltas / (double)(this.count() - 1L);
    }

    public final double pearsonsCorrelationCoefficient() {
        Preconditions.checkState(this.count() > 1L);
        if (Double.isNaN(this.sumOfProductsOfDeltas)) {
            return Double.NaN;
        }
        double d = this.xStats.sumOfSquaresOfDeltas();
        double d2 = this.yStats.sumOfSquaresOfDeltas();
        Preconditions.checkState(d > 0.0);
        Preconditions.checkState(d2 > 0.0);
        double d3 = this.ensurePositive(d * d2);
        return PairedStatsAccumulator.ensureInUnitRange(this.sumOfProductsOfDeltas / Math.sqrt(d3));
    }

    public final LinearTransformation leastSquaresFit() {
        Preconditions.checkState(this.count() > 1L);
        if (Double.isNaN(this.sumOfProductsOfDeltas)) {
            return LinearTransformation.forNaN();
        }
        double d = this.xStats.sumOfSquaresOfDeltas();
        if (d > 0.0) {
            if (this.yStats.sumOfSquaresOfDeltas() > 0.0) {
                return LinearTransformation.mapping(this.xStats.mean(), this.yStats.mean()).withSlope(this.sumOfProductsOfDeltas / d);
            }
            return LinearTransformation.horizontal(this.yStats.mean());
        }
        Preconditions.checkState(this.yStats.sumOfSquaresOfDeltas() > 0.0);
        return LinearTransformation.vertical(this.xStats.mean());
    }

    private double ensurePositive(double d) {
        if (d > 0.0) {
            return d;
        }
        return Double.MIN_VALUE;
    }

    private static double ensureInUnitRange(double d) {
        if (d >= 1.0) {
            return 1.0;
        }
        if (d <= -1.0) {
            return -1.0;
        }
        return d;
    }
}

