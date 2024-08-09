/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.advancements;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public enum FrameType {
    TASK("task", 0, TextFormatting.GREEN),
    CHALLENGE("challenge", 26, TextFormatting.DARK_PURPLE),
    GOAL("goal", 52, TextFormatting.GREEN);

    private final String name;
    private final int icon;
    private final TextFormatting format;
    private final ITextComponent translatedToast;

    private FrameType(String string2, int n2, TextFormatting textFormatting) {
        this.name = string2;
        this.icon = n2;
        this.format = textFormatting;
        this.translatedToast = new TranslationTextComponent("advancements.toast." + string2);
    }

    public String getName() {
        return this.name;
    }

    public int getIcon() {
        return this.icon;
    }

    public static FrameType byName(String string) {
        for (FrameType frameType : FrameType.values()) {
            if (!frameType.name.equals(string)) continue;
            return frameType;
        }
        throw new IllegalArgumentException("Unknown frame type '" + string + "'");
    }

    public TextFormatting getFormat() {
        return this.format;
    }

    public ITextComponent getTranslatedToast() {
        return this.translatedToast;
    }
}

