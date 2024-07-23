package io.github.liticane.monoxide.ui.screens.clickgui.oldaugustus.window.component.impl;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import io.github.liticane.monoxide.value.impl.ModeValue;
import io.github.liticane.monoxide.util.render.RenderUtil;

import java.awt.*;

public class ModeComponent extends ValueComponent {

    private final ModeValue modeValue;
    private FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;
    private boolean expanded;

    public ModeComponent(ModeValue modeValue, float posX, float posY, float height) {
        super(modeValue, posX, posY, height);
        this.modeValue = modeValue;
    }

    @Override
    public float draw(int mouseX, int mouseY) {
        fontRenderer.drawCenteredStringWithShadow(modeValue.getName(), getPosX() + getWidth() / 2, getPosY() + 1, new Color(139, 141, 145, 255).getRGB());
        RenderUtil.drawRect(getPosX() + 2, getPosY() + getHeight() - 3, getWidth() - 4, 2, new Color(72, 79, 89, 160).getRGB());
        if(expanded) {
            float posY = this.getPosY() + this.getHeight();
            for(String string : modeValue.getValues()) {
                fontRenderer.drawTotalCenteredStringWithShadow(string, getPosX() + getWidth() / 2, posY + getHeight() / 2, !modeValue.getValue().equalsIgnoreCase(string) ? new Color(139, 141, 145, 255).getRGB() : new Color(41, 146, 222).getRGB());
                posY += this.getHeight();
            }
        }
        return fontRenderer.getStringWidthInt(modeValue.getName()) + 6;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if(RenderUtil.isHovered(mouseX, mouseY, getPosX(), getPosY(), getWidth(), getHeight()) && mouseButton == 0) {
            expanded = !expanded;
        }
        if(expanded) {
            float posY = this.getPosY() + this.getHeight();
            for(String string : modeValue.getValues()) {
                if(RenderUtil.isHovered(mouseX, mouseY, getPosX(), posY, getWidth(), getHeight()) && mouseButton == 0) {
                    modeValue.setValue(string);
                }
                posY += this.getHeight();
            }
        }
    }

    public float getFinalHeight() {
        return getHeight() + (expanded ? modeValue.getValues().length * getHeight() : 0);
    }

}
