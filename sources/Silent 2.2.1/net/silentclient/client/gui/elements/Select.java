package net.silentclient.client.gui.elements;

import net.silentclient.client.Client;
import net.silentclient.client.gui.lite.clickgui.utils.MouseUtils;
import net.silentclient.client.gui.font.SilentFontRenderer;
import net.silentclient.client.utils.ColorUtils;

import java.awt.*;

public class Select {
    public static void render(int mouseX, int mouseY, int x, int y, int width, String name, String selected) {
        int left = Select.getLeft(x, width);
        boolean firstHovered = Select.prevHovered(mouseX, mouseY, x, y, width);
        boolean twoHovered = Select.nextHovered(mouseX, mouseY, x, y, width);
        Client.getInstance().getSilentFontRenderer().drawString(name + ":", x + 100, y - 2, 12, SilentFontRenderer.FontType.TITLE);
        ColorUtils.setColor(new Color(255, 255, 255, firstHovered ? 127 : 255).getRGB());
        Client.getInstance().getSilentFontRenderer().drawString("<", left, y - 2, 12, SilentFontRenderer.FontType.TITLE);
        ColorUtils.setColor(new Color(255, 255, 255).getRGB());
        Client.getInstance().getSilentFontRenderer().drawString(selected, left + 5 + ((Client.getInstance().getSilentFontRenderer().getStringWidth("<", 12, SilentFontRenderer.FontType.TITLE) + 5 + 100 + 5 + Client.getInstance().getSilentFontRenderer().getStringWidth(">", 12, SilentFontRenderer.FontType.TITLE)) / 2 - Client.getInstance().getSilentFontRenderer().getStringWidth(selected, 12, SilentFontRenderer.FontType.TITLE) / 2), y - 2, 12, SilentFontRenderer.FontType.TITLE);
        ColorUtils.setColor(new Color(255, 255, 255, twoHovered ? 127 : 255).getRGB());
        Client.getInstance().getSilentFontRenderer().drawString(">", left + Client.getInstance().getSilentFontRenderer().getStringWidth("<", 12, SilentFontRenderer.FontType.TITLE) + 5 + 100 + 5, y - 2, 12, SilentFontRenderer.FontType.TITLE);
        ColorUtils.setColor(new Color(255, 255, 255).getRGB());
    }

    public static int getLeft(int x, int width) {
        return (int) (x + width - (Client.getInstance().getSilentFontRenderer().getStringWidth("<", 12, SilentFontRenderer.FontType.TITLE) + 5 + 100 + 5 + Client.getInstance().getSilentFontRenderer().getStringWidth(">", 12, SilentFontRenderer.FontType.TITLE) + 5));
    }

    public static boolean prevHovered(int mouseX, int mouseY, int x, int y, int width) {
        return MouseUtils.isInside(mouseX, mouseY, Select.getLeft(x, width), y - 2, Client.getInstance().getSilentFontRenderer().getStringWidth("<", 12, SilentFontRenderer.FontType.TITLE), 12);
    }

    public static boolean nextHovered(int mouseX, int mouseY, int x, int y, int width) {
        return MouseUtils.isInside(mouseX, mouseY, Select.getLeft(x, width) + Client.getInstance().getSilentFontRenderer().getStringWidth("<", 12, SilentFontRenderer.FontType.TITLE) + 5 + 100 + 5, y - 2, Client.getInstance().getSilentFontRenderer().getStringWidth(">", 12, SilentFontRenderer.FontType.TITLE), 12);
    }
}
