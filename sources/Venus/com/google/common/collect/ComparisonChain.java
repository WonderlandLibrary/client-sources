/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.primitives.Booleans;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;
import java.util.Comparator;
import javax.annotation.Nullable;

@GwtCompatible
public abstract class ComparisonChain {
    private static final ComparisonChain ACTIVE = new ComparisonChain(){

        public ComparisonChain compare(Comparable comparable, Comparable comparable2) {
            return this.classify(comparable.compareTo(comparable2));
        }

        @Override
        public <T> ComparisonChain compare(@Nullable T t, @Nullable T t2, Comparator<T> comparator) {
            return this.classify(comparator.compare(t, t2));
        }

        @Override
        public ComparisonChain compare(int n, int n2) {
            return this.classify(Ints.compare(n, n2));
        }

        @Override
        public ComparisonChain compare(long l, long l2) {
            return this.classify(Longs.compare(l, l2));
        }

        @Override
        public ComparisonChain compare(float f, float f2) {
            return this.classify(Float.compare(f, f2));
        }

        @Override
        public ComparisonChain compare(double d, double d2) {
            return this.classify(Double.compare(d, d2));
        }

        @Override
        public ComparisonChain compareTrueFirst(boolean bl, boolean bl2) {
            return this.classify(Booleans.compare(bl2, bl));
        }

        @Override
        public ComparisonChain compareFalseFirst(boolean bl, boolean bl2) {
            return this.classify(Booleans.compare(bl, bl2));
        }

        ComparisonChain classify(int n) {
            return n < 0 ? ComparisonChain.access$100() : (n > 0 ? ComparisonChain.access$200() : ComparisonChain.access$300());
        }

        @Override
        public int result() {
            return 1;
        }
    };
    private static final ComparisonChain LESS = new InactiveComparisonChain(-1);
    private static final ComparisonChain GREATER = new InactiveComparisonChain(1);

    private ComparisonChain() {
    }

    public static ComparisonChain start() {
        return ACTIVE;
    }

    public abstract ComparisonChain compare(Comparable<?> var1, Comparable<?> var2);

    public abstract <T> ComparisonChain compare(@Nullable T var1, @Nullable T var2, Comparator<T> var3);

    public abstract ComparisonChain compare(int var1, int var2);

    public abstract ComparisonChain compare(long var1, long var3);

    public abstract ComparisonChain compare(float var1, float var2);

    public abstract ComparisonChain compare(double var1, double var3);

    @Deprecated
    public final ComparisonChain compare(Boolean bl, Boolean bl2) {
        return this.compareFalseFirst(bl, bl2);
    }

    public abstract ComparisonChain compareTrueFirst(boolean var1, boolean var2);

    public abstract ComparisonChain compareFalseFirst(boolean var1, boolean var2);

    public abstract int result();

    ComparisonChain(1 var1_1) {
        this();
    }

    static ComparisonChain access$100() {
        return LESS;
    }

    static ComparisonChain access$200() {
        return GREATER;
    }

    static ComparisonChain access$300() {
        return ACTIVE;
    }

    private static final class InactiveComparisonChain
    extends ComparisonChain {
        final int result;

        InactiveComparisonChain(int n) {
            super(null);
            this.result = n;
        }

        public ComparisonChain compare(@Nullable Comparable comparable, @Nullable Comparable comparable2) {
            return this;
        }

        @Override
        public <T> ComparisonChain compare(@Nullable T t, @Nullable T t2, @Nullable Comparator<T> comparator) {
            return this;
        }

        @Override
        public ComparisonChain compare(int n, int n2) {
            return this;
        }

        @Override
        public ComparisonChain compare(long l, long l2) {
            return this;
        }

        @Override
        public ComparisonChain compare(float f, float f2) {
            return this;
        }

        @Override
        public ComparisonChain compare(double d, double d2) {
            return this;
        }

        @Override
        public ComparisonChain compareTrueFirst(boolean bl, boolean bl2) {
            return this;
        }

        @Override
        public ComparisonChain compareFalseFirst(boolean bl, boolean bl2) {
            return this;
        }

        @Override
        public int result() {
            return this.result;
        }
    }
}

