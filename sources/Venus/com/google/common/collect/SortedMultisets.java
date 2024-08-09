/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.collect.BoundType;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import com.google.common.collect.SortedMultiset;
import com.google.j2objc.annotations.Weak;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.NoSuchElementException;
import java.util.SortedSet;
import javax.annotation.Nullable;

@GwtCompatible(emulated=true)
final class SortedMultisets {
    private SortedMultisets() {
    }

    private static <E> E getElementOrThrow(Multiset.Entry<E> entry) {
        if (entry == null) {
            throw new NoSuchElementException();
        }
        return entry.getElement();
    }

    private static <E> E getElementOrNull(@Nullable Multiset.Entry<E> entry) {
        return entry == null ? null : (E)entry.getElement();
    }

    static Object access$000(Multiset.Entry entry) {
        return SortedMultisets.getElementOrThrow(entry);
    }

    static Object access$100(Multiset.Entry entry) {
        return SortedMultisets.getElementOrNull(entry);
    }

    @GwtIncompatible
    static class NavigableElementSet<E>
    extends ElementSet<E>
    implements NavigableSet<E> {
        NavigableElementSet(SortedMultiset<E> sortedMultiset) {
            super(sortedMultiset);
        }

        @Override
        public E lower(E e) {
            return (E)SortedMultisets.access$100(this.multiset().headMultiset(e, BoundType.OPEN).lastEntry());
        }

        @Override
        public E floor(E e) {
            return (E)SortedMultisets.access$100(this.multiset().headMultiset(e, BoundType.CLOSED).lastEntry());
        }

        @Override
        public E ceiling(E e) {
            return (E)SortedMultisets.access$100(this.multiset().tailMultiset(e, BoundType.CLOSED).firstEntry());
        }

        @Override
        public E higher(E e) {
            return (E)SortedMultisets.access$100(this.multiset().tailMultiset(e, BoundType.OPEN).firstEntry());
        }

        @Override
        public NavigableSet<E> descendingSet() {
            return new NavigableElementSet(this.multiset().descendingMultiset());
        }

        @Override
        public Iterator<E> descendingIterator() {
            return this.descendingSet().iterator();
        }

        @Override
        public E pollFirst() {
            return (E)SortedMultisets.access$100(this.multiset().pollFirstEntry());
        }

        @Override
        public E pollLast() {
            return (E)SortedMultisets.access$100(this.multiset().pollLastEntry());
        }

        @Override
        public NavigableSet<E> subSet(E e, boolean bl, E e2, boolean bl2) {
            return new NavigableElementSet<E>(this.multiset().subMultiset(e, BoundType.forBoolean(bl), e2, BoundType.forBoolean(bl2)));
        }

        @Override
        public NavigableSet<E> headSet(E e, boolean bl) {
            return new NavigableElementSet<E>(this.multiset().headMultiset(e, BoundType.forBoolean(bl)));
        }

        @Override
        public NavigableSet<E> tailSet(E e, boolean bl) {
            return new NavigableElementSet<E>(this.multiset().tailMultiset(e, BoundType.forBoolean(bl)));
        }
    }

    static class ElementSet<E>
    extends Multisets.ElementSet<E>
    implements SortedSet<E> {
        @Weak
        private final SortedMultiset<E> multiset;

        ElementSet(SortedMultiset<E> sortedMultiset) {
            this.multiset = sortedMultiset;
        }

        @Override
        final SortedMultiset<E> multiset() {
            return this.multiset;
        }

        @Override
        public Comparator<? super E> comparator() {
            return this.multiset().comparator();
        }

        @Override
        public SortedSet<E> subSet(E e, E e2) {
            return this.multiset().subMultiset(e, BoundType.CLOSED, e2, BoundType.OPEN).elementSet();
        }

        @Override
        public SortedSet<E> headSet(E e) {
            return this.multiset().headMultiset(e, BoundType.OPEN).elementSet();
        }

        @Override
        public SortedSet<E> tailSet(E e) {
            return this.multiset().tailMultiset(e, BoundType.CLOSED).elementSet();
        }

        @Override
        public E first() {
            return (E)SortedMultisets.access$000(this.multiset().firstEntry());
        }

        @Override
        public E last() {
            return (E)SortedMultisets.access$000(this.multiset().lastEntry());
        }

        @Override
        Multiset multiset() {
            return this.multiset();
        }
    }
}

