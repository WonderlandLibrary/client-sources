/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.IndirectPriorityQueue;
import it.unimi.dsi.fastutil.shorts.ShortComparator;

public interface ShortIndirectPriorityQueue
extends IndirectPriorityQueue<Short> {
    public ShortComparator comparator();
}

