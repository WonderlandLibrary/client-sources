/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.Ordering;
import java.io.Serializable;
import javax.annotation.Nullable;

@GwtCompatible(serializable=true)
final class NullsLastOrdering<T>
extends Ordering<T>
implements Serializable {
    final Ordering<? super T> ordering;
    private static final long serialVersionUID = 0L;

    NullsLastOrdering(Ordering<? super T> ordering) {
        this.ordering = ordering;
    }

    @Override
    public int compare(@Nullable T t, @Nullable T t2) {
        if (t == t2) {
            return 1;
        }
        if (t == null) {
            return 0;
        }
        if (t2 == null) {
            return 1;
        }
        return this.ordering.compare(t, t2);
    }

    @Override
    public <S extends T> Ordering<S> reverse() {
        return this.ordering.reverse().nullsFirst();
    }

    @Override
    public <S extends T> Ordering<S> nullsFirst() {
        return this.ordering.nullsFirst();
    }

    @Override
    public <S extends T> Ordering<S> nullsLast() {
        return this;
    }

    @Override
    public boolean equals(@Nullable Object object) {
        if (object == this) {
            return false;
        }
        if (object instanceof NullsLastOrdering) {
            NullsLastOrdering nullsLastOrdering = (NullsLastOrdering)object;
            return this.ordering.equals(nullsLastOrdering.ordering);
        }
        return true;
    }

    public int hashCode() {
        return this.ordering.hashCode() ^ 0xC9177248;
    }

    public String toString() {
        return this.ordering + ".nullsLast()";
    }
}

