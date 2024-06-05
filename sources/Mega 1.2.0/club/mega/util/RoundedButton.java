package club.mega.util;

import club.mega.Mega;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;

public class RoundedButton extends GuiButton {

    private final Color color;
    private final Color hoverColor;
    private final int cr;

    public RoundedButton(final int buttonId, final int x, final int y, final int cr, final String buttonText) {
        super(buttonId, x, y, buttonText);
        color = new Color(15,15,15,180);
        hoverColor = new Color(25,25,25,180);
        this.cr = cr;
    }

    public RoundedButton(final int buttonId, final int x, final int y, final int widthIn, final int heightIn, final int cr, final String buttonText) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
        color = new Color(15,15,15,180);
        hoverColor = new Color(25,25,25,180);
        this.cr = cr;
    }

    public RoundedButton(final int buttonId, final int x, final int y, final Color color, final Color hoverColor, final int cr, final String buttonText) {
        super(buttonId, x, y, buttonText);
        this.color = color;
        this.hoverColor = hoverColor;
        this.cr = cr;
    }

    public RoundedButton(final int buttonId, final int x, final int y, final int widthIn, final int heightIn, final Color color, final Color hoverColor, final int cr, final String buttonText) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
        this.color = color;
        this.hoverColor = hoverColor;
        this.cr = cr;
    }

    @Override
    public void drawButton(final Minecraft mc, final int mouseX, final int mouseY)
    {
        if (this.visible)
        {
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            this.mouseDragged(mc, mouseX, mouseY);
            Color color = this.color;

            if (!this.enabled)
            {
                color = new Color(10, 10, 10);
            }
            else if (this.hovered)
            {
                color = hoverColor;
            }

            RenderUtil.drawRoundedRect(xPosition, yPosition, width, height, cr, color);
            Mega.INSTANCE.getFontManager().getFont("Roboto bold 20").drawCenteredString(displayString, xPosition + width / 2D, this.yPosition + (this.height - 8) / 2D, -1);
        }
    }

}
