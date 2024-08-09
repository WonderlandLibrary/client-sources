/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.PriorityQueue;
import it.unimi.dsi.fastutil.ints.IntComparator;
import java.util.Comparator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface IntPriorityQueue
extends PriorityQueue<Integer> {
    @Override
    public void enqueue(int var1);

    public int dequeueInt();

    public int firstInt();

    default public int lastInt() {
        throw new UnsupportedOperationException();
    }

    public IntComparator comparator();

    @Override
    @Deprecated
    default public void enqueue(Integer n) {
        this.enqueue((int)n);
    }

    @Override
    @Deprecated
    default public Integer dequeue() {
        return this.dequeueInt();
    }

    @Override
    @Deprecated
    default public Integer first() {
        return this.firstInt();
    }

    @Override
    @Deprecated
    default public Integer last() {
        return this.lastInt();
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
        this.enqueue((Integer)object);
    }
}

