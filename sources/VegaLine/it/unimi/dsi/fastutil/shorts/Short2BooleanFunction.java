/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.Function;

public interface Short2BooleanFunction
extends Function<Short, Boolean> {
    @Override
    public boolean put(short var1, boolean var2);

    public boolean get(short var1);

    public boolean remove(short var1);

    public boolean containsKey(short var1);

    public void defaultReturnValue(boolean var1);

    public boolean defaultReturnValue();
}

