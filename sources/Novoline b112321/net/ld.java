package net;

import net.minecraft.util.CombatEntry;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IChatComponent;

public class ld {
   public static boolean f(CombatEntry var0) {
      return var0.isLivingDamageSrc();
   }

   public static IChatComponent c(CombatEntry var0) {
      return var0.getDamageSrcDisplayName();
   }

   public static DamageSource a(CombatEntry var0) {
      return var0.getDamageSrc();
   }

   public static float b(CombatEntry var0) {
      return var0.func_94563_c();
   }

   public static float e(CombatEntry var0) {
      return var0.getDamageAmount();
   }

   public static String d(CombatEntry var0) {
      return var0.func_94562_g();
   }
}
