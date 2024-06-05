package net;

import cc.novoline.modules.configurations.property.object.IntProperty;
import cc.novoline.modules.configurations.property.object.ListProperty;
import cc.novoline.modules.player.InvManager;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;

public class aqf {
   public static void e(InvManager var0) {
      var0.u();
   }

   public static IntProperty c(InvManager var0) {
      return var0.v();
   }

   public static IntProperty f(InvManager var0) {
      return var0.getAxeSlot();
   }

   public static IntProperty a(InvManager var0) {
      return var0.getBowSlot();
   }

   public static IntProperty d(InvManager var0) {
      return var0.getHeadSlot();
   }

   public static IntProperty g(InvManager var0) {
      return var0.getShovelSlot();
   }

   public static IntProperty i(InvManager var0) {
      return var0.getPickAxeSlot();
   }

   public static IntProperty h(InvManager var0) {
      return var0.getSwordSlot();
   }

   public static boolean a(InvManager var0, ItemStack var1, ItemPotion var2) {
      return var0.isBadPotionEffect(var1, var2);
   }

   public static ListProperty b(InvManager var0) {
      return var0.getAutoDisable();
   }
}
