/*     */ package org.newdawn.slick.geom;
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
/*     */ public class OverTriangulator
/*     */   implements Triangulator
/*     */ {
/*     */   private float[][] triangles;
/*     */   
/*     */   public OverTriangulator(Triangulator tris) {
/*  19 */     this.triangles = new float[tris.getTriangleCount() * 6 * 3][2];
/*     */     
/*  21 */     int tcount = 0;
/*  22 */     for (int i = 0; i < tris.getTriangleCount(); i++) {
/*  23 */       float cx = 0.0F;
/*  24 */       float cy = 0.0F; int p;
/*  25 */       for (p = 0; p < 3; p++) {
/*  26 */         float[] pt = tris.getTrianglePoint(i, p);
/*  27 */         cx += pt[0];
/*  28 */         cy += pt[1];
/*     */       } 
/*     */       
/*  31 */       cx /= 3.0F;
/*  32 */       cy /= 3.0F;
/*     */       
/*  34 */       for (p = 0; p < 3; p++) {
/*  35 */         int n = p + 1;
/*  36 */         if (n > 2) {
/*  37 */           n = 0;
/*     */         }
/*     */         
/*  40 */         float[] pt1 = tris.getTrianglePoint(i, p);
/*  41 */         float[] pt2 = tris.getTrianglePoint(i, n);
/*     */         
/*  43 */         pt1[0] = (pt1[0] + pt2[0]) / 2.0F;
/*  44 */         pt1[1] = (pt1[1] + pt2[1]) / 2.0F;
/*     */         
/*  46 */         this.triangles[tcount * 3 + 0][0] = cx;
/*  47 */         this.triangles[tcount * 3 + 0][1] = cy;
/*  48 */         this.triangles[tcount * 3 + 1][0] = pt1[0];
/*  49 */         this.triangles[tcount * 3 + 1][1] = pt1[1];
/*  50 */         this.triangles[tcount * 3 + 2][0] = pt2[0];
/*  51 */         this.triangles[tcount * 3 + 2][1] = pt2[1];
/*  52 */         tcount++;
/*     */       } 
/*     */       
/*  55 */       for (p = 0; p < 3; p++) {
/*  56 */         int n = p + 1;
/*  57 */         if (n > 2) {
/*  58 */           n = 0;
/*     */         }
/*     */         
/*  61 */         float[] pt1 = tris.getTrianglePoint(i, p);
/*  62 */         float[] pt2 = tris.getTrianglePoint(i, n);
/*     */         
/*  64 */         pt2[0] = (pt1[0] + pt2[0]) / 2.0F;
/*  65 */         pt2[1] = (pt1[1] + pt2[1]) / 2.0F;
/*     */         
/*  67 */         this.triangles[tcount * 3 + 0][0] = cx;
/*  68 */         this.triangles[tcount * 3 + 0][1] = cy;
/*  69 */         this.triangles[tcount * 3 + 1][0] = pt1[0];
/*  70 */         this.triangles[tcount * 3 + 1][1] = pt1[1];
/*  71 */         this.triangles[tcount * 3 + 2][0] = pt2[0];
/*  72 */         this.triangles[tcount * 3 + 2][1] = pt2[1];
/*  73 */         tcount++;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addPolyPoint(float x, float y) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTriangleCount() {
/*  88 */     return this.triangles.length / 3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] getTrianglePoint(int tri, int i) {
/*  95 */     float[] pt = this.triangles[tri * 3 + i];
/*     */     
/*  97 */     return new float[] { pt[0], pt[1] };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startHole() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean triangulate() {
/* 110 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\geom\OverTriangulator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */