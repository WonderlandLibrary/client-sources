/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model.anim;

import net.optifine.entity.model.anim.EnumTokenType;

public class Token {
    private EnumTokenType type;
    private String text;

    public Token(EnumTokenType type2, String text) {
        this.type = type2;
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

