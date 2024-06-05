package net.minecraft.entity.monster;

import com.google.common.base.Predicate;
import net.aP;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;

class EntityIronGolem$AINearestAttackableTargetNonCreeper$1 implements Predicate {
   final Predicate val$p_i45858_6_;
   final EntityCreature val$creature;
   final aP c;

   EntityIronGolem$AINearestAttackableTargetNonCreeper$1(aP var1, Predicate var2, EntityCreature var3) {
      this.c = var1;
      this.val$p_i45858_6_ = var2;
      this.val$creature = var3;
   }

   public boolean apply(EntityLivingBase var1) {
      if(this.val$p_i45858_6_ != null && !this.val$p_i45858_6_.apply(var1)) {
         return false;
      } else if(var1 instanceof EntityCreeper) {
         return false;
      } else {
         if(var1 instanceof EntityPlayer) {
            double var2 = aP.a(this.c);
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

            if((double)var1.getDistanceToEntity(this.val$creature) > var2) {
               return false;
            }
         }

         return aP.a(this.c, var1, false);
      }
   }
}
