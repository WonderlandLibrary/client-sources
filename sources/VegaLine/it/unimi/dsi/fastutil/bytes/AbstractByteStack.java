/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.AbstractStack;
import it.unimi.dsi.fastutil.bytes.ByteStack;

public abstract class AbstractByteStack
extends AbstractStack<Byte>
implements ByteStack {
    protected AbstractByteStack() {
    }

    @Override
    public void push(Byte o) {
        this.push((byte)o);
    }

    @Override
    public Byte pop() {
        return this.popByte();
    }

    @Override
    public Byte top() {
        return this.topByte();
    }

    @Override
    public Byte peek(int i) {
        return this.peekByte(i);
    }

    @Override
    public void push(byte k) {
        this.push((Byte)k);
    }

    @Override
    public byte popByte() {
        return this.pop();
    }

    @Override
    public byte topByte() {
        return this.top();
    }

    @Override
    public byte peekByte(int i) {
        return this.peek(i);
    }
}

