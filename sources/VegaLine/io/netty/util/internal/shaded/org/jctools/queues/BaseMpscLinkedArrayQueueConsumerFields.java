/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.internal.shaded.org.jctools.queues;

import io.netty.util.internal.shaded.org.jctools.queues.BaseMpscLinkedArrayQueuePad2;

abstract class BaseMpscLinkedArrayQueueConsumerFields<E>
extends BaseMpscLinkedArrayQueuePad2<E> {
    protected long consumerMask;
    protected E[] consumerBuffer;
    protected long consumerIndex;

    BaseMpscLinkedArrayQueueConsumerFields() {
    }
}

