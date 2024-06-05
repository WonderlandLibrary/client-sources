package net.minecraft.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper$1;
import net.minecraft.enchantment.EnchantmentHelper$IModifier;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

final class EnchantmentHelper$DamageIterator implements EnchantmentHelper$IModifier {
   public EntityLivingBase user;
   public Entity target;

   private EnchantmentHelper$DamageIterator() {
   }

   public void calculateModifier(Enchantment var1, int var2) {
      var1.onEntityDamaged(this.user, this.target, var2);
   }

   EnchantmentHelper$DamageIterator(EnchantmentHelper$1 var1) {
      this();
   }
}
