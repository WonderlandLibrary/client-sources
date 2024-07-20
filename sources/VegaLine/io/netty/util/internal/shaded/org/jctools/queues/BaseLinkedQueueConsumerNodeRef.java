/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.internal.shaded.org.jctools.queues;

import io.netty.util.internal.shaded.org.jctools.queues.BaseLinkedQueuePad1;
import io.netty.util.internal.shaded.org.jctools.queues.LinkedQueueNode;
import io.netty.util.internal.shaded.org.jctools.util.UnsafeAccess;

abstract class BaseLinkedQueueConsumerNodeRef<E>
extends BaseLinkedQueuePad1<E> {
    protected static final long C_NODE_OFFSET;
    protected LinkedQueueNode<E> consumerNode;

    BaseLinkedQueueConsumerNodeRef() {
    }

    protected final void spConsumerNode(LinkedQueueNode<E> node) {
        this.consumerNode = node;
    }

    protected final LinkedQueueNode<E> lvConsumerNode() {
        return (LinkedQueueNode)UnsafeAccess.UNSAFE.getObjectVolatile(this, C_NODE_OFFSET);
    }

    protected final LinkedQueueNode<E> lpConsumerNode() {
        return this.consumerNode;
    }

    static {
        try {
            C_NODE_OFFSET = UnsafeAccess.UNSAFE.objectFieldOffset(BaseLinkedQueueConsumerNodeRef.class.getDeclaredField("consumerNode"));
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }
}

