package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.village.Village;

public class EntityAIDefendVillage extends EntityAITarget {
   private static final String __OBFID = "CL_00001618";
   EntityIronGolem irongolem;
   EntityLivingBase villageAgressorTarget;

   public boolean shouldExecute() {
      Village var1 = this.irongolem.getVillage();
      if (var1 == null) {
         return false;
      } else {
         this.villageAgressorTarget = var1.findNearestVillageAggressor(this.irongolem);
         if (!this.isSuitableTarget(this.villageAgressorTarget, false)) {
            if (this.taskOwner.getRNG().nextInt(20) == 0) {
               this.villageAgressorTarget = var1.func_82685_c(this.irongolem);
               return this.isSuitableTarget(this.villageAgressorTarget, false);
            } else {
               return false;
            }
         } else {
            return true;
         }
      }
   }

   public EntityAIDefendVillage(EntityIronGolem var1) {
      super(var1, false, true);
      this.irongolem = var1;
      this.setMutexBits(1);
   }

   public void startExecuting() {
      this.irongolem.setAttackTarget(this.villageAgressorTarget);
      super.startExecuting();
   }
}
