/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.Stack;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface FloatStack
extends Stack<Float> {
    @Override
    public void push(float var1);

    public float popFloat();

    public float topFloat();

    public float peekFloat(int var1);

    @Override
    @Deprecated
    default public void push(Float f) {
        this.push(f.floatValue());
    }

    @Override
    @Deprecated
    default public Float pop() {
        return Float.valueOf(this.popFloat());
    }

    @Override
    @Deprecated
    default public Float top() {
        return Float.valueOf(this.topFloat());
    }

    @Override
    @Deprecated
    default public Float peek(int n) {
        return Float.valueOf(this.peekFloat(n));
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
        this.push((Float)object);
    }
}

