/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.math;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.math.LinearTransformation;
import com.google.common.math.Stats;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import javax.annotation.Nullable;

@Beta
@GwtIncompatible
public final class PairedStats
implements Serializable {
    private final Stats xStats;
    private final Stats yStats;
    private final double sumOfProductsOfDeltas;
    private static final int BYTES = 88;
    private static final long serialVersionUID = 0L;

    PairedStats(Stats stats, Stats stats2, double d) {
        this.xStats = stats;
        this.yStats = stats2;
        this.sumOfProductsOfDeltas = d;
    }

    public long count() {
        return this.xStats.count();
    }

    public Stats xStats() {
        return this.xStats;
    }

    public Stats yStats() {
        return this.yStats;
    }

    public double populationCovariance() {
        Preconditions.checkState(this.count() != 0L);
        return this.sumOfProductsOfDeltas / (double)this.count();
    }

    public double sampleCovariance() {
        Preconditions.checkState(this.count() > 1L);
        return this.sumOfProductsOfDeltas / (double)(this.count() - 1L);
    }

    public double pearsonsCorrelationCoefficient() {
        Preconditions.checkState(this.count() > 1L);
        if (Double.isNaN(this.sumOfProductsOfDeltas)) {
            return Double.NaN;
        }
        double d = this.xStats().sumOfSquaresOfDeltas();
        double d2 = this.yStats().sumOfSquaresOfDeltas();
        Preconditions.checkState(d > 0.0);
        Preconditions.checkState(d2 > 0.0);
        double d3 = PairedStats.ensurePositive(d * d2);
        return PairedStats.ensureInUnitRange(this.sumOfProductsOfDeltas / Math.sqrt(d3));
    }

    public LinearTransformation leastSquaresFit() {
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

    public boolean equals(@Nullable Object object) {
        if (object == null) {
            return true;
        }
        if (this.getClass() != object.getClass()) {
            return true;
        }
        PairedStats pairedStats = (PairedStats)object;
        return this.xStats.equals(pairedStats.xStats) && this.yStats.equals(pairedStats.yStats) && Double.doubleToLongBits(this.sumOfProductsOfDeltas) == Double.doubleToLongBits(pairedStats.sumOfProductsOfDeltas);
    }

    public int hashCode() {
        return Objects.hashCode(this.xStats, this.yStats, this.sumOfProductsOfDeltas);
    }

    public String toString() {
        if (this.count() > 0L) {
            return MoreObjects.toStringHelper(this).add("xStats", this.xStats).add("yStats", this.yStats).add("populationCovariance", this.populationCovariance()).toString();
        }
        return MoreObjects.toStringHelper(this).add("xStats", this.xStats).add("yStats", this.yStats).toString();
    }

    double sumOfProductsOfDeltas() {
        return this.sumOfProductsOfDeltas;
    }

    private static double ensurePositive(double d) {
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

    public byte[] toByteArray() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(88).order(ByteOrder.LITTLE_ENDIAN);
        this.xStats.writeTo(byteBuffer);
        this.yStats.writeTo(byteBuffer);
        byteBuffer.putDouble(this.sumOfProductsOfDeltas);
        return byteBuffer.array();
    }

    public static PairedStats fromByteArray(byte[] byArray) {
        Preconditions.checkNotNull(byArray);
        Preconditions.checkArgument(byArray.length == 88, "Expected PairedStats.BYTES = %s, got %s", 88, byArray.length);
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray).order(ByteOrder.LITTLE_ENDIAN);
        Stats stats = Stats.readFrom(byteBuffer);
        Stats stats2 = Stats.readFrom(byteBuffer);
        double d = byteBuffer.getDouble();
        return new PairedStats(stats, stats2, d);
    }
}

