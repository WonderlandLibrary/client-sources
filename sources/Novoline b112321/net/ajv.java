package net;

import net.minecraft.stats.Achievement;
import net.minecraft.stats.IStatStringFormat;
import net.minecraft.util.IChatComponent;

public class ajv {
   public static Achievement a(Achievement var0, IStatStringFormat var1) {
      return var0.setStatStringFormatter(var1);
   }

   public static IChatComponent e(Achievement var0) {
      return var0.getStatName();
   }

   public static String b(Achievement var0) {
      return var0.getDescription();
   }

   public static Achievement d(Achievement var0) {
      return var0.initIndependentStat();
   }

   public static Achievement a(Achievement var0) {
      return var0.registerStat();
   }

   public static Achievement f(Achievement var0) {
      return var0.setSpecial();
   }

   public static Achievement a(Achievement var0, Class var1) {
      return var0.func_150953_b(var1);
   }

   public static boolean c(Achievement var0) {
      return var0.getSpecial();
   }
}
