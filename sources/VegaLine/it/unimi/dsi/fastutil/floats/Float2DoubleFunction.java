/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.Function;

public interface Float2DoubleFunction
extends Function<Float, Double> {
    @Override
    public double put(float var1, double var2);

    public double get(float var1);

    public double remove(float var1);

    public boolean containsKey(float var1);

    public void defaultReturnValue(double var1);

    public double defaultReturnValue();
}

