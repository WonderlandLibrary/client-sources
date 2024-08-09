/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterators;
import com.google.common.collect.UnmodifiableIterator;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;

@GwtCompatible(serializable=true, emulated=true)
final class SingletonImmutableList<E>
extends ImmutableList<E> {
    final transient E element;

    SingletonImmutableList(E e) {
        this.element = Preconditions.checkNotNull(e);
    }

    @Override
    public E get(int n) {
        Preconditions.checkElementIndex(n, 1);
        return this.element;
    }

    @Override
    public UnmodifiableIterator<E> iterator() {
        return Iterators.singletonIterator(this.element);
    }

    @Override
    public Spliterator<E> spliterator() {
        return Collections.singleton(this.element).spliterator();
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public ImmutableList<E> subList(int n, int n2) {
        Preconditions.checkPositionIndexes(n, n2, 1);
        return n == n2 ? ImmutableList.of() : this;
    }

    @Override
    public String toString() {
        return '[' + this.element.toString() + ']';
    }

    @Override
    boolean isPartialView() {
        return true;
    }

    @Override
    public List subList(int n, int n2) {
        return this.subList(n, n2);
    }

    @Override
    public Iterator iterator() {
        return this.iterator();
    }
}

