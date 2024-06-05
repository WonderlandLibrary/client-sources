package net.minecraft.pathfinding;

import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.World;

public abstract class PathNavigate {
   protected EntityLiving theEntity;
   protected World worldObj;
   protected PathEntity currentPath;
   protected double speed;
   private final IAttributeInstance pathSearchRange;
   private int totalTicks;
   private int ticksAtLastPos;
   private Vec3 lastPosCheck = new Vec3(0.0D, 0.0D, 0.0D);
   private float heightRequirement = 1.0F;
   private final PathFinder pathFinder;

   public PathNavigate(EntityLiving var1, World var2) {
      this.theEntity = var1;
      this.worldObj = var2;
      this.pathSearchRange = var1.getEntityAttribute(SharedMonsterAttributes.followRange);
      this.pathFinder = this.getPathFinder();
   }

   protected abstract PathFinder getPathFinder();

   public void setSpeed(double var1) {
      this.speed = var1;
   }

   public float getPathSearchRange() {
      return (float)this.pathSearchRange.getAttributeValue();
   }

   public final PathEntity getPathToXYZ(double var1, double var3, double var5) {
      return this.getPathToPos(new BlockPos(MathHelper.floor_double(var1), (int)var3, MathHelper.floor_double(var5)));
   }

   public PathEntity getPathToPos(BlockPos var1) {
      if(!this.canNavigate()) {
         return null;
      } else {
         float var2 = this.getPathSearchRange();
         this.worldObj.theProfiler.startSection("pathfind");
         BlockPos var3 = new BlockPos(this.theEntity);
         int var4 = (int)(var2 + 8.0F);
         ChunkCache var5 = new ChunkCache(this.worldObj, var3.a(-var4, -var4, -var4), var3.a(var4, var4, var4), 0);
         PathEntity var6 = this.pathFinder.createEntityPathTo(var5, this.theEntity, (BlockPos)var1, var2);
         this.worldObj.theProfiler.endSection();
         return var6;
      }
   }

   public boolean tryMoveToXYZ(double var1, double var3, double var5, double var7) {
      PathEntity var9 = this.getPathToXYZ((double)MathHelper.floor_double(var1), (double)((int)var3), (double)MathHelper.floor_double(var5));
      return this.a(var9, var7);
   }

   public void setHeightRequirement(float var1) {
      this.heightRequirement = var1;
   }

   public PathEntity getPathToEntityLiving(Entity var1) {
      if(!this.canNavigate()) {
         return null;
      } else {
         float var2 = this.getPathSearchRange();
         this.worldObj.theProfiler.startSection("pathfind");
         BlockPos var3 = (new BlockPos(this.theEntity)).up();
         int var4 = (int)(var2 + 16.0F);
         ChunkCache var5 = new ChunkCache(this.worldObj, var3.a(-var4, -var4, -var4), var3.a(var4, var4, var4), 0);
         PathEntity var6 = this.pathFinder.createEntityPathTo(var5, this.theEntity, (Entity)var1, var2);
         this.worldObj.theProfiler.endSection();
         return var6;
      }
   }

   public boolean tryMoveToEntityLiving(Entity var1, double var2) {
      PathEntity var4 = this.getPathToEntityLiving(var1);
      return this.a(var4, var2);
   }

   public boolean a(PathEntity var1, double var2) {
      this.currentPath = null;
      return false;
   }

   public PathEntity getPath() {
      return this.currentPath;
   }

   public void onUpdateNavigation() {
      ++this.totalTicks;
      if(!this.noPath()) {
         if(this.canNavigate()) {
            this.pathFollow();
         } else if(this.currentPath != null && this.currentPath.getCurrentPathIndex() < this.currentPath.getCurrentPathLength()) {
            Vec3 var1 = this.getEntityPosition();
            Vec3 var2 = this.currentPath.getVectorFromIndex(this.theEntity, this.currentPath.getCurrentPathIndex());
            if(var1.yCoord > var2.yCoord && !this.theEntity.onGround && MathHelper.floor_double(var1.xCoord) == MathHelper.floor_double(var2.xCoord) && MathHelper.floor_double(var1.zCoord) == MathHelper.floor_double(var2.zCoord)) {
               this.currentPath.setCurrentPathIndex(this.currentPath.getCurrentPathIndex() + 1);
            }
         }

         if(!this.noPath()) {
            Vec3 var8 = this.currentPath.getPosition(this.theEntity);
            AxisAlignedBB var9 = (new AxisAlignedBB(var8.xCoord, var8.yCoord, var8.zCoord, var8.xCoord, var8.yCoord, var8.zCoord)).expand(0.5D, 0.5D, 0.5D);
            List var3 = this.worldObj.getCollidingBoundingBoxes(this.theEntity, var9.addCoord(0.0D, -1.0D, 0.0D));
            double var4 = -1.0D;
            var9 = var9.offset(0.0D, 1.0D, 0.0D);

            for(AxisAlignedBB var7 : var3) {
               var4 = var7.calculateYOffset(var9, var4);
            }

            this.theEntity.getMoveHelper().setMoveTo(var8.xCoord, var8.yCoord + var4, var8.zCoord, this.speed);
         }
      }

   }

   protected void pathFollow() {
      Vec3 var1 = this.getEntityPosition();
      int var2 = this.currentPath.getCurrentPathLength();

      for(int var3 = this.currentPath.getCurrentPathIndex(); var3 < this.currentPath.getCurrentPathLength(); ++var3) {
         if(this.currentPath.getPathPointFromIndex(var3).yCoord != (int)var1.yCoord) {
            var2 = var3;
            break;
         }
      }

      float var8 = this.theEntity.width * this.theEntity.width * this.heightRequirement;

      for(int var4 = this.currentPath.getCurrentPathIndex(); var4 < var2; ++var4) {
         Vec3 var5 = this.currentPath.getVectorFromIndex(this.theEntity, var4);
         if(var1.squareDistanceTo(var5) < (double)var8) {
            this.currentPath.setCurrentPathIndex(var4 + 1);
         }
      }

      int var9 = MathHelper.ceiling_float_int(this.theEntity.width);
      int var10 = (int)this.theEntity.height + 1;
      int var6 = var9;

      for(int var7 = var2 - 1; var7 >= this.currentPath.getCurrentPathIndex(); --var7) {
         if(this.isDirectPathBetweenPoints(var1, this.currentPath.getVectorFromIndex(this.theEntity, var7), var9, var10, var6)) {
            this.currentPath.setCurrentPathIndex(var7);
            break;
         }
      }

      this.checkForStuck(var1);
   }

   protected void checkForStuck(Vec3 var1) {
      if(this.totalTicks - this.ticksAtLastPos > 100) {
         if(var1.squareDistanceTo(this.lastPosCheck) < 2.25D) {
            this.clearPathEntity();
         }

         this.ticksAtLastPos = this.totalTicks;
         this.lastPosCheck = var1;
      }

   }

   public boolean noPath() {
      return this.currentPath == null || this.currentPath.isFinished();
   }

   public void clearPathEntity() {
      this.currentPath = null;
   }

   protected abstract Vec3 getEntityPosition();

   protected abstract boolean canNavigate();

   protected boolean isInLiquid() {
      return this.theEntity.isInWater() || this.theEntity.isInLava();
   }

   protected void removeSunnyPath() {
   }

   protected abstract boolean isDirectPathBetweenPoints(Vec3 var1, Vec3 var2, int var3, int var4, int var5);
}
