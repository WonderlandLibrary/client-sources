/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.ForwardingQueue;
import com.google.common.collect.Iterables;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Queue;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@Beta
@GwtCompatible
public final class EvictingQueue<E>
extends ForwardingQueue<E>
implements Serializable {
    private final Queue<E> delegate;
    @VisibleForTesting
    final int maxSize;
    private static final long serialVersionUID = 0L;

    private EvictingQueue(int n) {
        Preconditions.checkArgument(n >= 0, "maxSize (%s) must >= 0", n);
        this.delegate = new ArrayDeque(n);
        this.maxSize = n;
    }

    public static <E> EvictingQueue<E> create(int n) {
        return new EvictingQueue<E>(n);
    }

    public int remainingCapacity() {
        return this.maxSize - this.size();
    }

    @Override
    protected Queue<E> delegate() {
        return this.delegate;
    }

    @Override
    @CanIgnoreReturnValue
    public boolean offer(E e) {
        return this.add(e);
    }

    @Override
    @CanIgnoreReturnValue
    public boolean add(E e) {
        Preconditions.checkNotNull(e);
        if (this.maxSize == 0) {
            return false;
        }
        if (this.size() == this.maxSize) {
            this.delegate.remove();
        }
        this.delegate.add(e);
        return false;
    }

    @Override
    @CanIgnoreReturnValue
    public boolean addAll(Collection<? extends E> collection) {
        int n = collection.size();
        if (n >= this.maxSize) {
            this.clear();
            return Iterables.addAll(this, Iterables.skip(collection, n - this.maxSize));
        }
        return this.standardAddAll(collection);
    }

    @Override
    public boolean contains(Object object) {
        return this.delegate().contains(Preconditions.checkNotNull(object));
    }

    @Override
    @CanIgnoreReturnValue
    public boolean remove(Object object) {
        return this.delegate().remove(Preconditions.checkNotNull(object));
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

