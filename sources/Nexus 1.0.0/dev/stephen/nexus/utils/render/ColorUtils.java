package dev.stephen.nexus.utils.render;

import dev.stephen.nexus.utils.Utils;
import dev.stephen.nexus.utils.timer.TickMode;

import java.awt.*;

public class ColorUtils implements Utils {
    public static Color interpolateBetween(Color color, Color color1, long speed, long delay) {
        speed *= 1000;
        delay *= -1;
        float percent = ((System.currentTimeMillis() + delay) % speed) / ((float) speed);
        percent = TickMode.SINE.toSmoothPercent(2 * percent);
        return interpolateColoursColor(color, color1, percent);
    }

    private static Color interpolateColoursColor(Color a, Color b, float f) {
        float rf = 1 - f;
        int red = (int) (a.getRed() * rf + b.getRed() * f);
        int green = (int) (a.getGreen() * rf + b.getGreen() * f);
        int blue = (int) (a.getBlue() * rf + b.getBlue() * f);
        int alpha = (int) (a.getAlpha() * rf + b.getAlpha() * f);
        return new Color(red, green, blue, alpha);
    }

    private static int interpolateColoursInt(int a, int b, float f) {
        return interpolateColoursColor(new Color(a), new Color(b), f).getRGB();
    }

    public static double interpolate(double oldValue, double newValue, double interpolationValue) {
        return (oldValue + (newValue - oldValue) * interpolationValue);
    }

    public static float interpolateFloat(float oldValue, float newValue, double interpolationValue) {
        return (float) interpolate(oldValue, newValue, (float) interpolationValue);
    }

    public static int interpolateInt(int oldValue, int newValue, double interpolationValue) {
        return (int) interpolate(oldValue, newValue, (float) interpolationValue);
    }

    public static Color changeOpacity(Color color, int opacity) {
        if (opacity < 1 || opacity > 255) {
            throw new IllegalArgumentException("Opacity must be between 1 and 255");
        }

        return new Color(color.getRed(), color.getGreen(), color.getBlue(), opacity);
    }

    public static String stripControlCodes(String text) {
        char[] chars = text.toCharArray();
        StringBuilder f = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (c == '§') {
                i++;
                continue;
            }
            f.append(c);
        }
        return f.toString();
    }
}
