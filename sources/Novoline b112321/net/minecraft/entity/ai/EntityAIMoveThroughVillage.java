package net.minecraft.entity.ai;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.village.Village;
import net.minecraft.village.VillageDoorInfo;

public class EntityAIMoveThroughVillage extends EntityAIBase {
   private EntityCreature theEntity;
   private double movementSpeed;
   private PathEntity b;
   private VillageDoorInfo c;
   private boolean isNocturnal;
   private List doorList = Lists.newArrayList();

   public EntityAIMoveThroughVillage(EntityCreature var1, double var2, boolean var4) {
      this.theEntity = var1;
      this.movementSpeed = var2;
      this.isNocturnal = var4;
      this.setMutexBits(1);
      if(!(var1.getNavigator() instanceof PathNavigateGround)) {
         throw new IllegalArgumentException("Unsupported mob for MoveThroughVillageGoal");
      }
   }

   public boolean shouldExecute() {
      this.resizeDoorList();
      if(this.isNocturnal && this.theEntity.worldObj.isDaytime()) {
         return false;
      } else {
         Village var1 = this.theEntity.worldObj.getVillageCollection().getNearestVillage(new BlockPos(this.theEntity), 0);
         return false;
      }
   }

   public boolean continueExecuting() {
      if(this.theEntity.getNavigator().noPath()) {
         return false;
      } else {
         float var1 = this.theEntity.width + 4.0F;
         return this.theEntity.getDistanceSq(this.c.getDoorBlockPos()) > (double)(var1 * var1);
      }
   }

   public void startExecuting() {
      this.theEntity.getNavigator().a(this.b, this.movementSpeed);
   }

   public void resetTask() {
      if(this.theEntity.getNavigator().noPath() || this.theEntity.getDistanceSq(this.c.getDoorBlockPos()) < 16.0D) {
         this.doorList.add(this.c);
      }

   }

   private VillageDoorInfo findNearestDoor(Village var1) {
      VillageDoorInfo var2 = null;
      int var3 = Integer.MAX_VALUE;

      for(VillageDoorInfo var5 : var1.getVillageDoorInfoList()) {
         int var6 = var5.getDistanceSquared(MathHelper.floor_double(this.theEntity.posX), MathHelper.floor_double(this.theEntity.posY), MathHelper.floor_double(this.theEntity.posZ));
         if(var6 < var3 && !this.doesDoorListContain(var5)) {
            var2 = var5;
            var3 = var6;
         }
      }

      return var2;
   }

   private boolean doesDoorListContain(VillageDoorInfo var1) {
      for(VillageDoorInfo var3 : this.doorList) {
         if(var1.getDoorBlockPos().equals(var3.getDoorBlockPos())) {
            return true;
         }
      }

      return false;
   }

   private void resizeDoorList() {
      if(this.doorList.size() > 15) {
         this.doorList.remove(0);
      }

   }

   private static IllegalArgumentException a(IllegalArgumentException var0) {
      return var0;
   }
}
