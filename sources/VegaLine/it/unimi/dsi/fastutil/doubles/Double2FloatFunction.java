/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.Function;

public interface Double2FloatFunction
extends Function<Double, Float> {
    @Override
    public float put(double var1, float var3);

    public float get(double var1);

    public float remove(double var1);

    public boolean containsKey(double var1);

    public void defaultReturnValue(float var1);

    public float defaultReturnValue();
}

