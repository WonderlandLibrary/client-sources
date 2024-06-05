package de.tired.api.util.shader.renderapi;

import java.awt.*;

public class ColorUtil {

    public static Color cleanColorCol(final int colorR, final float alpha) {
        final Color color = new Color(colorR);
        // 0.003921569f -- https://github.com/Ryex/libapril/blob/master/include/april/Color.h
        return new Color(0.003921569f * color.getRed(), 0.003921569f * color.getGreen(), 0.003921569f * color.getBlue(), alpha);
    }

    public static Color rainbow(long time, float count, float fade) {
        float hue = ((float) time + (1.0F + count) * 2.0E8F) / 1.0E10F % 1.0F;
        long color = Long.parseLong(Integer.toHexString(Color.HSBtoRGB(hue, .5F, 1.0F)), 16);
        Color c = new Color((int) color);
        return new Color((float) c.getRed() / 255.0F * fade, (float) c.getGreen() / 255.0F * fade, (float) c.getBlue() / 255.0F * fade, (float) c.getAlpha() / 255.0F);
    }

    public static int convertColorToInt(int color) {
        float alpha = (color >> 24 & 0xFF) / 255.0F;
        float R = (color >> 16 & 0xFF) / 255.0F;
        float G = (color >> 8 & 0xFF) / 255.0F;
        float B = (color & 0xFF) / 255.0F;
        return new Color(R,G,B, alpha).getRGB();
    }

    public static int cleanColor(final int colorR, final float alpha) {
        final Color color = new Color(colorR);
        // 0.003921569f -- https://github.com/Ryex/libapril/blob/master/include/april/Color.h
        return new Color(0.003921569f * color.getRed(), 0.003921569f * color.getGreen(), 0.003921569f * color.getBlue(), alpha).getRGB();
    }

    public static Color getGradientOffset(Color color1, Color color2, double offset) {
        if (offset > 1) {
            double left = offset % 1;
            int off = (int) offset;
            offset = off % 2 == 0 ? left : 1 - left;
        }
        double inverse_percent = 1 - offset;
        int redPart = (int) (color1.getRed() * inverse_percent + color2.getRed() * offset);
        int greenPart = (int) (color1.getGreen() * inverse_percent + color2.getGreen() * offset);
        int bluePart = (int) (color1.getBlue() * inverse_percent + color2.getBlue() * offset);
        return new Color(redPart, greenPart, bluePart);
    }
    public static Color fade(Color color, int index, int count) {
        float[] hsb = new float[3];
        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsb);
        float brightness = Math.abs(((float)(System.currentTimeMillis() % 2000L) / 1000.0F + (float)index / (float)count * 2.0F) % 2.0F - 1.0F);
        brightness = 0.5F + 0.5F * brightness;
        hsb[2] = brightness % 2.0F;
        return new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
    }

}
