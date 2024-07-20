/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.internal.shaded.org.jctools.queues;

import io.netty.util.internal.shaded.org.jctools.queues.BaseMpscLinkedArrayQueuePad1;

abstract class BaseMpscLinkedArrayQueueProducerFields<E>
extends BaseMpscLinkedArrayQueuePad1<E> {
    protected long producerIndex;

    BaseMpscLinkedArrayQueueProducerFields() {
    }
}

