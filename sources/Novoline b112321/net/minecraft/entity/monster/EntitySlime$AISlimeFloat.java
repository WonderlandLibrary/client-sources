package net.minecraft.entity.monster;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySlime$SlimeMoveHelper;
import net.minecraft.pathfinding.PathNavigateGround;

class EntitySlime$AISlimeFloat extends EntityAIBase {
   private EntitySlime slime;

   public EntitySlime$AISlimeFloat(EntitySlime var1) {
      this.slime = var1;
      this.setMutexBits(5);
      ((PathNavigateGround)var1.getNavigator()).setCanSwim(true);
   }

   public boolean shouldExecute() {
      return this.slime.isInWater() || this.slime.isInLava();
   }

   public void updateTask() {
      if(this.slime.getRNG().nextFloat() < 0.8F) {
         this.slime.getJumpHelper().setJumping();
      }

      ((EntitySlime$SlimeMoveHelper)this.slime.getMoveHelper()).setSpeed(1.2D);
   }
}
