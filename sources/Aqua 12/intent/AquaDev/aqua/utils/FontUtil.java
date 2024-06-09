// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.utils;

import net.minecraft.util.StringUtils;
import intent.AquaDev.aqua.Aqua;

public class FontUtil
{
    private static UnicodeFontRenderer fontRenderer;
    
    public static void setupFontUtils() {
        FontUtil.fontRenderer = Aqua.INSTANCE.comfortaa4;
    }
    
    public static int getStringWidth(final String text) {
        return FontUtil.fontRenderer.getStringWidth(StringUtils.stripControlCodes(text));
    }
    
    public static int getFontHeight() {
        FontUtil.fontRenderer.getClass();
        return 9;
    }
    
    public static void drawString(final String text, final double x, final double y, final int color) {
        FontUtil.fontRenderer.drawString(text, (float)(int)x, (float)(int)y, color);
    }
    
    public static void drawStringWithShadow(final String text, final double x, final double y, final int color) {
        FontUtil.fontRenderer.drawString(text, (float)(int)x, (float)(int)y, color);
    }
    
    public static void drawCenteredString(final String text, final double x, final double y, final int color) {
        drawString(text, x - FontUtil.fontRenderer.getStringWidth(text) / 2, y, color);
    }
    
    public static void drawCenteredStringWithShadow(final String text, final double x, final double y, final int color) {
        drawStringWithShadow(text, x - FontUtil.fontRenderer.getStringWidth(text) / 2, y, color);
    }
    
    public static void drawTotalCenteredString(final String text, final double x, final double y, final int color) {
        final double x2 = x - FontUtil.fontRenderer.getStringWidth(text) / 2;
        FontUtil.fontRenderer.getClass();
        drawString(text, x2, y - 9 / 2, color);
    }
    
    public static void drawTotalCenteredStringWithShadow(final String text, final double x, final double y, final int color) {
        final double x2 = x - FontUtil.fontRenderer.getStringWidth(text) / 2;
        FontUtil.fontRenderer.getClass();
        drawStringWithShadow(text, x2, y - 9.0f / 2.0f, color);
    }
}
