/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.Function;

public interface Byte2IntFunction
extends Function<Byte, Integer> {
    @Override
    public int put(byte var1, int var2);

    public int get(byte var1);

    public int remove(byte var1);

    public boolean containsKey(byte var1);

    public void defaultReturnValue(int var1);

    public int defaultReturnValue();
}

