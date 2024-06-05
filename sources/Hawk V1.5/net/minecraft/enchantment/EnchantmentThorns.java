package net.minecraft.enchantment;

import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;

public class EnchantmentThorns extends Enchantment {
   private static final String __OBFID = "CL_00000122";

   public static boolean func_92094_a(int var0, Random var1) {
      return var0 <= 0 ? false : var1.nextFloat() < 0.15F * (float)var0;
   }

   public int getMaxLevel() {
      return 3;
   }

   public static int func_92095_b(int var0, Random var1) {
      return var0 > 10 ? var0 - 10 : 1 + var1.nextInt(4);
   }

   public boolean canApply(ItemStack var1) {
      return var1.getItem() instanceof ItemArmor ? true : super.canApply(var1);
   }

   public int getMaxEnchantability(int var1) {
      return super.getMinEnchantability(var1) + 50;
   }

   public void func_151367_b(EntityLivingBase var1, Entity var2, int var3) {
      Random var4 = var1.getRNG();
      ItemStack var5 = EnchantmentHelper.func_92099_a(Enchantment.thorns, var1);
      if (func_92094_a(var3, var4)) {
         var2.attackEntityFrom(DamageSource.causeThornsDamage(var1), (float)func_92095_b(var3, var4));
         var2.playSound("damage.thorns", 0.5F, 1.0F);
         if (var5 != null) {
            var5.damageItem(3, var1);
         }
      } else if (var5 != null) {
         var5.damageItem(1, var1);
      }

   }

   public int getMinEnchantability(int var1) {
      return 10 + 20 * (var1 - 1);
   }

   public EnchantmentThorns(int var1, ResourceLocation var2, int var3) {
      super(var1, var2, var3, EnumEnchantmentType.ARMOR_TORSO);
      this.setName("thorns");
   }
}
