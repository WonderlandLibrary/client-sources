package xyz.cucumber.base.utils.render;

import java.awt.Color;
import xyz.cucumber.base.module.settings.ColorSettings;

public class ColorUtils {
   public static int mix(int c1, int c2, double size, double max) {
      int f3 = c1 >> 24 & 0xFF;
      int f4 = c1 >> 24 & 0xFF;
      Color col1 = new Color(c1);
      Color col2 = new Color(c2);
      int diffR = (int)((double)col1.getRed() - (double)(col1.getRed() - col2.getRed()) / max * size);
      int diffG = (int)((double)col1.getGreen() - (double)(col1.getGreen() - col2.getGreen()) / max * size);
      int diffB = (int)((double)col1.getBlue() - (double)(col1.getBlue() - col2.getBlue()) / max * size);
      if (diffR > 255) {
         diffR = 255;
      }

      if (diffR < 0) {
         diffR = 0;
      }

      if (diffG > 255) {
         diffG = 255;
      }

      if (diffG < 0) {
         diffG = 0;
      }

      if (diffB > 255) {
         diffB = 255;
      }

      if (diffB < 0) {
         diffB = 0;
      }

      return new Color(diffR, diffG, diffB).getRGB();
   }

   public static int skyRainbow(double offset, float time, double speed) {
      return Color.HSBtoRGB((float)((int)((double)time / speed + offset)) % 360.0F / 360.0F, 0.5F, 1.0F);
   }

   public static int rainbow(double speed, double offset) {
      return Color.HSBtoRGB((float)((double)(System.nanoTime() / 1000000L) / speed + offset) % 360.0F / 360.0F, 1.0F, 1.0F);
   }

   public static int rainbow(double speed, double offset, double milis) {
      return Color.HSBtoRGB((float)(milis / speed + offset) % 360.0F / 360.0F, 1.0F, 1.0F);
   }

   public static int getAlphaColor(int color, int alpha) {
      int newAlpha = 255 * alpha / 100;
      return new Color(new Color(color).getRed(), new Color(color).getGreen(), new Color(color).getBlue(), newAlpha).getRGB();
   }

   public static int getColor(ColorSettings color, double milis, double offset, double speed) {
      int c = getAlphaColor(color.getMainColor(), color.getAlpha());
      String var8;
      switch ((var8 = color.getMode().toLowerCase()).hashCode()) {
         case 108124:
            if (var8.equals("mix")) {
               c = getAlphaColor(
                  mix(color.getMainColor(), color.getSecondaryColor(), 1.0 + Math.cos(Math.toRadians(milis / speed + offset)), 2.0), color.getAlpha()
               );
            }
            break;
         case 113953:
            if (var8.equals("sky")) {
               c = getAlphaColor(skyRainbow(offset, (float)milis, speed), color.getAlpha());
            }
            break;
         case 973576630:
            if (var8.equals("rainbow")) {
               c = getAlphaColor(rainbow(speed, offset, milis), color.getAlpha());
            }
      }

      return c;
   }
}
