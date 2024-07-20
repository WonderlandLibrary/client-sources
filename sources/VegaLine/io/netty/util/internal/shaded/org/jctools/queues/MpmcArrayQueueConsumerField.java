/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.internal.shaded.org.jctools.queues;

import io.netty.util.internal.shaded.org.jctools.queues.MpmcArrayQueueL2Pad;
import io.netty.util.internal.shaded.org.jctools.util.UnsafeAccess;

abstract class MpmcArrayQueueConsumerField<E>
extends MpmcArrayQueueL2Pad<E> {
    private static final long C_INDEX_OFFSET;
    private volatile long consumerIndex;

    public MpmcArrayQueueConsumerField(int capacity) {
        super(capacity);
    }

    @Override
    public final long lvConsumerIndex() {
        return this.consumerIndex;
    }

    protected final boolean casConsumerIndex(long expect, long newValue) {
        return UnsafeAccess.UNSAFE.compareAndSwapLong(this, C_INDEX_OFFSET, expect, newValue);
    }

    static {
        try {
            C_INDEX_OFFSET = UnsafeAccess.UNSAFE.objectFieldOffset(MpmcArrayQueueConsumerField.class.getDeclaredField("consumerIndex"));
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }
}

