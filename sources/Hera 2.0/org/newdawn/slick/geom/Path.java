/*     */ package org.newdawn.slick.geom;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Path
/*     */   extends Shape
/*     */ {
/*  13 */   private ArrayList localPoints = new ArrayList();
/*     */   
/*     */   private float cx;
/*     */   
/*     */   private float cy;
/*     */   
/*     */   private boolean closed;
/*     */   
/*  21 */   private ArrayList holes = new ArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ArrayList hole;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Path(float sx, float sy) {
/*  32 */     this.localPoints.add(new float[] { sx, sy });
/*  33 */     this.cx = sx;
/*  34 */     this.cy = sy;
/*  35 */     this.pointsDirty = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startHole(float sx, float sy) {
/*  45 */     this.hole = new ArrayList();
/*  46 */     this.holes.add(this.hole);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void lineTo(float x, float y) {
/*  57 */     if (this.hole != null) {
/*  58 */       this.hole.add(new float[] { x, y });
/*     */     } else {
/*  60 */       this.localPoints.add(new float[] { x, y });
/*     */     } 
/*  62 */     this.cx = x;
/*  63 */     this.cy = y;
/*  64 */     this.pointsDirty = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() {
/*  71 */     this.closed = true;
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
/*     */   public void curveTo(float x, float y, float cx1, float cy1, float cx2, float cy2) {
/*  85 */     curveTo(x, y, cx1, cy1, cx2, cy2, 10);
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
/*     */   public void curveTo(float x, float y, float cx1, float cy1, float cx2, float cy2, int segments) {
/* 101 */     if (this.cx == x && this.cy == y) {
/*     */       return;
/*     */     }
/*     */     
/* 105 */     Curve curve = new Curve(new Vector2f(this.cx, this.cy), new Vector2f(cx1, cy1), new Vector2f(cx2, cy2), new Vector2f(x, y));
/* 106 */     float step = 1.0F / segments;
/*     */     
/* 108 */     for (int i = 1; i < segments + 1; i++) {
/* 109 */       float t = i * step;
/* 110 */       Vector2f p = curve.pointAt(t);
/* 111 */       if (this.hole != null) {
/* 112 */         this.hole.add(new float[] { p.x, p.y });
/*     */       } else {
/* 114 */         this.localPoints.add(new float[] { p.x, p.y });
/*     */       } 
/* 116 */       this.cx = p.x;
/* 117 */       this.cy = p.y;
/*     */     } 
/* 119 */     this.pointsDirty = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void createPoints() {
/* 126 */     this.points = new float[this.localPoints.size() * 2];
/* 127 */     for (int i = 0; i < this.localPoints.size(); i++) {
/* 128 */       float[] p = this.localPoints.get(i);
/* 129 */       this.points[i * 2] = p[0];
/* 130 */       this.points[i * 2 + 1] = p[1];
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Shape transform(Transform transform) {
/* 138 */     Path p = new Path(this.cx, this.cy);
/* 139 */     p.localPoints = transform(this.localPoints, transform);
/* 140 */     for (int i = 0; i < this.holes.size(); i++) {
/* 141 */       p.holes.add(transform(this.holes.get(i), transform));
/*     */     }
/* 143 */     p.closed = this.closed;
/*     */     
/* 145 */     return p;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ArrayList transform(ArrayList pts, Transform t) {
/* 156 */     float[] in = new float[pts.size() * 2];
/* 157 */     float[] out = new float[pts.size() * 2];
/*     */     
/* 159 */     for (int i = 0; i < pts.size(); i++) {
/* 160 */       in[i * 2] = ((float[])pts.get(i))[0];
/* 161 */       in[i * 2 + 1] = ((float[])pts.get(i))[1];
/*     */     } 
/* 163 */     t.transform(in, 0, out, 0, pts.size());
/*     */     
/* 165 */     ArrayList<float[]> outList = new ArrayList();
/* 166 */     for (int j = 0; j < pts.size(); j++) {
/* 167 */       outList.add(new float[] { out[j * 2], out[j * 2 + 1] });
/*     */     } 
/*     */     
/* 170 */     return outList;
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
/*     */   public boolean closed() {
/* 239 */     return this.closed;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\geom\Path.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */