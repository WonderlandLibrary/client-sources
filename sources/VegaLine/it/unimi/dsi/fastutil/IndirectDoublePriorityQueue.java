/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil;

import it.unimi.dsi.fastutil.IndirectPriorityQueue;
import java.util.Comparator;

public interface IndirectDoublePriorityQueue<K>
extends IndirectPriorityQueue<K> {
    public Comparator<? super K> secondaryComparator();

    public int secondaryFirst();

    public int secondaryLast();

    public int secondaryFront(int[] var1);
}

