/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.PriorityQueue;
import it.unimi.dsi.fastutil.doubles.DoubleComparator;
import java.util.Comparator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface DoublePriorityQueue
extends PriorityQueue<Double> {
    @Override
    public void enqueue(double var1);

    public double dequeueDouble();

    public double firstDouble();

    default public double lastDouble() {
        throw new UnsupportedOperationException();
    }

    public DoubleComparator comparator();

    @Override
    @Deprecated
    default public void enqueue(Double d) {
        this.enqueue((double)d);
    }

    @Override
    @Deprecated
    default public Double dequeue() {
        return this.dequeueDouble();
    }

    @Override
    @Deprecated
    default public Double first() {
        return this.firstDouble();
    }

    @Override
    @Deprecated
    default public Double last() {
        return this.lastDouble();
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
        this.enqueue((Double)object);
    }
}

