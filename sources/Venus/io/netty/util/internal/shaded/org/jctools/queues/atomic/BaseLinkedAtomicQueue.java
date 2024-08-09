/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.internal.shaded.org.jctools.queues.atomic;

import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue;
import io.netty.util.internal.shaded.org.jctools.queues.atomic.BaseLinkedAtomicQueuePad2;
import io.netty.util.internal.shaded.org.jctools.queues.atomic.LinkedQueueAtomicNode;
import java.util.Iterator;

abstract class BaseLinkedAtomicQueue<E>
extends BaseLinkedAtomicQueuePad2<E> {
    BaseLinkedAtomicQueue() {
    }

    @Override
    public final Iterator<E> iterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return this.getClass().getName();
    }

    protected final LinkedQueueAtomicNode<E> newNode() {
        return new LinkedQueueAtomicNode();
    }

    protected final LinkedQueueAtomicNode<E> newNode(E e) {
        return new LinkedQueueAtomicNode<E>(e);
    }

    @Override
    public final int size() {
        int n;
        LinkedQueueAtomicNode linkedQueueAtomicNode = this.lvConsumerNode();
        LinkedQueueAtomicNode linkedQueueAtomicNode2 = this.lvProducerNode();
        for (n = 0; linkedQueueAtomicNode != linkedQueueAtomicNode2 && linkedQueueAtomicNode != null && n < Integer.MAX_VALUE; ++n) {
            LinkedQueueAtomicNode linkedQueueAtomicNode3 = linkedQueueAtomicNode.lvNext();
            if (linkedQueueAtomicNode3 == linkedQueueAtomicNode) {
                return n;
            }
            linkedQueueAtomicNode = linkedQueueAtomicNode3;
        }
        return n;
    }

    @Override
    public final boolean isEmpty() {
        return this.lvConsumerNode() == this.lvProducerNode();
    }

    protected E getSingleConsumerNodeValue(LinkedQueueAtomicNode<E> linkedQueueAtomicNode, LinkedQueueAtomicNode<E> linkedQueueAtomicNode2) {
        E e = linkedQueueAtomicNode2.getAndNullValue();
        linkedQueueAtomicNode.soNext(linkedQueueAtomicNode);
        this.spConsumerNode(linkedQueueAtomicNode2);
        return e;
    }

    @Override
    public E relaxedPoll() {
        LinkedQueueAtomicNode linkedQueueAtomicNode = this.lpConsumerNode();
        LinkedQueueAtomicNode linkedQueueAtomicNode2 = linkedQueueAtomicNode.lvNext();
        if (linkedQueueAtomicNode2 != null) {
            return this.getSingleConsumerNodeValue(linkedQueueAtomicNode, linkedQueueAtomicNode2);
        }
        return null;
    }

    @Override
    public E relaxedPeek() {
        LinkedQueueAtomicNode linkedQueueAtomicNode = this.lpConsumerNode().lvNext();
        if (linkedQueueAtomicNode != null) {
            return linkedQueueAtomicNode.lpValue();
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
        LinkedQueueAtomicNode linkedQueueAtomicNode = this.consumerNode;
        for (int i = 0; i < n; ++i) {
            LinkedQueueAtomicNode linkedQueueAtomicNode2 = linkedQueueAtomicNode.lvNext();
            if (linkedQueueAtomicNode2 == null) {
                return i;
            }
            Object e = this.getSingleConsumerNodeValue(linkedQueueAtomicNode, linkedQueueAtomicNode2);
            linkedQueueAtomicNode = linkedQueueAtomicNode2;
            consumer.accept(e);
        }
        return n;
    }

    @Override
    public void drain(MessagePassingQueue.Consumer<E> consumer, MessagePassingQueue.WaitStrategy waitStrategy, MessagePassingQueue.ExitCondition exitCondition) {
        LinkedQueueAtomicNode linkedQueueAtomicNode = this.consumerNode;
        int n = 0;
        while (exitCondition.keepRunning()) {
            for (int i = 0; i < 4096; ++i) {
                LinkedQueueAtomicNode linkedQueueAtomicNode2 = linkedQueueAtomicNode.lvNext();
                if (linkedQueueAtomicNode2 == null) {
                    n = waitStrategy.idle(n);
                    continue;
                }
                n = 0;
                Object e = this.getSingleConsumerNodeValue(linkedQueueAtomicNode, linkedQueueAtomicNode2);
                linkedQueueAtomicNode = linkedQueueAtomicNode2;
                consumer.accept(e);
            }
        }
    }

    @Override
    public int capacity() {
        return 1;
    }
}

