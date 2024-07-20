/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.PriorityQueue;
import it.unimi.dsi.fastutil.ints.IntComparator;

public interface IntPriorityQueue
extends PriorityQueue<Integer> {
    @Override
    public void enqueue(int var1);

    public int dequeueInt();

    public int firstInt();

    public int lastInt();

    public IntComparator comparator();
}

