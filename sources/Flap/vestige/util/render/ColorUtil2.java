package vestige.util.render;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.client.renderer.GlStateManager;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class ColorUtil2 {
   public static final List<AnimationUtils> animation = new ArrayList();
   private static final Pattern COLOR_PATTERN;
   private static final Pattern COLOR_CODE_PATTERN;

   public static Color getBackgroundColor(int id) {
      switch(id) {
      case 1:
         return new Color(16, 15, 69);
      case 2:
         return new Color(19, 19, 128);
      case 3:
         return new Color(255, 255, 255);
      case 4:
         return new Color(0, 0, 0);
      default:
         return new Color(255, 0, 255);
      }
   }

   public static Color getFontColor(int id, int alpha) {
      Color rawColor = getRawFontColor(id);
      int speed = 12;
      if (id == 1) {
         ((AnimationUtils)animation.get(12)).setAnimation((double)rawColor.getRed(), (double)speed);
         ((AnimationUtils)animation.get(13)).setAnimation((double)rawColor.getGreen(), (double)speed);
         ((AnimationUtils)animation.get(14)).setAnimation((double)rawColor.getBlue(), (double)speed);
         return new Color((int)((AnimationUtils)animation.get(12)).getValue(), (int)((AnimationUtils)animation.get(13)).getValue(), (int)((AnimationUtils)animation.get(14)).getValue(), alpha);
      } else if (id == 2) {
         ((AnimationUtils)animation.get(15)).setAnimation((double)rawColor.getRed(), (double)speed);
         ((AnimationUtils)animation.get(16)).setAnimation((double)rawColor.getGreen(), (double)speed);
         ((AnimationUtils)animation.get(17)).setAnimation((double)rawColor.getBlue(), (double)speed);
         return new Color((int)((AnimationUtils)animation.get(15)).getValue(), (int)((AnimationUtils)animation.get(16)).getValue(), (int)((AnimationUtils)animation.get(17)).getValue(), alpha);
      } else {
         return rawColor;
      }
   }

   private static Color getRawFontColor(int id) {
      switch(id) {
      case 1:
         return new Color(255, 255, 255);
      case 2:
         return new Color(173, 173, 173);
      default:
         return new Color(255, 0, 0);
      }
   }

   public static Color getFontColor(int id) {
      return getFontColor(id, 255);
   }

   public static void setColor(int color, double alpha) {
      float r = (float)(color >> 16 & 255) / 255.0F;
      float g = (float)(color >> 8 & 255) / 255.0F;
      float b = (float)(color & 255) / 255.0F;
      GlStateManager.color(r, g, b, (float)alpha);
   }

   public static void setColor(int color) {
      setColor(color, (double)((float)(color >> 24 & 255) / 255.0F));
   }

   public static void resetColor() {
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
   }

   public static String stripColor(String input) {
      return COLOR_PATTERN.matcher(input).replaceAll("");
   }

   public static Color getColorFromCode(String input) {
      Matcher matcher = COLOR_CODE_PATTERN.matcher(input);
      if (matcher.find()) {
         char code = matcher.group(1).charAt(0);
         switch(code) {
         case '0':
            return new Color(0, 0, 0);
         case '1':
            return new Color(0, 0, 170);
         case '2':
            return new Color(0, 170, 0);
         case '3':
            return new Color(0, 170, 170);
         case '4':
            return new Color(170, 0, 0);
         case '5':
            return new Color(170, 0, 170);
         case '6':
            return new Color(255, 170, 0);
         case '7':
            return new Color(170, 170, 170);
         case '8':
            return new Color(85, 85, 85);
         case '9':
            return new Color(85, 85, 255);
         case ':':
         case ';':
         case '<':
         case '=':
         case '>':
         case '?':
         case '@':
         case 'A':
         case 'B':
         case 'C':
         case 'D':
         case 'E':
         case 'F':
         case 'G':
         case 'H':
         case 'I':
         case 'J':
         case 'K':
         case 'L':
         case 'M':
         case 'N':
         case 'O':
         case 'P':
         case 'Q':
         case 'R':
         case 'S':
         case 'T':
         case 'U':
         case 'V':
         case 'W':
         case 'X':
         case 'Y':
         case 'Z':
         case '[':
         case '\\':
         case ']':
         case '^':
         case '_':
         case '`':
         default:
            return new Color(255, 255, 255);
         case 'a':
            return new Color(85, 255, 85);
         case 'b':
            return new Color(85, 255, 255);
         case 'c':
            return new Color(255, 85, 85);
         case 'd':
            return new Color(255, 85, 255);
         case 'e':
            return new Color(255, 255, 85);
         case 'f':
            return new Color(255, 255, 255);
         }
      } else {
         return new Color(255, 255, 255);
      }
   }

   @Contract("_ -> new")
   @NotNull
   public static Color colorFromInt(int color) {
      Color c = new Color(color);
      return new Color(c.getRed(), c.getGreen(), c.getBlue(), 255);
   }

   @Contract("_, _ -> new")
   @NotNull
   public static Color brighter(@NotNull Color color, float FACTOR) {
      if (color == null) {
         $$$reportNull$$$0(0);
      }

      int r = color.getRed();
      int g = color.getGreen();
      int b = color.getBlue();
      int alpha = color.getAlpha();
      int i = (int)(1.0D / (1.0D - (double)FACTOR));
      if (r == 0 && g == 0 && b == 0) {
         return new Color(i, i, i, alpha);
      } else {
         if (r > 0 && r < i) {
            r = i;
         }

         if (g > 0 && g < i) {
            g = i;
         }

         if (b > 0 && b < i) {
            b = i;
         }

         return new Color(Math.min((int)((float)r / FACTOR), 255), Math.min((int)((float)g / FACTOR), 255), Math.min((int)((float)b / FACTOR), 255), alpha);
      }
   }

   @Contract("_, _, _ -> new")
   @NotNull
   public static Color interpolateColorC(@NotNull Color color1, @NotNull Color color2, float amount) {
      if (color1 == null) {
         $$$reportNull$$$0(1);
      }

      if (color2 == null) {
         $$$reportNull$$$0(2);
      }

      amount = Math.min(1.0F, Math.max(0.0F, amount));
      return new Color(interpolateInt(color1.getRed(), color2.getRed(), (double)amount), interpolateInt(color1.getGreen(), color2.getGreen(), (double)amount), interpolateInt(color1.getBlue(), color2.getBlue(), (double)amount), interpolateInt(color1.getAlpha(), color2.getAlpha(), (double)amount));
   }

   public static int interpolateInt(int oldValue, int newValue, double interpolationValue) {
      return Double.valueOf((double)oldValue + ((double)newValue - (double)oldValue) * (double)((float)interpolationValue)).intValue();
   }

   @Contract("_, _, _ -> new")
   @NotNull
   public static Color blend(@NotNull Color color1, @NotNull Color color2, double ratio) {
      if (color1 == null) {
         $$$reportNull$$$0(3);
      }

      if (color2 == null) {
         $$$reportNull$$$0(4);
      }

      float r = (float)ratio;
      float ir = 1.0F - r;
      float[] rgb1 = new float[3];
      float[] rgb2 = new float[3];
      color1.getColorComponents(rgb1);
      color2.getColorComponents(rgb2);
      return new Color(rgb1[0] * r + rgb2[0] * ir, rgb1[1] * r + rgb2[1] * ir, rgb1[2] * r + rgb2[2] * ir);
   }

   @Contract("_, _ -> new")
   @NotNull
   public static Color blend(Color color1, Color color2) {
      return blend(color1, color2, 0.5D);
   }

   @Contract("_, _ -> new")
   @NotNull
   public static Color blend(int color1, int color2) {
      return blend(colorFromInt(color1), colorFromInt(color2), 0.5D);
   }

   @NotNull
   public static Color reAlpha(@NotNull Color color, float alpha) {
      if (color == null) {
         $$$reportNull$$$0(5);
      }

      float r = 0.003921569F * (float)color.getRed();
      float g = 0.003921569F * (float)color.getGreen();
      float b = 0.003921569F * (float)color.getBlue();
      return new Color(r, g, b, alpha);
   }

   public static float calculateGaussianValue(float x, float sigma) {
      double output = 1.0D / Math.sqrt(6.283185307179586D * (double)(sigma * sigma));
      return (float)(output * Math.exp((double)(-(x * x)) / (2.0D * (double)(sigma * sigma))));
   }

   static {
      for(int i = 0; i < 18; ++i) {
         animation.add(new AnimationUtils(0.0D));
      }

      COLOR_PATTERN = Pattern.compile("(?i)§[0-9A-FK-OR]");
      COLOR_CODE_PATTERN = Pattern.compile("(?i)§([0-9A-FK-OR])");
   }

   // $FF: synthetic method
   private static void $$$reportNull$$$0(int var0) {
      Object[] var10001 = new Object[3];
      switch(var0) {
      case 0:
      case 5:
      default:
         var10001[0] = "color";
         break;
      case 1:
      case 3:
         var10001[0] = "color1";
         break;
      case 2:
      case 4:
         var10001[0] = "color2";
      }

      var10001[1] = "vestige/util/render/ColorUtil2";
      switch(var0) {
      case 0:
      default:
         var10001[2] = "brighter";
         break;
      case 1:
      case 2:
         var10001[2] = "interpolateColorC";
         break;
      case 3:
      case 4:
         var10001[2] = "blend";
         break;
      case 5:
         var10001[2] = "reAlpha";
      }

      throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", var10001));
   }
}
