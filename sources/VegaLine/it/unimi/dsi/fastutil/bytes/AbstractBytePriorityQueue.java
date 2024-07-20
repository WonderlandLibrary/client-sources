/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.AbstractPriorityQueue;
import it.unimi.dsi.fastutil.bytes.BytePriorityQueue;
import java.io.Serializable;

public abstract class AbstractBytePriorityQueue
extends AbstractPriorityQueue<Byte>
implements Serializable,
BytePriorityQueue {
    private static final long serialVersionUID = 1L;

    @Override
    @Deprecated
    public void enqueue(Byte x) {
        this.enqueue(x.byteValue());
    }

    @Override
    @Deprecated
    public Byte dequeue() {
        return this.dequeueByte();
    }

    @Override
    @Deprecated
    public Byte first() {
        return this.firstByte();
    }

    @Override
    @Deprecated
    public Byte last() {
        return this.lastByte();
    }

    @Override
    public byte lastByte() {
        throw new UnsupportedOperationException();
    }
}

