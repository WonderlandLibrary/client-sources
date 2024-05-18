/*     */ package org.newdawn.slick.geom;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import org.newdawn.slick.util.FastTrig;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Vector2f
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1339934L;
/*     */   public float x;
/*     */   public float y;
/*     */   
/*     */   public strictfp Vector2f() {}
/*     */   
/*     */   public strictfp Vector2f(float[] coords) {
/*  33 */     this.x = coords[0];
/*  34 */     this.y = coords[1];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public strictfp Vector2f(double theta) {
/*  43 */     this.x = 1.0F;
/*  44 */     this.y = 0.0F;
/*  45 */     setTheta(theta);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public strictfp void setTheta(double theta) {
/*  56 */     if (theta < -360.0D || theta > 360.0D) {
/*  57 */       theta %= 360.0D;
/*     */     }
/*  59 */     if (theta < 0.0D) {
/*  60 */       theta = 360.0D + theta;
/*     */     }
/*  62 */     double oldTheta = getTheta();
/*  63 */     if (theta < -360.0D || theta > 360.0D) {
/*  64 */       oldTheta %= 360.0D;
/*     */     }
/*  66 */     if (theta < 0.0D) {
/*  67 */       oldTheta = 360.0D + oldTheta;
/*     */     }
/*     */     
/*  70 */     float len = length();
/*  71 */     this.x = len * (float)FastTrig.cos(StrictMath.toRadians(theta));
/*  72 */     this.y = len * (float)FastTrig.sin(StrictMath.toRadians(theta));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public strictfp Vector2f add(double theta) {
/*  89 */     setTheta(getTheta() + theta);
/*     */     
/*  91 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public strictfp Vector2f sub(double theta) {
/* 101 */     setTheta(getTheta() - theta);
/*     */     
/* 103 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public strictfp double getTheta() {
/* 112 */     double theta = StrictMath.toDegrees(StrictMath.atan2(this.y, this.x));
/* 113 */     if (theta < -360.0D || theta > 360.0D) {
/* 114 */       theta %= 360.0D;
/*     */     }
/* 116 */     if (theta < 0.0D) {
/* 117 */       theta = 360.0D + theta;
/*     */     }
/*     */     
/* 120 */     return theta;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public strictfp float getX() {
/* 129 */     return this.x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public strictfp float getY() {
/* 138 */     return this.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public strictfp Vector2f(Vector2f other) {
/* 147 */     this(other.getX(), other.getY());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public strictfp Vector2f(float x, float y) {
/* 157 */     this.x = x;
/* 158 */     this.y = y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public strictfp void set(Vector2f other) {
/* 167 */     set(other.getX(), other.getY());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public strictfp float dot(Vector2f other) {
/* 177 */     return this.x * other.getX() + this.y * other.getY();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public strictfp Vector2f set(float x, float y) {
/* 188 */     this.x = x;
/* 189 */     this.y = y;
/*     */     
/* 191 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public strictfp Vector2f getPerpendicular() {
/* 200 */     return new Vector2f(-this.y, this.x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public strictfp Vector2f set(float[] pt) {
/* 210 */     return set(pt[0], pt[1]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public strictfp Vector2f negate() {
/* 219 */     return new Vector2f(-this.x, -this.y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public strictfp Vector2f negateLocal() {
/* 228 */     this.x = -this.x;
/* 229 */     this.y = -this.y;
/*     */     
/* 231 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public strictfp Vector2f add(Vector2f v) {
/* 242 */     this.x += v.getX();
/* 243 */     this.y += v.getY();
/*     */     
/* 245 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public strictfp Vector2f sub(Vector2f v) {
/* 256 */     this.x -= v.getX();
/* 257 */     this.y -= v.getY();
/*     */     
/* 259 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public strictfp Vector2f scale(float a) {
/* 270 */     this.x *= a;
/* 271 */     this.y *= a;
/*     */     
/* 273 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public strictfp Vector2f normalise() {
/* 282 */     float l = length();
/*     */     
/* 284 */     if (l == 0.0F) {
/* 285 */       return this;
/*     */     }
/*     */     
/* 288 */     this.x /= l;
/* 289 */     this.y /= l;
/* 290 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public strictfp Vector2f getNormal() {
/* 299 */     Vector2f cp = copy();
/* 300 */     cp.normalise();
/* 301 */     return cp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public strictfp float lengthSquared() {
/* 310 */     return this.x * this.x + this.y * this.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public strictfp float length() {
/* 320 */     return (float)Math.sqrt(lengthSquared());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public strictfp void projectOntoUnit(Vector2f b, Vector2f result) {
/* 330 */     float dp = b.dot(this);
/*     */     
/* 332 */     result.x = dp * b.getX();
/* 333 */     result.y = dp * b.getY();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public strictfp Vector2f copy() {
/* 343 */     return new Vector2f(this.x, this.y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public strictfp String toString() {
/* 350 */     return "[Vector2f " + this.x + "," + this.y + " (" + length() + ")]";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public strictfp float distance(Vector2f other) {
/* 360 */     return (float)Math.sqrt(distanceSquared(other));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public strictfp float distanceSquared(Vector2f other) {
/* 372 */     float dx = other.getX() - getX();
/* 373 */     float dy = other.getY() - getY();
/*     */     
/* 375 */     return dx * dx + dy * dy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public strictfp int hashCode() {
/* 382 */     return 997 * (int)this.x ^ 991 * (int)this.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public strictfp boolean equals(Object other) {
/* 389 */     if (other instanceof Vector2f) {
/* 390 */       Vector2f o = (Vector2f)other;
/* 391 */       return (o.x == this.x && o.y == this.y);
/*     */     } 
/*     */     
/* 394 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\geom\Vector2f.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */