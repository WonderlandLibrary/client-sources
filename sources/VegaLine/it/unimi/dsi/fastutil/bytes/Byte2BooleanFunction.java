/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.Function;

public interface Byte2BooleanFunction
extends Function<Byte, Boolean> {
    @Override
    public boolean put(byte var1, boolean var2);

    public boolean get(byte var1);

    public boolean remove(byte var1);

    public boolean containsKey(byte var1);

    public void defaultReturnValue(boolean var1);

    public boolean defaultReturnValue();
}

