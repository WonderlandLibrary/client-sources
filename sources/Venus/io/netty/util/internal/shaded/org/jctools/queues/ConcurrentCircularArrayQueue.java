/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.internal.shaded.org.jctools.queues;

import io.netty.util.internal.shaded.org.jctools.queues.CircularArrayOffsetCalculator;
import io.netty.util.internal.shaded.org.jctools.queues.ConcurrentCircularArrayQueueL0Pad;
import io.netty.util.internal.shaded.org.jctools.queues.IndexedQueueSizeUtil;
import io.netty.util.internal.shaded.org.jctools.util.Pow2;
import java.util.Iterator;

public abstract class ConcurrentCircularArrayQueue<E>
extends ConcurrentCircularArrayQueueL0Pad<E> {
    protected final long mask;
    protected final E[] buffer;

    public ConcurrentCircularArrayQueue(int n) {
        int n2 = Pow2.roundToPowerOfTwo(n);
        this.mask = n2 - 1;
        this.buffer = CircularArrayOffsetCalculator.allocate(n2);
    }

    protected static long calcElementOffset(long l, long l2) {
        return CircularArrayOffsetCalculator.calcElementOffset(l, l2);
    }

    protected final long calcElementOffset(long l) {
        return ConcurrentCircularArrayQueue.calcElementOffset(l, this.mask);
    }

    @Override
    public Iterator<E> iterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public final int size() {
        return IndexedQueueSizeUtil.size(this);
    }

    @Override
    public final boolean isEmpty() {
        return IndexedQueueSizeUtil.isEmpty(this);
    }

    @Override
    public String toString() {
        return this.getClass().getName();
    }

    @Override
    public void clear() {
        while (this.poll() != null) {
        }
    }

    @Override
    public int capacity() {
        return (int)(this.mask + 1L);
    }

    @Override
    public final long currentProducerIndex() {
        return this.lvProducerIndex();
    }

    @Override
    public final long currentConsumerIndex() {
        return this.lvConsumerIndex();
    }
}

