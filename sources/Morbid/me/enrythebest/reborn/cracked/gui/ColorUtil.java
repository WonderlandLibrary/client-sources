package me.enrythebest.reborn.cracked.gui;

import java.awt.*;

public class ColorUtil
{
    public static int transparency(final int var0, final double var1) {
        final Color var2 = new Color(var0);
        final float var3 = 0.003921569f * var2.getRed();
        final float var4 = 0.003921569f * var2.getGreen();
        final float var5 = 0.003921569f * var2.getBlue();
        return new Color(var3, var4, var5, (float)var1).getRGB();
    }
    
    public static Color rainbow(final long var0, final float var2) {
        final float var3 = (System.nanoTime() + var0) / 1.0E10f % 1.0f;
        final long var4 = Long.parseLong(Integer.toHexString(Integer.valueOf(Color.HSBtoRGB(var3, 1.0f, 1.0f))), 16);
        final Color var5 = new Color((int)var4);
        return new Color(var5.getRed() / 255.0f * var2, var5.getGreen() / 255.0f * var2, var5.getBlue() / 255.0f * var2, var5.getAlpha() / 255.0f);
    }
    
    public static Color random(final long var0, final float var2) {
        final float var3 = (System.nanoTime() + var0) / 1.0E10f % 1.0f;
        final long var4 = Long.parseLong(Integer.toHexString(Integer.valueOf(Color.HSBtoRGB(var3, 67.0f, 31.0f))), 30);
        final Color var5 = new Color((int)var4);
        return new Color(var5.getRed() / 255.0f * var2, var5.getGreen() / 255.0f * var2, var5.getBlue() / 255.0f * var2, var5.getAlpha() / 255.0f);
    }
    
    public static float[] getRGBA(final int var0) {
        final float var = (var0 >> 24 & 0xFF) / 255.0f;
        final float var2 = (var0 >> 16 & 0xFF) / 255.0f;
        final float var3 = (var0 >> 8 & 0xFF) / 255.0f;
        final float var4 = (var0 & 0xFF) / 255.0f;
        return new float[] { var2, var3, var4, var };
    }
    
    public static int intFromHex(final String var0) {
        try {
            return var0.equalsIgnoreCase("rainbow") ? rainbow(0L, 1.0f).getRGB() : Integer.parseInt(var0, 16);
        }
        catch (NumberFormatException var) {
            return -1;
        }
    }
    
    public static String hexFromInt(final int var0) {
        return hexFromInt(new Color(var0));
    }
    
    public static String hexFromInt(final Color var0) {
        return Integer.toHexString(var0.getRGB()).substring(2);
    }
    
    public static Color blend(final Color var0, final Color var1, final double var2) {
        final float var3 = (float)var2;
        final float var4 = 1.0f - var3;
        final float[] var5 = new float[3];
        final float[] var6 = new float[3];
        var0.getColorComponents(var5);
        var1.getColorComponents(var6);
        final Color var7 = new Color(var5[0] * var3 + var6[0] * var4, var5[1] * var3 + var6[1] * var4, var5[2] * var3 + var6[2] * var4);
        return var7;
    }
    
    public static Color blend(final Color var0, final Color var1) {
        return blend(var0, var1, 0.5);
    }
    
    public static Color darker(final Color color, final double fraction) {
        int red = (int)Math.round(color.getRed() * (1.0 - fraction));
        int green = (int)Math.round(color.getGreen() * (1.0 - fraction));
        int blue = (int)Math.round(color.getBlue() * (1.0 - fraction));
        if (red < 0) {
            red = 0;
        }
        else if (red > 255) {
            red = 255;
        }
        if (green < 0) {
            green = 0;
        }
        else if (green > 255) {
            green = 255;
        }
        if (blue < 0) {
            blue = 0;
        }
        else if (blue > 255) {
            blue = 255;
        }
        final int alpha = color.getAlpha();
        return new Color(red, green, blue, alpha);
    }
    
    public static Color lighter(final Color var0, final double var1) {
        int var2 = (int)Math.round(var0.getRed() * (1.0 + var1));
        int var3 = (int)Math.round(var0.getGreen() * (1.0 + var1));
        int var4 = (int)Math.round(var0.getBlue() * (1.0 + var1));
        if (var2 < 0) {
            var2 = 0;
        }
        else if (var2 > 255) {
            var2 = 255;
        }
        if (var3 < 0) {
            var3 = 0;
        }
        else if (var3 > 255) {
            var3 = 255;
        }
        if (var4 < 0) {
            var4 = 0;
        }
        else if (var4 > 255) {
            var4 = 255;
        }
        final int var5 = var0.getAlpha();
        return new Color(var2, var3, var4, var5);
    }
    
    public static String getHexName(final Color var0) {
        final int var = var0.getRed();
        final int var2 = var0.getGreen();
        final int var3 = var0.getBlue();
        final String var4 = Integer.toString(var, 16);
        final String var5 = Integer.toString(var2, 16);
        final String var6 = Integer.toString(var3, 16);
        return String.valueOf((var4.length() == 2) ? var4 : new StringBuilder("0").append(var4).toString()) + ((var5.length() == 2) ? var5 : ("0" + var5)) + ((var6.length() == 2) ? var6 : ("0" + var6));
    }
    
    public static double colorDistance(final double var0, final double var2, final double var4, final double var6, final double var8, final double var10) {
        final double var11 = var6 - var0;
        final double var12 = var8 - var2;
        final double var13 = var10 - var4;
        return Math.sqrt(var11 * var11 + var12 * var12 + var13 * var13);
    }
    
    public static double colorDistance(final double[] var0, final double[] var1) {
        return colorDistance(var0[0], var0[1], var0[2], var1[0], var1[1], var1[2]);
    }
    
    public static double colorDistance(final Color var0, final Color var1) {
        final float[] var2 = new float[3];
        final float[] var3 = new float[3];
        var0.getColorComponents(var2);
        var1.getColorComponents(var3);
        return colorDistance(var2[0], var2[1], var2[2], var3[0], var3[1], var3[2]);
    }
    
    public static boolean isDark(final double var0, final double var2, final double var4) {
        final double var5 = colorDistance(var0, var2, var4, 1.0, 1.0, 1.0);
        final double var6 = colorDistance(var0, var2, var4, 0.0, 0.0, 0.0);
        return var6 < var5;
    }
    
    public static boolean isDark(final Color var0) {
        final float var = var0.getRed() / 255.0f;
        final float var2 = var0.getGreen() / 255.0f;
        final float var3 = var0.getBlue() / 255.0f;
        return isDark(var, var2, var3);
    }
}
