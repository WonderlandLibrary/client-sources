package me.rich.helpers.render;

import java.awt.Color;
import java.text.NumberFormat;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

public class ColorHelper {
	public static int getColor(Color color) {
		return ColorHelper.getColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
	}

	public static Color getColorWithOpacity(Color color, int alpha) {
		return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
	}

	public static Color fade(Color color) {
		return ColorHelper.fade(color, 2, 100);
	}

	public static int color(int n, int n2, int n3, int n4) {
		n4 = 255;
		return new Color(n, n2, n3, n4).getRGB();
	}

	public static int getColor1(Color color) {
		return getColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
	}


    public static int rainbow(int delay, float saturation, float brightness) {
        double rainbow = Math.ceil((System.currentTimeMillis() + delay) / 16);
        rainbow %= 360.0D;
        return Color.getHSBColor((float) (rainbow / 360.0D), saturation, brightness).getRGB();
    }
    
    public static Color getRainbow(final int offset, final int speed) {
        float hue = (System.currentTimeMillis() + offset) % speed;
        hue /= speed;
        return Color.getHSBColor(hue, 0.7f, 1f);
    }
    
    public static Color rainbow2(int delay, float saturation, float brightness) {
        double rainbow = Math.ceil((System.currentTimeMillis() + delay) / 16);
        rainbow %= 360.0D;
        return Color.getHSBColor((float) (rainbow / 360.0D), saturation, brightness);
    }
	
    public static Color getHealthColor(EntityLivingBase entityLivingBase) {
        float health = entityLivingBase.getHealth();
        float[] fractions = new float[]{0.0f, 0.15f, 0.55f, 0.7f, 0.9f};
        Color[] colors = new Color[]{new Color(133, 0, 0), Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN};
        float progress = health / entityLivingBase.getMaxHealth();
        return health >= 0.0f ? ColorHelper.blendColors(fractions, colors, progress).brighter() : colors[0];
    }
	public static int getColor2(int brightness) {
		return getColor(brightness, brightness, brightness, 255);
	}

	public static int getColor3(int brightness, int alpha) {
		return getColor(brightness, brightness, brightness, alpha);
	}

	public static int getColor4(int red, int green, int blue) {
		return getColor(red, green, blue, 255);
	}
	
	public static int getColor5(int red, int green, int blue, int alpha) {
		int color = 0;
		color |= alpha << 24;
		color |= red << 16;
		color |= green << 8;
		color |= blue;
		return color;
	}

	public static int getRandomColor() {
		char[] letters = "012345678".toCharArray();
		String color = "0x";
		for (int i = 0; i < 6; ++i) {
			color = color + letters[new Random().nextInt(letters.length)];
		}
		return Integer.decode(color);
	}

	public static int reAlpha(int color, float alpha) {
		Color c = new Color(color);
		float r = 0.003921569f * (float) c.getRed();
		float g = 0.003921569f * (float) c.getGreen();
		float b = 0.003921569f * (float) c.getBlue();
		return new Color(r, g, b, alpha).getRGB();
	}

	public static Color getGradientOffset(Color color1, Color color2, double offset) {
		if (offset > 1.0) {
			double left = offset % 1.0;
			int off = (int) offset;
			offset = off % 2 == 0 ? left : 1.0 - left;
		}
		double inverse_percent = 1.0 - offset;
		int redPart = (int) ((double) color1.getRed() * inverse_percent + (double) color2.getRed() * offset);
		int greenPart = (int) ((double) color1.getGreen() * inverse_percent + (double) color2.getGreen() * offset);
		int bluePart = (int) ((double) color1.getBlue() * inverse_percent + (double) color2.getBlue() * offset);
		return new Color(redPart, greenPart, bluePart);
	}
	
	 public static class Colors
	    {
	        public static final int WHITE;
	        public static final int BLACK;
	        public static final int RED;
	        public static final int GREEN;
	        public static final int BLUE;
	        public static final int ORANGE;
	        public static final int PURPLE;
	        public static final int GRAY;
	        public static final int DARK_RED;
	        public static final int YELLOW;
	        public static final int RAINBOW = Integer.MIN_VALUE;
	        
	        static {
	            WHITE = ColorHelper.toRGBA(255, 255, 255, 255);
	            BLACK = ColorHelper.toRGBA(0, 0, 0, 255);
	            RED = ColorHelper.toRGBA(255, 0, 0, 255);
	            GREEN = ColorHelper.toRGBA(0, 255, 0, 255);
	            BLUE = ColorHelper.toRGBA(0, 0, 255, 255);
	            ORANGE = ColorHelper.toRGBA(255, 128, 0, 255);
	            PURPLE = ColorHelper.toRGBA(163, 73, 163, 255);
	            GRAY = ColorHelper.toRGBA(127, 127, 127, 255);
	            DARK_RED = ColorHelper.toRGBA(64, 0, 0, 255);
	            YELLOW = ColorHelper.toRGBA(255, 255, 0, 255);
	        }
	    }
	    public static int toRGBA(final int r, final int g, final int b, final int a) {
	        return (r << 16) + (g << 8) + (b << 0) + (a << 24);
	    }
	    
