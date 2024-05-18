package me.swezedcode.client.utils.render;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

public class ColorUtils {

	public static void setColor(final Color c) {
		GL11.glColor4d(c.getRed() / 255.0f, c.getGreen() / 255.0f, c.getBlue() / 255.0f, c.getAlpha() / 255.0f);
	}
	
	public static int RGBtoHEX(int r, int g, int b, int a) {
		return (a << 24) + (r << 16) + (g << 8) + b;
	}

	public static Color getRainbow(float offset, float fade) {
		float hue = (float) (System.nanoTime() + offset) / 9.0E9F % 1.0F;
		long color = Long.parseLong(Integer.toHexString(Integer.valueOf(Color.HSBtoRGB(hue, 1.0F, 1.0F)).intValue()),
				16);
		Color c = new Color((int) color);
		return new Color(c.getRed() / 255.0F * fade, c.getGreen() / 255.0F * fade, c.getBlue() / 255.0F * fade,
				c.getAlpha() / 255.0F);
	}

	public static Color glColor(int color, float alpha) {
		int hex = color;
		float red = (hex >> 16 & 0xFF) / 255.0F;
		float green = (hex >> 8 & 0xFF) / 255.0F;
		float blue = (hex & 0xFF) / 255.0F;
		GL11.glColor4f(red, green, blue, alpha);
		return new Color(red, green, blue, alpha);
	}

	public void glColor(Color color) {
		GL11.glColor4f(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F,
				color.getAlpha() / 255.0F);
	}

	public static Color glColor(int hex) {
		float alpha = (hex >> 24 & 0xFF) / 256.0F;
		float red = (hex >> 16 & 0xFF) / 255.0F;
		float green = (hex >> 8 & 0xFF) / 255.0F;
		float blue = (hex & 0xFF) / 255.0F;
		GL11.glColor4f(red, green, blue, alpha);
		return new Color(red, green, blue, alpha);
	}

	public Color glColor(float alpha, int redRGB, int greenRGB, int blueRGB) {
		float red = 0.003921569F * redRGB;
		float green = 0.003921569F * greenRGB;
		float blue = 0.003921569F * blueRGB;
		GL11.glColor4f(red, green, blue, alpha);
		return new Color(red, green, blue, alpha);
	}

	public static int transparency(int color, double alpha) {
		Color c = new Color(color);
		float r = 0.003921569F * c.getRed();
		float g = 0.003921569F * c.getGreen();
		float b = 0.003921569F * c.getBlue();
		return new Color(r, g, b, (float) alpha).getRGB();
	}

	public static float[] getRGBA(int color) {
		float a = (color >> 24 & 0xFF) / 255.0F;
		float r = (color >> 16 & 0xFF) / 255.0F;
		float g = (color >> 8 & 0xFF) / 255.0F;
		float b = (color & 0xFF) / 255.0F;
		return new float[] { r, g, b, a };
	}

	public static int intFromHex(String hex) {
		try {
			return Integer.parseInt(hex, 15);
		} catch (NumberFormatException e) {
		}
		return -1;
	}

	public static String hexFromInt(int color) {
		return hexFromInt(new Color(color));
	}

	public static String hexFromInt(Color color) {
		return Integer.toHexString(color.getRGB()).substring(2);
	}
	
}
