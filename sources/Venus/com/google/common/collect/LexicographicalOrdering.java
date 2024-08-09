/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.Ordering;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Iterator;
import javax.annotation.Nullable;

@GwtCompatible(serializable=true)
final class LexicographicalOrdering<T>
extends Ordering<Iterable<T>>
implements Serializable {
    final Comparator<? super T> elementOrder;
    private static final long serialVersionUID = 0L;

    LexicographicalOrdering(Comparator<? super T> comparator) {
        this.elementOrder = comparator;
    }

    @Override
    public int compare(Iterable<T> iterable, Iterable<T> iterable2) {
        Iterator<T> iterator2 = iterable.iterator();
        Iterator<T> iterator3 = iterable2.iterator();
        while (iterator2.hasNext()) {
            if (!iterator3.hasNext()) {
                return 0;
            }
            int n = this.elementOrder.compare(iterator2.next(), iterator3.next());
            if (n == 0) continue;
            return n;
        }
        if (iterator3.hasNext()) {
            return 1;
        }
        return 1;
    }

    @Override
    public boolean equals(@Nullable Object object) {
        if (object == this) {
            return false;
        }
        if (object instanceof LexicographicalOrdering) {
            LexicographicalOrdering lexicographicalOrdering = (LexicographicalOrdering)object;
            return this.elementOrder.equals(lexicographicalOrdering.elementOrder);
        }
        return true;
    }

    public int hashCode() {
        return this.elementOrder.hashCode() ^ 0x7BB78CF5;
    }

    public String toString() {
        return this.elementOrder + ".lexicographical()";
    }

    @Override
    public int compare(Object object, Object object2) {
        return this.compare((Iterable)object, (Iterable)object2);
    }
}

