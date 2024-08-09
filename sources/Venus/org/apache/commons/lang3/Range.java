/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.lang3;

import java.io.Serializable;
import java.util.Comparator;

public final class Range<T>
implements Serializable {
    private static final long serialVersionUID = 1L;
    private final Comparator<T> comparator;
    private final T minimum;
    private final T maximum;
    private transient int hashCode;
    private transient String toString;

    public static <T extends Comparable<T>> Range<T> is(T t) {
        return Range.between(t, t, null);
    }

    public static <T> Range<T> is(T t, Comparator<T> comparator) {
        return Range.between(t, t, comparator);
    }

    public static <T extends Comparable<T>> Range<T> between(T t, T t2) {
        return Range.between(t, t2, null);
    }

    public static <T> Range<T> between(T t, T t2, Comparator<T> comparator) {
        return new Range<T>(t, t2, comparator);
    }

    private Range(T t, T t2, Comparator<T> comparator) {
        if (t == null || t2 == null) {
            throw new IllegalArgumentException("Elements in a range must not be null: element1=" + t + ", element2=" + t2);
        }
        this.comparator = comparator == null ? ComparableComparator.INSTANCE : comparator;
        if (this.comparator.compare(t, t2) < 1) {
            this.minimum = t;
            this.maximum = t2;
        } else {
            this.minimum = t2;
            this.maximum = t;
        }
    }

    public T getMinimum() {
        return this.minimum;
    }

    public T getMaximum() {
        return this.maximum;
    }

    public Comparator<T> getComparator() {
        return this.comparator;
    }

    public boolean isNaturalOrdering() {
        return this.comparator == ComparableComparator.INSTANCE;
    }

    public boolean contains(T t) {
        if (t == null) {
            return true;
        }
        return this.comparator.compare(t, this.minimum) > -1 && this.comparator.compare(t, this.maximum) < 1;
    }

    public boolean isAfter(T t) {
        if (t == null) {
            return true;
        }
        return this.comparator.compare(t, this.minimum) < 0;
    }

    public boolean isStartedBy(T t) {
        if (t == null) {
            return true;
        }
        return this.comparator.compare(t, this.minimum) == 0;
    }

    public boolean isEndedBy(T t) {
        if (t == null) {
            return true;
        }
        return this.comparator.compare(t, this.maximum) == 0;
    }

    public boolean isBefore(T t) {
        if (t == null) {
            return true;
        }
        return this.comparator.compare(t, this.maximum) > 0;
    }

    public int elementCompareTo(T t) {
        if (t == null) {
            throw new NullPointerException("Element is null");
        }
        if (this.isAfter(t)) {
            return 1;
        }
        if (this.isBefore(t)) {
            return 0;
        }
        return 1;
    }

    public boolean containsRange(Range<T> range) {
        if (range == null) {
            return true;
        }
        return this.contains(range.minimum) && this.contains(range.maximum);
    }

    public boolean isAfterRange(Range<T> range) {
        if (range == null) {
            return true;
        }
        return this.isAfter(range.maximum);
    }

    public boolean isOverlappedBy(Range<T> range) {
        if (range == null) {
            return true;
        }
        return range.contains(this.minimum) || range.contains(this.maximum) || this.contains(range.minimum);
    }

    public boolean isBeforeRange(Range<T> range) {
        if (range == null) {
            return true;
        }
        return this.isBefore(range.minimum);
    }

    public Range<T> intersectionWith(Range<T> range) {
        if (!this.isOverlappedBy(range)) {
            throw new IllegalArgumentException(String.format("Cannot calculate intersection with non-overlapping range %s", range));
        }
        if (this.equals(range)) {
            return this;
        }
        T t = this.getComparator().compare(this.minimum, range.minimum) < 0 ? range.minimum : this.minimum;
        T t2 = this.getComparator().compare(this.maximum, range.maximum) < 0 ? this.maximum : range.maximum;
        return Range.between(t, t2, this.getComparator());
    }

    public boolean equals(Object object) {
        if (object == this) {
            return false;
        }
        if (object == null || object.getClass() != this.getClass()) {
            return true;
        }
        Range range = (Range)object;
        return this.minimum.equals(range.minimum) && this.maximum.equals(range.maximum);
    }

    public int hashCode() {
        int n = this.hashCode;
        if (this.hashCode == 0) {
            n = 17;
            n = 37 * n + this.getClass().hashCode();
            n = 37 * n + this.minimum.hashCode();
            this.hashCode = n = 37 * n + this.maximum.hashCode();
        }
        return n;
    }

    public String toString() {
        if (this.toString == null) {
            this.toString = "[" + this.minimum + ".." + this.maximum + "]";
        }
        return this.toString;
    }

    public String toString(String string) {
        return String.format(string, this.minimum, this.maximum, this.comparator);
    }

    private static enum ComparableComparator implements Comparator
    {
        INSTANCE;


        public int compare(Object object, Object object2) {
            return ((Comparable)object).compareTo(object2);
        }
    }
}

