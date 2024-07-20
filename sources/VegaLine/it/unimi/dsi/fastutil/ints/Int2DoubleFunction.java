/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.Function;

public interface Int2DoubleFunction
extends Function<Integer, Double> {
    @Override
    public double put(int var1, double var2);

    public double get(int var1);

    public double remove(int var1);

    public boolean containsKey(int var1);

    public void defaultReturnValue(double var1);

    public double defaultReturnValue();
}

