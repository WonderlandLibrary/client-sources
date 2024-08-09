/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.ForwardingCollection;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Queue;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@GwtCompatible
public abstract class ForwardingQueue<E>
extends ForwardingCollection<E>
implements Queue<E> {
    protected ForwardingQueue() {
    }

    @Override
    protected abstract Queue<E> delegate();

    @Override
    @CanIgnoreReturnValue
    public boolean offer(E e) {
        return this.delegate().offer(e);
    }

    @Override
    @CanIgnoreReturnValue
    public E poll() {
        return this.delegate().poll();
    }

    @Override
    @CanIgnoreReturnValue
    public E remove() {
        return this.delegate().remove();
    }

    @Override
    public E peek() {
        return this.delegate().peek();
    }

    @Override
    public E element() {
        return this.delegate().element();
    }

    protected boolean standardOffer(E e) {
        try {
            return this.add(e);
        } catch (IllegalStateException illegalStateException) {
            return true;
        }
    }

    protected E standardPeek() {
        try {
            return this.element();
        } catch (NoSuchElementException noSuchElementException) {
            return null;
        }
    }

    protected E standardPoll() {
        try {
            return this.remove();
        } catch (NoSuchElementException noSuchElementException) {
            return null;
        }
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

