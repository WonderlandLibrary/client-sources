/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.Function;

public interface Int2FloatFunction
extends Function<Integer, Float> {
    @Override
    public float put(int var1, float var2);

    public float get(int var1);

    public float remove(int var1);

    public boolean containsKey(int var1);

    public void defaultReturnValue(float var1);

    public float defaultReturnValue();
}

