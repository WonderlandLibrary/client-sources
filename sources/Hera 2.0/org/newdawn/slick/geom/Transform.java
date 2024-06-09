/*     */ package org.newdawn.slick.geom;
/*     */ 
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
/*     */ public class Transform
/*     */ {
/*     */   private float[] matrixPosition;
/*     */   
/*     */   public Transform() {
/*  25 */     this.matrixPosition = new float[] { 1.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 1.0F };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Transform(Transform other) {
/*  34 */     this.matrixPosition = new float[9];
/*  35 */     for (int i = 0; i < 9; i++) {
/*  36 */       this.matrixPosition[i] = other.matrixPosition[i];
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Transform(Transform t1, Transform t2) {
/*  47 */     this(t1);
/*  48 */     concatenate(t2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Transform(float[] matrixPosition) {
/*  58 */     if (matrixPosition.length != 6) {
/*  59 */       throw new RuntimeException("The parameter must be a float array of length 6.");
/*     */     }
/*  61 */     this.matrixPosition = new float[] { matrixPosition[0], matrixPosition[1], matrixPosition[2], matrixPosition[3], matrixPosition[4], matrixPosition[5], 0.0F, 0.0F, 1.0F };
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
/*     */   public Transform(float point00, float point01, float point02, float point10, float point11, float point12) {
/*  77 */     this.matrixPosition = new float[] { point00, point01, point02, point10, point11, point12, 0.0F, 0.0F, 1.0F };
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
/*     */   public void transform(float[] source, int sourceOffset, float[] destination, int destOffset, int numberOfPoints) {
/*  94 */     float[] result = (source == destination) ? new float[numberOfPoints * 2] : destination;
/*     */     int i;
/*  96 */     for (i = 0; i < numberOfPoints * 2; i += 2) {
/*  97 */       for (int j = 0; j < 6; j += 3) {
/*  98 */         result[i + j / 3] = source[i + sourceOffset] * this.matrixPosition[j] + source[i + sourceOffset + 1] * this.matrixPosition[j + 1] + 1.0F * this.matrixPosition[j + 2];
/*     */       }
/*     */     } 
/*     */     
/* 102 */     if (source == destination)
/*     */     {
/* 104 */       for (i = 0; i < numberOfPoints * 2; i += 2) {
/* 105 */         destination[i + destOffset] = result[i];
/* 106 */         destination[i + destOffset + 1] = result[i + 1];
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Transform concatenate(Transform tx) {
/* 118 */     float[] mp = new float[9];
/* 119 */     float n00 = this.matrixPosition[0] * tx.matrixPosition[0] + this.matrixPosition[1] * tx.matrixPosition[3];
/* 120 */     float n01 = this.matrixPosition[0] * tx.matrixPosition[1] + this.matrixPosition[1] * tx.matrixPosition[4];
/* 121 */     float n02 = this.matrixPosition[0] * tx.matrixPosition[2] + this.matrixPosition[1] * tx.matrixPosition[5] + this.matrixPosition[2];
/* 122 */     float n10 = this.matrixPosition[3] * tx.matrixPosition[0] + this.matrixPosition[4] * tx.matrixPosition[3];
/* 123 */     float n11 = this.matrixPosition[3] * tx.matrixPosition[1] + this.matrixPosition[4] * tx.matrixPosition[4];
/* 124 */     float n12 = this.matrixPosition[3] * tx.matrixPosition[2] + this.matrixPosition[4] * tx.matrixPosition[5] + this.matrixPosition[5];
/* 125 */     mp[0] = n00;
/* 126 */     mp[1] = n01;
/* 127 */     mp[2] = n02;
/* 128 */     mp[3] = n10;
/* 129 */     mp[4] = n11;
/* 130 */     mp[5] = n12;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 139 */     this.matrixPosition = mp;
/* 140 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 150 */     String result = "Transform[[" + this.matrixPosition[0] + "," + this.matrixPosition[1] + "," + this.matrixPosition[2] + "][" + this.matrixPosition[3] + "," + this.matrixPosition[4] + "," + this.matrixPosition[5] + "][" + this.matrixPosition[6] + "," + this.matrixPosition[7] + "," + this.matrixPosition[8] + "]]";
/*     */ 
/*     */ 
/*     */     
/* 154 */     return result.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] getMatrixPosition() {
/* 163 */     return this.matrixPosition;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Transform createRotateTransform(float angle) {
/* 173 */     return new Transform((float)FastTrig.cos(angle), -((float)FastTrig.sin(angle)), 0.0F, (float)FastTrig.sin(angle), (float)FastTrig.cos(angle), 0.0F);
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
/*     */   public static Transform createRotateTransform(float angle, float x, float y) {
/* 185 */     Transform temp = createRotateTransform(angle);
/* 186 */     float sinAngle = temp.matrixPosition[3];
/* 187 */     float oneMinusCosAngle = 1.0F - temp.matrixPosition[4];
/* 188 */     temp.matrixPosition[2] = x * oneMinusCosAngle + y * sinAngle;
/* 189 */     temp.matrixPosition[5] = y * oneMinusCosAngle - x * sinAngle;
/*     */     
/* 191 */     return temp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Transform createTranslateTransform(float xOffset, float yOffset) {
/* 202 */     return new Transform(1.0F, 0.0F, xOffset, 0.0F, 1.0F, yOffset);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Transform createScaleTransform(float xScale, float yScale) {
/* 213 */     return new Transform(xScale, 0.0F, 0.0F, 0.0F, yScale, 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector2f transform(Vector2f pt) {
/* 223 */     float[] in = { pt.x, pt.y };
/* 224 */     float[] out = new float[2];
/*     */     
/* 226 */     transform(in, 0, out, 0, 1);
/*     */     
/* 228 */     return new Vector2f(out[0], out[1]);
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\geom\Transform.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */