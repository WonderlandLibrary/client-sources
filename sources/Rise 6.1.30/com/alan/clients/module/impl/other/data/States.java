package com.alan.clients.module.impl.other.data;

import com.alan.clients.util.EvictingList;

import java.util.Collection;

public class States<T> extends EvictingList<T> implements Cloneable {
    public States(int maxSize) {
        super(maxSize);
    }

    public States(Collection<? extends T> c, int maxSize) {
        super(c, maxSize);
    }

    public States(States<? extends T> c) {
        super(c, c.getMaxSize());
    }

    public T get() {
        return getLast();
    }

    public T get(int index) {
        return super.get(size() - index - 1);
    }

    @Override
    public States<T> clone() {
        return (States<T>) super.clone();
    }
}