	    public static int toRGBA(final float r, final float g, final float b, final float a) {
	        return toRGBA((int)(r * 255.0f), (int)(g * 255.0f), (int)(b * 255.0f), (int)(a * 255.0f));
	    }
	    
	    public static int toRGBA(final float[] colors) {
	        if (colors.length != 4) {
	            throw new IllegalArgumentException("colors[] must have a length of 4!");
	        }
	        return toRGBA(colors[0], colors[1], colors[2], colors[3]);
	    }
	    
	    public static int toRGBA(final double[] colors) {
	        if (colors.length != 4) {
	            throw new IllegalArgumentException("colors[] must have a length of 4!");
	        }
	        return toRGBA((float)colors[0], (float)colors[1], (float)colors[2], (float)colors[3]);
	    }

	public static int getColor1(int brightness) {
		return ColorHelper.getColor(brightness, brightness, brightness, 255);
	}

	public static int getColor(int red, int green, int blue) {
		return ColorHelper.getColor(red, green, blue, 255);
	}

	public static int getColor(int red, int green, int blue, int alpha) {
		int color = 0;
		color |= alpha << 24;
		color |= red << 16;
		color |= green << 8;
		return color |= blue;
	}

	public static int getColor(int brightness) {
		return ColorHelper.getColor(brightness, brightness, brightness, 255);
	}

	public static int getColor(int brightness, int alpha) {
		return ColorHelper.getColor(brightness, brightness, brightness, alpha);
	}

