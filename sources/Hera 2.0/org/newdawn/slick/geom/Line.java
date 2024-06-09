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
/*     */ public class Line
/*     */   extends Shape
/*     */ {
/*     */   private Vector2f start;
/*     */   private Vector2f end;
/*     */   private Vector2f vec;
/*     */   private float lenSquared;
/*  21 */   private Vector2f loc = new Vector2f(0.0F, 0.0F);
/*     */   
/*  23 */   private Vector2f v = new Vector2f(0.0F, 0.0F);
/*     */   
/*  25 */   private Vector2f v2 = new Vector2f(0.0F, 0.0F);
/*     */   
/*  27 */   private Vector2f proj = new Vector2f(0.0F, 0.0F);
/*     */ 
/*     */   
/*  30 */   private Vector2f closest = new Vector2f(0.0F, 0.0F);
/*     */   
/*  32 */   private Vector2f other = new Vector2f(0.0F, 0.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean outerEdge = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean innerEdge = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Line(float x, float y, boolean inner, boolean outer) {
/*  52 */     this(0.0F, 0.0F, x, y);
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
/*     */   public Line(float x, float y) {
/*  64 */     this(x, y, true, true);
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
/*     */   public Line(float x1, float y1, float x2, float y2) {
/*  80 */     this(new Vector2f(x1, y1), new Vector2f(x2, y2));
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
/*     */   public Line(float x1, float y1, float dx, float dy, boolean dummy) {
/*  98 */     this(new Vector2f(x1, y1), new Vector2f(x1 + dx, y1 + dy));
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
/*     */   public Line(float[] start, float[] end) {
/* 112 */     set(start, end);
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
/*     */   public Line(Vector2f start, Vector2f end) {
/* 126 */     set(start, end);
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
/*     */   public void set(float[] start, float[] end) {
/* 138 */     set(start[0], start[1], end[0], end[1]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector2f getStart() {
/* 147 */     return this.start;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector2f getEnd() {
/* 156 */     return this.end;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float length() {
/* 165 */     return this.vec.length();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float lengthSquared() {
/* 174 */     return this.vec.lengthSquared();
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
/*     */   public void set(Vector2f start, Vector2f end) {
/* 186 */     this.pointsDirty = true;
/* 187 */     if (this.start == null) {
/* 188 */       this.start = new Vector2f();
/*     */     }
/* 190 */     this.start.set(start);
/*     */     
/* 192 */     if (this.end == null) {
/* 193 */       this.end = new Vector2f();
/*     */     }
/* 195 */     this.end.set(end);
/*     */     
/* 197 */     this.vec = new Vector2f(end);
/* 198 */     this.vec.sub(start);
/*     */     
/* 200 */     this.lenSquared = this.vec.lengthSquared();
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
/*     */   public void set(float sx, float sy, float ex, float ey) {
/* 216 */     this.pointsDirty = true;
/* 217 */     this.start.set(sx, sy);
/* 218 */     this.end.set(ex, ey);
/* 219 */     float dx = ex - sx;
/* 220 */     float dy = ey - sy;
/* 221 */     this.vec.set(dx, dy);
/*     */     
/* 223 */     this.lenSquared = dx * dx + dy * dy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getDX() {
/* 232 */     return this.end.getX() - this.start.getX();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getDY() {
/* 241 */     return this.end.getY() - this.start.getY();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getX() {
/* 248 */     return getX1();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getY() {
/* 255 */     return getY1();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getX1() {
/* 264 */     return this.start.getX();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getY1() {
/* 273 */     return this.start.getY();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getX2() {
/* 282 */     return this.end.getX();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getY2() {
/* 291 */     return this.end.getY();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float distance(Vector2f point) {
/* 302 */     return (float)Math.sqrt(distanceSquared(point));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean on(Vector2f point) {
/* 313 */     getClosestPoint(point, this.closest);
/*     */     
/* 315 */     return point.equals(this.closest);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float distanceSquared(Vector2f point) {
/* 326 */     getClosestPoint(point, this.closest);
/* 327 */     this.closest.sub(point);
/*     */     
/* 329 */     float result = this.closest.lengthSquared();
/*     */     
/* 331 */     return result;
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
/*     */   public void getClosestPoint(Vector2f point, Vector2f result) {
/* 343 */     this.loc.set(point);
/* 344 */     this.loc.sub(this.start);
/*     */     
/* 346 */     float projDistance = this.vec.dot(this.loc);
/*     */     
/* 348 */     projDistance /= this.vec.lengthSquared();
/*     */     
/* 350 */     if (projDistance < 0.0F) {
/* 351 */       result.set(this.start);
/*     */       return;
/*     */     } 
/* 354 */     if (projDistance > 1.0F) {
/* 355 */       result.set(this.end);
/*     */       
/*     */       return;
/*     */     } 
/* 359 */     result.x = this.start.getX() + projDistance * this.vec.getX();
/* 360 */     result.y = this.start.getY() + projDistance * this.vec.getY();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 367 */     return "[Line " + this.start + "," + this.end + "]";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector2f intersect(Line other) {
/* 378 */     return intersect(other, false);
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
/*     */   public Vector2f intersect(Line other, boolean limit) {
/* 391 */     Vector2f temp = new Vector2f();
/*     */     
/* 393 */     if (!intersect(other, limit, temp)) {
/* 394 */       return null;
/*     */     }
/*     */     
/* 397 */     return temp;
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
/*     */   public boolean intersect(Line other, boolean limit, Vector2f result) {
/* 412 */     float dx1 = this.end.getX() - this.start.getX();
/* 413 */     float dx2 = other.end.getX() - other.start.getX();
/* 414 */     float dy1 = this.end.getY() - this.start.getY();
/* 415 */     float dy2 = other.end.getY() - other.start.getY();
/* 416 */     float denom = dy2 * dx1 - dx2 * dy1;
/*     */     
/* 418 */     if (denom == 0.0F) {
/* 419 */       return false;
/*     */     }
/*     */     
/* 422 */     float ua = dx2 * (this.start.getY() - other.start.getY()) - dy2 * (this.start.getX() - other.start.getX());
/*     */     
/* 424 */     ua /= denom;
/* 425 */     float ub = dx1 * (this.start.getY() - other.start.getY()) - dy1 * (this.start.getX() - other.start.getX());
/*     */     
/* 427 */     ub /= denom;
/*     */     
/* 429 */     if (limit && (ua < 0.0F || ua > 1.0F || ub < 0.0F || ub > 1.0F)) {
/* 430 */       return false;
/*     */     }
/*     */     
/* 433 */     float u = ua;
/*     */     
/* 435 */     float ix = this.start.getX() + u * (this.end.getX() - this.start.getX());
/* 436 */     float iy = this.start.getY() + u * (this.end.getY() - this.start.getY());
/*     */     
/* 438 */     result.set(ix, iy);
/* 439 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void createPoints() {
/* 446 */     this.points = new float[4];
/* 447 */     this.points[0] = getX1();
/* 448 */     this.points[1] = getY1();
/* 449 */     this.points[2] = getX2();
/* 450 */     this.points[3] = getY2();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Shape transform(Transform transform) {
/* 457 */     float[] temp = new float[4];
/* 458 */     createPoints();
/* 459 */     transform.transform(this.points, 0, temp, 0, 2);
/*     */     
/* 461 */     return new Line(temp[0], temp[1], temp[2], temp[3]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean closed() {
/* 468 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean intersects(Shape shape) {
/* 476 */     if (shape instanceof Circle)
/*     */     {
/* 478 */       return shape.intersects(this);
/*     */     }
/* 480 */     return super.intersects(shape);
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\geom\Line.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */