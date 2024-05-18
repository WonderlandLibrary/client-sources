/*    */ package net.minecraft.world.pathfinder;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.pathfinding.PathPoint;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ 
/*    */ public class SwimNodeProcessor
/*    */   extends NodeProcessor
/*    */ {
/*    */   public void initProcessor(IBlockAccess iblockaccessIn, Entity entityIn) {
/* 16 */     super.initProcessor(iblockaccessIn, entityIn);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void postProcess() {
/* 26 */     super.postProcess();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PathPoint getPathPointTo(Entity entityIn) {
/* 34 */     return openPoint(MathHelper.floor_double((entityIn.getEntityBoundingBox()).minX), MathHelper.floor_double((entityIn.getEntityBoundingBox()).minY + 0.5D), MathHelper.floor_double((entityIn.getEntityBoundingBox()).minZ));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PathPoint getPathPointToCoords(Entity entityIn, double x, double y, double target) {
/* 42 */     return openPoint(MathHelper.floor_double(x - (entityIn.width / 2.0F)), MathHelper.floor_double(y + 0.5D), MathHelper.floor_double(target - (entityIn.width / 2.0F)));
/*    */   }
/*    */ 
/*    */   
/*    */   public int findPathOptions(PathPoint[] pathOptions, Entity entityIn, PathPoint currentPoint, PathPoint targetPoint, float maxDistance) {
/* 47 */     int i = 0; byte b; int j;
/*    */     EnumFacing[] arrayOfEnumFacing;
/* 49 */     for (j = (arrayOfEnumFacing = EnumFacing.values()).length, b = 0; b < j; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/*    */       
/* 51 */       PathPoint pathpoint = getSafePoint(entityIn, currentPoint.xCoord + enumfacing.getFrontOffsetX(), currentPoint.yCoord + enumfacing.getFrontOffsetY(), currentPoint.zCoord + enumfacing.getFrontOffsetZ());
/*    */       
/* 53 */       if (pathpoint != null && !pathpoint.visited && pathpoint.distanceTo(targetPoint) < maxDistance)
/*    */       {
/* 55 */         pathOptions[i++] = pathpoint;
/*    */       }
/*    */       b++; }
/*    */     
/* 59 */     return i;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private PathPoint getSafePoint(Entity entityIn, int x, int y, int z) {
/* 67 */     int i = func_176186_b(entityIn, x, y, z);
/* 68 */     return (i == -1) ? openPoint(x, y, z) : null;
/*    */   }
/*    */ 
/*    */   
/*    */   private int func_176186_b(Entity entityIn, int x, int y, int z) {
/* 73 */     BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*    */     
/* 75 */     for (int i = x; i < x + this.entitySizeX; i++) {
/*    */       
/* 77 */       for (int j = y; j < y + this.entitySizeY; j++) {
/*    */         
/* 79 */         for (int k = z; k < z + this.entitySizeZ; k++) {
/*    */           
/* 81 */           Block block = this.blockaccess.getBlockState((BlockPos)blockpos$mutableblockpos.func_181079_c(i, j, k)).getBlock();
/*    */           
/* 83 */           if (block.getMaterial() != Material.water)
/*    */           {
/* 85 */             return 0;
/*    */           }
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 91 */     return -1;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\world\pathfinder\SwimNodeProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */