/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.widget;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.widget.GameSettingsSlider;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class SoundSlider
extends GameSettingsSlider {
    private final SoundCategory category;

    public SoundSlider(Minecraft minecraft, int n, int n2, SoundCategory soundCategory, int n3) {
        super(minecraft.gameSettings, n, n2, n3, 20, (double)minecraft.gameSettings.getSoundLevel(soundCategory));
        this.category = soundCategory;
        this.func_230979_b_();
    }

    @Override
    protected void func_230979_b_() {
        ITextComponent iTextComponent = (float)this.sliderValue == (float)this.getYImage(true) ? DialogTexts.OPTIONS_OFF : new StringTextComponent((int)(this.sliderValue * 100.0) + "%");
        this.setMessage(new TranslationTextComponent("soundCategory." + this.category.getName()).appendString(": ").append(iTextComponent));
    }

    @Override
    protected void func_230972_a_() {
        this.settings.setSoundLevel(this.category, (float)this.sliderValue);
        this.settings.saveOptions();
    }
}

