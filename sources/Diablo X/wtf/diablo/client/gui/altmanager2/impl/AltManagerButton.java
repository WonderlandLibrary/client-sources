package wtf.diablo.client.gui.altmanager2.impl;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import wtf.diablo.client.util.render.RenderUtil;

public class AltManagerButton extends GuiButton {
    public AltManagerButton(int buttonId, int x, int y, String buttonText) {
        super(buttonId, x, y, buttonText);
    }

    public AltManagerButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (this.visible) {
            FontRenderer fontrenderer = mc.fontRendererObj;
            this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;

            RenderUtil.drawRectWidth(xPosition, yPosition, width, height, 0xFF171717);
            RenderUtil.drawRectOutlineWidth(xPosition, yPosition, width, height, enabled ? (hovered ? 0xFF8C61B2 : 0xFF303030) : 0xFF202020, 0.5f);


            this.mouseDragged(mc, mouseX, mouseY);
            int colour = -1;

            if (!enabled) {
                colour = 0xFF666666;
            } else if (hovered) {
                colour = 0xFF8C61B2;
            }

            this.drawCenteredString(fontrenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, colour);
        }
    }
}
