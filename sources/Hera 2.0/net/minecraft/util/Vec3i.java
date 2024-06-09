/*     */ package net.minecraft.util;
/*     */ 
/*     */ import com.google.common.base.Objects;
/*     */ 
/*     */ public class Vec3i
/*     */   implements Comparable<Vec3i>
/*     */ {
/*   8 */   public static final Vec3i NULL_VECTOR = new Vec3i(0, 0, 0);
/*     */ 
/*     */   
/*     */   private final int x;
/*     */ 
/*     */   
/*     */   private final int y;
/*     */ 
/*     */   
/*     */   private final int z;
/*     */ 
/*     */   
/*     */   public Vec3i(int xIn, int yIn, int zIn) {
/*  21 */     this.x = xIn;
/*  22 */     this.y = yIn;
/*  23 */     this.z = zIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public Vec3i(double xIn, double yIn, double zIn) {
/*  28 */     this(MathHelper.floor_double(xIn), MathHelper.floor_double(yIn), MathHelper.floor_double(zIn));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object p_equals_1_) {
/*  33 */     if (this == p_equals_1_)
/*     */     {
/*  35 */       return true;
/*     */     }
/*  37 */     if (!(p_equals_1_ instanceof Vec3i))
/*     */     {
/*  39 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  43 */     Vec3i vec3i = (Vec3i)p_equals_1_;
/*  44 */     return (getX() != vec3i.getX()) ? false : ((getY() != vec3i.getY()) ? false : ((getZ() == vec3i.getZ())));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  50 */     return (getY() + getZ() * 31) * 31 + getX();
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(Vec3i p_compareTo_1_) {
/*  55 */     return (getY() == p_compareTo_1_.getY()) ? ((getZ() == p_compareTo_1_.getZ()) ? (getX() - p_compareTo_1_.getX()) : (getZ() - p_compareTo_1_.getZ())) : (getY() - p_compareTo_1_.getY());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getX() {
/*  63 */     return this.x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getY() {
/*  71 */     return this.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getZ() {
/*  79 */     return this.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vec3i crossProduct(Vec3i vec) {
/*  87 */     return new Vec3i(getY() * vec.getZ() - getZ() * vec.getY(), getZ() * vec.getX() - getX() * vec.getZ(), getX() * vec.getY() - getY() * vec.getX());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double distanceSq(double toX, double toY, double toZ) {
/*  95 */     double d0 = getX() - toX;
/*  96 */     double d1 = getY() - toY;
/*  97 */     double d2 = getZ() - toZ;
/*  98 */     return d0 * d0 + d1 * d1 + d2 * d2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double distanceSqToCenter(double xIn, double yIn, double zIn) {
/* 106 */     double d0 = getX() + 0.5D - xIn;
/* 107 */     double d1 = getY() + 0.5D - yIn;
/* 108 */     double d2 = getZ() + 0.5D - zIn;
/* 109 */     return d0 * d0 + d1 * d1 + d2 * d2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double distanceSq(Vec3i to) {
/* 117 */     return distanceSq(to.getX(), to.getY(), to.getZ());
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 122 */     return Objects.toStringHelper(this).add("x", getX()).add("y", getY()).add("z", getZ()).toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraf\\util\Vec3i.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */