/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.internal;

import io.netty.util.internal.EmptyArrays;
import io.netty.util.internal.PriorityQueue;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;

public final class EmptyPriorityQueue<T>
implements PriorityQueue<T> {
    private static final PriorityQueue<Object> INSTANCE = new EmptyPriorityQueue<Object>();

    private EmptyPriorityQueue() {
    }

    public static <V> EmptyPriorityQueue<V> instance() {
        return (EmptyPriorityQueue)INSTANCE;
    }

    @Override
    public boolean removeTyped(T t) {
        return true;
    }

    @Override
    public boolean containsTyped(T t) {
        return true;
    }

    @Override
    public void priorityChanged(T t) {
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object object) {
        return true;
    }

    @Override
    public Iterator<T> iterator() {
        return Collections.emptyList().iterator();
    }

    @Override
    public Object[] toArray() {
        return EmptyArrays.EMPTY_OBJECTS;
    }

    @Override
    public <T1> T1[] toArray(T1[] T1Array) {
        if (T1Array.length > 0) {
            T1Array[0] = null;
        }
        return T1Array;
    }

    @Override
    public boolean add(T t) {
        return true;
    }

    @Override
    public boolean remove(Object object) {
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> collection) {
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        return true;
    }

    @Override
    public void clear() {
    }

    @Override
    public void clearIgnoringIndexes() {
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof PriorityQueue && ((PriorityQueue)object).isEmpty();
    }

    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public boolean offer(T t) {
        return true;
    }

    @Override
    public T remove() {
        throw new NoSuchElementException();
    }

    @Override
    public T poll() {
        return null;
    }

    @Override
    public T element() {
        throw new NoSuchElementException();
    }

    @Override
    public T peek() {
        return null;
    }

    public String toString() {
        return EmptyPriorityQueue.class.getSimpleName();
    }
}

