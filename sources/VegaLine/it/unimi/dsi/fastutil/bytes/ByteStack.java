/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.Stack;

public interface ByteStack
extends Stack<Byte> {
    @Override
    public void push(byte var1);

    public byte popByte();

    public byte topByte();

    public byte peekByte(int var1);
}

