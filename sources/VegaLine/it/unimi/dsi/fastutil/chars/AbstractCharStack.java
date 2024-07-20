/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.AbstractStack;
import it.unimi.dsi.fastutil.chars.CharStack;

public abstract class AbstractCharStack
extends AbstractStack<Character>
implements CharStack {
    protected AbstractCharStack() {
    }

    @Override
    public void push(Character o) {
        this.push(o.charValue());
    }

    @Override
    public Character pop() {
        return Character.valueOf(this.popChar());
    }

    @Override
    public Character top() {
        return Character.valueOf(this.topChar());
    }

    @Override
    public Character peek(int i) {
        return Character.valueOf(this.peekChar(i));
    }

    @Override
    public void push(char k) {
        this.push(Character.valueOf(k));
    }

    @Override
    public char popChar() {
        return this.pop().charValue();
    }

    @Override
    public char topChar() {
        return this.top().charValue();
    }

    @Override
    public char peekChar(int i) {
        return this.peek(i).charValue();
    }
}

