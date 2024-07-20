/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.Function;

public interface Float2ByteFunction
extends Function<Float, Byte> {
    @Override
    public byte put(float var1, byte var2);

    public byte get(float var1);

    public byte remove(float var1);

    public boolean containsKey(float var1);

    public void defaultReturnValue(byte var1);

    public byte defaultReturnValue();
}

