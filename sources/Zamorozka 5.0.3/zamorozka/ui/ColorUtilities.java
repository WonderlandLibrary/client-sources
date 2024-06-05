package zamorozka.ui;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ColorUtilities {
	public static Color getRainbow(int speed, int offset) {
		float hue = (float) (((System.currentTimeMillis() * 1.5) + offset) % speed);
		return Color.getHSBColor(hue /= speed, 0.5f, 1);
	}
	
	public static Color getRainbow(int speed, int offset, float hue) {
		return Color.getHSBColor(hue /= speed, 0.7F, 1);
	}
	
	public static Color getRainbowWithSaturation(int speed, final int offset, float saturation,
			float brightness) {
		float hue = (float) ((System.currentTimeMillis() + offset) % speed);
		hue /= speed;
		return Color.getHSBColor(hue, saturation, brightness);
	}
	
	public static String getColor(EnumColor color) {
        int count = 0;
        EnumColor[] arrenumColor = EnumColor.values();
        int n = arrenumColor.length;
        int n2 = 0;
        while (n2 < n) {
            EnumColor e = arrenumColor[n2];
            if (e == color && count < 10) {
                return "\u00a7" + count;
            }
            ++count;
            ++n2;
        }
        if (color == EnumColor.Green) {
            return "\u00a7a";
        }
        if (color == EnumColor.Aqua) {
            return "\u00a7b";
        }
        if (color == EnumColor.Red) {
            return "\u00a7c";
        }
        if (color == EnumColor.Purple) {
            return "\u00a7d";
        }
        if (color == EnumColor.Yellow) {
            return "\u00a7e";
        }
        if (color == EnumColor.White) {
            return "\u00a7f";
        }
        return "\u00a70";
    }

    public static int getHexColor(EnumColor color) {
        if (color == EnumColor.Black) {
            return -16777216;
        }
        if (color == EnumColor.DarkBlue) {
            return -16777046;
        }
        if (color == EnumColor.DarkGreen) {
            return -16733696;
        }
        if (color == EnumColor.DarkAqua) {
            return -16733526;
        }
        if (color == EnumColor.DarkRed) {
            return -5636096;
        }
        if (color == EnumColor.DarkPurple) {
            return -5635926;
        }
        if (color == EnumColor.Gold) {
            return -22016;
        }
        if (color == EnumColor.Gray) {
            return -5592406;
        }
        if (color == EnumColor.DarkGray) {
            return -11184811;
        }
        if (color == EnumColor.Blue) {
            return -11184641;
        }
        if (color == EnumColor.Green) {
            return -11141291;
        }
        if (color == EnumColor.Aqua) {
            return -11141121;
        }
        if (color == EnumColor.Red) {
            return -43691;
        }
        if (color == EnumColor.Purple) {
            return -43521;
        }
        if (color == EnumColor.Yellow) {
            return -171;
        }
        if (color == EnumColor.White) {
            return -1;
        }
        return -16777216;
    }
	
    public static Map<String, ChatColor> colors = new HashMap();
    
    class ChatColor {
        public String color;
        public String regex;

        public ChatColor(String color, String regex) {
            this.color = color;
            this.regex = regex;
        }
    }

    public static int transparency(int color, double alpha) {
        Color c = new Color(color);
        float r = ((float) 1f / 255f) * c.getRed();
        float g = ((float) 1f / 255f) * c.getGreen();
        float b = ((float) 1f / 255f) * c.getBlue();
        return new Color(r, g, b, (float) alpha).getRGB();
    }

    public static Color rainbow(long offset, float fade) {
        float hue = (float) ((System.nanoTime() + offset) / 10000000000f) % 1;
        long color = Long.parseLong(Integer.toHexString(Integer.valueOf(Color.HSBtoRGB(hue, 1f, 1f))), 16);
        Color c = new Color((int) color);
        return new Color((c.getRed() / 255f) * fade, (c.getGreen() / 255f) * fade, (c.getBlue() / 255f) * fade, c.getAlpha() / 255f);
    }

    public static float[] getRGBA(int color) {
        float a = (color >> 24 & 255) / 255f;
        float r = (color >> 16 & 255) / 255f;
        float g = (color >> 8 & 255) / 255f;
        float b = (color & 255) / 255f;
        return new float[] {r, g, b, a};
    }

    public static int intFromHex(String hex) {
        try {
            if (hex.equalsIgnoreCase("rainbow")) {
                return rainbow(0, 1f).getRGB();
            }
            return Integer.parseInt(hex, 16);
        } catch (NumberFormatException e) {
            return 0xFFFFFFFF;
        }
    }

    public static String hexFromInt(int color) {
        return hexFromInt(new Color(color));
    }

    public static String hexFromInt(Color color) {
        return Integer.toHexString(color.getRGB()).substring(2);
    }

    /**
     * Blend two colors.
     *
     * @param color1 First color to blend.
     * @param color2 Second color to blend.
     * @param ratio  Blend ratio. 0.5 will give even blend, 1.0 will return
     *               color1, 0.0 will return color2 and so on.
     * @return Blended color.
     */
    public static Color blend(Color color1, Color color2, double ratio) {
        float r = (float) ratio;
        float ir = (float) 1.0 - r;

        float rgb1[] = new float[3];
        float rgb2[] = new float[3];

        color1.getColorComponents(rgb1);
        color2.getColorComponents(rgb2);

        Color color = new Color(rgb1[0] * r + rgb2[0] * ir,
                rgb1[1] * r + rgb2[1] * ir,
                rgb1[2] * r + rgb2[2] * ir);

        return color;
    }


    /**
     * Make an even blend between two colors.
     *
     * @param c1 First color to blend.
     * @param c2 Second color to blend.
     * @return Blended color.
     */
    public static Color blend(Color color1, Color color2) {
        return ColorUtilities.blend(color1, color2, 0.5);
    }


    /**
     * Make a color darker.
     *
     * @param color    Color to make darker.
     * @param fraction Darkness fraction.
     * @return Darker color.
     */
    public static Color darker(Color color, double fraction) {
        int red = (int) Math.round(color.getRed() * (1.0 - fraction));
        int green = (int) Math.round(color.getGreen() * (1.0 - fraction));
        int blue = (int) Math.round(color.getBlue() * (1.0 - fraction));

        if (red < 0) red = 0;
        else if (red > 255) red = 255;
        if (green < 0) green = 0;
        else if (green > 255) green = 255;
        if (blue < 0) blue = 0;
        else if (blue > 255) blue = 255;

        int alpha = color.getAlpha();

        return new Color(red, green, blue, alpha);
    }


    /**
     * Make a color lighter.
     *
     * @param color    Color to make lighter.
     * @param fraction Darkness fraction.
     * @return Lighter color.
     */
    public static Color lighter(Color color, double fraction) {
        int red = (int) Math.round(color.getRed() * (1.0 + fraction));
        int green = (int) Math.round(color.getGreen() * (1.0 + fraction));
        int blue = (int) Math.round(color.getBlue() * (1.0 + fraction));

        if (red < 0) red = 0;
        else if (red > 255) red = 255;
        if (green < 0) green = 0;
        else if (green > 255) green = 255;
        if (blue < 0) blue = 0;
        else if (blue > 255) blue = 255;

        int alpha = color.getAlpha();

        return new Color(red, green, blue, alpha);
    }


    /**
     * Return the hex name of a specified color.
     *
     * @param color Color to get hex name of.
     * @return Hex name of color: "rrggbb".
     */
    public static String getHexName(Color color) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();

        String rHex = Integer.toString(r, 16);
        String gHex = Integer.toString(g, 16);
        String bHex = Integer.toString(b, 16);

        return (rHex.length() == 2 ? "" + rHex : "0" + rHex) +
                (gHex.length() == 2 ? "" + gHex : "0" + gHex) +
                (bHex.length() == 2 ? "" + bHex : "0" + bHex);
    }


    /**
     * Return the "distance" between two colors. The rgb entries are taken
     * to be coordinates in a 3D space [0.0-1.0], and this method returnes
     * the distance between the coordinates for the first and second color.
     *
     * @param r1, g1, b1  First color.
     * @param r2, g2, b2  Second color.
     * @return Distance bwetween colors.
     */
    public static double colorDistance(double r1, double g1, double b1,
                                       double r2, double g2, double b2) {
        double a = r2 - r1;
        double b = g2 - g1;
        double c = b2 - b1;

        return Math.sqrt(a * a + b * b + c * c);
    }


    /**
     * Return the "distance" between two colors.
     *
     * @param color1 First color [r,g,b].
     * @param color2 Second color [r,g,b].
     * @return Distance bwetween colors.
     */
    public static double colorDistance(double[] color1, double[] color2) {
        return ColorUtilities.colorDistance(color1[0], color1[1], color1[2],
                color2[0], color2[1], color2[2]);
    }


    /**
     * Return the "distance" between two colors.
     *
     * @param color1 First color.
     * @param color2 Second color.
     * @return Distance between colors.
     */
    public static double colorDistance(Color color1, Color color2) {
        float rgb1[] = new float[3];
        float rgb2[] = new float[3];

        color1.getColorComponents(rgb1);
        color2.getColorComponents(rgb2);

        return ColorUtilities.colorDistance(rgb1[0], rgb1[1], rgb1[2],
                rgb2[0], rgb2[1], rgb2[2]);
    }


    /**
     * Check if a color is more dark than light. Useful if an entity of
     * this color is to be labeled: Use white label on a "dark" color and
     * black label on a "light" color.
     *
     * @param r,g,b Color to check.
     * @return True if this is a "dark" color, false otherwise.
     */
    public static boolean isDark(double r, double g, double b) {
        // Measure distance to white and black respectively
        double dWhite = ColorUtilities.colorDistance(r, g, b, 1.0, 1.0, 1.0);
        double dBlack = ColorUtilities.colorDistance(r, g, b, 0.0, 0.0, 0.0);

        return dBlack < dWhite;
    }


    /**
     * Check if a color is more dark than light. Useful if an entity of
     * this color is to be labeled: Use white label on a "dark" color and
     * black label on a "light" color.
     *
     * @param color Color to check.
     * @return True if this is a "dark" color, false otherwise.
     */
    public static boolean isDark(Color color) {
        float r = color.getRed() / 255.0f;
        float g = color.getGreen() / 255.0f;
        float b = color.getBlue() / 255.0f;

        return isDark(r, g, b);
    }

	public static int getGerman(float luminance) {
		Random random = new Random();
		final float hue = random.nextFloat();
		final float saturation = (random.nextInt(8000) + 500) / 10000f;
		return Color.getHSBColor(hue, saturation, 0.9f).getRGB();
	}

	public static Color getRainbowSaturation(int speed, int offset) {
		float hue = (float) ((System.currentTimeMillis() + offset) % speed);
		return Color.getHSBColor(hue /= speed, 1F, 1.0F);
	}
	
    public static Color fade(Color color, int index, int count) {
        float[] hsb = new float[3];
        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsb);
        float brightness = Math.abs(((float)(System.currentTimeMillis() % 2000L) / 1000.0F + (float)index / (float)count * 2.0F) % 2.0F - 1.0F);
        brightness = 0.5F + 0.5F * brightness;
        hsb[2] = brightness % 2.0F;
        return new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
    }
    
	public static int astolfoColors1(int yOffset, int yTotal) {
        float speed = 2900F;
        float hue = (float) (System.currentTimeMillis() % (int)speed) + ((yTotal - yOffset) * 9);
        while (hue > speed) {
            hue -= speed;
        }
        hue /= speed;
        if (hue > 0.5) {
            hue = 0.5F - (hue - 0.5f);
        }
        hue += 0.5F;
        return Color.HSBtoRGB(hue, 0.5f, 1F);
	}
    
	public static Color astolfoColors(int yOffset, int yTotal) {
        float speed = 2900F;
        float hue = (float) (System.currentTimeMillis() % (int)speed) + ((yTotal - yOffset) * 9);
        while (hue > speed) {
            hue -= speed;
        }
        hue /= speed;
        if (hue > 0.5) {
            hue = 0.5F - (hue - 0.5f);
        }
        hue += 0.5F;
        return new Color(hue, 0.5f, 1F);
	}
	
	public static int fadeColor(double delay) {
		double fadeState = Math.sin(-1) / 5.0D;
		fadeState %= 740.0F;
		int ni;
		ni = Integer.valueOf((int) (180 + Math.sin(System.nanoTime() * 0.0000000035 + delay / 600) * 75));
		float hue = 0;
	    hue = 125F / 255F;
		int c1 = Color.HSBtoRGB(hue, 0.5f, ((float) ni) / 255F);
		return c1;

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

	public static Color fadeColor(Color color, int index, int count) {
		float[] hsb = new float[3];
		Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsb);
		float brightness = Math.abs(
				((float) (System.currentTimeMillis() % 2000L) / 1000.0F + (float) index / (float) count * 2.0F) % 2.0F
						- 1.0F);
		brightness = 0.5F + 0.5F * brightness;
		hsb[2] = brightness % 2.0F;
		return new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
	}
    public static Color getHealthColor(final float health, final float maxHealth) {
    	final float[] fractions = { 0.0f, 0.5f, 1.0f };
        final Color[] colors = { new Color(108, 20, 20), new Color(255, 0, 60), Color.GREEN };
        final float progress = health / maxHealth;
        return blendColors(fractions, colors, progress).brighter();
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
    
    public static Color blendColors(final float[] fractions, final Color[] colors, final float progress) {
        if (fractions.length == colors.length) {
            final int[] indices = getFractionIndices(fractions, progress);
            final float[] range = { fractions[indices[0]], fractions[indices[1]] };
            final Color[] colorRange = { colors[indices[0]], colors[indices[1]] };
            final float max = range[1] - range[0];
            final float value = progress - range[0];
            final float weight = value / max;
            final Color color = blend(colorRange[0], colorRange[1], 1.0f - weight);
            return color;
        }
        throw new IllegalArgumentException("Fractions and colours must have equal number of elements");
    }

}
