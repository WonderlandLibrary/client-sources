package net.optifine.entity.model.anim;

public enum EnumTokenType {
    IDENTIFIER("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz", "0123456789_:."),
    CONSTANT("0123456789", "."),
    OPERATOR("+-*/%", 1),
    COMMA(",", 1),
    BRACKET_OPEN("(", 1),
    BRACKET_CLOSE(")", 1);

    public static final EnumTokenType[] VALUES = values();
    private final String charsFirst;
    private final String charsExt;
    private int maxLen;

    EnumTokenType(String charsFirst) {
        this.charsFirst = charsFirst;
        this.charsExt = "";
    }

    EnumTokenType(String charsFirst, int maxLen) {
        this.charsFirst = charsFirst;
        this.charsExt = "";
        this.maxLen = maxLen;
    }

    EnumTokenType(String charsFirst, String charsExt) {
        this.charsFirst = charsFirst;
        this.charsExt = charsExt;
    }

    public static EnumTokenType getTypeByFirstChar(char ch) {
        for (int i = 0; i < VALUES.length; ++i) {
            EnumTokenType enumtokentype = VALUES[i];

            if (enumtokentype.getCharsFirst().indexOf(ch) >= 0) {
                return enumtokentype;
            }
        }

        return null;
    }

    public String getCharsFirst() {
        return this.charsFirst;
    }

    public String getCharsExt() {
        return this.charsExt;
    }

    public boolean hasChar(char ch) {
        if (this.getCharsFirst().indexOf(ch) >= 0) {
            return true;
        } else {
            return this.getCharsExt().indexOf(ch) >= 0;
        }
    }

    public int getMaxLen() {
        return this.maxLen;
    }
}
