/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.AbstractPriorityQueue;
import it.unimi.dsi.fastutil.doubles.DoublePriorityQueue;
import java.io.Serializable;

public abstract class AbstractDoublePriorityQueue
extends AbstractPriorityQueue<Double>
implements Serializable,
DoublePriorityQueue {
    private static final long serialVersionUID = 1L;

    @Override
    @Deprecated
    public void enqueue(Double x) {
        this.enqueue(x.doubleValue());
    }

    @Override
    @Deprecated
    public Double dequeue() {
        return this.dequeueDouble();
    }

    @Override
    @Deprecated
    public Double first() {
        return this.firstDouble();
    }

    @Override
    @Deprecated
    public Double last() {
        return this.lastDouble();
    }

    @Override
    public double lastDouble() {
        throw new UnsupportedOperationException();
    }
}

