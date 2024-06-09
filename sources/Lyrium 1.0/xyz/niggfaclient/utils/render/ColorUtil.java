// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.utils.render;

import xyz.niggfaclient.module.impl.render.HUD;
import net.minecraft.entity.EntityLivingBase;
import java.awt.Color;

public class ColorUtil
{
    public static final int WHITE;
    public static final int RED;
    public static final int LIGHT_RED;
    public static final int PINK;
    public static final int PURPLE;
    public static final int DEEP_PURPLE;
    public static final int INDIGO;
    public static final int BLUE;
    public static final int LIGHT_BLUE;
    public static final int CYAN;
    public static final int TEAL;
    public static final int GREEN;
    
    public static Color applyOpacity(final Color color, float opacity) {
        opacity = Math.min(1.0f, Math.max(0.0f, opacity));
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), (int)(color.getAlpha() * opacity));
    }
    
    public static int getRainbow2(final int speed, final int offset, final float saturation) {
        float hue = (float)((System.currentTimeMillis() + offset) % speed);
        hue /= speed;
        return Color.getHSBColor(hue, saturation, 1.0f).getRGB();
    }
    
    public static int getRainbow(final float seconds, final float saturation, final float brightness, final long index) {
        final float hue = (System.currentTimeMillis() + index) % (int)(seconds * 1000.0f) / (seconds * 1000.0f);
        return Color.HSBtoRGB(hue, saturation, brightness);
    }
    
    public static int astolfoColor(final float seconds, final float saturation, final float brightness, final float index) {
        float speed;
        float hue;
        for (speed = 3000.0f, hue = System.currentTimeMillis() % (int)(seconds * 1000.0f) + index; hue > speed; hue -= speed) {}
        hue /= speed;
        if (hue > 0.5) {
            hue = 0.5f - (hue - 0.5f);
        }
        hue += 0.5f;
        return Color.HSBtoRGB(hue, saturation, brightness);
    }
    
    public static Color blendColors(final float[] fractions, final Color[] colors, final float progress) {
        if (fractions.length == colors.length) {
            final int[] indices = getFractionIndices(fractions, progress);
            final float[] range = { fractions[indices[0]], fractions[indices[1]] };
            final Color[] colorRange = { colors[indices[0]], colors[indices[1]] };
            final float max = range[1] - range[0];
            final float value = progress - range[0];
            final float weight = value / max;
            return blend(colorRange[0], colorRange[1], 1.0f - weight);
        }
        throw new IllegalArgumentException("Fractions and colours must have equal number of elements");
    }
    
    public static int[] getFractionIndices(final float[] fractions, final float progress) {
        final int[] range = new int[2];
        int startPoint;
        for (startPoint = 0; startPoint < fractions.length && fractions[startPoint] <= progress; ++startPoint) {}
        if (startPoint >= fractions.length) {
            startPoint = fractions.length - 1;
        }
        range[0] = startPoint - 1;
        range[1] = startPoint;
        return range;
    }
    
    public static Color blend(final Color color1, final Color color2, final double ratio) {
        final float r = (float)ratio;
        final float ir = 1.0f - r;
        final float[] rgb1 = color1.getColorComponents(new float[3]);
        final float[] rgb2 = color2.getColorComponents(new float[3]);
        float red = rgb1[0] * r + rgb2[0] * ir;
        float green = rgb1[1] * r + rgb2[1] * ir;
        float blue = rgb1[2] * r + rgb2[2] * ir;
        if (red < 0.0f) {
            red = 0.0f;
        }
        else if (red > 255.0f) {
            red = 255.0f;
        }
        if (green < 0.0f) {
            green = 0.0f;
        }
        else if (green > 255.0f) {
            green = 255.0f;
        }
        if (blue < 0.0f) {
            blue = 0.0f;
        }
        else if (blue > 255.0f) {
            blue = 255.0f;
        }
        Color color3 = null;
        try {
            color3 = new Color(red, green, blue);
        }
        catch (IllegalArgumentException ex) {}
        return color3;
    }
    
    public static String getColor(final int n) {
        if (n != 1) {
            if (n == 2) {
                return "§a";
            }
            if (n == 3) {
                return "§3";
            }
            if (n == 4) {
                return "§4";
            }
            if (n >= 5) {
                return "§e";
            }
        }
        return "§f";
    }
    
    public static int getColor(final int red, final int green, final int blue, final int alpha) {
        int color = 0;
        color |= alpha << 24;
        color |= red << 16;
        color |= green << 8;
        color |= blue;
        return color;
    }
    
    public static int fade(final Color color, final int count, final int index) {
        final float[] hsb = new float[3];
        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsb);
        float brightness = Math.abs((System.currentTimeMillis() % 2000L / 1000.0f + index / (float)count * 2.0f) % 2.0f - 1.0f);
        brightness = 0.5f + 0.5f * brightness;
        return Color.HSBtoRGB(hsb[0], hsb[1], brightness % 2.0f);
    }
    
    public static Color getHealthColor(final EntityLivingBase entityLivingBase) {
        final float health = entityLivingBase.getHealth();
        final float[] fractions = { 0.0f, 0.15f, 0.55f, 0.7f, 0.9f };
        final Color[] colors = { new Color(133, 0, 0), Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN };
        final float progress = health / entityLivingBase.getMaxHealth();
        return (health >= 0.0f) ? blendColors(fractions, colors, progress).brighter() : colors[0];
    }
    
    public static double interpolate(final double oldValue, final double newValue, final double interpolationValue) {
        return oldValue + (newValue - oldValue) * interpolationValue;
    }
    
    public static int fadeBetween(final int color1, final int color2, float offset) {
        if (offset > 1.0f) {
            offset = 1.0f - offset % 1.0f;
        }
        final double invert = 1.0f - offset;
        final int r = (int)((color1 >> 16 & 0xFF) * invert + (color2 >> 16 & 0xFF) * offset);
        final int g = (int)((color1 >> 8 & 0xFF) * invert + (color2 >> 8 & 0xFF) * offset);
        final int b = (int)((color1 & 0xFF) * invert + (color2 & 0xFF) * offset);
        final int a = (int)((color1 >> 24 & 0xFF) * invert + (color2 >> 24 & 0xFF) * offset);
        return (a & 0xFF) << 24 | (r & 0xFF) << 16 | (g & 0xFF) << 8 | (b & 0xFF);
    }
    
    public static int getHUDColor(final int count) {
        long currentMillis = System.currentTimeMillis();
        if (currentMillis == -1L) {
            currentMillis = System.currentTimeMillis();
        }
        final float offset = (currentMillis + count) % 2000L / 1000.0f;
        switch (HUD.colorMode.getValue()) {
            case Static: {
                HUD.color = new Color(HUD.hudColor.getValue()).getRGB();
                break;
            }
            case Dynamic: {
                HUD.color = fade(new Color(HUD.hudColor.getValue()), -8000, count * -5);
                break;
            }
            case Astolfo: {
                HUD.color = astolfoColor(6.0f, 0.4f, 1.0f, (float)count);
                break;
            }
            case Rainbow: {
                HUD.color = getRainbow(4.0f, 0.4f, 1.0f, count);
                break;
            }
            case Christmas: {
                HUD.color = fadeBetween(new Color(255, 60, 69).getRGB(), new Color(255, 255, 255).getRGB(), offset);
                break;
            }
            case CustomGradient: {
                HUD.color = fadeBetween(new Color(HUD.customGradient1.getValue()).getRGB(), new Color(HUD.customGradient2.getValue()).getRGB(), offset);
                break;
            }
        }
        return HUD.color;
    }
    
    public static Color interpolateColorC(final Color color1, final Color color2, float amount) {
        amount = Math.min(1.0f, Math.max(0.0f, amount));
        return new Color(interpolateInt(color1.getRed(), color2.getRed(), amount), interpolateInt(color1.getGreen(), color2.getGreen(), amount), interpolateInt(color1.getBlue(), color2.getBlue(), amount), interpolateInt(color1.getAlpha(), color2.getAlpha(), amount));
    }
    
    public static Double interpolate2(final double oldValue, final double newValue, final double interpolationValue) {
        return oldValue + (newValue - oldValue) * interpolationValue;
    }
    
    public static int interpolateInt(final int oldValue, final int newValue, final double interpolationValue) {
        return interpolate2(oldValue, newValue, (float)interpolationValue).intValue();
    }
    
    public static Color interpolateColorsBackAndForth(final int speed, final int index, final Color start, final Color end) {
        int angle = (int)((System.currentTimeMillis() / speed + index) % 360L);
        angle = ((angle >= 180) ? (360 - angle) : angle) * 2;
        return interpolateColorC(start, end, angle / 360.0f);
    }
    
    public static Color darker(final Color color, final float factor) {
        return new Color(Math.max((int)(color.getRed() * factor), 0), Math.max((int)(color.getGreen() * factor), 0), Math.max((int)(color.getBlue() * factor), 0), color.getAlpha());
    }
    
    static {
        WHITE = Color.WHITE.getRGB();
        RED = new Color(16007990).getRGB();
        LIGHT_RED = new Color(16742509).getRGB();
        PINK = new Color(16744619).getRGB();
        PURPLE = new Color(12216520).getRGB();
        DEEP_PURPLE = new Color(8281781).getRGB();
        INDIGO = new Color(7964363).getRGB();
        BLUE = new Color(1668818).getRGB();
        LIGHT_BLUE = new Color(7652351).getRGB();
        CYAN = new Color(44225).getRGB();
        TEAL = new Color(11010027).getRGB();
        GREEN = new Color(65350).getRGB();
    }
}
