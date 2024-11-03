package net.silentclient.client.gui.modmenu;

import net.silentclient.client.Client;
import net.silentclient.client.gui.font.SilentFontRenderer;
import net.silentclient.client.gui.lite.clickgui.utils.MouseUtils;
import net.silentclient.client.gui.util.RenderUtil;

import java.awt.*;

public class RegularColorPicker {
    public static void render(float x, float y, int width, String name, int color) {
        Client.getInstance().getSilentFontRenderer().drawString(name, x, y - 1, 12, SilentFontRenderer.FontType.TITLE);
        RenderUtil.drawRoundedRect(x + width - 15, y + 2, 15, 8, 5, color);
        RenderUtil.drawRoundedOutline(x + width - 15, y + 2, 15, 8, 5, 2, new Color(255, 255, 255).getRGB());
    }

    public static boolean isHovered(int mouseX, int mouseY, int x, int y, int width) {
        return MouseUtils.isInside(mouseX, mouseY, x + width - 15, y + 2, 15, 8);
    }
}
