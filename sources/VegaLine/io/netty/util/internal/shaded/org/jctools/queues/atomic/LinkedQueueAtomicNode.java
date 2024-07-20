/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.internal.shaded.org.jctools.queues.atomic;

import java.util.concurrent.atomic.AtomicReference;

public final class LinkedQueueAtomicNode<E>
extends AtomicReference<LinkedQueueAtomicNode<E>> {
    private static final long serialVersionUID = 2404266111789071508L;
    private E value;

    LinkedQueueAtomicNode() {
    }

    LinkedQueueAtomicNode(E val) {
        this.spValue(val);
    }

    public E getAndNullValue() {
        E temp = this.lpValue();
        this.spValue(null);
        return temp;
    }

    public E lpValue() {
        return this.value;
    }

    public void spValue(E newValue) {
        this.value = newValue;
    }

    public void soNext(LinkedQueueAtomicNode<E> n) {
        this.lazySet(n);
    }

    public LinkedQueueAtomicNode<E> lvNext() {
        return (LinkedQueueAtomicNode)this.get();
    }
}

