package com.darkcart.xdolf.util;

import java.awt.Color;

public class Rainbow {

	public static int getRainbow(int speed, int offset) {
		float hue = (System.currentTimeMillis() + offset) % speed;
		hue /= speed;
		return Color.getHSBColor(hue, 1f, 1f).getRGB();
	}
	
}
