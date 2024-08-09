/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.ForwardingCollection;
import com.google.common.collect.Lists;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import javax.annotation.Nullable;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@GwtCompatible
public abstract class ForwardingList<E>
extends ForwardingCollection<E>
implements List<E> {
    protected ForwardingList() {
    }

    @Override
    protected abstract List<E> delegate();

    @Override
    public void add(int n, E e) {
        this.delegate().add(n, e);
    }

    @Override
    @CanIgnoreReturnValue
    public boolean addAll(int n, Collection<? extends E> collection) {
        return this.delegate().addAll(n, collection);
    }

    @Override
    public E get(int n) {
        return this.delegate().get(n);
    }

    @Override
    public int indexOf(Object object) {
        return this.delegate().indexOf(object);
    }

    @Override
    public int lastIndexOf(Object object) {
        return this.delegate().lastIndexOf(object);
    }

    @Override
    public ListIterator<E> listIterator() {
        return this.delegate().listIterator();
    }

    @Override
    public ListIterator<E> listIterator(int n) {
        return this.delegate().listIterator(n);
    }

    @Override
    @CanIgnoreReturnValue
    public E remove(int n) {
        return this.delegate().remove(n);
    }

    @Override
    @CanIgnoreReturnValue
    public E set(int n, E e) {
        return this.delegate().set(n, e);
    }

    @Override
    public List<E> subList(int n, int n2) {
        return this.delegate().subList(n, n2);
    }

    @Override
    public boolean equals(@Nullable Object object) {
        return object == this || this.delegate().equals(object);
    }

    @Override
    public int hashCode() {
        return this.delegate().hashCode();
    }

    protected boolean standardAdd(E e) {
        this.add(this.size(), e);
        return false;
    }

    protected boolean standardAddAll(int n, Iterable<? extends E> iterable) {
        return Lists.addAllImpl(this, n, iterable);
    }

    protected int standardIndexOf(@Nullable Object object) {
        return Lists.indexOfImpl(this, object);
    }

    protected int standardLastIndexOf(@Nullable Object object) {
        return Lists.lastIndexOfImpl(this, object);
    }

    protected Iterator<E> standardIterator() {
        return this.listIterator();
    }

    protected ListIterator<E> standardListIterator() {
        return this.listIterator(0);
    }

    @Beta
    protected ListIterator<E> standardListIterator(int n) {
        return Lists.listIteratorImpl(this, n);
    }

    @Beta
    protected List<E> standardSubList(int n, int n2) {
        return Lists.subListImpl(this, n, n2);
    }

    @Beta
    protected boolean standardEquals(@Nullable Object object) {
        return Lists.equalsImpl(this, object);
    }

    @Beta
    protected int standardHashCode() {
        return Lists.hashCodeImpl(this);
    }

    @Override
    protected Collection delegate() {
        return this.delegate();
    }

    @Override
    protected Object delegate() {
        return this.delegate();
    }
}

