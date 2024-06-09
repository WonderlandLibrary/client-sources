/*
 * Decompiled with CFR 0_118.
 */
package me.imfr0zen.guiapi;

import me.imfr0zen.guiapi.ClickGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

public final class RenderUtil {
    private RenderUtil() {
    }

    public static boolean isHovered(int x, int y, int width, int height, int mouseX, int mouseY) {
        if (mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY < y + height) {
            return true;
        }
        return false;
    }

    public static void drawRect(int left, int top, int right, int bottom, int color) {
        Gui.drawRect(left, top, right, bottom, color);
    }

    public static void drawHorizontalLine(int startX, int endX, int y, int color) {
        if (endX < startX) {
            int i = startX;
            startX = endX;
            endX = i;
        }
        RenderUtil.drawRect(startX, y, endX + 1, y + 1, color);
    }

    public static void drawVerticalLine(int x, int startY, int endY, int color) {
        if (endY < startY) {
            int i = startY;
            startY = endY;
            endY = i;
        }
        RenderUtil.drawRect(x, startY + 1, x + 1, endY, color);
    }

    public static int getWidth(String text) {
        return ClickGui.FONTRENDERER.getStringWidth(text);
    }

    public static void drawString(String text, int x, int y, int color) {
        ClickGui.FONTRENDERER.drawString(text, x, y, color);
    }

    public static void drawCenteredString(String text, int x, int y, int color) {
        ClickGui.FONTRENDERER.drawString(text, x - RenderUtil.getWidth(text) / 2, y, color);
    }
}

