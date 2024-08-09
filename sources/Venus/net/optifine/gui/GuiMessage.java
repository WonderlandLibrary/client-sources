/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.gui;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.List;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.optifine.Config;
import net.optifine.gui.GuiButtonOF;
import net.optifine.gui.GuiScreenOF;

public class GuiMessage
extends GuiScreenOF {
    private Screen parentScreen;
    private ITextComponent messageLine1;
    private ITextComponent messageLine2;
    private final List<IReorderingProcessor> listLines2 = Lists.newArrayList();
    protected String confirmButtonText;
    private int ticksUntilEnable;

    public GuiMessage(Screen screen, String string, String string2) {
        super(new TranslationTextComponent("of.options.detailsTitle"));
        this.parentScreen = screen;
        this.messageLine1 = new StringTextComponent(string);
        this.messageLine2 = new StringTextComponent(string2);
        this.confirmButtonText = I18n.format("gui.done", new Object[0]);
    }

    @Override
    public void init() {
        this.addButton(new GuiButtonOF(0, this.width / 2 - 100, this.height / 6 + 96, this.confirmButtonText));
        this.listLines2.clear();
        this.listLines2.addAll(this.minecraft.fontRenderer.trimStringToWidth(this.messageLine2, this.width - 50));
    }

    @Override
    protected void actionPerformed(Widget widget) {
        Config.getMinecraft().displayGuiScreen(this.parentScreen);
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.renderBackground(matrixStack);
        GuiMessage.drawCenteredString(matrixStack, this.fontRenderer, this.messageLine1, this.width / 2, 70, 0xFFFFFF);
        int n3 = 90;
        for (IReorderingProcessor iReorderingProcessor : this.listLines2) {
            GuiMessage.drawCenteredString(matrixStack, this.fontRenderer, iReorderingProcessor, this.width / 2, n3, 0xFFFFFF);
            n3 += 9;
        }
        super.render(matrixStack, n, n2, f);
    }

    public void setButtonDelay(int n) {
        this.ticksUntilEnable = n;
        for (Widget widget : this.buttonList) {
            widget.active = false;
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (--this.ticksUntilEnable == 0) {
            for (Widget widget : this.buttonList) {
                widget.active = true;
            }
        }
    }
}

