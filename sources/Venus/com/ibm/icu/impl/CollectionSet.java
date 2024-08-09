/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class CollectionSet<E>
implements Set<E> {
    private final Collection<E> data;

    public CollectionSet(Collection<E> collection) {
        this.data = collection;
    }

    @Override
    public int size() {
        return this.data.size();
    }

    @Override
    public boolean isEmpty() {
        return this.data.isEmpty();
    }

    @Override
    public boolean contains(Object object) {
        return this.data.contains(object);
    }

    @Override
    public Iterator<E> iterator() {
        return this.data.iterator();
    }

    @Override
    public Object[] toArray() {
        return this.data.toArray();
    }

    @Override
    public <T> T[] toArray(T[] TArray) {
        return this.data.toArray(TArray);
    }

    @Override
    public boolean add(E e) {
        return this.data.add(e);
    }

    @Override
    public boolean remove(Object object) {
        return this.data.remove(object);
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return this.data.containsAll(collection);
    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {
        return this.data.addAll(collection);
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        return this.data.retainAll(collection);
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        return this.data.removeAll(collection);
    }

    @Override
    public void clear() {
        this.data.clear();
    }
}

