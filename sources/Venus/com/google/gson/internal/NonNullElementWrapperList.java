/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.gson.internal;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.RandomAccess;

public class NonNullElementWrapperList<E>
extends AbstractList<E>
implements RandomAccess {
    private final ArrayList<E> delegate;

    public NonNullElementWrapperList(ArrayList<E> arrayList) {
        this.delegate = Objects.requireNonNull(arrayList);
    }

    @Override
    public E get(int n) {
        return this.delegate.get(n);
    }

    @Override
    public int size() {
        return this.delegate.size();
    }

    private E nonNull(E e) {
        if (e == null) {
            throw new NullPointerException("Element must be non-null");
        }
        return e;
    }

    @Override
    public E set(int n, E e) {
        return this.delegate.set(n, this.nonNull(e));
    }

    @Override
    public void add(int n, E e) {
        this.delegate.add(n, this.nonNull(e));
    }

    @Override
    public E remove(int n) {
        return this.delegate.remove(n);
    }

    @Override
    public void clear() {
        this.delegate.clear();
    }

    @Override
    public boolean remove(Object object) {
        return this.delegate.remove(object);
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        return this.delegate.removeAll(collection);
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        return this.delegate.retainAll(collection);
    }

    @Override
    public boolean contains(Object object) {
        return this.delegate.contains(object);
    }

    @Override
    public int indexOf(Object object) {
        return this.delegate.indexOf(object);
    }

    @Override
    public int lastIndexOf(Object object) {
        return this.delegate.lastIndexOf(object);
    }

    @Override
    public Object[] toArray() {
        return this.delegate.toArray();
    }

    @Override
    public <T> T[] toArray(T[] TArray) {
        return this.delegate.toArray(TArray);
    }

    @Override
    public boolean equals(Object object) {
        return this.delegate.equals(object);
    }

    @Override
    public int hashCode() {
        return this.delegate.hashCode();
    }
}

