package net;

import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor$ArmorMaterial;

public class _k {
   public static String a(ItemArmor$ArmorMaterial var0) {
      return var0.getName();
   }

   public static int b(ItemArmor$ArmorMaterial var0, int var1) {
      return var0.getDamageReductionAmount(var1);
   }

   public static int a(ItemArmor$ArmorMaterial var0, int var1) {
      return var0.getDurability(var1);
   }

   public static int c(ItemArmor$ArmorMaterial var0) {
      return var0.getEnchantability();
   }

   public static Item b(ItemArmor$ArmorMaterial var0) {
      return var0.getRepairItem();
   }
}
