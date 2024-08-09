/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.expr;

public enum TokenType {
    IDENTIFIER("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz", "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_:."),
    NUMBER("0123456789", "0123456789."),
    OPERATOR("+-*/%!&|<>=", "&|="),
    COMMA(","),
    BRACKET_OPEN("("),
    BRACKET_CLOSE(")");

    private String charsFirst;
    private String charsNext;
    public static final TokenType[] VALUES;

    private TokenType(String string2) {
        this(string2, "");
    }

    private TokenType(String string2, String string3) {
        this.charsFirst = string2;
        this.charsNext = string3;
    }

    public String getCharsFirst() {
        return this.charsFirst;
    }

    public String getCharsNext() {
        return this.charsNext;
    }

    public static TokenType getTypeByFirstChar(char c) {
        for (int i = 0; i < VALUES.length; ++i) {
            TokenType tokenType = VALUES[i];
            if (tokenType.getCharsFirst().indexOf(c) < 0) continue;
            return tokenType;
        }
        return null;
    }

    public boolean hasCharNext(char c) {
        return this.charsNext.indexOf(c) >= 0;
    }

    static {
        VALUES = TokenType.values();
    }

    private static class Const {
        static final String ALPHAS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        static final String DIGITS = "0123456789";

        private Const() {
        }
    }
}

