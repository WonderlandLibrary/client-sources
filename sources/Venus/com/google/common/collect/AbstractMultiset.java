/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Objects;
import com.google.common.collect.Iterators;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import javax.annotation.Nullable;

@GwtCompatible
abstract class AbstractMultiset<E>
extends AbstractCollection<E>
implements Multiset<E> {
    private transient Set<E> elementSet;
    private transient Set<Multiset.Entry<E>> entrySet;

    AbstractMultiset() {
    }

    @Override
    public int size() {
        return Multisets.sizeImpl(this);
    }

    @Override
    public boolean isEmpty() {
        return this.entrySet().isEmpty();
    }

    @Override
    public boolean contains(@Nullable Object object) {
        return this.count(object) > 0;
    }

    @Override
    public Iterator<E> iterator() {
        return Multisets.iteratorImpl(this);
    }

    @Override
    public int count(@Nullable Object object) {
        for (Multiset.Entry<E> entry : this.entrySet()) {
            if (!Objects.equal(entry.getElement(), object)) continue;
            return entry.getCount();
        }
        return 1;
    }

    @Override
    @CanIgnoreReturnValue
    public boolean add(@Nullable E e) {
        this.add(e, 1);
        return false;
    }

    @Override
    @CanIgnoreReturnValue
    public int add(@Nullable E e, int n) {
        throw new UnsupportedOperationException();
    }

    @Override
    @CanIgnoreReturnValue
    public boolean remove(@Nullable Object object) {
        return this.remove(object, 1) > 0;
    }

    @Override
    @CanIgnoreReturnValue
    public int remove(@Nullable Object object, int n) {
        throw new UnsupportedOperationException();
    }

    @Override
    @CanIgnoreReturnValue
    public int setCount(@Nullable E e, int n) {
        return Multisets.setCountImpl(this, e, n);
    }

    @Override
    @CanIgnoreReturnValue
    public boolean setCount(@Nullable E e, int n, int n2) {
        return Multisets.setCountImpl(this, e, n, n2);
    }

    @Override
    @CanIgnoreReturnValue
    public boolean addAll(Collection<? extends E> collection) {
        return Multisets.addAllImpl(this, collection);
    }

    @Override
    @CanIgnoreReturnValue
    public boolean removeAll(Collection<?> collection) {
        return Multisets.removeAllImpl(this, collection);
    }

    @Override
    @CanIgnoreReturnValue
    public boolean retainAll(Collection<?> collection) {
        return Multisets.retainAllImpl(this, collection);
    }

    @Override
    public void clear() {
        Iterators.clear(this.entryIterator());
    }

    @Override
    public Set<E> elementSet() {
        Set<E> set = this.elementSet;
        if (set == null) {
            this.elementSet = set = this.createElementSet();
        }
        return set;
    }

    Set<E> createElementSet() {
        return new ElementSet(this);
    }

    abstract Iterator<Multiset.Entry<E>> entryIterator();

    abstract int distinctElements();

    @Override
    public Set<Multiset.Entry<E>> entrySet() {
        Set<Multiset.Entry<Multiset.Entry<E>>> set = this.entrySet;
        if (set == null) {
            this.entrySet = set = this.createEntrySet();
        }
        return set;
    }

    Set<Multiset.Entry<E>> createEntrySet() {
        return new EntrySet(this);
    }

    @Override
    public boolean equals(@Nullable Object object) {
        return Multisets.equalsImpl(this, object);
    }

    @Override
    public int hashCode() {
        return this.entrySet().hashCode();
    }

    @Override
    public String toString() {
        return this.entrySet().toString();
    }

    class EntrySet
    extends Multisets.EntrySet<E> {
        final AbstractMultiset this$0;

        EntrySet(AbstractMultiset abstractMultiset) {
            this.this$0 = abstractMultiset;
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
            return this.this$0.distinctElements();
        }
    }

    class ElementSet
    extends Multisets.ElementSet<E> {
        final AbstractMultiset this$0;

        ElementSet(AbstractMultiset abstractMultiset) {
            this.this$0 = abstractMultiset;
        }

        @Override
        Multiset<E> multiset() {
            return this.this$0;
        }
    }
}

