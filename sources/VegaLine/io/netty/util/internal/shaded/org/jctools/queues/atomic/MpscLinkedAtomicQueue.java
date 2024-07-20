/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.internal.shaded.org.jctools.queues.atomic;

import io.netty.util.internal.shaded.org.jctools.queues.atomic.BaseLinkedAtomicQueue;
import io.netty.util.internal.shaded.org.jctools.queues.atomic.LinkedQueueAtomicNode;

public final class MpscLinkedAtomicQueue<E>
extends BaseLinkedAtomicQueue<E> {
    public MpscLinkedAtomicQueue() {
        LinkedQueueAtomicNode node = new LinkedQueueAtomicNode();
        this.spConsumerNode(node);
        this.xchgProducerNode(node);
    }

    @Override
    public final boolean offer(E e) {
        if (null == e) {
            throw new NullPointerException();
        }
        LinkedQueueAtomicNode<E> nextNode = new LinkedQueueAtomicNode<E>(e);
        LinkedQueueAtomicNode<E> prevProducerNode = this.xchgProducerNode(nextNode);
        prevProducerNode.soNext(nextNode);
        return true;
    }

    @Override
    public final E poll() {
        LinkedQueueAtomicNode currConsumerNode = this.lpConsumerNode();
        LinkedQueueAtomicNode nextNode = currConsumerNode.lvNext();
        if (nextNode != null) {
            return this.getSingleConsumerNodeValue(currConsumerNode, nextNode);
        }
        if (currConsumerNode != this.lvProducerNode()) {
            while ((nextNode = currConsumerNode.lvNext()) == null) {
            }
            return this.getSingleConsumerNodeValue(currConsumerNode, nextNode);
        }
        return null;
    }

    @Override
    public final E peek() {
        LinkedQueueAtomicNode currConsumerNode = this.lpConsumerNode();
        LinkedQueueAtomicNode nextNode = currConsumerNode.lvNext();
        if (nextNode != null) {
            return nextNode.lpValue();
        }
        if (currConsumerNode != this.lvProducerNode()) {
            while ((nextNode = currConsumerNode.lvNext()) == null) {
            }
            return nextNode.lpValue();
        }
        return null;
    }
}

