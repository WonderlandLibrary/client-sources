/*     */ package org.newdawn.slick.geom;
/*     */ 
/*     */ import java.util.ArrayList;
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
/*     */ public class Polygon
/*     */   extends Shape
/*     */ {
/*     */   private boolean allowDups = false;
/*     */   private boolean closed = true;
/*     */   
/*     */   public Polygon(float[] points) {
/*  25 */     int length = points.length;
/*     */     
/*  27 */     this.points = new float[length];
/*  28 */     this.maxX = -1.4E-45F;
/*  29 */     this.maxY = -1.4E-45F;
/*  30 */     this.minX = Float.MAX_VALUE;
/*  31 */     this.minY = Float.MAX_VALUE;
/*  32 */     this.x = Float.MAX_VALUE;
/*  33 */     this.y = Float.MAX_VALUE;
/*     */     
/*  35 */     for (int i = 0; i < length; i++) {
/*  36 */       this.points[i] = points[i];
/*  37 */       if (i % 2 == 0) {
/*  38 */         if (points[i] > this.maxX) {
/*  39 */           this.maxX = points[i];
/*     */         }
/*  41 */         if (points[i] < this.minX) {
/*  42 */           this.minX = points[i];
/*     */         }
/*  44 */         if (points[i] < this.x) {
/*  45 */           this.x = points[i];
/*     */         }
/*     */       } else {
/*     */         
/*  49 */         if (points[i] > this.maxY) {
/*  50 */           this.maxY = points[i];
/*     */         }
/*  52 */         if (points[i] < this.minY) {
/*  53 */           this.minY = points[i];
/*     */         }
/*  55 */         if (points[i] < this.y) {
/*  56 */           this.y = points[i];
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  61 */     findCenter();
/*  62 */     calculateRadius();
/*  63 */     this.pointsDirty = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Polygon() {
/*  70 */     this.points = new float[0];
/*  71 */     this.maxX = -1.4E-45F;
/*  72 */     this.maxY = -1.4E-45F;
/*  73 */     this.minX = Float.MAX_VALUE;
/*  74 */     this.minY = Float.MAX_VALUE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAllowDuplicatePoints(boolean allowDups) {
/*  83 */     this.allowDups = allowDups;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addPoint(float x, float y) {
/*  93 */     if (hasVertex(x, y) && !this.allowDups) {
/*     */       return;
/*     */     }
/*     */     
/*  97 */     ArrayList<Float> tempPoints = new ArrayList();
/*  98 */     for (int i = 0; i < this.points.length; i++) {
/*  99 */       tempPoints.add(new Float(this.points[i]));
/*     */     }
/* 101 */     tempPoints.add(new Float(x));
/* 102 */     tempPoints.add(new Float(y));
/* 103 */     int length = tempPoints.size();
/* 104 */     this.points = new float[length];
/* 105 */     for (int j = 0; j < length; j++) {
/* 106 */       this.points[j] = ((Float)tempPoints.get(j)).floatValue();
/*     */     }
/* 108 */     if (x > this.maxX) {
/* 109 */       this.maxX = x;
/*     */     }
/* 111 */     if (y > this.maxY) {
/* 112 */       this.maxY = y;
/*     */     }
/* 114 */     if (x < this.minX) {
/* 115 */       this.minX = x;
/*     */     }
/* 117 */     if (y < this.minY) {
/* 118 */       this.minY = y;
/*     */     }
/* 120 */     findCenter();
/* 121 */     calculateRadius();
/*     */     
/* 123 */     this.pointsDirty = true;
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
/*     */   public Shape transform(Transform transform) {
/* 135 */     checkPoints();
/*     */     
/* 137 */     Polygon resultPolygon = new Polygon();
/*     */     
/* 139 */     float[] result = new float[this.points.length];
/* 140 */     transform.transform(this.points, 0, result, 0, this.points.length / 2);
/* 141 */     resultPolygon.points = result;
/* 142 */     resultPolygon.findCenter();
/* 143 */     resultPolygon.closed = this.closed;
/*     */     
/* 145 */     return resultPolygon;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setX(float x) {
/* 152 */     super.setX(x);
/*     */     
/* 154 */     this.pointsDirty = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setY(float y) {
/* 161 */     super.setY(y);
/*     */     
/* 163 */     this.pointsDirty = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void createPoints() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean closed() {
/* 177 */     return this.closed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setClosed(boolean closed) {
/* 186 */     this.closed = closed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Polygon copy() {
/* 195 */     float[] copyPoints = new float[this.points.length];
/* 196 */     System.arraycopy(this.points, 0, copyPoints, 0, copyPoints.length);
/*     */     
/* 198 */     return new Polygon(copyPoints);
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\geom\Polygon.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */