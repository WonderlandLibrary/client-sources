/*     */ package org.newdawn.slick.geom;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MorphShape
/*     */   extends Shape
/*     */ {
/*  12 */   private ArrayList shapes = new ArrayList();
/*     */ 
/*     */ 
/*     */   
/*     */   private float offset;
/*     */ 
/*     */   
/*     */   private Shape current;
/*     */ 
/*     */   
/*     */   private Shape next;
/*     */ 
/*     */ 
/*     */   
/*     */   public MorphShape(Shape base) {
/*  27 */     this.shapes.add(base);
/*  28 */     float[] copy = base.points;
/*  29 */     this.points = new float[copy.length];
/*     */     
/*  31 */     this.current = base;
/*  32 */     this.next = base;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addShape(Shape shape) {
/*  41 */     if (shape.points.length != this.points.length) {
/*  42 */       throw new RuntimeException("Attempt to morph between two shapes with different vertex counts");
/*     */     }
/*     */     
/*  45 */     Shape prev = this.shapes.get(this.shapes.size() - 1);
/*  46 */     if (equalShapes(prev, shape)) {
/*  47 */       this.shapes.add(prev);
/*     */     } else {
/*  49 */       this.shapes.add(shape);
/*     */     } 
/*     */     
/*  52 */     if (this.shapes.size() == 2) {
/*  53 */       this.next = this.shapes.get(1);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean equalShapes(Shape a, Shape b) {
/*  65 */     a.checkPoints();
/*  66 */     b.checkPoints();
/*     */     
/*  68 */     for (int i = 0; i < a.points.length; i++) {
/*  69 */       if (a.points[i] != b.points[i]) {
/*  70 */         return false;
/*     */       }
/*     */     } 
/*     */     
/*  74 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMorphTime(float time) {
/*  84 */     int p = (int)time;
/*  85 */     int n = p + 1;
/*  86 */     float offset = time - p;
/*     */     
/*  88 */     p = rational(p);
/*  89 */     n = rational(n);
/*     */     
/*  91 */     setFrame(p, n, offset);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateMorphTime(float delta) {
/* 100 */     this.offset += delta;
/* 101 */     if (this.offset < 0.0F) {
/* 102 */       int index = this.shapes.indexOf(this.current);
/* 103 */       if (index < 0) {
/* 104 */         index = this.shapes.size() - 1;
/*     */       }
/*     */       
/* 107 */       int nframe = rational(index + 1);
/* 108 */       setFrame(index, nframe, this.offset);
/* 109 */       this.offset++;
/* 110 */     } else if (this.offset > 1.0F) {
/* 111 */       int index = this.shapes.indexOf(this.next);
/* 112 */       if (index < 1) {
/* 113 */         index = 0;
/*     */       }
/*     */       
/* 116 */       int nframe = rational(index + 1);
/* 117 */       setFrame(index, nframe, this.offset);
/* 118 */       this.offset--;
/*     */     } else {
/* 120 */       this.pointsDirty = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setExternalFrame(Shape current) {
/* 130 */     this.current = current;
/* 131 */     this.next = this.shapes.get(0);
/* 132 */     this.offset = 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int rational(int n) {
/* 142 */     while (n >= this.shapes.size()) {
/* 143 */       n -= this.shapes.size();
/*     */     }
/* 145 */     while (n < 0) {
/* 146 */       n += this.shapes.size();
/*     */     }
/*     */     
/* 149 */     return n;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setFrame(int a, int b, float offset) {
/* 160 */     this.current = this.shapes.get(a);
/* 161 */     this.next = this.shapes.get(b);
/* 162 */     this.offset = offset;
/* 163 */     this.pointsDirty = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void createPoints() {
/* 170 */     if (this.current == this.next) {
/* 171 */       System.arraycopy(this.current.points, 0, this.points, 0, this.points.length);
/*     */       
/*     */       return;
/*     */     } 
/* 175 */     float[] apoints = this.current.points;
/* 176 */     float[] bpoints = this.next.points;
/*     */     
/* 178 */     for (int i = 0; i < this.points.length; i++) {
/* 179 */       this.points[i] = apoints[i] * (1.0F - this.offset);
/* 180 */       this.points[i] = this.points[i] + bpoints[i] * this.offset;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Shape transform(Transform transform) {
/* 188 */     createPoints();
/* 189 */     Polygon poly = new Polygon(this.points);
/*     */     
/* 191 */     return poly;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\geom\MorphShape.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */