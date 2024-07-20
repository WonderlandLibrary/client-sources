/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.Function;

public interface Byte2FloatFunction
extends Function<Byte, Float> {
    @Override
    public float put(byte var1, float var2);

    public float get(byte var1);

    public float remove(byte var1);

    public boolean containsKey(byte var1);

    public void defaultReturnValue(float var1);

    public float defaultReturnValue();
}

