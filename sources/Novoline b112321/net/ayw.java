package net;

import net.minecraft.entity.ai.attributes.IAttribute;

public class ayw {
   public static String a(IAttribute var0) {
      return var0.getAttributeUnlocalizedName();
   }

   public static IAttribute c(IAttribute var0) {
      return var0.func_180372_d();
   }

   public static boolean d(IAttribute var0) {
      return var0.getShouldWatch();
   }

   public static double b(IAttribute var0) {
      return var0.getDefaultValue();
   }

   public static double a(IAttribute var0, double var1) {
      return var0.clampValue(var1);
   }
}
