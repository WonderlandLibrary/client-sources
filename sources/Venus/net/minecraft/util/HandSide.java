/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public enum HandSide {
    LEFT(new TranslationTextComponent("options.mainHand.left")),
    RIGHT(new TranslationTextComponent("options.mainHand.right"));

    private final ITextComponent handName;

    private HandSide(ITextComponent iTextComponent) {
        this.handName = iTextComponent;
    }

    public HandSide opposite() {
        return this == LEFT ? RIGHT : LEFT;
    }

    public String toString() {
        return this.handName.getString();
    }

    public ITextComponent getHandName() {
        return this.handName;
    }
}

