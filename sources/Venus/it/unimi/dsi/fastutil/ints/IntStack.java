/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.Stack;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface IntStack
extends Stack<Integer> {
    @Override
    public void push(int var1);

    public int popInt();

    public int topInt();

    public int peekInt(int var1);

    @Override
    @Deprecated
    default public void push(Integer n) {
        this.push((int)n);
    }

    @Override
    @Deprecated
    default public Integer pop() {
        return this.popInt();
    }

    @Override
    @Deprecated
    default public Integer top() {
        return this.topInt();
    }

    @Override
    @Deprecated
    default public Integer peek(int n) {
        return this.peekInt(n);
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
        this.push((Integer)object);
    }
}

