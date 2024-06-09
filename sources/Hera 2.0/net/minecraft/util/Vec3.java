/*     */ package net.minecraft.util;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Vec3
/*     */ {
/*     */   public final double xCoord;
/*     */   public final double yCoord;
/*     */   public final double zCoord;
/*     */   
/*     */   public Vec3(double x, double y, double z) {
/*  16 */     if (x == -0.0D)
/*     */     {
/*  18 */       x = 0.0D;
/*     */     }
/*     */     
/*  21 */     if (y == -0.0D)
/*     */     {
/*  23 */       y = 0.0D;
/*     */     }
/*     */     
/*  26 */     if (z == -0.0D)
/*     */     {
/*  28 */       z = 0.0D;
/*     */     }
/*     */     
/*  31 */     this.xCoord = x;
/*  32 */     this.yCoord = y;
/*  33 */     this.zCoord = z;
/*     */   }
/*     */ 
/*     */   
/*     */   public Vec3(Vec3i p_i46377_1_) {
/*  38 */     this(p_i46377_1_.getX(), p_i46377_1_.getY(), p_i46377_1_.getZ());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vec3 subtractReverse(Vec3 vec) {
/*  46 */     return new Vec3(vec.xCoord - this.xCoord, vec.yCoord - this.yCoord, vec.zCoord - this.zCoord);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vec3 normalize() {
/*  54 */     double d0 = MathHelper.sqrt_double(this.xCoord * this.xCoord + this.yCoord * this.yCoord + this.zCoord * this.zCoord);
/*  55 */     return (d0 < 1.0E-4D) ? new Vec3(0.0D, 0.0D, 0.0D) : new Vec3(this.xCoord / d0, this.yCoord / d0, this.zCoord / d0);
/*     */   }
/*     */ 
/*     */   
/*     */   public double dotProduct(Vec3 vec) {
/*  60 */     return this.xCoord * vec.xCoord + this.yCoord * vec.yCoord + this.zCoord * vec.zCoord;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vec3 crossProduct(Vec3 vec) {
/*  68 */     return new Vec3(this.yCoord * vec.zCoord - this.zCoord * vec.yCoord, this.zCoord * vec.xCoord - this.xCoord * vec.zCoord, this.xCoord * vec.yCoord - this.yCoord * vec.xCoord);
/*     */   }
/*     */ 
/*     */   
/*     */   public Vec3 subtract(Vec3 vec) {
/*  73 */     return subtract(vec.xCoord, vec.yCoord, vec.zCoord);
/*     */   }
/*     */ 
/*     */   
/*     */   public Vec3 subtract(double x, double y, double z) {
/*  78 */     return addVector(-x, -y, -z);
/*     */   }
/*     */ 
/*     */   
/*     */   public Vec3 add(Vec3 vec) {
/*  83 */     return addVector(vec.xCoord, vec.yCoord, vec.zCoord);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vec3 addVector(double x, double y, double z) {
/*  92 */     return new Vec3(this.xCoord + x, this.yCoord + y, this.zCoord + z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double distanceTo(Vec3 vec) {
/* 100 */     double d0 = vec.xCoord - this.xCoord;
/* 101 */     double d1 = vec.yCoord - this.yCoord;
/* 102 */     double d2 = vec.zCoord - this.zCoord;
/* 103 */     return MathHelper.sqrt_double(d0 * d0 + d1 * d1 + d2 * d2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double squareDistanceTo(Vec3 vec) {
/* 111 */     double d0 = vec.xCoord - this.xCoord;
/* 112 */     double d1 = vec.yCoord - this.yCoord;
/* 113 */     double d2 = vec.zCoord - this.zCoord;
/* 114 */     return d0 * d0 + d1 * d1 + d2 * d2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double lengthVector() {
/* 122 */     return MathHelper.sqrt_double(this.xCoord * this.xCoord + this.yCoord * this.yCoord + this.zCoord * this.zCoord);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vec3 getIntermediateWithXValue(Vec3 vec, double x) {
/* 131 */     double d0 = vec.xCoord - this.xCoord;
/* 132 */     double d1 = vec.yCoord - this.yCoord;
/* 133 */     double d2 = vec.zCoord - this.zCoord;
/*     */     
/* 135 */     if (d0 * d0 < 1.0000000116860974E-7D)
/*     */     {
/* 137 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 141 */     double d3 = (x - this.xCoord) / d0;
/* 142 */     return (d3 >= 0.0D && d3 <= 1.0D) ? new Vec3(this.xCoord + d0 * d3, this.yCoord + d1 * d3, this.zCoord + d2 * d3) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vec3 getIntermediateWithYValue(Vec3 vec, double y) {
/* 152 */     double d0 = vec.xCoord - this.xCoord;
/* 153 */     double d1 = vec.yCoord - this.yCoord;
/* 154 */     double d2 = vec.zCoord - this.zCoord;
/*     */     
/* 156 */     if (d1 * d1 < 1.0000000116860974E-7D)
/*     */     {
/* 158 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 162 */     double d3 = (y - this.yCoord) / d1;
/* 163 */     return (d3 >= 0.0D && d3 <= 1.0D) ? new Vec3(this.xCoord + d0 * d3, this.yCoord + d1 * d3, this.zCoord + d2 * d3) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vec3 getIntermediateWithZValue(Vec3 vec, double z) {
/* 173 */     double d0 = vec.xCoord - this.xCoord;
/* 174 */     double d1 = vec.yCoord - this.yCoord;
/* 175 */     double d2 = vec.zCoord - this.zCoord;
/*     */     
/* 177 */     if (d2 * d2 < 1.0000000116860974E-7D)
/*     */     {
/* 179 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 183 */     double d3 = (z - this.zCoord) / d2;
/* 184 */     return (d3 >= 0.0D && d3 <= 1.0D) ? new Vec3(this.xCoord + d0 * d3, this.yCoord + d1 * d3, this.zCoord + d2 * d3) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 190 */     return "(" + this.xCoord + ", " + this.yCoord + ", " + this.zCoord + ")";
/*     */   }
/*     */ 
/*     */   
/*     */   public Vec3 rotatePitch(float pitch) {
/* 195 */     float f = MathHelper.cos(pitch);
/* 196 */     float f1 = MathHelper.sin(pitch);
/* 197 */     double d0 = this.xCoord;
/* 198 */     double d1 = this.yCoord * f + this.zCoord * f1;
/* 199 */     double d2 = this.zCoord * f - this.yCoord * f1;
/* 200 */     return new Vec3(d0, d1, d2);
/*     */   }
/*     */ 
/*     */   
/*     */   public Vec3 rotateYaw(float yaw) {
/* 205 */     float f = MathHelper.cos(yaw);
/* 206 */     float f1 = MathHelper.sin(yaw);
/* 207 */     double d0 = this.xCoord * f + this.zCoord * f1;
/* 208 */     double d1 = this.yCoord;
/* 209 */     double d2 = this.zCoord * f - this.xCoord * f1;
/* 210 */     return new Vec3(d0, d1, d2);
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraf\\util\Vec3.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */