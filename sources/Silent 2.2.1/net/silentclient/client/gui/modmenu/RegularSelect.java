package net.silentclient.client.gui.modmenu;

import net.silentclient.client.Client;
import net.silentclient.client.gui.font.SilentFontRenderer;
import net.silentclient.client.gui.lite.clickgui.utils.MouseUtils;
import net.silentclient.client.utils.ColorUtils;

import java.awt.*;

public class RegularSelect {
    public static void render(int mouseX, int mouseY, float x, float y, int width, String name, String selected) {
        boolean firstHovered = RegularSelect.prevHovered(mouseX, mouseY, x, y);
        boolean twoHovered = RegularSelect.nextHovered(mouseX, mouseY, x, y, width);
        Client.getInstance().getSilentFontRenderer().drawString(name, x, y, 12, SilentFontRenderer.FontType.TITLE);
        ColorUtils.setColor(new Color(255, 255, 255, firstHovered ? 127 : 255).getRGB());
        Client.getInstance().getSilentFontRenderer().drawString("<", x, y + 15, 12, SilentFontRenderer.FontType.TITLE);
        ColorUtils.setColor(new Color(255, 255, 255).getRGB());
        Client.getInstance().getSilentFontRenderer().drawCenteredString(selected, (int) (x + width / 2), (int) (y + 15), 12, SilentFontRenderer.FontType.TITLE);
        ColorUtils.setColor(new Color(255, 255, 255, twoHovered ? 127 : 255).getRGB());
        Client.getInstance().getSilentFontRenderer().drawString(">", x + width - Client.getInstance().getSilentFontRenderer().getStringWidth(">", 12, SilentFontRenderer.FontType.TITLE), y + 15, 12, SilentFontRenderer.FontType.TITLE);
        ColorUtils.setColor(new Color(255, 255, 255).getRGB());
    }

    public static boolean prevHovered(int mouseX, int mouseY, float x, float y) {
        return MouseUtils.isInside(mouseX, mouseY, x, y + 12, Client.getInstance().getSilentFontRenderer().getStringWidth("<", 12, SilentFontRenderer.FontType.TITLE), 12);
    }

    public static boolean nextHovered(int mouseX, int mouseY, float x, float y, int width) {
        return MouseUtils.isInside(mouseX, mouseY, x + width - Client.getInstance().getSilentFontRenderer().getStringWidth(">", 12, SilentFontRenderer.FontType.TITLE), y + 15, Client.getInstance().getSilentFontRenderer().getStringWidth(">", 12, SilentFontRenderer.FontType.TITLE), 12);
    }
}
