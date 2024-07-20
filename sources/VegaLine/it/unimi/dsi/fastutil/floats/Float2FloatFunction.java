/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.Function;

public interface Float2FloatFunction
extends Function<Float, Float> {
    @Override
    public float put(float var1, float var2);

    public float get(float var1);

    public float remove(float var1);

    public boolean containsKey(float var1);

    public void defaultReturnValue(float var1);

    public float defaultReturnValue();
}

