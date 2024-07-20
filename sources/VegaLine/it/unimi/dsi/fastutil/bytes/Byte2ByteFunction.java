/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.Function;

public interface Byte2ByteFunction
extends Function<Byte, Byte> {
    @Override
    public byte put(byte var1, byte var2);

    public byte get(byte var1);

    public byte remove(byte var1);

    public boolean containsKey(byte var1);

    public void defaultReturnValue(byte var1);

    public byte defaultReturnValue();
}

