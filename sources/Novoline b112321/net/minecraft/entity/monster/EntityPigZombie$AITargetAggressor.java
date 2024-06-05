package net.minecraft.entity.monster;

import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.player.EntityPlayer;

class EntityPigZombie$AITargetAggressor extends EntityAINearestAttackableTarget {
   public EntityPigZombie$AITargetAggressor(EntityPigZombie var1) {
      super(var1, EntityPlayer.class, true);
   }

   public boolean shouldExecute() {
      return ((EntityPigZombie)this.taskOwner).isAngry() && super.shouldExecute();
   }
}
