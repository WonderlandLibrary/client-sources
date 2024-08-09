/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.collect.ForwardingSortedSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedSet;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@GwtIncompatible
public abstract class ForwardingNavigableSet<E>
extends ForwardingSortedSet<E>
implements NavigableSet<E> {
    protected ForwardingNavigableSet() {
    }

    @Override
    protected abstract NavigableSet<E> delegate();

    @Override
    public E lower(E e) {
        return this.delegate().lower(e);
    }

    protected E standardLower(E e) {
        return Iterators.getNext(this.headSet(e, true).descendingIterator(), null);
    }

    @Override
    public E floor(E e) {
        return this.delegate().floor(e);
    }

    protected E standardFloor(E e) {
        return Iterators.getNext(this.headSet(e, false).descendingIterator(), null);
    }

    @Override
    public E ceiling(E e) {
        return this.delegate().ceiling(e);
    }

    protected E standardCeiling(E e) {
        return Iterators.getNext(this.tailSet(e, false).iterator(), null);
    }

    @Override
    public E higher(E e) {
        return this.delegate().higher(e);
    }

    protected E standardHigher(E e) {
        return Iterators.getNext(this.tailSet(e, true).iterator(), null);
    }

    @Override
    public E pollFirst() {
        return this.delegate().pollFirst();
    }

    protected E standardPollFirst() {
        return Iterators.pollNext(this.iterator());
    }

    @Override
    public E pollLast() {
        return this.delegate().pollLast();
    }

    protected E standardPollLast() {
        return Iterators.pollNext(this.descendingIterator());
    }

    protected E standardFirst() {
        return this.iterator().next();
    }

    protected E standardLast() {
        return this.descendingIterator().next();
    }

    @Override
    public NavigableSet<E> descendingSet() {
        return this.delegate().descendingSet();
    }

    @Override
    public Iterator<E> descendingIterator() {
        return this.delegate().descendingIterator();
    }

    @Override
    public NavigableSet<E> subSet(E e, boolean bl, E e2, boolean bl2) {
        return this.delegate().subSet(e, bl, e2, bl2);
    }

    @Beta
    protected NavigableSet<E> standardSubSet(E e, boolean bl, E e2, boolean bl2) {
        return this.tailSet(e, bl).headSet(e2, bl2);
    }

    @Override
    protected SortedSet<E> standardSubSet(E e, E e2) {
        return this.subSet(e, true, e2, true);
    }

    @Override
    public NavigableSet<E> headSet(E e, boolean bl) {
        return this.delegate().headSet(e, bl);
    }

    protected SortedSet<E> standardHeadSet(E e) {
        return this.headSet(e, true);
    }

    @Override
    public NavigableSet<E> tailSet(E e, boolean bl) {
        return this.delegate().tailSet(e, bl);
    }

    protected SortedSet<E> standardTailSet(E e) {
        return this.tailSet(e, false);
    }

    @Override
    protected SortedSet delegate() {
        return this.delegate();
    }

    @Override
    protected Set delegate() {
        return this.delegate();
    }

    @Override
    protected Collection delegate() {
        return this.delegate();
    }

    @Override
    protected Object delegate() {
        return this.delegate();
    }

    @Beta
    protected class StandardDescendingSet
    extends Sets.DescendingSet<E> {
        final ForwardingNavigableSet this$0;

        public StandardDescendingSet(ForwardingNavigableSet forwardingNavigableSet) {
            this.this$0 = forwardingNavigableSet;
            super(forwardingNavigableSet);
        }
    }
}

