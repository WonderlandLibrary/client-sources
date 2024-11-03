package vestige.util.render;

import java.awt.Color;

public class ColorUtil {
   public static final int buttonHoveredColor = (new Color(18, 10, 168)).getRGB();

   public static int getColor(Color color1, Color color2, long ms, int offset) {
      double scale = (double)((System.currentTimeMillis() + (long)offset) % ms) / (double)ms * 2.0D;
      double finalScale = scale > 1.0D ? 2.0D - scale : scale;
      return getGradient(color1, color2, finalScale).getRGB();
   }

   public static Color getColorinColor(Color color1, Color color2, Color color3, long ms, int offset) {
      double scale = (double)((System.currentTimeMillis() + (long)offset) % ms) / (double)ms * 3.0D;
      if (scale > 2.0D) {
         return getGradient(color3, color1, scale - 2.0D);
      } else {
         return scale > 1.0D ? getGradient(color2, color3, scale - 1.0D) : getGradient(color1, color2, scale);
      }
   }

   public static Color blendColors(float[] fractions, Color[] colors, float progress) {
      if (fractions == null) {
         throw new IllegalArgumentException("Fractions can't be null");
      } else if (colors == null) {
         throw new IllegalArgumentException("Colours can't be null");
      } else if (fractions.length == colors.length) {
         int[] getFractionBlack = getFraction(fractions, progress);
         float[] range = new float[]{fractions[getFractionBlack[0]], fractions[getFractionBlack[1]]};
         Color[] colorRange = new Color[]{colors[getFractionBlack[0]], colors[getFractionBlack[1]]};
         float max = range[1] - range[0];
         float value = progress - range[0];
         float weight = value / max;
         return ColorUtil2.blend(colorRange[0], colorRange[1], (double)(1.0F - weight));
      } else {
         throw new IllegalArgumentException("Fractions and colours must have equal number of elements");
      }
   }

   public static int[] getFraction(float[] fractions, float progress) {
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

   public static Color getColorinColor(Color color1, Color color2, long ms, int offset) {
      double scale = (double)((System.currentTimeMillis() + (long)offset) % ms) / (double)ms * 2.0D;
      double finalScale = scale > 1.0D ? 2.0D - scale : scale;
      return getGradient(color1, color2, finalScale);
   }

   public static int getColor(Color color1, Color color2, Color color3, long ms, int offset) {
      double scale = (double)((System.currentTimeMillis() + (long)offset) % ms) / (double)ms * 3.0D;
      if (scale > 2.0D) {
         return getGradient(color3, color1, scale - 2.0D).getRGB();
      } else {
         return scale > 1.0D ? getGradient(color2, color3, scale - 1.0D).getRGB() : getGradient(color1, color2, scale).getRGB();
      }
   }

   public static int rgbaToNegativeInt(int red, int green, int blue, int alpha) {
      int color = (alpha & 255) << 24 | (red & 255) << 16 | (green & 255) << 8 | blue & 255;
      if (color >= 0) {
         color |= Integer.MIN_VALUE;
      }

      return color;
   }

   public static Color getGradient(Color color1, Color color2, double scale) {
      scale = Math.max(0.0D, Math.min(1.0D, scale));
      return new Color((int)((double)color1.getRed() + (double)(color2.getRed() - color1.getRed()) * scale), (int)((double)color1.getGreen() + (double)(color2.getGreen() - color1.getGreen()) * scale), (int)((double)color1.getBlue() + (double)(color2.getBlue() - color1.getBlue()) * scale));
   }

   public static int getRainbow(long ms, int offset, float saturation, float brightness) {
      float scale = (float)((System.currentTimeMillis() + (long)offset) % ms) / (float)ms;
      return Color.HSBtoRGB(scale, saturation / 1.3F, brightness + 0.05F);
   }

   public static Color getRainboww(long ms, int offset, float saturation, float brightness) {
      float scale = (float)((System.currentTimeMillis() + (long)offset) % ms) / (float)ms;
      int rgb = Color.HSBtoRGB(scale, saturation / 1.3F, brightness + 0.05F);
      return new Color(rgb);
   }
}
