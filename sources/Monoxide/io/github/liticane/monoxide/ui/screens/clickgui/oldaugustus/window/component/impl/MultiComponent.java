package io.github.liticane.monoxide.ui.screens.clickgui.oldaugustus.window.component.impl;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import io.github.liticane.monoxide.value.impl.MultiBooleanValue;
import io.github.liticane.monoxide.util.render.RenderUtil;

import java.awt.*;

public class MultiComponent extends ValueComponent {

    private final MultiBooleanValue multiBooleanValue;
    private FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;
    private boolean expanded;

    public MultiComponent(MultiBooleanValue multiBooleanValue, float posX, float posY, float height) {
        super(multiBooleanValue, posX, posY, height);
        this.multiBooleanValue = multiBooleanValue;
    }

    @Override
    public float draw(int mouseX, int mouseY) {
        fontRenderer.drawCenteredStringWithShadow(multiBooleanValue.getName(), getPosX() + getWidth() / 2, getPosY() + 1, new Color(139, 141, 145, 255).getRGB());
        RenderUtil.drawRect(getPosX() + 2, getPosY() + getHeight() - 3, getWidth() - 4, 2, new Color(72, 79, 89, 160).getRGB());
        if(expanded) {
            float posY = this.getPosY() + this.getHeight();
            for(String string : multiBooleanValue.getValues()) {
                fontRenderer.drawTotalCenteredStringWithShadow(string, getPosX() + getWidth() / 2, posY + getHeight() / 2, !multiBooleanValue.get(string) ? new Color(139, 141, 145, 255).getRGB() : new Color(41, 146, 222).getRGB());
                posY += this.getHeight();
            }
        }
        return fontRenderer.getStringWidthInt(multiBooleanValue.getName()) + 6;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if(RenderUtil.isHovered(mouseX, mouseY, getPosX(), getPosY(), getWidth(), getHeight()) && mouseButton == 0) {
            expanded = !expanded;
        }
        if(expanded) {
            float posY = this.getPosY() + this.getHeight();
            String toToggle = null;
            for(String string : multiBooleanValue.getValues()) {
                if(RenderUtil.isHovered(mouseX, mouseY, getPosX(), posY, getWidth(), getHeight()) && mouseButton == 0) {
                    toToggle = string;
                }
                posY += this.getHeight();
            }
            if(toToggle != null)
                multiBooleanValue.toggle(toToggle);
        }
    }

    public float getFinalHeight() {
        return getHeight() + (expanded ? multiBooleanValue.getValues().length * getHeight() : 0);
    }

}
