// 
// Decompiled by the rizzer xd
// 

package dev.lvstrng.argon.utils;

import net.minecraft.util.math.MathHelper;

import java.awt.*;

public final class ColorUtil {
    public static Color method520(final int increment, final int alpha) {
        final Color hsbColor = Color.getHSBColor((System.currentTimeMillis() * 3L + increment * 175) % 7200L / 7200.0f, 0.6f, 1.0f);
        return new Color(hsbColor.getRed(), hsbColor.getGreen(), hsbColor.getBlue(), alpha);
    }

    public static Color method521(final Color color, final int n, final int n2) {
        final float[] hsbvals = new float[3];
        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsbvals);
        hsbvals[2] = 0.25f + 0.75f * Math.abs((System.currentTimeMillis() % 2000L / 1000.0f + n / (float) n2 * 2.0f) % 2.0f - 1.0f) % 2.0f;
        final int hsBtoRGB = Color.HSBtoRGB(hsbvals[0], hsbvals[1], hsbvals[2]);
        return new Color(hsBtoRGB >> 16 & 0xFF, hsBtoRGB >> 8 & 0xFF, hsBtoRGB & 0xFF, color.getAlpha());
    }

    public static Color method522(final float speed, final Color toColor, final Color fromColor) {
        return new Color(MathHelper.lerp(speed, fromColor.getRed(), toColor.getRed()), MathHelper.lerp(speed, fromColor.getGreen(), toColor.getGreen()), MathHelper.lerp(speed, fromColor.getBlue(), toColor.getBlue()));
    }

    public static Color method523(final float speed, final int toAlpha, final Color fromColor) {
        return new Color(fromColor.getRed(), fromColor.getGreen(), fromColor.getBlue(), MathHelper.lerp(speed, fromColor.getAlpha(), toAlpha));
    }
}
