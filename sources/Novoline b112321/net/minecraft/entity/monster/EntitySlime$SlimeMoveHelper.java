package net.minecraft.entity.monster;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.monster.EntitySlime;

class EntitySlime$SlimeMoveHelper extends EntityMoveHelper {
   private float field_179922_g;
   private int field_179924_h;
   private EntitySlime slime;
   private boolean field_179923_j;

   public EntitySlime$SlimeMoveHelper(EntitySlime var1) {
      super(var1);
      this.slime = var1;
   }

   public void func_179920_a(float var1, boolean var2) {
      this.field_179922_g = var1;
      this.field_179923_j = var2;
   }

   public void setSpeed(double var1) {
      this.speed = var1;
      this.update = true;
   }

   public void onUpdateMoveHelper() {
      this.entity.rotationYaw = this.limitAngle(this.entity.rotationYaw, this.field_179922_g, 30.0F);
      this.entity.rotationYawHead = this.entity.rotationYaw;
      this.entity.renderYawOffset = this.entity.rotationYaw;
      if(!this.update) {
         this.entity.setMoveForward(0.0F);
      } else {
         this.update = false;
         if(this.entity.onGround) {
            this.entity.setAIMoveSpeed((float)(this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue()));
            if(this.field_179924_h-- <= 0) {
               this.field_179924_h = this.slime.getJumpDelay();
               if(this.field_179923_j) {
                  this.field_179924_h /= 3;
               }

               this.slime.getJumpHelper().setJumping();
               if(this.slime.makesSoundOnJump()) {
                  this.slime.playSound(this.slime.getJumpSound(), this.slime.getSoundVolume(), ((this.slime.getRNG().nextFloat() - this.slime.getRNG().nextFloat()) * 0.2F + 1.0F) * 0.8F);
               }
            } else {
               this.slime.moveStrafing = this.slime.moveForward = 0.0F;
               this.entity.setAIMoveSpeed(0.0F);
            }
         } else {
            this.entity.setAIMoveSpeed((float)(this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue()));
         }
      }

   }
}
