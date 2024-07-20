/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.internal.shaded.org.jctools.queues;

import io.netty.util.internal.shaded.org.jctools.queues.BaseLinkedQueueConsumerNodeRef;
import io.netty.util.internal.shaded.org.jctools.queues.LinkedQueueNode;
import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue;
import java.util.Iterator;

abstract class BaseLinkedQueue<E>
extends BaseLinkedQueueConsumerNodeRef<E> {
    long p01;
    long p02;
    long p03;
    long p04;
    long p05;
    long p06;
    long p07;
    long p10;
    long p11;
    long p12;
    long p13;
    long p14;
    long p15;
    long p16;
    long p17;

    BaseLinkedQueue() {
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
        LinkedQueueNode chaserNode = this.lvConsumerNode();
        LinkedQueueNode producerNode = this.lvProducerNode();
        for (size = 0; chaserNode != producerNode && chaserNode != null && size < Integer.MAX_VALUE; ++size) {
            LinkedQueueNode next = chaserNode.lvNext();
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

    @Override
    public int capacity() {
        return -1;
    }

    protected E getSingleConsumerNodeValue(LinkedQueueNode<E> currConsumerNode, LinkedQueueNode<E> nextNode) {
        E nextValue = nextNode.getAndNullValue();
        currConsumerNode.soNext(currConsumerNode);
        this.spConsumerNode(nextNode);
        return nextValue;
    }

    @Override
    public E relaxedPoll() {
        LinkedQueueNode currConsumerNode = this.lpConsumerNode();
        LinkedQueueNode nextNode = currConsumerNode.lvNext();
        if (nextNode != null) {
            return this.getSingleConsumerNodeValue(currConsumerNode, nextNode);
        }
        return null;
    }

    @Override
    public int drain(MessagePassingQueue.Consumer<E> c) {
        int drained;
        long result = 0L;
        while ((drained = this.drain(c, 4096)) == 4096 && (result += (long)drained) <= 0x7FFFEFFFL) {
        }
        return (int)result;
    }

    @Override
    public int drain(MessagePassingQueue.Consumer<E> c, int limit) {
        LinkedQueueNode chaserNode = this.consumerNode;
        for (int i = 0; i < limit; ++i) {
            LinkedQueueNode nextNode = chaserNode.lvNext();
            if (nextNode == null) {
                return i;
            }
            Object nextValue = this.getSingleConsumerNodeValue(chaserNode, nextNode);
            chaserNode = nextNode;
            c.accept(nextValue);
        }
        return limit;
    }

    @Override
    public void drain(MessagePassingQueue.Consumer<E> c, MessagePassingQueue.WaitStrategy wait, MessagePassingQueue.ExitCondition exit) {
        LinkedQueueNode chaserNode = this.consumerNode;
        int idleCounter = 0;
        while (exit.keepRunning()) {
            for (int i = 0; i < 4096; ++i) {
                LinkedQueueNode nextNode = chaserNode.lvNext();
                if (nextNode == null) {
                    idleCounter = wait.idle(idleCounter);
                    continue;
                }
                idleCounter = 0;
                Object nextValue = this.getSingleConsumerNodeValue(chaserNode, nextNode);
                chaserNode = nextNode;
                c.accept(nextValue);
            }
        }
    }

    @Override
    public E relaxedPeek() {
        LinkedQueueNode currConsumerNode = this.consumerNode;
        LinkedQueueNode nextNode = currConsumerNode.lvNext();
        if (nextNode != null) {
            return nextNode.lpValue();
        }
        return null;
    }

    @Override
    public boolean relaxedOffer(E e) {
        return this.offer(e);
    }
}

