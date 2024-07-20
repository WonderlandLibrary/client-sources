/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.internal.shaded.org.jctools.queues.atomic;

import io.netty.util.internal.shaded.org.jctools.util.Pow2;
import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReferenceArray;

abstract class AtomicReferenceArrayQueue<E>
extends AbstractQueue<E> {
    protected final AtomicReferenceArray<E> buffer;
    protected final int mask;

    public AtomicReferenceArrayQueue(int capacity) {
        int actualCapacity = Pow2.roundToPowerOfTwo(capacity);
        this.mask = actualCapacity - 1;
        this.buffer = new AtomicReferenceArray(actualCapacity);
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
        while (this.poll() != null || !this.isEmpty()) {
        }
    }

    protected final int calcElementOffset(long index, int mask) {
        return (int)index & mask;
    }

    protected final int calcElementOffset(long index) {
        return (int)index & this.mask;
    }

    protected final E lvElement(AtomicReferenceArray<E> buffer, int offset) {
        return buffer.get(offset);
    }

    protected final E lpElement(AtomicReferenceArray<E> buffer, int offset) {
        return buffer.get(offset);
    }

    protected final E lpElement(int offset) {
        return this.buffer.get(offset);
    }

    protected final void spElement(AtomicReferenceArray<E> buffer, int offset, E value) {
        buffer.lazySet(offset, value);
    }

    protected final void spElement(int offset, E value) {
        this.buffer.lazySet(offset, value);
    }

    protected final void soElement(AtomicReferenceArray<E> buffer, int offset, E value) {
        buffer.lazySet(offset, value);
    }

    protected final void soElement(int offset, E value) {
        this.buffer.lazySet(offset, value);
    }

    protected final void svElement(AtomicReferenceArray<E> buffer, int offset, E value) {
        buffer.set(offset, value);
    }

    protected final E lvElement(int offset) {
        return this.lvElement(this.buffer, offset);
    }
}

