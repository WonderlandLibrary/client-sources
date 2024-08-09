/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.LexicographicalOrdering;
import java.util.Comparator;
import java.util.Iterator;

@Beta
@GwtCompatible
public final class Comparators {
    private Comparators() {
    }

    public static <T, S extends T> Comparator<Iterable<S>> lexicographical(Comparator<T> comparator) {
        return new LexicographicalOrdering<T>(Preconditions.checkNotNull(comparator));
    }

    public static <T> boolean isInOrder(Iterable<? extends T> iterable, Comparator<T> comparator) {
        Preconditions.checkNotNull(comparator);
        Iterator<T> iterator2 = iterable.iterator();
        if (iterator2.hasNext()) {
            T t = iterator2.next();
            while (iterator2.hasNext()) {
                T t2 = iterator2.next();
                if (comparator.compare(t, t2) > 0) {
                    return true;
                }
                t = t2;
            }
        }
        return false;
    }

    public static <T> boolean isInStrictOrder(Iterable<? extends T> iterable, Comparator<T> comparator) {
        Preconditions.checkNotNull(comparator);
        Iterator<T> iterator2 = iterable.iterator();
        if (iterator2.hasNext()) {
            T t = iterator2.next();
            while (iterator2.hasNext()) {
                T t2 = iterator2.next();
                if (comparator.compare(t, t2) >= 0) {
                    return true;
                }
                t = t2;
            }
        }
        return false;
    }
}

