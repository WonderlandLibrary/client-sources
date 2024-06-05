package net.minecraft.util;

import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition$MovingObjectType;
import net.minecraft.util.Vec3;

public class MovingObjectPosition {
   private BlockPos blockPos;
   public MovingObjectPosition$MovingObjectType typeOfHit;
   public EnumFacing facing;
   public Vec3 hitVec;
   public Entity entityHit;

   public MovingObjectPosition(Vec3 var1, EnumFacing var2, BlockPos var3) {
      this(MovingObjectPosition$MovingObjectType.BLOCK, var1, var2, var3);
   }

   public MovingObjectPosition(Vec3 var1, EnumFacing var2) {
      this(MovingObjectPosition$MovingObjectType.BLOCK, var1, var2, BlockPos.ORIGIN);
   }

   public MovingObjectPosition(Entity var1) {
      this(var1, new Vec3(var1.posX, var1.posY, var1.posZ));
   }

   public MovingObjectPosition(MovingObjectPosition$MovingObjectType var1, Vec3 var2, EnumFacing var3, BlockPos var4) {
      this.typeOfHit = var1;
      this.blockPos = var4;
      this.facing = var3;
      this.hitVec = new Vec3(var2.xCoord, var2.yCoord, var2.zCoord);
   }

   public MovingObjectPosition(Entity var1, Vec3 var2) {
      this.typeOfHit = MovingObjectPosition$MovingObjectType.ENTITY;
      this.entityHit = var1;
      this.hitVec = var2;
   }

   public BlockPos getBlockPos() {
      return this.blockPos;
   }

   public String toString() {
      return "HitResult{type=" + this.typeOfHit + ", blockpos=" + this.blockPos + ", f=" + this.facing + ", pos=" + this.hitVec + ", entity=" + this.entityHit + '}';
   }
}
