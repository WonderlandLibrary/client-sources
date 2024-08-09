/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.Stack;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface CharStack
extends Stack<Character> {
    @Override
    public void push(char var1);

    public char popChar();

    public char topChar();

    public char peekChar(int var1);

    @Override
    @Deprecated
    default public void push(Character c) {
        this.push(c.charValue());
    }

    @Override
    @Deprecated
    default public Character pop() {
        return Character.valueOf(this.popChar());
    }

    @Override
    @Deprecated
    default public Character top() {
        return Character.valueOf(this.topChar());
    }

    @Override
    @Deprecated
    default public Character peek(int n) {
        return Character.valueOf(this.peekChar(n));
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
        this.push((Character)object);
    }
}

