package lol.point.returnclient.util.system;

import java.awt.*;
import java.util.Random;

public class ColorUtil {

    public static final int[] TRANS_COLORS = {
            0xFF00D7FE,
            0xFFFFFFFF,
            0xFFFF95B3
    };

    public static final int[] RAINBOW_COLORS = {
            0xfffc6a8c,
            0xfffc6ad5,
            0xffda6afc,
            0xff916afc,
            0xff6a8cfc,
            0xff6ad5fc,
            0xffda6afc,
            0xfffc6a8c,
    };

    public static final int[] HEALTH_COLOURS = {
            0xFF08FF00,
            0xFFFFFF00,
            0xFFFF8000,
            0xFFFF0000,
            0xFF800000
    };

    public static int blendRainbowColours(final double progress) {
        return blendColours(RAINBOW_COLORS, progress);
    }

    public static int blendRainbowColours(final long offset) {
        return blendRainbowColours(getFadingFromSysTime(offset));
    }

    public static int blendHealthColours(final double progress) {
        return blendColours(HEALTH_COLOURS, progress);
    }

    public static double getFadingFromSysTime(final long offset) {
        return ((System.currentTimeMillis() + offset) % 2000L) / 2000.0;
    }

    public static Color generateRandomTonedColor(int baseHue, int minValue, int maxValue, int alpha) {
        Random random = new Random();

        // Generate random saturation and value within the given range
        float saturation = random.nextFloat();
        float value = minValue + random.nextInt(maxValue - minValue + 1) / 255.0f;

        return Color.getHSBColor(baseHue / 360.0f, saturation, value).darker();
    }

    public static int blendColours(int[] colours, double progress) {
        final int size = colours.length;
        if (progress == 1.f) return colours[0];
        else if (progress == 0.f) return colours[size - 1];
        final double mulProgress = Math.max(0, (1 - progress) * (size - 1));
        final int index = (int) mulProgress;
        return fadeBetween(colours[index], colours[index + 1], mulProgress - index);
    }

    public static int fadeBetween(int startColour, int endColour, double progress) {
        if (progress > 1) progress = 1 - progress % 1;
        return fadeTo(startColour, endColour, progress);
    }

    public static int fadeBetween(int[] colors, long offset) {
        return blendColours(colors, ((System.currentTimeMillis() + offset) % 2000L) / 1000.0);
    }

    public static int fadeBetween(int startColour, int endColour, long offset) {
        return fadeBetween(startColour, endColour, ((System.currentTimeMillis() + offset) % 2000L) / 1000.0);
    }

    public static Color fadeBetween(Color startColour, Color endColour, long offset) {
        double ratio = ((System.currentTimeMillis() + offset) % 2000L) / 1000.0;
        return fadeBetween(startColour, endColour, ratio);
    }

    public static Color fadeBetween(Color startColour, Color endColour, double ratio) {
        ratio = Math.max(0, Math.min(1, ratio));

        int red = (int) (startColour.getRed() * (1 - ratio) + endColour.getRed() * ratio);
        int green = (int) (startColour.getGreen() * (1 - ratio) + endColour.getGreen() * ratio);
        int blue = (int) (startColour.getBlue() * (1 - ratio) + endColour.getBlue() * ratio);

        return new Color(red, green, blue);
    }

    public static int fadeBetween(int startColour, int endColour) {
        return fadeBetween(startColour, endColour, 0L);
    }

    public static int fadeTo(int startColour, int endColour, double progress) {
        double invert = 1.0 - progress;
        int r = (int) ((startColour >> 16 & 0xFF) * invert +
                (endColour >> 16 & 0xFF) * progress);
        int g = (int) ((startColour >> 8 & 0xFF) * invert +
                (endColour >> 8 & 0xFF) * progress);
        int b = (int) ((startColour & 0xFF) * invert +
                (endColour & 0xFF) * progress);
        int a = (int) ((startColour >> 24 & 0xFF) * invert +
                (endColour >> 24 & 0xFF) * progress);
        return ((a & 0xFF) << 24) |
                ((r & 0xFF) << 16) |
                ((g & 0xFF) << 8) |
                (b & 0xFF);
    }

    public static int getRainbow(int speed, int offset) {
        float hue = (System.currentTimeMillis() + offset) % speed;
        hue /= speed;
        return Color.getHSBColor(hue, 0.85f, 1f).getRGB();
    }

    public static int getRainbowColor(float counter) {
        return ColorUtil.getRainbow(2500, (int) (counter * 150L));
    }

    public static int fade(final Color first, final Color second, final double index, final double wave) {
        return fade(first, second, (System.currentTimeMillis() % 2000L) / 2000.0 + index * wave);
    }

    public static int fade(final Color first, final Color second, double index) {
        if (index > 1) {
            index = (int) index % 2 == 0 ? index % 1 : 1 - index % 1;
        }

        return new Color(
                (int) (first.getRed() * (1 - index) + second.getRed() * index),
                (int) (first.getGreen() * (1 - index) + second.getGreen() * index),
                (int) (first.getBlue() * (1 - index) + second.getBlue() * index),
                (int) (first.getAlpha() * (1 - index) + second.getAlpha() * index)
        ).getRGB();
    }

}
