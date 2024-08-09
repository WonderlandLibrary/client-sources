/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractMultiset;
import com.google.common.collect.BoundType;
import com.google.common.collect.DescendingMultiset;
import com.google.common.collect.GwtTransient;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import com.google.common.collect.Ordering;
import com.google.common.collect.SortedMultiset;
import com.google.common.collect.SortedMultisets;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedSet;
import javax.annotation.Nullable;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@GwtCompatible(emulated=true)
abstract class AbstractSortedMultiset<E>
extends AbstractMultiset<E>
implements SortedMultiset<E> {
    @GwtTransient
    final Comparator<? super E> comparator;
    private transient SortedMultiset<E> descendingMultiset;

    AbstractSortedMultiset() {
        this(Ordering.natural());
    }

    AbstractSortedMultiset(Comparator<? super E> comparator) {
        this.comparator = Preconditions.checkNotNull(comparator);
    }

    @Override
    public NavigableSet<E> elementSet() {
        return (NavigableSet)super.elementSet();
    }

    @Override
    NavigableSet<E> createElementSet() {
        return new SortedMultisets.NavigableElementSet(this);
    }

    @Override
    public Comparator<? super E> comparator() {
        return this.comparator;
    }

    @Override
    public Multiset.Entry<E> firstEntry() {
        Iterator iterator2 = this.entryIterator();
        return iterator2.hasNext() ? iterator2.next() : null;
    }

    @Override
    public Multiset.Entry<E> lastEntry() {
        Iterator<Multiset.Entry<E>> iterator2 = this.descendingEntryIterator();
        return iterator2.hasNext() ? iterator2.next() : null;
    }

    @Override
    public Multiset.Entry<E> pollFirstEntry() {
        Iterator iterator2 = this.entryIterator();
        if (iterator2.hasNext()) {
            Multiset.Entry entry = iterator2.next();
            entry = Multisets.immutableEntry(entry.getElement(), entry.getCount());
            iterator2.remove();
            return entry;
        }
        return null;
    }

    @Override
    public Multiset.Entry<E> pollLastEntry() {
        Iterator<Multiset.Entry<E>> iterator2 = this.descendingEntryIterator();
        if (iterator2.hasNext()) {
            Multiset.Entry<E> entry = iterator2.next();
            entry = Multisets.immutableEntry(entry.getElement(), entry.getCount());
            iterator2.remove();
            return entry;
        }
        return null;
    }

    @Override
    public SortedMultiset<E> subMultiset(@Nullable E e, BoundType boundType, @Nullable E e2, BoundType boundType2) {
        Preconditions.checkNotNull(boundType);
        Preconditions.checkNotNull(boundType2);
        return this.tailMultiset(e, boundType).headMultiset(e2, boundType2);
    }

    abstract Iterator<Multiset.Entry<E>> descendingEntryIterator();

    Iterator<E> descendingIterator() {
        return Multisets.iteratorImpl(this.descendingMultiset());
    }

    @Override
    public SortedMultiset<E> descendingMultiset() {
        SortedMultiset<E> sortedMultiset = this.descendingMultiset;
        return sortedMultiset == null ? (this.descendingMultiset = this.createDescendingMultiset()) : sortedMultiset;
    }

    SortedMultiset<E> createDescendingMultiset() {
        class DescendingMultisetImpl
        extends DescendingMultiset<E> {
            final AbstractSortedMultiset this$0;

            DescendingMultisetImpl(AbstractSortedMultiset abstractSortedMultiset) {
                this.this$0 = abstractSortedMultiset;
            }

            @Override
            SortedMultiset<E> forwardMultiset() {
                return this.this$0;
            }

            @Override
            Iterator<Multiset.Entry<E>> entryIterator() {
                return this.this$0.descendingEntryIterator();
            }

            @Override
            public Iterator<E> iterator() {
                return this.this$0.descendingIterator();
            }
        }
        return new DescendingMultisetImpl(this);
    }

    @Override
    Set createElementSet() {
        return this.createElementSet();
    }

    @Override
    public Set elementSet() {
        return this.elementSet();
    }

    @Override
    public SortedSet elementSet() {
        return this.elementSet();
    }
}

