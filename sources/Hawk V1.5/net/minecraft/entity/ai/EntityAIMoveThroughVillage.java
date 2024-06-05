package net.minecraft.entity.ai;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.EntityCreature;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.village.Village;
import net.minecraft.village.VillageDoorInfo;

public class EntityAIMoveThroughVillage extends EntityAIBase {
   private VillageDoorInfo doorInfo;
   private boolean isNocturnal;
   private List doorList = Lists.newArrayList();
   private PathEntity entityPathNavigate;
   private static final String __OBFID = "CL_00001597";
   private EntityCreature theEntity;
   private double movementSpeed;

   public boolean continueExecuting() {
      if (this.theEntity.getNavigator().noPath()) {
         return false;
      } else {
         float var1 = this.theEntity.width + 4.0F;
         return this.theEntity.getDistanceSq(this.doorInfo.func_179852_d()) > (double)(var1 * var1);
      }
   }

   private VillageDoorInfo func_75412_a(Village var1) {
      VillageDoorInfo var2 = null;
      int var3 = Integer.MAX_VALUE;
      List var4 = var1.getVillageDoorInfoList();
      Iterator var5 = var4.iterator();

      while(var5.hasNext()) {
         VillageDoorInfo var6 = (VillageDoorInfo)var5.next();
         int var7 = var6.getDistanceSquared(MathHelper.floor_double(this.theEntity.posX), MathHelper.floor_double(this.theEntity.posY), MathHelper.floor_double(this.theEntity.posZ));
         if (var7 < var3 && !this.func_75413_a(var6)) {
            var2 = var6;
            var3 = var7;
         }
      }

      return var2;
   }

   public EntityAIMoveThroughVillage(EntityCreature var1, double var2, boolean var4) {
      this.theEntity = var1;
      this.movementSpeed = var2;
      this.isNocturnal = var4;
      this.setMutexBits(1);
      if (!(var1.getNavigator() instanceof PathNavigateGround)) {
         throw new IllegalArgumentException("Unsupported mob for MoveThroughVillageGoal");
      }
   }

   private void func_75414_f() {
      if (this.doorList.size() > 15) {
         this.doorList.remove(0);
      }

   }

   public boolean shouldExecute() {
      this.func_75414_f();
      if (this.isNocturnal && this.theEntity.worldObj.isDaytime()) {
         return false;
      } else {
         Village var1 = this.theEntity.worldObj.getVillageCollection().func_176056_a(new BlockPos(this.theEntity), 0);
         if (var1 == null) {
            return false;
         } else {
            this.doorInfo = this.func_75412_a(var1);
            if (this.doorInfo == null) {
               return false;
            } else {
               PathNavigateGround var2 = (PathNavigateGround)this.theEntity.getNavigator();
               boolean var3 = var2.func_179686_g();
               var2.func_179688_b(false);
               this.entityPathNavigate = var2.func_179680_a(this.doorInfo.func_179852_d());
               var2.func_179688_b(var3);
               if (this.entityPathNavigate != null) {
                  return true;
               } else {
                  Vec3 var4 = RandomPositionGenerator.findRandomTargetBlockTowards(this.theEntity, 10, 7, new Vec3((double)this.doorInfo.func_179852_d().getX(), (double)this.doorInfo.func_179852_d().getY(), (double)this.doorInfo.func_179852_d().getZ()));
                  if (var4 == null) {
                     return false;
                  } else {
                     var2.func_179688_b(false);
                     this.entityPathNavigate = this.theEntity.getNavigator().getPathToXYZ(var4.xCoord, var4.yCoord, var4.zCoord);
                     var2.func_179688_b(var3);
                     return this.entityPathNavigate != null;
                  }
               }
            }
         }
      }
   }

   public void startExecuting() {
      this.theEntity.getNavigator().setPath(this.entityPathNavigate, this.movementSpeed);
   }

   private boolean func_75413_a(VillageDoorInfo var1) {
      Iterator var2 = this.doorList.iterator();

      while(var2.hasNext()) {
         VillageDoorInfo var3 = (VillageDoorInfo)var2.next();
         if (var1.func_179852_d().equals(var3.func_179852_d())) {
            return true;
         }
      }

      return false;
   }

   public void resetTask() {
      if (this.theEntity.getNavigator().noPath() || this.theEntity.getDistanceSq(this.doorInfo.func_179852_d()) < 16.0D) {
         this.doorList.add(this.doorInfo);
      }

   }
}
