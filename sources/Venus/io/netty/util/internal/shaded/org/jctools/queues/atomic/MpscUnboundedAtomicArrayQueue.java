/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.internal.shaded.org.jctools.queues.atomic;

import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue;
import io.netty.util.internal.shaded.org.jctools.queues.atomic.BaseMpscLinkedAtomicArrayQueue;
import io.netty.util.internal.shaded.org.jctools.queues.atomic.LinkedAtomicArrayQueueUtil;
import io.netty.util.internal.shaded.org.jctools.util.PortableJvmInfo;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class MpscUnboundedAtomicArrayQueue<E>
extends BaseMpscLinkedAtomicArrayQueue<E> {
    long p0;
    long p1;
    long p2;
    long p3;
    long p4;
    long p5;
    long p6;
    long p7;
    long p10;
    long p11;
    long p12;
    long p13;
    long p14;
    long p15;
    long p16;
    long p17;

    public MpscUnboundedAtomicArrayQueue(int n) {
        super(n);
    }

    @Override
    protected long availableInQueue(long l, long l2) {
        return Integer.MAX_VALUE;
    }

    @Override
    public int capacity() {
        return 1;
    }

    @Override
    public int drain(MessagePassingQueue.Consumer<E> consumer) {
        return this.drain(consumer, 4096);
    }

    @Override
    public int fill(MessagePassingQueue.Supplier<E> supplier) {
        int n;
        long l = 0L;
        int n2 = 4096;
        do {
            if ((n = this.fill(supplier, PortableJvmInfo.RECOMENDED_OFFER_BATCH)) != 0) continue;
            return (int)l;
        } while ((l += (long)n) <= 4096L);
        return (int)l;
    }

    @Override
    protected int getNextBufferSize(AtomicReferenceArray<E> atomicReferenceArray) {
        return LinkedAtomicArrayQueueUtil.length(atomicReferenceArray);
    }

    @Override
    protected long getCurrentBufferCapacity(long l) {
        return l;
    }
}

