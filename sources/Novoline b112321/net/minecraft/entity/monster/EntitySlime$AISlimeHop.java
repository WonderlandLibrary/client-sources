package net.minecraft.entity.monster;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySlime$SlimeMoveHelper;

class EntitySlime$AISlimeHop extends EntityAIBase {
   private EntitySlime slime;

   public EntitySlime$AISlimeHop(EntitySlime var1) {
      this.slime = var1;
      this.setMutexBits(5);
   }

   public boolean shouldExecute() {
      return true;
   }

   public void updateTask() {
      ((EntitySlime$SlimeMoveHelper)this.slime.getMoveHelper()).setSpeed(1.0D);
   }
}
