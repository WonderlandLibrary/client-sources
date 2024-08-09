/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.internal.shaded.org.jctools.queues;

import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue;
import io.netty.util.internal.shaded.org.jctools.queues.MpscArrayQueueL3Pad;
import io.netty.util.internal.shaded.org.jctools.util.PortableJvmInfo;
import io.netty.util.internal.shaded.org.jctools.util.UnsafeRefArrayAccess;

public class MpscArrayQueue<E>
extends MpscArrayQueueL3Pad<E> {
    public MpscArrayQueue(int n) {
        super(n);
    }

    public boolean offerIfBelowThreshold(E e, int n) {
        long l;
        long l2;
        if (null == e) {
            throw new NullPointerException();
        }
        long l3 = this.mask;
        long l4 = l3 + 1L;
        long l5 = this.lvProducerLimit();
        do {
            long l6;
            if ((l6 = l4 - (l = l5 - (l2 = this.lvProducerIndex()))) < (long)n) continue;
            long l7 = this.lvConsumerIndex();
            l6 = l2 - l7;
            if (l6 >= (long)n) {
                return true;
            }
            l5 = l7 + l4;
            this.soProducerLimit(l5);
        } while (!this.casProducerIndex(l2, l2 + 1L));
        l = MpscArrayQueue.calcElementOffset(l2, l3);
        UnsafeRefArrayAccess.soElement(this.buffer, l, e);
        return false;
    }

    @Override
    public boolean offer(E e) {
        long l;
        long l2;
        if (null == e) {
            throw new NullPointerException();
        }
        long l3 = this.mask;
        long l4 = this.lvProducerLimit();
        do {
            if ((l2 = this.lvProducerIndex()) < l4) continue;
            l = this.lvConsumerIndex();
            l4 = l + l3 + 1L;
            if (l2 >= l4) {
                return true;
            }
            this.soProducerLimit(l4);
        } while (!this.casProducerIndex(l2, l2 + 1L));
        l = MpscArrayQueue.calcElementOffset(l2, l3);
        UnsafeRefArrayAccess.soElement(this.buffer, l, e);
        return false;
    }

    public final int failFastOffer(E e) {
        long l;
        long l2;
        if (null == e) {
            throw new NullPointerException();
        }
        long l3 = this.mask;
        long l4 = l3 + 1L;
        long l5 = this.lvProducerIndex();
        if (l5 >= (l2 = this.lvProducerLimit())) {
            l = this.lvConsumerIndex();
            l2 = l + l4;
            if (l5 >= l2) {
                return 0;
            }
            this.soProducerLimit(l2);
        }
        if (!this.casProducerIndex(l5, l5 + 1L)) {
            return 1;
        }
        l = MpscArrayQueue.calcElementOffset(l5, l3);
        UnsafeRefArrayAccess.soElement(this.buffer, l, e);
        return 1;
    }

    @Override
    public E poll() {
        Object[] objectArray = this.buffer;
        long l = this.lpConsumerIndex();
        long l2 = this.calcElementOffset(l);
        Object object = UnsafeRefArrayAccess.lvElement(objectArray, l2);
        if (null == object) {
            if (l != this.lvProducerIndex()) {
                while ((object = UnsafeRefArrayAccess.lvElement(objectArray, l2)) == null) {
                }
            } else {
                return null;
            }
        }
        UnsafeRefArrayAccess.spElement(objectArray, l2, null);
        this.soConsumerIndex(l + 1L);
        return (E)object;
    }

    @Override
    public E peek() {
        Object[] objectArray = this.buffer;
        long l = this.lpConsumerIndex();
        long l2 = this.calcElementOffset(l);
        Object object = UnsafeRefArrayAccess.lvElement(objectArray, l2);
        if (null == object) {
            if (l != this.lvProducerIndex()) {
                while ((object = UnsafeRefArrayAccess.lvElement(objectArray, l2)) == null) {
                }
            } else {
                return null;
            }
        }
        return (E)object;
    }

    @Override
    public boolean relaxedOffer(E e) {
        return this.offer(e);
    }

    @Override
    public E relaxedPoll() {
        Object[] objectArray = this.buffer;
        long l = this.lpConsumerIndex();
        long l2 = this.calcElementOffset(l);
        Object object = UnsafeRefArrayAccess.lvElement(objectArray, l2);
        if (null == object) {
            return null;
        }
        UnsafeRefArrayAccess.spElement(objectArray, l2, null);
        this.soConsumerIndex(l + 1L);
        return (E)object;
    }

    @Override
    public E relaxedPeek() {
        Object[] objectArray = this.buffer;
        long l = this.mask;
        long l2 = this.lpConsumerIndex();
        return (E)UnsafeRefArrayAccess.lvElement(objectArray, MpscArrayQueue.calcElementOffset(l2, l));
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
        Object[] objectArray = this.buffer;
        long l = this.mask;
        long l2 = this.lpConsumerIndex();
        for (int i = 0; i < n; ++i) {
            long l3 = l2 + (long)i;
            long l4 = MpscArrayQueue.calcElementOffset(l3, l);
            Object object = UnsafeRefArrayAccess.lvElement(objectArray, l4);
            if (null == object) {
                return i;
            }
            UnsafeRefArrayAccess.spElement(objectArray, l4, null);
            this.soConsumerIndex(l3 + 1L);
            consumer.accept(object);
        }
        return n;
    }

    @Override
    public int fill(MessagePassingQueue.Supplier<E> supplier, int n) {
        long l;
        long l2;
        long l3;
        long l4 = this.mask;
        long l5 = l4 + 1L;
        long l6 = this.lvProducerLimit();
        int n2 = 0;
        do {
            if ((l2 = l6 - (l3 = this.lvProducerIndex())) > 0L) continue;
            l = this.lvConsumerIndex();
            l6 = l + l5;
            l2 = l6 - l3;
            if (l2 <= 0L) {
                return 1;
            }
            this.soProducerLimit(l6);
        } while (!this.casProducerIndex(l3, l3 + (long)(n2 = Math.min((int)l2, n))));
        Object[] objectArray = this.buffer;
        for (int i = 0; i < n2; ++i) {
            l = MpscArrayQueue.calcElementOffset(l3 + (long)i, l4);
            UnsafeRefArrayAccess.soElement(objectArray, l, supplier.get());
        }
        return n2;
    }

    @Override
    public void drain(MessagePassingQueue.Consumer<E> consumer, MessagePassingQueue.WaitStrategy waitStrategy, MessagePassingQueue.ExitCondition exitCondition) {
        Object[] objectArray = this.buffer;
        long l = this.mask;
        long l2 = this.lpConsumerIndex();
        int n = 0;
        while (exitCondition.keepRunning()) {
            for (int i = 0; i < 4096; ++i) {
                long l3 = MpscArrayQueue.calcElementOffset(l2, l);
                Object object = UnsafeRefArrayAccess.lvElement(objectArray, l3);
                if (null == object) {
                    n = waitStrategy.idle(n);
                    continue;
                }
                n = 0;
                UnsafeRefArrayAccess.spElement(objectArray, l3, null);
                this.soConsumerIndex(++l2);
                consumer.accept(object);
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
}

