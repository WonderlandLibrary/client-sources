package net.minecraft.enchantment;

import java.util.Random;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class EnchantmentDurability extends Enchantment {
   protected EnchantmentDurability(int var1, ResourceLocation var2, int var3) {
      super(var1, var2, var3, EnumEnchantmentType.BREAKABLE);
      this.setName("durability");
   }

   public int getMinEnchantability(int var1) {
      return 5 + (var1 - 1) * 8;
   }

   public int getMaxEnchantability(int var1) {
      return super.getMinEnchantability(var1) + 50;
   }

   public int getMaxLevel() {
      return 3;
   }

   public boolean canApply(ItemStack var1) {
      return var1.isItemStackDamageable() || super.canApply(var1);
   }

   public static boolean negateDamage(ItemStack var0, int var1, Random var2) {
      return (!(var0.getItem() instanceof ItemArmor) || var2.nextFloat() >= 0.6F) && var2.nextInt(var1 + 1) > 0;
   }
}
