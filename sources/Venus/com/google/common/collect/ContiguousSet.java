/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.BoundType;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.EmptyContiguousSet;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Ordering;
import com.google.common.collect.Range;
import com.google.common.collect.RegularContiguousSet;
import java.util.NavigableSet;
import java.util.NoSuchElementException;
import java.util.SortedSet;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@GwtCompatible(emulated=true)
public abstract class ContiguousSet<C extends Comparable>
extends ImmutableSortedSet<C> {
    final DiscreteDomain<C> domain;

    public static <C extends Comparable> ContiguousSet<C> create(Range<C> range, DiscreteDomain<C> discreteDomain) {
        Preconditions.checkNotNull(range);
        Preconditions.checkNotNull(discreteDomain);
        Range<C> range2 = range;
        try {
            if (!range.hasLowerBound()) {
                range2 = range2.intersection(Range.atLeast(discreteDomain.minValue()));
            }
            if (!range.hasUpperBound()) {
                range2 = range2.intersection(Range.atMost(discreteDomain.maxValue()));
            }
        } catch (NoSuchElementException noSuchElementException) {
            throw new IllegalArgumentException(noSuchElementException);
        }
        boolean bl = range2.isEmpty() || Range.compareOrThrow(range.lowerBound.leastValueAbove(discreteDomain), range.upperBound.greatestValueBelow(discreteDomain)) > 0;
        return bl ? new EmptyContiguousSet<C>(discreteDomain) : new RegularContiguousSet<C>(range2, discreteDomain);
    }

    ContiguousSet(DiscreteDomain<C> discreteDomain) {
        super(Ordering.natural());
        this.domain = discreteDomain;
    }

    @Override
    public ContiguousSet<C> headSet(C c) {
        return this.headSetImpl((C)((Comparable)Preconditions.checkNotNull(c)), true);
    }

    @Override
    @GwtIncompatible
    public ContiguousSet<C> headSet(C c, boolean bl) {
        return this.headSetImpl((C)((Comparable)Preconditions.checkNotNull(c)), bl);
    }

    @Override
    public ContiguousSet<C> subSet(C c, C c2) {
        Preconditions.checkNotNull(c);
        Preconditions.checkNotNull(c2);
        Preconditions.checkArgument(this.comparator().compare(c, c2) <= 0);
        return this.subSetImpl(c, true, c2, true);
    }

    @Override
    @GwtIncompatible
    public ContiguousSet<C> subSet(C c, boolean bl, C c2, boolean bl2) {
        Preconditions.checkNotNull(c);
        Preconditions.checkNotNull(c2);
        Preconditions.checkArgument(this.comparator().compare(c, c2) <= 0);
        return this.subSetImpl(c, bl, c2, bl2);
    }

    @Override
    public ContiguousSet<C> tailSet(C c) {
        return this.tailSetImpl((C)((Comparable)Preconditions.checkNotNull(c)), false);
    }

    @Override
    @GwtIncompatible
    public ContiguousSet<C> tailSet(C c, boolean bl) {
        return this.tailSetImpl((C)((Comparable)Preconditions.checkNotNull(c)), bl);
    }

    @Override
    abstract ContiguousSet<C> headSetImpl(C var1, boolean var2);

    @Override
    abstract ContiguousSet<C> subSetImpl(C var1, boolean var2, C var3, boolean var4);

    @Override
    abstract ContiguousSet<C> tailSetImpl(C var1, boolean var2);

    public abstract ContiguousSet<C> intersection(ContiguousSet<C> var1);

    public abstract Range<C> range();

    public abstract Range<C> range(BoundType var1, BoundType var2);

    @Override
    public String toString() {
        return this.range().toString();
    }

    @Deprecated
    public static <E> ImmutableSortedSet.Builder<E> builder() {
        throw new UnsupportedOperationException();
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
    @GwtIncompatible
    public ImmutableSortedSet tailSet(Object object, boolean bl) {
        return this.tailSet((C)((Comparable)object), bl);
    }

    @Override
    public ImmutableSortedSet tailSet(Object object) {
        return this.tailSet((C)((Comparable)object));
    }

    @Override
    @GwtIncompatible
    public ImmutableSortedSet subSet(Object object, boolean bl, Object object2, boolean bl2) {
        return this.subSet((C)((Comparable)object), bl, (C)((Comparable)object2), bl2);
    }

    @Override
    public ImmutableSortedSet subSet(Object object, Object object2) {
        return this.subSet((C)((Comparable)object), (C)((Comparable)object2));
    }

    @Override
    @GwtIncompatible
    public ImmutableSortedSet headSet(Object object, boolean bl) {
        return this.headSet((C)((Comparable)object), bl);
    }

    @Override
    public ImmutableSortedSet headSet(Object object) {
        return this.headSet((C)((Comparable)object));
    }

    @Override
    public SortedSet tailSet(Object object) {
        return this.tailSet((C)((Comparable)object));
    }

    @Override
    public SortedSet headSet(Object object) {
        return this.headSet((C)((Comparable)object));
    }

    @Override
    public SortedSet subSet(Object object, Object object2) {
        return this.subSet((C)((Comparable)object), (C)((Comparable)object2));
    }

    @Override
    @GwtIncompatible
    public NavigableSet tailSet(Object object, boolean bl) {
        return this.tailSet((C)((Comparable)object), bl);
    }

    @Override
    @GwtIncompatible
    public NavigableSet headSet(Object object, boolean bl) {
        return this.headSet((C)((Comparable)object), bl);
    }

    @Override
    @GwtIncompatible
    public NavigableSet subSet(Object object, boolean bl, Object object2, boolean bl2) {
        return this.subSet((C)((Comparable)object), bl, (C)((Comparable)object2), bl2);
    }
}

