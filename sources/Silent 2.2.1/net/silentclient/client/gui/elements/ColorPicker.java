package net.silentclient.client.gui.elements;

import net.silentclient.client.Client;
import net.silentclient.client.gui.lite.clickgui.utils.MouseUtils;
import net.silentclient.client.gui.font.SilentFontRenderer;
import net.silentclient.client.gui.util.RenderUtil;

import java.awt.*;

public class ColorPicker {
    public static void render(int x, int y, int width, String name, int color) {
        Client.getInstance().getSilentFontRenderer().drawString(name, x + 100, y - 2, 12, SilentFontRenderer.FontType.TITLE);
        RenderUtil.drawRoundedRect(x + width - 20, y + 1, 15, 8, 5, color);
        RenderUtil.drawRoundedOutline(x + width - 20, y + 1, 15, 8, 5, 2, new Color(255, 255, 255).getRGB());
    }

    public static boolean isHovered(int mouseX, int mouseY, int x, int y, int width) {
        return MouseUtils.isInside(mouseX, mouseY, x + width - 20, y + 1, 15, 8);
    }
}
