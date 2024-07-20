/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.Function;

public interface Double2ByteFunction
extends Function<Double, Byte> {
    @Override
    public byte put(double var1, byte var3);

    public byte get(double var1);

    public byte remove(double var1);

    public boolean containsKey(double var1);

    public void defaultReturnValue(byte var1);

    public byte defaultReturnValue();
}

