package us.loki.legit.utils;

import java.awt.Color;

public class ColorUtils {

	public static Color rainbowEffekt(long offset, float fade) {
		float hue = (float) (System.nanoTime() + offset) / 1.0E10f % 1.0f;
		long color = Long.parseLong(Integer.toHexString(Color.HSBtoRGB(hue, 1.0f, 1.0f)), 16);
		Color c = new Color((int) color);
		return new Color((float) c.getRed() / 255.0f * fade, (float) c.getGreen() / 255.0f * fade,
				(float) c.getBlue() / 255.0f * fade, (float) c.getAlpha() / 255.0f);
	}

}
