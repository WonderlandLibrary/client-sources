package me.vaziak.sensation.utils.anthony;

import java.awt.*;
import java.util.ArrayList;

/**
 * @author antja03
 */
public class ColorCreator {
    private static final ArrayList<Color> loggedColors = new ArrayList<>();

    public static int create(int r, int g, int b) {
        for (Color color : loggedColors) {
            if (color.getRed() == r && color.getGreen() == g && color.getBlue() == b && color.getAlpha() == 255) {
                return color.getRGB();
            }
        }

        Color color = new Color(r, g, b);
        loggedColors.add(color);
        return color.getRGB();
    }

    public static int create(int r, int g, int b, int a) {
        for (Color color : loggedColors) {
            if (color.getRed() == r && color.getGreen() == g && color.getBlue() == b && color.getAlpha() == a) {
                return color.getRGB();
            }
        }

        Color color = new Color(r, g, b, a);
        loggedColors.add(color);
        return color.getRGB();
    }

    public static int createRainbowFromOffset(int speed, int offset, float sat) {
        float hue = (System.currentTimeMillis() + (long) offset) % (long) speed;
        return Color.getHSBColor(hue /= (float) speed, sat * .1f, 1.0f).getRGB();
    }

	public static int createRainbowFromOffset(int i, int j, float f, float brightness) {
		// TODO Auto-generated method stub
		return 0;
	}

}
