/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.BoundType;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.ContiguousSet;
import com.google.common.collect.Cut;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Iterables;
import com.google.common.collect.Ordering;
import java.io.Serializable;
import java.util.Iterator;
import java.util.SortedSet;
import javax.annotation.Nullable;

@GwtCompatible
public final class Range<C extends Comparable>
implements Predicate<C>,
Serializable {
    private static final Function<Range, Cut> LOWER_BOUND_FN = new Function<Range, Cut>(){

        @Override
        public Cut apply(Range range) {
            return range.lowerBound;
        }

        @Override
        public Object apply(Object object) {
            return this.apply((Range)object);
        }
    };
    private static final Function<Range, Cut> UPPER_BOUND_FN = new Function<Range, Cut>(){

        @Override
        public Cut apply(Range range) {
            return range.upperBound;
        }

        @Override
        public Object apply(Object object) {
            return this.apply((Range)object);
        }
    };
    static final Ordering<Range<?>> RANGE_LEX_ORDERING = new RangeLexOrdering(null);
    private static final Range<Comparable> ALL = new Range(Cut.belowAll(), Cut.aboveAll());
    final Cut<C> lowerBound;
    final Cut<C> upperBound;
    private static final long serialVersionUID = 0L;

    static <C extends Comparable<?>> Function<Range<C>, Cut<C>> lowerBoundFn() {
        return LOWER_BOUND_FN;
    }

    static <C extends Comparable<?>> Function<Range<C>, Cut<C>> upperBoundFn() {
        return UPPER_BOUND_FN;
    }

    static <C extends Comparable<?>> Range<C> create(Cut<C> cut, Cut<C> cut2) {
        return new Range<C>(cut, cut2);
    }

    public static <C extends Comparable<?>> Range<C> open(C c, C c2) {
        return Range.create(Cut.aboveValue(c), Cut.belowValue(c2));
    }

    public static <C extends Comparable<?>> Range<C> closed(C c, C c2) {
        return Range.create(Cut.belowValue(c), Cut.aboveValue(c2));
    }

    public static <C extends Comparable<?>> Range<C> closedOpen(C c, C c2) {
        return Range.create(Cut.belowValue(c), Cut.belowValue(c2));
    }

    public static <C extends Comparable<?>> Range<C> openClosed(C c, C c2) {
        return Range.create(Cut.aboveValue(c), Cut.aboveValue(c2));
    }

    public static <C extends Comparable<?>> Range<C> range(C c, BoundType boundType, C c2, BoundType boundType2) {
        Preconditions.checkNotNull(boundType);
        Preconditions.checkNotNull(boundType2);
        Cut<C> cut = boundType == BoundType.OPEN ? Cut.aboveValue(c) : Cut.belowValue(c);
        Cut<C> cut2 = boundType2 == BoundType.OPEN ? Cut.belowValue(c2) : Cut.aboveValue(c2);
        return Range.create(cut, cut2);
    }

    public static <C extends Comparable<?>> Range<C> lessThan(C c) {
        return Range.create(Cut.belowAll(), Cut.belowValue(c));
    }

    public static <C extends Comparable<?>> Range<C> atMost(C c) {
        return Range.create(Cut.belowAll(), Cut.aboveValue(c));
    }

    public static <C extends Comparable<?>> Range<C> upTo(C c, BoundType boundType) {
        switch (3.$SwitchMap$com$google$common$collect$BoundType[boundType.ordinal()]) {
            case 1: {
                return Range.lessThan(c);
            }
            case 2: {
                return Range.atMost(c);
            }
        }
        throw new AssertionError();
    }

    public static <C extends Comparable<?>> Range<C> greaterThan(C c) {
        return Range.create(Cut.aboveValue(c), Cut.aboveAll());
    }

    public static <C extends Comparable<?>> Range<C> atLeast(C c) {
        return Range.create(Cut.belowValue(c), Cut.aboveAll());
    }

    public static <C extends Comparable<?>> Range<C> downTo(C c, BoundType boundType) {
        switch (3.$SwitchMap$com$google$common$collect$BoundType[boundType.ordinal()]) {
            case 1: {
                return Range.greaterThan(c);
            }
            case 2: {
                return Range.atLeast(c);
            }
        }
        throw new AssertionError();
    }

    public static <C extends Comparable<?>> Range<C> all() {
        return ALL;
    }

    public static <C extends Comparable<?>> Range<C> singleton(C c) {
        return Range.closed(c, c);
    }

    public static <C extends Comparable<?>> Range<C> encloseAll(Iterable<C> iterable) {
        Comparable comparable;
        Preconditions.checkNotNull(iterable);
        if (iterable instanceof ContiguousSet) {
            return ((ContiguousSet)iterable).range();
        }
        Iterator<C> iterator2 = iterable.iterator();
        Comparable comparable2 = comparable = (Comparable)Preconditions.checkNotNull(iterator2.next());
        while (iterator2.hasNext()) {
            Comparable comparable3 = (Comparable)Preconditions.checkNotNull(iterator2.next());
            comparable = Ordering.natural().min(comparable, comparable3);
            comparable2 = Ordering.natural().max(comparable2, comparable3);
        }
        return Range.closed(comparable, comparable2);
    }

    private Range(Cut<C> cut, Cut<C> cut2) {
        this.lowerBound = Preconditions.checkNotNull(cut);
        this.upperBound = Preconditions.checkNotNull(cut2);
        if (cut.compareTo(cut2) > 0 || cut == Cut.aboveAll() || cut2 == Cut.belowAll()) {
            throw new IllegalArgumentException("Invalid range: " + Range.toString(cut, cut2));
        }
    }

    public boolean hasLowerBound() {
        return this.lowerBound != Cut.belowAll();
    }

    public C lowerEndpoint() {
        return this.lowerBound.endpoint();
    }

    public BoundType lowerBoundType() {
        return this.lowerBound.typeAsLowerBound();
    }

    public boolean hasUpperBound() {
        return this.upperBound != Cut.aboveAll();
    }

    public C upperEndpoint() {
        return this.upperBound.endpoint();
    }

    public BoundType upperBoundType() {
        return this.upperBound.typeAsUpperBound();
    }

    public boolean isEmpty() {
        return this.lowerBound.equals(this.upperBound);
    }

    public boolean contains(C c) {
        Preconditions.checkNotNull(c);
        return this.lowerBound.isLessThan(c) && !this.upperBound.isLessThan(c);
    }

    @Override
    @Deprecated
    public boolean apply(C c) {
        return this.contains(c);
    }

    public boolean containsAll(Iterable<? extends C> iterable) {
        if (Iterables.isEmpty(iterable)) {
            return false;
        }
        if (iterable instanceof SortedSet) {
            SortedSet<? extends C> sortedSet = Range.cast(iterable);
            Object object = sortedSet.comparator();
            if (Ordering.natural().equals(object) || object == null) {
                return this.contains((Comparable)sortedSet.first()) && this.contains((Comparable)sortedSet.last());
            }
        }
        for (Object object : iterable) {
            if (this.contains(object)) continue;
            return true;
        }
        return false;
    }

    public boolean encloses(Range<C> range) {
        return this.lowerBound.compareTo(range.lowerBound) <= 0 && this.upperBound.compareTo(range.upperBound) >= 0;
    }

    public boolean isConnected(Range<C> range) {
        return this.lowerBound.compareTo(range.upperBound) <= 0 && range.lowerBound.compareTo(this.upperBound) <= 0;
    }

    public Range<C> intersection(Range<C> range) {
        int n = this.lowerBound.compareTo(range.lowerBound);
        int n2 = this.upperBound.compareTo(range.upperBound);
        if (n >= 0 && n2 <= 0) {
            return this;
        }
        if (n <= 0 && n2 >= 0) {
            return range;
        }
        Cut<C> cut = n >= 0 ? this.lowerBound : range.lowerBound;
        Cut<C> cut2 = n2 <= 0 ? this.upperBound : range.upperBound;
        return Range.create(cut, cut2);
    }

    public Range<C> span(Range<C> range) {
        int n = this.lowerBound.compareTo(range.lowerBound);
        int n2 = this.upperBound.compareTo(range.upperBound);
        if (n <= 0 && n2 >= 0) {
            return this;
        }
        if (n >= 0 && n2 <= 0) {
            return range;
        }
        Cut<C> cut = n <= 0 ? this.lowerBound : range.lowerBound;
        Cut<C> cut2 = n2 >= 0 ? this.upperBound : range.upperBound;
        return Range.create(cut, cut2);
    }

    public Range<C> canonical(DiscreteDomain<C> discreteDomain) {
        Preconditions.checkNotNull(discreteDomain);
        Cut<C> cut = this.lowerBound.canonical(discreteDomain);
        Cut<C> cut2 = this.upperBound.canonical(discreteDomain);
        return cut == this.lowerBound && cut2 == this.upperBound ? this : Range.create(cut, cut2);
    }

    @Override
    public boolean equals(@Nullable Object object) {
        if (object instanceof Range) {
            Range range = (Range)object;
            return this.lowerBound.equals(range.lowerBound) && this.upperBound.equals(range.upperBound);
        }
        return true;
    }

    public int hashCode() {
        return this.lowerBound.hashCode() * 31 + this.upperBound.hashCode();
    }

    public String toString() {
        return Range.toString(this.lowerBound, this.upperBound);
    }

    private static String toString(Cut<?> cut, Cut<?> cut2) {
        StringBuilder stringBuilder = new StringBuilder(16);
        cut.describeAsLowerBound(stringBuilder);
        stringBuilder.append("..");
        cut2.describeAsUpperBound(stringBuilder);
        return stringBuilder.toString();
    }

    private static <T> SortedSet<T> cast(Iterable<T> iterable) {
        return (SortedSet)iterable;
    }

    Object readResolve() {
        if (this.equals(ALL)) {
            return Range.all();
        }
        return this;
    }

    static int compareOrThrow(Comparable comparable, Comparable comparable2) {
        return comparable.compareTo(comparable2);
    }

    @Override
    @Deprecated
    public boolean apply(Object object) {
        return this.apply((C)((Comparable)object));
    }

    private static class RangeLexOrdering
    extends Ordering<Range<?>>
    implements Serializable {
        private static final long serialVersionUID = 0L;

        private RangeLexOrdering() {
        }

        @Override
        public int compare(Range<?> range, Range<?> range2) {
            return ComparisonChain.start().compare(range.lowerBound, range2.lowerBound).compare(range.upperBound, range2.upperBound).result();
        }

        @Override
        public int compare(Object object, Object object2) {
            return this.compare((Range)object, (Range)object2);
        }

        RangeLexOrdering(1 var1_1) {
            this();
        }
    }
}

