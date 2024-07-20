/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.Function;

public interface Float2LongFunction
extends Function<Float, Long> {
    @Override
    public long put(float var1, long var2);

    public long get(float var1);

    public long remove(float var1);

    public boolean containsKey(float var1);

    public void defaultReturnValue(long var1);

    public long defaultReturnValue();
}

