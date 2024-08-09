/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.resources;

import net.minecraft.util.SharedConstants;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public enum PackCompatibility {
    TOO_OLD("old"),
    TOO_NEW("new"),
    COMPATIBLE("compatible");

    private final ITextComponent description;
    private final ITextComponent confirmMessage;

    private PackCompatibility(String string2) {
        this.description = new TranslationTextComponent("pack.incompatible." + string2).mergeStyle(TextFormatting.GRAY);
        this.confirmMessage = new TranslationTextComponent("pack.incompatible.confirm." + string2);
    }

    public boolean isCompatible() {
        return this == COMPATIBLE;
    }

    public static PackCompatibility getCompatibility(int n) {
        if (n < SharedConstants.getVersion().getPackVersion()) {
            return TOO_OLD;
        }
        return n > SharedConstants.getVersion().getPackVersion() ? TOO_NEW : COMPATIBLE;
    }

    public ITextComponent getDescription() {
        return this.description;
    }

    public ITextComponent getConfirmMessage() {
        return this.confirmMessage;
    }
}

