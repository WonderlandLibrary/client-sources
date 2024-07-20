/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.Function;

public interface Short2IntFunction
extends Function<Short, Integer> {
    @Override
    public int put(short var1, int var2);

    public int get(short var1);

    public int remove(short var1);

    public boolean containsKey(short var1);

    public void defaultReturnValue(int var1);

    public int defaultReturnValue();
}

