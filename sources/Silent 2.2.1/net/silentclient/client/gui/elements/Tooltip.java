package net.silentclient.client.gui.elements;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.silentclient.client.Client;
import net.silentclient.client.gui.font.SilentFontRenderer;
import net.silentclient.client.gui.lite.clickgui.utils.MouseUtils;
import net.silentclient.client.gui.lite.clickgui.utils.RenderUtils;
import net.silentclient.client.gui.theme.Theme;

public class Tooltip {
    public static void render(int mouseX, int mouseY, float x, float y, int width, int height, String text) {
        if(MouseUtils.isInside(mouseX, mouseY, x, y, width, height) && text != null) {
            float tooltipWidth = Client.getInstance().getSilentFontRenderer().getStringWidth(text, 10, SilentFontRenderer.FontType.TITLE) + 4;
            float tooltipX = x + ((width / 2) - (tooltipWidth / 2));
            float tooltipY = y + height + 2;
            ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
            if(tooltipX < 2) {
                tooltipX = 2;
            }
            if(tooltipX + tooltipWidth > scaledResolution.getScaledWidth()) {
                tooltipX = scaledResolution.getScaledWidth() - tooltipWidth - 2;
            }
            if(tooltipY + 14 > scaledResolution.getScaledHeight()) {
                tooltipY = y - 16;
            }
            RenderUtils.drawRect(tooltipX, tooltipY, tooltipWidth, 14, Theme.backgroundColor().getRGB());
            Client.getInstance().getSilentFontRenderer().drawString(text, tooltipX + 2, tooltipY + 2, 10, SilentFontRenderer.FontType.TITLE);
        }
    }
}
