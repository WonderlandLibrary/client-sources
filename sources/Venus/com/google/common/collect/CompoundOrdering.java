/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Ordering;
import java.io.Serializable;
import java.util.Comparator;

@GwtCompatible(serializable=true)
final class CompoundOrdering<T>
extends Ordering<T>
implements Serializable {
    final ImmutableList<Comparator<? super T>> comparators;
    private static final long serialVersionUID = 0L;

    CompoundOrdering(Comparator<? super T> comparator, Comparator<? super T> comparator2) {
        this.comparators = ImmutableList.of(comparator, comparator2);
    }

    CompoundOrdering(Iterable<? extends Comparator<? super T>> iterable) {
        this.comparators = ImmutableList.copyOf(iterable);
    }

    @Override
    public int compare(T t, T t2) {
        int n = this.comparators.size();
        for (int i = 0; i < n; ++i) {
            int n2 = ((Comparator)this.comparators.get(i)).compare(t, t2);
            if (n2 == 0) continue;
            return n2;
        }
        return 1;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return false;
        }
        if (object instanceof CompoundOrdering) {
            CompoundOrdering compoundOrdering = (CompoundOrdering)object;
            return this.comparators.equals(compoundOrdering.comparators);
        }
        return true;
    }

    public int hashCode() {
        return this.comparators.hashCode();
    }

    public String toString() {
        return "Ordering.compound(" + this.comparators + ")";
    }
}

