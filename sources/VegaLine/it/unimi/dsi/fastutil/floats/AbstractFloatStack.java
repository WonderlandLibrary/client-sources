/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.AbstractStack;
import it.unimi.dsi.fastutil.floats.FloatStack;

public abstract class AbstractFloatStack
extends AbstractStack<Float>
implements FloatStack {
    protected AbstractFloatStack() {
    }

    @Override
    public void push(Float o) {
        this.push(o.floatValue());
    }

    @Override
    public Float pop() {
        return Float.valueOf(this.popFloat());
    }

    @Override
    public Float top() {
        return Float.valueOf(this.topFloat());
    }

    @Override
    public Float peek(int i) {
        return Float.valueOf(this.peekFloat(i));
    }

    @Override
    public void push(float k) {
        this.push(Float.valueOf(k));
    }

    @Override
    public float popFloat() {
        return this.pop().floatValue();
    }

    @Override
    public float topFloat() {
        return this.top().floatValue();
    }

    @Override
    public float peekFloat(int i) {
        return this.peek(i).floatValue();
    }
}

