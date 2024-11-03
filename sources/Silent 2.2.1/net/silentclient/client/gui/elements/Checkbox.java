package net.silentclient.client.gui.elements;

import net.silentclient.client.Client;
import net.silentclient.client.gui.lite.clickgui.utils.MouseUtils;
import net.silentclient.client.gui.font.SilentFontRenderer;
import net.silentclient.client.gui.theme.checkbox.DefaultCheckboxTheme;
import net.silentclient.client.gui.theme.checkbox.ICheckboxTheme;
import net.silentclient.client.gui.util.RenderUtil;

import java.awt.*;

public class Checkbox {
    public static void render(int mouseX, int mouseY, float x, float y, String name, boolean selected) {
        render(mouseX, mouseY, x, y, name, selected, new DefaultCheckboxTheme());
    }

    public static void render(int mouseX, int mouseY, float x, float y, String name, boolean selected, ICheckboxTheme theme) {
        boolean hovered = Checkbox.isHovered(mouseX, mouseY, x, y);
        Color checkColor = selected ? theme.getSelectedColor() : theme.getColor();
        RenderUtil.drawRoundedOutline(x, y, 9, 9, 9, 2, new Color(checkColor.getRed(), checkColor.getGreen(), checkColor.getBlue(), hovered ? 127 : 255).getRGB());
        if(selected) {
            RenderUtil.drawRoundedRect(x + (9 / 2 - 5 / 2), y + (9 / 2 - 5 / 2),  5, 5, 5, checkColor.getRGB());
        }
        Client.getInstance().getSilentFontRenderer().drawString(name, x + 12, y + ((9 / 2) - (12 / 2)), 12, SilentFontRenderer.FontType.TITLE);
    }

    public static boolean isHovered(int mouseX, int mouseY, float x, float y) {
        return MouseUtils.isInside(mouseX, mouseY, x, y, 9, 9);
    }
}
