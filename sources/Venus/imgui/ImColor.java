/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package imgui;

import imgui.ImVec4;
import java.awt.Color;

public final class ImColor {
    private ImColor() {
    }

    public static int rgba(int n, int n2, int n3, int n4) {
        return ImColor.intToColor(n, n2, n3, n4);
    }

    public static int rgb(int n, int n2, int n3) {
        return ImColor.intToColor(n, n2, n3);
    }

    public static int rgba(float f, float f2, float f3, float f4) {
        return ImColor.floatToColor(f, f2, f3, f4);
    }

    public static int rgb(float f, float f2, float f3) {
        return ImColor.floatToColor(f, f2, f3);
    }

    public static int rgba(String string) {
        return ImColor.rgbaToColor(string);
    }

    public static int rgb(String string) {
        return ImColor.rgbToColor(string);
    }

    public static int rgba(ImVec4 imVec4) {
        return ImColor.rgba(imVec4.x, imVec4.y, imVec4.z, imVec4.w);
    }

    public static int rgb(ImVec4 imVec4) {
        return ImColor.rgb(imVec4.x, imVec4.y, imVec4.z);
    }

    public static int rgba(Color color) {
        return ImColor.rgba(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    public static int rgb(Color color) {
        return ImColor.rgb(color.getRed(), color.getGreen(), color.getBlue());
    }

    public static int hsla(float f, float f2, float f3, float f4) {
        return ImColor.hslToColor(f, f2, f3, f4);
    }

    public static int hsl(float f, float f2, float f3) {
        return ImColor.hslToColor(f, f2, f3);
    }

    public static int hsla(int n, int n2, int n3, int n4) {
        return ImColor.hslToColor(n, n2, n3, (float)n4);
    }

    public static int hsl(int n, int n2, int n3) {
        return ImColor.hslToColor(n, n2, n3);
    }

    @Deprecated
    public static int intToColor(int n, int n2, int n3, int n4) {
        return n4 << 24 | n3 << 16 | n2 << 8 | n;
    }

    @Deprecated
    public static int intToColor(int n, int n2, int n3) {
        return ImColor.intToColor(n, n2, n3, 255);
    }

    @Deprecated
    public static int floatToColor(float f, float f2, float f3, float f4) {
        return ImColor.intToColor((int)(f * 255.0f), (int)(f2 * 255.0f), (int)(f3 * 255.0f), (int)(f4 * 255.0f));
    }

    @Deprecated
    public static int floatToColor(float f, float f2, float f3) {
        return ImColor.floatToColor(f, f2, f3, 1.0f);
    }

    @Deprecated
    public static int rgbToColor(String string) {
        return ImColor.intToColor(Integer.parseInt(string.substring(1, 3), 16), Integer.parseInt(string.substring(3, 5), 16), Integer.parseInt(string.substring(5, 7), 16));
    }

    @Deprecated
    public static int rgbaToColor(String string) {
        return ImColor.intToColor(Integer.parseInt(string.substring(1, 3), 16), Integer.parseInt(string.substring(3, 5), 16), Integer.parseInt(string.substring(5, 7), 16), Integer.parseInt(string.substring(7, 9), 16));
    }

    @Deprecated
    public static int hslToColor(int n, int n2, int n3) {
        return ImColor.hslToColor(n, n2, n3, 1.0f);
    }

    @Deprecated
    public static int hslToColor(int n, int n2, int n3, float f) {
        return ImColor.hslToColor((float)n / 360.0f, (float)n2 / 100.0f, (float)n3 / 100.0f, f);
    }

    @Deprecated
    public static int hslToColor(float f, float f2, float f3) {
        return ImColor.hslToColor(f, f2, f3, 1.0f);
    }

    @Deprecated
    public static int hslToColor(float f, float f2, float f3, float f4) {
        float f5;
        float f6;
        float f7;
        if (f2 == 0.0f) {
            f7 = f3;
            f6 = f3;
            f5 = f3;
        } else {
            float f8 = (double)f3 < 0.5 ? f3 * (1.0f + f2) : f3 + f2 - f3 * f2;
            float f9 = 2.0f * f3 - f8;
            f7 = ImColor.hue2rgb(f9, f8, f + 0.33333334f);
            f6 = ImColor.hue2rgb(f9, f8, f);
            f5 = ImColor.hue2rgb(f9, f8, f - 0.33333334f);
        }
        return ImColor.floatToColor(f7, f6, f5, f4);
    }

    private static float hue2rgb(float f, float f2, float f3) {
        float f4 = f3;
        if (f4 < 0.0f) {
            f4 += 1.0f;
        }
        if (f4 > 1.0f) {
            f4 -= 1.0f;
        }
        if (6.0f * f4 < 1.0f) {
            return f + (f2 - f) * 6.0f * f4;
        }
        if (2.0f * f4 < 1.0f) {
            return f2;
        }
        if (3.0f * f4 < 2.0f) {
            return f + (f2 - f) * 6.0f * (0.6666667f - f4);
        }
        return f;
    }
}

