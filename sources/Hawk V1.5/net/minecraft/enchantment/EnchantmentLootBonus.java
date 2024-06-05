package net.minecraft.enchantment;

import net.minecraft.util.ResourceLocation;

public class EnchantmentLootBonus extends Enchantment {
   private static final String __OBFID = "CL_00000119";

   public int getMaxEnchantability(int var1) {
      return super.getMinEnchantability(var1) + 50;
   }

   public boolean canApplyTogether(Enchantment var1) {
      return super.canApplyTogether(var1) && var1.effectId != silkTouch.effectId;
   }

   protected EnchantmentLootBonus(int var1, ResourceLocation var2, int var3, EnumEnchantmentType var4) {
      super(var1, var2, var3, var4);
      if (var4 == EnumEnchantmentType.DIGGER) {
         this.setName("lootBonusDigger");
      } else if (var4 == EnumEnchantmentType.FISHING_ROD) {
         this.setName("lootBonusFishing");
      } else {
         this.setName("lootBonus");
      }

   }

   public int getMaxLevel() {
      return 3;
   }

   public int getMinEnchantability(int var1) {
      return 15 + (var1 - 1) * 9;
   }
}
