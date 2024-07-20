/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.internal.shaded.org.jctools.queues;

import io.netty.util.internal.shaded.org.jctools.queues.MpscArrayQueueMidPad;
import io.netty.util.internal.shaded.org.jctools.util.UnsafeAccess;

abstract class MpscArrayQueueHeadLimitField<E>
extends MpscArrayQueueMidPad<E> {
    private static final long P_LIMIT_OFFSET;
    private volatile long producerLimit;

    public MpscArrayQueueHeadLimitField(int capacity) {
        super(capacity);
        this.producerLimit = capacity;
    }

    protected final long lvProducerLimit() {
        return this.producerLimit;
    }

    protected final void soProducerLimit(long v) {
        UnsafeAccess.UNSAFE.putOrderedLong(this, P_LIMIT_OFFSET, v);
    }

    static {
        try {
            P_LIMIT_OFFSET = UnsafeAccess.UNSAFE.objectFieldOffset(MpscArrayQueueHeadLimitField.class.getDeclaredField("producerLimit"));
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }
}

