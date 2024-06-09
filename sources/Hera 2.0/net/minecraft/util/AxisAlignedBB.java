/*     */ package net.minecraft.util;
/*     */ 
/*     */ 
/*     */ public class AxisAlignedBB
/*     */ {
/*     */   public final double minX;
/*     */   public final double minY;
/*     */   public final double minZ;
/*     */   public final double maxX;
/*     */   public final double maxY;
/*     */   public final double maxZ;
/*     */   
/*     */   public AxisAlignedBB(double x1, double y1, double z1, double x2, double y2, double z2) {
/*  14 */     this.minX = Math.min(x1, x2);
/*  15 */     this.minY = Math.min(y1, y2);
/*  16 */     this.minZ = Math.min(z1, z2);
/*  17 */     this.maxX = Math.max(x1, x2);
/*  18 */     this.maxY = Math.max(y1, y2);
/*  19 */     this.maxZ = Math.max(z1, z2);
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB(BlockPos pos1, BlockPos pos2) {
/*  24 */     this.minX = pos1.getX();
/*  25 */     this.minY = pos1.getY();
/*  26 */     this.minZ = pos1.getZ();
/*  27 */     this.maxX = pos2.getX();
/*  28 */     this.maxY = pos2.getY();
/*  29 */     this.maxZ = pos2.getZ();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB addCoord(double x, double y, double z) {
/*  37 */     double d0 = this.minX;
/*  38 */     double d1 = this.minY;
/*  39 */     double d2 = this.minZ;
/*  40 */     double d3 = this.maxX;
/*  41 */     double d4 = this.maxY;
/*  42 */     double d5 = this.maxZ;
/*     */     
/*  44 */     if (x < 0.0D) {
/*     */       
/*  46 */       d0 += x;
/*     */     }
/*  48 */     else if (x > 0.0D) {
/*     */       
/*  50 */       d3 += x;
/*     */     } 
/*     */     
/*  53 */     if (y < 0.0D) {
/*     */       
/*  55 */       d1 += y;
/*     */     }
/*  57 */     else if (y > 0.0D) {
/*     */       
/*  59 */       d4 += y;
/*     */     } 
/*     */     
/*  62 */     if (z < 0.0D) {
/*     */       
/*  64 */       d2 += z;
/*     */     }
/*  66 */     else if (z > 0.0D) {
/*     */       
/*  68 */       d5 += z;
/*     */     } 
/*     */     
/*  71 */     return new AxisAlignedBB(d0, d1, d2, d3, d4, d5);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB expand(double x, double y, double z) {
/*  80 */     double d0 = this.minX - x;
/*  81 */     double d1 = this.minY - y;
/*  82 */     double d2 = this.minZ - z;
/*  83 */     double d3 = this.maxX + x;
/*  84 */     double d4 = this.maxY + y;
/*  85 */     double d5 = this.maxZ + z;
/*  86 */     return new AxisAlignedBB(d0, d1, d2, d3, d4, d5);
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB union(AxisAlignedBB other) {
/*  91 */     double d0 = Math.min(this.minX, other.minX);
/*  92 */     double d1 = Math.min(this.minY, other.minY);
/*  93 */     double d2 = Math.min(this.minZ, other.minZ);
/*  94 */     double d3 = Math.max(this.maxX, other.maxX);
/*  95 */     double d4 = Math.max(this.maxY, other.maxY);
/*  96 */     double d5 = Math.max(this.maxZ, other.maxZ);
/*  97 */     return new AxisAlignedBB(d0, d1, d2, d3, d4, d5);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AxisAlignedBB fromBounds(double x1, double y1, double z1, double x2, double y2, double z2) {
/* 105 */     double d0 = Math.min(x1, x2);
/* 106 */     double d1 = Math.min(y1, y2);
/* 107 */     double d2 = Math.min(z1, z2);
/* 108 */     double d3 = Math.max(x1, x2);
/* 109 */     double d4 = Math.max(y1, y2);
/* 110 */     double d5 = Math.max(z1, z2);
/* 111 */     return new AxisAlignedBB(d0, d1, d2, d3, d4, d5);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB offset(double x, double y, double z) {
/* 119 */     return new AxisAlignedBB(this.minX + x, this.minY + y, this.minZ + z, this.maxX + x, this.maxY + y, this.maxZ + z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double calculateXOffset(AxisAlignedBB other, double offsetX) {
/* 129 */     if (other.maxY > this.minY && other.minY < this.maxY && other.maxZ > this.minZ && other.minZ < this.maxZ) {
/*     */       
/* 131 */       if (offsetX > 0.0D && other.maxX <= this.minX) {
/*     */         
/* 133 */         double d1 = this.minX - other.maxX;
/*     */         
/* 135 */         if (d1 < offsetX)
/*     */         {
/* 137 */           offsetX = d1;
/*     */         }
/*     */       }
/* 140 */       else if (offsetX < 0.0D && other.minX >= this.maxX) {
/*     */         
/* 142 */         double d0 = this.maxX - other.minX;
/*     */         
/* 144 */         if (d0 > offsetX)
/*     */         {
/* 146 */           offsetX = d0;
/*     */         }
/*     */       } 
/*     */       
/* 150 */       return offsetX;
/*     */     } 
/*     */ 
/*     */     
/* 154 */     return offsetX;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double calculateYOffset(AxisAlignedBB other, double offsetY) {
/* 165 */     if (other.maxX > this.minX && other.minX < this.maxX && other.maxZ > this.minZ && other.minZ < this.maxZ) {
/*     */       
/* 167 */       if (offsetY > 0.0D && other.maxY <= this.minY) {
/*     */         
/* 169 */         double d1 = this.minY - other.maxY;
/*     */         
/* 171 */         if (d1 < offsetY)
/*     */         {
/* 173 */           offsetY = d1;
/*     */         }
/*     */       }
/* 176 */       else if (offsetY < 0.0D && other.minY >= this.maxY) {
/*     */         
/* 178 */         double d0 = this.maxY - other.minY;
/*     */         
/* 180 */         if (d0 > offsetY)
/*     */         {
/* 182 */           offsetY = d0;
/*     */         }
/*     */       } 
/*     */       
/* 186 */       return offsetY;
/*     */     } 
/*     */ 
/*     */     
/* 190 */     return offsetY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double calculateZOffset(AxisAlignedBB other, double offsetZ) {
/* 201 */     if (other.maxX > this.minX && other.minX < this.maxX && other.maxY > this.minY && other.minY < this.maxY) {
/*     */       
/* 203 */       if (offsetZ > 0.0D && other.maxZ <= this.minZ) {
/*     */         
/* 205 */         double d1 = this.minZ - other.maxZ;
/*     */         
/* 207 */         if (d1 < offsetZ)
/*     */         {
/* 209 */           offsetZ = d1;
/*     */         }
/*     */       }
/* 212 */       else if (offsetZ < 0.0D && other.minZ >= this.maxZ) {
/*     */         
/* 214 */         double d0 = this.maxZ - other.minZ;
/*     */         
/* 216 */         if (d0 > offsetZ)
/*     */         {
/* 218 */           offsetZ = d0;
/*     */         }
/*     */       } 
/*     */       
/* 222 */       return offsetZ;
/*     */     } 
/*     */ 
/*     */     
/* 226 */     return offsetZ;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean intersectsWith(AxisAlignedBB other) {
/* 235 */     return (other.maxX > this.minX && other.minX < this.maxX) ? ((other.maxY > this.minY && other.minY < this.maxY) ? ((other.maxZ > this.minZ && other.minZ < this.maxZ)) : false) : false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isVecInside(Vec3 vec) {
/* 243 */     return (vec.xCoord > this.minX && vec.xCoord < this.maxX) ? ((vec.yCoord > this.minY && vec.yCoord < this.maxY) ? ((vec.zCoord > this.minZ && vec.zCoord < this.maxZ)) : false) : false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getAverageEdgeLength() {
/* 251 */     double d0 = this.maxX - this.minX;
/* 252 */     double d1 = this.maxY - this.minY;
/* 253 */     double d2 = this.maxZ - this.minZ;
/* 254 */     return (d0 + d1 + d2) / 3.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB contract(double x, double y, double z) {
/* 262 */     double d0 = this.minX + x;
/* 263 */     double d1 = this.minY + y;
/* 264 */     double d2 = this.minZ + z;
/* 265 */     double d3 = this.maxX - x;
/* 266 */     double d4 = this.maxY - y;
/* 267 */     double d5 = this.maxZ - z;
/* 268 */     return new AxisAlignedBB(d0, d1, d2, d3, d4, d5);
/*     */   }
/*     */ 
/*     */   
/*     */   public MovingObjectPosition calculateIntercept(Vec3 vecA, Vec3 vecB) {
/* 273 */     Vec3 vec3 = vecA.getIntermediateWithXValue(vecB, this.minX);
/* 274 */     Vec3 vec31 = vecA.getIntermediateWithXValue(vecB, this.maxX);
/* 275 */     Vec3 vec32 = vecA.getIntermediateWithYValue(vecB, this.minY);
/* 276 */     Vec3 vec33 = vecA.getIntermediateWithYValue(vecB, this.maxY);
/* 277 */     Vec3 vec34 = vecA.getIntermediateWithZValue(vecB, this.minZ);
/* 278 */     Vec3 vec35 = vecA.getIntermediateWithZValue(vecB, this.maxZ);
/*     */     
/* 280 */     if (!isVecInYZ(vec3))
/*     */     {
/* 282 */       vec3 = null;
/*     */     }
/*     */     
/* 285 */     if (!isVecInYZ(vec31))
/*     */     {
/* 287 */       vec31 = null;
/*     */     }
/*     */     
/* 290 */     if (!isVecInXZ(vec32))
/*     */     {
/* 292 */       vec32 = null;
/*     */     }
/*     */     
/* 295 */     if (!isVecInXZ(vec33))
/*     */     {
/* 297 */       vec33 = null;
/*     */     }
/*     */     
/* 300 */     if (!isVecInXY(vec34))
/*     */     {
/* 302 */       vec34 = null;
/*     */     }
/*     */     
/* 305 */     if (!isVecInXY(vec35))
/*     */     {
/* 307 */       vec35 = null;
/*     */     }
/*     */     
/* 310 */     Vec3 vec36 = null;
/*     */     
/* 312 */     if (vec3 != null)
/*     */     {
/* 314 */       vec36 = vec3;
/*     */     }
/*     */     
/* 317 */     if (vec31 != null && (vec36 == null || vecA.squareDistanceTo(vec31) < vecA.squareDistanceTo(vec36)))
/*     */     {
/* 319 */       vec36 = vec31;
/*     */     }
/*     */     
/* 322 */     if (vec32 != null && (vec36 == null || vecA.squareDistanceTo(vec32) < vecA.squareDistanceTo(vec36)))
/*     */     {
/* 324 */       vec36 = vec32;
/*     */     }
/*     */     
/* 327 */     if (vec33 != null && (vec36 == null || vecA.squareDistanceTo(vec33) < vecA.squareDistanceTo(vec36)))
/*     */     {
/* 329 */       vec36 = vec33;
/*     */     }
/*     */     
/* 332 */     if (vec34 != null && (vec36 == null || vecA.squareDistanceTo(vec34) < vecA.squareDistanceTo(vec36)))
/*     */     {
/* 334 */       vec36 = vec34;
/*     */     }
/*     */     
/* 337 */     if (vec35 != null && (vec36 == null || vecA.squareDistanceTo(vec35) < vecA.squareDistanceTo(vec36)))
/*     */     {
/* 339 */       vec36 = vec35;
/*     */     }
/*     */     
/* 342 */     if (vec36 == null)
/*     */     {
/* 344 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 348 */     EnumFacing enumfacing = null;
/*     */     
/* 350 */     if (vec36 == vec3) {
/*     */       
/* 352 */       enumfacing = EnumFacing.WEST;
/*     */     }
/* 354 */     else if (vec36 == vec31) {
/*     */       
/* 356 */       enumfacing = EnumFacing.EAST;
/*     */     }
/* 358 */     else if (vec36 == vec32) {
/*     */       
/* 360 */       enumfacing = EnumFacing.DOWN;
/*     */     }
/* 362 */     else if (vec36 == vec33) {
/*     */       
/* 364 */       enumfacing = EnumFacing.UP;
/*     */     }
/* 366 */     else if (vec36 == vec34) {
/*     */       
/* 368 */       enumfacing = EnumFacing.NORTH;
/*     */     }
/*     */     else {
/*     */       
/* 372 */       enumfacing = EnumFacing.SOUTH;
/*     */     } 
/*     */     
/* 375 */     return new MovingObjectPosition(vec36, enumfacing);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isVecInYZ(Vec3 vec) {
/* 384 */     return (vec == null) ? false : ((vec.yCoord >= this.minY && vec.yCoord <= this.maxY && vec.zCoord >= this.minZ && vec.zCoord <= this.maxZ));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isVecInXZ(Vec3 vec) {
/* 392 */     return (vec == null) ? false : ((vec.xCoord >= this.minX && vec.xCoord <= this.maxX && vec.zCoord >= this.minZ && vec.zCoord <= this.maxZ));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isVecInXY(Vec3 vec) {
/* 400 */     return (vec == null) ? false : ((vec.xCoord >= this.minX && vec.xCoord <= this.maxX && vec.yCoord >= this.minY && vec.yCoord <= this.maxY));
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 405 */     return "box[" + this.minX + ", " + this.minY + ", " + this.minZ + " -> " + this.maxX + ", " + this.maxY + ", " + this.maxZ + "]";
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_181656_b() {
/* 410 */     return !(!Double.isNaN(this.minX) && !Double.isNaN(this.minY) && !Double.isNaN(this.minZ) && !Double.isNaN(this.maxX) && !Double.isNaN(this.maxY) && !Double.isNaN(this.maxZ));
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraf\\util\AxisAlignedBB.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */