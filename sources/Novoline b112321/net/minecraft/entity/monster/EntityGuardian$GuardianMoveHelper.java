package net.minecraft.entity.monster;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityLookHelper;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.util.MathHelper;

class EntityGuardian$GuardianMoveHelper extends EntityMoveHelper {
   private EntityGuardian entityGuardian;

   public EntityGuardian$GuardianMoveHelper(EntityGuardian var1) {
      super(var1);
      this.entityGuardian = var1;
   }

   public void onUpdateMoveHelper() {
      if(this.update && !this.entityGuardian.getNavigator().noPath()) {
         double var1 = this.posX - this.entityGuardian.posX;
         double var3 = this.posY - this.entityGuardian.posY;
         double var5 = this.posZ - this.entityGuardian.posZ;
         double var7 = var1 * var1 + var3 * var3 + var5 * var5;
         var7 = (double)MathHelper.sqrt_double(var7);
         var3 = var3 / var7;
         float var9 = (float)(MathHelper.func_181159_b(var5, var1) * 180.0D / 3.141592653589793D) - 90.0F;
         this.entityGuardian.rotationYaw = this.limitAngle(this.entityGuardian.rotationYaw, var9, 30.0F);
         this.entityGuardian.renderYawOffset = this.entityGuardian.rotationYaw;
         float var10 = (float)(this.speed * this.entityGuardian.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue());
         this.entityGuardian.setAIMoveSpeed(this.entityGuardian.getAIMoveSpeed() + (var10 - this.entityGuardian.getAIMoveSpeed()) * 0.125F);
         double var11 = (double)MathHelper.sin((double)(this.entityGuardian.ticksExisted + this.entityGuardian.getEntityID()) * 0.5D) * 0.05D;
         double var13 = (double)MathHelper.cos((double)(this.entityGuardian.rotationYaw * 3.1415927F / 180.0F));
         double var15 = (double)MathHelper.sin((double)(this.entityGuardian.rotationYaw * 3.1415927F / 180.0F));
         this.entityGuardian.motionX += var11 * var13;
         this.entityGuardian.motionZ += var11 * var15;
         var11 = (double)MathHelper.sin((double)(this.entityGuardian.ticksExisted + this.entityGuardian.getEntityID()) * 0.75D) * 0.05D;
         this.entityGuardian.motionY += var11 * (var15 + var13) * 0.25D;
         this.entityGuardian.motionY += (double)this.entityGuardian.getAIMoveSpeed() * var3 * 0.1D;
         EntityLookHelper var17 = this.entityGuardian.getLookHelper();
         double var18 = this.entityGuardian.posX + var1 / var7 * 2.0D;
         double var20 = (double)this.entityGuardian.getEyeHeight() + this.entityGuardian.posY + var3 / var7 * 1.0D;
         double var22 = this.entityGuardian.posZ + var5 / var7 * 2.0D;
         double var24 = var17.getLookPosX();
         double var26 = var17.getLookPosY();
         double var28 = var17.getLookPosZ();
         if(!var17.getIsLooking()) {
            var24 = var18;
            var26 = var20;
            var28 = var22;
         }

         this.entityGuardian.getLookHelper().setLookPosition(var24 + (var18 - var24) * 0.125D, var26 + (var20 - var26) * 0.125D, var28 + (var22 - var28) * 0.125D, 10.0F, 40.0F);
         EntityGuardian.access$200(this.entityGuardian, true);
      } else {
         this.entityGuardian.setAIMoveSpeed(0.0F);
         EntityGuardian.access$200(this.entityGuardian, false);
      }

   }
}
