/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.Stack;

public interface DoubleStack
extends Stack<Double> {
    @Override
    public void push(double var1);

    public double popDouble();

    public double topDouble();

    public double peekDouble(int var1);
}

