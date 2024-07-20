/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.Function;

public interface Long2ShortFunction
extends Function<Long, Short> {
    @Override
    public short put(long var1, short var3);

    public short get(long var1);

    public short remove(long var1);

    public boolean containsKey(long var1);

    public void defaultReturnValue(short var1);

    public short defaultReturnValue();
}

