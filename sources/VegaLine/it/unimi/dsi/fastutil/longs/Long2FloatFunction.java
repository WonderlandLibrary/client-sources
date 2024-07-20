/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.Function;

public interface Long2FloatFunction
extends Function<Long, Float> {
    @Override
    public float put(long var1, float var3);

    public float get(long var1);

    public float remove(long var1);

    public boolean containsKey(long var1);

    public void defaultReturnValue(float var1);

    public float defaultReturnValue();
}

