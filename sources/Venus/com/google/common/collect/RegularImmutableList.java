/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterators;
import com.google.common.collect.ObjectArrays;
import com.google.common.collect.UnmodifiableListIterator;
import java.util.ListIterator;
import java.util.Spliterator;
import java.util.Spliterators;

@GwtCompatible(serializable=true, emulated=true)
class RegularImmutableList<E>
extends ImmutableList<E> {
    static final ImmutableList<Object> EMPTY = new RegularImmutableList<Object>(ObjectArrays.EMPTY_ARRAY);
    private final transient Object[] array;

    RegularImmutableList(Object[] objectArray) {
        this.array = objectArray;
    }

    @Override
    public int size() {
        return this.array.length;
    }

    @Override
    boolean isPartialView() {
        return true;
    }

    @Override
    int copyIntoArray(Object[] objectArray, int n) {
        System.arraycopy(this.array, 0, objectArray, n, this.array.length);
        return n + this.array.length;
    }

    @Override
    public E get(int n) {
        return (E)this.array[n];
    }

    @Override
    public UnmodifiableListIterator<E> listIterator(int n) {
        return Iterators.forArray(this.array, 0, this.array.length, n);
    }

    @Override
    public Spliterator<E> spliterator() {
        return Spliterators.spliterator(this.array, 1296);
    }

    @Override
    public ListIterator listIterator(int n) {
        return this.listIterator(n);
    }
}

