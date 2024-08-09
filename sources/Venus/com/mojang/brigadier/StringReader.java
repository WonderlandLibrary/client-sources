/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.brigadier;

import com.mojang.brigadier.ImmutableStringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

public class StringReader
implements ImmutableStringReader {
    private static final char SYNTAX_ESCAPE = '\\';
    private static final char SYNTAX_DOUBLE_QUOTE = '\"';
    private static final char SYNTAX_SINGLE_QUOTE = '\'';
    private final String string;
    private int cursor;

    public StringReader(StringReader stringReader) {
        this.string = stringReader.string;
        this.cursor = stringReader.cursor;
    }

    public StringReader(String string) {
        this.string = string;
    }

    @Override
    public String getString() {
        return this.string;
    }

    public void setCursor(int n) {
        this.cursor = n;
    }

    @Override
    public int getRemainingLength() {
        return this.string.length() - this.cursor;
    }

    @Override
    public int getTotalLength() {
        return this.string.length();
    }

    @Override
    public int getCursor() {
        return this.cursor;
    }

    @Override
    public String getRead() {
        return this.string.substring(0, this.cursor);
    }

    @Override
    public String getRemaining() {
        return this.string.substring(this.cursor);
    }

    @Override
    public boolean canRead(int n) {
        return this.cursor + n <= this.string.length();
    }

    @Override
    public boolean canRead() {
        return this.canRead(0);
    }

    @Override
    public char peek() {
        return this.string.charAt(this.cursor);
    }

    @Override
    public char peek(int n) {
        return this.string.charAt(this.cursor + n);
    }

    public char read() {
        return this.string.charAt(this.cursor++);
    }

    public void skip() {
        ++this.cursor;
    }

    public static boolean isAllowedNumber(char c) {
        return c >= '0' && c <= '9' || c == '.' || c == '-';
    }

    public static boolean isQuotedStringStart(char c) {
        return c == '\"' || c == '\'';
    }

    public void skipWhitespace() {
        while (this.canRead() && Character.isWhitespace(this.peek())) {
            this.skip();
        }
    }

    public int readInt() throws CommandSyntaxException {
        int n = this.cursor;
        while (this.canRead() && StringReader.isAllowedNumber(this.peek())) {
            this.skip();
        }
        String string = this.string.substring(n, this.cursor);
        if (string.isEmpty()) {
            throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.readerExpectedInt().createWithContext(this);
        }
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException numberFormatException) {
            this.cursor = n;
            throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.readerInvalidInt().createWithContext(this, string);
        }
    }

    public long readLong() throws CommandSyntaxException {
        int n = this.cursor;
        while (this.canRead() && StringReader.isAllowedNumber(this.peek())) {
            this.skip();
        }
        String string = this.string.substring(n, this.cursor);
        if (string.isEmpty()) {
            throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.readerExpectedLong().createWithContext(this);
        }
        try {
            return Long.parseLong(string);
        } catch (NumberFormatException numberFormatException) {
            this.cursor = n;
            throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.readerInvalidLong().createWithContext(this, string);
        }
    }

    public double readDouble() throws CommandSyntaxException {
        int n = this.cursor;
        while (this.canRead() && StringReader.isAllowedNumber(this.peek())) {
            this.skip();
        }
        String string = this.string.substring(n, this.cursor);
        if (string.isEmpty()) {
            throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.readerExpectedDouble().createWithContext(this);
        }
        try {
            return Double.parseDouble(string);
        } catch (NumberFormatException numberFormatException) {
            this.cursor = n;
            throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.readerInvalidDouble().createWithContext(this, string);
        }
    }

    public float readFloat() throws CommandSyntaxException {
        int n = this.cursor;
        while (this.canRead() && StringReader.isAllowedNumber(this.peek())) {
            this.skip();
        }
        String string = this.string.substring(n, this.cursor);
        if (string.isEmpty()) {
            throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.readerExpectedFloat().createWithContext(this);
        }
        try {
            return Float.parseFloat(string);
        } catch (NumberFormatException numberFormatException) {
            this.cursor = n;
            throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.readerInvalidFloat().createWithContext(this, string);
        }
    }

    public static boolean isAllowedInUnquotedString(char c) {
        return c >= '0' && c <= '9' || c >= 'A' && c <= 'Z' || c >= 'a' && c <= 'z' || c == '_' || c == '-' || c == '.' || c == '+';
    }

    public String readUnquotedString() {
        int n = this.cursor;
        while (this.canRead() && StringReader.isAllowedInUnquotedString(this.peek())) {
            this.skip();
        }
        return this.string.substring(n, this.cursor);
    }

    public String readQuotedString() throws CommandSyntaxException {
        if (!this.canRead()) {
            return "";
        }
        char c = this.peek();
        if (!StringReader.isQuotedStringStart(c)) {
            throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.readerExpectedStartOfQuote().createWithContext(this);
        }
        this.skip();
        return this.readStringUntil(c);
    }

    public String readStringUntil(char c) throws CommandSyntaxException {
        StringBuilder stringBuilder = new StringBuilder();
        boolean bl = false;
        while (this.canRead()) {
            char c2 = this.read();
            if (bl) {
                if (c2 == c || c2 == '\\') {
                    stringBuilder.append(c2);
                    bl = false;
                    continue;
                }
                this.setCursor(this.getCursor() - 1);
                throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.readerInvalidEscape().createWithContext(this, String.valueOf(c2));
            }
            if (c2 == '\\') {
                bl = true;
                continue;
            }
            if (c2 == c) {
                return stringBuilder.toString();
            }
            stringBuilder.append(c2);
        }
        throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.readerExpectedEndOfQuote().createWithContext(this);
    }

    public String readString() throws CommandSyntaxException {
        if (!this.canRead()) {
            return "";
        }
        char c = this.peek();
        if (StringReader.isQuotedStringStart(c)) {
            this.skip();
            return this.readStringUntil(c);
        }
        return this.readUnquotedString();
    }

    public boolean readBoolean() throws CommandSyntaxException {
        int n = this.cursor;
        String string = this.readString();
        if (string.isEmpty()) {
            throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.readerExpectedBool().createWithContext(this);
        }
        if (string.equals("true")) {
            return false;
        }
        if (string.equals("false")) {
            return true;
        }
        this.cursor = n;
        throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.readerInvalidBool().createWithContext(this, string);
    }

    public void expect(char c) throws CommandSyntaxException {
        if (!this.canRead() || this.peek() != c) {
            throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.readerExpectedSymbol().createWithContext(this, String.valueOf(c));
        }
        this.skip();
    }
}

