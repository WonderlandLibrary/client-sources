/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.Function;

public interface Short2ShortFunction
extends Function<Short, Short> {
    @Override
    public short put(short var1, short var2);

    public short get(short var1);

    public short remove(short var1);

    public boolean containsKey(short var1);

    public void defaultReturnValue(short var1);

    public short defaultReturnValue();
}

