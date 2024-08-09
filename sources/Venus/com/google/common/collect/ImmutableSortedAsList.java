/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.collect.CollectSpliterators;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.RegularImmutableAsList;
import com.google.common.collect.RegularImmutableSortedSet;
import com.google.common.collect.SortedIterable;
import java.util.Comparator;
import java.util.Spliterator;
import javax.annotation.Nullable;

@GwtCompatible(emulated=true)
final class ImmutableSortedAsList<E>
extends RegularImmutableAsList<E>
implements SortedIterable<E> {
    ImmutableSortedAsList(ImmutableSortedSet<E> immutableSortedSet, ImmutableList<E> immutableList) {
        super(immutableSortedSet, immutableList);
    }

    @Override
    ImmutableSortedSet<E> delegateCollection() {
        return (ImmutableSortedSet)super.delegateCollection();
    }

    @Override
    public Comparator<? super E> comparator() {
        return ((ImmutableSortedSet)this.delegateCollection()).comparator();
    }

    @Override
    @GwtIncompatible
    public int indexOf(@Nullable Object object) {
        int n = ((ImmutableSortedSet)this.delegateCollection()).indexOf(object);
        return n >= 0 && this.get(n).equals(object) ? n : -1;
    }

    @Override
    @GwtIncompatible
    public int lastIndexOf(@Nullable Object object) {
        return this.indexOf(object);
    }

    @Override
    public boolean contains(Object object) {
        return this.indexOf(object) >= 0;
    }

    @Override
    @GwtIncompatible
    ImmutableList<E> subListUnchecked(int n, int n2) {
        ImmutableList immutableList = super.subListUnchecked(n, n2);
        return new RegularImmutableSortedSet(immutableList, this.comparator()).asList();
    }

    @Override
    public Spliterator<E> spliterator() {
        return CollectSpliterators.indexed(this.size(), 1301, this.delegateList()::get, this.comparator());
    }

    @Override
    ImmutableCollection delegateCollection() {
        return this.delegateCollection();
    }
}

