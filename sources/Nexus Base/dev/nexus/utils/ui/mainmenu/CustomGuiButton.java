package dev.nexus.utils.ui.mainmenu;

import dev.nexus.utils.render.DrawUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;

public class CustomGuiButton extends GuiButton {

    private final Color buttonColor;
    private final Color hoverColor;

    public CustomGuiButton(int buttonId, int x, int y, int width, int height, String buttonText, Color buttonColor) {
        super(buttonId, x, y, width, height, buttonText);
        this.buttonColor = buttonColor;
        this.hoverColor = darkenColor(buttonColor, 0.2f);
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (this.visible) {
            boolean isHovered = mouseX >= this.xPosition && mouseX < this.xPosition + this.width && mouseY >= this.yPosition && mouseY < this.yPosition + this.height;

            Color currentColor = isHovered ? hoverColor : buttonColor;

            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            DrawUtils.drawRoundedGradientOutlineCorner(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, 2, 5, -1, currentColor.getRGB());

            drawCenteredString(mc.fontRendererObj, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, 0xFFFFFF);
        }
    }

    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        return mouseX >= this.xPosition && mouseX < this.xPosition + this.width && mouseY >= this.yPosition && mouseY < this.yPosition + this.height;
    }

    private Color darkenColor(Color color, float factor) {
        int r = (int) (color.getRed() * (1 - factor));
        int g = (int) (color.getGreen() * (1 - factor));
        int b = (int) (color.getBlue() * (1 - factor));
        return new Color(r, g, b, color.getAlpha());
    }
}
