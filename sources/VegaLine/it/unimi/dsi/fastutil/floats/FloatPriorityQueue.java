/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.PriorityQueue;
import it.unimi.dsi.fastutil.floats.FloatComparator;

public interface FloatPriorityQueue
extends PriorityQueue<Float> {
    @Override
    public void enqueue(float var1);

    public float dequeueFloat();

    public float firstFloat();

    public float lastFloat();

    public FloatComparator comparator();
}

