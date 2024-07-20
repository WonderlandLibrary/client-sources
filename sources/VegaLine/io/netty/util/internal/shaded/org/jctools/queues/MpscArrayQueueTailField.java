/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.internal.shaded.org.jctools.queues;

import io.netty.util.internal.shaded.org.jctools.queues.MpscArrayQueueL1Pad;
import io.netty.util.internal.shaded.org.jctools.util.UnsafeAccess;

abstract class MpscArrayQueueTailField<E>
extends MpscArrayQueueL1Pad<E> {
    private static final long P_INDEX_OFFSET;
    private volatile long producerIndex;

    public MpscArrayQueueTailField(int capacity) {
        super(capacity);
    }

    @Override
    public final long lvProducerIndex() {
        return this.producerIndex;
    }

    protected final boolean casProducerIndex(long expect, long newValue) {
        return UnsafeAccess.UNSAFE.compareAndSwapLong(this, P_INDEX_OFFSET, expect, newValue);
    }

    static {
        try {
            P_INDEX_OFFSET = UnsafeAccess.UNSAFE.objectFieldOffset(MpscArrayQueueTailField.class.getDeclaredField("producerIndex"));
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }
}

