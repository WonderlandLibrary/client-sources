/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.AbstractOption;
import net.minecraft.client.FullscreenResolutionOption;
import net.minecraft.client.GameSettings;
import net.minecraft.client.gui.screen.ConfirmScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.StringTextComponent;
import net.optifine.gui.GuiButtonOF;
import net.optifine.gui.GuiScreenOF;
import net.optifine.gui.TooltipManager;
import net.optifine.gui.TooltipProviderOptions;

public class GuiOtherSettingsOF
extends GuiScreenOF {
    private Screen prevScreen;
    private GameSettings settings;
    private TooltipManager tooltipManager = new TooltipManager(this, new TooltipProviderOptions());

    public GuiOtherSettingsOF(Screen screen, GameSettings gameSettings) {
        super(new StringTextComponent(I18n.format("of.options.otherTitle", new Object[0])));
        this.prevScreen = screen;
        this.settings = gameSettings;
    }

    @Override
    public void init() {
        this.buttonList.clear();
        FullscreenResolutionOption fullscreenResolutionOption = new FullscreenResolutionOption(this.minecraft.getMainWindow());
        AbstractOption[] abstractOptionArray = new AbstractOption[]{AbstractOption.LAGOMETER, AbstractOption.PROFILER, AbstractOption.SHOW_FPS, AbstractOption.ADVANCED_TOOLTIPS, AbstractOption.WEATHER, AbstractOption.TIME, AbstractOption.FULLSCREEN, AbstractOption.AUTOSAVE_TICKS, AbstractOption.SCREENSHOT_SIZE, AbstractOption.SHOW_GL_ERRORS, fullscreenResolutionOption, null};
        for (int i = 0; i < abstractOptionArray.length; ++i) {
            AbstractOption abstractOption = abstractOptionArray[i];
            int n = this.width / 2 - 155 + i % 2 * 160;
            int n2 = this.height / 6 + 21 * (i / 2) - 12;
            Widget widget = this.addButton(abstractOption.createWidget(this.minecraft.gameSettings, n, n2, 150));
            if (abstractOption != fullscreenResolutionOption) continue;
            widget.setWidth(310);
            ++i;
        }
        this.addButton(new GuiButtonOF(210, this.width / 2 - 100, this.height / 6 + 168 + 11 - 44, I18n.format("of.options.other.reset", new Object[0])));
        this.addButton(new GuiButtonOF(200, this.width / 2 - 100, this.height / 6 + 168 + 11, I18n.format("gui.done", new Object[0])));
    }

    @Override
    protected void actionPerformed(Widget widget) {
        if (widget instanceof GuiButtonOF) {
            GuiButtonOF guiButtonOF = (GuiButtonOF)widget;
            if (guiButtonOF.active) {
                if (guiButtonOF.id == 200) {
                    this.minecraft.gameSettings.saveOptions();
                    this.minecraft.getMainWindow().update();
                    this.minecraft.displayGuiScreen(this.prevScreen);
                }
                if (guiButtonOF.id == 210) {
                    this.minecraft.gameSettings.saveOptions();
                    String string = I18n.format("of.message.other.reset", new Object[0]);
                    ConfirmScreen confirmScreen = new ConfirmScreen(this::confirmResult, new StringTextComponent(string), new StringTextComponent(""));
                    this.minecraft.displayGuiScreen(confirmScreen);
                }
            }
        }
    }

    @Override
    public void onClose() {
        this.minecraft.gameSettings.saveOptions();
        this.minecraft.getMainWindow().update();
        super.onClose();
    }

    public void confirmResult(boolean bl) {
        if (bl) {
            this.minecraft.gameSettings.resetSettings();
        }
        this.minecraft.displayGuiScreen(this);
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.renderBackground(matrixStack);
        GuiOtherSettingsOF.drawCenteredString(matrixStack, this.fontRenderer, this.title, this.width / 2, 15, 0xFFFFFF);
        super.render(matrixStack, n, n2, f);
        this.tooltipManager.drawTooltips(matrixStack, n, n2, this.buttonList);
    }
}

