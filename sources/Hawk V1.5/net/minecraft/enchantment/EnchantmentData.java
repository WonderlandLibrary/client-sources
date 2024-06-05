package net.minecraft.enchantment;

import net.minecraft.util.WeightedRandom;

public class EnchantmentData extends WeightedRandom.Item {
   public final int enchantmentLevel;
   private static final String __OBFID = "CL_00000115";
   public final Enchantment enchantmentobj;

   public EnchantmentData(Enchantment var1, int var2) {
      super(var1.getWeight());
      this.enchantmentobj = var1;
      this.enchantmentLevel = var2;
   }
}
