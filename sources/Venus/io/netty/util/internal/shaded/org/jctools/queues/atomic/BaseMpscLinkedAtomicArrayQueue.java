/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.internal.shaded.org.jctools.queues.atomic;

import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue;
import io.netty.util.internal.shaded.org.jctools.queues.QueueProgressIndicators;
import io.netty.util.internal.shaded.org.jctools.queues.atomic.BaseMpscLinkedAtomicArrayQueueColdProducerFields;
import io.netty.util.internal.shaded.org.jctools.queues.atomic.LinkedAtomicArrayQueueUtil;
import io.netty.util.internal.shaded.org.jctools.util.PortableJvmInfo;
import io.netty.util.internal.shaded.org.jctools.util.Pow2;
import io.netty.util.internal.shaded.org.jctools.util.RangeUtil;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReferenceArray;

public abstract class BaseMpscLinkedAtomicArrayQueue<E>
extends BaseMpscLinkedAtomicArrayQueueColdProducerFields<E>
implements MessagePassingQueue<E>,
QueueProgressIndicators {
    private static final Object JUMP = new Object();

    public BaseMpscLinkedAtomicArrayQueue(int n) {
        AtomicReferenceArray atomicReferenceArray;
        RangeUtil.checkGreaterThanOrEqual(n, 2, "initialCapacity");
        int n2 = Pow2.roundToPowerOfTwo(n);
        long l = n2 - 1 << 1;
        this.producerBuffer = atomicReferenceArray = LinkedAtomicArrayQueueUtil.allocate(n2 + 1);
        this.producerMask = l;
        this.consumerBuffer = atomicReferenceArray;
        this.consumerMask = l;
        this.soProducerLimit(l);
    }

    @Override
    public final Iterator<E> iterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public final int size() {
        long l;
        long l2;
        long l3 = this.lvConsumerIndex();
        do {
            l2 = l3;
            l = this.lvProducerIndex();
        } while (l2 != (l3 = this.lvConsumerIndex()));
        long l4 = l - l3 >> 1;
        if (l4 > Integer.MAX_VALUE) {
            return 0;
        }
        return (int)l4;
    }

    @Override
    public final boolean isEmpty() {
        return this.lvConsumerIndex() == this.lvProducerIndex();
    }

    @Override
    public String toString() {
        return this.getClass().getName();
    }

    @Override
    public boolean offer(E e) {
        AtomicReferenceArray atomicReferenceArray;
        long l;
        long l2;
        if (null == e) {
            throw new NullPointerException();
        }
        block6: while (true) {
            long l3 = this.lvProducerLimit();
            l2 = this.lvProducerIndex();
            if ((l2 & 1L) == 1L) continue;
            l = this.producerMask;
            atomicReferenceArray = this.producerBuffer;
            if (l3 <= l2) {
                int n = this.offerSlowPath(l, l2, l3);
                switch (n) {
                    case 0: {
                        break;
                    }
                    case 1: {
                        continue block6;
                    }
                    case 2: {
                        return true;
                    }
                    case 3: {
                        this.resize(l, atomicReferenceArray, l2, e);
                        return false;
                    }
                }
            }
            if (this.casProducerIndex(l2, l2 + 2L)) break;
        }
        int n = LinkedAtomicArrayQueueUtil.modifiedCalcElementOffset(l2, l);
        LinkedAtomicArrayQueueUtil.soElement(atomicReferenceArray, n, e);
        return false;
    }

    @Override
    public E poll() {
        AtomicReferenceArray atomicReferenceArray = this.consumerBuffer;
        long l = this.consumerIndex;
        long l2 = this.consumerMask;
        int n = LinkedAtomicArrayQueueUtil.modifiedCalcElementOffset(l, l2);
        Object e = LinkedAtomicArrayQueueUtil.lvElement(atomicReferenceArray, n);
        if (e == null) {
            if (l != this.lvProducerIndex()) {
                while ((e = LinkedAtomicArrayQueueUtil.lvElement(atomicReferenceArray, n)) == null) {
                }
            } else {
                return null;
            }
        }
        if (e == JUMP) {
            AtomicReferenceArray<E> atomicReferenceArray2 = this.getNextBuffer(atomicReferenceArray, l2);
            return this.newBufferPoll(atomicReferenceArray2, l);
        }
        LinkedAtomicArrayQueueUtil.soElement(atomicReferenceArray, n, null);
        this.soConsumerIndex(l + 2L);
        return e;
    }

    @Override
    public E peek() {
        AtomicReferenceArray atomicReferenceArray = this.consumerBuffer;
        long l = this.consumerIndex;
        long l2 = this.consumerMask;
        int n = LinkedAtomicArrayQueueUtil.modifiedCalcElementOffset(l, l2);
        Object e = LinkedAtomicArrayQueueUtil.lvElement(atomicReferenceArray, n);
        if (e == null && l != this.lvProducerIndex()) {
            while ((e = LinkedAtomicArrayQueueUtil.lvElement(atomicReferenceArray, n)) == null) {
            }
        }
        if (e == JUMP) {
            return this.newBufferPeek(this.getNextBuffer(atomicReferenceArray, l2), l);
        }
        return e;
    }

    private int offerSlowPath(long l, long l2, long l3) {
        long l4 = this.lvConsumerIndex();
        long l5 = this.getCurrentBufferCapacity(l);
        int n = 0;
        if (l4 + l5 > l2) {
            if (!this.casProducerLimit(l3, l4 + l5)) {
                n = 1;
            }
        } else {
            n = this.availableInQueue(l2, l4) <= 0L ? 2 : (this.casProducerIndex(l2, l2 + 1L) ? 3 : 1);
        }
        return n;
    }

    protected abstract long availableInQueue(long var1, long var3);

    private AtomicReferenceArray<E> getNextBuffer(AtomicReferenceArray<E> atomicReferenceArray, long l) {
        int n = this.nextArrayOffset(l);
        AtomicReferenceArray atomicReferenceArray2 = (AtomicReferenceArray)LinkedAtomicArrayQueueUtil.lvElement(atomicReferenceArray, n);
        LinkedAtomicArrayQueueUtil.soElement(atomicReferenceArray, n, null);
        return atomicReferenceArray2;
    }

    private int nextArrayOffset(long l) {
        return LinkedAtomicArrayQueueUtil.modifiedCalcElementOffset(l + 2L, Long.MAX_VALUE);
    }

    private E newBufferPoll(AtomicReferenceArray<E> atomicReferenceArray, long l) {
        int n = this.newBufferAndOffset(atomicReferenceArray, l);
        E e = LinkedAtomicArrayQueueUtil.lvElement(atomicReferenceArray, n);
        if (e == null) {
            throw new IllegalStateException("new buffer must have at least one element");
        }
        LinkedAtomicArrayQueueUtil.soElement(atomicReferenceArray, n, null);
        this.soConsumerIndex(l + 2L);
        return e;
    }

    private E newBufferPeek(AtomicReferenceArray<E> atomicReferenceArray, long l) {
        int n = this.newBufferAndOffset(atomicReferenceArray, l);
        E e = LinkedAtomicArrayQueueUtil.lvElement(atomicReferenceArray, n);
        if (null == e) {
            throw new IllegalStateException("new buffer must have at least one element");
        }
        return e;
    }

    private int newBufferAndOffset(AtomicReferenceArray<E> atomicReferenceArray, long l) {
        this.consumerBuffer = atomicReferenceArray;
        this.consumerMask = LinkedAtomicArrayQueueUtil.length(atomicReferenceArray) - 2 << 1;
        int n = LinkedAtomicArrayQueueUtil.modifiedCalcElementOffset(l, this.consumerMask);
        return n;
    }

    @Override
    public long currentProducerIndex() {
        return this.lvProducerIndex() / 2L;
    }

    @Override
    public long currentConsumerIndex() {
        return this.lvConsumerIndex() / 2L;
    }

    @Override
    public abstract int capacity();

    @Override
    public boolean relaxedOffer(E e) {
        return this.offer(e);
    }

    @Override
    public E relaxedPoll() {
        AtomicReferenceArray atomicReferenceArray = this.consumerBuffer;
        long l = this.consumerIndex;
        long l2 = this.consumerMask;
        int n = LinkedAtomicArrayQueueUtil.modifiedCalcElementOffset(l, l2);
        Object e = LinkedAtomicArrayQueueUtil.lvElement(atomicReferenceArray, n);
        if (e == null) {
            return null;
        }
        if (e == JUMP) {
            AtomicReferenceArray<E> atomicReferenceArray2 = this.getNextBuffer(atomicReferenceArray, l2);
            return this.newBufferPoll(atomicReferenceArray2, l);
        }
        LinkedAtomicArrayQueueUtil.soElement(atomicReferenceArray, n, null);
        this.soConsumerIndex(l + 2L);
        return e;
    }

    @Override
    public E relaxedPeek() {
        AtomicReferenceArray atomicReferenceArray = this.consumerBuffer;
        long l = this.consumerIndex;
        long l2 = this.consumerMask;
        int n = LinkedAtomicArrayQueueUtil.modifiedCalcElementOffset(l, l2);
        Object e = LinkedAtomicArrayQueueUtil.lvElement(atomicReferenceArray, n);
        if (e == JUMP) {
            return this.newBufferPeek(this.getNextBuffer(atomicReferenceArray, l2), l);
        }
        return e;
    }

    @Override
    public int fill(MessagePassingQueue.Supplier<E> supplier) {
        int n;
        long l = 0L;
        int n2 = this.capacity();
        do {
            if ((n = this.fill(supplier, PortableJvmInfo.RECOMENDED_OFFER_BATCH)) != 0) continue;
            return (int)l;
        } while ((l += (long)n) <= (long)n2);
        return (int)l;
    }

    @Override
    public int fill(MessagePassingQueue.Supplier<E> supplier, int n) {
        long l;
        AtomicReferenceArray atomicReferenceArray;
        long l2;
        long l3;
        block5: while (true) {
            long l4 = this.lvProducerLimit();
            l3 = this.lvProducerIndex();
            if ((l3 & 1L) == 1L) continue;
            l2 = this.producerMask;
            atomicReferenceArray = this.producerBuffer;
            l = Math.min(l4, l3 + (long)(2 * n));
            if (l3 == l4 || l4 < l) {
                int n2 = this.offerSlowPath(l2, l3, l4);
                switch (n2) {
                    case 1: {
                        continue block5;
                    }
                    case 2: {
                        return 1;
                    }
                    case 3: {
                        this.resize(l2, atomicReferenceArray, l3, supplier.get());
                        return 0;
                    }
                }
            }
            if (this.casProducerIndex(l3, l)) break;
        }
        int n3 = (int)((l - l3) / 2L);
        int n4 = 0;
        for (n4 = 0; n4 < n3; ++n4) {
            int n5 = LinkedAtomicArrayQueueUtil.modifiedCalcElementOffset(l3 + (long)(2 * n4), l2);
            LinkedAtomicArrayQueueUtil.soElement(atomicReferenceArray, n5, supplier.get());
        }
        return n3;
    }

    @Override
    public void fill(MessagePassingQueue.Supplier<E> supplier, MessagePassingQueue.WaitStrategy waitStrategy, MessagePassingQueue.ExitCondition exitCondition) {
        while (exitCondition.keepRunning()) {
            while (this.fill(supplier, PortableJvmInfo.RECOMENDED_OFFER_BATCH) != 0 && exitCondition.keepRunning()) {
            }
            int n = 0;
            while (exitCondition.keepRunning() && this.fill(supplier, PortableJvmInfo.RECOMENDED_OFFER_BATCH) == 0) {
                n = waitStrategy.idle(n);
            }
        }
    }

    @Override
    public int drain(MessagePassingQueue.Consumer<E> consumer) {
        return this.drain(consumer, this.capacity());
    }

    @Override
    public int drain(MessagePassingQueue.Consumer<E> consumer, int n) {
        E e;
        int n2;
        for (n2 = 0; n2 < n && (e = this.relaxedPoll()) != null; ++n2) {
            consumer.accept(e);
        }
        return n2;
    }

    @Override
    public void drain(MessagePassingQueue.Consumer<E> consumer, MessagePassingQueue.WaitStrategy waitStrategy, MessagePassingQueue.ExitCondition exitCondition) {
        int n = 0;
        while (exitCondition.keepRunning()) {
            E e = this.relaxedPoll();
            if (e == null) {
                n = waitStrategy.idle(n);
                continue;
            }
            n = 0;
            consumer.accept(e);
        }
    }

    private void resize(long l, AtomicReferenceArray<E> atomicReferenceArray, long l2, E e) {
        AtomicReferenceArray atomicReferenceArray2;
        int n = this.getNextBufferSize(atomicReferenceArray);
        this.producerBuffer = atomicReferenceArray2 = LinkedAtomicArrayQueueUtil.allocate(n);
        int n2 = n - 2 << 1;
        this.producerMask = n2;
        int n3 = LinkedAtomicArrayQueueUtil.modifiedCalcElementOffset(l2, l);
        int n4 = LinkedAtomicArrayQueueUtil.modifiedCalcElementOffset(l2, n2);
        LinkedAtomicArrayQueueUtil.soElement(atomicReferenceArray2, n4, e);
        LinkedAtomicArrayQueueUtil.soElement(atomicReferenceArray, this.nextArrayOffset(l), atomicReferenceArray2);
        long l3 = this.lvConsumerIndex();
        long l4 = this.availableInQueue(l2, l3);
        RangeUtil.checkPositive(l4, "availableInQueue");
        this.soProducerLimit(l2 + Math.min((long)n2, l4));
        this.soProducerIndex(l2 + 2L);
        LinkedAtomicArrayQueueUtil.soElement(atomicReferenceArray, n3, JUMP);
    }

    protected abstract int getNextBufferSize(AtomicReferenceArray<E> var1);

    protected abstract long getCurrentBufferCapacity(long var1);
}

