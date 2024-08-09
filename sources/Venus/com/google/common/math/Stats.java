/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.math;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.math.DoubleUtils;
import com.google.common.math.StatsAccumulator;
import com.google.common.primitives.Doubles;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Iterator;
import javax.annotation.Nullable;

@Beta
@GwtIncompatible
public final class Stats
implements Serializable {
    private final long count;
    private final double mean;
    private final double sumOfSquaresOfDeltas;
    private final double min;
    private final double max;
    static final int BYTES = 40;
    private static final long serialVersionUID = 0L;

    Stats(long l, double d, double d2, double d3, double d4) {
        this.count = l;
        this.mean = d;
        this.sumOfSquaresOfDeltas = d2;
        this.min = d3;
        this.max = d4;
    }

    public static Stats of(Iterable<? extends Number> iterable) {
        StatsAccumulator statsAccumulator = new StatsAccumulator();
        statsAccumulator.addAll(iterable);
        return statsAccumulator.snapshot();
    }

    public static Stats of(Iterator<? extends Number> iterator2) {
        StatsAccumulator statsAccumulator = new StatsAccumulator();
        statsAccumulator.addAll(iterator2);
        return statsAccumulator.snapshot();
    }

    public static Stats of(double ... dArray) {
        StatsAccumulator statsAccumulator = new StatsAccumulator();
        statsAccumulator.addAll(dArray);
        return statsAccumulator.snapshot();
    }

    public static Stats of(int ... nArray) {
        StatsAccumulator statsAccumulator = new StatsAccumulator();
        statsAccumulator.addAll(nArray);
        return statsAccumulator.snapshot();
    }

    public static Stats of(long ... lArray) {
        StatsAccumulator statsAccumulator = new StatsAccumulator();
        statsAccumulator.addAll(lArray);
        return statsAccumulator.snapshot();
    }

    public long count() {
        return this.count;
    }

    public double mean() {
        Preconditions.checkState(this.count != 0L);
        return this.mean;
    }

    public double sum() {
        return this.mean * (double)this.count;
    }

    public double populationVariance() {
        Preconditions.checkState(this.count > 0L);
        if (Double.isNaN(this.sumOfSquaresOfDeltas)) {
            return Double.NaN;
        }
        if (this.count == 1L) {
            return 0.0;
        }
        return DoubleUtils.ensureNonNegative(this.sumOfSquaresOfDeltas) / (double)this.count();
    }

    public double populationStandardDeviation() {
        return Math.sqrt(this.populationVariance());
    }

    public double sampleVariance() {
        Preconditions.checkState(this.count > 1L);
        if (Double.isNaN(this.sumOfSquaresOfDeltas)) {
            return Double.NaN;
        }
        return DoubleUtils.ensureNonNegative(this.sumOfSquaresOfDeltas) / (double)(this.count - 1L);
    }

    public double sampleStandardDeviation() {
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

    public boolean equals(@Nullable Object object) {
        if (object == null) {
            return true;
        }
        if (this.getClass() != object.getClass()) {
            return true;
        }
        Stats stats = (Stats)object;
        return this.count == stats.count && Double.doubleToLongBits(this.mean) == Double.doubleToLongBits(stats.mean) && Double.doubleToLongBits(this.sumOfSquaresOfDeltas) == Double.doubleToLongBits(stats.sumOfSquaresOfDeltas) && Double.doubleToLongBits(this.min) == Double.doubleToLongBits(stats.min) && Double.doubleToLongBits(this.max) == Double.doubleToLongBits(stats.max);
    }

    public int hashCode() {
        return Objects.hashCode(this.count, this.mean, this.sumOfSquaresOfDeltas, this.min, this.max);
    }

    public String toString() {
        if (this.count() > 0L) {
            return MoreObjects.toStringHelper(this).add("count", this.count).add("mean", this.mean).add("populationStandardDeviation", this.populationStandardDeviation()).add("min", this.min).add("max", this.max).toString();
        }
        return MoreObjects.toStringHelper(this).add("count", this.count).toString();
    }

    double sumOfSquaresOfDeltas() {
        return this.sumOfSquaresOfDeltas;
    }

    public static double meanOf(Iterable<? extends Number> iterable) {
        return Stats.meanOf(iterable.iterator());
    }

    public static double meanOf(Iterator<? extends Number> iterator2) {
        Preconditions.checkArgument(iterator2.hasNext());
        long l = 1L;
        double d = iterator2.next().doubleValue();
        while (iterator2.hasNext()) {
            double d2 = iterator2.next().doubleValue();
            ++l;
            if (Doubles.isFinite(d2) && Doubles.isFinite(d)) {
                d += (d2 - d) / (double)l;
                continue;
            }
            d = StatsAccumulator.calculateNewMeanNonFinite(d, d2);
        }
        return d;
    }

    public static double meanOf(double ... dArray) {
        Preconditions.checkArgument(dArray.length > 0);
        double d = dArray[0];
        for (int i = 1; i < dArray.length; ++i) {
            double d2 = dArray[i];
            if (Doubles.isFinite(d2) && Doubles.isFinite(d)) {
                d += (d2 - d) / (double)(i + 1);
                continue;
            }
            d = StatsAccumulator.calculateNewMeanNonFinite(d, d2);
        }
        return d;
    }

    public static double meanOf(int ... nArray) {
        Preconditions.checkArgument(nArray.length > 0);
        double d = nArray[0];
        for (int i = 1; i < nArray.length; ++i) {
            double d2 = nArray[i];
            if (Doubles.isFinite(d2) && Doubles.isFinite(d)) {
                d += (d2 - d) / (double)(i + 1);
                continue;
            }
            d = StatsAccumulator.calculateNewMeanNonFinite(d, d2);
        }
        return d;
    }

    public static double meanOf(long ... lArray) {
        Preconditions.checkArgument(lArray.length > 0);
        double d = lArray[0];
        for (int i = 1; i < lArray.length; ++i) {
            double d2 = lArray[i];
            if (Doubles.isFinite(d2) && Doubles.isFinite(d)) {
                d += (d2 - d) / (double)(i + 1);
                continue;
            }
            d = StatsAccumulator.calculateNewMeanNonFinite(d, d2);
        }
        return d;
    }

    public byte[] toByteArray() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(40).order(ByteOrder.LITTLE_ENDIAN);
        this.writeTo(byteBuffer);
        return byteBuffer.array();
    }

    void writeTo(ByteBuffer byteBuffer) {
        Preconditions.checkNotNull(byteBuffer);
        Preconditions.checkArgument(byteBuffer.remaining() >= 40, "Expected at least Stats.BYTES = %s remaining , got %s", 40, byteBuffer.remaining());
        byteBuffer.putLong(this.count).putDouble(this.mean).putDouble(this.sumOfSquaresOfDeltas).putDouble(this.min).putDouble(this.max);
    }

    public static Stats fromByteArray(byte[] byArray) {
        Preconditions.checkNotNull(byArray);
        Preconditions.checkArgument(byArray.length == 40, "Expected Stats.BYTES = %s remaining , got %s", 40, byArray.length);
        return Stats.readFrom(ByteBuffer.wrap(byArray).order(ByteOrder.LITTLE_ENDIAN));
    }

    static Stats readFrom(ByteBuffer byteBuffer) {
        Preconditions.checkNotNull(byteBuffer);
        Preconditions.checkArgument(byteBuffer.remaining() >= 40, "Expected at least Stats.BYTES = %s remaining , got %s", 40, byteBuffer.remaining());
        return new Stats(byteBuffer.getLong(), byteBuffer.getDouble(), byteBuffer.getDouble(), byteBuffer.getDouble(), byteBuffer.getDouble());
    }
}

