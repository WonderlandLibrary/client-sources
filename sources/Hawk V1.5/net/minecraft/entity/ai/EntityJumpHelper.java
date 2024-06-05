package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLiving;

public class EntityJumpHelper {
   private EntityLiving entity;
   private static final String __OBFID = "CL_00001571";
   protected boolean isJumping;

   public void setJumping() {
      this.isJumping = true;
   }

   public void doJump() {
      this.entity.setJumping(this.isJumping);
      this.isJumping = false;
   }

   public EntityJumpHelper(EntityLiving var1) {
      this.entity = var1;
   }
}
