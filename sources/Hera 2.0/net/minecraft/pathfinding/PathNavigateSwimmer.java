/*    */ package net.minecraft.pathfinding;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.util.MovingObjectPosition;
/*    */ import net.minecraft.util.Vec3;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraft.world.pathfinder.NodeProcessor;
/*    */ import net.minecraft.world.pathfinder.SwimNodeProcessor;
/*    */ 
/*    */ public class PathNavigateSwimmer extends PathNavigate {
/*    */   public PathNavigateSwimmer(EntityLiving entitylivingIn, World worldIn) {
/* 13 */     super(entitylivingIn, worldIn);
/*    */   }
/*    */ 
/*    */   
/*    */   protected PathFinder getPathFinder() {
/* 18 */     return new PathFinder((NodeProcessor)new SwimNodeProcessor());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean canNavigate() {
/* 26 */     return isInLiquid();
/*    */   }
/*    */ 
/*    */   
/*    */   protected Vec3 getEntityPosition() {
/* 31 */     return new Vec3(this.theEntity.posX, this.theEntity.posY + this.theEntity.height * 0.5D, this.theEntity.posZ);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void pathFollow() {
/* 36 */     Vec3 vec3 = getEntityPosition();
/* 37 */     float f = this.theEntity.width * this.theEntity.width;
/* 38 */     int i = 6;
/*    */     
/* 40 */     if (vec3.squareDistanceTo(this.currentPath.getVectorFromIndex((Entity)this.theEntity, this.currentPath.getCurrentPathIndex())) < f)
/*    */     {
/* 42 */       this.currentPath.incrementPathIndex();
/*    */     }
/*    */     
/* 45 */     for (int j = Math.min(this.currentPath.getCurrentPathIndex() + i, this.currentPath.getCurrentPathLength() - 1); j > this.currentPath.getCurrentPathIndex(); j--) {
/*    */       
/* 47 */       Vec3 vec31 = this.currentPath.getVectorFromIndex((Entity)this.theEntity, j);
/*    */       
/* 49 */       if (vec31.squareDistanceTo(vec3) <= 36.0D && isDirectPathBetweenPoints(vec3, vec31, 0, 0, 0)) {
/*    */         
/* 51 */         this.currentPath.setCurrentPathIndex(j);
/*    */         
/*    */         break;
/*    */       } 
/*    */     } 
/* 56 */     checkForStuck(vec3);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void removeSunnyPath() {
/* 64 */     super.removeSunnyPath();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean isDirectPathBetweenPoints(Vec3 posVec31, Vec3 posVec32, int sizeX, int sizeY, int sizeZ) {
/* 73 */     MovingObjectPosition movingobjectposition = this.worldObj.rayTraceBlocks(posVec31, new Vec3(posVec32.xCoord, posVec32.yCoord + this.theEntity.height * 0.5D, posVec32.zCoord), false, true, false);
/* 74 */     return !(movingobjectposition != null && movingobjectposition.typeOfHit != MovingObjectPosition.MovingObjectType.MISS);
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\pathfinding\PathNavigateSwimmer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */