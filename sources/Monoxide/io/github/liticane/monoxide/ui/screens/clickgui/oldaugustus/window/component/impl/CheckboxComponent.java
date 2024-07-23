package io.github.liticane.monoxide.ui.screens.clickgui.oldaugustus.window.component.impl;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import io.github.liticane.monoxide.value.impl.BooleanValue;
import io.github.liticane.monoxide.util.render.RenderUtil;

import java.awt.*;

public class CheckboxComponent extends ValueComponent {

    private final BooleanValue booleanValue;
    private FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;

    public CheckboxComponent(BooleanValue booleanValue, float posX, float posY, float height) {
        super(booleanValue, posX, posY, height);
        this.booleanValue = booleanValue;
    }

    @Override
    public float draw(int mouseX, int mouseY) {
        fontRenderer.drawStringWithShadow("x", getPosX() + 5, getPosY() + getFinalHeight() / 2 - fontRenderer.FONT_HEIGHT / 2 - 1, !booleanValue.getValue() ? new Color(139, 141, 145, 255).getRGB() : new Color(41, 146, 222).getRGB());
        fontRenderer.drawStringWithShadow(booleanValue.getName(), getPosX() + 13 + fontRenderer.getStringWidthInt("X"), getPosY() + getFinalHeight() / 2 - fontRenderer.FONT_HEIGHT / 2, new Color(139, 141, 145, 255).getRGB());
        return fontRenderer.getStringWidthInt(booleanValue.getName()) + 15 + fontRenderer.getStringWidthInt("X");
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if(RenderUtil.isHovered(mouseX, mouseY, getPosX(), getPosY(), getWidth(), getHeight()) && mouseButton == 0) {
            booleanValue.setValue(!booleanValue.getValue());
        }
    }

}
