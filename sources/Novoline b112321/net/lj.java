package net;

import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.util.IChatComponent;

public class lj {
   public static float a(IBossDisplayData var0) {
      return var0.getHealth();
   }

   public static float b(IBossDisplayData var0) {
      return var0.getMaxHealth();
   }

   public static IChatComponent c(IBossDisplayData var0) {
      return var0.getDisplayName();
   }
}
