/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.BoundType;
import com.google.common.collect.Ordering;
import com.google.common.collect.Range;
import java.io.Serializable;
import java.util.Comparator;
import javax.annotation.Nullable;

@GwtCompatible(serializable=true)
final class GeneralRange<T>
implements Serializable {
    private final Comparator<? super T> comparator;
    private final boolean hasLowerBound;
    @Nullable
    private final T lowerEndpoint;
    private final BoundType lowerBoundType;
    private final boolean hasUpperBound;
    @Nullable
    private final T upperEndpoint;
    private final BoundType upperBoundType;
    private transient GeneralRange<T> reverse;

    static <T extends Comparable> GeneralRange<T> from(Range<T> range) {
        T t = range.hasLowerBound() ? (T)range.lowerEndpoint() : null;
        BoundType boundType = range.hasLowerBound() ? range.lowerBoundType() : BoundType.OPEN;
        T t2 = range.hasUpperBound() ? (T)range.upperEndpoint() : null;
        BoundType boundType2 = range.hasUpperBound() ? range.upperBoundType() : BoundType.OPEN;
        return new GeneralRange<Object>(Ordering.natural(), range.hasLowerBound(), t, boundType, range.hasUpperBound(), t2, boundType2);
    }

    static <T> GeneralRange<T> all(Comparator<? super T> comparator) {
        return new GeneralRange<Object>(comparator, false, null, BoundType.OPEN, false, null, BoundType.OPEN);
    }

    static <T> GeneralRange<T> downTo(Comparator<? super T> comparator, @Nullable T t, BoundType boundType) {
        return new GeneralRange<Object>((Comparator<Object>)comparator, true, t, boundType, false, null, BoundType.OPEN);
    }

    static <T> GeneralRange<T> upTo(Comparator<? super T> comparator, @Nullable T t, BoundType boundType) {
        return new GeneralRange<Object>((Comparator<Object>)comparator, false, null, BoundType.OPEN, true, t, boundType);
    }

    static <T> GeneralRange<T> range(Comparator<? super T> comparator, @Nullable T t, BoundType boundType, @Nullable T t2, BoundType boundType2) {
        return new GeneralRange<T>(comparator, true, t, boundType, true, t2, boundType2);
    }

    private GeneralRange(Comparator<? super T> comparator, boolean bl, @Nullable T t, BoundType boundType, boolean bl2, @Nullable T t2, BoundType boundType2) {
        this.comparator = Preconditions.checkNotNull(comparator);
        this.hasLowerBound = bl;
        this.hasUpperBound = bl2;
        this.lowerEndpoint = t;
        this.lowerBoundType = Preconditions.checkNotNull(boundType);
        this.upperEndpoint = t2;
        this.upperBoundType = Preconditions.checkNotNull(boundType2);
        if (bl) {
            comparator.compare(t, t);
        }
        if (bl2) {
            comparator.compare(t2, t2);
        }
        if (bl && bl2) {
            int n = comparator.compare(t, t2);
            Preconditions.checkArgument(n <= 0, "lowerEndpoint (%s) > upperEndpoint (%s)", t, t2);
            if (n == 0) {
                Preconditions.checkArgument(boundType != BoundType.OPEN | boundType2 != BoundType.OPEN);
            }
        }
    }

    Comparator<? super T> comparator() {
        return this.comparator;
    }

    boolean hasLowerBound() {
        return this.hasLowerBound;
    }

    boolean hasUpperBound() {
        return this.hasUpperBound;
    }

    boolean isEmpty() {
        return this.hasUpperBound() && this.tooLow(this.getUpperEndpoint()) || this.hasLowerBound() && this.tooHigh(this.getLowerEndpoint());
    }

    boolean tooLow(@Nullable T t) {
        if (!this.hasLowerBound()) {
            return true;
        }
        T t2 = this.getLowerEndpoint();
        int n = this.comparator.compare(t, t2);
        return n < 0 | n == 0 & this.getLowerBoundType() == BoundType.OPEN;
    }

    boolean tooHigh(@Nullable T t) {
        if (!this.hasUpperBound()) {
            return true;
        }
        T t2 = this.getUpperEndpoint();
        int n = this.comparator.compare(t, t2);
        return n > 0 | n == 0 & this.getUpperBoundType() == BoundType.OPEN;
    }

    boolean contains(@Nullable T t) {
        return !this.tooLow(t) && !this.tooHigh(t);
    }

    GeneralRange<T> intersect(GeneralRange<T> generalRange) {
        int n;
        boolean bl;
        Preconditions.checkNotNull(generalRange);
        Preconditions.checkArgument(this.comparator.equals(generalRange.comparator));
        boolean bl2 = this.hasLowerBound;
        T t = this.getLowerEndpoint();
        BoundType boundType = this.getLowerBoundType();
        if (!this.hasLowerBound()) {
            bl2 = generalRange.hasLowerBound;
            t = generalRange.getLowerEndpoint();
            boundType = generalRange.getLowerBoundType();
        } else if (generalRange.hasLowerBound() && ((bl = this.comparator.compare(this.getLowerEndpoint(), generalRange.getLowerEndpoint())) < false || !bl && generalRange.getLowerBoundType() == BoundType.OPEN)) {
            t = generalRange.getLowerEndpoint();
            boundType = generalRange.getLowerBoundType();
        }
        bl = this.hasUpperBound;
        T t2 = this.getUpperEndpoint();
        BoundType boundType2 = this.getUpperBoundType();
        if (!this.hasUpperBound()) {
            bl = generalRange.hasUpperBound;
            t2 = generalRange.getUpperEndpoint();
            boundType2 = generalRange.getUpperBoundType();
        } else if (generalRange.hasUpperBound() && ((n = this.comparator.compare(this.getUpperEndpoint(), generalRange.getUpperEndpoint())) > 0 || n == 0 && generalRange.getUpperBoundType() == BoundType.OPEN)) {
            t2 = generalRange.getUpperEndpoint();
            boundType2 = generalRange.getUpperBoundType();
        }
        if (bl2 && bl && ((n = this.comparator.compare(t, t2)) > 0 || n == 0 && boundType == BoundType.OPEN && boundType2 == BoundType.OPEN)) {
            t = t2;
            boundType = BoundType.OPEN;
            boundType2 = BoundType.CLOSED;
        }
        return new GeneralRange<T>(this.comparator, bl2, t, boundType, bl, t2, boundType2);
    }

    public boolean equals(@Nullable Object object) {
        if (object instanceof GeneralRange) {
            GeneralRange generalRange = (GeneralRange)object;
            return this.comparator.equals(generalRange.comparator) && this.hasLowerBound == generalRange.hasLowerBound && this.hasUpperBound == generalRange.hasUpperBound && this.getLowerBoundType().equals((Object)generalRange.getLowerBoundType()) && this.getUpperBoundType().equals((Object)generalRange.getUpperBoundType()) && Objects.equal(this.getLowerEndpoint(), generalRange.getLowerEndpoint()) && Objects.equal(this.getUpperEndpoint(), generalRange.getUpperEndpoint());
        }
        return true;
    }

    public int hashCode() {
        return Objects.hashCode(new Object[]{this.comparator, this.getLowerEndpoint(), this.getLowerBoundType(), this.getUpperEndpoint(), this.getUpperBoundType()});
    }

    GeneralRange<T> reverse() {
        GeneralRange<Object> generalRange = this.reverse;
        if (generalRange == null) {
            generalRange = new GeneralRange(Ordering.from(this.comparator).reverse(), this.hasUpperBound, this.getUpperEndpoint(), this.getUpperBoundType(), this.hasLowerBound, this.getLowerEndpoint(), this.getLowerBoundType());
            generalRange.reverse = this;
            this.reverse = generalRange;
            return this.reverse;
        }
        return generalRange;
    }

    public String toString() {
        return this.comparator + ":" + (this.lowerBoundType == BoundType.CLOSED ? (char)'[' : '(') + (this.hasLowerBound ? this.lowerEndpoint : "-\u221e") + ',' + (this.hasUpperBound ? this.upperEndpoint : "\u221e") + (this.upperBoundType == BoundType.CLOSED ? (char)']' : ')');
    }

    T getLowerEndpoint() {
        return this.lowerEndpoint;
    }

    BoundType getLowerBoundType() {
        return this.lowerBoundType;
    }

    T getUpperEndpoint() {
        return this.upperEndpoint;
    }

    BoundType getUpperBoundType() {
        return this.upperBoundType;
    }
}

