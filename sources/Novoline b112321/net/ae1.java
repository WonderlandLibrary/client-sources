package net;

import net.minecraft.util.EnchantmentNameParts;

public class ae1 {
   public static EnchantmentNameParts a() {
      return EnchantmentNameParts.getInstance();
   }

   public static void a(EnchantmentNameParts var0, long var1) {
      var0.reseedRandomGenerator(var1);
   }

   public static String a(EnchantmentNameParts var0) {
      return var0.generateNewRandomName();
   }
}
