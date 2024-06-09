/*     */ package org.newdawn.slick.geom;
/*     */ 
/*     */ import java.io.Serializable;
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
/*     */ public abstract class Shape
/*     */   implements Serializable
/*     */ {
/*     */   protected float[] points;
/*     */   protected float[] center;
/*     */   protected float x;
/*     */   protected float y;
/*     */   protected float maxX;
/*     */   protected float maxY;
/*     */   protected float minX;
/*     */   protected float minY;
/*     */   protected float boundingCircleRadius;
/*     */   protected boolean pointsDirty = true;
/*     */   protected transient Triangulator tris;
/*     */   protected boolean trianglesDirty;
/*     */   
/*     */   public void setLocation(float x, float y) {
/*  52 */     setX(x);
/*  53 */     setY(y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Shape transform(Transform paramTransform);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void createPoints();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getX() {
/*  77 */     return this.x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setX(float x) {
/*  86 */     if (x != this.x) {
/*  87 */       float dx = x - this.x;
/*  88 */       this.x = x;
/*     */       
/*  90 */       if (this.points == null || this.center == null) {
/*  91 */         checkPoints();
/*     */       }
/*     */       
/*  94 */       for (int i = 0; i < this.points.length / 2; i++) {
/*  95 */         this.points[i * 2] = this.points[i * 2] + dx;
/*     */       }
/*  97 */       this.center[0] = this.center[0] + dx;
/*  98 */       x += dx;
/*  99 */       this.maxX += dx;
/* 100 */       this.minX += dx;
/* 101 */       this.trianglesDirty = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setY(float y) {
/* 111 */     if (y != this.y) {
/* 112 */       float dy = y - this.y;
/* 113 */       this.y = y;
/*     */       
/* 115 */       if (this.points == null || this.center == null) {
/* 116 */         checkPoints();
/*     */       }
/*     */       
/* 119 */       for (int i = 0; i < this.points.length / 2; i++) {
/* 120 */         this.points[i * 2 + 1] = this.points[i * 2 + 1] + dy;
/*     */       }
/* 122 */       this.center[1] = this.center[1] + dy;
/* 123 */       y += dy;
/* 124 */       this.maxY += dy;
/* 125 */       this.minY += dy;
/* 126 */       this.trianglesDirty = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getY() {
/* 136 */     return this.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector2f getLocation() {
/* 145 */     return new Vector2f(getX(), getY());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLocation(Vector2f loc) {
/* 154 */     setX(loc.x);
/* 155 */     setY(loc.y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getCenterX() {
/* 164 */     checkPoints();
/*     */     
/* 166 */     return this.center[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCenterX(float centerX) {
/* 175 */     if (this.points == null || this.center == null) {
/* 176 */       checkPoints();
/*     */     }
/*     */     
/* 179 */     float xDiff = centerX - getCenterX();
/* 180 */     setX(this.x + xDiff);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getCenterY() {
/* 189 */     checkPoints();
/*     */     
/* 191 */     return this.center[1];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCenterY(float centerY) {
/* 200 */     if (this.points == null || this.center == null) {
/* 201 */       checkPoints();
/*     */     }
/*     */     
/* 204 */     float yDiff = centerY - getCenterY();
/* 205 */     setY(this.y + yDiff);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getMaxX() {
/* 214 */     checkPoints();
/* 215 */     return this.maxX;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getMaxY() {
/* 223 */     checkPoints();
/* 224 */     return this.maxY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getMinX() {
/* 233 */     checkPoints();
/* 234 */     return this.minX;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getMinY() {
/* 243 */     checkPoints();
/* 244 */     return this.minY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getBoundingCircleRadius() {
/* 253 */     checkPoints();
/* 254 */     return this.boundingCircleRadius;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] getCenter() {
/* 263 */     checkPoints();
/* 264 */     return this.center;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] getPoints() {
/* 273 */     checkPoints();
/* 274 */     return this.points;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPointCount() {
/* 283 */     checkPoints();
/* 284 */     return this.points.length / 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] getPoint(int index) {
/* 294 */     checkPoints();
/*     */     
/* 296 */     float[] result = new float[2];
/*     */     
/* 298 */     result[0] = this.points[index * 2];
/* 299 */     result[1] = this.points[index * 2 + 1];
/*     */     
/* 301 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] getNormal(int index) {
/* 311 */     float[] current = getPoint(index);
/* 312 */     float[] prev = getPoint((index - 1 < 0) ? (getPointCount() - 1) : (index - 1));
/* 313 */     float[] next = getPoint((index + 1 >= getPointCount()) ? 0 : (index + 1));
/*     */     
/* 315 */     float[] t1 = getNormal(prev, current);
/* 316 */     float[] t2 = getNormal(current, next);
/*     */     
/* 318 */     if (index == 0 && !closed()) {
/* 319 */       return t2;
/*     */     }
/* 321 */     if (index == getPointCount() - 1 && !closed()) {
/* 322 */       return t1;
/*     */     }
/*     */     
/* 325 */     float tx = (t1[0] + t2[0]) / 2.0F;
/* 326 */     float ty = (t1[1] + t2[1]) / 2.0F;
/* 327 */     float len = (float)Math.sqrt((tx * tx + ty * ty));
/* 328 */     return new float[] { tx / len, ty / len };
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
/*     */   public boolean contains(Shape other) {
/* 340 */     if (other.intersects(this)) {
/* 341 */       return false;
/*     */     }
/*     */     
/* 344 */     for (int i = 0; i < other.getPointCount(); i++) {
/* 345 */       float[] pt = other.getPoint(i);
/* 346 */       if (!contains(pt[0], pt[1])) {
/* 347 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 351 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float[] getNormal(float[] start, float[] end) {
/* 361 */     float dx = start[0] - end[0];
/* 362 */     float dy = start[1] - end[1];
/* 363 */     float len = (float)Math.sqrt((dx * dx + dy * dy));
/* 364 */     dx /= len;
/* 365 */     dy /= len;
/* 366 */     return new float[] { -dy, dx };
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
/*     */   public boolean includes(float x, float y) {
/* 378 */     if (this.points.length == 0) {
/* 379 */       return false;
/*     */     }
/*     */     
/* 382 */     checkPoints();
/*     */     
/* 384 */     Line testLine = new Line(0.0F, 0.0F, 0.0F, 0.0F);
/* 385 */     Vector2f pt = new Vector2f(x, y);
/*     */     
/* 387 */     for (int i = 0; i < this.points.length; i += 2) {
/* 388 */       int n = i + 2;
/* 389 */       if (n >= this.points.length) {
/* 390 */         n = 0;
/*     */       }
/* 392 */       testLine.set(this.points[i], this.points[i + 1], this.points[n], this.points[n + 1]);
/*     */       
/* 394 */       if (testLine.on(pt)) {
/* 395 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 399 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int indexOf(float x, float y) {
/* 410 */     for (int i = 0; i < this.points.length; i += 2) {
/* 411 */       if (this.points[i] == x && this.points[i + 1] == y) {
/* 412 */         return i / 2;
/*     */       }
/*     */     } 
/*     */     
/* 416 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(float x, float y) {
/* 427 */     checkPoints();
/* 428 */     if (this.points.length == 0) {
/* 429 */       return false;
/*     */     }
/*     */     
/* 432 */     boolean result = false;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 437 */     int npoints = this.points.length;
/*     */     
/* 439 */     float xold = this.points[npoints - 2];
/* 440 */     float yold = this.points[npoints - 1];
/* 441 */     for (int i = 0; i < npoints; i += 2) {
/* 442 */       float x1, y1, x2, y2, xnew = this.points[i];
/* 443 */       float ynew = this.points[i + 1];
/* 444 */       if (xnew > xold) {
/* 445 */         x1 = xold;
/* 446 */         x2 = xnew;
/* 447 */         y1 = yold;
/* 448 */         y2 = ynew;
/*     */       } else {
/*     */         
/* 451 */         x1 = xnew;
/* 452 */         x2 = xold;
/* 453 */         y1 = ynew;
/* 454 */         y2 = yold;
/*     */       } 
/* 456 */       if (((xnew < x) ? true : false) == ((x <= xold) ? true : false) && (y - y1) * (x2 - x1) < (y2 - y1) * (x - x1))
/*     */       {
/*     */         
/* 459 */         result = !result;
/*     */       }
/* 461 */       xold = xnew;
/* 462 */       yold = ynew;
/*     */     } 
/*     */     
/* 465 */     return result;
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
/*     */   public boolean intersects(Shape shape) {
/* 489 */     checkPoints();
/*     */     
/* 491 */     boolean result = false;
/* 492 */     float[] points = getPoints();
/* 493 */     float[] thatPoints = shape.getPoints();
/* 494 */     int length = points.length;
/* 495 */     int thatLength = thatPoints.length;
/*     */ 
/*     */ 
/*     */     
/* 499 */     if (!closed()) {
/* 500 */       length -= 2;
/*     */     }
/* 502 */     if (!shape.closed()) {
/* 503 */       thatLength -= 2;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 514 */     for (int i = 0; i < length; i += 2) {
/* 515 */       int iNext = i + 2;
/* 516 */       if (iNext >= points.length) {
/* 517 */         iNext = 0;
/*     */       }
/*     */       
/* 520 */       for (int j = 0; j < thatLength; j += 2) {
/* 521 */         int jNext = j + 2;
/* 522 */         if (jNext >= thatPoints.length) {
/* 523 */           jNext = 0;
/*     */         }
/*     */         
/* 526 */         double unknownA = ((points[iNext] - points[i]) * (thatPoints[j + 1] - points[i + 1]) - ((points[iNext + 1] - points[i + 1]) * (thatPoints[j] - points[i]))) / ((points[iNext + 1] - points[i + 1]) * (thatPoints[jNext] - thatPoints[j]) - (points[iNext] - points[i]) * (thatPoints[jNext + 1] - thatPoints[j + 1]));
/*     */ 
/*     */ 
/*     */         
/* 530 */         double unknownB = ((thatPoints[jNext] - thatPoints[j]) * (thatPoints[j + 1] - points[i + 1]) - ((thatPoints[jNext + 1] - thatPoints[j + 1]) * (thatPoints[j] - points[i]))) / ((points[iNext + 1] - points[i + 1]) * (thatPoints[jNext] - thatPoints[j]) - (points[iNext] - points[i]) * (thatPoints[jNext + 1] - thatPoints[j + 1]));
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 535 */         if (unknownA >= 0.0D && unknownA <= 1.0D && unknownB >= 0.0D && unknownB <= 1.0D) {
/* 536 */           result = true;
/*     */           break;
/*     */         } 
/*     */       } 
/* 540 */       if (result) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */     
/* 545 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasVertex(float x, float y) {
/* 556 */     if (this.points.length == 0) {
/* 557 */       return false;
/*     */     }
/*     */     
/* 560 */     checkPoints();
/*     */     
/* 562 */     for (int i = 0; i < this.points.length; i += 2) {
/* 563 */       if (this.points[i] == x && this.points[i + 1] == y) {
/* 564 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 568 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void findCenter() {
/* 576 */     this.center = new float[] { 0.0F, 0.0F };
/* 577 */     int length = this.points.length;
/* 578 */     for (int i = 0; i < length; i += 2) {
/* 579 */       this.center[0] = this.center[0] + this.points[i];
/* 580 */       this.center[1] = this.center[1] + this.points[i + 1];
/*     */     } 
/* 582 */     this.center[0] = this.center[0] / (length / 2);
/* 583 */     this.center[1] = this.center[1] / (length / 2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void calculateRadius() {
/* 591 */     this.boundingCircleRadius = 0.0F;
/*     */     
/* 593 */     for (int i = 0; i < this.points.length; i += 2) {
/* 594 */       float temp = (this.points[i] - this.center[0]) * (this.points[i] - this.center[0]) + (this.points[i + 1] - this.center[1]) * (this.points[i + 1] - this.center[1]);
/*     */       
/* 596 */       this.boundingCircleRadius = (this.boundingCircleRadius > temp) ? this.boundingCircleRadius : temp;
/*     */     } 
/* 598 */     this.boundingCircleRadius = (float)Math.sqrt(this.boundingCircleRadius);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void calculateTriangles() {
/* 605 */     if (!this.trianglesDirty && this.tris != null) {
/*     */       return;
/*     */     }
/* 608 */     if (this.points.length >= 6) {
/* 609 */       boolean clockwise = true;
/* 610 */       float area = 0.0F; int i;
/* 611 */       for (i = 0; i < this.points.length / 2 - 1; i++) {
/* 612 */         float x1 = this.points[i * 2];
/* 613 */         float y1 = this.points[i * 2 + 1];
/* 614 */         float x2 = this.points[i * 2 + 2];
/* 615 */         float y2 = this.points[i * 2 + 3];
/*     */         
/* 617 */         area += x1 * y2 - y1 * x2;
/*     */       } 
/* 619 */       area /= 2.0F;
/* 620 */       clockwise = (area > 0.0F);
/*     */       
/* 622 */       this.tris = new NeatTriangulator();
/* 623 */       for (i = 0; i < this.points.length; i += 2) {
/* 624 */         this.tris.addPolyPoint(this.points[i], this.points[i + 1]);
/*     */       }
/* 626 */       this.tris.triangulate();
/*     */     } 
/*     */     
/* 629 */     this.trianglesDirty = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void increaseTriangulation() {
/* 636 */     checkPoints();
/* 637 */     calculateTriangles();
/*     */     
/* 639 */     this.tris = new OverTriangulator(this.tris);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Triangulator getTriangles() {
/* 648 */     checkPoints();
/* 649 */     calculateTriangles();
/* 650 */     return this.tris;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void checkPoints() {
/* 657 */     if (this.pointsDirty) {
/* 658 */       createPoints();
/* 659 */       findCenter();
/* 660 */       calculateRadius();
/*     */       
/* 662 */       if (this.points.length > 0) {
/* 663 */         this.maxX = this.points[0];
/* 664 */         this.maxY = this.points[1];
/* 665 */         this.minX = this.points[0];
/* 666 */         this.minY = this.points[1];
/* 667 */         for (int i = 0; i < this.points.length / 2; i++) {
/* 668 */           this.maxX = Math.max(this.points[i * 2], this.maxX);
/* 669 */           this.maxY = Math.max(this.points[i * 2 + 1], this.maxY);
/* 670 */           this.minX = Math.min(this.points[i * 2], this.minX);
/* 671 */           this.minY = Math.min(this.points[i * 2 + 1], this.minY);
/*     */         } 
/*     */       } 
/* 674 */       this.pointsDirty = false;
/* 675 */       this.trianglesDirty = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void preCache() {
/* 683 */     checkPoints();
/* 684 */     getTriangles();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean closed() {
/* 693 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Shape prune() {
/* 702 */     Polygon result = new Polygon();
/*     */     
/* 704 */     for (int i = 0; i < getPointCount(); i++) {
/* 705 */       int next = (i + 1 >= getPointCount()) ? 0 : (i + 1);
/* 706 */       int prev = (i - 1 < 0) ? (getPointCount() - 1) : (i - 1);
/*     */       
/* 708 */       float dx1 = getPoint(i)[0] - getPoint(prev)[0];
/* 709 */       float dy1 = getPoint(i)[1] - getPoint(prev)[1];
/* 710 */       float dx2 = getPoint(next)[0] - getPoint(i)[0];
/* 711 */       float dy2 = getPoint(next)[1] - getPoint(i)[1];
/*     */       
/* 713 */       float len1 = (float)Math.sqrt((dx1 * dx1 + dy1 * dy1));
/* 714 */       float len2 = (float)Math.sqrt((dx2 * dx2 + dy2 * dy2));
/* 715 */       dx1 /= len1;
/* 716 */       dy1 /= len1;
/* 717 */       dx2 /= len2;
/* 718 */       dy2 /= len2;
/*     */       
/* 720 */       if (dx1 != dx2 || dy1 != dy2) {
/* 721 */         result.addPoint(getPoint(i)[0], getPoint(i)[1]);
/*     */       }
/*     */     } 
/*     */     
/* 725 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Shape[] subtract(Shape other) {
/* 736 */     return (new GeomUtil()).subtract(this, other);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Shape[] union(Shape other) {
/* 746 */     return (new GeomUtil()).union(this, other);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getWidth() {
/* 755 */     return this.maxX - this.minX;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getHeight() {
/* 765 */     return this.maxY - this.minY;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\geom\Shape.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */