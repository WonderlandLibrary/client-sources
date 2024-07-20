/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.Function;

public interface Int2BooleanFunction
extends Function<Integer, Boolean> {
    @Override
    public boolean put(int var1, boolean var2);

    public boolean get(int var1);

    public boolean remove(int var1);

    public boolean containsKey(int var1);

    public void defaultReturnValue(boolean var1);

    public boolean defaultReturnValue();
}

