/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.internal.shaded.org.jctools.queues;

import io.netty.util.internal.shaded.org.jctools.queues.BaseMpscLinkedArrayQueuePad3;

abstract class BaseMpscLinkedArrayQueueColdProducerFields<E>
extends BaseMpscLinkedArrayQueuePad3<E> {
    protected volatile long producerLimit;
    protected long producerMask;
    protected E[] producerBuffer;

    BaseMpscLinkedArrayQueueColdProducerFields() {
    }
}

