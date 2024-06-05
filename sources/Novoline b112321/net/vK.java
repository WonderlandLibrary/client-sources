package net;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.CombatTracker;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IChatComponent;

public class vK {
   public static void a(CombatTracker var0) {
      var0.reset();
   }

   public static void a(CombatTracker var0, DamageSource var1, float var2, float var3) {
      var0.trackDamage(var1, var2, var3);
   }

   public static EntityLivingBase e(CombatTracker var0) {
      return var0.func_94550_c();
   }

   public static int c(CombatTracker var0) {
      return var0.func_180134_f();
   }

   public static EntityLivingBase b(CombatTracker var0) {
      return var0.getFighter();
   }

   public static IChatComponent d(CombatTracker var0) {
      return var0.getDeathMessage();
   }
}
