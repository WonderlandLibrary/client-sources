/*
 * Decompiled with CFR 0_118.
 */
package me.imfr0zen.guiapi.components;

import me.imfr0zen.guiapi.ClickGui;
import me.imfr0zen.guiapi.RenderUtil;
import me.imfr0zen.guiapi.components.GuiComponent;
import net.minecraft.client.gui.FontRenderer;

public class GuiLabel
implements GuiComponent {
    private String text;

    public GuiLabel(String text) {
        this.text = text;
    }

    @Override
    public void render(int posX, int posY, int width, int mouseX, int mouseY) {
        RenderUtil.drawString(this.text, posX + 3, posY + 2, -1);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
    }

    @Override
    public void keyTyped(int keyCode, char typedChar) {
    }

    @Override
    public int getWidth() {
        return ClickGui.FONTRENDERER.getStringWidth(this.text) + 4;
    }

    @Override
    public int getHeight() {
        return ClickGui.FONTRENDERER.FONT_HEIGHT + 2;
    }
}

