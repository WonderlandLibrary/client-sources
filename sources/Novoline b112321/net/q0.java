package net;

import java.util.List;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;

public class q0 {
   public static List a(ItemPotion var0, ItemStack var1) {
      return var0.getEffects(var1);
   }

   public static boolean a(int var0) {
      return ItemPotion.isSplash(var0);
   }

   public static List a(ItemPotion var0, int var1) {
      return var0.getEffects(var1);
   }

   public static int c(ItemPotion var0, int var1) {
      return var0.getColorFromDamage(var1);
   }

   public static boolean b(ItemPotion var0, int var1) {
      return var0.isEffectInstant(var1);
   }
}
