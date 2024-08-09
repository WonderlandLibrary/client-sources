/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.collect.BoundType;
import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.Ordering;
import com.google.common.collect.Range;
import com.google.common.collect.UnmodifiableIterator;
import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import javax.annotation.Nullable;

@GwtCompatible(emulated=true)
final class EmptyContiguousSet<C extends Comparable>
extends ContiguousSet<C> {
    EmptyContiguousSet(DiscreteDomain<C> discreteDomain) {
        super(discreteDomain);
    }

    @Override
    public C first() {
        throw new NoSuchElementException();
    }

    @Override
    public C last() {
        throw new NoSuchElementException();
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public ContiguousSet<C> intersection(ContiguousSet<C> contiguousSet) {
        return this;
    }

    @Override
    public Range<C> range() {
        throw new NoSuchElementException();
    }

    @Override
    public Range<C> range(BoundType boundType, BoundType boundType2) {
        throw new NoSuchElementException();
    }

    @Override
    ContiguousSet<C> headSetImpl(C c, boolean bl) {
        return this;
    }

    @Override
    ContiguousSet<C> subSetImpl(C c, boolean bl, C c2, boolean bl2) {
        return this;
    }

    @Override
    ContiguousSet<C> tailSetImpl(C c, boolean bl) {
        return this;
    }

    @Override
    public boolean contains(Object object) {
        return true;
    }

    @Override
    @GwtIncompatible
    int indexOf(Object object) {
        return 1;
    }

    @Override
    public UnmodifiableIterator<C> iterator() {
        return Iterators.emptyIterator();
    }

    @Override
    @GwtIncompatible
    public UnmodifiableIterator<C> descendingIterator() {
        return Iterators.emptyIterator();
    }

    @Override
    boolean isPartialView() {
        return true;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public ImmutableList<C> asList() {
        return ImmutableList.of();
    }

    @Override
    public String toString() {
        return "[]";
    }

    @Override
    public boolean equals(@Nullable Object object) {
        if (object instanceof Set) {
            Set set = (Set)object;
            return set.isEmpty();
        }
        return true;
    }

    @Override
    @GwtIncompatible
    boolean isHashCodeFast() {
        return false;
    }

    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    @GwtIncompatible
    Object writeReplace() {
        return new SerializedForm(this.domain, null);
    }

    @Override
    @GwtIncompatible
    ImmutableSortedSet<C> createDescendingSet() {
        return ImmutableSortedSet.emptySet(Ordering.natural().reverse());
    }

    @Override
    public Object last() {
        return this.last();
    }

    @Override
    public Object first() {
        return this.first();
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
    public Iterator descendingIterator() {
        return this.descendingIterator();
    }

    @Override
    public Iterator iterator() {
        return this.iterator();
    }

    @GwtIncompatible
    private static final class SerializedForm<C extends Comparable>
    implements Serializable {
        private final DiscreteDomain<C> domain;
        private static final long serialVersionUID = 0L;

        private SerializedForm(DiscreteDomain<C> discreteDomain) {
            this.domain = discreteDomain;
        }

        private Object readResolve() {
            return new EmptyContiguousSet<C>(this.domain);
        }

        SerializedForm(DiscreteDomain discreteDomain, 1 var2_2) {
            this(discreteDomain);
        }
    }
}

