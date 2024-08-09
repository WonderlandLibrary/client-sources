/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.Stack;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface LongStack
extends Stack<Long> {
    @Override
    public void push(long var1);

    public long popLong();

    public long topLong();

    public long peekLong(int var1);

    @Override
    @Deprecated
    default public void push(Long l) {
        this.push((long)l);
    }

    @Override
    @Deprecated
    default public Long pop() {
        return this.popLong();
    }

    @Override
    @Deprecated
    default public Long top() {
        return this.topLong();
    }

    @Override
    @Deprecated
    default public Long peek(int n) {
        return this.peekLong(n);
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
        this.push((Long)object);
    }
}

