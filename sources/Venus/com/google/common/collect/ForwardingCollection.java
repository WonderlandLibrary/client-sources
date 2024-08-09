/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Objects;
import com.google.common.collect.Collections2;
import com.google.common.collect.ForwardingObject;
import com.google.common.collect.Iterators;
import com.google.common.collect.ObjectArrays;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Collection;
import java.util.Iterator;
import javax.annotation.Nullable;

@GwtCompatible
public abstract class ForwardingCollection<E>
extends ForwardingObject
implements Collection<E> {
    protected ForwardingCollection() {
    }

    @Override
    protected abstract Collection<E> delegate();

    @Override
    public Iterator<E> iterator() {
        return this.delegate().iterator();
    }

    @Override
    public int size() {
        return this.delegate().size();
    }

    @Override
    @CanIgnoreReturnValue
    public boolean removeAll(Collection<?> collection) {
        return this.delegate().removeAll(collection);
    }

    @Override
    public boolean isEmpty() {
        return this.delegate().isEmpty();
    }

    @Override
    public boolean contains(Object object) {
        return this.delegate().contains(object);
    }

    @Override
    @CanIgnoreReturnValue
    public boolean add(E e) {
        return this.delegate().add(e);
    }

    @Override
    @CanIgnoreReturnValue
    public boolean remove(Object object) {
        return this.delegate().remove(object);
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return this.delegate().containsAll(collection);
    }

    @Override
    @CanIgnoreReturnValue
    public boolean addAll(Collection<? extends E> collection) {
        return this.delegate().addAll(collection);
    }

    @Override
    @CanIgnoreReturnValue
    public boolean retainAll(Collection<?> collection) {
        return this.delegate().retainAll(collection);
    }

    @Override
    public void clear() {
        this.delegate().clear();
    }

    @Override
    public Object[] toArray() {
        return this.delegate().toArray();
    }

    @Override
    @CanIgnoreReturnValue
    public <T> T[] toArray(T[] TArray) {
        return this.delegate().toArray(TArray);
    }

    protected boolean standardContains(@Nullable Object object) {
        return Iterators.contains(this.iterator(), object);
    }

    protected boolean standardContainsAll(Collection<?> collection) {
        return Collections2.containsAllImpl(this, collection);
    }

    protected boolean standardAddAll(Collection<? extends E> collection) {
        return Iterators.addAll(this, collection.iterator());
    }

    protected boolean standardRemove(@Nullable Object object) {
        Iterator<E> iterator2 = this.iterator();
        while (iterator2.hasNext()) {
            if (!Objects.equal(iterator2.next(), object)) continue;
            iterator2.remove();
            return false;
        }
        return true;
    }

    protected boolean standardRemoveAll(Collection<?> collection) {
        return Iterators.removeAll(this.iterator(), collection);
    }

    protected boolean standardRetainAll(Collection<?> collection) {
        return Iterators.retainAll(this.iterator(), collection);
    }

    protected void standardClear() {
        Iterators.clear(this.iterator());
    }

    protected boolean standardIsEmpty() {
        return !this.iterator().hasNext();
    }

    protected String standardToString() {
        return Collections2.toStringImpl(this);
    }

    protected Object[] standardToArray() {
        Object[] objectArray = new Object[this.size()];
        return this.toArray(objectArray);
    }

    protected <T> T[] standardToArray(T[] TArray) {
        return ObjectArrays.toArrayImpl(this, TArray);
    }

    @Override
    protected Object delegate() {
        return this.delegate();
    }
}

