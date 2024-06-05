package net.minecraft.entity.monster;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.monster.EntityPigZombie;

class EntityPigZombie$AIHurtByAggressor extends EntityAIHurtByTarget {
   public EntityPigZombie$AIHurtByAggressor(EntityPigZombie var1) {
      super(var1, true, new Class[0]);
   }

   protected void setEntityAttackTarget(EntityCreature var1, EntityLivingBase var2) {
      super.setEntityAttackTarget(var1, var2);
      if(var1 instanceof EntityPigZombie) {
         EntityPigZombie.access$000((EntityPigZombie)var1, var2);
      }

   }
}
