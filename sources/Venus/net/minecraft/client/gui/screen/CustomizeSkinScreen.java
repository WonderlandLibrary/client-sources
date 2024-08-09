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
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.OptionButton;
import net.minecraft.entity.player.PlayerModelPart;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.optifine.Lang;
import net.optifine.gui.GuiScreenCapeOF;

public class CustomizeSkinScreen
extends SettingsScreen {
    public CustomizeSkinScreen(Screen screen, GameSettings gameSettings) {
        super(screen, gameSettings, new TranslationTextComponent("options.skinCustomisation.title"));
    }

    @Override
    protected void init() {
        int n = 0;
        for (PlayerModelPart playerModelPart : PlayerModelPart.values()) {
            this.addButton(new Button(this.width / 2 - 155 + n % 2 * 160, this.height / 6 + 24 * (n >> 1), 150, 20, this.func_238655_a_(playerModelPart), arg_0 -> this.lambda$init$0(playerModelPart, arg_0)));
            ++n;
        }
        this.addButton(new OptionButton(this.width / 2 - 155 + n % 2 * 160, this.height / 6 + 24 * (n >> 1), 150, 20, AbstractOption.MAIN_HAND, AbstractOption.MAIN_HAND.getName(this.gameSettings), this::lambda$init$1));
        if (++n % 2 == 1) {
            ++n;
        }
        this.addButton(new Button(this.width / 2 - 100, this.height / 6 + 24 * (n >> 1), 200, 20, Lang.getComponent("of.options.skinCustomisation.ofCape"), this::lambda$init$2));
        this.addButton(new Button(this.width / 2 - 100, this.height / 6 + 24 * ((n += 2) >> 1), 200, 20, DialogTexts.GUI_DONE, this::lambda$init$3));
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.renderBackground(matrixStack);
        CustomizeSkinScreen.drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 20, 0xFFFFFF);
        super.render(matrixStack, n, n2, f);
    }

    private ITextComponent func_238655_a_(PlayerModelPart playerModelPart) {
        return DialogTexts.getComposedOptionMessage(playerModelPart.getName(), this.gameSettings.getModelParts().contains((Object)playerModelPart));
    }

    private void lambda$init$3(Button button) {
        this.minecraft.displayGuiScreen(this.parentScreen);
    }

    private void lambda$init$2(Button button) {
        this.minecraft.displayGuiScreen(new GuiScreenCapeOF(this));
    }

    private void lambda$init$1(Button button) {
        AbstractOption.MAIN_HAND.setValueIndex(this.gameSettings, 1);
        this.gameSettings.saveOptions();
        button.setMessage(AbstractOption.MAIN_HAND.getName(this.gameSettings));
        this.gameSettings.sendSettingsToServer();
    }

    private void lambda$init$0(PlayerModelPart playerModelPart, Button button) {
        this.gameSettings.switchModelPartEnabled(playerModelPart);
        button.setMessage(this.func_238655_a_(playerModelPart));
    }
}

