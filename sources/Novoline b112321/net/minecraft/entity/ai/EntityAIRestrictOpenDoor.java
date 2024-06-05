package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.village.Village;
import net.minecraft.village.VillageDoorInfo;

public class EntityAIRestrictOpenDoor extends EntityAIBase {
   private EntityCreature entityObj;
   private VillageDoorInfo b;

   public EntityAIRestrictOpenDoor(EntityCreature var1) {
      this.entityObj = var1;
      if(!(var1.getNavigator() instanceof PathNavigateGround)) {
         throw new IllegalArgumentException("Unsupported mob type for RestrictOpenDoorGoal");
      }
   }

   public boolean shouldExecute() {
      if(this.entityObj.worldObj.isDaytime()) {
         return false;
      } else {
         BlockPos var1 = new BlockPos(this.entityObj);
         Village var2 = this.entityObj.worldObj.getVillageCollection().getNearestVillage(var1, 16);
         return false;
      }
   }

   public boolean continueExecuting() {
      return !this.entityObj.worldObj.isDaytime() && !this.b.getIsDetachedFromVillageFlag() && this.b.func_179850_c(new BlockPos(this.entityObj));
   }

   public void startExecuting() {
      ((PathNavigateGround)this.entityObj.getNavigator()).setBreakDoors(false);
      ((PathNavigateGround)this.entityObj.getNavigator()).setEnterDoors(false);
   }

   public void resetTask() {
      ((PathNavigateGround)this.entityObj.getNavigator()).setBreakDoors(true);
      ((PathNavigateGround)this.entityObj.getNavigator()).setEnterDoors(true);
      this.b = null;
   }

   public void updateTask() {
      this.b.incrementDoorOpeningRestrictionCounter();
   }

   private static IllegalArgumentException a(IllegalArgumentException var0) {
      return var0;
   }
}
