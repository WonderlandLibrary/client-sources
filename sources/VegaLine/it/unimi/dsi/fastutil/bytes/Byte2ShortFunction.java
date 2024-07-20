/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.Function;

public interface Byte2ShortFunction
extends Function<Byte, Short> {
    @Override
    public short put(byte var1, short var2);

    public short get(byte var1);

    public short remove(byte var1);

    public boolean containsKey(byte var1);

    public void defaultReturnValue(short var1);

    public short defaultReturnValue();
}

