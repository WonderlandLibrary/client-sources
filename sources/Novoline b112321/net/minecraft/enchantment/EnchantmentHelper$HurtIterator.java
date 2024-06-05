package net.minecraft.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper$1;
import net.minecraft.enchantment.EnchantmentHelper$IModifier;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

final class EnchantmentHelper$HurtIterator implements EnchantmentHelper$IModifier {
   public EntityLivingBase user;
   public Entity attacker;

   private EnchantmentHelper$HurtIterator() {
   }

   public void calculateModifier(Enchantment var1, int var2) {
      var1.onUserHurt(this.user, this.attacker, var2);
   }

   EnchantmentHelper$HurtIterator(EnchantmentHelper$1 var1) {
      this();
   }
}
