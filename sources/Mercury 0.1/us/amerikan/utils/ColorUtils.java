/*
 * Decompiled with CFR 0.145.
 */
package us.amerikan.utils;

import java.awt.Color;

public class ColorUtils {
    public static Color rainbowEffekt(long offset, float fade) {
        float hue = (float)(System.nanoTime() + offset) / 1.0E10f % 1.0f;
        long color = Long.parseLong(Integer.toHexString(Color.HSBtoRGB(hue, 1.0f, 1.0f)), 16);
        Color c2 = new Color((int)color);
        return new Color((float)c2.getRed() / 255.0f * fade, (float)c2.getGreen() / 255.0f * fade, (float)c2.getBlue() / 255.0f * fade, (float)c2.getAlpha() / 255.0f);
    }
}

