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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Curve
/*     */   extends Shape
/*     */ {
/*     */   private Vector2f p1;
/*     */   private Vector2f c1;
/*     */   private Vector2f c2;
/*     */   private Vector2f p2;
/*     */   private int segments;
/*     */   
/*     */   public Curve(Vector2f p1, Vector2f c1, Vector2f c2, Vector2f p2) {
/*  31 */     this(p1, c1, c2, p2, 20);
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
/*     */   public Curve(Vector2f p1, Vector2f c1, Vector2f c2, Vector2f p2, int segments) {
/*  44 */     this.p1 = new Vector2f(p1);
/*  45 */     this.c1 = new Vector2f(c1);
/*  46 */     this.c2 = new Vector2f(c2);
/*  47 */     this.p2 = new Vector2f(p2);
/*     */     
/*  49 */     this.segments = segments;
/*  50 */     this.pointsDirty = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector2f pointAt(float t) {
/*  60 */     float a = 1.0F - t;
/*  61 */     float b = t;
/*     */     
/*  63 */     float f1 = a * a * a;
/*  64 */     float f2 = 3.0F * a * a * b;
/*  65 */     float f3 = 3.0F * a * b * b;
/*  66 */     float f4 = b * b * b;
/*     */     
/*  68 */     float nx = this.p1.x * f1 + this.c1.x * f2 + this.c2.x * f3 + this.p2.x * f4;
/*  69 */     float ny = this.p1.y * f1 + this.c1.y * f2 + this.c2.y * f3 + this.p2.y * f4;
/*     */     
/*  71 */     return new Vector2f(nx, ny);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void createPoints() {
/*  78 */     float step = 1.0F / this.segments;
/*  79 */     this.points = new float[(this.segments + 1) * 2];
/*  80 */     for (int i = 0; i < this.segments + 1; i++) {
/*  81 */       float t = i * step;
/*     */       
/*  83 */       Vector2f p = pointAt(t);
/*  84 */       this.points[i * 2] = p.x;
/*  85 */       this.points[i * 2 + 1] = p.y;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Shape transform(Transform transform) {
/*  93 */     float[] pts = new float[8];
/*  94 */     float[] dest = new float[8];
/*  95 */     pts[0] = this.p1.x; pts[1] = this.p1.y;
/*  96 */     pts[2] = this.c1.x; pts[3] = this.c1.y;
/*  97 */     pts[4] = this.c2.x; pts[5] = this.c2.y;
/*  98 */     pts[6] = this.p2.x; pts[7] = this.p2.y;
/*  99 */     transform.transform(pts, 0, dest, 0, 4);
/*     */     
/* 101 */     return new Curve(new Vector2f(dest[0], dest[1]), new Vector2f(dest[2], dest[3]), new Vector2f(dest[4], dest[5]), new Vector2f(dest[6], dest[7]));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean closed() {
/* 111 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\geom\Curve.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */