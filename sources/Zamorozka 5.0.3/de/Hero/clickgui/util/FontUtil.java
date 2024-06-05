package de.Hero.clickgui.util;

import net.minecraft.util.StringUtils;
import zamorozka.ui.MinecraftFontRenderer;

import java.awt.*;

/**
 * Made by HeroCode
 * it's free to use
 * but you have to credit me
 *
 * @author HeroCode
 */
public class FontUtil {
    private static MinecraftFontRenderer fontRenderer;


    public static void setupFontUtils() {
        fontRenderer = new MinecraftFontRenderer(new Font("Tahoma", Font.PLAIN, 15), true, true);
    }

    public static int getStringWidth(String text) {
        return fontRenderer.getStringWidth(StringUtils.stripControlCodes(text));
    }

    public static int getFontHeight() {
        return fontRenderer.getHeight();
    }

    public static void drawString(String text, double x, double y, int color) {
        fontRenderer.drawString(text, (int) x, (int) y, color);
    }

    public static void drawStringWithShadow(String text, double x, double y, int color) {
        fontRenderer.drawStringWithShadow(text, (float) x, (float) y, color);
    }

    public static void drawCenteredString(String text, double x, double y, int color) {
        drawString(text, x - fontRenderer.getStringWidth(text) / 2, y, color);
    }

    public static void drawCenteredStringWithShadow(String text, double x, double y, int color) {
        drawStringWithShadow(text, x - fontRenderer.getStringWidth(text) / 2, y, color);
    }

    public static void drawTotalCenteredString(String text, double x, double y, int color) {
        drawString(text, x - fontRenderer.getStringWidth(text) / 2, y - fontRenderer.getHeight() / 2, color);
    }

    public static void drawTotalCenteredStringWithShadow(String text, double x, double y, int color) {
        drawStringWithShadow(text, x - fontRenderer.getStringWidth(text) / 2D, y - fontRenderer.getHeight() / 2D, color);
    }
}
