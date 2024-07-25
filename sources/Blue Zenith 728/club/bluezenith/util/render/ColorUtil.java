package club.bluezenith.util.render;

import net.minecraft.util.EnumChatFormatting;

import java.awt.*;
import java.util.regex.Matcher;

import static java.awt.Color.HSBtoRGB;
import static java.lang.Math.*;

public class ColorUtil {
    public static final Color grayColor = new Color(204, 204, 204);
    private static final Color MAIN_COLOR = new Color(11, 120, 252);
    private static final Color BACKGROUND_COLOR = new Color(0, 0, 69);

    public static int getColorForHealth(float maxHealth, float currentHealth) {
        return getColorForHealth(currentHealth, maxHealth, 0.7F, 1.0F);
    }

    public static Color darken(Color origin, int points) {
        final int[] rgba = getInt(origin.getRGB());
        return new Color(
                Math.max(0, rgba[0] - points),
                Math.max(0, rgba[1] - points),
                Math.max(0, rgba[2] - points)
        );
    }

    public static int darken(int origin, int points) {
        final int[] rgba = getInt(origin);
        return get(
                Math.max(0, rgba[0] - points),
                Math.max(0, rgba[1] - points),
                Math.max(0, rgba[2] - points),
                rgba[3]
        );
    }

    public static int getColorForHealth(float maxHealth, float currentHealth, float saturation, float brightness) {
        float diff = min(currentHealth, maxHealth) / maxHealth;
        return HSBtoRGB(diff / 3F, saturation, brightness);
    }

    public static Color setOpacity(Color color, int opacity255) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), opacity255);
    }

    public static int setOpacity(int color, int opacity255) {
        final int[] colors = getInt(color);
        colors[3] = opacity255;
        return get(colors[0], colors[1], colors[2], colors[3]);
    }

    public static Color rainbow(float index, float multi) {
        return rainbow(index, multi, 0.6F, 1F);
    }

    public static Color rainbow(float index, float multi, float saturation, float brightness) {
        double delay = (Math.abs(System.currentTimeMillis() / 20L) / 100.0 + 6.0F * ((index * multi) + 2.55) / 60) % 1;
        final double n3 = 1.0 - delay;
        return Color.getHSBColor((float) n3, saturation, brightness);
    }

    public static Color astolfoColor(int counter, float bright, float st) {
        double v1 = Math.ceil(System.currentTimeMillis() + counter * 109L) / 5;
        return Color.getHSBColor((double) ((float) ((v1 %= 360.0) / 360.0)) < 0.5 ? -((float) (v1 / 360.0)) : (float) (v1 / 360.0), st, bright);
    }

    public static int pulseBlue(int secs) {
        Color color = getBackgroundColor();
        Color color2 = getMainColor();
        return pulse(secs, color.getRed(), color.getGreen(), color.getBlue(), color2.getRed(), color2.getGreen(), color2.getBlue());
    }

    public static int pulse(int secs, int red1, int green1, int blue1, int red2, int green2, int blue2) {
        double delay = Math.abs(System.currentTimeMillis() / 20L) / 100.0 + 6.0F * ((secs * 2) + 2.55) / 60;
        if (delay > 1.0) {
            final double n2 = delay % 1.0;
            delay = (((int) delay % 2 == 0) ? n2 : (1.0 - n2));
        }
        final double n3 = 1.0 - delay;
        return get((int) (red1 * n3 + red2 * delay), (int) (green1 * n3 + green2 * delay), (int) (blue1 * n3 + blue2 * delay), (int) (255 * n3 + 255 * delay));
    }

    public static String stripFormatting(String text) {
        String lol = EnumChatFormatting.getTextWithoutFormattingCodes(text);
        return lol == null ? "" : lol;
    }

    public static String getFirstColor(String text) {
        Matcher m = EnumChatFormatting.getFormattingCodePattern().matcher(text);
        return m.find() ? m.group(0) : "§r";
    }

    public static Color getMainColor() {
        return MAIN_COLOR;
    }

    public static Color getBackgroundColor() {
        return BACKGROUND_COLOR;
    }

    public static String toString(Color color){
        return toString(color.getRGB());
    }

    public static String toString(int r, int g, int b, int a){
        return toString(ColorUtil.get(r, g, b, a));
    }

    public static String toString(int color) {
        return "${" + color + "}";
    }

    public static int get(int r, int g, int b, int a) {
        return ((a & 0xFF) << 24) | ((r & 0xFF) << 16) | ((g & 0xFF) << 8) | ((b & 0xFF));
    }

    public static float[] get(int rgba) {
        float red = (rgba >> 16 & 255) / 255F;
        float green = (rgba >> 8 & 255) / 255F;
        float blue = (rgba & 255) / 255F;
        float alpha = (rgba >> 24 & 255) / 255F;

        return new float[]{red, green, blue, alpha};
    }

    public static int[] getInt(int rgba) {
        int red = (rgba >> 16 & 255);
        int green = (rgba >> 8 & 255);
        int blue = (rgba & 255);
        int alpha = (rgba >> 24 & 255);

        return new int[]{red, green, blue, alpha};
    }

    public static Color transitionBetween(float progress, float[] from, float[] to) {
        float currentRed = progress * (to[0] - from[0]) + from[0],
                currentGreen = progress * (to[1] - from[1]) + from[1],
                currentBlue = progress * (to[2] - from[2]) + from[2];

        try {
            return new Color(
                    (int) min(255, abs(currentRed) * 255F),
                    (int) min(255, abs(currentGreen) * 255F),
                    (int) min(255, abs(currentBlue) * 255F)
            );
        } catch (Exception exception) {
            //System.err.printf("ERROR: Red: %s, Green: %s, Blue: %s\n", currentRed, currentGreen, currentBlue);
            return Color.white;
        }
    }
}
