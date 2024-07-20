/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.IndirectPriorityQueue;
import it.unimi.dsi.fastutil.doubles.DoubleComparator;

public interface DoubleIndirectPriorityQueue
extends IndirectPriorityQueue<Double> {
    public DoubleComparator comparator();
}

