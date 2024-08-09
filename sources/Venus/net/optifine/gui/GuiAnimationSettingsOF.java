/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.AbstractOption;
import net.minecraft.client.GameSettings;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.StringTextComponent;
import net.optifine.Lang;
import net.optifine.gui.GuiButtonOF;
import net.optifine.gui.GuiScreenButtonOF;
import net.optifine.gui.GuiScreenOF;

public class GuiAnimationSettingsOF
extends GuiScreenOF {
    private Screen prevScreen;
    private GameSettings settings;
    private static AbstractOption[] enumOptions = new AbstractOption[]{AbstractOption.ANIMATED_WATER, AbstractOption.ANIMATED_LAVA, AbstractOption.ANIMATED_FIRE, AbstractOption.ANIMATED_PORTAL, AbstractOption.ANIMATED_REDSTONE, AbstractOption.ANIMATED_EXPLOSION, AbstractOption.ANIMATED_FLAME, AbstractOption.ANIMATED_SMOKE, AbstractOption.VOID_PARTICLES, AbstractOption.WATER_PARTICLES, AbstractOption.RAIN_SPLASH, AbstractOption.PORTAL_PARTICLES, AbstractOption.POTION_PARTICLES, AbstractOption.DRIPPING_WATER_LAVA, AbstractOption.ANIMATED_TERRAIN, AbstractOption.ANIMATED_TEXTURES, AbstractOption.FIREWORK_PARTICLES, AbstractOption.PARTICLES};

    public GuiAnimationSettingsOF(Screen screen, GameSettings gameSettings) {
        super(new StringTextComponent(I18n.format("of.options.animationsTitle", new Object[0])));
        this.prevScreen = screen;
        this.settings = gameSettings;
    }

    @Override
    public void init() {
        this.buttonList.clear();
        for (int i = 0; i < enumOptions.length; ++i) {
            AbstractOption abstractOption = enumOptions[i];
            int n = this.width / 2 - 155 + i % 2 * 160;
            int n2 = this.height / 6 + 21 * (i / 2) - 12;
            this.addButton(abstractOption.createWidget(this.minecraft.gameSettings, n, n2, 150));
        }
        this.addButton(new GuiButtonOF(210, this.width / 2 - 155, this.height / 6 + 168 + 11, 70, 20, Lang.get("of.options.animation.allOn")));
        this.addButton(new GuiButtonOF(211, this.width / 2 - 155 + 80, this.height / 6 + 168 + 11, 70, 20, Lang.get("of.options.animation.allOff")));
        this.addButton(new GuiScreenButtonOF(200, this.width / 2 + 5, this.height / 6 + 168 + 11, I18n.format("gui.done", new Object[0])));
    }

    @Override
    protected void actionPerformed(Widget widget) {
        if (widget instanceof GuiButtonOF) {
            GuiButtonOF guiButtonOF = (GuiButtonOF)widget;
            if (guiButtonOF.active) {
                if (guiButtonOF.id == 200) {
                    this.minecraft.gameSettings.saveOptions();
                    this.minecraft.displayGuiScreen(this.prevScreen);
                }
                if (guiButtonOF.id == 210) {
                    this.minecraft.gameSettings.setAllAnimations(false);
                }
                if (guiButtonOF.id == 211) {
                    this.minecraft.gameSettings.setAllAnimations(true);
                }
                this.minecraft.updateWindowSize();
            }
        }
    }

    @Override
    public void onClose() {
        this.minecraft.gameSettings.saveOptions();
        super.onClose();
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.renderBackground(matrixStack);
        GuiAnimationSettingsOF.drawCenteredString(matrixStack, this.minecraft.fontRenderer, this.title, this.width / 2, 15, 0xFFFFFF);
        super.render(matrixStack, n, n2, f);
    }
}

