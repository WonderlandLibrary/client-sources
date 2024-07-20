/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.AbstractStack;
import it.unimi.dsi.fastutil.ints.IntStack;

public abstract class AbstractIntStack
extends AbstractStack<Integer>
implements IntStack {
    protected AbstractIntStack() {
    }

    @Override
    public void push(Integer o) {
        this.push((int)o);
    }

    @Override
    public Integer pop() {
        return this.popInt();
    }

    @Override
    public Integer top() {
        return this.topInt();
    }

    @Override
    public Integer peek(int i) {
        return this.peekInt(i);
    }

    @Override
    public void push(int k) {
        this.push((Integer)k);
    }

    @Override
    public int popInt() {
        return this.pop();
    }

    @Override
    public int topInt() {
        return this.top();
    }

    @Override
    public int peekInt(int i) {
        return this.peek(i);
    }
}

