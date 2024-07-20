/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.PriorityQueue;
import it.unimi.dsi.fastutil.longs.LongComparator;

public interface LongPriorityQueue
extends PriorityQueue<Long> {
    @Override
    public void enqueue(long var1);

    public long dequeueLong();

    public long firstLong();

    public long lastLong();

    public LongComparator comparator();
}

