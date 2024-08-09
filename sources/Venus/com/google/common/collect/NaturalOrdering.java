/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.Ordering;
import com.google.common.collect.ReverseNaturalOrdering;
import java.io.Serializable;

@GwtCompatible(serializable=true)
final class NaturalOrdering
extends Ordering<Comparable>
implements Serializable {
    static final NaturalOrdering INSTANCE = new NaturalOrdering();
    private transient Ordering<Comparable> nullsFirst;
    private transient Ordering<Comparable> nullsLast;
    private static final long serialVersionUID = 0L;

    @Override
    public int compare(Comparable comparable, Comparable comparable2) {
        Preconditions.checkNotNull(comparable);
        Preconditions.checkNotNull(comparable2);
        return comparable.compareTo(comparable2);
    }

    @Override
    public <S extends Comparable> Ordering<S> nullsFirst() {
        Ordering<Comparable<Object>> ordering = this.nullsFirst;
        if (ordering == null) {
            ordering = this.nullsFirst = super.nullsFirst();
        }
        return ordering;
    }

    @Override
    public <S extends Comparable> Ordering<S> nullsLast() {
        Ordering<Comparable<Object>> ordering = this.nullsLast;
        if (ordering == null) {
            ordering = this.nullsLast = super.nullsLast();
        }
        return ordering;
    }

    @Override
    public <S extends Comparable> Ordering<S> reverse() {
        return ReverseNaturalOrdering.INSTANCE;
    }

    private Object readResolve() {
        return INSTANCE;
    }

    public String toString() {
        return "Ordering.natural()";
    }

    private NaturalOrdering() {
    }

    @Override
    public int compare(Object object, Object object2) {
        return this.compare((Comparable)object, (Comparable)object2);
    }
}

