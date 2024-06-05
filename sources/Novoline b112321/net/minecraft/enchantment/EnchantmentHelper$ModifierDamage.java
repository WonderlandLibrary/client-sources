package net.minecraft.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper$1;
import net.minecraft.enchantment.EnchantmentHelper$IModifier;
import net.minecraft.util.DamageSource;

final class EnchantmentHelper$ModifierDamage implements EnchantmentHelper$IModifier {
   public int damageModifier;
   public DamageSource source;

   private EnchantmentHelper$ModifierDamage() {
   }

   public void calculateModifier(Enchantment var1, int var2) {
      this.damageModifier += var1.calcModifierDamage(var2, this.source);
   }

   EnchantmentHelper$ModifierDamage(EnchantmentHelper$1 var1) {
      this();
   }
}
