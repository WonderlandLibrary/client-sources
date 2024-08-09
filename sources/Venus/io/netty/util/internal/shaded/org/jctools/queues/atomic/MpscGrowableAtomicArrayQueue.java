/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.internal.shaded.org.jctools.queues.atomic;

import io.netty.util.internal.shaded.org.jctools.queues.atomic.LinkedAtomicArrayQueueUtil;
import io.netty.util.internal.shaded.org.jctools.queues.atomic.MpscChunkedAtomicArrayQueue;
import io.netty.util.internal.shaded.org.jctools.util.Pow2;
import io.netty.util.internal.shaded.org.jctools.util.RangeUtil;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class MpscGrowableAtomicArrayQueue<E>
extends MpscChunkedAtomicArrayQueue<E> {
    public MpscGrowableAtomicArrayQueue(int n) {
        super(Math.max(2, Pow2.roundToPowerOfTwo(n / 8)), n);
    }

    public MpscGrowableAtomicArrayQueue(int n, int n2) {
        super(n, n2);
    }

    @Override
    protected int getNextBufferSize(AtomicReferenceArray<E> atomicReferenceArray) {
        long l = this.maxQueueCapacity / 2L;
        RangeUtil.checkLessThanOrEqual(LinkedAtomicArrayQueueUtil.length(atomicReferenceArray), l, "buffer.length");
        int n = 2 * (LinkedAtomicArrayQueueUtil.length(atomicReferenceArray) - 1);
        return n + 1;
    }

    @Override
    protected long getCurrentBufferCapacity(long l) {
        return l + 2L == this.maxQueueCapacity ? this.maxQueueCapacity : l;
    }
}

