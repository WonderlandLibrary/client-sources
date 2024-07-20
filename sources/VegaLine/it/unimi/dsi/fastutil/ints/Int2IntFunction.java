/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.Function;

public interface Int2IntFunction
extends Function<Integer, Integer> {
    @Override
    public int put(int var1, int var2);

    public int get(int var1);

    public int remove(int var1);

    public boolean containsKey(int var1);

    public void defaultReturnValue(int var1);

    public int defaultReturnValue();
}

