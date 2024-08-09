/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import net.minecraft.util.text.TextFormatting;

public enum Rarity {
    COMMON(TextFormatting.WHITE),
    UNCOMMON(TextFormatting.YELLOW),
    RARE(TextFormatting.AQUA),
    EPIC(TextFormatting.LIGHT_PURPLE);

    public final TextFormatting color;

    private Rarity(TextFormatting textFormatting) {
        this.color = textFormatting;
    }
}

