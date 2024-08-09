/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.BoundType;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedMultiset;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import com.google.common.collect.Ordering;
import com.google.common.collect.RegularImmutableSortedSet;
import com.google.common.collect.SortedMultiset;
import com.google.common.primitives.Ints;
import java.util.Comparator;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.function.ObjIntConsumer;
import javax.annotation.Nullable;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@GwtIncompatible
final class RegularImmutableSortedMultiset<E>
extends ImmutableSortedMultiset<E> {
    private static final long[] ZERO_CUMULATIVE_COUNTS = new long[]{0L};
    static final ImmutableSortedMultiset<Comparable> NATURAL_EMPTY_MULTISET = new RegularImmutableSortedMultiset(Ordering.natural());
    private final transient RegularImmutableSortedSet<E> elementSet;
    private final transient long[] cumulativeCounts;
    private final transient int offset;
    private final transient int length;

    RegularImmutableSortedMultiset(Comparator<? super E> comparator) {
        this.elementSet = ImmutableSortedSet.emptySet(comparator);
        this.cumulativeCounts = ZERO_CUMULATIVE_COUNTS;
        this.offset = 0;
        this.length = 0;
    }

    RegularImmutableSortedMultiset(RegularImmutableSortedSet<E> regularImmutableSortedSet, long[] lArray, int n, int n2) {
        this.elementSet = regularImmutableSortedSet;
        this.cumulativeCounts = lArray;
        this.offset = n;
        this.length = n2;
    }

    private int getCount(int n) {
        return (int)(this.cumulativeCounts[this.offset + n + 1] - this.cumulativeCounts[this.offset + n]);
    }

    @Override
    Multiset.Entry<E> getEntry(int n) {
        return Multisets.immutableEntry(this.elementSet.asList().get(n), this.getCount(n));
    }

    @Override
    public void forEachEntry(ObjIntConsumer<? super E> objIntConsumer) {
        Preconditions.checkNotNull(objIntConsumer);
        for (int i = 0; i < this.size(); ++i) {
            objIntConsumer.accept(this.elementSet.asList().get(i), this.getCount(i));
        }
    }

    @Override
    public Multiset.Entry<E> firstEntry() {
        return this.isEmpty() ? null : this.getEntry(0);
    }

    @Override
    public Multiset.Entry<E> lastEntry() {
        return this.isEmpty() ? null : this.getEntry(this.length - 1);
    }

    @Override
    public int count(@Nullable Object object) {
        int n = this.elementSet.indexOf(object);
        return n >= 0 ? this.getCount(n) : 0;
    }

    @Override
    public int size() {
        long l = this.cumulativeCounts[this.offset + this.length] - this.cumulativeCounts[this.offset];
        return Ints.saturatedCast(l);
    }

    @Override
    public ImmutableSortedSet<E> elementSet() {
        return this.elementSet;
    }

    @Override
    public ImmutableSortedMultiset<E> headMultiset(E e, BoundType boundType) {
        return this.getSubMultiset(0, this.elementSet.headIndex(e, Preconditions.checkNotNull(boundType) == BoundType.CLOSED));
    }

    @Override
    public ImmutableSortedMultiset<E> tailMultiset(E e, BoundType boundType) {
        return this.getSubMultiset(this.elementSet.tailIndex(e, Preconditions.checkNotNull(boundType) == BoundType.CLOSED), this.length);
    }

    ImmutableSortedMultiset<E> getSubMultiset(int n, int n2) {
        Preconditions.checkPositionIndexes(n, n2, this.length);
        if (n == n2) {
            return RegularImmutableSortedMultiset.emptyMultiset(this.comparator());
        }
        if (n == 0 && n2 == this.length) {
            return this;
        }
        RegularImmutableSortedSet<E> regularImmutableSortedSet = this.elementSet.getSubSet(n, n2);
        return new RegularImmutableSortedMultiset<E>(regularImmutableSortedSet, this.cumulativeCounts, this.offset + n, n2 - n);
    }

    @Override
    boolean isPartialView() {
        return this.offset > 0 || this.length < this.cumulativeCounts.length - 1;
    }

    @Override
    public SortedMultiset tailMultiset(Object object, BoundType boundType) {
        return this.tailMultiset(object, boundType);
    }

    @Override
    public SortedMultiset headMultiset(Object object, BoundType boundType) {
        return this.headMultiset(object, boundType);
    }

    @Override
    public NavigableSet elementSet() {
        return this.elementSet();
    }

    @Override
    public SortedSet elementSet() {
        return this.elementSet();
    }

    @Override
    public Set elementSet() {
        return this.elementSet();
    }

    @Override
    public ImmutableSet elementSet() {
        return this.elementSet();
    }
}

