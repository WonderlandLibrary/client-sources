/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.AbstractStack;
import it.unimi.dsi.fastutil.longs.LongStack;

public abstract class AbstractLongStack
extends AbstractStack<Long>
implements LongStack {
    protected AbstractLongStack() {
    }

    @Override
    public void push(Long o) {
        this.push((long)o);
    }

    @Override
    public Long pop() {
        return this.popLong();
    }

    @Override
    public Long top() {
        return this.topLong();
    }

    @Override
    public Long peek(int i) {
        return this.peekLong(i);
    }

    @Override
    public void push(long k) {
        this.push((Long)k);
    }

    @Override
    public long popLong() {
        return this.pop();
    }

    @Override
    public long topLong() {
        return this.top();
    }

    @Override
    public long peekLong(int i) {
        return this.peek(i);
    }
}

