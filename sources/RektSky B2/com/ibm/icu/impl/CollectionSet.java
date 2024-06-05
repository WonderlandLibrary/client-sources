package com.ibm.icu.impl;

import java.util.*;

public class CollectionSet<E> implements Set<E>
{
    private final Collection<E> data;
    
    public CollectionSet(final Collection<E> data) {
        this.data = data;
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
    public boolean contains(final Object o) {
        return this.data.contains(o);
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
    public <T> T[] toArray(final T[] a) {
        return this.data.toArray(a);
    }
    
    @Override
    public boolean add(final E e) {
        return this.data.add(e);
    }
    
    @Override
    public boolean remove(final Object o) {
        return this.data.remove(o);
    }
    
    @Override
    public boolean containsAll(final Collection<?> c) {
        return this.data.containsAll(c);
    }
    
    @Override
    public boolean addAll(final Collection<? extends E> c) {
        return this.data.addAll(c);
    }
    
    @Override
    public boolean retainAll(final Collection<?> c) {
        return this.data.retainAll(c);
    }
    
    @Override
    public boolean removeAll(final Collection<?> c) {
        return this.data.removeAll(c);
    }
    
    @Override
    public void clear() {
        this.data.clear();
    }
}
