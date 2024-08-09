/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import com.viaversion.viaversion.libs.kyori.adventure.nbt.StringTagParseException;

final class CharBuffer {
    private final CharSequence sequence;
    private int index;

    CharBuffer(CharSequence charSequence) {
        this.sequence = charSequence;
    }

    public char peek() {
        return this.sequence.charAt(this.index);
    }

    public char peek(int n) {
        return this.sequence.charAt(this.index + n);
    }

    public char take() {
        return this.sequence.charAt(this.index++);
    }

    public boolean advance() {
        ++this.index;
        return this.hasMore();
    }

    public boolean hasMore() {
        return this.index < this.sequence.length();
    }

    public boolean hasMore(int n) {
        return this.index + n < this.sequence.length();
    }

    public CharSequence takeUntil(char c) throws StringTagParseException {
        c = Character.toLowerCase(c);
        int n = -1;
        for (int i = this.index; i < this.sequence.length(); ++i) {
            if (this.sequence.charAt(i) == '\\') {
                ++i;
                continue;
            }
            if (Character.toLowerCase(this.sequence.charAt(i)) != c) continue;
            n = i;
            break;
        }
        if (n == -1) {
            throw this.makeError("No occurrence of " + c + " was found");
        }
        CharSequence charSequence = this.sequence.subSequence(this.index, n);
        this.index = n + 1;
        return charSequence;
    }

    public CharBuffer expect(char c) throws StringTagParseException {
        this.skipWhitespace();
        if (!this.hasMore()) {
            throw this.makeError("Expected character '" + c + "' but got EOF");
        }
        if (this.peek() != c) {
            throw this.makeError("Expected character '" + c + "' but got '" + this.peek() + "'");
        }
        this.take();
        return this;
    }

    public boolean takeIf(char c) {
        this.skipWhitespace();
        if (this.hasMore() && this.peek() == c) {
            this.advance();
            return false;
        }
        return true;
    }

    public CharBuffer skipWhitespace() {
        while (this.hasMore() && Character.isWhitespace(this.peek())) {
            this.advance();
        }
        return this;
    }

    public StringTagParseException makeError(String string) {
        return new StringTagParseException(string, this.sequence, this.index);
    }
}

