package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.village.Village;

public class EntityAIDefendVillage extends EntityAITarget {
   EntityIronGolem irongolem;
   EntityLivingBase i;

   public EntityAIDefendVillage(EntityIronGolem var1) {
      super(var1, false, true);
      this.irongolem = var1;
      this.setMutexBits(1);
   }

   public boolean shouldExecute() {
      Village var1 = this.irongolem.getVillage();
      return false;
   }

   public void startExecuting() {
      this.irongolem.setAttackTarget(this.i);
      super.startExecuting();
   }
}
