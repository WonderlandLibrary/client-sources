/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.internal.shaded.org.jctools.queues.atomic;

import io.netty.util.internal.shaded.org.jctools.queues.atomic.BaseLinkedAtomicQueue;
import io.netty.util.internal.shaded.org.jctools.queues.atomic.LinkedQueueAtomicNode;

public final class SpscLinkedAtomicQueue<E>
extends BaseLinkedAtomicQueue<E> {
    public SpscLinkedAtomicQueue() {
        LinkedQueueAtomicNode node = new LinkedQueueAtomicNode();
        this.spProducerNode(node);
        this.spConsumerNode(node);
        node.soNext(null);
    }

    @Override
    public boolean offer(E e) {
        if (null == e) {
            throw new NullPointerException();
        }
        LinkedQueueAtomicNode<E> nextNode = new LinkedQueueAtomicNode<E>(e);
        this.lpProducerNode().soNext(nextNode);
        this.spProducerNode(nextNode);
        return true;
    }

    @Override
    public E poll() {
        LinkedQueueAtomicNode currConsumerNode = this.lpConsumerNode();
        LinkedQueueAtomicNode nextNode = currConsumerNode.lvNext();
        if (nextNode != null) {
            return this.getSingleConsumerNodeValue(currConsumerNode, nextNode);
        }
        return null;
    }

    @Override
    public E peek() {
        LinkedQueueAtomicNode nextNode = this.lpConsumerNode().lvNext();
        if (nextNode != null) {
            return nextNode.lpValue();
        }
        return null;
    }
}

