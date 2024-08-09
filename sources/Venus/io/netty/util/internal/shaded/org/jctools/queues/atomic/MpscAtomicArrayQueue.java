/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.internal.shaded.org.jctools.queues.atomic;

import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue;
import io.netty.util.internal.shaded.org.jctools.queues.atomic.MpscAtomicArrayQueueL3Pad;
import io.netty.util.internal.shaded.org.jctools.util.PortableJvmInfo;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class MpscAtomicArrayQueue<E>
extends MpscAtomicArrayQueueL3Pad<E> {
    public MpscAtomicArrayQueue(int n) {
        super(n);
    }

    public boolean offerIfBelowThreshold(E e, int n) {
        long l;
        if (null == e) {
            throw new NullPointerException();
        }
        int n2 = this.mask;
        long l2 = n2 + 1;
        long l3 = this.lvProducerLimit();
        do {
            long l4;
            long l5;
            if ((l5 = l2 - (l4 = l3 - (l = this.lvProducerIndex()))) < (long)n) continue;
            long l6 = this.lvConsumerIndex();
            l5 = l - l6;
            if (l5 >= (long)n) {
                return true;
            }
            l3 = l6 + l2;
            this.soProducerLimit(l3);
        } while (!this.casProducerIndex(l, l + 1L));
        int n3 = this.calcElementOffset(l, n2);
        MpscAtomicArrayQueue.soElement(this.buffer, n3, e);
        return false;
    }

    @Override
    public boolean offer(E e) {
        long l;
        if (null == e) {
            throw new NullPointerException();
        }
        int n = this.mask;
        long l2 = this.lvProducerLimit();
        do {
            if ((l = this.lvProducerIndex()) < l2) continue;
            long l3 = this.lvConsumerIndex();
            l2 = l3 + (long)n + 1L;
            if (l >= l2) {
                return true;
            }
            this.soProducerLimit(l2);
        } while (!this.casProducerIndex(l, l + 1L));
        int n2 = this.calcElementOffset(l, n);
        MpscAtomicArrayQueue.soElement(this.buffer, n2, e);
        return false;
    }

    public final int failFastOffer(E e) {
        long l;
        if (null == e) {
            throw new NullPointerException();
        }
        int n = this.mask;
        long l2 = n + 1;
        long l3 = this.lvProducerIndex();
        if (l3 >= (l = this.lvProducerLimit())) {
            long l4 = this.lvConsumerIndex();
            l = l4 + l2;
            if (l3 >= l) {
                return 0;
            }
            this.soProducerLimit(l);
        }
        if (!this.casProducerIndex(l3, l3 + 1L)) {
            return 1;
        }
        int n2 = this.calcElementOffset(l3, n);
        MpscAtomicArrayQueue.soElement(this.buffer, n2, e);
        return 1;
    }

    @Override
    public E poll() {
        AtomicReferenceArray atomicReferenceArray = this.buffer;
        long l = this.lpConsumerIndex();
        int n = this.calcElementOffset(l);
        Object e = MpscAtomicArrayQueue.lvElement(atomicReferenceArray, n);
        if (null == e) {
            if (l != this.lvProducerIndex()) {
                while ((e = MpscAtomicArrayQueue.lvElement(atomicReferenceArray, n)) == null) {
                }
            } else {
                return null;
            }
        }
        MpscAtomicArrayQueue.spElement(atomicReferenceArray, n, null);
        this.soConsumerIndex(l + 1L);
        return e;
    }

    @Override
    public E peek() {
        AtomicReferenceArray atomicReferenceArray = this.buffer;
        long l = this.lpConsumerIndex();
        int n = this.calcElementOffset(l);
        Object e = MpscAtomicArrayQueue.lvElement(atomicReferenceArray, n);
        if (null == e) {
            if (l != this.lvProducerIndex()) {
                while ((e = MpscAtomicArrayQueue.lvElement(atomicReferenceArray, n)) == null) {
                }
            } else {
                return null;
            }
        }
        return e;
    }

    @Override
    public boolean relaxedOffer(E e) {
        return this.offer(e);
    }

    @Override
    public E relaxedPoll() {
        AtomicReferenceArray atomicReferenceArray = this.buffer;
        long l = this.lpConsumerIndex();
        int n = this.calcElementOffset(l);
        Object e = MpscAtomicArrayQueue.lvElement(atomicReferenceArray, n);
        if (null == e) {
            return null;
        }
        MpscAtomicArrayQueue.spElement(atomicReferenceArray, n, null);
        this.soConsumerIndex(l + 1L);
        return e;
    }

    @Override
    public E relaxedPeek() {
        AtomicReferenceArray atomicReferenceArray = this.buffer;
        int n = this.mask;
        long l = this.lpConsumerIndex();
        return MpscAtomicArrayQueue.lvElement(atomicReferenceArray, this.calcElementOffset(l, n));
    }

    @Override
    public int drain(MessagePassingQueue.Consumer<E> consumer) {
        return this.drain(consumer, this.capacity());
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
    public int drain(MessagePassingQueue.Consumer<E> consumer, int n) {
        AtomicReferenceArray atomicReferenceArray = this.buffer;
        int n2 = this.mask;
        long l = this.lpConsumerIndex();
        for (int i = 0; i < n; ++i) {
            long l2 = l + (long)i;
            int n3 = this.calcElementOffset(l2, n2);
            Object e = MpscAtomicArrayQueue.lvElement(atomicReferenceArray, n3);
            if (null == e) {
                return i;
            }
            MpscAtomicArrayQueue.spElement(atomicReferenceArray, n3, null);
            this.soConsumerIndex(l2 + 1L);
            consumer.accept(e);
        }
        return n;
    }

    @Override
    public int fill(MessagePassingQueue.Supplier<E> supplier, int n) {
        long l;
        long l2;
        int n2 = this.mask;
        long l3 = n2 + 1;
        long l4 = this.lvProducerLimit();
        int n3 = 0;
        do {
            if ((l = l4 - (l2 = this.lvProducerIndex())) > 0L) continue;
            long l5 = this.lvConsumerIndex();
            l4 = l5 + l3;
            l = l4 - l2;
            if (l <= 0L) {
                return 1;
            }
            this.soProducerLimit(l4);
        } while (!this.casProducerIndex(l2, l2 + (long)(n3 = Math.min((int)l, n))));
        AtomicReferenceArray atomicReferenceArray = this.buffer;
        for (int i = 0; i < n3; ++i) {
            int n4 = this.calcElementOffset(l2 + (long)i, n2);
            MpscAtomicArrayQueue.soElement(atomicReferenceArray, n4, supplier.get());
        }
        return n3;
    }

    @Override
    public void drain(MessagePassingQueue.Consumer<E> consumer, MessagePassingQueue.WaitStrategy waitStrategy, MessagePassingQueue.ExitCondition exitCondition) {
        AtomicReferenceArray atomicReferenceArray = this.buffer;
        int n = this.mask;
        long l = this.lpConsumerIndex();
        int n2 = 0;
        while (exitCondition.keepRunning()) {
            for (int i = 0; i < 4096; ++i) {
                int n3 = this.calcElementOffset(l, n);
                Object e = MpscAtomicArrayQueue.lvElement(atomicReferenceArray, n3);
                if (null == e) {
                    n2 = waitStrategy.idle(n2);
                    continue;
                }
                n2 = 0;
                MpscAtomicArrayQueue.spElement(atomicReferenceArray, n3, null);
                this.soConsumerIndex(++l);
                consumer.accept(e);
            }
        }
    }

    @Override
    public void fill(MessagePassingQueue.Supplier<E> supplier, MessagePassingQueue.WaitStrategy waitStrategy, MessagePassingQueue.ExitCondition exitCondition) {
        int n = 0;
        while (exitCondition.keepRunning()) {
            if (this.fill(supplier, PortableJvmInfo.RECOMENDED_OFFER_BATCH) == 0) {
                n = waitStrategy.idle(n);
                continue;
            }
            n = 0;
        }
    }

    @Deprecated
    public int weakOffer(E e) {
        return this.failFastOffer(e);
    }

    @Override
    public void clear() {
        super.clear();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public Iterator iterator() {
        return super.iterator();
    }
}

