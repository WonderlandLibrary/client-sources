/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.collect.ForwardingQueue;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Collection;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@CanIgnoreReturnValue
@GwtIncompatible
public abstract class ForwardingBlockingQueue<E>
extends ForwardingQueue<E>
implements BlockingQueue<E> {
    protected ForwardingBlockingQueue() {
    }

    @Override
    protected abstract BlockingQueue<E> delegate();

    @Override
    public int drainTo(Collection<? super E> collection, int n) {
        return this.delegate().drainTo(collection, n);
    }

    @Override
    public int drainTo(Collection<? super E> collection) {
        return this.delegate().drainTo(collection);
    }

    @Override
    public boolean offer(E e, long l, TimeUnit timeUnit) throws InterruptedException {
        return this.delegate().offer(e, l, timeUnit);
    }

    @Override
    public E poll(long l, TimeUnit timeUnit) throws InterruptedException {
        return this.delegate().poll(l, timeUnit);
    }

    @Override
    public void put(E e) throws InterruptedException {
        this.delegate().put(e);
    }

    @Override
    public int remainingCapacity() {
        return this.delegate().remainingCapacity();
    }

    @Override
    public E take() throws InterruptedException {
        return this.delegate().take();
    }

    @Override
    protected Queue delegate() {
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
}

