/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.internal.shaded.org.jctools.queues.atomic;

import io.netty.util.internal.shaded.org.jctools.queues.IndexedQueueSizeUtil;
import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue;
import io.netty.util.internal.shaded.org.jctools.queues.QueueProgressIndicators;
import io.netty.util.internal.shaded.org.jctools.util.Pow2;
import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReferenceArray;

abstract class AtomicReferenceArrayQueue<E>
extends AbstractQueue<E>
implements IndexedQueueSizeUtil.IndexedQueue,
QueueProgressIndicators,
MessagePassingQueue<E> {
    protected final AtomicReferenceArray<E> buffer;
    protected final int mask;

    public AtomicReferenceArrayQueue(int n) {
        int n2 = Pow2.roundToPowerOfTwo(n);
        this.mask = n2 - 1;
        this.buffer = new AtomicReferenceArray(n2);
    }

    @Override
    public Iterator<E> iterator() {
        throw new UnsupportedOperationException();
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

    protected final int calcElementOffset(long l, int n) {
        return (int)l & n;
    }

    protected final int calcElementOffset(long l) {
        return (int)l & this.mask;
    }

    public static <E> E lvElement(AtomicReferenceArray<E> atomicReferenceArray, int n) {
        return atomicReferenceArray.get(n);
    }

    public static <E> E lpElement(AtomicReferenceArray<E> atomicReferenceArray, int n) {
        return atomicReferenceArray.get(n);
    }

    protected final E lpElement(int n) {
        return this.buffer.get(n);
    }

    public static <E> void spElement(AtomicReferenceArray<E> atomicReferenceArray, int n, E e) {
        atomicReferenceArray.lazySet(n, e);
    }

    protected final void spElement(int n, E e) {
        this.buffer.lazySet(n, e);
    }

    public static <E> void soElement(AtomicReferenceArray<E> atomicReferenceArray, int n, E e) {
        atomicReferenceArray.lazySet(n, e);
    }

    protected final void soElement(int n, E e) {
        this.buffer.lazySet(n, e);
    }

    public static <E> void svElement(AtomicReferenceArray<E> atomicReferenceArray, int n, E e) {
        atomicReferenceArray.set(n, e);
    }

    protected final E lvElement(int n) {
        return AtomicReferenceArrayQueue.lvElement(this.buffer, n);
    }

    @Override
    public final int capacity() {
        return this.mask + 1;
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
    public final long currentProducerIndex() {
        return this.lvProducerIndex();
    }

    @Override
    public final long currentConsumerIndex() {
        return this.lvConsumerIndex();
    }
}

