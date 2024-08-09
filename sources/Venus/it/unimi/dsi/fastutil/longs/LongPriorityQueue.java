/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.PriorityQueue;
import it.unimi.dsi.fastutil.longs.LongComparator;
import java.util.Comparator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface LongPriorityQueue
extends PriorityQueue<Long> {
    @Override
    public void enqueue(long var1);

    public long dequeueLong();

    public long firstLong();

    default public long lastLong() {
        throw new UnsupportedOperationException();
    }

    public LongComparator comparator();

    @Override
    @Deprecated
    default public void enqueue(Long l) {
        this.enqueue((long)l);
    }

    @Override
    @Deprecated
    default public Long dequeue() {
        return this.dequeueLong();
    }

    @Override
    @Deprecated
    default public Long first() {
        return this.firstLong();
    }

    @Override
    @Deprecated
    default public Long last() {
        return this.lastLong();
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
        this.enqueue((Long)object);
    }
}

