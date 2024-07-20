/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.PriorityQueue;
import it.unimi.dsi.fastutil.shorts.ShortComparator;

public interface ShortPriorityQueue
extends PriorityQueue<Short> {
    @Override
    public void enqueue(short var1);

    public short dequeueShort();

    public short firstShort();

    public short lastShort();

    public ShortComparator comparator();
}

