/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.GameSettings;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.StringTextComponent;
import net.optifine.Config;
import net.optifine.Lang;
import net.optifine.gui.GuiButtonOF;
import net.optifine.gui.GuiScreenOF;
import net.optifine.gui.TooltipManager;
import net.optifine.gui.TooltipProviderShaderOptions;
import net.optifine.shaders.Shaders;
import net.optifine.shaders.config.ShaderOption;
import net.optifine.shaders.config.ShaderOptionProfile;
import net.optifine.shaders.config.ShaderOptionScreen;
import net.optifine.shaders.gui.GuiButtonShaderOption;
import net.optifine.shaders.gui.GuiSliderShaderOption;

public class GuiShaderOptions
extends GuiScreenOF {
    private Screen prevScreen;
    private GameSettings settings;
    private TooltipManager tooltipManager = new TooltipManager(this, new TooltipProviderShaderOptions());
    private String screenName = null;
    private String screenText = null;
    private boolean changed = false;
    public static final String OPTION_PROFILE = "<profile>";
    public static final String OPTION_EMPTY = "<empty>";
    public static final String OPTION_REST = "*";

    public GuiShaderOptions(Screen screen, GameSettings gameSettings) {
        super(new StringTextComponent(I18n.format("of.options.shaderOptionsTitle", new Object[0])));
        this.prevScreen = screen;
        this.settings = gameSettings;
    }

    public GuiShaderOptions(Screen screen, GameSettings gameSettings, String string) {
        this(screen, gameSettings);
        this.screenName = string;
        if (string != null) {
            this.screenText = Shaders.translate("screen." + string, string);
        }
    }

    @Override
    public void init() {
        int n = 100;
        int n2 = 0;
        int n3 = 30;
        int n4 = 20;
        int n5 = 120;
        int n6 = 20;
        int n7 = Shaders.getShaderPackColumns(this.screenName, 2);
        ShaderOption[] shaderOptionArray = Shaders.getShaderPackOptions(this.screenName);
        if (shaderOptionArray != null) {
            int n8 = MathHelper.ceil((double)shaderOptionArray.length / 9.0);
            if (n7 < n8) {
                n7 = n8;
            }
            for (int i = 0; i < shaderOptionArray.length; ++i) {
                ShaderOption shaderOption = shaderOptionArray[i];
                if (shaderOption == null || !shaderOption.isVisible()) continue;
                int n9 = i % n7;
                int n10 = i / n7;
                int n11 = Math.min(this.width / n7, 200);
                n2 = (this.width - n11 * n7) / 2;
                int n12 = n9 * n11 + 5 + n2;
                int n13 = n3 + n10 * n4;
                int n14 = n11 - 10;
                String string = GuiShaderOptions.getButtonText(shaderOption, n14);
                GuiButtonShaderOption guiButtonShaderOption = Shaders.isShaderPackOptionSlider(shaderOption.getName()) ? new GuiSliderShaderOption(n + i, n12, n13, n14, n6, shaderOption, string) : new GuiButtonShaderOption(n + i, n12, n13, n14, n6, shaderOption, string);
                guiButtonShaderOption.active = shaderOption.isEnabled();
                this.addButton(guiButtonShaderOption);
            }
        }
        this.addButton(new GuiButtonOF(201, this.width / 2 - n5 - 20, this.height / 6 + 168 + 11, n5, n6, I18n.format("controls.reset", new Object[0])));
        this.addButton(new GuiButtonOF(200, this.width / 2 + 20, this.height / 6 + 168 + 11, n5, n6, I18n.format("gui.done", new Object[0])));
    }

    public static String getButtonText(ShaderOption shaderOption, int n) {
        String string = shaderOption.getNameText();
        if (shaderOption instanceof ShaderOptionScreen) {
            ShaderOptionScreen shaderOptionScreen = (ShaderOptionScreen)shaderOption;
            return string + "...";
        }
        FontRenderer fontRenderer = Config.getMinecraft().fontRenderer;
        int n2 = fontRenderer.getStringWidth(": " + Lang.getOff()) + 5;
        while (fontRenderer.getStringWidth(string) + n2 >= n && string.length() > 0) {
            string = string.substring(0, string.length() - 1);
        }
        String string2 = shaderOption.isChanged() ? shaderOption.getValueColor(shaderOption.getValue()) : "";
        String string3 = shaderOption.getValueText(shaderOption.getValue());
        return string + ": " + string2 + string3;
    }

    @Override
    protected void actionPerformed(Widget widget) {
        if (widget instanceof GuiButtonOF) {
            GuiButtonOF guiButtonOF = (GuiButtonOF)widget;
            if (guiButtonOF.active) {
                ShaderOption[] shaderOptionArray;
                if (guiButtonOF.id < 200 && guiButtonOF instanceof GuiButtonShaderOption) {
                    shaderOptionArray = (ShaderOption[])guiButtonOF;
                    ShaderOption shaderOption = shaderOptionArray.getShaderOption();
                    if (shaderOption instanceof ShaderOptionScreen) {
                        String string = shaderOption.getName();
                        GuiShaderOptions guiShaderOptions = new GuiShaderOptions(this, this.settings, string);
                        this.minecraft.displayGuiScreen(guiShaderOptions);
                        return;
                    }
                    if (GuiShaderOptions.hasShiftDown()) {
                        shaderOption.resetValue();
                    } else if (shaderOptionArray.isSwitchable()) {
                        shaderOption.nextValue();
                    }
                    this.updateAllButtons();
                    this.changed = true;
                }
                if (guiButtonOF.id == 201) {
                    shaderOptionArray = Shaders.getChangedOptions(Shaders.getShaderPackOptions());
                    for (int i = 0; i < shaderOptionArray.length; ++i) {
                        ShaderOption shaderOption = shaderOptionArray[i];
                        shaderOption.resetValue();
                        this.changed = true;
                    }
                    this.updateAllButtons();
                }
                if (guiButtonOF.id == 200) {
                    if (this.changed) {
                        Shaders.saveShaderPackOptions();
                        this.changed = false;
                        Shaders.uninit();
                    }
                    this.minecraft.displayGuiScreen(this.prevScreen);
                }
            }
        }
    }

    @Override
    public void onClose() {
        if (this.changed) {
            Shaders.saveShaderPackOptions();
            this.changed = false;
            Shaders.uninit();
        }
        super.onClose();
    }

    @Override
    protected void actionPerformedRightClick(Widget widget) {
        if (widget instanceof GuiButtonShaderOption) {
            GuiButtonShaderOption guiButtonShaderOption = (GuiButtonShaderOption)widget;
            ShaderOption shaderOption = guiButtonShaderOption.getShaderOption();
            if (GuiShaderOptions.hasShiftDown()) {
                shaderOption.resetValue();
            } else if (guiButtonShaderOption.isSwitchable()) {
                shaderOption.prevValue();
            }
            this.updateAllButtons();
            this.changed = true;
        }
    }

    private void updateAllButtons() {
        for (Widget widget : this.buttonList) {
            if (!(widget instanceof GuiButtonShaderOption)) continue;
            GuiButtonShaderOption guiButtonShaderOption = (GuiButtonShaderOption)widget;
            ShaderOption shaderOption = guiButtonShaderOption.getShaderOption();
            if (shaderOption instanceof ShaderOptionProfile) {
                ShaderOptionProfile shaderOptionProfile = (ShaderOptionProfile)shaderOption;
                shaderOptionProfile.updateProfile();
            }
            guiButtonShaderOption.setMessage(GuiShaderOptions.getButtonText(shaderOption, guiButtonShaderOption.getWidth()));
            guiButtonShaderOption.valueChanged();
        }
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.renderBackground(matrixStack);
        if (this.screenText != null) {
            GuiShaderOptions.drawCenteredString(matrixStack, this.fontRenderer, this.screenText, this.width / 2, 15, 0xFFFFFF);
        } else {
            GuiShaderOptions.drawCenteredString(matrixStack, this.fontRenderer, this.title, this.width / 2, 15, 0xFFFFFF);
        }
        super.render(matrixStack, n, n2, f);
        this.tooltipManager.drawTooltips(matrixStack, n, n2, this.buttonList);
    }
}

