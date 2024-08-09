/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.Stack;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface ByteStack
extends Stack<Byte> {
    @Override
    public void push(byte var1);

    public byte popByte();

    public byte topByte();

    public byte peekByte(int var1);

    @Override
    @Deprecated
    default public void push(Byte by) {
        this.push((byte)by);
    }

    @Override
    @Deprecated
    default public Byte pop() {
        return this.popByte();
    }

    @Override
    @Deprecated
    default public Byte top() {
        return this.topByte();
    }

    @Override
    @Deprecated
    default public Byte peek(int n) {
        return this.peekByte(n);
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
        this.push((Byte)object);
    }
}

