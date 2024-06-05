package net.minecraft.pathfinding;

import net.minecraft.entity.EntityLiving;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition$MovingObjectType;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.pathfinder.SwimNodeProcessor;

public class PathNavigateSwimmer extends PathNavigate {
   public PathNavigateSwimmer(EntityLiving var1, World var2) {
      super(var1, var2);
   }

   protected PathFinder getPathFinder() {
      return new PathFinder(new SwimNodeProcessor());
   }

   protected boolean canNavigate() {
      return this.isInLiquid();
   }

   protected Vec3 getEntityPosition() {
      return new Vec3(this.theEntity.posX, this.theEntity.posY + (double)this.theEntity.height * 0.5D, this.theEntity.posZ);
   }

   protected void pathFollow() {
      Vec3 var1 = this.getEntityPosition();
      float var2 = this.theEntity.width * this.theEntity.width;
      byte var3 = 6;
      if(var1.squareDistanceTo(this.currentPath.getVectorFromIndex(this.theEntity, this.currentPath.getCurrentPathIndex())) < (double)var2) {
         this.currentPath.incrementPathIndex();
      }

      for(int var4 = Math.min(this.currentPath.getCurrentPathIndex() + var3, this.currentPath.getCurrentPathLength() - 1); var4 > this.currentPath.getCurrentPathIndex(); --var4) {
         Vec3 var5 = this.currentPath.getVectorFromIndex(this.theEntity, var4);
         if(var5.squareDistanceTo(var1) <= 36.0D && this.isDirectPathBetweenPoints(var1, var5, 0, 0, 0)) {
            this.currentPath.setCurrentPathIndex(var4);
            break;
         }
      }

      this.checkForStuck(var1);
   }

   protected void removeSunnyPath() {
      super.removeSunnyPath();
   }

   protected boolean isDirectPathBetweenPoints(Vec3 var1, Vec3 var2, int var3, int var4, int var5) {
      MovingObjectPosition var6 = this.worldObj.rayTraceBlocks(var1, new Vec3(var2.xCoord, var2.yCoord + (double)this.theEntity.height * 0.5D, var2.zCoord), false, true, false);
      return var6.typeOfHit == MovingObjectPosition$MovingObjectType.MISS;
   }
}
