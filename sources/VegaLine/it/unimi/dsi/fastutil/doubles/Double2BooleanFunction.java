/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.Function;

public interface Double2BooleanFunction
extends Function<Double, Boolean> {
    @Override
    public boolean put(double var1, boolean var3);

    public boolean get(double var1);

    public boolean remove(double var1);

    public boolean containsKey(double var1);

    public void defaultReturnValue(boolean var1);

    public boolean defaultReturnValue();
}

