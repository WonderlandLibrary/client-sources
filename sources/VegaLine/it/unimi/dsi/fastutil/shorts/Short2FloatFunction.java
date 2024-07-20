/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.Function;

public interface Short2FloatFunction
extends Function<Short, Float> {
    @Override
    public float put(short var1, float var2);

    public float get(short var1);

    public float remove(short var1);

    public boolean containsKey(short var1);

    public void defaultReturnValue(float var1);

    public float defaultReturnValue();
}

