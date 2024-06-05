package com.ibm.icu.impl;

import java.util.*;

public class IterableComparator<T> implements Comparator<Iterable<T>>
{
    private final Comparator<T> comparator;
    private final int shorterFirst;
    private static final IterableComparator NOCOMPARATOR;
    
    public IterableComparator() {
        this(null, true);
    }
    
    public IterableComparator(final Comparator<T> comparator) {
        this(comparator, true);
    }
    
    public IterableComparator(final Comparator<T> comparator, final boolean shorterFirst) {
        this.comparator = comparator;
        this.shorterFirst = (shorterFirst ? 1 : -1);
    }
    
    @Override
    public int compare(final Iterable<T> a, final Iterable<T> b) {
        if (a == null) {
            return (b == null) ? 0 : (-this.shorterFirst);
        }
        if (b == null) {
            return this.shorterFirst;
        }
        final Iterator<T> ai = a.iterator();
        final Iterator<T> bi = b.iterator();
        while (ai.hasNext()) {
            if (!bi.hasNext()) {
                return this.shorterFirst;
            }
            final T aItem = ai.next();
            final T bItem = bi.next();
            final int result = (this.comparator != null) ? this.comparator.compare(aItem, bItem) : ((Comparable)aItem).compareTo(bItem);
            if (result != 0) {
                return result;
            }
        }
        return bi.hasNext() ? (-this.shorterFirst) : 0;
    }
    
    public static <T> int compareIterables(final Iterable<T> a, final Iterable<T> b) {
        return IterableComparator.NOCOMPARATOR.compare(a, b);
    }
    
    static {
        NOCOMPARATOR = new IterableComparator();
    }
}
