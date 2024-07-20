/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.AbstractPriorityQueue;
import it.unimi.dsi.fastutil.shorts.ShortPriorityQueue;
import java.io.Serializable;

public abstract class AbstractShortPriorityQueue
extends AbstractPriorityQueue<Short>
implements Serializable,
ShortPriorityQueue {
    private static final long serialVersionUID = 1L;

    @Override
    @Deprecated
    public void enqueue(Short x) {
        this.enqueue(x.shortValue());
    }

    @Override
    @Deprecated
    public Short dequeue() {
        return this.dequeueShort();
    }

    @Override
    @Deprecated
    public Short first() {
        return this.firstShort();
    }

    @Override
    @Deprecated
    public Short last() {
        return this.lastShort();
    }

    @Override
    public short lastShort() {
        throw new UnsupportedOperationException();
    }
}

