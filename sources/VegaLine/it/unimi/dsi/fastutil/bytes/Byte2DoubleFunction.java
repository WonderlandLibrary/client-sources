/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.Function;

public interface Byte2DoubleFunction
extends Function<Byte, Double> {
    @Override
    public double put(byte var1, double var2);

    public double get(byte var1);

    public double remove(byte var1);

    public boolean containsKey(byte var1);

    public void defaultReturnValue(double var1);

    public double defaultReturnValue();
}

