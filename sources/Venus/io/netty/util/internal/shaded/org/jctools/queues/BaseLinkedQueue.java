/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.internal.shaded.org.jctools.queues;

import io.netty.util.internal.shaded.org.jctools.queues.BaseLinkedQueuePad2;
import io.netty.util.internal.shaded.org.jctools.queues.LinkedQueueNode;
import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue;
import java.util.Iterator;

abstract class BaseLinkedQueue<E>
extends BaseLinkedQueuePad2<E> {
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

    protected final LinkedQueueNode<E> newNode() {
        return new LinkedQueueNode();
    }

    protected final LinkedQueueNode<E> newNode(E e) {
        return new LinkedQueueNode<E>(e);
    }

    @Override
    public final int size() {
        int n;
        LinkedQueueNode linkedQueueNode = this.lvConsumerNode();
        LinkedQueueNode linkedQueueNode2 = this.lvProducerNode();
        for (n = 0; linkedQueueNode != linkedQueueNode2 && linkedQueueNode != null && n < Integer.MAX_VALUE; ++n) {
            LinkedQueueNode linkedQueueNode3 = linkedQueueNode.lvNext();
            if (linkedQueueNode3 == linkedQueueNode) {
                return n;
            }
            linkedQueueNode = linkedQueueNode3;
        }
        return n;
    }

    @Override
    public final boolean isEmpty() {
        return this.lvConsumerNode() == this.lvProducerNode();
    }

    protected E getSingleConsumerNodeValue(LinkedQueueNode<E> linkedQueueNode, LinkedQueueNode<E> linkedQueueNode2) {
        E e = linkedQueueNode2.getAndNullValue();
        linkedQueueNode.soNext(linkedQueueNode);
        this.spConsumerNode(linkedQueueNode2);
        return e;
    }

    @Override
    public E relaxedPoll() {
        LinkedQueueNode linkedQueueNode = this.lpConsumerNode();
        LinkedQueueNode linkedQueueNode2 = linkedQueueNode.lvNext();
        if (linkedQueueNode2 != null) {
            return this.getSingleConsumerNodeValue(linkedQueueNode, linkedQueueNode2);
        }
        return null;
    }

    @Override
    public E relaxedPeek() {
        LinkedQueueNode linkedQueueNode = this.lpConsumerNode().lvNext();
        if (linkedQueueNode != null) {
            return linkedQueueNode.lpValue();
        }
        return null;
    }

    @Override
    public boolean relaxedOffer(E e) {
        return this.offer(e);
    }

    @Override
    public int drain(MessagePassingQueue.Consumer<E> consumer) {
        int n;
        long l = 0L;
        while ((n = this.drain(consumer, 4096)) == 4096 && (l += (long)n) <= 0x7FFFEFFFL) {
        }
        return (int)l;
    }

    @Override
    public int drain(MessagePassingQueue.Consumer<E> consumer, int n) {
        LinkedQueueNode linkedQueueNode = this.consumerNode;
        for (int i = 0; i < n; ++i) {
            LinkedQueueNode linkedQueueNode2 = linkedQueueNode.lvNext();
            if (linkedQueueNode2 == null) {
                return i;
            }
            Object e = this.getSingleConsumerNodeValue(linkedQueueNode, linkedQueueNode2);
            linkedQueueNode = linkedQueueNode2;
            consumer.accept(e);
        }
        return n;
    }

    @Override
    public void drain(MessagePassingQueue.Consumer<E> consumer, MessagePassingQueue.WaitStrategy waitStrategy, MessagePassingQueue.ExitCondition exitCondition) {
        LinkedQueueNode linkedQueueNode = this.consumerNode;
        int n = 0;
        while (exitCondition.keepRunning()) {
            for (int i = 0; i < 4096; ++i) {
                LinkedQueueNode linkedQueueNode2 = linkedQueueNode.lvNext();
                if (linkedQueueNode2 == null) {
                    n = waitStrategy.idle(n);
                    continue;
                }
                n = 0;
                Object e = this.getSingleConsumerNodeValue(linkedQueueNode, linkedQueueNode2);
                linkedQueueNode = linkedQueueNode2;
                consumer.accept(e);
            }
        }
    }

    @Override
    public int capacity() {
        return 1;
    }
}

