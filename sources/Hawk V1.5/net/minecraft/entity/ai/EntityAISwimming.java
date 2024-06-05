package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.pathfinding.PathNavigateGround;

public class EntityAISwimming extends EntityAIBase {
   private static final String __OBFID = "CL_00001584";
   private EntityLiving theEntity;

   public EntityAISwimming(EntityLiving var1) {
      this.theEntity = var1;
      this.setMutexBits(4);
      ((PathNavigateGround)var1.getNavigator()).func_179693_d(true);
   }

   public boolean shouldExecute() {
      return this.theEntity.isInWater() || this.theEntity.func_180799_ab();
   }

   public void updateTask() {
      if (this.theEntity.getRNG().nextFloat() < 0.8F) {
         this.theEntity.getJumpHelper().setJumping();
      }

   }
}
