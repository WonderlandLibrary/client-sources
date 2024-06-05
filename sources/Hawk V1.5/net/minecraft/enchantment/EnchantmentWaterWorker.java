package net.minecraft.enchantment;

import net.minecraft.util.ResourceLocation;

public class EnchantmentWaterWorker extends Enchantment {
   private static final String __OBFID = "CL_00000124";

   public int getMaxLevel() {
      return 1;
   }

   public EnchantmentWaterWorker(int var1, ResourceLocation var2, int var3) {
      super(var1, var2, var3, EnumEnchantmentType.ARMOR_HEAD);
      this.setName("waterWorker");
   }

   public int getMaxEnchantability(int var1) {
      return this.getMinEnchantability(var1) + 40;
   }

   public int getMinEnchantability(int var1) {
      return 1;
   }
}
