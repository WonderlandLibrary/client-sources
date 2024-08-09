/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.AbstractOption;
import net.minecraft.client.GameSettings;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.SettingsScreen;
import net.minecraft.client.gui.widget.SoundSlider;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.OptionButton;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TranslationTextComponent;

public class OptionsSoundsScreen
extends SettingsScreen {
    public OptionsSoundsScreen(Screen screen, GameSettings gameSettings) {
        super(screen, gameSettings, new TranslationTextComponent("options.sounds.title"));
    }

    @Override
    protected void init() {
        int n = 0;
        this.addButton(new SoundSlider(this.minecraft, this.width / 2 - 155 + n % 2 * 160, this.height / 6 - 12 + 24 * (n >> 1), SoundCategory.MASTER, 310));
        n += 2;
        for (SoundCategory soundCategory : SoundCategory.values()) {
            if (soundCategory == SoundCategory.MASTER) continue;
            this.addButton(new SoundSlider(this.minecraft, this.width / 2 - 155 + n % 2 * 160, this.height / 6 - 12 + 24 * (n >> 1), soundCategory, 150));
            ++n;
        }
        int n2 = this.width / 2 - 75;
        int n3 = this.height / 6 - 12;
        this.addButton(new OptionButton(n2, n3 + 24 * (++n >> 1), 150, 20, AbstractOption.SHOW_SUBTITLES, AbstractOption.SHOW_SUBTITLES.func_238152_c_(this.gameSettings), this::lambda$init$0));
        this.addButton(new Button(this.width / 2 - 100, this.height / 6 + 168, 200, 20, DialogTexts.GUI_DONE, this::lambda$init$1));
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.renderBackground(matrixStack);
        OptionsSoundsScreen.drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 15, 0xFFFFFF);
        super.render(matrixStack, n, n2, f);
    }

    private void lambda$init$1(Button button) {
        this.minecraft.displayGuiScreen(this.parentScreen);
    }

    private void lambda$init$0(Button button) {
        AbstractOption.SHOW_SUBTITLES.nextValue(this.minecraft.gameSettings);
        button.setMessage(AbstractOption.SHOW_SUBTITLES.func_238152_c_(this.minecraft.gameSettings));
        this.minecraft.gameSettings.saveOptions();
    }
}

