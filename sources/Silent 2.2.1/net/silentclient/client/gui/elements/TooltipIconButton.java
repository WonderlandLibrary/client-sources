package net.silentclient.client.gui.elements;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class TooltipIconButton extends IconButton {
    private final String tooltipText;

    public TooltipIconButton(int buttonId, int x, int y, int widthIn, int heightIn, int iconWidth, int iconHeight, ResourceLocation icon, String tooltipText) {
        super(buttonId, x, y, widthIn, heightIn, iconWidth, iconHeight, icon);
        this.tooltipText = tooltipText;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        super.drawButton(mc, mouseX, mouseY);
        Tooltip.render(mouseX, mouseY, this.xPosition, this.yPosition, this.width, this.height, this.tooltipText);
    }
}
