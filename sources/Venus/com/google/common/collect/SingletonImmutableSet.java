/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.UnmodifiableIterator;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.util.Iterator;

@GwtCompatible(serializable=true, emulated=true)
final class SingletonImmutableSet<E>
extends ImmutableSet<E> {
    final transient E element;
    @LazyInit
    private transient int cachedHashCode;

    SingletonImmutableSet(E e) {
        this.element = Preconditions.checkNotNull(e);
    }

    SingletonImmutableSet(E e, int n) {
        this.element = e;
        this.cachedHashCode = n;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean contains(Object object) {
        return this.element.equals(object);
    }

    @Override
    public UnmodifiableIterator<E> iterator() {
        return Iterators.singletonIterator(this.element);
    }

    @Override
    ImmutableList<E> createAsList() {
        return ImmutableList.of(this.element);
    }

    @Override
    boolean isPartialView() {
        return true;
    }

    @Override
    int copyIntoArray(Object[] objectArray, int n) {
        objectArray[n] = this.element;
        return n + 1;
    }

    @Override
    public final int hashCode() {
        int n = this.cachedHashCode;
        if (n == 0) {
            this.cachedHashCode = n = this.element.hashCode();
        }
        return n;
    }

    @Override
    boolean isHashCodeFast() {
        return this.cachedHashCode != 0;
    }

    @Override
    public String toString() {
        return '[' + this.element.toString() + ']';
    }

    @Override
    public Iterator iterator() {
        return this.iterator();
    }
}

