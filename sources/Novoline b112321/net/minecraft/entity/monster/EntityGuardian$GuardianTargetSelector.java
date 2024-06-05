package net.minecraft.entity.monster;

import com.google.common.base.Predicate;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;

class EntityGuardian$GuardianTargetSelector implements Predicate {
   private EntityGuardian parentEntity;

   public EntityGuardian$GuardianTargetSelector(EntityGuardian var1) {
      this.parentEntity = var1;
   }

   public boolean apply(EntityLivingBase var1) {
      return (var1 instanceof EntityPlayer || var1 instanceof EntitySquid) && var1.getDistanceSqToEntity(this.parentEntity) > 9.0D;
   }
}
