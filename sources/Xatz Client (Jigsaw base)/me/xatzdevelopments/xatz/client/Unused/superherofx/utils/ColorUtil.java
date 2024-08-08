package me.xatzdevelopments.xatz.client.Unused.superherofx.utils;

import java.awt.Color;

import me.xatzdevelopments.xatz.client.main.Xatz;

//import me.xatzdevelopments.Xatz;

public class ColorUtil {

	public static int getRainbow(float seconds, float saturation, float brightness) {
		float hue = (System.currentTimeMillis() % (int)(seconds * 1000)) / (float)(seconds * 1000);
		int color = Color.HSBtoRGB(hue, saturation, brightness);
		return color;
	}
	
	public static int getRainbow(float seconds, float saturation, float brightness, long index) {
		float hue = ((System.currentTimeMillis() + index) % (int)(seconds * 1000)) / (float)(seconds * 1000);
		int color = Color.HSBtoRGB(hue, saturation, brightness);
		return color;
	}
	
	public static int getRainbow() {
        final float hue = System.currentTimeMillis() % 10000L / 10000.0f;
        final int RainbowColour = Color.HSBtoRGB(hue, 0.4f, 1.0f);
        return RainbowColour;
    }
    
    public static int getRainbowCustom(final float hueoffset, final float saturation, final float brightness) {
        final float hue = System.currentTimeMillis() % 4500L / 4500.0f;
        final int RainbowColour = Color.HSBtoRGB(hue - hueoffset / 40.0f, saturation, brightness);
        return RainbowColour;
//    }
    
//    public static Color getClickGUIColor(){
//		return new Color((int)Xatz.getModuleByName("ClickGui").getNumberSetting("Red").getValue(), (int)Xatz.getModuleByName("ClickGui").getNumberSetting("Green").getValue(), (int)Xatz.getModuleByName("ClickGui").getNumberSetting("Blue").getValue());
	}
    
    public static Color getHealthColor(float health, float maxHealth) {
        float[] fractions = new float[]{0.0F, 0.5F, 1.0F};
        Color[] colors = new Color[]{new Color(108, 0, 0), new Color(255, 51, 0), Color.GREEN};
        float progress = health / maxHealth;
        return blendColors(fractions, colors, progress).brighter();
     }
    
    public static int createRainbowFromOffset(int speed, int offset) {
        float hue = (System.currentTimeMillis() + (long)offset) % (long)speed;
        return Color.getHSBColor(hue /= (float)speed, 0.6f, 1.0f).getRGB();
    }
    
    public static int rainbowWave(int delay) {
    	double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 12);
    	rainbowState %= 360;
    	return Color.getHSBColor((float) (rainbowState / 370.00f), 0.8f, 1f).getRGB();
    }
    
    public static Color blendColors(float[] fractions, Color[] colors, float progress) {
        if (fractions.length == colors.length) {
           int[] indices = getFractionIndices(fractions, progress);
           float[] range = new float[]{fractions[indices[0]], fractions[indices[1]]};
           Color[] colorRange = new Color[]{colors[indices[0]], colors[indices[1]]};
           float max = range[1] - range[0];
           float value = progress - range[0];
           float weight = value / max;
           Color color = blend(colorRange[0], colorRange[1], (double)(1.0F - weight));
           return color;
        } else {
           throw new IllegalArgumentException("Fractions and colours must have equal number of elements");
        }
     }
    
    public static int[] getFractionIndices(float[] fractions, float progress) {
        int[] range = new int[2];

        int startPoint;
        for(startPoint = 0; startPoint < fractions.length && fractions[startPoint] <= progress; ++startPoint) {
        }

        if (startPoint >= fractions.length) {
           startPoint = fractions.length - 1;
        }

        range[0] = startPoint - 1;
        range[1] = startPoint;
        return range;
     }

     public static Color blend(Color color1, Color color2, double ratio) {
        float r = (float)ratio;
        float ir = 1.0F - r;
        float[] rgb1 = color1.getColorComponents(new float[3]);
        float[] rgb2 = color2.getColorComponents(new float[3]);
        float red = rgb1[0] * r + rgb2[0] * ir;
        float green = rgb1[1] * r + rgb2[1] * ir;
        float blue = rgb1[2] * r + rgb2[2] * ir;
        if (red < 0.0F) {
           red = 0.0F;
        } else if (red > 255.0F) {
           red = 255.0F;
        }

        if (green < 0.0F) {
           green = 0.0F;
        } else if (green > 255.0F) {
           green = 255.0F;
        }

        if (blue < 0.0F) {
           blue = 0.0F;
        } else if (blue > 255.0F) {
           blue = 255.0F;
        }

        Color color3 = null;

        try {
           color3 = new Color(red, green, blue);
        } catch (IllegalArgumentException var13) {
        }

        return color3;
     }
	
}
