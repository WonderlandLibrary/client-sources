/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.Function;

public interface Int2ByteFunction
extends Function<Integer, Byte> {
    @Override
    public byte put(int var1, byte var2);

    public byte get(int var1);

    public byte remove(int var1);

    public boolean containsKey(int var1);

    public void defaultReturnValue(byte var1);

    public byte defaultReturnValue();
}

