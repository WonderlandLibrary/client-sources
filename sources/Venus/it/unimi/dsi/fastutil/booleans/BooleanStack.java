/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.booleans;

import it.unimi.dsi.fastutil.Stack;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface BooleanStack
extends Stack<Boolean> {
    @Override
    public void push(boolean var1);

    public boolean popBoolean();

    public boolean topBoolean();

    public boolean peekBoolean(int var1);

    @Override
    @Deprecated
    default public void push(Boolean bl) {
        this.push((boolean)bl);
    }

    @Override
    @Deprecated
    default public Boolean pop() {
        return this.popBoolean();
    }

    @Override
    @Deprecated
    default public Boolean top() {
        return this.topBoolean();
    }

    @Override
    @Deprecated
    default public Boolean peek(int n) {
        return this.peekBoolean(n);
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
        this.push((Boolean)object);
    }
}

