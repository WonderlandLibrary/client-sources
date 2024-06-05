package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLiving;

public class EntityJumpHelper {
   private EntityLiving entity;
   protected boolean isJumping;

   public EntityJumpHelper(EntityLiving var1) {
      this.entity = var1;
   }

   public void setJumping() {
      this.isJumping = true;
   }

   public void doJump() {
      this.entity.setJumping(this.isJumping);
      this.isJumping = false;
   }
}
