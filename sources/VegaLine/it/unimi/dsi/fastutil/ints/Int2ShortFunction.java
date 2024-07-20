/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.Function;

public interface Int2ShortFunction
extends Function<Integer, Short> {
    @Override
    public short put(int var1, short var2);

    public short get(int var1);

    public short remove(int var1);

    public boolean containsKey(int var1);

    public void defaultReturnValue(short var1);

    public short defaultReturnValue();
}

