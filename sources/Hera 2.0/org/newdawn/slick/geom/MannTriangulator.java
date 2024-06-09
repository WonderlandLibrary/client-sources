/*     */ package org.newdawn.slick.geom;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.Array;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ public class MannTriangulator
/*     */   implements Triangulator
/*     */ {
/*     */   private static final double EPSILON = 1.0E-5D;
/*     */   protected PointBag contour;
/*     */   protected PointBag holes;
/*     */   private PointBag nextFreePointBag;
/*     */   private Point nextFreePoint;
/*  52 */   private List triangles = new ArrayList();
/*     */ 
/*     */   
/*     */   public MannTriangulator() {
/*  56 */     this.contour = getPointBag();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addPolyPoint(float x, float y) {
/*  63 */     addPoint(new Vector2f(x, y));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset() {
/*  70 */     while (this.holes != null) {
/*  71 */       this.holes = freePointBag(this.holes);
/*     */     }
/*     */     
/*  74 */     this.contour.clear();
/*  75 */     this.holes = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startHole() {
/*  82 */     PointBag newHole = getPointBag();
/*  83 */     newHole.next = this.holes;
/*  84 */     this.holes = newHole;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addPoint(Vector2f pt) {
/*  93 */     if (this.holes == null) {
/*  94 */       Point p = getPoint(pt);
/*  95 */       this.contour.add(p);
/*     */     } else {
/*  97 */       Point p = getPoint(pt);
/*  98 */       this.holes.add(p);
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
/*     */   private Vector2f[] triangulate(Vector2f[] result) {
/* 110 */     this.contour.computeAngles();
/* 111 */     for (PointBag hole = this.holes; hole != null; hole = hole.next) {
/* 112 */       hole.computeAngles();
/*     */     }
/*     */ 
/*     */     
/* 116 */     while (this.holes != null) {
/* 117 */       Point pHole = this.holes.first;
/*     */       label54: do {
/* 119 */         if (pHole.angle <= 0.0D)
/* 120 */         { Point pContour = this.contour.first;
/*     */           label51: while (true)
/* 122 */           { if (pHole.isInfront(pContour) && pContour.isInfront(pHole))
/*     */             {
/* 124 */               if (!this.contour.doesIntersectSegment(pHole.pt, pContour.pt)) {
/*     */                 
/* 126 */                 PointBag pointBag = this.holes;
/*     */                 
/* 128 */                 while (!pointBag.doesIntersectSegment(pHole.pt, pContour.pt)) {
/*     */ 
/*     */ 
/*     */                   
/* 132 */                   if ((pointBag = pointBag.next) == null) {
/*     */                     
/* 134 */                     Point newPtContour = getPoint(pContour.pt);
/* 135 */                     pContour.insertAfter(newPtContour);
/*     */                     
/* 137 */                     Point newPtHole = getPoint(pHole.pt);
/* 138 */                     pHole.insertBefore(newPtHole);
/*     */                     
/* 140 */                     pContour.next = pHole;
/* 141 */                     pHole.prev = pContour;
/*     */                     
/* 143 */                     newPtHole.next = newPtContour;
/* 144 */                     newPtContour.prev = newPtHole;
/*     */                     
/* 146 */                     pContour.computeAngle();
/* 147 */                     pHole.computeAngle();
/* 148 */                     newPtContour.computeAngle();
/* 149 */                     newPtHole.computeAngle();
/*     */ 
/*     */                     
/* 152 */                     this.holes.first = null; break label54;
/*     */                   } 
/*     */                 } 
/*     */               }  } 
/* 156 */             if ((pContour = pContour.next) == this.contour.first)
/*     */               break label51;  }  break; } 
/* 158 */       } while ((pHole = pHole.next) != this.holes.first);
/*     */ 
/*     */       
/* 161 */       this.holes = freePointBag(this.holes);
/*     */     } 
/*     */ 
/*     */     
/* 165 */     int numTriangles = this.contour.countPoints() - 2;
/* 166 */     int neededSpace = numTriangles * 3 + 1;
/* 167 */     if (result.length < neededSpace) {
/* 168 */       result = (Vector2f[])Array.newInstance(result.getClass().getComponentType(), neededSpace);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 173 */     int idx = 0;
/*     */     while (true) {
/* 175 */       Point pContour = this.contour.first;
/*     */       
/* 177 */       if (pContour == null) {
/*     */         break;
/*     */       }
/*     */       
/* 181 */       if (pContour.next == pContour.prev) {
/*     */         break;
/*     */       }
/*     */       
/*     */       do {
/* 186 */         if (pContour.angle <= 0.0D)
/* 187 */           continue;  Point point1 = pContour.prev;
/* 188 */         Point point2 = pContour.next;
/*     */         
/* 190 */         if ((point2.next == point1 || (point1.isInfront(point2) && point2.isInfront(point1))) && 
/* 191 */           !this.contour.doesIntersectSegment(point1.pt, point2.pt)) {
/* 192 */           result[idx++] = pContour.pt;
/* 193 */           result[idx++] = point2.pt;
/* 194 */           result[idx++] = point1.pt;
/*     */ 
/*     */           
/*     */           break;
/*     */         } 
/* 199 */       } while ((pContour = pContour.next) != this.contour.first);
/*     */ 
/*     */       
/* 202 */       Point prev = pContour.prev;
/* 203 */       Point next = pContour.next;
/*     */       
/* 205 */       this.contour.first = prev;
/* 206 */       pContour.unlink();
/* 207 */       freePoint(pContour);
/*     */       
/* 209 */       next.computeAngle();
/* 210 */       prev.computeAngle();
/*     */     } 
/*     */ 
/*     */     
/* 214 */     result[idx] = null;
/*     */ 
/*     */     
/* 217 */     this.contour.clear();
/*     */ 
/*     */     
/* 220 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PointBag getPointBag() {
/* 229 */     PointBag pb = this.nextFreePointBag;
/* 230 */     if (pb != null) {
/* 231 */       this.nextFreePointBag = pb.next;
/* 232 */       pb.next = null;
/* 233 */       return pb;
/*     */     } 
/* 235 */     return new PointBag();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PointBag freePointBag(PointBag pb) {
/* 245 */     PointBag next = pb.next;
/* 246 */     pb.clear();
/* 247 */     pb.next = this.nextFreePointBag;
/* 248 */     this.nextFreePointBag = pb;
/* 249 */     return next;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Point getPoint(Vector2f pt) {
/* 259 */     Point p = this.nextFreePoint;
/* 260 */     if (p != null) {
/* 261 */       this.nextFreePoint = p.next;
/*     */       
/* 263 */       p.next = null;
/* 264 */       p.prev = null;
/* 265 */       p.pt = pt;
/* 266 */       return p;
/*     */     } 
/* 268 */     return new Point(pt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void freePoint(Point p) {
/* 277 */     p.next = this.nextFreePoint;
/* 278 */     this.nextFreePoint = p;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void freePoints(Point head) {
/* 287 */     head.prev.next = this.nextFreePoint;
/* 288 */     head.prev = null;
/* 289 */     this.nextFreePoint = head;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class Point
/*     */     implements Serializable
/*     */   {
/*     */     protected Vector2f pt;
/*     */ 
/*     */     
/*     */     protected Point prev;
/*     */ 
/*     */     
/*     */     protected Point next;
/*     */ 
/*     */     
/*     */     protected double nx;
/*     */ 
/*     */     
/*     */     protected double ny;
/*     */ 
/*     */     
/*     */     protected double angle;
/*     */ 
/*     */     
/*     */     protected double dist;
/*     */ 
/*     */     
/*     */     public Point(Vector2f pt) {
/* 319 */       this.pt = pt;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void unlink() {
/* 326 */       this.prev.next = this.next;
/* 327 */       this.next.prev = this.prev;
/* 328 */       this.next = null;
/* 329 */       this.prev = null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void insertBefore(Point p) {
/* 338 */       this.prev.next = p;
/* 339 */       p.prev = this.prev;
/* 340 */       p.next = this;
/* 341 */       this.prev = p;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void insertAfter(Point p) {
/* 350 */       this.next.prev = p;
/* 351 */       p.prev = this;
/* 352 */       p.next = this.next;
/* 353 */       this.next = p;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private double hypot(double x, double y) {
/* 364 */       return Math.sqrt(x * x + y * y);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void computeAngle() {
/* 371 */       if (this.prev.pt.equals(this.pt)) {
/* 372 */         this.pt.x += 0.01F;
/*     */       }
/* 374 */       double dx1 = (this.pt.x - this.prev.pt.x);
/* 375 */       double dy1 = (this.pt.y - this.prev.pt.y);
/* 376 */       double len1 = hypot(dx1, dy1);
/* 377 */       dx1 /= len1;
/* 378 */       dy1 /= len1;
/*     */       
/* 380 */       if (this.next.pt.equals(this.pt)) {
/* 381 */         this.pt.y += 0.01F;
/*     */       }
/* 383 */       double dx2 = (this.next.pt.x - this.pt.x);
/* 384 */       double dy2 = (this.next.pt.y - this.pt.y);
/* 385 */       double len2 = hypot(dx2, dy2);
/* 386 */       dx2 /= len2;
/* 387 */       dy2 /= len2;
/*     */       
/* 389 */       double nx1 = -dy1;
/* 390 */       double ny1 = dx1;
/*     */       
/* 392 */       this.nx = (nx1 - dy2) * 0.5D;
/* 393 */       this.ny = (ny1 + dx2) * 0.5D;
/*     */       
/* 395 */       if (this.nx * this.nx + this.ny * this.ny < 1.0E-5D) {
/* 396 */         this.nx = dx1;
/* 397 */         this.ny = dy2;
/* 398 */         this.angle = 1.0D;
/* 399 */         if (dx1 * dx2 + dy1 * dy2 > 0.0D) {
/* 400 */           this.nx = -dx1;
/* 401 */           this.ny = -dy1;
/*     */         } 
/*     */       } else {
/* 404 */         this.angle = this.nx * dx2 + this.ny * dy2;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public double getAngle(Point p) {
/* 415 */       double dx = (p.pt.x - this.pt.x);
/* 416 */       double dy = (p.pt.y - this.pt.y);
/* 417 */       double dlen = hypot(dx, dy);
/*     */       
/* 419 */       return (this.nx * dx + this.ny * dy) / dlen;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isConcave() {
/* 428 */       return (this.angle < 0.0D);
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
/*     */     
/*     */     public boolean isInfront(double dx, double dy) {
/* 441 */       boolean sidePrev = ((this.prev.pt.y - this.pt.y) * dx + (this.pt.x - this.prev.pt.x) * dy >= 0.0D);
/*     */       
/* 443 */       boolean sideNext = ((this.pt.y - this.next.pt.y) * dx + (this.next.pt.x - this.pt.x) * dy >= 0.0D);
/*     */ 
/*     */       
/* 446 */       return (this.angle < 0.0D) ? (sidePrev | sideNext) : (sidePrev & sideNext);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isInfront(Point p) {
/* 456 */       return isInfront((p.pt.x - this.pt.x), (p.pt.y - this.pt.y));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected class PointBag
/*     */     implements Serializable
/*     */   {
/*     */     protected MannTriangulator.Point first;
/*     */ 
/*     */ 
/*     */     
/*     */     protected PointBag next;
/*     */ 
/*     */ 
/*     */     
/*     */     public void clear() {
/* 475 */       if (this.first != null) {
/* 476 */         MannTriangulator.this.freePoints(this.first);
/* 477 */         this.first = null;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void add(MannTriangulator.Point p) {
/* 487 */       if (this.first != null) {
/* 488 */         this.first.insertBefore(p);
/*     */       } else {
/* 490 */         this.first = p;
/* 491 */         p.next = p;
/* 492 */         p.prev = p;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void computeAngles() {
/* 500 */       if (this.first == null) {
/*     */         return;
/*     */       }
/*     */       
/* 504 */       MannTriangulator.Point p = this.first;
/*     */       do {
/* 506 */         p.computeAngle();
/* 507 */       } while ((p = p.next) != this.first);
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
/*     */     public boolean doesIntersectSegment(Vector2f v1, Vector2f v2) {
/* 519 */       double dxA = (v2.x - v1.x);
/* 520 */       double dyA = (v2.y - v1.y);
/*     */       
/* 522 */       MannTriangulator.Point p = this.first; while (true) {
/* 523 */         MannTriangulator.Point n = p.next;
/* 524 */         if (p.pt != v1 && n.pt != v1 && p.pt != v2 && n.pt != v2) {
/* 525 */           double dxB = (n.pt.x - p.pt.x);
/* 526 */           double dyB = (n.pt.y - p.pt.y);
/* 527 */           double d = dxA * dyB - dyA * dxB;
/*     */           
/* 529 */           if (Math.abs(d) > 1.0E-5D) {
/* 530 */             double tmp1 = (p.pt.x - v1.x);
/* 531 */             double tmp2 = (p.pt.y - v1.y);
/* 532 */             double tA = (dyB * tmp1 - dxB * tmp2) / d;
/* 533 */             double tB = (dyA * tmp1 - dxA * tmp2) / d;
/*     */             
/* 535 */             if (tA >= 0.0D && tA <= 1.0D && tB >= 0.0D && tB <= 1.0D) {
/* 536 */               return true;
/*     */             }
/*     */           } 
/*     */         } 
/*     */         
/* 541 */         if (n == this.first) {
/* 542 */           return false;
/*     */         }
/* 544 */         p = n;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int countPoints() {
/* 554 */       if (this.first == null) {
/* 555 */         return 0;
/*     */       }
/*     */       
/* 558 */       int count = 0;
/* 559 */       MannTriangulator.Point p = this.first;
/*     */       do {
/* 561 */         count++;
/* 562 */       } while ((p = p.next) != this.first);
/* 563 */       return count;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean contains(Vector2f point) {
/* 573 */       if (this.first == null) {
/* 574 */         return false;
/*     */       }
/*     */       
/* 577 */       if (this.first.prev.pt.equals(point)) {
/* 578 */         return true;
/*     */       }
/* 580 */       if (this.first.pt.equals(point)) {
/* 581 */         return true;
/*     */       }
/* 583 */       return false;
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean triangulate() {
/* 588 */     Vector2f[] temp = triangulate(new Vector2f[0]);
/*     */     
/* 590 */     for (int i = 0; i < temp.length && 
/* 591 */       temp[i] != null; i++)
/*     */     {
/*     */       
/* 594 */       this.triangles.add(temp[i]);
/*     */     }
/*     */ 
/*     */     
/* 598 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTriangleCount() {
/* 605 */     return this.triangles.size() / 3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] getTrianglePoint(int tri, int i) {
/* 612 */     Vector2f pt = this.triangles.get(tri * 3 + i);
/*     */     
/* 614 */     return new float[] { pt.x, pt.y };
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\geom\MannTriangulator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */