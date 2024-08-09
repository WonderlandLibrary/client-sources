/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.config;

import net.minecraft.client.GameSettings;
import net.minecraft.client.settings.IteratableOption;
import net.minecraft.util.text.ITextComponent;

public class IteratableOptionOF
extends IteratableOption {
    public IteratableOptionOF(String string) {
        super(string, null, null);
        this.setter = this::nextOptionValue;
        this.getter = this::getOptionText;
    }

    public void nextOptionValue(GameSettings gameSettings, int n) {
        gameSettings.setOptionValueOF(this, n);
    }

    public ITextComponent getOptionText(GameSettings gameSettings, IteratableOption iteratableOption) {
        return gameSettings.getKeyComponentOF(iteratableOption);
    }
}

