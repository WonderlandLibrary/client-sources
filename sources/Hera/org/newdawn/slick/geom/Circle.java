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
/*     */ public class Circle
/*     */   extends Ellipse
/*     */ {
/*     */   public float radius;
/*     */   
/*     */   public strictfp Circle(float centerPointX, float centerPointY, float radius) {
/*  20 */     this(centerPointX, centerPointY, radius, 50);
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
/*     */   public strictfp Circle(float centerPointX, float centerPointY, float radius, int segmentCount) {
/*  32 */     super(centerPointX, centerPointY, radius, radius, segmentCount);
/*  33 */     this.x = centerPointX - radius;
/*  34 */     this.y = centerPointY - radius;
/*  35 */     this.radius = radius;
/*  36 */     this.boundingCircleRadius = radius;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public strictfp float getCenterX() {
/*  45 */     return getX() + this.radius;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public strictfp float getCenterY() {
/*  54 */     return getY() + this.radius;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public strictfp float[] getCenter() {
/*  64 */     return new float[] { getCenterX(), getCenterY() };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public strictfp void setRadius(float radius) {
/*  73 */     if (radius != this.radius) {
/*  74 */       this.pointsDirty = true;
/*  75 */       this.radius = radius;
/*  76 */       setRadii(radius, radius);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public strictfp float getRadius() {
/*  86 */     return this.radius;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public strictfp boolean intersects(Shape shape) {
/*  96 */     if (shape instanceof Circle) {
/*  97 */       Circle other = (Circle)shape;
/*  98 */       float totalRad2 = getRadius() + other.getRadius();
/*     */       
/* 100 */       if (Math.abs(other.getCenterX() - getCenterX()) > totalRad2) {
/* 101 */         return false;
/*     */       }
/* 103 */       if (Math.abs(other.getCenterY() - getCenterY()) > totalRad2) {
/* 104 */         return false;
/*     */       }
/*     */       
/* 107 */       totalRad2 *= totalRad2;
/*     */       
/* 109 */       float dx = Math.abs(other.getCenterX() - getCenterX());
/* 110 */       float dy = Math.abs(other.getCenterY() - getCenterY());
/*     */       
/* 112 */       return (totalRad2 >= dx * dx + dy * dy);
/*     */     } 
/* 114 */     if (shape instanceof Rectangle) {
/* 115 */       return intersects((Rectangle)shape);
/*     */     }
/*     */     
/* 118 */     return super.intersects(shape);
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
/*     */   public strictfp boolean contains(float x, float y) {
/* 131 */     float xDelta = x - getCenterX(), yDelta = y - getCenterY();
/* 132 */     return (xDelta * xDelta + yDelta * yDelta < getRadius() * getRadius());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private strictfp boolean contains(Line line) {
/* 141 */     return (contains(line.getX1(), line.getY1()) && contains(line.getX2(), line.getY2()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected strictfp void findCenter() {
/* 148 */     this.center = new float[2];
/* 149 */     this.center[0] = this.x + this.radius;
/* 150 */     this.center[1] = this.y + this.radius;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected strictfp void calculateRadius() {
/* 157 */     this.boundingCircleRadius = this.radius;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private strictfp boolean intersects(Rectangle other) {
/* 167 */     Rectangle box = other;
/* 168 */     Circle circle = this;
/*     */     
/* 170 */     if (box.contains(this.x + this.radius, this.y + this.radius)) {
/* 171 */       return true;
/*     */     }
/*     */     
/* 174 */     float x1 = box.getX();
/* 175 */     float y1 = box.getY();
/* 176 */     float x2 = box.getX() + box.getWidth();
/* 177 */     float y2 = box.getY() + box.getHeight();
/*     */     
/* 179 */     Line[] lines = new Line[4];
/* 180 */     lines[0] = new Line(x1, y1, x2, y1);
/* 181 */     lines[1] = new Line(x2, y1, x2, y2);
/* 182 */     lines[2] = new Line(x2, y2, x1, y2);
/* 183 */     lines[3] = new Line(x1, y2, x1, y1);
/*     */     
/* 185 */     float r2 = circle.getRadius() * circle.getRadius();
/*     */     
/* 187 */     Vector2f pos = new Vector2f(circle.getCenterX(), circle.getCenterY());
/*     */     
/* 189 */     for (int i = 0; i < 4; i++) {
/* 190 */       float dis = lines[i].distanceSquared(pos);
/* 191 */       if (dis < r2) {
/* 192 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 196 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private strictfp boolean intersects(Line other) {
/* 206 */     Vector2f closest, lineSegmentStart = new Vector2f(other.getX1(), other.getY1());
/* 207 */     Vector2f lineSegmentEnd = new Vector2f(other.getX2(), other.getY2());
/* 208 */     Vector2f circleCenter = new Vector2f(getCenterX(), getCenterY());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 213 */     Vector2f segv = lineSegmentEnd.copy().sub(lineSegmentStart);
/* 214 */     Vector2f ptv = circleCenter.copy().sub(lineSegmentStart);
/* 215 */     float segvLength = segv.length();
/* 216 */     float projvl = ptv.dot(segv) / segvLength;
/* 217 */     if (projvl < 0.0F) {
/*     */       
/* 219 */       closest = lineSegmentStart;
/*     */     }
/* 221 */     else if (projvl > segvLength) {
/*     */       
/* 223 */       closest = lineSegmentEnd;
/*     */     }
/*     */     else {
/*     */       
/* 227 */       Vector2f projv = segv.copy().scale(projvl / segvLength);
/* 228 */       closest = lineSegmentStart.copy().add(projv);
/*     */     } 
/* 230 */     boolean intersects = (circleCenter.copy().sub(closest).lengthSquared() <= getRadius() * getRadius());
/*     */     
/* 232 */     return intersects;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\geom\Circle.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */