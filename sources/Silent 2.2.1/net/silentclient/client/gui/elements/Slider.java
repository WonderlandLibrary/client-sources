package net.silentclient.client.gui.elements;

import net.silentclient.client.Client;
import net.silentclient.client.gui.lite.clickgui.utils.MouseUtils;
import net.silentclient.client.gui.lite.clickgui.utils.RenderUtils;
import net.silentclient.client.gui.font.SilentFontRenderer;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.text.DecimalFormat;

public class Slider {
    public static void render(int x, int y, int width, String name, double max, double value) {
        Client.getInstance().getSilentFontRenderer().drawString(name, x + 100, y + 1 - 2, 12, SilentFontRenderer.FontType.TITLE);
        int left = Slider.getLeft(x, width);

        RenderUtils.drawRect(left, y, 90, 9, Color.black.getRGB());
        RenderUtils.drawRect(left, y, 90F * (float) (value / max), 9, -1);

        int textLeft = left + 100 + (Client.getInstance().getSilentFontRenderer().getStringWidth("100.00", 12, SilentFontRenderer.FontType.TITLE) - Client.getInstance().getSilentFontRenderer().getStringWidth(new DecimalFormat("0.00").format(value), 12, SilentFontRenderer.FontType.TITLE));
        Client.getInstance().getSilentFontRenderer().drawString(new DecimalFormat("0.00").format(value), textLeft, y - 2, 12, SilentFontRenderer.FontType.TITLE);
    }

    public static int getLeft(int x, int width) {
        return x + width - 100 - Client.getInstance().getSilentFontRenderer().getStringWidth("100.00", 12, SilentFontRenderer.FontType.TITLE) - 5;
    }

    public static boolean isDrag(int mouseX, int mouseY, int x, int y, int width) {
        return MouseUtils.isInside(mouseX, mouseY, Slider.getLeft(x, width), y, 90, 9) && Mouse.isButtonDown(0);
    }
}
