/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.internal.shaded.org.jctools.queues.atomic;

import io.netty.util.internal.shaded.org.jctools.queues.atomic.LinkedQueueAtomicNode;
import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReference;

abstract class BaseLinkedAtomicQueue<E>
extends AbstractQueue<E> {
    private final AtomicReference<LinkedQueueAtomicNode<E>> producerNode = new AtomicReference();
    private final AtomicReference<LinkedQueueAtomicNode<E>> consumerNode = new AtomicReference();

    protected final LinkedQueueAtomicNode<E> lvProducerNode() {
        return this.producerNode.get();
    }

    protected final LinkedQueueAtomicNode<E> lpProducerNode() {
        return this.producerNode.get();
    }

    protected final void spProducerNode(LinkedQueueAtomicNode<E> node) {
        this.producerNode.lazySet(node);
    }

    protected final LinkedQueueAtomicNode<E> xchgProducerNode(LinkedQueueAtomicNode<E> node) {
        return this.producerNode.getAndSet(node);
    }

    protected final LinkedQueueAtomicNode<E> lvConsumerNode() {
        return this.consumerNode.get();
    }

    protected final LinkedQueueAtomicNode<E> lpConsumerNode() {
        return this.consumerNode.get();
    }

    protected final void spConsumerNode(LinkedQueueAtomicNode<E> node) {
        this.consumerNode.lazySet(node);
    }

    @Override
    public final Iterator<E> iterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return this.getClass().getName();
    }

    @Override
    public final int size() {
        int size;
        LinkedQueueAtomicNode<E> chaserNode = this.lvConsumerNode();
        LinkedQueueAtomicNode<E> producerNode = this.lvProducerNode();
        for (size = 0; chaserNode != producerNode && chaserNode != null && size < Integer.MAX_VALUE; ++size) {
            LinkedQueueAtomicNode<E> next = chaserNode.lvNext();
            if (next == chaserNode) {
                return size;
            }
            chaserNode = next;
        }
        return size;
    }

    @Override
    public final boolean isEmpty() {
        return this.lvConsumerNode() == this.lvProducerNode();
    }

    protected E getSingleConsumerNodeValue(LinkedQueueAtomicNode<E> currConsumerNode, LinkedQueueAtomicNode<E> nextNode) {
        E nextValue = nextNode.getAndNullValue();
        currConsumerNode.soNext(currConsumerNode);
        this.spConsumerNode(nextNode);
        return nextValue;
    }
}

