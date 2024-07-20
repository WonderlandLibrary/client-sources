/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.IndirectPriorityQueue;
import it.unimi.dsi.fastutil.bytes.ByteComparator;

public interface ByteIndirectPriorityQueue
extends IndirectPriorityQueue<Byte> {
    public ByteComparator comparator();
}

