/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.internal.shaded.org.jctools.queues;

import io.netty.util.internal.shaded.org.jctools.queues.BaseLinkedQueue;
import io.netty.util.internal.shaded.org.jctools.queues.LinkedQueueNode;
import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue;

public class SpscLinkedQueue<E>
extends BaseLinkedQueue<E> {
    public SpscLinkedQueue() {
        LinkedQueueNode linkedQueueNode = this.newNode();
        this.spProducerNode(linkedQueueNode);
        this.spConsumerNode(linkedQueueNode);
        linkedQueueNode.soNext(null);
    }

    @Override
    public boolean offer(E e) {
        if (null == e) {
            throw new NullPointerException();
        }
        LinkedQueueNode<E> linkedQueueNode = this.newNode(e);
        this.lpProducerNode().soNext(linkedQueueNode);
        this.spProducerNode(linkedQueueNode);
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
        LinkedQueueNode<E> linkedQueueNode;
        if (n == 0) {
            return 1;
        }
        LinkedQueueNode<E> linkedQueueNode2 = linkedQueueNode = this.newNode(supplier.get());
        for (int i = 1; i < n; ++i) {
            LinkedQueueNode<E> linkedQueueNode3 = this.newNode(supplier.get());
            linkedQueueNode.soNext(linkedQueueNode3);
            linkedQueueNode = linkedQueueNode3;
        }
        LinkedQueueNode<E> linkedQueueNode4 = this.lpProducerNode();
        linkedQueueNode4.soNext(linkedQueueNode2);
        this.spProducerNode(linkedQueueNode);
        return n;
    }

    @Override
    public void fill(MessagePassingQueue.Supplier<E> supplier, MessagePassingQueue.WaitStrategy waitStrategy, MessagePassingQueue.ExitCondition exitCondition) {
        LinkedQueueNode<E> linkedQueueNode = this.producerNode;
        while (exitCondition.keepRunning()) {
            for (int i = 0; i < 4096; ++i) {
                LinkedQueueNode<E> linkedQueueNode2 = this.newNode(supplier.get());
                linkedQueueNode.soNext(linkedQueueNode2);
                this.producerNode = linkedQueueNode = linkedQueueNode2;
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

