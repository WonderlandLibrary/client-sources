/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.Function;

public interface Char2DoubleFunction
extends Function<Character, Double> {
    @Override
    public double put(char var1, double var2);

    public double get(char var1);

    public double remove(char var1);

    public boolean containsKey(char var1);

    public void defaultReturnValue(double var1);

    public double defaultReturnValue();
}

