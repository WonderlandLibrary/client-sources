/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.internal.shaded.org.jctools.queues;

import io.netty.util.internal.shaded.org.jctools.queues.BaseMpscLinkedArrayQueueColdProducerFields;
import io.netty.util.internal.shaded.org.jctools.queues.CircularArrayOffsetCalculator;
import io.netty.util.internal.shaded.org.jctools.queues.LinkedArrayQueueUtil;
import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue;
import io.netty.util.internal.shaded.org.jctools.queues.QueueProgressIndicators;
import io.netty.util.internal.shaded.org.jctools.util.PortableJvmInfo;
import io.netty.util.internal.shaded.org.jctools.util.Pow2;
import io.netty.util.internal.shaded.org.jctools.util.RangeUtil;
import io.netty.util.internal.shaded.org.jctools.util.UnsafeRefArrayAccess;
import java.util.Iterator;

public abstract class BaseMpscLinkedArrayQueue<E>
extends BaseMpscLinkedArrayQueueColdProducerFields<E>
implements MessagePassingQueue<E>,
QueueProgressIndicators {
    private static final Object JUMP = new Object();
    private static final int CONTINUE_TO_P_INDEX_CAS = 0;
    private static final int RETRY = 1;
    private static final int QUEUE_FULL = 2;
    private static final int QUEUE_RESIZE = 3;

    public BaseMpscLinkedArrayQueue(int n) {
        RangeUtil.checkGreaterThanOrEqual(n, 2, "initialCapacity");
        int n2 = Pow2.roundToPowerOfTwo(n);
        long l = n2 - 1 << 1;
        E[] EArray = CircularArrayOffsetCalculator.allocate(n2 + 1);
        this.producerBuffer = EArray;
        this.producerMask = l;
        this.consumerBuffer = EArray;
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
        Object[] objectArray;
        long l;
        long l2;
        long l3;
        if (null == e) {
            throw new NullPointerException();
        }
        block6: while (true) {
            l3 = this.lvProducerLimit();
            l2 = this.lvProducerIndex();
            if ((l2 & 1L) == 1L) continue;
            l = this.producerMask;
            objectArray = this.producerBuffer;
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
                        this.resize(l, objectArray, l2, e);
                        return false;
                    }
                }
            }
            if (this.casProducerIndex(l2, l2 + 2L)) break;
        }
        l3 = LinkedArrayQueueUtil.modifiedCalcElementOffset(l2, l);
        UnsafeRefArrayAccess.soElement(objectArray, l3, e);
        return false;
    }

    @Override
    public E poll() {
        Object[] objectArray = this.consumerBuffer;
        long l = this.consumerIndex;
        long l2 = this.consumerMask;
        long l3 = LinkedArrayQueueUtil.modifiedCalcElementOffset(l, l2);
        Object object = UnsafeRefArrayAccess.lvElement(objectArray, l3);
        if (object == null) {
            if (l != this.lvProducerIndex()) {
                while ((object = UnsafeRefArrayAccess.lvElement(objectArray, l3)) == null) {
                }
            } else {
                return null;
            }
        }
        if (object == JUMP) {
            Object[] objectArray2 = this.getNextBuffer(objectArray, l2);
            return (E)this.newBufferPoll(objectArray2, l);
        }
        UnsafeRefArrayAccess.soElement(objectArray, l3, null);
        this.soConsumerIndex(l + 2L);
        return (E)object;
    }

    @Override
    public E peek() {
        Object[] objectArray = this.consumerBuffer;
        long l = this.consumerIndex;
        long l2 = this.consumerMask;
        long l3 = LinkedArrayQueueUtil.modifiedCalcElementOffset(l, l2);
        Object object = UnsafeRefArrayAccess.lvElement(objectArray, l3);
        if (object == null && l != this.lvProducerIndex()) {
            while ((object = UnsafeRefArrayAccess.lvElement(objectArray, l3)) == null) {
            }
        }
        if (object == JUMP) {
            return (E)this.newBufferPeek(this.getNextBuffer(objectArray, l2), l);
        }
        return (E)object;
    }

    private int offerSlowPath(long l, long l2, long l3) {
        long l4;
        long l5 = this.lvConsumerIndex();
        if (l5 + (l4 = this.getCurrentBufferCapacity(l)) > l2) {
            if (!this.casProducerLimit(l3, l5 + l4)) {
                return 0;
            }
            return 1;
        }
        if (this.availableInQueue(l2, l5) <= 0L) {
            return 1;
        }
        if (this.casProducerIndex(l2, l2 + 1L)) {
            return 0;
        }
        return 0;
    }

    protected abstract long availableInQueue(long var1, long var3);

    private E[] getNextBuffer(E[] EArray, long l) {
        long l2 = this.nextArrayOffset(l);
        Object[] objectArray = (Object[])UnsafeRefArrayAccess.lvElement(EArray, l2);
        UnsafeRefArrayAccess.soElement(EArray, l2, null);
        return objectArray;
    }

    private long nextArrayOffset(long l) {
        return LinkedArrayQueueUtil.modifiedCalcElementOffset(l + 2L, Long.MAX_VALUE);
    }

    private E newBufferPoll(E[] EArray, long l) {
        long l2 = this.newBufferAndOffset(EArray, l);
        E e = UnsafeRefArrayAccess.lvElement(EArray, l2);
        if (e == null) {
            throw new IllegalStateException("new buffer must have at least one element");
        }
        UnsafeRefArrayAccess.soElement(EArray, l2, null);
        this.soConsumerIndex(l + 2L);
        return e;
    }

    private E newBufferPeek(E[] EArray, long l) {
        long l2 = this.newBufferAndOffset(EArray, l);
        E e = UnsafeRefArrayAccess.lvElement(EArray, l2);
        if (null == e) {
            throw new IllegalStateException("new buffer must have at least one element");
        }
        return e;
    }

    private long newBufferAndOffset(E[] EArray, long l) {
        this.consumerBuffer = EArray;
        this.consumerMask = LinkedArrayQueueUtil.length(EArray) - 2 << 1;
        return LinkedArrayQueueUtil.modifiedCalcElementOffset(l, this.consumerMask);
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
        Object[] objectArray = this.consumerBuffer;
        long l = this.consumerIndex;
        long l2 = this.consumerMask;
        long l3 = LinkedArrayQueueUtil.modifiedCalcElementOffset(l, l2);
        Object object = UnsafeRefArrayAccess.lvElement(objectArray, l3);
        if (object == null) {
            return null;
        }
        if (object == JUMP) {
            Object[] objectArray2 = this.getNextBuffer(objectArray, l2);
            return (E)this.newBufferPoll(objectArray2, l);
        }
        UnsafeRefArrayAccess.soElement(objectArray, l3, null);
        this.soConsumerIndex(l + 2L);
        return (E)object;
    }

    @Override
    public E relaxedPeek() {
        Object[] objectArray = this.consumerBuffer;
        long l = this.consumerIndex;
        long l2 = this.consumerMask;
        long l3 = LinkedArrayQueueUtil.modifiedCalcElementOffset(l, l2);
        Object object = UnsafeRefArrayAccess.lvElement(objectArray, l3);
        if (object == JUMP) {
            return (E)this.newBufferPeek(this.getNextBuffer(objectArray, l2), l);
        }
        return (E)object;
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
        Object[] objectArray;
        long l2;
        long l3;
        block5: while (true) {
            long l4 = this.lvProducerLimit();
            l3 = this.lvProducerIndex();
            if ((l3 & 1L) == 1L) continue;
            l2 = this.producerMask;
            objectArray = this.producerBuffer;
            l = Math.min(l4, l3 + (long)(2 * n));
            if (l3 >= l4 || l4 < l) {
                int n2 = this.offerSlowPath(l2, l3, l4);
                switch (n2) {
                    case 0: 
                    case 1: {
                        continue block5;
                    }
                    case 2: {
                        return 1;
                    }
                    case 3: {
                        this.resize(l2, objectArray, l3, supplier.get());
                        return 0;
                    }
                }
            }
            if (this.casProducerIndex(l3, l)) break;
        }
        int n3 = (int)((l - l3) / 2L);
        for (int i = 0; i < n3; ++i) {
            long l5 = LinkedArrayQueueUtil.modifiedCalcElementOffset(l3 + (long)(2 * i), l2);
            UnsafeRefArrayAccess.soElement(objectArray, l5, supplier.get());
        }
        return n3;
    }

    @Override
    public void fill(MessagePassingQueue.Supplier<E> supplier, MessagePassingQueue.WaitStrategy waitStrategy, MessagePassingQueue.ExitCondition exitCondition) {
        while (exitCondition.keepRunning()) {
            if (this.fill(supplier, PortableJvmInfo.RECOMENDED_OFFER_BATCH) != 0) continue;
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

    private void resize(long l, E[] EArray, long l2, E e) {
        int n = this.getNextBufferSize(EArray);
        E[] EArray2 = CircularArrayOffsetCalculator.allocate(n);
        this.producerBuffer = EArray2;
        int n2 = n - 2 << 1;
        this.producerMask = n2;
        long l3 = LinkedArrayQueueUtil.modifiedCalcElementOffset(l2, l);
        long l4 = LinkedArrayQueueUtil.modifiedCalcElementOffset(l2, n2);
        UnsafeRefArrayAccess.soElement(EArray2, l4, e);
        UnsafeRefArrayAccess.soElement(EArray, this.nextArrayOffset(l), EArray2);
        long l5 = this.lvConsumerIndex();
        long l6 = this.availableInQueue(l2, l5);
        RangeUtil.checkPositive(l6, "availableInQueue");
        this.soProducerLimit(l2 + Math.min((long)n2, l6));
        this.soProducerIndex(l2 + 2L);
        UnsafeRefArrayAccess.soElement(EArray, l3, JUMP);
    }

    protected abstract int getNextBufferSize(E[] var1);

    protected abstract long getCurrentBufferCapacity(long var1);
}

