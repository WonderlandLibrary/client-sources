package com.alan.clients.util;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

public class EvictingList<T> extends LinkedList<T> implements Serializable {

    @Getter
    @Setter
    private int maxSize;

    public EvictingList(final int maxSize) {
        this.maxSize = maxSize;
    }

    public EvictingList(final Collection<? extends T> c, final int maxSize) {
        super(c);
        this.maxSize = maxSize;
    }

    @Override
    public boolean add(final T t) {
        if (size() >= getMaxSize()) removeFirst();
        return super.add(t);
    }

    public boolean isFull() {
        return size() >= getMaxSize();
    }

    public EvictingList<T> reverse() {
        EvictingList<T> list = new EvictingList<>(maxSize);
        for (int i = size() - 1; i >= 0; i--) {
            list.add(get(i));
        }
        return list;
    }
}