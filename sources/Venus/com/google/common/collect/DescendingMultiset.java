/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.BoundType;
import com.google.common.collect.ForwardingMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import com.google.common.collect.Ordering;
import com.google.common.collect.SortedMultiset;
import com.google.common.collect.SortedMultisets;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedSet;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@GwtCompatible(emulated=true)
abstract class DescendingMultiset<E>
extends ForwardingMultiset<E>
implements SortedMultiset<E> {
    private transient Comparator<? super E> comparator;
    private transient NavigableSet<E> elementSet;
    private transient Set<Multiset.Entry<E>> entrySet;

    DescendingMultiset() {
    }

    abstract SortedMultiset<E> forwardMultiset();

    @Override
    public Comparator<? super E> comparator() {
        Comparator<? super E> comparator = this.comparator;
        if (comparator == null) {
            this.comparator = Ordering.from(this.forwardMultiset().comparator()).reverse();
            return this.comparator;
        }
        return comparator;
    }

    @Override
    public NavigableSet<E> elementSet() {
        NavigableSet<E> navigableSet = this.elementSet;
        if (navigableSet == null) {
            this.elementSet = new SortedMultisets.NavigableElementSet(this);
            return this.elementSet;
        }
        return navigableSet;
    }

    @Override
    public Multiset.Entry<E> pollFirstEntry() {
        return this.forwardMultiset().pollLastEntry();
    }

    @Override
    public Multiset.Entry<E> pollLastEntry() {
        return this.forwardMultiset().pollFirstEntry();
    }

    @Override
    public SortedMultiset<E> headMultiset(E e, BoundType boundType) {
        return this.forwardMultiset().tailMultiset(e, boundType).descendingMultiset();
    }

    @Override
    public SortedMultiset<E> subMultiset(E e, BoundType boundType, E e2, BoundType boundType2) {
        return this.forwardMultiset().subMultiset(e2, boundType2, e, boundType).descendingMultiset();
    }

    @Override
    public SortedMultiset<E> tailMultiset(E e, BoundType boundType) {
        return this.forwardMultiset().headMultiset(e, boundType).descendingMultiset();
    }

    @Override
    protected Multiset<E> delegate() {
        return this.forwardMultiset();
    }

    @Override
    public SortedMultiset<E> descendingMultiset() {
        return this.forwardMultiset();
    }

    @Override
    public Multiset.Entry<E> firstEntry() {
        return this.forwardMultiset().lastEntry();
    }

    @Override
    public Multiset.Entry<E> lastEntry() {
        return this.forwardMultiset().firstEntry();
    }

    abstract Iterator<Multiset.Entry<E>> entryIterator();

    @Override
    public Set<Multiset.Entry<E>> entrySet() {
        Set<Multiset.Entry<Multiset.Entry<E>>> set = this.entrySet;
        return set == null ? (this.entrySet = this.createEntrySet()) : set;
    }

    Set<Multiset.Entry<E>> createEntrySet() {
        class EntrySetImpl
        extends Multisets.EntrySet<E> {
            final DescendingMultiset this$0;

            EntrySetImpl(DescendingMultiset descendingMultiset) {
                this.this$0 = descendingMultiset;
            }

            @Override
            Multiset<E> multiset() {
                return this.this$0;
            }

            @Override
            public Iterator<Multiset.Entry<E>> iterator() {
                return this.this$0.entryIterator();
            }

            @Override
            public int size() {
                return this.this$0.forwardMultiset().entrySet().size();
            }
        }
        return new EntrySetImpl(this);
    }

    @Override
    public Iterator<E> iterator() {
        return Multisets.iteratorImpl(this);
    }

    @Override
    public Object[] toArray() {
        return this.standardToArray();
    }

    @Override
    public <T> T[] toArray(T[] TArray) {
        return this.standardToArray(TArray);
    }

    @Override
    public String toString() {
        return this.entrySet().toString();
    }

    @Override
    public Set elementSet() {
        return this.elementSet();
    }

    @Override
    protected Collection delegate() {
        return this.delegate();
    }

    @Override
    protected Object delegate() {
        return this.delegate();
    }

    @Override
    public SortedSet elementSet() {
        return this.elementSet();
    }
}

