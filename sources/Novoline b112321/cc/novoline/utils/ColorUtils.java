package cc.novoline.utils;

import java.awt.Color;
import net.minecraft.util.MathHelper;

public final class ColorUtils {
   private ColorUtils() {
      throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
   }

   public static int getColor(Color var0) {
      return getColor(var0.getRed(), var0.getGreen(), var0.getBlue(), var0.getAlpha());
   }

   public static int getColor(int var0) {
      return getColor(var0, var0, var0, 255);
   }

   public static int getColor(int var0, int var1) {
      return getColor(var0, var0, var0, var1);
   }

   public static int getColor(int var0, int var1, int var2) {
      return getColor(var0, var1, var2, 255);
   }

   public static int getColor(int var0, int var1, int var2, int var3) {
      int var4 = MathHelper.clamp_int(var3, 0, 255) << 24;
      var4 = var4 | MathHelper.clamp_int(var0, 0, 255) << 16;
      var4 = var4 | MathHelper.clamp_int(var1, 0, 255) << 8;
      var4 = var4 | MathHelper.clamp_int(var2, 0, 255);
      return var4;
   }

   public static Color a(Color var0, double var1) {
      return new Color(Math.max((int)((double)var0.getRed() * var1), 0), Math.max((int)((double)var0.getGreen() * var1), 0), Math.max((int)((double)var0.getBlue() * var1), 0), var0.getAlpha());
   }
}
