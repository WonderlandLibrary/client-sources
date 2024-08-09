/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.PriorityQueue;
import it.unimi.dsi.fastutil.floats.FloatComparator;
import java.util.Comparator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface FloatPriorityQueue
extends PriorityQueue<Float> {
    @Override
    public void enqueue(float var1);

    public float dequeueFloat();

    public float firstFloat();

    default public float lastFloat() {
        throw new UnsupportedOperationException();
    }

    public FloatComparator comparator();

    @Override
    @Deprecated
    default public void enqueue(Float f) {
        this.enqueue(f.floatValue());
    }

    @Override
    @Deprecated
    default public Float dequeue() {
        return Float.valueOf(this.dequeueFloat());
    }

    @Override
    @Deprecated
    default public Float first() {
        return Float.valueOf(this.firstFloat());
    }

    @Override
    @Deprecated
    default public Float last() {
        return Float.valueOf(this.lastFloat());
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
        this.enqueue((Float)object);
    }
}

