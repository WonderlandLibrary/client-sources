/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.Stack;

public interface ShortStack
extends Stack<Short> {
    @Override
    public void push(short var1);

    public short popShort();

    public short topShort();

    public short peekShort(int var1);
}

