package net;

import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.Item;

public class Al {
   public static boolean a(EnumEnchantmentType var0, Item var1) {
      return var0.canEnchantItem(var1);
   }
}
