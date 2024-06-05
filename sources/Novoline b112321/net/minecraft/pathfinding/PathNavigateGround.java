package net.minecraft.pathfinding;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.init.Blocks;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.pathfinder.WalkNodeProcessor;

public class PathNavigateGround extends PathNavigate {
   protected WalkNodeProcessor nodeProcessor;
   private boolean shouldAvoidSun;

   public PathNavigateGround(EntityLiving var1, World var2) {
      super(var1, var2);
   }

   protected PathFinder getPathFinder() {
      this.nodeProcessor = new WalkNodeProcessor();
      this.nodeProcessor.setEnterDoors(true);
      return new PathFinder(this.nodeProcessor);
   }

   protected boolean canNavigate() {
      return this.theEntity.onGround || this.getCanSwim() && this.isInLiquid() || this.theEntity.isRiding() && this.theEntity instanceof EntityZombie && this.theEntity.ridingEntity instanceof EntityChicken;
   }

   protected Vec3 getEntityPosition() {
      return new Vec3(this.theEntity.posX, (double)this.getPathablePosY(), this.theEntity.posZ);
   }

   private int getPathablePosY() {
      if(this.theEntity.isInWater() && this.getCanSwim()) {
         int var1 = (int)this.theEntity.getEntityBoundingBox().minY;
         Block var2 = this.worldObj.getBlockState(new BlockPos(MathHelper.floor_double(this.theEntity.posX), var1, MathHelper.floor_double(this.theEntity.posZ))).getBlock();
         int var3 = 0;

         while(var2 == Blocks.flowing_water || var2 == Blocks.water) {
            ++var1;
            var2 = this.worldObj.getBlockState(new BlockPos(MathHelper.floor_double(this.theEntity.posX), var1, MathHelper.floor_double(this.theEntity.posZ))).getBlock();
            ++var3;
            if(var3 > 16) {
               return (int)this.theEntity.getEntityBoundingBox().minY;
            }
         }

         return var1;
      } else {
         return (int)(this.theEntity.getEntityBoundingBox().minY + 0.5D);
      }
   }

   protected void removeSunnyPath() {
      super.removeSunnyPath();
      if(this.shouldAvoidSun) {
         if(this.worldObj.canSeeSky(new BlockPos(MathHelper.floor_double(this.theEntity.posX), (int)(this.theEntity.getEntityBoundingBox().minY + 0.5D), MathHelper.floor_double(this.theEntity.posZ)))) {
            return;
         }

         for(int var1 = 0; var1 < this.currentPath.getCurrentPathLength(); ++var1) {
            PathPoint var2 = this.currentPath.getPathPointFromIndex(var1);
            if(this.worldObj.canSeeSky(new BlockPos(var2.xCoord, var2.yCoord, var2.zCoord))) {
               this.currentPath.setCurrentPathLength(var1 - 1);
               return;
            }
         }
      }

   }

