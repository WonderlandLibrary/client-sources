package net.minecraft.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class EnchantmentDigging extends Enchantment {
   protected EnchantmentDigging(int var1, ResourceLocation var2, int var3) {
      super(var1, var2, var3, EnumEnchantmentType.DIGGER);
      this.setName("digging");
   }

   public int getMinEnchantability(int var1) {
      return 1 + 10 * (var1 - 1);
   }

   public int getMaxEnchantability(int var1) {
      return super.getMinEnchantability(var1) + 50;
   }

   public int getMaxLevel() {
      return 5;
   }

   public boolean canApply(ItemStack var1) {
      return var1.getItem() == Items.shears || super.canApply(var1);
   }
}