	public static Color fade(Color color, int index, int count) {
		float[] hsb = new float[3];
		Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsb);
		float brightness = Math.abs(
				((float) (System.currentTimeMillis() % 2000L) / 1000.0f + (float) index / (float) count * 2.0f) % 2.0f
						- 1.0f);
		brightness = 0.5f + 0.5f * brightness;
		hsb[2] = brightness % 2.0f;
		return new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
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
		int[] indicies = ColorHelper.getFractionIndicies(fractions, progress);
		float[] range = new float[] { fractions[indicies[0]], fractions[indicies[1]] };
		Color[] colorRange = new Color[] { colors[indicies[0]], colors[indicies[1]] };
		float max = range[1] - range[0];
		float value = progress - range[0];
		float weight = value / max;
		return ColorHelper.blend(colorRange[0], colorRange[1], 1.0f - weight);
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
		float r = (float) ratio;
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
		float hue;
		float speed = 3000.0f;
		for (hue = Math.abs((float) (System.currentTimeMillis() % (long) delay)
				+ -offset / 21.0f * 2.0f); hue > speed; hue -= speed) {
		}
		if ((double) (hue /= speed) > 0.5) {
			hue = 0.5f - (hue - 0.5f);
		}
		return Color.HSBtoRGB(hue += 0.5f, 0.5f, 1.0f);
	}

	public static int Yellowastolfo(int delay, float offset) {
		float hue;
		float speed = 2900.0f;
		for (hue = (float) (System.currentTimeMillis() % (long) ((int) speed))
				+ ((float) (-delay) - offset) * 9.0f; hue > speed; hue -= speed) {
		}
		if ((double) (hue /= speed) > 0.6) {
			hue = 0.6f - (hue - 0.6f);
		}
		return Color.HSBtoRGB(hue += 0.6f, 0.5f, 1.0f);
	}

	public static Color Yellowastolfo1(int delay, float offset) {
		float hue;
		float speed = 2900.0f;
		for (hue = (float) (System.currentTimeMillis() % (long) ((int) speed))
				+ ((float) delay - offset) * 9.0f; hue > speed; hue -= speed) {
		}
		if ((double) (hue /= speed) > 0.6) {
			hue = 0.6f - (hue - 0.6f);
		}
		return new Color(hue += 0.6f, 0.5f, 1.0f);
	}

	public static Color TwoColoreffect(Color cl1, Color cl2, double speed) {
		double thing = speed / 4.0 % 1.0;
		float val = MathHelper.clamp((float) Math.sin(Math.PI * 6 * thing) / 2.0f + 0.5f, 0.0f, 1.0f);
		return new Color(lerp((float) cl1.getRed() / 255.0f, (float) cl2.getRed() / 255.0f, val),
				lerp((float) cl1.getGreen() / 255.0f, (float) cl2.getGreen() / 255.0f, val),
				lerp((float) cl1.getBlue() / 255.0f, (float) cl2.getBlue() / 255.0f, val));
	}

	public static int astolfoColors(int yOffset, int yTotal) {
		float hue;
		float speed = 2900.0f;
		for (hue = (float) (System.currentTimeMillis() % (long) ((int) speed))
				+ (float) ((yTotal - yOffset) * 9); hue > speed; hue -= speed) {
		}
		if ((double) (hue /= speed) > 0.5) {
			hue = 0.5f - (hue - 0.5f);
		}
		return Color.HSBtoRGB(hue += 0.5f, 0.5f, 1.0f);
	}
	public static Color astolfoColor(int yOffset, int yTotal) {
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

	public static int getTeamColor(Entity entityIn) {
		int i = -1;
		i = entityIn.getDisplayName().getUnformattedText().equalsIgnoreCase(
				"\u043f\u0457\u0405f[\u043f\u0457\u0405cR\u043f\u0457\u0405f]\u043f\u0457\u0405c" + entityIn.getName())
						? ColorHelper.getColor(new Color(255, 60, 60))
						: (entityIn.getDisplayName().getUnformattedText().equalsIgnoreCase(
								"\u043f\u0457\u0405f[\u043f\u0457\u04059B\u043f\u0457\u0405f]\u043f\u0457\u04059"
										+ entityIn.getName())
												? ColorHelper.getColor(new Color(60, 60, 255))
												: (entityIn.getDisplayName().getUnformattedText().equalsIgnoreCase(
														"\u043f\u0457\u0405f[\u043f\u0457\u0405eY\u043f\u0457\u0405f]\u043f\u0457\u0405e"
																+ entityIn.getName())
																		? ColorHelper.getColor(new Color(255, 255, 60))
																		: (entityIn.getDisplayName()
																				.getUnformattedText().equalsIgnoreCase(
																						"\u043f\u0457\u0405f[\u043f\u0457\u0405aG\u043f\u0457\u0405f]\u043f\u0457\u0405a"
																								+ entityIn.getName())
																										? ColorHelper
																												.getColor(
																														new Color(
																																60,
																																255,
																																60))
																										: ColorHelper
																												.getColor(
																														new Color(
																																255,
																																255,
																																255)))));
		return i;
	}

	public static int astolfoColors1(float yDist, float yTotal) {
		float speed = 3500f;
		float hue = (System.currentTimeMillis() % (int) speed) + (yTotal - yDist) * 12;
		while (hue > speed) {
			hue -= speed;
		}
		hue /= speed;
		if (hue > 0.5) {
			hue = 0.5F - (hue - 0.5f);
		}
		hue += 0.5F;
		return Color.HSBtoRGB(hue, 0.4f, 1F);
	}
	public static int astolfoColors4(float yDist, float yTotal, float saturation) {
		float speed = 1800f;
		float hue = (System.currentTimeMillis() % (int) speed) + (yTotal - yDist) * 12;
		while (hue > speed) {
			hue -= speed;
		}
		hue /= speed;
		if (hue > 0.5) {
			hue = 0.5F - (hue - 0.5f);
		}
		hue += 0.5F;
		return Color.HSBtoRGB(hue, saturation, 1F);
	}
	
	public static Color astolfoColors45(float yDist, float yTotal, float saturation) {
		float speed = 1800f;
		float hue = (System.currentTimeMillis() % (int) speed) + (yTotal - yDist) * 12;
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
	
	public static int astolfoColors2(float yDist, float yTotal) {
		float speed = 3500f;
		float hue = (System.currentTimeMillis() % (int) speed) + (yTotal - yDist) * 12;
		while (hue > speed) {
			hue -= speed;
		}
		hue /= speed;
		if (hue > 0.5) {
			hue = 0.5F - (hue - 0.5f);
		}
		hue += 0.5F;
		return Color.HSBtoRGB(hue, 0.5f, 0.75F);
	}
	
    public static Color astolfoColors1(int yOffset, int yTotal) {
        float hue;
        float speed = 2900.0f;
        for (hue = (float)(System.currentTimeMillis() % (long)((int)speed)) + (float)((yTotal - yOffset) * 9); hue > speed; hue -= speed) {
        }
        if ((double)(hue /= speed) > 0.5) {
            hue = 0.5f - (hue - 0.5f);
        }
        return new Color(hue += 0.5f, 0.5f, 1.0f);
    }
	
	public static int astolfoColors3(float yDist, float yTotal) {
		float speed = 3500f;
		float hue = (System.currentTimeMillis() % (int) speed) + (yTotal - yDist) * 12;
		while (hue > speed) {
			hue -= speed;
		}
		hue /= speed;
		if (hue > 0.5) {
			hue = 0.5F - (hue - 0.5f);
		}
		hue += 0.5F;
		return Color.HSBtoRGB(hue, 0.2f, 1F);
	}
	
    public static float lerp(float a, float b, float f) {
        return a + f * (b - a);
    }
}