/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.Function;

public interface Float2BooleanFunction
extends Function<Float, Boolean> {
    @Override
    public boolean put(float var1, boolean var2);

    public boolean get(float var1);

    public boolean remove(float var1);

    public boolean containsKey(float var1);

    public void defaultReturnValue(boolean var1);

    public boolean defaultReturnValue();
}

