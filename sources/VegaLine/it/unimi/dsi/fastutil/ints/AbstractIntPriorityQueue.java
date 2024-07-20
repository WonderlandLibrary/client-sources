/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.AbstractPriorityQueue;
import it.unimi.dsi.fastutil.ints.IntPriorityQueue;
import java.io.Serializable;

public abstract class AbstractIntPriorityQueue
extends AbstractPriorityQueue<Integer>
implements Serializable,
IntPriorityQueue {
    private static final long serialVersionUID = 1L;

    @Override
    @Deprecated
    public void enqueue(Integer x) {
        this.enqueue(x.intValue());
    }

    @Override
    @Deprecated
    public Integer dequeue() {
        return this.dequeueInt();
    }

    @Override
    @Deprecated
    public Integer first() {
        return this.firstInt();
    }

    @Override
    @Deprecated
    public Integer last() {
        return this.lastInt();
    }

    @Override
    public int lastInt() {
        throw new UnsupportedOperationException();
    }
}

