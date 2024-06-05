package net.minecraft.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper$1;
import net.minecraft.enchantment.EnchantmentHelper$IModifier;
import net.minecraft.entity.EnumCreatureAttribute;

final class EnchantmentHelper$ModifierLiving implements EnchantmentHelper$IModifier {
   public float livingModifier;
   public EnumCreatureAttribute entityLiving;

   private EnchantmentHelper$ModifierLiving() {
   }

   public void calculateModifier(Enchantment var1, int var2) {
      this.livingModifier += var1.calcDamageByCreature(var2, this.entityLiving);
   }

   EnchantmentHelper$ModifierLiving(EnchantmentHelper$1 var1) {
      this();
   }
}
