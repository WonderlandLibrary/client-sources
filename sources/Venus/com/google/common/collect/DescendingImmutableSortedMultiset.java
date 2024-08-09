/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.collect.BoundType;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedMultiset;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Multiset;
import com.google.common.collect.SortedMultiset;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedSet;
import javax.annotation.Nullable;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@GwtIncompatible
final class DescendingImmutableSortedMultiset<E>
extends ImmutableSortedMultiset<E> {
    private final transient ImmutableSortedMultiset<E> forward;

    DescendingImmutableSortedMultiset(ImmutableSortedMultiset<E> immutableSortedMultiset) {
        this.forward = immutableSortedMultiset;
    }

    @Override
    public int count(@Nullable Object object) {
        return this.forward.count(object);
    }

    @Override
    public Multiset.Entry<E> firstEntry() {
        return this.forward.lastEntry();
    }

    @Override
    public Multiset.Entry<E> lastEntry() {
        return this.forward.firstEntry();
    }

    @Override
    public int size() {
        return this.forward.size();
    }

    @Override
    public ImmutableSortedSet<E> elementSet() {
        return ((ImmutableSortedSet)this.forward.elementSet()).descendingSet();
    }

    @Override
    Multiset.Entry<E> getEntry(int n) {
        return (Multiset.Entry)((ImmutableSet)this.forward.entrySet()).asList().reverse().get(n);
    }

    @Override
    public ImmutableSortedMultiset<E> descendingMultiset() {
        return this.forward;
    }

    @Override
    public ImmutableSortedMultiset<E> headMultiset(E e, BoundType boundType) {
        return ((ImmutableSortedMultiset)this.forward.tailMultiset((Object)e, boundType)).descendingMultiset();
    }

    @Override
    public ImmutableSortedMultiset<E> tailMultiset(E e, BoundType boundType) {
        return ((ImmutableSortedMultiset)this.forward.headMultiset((Object)e, boundType)).descendingMultiset();
    }

    @Override
    boolean isPartialView() {
        return this.forward.isPartialView();
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
    public SortedMultiset descendingMultiset() {
        return this.descendingMultiset();
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

