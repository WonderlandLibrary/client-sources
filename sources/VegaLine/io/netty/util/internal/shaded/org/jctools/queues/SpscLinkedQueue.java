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
        this.spProducerNode(new LinkedQueueNode());
        this.spConsumerNode(this.producerNode);
        this.consumerNode.soNext(null);
    }

    @Override
    public boolean offer(E e) {
        if (null == e) {
            throw new NullPointerException();
        }
        LinkedQueueNode<E> nextNode = new LinkedQueueNode<E>(e);
        LinkedQueueNode<E> producerNode = this.lpProducerNode();
        producerNode.soNext(nextNode);
        this.spProducerNode(nextNode);
        return true;
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
    public int fill(MessagePassingQueue.Supplier<E> s) {
        long result = 0L;
        do {
            this.fill(s, 4096);
        } while ((result += 4096L) <= 0x7FFFEFFFL);
        return (int)result;
    }

    @Override
    public int fill(MessagePassingQueue.Supplier<E> s, int limit) {
        LinkedQueueNode<E> tail;
        if (limit == 0) {
            return 0;
        }
        LinkedQueueNode<E> head = tail = new LinkedQueueNode<E>(s.get());
        for (int i = 1; i < limit; ++i) {
            LinkedQueueNode<E> temp = new LinkedQueueNode<E>(s.get());
            tail.soNext(temp);
            tail = temp;
        }
        LinkedQueueNode<E> oldPNode = this.lpProducerNode();
        oldPNode.soNext(head);
        this.spProducerNode(tail);
        return limit;
    }

    @Override
    public void fill(MessagePassingQueue.Supplier<E> s, MessagePassingQueue.WaitStrategy wait, MessagePassingQueue.ExitCondition exit) {
        LinkedQueueNode<E> chaserNode = this.producerNode;
        while (exit.keepRunning()) {
            for (int i = 0; i < 4096; ++i) {
                LinkedQueueNode<E> nextNode = new LinkedQueueNode<E>(s.get());
                chaserNode.soNext(nextNode);
                this.producerNode = chaserNode = nextNode;
            }
        }
    }
}

