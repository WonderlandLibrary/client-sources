/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.BoundType;
import com.google.common.collect.DescendingMultiset;
import com.google.common.collect.ForwardingMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
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
@Beta
@GwtCompatible(emulated=true)
public abstract class ForwardingSortedMultiset<E>
extends ForwardingMultiset<E>
implements SortedMultiset<E> {
    protected ForwardingSortedMultiset() {
    }

    @Override
    protected abstract SortedMultiset<E> delegate();

    @Override
    public NavigableSet<E> elementSet() {
        return this.delegate().elementSet();
    }

    @Override
    public Comparator<? super E> comparator() {
        return this.delegate().comparator();
    }

    @Override
    public SortedMultiset<E> descendingMultiset() {
        return this.delegate().descendingMultiset();
    }

    @Override
    public Multiset.Entry<E> firstEntry() {
        return this.delegate().firstEntry();
    }

    protected Multiset.Entry<E> standardFirstEntry() {
        Iterator iterator2 = this.entrySet().iterator();
        if (!iterator2.hasNext()) {
            return null;
        }
        Multiset.Entry entry = iterator2.next();
        return Multisets.immutableEntry(entry.getElement(), entry.getCount());
    }

    @Override
    public Multiset.Entry<E> lastEntry() {
        return this.delegate().lastEntry();
    }

    protected Multiset.Entry<E> standardLastEntry() {
        Iterator<Multiset.Entry<E>> iterator2 = this.descendingMultiset().entrySet().iterator();
        if (!iterator2.hasNext()) {
            return null;
        }
        Multiset.Entry<E> entry = iterator2.next();
        return Multisets.immutableEntry(entry.getElement(), entry.getCount());
    }

    @Override
    public Multiset.Entry<E> pollFirstEntry() {
        return this.delegate().pollFirstEntry();
    }

    protected Multiset.Entry<E> standardPollFirstEntry() {
        Iterator iterator2 = this.entrySet().iterator();
        if (!iterator2.hasNext()) {
            return null;
        }
        Multiset.Entry entry = iterator2.next();
        entry = Multisets.immutableEntry(entry.getElement(), entry.getCount());
        iterator2.remove();
        return entry;
    }

    @Override
    public Multiset.Entry<E> pollLastEntry() {
        return this.delegate().pollLastEntry();
    }

    protected Multiset.Entry<E> standardPollLastEntry() {
        Iterator<Multiset.Entry<E>> iterator2 = this.descendingMultiset().entrySet().iterator();
        if (!iterator2.hasNext()) {
            return null;
        }
        Multiset.Entry<E> entry = iterator2.next();
        entry = Multisets.immutableEntry(entry.getElement(), entry.getCount());
        iterator2.remove();
        return entry;
    }

    @Override
    public SortedMultiset<E> headMultiset(E e, BoundType boundType) {
        return this.delegate().headMultiset(e, boundType);
    }

    @Override
    public SortedMultiset<E> subMultiset(E e, BoundType boundType, E e2, BoundType boundType2) {
        return this.delegate().subMultiset(e, boundType, e2, boundType2);
    }

    protected SortedMultiset<E> standardSubMultiset(E e, BoundType boundType, E e2, BoundType boundType2) {
        return this.tailMultiset(e, boundType).headMultiset(e2, boundType2);
    }

    @Override
    public SortedMultiset<E> tailMultiset(E e, BoundType boundType) {
        return this.delegate().tailMultiset(e, boundType);
    }

    @Override
    public Set elementSet() {
        return this.elementSet();
    }

    @Override
    protected Multiset delegate() {
        return this.delegate();
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

    protected abstract class StandardDescendingMultiset
    extends DescendingMultiset<E> {
        final ForwardingSortedMultiset this$0;

        public StandardDescendingMultiset(ForwardingSortedMultiset forwardingSortedMultiset) {
            this.this$0 = forwardingSortedMultiset;
        }

        @Override
        SortedMultiset<E> forwardMultiset() {
            return this.this$0;
        }
    }

    protected class StandardElementSet
    extends SortedMultisets.NavigableElementSet<E> {
        final ForwardingSortedMultiset this$0;

        public StandardElementSet(ForwardingSortedMultiset forwardingSortedMultiset) {
            this.this$0 = forwardingSortedMultiset;
            super(forwardingSortedMultiset);
        }
    }
}

