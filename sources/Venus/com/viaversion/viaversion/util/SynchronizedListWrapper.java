/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.util;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class SynchronizedListWrapper<E>
implements List<E> {
    private final List<E> list;
    private final Consumer<E> addHandler;

    public SynchronizedListWrapper(List<E> list, Consumer<E> consumer) {
        this.list = list;
        this.addHandler = consumer;
    }

    public List<E> originalList() {
        return this.list;
    }

    private void handleAdd(E e) {
        this.addHandler.accept(e);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int size() {
        SynchronizedListWrapper synchronizedListWrapper = this;
        synchronized (synchronizedListWrapper) {
            return this.list.size();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean isEmpty() {
        SynchronizedListWrapper synchronizedListWrapper = this;
        synchronized (synchronizedListWrapper) {
            return this.list.isEmpty();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean contains(Object object) {
        SynchronizedListWrapper synchronizedListWrapper = this;
        synchronized (synchronizedListWrapper) {
            return this.list.contains(object);
        }
    }

    @Override
    public @NonNull Iterator<E> iterator() {
        return this.listIterator();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Object @NonNull [] toArray() {
        SynchronizedListWrapper synchronizedListWrapper = this;
        synchronized (synchronizedListWrapper) {
            return this.list.toArray();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean add(E e) {
        SynchronizedListWrapper synchronizedListWrapper = this;
        synchronized (synchronizedListWrapper) {
            this.handleAdd(e);
            return this.list.add(e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean remove(Object object) {
        SynchronizedListWrapper synchronizedListWrapper = this;
        synchronized (synchronizedListWrapper) {
            return this.list.remove(object);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean addAll(Collection<? extends E> collection) {
        SynchronizedListWrapper synchronizedListWrapper = this;
        synchronized (synchronizedListWrapper) {
            for (E e : collection) {
                this.handleAdd(e);
            }
            return this.list.addAll(collection);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean addAll(int n, Collection<? extends E> collection) {
        SynchronizedListWrapper synchronizedListWrapper = this;
        synchronized (synchronizedListWrapper) {
            for (E e : collection) {
                this.handleAdd(e);
            }
            return this.list.addAll(n, collection);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void clear() {
        SynchronizedListWrapper synchronizedListWrapper = this;
        synchronized (synchronizedListWrapper) {
            this.list.clear();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public E get(int n) {
        SynchronizedListWrapper synchronizedListWrapper = this;
        synchronized (synchronizedListWrapper) {
            return this.list.get(n);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public E set(int n, E e) {
        SynchronizedListWrapper synchronizedListWrapper = this;
        synchronized (synchronizedListWrapper) {
            return this.list.set(n, e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void add(int n, E e) {
        SynchronizedListWrapper synchronizedListWrapper = this;
        synchronized (synchronizedListWrapper) {
            this.list.add(n, e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public E remove(int n) {
        SynchronizedListWrapper synchronizedListWrapper = this;
        synchronized (synchronizedListWrapper) {
            return this.list.remove(n);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int indexOf(Object object) {
        SynchronizedListWrapper synchronizedListWrapper = this;
        synchronized (synchronizedListWrapper) {
            return this.list.indexOf(object);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int lastIndexOf(Object object) {
        SynchronizedListWrapper synchronizedListWrapper = this;
        synchronized (synchronizedListWrapper) {
            return this.list.lastIndexOf(object);
        }
    }

    @Override
    public @NonNull ListIterator<E> listIterator() {
        return this.list.listIterator();
    }

    @Override
    public @NonNull ListIterator<E> listIterator(int n) {
        return this.list.listIterator(n);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public @NonNull List<E> subList(int n, int n2) {
        SynchronizedListWrapper synchronizedListWrapper = this;
        synchronized (synchronizedListWrapper) {
            return this.list.subList(n, n2);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean retainAll(@NonNull Collection<?> collection) {
        SynchronizedListWrapper synchronizedListWrapper = this;
        synchronized (synchronizedListWrapper) {
            return this.list.retainAll(collection);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean removeAll(@NonNull Collection<?> collection) {
        SynchronizedListWrapper synchronizedListWrapper = this;
        synchronized (synchronizedListWrapper) {
            return this.list.removeAll(collection);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean containsAll(@NonNull Collection<?> collection) {
        SynchronizedListWrapper synchronizedListWrapper = this;
        synchronized (synchronizedListWrapper) {
            return this.list.containsAll(collection);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public <T> T @NonNull [] toArray(T @NonNull [] TArray) {
        SynchronizedListWrapper synchronizedListWrapper = this;
        synchronized (synchronizedListWrapper) {
            return this.list.toArray(TArray);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void sort(Comparator<? super E> comparator) {
        SynchronizedListWrapper synchronizedListWrapper = this;
        synchronized (synchronizedListWrapper) {
            this.list.sort(comparator);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void forEach(Consumer<? super E> consumer) {
        SynchronizedListWrapper synchronizedListWrapper = this;
        synchronized (synchronizedListWrapper) {
            this.list.forEach(consumer);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean removeIf(Predicate<? super E> predicate) {
        SynchronizedListWrapper synchronizedListWrapper = this;
        synchronized (synchronizedListWrapper) {
            return this.list.removeIf(predicate);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        SynchronizedListWrapper synchronizedListWrapper = this;
        synchronized (synchronizedListWrapper) {
            return this.list.equals(object);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int hashCode() {
        SynchronizedListWrapper synchronizedListWrapper = this;
        synchronized (synchronizedListWrapper) {
            return this.list.hashCode();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String toString() {
        SynchronizedListWrapper synchronizedListWrapper = this;
        synchronized (synchronizedListWrapper) {
            return this.list.toString();
        }
    }
}

