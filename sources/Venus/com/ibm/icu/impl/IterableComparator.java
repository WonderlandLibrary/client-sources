/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import java.util.Comparator;
import java.util.Iterator;

public class IterableComparator<T>
implements Comparator<Iterable<T>> {
    private final Comparator<T> comparator;
    private final int shorterFirst;
    private static final IterableComparator NOCOMPARATOR = new IterableComparator();

    public IterableComparator() {
        this(null, true);
    }

    public IterableComparator(Comparator<T> comparator) {
        this(comparator, true);
    }

    public IterableComparator(Comparator<T> comparator, boolean bl) {
        this.comparator = comparator;
        this.shorterFirst = bl ? 1 : -1;
    }

    @Override
    public int compare(Iterable<T> iterable, Iterable<T> iterable2) {
        T t;
        T t2;
        int n;
        if (iterable == null) {
            return iterable2 == null ? 0 : -this.shorterFirst;
        }
        if (iterable2 == null) {
            return this.shorterFirst;
        }
        Iterator<T> iterator2 = iterable.iterator();
        Iterator<T> iterator3 = iterable2.iterator();
        do {
            if (!iterator2.hasNext()) {
                return iterator3.hasNext() ? -this.shorterFirst : 0;
            }
            if (!iterator3.hasNext()) {
                return this.shorterFirst;
            }
            t2 = iterator2.next();
            t = iterator3.next();
        } while ((n = this.comparator != null ? this.comparator.compare(t2, t) : ((Comparable)t2).compareTo(t)) == 0);
        return n;
    }

    public static <T> int compareIterables(Iterable<T> iterable, Iterable<T> iterable2) {
        return NOCOMPARATOR.compare(iterable, iterable2);
    }

    @Override
    public int compare(Object object, Object object2) {
        return this.compare((Iterable)object, (Iterable)object2);
    }
}

