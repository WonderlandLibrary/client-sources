/*    */ package net.minecraft.util;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MovingObjectPosition
/*    */ {
/*    */   private BlockPos blockPos;
/*    */   public MovingObjectType typeOfHit;
/*    */   public EnumFacing sideHit;
/*    */   public Vec3 hitVec;
/*    */   public Entity entityHit;
/*    */   
/*    */   public MovingObjectPosition(Vec3 hitVecIn, EnumFacing facing, BlockPos blockPosIn) {
/* 17 */     this(MovingObjectType.BLOCK, hitVecIn, facing, blockPosIn);
/*    */   }
/*    */   
/*    */   public MovingObjectPosition(Vec3 p_i45552_1_, EnumFacing facing) {
/* 21 */     this(MovingObjectType.BLOCK, p_i45552_1_, facing, BlockPos.ORIGIN);
/*    */   }
/*    */   
/*    */   public MovingObjectPosition(Entity p_i2304_1_) {
/* 25 */     this(p_i2304_1_, new Vec3(p_i2304_1_.posX, p_i2304_1_.posY, p_i2304_1_.posZ));
/*    */   }
/*    */ 
/*    */   
/*    */   public MovingObjectPosition(MovingObjectType typeOfHitIn, Vec3 hitVecIn, EnumFacing sideHitIn, BlockPos blockPosIn) {
/* 30 */     this.typeOfHit = typeOfHitIn;
/* 31 */     this.blockPos = blockPosIn;
/* 32 */     this.sideHit = sideHitIn;
/* 33 */     this.hitVec = new Vec3(hitVecIn.xCoord, hitVecIn.yCoord, hitVecIn.zCoord);
/*    */   }
/*    */   
/*    */   public MovingObjectPosition(Entity entityHitIn, Vec3 hitVecIn) {
/* 37 */     this.typeOfHit = MovingObjectType.ENTITY;
/* 38 */     this.entityHit = entityHitIn;
/* 39 */     this.hitVec = hitVecIn;
/*    */   }
/*    */   
/*    */   public BlockPos getBlockPos() {
/* 43 */     return this.blockPos;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 47 */     return "HitResult{type=" + this.typeOfHit + ", blockpos=" + this.blockPos + ", f=" + this.sideHit + ", pos=" + 
/* 48 */       this.hitVec + ", entity=" + this.entityHit + '}';
/*    */   }
/*    */   
/*    */   public enum MovingObjectType {
/* 52 */     MISS, BLOCK, ENTITY;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraf\\util\MovingObjectPosition.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */