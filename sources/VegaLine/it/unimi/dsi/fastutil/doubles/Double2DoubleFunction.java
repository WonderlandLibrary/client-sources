/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.Function;

public interface Double2DoubleFunction
extends Function<Double, Double> {
    @Override
    public double put(double var1, double var3);

    public double get(double var1);

    public double remove(double var1);

    public boolean containsKey(double var1);

    public void defaultReturnValue(double var1);

    public double defaultReturnValue();
}

