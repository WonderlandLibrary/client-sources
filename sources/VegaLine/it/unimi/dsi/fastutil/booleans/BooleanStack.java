/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.booleans;

import it.unimi.dsi.fastutil.Stack;

public interface BooleanStack
extends Stack<Boolean> {
    @Override
    public void push(boolean var1);

    public boolean popBoolean();

    public boolean topBoolean();

    public boolean peekBoolean(int var1);
}

