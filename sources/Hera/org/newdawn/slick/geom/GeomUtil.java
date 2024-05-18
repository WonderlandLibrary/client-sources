/*     */ package org.newdawn.slick.geom;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GeomUtil
/*     */ {
/*  12 */   public float EPSILON = 1.0E-4F;
/*     */   
/*  14 */   public float EDGE_SCALE = 1.0F;
/*     */   
/*  16 */   public int MAX_POINTS = 10000;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GeomUtilListener listener;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Shape[] subtract(Shape target, Shape missing) {
/*  29 */     target = target.transform(new Transform());
/*  30 */     missing = missing.transform(new Transform());
/*     */     
/*  32 */     int count = 0;
/*  33 */     for (int i = 0; i < target.getPointCount(); i++) {
/*  34 */       if (missing.contains(target.getPoint(i)[0], target.getPoint(i)[1])) {
/*  35 */         count++;
/*     */       }
/*     */     } 
/*     */     
/*  39 */     if (count == target.getPointCount()) {
/*  40 */       return new Shape[0];
/*     */     }
/*     */     
/*  43 */     if (!target.intersects(missing)) {
/*  44 */       return new Shape[] { target };
/*     */     }
/*     */     
/*  47 */     int found = 0; int j;
/*  48 */     for (j = 0; j < missing.getPointCount(); j++) {
/*  49 */       if (target.contains(missing.getPoint(j)[0], missing.getPoint(j)[1]) && 
/*  50 */         !onPath(target, missing.getPoint(j)[0], missing.getPoint(j)[1])) {
/*  51 */         found++;
/*     */       }
/*     */     } 
/*     */     
/*  55 */     for (j = 0; j < target.getPointCount(); j++) {
/*  56 */       if (missing.contains(target.getPoint(j)[0], target.getPoint(j)[1]) && 
/*  57 */         !onPath(missing, target.getPoint(j)[0], target.getPoint(j)[1]))
/*     */       {
/*  59 */         found++;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  64 */     if (found < 1) {
/*  65 */       return new Shape[] { target };
/*     */     }
/*     */     
/*  68 */     return combine(target, missing, true);
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
/*     */   private boolean onPath(Shape path, float x, float y) {
/*  80 */     for (int i = 0; i < path.getPointCount() + 1; i++) {
/*  81 */       int n = rationalPoint(path, i + 1);
/*  82 */       Line line = getLine(path, rationalPoint(path, i), n);
/*  83 */       if (line.distance(new Vector2f(x, y)) < this.EPSILON * 100.0F) {
/*  84 */         return true;
/*     */       }
/*     */     } 
/*     */     
/*  88 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setListener(GeomUtilListener listener) {
/*  97 */     this.listener = listener;
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
/*     */   public Shape[] union(Shape target, Shape other) {
/* 109 */     target = target.transform(new Transform());
/* 110 */     other = other.transform(new Transform());
/*     */     
/* 112 */     if (!target.intersects(other)) {
/* 113 */       return new Shape[] { target, other };
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 118 */     boolean touches = false;
/* 119 */     int buttCount = 0; int i;
/* 120 */     for (i = 0; i < target.getPointCount(); i++) {
/* 121 */       if (other.contains(target.getPoint(i)[0], target.getPoint(i)[1]) && 
/* 122 */         !other.hasVertex(target.getPoint(i)[0], target.getPoint(i)[1])) {
/* 123 */         touches = true;
/*     */         
/*     */         break;
/*     */       } 
/* 127 */       if (other.hasVertex(target.getPoint(i)[0], target.getPoint(i)[1])) {
/* 128 */         buttCount++;
/*     */       }
/*     */     } 
/* 131 */     for (i = 0; i < other.getPointCount(); i++) {
/* 132 */       if (target.contains(other.getPoint(i)[0], other.getPoint(i)[1]) && 
/* 133 */         !target.hasVertex(other.getPoint(i)[0], other.getPoint(i)[1])) {
/* 134 */         touches = true;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*     */     
/* 140 */     if (!touches && buttCount < 2) {
/* 141 */       return new Shape[] { target, other };
/*     */     }
/*     */ 
/*     */     
/* 145 */     return combine(target, other, false);
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
/*     */   private Shape[] combine(Shape target, Shape other, boolean subtract) {
/* 157 */     if (subtract) {
/* 158 */       ArrayList<Shape> shapes = new ArrayList();
/* 159 */       ArrayList<Vector2f> used = new ArrayList();
/*     */       
/*     */       int j;
/*     */       
/* 163 */       for (j = 0; j < target.getPointCount(); j++) {
/* 164 */         float[] point = target.getPoint(j);
/* 165 */         if (other.contains(point[0], point[1])) {
/* 166 */           used.add(new Vector2f(point[0], point[1]));
/* 167 */           if (this.listener != null) {
/* 168 */             this.listener.pointExcluded(point[0], point[1]);
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 173 */       for (j = 0; j < target.getPointCount(); j++) {
/* 174 */         float[] point = target.getPoint(j);
/* 175 */         Vector2f pt = new Vector2f(point[0], point[1]);
/*     */         
/* 177 */         if (!used.contains(pt)) {
/* 178 */           Shape result = combineSingle(target, other, true, j);
/* 179 */           shapes.add(result);
/* 180 */           for (int k = 0; k < result.getPointCount(); k++) {
/* 181 */             float[] kpoint = result.getPoint(k);
/* 182 */             Vector2f kpt = new Vector2f(kpoint[0], kpoint[1]);
/* 183 */             used.add(kpt);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 188 */       return shapes.<Shape>toArray(new Shape[0]);
/*     */     } 
/* 190 */     for (int i = 0; i < target.getPointCount(); i++) {
/* 191 */       if (!other.contains(target.getPoint(i)[0], target.getPoint(i)[1]) && 
/* 192 */         !other.hasVertex(target.getPoint(i)[0], target.getPoint(i)[1])) {
/* 193 */         Shape shape = combineSingle(target, other, false, i);
/* 194 */         return new Shape[] { shape };
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 199 */     return new Shape[] { other };
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
/*     */   private Shape combineSingle(Shape target, Shape missing, boolean subtract, int start) {
/* 213 */     Shape current = target;
/* 214 */     Shape other = missing;
/* 215 */     int point = start;
/* 216 */     int dir = 1;
/*     */     
/* 218 */     Polygon poly = new Polygon();
/* 219 */     boolean first = true;
/*     */     
/* 221 */     int loop = 0;
/*     */ 
/*     */     
/* 224 */     float px = current.getPoint(point)[0];
/* 225 */     float py = current.getPoint(point)[1];
/*     */     
/* 227 */     while (!poly.hasVertex(px, py) || first || current != target) {
/* 228 */       first = false;
/* 229 */       loop++;
/* 230 */       if (loop > this.MAX_POINTS) {
/*     */         break;
/*     */       }
/*     */ 
/*     */       
/* 235 */       poly.addPoint(px, py);
/* 236 */       if (this.listener != null) {
/* 237 */         this.listener.pointUsed(px, py);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 243 */       Line line = getLine(current, px, py, rationalPoint(current, point + dir));
/* 244 */       HitResult hit = intersect(other, line);
/*     */       
/* 246 */       if (hit != null) {
/* 247 */         Line hitLine = hit.line;
/* 248 */         Vector2f pt = hit.pt;
/* 249 */         px = pt.x;
/* 250 */         py = pt.y;
/*     */         
/* 252 */         if (this.listener != null) {
/* 253 */           this.listener.pointIntersected(px, py);
/*     */         }
/*     */         
/* 256 */         if (other.hasVertex(px, py)) {
/* 257 */           point = other.indexOf(pt.x, pt.y);
/* 258 */           dir = 1;
/* 259 */           px = pt.x;
/* 260 */           py = pt.y;
/*     */           
/* 262 */           Shape shape = current;
/* 263 */           current = other;
/* 264 */           other = shape;
/*     */           
/*     */           continue;
/*     */         } 
/* 268 */         float dx = hitLine.getDX() / hitLine.length();
/* 269 */         float dy = hitLine.getDY() / hitLine.length();
/* 270 */         dx *= this.EDGE_SCALE;
/* 271 */         dy *= this.EDGE_SCALE;
/*     */         
/* 273 */         if (current.contains(pt.x + dx, pt.y + dy)) {
/*     */ 
/*     */           
/* 276 */           if (subtract) {
/* 277 */             if (current == missing) {
/* 278 */               point = hit.p2;
/* 279 */               dir = -1;
/*     */             } else {
/* 281 */               point = hit.p1;
/* 282 */               dir = 1;
/*     */             }
/*     */           
/* 285 */           } else if (current == target) {
/* 286 */             point = hit.p2;
/* 287 */             dir = -1;
/*     */           } else {
/* 289 */             point = hit.p2;
/* 290 */             dir = -1;
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 295 */           Shape shape = current;
/* 296 */           current = other;
/* 297 */           other = shape; continue;
/* 298 */         }  if (current.contains(pt.x - dx, pt.y - dy)) {
/* 299 */           if (subtract) {
/* 300 */             if (current == target) {
/* 301 */               point = hit.p2;
/* 302 */               dir = -1;
/*     */             } else {
/* 304 */               point = hit.p1;
/* 305 */               dir = 1;
/*     */             }
/*     */           
/* 308 */           } else if (current == missing) {
/* 309 */             point = hit.p1;
/* 310 */             dir = 1;
/*     */           } else {
/* 312 */             point = hit.p1;
/* 313 */             dir = 1;
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 318 */           Shape shape = current;
/* 319 */           current = other;
/* 320 */           other = shape;
/*     */           continue;
/*     */         } 
/* 323 */         if (subtract) {
/*     */           break;
/*     */         }
/* 326 */         point = hit.p1;
/* 327 */         dir = 1;
/* 328 */         Shape temp = current;
/* 329 */         current = other;
/* 330 */         other = temp;
/*     */         
/* 332 */         point = rationalPoint(current, point + dir);
/* 333 */         px = current.getPoint(point)[0];
/* 334 */         py = current.getPoint(point)[1];
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 339 */       point = rationalPoint(current, point + dir);
/* 340 */       px = current.getPoint(point)[0];
/* 341 */       py = current.getPoint(point)[1];
/*     */     } 
/*     */ 
/*     */     
/* 345 */     poly.addPoint(px, py);
/* 346 */     if (this.listener != null) {
/* 347 */       this.listener.pointUsed(px, py);
/*     */     }
/*     */     
/* 350 */     return poly;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HitResult intersect(Shape shape, Line line) {
/* 361 */     float distance = Float.MAX_VALUE;
/* 362 */     HitResult hit = null;
/*     */     
/* 364 */     for (int i = 0; i < shape.getPointCount(); i++) {
/* 365 */       int next = rationalPoint(shape, i + 1);
/* 366 */       Line local = getLine(shape, i, next);
/*     */       
/* 368 */       Vector2f pt = line.intersect(local, true);
/* 369 */       if (pt != null) {
/* 370 */         float newDis = pt.distance(line.getStart());
/* 371 */         if (newDis < distance && newDis > this.EPSILON) {
/* 372 */           hit = new HitResult();
/* 373 */           hit.pt = pt;
/* 374 */           hit.line = local;
/* 375 */           hit.p1 = i;
/* 376 */           hit.p2 = next;
/* 377 */           distance = newDis;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 382 */     return hit;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int rationalPoint(Shape shape, int p) {
/* 393 */     while (p < 0) {
/* 394 */       p += shape.getPointCount();
/*     */     }
/* 396 */     while (p >= shape.getPointCount()) {
/* 397 */       p -= shape.getPointCount();
/*     */     }
/*     */     
/* 400 */     return p;
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
/*     */   public Line getLine(Shape shape, int s, int e) {
/* 412 */     float[] start = shape.getPoint(s);
/* 413 */     float[] end = shape.getPoint(e);
/*     */     
/* 415 */     Line line = new Line(start[0], start[1], end[0], end[1]);
/* 416 */     return line;
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
/*     */   public Line getLine(Shape shape, float sx, float sy, int e) {
/* 429 */     float[] end = shape.getPoint(e);
/*     */     
/* 431 */     Line line = new Line(sx, sy, end[0], end[1]);
/* 432 */     return line;
/*     */   }
/*     */   
/*     */   public class HitResult {
/*     */     public Line line;
/*     */     public int p1;
/*     */     public int p2;
/*     */     public Vector2f pt;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\geom\GeomUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */