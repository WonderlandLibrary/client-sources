/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.math;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.math.DoubleUtils;
import com.google.errorprone.annotations.concurrent.LazyInit;

@Beta
@GwtIncompatible
public abstract class LinearTransformation {
    public static LinearTransformationBuilder mapping(double d, double d2) {
        Preconditions.checkArgument(DoubleUtils.isFinite(d) && DoubleUtils.isFinite(d2));
        return new LinearTransformationBuilder(d, d2, null);
    }

    public static LinearTransformation vertical(double d) {
        Preconditions.checkArgument(DoubleUtils.isFinite(d));
        return new VerticalLinearTransformation(d);
    }

    public static LinearTransformation horizontal(double d) {
        Preconditions.checkArgument(DoubleUtils.isFinite(d));
        double d2 = 0.0;
        return new RegularLinearTransformation(d2, d);
    }

    public static LinearTransformation forNaN() {
        return NaNLinearTransformation.INSTANCE;
    }

    public abstract boolean isVertical();

    public abstract boolean isHorizontal();

    public abstract double slope();

    public abstract double transform(double var1);

    public abstract LinearTransformation inverse();

    private static final class NaNLinearTransformation
    extends LinearTransformation {
        static final NaNLinearTransformation INSTANCE = new NaNLinearTransformation();

        private NaNLinearTransformation() {
        }

        @Override
        public boolean isVertical() {
            return true;
        }

        @Override
        public boolean isHorizontal() {
            return true;
        }

        @Override
        public double slope() {
            return Double.NaN;
        }

        @Override
        public double transform(double d) {
            return Double.NaN;
        }

        @Override
        public LinearTransformation inverse() {
            return this;
        }

        public String toString() {
            return "NaN";
        }
    }

    private static final class VerticalLinearTransformation
    extends LinearTransformation {
        final double x;
        @LazyInit
        LinearTransformation inverse;

        VerticalLinearTransformation(double d) {
            this.x = d;
            this.inverse = null;
        }

        VerticalLinearTransformation(double d, LinearTransformation linearTransformation) {
            this.x = d;
            this.inverse = linearTransformation;
        }

        @Override
        public boolean isVertical() {
            return false;
        }

        @Override
        public boolean isHorizontal() {
            return true;
        }

        @Override
        public double slope() {
            throw new IllegalStateException();
        }

        @Override
        public double transform(double d) {
            throw new IllegalStateException();
        }

        @Override
        public LinearTransformation inverse() {
            LinearTransformation linearTransformation = this.inverse;
            return linearTransformation == null ? (this.inverse = this.createInverse()) : linearTransformation;
        }

        public String toString() {
            return String.format("x = %g", this.x);
        }

        private LinearTransformation createInverse() {
            return new RegularLinearTransformation(0.0, this.x, this);
        }
    }

    private static final class RegularLinearTransformation
    extends LinearTransformation {
        final double slope;
        final double yIntercept;
        @LazyInit
        LinearTransformation inverse;

        RegularLinearTransformation(double d, double d2) {
            this.slope = d;
            this.yIntercept = d2;
            this.inverse = null;
        }

        RegularLinearTransformation(double d, double d2, LinearTransformation linearTransformation) {
            this.slope = d;
            this.yIntercept = d2;
            this.inverse = linearTransformation;
        }

        @Override
        public boolean isVertical() {
            return true;
        }

        @Override
        public boolean isHorizontal() {
            return this.slope == 0.0;
        }

        @Override
        public double slope() {
            return this.slope;
        }

        @Override
        public double transform(double d) {
            return d * this.slope + this.yIntercept;
        }

        @Override
        public LinearTransformation inverse() {
            LinearTransformation linearTransformation = this.inverse;
            return linearTransformation == null ? (this.inverse = this.createInverse()) : linearTransformation;
        }

        public String toString() {
            return String.format("y = %g * x + %g", this.slope, this.yIntercept);
        }

        private LinearTransformation createInverse() {
            if (this.slope != 0.0) {
                return new RegularLinearTransformation(1.0 / this.slope, -1.0 * this.yIntercept / this.slope, this);
            }
            return new VerticalLinearTransformation(this.yIntercept, this);
        }
    }

    public static final class LinearTransformationBuilder {
        private final double x1;
        private final double y1;

        private LinearTransformationBuilder(double d, double d2) {
            this.x1 = d;
            this.y1 = d2;
        }

        public LinearTransformation and(double d, double d2) {
            Preconditions.checkArgument(DoubleUtils.isFinite(d) && DoubleUtils.isFinite(d2));
            if (d == this.x1) {
                Preconditions.checkArgument(d2 != this.y1);
                return new VerticalLinearTransformation(this.x1);
            }
            return this.withSlope((d2 - this.y1) / (d - this.x1));
        }

        public LinearTransformation withSlope(double d) {
            Preconditions.checkArgument(!Double.isNaN(d));
            if (DoubleUtils.isFinite(d)) {
                double d2 = this.y1 - this.x1 * d;
                return new RegularLinearTransformation(d, d2);
            }
            return new VerticalLinearTransformation(this.x1);
        }

        LinearTransformationBuilder(double d, double d2, 1 var5_3) {
            this(d, d2);
        }
    }
}

