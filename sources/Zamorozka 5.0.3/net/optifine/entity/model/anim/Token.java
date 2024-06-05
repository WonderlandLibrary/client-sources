package net.optifine.entity.model.anim;

public class Token {
    private final EnumTokenType type;
    private final String text;

    public Token(EnumTokenType type, String text) {
        this.type = type;
        this.text = text;
    }

    public EnumTokenType getType() {
        return this.type;
    }

    public String getText() {
        return this.text;
    }

    public String toString() {
        return this.text;
    }
}
