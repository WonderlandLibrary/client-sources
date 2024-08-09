/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.Stack;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface DoubleStack
extends Stack<Double> {
    @Override
    public void push(double var1);

    public double popDouble();

    public double topDouble();

    public double peekDouble(int var1);

    @Override
    @Deprecated
    default public void push(Double d) {
        this.push((double)d);
    }

    @Override
    @Deprecated
    default public Double pop() {
        return this.popDouble();
    }

    @Override
    @Deprecated
    default public Double top() {
        return this.topDouble();
    }

    @Override
    @Deprecated
    default public Double peek(int n) {
        return this.peekDouble(n);
    }

    @Override
    @Deprecated
    default public Object peek(int n) {
        return this.peek(n);
    }

    @Override
    @Deprecated
    default public Object top() {
        return this.top();
    }

    @Override
    @Deprecated
    default public Object pop() {
        return this.pop();
    }

    @Override
    @Deprecated
    default public void push(Object object) {
        this.push((Double)object);
    }
}

