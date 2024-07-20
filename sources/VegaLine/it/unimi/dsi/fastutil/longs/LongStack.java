/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.Stack;

public interface LongStack
extends Stack<Long> {
    @Override
    public void push(long var1);

    public long popLong();

    public long topLong();

    public long peekLong(int var1);
}

