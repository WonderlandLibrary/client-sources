/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.AbstractPriorityQueue;
import it.unimi.dsi.fastutil.floats.FloatPriorityQueue;
import java.io.Serializable;

public abstract class AbstractFloatPriorityQueue
extends AbstractPriorityQueue<Float>
implements Serializable,
FloatPriorityQueue {
    private static final long serialVersionUID = 1L;

    @Override
    @Deprecated
    public void enqueue(Float x) {
        this.enqueue(x.floatValue());
    }

    @Override
    @Deprecated
    public Float dequeue() {
        return Float.valueOf(this.dequeueFloat());
    }

    @Override
    @Deprecated
    public Float first() {
        return Float.valueOf(this.firstFloat());
    }

    @Override
    @Deprecated
    public Float last() {
        return Float.valueOf(this.lastFloat());
    }

    @Override
    public float lastFloat() {
        throw new UnsupportedOperationException();
    }
}

