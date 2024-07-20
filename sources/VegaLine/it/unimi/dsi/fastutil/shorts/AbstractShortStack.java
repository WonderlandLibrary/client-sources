/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.AbstractStack;
import it.unimi.dsi.fastutil.shorts.ShortStack;

public abstract class AbstractShortStack
extends AbstractStack<Short>
implements ShortStack {
    protected AbstractShortStack() {
    }

    @Override
    public void push(Short o) {
        this.push((short)o);
    }

    @Override
    public Short pop() {
        return this.popShort();
    }

    @Override
    public Short top() {
        return this.topShort();
    }

    @Override
    public Short peek(int i) {
        return this.peekShort(i);
    }

    @Override
    public void push(short k) {
        this.push((Short)k);
    }

    @Override
    public short popShort() {
        return this.pop();
    }

    @Override
    public short topShort() {
        return this.top();
    }

    @Override
    public short peekShort(int i) {
        return this.peek(i);
    }
}

