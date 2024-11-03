package net.silentclient.client.gui.modmenu;

import net.silentclient.client.Client;
import net.silentclient.client.gui.font.SilentFontRenderer;
import net.silentclient.client.gui.lite.clickgui.utils.MouseUtils;
import net.silentclient.client.gui.util.RenderUtil;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.text.DecimalFormat;

public class RegularSlider {
    public static void render(float x, float y, int width, String name, double max, double value) {
        int valueWidth = Client.getInstance().getSilentFontRenderer().getStringWidth(new DecimalFormat("0.00").format(value), 12, SilentFontRenderer.FontType.TITLE);
        Client.getInstance().getSilentFontRenderer().drawString(name, x, y, 12, SilentFontRenderer.FontType.TITLE, 142 - valueWidth);

        RenderUtil.drawRoundedRect(x, y + 15, width, 9, 3, Color.black.getRGB());
        if(value != 0) {
            RenderUtil.drawRoundedRect(x, y + 15, width * (float) (value / max), 9, 3, -1);
        }

        float textLeft = x + width - valueWidth;
        Client.getInstance().getSilentFontRenderer().drawString(new DecimalFormat("0.00").format(value), textLeft, y, 12, SilentFontRenderer.FontType.TITLE);
    }

    public static boolean isDrag(int mouseX, int mouseY, float x, float y, int width) {
        return MouseUtils.isInside(mouseX, mouseY, x, y + 15, width, 9) && Mouse.isButtonDown(0);
    }
}
