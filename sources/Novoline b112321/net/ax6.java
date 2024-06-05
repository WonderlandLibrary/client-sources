package net;

import java.util.Random;
import net.minecraft.enchantment.EnchantmentDurability;
import net.minecraft.item.ItemStack;

public class ax6 {
   public static boolean a(ItemStack var0, int var1, Random var2) {
      return EnchantmentDurability.negateDamage(var0, var1, var2);
   }
}
