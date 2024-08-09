package dev.darkmoon.client.utility.render;

import dev.darkmoon.client.DarkMoon;
import dev.darkmoon.client.module.impl.render.Arraylist;
import dev.darkmoon.client.module.impl.render.Hud;
import dev.darkmoon.client.utility.Utility;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class ColorUtility implements Utility {
    public static float[] getRGBAf(int color) {
        return new float[]{(color >> 16 & 0xFF) / 255.0f, (color >> 8 & 0xFF) / 255.0f, (color & 0xFF) / 255.0f,
                (color >> 24 & 0xFF) / 255.0f};
    }
    public static int rgba(int var0, int var1, int var2, int var3) {
        return var3 << 24 | var0 << 16 | var1 << 8 | var2;
    }
    public static int interpolateColor(Color color1, Color color2, float amount) {
        amount = Math.min(1, Math.max(0, amount));
        return interpolateColorC(color1, color2, amount).getRGB();
    }
    public static int rgba(double r, double g, double b, double a) {
        return rgba((int) r, (int) g, (int) b, (int) a);
    }

    public static Color astolfo(float yDist, float yTotal, float saturation, float speedt) {
        float speed = 2800f;
        float hue = (System.currentTimeMillis() % (int) speed) + (yTotal - yDist) * speedt;
        while (hue > speed) {
            hue -= speed;
        }
        hue /= speed;
        if (hue > 0.5) {
            hue = 0.5F - (hue - 0.5f);
        }
        hue += 0.5F;
        return Color.getHSBColor(hue, saturation, 1F);
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
    public static Color blendColors(float[] fractions, Color[] colors, float progress) {
        Color color = null;
        if (fractions != null && colors != null && fractions.length == colors.length) {
            int[] indicies = getFractionIndicies(fractions, progress);
            if (indicies[0] < 0 || indicies[0] >= fractions.length || indicies[1] < 0 || indicies[1] >= fractions.length) {
                return colors[0];
            }
            float[] range = new float[]{fractions[indicies[0]], fractions[indicies[1]]};
            Color[] colorRange = new Color[]{colors[indicies[0]], colors[indicies[1]]};
            float max = range[1] - range[0];
            float value = progress - range[0];
            float weight = value / max;
            color = blend(colorRange[0], colorRange[1], 1.0f - weight);
        }
        return color;
    }
    public static int applyOpacity(int color, float opacity) {
        Color old = new Color(color);
        return applyOpacity(old, opacity).getRGB();
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
        Color color = new Color(red, green, blue);
        return color;
    }

    public static Color getHealthColor(float health, float maxHealth) {
        GlStateManager.pushMatrix();
        float[] fractions = new float[]{0.0f, 0.5f, 1.0f};
        Color[] colors = new Color[]{new Color(108, 0, 0), new Color(255, 51, 0), Color.GREEN};
        float progress = health / maxHealth;
        GlStateManager.popMatrix();
        return blendColors(fractions, colors, progress).brighter();
    }
    public static void setColor(Color color) {
        if (color == null) color = Color.WHITE;
        setColor(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, color.getAlpha() / 255F);
    }

    public static int setAlpha(int color, int alpha) {
        Color c = new Color(color);
        return new Color(c.getRed(), c.getGreen(), c.getBlue(), alpha).getRGB();
    }

    public static int setAlpha(int color, float alpha) {
        Color c = new Color(color);
        return new Color(c.getRed() / 255f, c.getGreen() / 255f, c.getBlue() / 255f, alpha).getRGB();
    }

    public static void setColor(double red, double green, double blue, double alpha) {
        GL11.glColor4d(red, green, blue, alpha);
    }

    public static void setColor(int color) {
        setColor(color, (float) (color >> 24 & 255) / 255.0F);
    }

    public static void setColor(int color, float alpha) {
        float r = (float) (color >> 16 & 255) / 255.0F;
        float g = (float) (color >> 8 & 255) / 255.0F;
        float b = (float) (color & 255) / 255.0F;
        GlStateManager.color(r, g, b, alpha);
    }

    public static int getRed(final int hex) {
        return hex >> 16 & 255;
    }

    public static int getGreen(final int hex) {
        return hex >> 8 & 255;
    }

    public static int getBlue(final int hex) {
        return hex & 255;
    }

    public static int getAlpha(final int hex) {
        return hex >> 24 & 255;
    }

    public static Double interpolate(double oldValue, double newValue, double interpolationValue) {
        return (oldValue + (newValue - oldValue) * interpolationValue);
    }

    public static Color applyOpacity(Color color, float opacity) {
        opacity = Math.min(1, Math.max(0, opacity));
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), (int) (color.getAlpha() * opacity));
    }

    public static int interpolateInt(int oldValue, int newValue, double interpolationValue) {
        return interpolate(oldValue, newValue, (float) interpolationValue).intValue();
    }
    public static Color interpolateColorC(Color color1, Color color2, float amount) {
        amount = Math.min(1, Math.max(0, amount));
        return new Color(interpolateInt(color1.getRed(), color2.getRed(), amount),
                interpolateInt(color1.getGreen(), color2.getGreen(), amount),
                interpolateInt(color1.getBlue(), color2.getBlue(), amount),
                interpolateInt(color1.getAlpha(), color2.getAlpha(), amount));
    }

    public static Color interpolateColorC(int color1, int color2, float amount) {
        amount = Math.min(1, Math.max(0, amount));
        return new Color(interpolateInt(getRed(color1), getRed(color2), amount),
                interpolateInt(getGreen(color1), getGreen(color2), amount),
                interpolateInt(getBlue(color1), getBlue(color2), amount),
                interpolateInt(getAlpha(color1), getAlpha(color2), amount));
    }

    public static Color gradient(int speed, int index, Color... colors) {
        int angle = (int) ((System.currentTimeMillis() / speed + index) % 360);
        angle = (angle > 180 ? 360 - angle : angle) + 180;
        int colorIndex = (int) (angle / 360f * colors.length);
        if (colorIndex == colors.length) {
            colorIndex--;
        }
        Color color1 = colors[colorIndex];
        Color color2 = colors[colorIndex == colors.length - 1 ? 0 : colorIndex + 1];
        return interpolateColorC(color1.getRGB(), color2.getRGB(), angle / 360f * colors.length - colorIndex);
    }

    public static Color gradientDark(int speed, int index, Color... colors) {
        int angle = (int) ((System.currentTimeMillis() / speed + index) % 360);
        angle = (angle > 180 ? 360 - angle : angle) + 180;
        int colorIndex = (int) (angle / 360f * colors.length);
        if (colorIndex == colors.length) {
            colorIndex--;
        }
        Color color1 = colors[colorIndex];
        Color color2 = colors[colorIndex == colors.length - 1 ? 0 : colorIndex + 1];
        return interpolateColorC(color1.getRGB(), color2.getRGB(), angle / 360f * colors.length - colorIndex);
    }
    public static Color fade(int speed, int index, Color color, float alpha) {
        float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        int angle = (int) ((System.currentTimeMillis() / speed + index) % 360);
        angle = (angle > 180 ? 360 - angle : angle) + 180;

        Color colorHSB = new Color(Color.HSBtoRGB(hsb[0], hsb[1], angle / 360f));

        return new Color(colorHSB.getRed(), colorHSB.getGreen(), colorHSB.getBlue(), Math.max(0, Math.min(255, (int) (alpha * 255))));
    }

    public static int HUEtoRGB(int value) {
        float hue = (value / 360f);
        return Color.HSBtoRGB(hue, 1, 1);
    }
    public static float interpolateFloat(float oldValue, float newValue, double interpolationValue){
        return interpolate(oldValue, newValue, (float) interpolationValue).floatValue();
    }
    public static Color interpolateColorsBackAndForth(int speed, int index, Color start, Color end, boolean trueColor) {
        int angle = (int) (((System.currentTimeMillis()) / speed + index) % 360);
        angle = (angle >= 180 ? 360 - angle : angle) * 2;
        return trueColor ? interpolateColorHue(start, end, angle / 360f) : interpolateColorC(start, end, angle / 360f);
    }
    public static Color interpolateColorHue(Color color1, Color color2, float amount) {
        amount = Math.min(1, Math.max(0, amount));

        float[] color1HSB = Color.RGBtoHSB(color1.getRed(), color1.getGreen(), color1.getBlue(), null);
        float[] color2HSB = Color.RGBtoHSB(color2.getRed(), color2.getGreen(), color2.getBlue(), null);

        Color resultColor = Color.getHSBColor(interpolateFloat(color1HSB[0], color2HSB[0], amount),
                interpolateFloat(color1HSB[1], color2HSB[1], amount), interpolateFloat(color1HSB[2], color2HSB[2], amount));

        return new Color(resultColor.getRed(), resultColor.getGreen(), resultColor.getBlue(),
                interpolateInt(color1.getAlpha(), color2.getAlpha(), amount));
    }
    public static Color getColorStyle(int index) {
        Color gradientColor1 = ColorUtility2.interpolateColorsBackAndForth(Hud.hueInterpolate.get() ? 15 : 4, 0, DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[0], DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[1], Hud.hueInterpolate.get());
        Color gradientColor2 = ColorUtility2.interpolateColorsBackAndForth(Hud.hueInterpolate.get() ? 15 : 4, 90, DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[0], DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[1], Hud.hueInterpolate.get());
        Color gradientColor3 = ColorUtility2.interpolateColorsBackAndForth(Hud.hueInterpolate.get() ? 15 : 4, 180, DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[0], DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[1], Hud.hueInterpolate.get());
        Color gradientColor4 = ColorUtility2.interpolateColorsBackAndForth(Hud.hueInterpolate.get() ? 15 : 4, 270, DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[0], DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[1], Hud.hueInterpolate.get());
        return gradient((int) (10 - Arraylist.colorSpeed.get()), index, gradientColor1, gradientColor2, gradientColor3, gradientColor4);
    }
    public static Color getColor(int index) {
        Color color = DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[0];
        Color color2 = DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[1];
        return ColorUtility.gradient((int) (10 - Arraylist.colorSpeed.get()), index, color, color2);
    }

    public static Color getColorDark(int index) {
        Color color = DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[0];
        Color color2 = DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[1];
        return ColorUtility.gradientDark((int) (10 - Arraylist.colorSpeed.get()), index, color, color2, color, color2, color, color2, color, color2, color, color2);
    }
    public static String getHealthStr(EntityLivingBase entity) {
        String str = "";
        int health = (int) entity.getHealth();
        if (health <= entity.getMaxHealth() * 0.25) {
            str = "§4";
        } else if (health <= entity.getMaxHealth() * 0.5) {
            str = "§6";
        } else if (health <= entity.getMaxHealth() * 0.75) {
            str = "§e";
        } else if (health <= entity.getMaxHealth()) {
            str = "§a";
        }
        return str;
    }
}
