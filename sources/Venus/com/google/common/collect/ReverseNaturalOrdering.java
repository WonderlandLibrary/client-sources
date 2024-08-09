/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.NaturalOrdering;
import com.google.common.collect.Ordering;
import java.io.Serializable;
import java.util.Iterator;

@GwtCompatible(serializable=true)
final class ReverseNaturalOrdering
extends Ordering<Comparable>
implements Serializable {
    static final ReverseNaturalOrdering INSTANCE = new ReverseNaturalOrdering();
    private static final long serialVersionUID = 0L;

    @Override
    public int compare(Comparable comparable, Comparable comparable2) {
        Preconditions.checkNotNull(comparable);
        if (comparable == comparable2) {
            return 1;
        }
        return comparable2.compareTo(comparable);
    }

    @Override
    public <S extends Comparable> Ordering<S> reverse() {
        return Ordering.natural();
    }

    @Override
    public <E extends Comparable> E min(E e, E e2) {
        return NaturalOrdering.INSTANCE.max(e, e2);
    }

    @Override
    public <E extends Comparable> E min(E e, E e2, E e3, E ... EArray) {
        return NaturalOrdering.INSTANCE.max(e, e2, e3, EArray);
    }

    @Override
    public <E extends Comparable> E min(Iterator<E> iterator2) {
        return (E)((Comparable)NaturalOrdering.INSTANCE.max(iterator2));
    }

    @Override
    public <E extends Comparable> E min(Iterable<E> iterable) {
        return (E)((Comparable)NaturalOrdering.INSTANCE.max(iterable));
    }

    @Override
    public <E extends Comparable> E max(E e, E e2) {
        return NaturalOrdering.INSTANCE.min(e, e2);
    }

    @Override
    public <E extends Comparable> E max(E e, E e2, E e3, E ... EArray) {
        return NaturalOrdering.INSTANCE.min(e, e2, e3, EArray);
    }

    @Override
    public <E extends Comparable> E max(Iterator<E> iterator2) {
        return (E)((Comparable)NaturalOrdering.INSTANCE.min(iterator2));
    }

    @Override
    public <E extends Comparable> E max(Iterable<E> iterable) {
        return (E)((Comparable)NaturalOrdering.INSTANCE.min(iterable));
    }

    private Object readResolve() {
        return INSTANCE;
    }

    public String toString() {
        return "Ordering.natural().reverse()";
    }

    private ReverseNaturalOrdering() {
    }

    @Override
    public Object max(Object object, Object object2, Object object3, Object[] objectArray) {
        return this.max((Comparable)object, (Comparable)object2, (Comparable)object3, (Comparable[])objectArray);
    }

    @Override
    public Object max(Object object, Object object2) {
        return this.max((Comparable)object, (Comparable)object2);
    }

    @Override
    public Object max(Iterable iterable) {
        return this.max(iterable);
    }

    @Override
    public Object max(Iterator iterator2) {
        return this.max(iterator2);
    }

    @Override
    public Object min(Object object, Object object2, Object object3, Object[] objectArray) {
        return this.min((Comparable)object, (Comparable)object2, (Comparable)object3, (Comparable[])objectArray);
    }

    @Override
    public Object min(Object object, Object object2) {
        return this.min((Comparable)object, (Comparable)object2);
    }

    @Override
    public Object min(Iterable iterable) {
        return this.min(iterable);
    }

    @Override
    public Object min(Iterator iterator2) {
        return this.min(iterator2);
    }

    @Override
    public int compare(Object object, Object object2) {
        return this.compare((Comparable)object, (Comparable)object2);
    }
}

