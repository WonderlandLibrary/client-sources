/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.PriorityQueue;
import it.unimi.dsi.fastutil.bytes.ByteComparator;

public interface BytePriorityQueue
extends PriorityQueue<Byte> {
    @Override
    public void enqueue(byte var1);

    public byte dequeueByte();

    public byte firstByte();

    public byte lastByte();

    public ByteComparator comparator();
}

