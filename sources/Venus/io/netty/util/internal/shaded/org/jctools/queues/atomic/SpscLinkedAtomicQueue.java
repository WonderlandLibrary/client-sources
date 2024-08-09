/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.internal.shaded.org.jctools.queues.atomic;

import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue;
import io.netty.util.internal.shaded.org.jctools.queues.atomic.BaseLinkedAtomicQueue;
import io.netty.util.internal.shaded.org.jctools.queues.atomic.LinkedQueueAtomicNode;

public class SpscLinkedAtomicQueue<E>
extends BaseLinkedAtomicQueue<E> {
    public SpscLinkedAtomicQueue() {
        LinkedQueueAtomicNode linkedQueueAtomicNode = this.newNode();
        this.spProducerNode(linkedQueueAtomicNode);
        this.spConsumerNode(linkedQueueAtomicNode);
        linkedQueueAtomicNode.soNext(null);
    }

    @Override
    public boolean offer(E e) {
        if (null == e) {
            throw new NullPointerException();
        }
        LinkedQueueAtomicNode<E> linkedQueueAtomicNode = this.newNode(e);
        this.lpProducerNode().soNext(linkedQueueAtomicNode);
        this.spProducerNode(linkedQueueAtomicNode);
        return false;
    }

    @Override
    public E poll() {
        return (E)this.relaxedPoll();
    }

    @Override
    public E peek() {
        return (E)this.relaxedPeek();
    }

    @Override
    public int fill(MessagePassingQueue.Supplier<E> supplier) {
        long l = 0L;
        do {
            this.fill(supplier, 4096);
        } while ((l += 4096L) <= 0x7FFFEFFFL);
        return (int)l;
    }

    @Override
    public int fill(MessagePassingQueue.Supplier<E> supplier, int n) {
        LinkedQueueAtomicNode<E> linkedQueueAtomicNode;
        if (n == 0) {
            return 1;
        }
        LinkedQueueAtomicNode<E> linkedQueueAtomicNode2 = linkedQueueAtomicNode = this.newNode(supplier.get());
        for (int i = 1; i < n; ++i) {
            LinkedQueueAtomicNode<E> linkedQueueAtomicNode3 = this.newNode(supplier.get());
            linkedQueueAtomicNode.soNext(linkedQueueAtomicNode3);
            linkedQueueAtomicNode = linkedQueueAtomicNode3;
        }
        LinkedQueueAtomicNode<E> linkedQueueAtomicNode4 = this.lpProducerNode();
        linkedQueueAtomicNode4.soNext(linkedQueueAtomicNode2);
        this.spProducerNode(linkedQueueAtomicNode);
        return n;
    }

    @Override
    public void fill(MessagePassingQueue.Supplier<E> supplier, MessagePassingQueue.WaitStrategy waitStrategy, MessagePassingQueue.ExitCondition exitCondition) {
        LinkedQueueAtomicNode<E> linkedQueueAtomicNode = this.producerNode;
        while (exitCondition.keepRunning()) {
            for (int i = 0; i < 4096; ++i) {
                LinkedQueueAtomicNode<E> linkedQueueAtomicNode2 = this.newNode(supplier.get());
                linkedQueueAtomicNode.soNext(linkedQueueAtomicNode2);
                this.producerNode = linkedQueueAtomicNode = linkedQueueAtomicNode2;
            }
        }
    }

    @Override
    public int capacity() {
        return super.capacity();
    }

    @Override
    public void drain(MessagePassingQueue.Consumer consumer, MessagePassingQueue.WaitStrategy waitStrategy, MessagePassingQueue.ExitCondition exitCondition) {
        super.drain(consumer, waitStrategy, exitCondition);
    }

    @Override
    public int drain(MessagePassingQueue.Consumer consumer, int n) {
        return super.drain(consumer, n);
    }

    @Override
    public int drain(MessagePassingQueue.Consumer consumer) {
        return super.drain(consumer);
    }

    @Override
    public boolean relaxedOffer(Object object) {
        return super.relaxedOffer(object);
    }

    @Override
    public Object relaxedPeek() {
        return super.relaxedPeek();
    }

    @Override
    public Object relaxedPoll() {
        return super.relaxedPoll();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

