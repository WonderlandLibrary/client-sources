/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.Function;

public interface Long2BooleanFunction
extends Function<Long, Boolean> {
    @Override
    public boolean put(long var1, boolean var3);

    public boolean get(long var1);

    public boolean remove(long var1);

    public boolean containsKey(long var1);

    public void defaultReturnValue(boolean var1);

    public boolean defaultReturnValue();
}

