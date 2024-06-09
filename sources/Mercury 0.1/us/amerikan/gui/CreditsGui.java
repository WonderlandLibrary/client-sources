/*
 * Decompiled with CFR 0.145.
 */
package us.amerikan.gui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Keyboard;

public class CreditsGui
extends GuiScreen {
    public GuiTextField email;
    public GuiTextField pw;
    public GuiScreen alterScreen;
    private Object GuiButton;

    public CreditsGui(GuiScreen alt2) {
        this.alterScreen = alt2;
    }

    @Override
    public void initGui() {
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    public void updateScreen() {
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawBackground(1);
        CreditsGui.drawString(this.fontRendererObj, "Comming soon", this.width / 2 - this.fontRendererObj.getStringWidth("Comming soon") / 2, this.height / 2 - this.fontRendererObj.FONT_HEIGHT / 2, -1);
    }
}

