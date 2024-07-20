/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.AbstractPriorityQueue;
import it.unimi.dsi.fastutil.longs.LongPriorityQueue;
import java.io.Serializable;

public abstract class AbstractLongPriorityQueue
extends AbstractPriorityQueue<Long>
implements Serializable,
LongPriorityQueue {
    private static final long serialVersionUID = 1L;

    @Override
    @Deprecated
    public void enqueue(Long x) {
        this.enqueue(x.longValue());
    }

    @Override
    @Deprecated
    public Long dequeue() {
        return this.dequeueLong();
    }

    @Override
    @Deprecated
    public Long first() {
        return this.firstLong();
    }

    @Override
    @Deprecated
    public Long last() {
        return this.lastLong();
    }

    @Override
    public long lastLong() {
        throw new UnsupportedOperationException();
    }
}

