/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.PriorityQueue;
import it.unimi.dsi.fastutil.shorts.ShortComparator;
import java.util.Comparator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface ShortPriorityQueue
extends PriorityQueue<Short> {
    @Override
    public void enqueue(short var1);

    public short dequeueShort();

    public short firstShort();

    default public short lastShort() {
        throw new UnsupportedOperationException();
    }

    public ShortComparator comparator();

    @Override
    @Deprecated
    default public void enqueue(Short s) {
        this.enqueue((short)s);
    }

    @Override
    @Deprecated
    default public Short dequeue() {
        return this.dequeueShort();
    }

    @Override
    @Deprecated
    default public Short first() {
        return this.firstShort();
    }

    @Override
    @Deprecated
    default public Short last() {
        return this.lastShort();
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
        this.enqueue((Short)object);
    }
}

