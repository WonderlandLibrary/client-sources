/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.internal.shaded.org.jctools.queues;

import io.netty.util.internal.shaded.org.jctools.queues.BaseMpscLinkedArrayQueue;
import io.netty.util.internal.shaded.org.jctools.util.Pow2;
import io.netty.util.internal.shaded.org.jctools.util.RangeUtil;

abstract class MpscChunkedArrayQueueColdProducerFields<E>
extends BaseMpscLinkedArrayQueue<E> {
    protected final long maxQueueCapacity;

    public MpscChunkedArrayQueueColdProducerFields(int n, int n2) {
        super(n);
        RangeUtil.checkGreaterThanOrEqual(n2, 4, "maxCapacity");
        RangeUtil.checkLessThan(Pow2.roundToPowerOfTwo(n), Pow2.roundToPowerOfTwo(n2), "initialCapacity");
        this.maxQueueCapacity = (long)Pow2.roundToPowerOfTwo(n2) << 1;
    }
}

