/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.Function;

public interface Double2ShortFunction
extends Function<Double, Short> {
    @Override
    public short put(double var1, short var3);

    public short get(double var1);

    public short remove(double var1);

    public boolean containsKey(double var1);

    public void defaultReturnValue(short var1);

    public short defaultReturnValue();
}

