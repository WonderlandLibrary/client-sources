/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.Function;

public interface Long2DoubleFunction
extends Function<Long, Double> {
    @Override
    public double put(long var1, double var3);

    public double get(long var1);

    public double remove(long var1);

    public boolean containsKey(long var1);

    public void defaultReturnValue(double var1);

    public double defaultReturnValue();
}

