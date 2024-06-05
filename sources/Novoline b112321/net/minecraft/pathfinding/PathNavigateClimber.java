package net.minecraft.pathfinding;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class PathNavigateClimber extends PathNavigateGround {
   private BlockPos targetPosition;

   public PathNavigateClimber(EntityLiving var1, World var2) {
      super(var1, var2);
   }

   public PathEntity getPathToPos(BlockPos var1) {
      this.targetPosition = var1;
      return super.getPathToPos(var1);
   }

   public PathEntity getPathToEntityLiving(Entity var1) {
      this.targetPosition = new BlockPos(var1);
      return super.getPathToEntityLiving(var1);
   }

   public boolean tryMoveToEntityLiving(Entity var1, double var2) {
      PathEntity var4 = this.getPathToEntityLiving(var1);
      return this.a(var4, var2);
   }

   public void onUpdateNavigation() {
      if(!this.noPath()) {
         super.onUpdateNavigation();
      } else if(this.targetPosition != null) {
         double var1 = (double)(this.theEntity.width * this.theEntity.width);
         if(this.theEntity.getDistanceSqToCenter(this.targetPosition) < var1 || this.theEntity.posY > (double)this.targetPosition.getY() && this.theEntity.getDistanceSqToCenter(new BlockPos(this.targetPosition.getX(), MathHelper.floor_double(this.theEntity.posY), this.targetPosition.getZ())) < var1) {
            this.targetPosition = null;
         } else {
            this.theEntity.getMoveHelper().setMoveTo((double)this.targetPosition.getX(), (double)this.targetPosition.getY(), (double)this.targetPosition.getZ(), this.speed);
         }
      }

   }
}
