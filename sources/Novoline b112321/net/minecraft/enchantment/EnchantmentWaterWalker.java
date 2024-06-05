package net.minecraft.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.util.ResourceLocation;

public class EnchantmentWaterWalker extends Enchantment {
   public EnchantmentWaterWalker(int var1, ResourceLocation var2, int var3) {
      super(var1, var2, var3, EnumEnchantmentType.ARMOR_FEET);
      this.setName("waterWalker");
   }

   public int getMinEnchantability(int var1) {
      return var1 * 10;
   }

   public int getMaxEnchantability(int var1) {
      return this.getMinEnchantability(var1) + 15;
   }

   public int getMaxLevel() {
      return 3;
   }
}
