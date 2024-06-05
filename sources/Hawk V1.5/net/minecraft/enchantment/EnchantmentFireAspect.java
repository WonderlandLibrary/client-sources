package net.minecraft.enchantment;

import net.minecraft.util.ResourceLocation;

public class EnchantmentFireAspect extends Enchantment {
   private static final String __OBFID = "CL_00000116";

   protected EnchantmentFireAspect(int var1, ResourceLocation var2, int var3) {
      super(var1, var2, var3, EnumEnchantmentType.WEAPON);
      this.setName("fire");
   }

   public int getMaxLevel() {
      return 2;
   }

   public int getMaxEnchantability(int var1) {
      return super.getMinEnchantability(var1) + 50;
   }

   public int getMinEnchantability(int var1) {
      return 10 + 20 * (var1 - 1);
   }
}