   protected boolean isDirectPathBetweenPoints(Vec3 var1, Vec3 var2, int var3, int var4, int var5) {
      int var6 = MathHelper.floor_double(var1.xCoord);
      int var7 = MathHelper.floor_double(var1.zCoord);
      double var8 = var2.xCoord - var1.xCoord;
      double var10 = var2.zCoord - var1.zCoord;
      double var12 = var8 * var8 + var10 * var10;
      if(var12 < 1.0E-8D) {
         return false;
      } else {
         double var14 = 1.0D / Math.sqrt(var12);
         var8 = var8 * var14;
         var10 = var10 * var14;
         var3 = var3 + 2;
         var5 = var5 + 2;
         if(!this.isSafeToStandAt(var6, (int)var1.yCoord, var7, var3, var4, var5, var1, var8, var10)) {
            return false;
         } else {
            var3 = var3 - 2;
            var5 = var5 - 2;
            double var16 = 1.0D / Math.abs(var8);
            double var18 = 1.0D / Math.abs(var10);
            double var20 = (double)(var6 * 1) - var1.xCoord;
            double var22 = (double)(var7 * 1) - var1.zCoord;
            if(var8 >= 0.0D) {
               ++var20;
            }

            if(var10 >= 0.0D) {
               ++var22;
            }

            var20 = var20 / var8;
            var22 = var22 / var10;
            int var24 = var8 < 0.0D?-1:1;
            int var25 = var10 < 0.0D?-1:1;
            int var26 = MathHelper.floor_double(var2.xCoord);
            int var27 = MathHelper.floor_double(var2.zCoord);
            int var28 = var26 - var6;
            int var29 = var27 - var7;

            while(var28 * var24 > 0 || var29 * var25 > 0) {
               if(var20 < var22) {
                  var20 += var16;
                  var6 += var24;
                  var28 = var26 - var6;
               } else {
                  var22 += var18;
                  var7 += var25;
                  var29 = var27 - var7;
               }

               if(!this.isSafeToStandAt(var6, (int)var1.yCoord, var7, var3, var4, var5, var1, var8, var10)) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   private boolean isSafeToStandAt(int var1, int var2, int var3, int var4, int var5, int var6, Vec3 var7, double var8, double var10) {
      int var12 = var1 - var4 / 2;
      int var13 = var3 - var6 / 2;
      if(!this.isPositionClear(var12, var2, var13, var4, var5, var6, var7, var8, var10)) {
         return false;
      } else {
         for(int var14 = var12; var14 < var12 + var4; ++var14) {
            for(int var15 = var13; var15 < var13 + var6; ++var15) {
               double var16 = (double)var14 + 0.5D - var7.xCoord;
               double var18 = (double)var15 + 0.5D - var7.zCoord;
               if(var16 * var8 + var18 * var10 >= 0.0D) {
                  Block var20 = this.worldObj.getBlockState(new BlockPos(var14, var2 - 1, var15)).getBlock();
                  Material var21 = var20.getMaterial();
                  if(var21 == Material.air) {
                     return false;
                  }

                  if(var21 == Material.water && !this.theEntity.isInWater()) {
                     return false;
                  }

                  if(var21 == Material.lava) {
                     return false;
                  }
               }
            }
         }

         return true;
      }
   }

   private boolean isPositionClear(int var1, int var2, int var3, int var4, int var5, int var6, Vec3 var7, double var8, double var10) {
      for(BlockPos var13 : BlockPos.getAllInBox(new BlockPos(var1, var2, var3), new BlockPos(var1 + var4 - 1, var2 + var5 - 1, var3 + var6 - 1))) {
         double var14 = (double)var13.getX() + 0.5D - var7.xCoord;
         double var16 = (double)var13.getZ() + 0.5D - var7.zCoord;
         if(var14 * var8 + var16 * var10 >= 0.0D) {
            Block var18 = this.worldObj.getBlockState(var13).getBlock();
            if(!var18.isPassable(this.worldObj, var13)) {
               return false;
            }
         }
      }

      return true;
   }

   public void setAvoidsWater(boolean var1) {
      this.nodeProcessor.setAvoidsWater(var1);
   }

   public boolean getAvoidsWater() {
      return this.nodeProcessor.getAvoidsWater();
   }

   public void setBreakDoors(boolean var1) {
      this.nodeProcessor.setBreakDoors(var1);
   }

   public void setEnterDoors(boolean var1) {
      this.nodeProcessor.setEnterDoors(var1);
   }

   public boolean getEnterDoors() {
      return this.nodeProcessor.getEnterDoors();
   }

   public void setCanSwim(boolean var1) {
      this.nodeProcessor.setCanSwim(var1);
   }

   public boolean getCanSwim() {
      return this.nodeProcessor.getCanSwim();
   }

   public void setAvoidSun(boolean var1) {
      this.shouldAvoidSun = var1;
   }
}
