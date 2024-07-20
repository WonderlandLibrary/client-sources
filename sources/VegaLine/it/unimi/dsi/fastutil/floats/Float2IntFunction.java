/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.Function;

public interface Float2IntFunction
extends Function<Float, Integer> {
    @Override
    public int put(float var1, int var2);

    public int get(float var1);

    public int remove(float var1);

    public boolean containsKey(float var1);

    public void defaultReturnValue(int var1);

    public int defaultReturnValue();
}

