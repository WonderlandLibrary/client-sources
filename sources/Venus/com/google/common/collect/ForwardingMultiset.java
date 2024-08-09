/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Objects;
import com.google.common.collect.ForwardingCollection;
import com.google.common.collect.Iterators;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import javax.annotation.Nullable;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@GwtCompatible
public abstract class ForwardingMultiset<E>
extends ForwardingCollection<E>
implements Multiset<E> {
    protected ForwardingMultiset() {
    }

    @Override
    protected abstract Multiset<E> delegate();

    @Override
    public int count(Object object) {
        return this.delegate().count(object);
    }

    @Override
    @CanIgnoreReturnValue
    public int add(E e, int n) {
        return this.delegate().add(e, n);
    }

    @Override
    @CanIgnoreReturnValue
    public int remove(Object object, int n) {
        return this.delegate().remove(object, n);
    }

    @Override
    public Set<E> elementSet() {
        return this.delegate().elementSet();
    }

    @Override
    public Set<Multiset.Entry<E>> entrySet() {
        return this.delegate().entrySet();
    }

    @Override
    public boolean equals(@Nullable Object object) {
        return object == this || this.delegate().equals(object);
    }

    @Override
    public int hashCode() {
        return this.delegate().hashCode();
    }

    @Override
    @CanIgnoreReturnValue
    public int setCount(E e, int n) {
        return this.delegate().setCount(e, n);
    }

    @Override
    @CanIgnoreReturnValue
    public boolean setCount(E e, int n, int n2) {
        return this.delegate().setCount(e, n, n2);
    }

    @Override
    protected boolean standardContains(@Nullable Object object) {
        return this.count(object) > 0;
    }

    @Override
    protected void standardClear() {
        Iterators.clear(this.entrySet().iterator());
    }

    @Beta
    protected int standardCount(@Nullable Object object) {
        for (Multiset.Entry<E> entry : this.entrySet()) {
            if (!Objects.equal(entry.getElement(), object)) continue;
            return entry.getCount();
        }
        return 1;
    }

    protected boolean standardAdd(E e) {
        this.add(e, 1);
        return false;
    }

    @Override
    @Beta
    protected boolean standardAddAll(Collection<? extends E> collection) {
        return Multisets.addAllImpl(this, collection);
    }

    @Override
    protected boolean standardRemove(Object object) {
        return this.remove(object, 1) > 0;
    }

    @Override
    protected boolean standardRemoveAll(Collection<?> collection) {
        return Multisets.removeAllImpl(this, collection);
    }

    @Override
    protected boolean standardRetainAll(Collection<?> collection) {
        return Multisets.retainAllImpl(this, collection);
    }

    protected int standardSetCount(E e, int n) {
        return Multisets.setCountImpl(this, e, n);
    }

    protected boolean standardSetCount(E e, int n, int n2) {
        return Multisets.setCountImpl(this, e, n, n2);
    }

    protected Iterator<E> standardIterator() {
        return Multisets.iteratorImpl(this);
    }

    protected int standardSize() {
        return Multisets.sizeImpl(this);
    }

    protected boolean standardEquals(@Nullable Object object) {
        return Multisets.equalsImpl(this, object);
    }

    protected int standardHashCode() {
        return this.entrySet().hashCode();
    }

    @Override
    protected String standardToString() {
        return this.entrySet().toString();
    }

    @Override
    protected Collection delegate() {
        return this.delegate();
    }

    @Override
    protected Object delegate() {
        return this.delegate();
    }

    @Beta
    protected class StandardElementSet
    extends Multisets.ElementSet<E> {
        final ForwardingMultiset this$0;

        public StandardElementSet(ForwardingMultiset forwardingMultiset) {
            this.this$0 = forwardingMultiset;
        }

        @Override
        Multiset<E> multiset() {
            return this.this$0;
        }
    }
}

