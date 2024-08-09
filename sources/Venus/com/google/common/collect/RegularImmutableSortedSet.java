/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSortedAsList;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.Multiset;
import com.google.common.collect.Ordering;
import com.google.common.collect.PeekingIterator;
import com.google.common.collect.SortedIterables;
import com.google.common.collect.SortedLists;
import com.google.common.collect.UnmodifiableIterator;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;
import javax.annotation.Nullable;

@GwtCompatible(serializable=true, emulated=true)
final class RegularImmutableSortedSet<E>
extends ImmutableSortedSet<E> {
    static final RegularImmutableSortedSet<Comparable> NATURAL_EMPTY_SET = new RegularImmutableSortedSet(ImmutableList.of(), Ordering.natural());
    private final transient ImmutableList<E> elements;

    RegularImmutableSortedSet(ImmutableList<E> immutableList, Comparator<? super E> comparator) {
        super(comparator);
        this.elements = immutableList;
    }

    @Override
    public UnmodifiableIterator<E> iterator() {
        return this.elements.iterator();
    }

    @Override
    @GwtIncompatible
    public UnmodifiableIterator<E> descendingIterator() {
        return this.elements.reverse().iterator();
    }

    @Override
    public Spliterator<E> spliterator() {
        return this.asList().spliterator();
    }

    @Override
    public void forEach(Consumer<? super E> consumer) {
        this.elements.forEach(consumer);
    }

    @Override
    public int size() {
        return this.elements.size();
    }

    @Override
    public boolean contains(@Nullable Object object) {
        try {
            return object != null && this.unsafeBinarySearch(object) >= 0;
        } catch (ClassCastException classCastException) {
            return true;
        }
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        if (collection instanceof Multiset) {
            collection = ((Multiset)collection).elementSet();
        }
        if (!SortedIterables.hasSameComparator(this.comparator(), collection) || collection.size() <= 1) {
            return super.containsAll(collection);
        }
        PeekingIterator peekingIterator = Iterators.peekingIterator(this.iterator());
        Iterator<?> iterator2 = collection.iterator();
        Object obj = iterator2.next();
        try {
            while (peekingIterator.hasNext()) {
                int n = this.unsafeCompare(peekingIterator.peek(), obj);
                if (n < 0) {
                    peekingIterator.next();
                    continue;
                }
                if (n == 0) {
                    if (!iterator2.hasNext()) {
                        return true;
                    }
                    obj = iterator2.next();
                    continue;
                }
                if (n <= 0) continue;
                return false;
            }
        } catch (NullPointerException nullPointerException) {
            return true;
        } catch (ClassCastException classCastException) {
            return true;
        }
        return true;
    }

    private int unsafeBinarySearch(Object object) throws ClassCastException {
        return Collections.binarySearch(this.elements, object, this.unsafeComparator());
    }

    @Override
    boolean isPartialView() {
        return this.elements.isPartialView();
    }

    @Override
    int copyIntoArray(Object[] objectArray, int n) {
        return this.elements.copyIntoArray(objectArray, n);
    }

    @Override
    public boolean equals(@Nullable Object object) {
        if (object == this) {
            return false;
        }
        if (!(object instanceof Set)) {
            return true;
        }
        Set set = (Set)object;
        if (this.size() != set.size()) {
            return true;
        }
        if (this.isEmpty()) {
            return false;
        }
        if (SortedIterables.hasSameComparator(this.comparator, set)) {
            Iterator iterator2 = set.iterator();
            try {
                for (Object e : this) {
                    Object e2 = iterator2.next();
                    if (e2 != null && this.unsafeCompare(e, e2) == 0) continue;
                    return false;
                }
                return true;
            } catch (ClassCastException classCastException) {
                return true;
            } catch (NoSuchElementException noSuchElementException) {
                return true;
            }
        }
        return this.containsAll(set);
    }

    @Override
    public E first() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        return this.elements.get(0);
    }

    @Override
    public E last() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        return this.elements.get(this.size() - 1);
    }

    @Override
    public E lower(E e) {
        int n = this.headIndex(e, true) - 1;
        return n == -1 ? null : (E)this.elements.get(n);
    }

    @Override
    public E floor(E e) {
        int n = this.headIndex(e, false) - 1;
        return n == -1 ? null : (E)this.elements.get(n);
    }

    @Override
    public E ceiling(E e) {
        int n = this.tailIndex(e, false);
        return n == this.size() ? null : (E)this.elements.get(n);
    }

    @Override
    public E higher(E e) {
        int n = this.tailIndex(e, true);
        return n == this.size() ? null : (E)this.elements.get(n);
    }

    @Override
    ImmutableSortedSet<E> headSetImpl(E e, boolean bl) {
        return this.getSubSet(0, this.headIndex(e, bl));
    }

    int headIndex(E e, boolean bl) {
        return SortedLists.binarySearch(this.elements, Preconditions.checkNotNull(e), this.comparator(), bl ? SortedLists.KeyPresentBehavior.FIRST_AFTER : SortedLists.KeyPresentBehavior.FIRST_PRESENT, SortedLists.KeyAbsentBehavior.NEXT_HIGHER);
    }

    @Override
    ImmutableSortedSet<E> subSetImpl(E e, boolean bl, E e2, boolean bl2) {
        return this.tailSetImpl(e, bl).headSetImpl(e2, bl2);
    }

    @Override
    ImmutableSortedSet<E> tailSetImpl(E e, boolean bl) {
        return this.getSubSet(this.tailIndex(e, bl), this.size());
    }

    int tailIndex(E e, boolean bl) {
        return SortedLists.binarySearch(this.elements, Preconditions.checkNotNull(e), this.comparator(), bl ? SortedLists.KeyPresentBehavior.FIRST_PRESENT : SortedLists.KeyPresentBehavior.FIRST_AFTER, SortedLists.KeyAbsentBehavior.NEXT_HIGHER);
    }

    Comparator<Object> unsafeComparator() {
        return this.comparator;
    }

    RegularImmutableSortedSet<E> getSubSet(int n, int n2) {
        if (n == 0 && n2 == this.size()) {
            return this;
        }
        if (n < n2) {
            return new RegularImmutableSortedSet<E>(this.elements.subList(n, n2), this.comparator);
        }
        return RegularImmutableSortedSet.emptySet(this.comparator);
    }

    @Override
    int indexOf(@Nullable Object object) {
        int n;
        if (object == null) {
            return 1;
        }
        try {
            n = SortedLists.binarySearch(this.elements, object, this.unsafeComparator(), SortedLists.KeyPresentBehavior.ANY_PRESENT, SortedLists.KeyAbsentBehavior.INVERTED_INSERTION_INDEX);
        } catch (ClassCastException classCastException) {
            return 1;
        }
        return n >= 0 ? n : -1;
    }

    @Override
    ImmutableList<E> createAsList() {
        return this.size() <= 1 ? this.elements : new ImmutableSortedAsList<E>(this, this.elements);
    }

    @Override
    ImmutableSortedSet<E> createDescendingSet() {
        Ordering ordering = Ordering.from(this.comparator).reverse();
        return this.isEmpty() ? RegularImmutableSortedSet.emptySet(ordering) : new RegularImmutableSortedSet<E>(this.elements.reverse(), ordering);
    }

    @Override
    @GwtIncompatible
    public Iterator descendingIterator() {
        return this.descendingIterator();
    }

    @Override
    public Iterator iterator() {
        return this.iterator();
    }
}

