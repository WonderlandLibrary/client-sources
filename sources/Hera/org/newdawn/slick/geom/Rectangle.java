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
/*     */ public class Rectangle
/*     */   extends Shape
/*     */ {
/*     */   protected float width;
/*     */   protected float height;
/*     */   
/*     */   public Rectangle(float x, float y, float width, float height) {
/*  23 */     this.x = x;
/*  24 */     this.y = y;
/*  25 */     this.width = width;
/*  26 */     this.height = height;
/*  27 */     this.maxX = x + width;
/*  28 */     this.maxY = y + height;
/*  29 */     checkPoints();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(float xp, float yp) {
/*  40 */     if (xp <= getX()) {
/*  41 */       return false;
/*     */     }
/*  43 */     if (yp <= getY()) {
/*  44 */       return false;
/*     */     }
/*  46 */     if (xp >= this.maxX) {
/*  47 */       return false;
/*     */     }
/*  49 */     if (yp >= this.maxY) {
/*  50 */       return false;
/*     */     }
/*     */     
/*  53 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBounds(Rectangle other) {
/*  62 */     setBounds(other.getX(), other.getY(), other.getWidth(), other.getHeight());
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
/*     */   public void setBounds(float x, float y, float width, float height) {
/*  74 */     setX(x);
/*  75 */     setY(y);
/*  76 */     setSize(width, height);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSize(float width, float height) {
/*  86 */     setWidth(width);
/*  87 */     setHeight(height);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getWidth() {
/*  97 */     return this.width;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getHeight() {
/* 106 */     return this.height;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void grow(float h, float v) {
/* 117 */     setX(getX() - h);
/* 118 */     setY(getY() - v);
/* 119 */     setWidth(getWidth() + h * 2.0F);
/* 120 */     setHeight(getHeight() + v * 2.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void scaleGrow(float h, float v) {
/* 130 */     grow(getWidth() * (h - 1.0F), getHeight() * (v - 1.0F));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWidth(float width) {
/* 139 */     if (width != this.width) {
/* 140 */       this.pointsDirty = true;
/* 141 */       this.width = width;
/* 142 */       this.maxX = this.x + width;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHeight(float height) {
/* 152 */     if (height != this.height) {
/* 153 */       this.pointsDirty = true;
/* 154 */       this.height = height;
/* 155 */       this.maxY = this.y + height;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean intersects(Shape shape) {
/* 166 */     if (shape instanceof Rectangle) {
/* 167 */       Rectangle other = (Rectangle)shape;
/* 168 */       if (this.x > other.x + other.width || this.x + this.width < other.x) {
/* 169 */         return false;
/*     */       }
/* 171 */       if (this.y > other.y + other.height || this.y + this.height < other.y) {
/* 172 */         return false;
/*     */       }
/* 174 */       return true;
/*     */     } 
/* 176 */     if (shape instanceof Circle) {
/* 177 */       return intersects((Circle)shape);
/*     */     }
/*     */     
/* 180 */     return super.intersects(shape);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createPoints() {
/* 185 */     float useWidth = this.width;
/* 186 */     float useHeight = this.height;
/* 187 */     this.points = new float[8];
/*     */     
/* 189 */     this.points[0] = this.x;
/* 190 */     this.points[1] = this.y;
/*     */     
/* 192 */     this.points[2] = this.x + useWidth;
/* 193 */     this.points[3] = this.y;
/*     */     
/* 195 */     this.points[4] = this.x + useWidth;
/* 196 */     this.points[5] = this.y + useHeight;
/*     */     
/* 198 */     this.points[6] = this.x;
/* 199 */     this.points[7] = this.y + useHeight;
/*     */     
/* 201 */     this.maxX = this.points[2];
/* 202 */     this.maxY = this.points[5];
/* 203 */     this.minX = this.points[0];
/* 204 */     this.minY = this.points[1];
/*     */     
/* 206 */     findCenter();
/* 207 */     calculateRadius();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean intersects(Circle other) {
/* 217 */     return other.intersects(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 224 */     return "[Rectangle " + this.width + "x" + this.height + "]";
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
/*     */   public static boolean contains(float xp, float yp, float xr, float yr, float widthr, float heightr) {
/* 245 */     return (xp >= xr && yp >= yr && xp <= xr + widthr && yp <= yr + heightr);
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
/* 257 */     checkPoints();
/*     */     
/* 259 */     Polygon resultPolygon = new Polygon();
/*     */     
/* 261 */     float[] result = new float[this.points.length];
/* 262 */     transform.transform(this.points, 0, result, 0, this.points.length / 2);
/* 263 */     resultPolygon.points = result;
/* 264 */     resultPolygon.findCenter();
/* 265 */     resultPolygon.checkPoints();
/*     */     
/* 267 */     return resultPolygon;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\geom\Rectangle.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */