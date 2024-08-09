/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.potion;

import net.minecraft.util.text.TextFormatting;

public enum EffectType {
    BENEFICIAL(TextFormatting.BLUE),
    HARMFUL(TextFormatting.RED),
    NEUTRAL(TextFormatting.BLUE);

    private final TextFormatting color;

    private EffectType(TextFormatting textFormatting) {
        this.color = textFormatting;
    }

    public TextFormatting getColor() {
        return this.color;
    }
}

