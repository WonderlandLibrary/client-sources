/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.Ordering;
import java.io.Serializable;
import java.util.Iterator;
import javax.annotation.Nullable;

@GwtCompatible(serializable=true)
final class ReverseOrdering<T>
extends Ordering<T>
implements Serializable {
    final Ordering<? super T> forwardOrder;
    private static final long serialVersionUID = 0L;

    ReverseOrdering(Ordering<? super T> ordering) {
        this.forwardOrder = Preconditions.checkNotNull(ordering);
    }

    @Override
    public int compare(T t, T t2) {
        return this.forwardOrder.compare(t2, t);
    }

    @Override
    public <S extends T> Ordering<S> reverse() {
        return this.forwardOrder;
    }

    @Override
    public <E extends T> E min(E e, E e2) {
        return this.forwardOrder.max(e, e2);
    }

    @Override
    public <E extends T> E min(E e, E e2, E e3, E ... EArray) {
        return this.forwardOrder.max(e, e2, e3, EArray);
    }

    @Override
    public <E extends T> E min(Iterator<E> iterator2) {
        return this.forwardOrder.max(iterator2);
    }

    @Override
    public <E extends T> E min(Iterable<E> iterable) {
        return this.forwardOrder.max(iterable);
    }

    @Override
    public <E extends T> E max(E e, E e2) {
        return this.forwardOrder.min(e, e2);
    }

    @Override
    public <E extends T> E max(E e, E e2, E e3, E ... EArray) {
        return this.forwardOrder.min(e, e2, e3, EArray);
    }

    @Override
    public <E extends T> E max(Iterator<E> iterator2) {
        return this.forwardOrder.min(iterator2);
    }

    @Override
    public <E extends T> E max(Iterable<E> iterable) {
        return this.forwardOrder.min(iterable);
    }

    public int hashCode() {
        return -this.forwardOrder.hashCode();
    }

    @Override
    public boolean equals(@Nullable Object object) {
        if (object == this) {
            return false;
        }
        if (object instanceof ReverseOrdering) {
            ReverseOrdering reverseOrdering = (ReverseOrdering)object;
            return this.forwardOrder.equals(reverseOrdering.forwardOrder);
        }
        return true;
    }

    public String toString() {
        return this.forwardOrder + ".reverse()";
    }
}

