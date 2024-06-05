package net.minecraft.entity.passive;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.passive.EntityRabbit;

class EntityRabbit$AIEvilAttack extends EntityAIAttackOnCollide {
   public EntityRabbit$AIEvilAttack(EntityRabbit var1) {
      super(var1, EntityLivingBase.class, 1.4D, true);
   }

   protected double func_179512_a(EntityLivingBase var1) {
      return (double)(4.0F + var1.width);
   }
}
