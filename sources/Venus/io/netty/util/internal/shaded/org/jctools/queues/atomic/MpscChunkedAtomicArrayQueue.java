/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.internal.shaded.org.jctools.queues.atomic;

import io.netty.util.internal.shaded.org.jctools.queues.atomic.LinkedAtomicArrayQueueUtil;
import io.netty.util.internal.shaded.org.jctools.queues.atomic.MpscChunkedAtomicArrayQueueColdProducerFields;
import io.netty.util.internal.shaded.org.jctools.util.Pow2;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class MpscChunkedAtomicArrayQueue<E>
extends MpscChunkedAtomicArrayQueueColdProducerFields<E> {
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

    public MpscChunkedAtomicArrayQueue(int n) {
        super(Math.max(2, Math.min(1024, Pow2.roundToPowerOfTwo(n / 8))), n);
    }

    public MpscChunkedAtomicArrayQueue(int n, int n2) {
        super(n, n2);
    }

    @Override
    protected long availableInQueue(long l, long l2) {
        return this.maxQueueCapacity - (l - l2);
    }

    @Override
    public int capacity() {
        return (int)(this.maxQueueCapacity / 2L);
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

