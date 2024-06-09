/*     */ package org.newdawn.slick.geom;
/*     */ 
/*     */ import java.util.ArrayList;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Ellipse
/*     */   extends Shape
/*     */ {
/*     */   protected static final int DEFAULT_SEGMENT_COUNT = 50;
/*     */   private int segmentCount;
/*     */   private float radius1;
/*     */   private float radius2;
/*     */   
/*     */   public Ellipse(float centerPointX, float centerPointY, float radius1, float radius2) {
/*  41 */     this(centerPointX, centerPointY, radius1, radius2, 50);
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
/*     */   public Ellipse(float centerPointX, float centerPointY, float radius1, float radius2, int segmentCount) {
/*  54 */     this.x = centerPointX - radius1;
/*  55 */     this.y = centerPointY - radius2;
/*  56 */     this.radius1 = radius1;
/*  57 */     this.radius2 = radius2;
/*  58 */     this.segmentCount = segmentCount;
/*  59 */     checkPoints();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRadii(float radius1, float radius2) {
/*  69 */     setRadius1(radius1);
/*  70 */     setRadius2(radius2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getRadius1() {
/*  79 */     return this.radius1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRadius1(float radius1) {
/*  88 */     if (radius1 != this.radius1) {
/*  89 */       this.radius1 = radius1;
/*  90 */       this.pointsDirty = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getRadius2() {
/* 100 */     return this.radius2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRadius2(float radius2) {
/* 109 */     if (radius2 != this.radius2) {
/* 110 */       this.radius2 = radius2;
/* 111 */       this.pointsDirty = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void createPoints() {
/* 120 */     ArrayList<Float> tempPoints = new ArrayList();
/*     */     
/* 122 */     this.maxX = -1.4E-45F;
/* 123 */     this.maxY = -1.4E-45F;
/* 124 */     this.minX = Float.MAX_VALUE;
/* 125 */     this.minY = Float.MAX_VALUE;
/*     */     
/* 127 */     float start = 0.0F;
/* 128 */     float end = 359.0F;
/*     */     
/* 130 */     float cx = this.x + this.radius1;
/* 131 */     float cy = this.y + this.radius2;
/*     */     
/* 133 */     int step = 360 / this.segmentCount;
/*     */     float a;
/* 135 */     for (a = start; a <= end + step; a += step) {
/* 136 */       float ang = a;
/* 137 */       if (ang > end) {
/* 138 */         ang = end;
/*     */       }
/* 140 */       float newX = (float)(cx + FastTrig.cos(Math.toRadians(ang)) * this.radius1);
/* 141 */       float newY = (float)(cy + FastTrig.sin(Math.toRadians(ang)) * this.radius2);
/*     */       
/* 143 */       if (newX > this.maxX) {
/* 144 */         this.maxX = newX;
/*     */       }
/* 146 */       if (newY > this.maxY) {
/* 147 */         this.maxY = newY;
/*     */       }
/* 149 */       if (newX < this.minX) {
/* 150 */         this.minX = newX;
/*     */       }
/* 152 */       if (newY < this.minY) {
/* 153 */         this.minY = newY;
/*     */       }
/*     */       
/* 156 */       tempPoints.add(new Float(newX));
/* 157 */       tempPoints.add(new Float(newY));
/*     */     } 
/* 159 */     this.points = new float[tempPoints.size()];
/* 160 */     for (int i = 0; i < this.points.length; i++) {
/* 161 */       this.points[i] = ((Float)tempPoints.get(i)).floatValue();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Shape transform(Transform transform) {
/* 169 */     checkPoints();
/*     */     
/* 171 */     Polygon resultPolygon = new Polygon();
/*     */     
/* 173 */     float[] result = new float[this.points.length];
/* 174 */     transform.transform(this.points, 0, result, 0, this.points.length / 2);
/* 175 */     resultPolygon.points = result;
/* 176 */     resultPolygon.checkPoints();
/*     */     
/* 178 */     return resultPolygon;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void findCenter() {
/* 185 */     this.center = new float[2];
/* 186 */     this.center[0] = this.x + this.radius1;
/* 187 */     this.center[1] = this.y + this.radius2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void calculateRadius() {
/* 194 */     this.boundingCircleRadius = (this.radius1 > this.radius2) ? this.radius1 : this.radius2;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\geom\Ellipse.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */