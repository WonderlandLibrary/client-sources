package zamorozka.ui;

import net.minecraft.entity.Entity;

import java.awt.*;
import java.text.NumberFormat;
import java.util.Random;

public class Colors implements MCUtil {
    public static String BLACK = "\2470";
    public static String DARK_BLUE = "\2471";
    public static String DARK_GREEN = "\2472";
    public static String DARK_AQUA = "\2473";
    public static String DARK_RED = "\2474";
    public static String DARK_PURPLE = "\2475";
    public static String GOLD = "\2476";
    public static String GRAY = "\2477";
    public static String DARK_GRAY = "\2478";
    public static String BLUE = "\2479";
    public static String GREEN = "\247a";
    public static String AQUA = "\247b";
    public static String RED = "\247c";
    public static String LIGHT_PURPLE = "\247d";
    public static String YELLOW = "\247e";
    public static String WHITE = "\247f";
    public static String RANDOM = "\247k";
    public static String BOLD = "\247l";
    public static String STRIKETHROUGH = "\247m";
    public static String UNDERLINE = "\247n";
    public static String ITALIC = "\247o";
    public static String RESET = "\247r";

    public static int getRandomColor() {
        char[] letters = "012345678".toCharArray();
        String color = "0x";
        for (int i = 0; i < 6; i++)
            color += letters[new Random().nextInt(letters.length)];
        return Integer.decode(color);
    }

    public static int getColor(Color color) {
        return getColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    public static int getColor(int brightness) {
        return getColor(brightness, brightness, brightness, 255);
    }

    public static int getColor(int brightness, int alpha) {
        return getColor(brightness, brightness, brightness, alpha);
    }

    public static int getColor(int red, int green, int blue) {
        return getColor(red, green, blue, 255);
    }

    public static int getColor(int red, int green, int blue, int alpha) {
        int color = 0;
        color |= alpha << 24;
        color |= red << 16;
        color |= green << 8;
        color |= blue;
        return color;
    }

    public static int reAlpha(int color, float alpha) {
        Color c = new Color(color);
        float r = 0.003921569F * c.getRed();
        float g = 0.003921569F * c.getGreen();
        float b = 0.003921569F * c.getBlue();
        return new Color(r, g, b, alpha).getRGB();
    }

    public static Color blendColors(float[] fractions, Color[] colors, float progress) {
        Color color = null;
        if (fractions != null) {
            if (colors != null) {
                if (fractions.length == colors.length) {
                    int[] indicies = getFractionIndicies(fractions, progress);

                    if (indicies[0] < 0 || indicies[0] >= fractions.length || indicies[1] < 0 || indicies[1] >= fractions.length) {
                        return colors[0];
                    }
                    float[] range = new float[]{fractions[indicies[0]], fractions[indicies[1]]};
                    Color[] colorRange = new Color[]{colors[indicies[0]], colors[indicies[1]]};

                    float max = range[1] - range[0];
                    float value = progress - range[0];
                    float weight = value / max;

                    color = blend(colorRange[0], colorRange[1], 1f - weight);
                } else {
                    throw new IllegalArgumentException("Fractions and colours must have equal number of elements");
                }
            } else {
                throw new IllegalArgumentException("Colours can't be null");
            }
        } else {
            throw new IllegalArgumentException("Fractions can't be null");
        }
        return color;
    }

    public static Color blend(Color color1, Color color2, double ratio) {
        float r = (float) ratio;
        float ir = (float) 1.0 - r;

        float[] rgb1 = new float[3];
        float[] rgb2 = new float[3];

        color1.getColorComponents(rgb1);
        color2.getColorComponents(rgb2);

        float red = rgb1[0] * r + rgb2[0] * ir;
        float green = rgb1[1] * r + rgb2[1] * ir;
        float blue = rgb1[2] * r + rgb2[2] * ir;

        if (red < 0) {
            red = 0;
        } else if (red > 255) {
            red = 255;
        }
        if (green < 0) {
            green = 0;
        } else if (green > 255) {
            green = 255;
        }
        if (blue < 0) {
            blue = 0;
        } else if (blue > 255) {
            blue = 255;
        }

        Color color = null;
        try {
            color = new Color(red, green, blue);
        } catch (IllegalArgumentException exp) {
            NumberFormat nf = NumberFormat.getNumberInstance();
            System.out.println(nf.format(red) + "; " + nf.format(green) + "; " + nf.format(blue));
            exp.printStackTrace();
        }
        return color;
    }

    public static Color getHealthColor(float health, float maxHealth) {
        float[] fractions = {0.0F, 0.5F, 1.0F};
        Color[] colors = {new Color(108, 0, 0), new Color(255, 51, 0), Color.GREEN};
        float progress = health / maxHealth;
        return blendColors(fractions, colors, progress).brighter();
    }

    public static int[] getFractionIndicies(float[] fractions, float progress) {
        int[] range = new int[2];

        int startPoint = 0;
        while (startPoint < fractions.length && fractions[startPoint] <= progress) {
            startPoint++;
        }

        if (startPoint >= fractions.length) {
            startPoint = fractions.length - 1;
        }

        range[0] = startPoint - 1;
        range[1] = startPoint;

        return range;
    }

    public static int getTeamColor(Entity entityIn) {
        int i = -1;
        if (entityIn.getDisplayName().getUnformattedText().equalsIgnoreCase("�f[�cR�f]�c" + entityIn.getName())) {
            i = Colors.getColor(new Color(255, 60, 60));
        } else if (entityIn.getDisplayName().getUnformattedText().equalsIgnoreCase("�f[�9B�f]�9" + entityIn.getName())) {
            i = Colors.getColor(new Color(60, 60, 255));
        } else if (entityIn.getDisplayName().getUnformattedText().equalsIgnoreCase("�f[�eY�f]�e" + entityIn.getName())) {
            i = Colors.getColor(new Color(255, 255, 60));
        } else if (entityIn.getDisplayName().getUnformattedText().equalsIgnoreCase("�f[�aG�f]�a" + entityIn.getName())) {
            i = Colors.getColor(new Color(60, 255, 60));
        } else {
            i = Colors.getColor(new Color(255, 255, 255));
        }
        return i;
    }

    public static Color rainbow(long time, float count, float fade) {
        float hue = ((float) time + (1.0F + count) * 2.0E8F) / 1.0E10F % 1.0F;
        long color = Long.parseLong(Integer.toHexString(Integer.valueOf(Color.HSBtoRGB(hue, 1.0F, 1.0F)).intValue()), 16);
        Color c = new Color((int) color);
        return new Color(c.getRed() / 255.0F * fade, c.getGreen() / 255.0F * fade, c.getBlue() / 255.0F * fade, c.getAlpha() / 255.0F);
    }

    public static int rainbow(int delay, float saturation, float brightness) {
        double rainbow = Math.ceil((System.currentTimeMillis() + delay) / 16);
        rainbow %= 360.0D;
        return Color.getHSBColor((float) (rainbow / 360.0D), saturation, brightness).getRGB();
    }

    public static Color rainbowCol(int delay, float saturation, float brightness) {
        double rainbow = Math.ceil((System.currentTimeMillis() + delay) / 16);
        rainbow %= 360.0D;
        return Color.getHSBColor((float) (rainbow / 360.0D), saturation, brightness);
    }
}