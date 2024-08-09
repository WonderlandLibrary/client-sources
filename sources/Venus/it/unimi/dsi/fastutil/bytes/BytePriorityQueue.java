/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.PriorityQueue;
import it.unimi.dsi.fastutil.bytes.ByteComparator;
import java.util.Comparator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface BytePriorityQueue
extends PriorityQueue<Byte> {
    @Override
    public void enqueue(byte var1);

    public byte dequeueByte();

    public byte firstByte();

    default public byte lastByte() {
        throw new UnsupportedOperationException();
    }

    public ByteComparator comparator();

    @Override
    @Deprecated
    default public void enqueue(Byte by) {
        this.enqueue((byte)by);
    }

    @Override
    @Deprecated
    default public Byte dequeue() {
        return this.dequeueByte();
    }

    @Override
    @Deprecated
    default public Byte first() {
        return this.firstByte();
    }

    @Override
    @Deprecated
    default public Byte last() {
        return this.lastByte();
    }

    @Override
    default public Comparator comparator() {
        return this.comparator();
    }

    @Override
    @Deprecated
    default public Object last() {
        return this.last();
    }

    @Override
    @Deprecated
    default public Object first() {
        return this.first();
    }

    @Override
    @Deprecated
    default public Object dequeue() {
        return this.dequeue();
    }

    @Override
    @Deprecated
    default public void enqueue(Object object) {
        this.enqueue((Byte)object);
    }
}

