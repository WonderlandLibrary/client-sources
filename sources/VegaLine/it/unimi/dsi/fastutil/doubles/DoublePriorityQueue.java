/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.PriorityQueue;
import it.unimi.dsi.fastutil.doubles.DoubleComparator;

public interface DoublePriorityQueue
extends PriorityQueue<Double> {
    @Override
    public void enqueue(double var1);

    public double dequeueDouble();

    public double firstDouble();

    public double lastDouble();

    public DoubleComparator comparator();
}

