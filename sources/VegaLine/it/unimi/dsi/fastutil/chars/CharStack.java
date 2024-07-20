/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.Stack;

public interface CharStack
extends Stack<Character> {
    @Override
    public void push(char var1);

    public char popChar();

    public char topChar();

    public char peekChar(int var1);
}

