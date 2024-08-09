/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.AbstractRangeSet;
import com.google.common.collect.BoundType;
import com.google.common.collect.ContiguousSet;
import com.google.common.collect.Cut;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.google.common.collect.PeekingIterator;
import com.google.common.collect.Range;
import com.google.common.collect.RangeSet;
import com.google.common.collect.RegularImmutableSortedSet;
import com.google.common.collect.SortedLists;
import com.google.common.collect.TreeRangeSet;
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.primitives.Ints;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import javax.annotation.Nullable;

@Beta
@GwtIncompatible
public final class ImmutableRangeSet<C extends Comparable>
extends AbstractRangeSet<C>
implements Serializable {
    private static final ImmutableRangeSet<Comparable<?>> EMPTY = new ImmutableRangeSet(ImmutableList.of());
    private static final ImmutableRangeSet<Comparable<?>> ALL = new ImmutableRangeSet(ImmutableList.of(Range.all()));
    private final transient ImmutableList<Range<C>> ranges;
    @LazyInit
    private transient ImmutableRangeSet<C> complement;

    public static <C extends Comparable> ImmutableRangeSet<C> of() {
        return EMPTY;
    }

    static <C extends Comparable> ImmutableRangeSet<C> all() {
        return ALL;
    }

    public static <C extends Comparable> ImmutableRangeSet<C> of(Range<C> range) {
        Preconditions.checkNotNull(range);
        if (range.isEmpty()) {
            return ImmutableRangeSet.of();
        }
        if (range.equals(Range.all())) {
            return ImmutableRangeSet.all();
        }
        return new ImmutableRangeSet<C>(ImmutableList.of(range));
    }

    public static <C extends Comparable> ImmutableRangeSet<C> copyOf(RangeSet<C> rangeSet) {
        ImmutableRangeSet immutableRangeSet;
        Preconditions.checkNotNull(rangeSet);
        if (rangeSet.isEmpty()) {
            return ImmutableRangeSet.of();
        }
        if (rangeSet.encloses(Range.all())) {
            return ImmutableRangeSet.all();
        }
        if (rangeSet instanceof ImmutableRangeSet && !(immutableRangeSet = (ImmutableRangeSet)rangeSet).isPartialView()) {
            return immutableRangeSet;
        }
        return new ImmutableRangeSet<C>(ImmutableList.copyOf(rangeSet.asRanges()));
    }

    public static <C extends Comparable<?>> ImmutableRangeSet<C> unionOf(Iterable<Range<C>> iterable) {
        return ImmutableRangeSet.copyOf(TreeRangeSet.create(iterable));
    }

    public static <C extends Comparable<?>> ImmutableRangeSet<C> copyOf(Iterable<Range<C>> iterable) {
        return new Builder<C>().addAll(iterable).build();
    }

    ImmutableRangeSet(ImmutableList<Range<C>> immutableList) {
        this.ranges = immutableList;
    }

    private ImmutableRangeSet(ImmutableList<Range<C>> immutableList, ImmutableRangeSet<C> immutableRangeSet) {
        this.ranges = immutableList;
        this.complement = immutableRangeSet;
    }

    @Override
    public boolean intersects(Range<C> range) {
        int n = SortedLists.binarySearch(this.ranges, Range.lowerBoundFn(), range.lowerBound, Ordering.natural(), SortedLists.KeyPresentBehavior.ANY_PRESENT, SortedLists.KeyAbsentBehavior.NEXT_HIGHER);
        if (n < this.ranges.size() && ((Range)this.ranges.get(n)).isConnected(range) && !((Range)this.ranges.get(n)).intersection(range).isEmpty()) {
            return false;
        }
        return n > 0 && ((Range)this.ranges.get(n - 1)).isConnected(range) && !((Range)this.ranges.get(n - 1)).intersection(range).isEmpty();
    }

    @Override
    public boolean encloses(Range<C> range) {
        int n = SortedLists.binarySearch(this.ranges, Range.lowerBoundFn(), range.lowerBound, Ordering.natural(), SortedLists.KeyPresentBehavior.ANY_PRESENT, SortedLists.KeyAbsentBehavior.NEXT_LOWER);
        return n != -1 && ((Range)this.ranges.get(n)).encloses(range);
    }

    @Override
    public Range<C> rangeContaining(C c) {
        int n = SortedLists.binarySearch(this.ranges, Range.lowerBoundFn(), Cut.belowValue(c), Ordering.natural(), SortedLists.KeyPresentBehavior.ANY_PRESENT, SortedLists.KeyAbsentBehavior.NEXT_LOWER);
        if (n != -1) {
            Range range = (Range)this.ranges.get(n);
            return range.contains(c) ? range : null;
        }
        return null;
    }

    @Override
    public Range<C> span() {
        if (this.ranges.isEmpty()) {
            throw new NoSuchElementException();
        }
        return Range.create(((Range)this.ranges.get((int)0)).lowerBound, ((Range)this.ranges.get((int)(this.ranges.size() - 1))).upperBound);
    }

    @Override
    public boolean isEmpty() {
        return this.ranges.isEmpty();
    }

    @Override
    @Deprecated
    public void add(Range<C> range) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    public void addAll(RangeSet<C> rangeSet) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    public void addAll(Iterable<Range<C>> iterable) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    public void remove(Range<C> range) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    public void removeAll(RangeSet<C> rangeSet) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    public void removeAll(Iterable<Range<C>> iterable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ImmutableSet<Range<C>> asRanges() {
        if (this.ranges.isEmpty()) {
            return ImmutableSet.of();
        }
        return new RegularImmutableSortedSet<Range<C>>(this.ranges, Range.RANGE_LEX_ORDERING);
    }

    @Override
    public ImmutableSet<Range<C>> asDescendingSetOfRanges() {
        if (this.ranges.isEmpty()) {
            return ImmutableSet.of();
        }
        return new RegularImmutableSortedSet<Range<C>>(this.ranges.reverse(), Range.RANGE_LEX_ORDERING.reverse());
    }

    @Override
    public ImmutableRangeSet<C> complement() {
        ImmutableRangeSet<C> immutableRangeSet = this.complement;
        if (immutableRangeSet != null) {
            return immutableRangeSet;
        }
        if (this.ranges.isEmpty()) {
            this.complement = ImmutableRangeSet.all();
            return this.complement;
        }
        if (this.ranges.size() == 1 && ((Range)this.ranges.get(0)).equals(Range.all())) {
            this.complement = ImmutableRangeSet.of();
            return this.complement;
        }
        ComplementRanges complementRanges = new ComplementRanges(this);
        immutableRangeSet = this.complement = new ImmutableRangeSet<C>(complementRanges, this);
        return immutableRangeSet;
    }

    public ImmutableRangeSet<C> union(RangeSet<C> rangeSet) {
        return ImmutableRangeSet.unionOf(Iterables.concat(this.asRanges(), rangeSet.asRanges()));
    }

    public ImmutableRangeSet<C> intersection(RangeSet<C> rangeSet) {
        TreeRangeSet<C> treeRangeSet = TreeRangeSet.create(this);
        treeRangeSet.removeAll(rangeSet.complement());
        return ImmutableRangeSet.copyOf(treeRangeSet);
    }

    public ImmutableRangeSet<C> difference(RangeSet<C> rangeSet) {
        TreeRangeSet<C> treeRangeSet = TreeRangeSet.create(this);
        treeRangeSet.removeAll(rangeSet);
        return ImmutableRangeSet.copyOf(treeRangeSet);
    }

    private ImmutableList<Range<C>> intersectRanges(Range<C> range) {
        if (this.ranges.isEmpty() || range.isEmpty()) {
            return ImmutableList.of();
        }
        if (range.encloses(this.span())) {
            return this.ranges;
        }
        int n = range.hasLowerBound() ? SortedLists.binarySearch(this.ranges, Range.upperBoundFn(), range.lowerBound, SortedLists.KeyPresentBehavior.FIRST_AFTER, SortedLists.KeyAbsentBehavior.NEXT_HIGHER) : 0;
        int n2 = range.hasUpperBound() ? SortedLists.binarySearch(this.ranges, Range.lowerBoundFn(), range.upperBound, SortedLists.KeyPresentBehavior.FIRST_PRESENT, SortedLists.KeyAbsentBehavior.NEXT_HIGHER) : this.ranges.size();
        int n3 = n2 - n;
        if (n3 == 0) {
            return ImmutableList.of();
        }
        return new ImmutableList<Range<C>>(this, n3, n, range){
            final int val$length;
            final int val$fromIndex;
            final Range val$range;
            final ImmutableRangeSet this$0;
            {
                this.this$0 = immutableRangeSet;
                this.val$length = n;
                this.val$fromIndex = n2;
                this.val$range = range;
            }

            @Override
            public int size() {
                return this.val$length;
            }

            @Override
            public Range<C> get(int n) {
                Preconditions.checkElementIndex(n, this.val$length);
                if (n == 0 || n == this.val$length - 1) {
                    return ((Range)ImmutableRangeSet.access$000(this.this$0).get(n + this.val$fromIndex)).intersection(this.val$range);
                }
                return (Range)ImmutableRangeSet.access$000(this.this$0).get(n + this.val$fromIndex);
            }

            @Override
            boolean isPartialView() {
                return false;
            }

            @Override
            public Object get(int n) {
                return this.get(n);
            }
        };
    }

    @Override
    public ImmutableRangeSet<C> subRangeSet(Range<C> range) {
        if (!this.isEmpty()) {
            Range<C> range2 = this.span();
            if (range.encloses(range2)) {
                return this;
            }
            if (range.isConnected(range2)) {
                return new ImmutableRangeSet<C>(this.intersectRanges(range));
            }
        }
        return ImmutableRangeSet.of();
    }

    public ImmutableSortedSet<C> asSet(DiscreteDomain<C> discreteDomain) {
        Preconditions.checkNotNull(discreteDomain);
        if (this.isEmpty()) {
            return ImmutableSortedSet.of();
        }
        Range<C> range = this.span().canonical(discreteDomain);
        if (!range.hasLowerBound()) {
            throw new IllegalArgumentException("Neither the DiscreteDomain nor this range set are bounded below");
        }
        if (!range.hasUpperBound()) {
            try {
                discreteDomain.maxValue();
            } catch (NoSuchElementException noSuchElementException) {
                throw new IllegalArgumentException("Neither the DiscreteDomain nor this range set are bounded above");
            }
        }
        return new AsSet(this, discreteDomain);
    }

    boolean isPartialView() {
        return this.ranges.isPartialView();
    }

    public static <C extends Comparable<?>> Builder<C> builder() {
        return new Builder();
    }

    Object writeReplace() {
        return new SerializedForm<C>(this.ranges);
    }

    @Override
    public boolean equals(@Nullable Object object) {
        return super.equals(object);
    }

    @Override
    public boolean enclosesAll(RangeSet rangeSet) {
        return super.enclosesAll(rangeSet);
    }

    @Override
    public void clear() {
        super.clear();
    }

    @Override
    public boolean contains(Comparable comparable) {
        return super.contains(comparable);
    }

    @Override
    public RangeSet subRangeSet(Range range) {
        return this.subRangeSet(range);
    }

    @Override
    public RangeSet complement() {
        return this.complement();
    }

    @Override
    public Set asDescendingSetOfRanges() {
        return this.asDescendingSetOfRanges();
    }

    @Override
    public Set asRanges() {
        return this.asRanges();
    }

    static ImmutableList access$000(ImmutableRangeSet immutableRangeSet) {
        return immutableRangeSet.ranges;
    }

    private static final class SerializedForm<C extends Comparable>
    implements Serializable {
        private final ImmutableList<Range<C>> ranges;

        SerializedForm(ImmutableList<Range<C>> immutableList) {
            this.ranges = immutableList;
        }

        Object readResolve() {
            if (this.ranges.isEmpty()) {
                return ImmutableRangeSet.of();
            }
            if (this.ranges.equals(ImmutableList.of(Range.all()))) {
                return ImmutableRangeSet.all();
            }
            return new ImmutableRangeSet<C>(this.ranges);
        }
    }

    public static class Builder<C extends Comparable<?>> {
        private final List<Range<C>> ranges = Lists.newArrayList();

        @CanIgnoreReturnValue
        public Builder<C> add(Range<C> range) {
            Preconditions.checkArgument(!range.isEmpty(), "range must not be empty, but was %s", range);
            this.ranges.add(range);
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<C> addAll(RangeSet<C> rangeSet) {
            return this.addAll(rangeSet.asRanges());
        }

        @CanIgnoreReturnValue
        public Builder<C> addAll(Iterable<Range<C>> iterable) {
            for (Range<C> range : iterable) {
                this.add(range);
            }
            return this;
        }

        public ImmutableRangeSet<C> build() {
            Serializable serializable;
            ImmutableList.Builder builder = new ImmutableList.Builder(this.ranges.size());
            Collections.sort(this.ranges, Range.RANGE_LEX_ORDERING);
            PeekingIterator<Range<C>> peekingIterator = Iterators.peekingIterator(this.ranges.iterator());
            while (peekingIterator.hasNext()) {
                Range<C> range;
                serializable = peekingIterator.next();
                while (peekingIterator.hasNext() && ((Range)serializable).isConnected(range = peekingIterator.peek())) {
                    Preconditions.checkArgument(((Range)serializable).intersection(range).isEmpty(), "Overlapping ranges not permitted but found %s overlapping %s", (Object)serializable, range);
                    serializable = ((Range)serializable).span(peekingIterator.next());
                }
                builder.add(serializable);
            }
            serializable = builder.build();
            if (((AbstractCollection)((Object)serializable)).isEmpty()) {
                return ImmutableRangeSet.of();
            }
            if (((AbstractCollection)((Object)serializable)).size() == 1 && ((Range)Iterables.getOnlyElement(serializable)).equals(Range.all())) {
                return ImmutableRangeSet.all();
            }
            return new ImmutableRangeSet(serializable);
        }
    }

    private static class AsSetSerializedForm<C extends Comparable>
    implements Serializable {
        private final ImmutableList<Range<C>> ranges;
        private final DiscreteDomain<C> domain;

        AsSetSerializedForm(ImmutableList<Range<C>> immutableList, DiscreteDomain<C> discreteDomain) {
            this.ranges = immutableList;
            this.domain = discreteDomain;
        }

        Object readResolve() {
            return new ImmutableRangeSet<C>(this.ranges).asSet(this.domain);
        }
    }

    private final class AsSet
    extends ImmutableSortedSet<C> {
        private final DiscreteDomain<C> domain;
        private transient Integer size;
        final ImmutableRangeSet this$0;

        AsSet(ImmutableRangeSet immutableRangeSet, DiscreteDomain<C> discreteDomain) {
            this.this$0 = immutableRangeSet;
            super(Ordering.natural());
            this.domain = discreteDomain;
        }

        @Override
        public int size() {
            Integer n = this.size;
            if (n == null) {
                Range range;
                long l = 0L;
                Iterator iterator2 = ImmutableRangeSet.access$000(this.this$0).iterator();
                while (iterator2.hasNext() && (l += (long)ContiguousSet.create(range = (Range)iterator2.next(), this.domain).size()) < Integer.MAX_VALUE) {
                }
                n = this.size = Integer.valueOf(Ints.saturatedCast(l));
            }
            return n;
        }

        @Override
        public UnmodifiableIterator<C> iterator() {
            return new AbstractIterator<C>(this){
                final Iterator<Range<C>> rangeItr;
                Iterator<C> elemItr;
                final AsSet this$1;
                {
                    this.this$1 = asSet;
                    this.rangeItr = ImmutableRangeSet.access$000(this.this$1.this$0).iterator();
                    this.elemItr = Iterators.emptyIterator();
                }

                @Override
                protected C computeNext() {
                    while (!this.elemItr.hasNext()) {
                        if (this.rangeItr.hasNext()) {
                            this.elemItr = ContiguousSet.create(this.rangeItr.next(), AsSet.access$100(this.this$1)).iterator();
                            continue;
                        }
                        return (Comparable)this.endOfData();
                    }
                    return (Comparable)this.elemItr.next();
                }

                @Override
                protected Object computeNext() {
                    return this.computeNext();
                }
            };
        }

        @Override
        @GwtIncompatible(value="NavigableSet")
        public UnmodifiableIterator<C> descendingIterator() {
            return new AbstractIterator<C>(this){
                final Iterator<Range<C>> rangeItr;
                Iterator<C> elemItr;
                final AsSet this$1;
                {
                    this.this$1 = asSet;
                    this.rangeItr = ImmutableRangeSet.access$000(this.this$1.this$0).reverse().iterator();
                    this.elemItr = Iterators.emptyIterator();
                }

                @Override
                protected C computeNext() {
                    while (!this.elemItr.hasNext()) {
                        if (this.rangeItr.hasNext()) {
                            this.elemItr = ContiguousSet.create(this.rangeItr.next(), AsSet.access$100(this.this$1)).descendingIterator();
                            continue;
                        }
                        return (Comparable)this.endOfData();
                    }
                    return (Comparable)this.elemItr.next();
                }

                @Override
                protected Object computeNext() {
                    return this.computeNext();
                }
            };
        }

        ImmutableSortedSet<C> subSet(Range<C> range) {
            return ((ImmutableRangeSet)this.this$0.subRangeSet(range)).asSet(this.domain);
        }

        @Override
        ImmutableSortedSet<C> headSetImpl(C c, boolean bl) {
            return this.subSet(Range.upTo(c, BoundType.forBoolean(bl)));
        }

        @Override
        ImmutableSortedSet<C> subSetImpl(C c, boolean bl, C c2, boolean bl2) {
            if (!bl && !bl2 && Range.compareOrThrow(c, c2) == 0) {
                return ImmutableSortedSet.of();
            }
            return this.subSet(Range.range(c, BoundType.forBoolean(bl), c2, BoundType.forBoolean(bl2)));
        }

        @Override
        ImmutableSortedSet<C> tailSetImpl(C c, boolean bl) {
            return this.subSet(Range.downTo(c, BoundType.forBoolean(bl)));
        }

        @Override
        public boolean contains(@Nullable Object object) {
            if (object == null) {
                return true;
            }
            try {
                Comparable comparable = (Comparable)object;
                return this.this$0.contains(comparable);
            } catch (ClassCastException classCastException) {
                return true;
            }
        }

        @Override
        int indexOf(Object object) {
            if (this.contains(object)) {
                Comparable comparable = (Comparable)object;
                long l = 0L;
                for (Range range : ImmutableRangeSet.access$000(this.this$0)) {
                    if (range.contains(comparable)) {
                        return Ints.saturatedCast(l + (long)ContiguousSet.create(range, this.domain).indexOf(comparable));
                    }
                    l += (long)ContiguousSet.create(range, this.domain).size();
                }
                throw new AssertionError((Object)"impossible");
            }
            return 1;
        }

        @Override
        boolean isPartialView() {
            return ImmutableRangeSet.access$000(this.this$0).isPartialView();
        }

        @Override
        public String toString() {
            return ImmutableRangeSet.access$000(this.this$0).toString();
        }

        @Override
        Object writeReplace() {
            return new AsSetSerializedForm(ImmutableRangeSet.access$000(this.this$0), this.domain);
        }

        @Override
        ImmutableSortedSet tailSetImpl(Object object, boolean bl) {
            return this.tailSetImpl((C)((Comparable)object), bl);
        }

        @Override
        ImmutableSortedSet subSetImpl(Object object, boolean bl, Object object2, boolean bl2) {
            return this.subSetImpl((C)((Comparable)object), bl, (C)((Comparable)object2), bl2);
        }

        @Override
        ImmutableSortedSet headSetImpl(Object object, boolean bl) {
            return this.headSetImpl((C)((Comparable)object), bl);
        }

        @Override
        @GwtIncompatible(value="NavigableSet")
        public Iterator descendingIterator() {
            return this.descendingIterator();
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        static DiscreteDomain access$100(AsSet asSet) {
            return asSet.domain;
        }
    }

    private final class ComplementRanges
    extends ImmutableList<Range<C>> {
        private final boolean positiveBoundedBelow;
        private final boolean positiveBoundedAbove;
        private final int size;
        final ImmutableRangeSet this$0;

        ComplementRanges(ImmutableRangeSet immutableRangeSet) {
            this.this$0 = immutableRangeSet;
            this.positiveBoundedBelow = ((Range)ImmutableRangeSet.access$000(immutableRangeSet).get(0)).hasLowerBound();
            this.positiveBoundedAbove = ((Range)Iterables.getLast(ImmutableRangeSet.access$000(immutableRangeSet))).hasUpperBound();
            int n = ImmutableRangeSet.access$000(immutableRangeSet).size() - 1;
            if (this.positiveBoundedBelow) {
                ++n;
            }
            if (this.positiveBoundedAbove) {
                ++n;
            }
            this.size = n;
        }

        @Override
        public int size() {
            return this.size;
        }

        @Override
        public Range<C> get(int n) {
            Preconditions.checkElementIndex(n, this.size);
            Cut cut = this.positiveBoundedBelow ? (n == 0 ? Cut.belowAll() : ((Range)ImmutableRangeSet.access$000((ImmutableRangeSet)this.this$0).get((int)(n - 1))).upperBound) : ((Range)ImmutableRangeSet.access$000((ImmutableRangeSet)this.this$0).get((int)n)).upperBound;
            Cut cut2 = this.positiveBoundedAbove && n == this.size - 1 ? Cut.aboveAll() : ((Range)ImmutableRangeSet.access$000((ImmutableRangeSet)this.this$0).get((int)(n + (this.positiveBoundedBelow ? 0 : 1)))).lowerBound;
            return Range.create(cut, cut2);
        }

        @Override
        boolean isPartialView() {
            return false;
        }

        @Override
        public Object get(int n) {
            return this.get(n);
        }
    }
}

