/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.internal.shaded.org.jctools.queues;

import io.netty.util.internal.shaded.org.jctools.queues.BaseLinkedQueuePad0;
import io.netty.util.internal.shaded.org.jctools.queues.LinkedQueueNode;
import io.netty.util.internal.shaded.org.jctools.util.UnsafeAccess;

abstract class BaseLinkedQueueProducerNodeRef<E>
extends BaseLinkedQueuePad0<E> {
    protected static final long P_NODE_OFFSET;
    protected LinkedQueueNode<E> producerNode;

    BaseLinkedQueueProducerNodeRef() {
    }

    protected final void spProducerNode(LinkedQueueNode<E> node) {
        this.producerNode = node;
    }

    protected final LinkedQueueNode<E> lvProducerNode() {
        return (LinkedQueueNode)UnsafeAccess.UNSAFE.getObjectVolatile(this, P_NODE_OFFSET);
    }

    protected final LinkedQueueNode<E> lpProducerNode() {
        return this.producerNode;
    }

    static {
        try {
            P_NODE_OFFSET = UnsafeAccess.UNSAFE.objectFieldOffset(BaseLinkedQueueProducerNodeRef.class.getDeclaredField("producerNode"));
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }
}

