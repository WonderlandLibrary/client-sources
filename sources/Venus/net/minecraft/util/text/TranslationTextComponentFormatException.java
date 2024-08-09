/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.text;

import net.minecraft.util.text.TranslationTextComponent;

public class TranslationTextComponentFormatException
extends IllegalArgumentException {
    public TranslationTextComponentFormatException(TranslationTextComponent translationTextComponent, String string) {
        super(String.format("Error parsing: %s: %s", translationTextComponent, string));
    }

    public TranslationTextComponentFormatException(TranslationTextComponent translationTextComponent, int n) {
        super(String.format("Invalid index %d requested for %s", n, translationTextComponent));
    }

    public TranslationTextComponentFormatException(TranslationTextComponent translationTextComponent, Throwable throwable) {
        super(String.format("Error while parsing: %s", translationTextComponent), throwable);
    }
}

