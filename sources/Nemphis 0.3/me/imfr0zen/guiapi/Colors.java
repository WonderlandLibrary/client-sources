/*
 * Decompiled with CFR 0_118.
 */
package me.imfr0zen.guiapi;

import java.nio.ByteOrder;

public final class Colors {
    public static final int OUTLINE_COLOR = -3527576;
    public static final int BG_COLOR = -870375649;
    public static final int FONT_COLOR = -1;
    public static int buttonColor;
    public static int buttonColorLight;
    public static int buttonColorDark;
    private static final int redOffset;
    private static final int greenOffset;
    private static final int blueOffset;
    private static final int alphaOffset;

    static {
        boolean big = ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN;
        redOffset = big ? 8 : 16;
        greenOffset = big ? 16 : 8;
        blueOffset = big ? 24 : 0;
        alphaOffset = big ? 0 : 24;
    }

    public static void setButtonColor(int r, int g, int b, int a) {
        buttonColor = Colors.getARGB(r, g, b, a);
        buttonColorDark = Colors.getARGB(r <= 25 ? r : r - 25, g <= 25 ? g : g - 25, b <= 25 ? b : b - 25, a);
        buttonColorLight = Colors.getARGB(r >= 230 ? 255 : r + 25, g >= 230 ? 255 : g + 25, b >= 230 ? 255 : b + 25, a);
    }

    public static int[] getARGB(int color) {
        int a = color >> 24 & 255;
        int r = color >> 16 & 255;
        int g = color >> 8 & 255;
        int b = color >> 0 & 255;
        return new int[]{a, r, g, b};
    }

    public static int getARGB(int r, int g, int b, int a) {
        r = (r & 255) << redOffset;
        g = (g & 255) << greenOffset;
        b = (b & 255) << blueOffset;
        a = (a & 255) << alphaOffset;
        return r | g | b | a;
    }
}

