package net.minecraft.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.util.ResourceLocation;

public class EnchantmentOxygen extends Enchantment {
   public EnchantmentOxygen(int var1, ResourceLocation var2, int var3) {
      super(var1, var2, var3, EnumEnchantmentType.ARMOR_HEAD);
      this.setName("oxygen");
   }

   public int getMinEnchantability(int var1) {
      return 10 * var1;
   }

   public int getMaxEnchantability(int var1) {
      return this.getMinEnchantability(var1) + 30;
   }

   public int getMaxLevel() {
      return 3;
   }
}
