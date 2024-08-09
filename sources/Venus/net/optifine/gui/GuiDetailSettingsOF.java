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
import net.optifine.gui.GuiButtonOF;
import net.optifine.gui.GuiScreenOF;
import net.optifine.gui.TooltipManager;
import net.optifine.gui.TooltipProviderOptions;

public class GuiDetailSettingsOF
extends GuiScreenOF {
    private Screen prevScreen;
    private GameSettings settings;
    private static AbstractOption[] enumOptions = new AbstractOption[]{AbstractOption.CLOUDS, AbstractOption.CLOUD_HEIGHT, AbstractOption.TREES, AbstractOption.RAIN, AbstractOption.SKY, AbstractOption.STARS, AbstractOption.SUN_MOON, AbstractOption.SHOW_CAPES, AbstractOption.FOG_FANCY, AbstractOption.FOG_START, AbstractOption.TRANSLUCENT_BLOCKS, AbstractOption.HELD_ITEM_TOOLTIPS, AbstractOption.DROPPED_ITEMS, AbstractOption.SWAMP_COLORS, AbstractOption.VIGNETTE, AbstractOption.ALTERNATE_BLOCKS, AbstractOption.ENTITY_DISTANCE_SCALING, AbstractOption.BIOME_BLEND_RADIUS};
    private TooltipManager tooltipManager = new TooltipManager(this, new TooltipProviderOptions());

    public GuiDetailSettingsOF(Screen screen, GameSettings gameSettings) {
        super(new StringTextComponent(I18n.format("of.options.detailsTitle", new Object[0])));
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
        this.addButton(new GuiButtonOF(200, this.width / 2 - 100, this.height / 6 + 168 + 11, I18n.format("gui.done", new Object[0])));
    }

    @Override
    protected void actionPerformed(Widget widget) {
        if (widget instanceof GuiButtonOF) {
            GuiButtonOF guiButtonOF = (GuiButtonOF)widget;
            if (guiButtonOF.active && guiButtonOF.id == 200) {
                this.minecraft.gameSettings.saveOptions();
                this.minecraft.displayGuiScreen(this.prevScreen);
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
        GuiDetailSettingsOF.drawCenteredString(matrixStack, this.minecraft.fontRenderer, this.title, this.width / 2, 15, 0xFFFFFF);
        super.render(matrixStack, n, n2, f);
        this.tooltipManager.drawTooltips(matrixStack, n, n2, this.buttonList);
    }
}

