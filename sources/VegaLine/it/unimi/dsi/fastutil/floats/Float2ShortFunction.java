/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.Function;

public interface Float2ShortFunction
extends Function<Float, Short> {
    @Override
    public short put(float var1, short var2);

    public short get(float var1);

    public short remove(float var1);

    public boolean containsKey(float var1);

    public void defaultReturnValue(short var1);

    public short defaultReturnValue();
}

