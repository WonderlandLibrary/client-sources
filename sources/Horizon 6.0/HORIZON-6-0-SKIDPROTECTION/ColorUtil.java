package HORIZON-6-0-SKIDPROTECTION;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public class ColorUtil
{
    public static Map<String, ChatColor> HorizonCode_Horizon_È;
    
    static {
        ColorUtil.HorizonCode_Horizon_È = new HashMap<String, ChatColor>();
    }
    
    public static int HorizonCode_Horizon_È(final int color, final double alpha) {
        final Color c = new Color(color);
        final float r = 0.003921569f * c.getRed();
        final float g = 0.003921569f * c.getGreen();
        final float b = 0.003921569f * c.getBlue();
        return new Color(r, g, b, (float)alpha).getRGB();
    }
    
    public static Color HorizonCode_Horizon_È(final long delay, final float fade) {
        final float hue = (System.nanoTime() + delay) / 1.0E10f % 1.0f;
        final long color = Long.parseLong(Integer.toHexString(Integer.valueOf(Color.HSBtoRGB(hue, 1.0f, 1.0f))), 16);
        final Color c = new Color((int)color);
        return new Color(c.getRed() / 255.0f * fade, c.getGreen() / 255.0f * fade, c.getBlue() / 255.0f * fade, c.getAlpha() / 255.0f);
    }
    
    public static float[] HorizonCode_Horizon_È(final int color) {
        final float a = (color >> 24 & 0xFF) / 255.0f;
        final float r = (color >> 16 & 0xFF) / 255.0f;
        final float g = (color >> 8 & 0xFF) / 255.0f;
        final float b = (color & 0xFF) / 255.0f;
        return new float[] { r, g, b, a };
    }
    
    public static int HorizonCode_Horizon_È(final String hex) {
        try {
            if (hex.equalsIgnoreCase("rainbow")) {
                return HorizonCode_Horizon_È(0L, 1.0f).getRGB();
            }
            return Integer.parseInt(hex, 16);
        }
        catch (NumberFormatException e) {
            return -1;
        }
    }
    
    public static String Â(final int color) {
        return HorizonCode_Horizon_È(new Color(color));
    }
    
    public static String HorizonCode_Horizon_È(final Color color) {
        return Integer.toHexString(color.getRGB()).substring(2);
    }
    
    public static Color HorizonCode_Horizon_È(final Color color1, final Color color2, final double ratio) {
        final float r = (float)ratio;
        final float ir = 1.0f - r;
        final float[] rgb1 = new float[3];
        final float[] rgb2 = new float[3];
        color1.getColorComponents(rgb1);
        color2.getColorComponents(rgb2);
        final Color color3 = new Color(rgb1[0] * r + rgb2[0] * ir, rgb1[1] * r + rgb2[1] * ir, rgb1[2] * r + rgb2[2] * ir);
        return color3;
    }
    
    public static Color HorizonCode_Horizon_È(final Color color1, final Color color2) {
        return HorizonCode_Horizon_È(color1, color2, 0.5);
    }
    
    public static Color HorizonCode_Horizon_È(final Color color, final double fraction) {
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
    
    public static Color Â(final Color color, final double fraction) {
        int red = (int)Math.round(color.getRed() * (1.0 + fraction));
        int green = (int)Math.round(color.getGreen() * (1.0 + fraction));
        int blue = (int)Math.round(color.getBlue() * (1.0 + fraction));
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
    
    public static String Â(final Color color) {
        final int r = color.getRed();
        final int g = color.getGreen();
        final int b = color.getBlue();
        final String rHex = Integer.toString(r, 16);
        final String gHex = Integer.toString(g, 16);
        final String bHex = Integer.toString(b, 16);
        return String.valueOf((rHex.length() == 2) ? new StringBuilder().append(rHex).toString() : new StringBuilder("0").append(rHex).toString()) + ((gHex.length() == 2) ? new StringBuilder().append(gHex).toString() : ("0" + gHex)) + ((bHex.length() == 2) ? new StringBuilder().append(bHex).toString() : ("0" + bHex));
    }
    
    public static double HorizonCode_Horizon_È(final double r1, final double g1, final double b1, final double r2, final double g2, final double b2) {
        final double a = r2 - r1;
        final double b3 = g2 - g1;
        final double c = b2 - b1;
        return Math.sqrt(a * a + b3 * b3 + c * c);
    }
    
    public static double HorizonCode_Horizon_È(final double[] color1, final double[] color2) {
        return HorizonCode_Horizon_È(color1[0], color1[1], color1[2], color2[0], color2[1], color2[2]);
    }
    
    public static double Â(final Color color1, final Color color2) {
        final float[] rgb1 = new float[3];
        final float[] rgb2 = new float[3];
        color1.getColorComponents(rgb1);
        color2.getColorComponents(rgb2);
        return HorizonCode_Horizon_È(rgb1[0], rgb1[1], rgb1[2], rgb2[0], rgb2[1], rgb2[2]);
    }
    
    public static boolean HorizonCode_Horizon_È(final double r, final double g, final double b) {
        final double dWhite = HorizonCode_Horizon_È(r, g, b, 1.0, 1.0, 1.0);
        final double dBlack = HorizonCode_Horizon_È(r, g, b, 0.0, 0.0, 0.0);
        return dBlack < dWhite;
    }
    
    public static boolean Ý(final Color color) {
        final float r = color.getRed() / 255.0f;
        final float g = color.getGreen() / 255.0f;
        final float b = color.getBlue() / 255.0f;
        return HorizonCode_Horizon_È(r, g, b);
    }
}
