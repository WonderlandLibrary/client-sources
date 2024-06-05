package net.minecraft.entity.ai;

import com.google.common.base.Predicate;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;

class EntityAINearestAttackableTarget$1 implements Predicate {
   final Predicate val$targetSelector;
   final EntityAINearestAttackableTarget this$0;

   EntityAINearestAttackableTarget$1(EntityAINearestAttackableTarget var1, Predicate var2) {
      this.this$0 = var1;
      this.val$targetSelector = var2;
   }

   public boolean apply(EntityLivingBase var1) {
      if(this.val$targetSelector != null && !this.val$targetSelector.apply(var1)) {
         return false;
      } else {
         if(var1 instanceof EntityPlayer) {
            double var2 = this.this$0.getTargetDistance();
            if(var1.isSneaking()) {
               var2 *= 0.800000011920929D;
            }

            if(var1.isInvisible()) {
               float var4 = ((EntityPlayer)var1).getArmorVisibility();
               if(var4 < 0.1F) {
                  var4 = 0.1F;
               }

               var2 *= (double)(0.7F * var4);
            }

            if((double)var1.getDistanceToEntity(this.this$0.taskOwner) > var2) {
               return false;
            }
         }

         return this.this$0.isSuitableTarget(var1, false);
      }
   }
}
