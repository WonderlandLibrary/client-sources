/*
 * Decompiled with CFR 0.145.
 */
package de.Hero.clickgui.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.StringUtils;

public class FontUtil {
    private static FontRenderer fontRenderer;

    public static void setupFontUtils() {
        fontRenderer = Minecraft.getMinecraft().fontRendererObj;
    }

    public static int getStringWidth(String text) {
        return fontRenderer.getStringWidth(StringUtils.stripControlCodes(text));
    }

    public static int getFontHeight() {
        return FontUtil.fontRenderer.FONT_HEIGHT;
    }

    public static void drawString(String text, double x2, double y2, int color) {
        fontRenderer.drawString(text, x2, y2, color);
    }

    public static void drawStringWithShadow(String text, double x2, double y2, int color) {
        fontRenderer.func_175063_a(text, (float)x2, (float)y2, color);
    }

    public static void drawCenteredString(String text, double x2, double y2, int color) {
        FontUtil.drawString(text, x2 - (double)(fontRenderer.getStringWidth(text) / 2), y2, color);
    }

    public static void drawCenteredStringWithShadow(String text, double x2, double y2, int color) {
        FontUtil.drawStringWithShadow(text, x2 - (double)(fontRenderer.getStringWidth(text) / 2), y2, color);
    }

    public static void drawTotalCenteredString(String text, double x2, double y2, int color) {
        FontUtil.drawString(text, x2 - (double)(fontRenderer.getStringWidth(text) / 2), y2 - (double)(FontUtil.fontRenderer.FONT_HEIGHT / 2), color);
    }

    public static void drawTotalCenteredStringWithShadow(String text, double x2, double y2, int color) {
        FontUtil.drawStringWithShadow(text, x2 - (double)(fontRenderer.getStringWidth(text) / 2), y2 - (double)((float)FontUtil.fontRenderer.FONT_HEIGHT / 2.0f), color);
    }
}

