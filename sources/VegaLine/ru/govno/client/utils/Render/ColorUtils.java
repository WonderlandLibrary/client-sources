/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.utils.Render;

import com.ibm.icu.text.NumberFormat;
import java.awt.Color;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import ru.govno.client.utils.Math.MathUtils;

public class ColorUtils {
    public static int getColor(Color color) {
        return ColorUtils.getColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    public static Color getJavaColor(int color) {
        return new Color(ColorUtils.getRedFromColor(color), ColorUtils.getGreenFromColor(color), ColorUtils.getBlueFromColor(color), ColorUtils.getAlphaFromColor(color));
    }

    public static Color getColorWithOpacity(Color color, int alpha) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
    }

    public static Color injectAlpha(Color color, int alpha) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
    }

    public static Color TwoColor(Color cl1, Color cl2, double speed) {
        double thing = speed / 4.0 % 1.0;
        float val = MathUtils.clamp((float)Math.sin(Math.PI * 6 * thing) / 2.0f + 0.5f, 0.0f, 1.0f);
        return new Color(MathUtils.lerp((float)cl1.getRed() / 255.0f, (float)cl2.getRed() / 255.0f, val), MathUtils.lerp((float)cl1.getGreen() / 255.0f, (float)cl2.getGreen() / 255.0f, val), MathUtils.lerp((float)cl1.getBlue() / 255.0f, (float)cl2.getBlue() / 255.0f, val), 1.0f);
    }

    public static int rainbow(int delay, long index) {
        double rainbowState = Math.ceil(System.currentTimeMillis() + index + (long)delay) / 3.0;
        return Color.getHSBColor((float)((rainbowState %= 360.0) / 360.0), 0.4f, 1.0f).getRGB();
    }

    public static int getFixedWhiteColor() {
        return -65537;
    }

    public static int rainbowWithDark(int delay, long index, float dark) {
        double rainbowState = Math.ceil(System.currentTimeMillis() + index + (long)delay) / 3.0;
        return Color.getHSBColor((float)((rainbowState %= 360.0) / 360.0), 0.4f, 1.0f * dark).getRGB();
    }

    public static int Flicker2(int delay, long index) {
        double rainbowState = Math.ceil(System.currentTimeMillis() + index + (long)delay) / 5.0;
        return Color.getHSBColor((float)((rainbowState %= 30.0) / 30.0), 1.0f, 1.0f).getRGB();
    }

    public static int rainbowLT(int delay, long index) {
        double rainbowState = Math.ceil(System.currentTimeMillis() + index + (long)delay) / 3.0;
        return Color.getHSBColor((float)((rainbowState %= 248.0) / 248.0), 0.5f, 0.6f).getRGB();
    }

    public static int rainbowGui(int delay, long index) {
        double rainbowState = Math.ceil(System.currentTimeMillis() + index + (long)delay) / 3.0;
        return Color.getHSBColor((float)((rainbowState %= 360.0) / 360.0), 0.8f, 1.0f).getRGB();
    }

    public static int rainbowGui2(int delay, long index) {
        double rainbowState = Math.ceil(System.currentTimeMillis() + index + (long)delay) / 3.0;
        return Color.getHSBColor((float)((rainbowState %= 360.0) / 360.0), 0.7f, 1.0f).getRGB();
    }

    public static int rainbowGui2WithDark(int delay, long index, float dark) {
        double rainbowState = Math.ceil(System.currentTimeMillis() + index + (long)delay) / 3.0;
        return Color.getHSBColor((float)((rainbowState %= 360.0) / 360.0), 0.7f, 1.0f * dark).getRGB();
    }

    public static Color fade(Color color) {
        return ColorUtils.fade(color, 2, 100);
    }

    public static int color(int n, int n2, int n3, int n4) {
        n4 = 255;
        return new Color(n, n2, n3, n4).getRGB();
    }

    public static int getRandomColor() {
        char[] letters = "012345678".toCharArray();
        Object color = "0x";
        for (int i = 0; i < 6; ++i) {
            color = (String)color + letters[new Random().nextInt(letters.length)];
        }
        return Integer.decode((String)color);
    }

    public static int getRedFromColor(int color) {
        return color >> 16 & 0xFF;
    }

    public static int getGreenFromColor(int color) {
        return color >> 8 & 0xFF;
    }

    public static int getBlueFromColor(int color) {
        return color & 0xFF;
    }

    public static int getAlphaFromColor(int color) {
        return color >> 24 & 0xFF;
    }

    public static float getGLRedFromColor(int color) {
        return (float)(color >> 16 & 0xFF) / 255.0f;
    }

    public static float getGLGreenFromColor(int color) {
        return (float)(color >> 8 & 0xFF) / 255.0f;
    }

    public static float getGLBlueFromColor(int color) {
        return (float)(color & 0xFF) / 255.0f;
    }

    public static float getGLAlphaFromColor(int color) {
        return (float)(color >> 24 & 0xFF) / 255.0f;
    }

    public static int getHue(int red, int green, int blue) {
        float max;
        float min = Math.min(Math.min(red, green), blue);
        if (min == (max = (float)Math.max(Math.max(red, green), blue))) {
            return 0;
        }
        float hue = 0.0f;
        hue = max == (float)red ? (float)(green - blue) / (max - min) : (max == (float)green ? 2.0f + (float)(blue - red) / (max - min) : 4.0f + (float)(red - green) / (max - min));
        if ((hue *= 60.0f) < 0.0f) {
            hue += 360.0f;
        }
        return Math.round(hue);
    }

    public static int getHueFromColor(int color) {
        return ColorUtils.getHue(ColorUtils.getRedFromColor(color), ColorUtils.getGreenFromColor(color), ColorUtils.getBlueFromColor(color));
    }

    public static float getFullyBrightnessFromColor(int color) {
        float max = 765.0f;
        float main = (float)ColorUtils.getRedFromColor(color) * 1.0f + (float)ColorUtils.getGreenFromColor(color) * 1.0f + (float)ColorUtils.getBlueFromColor(color) * 1.0f;
        return main / max;
    }

    public static float getSaturateFromColor(int color) {
        float[] athsb = Color.RGBtoHSB(ColorUtils.getRedFromColor(color), ColorUtils.getGreenFromColor(color), ColorUtils.getBlueFromColor(color), null);
        return athsb[1];
    }

    public static float getBrightnessFromColor(int color) {
        float[] athsb = Color.RGBtoHSB(ColorUtils.getRedFromColor(color), ColorUtils.getGreenFromColor(color), ColorUtils.getBlueFromColor(color), null);
        return athsb[2];
    }

    public static int getOverallColorFrom(int color1, int color2) {
        int red1 = ColorUtils.getRedFromColor(color1);
        int green1 = ColorUtils.getGreenFromColor(color1);
        int blue1 = ColorUtils.getBlueFromColor(color1);
        int alpha1 = ColorUtils.getAlphaFromColor(color1);
        int red2 = ColorUtils.getRedFromColor(color2);
        int green2 = ColorUtils.getGreenFromColor(color2);
        int blue2 = ColorUtils.getBlueFromColor(color2);
        int alpha2 = ColorUtils.getAlphaFromColor(color2);
        int finalRed = (red1 + red2) / 2;
        int finalGreen = (green1 + green2) / 2;
        int finalBlue = (blue1 + blue2) / 2;
        int finalAlpha = (alpha1 + alpha2) / 2;
        return ColorUtils.getColor(finalRed, finalGreen, finalBlue, finalAlpha);
    }

    public static int getOverallColorFrom(int color1, int color2, float percentTo2) {
        int red1 = ColorUtils.getRedFromColor(color1);
        int green1 = ColorUtils.getGreenFromColor(color1);
        int blue1 = ColorUtils.getBlueFromColor(color1);
        int alpha1 = ColorUtils.getAlphaFromColor(color1);
        int red2 = ColorUtils.getRedFromColor(color2);
        int green2 = ColorUtils.getGreenFromColor(color2);
        int blue2 = ColorUtils.getBlueFromColor(color2);
        int alpha2 = ColorUtils.getAlphaFromColor(color2);
        int finalRed = (int)((float)red1 * (1.0f - percentTo2) + (float)red2 * percentTo2);
        int finalGreen = (int)((float)green1 * (1.0f - percentTo2) + (float)green2 * percentTo2);
        int finalBlue = (int)((float)blue1 * (1.0f - percentTo2) + (float)blue2 * percentTo2);
        int finalAlpha = (int)((float)alpha1 * (1.0f - percentTo2) + (float)alpha2 * percentTo2);
        return ColorUtils.getColor(finalRed, finalGreen, finalBlue, finalAlpha);
    }

    public static int fadeColor(int color1, int color2, float speed) {
        float cr1 = color1 >> 16 & 0xFF;
        float cg1 = color1 >> 8 & 0xFF;
        float cb1 = color1 & 0xFF;
        float ca1 = color1 >> 24 & 0xFF;
        float cr2 = color2 >> 16 & 0xFF;
        float cg2 = color2 >> 8 & 0xFF;
        float cb2 = color2 & 0xFF;
        float ca2 = color2 >> 24 & 0xFF;
        return ColorUtils.TwoColoreffect((int)cr1, (int)cg1, (int)cb1, (int)ca1, (int)cr2, (int)cg2, (int)cb2, (int)ca2, (double)Math.abs(System.currentTimeMillis() / 4L) / 100.1275 * (double)speed);
    }

    public static int fadeColor(int color1, int color2, float speed, int index) {
        float cr1 = color1 >> 16 & 0xFF;
        float cg1 = color1 >> 8 & 0xFF;
        float cb1 = color1 & 0xFF;
        float ca1 = color1 >> 24 & 0xFF;
        float cr2 = color2 >> 16 & 0xFF;
        float cg2 = color2 >> 8 & 0xFF;
        float cb2 = color2 & 0xFF;
        float ca2 = color2 >> 24 & 0xFF;
        return ColorUtils.TwoColoreffect((int)cr1, (int)cg1, (int)cb1, (int)ca1, (int)cr2, (int)cg2, (int)cb2, (int)ca2, (double)Math.abs(System.currentTimeMillis() / 4L + (long)index) / 100.1275 * (double)speed);
    }

    public static int fadeColorIndexed(int color1, int color2, float speed, int index) {
        float cr1 = color1 >> 16 & 0xFF;
        float cg1 = color1 >> 8 & 0xFF;
        float cb1 = color1 & 0xFF;
        float ca1 = color1 >> 24 & 0xFF;
        float cr2 = color2 >> 16 & 0xFF;
        float cg2 = color2 >> 8 & 0xFF;
        float cb2 = color2 & 0xFF;
        float ca2 = color2 >> 24 & 0xFF;
        return ColorUtils.TwoColoreffect((int)cr1, (int)cg1, (int)cb1, (int)ca1, (int)cr2, (int)cg2, (int)cb2, (int)ca2, (double)Math.abs(System.currentTimeMillis() / 4L + (long)index) / 100.1275 * (double)speed);
    }

    public static int reAlpha(int color, float alpha) {
        Color c = new Color(color);
        float r = 0.003921569f * (float)c.getRed();
        float g = 0.003921569f * (float)c.getGreen();
        float b = 0.003921569f * (float)c.getBlue();
        return new Color(r, g, b, alpha).getRGB();
    }

    public static int swapAlpha(int color, float alpha) {
        int f = color >> 16 & 0xFF;
        int f1 = color >> 8 & 0xFF;
        int f2 = color & 0xFF;
        return ColorUtils.getColor(f, f1, f2, (int)alpha);
    }

    public static int swapDark(int color, float dark) {
        int f = color >> 16 & 0xFF;
        int f1 = color >> 8 & 0xFF;
        int f2 = color & 0xFF;
        return ColorUtils.getColor((int)((float)f * dark), (int)((float)f1 * dark), (int)((float)f2 * dark));
    }

    public static int toDark(int color, float dark) {
        return ColorUtils.getColor((int)((float)ColorUtils.getRedFromColor(color) * dark), (int)((float)ColorUtils.getGreenFromColor(color) * dark), (int)((float)ColorUtils.getBlueFromColor(color) * dark), ColorUtils.getAlphaFromColor(color));
    }

    public static Color getGradientOffset(Color color1, Color color2, double offset) {
        if (offset > 1.0) {
            double left = offset % 1.0;
            int off = (int)offset;
            offset = off % 2 == 0 ? left : 1.0 - left;
        }
        double inverse_percent = 1.0 - offset;
        int redPart = (int)((double)color1.getRed() * inverse_percent + (double)color2.getRed() * offset);
        int greenPart = (int)((double)color1.getGreen() * inverse_percent + (double)color2.getGreen() * offset);
        int bluePart = (int)((double)color1.getBlue() * inverse_percent + (double)color2.getBlue() * offset);
        return new Color(redPart, greenPart, bluePart);
    }

    public static int getColor1(int brightness) {
        return ColorUtils.getColor(brightness, brightness, brightness, 255);
    }

    public static int getColor(int red, int green, int blue) {
        return ColorUtils.getColor(red, green, blue, 255);
    }

    public static int getColor(int red, int green, int blue, int alpha) {
        int color = 0;
        color |= alpha << 24;
        color |= red << 16;
        color |= green << 8;
        return color |= blue;
    }

    public static int getColor(int red, int green, int blue, float alpha) {
        int color = 0;
        color |= (int)alpha << 24;
        color |= red << 16;
        color |= green << 8;
        return color |= blue;
    }

    public static int getColor(int brightness) {
        return ColorUtils.getColor(brightness, brightness, brightness, 255);
    }

    public static int getColor(int brightness, int alpha) {
        return ColorUtils.getColor(brightness, brightness, brightness, alpha);
    }

    public static Color fade(Color color, int index, int count) {
        float[] hsb = new float[3];
        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsb);
        float brightness = Math.abs(((float)(System.currentTimeMillis() % 2000L) / 1000.0f + (float)index / (float)count * 2.0f) % 2.0f - 1.0f);
        brightness = 0.5f + 0.5f * brightness;
        hsb[2] = brightness % 2.0f;
        return new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
    }

    public static Color getHealthColor(EntityLivingBase entityLivingBase) {
        float health = entityLivingBase.getHealth();
        float[] fractions = new float[]{0.0f, 0.15f, 0.55f, 0.7f, 0.9f};
        Color[] colors = new Color[]{new Color(133, 0, 0), Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN};
        float progress = health / entityLivingBase.getMaxHealth();
        return health >= 0.0f ? ColorUtils.blendColors(fractions, colors, progress).brighter() : colors[0];
    }

    public static Color getProgressColor(float val) {
        float[] fractions = new float[]{0.0f, 0.15f, 0.55f, 0.7f, 0.9f};
        Color[] colors = new Color[]{new Color(133, 0, 0), Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN};
        float progress = val;
        return val >= 0.0f ? ColorUtils.blendColors(fractions, colors, progress).brighter() : colors[0];
    }

    public static Color blendColors(float[] fractions, Color[] colors, float progress) {
        if (fractions == null) {
            throw new IllegalArgumentException("Fractions can't be null");
        }
        if (colors == null) {
            throw new IllegalArgumentException("Colours can't be null");
        }
        if (fractions.length != colors.length) {
            throw new IllegalArgumentException("Fractions and colours must have equal number of elements");
        }
        int[] indicies = ColorUtils.getFractionIndicies(fractions, progress);
        float[] range = new float[]{fractions[indicies[0]], fractions[indicies[1]]};
        Color[] colorRange = new Color[]{colors[indicies[0]], colors[indicies[1]]};
        float max = range[1] - range[0];
        float value = progress - range[0];
        float weight = value / max;
        return ColorUtils.blend(colorRange[0], colorRange[1], 1.0f - weight);
    }

    public static int[] getFractionIndicies(float[] fractions, float progress) {
        int startPoint;
        int[] range = new int[2];
        for (startPoint = 0; startPoint < fractions.length && fractions[startPoint] <= progress; ++startPoint) {
        }
        if (startPoint >= fractions.length) {
            startPoint = fractions.length - 1;
        }
        range[0] = startPoint - 1;
        range[1] = startPoint;
        return range;
    }

    public static Color blend(Color color1, Color color2, double ratio) {
        float r = (float)ratio;
        float ir = 1.0f - r;
        float[] rgb1 = new float[3];
        float[] rgb2 = new float[3];
        color1.getColorComponents(rgb1);
        color2.getColorComponents(rgb2);
        float red = rgb1[0] * r + rgb2[0] * ir;
        float green = rgb1[1] * r + rgb2[1] * ir;
        float blue = rgb1[2] * r + rgb2[2] * ir;
        if (red < 0.0f) {
            red = 0.0f;
        } else if (red > 255.0f) {
            red = 255.0f;
        }
        if (green < 0.0f) {
            green = 0.0f;
        } else if (green > 255.0f) {
            green = 255.0f;
        }
        if (blue < 0.0f) {
            blue = 0.0f;
        } else if (blue > 255.0f) {
            blue = 255.0f;
        }
        Color color = null;
        try {
            color = new Color(red, green, blue);
        } catch (IllegalArgumentException exp) {
            NumberFormat numberFormat = NumberFormat.getNumberInstance();
        }
        return color;
    }

    public static int astolfo(int delay, float offset) {
        float f;
        float hue;
        float speed = 3000.0f;
        for (hue = Math.abs((float)(System.currentTimeMillis() % (long)delay) + -offset / 21.0f * 2.0f); hue > speed; hue -= speed) {
        }
        hue /= speed;
        if ((double)f > 0.5) {
            hue = 0.5f - (hue - 0.5f);
        }
        return Color.HSBtoRGB(hue += 0.5f, 0.5f, 1.0f);
    }

    public static int Yellowastolfo(int delay, float offset) {
        float f;
        float hue;
        float speed = 2900.0f;
        for (hue = (float)(System.currentTimeMillis() % (long)((int)speed)) + ((float)(-delay) - offset) * 9.0f; hue > speed; hue -= speed) {
        }
        hue /= speed;
        if ((double)f > 0.6) {
            hue = 0.6f - (hue - 0.6f);
        }
        return Color.HSBtoRGB(hue += 0.6f, 0.5f, 1.0f);
    }

    public static int YellowastolfoLT(int delay, float offset) {
        float f;
        float hue;
        float speed = 1450.0f;
        for (hue = (float)(System.currentTimeMillis() % (long)((int)speed)) + ((float)(-delay) - offset) * 9.0f; hue > speed; hue -= speed) {
        }
        hue /= speed;
        if ((double)f > 0.6) {
            hue = 0.6f - (hue - 0.6f);
        }
        return Color.HSBtoRGB(hue += 0.6f, 0.5f, 1.0f);
    }

    public static Color Yellowastolfo1(int delay, float offset) {
        float f;
        float hue;
        float speed = 2900.0f;
        for (hue = (float)(System.currentTimeMillis() % (long)((int)speed)) + ((float)delay - offset) * 9.0f; hue > speed; hue -= speed) {
        }
        hue /= speed;
        if ((double)f > 0.6) {
            hue = 0.6f - (hue - 0.6f);
        }
        return new Color(hue += 0.6f, 0.5f, 1.0f);
    }

    public static Color Yellowastolfo2(int delay, float offset) {
        float f;
        float hue;
        float speed = 2900.0f;
        for (hue = (float)(System.currentTimeMillis() % (long)((int)speed)) + ((float)delay - offset) * 9.0f; hue > speed; hue -= speed) {
        }
        hue /= speed;
        if ((double)f > 0.6) {
            hue = 0.6f - (hue - 0.6f);
        }
        return new Color(hue += 0.6f, 0.5f, 1.0f);
    }

    public static Color TwoColoreffect(Color cl1, Color cl2, double speed) {
        double thing = speed / 4.0 % 1.0;
        float val = MathUtils.clamp((float)Math.sin(Math.PI * 6 * thing) / 2.0f + 0.5f, 0.0f, 1.0f);
        return new Color(MathUtils.lerp((float)cl1.getRed() / 255.0f, (float)cl2.getRed() / 255.0f, val), MathUtils.lerp((float)cl1.getGreen() / 255.0f, (float)cl2.getGreen() / 255.0f, val), MathUtils.lerp((float)cl1.getBlue() / 255.0f, (float)cl2.getBlue() / 255.0f, val));
    }

    public static int TwoColoreffect(int cl1, int cl2, double speed) {
        double thing = speed / 4.0 % 1.0;
        float val = MathUtils.clamp((float)Math.sin(Math.PI * 6 * thing) / 2.0f + 0.5f, 0.0f, 1.0f);
        return ColorUtils.getColor((int)MathUtils.lerp(cl1, cl2, val), (int)MathUtils.lerp(cl1, cl2, val), (int)MathUtils.lerp(cl1, cl2, val));
    }

    public static int TwoColoreffect(int cl1r, int cl1g, int cl1b, int cl2r, int cl2g, int cl2b, double speed) {
        double thing = speed / 4.0 % 1.0;
        float val = MathUtils.clamp((float)Math.sin(Math.PI * 6 * thing) / 2.0f + 0.5f, 0.0f, 1.0f);
        return ColorUtils.getColor((int)MathUtils.lerp(cl1r, cl2r, val), (int)MathUtils.lerp(cl1g, cl2g, val), (int)MathUtils.lerp(cl1b, cl2b, val));
    }

    public static int TwoColoreffect(int cl1r, int cl1g, int cl1b, int cl2r, int cl2g, int cl2b, int alpha, double speed) {
        double thing = speed / 4.0 % 1.0;
        float val = MathUtils.clamp((float)Math.sin(Math.PI * 6 * thing) / 2.0f + 0.5f, 0.0f, 1.0f);
        return ColorUtils.getColor((int)MathUtils.lerp(cl1r, cl2r, val), (int)MathUtils.lerp(cl1g, cl2g, val), (int)MathUtils.lerp(cl1b, cl2b, val), alpha);
    }

    public static int TwoColoreffect(int cl1r, int cl1g, int cl1b, int cl1a, int cl2r, int cl2g, int cl2b, int cl2a, double speed) {
        double thing = speed / 4.0 % 1.0;
        float val = MathUtils.clamp((float)Math.sin(Math.PI * 6 * thing) / 2.0f + 0.5f, 0.0f, 1.0f);
        return ColorUtils.getColor((int)MathUtils.lerp(cl1r, cl2r, val), (int)MathUtils.lerp(cl1g, cl2g, val), (int)MathUtils.lerp(cl1b, cl2b, val), (int)MathUtils.lerp(cl1a, cl2a, val));
    }

    public static int astolfoColors(int yOffset, int yTotal) {
        float f;
        float hue;
        float speed = 2900.0f;
        for (hue = (float)(System.currentTimeMillis() % (long)((int)speed)) + (float)((yTotal - yOffset) * 9); hue > speed; hue -= speed) {
        }
        hue /= speed;
        if ((double)f > 0.5) {
            hue = 0.5f - (hue - 0.5f);
        }
        return Color.HSBtoRGB(hue += 0.5f, 0.5f, 1.0f);
    }

    public static int astolfoNew(int delay, float offset) {
        float f;
        float hue;
        float speed = 2900.0f;
        for (hue = (float)(System.currentTimeMillis() % (long)((int)speed)) + ((float)delay - offset) * 9.0f; hue > speed; hue -= speed) {
        }
        hue /= speed;
        if ((double)f > 0.5) {
            hue = 0.5f - (hue - 0.5f);
        }
        return Color.HSBtoRGB(hue += 0.5f, 0.5f, 1.0f);
    }

    public static int astolfoColorsCool(int yOffset, int yTotal) {
        float f;
        float hue;
        float speed = 1450.0f;
        for (hue = (float)(System.currentTimeMillis() % (long)((int)speed)) + (float)((yTotal - yOffset) * 9); hue > speed; hue -= speed) {
        }
        hue /= speed;
        if ((double)f > 0.5) {
            hue = 0.5f - (hue - 0.5f);
        }
        return Color.HSBtoRGB(hue += 0.5f, 0.6f, 1.0f);
    }

    public static int astolfoColorsCoolWithDark(int yOffset, int yTotal, float dark) {
        float f;
        float hue;
        float speed = 1450.0f;
        for (hue = (float)(System.currentTimeMillis() % (long)((int)speed)) + (float)((yTotal - yOffset) * 9); hue > speed; hue -= speed) {
        }
        hue /= speed;
        if ((double)f > 0.5) {
            hue = 0.5f - (hue - 0.5f);
        }
        return Color.HSBtoRGB(hue += 0.5f, 0.67f, 1.0f * dark);
    }

    public static int getTeamColor(Entity entityIn) {
        int i = -1;
        i = entityIn.getDisplayName().getUnformattedText().equalsIgnoreCase("\u043f\u0457\u0405f[\u043f\u0457\u0405cR\u043f\u0457\u0405f]\u043f\u0457\u0405c" + entityIn.getName()) ? ColorUtils.getColor(new Color(255, 60, 60)) : (entityIn.getDisplayName().getUnformattedText().equalsIgnoreCase("\u043f\u0457\u0405f[\u043f\u0457\u04059B\u043f\u0457\u0405f]\u043f\u0457\u04059" + entityIn.getName()) ? ColorUtils.getColor(new Color(60, 60, 255)) : (entityIn.getDisplayName().getUnformattedText().equalsIgnoreCase("\u043f\u0457\u0405f[\u043f\u0457\u0405eY\u043f\u0457\u0405f]\u043f\u0457\u0405e" + entityIn.getName()) ? ColorUtils.getColor(new Color(255, 255, 60)) : (entityIn.getDisplayName().getUnformattedText().equalsIgnoreCase("\u043f\u0457\u0405f[\u043f\u0457\u0405aG\u043f\u0457\u0405f]\u043f\u0457\u0405a" + entityIn.getName()) ? ColorUtils.getColor(new Color(60, 255, 60)) : ColorUtils.getColor(new Color(255, 255, 255)))));
        return i;
    }

    public static Color astolfoColors1(int yOffset, int yTotal) {
        float f;
        float hue;
        float speed = 2900.0f;
        for (hue = (float)(System.currentTimeMillis() % (long)((int)speed)) + (float)((yTotal - yOffset) * 9); hue > speed; hue -= speed) {
        }
        hue /= speed;
        if ((double)f > 0.5) {
            hue = 0.5f - (hue - 0.5f);
        }
        return new Color(hue += 0.5f, 0.5f, 1.0f);
    }

    public static Color rainbowCol(int delay, float saturation, float brightness) {
        double rainbow = Math.ceil((System.currentTimeMillis() + (long)delay) / 12L);
        return Color.getHSBColor((float)((rainbow %= 360.0) / 360.0), saturation, brightness);
    }

    public static Color Flicker(int delay, float saturation, float brightness) {
        double rainbow = Math.ceil((System.currentTimeMillis() + (long)delay) / 10L);
        return Color.getHSBColor((float)((rainbow %= 10.0) / 10.0), saturation, brightness);
    }

    public static Color Rgbdel(int delay, float saturation, float brightness, float d) {
        double rainbow = Math.ceil((double)(System.currentTimeMillis() + (long)d) / 128.0);
        return Color.getHSBColor((float)((rainbow %= (double)d * 3.6) / (double)d * 3.6), saturation, brightness);
    }

    public static Color rainbowColA(int delay, float saturation, float brightness) {
        double rainbow = Math.ceil((System.currentTimeMillis() + (long)delay) / 64L);
        return Color.getHSBColor((float)((rainbow %= 360.0) / 360.0), saturation, brightness);
    }

    public static int rainbowNew(int delay, float saturation, float brightness) {
        double rainbow = Math.ceil((System.currentTimeMillis() + (long)delay) / 16L);
        return Color.getHSBColor((float)((rainbow %= 360.0) / 360.0), saturation, brightness).getRGB();
    }

    public static Color TwoColoreffect(org.lwjgl.util.Color color, org.lwjgl.util.Color color2, double speed) {
        return null;
    }
}

