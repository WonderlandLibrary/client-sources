/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.booleans;

import it.unimi.dsi.fastutil.AbstractStack;
import it.unimi.dsi.fastutil.booleans.BooleanStack;

public abstract class AbstractBooleanStack
extends AbstractStack<Boolean>
implements BooleanStack {
    protected AbstractBooleanStack() {
    }

    @Override
    public void push(Boolean o) {
        this.push((boolean)o);
    }

    @Override
    public Boolean pop() {
        return this.popBoolean();
    }

    @Override
    public Boolean top() {
        return this.topBoolean();
    }

    @Override
    public Boolean peek(int i) {
        return this.peekBoolean(i);
    }

    @Override
    public void push(boolean k) {
        this.push((Boolean)k);
    }

    @Override
    public boolean popBoolean() {
        return this.pop();
    }

    @Override
    public boolean topBoolean() {
        return this.top();
    }

    @Override
    public boolean peekBoolean(int i) {
        return this.peek(i);
    }
}

