package me.kaimson.melonclient.gui.utils;

public class FontUtils extends avp
{
    protected static final ave mc;
    
    public static int drawString(final Object text, final int x, final int y) {
        return drawString(text, x, y, 16777215, false);
    }
    
    public static int drawString(final Object text, final int x, final int y, final int color) {
        return drawString(text, x, y, color, false);
    }
    
    public static int drawString(final Object text, final int x, final int y, final boolean shadow) {
        return drawString(text, x, y, 16777215, shadow);
    }
    
    public static int drawString(final Object text, final int x, final int y, final int color, final boolean shadow) {
        return FontUtils.mc.k.a(String.valueOf(text), (float)x, (float)y, color, shadow);
    }
    
    public static int drawString(final Object text, final float x, final float y) {
        return drawString(text, x, y, 16777215, false);
    }
    
    public static int drawString(final Object text, final float x, final float y, final int color) {
        return drawString(text, x, y, color, false);
    }
    
    public static int drawString(final Object text, final float x, final float y, final boolean shadow) {
        return drawString(text, x, y, 16777215, shadow);
    }
    
    public static int drawString(final Object text, final float x, final float y, final int color, final boolean shadow) {
        return FontUtils.mc.k.a(String.valueOf(text), x, y, color, shadow);
    }
    
    public static int drawCenteredString(final Object text, final int x, final int y, final int color) {
        return drawCenteredString(text, x, y, color, false);
    }
    
    public static int drawCenteredString(final Object text, final float x, final float y, final int color) {
        return FontUtils.mc.k.a(String.valueOf(text), x - FontUtils.mc.k.a(String.valueOf(text)) / 2.0f, y, color, false);
    }
    
    public static int drawCenteredString(final Object text, final int x, final int y, final int color, final boolean shadow) {
        return FontUtils.mc.k.a(String.valueOf(text), x - FontUtils.mc.k.a(String.valueOf(text)) / 2.0f, (float)y, color, shadow);
    }
    
    static {
        mc = ave.A();
    }
}
