/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.internal.shaded.org.jctools.queues;

import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue;
import io.netty.util.internal.shaded.org.jctools.queues.MpmcArrayQueue;
import io.netty.util.internal.shaded.org.jctools.queues.MpscArrayQueueConsumerField;
import io.netty.util.internal.shaded.org.jctools.queues.QueueProgressIndicators;
import io.netty.util.internal.shaded.org.jctools.util.UnsafeRefArrayAccess;

public class MpscArrayQueue<E>
extends MpscArrayQueueConsumerField<E>
implements QueueProgressIndicators {
    long p01;
    long p02;
    long p03;
    long p04;
    long p05;
    long p06;
    long p07;
    long p10;
    long p11;
    long p12;
    long p13;
    long p14;
    long p15;
    long p16;
    long p17;

    public MpscArrayQueue(int capacity) {
        super(capacity);
    }

    public boolean offerIfBelowThreshold(E e, int threshold) {
        long pIndex;
        if (null == e) {
            throw new NullPointerException();
        }
        long mask = this.mask;
        long capacity = mask + 1L;
        long producerLimit = this.lvProducerLimit();
        do {
            long available;
            long size;
            if ((size = capacity - (available = producerLimit - (pIndex = this.lvProducerIndex()))) < (long)threshold) continue;
            long cIndex = this.lvConsumerIndex();
            size = pIndex - cIndex;
            if (size >= (long)threshold) {
                return false;
            }
            producerLimit = cIndex + capacity;
            this.soProducerLimit(producerLimit);
        } while (!this.casProducerIndex(pIndex, pIndex + 1L));
        long offset = MpscArrayQueue.calcElementOffset(pIndex, mask);
        UnsafeRefArrayAccess.soElement(this.buffer, offset, e);
        return true;
    }

    @Override
    public boolean offer(E e) {
        long pIndex;
        if (null == e) {
            throw new NullPointerException();
        }
        long mask = this.mask;
        long producerLimit = this.lvProducerLimit();
        do {
            if ((pIndex = this.lvProducerIndex()) < producerLimit) continue;
            long cIndex = this.lvConsumerIndex();
            producerLimit = cIndex + mask + 1L;
            if (pIndex >= producerLimit) {
                return false;
            }
            this.soProducerLimit(producerLimit);
        } while (!this.casProducerIndex(pIndex, pIndex + 1L));
        long offset = MpscArrayQueue.calcElementOffset(pIndex, mask);
        UnsafeRefArrayAccess.soElement(this.buffer, offset, e);
        return true;
    }

    public final int failFastOffer(E e) {
        long producerLimit;
        if (null == e) {
            throw new NullPointerException();
        }
        long mask = this.mask;
        long capacity = mask + 1L;
        long pIndex = this.lvProducerIndex();
        if (pIndex >= (producerLimit = this.lvProducerLimit())) {
            long cIndex = this.lvConsumerIndex();
            producerLimit = cIndex + capacity;
            if (pIndex >= producerLimit) {
                return 1;
            }
            this.soProducerLimit(producerLimit);
        }
        if (!this.casProducerIndex(pIndex, pIndex + 1L)) {
            return -1;
        }
        long offset = MpscArrayQueue.calcElementOffset(pIndex, mask);
        UnsafeRefArrayAccess.soElement(this.buffer, offset, e);
        return 0;
    }

    @Override
    public E poll() {
        Object[] buffer = this.buffer;
        long cIndex = this.lpConsumerIndex();
        long offset = this.calcElementOffset(cIndex);
        Object e = UnsafeRefArrayAccess.lvElement(buffer, offset);
        if (null == e) {
            if (cIndex != this.lvProducerIndex()) {
                while ((e = UnsafeRefArrayAccess.lvElement(buffer, offset)) == null) {
                }
            } else {
                return null;
            }
        }
        UnsafeRefArrayAccess.spElement(buffer, offset, null);
        this.soConsumerIndex(cIndex + 1L);
        return (E)e;
    }

    @Override
    public E peek() {
        Object[] buffer = this.buffer;
        long cIndex = this.lpConsumerIndex();
        long offset = this.calcElementOffset(cIndex);
        Object e = UnsafeRefArrayAccess.lvElement(buffer, offset);
        if (null == e) {
            if (cIndex != this.lvProducerIndex()) {
                while ((e = UnsafeRefArrayAccess.lvElement(buffer, offset)) == null) {
                }
            } else {
                return null;
            }
        }
        return (E)e;
    }

    @Override
    public boolean relaxedOffer(E e) {
        return this.offer(e);
    }

    @Override
    public E relaxedPoll() {
        Object[] buffer = this.buffer;
        long cIndex = this.lpConsumerIndex();
        long offset = this.calcElementOffset(cIndex);
        Object e = UnsafeRefArrayAccess.lvElement(buffer, offset);
        if (null == e) {
            return null;
        }
        UnsafeRefArrayAccess.spElement(buffer, offset, null);
        this.soConsumerIndex(cIndex + 1L);
        return (E)e;
    }

    @Override
    public E relaxedPeek() {
        Object[] buffer = this.buffer;
        long mask = this.mask;
        long cIndex = this.lpConsumerIndex();
        return (E)UnsafeRefArrayAccess.lvElement(buffer, MpscArrayQueue.calcElementOffset(cIndex, mask));
    }

    @Override
    public int drain(MessagePassingQueue.Consumer<E> c) {
        return this.drain(c, this.capacity());
    }

    @Override
    public int fill(MessagePassingQueue.Supplier<E> s) {
        int filled;
        long result = 0L;
        int capacity = this.capacity();
        do {
            if ((filled = this.fill(s, MpmcArrayQueue.RECOMENDED_OFFER_BATCH)) != 0) continue;
            return (int)result;
        } while ((result += (long)filled) <= (long)capacity);
        return (int)result;
    }

    @Override
    public int drain(MessagePassingQueue.Consumer<E> c, int limit) {
        Object[] buffer = this.buffer;
        long mask = this.mask;
        long cIndex = this.lpConsumerIndex();
        for (int i = 0; i < limit; ++i) {
            long index = cIndex + (long)i;
            long offset = MpscArrayQueue.calcElementOffset(index, mask);
            Object e = UnsafeRefArrayAccess.lvElement(buffer, offset);
            if (null == e) {
                return i;
            }
            UnsafeRefArrayAccess.spElement(buffer, offset, null);
            this.soConsumerIndex(index + 1L);
            c.accept(e);
        }
        return limit;
    }

    @Override
    public int fill(MessagePassingQueue.Supplier<E> s, int limit) {
        long available;
        long pIndex;
        long mask = this.mask;
        long capacity = mask + 1L;
        long producerLimit = this.lvProducerLimit();
        int actualLimit = 0;
        do {
            if ((available = producerLimit - (pIndex = this.lvProducerIndex())) > 0L) continue;
            long cIndex = this.lvConsumerIndex();
            producerLimit = cIndex + capacity;
            available = producerLimit - pIndex;
            if (available <= 0L) {
                return 0;
            }
            this.soProducerLimit(producerLimit);
        } while (!this.casProducerIndex(pIndex, pIndex + (long)(actualLimit = Math.min((int)available, limit))));
        Object[] buffer = this.buffer;
        for (int i = 0; i < actualLimit; ++i) {
            long offset = MpscArrayQueue.calcElementOffset(pIndex + (long)i, mask);
            UnsafeRefArrayAccess.soElement(buffer, offset, s.get());
        }
        return actualLimit;
    }

    @Override
    public void drain(MessagePassingQueue.Consumer<E> c, MessagePassingQueue.WaitStrategy w, MessagePassingQueue.ExitCondition exit) {
        Object[] buffer = this.buffer;
        long mask = this.mask;
        long cIndex = this.lpConsumerIndex();
        int counter = 0;
        while (exit.keepRunning()) {
            for (int i = 0; i < 4096; ++i) {
                long offset = MpscArrayQueue.calcElementOffset(cIndex, mask);
                Object e = UnsafeRefArrayAccess.lvElement(buffer, offset);
                if (null == e) {
                    counter = w.idle(counter);
                    continue;
                }
                counter = 0;
                UnsafeRefArrayAccess.spElement(buffer, offset, null);
                this.soConsumerIndex(++cIndex);
                c.accept(e);
            }
        }
    }

    @Override
    public void fill(MessagePassingQueue.Supplier<E> s, MessagePassingQueue.WaitStrategy w, MessagePassingQueue.ExitCondition exit) {
        int idleCounter = 0;
        while (exit.keepRunning()) {
            if (this.fill(s, MpmcArrayQueue.RECOMENDED_OFFER_BATCH) == 0) {
                idleCounter = w.idle(idleCounter);
                continue;
            }
            idleCounter = 0;
        }
    }
}

