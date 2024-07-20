/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.AbstractStack;
import it.unimi.dsi.fastutil.doubles.DoubleStack;

public abstract class AbstractDoubleStack
extends AbstractStack<Double>
implements DoubleStack {
    protected AbstractDoubleStack() {
    }

    @Override
    public void push(Double o) {
        this.push((double)o);
    }

    @Override
    public Double pop() {
        return this.popDouble();
    }

    @Override
    public Double top() {
        return this.topDouble();
    }

    @Override
    public Double peek(int i) {
        return this.peekDouble(i);
    }

    @Override
    public void push(double k) {
        this.push((Double)k);
    }

    @Override
    public double popDouble() {
        return this.pop();
    }

    @Override
    public double topDouble() {
        return this.top();
    }

    @Override
    public double peekDouble(int i) {
        return this.peek(i);
    }
}

