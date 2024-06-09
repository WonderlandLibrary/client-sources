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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NeatTriangulator
/*     */   implements Triangulator
/*     */ {
/*     */   static final float EPSILON = 1.0E-6F;
/*     */   private float[] pointsX;
/*     */   private float[] pointsY;
/*     */   private int numPoints;
/*     */   private Edge[] edges;
/*     */   private int[] V;
/*     */   private int numEdges;
/*     */   private Triangle[] triangles;
/*     */   private int numTriangles;
/*  31 */   private float offset = 1.0E-6F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NeatTriangulator() {
/*  38 */     this.pointsX = new float[100];
/*  39 */     this.pointsY = new float[100];
/*  40 */     this.numPoints = 0;
/*  41 */     this.edges = new Edge[100];
/*  42 */     this.numEdges = 0;
/*  43 */     this.triangles = new Triangle[100];
/*  44 */     this.numTriangles = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/*  52 */     this.numPoints = 0;
/*  53 */     this.numEdges = 0;
/*  54 */     this.numTriangles = 0;
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
/*     */   private int findEdge(int i, int j) {
/*     */     int k;
/*     */     int l;
/*  68 */     if (i < j) {
/*     */       
/*  70 */       k = i;
/*  71 */       l = j;
/*     */     } else {
/*     */       
/*  74 */       k = j;
/*  75 */       l = i;
/*     */     } 
/*  77 */     for (int i1 = 0; i1 < this.numEdges; i1++) {
/*  78 */       if ((this.edges[i1]).v0 == k && (this.edges[i1]).v1 == l)
/*  79 */         return i1; 
/*     */     } 
/*  81 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addEdge(int i, int j, int k) {
/*     */     int m, n;
/*     */     Edge edge;
/*  93 */     int l, i1, l1 = findEdge(i, j);
/*     */ 
/*     */ 
/*     */     
/*  97 */     if (l1 < 0) {
/*     */       
/*  99 */       if (this.numEdges == this.edges.length) {
/*     */         
/* 101 */         Edge[] aedge = new Edge[this.edges.length * 2];
/* 102 */         System.arraycopy(this.edges, 0, aedge, 0, this.numEdges);
/* 103 */         this.edges = aedge;
/*     */       } 
/* 105 */       m = -1;
/* 106 */       n = -1;
/* 107 */       l1 = this.numEdges++;
/* 108 */       edge = this.edges[l1] = new Edge();
/*     */     } else {
/*     */       
/* 111 */       edge = this.edges[l1];
/* 112 */       m = edge.t0;
/* 113 */       n = edge.t1;
/*     */     } 
/*     */ 
/*     */     
/* 117 */     if (i < j) {
/*     */       
/* 119 */       l = i;
/* 120 */       i1 = j;
/* 121 */       m = k;
/*     */     } else {
/*     */       
/* 124 */       l = j;
/* 125 */       i1 = i;
/* 126 */       n = k;
/*     */     } 
/* 128 */     edge.v0 = l;
/* 129 */     edge.v1 = i1;
/* 130 */     edge.t0 = m;
/* 131 */     edge.t1 = n;
/* 132 */     edge.suspect = true;
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
/*     */   private void deleteEdge(int i, int j) throws InternalException {
/*     */     int k;
/* 145 */     if (0 > (k = findEdge(i, j)))
/*     */     {
/* 147 */       throw new InternalException("Attempt to delete unknown edge");
/*     */     }
/*     */ 
/*     */     
/* 151 */     this.edges[k] = this.edges[--this.numEdges];
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
/*     */   void markSuspect(int i, int j, boolean flag) throws InternalException {
/*     */     int k;
/* 167 */     if (0 > (k = findEdge(i, j)))
/*     */     {
/* 169 */       throw new InternalException("Attempt to mark unknown edge");
/*     */     }
/*     */     
/* 172 */     (this.edges[k]).suspect = flag;
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
/*     */   private Edge chooseSuspect() {
/* 184 */     for (int i = 0; i < this.numEdges; i++) {
/*     */       
/* 186 */       Edge edge = this.edges[i];
/* 187 */       if (edge.suspect) {
/*     */         
/* 189 */         edge.suspect = false;
/* 190 */         if (edge.t0 >= 0 && edge.t1 >= 0) {
/* 191 */           return edge;
/*     */         }
/*     */       } 
/*     */     } 
/* 195 */     return null;
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
/*     */   private static float rho(float f, float f1, float f2, float f3, float f4, float f5) {
/* 211 */     float f6 = f4 - f2;
/* 212 */     float f7 = f5 - f3;
/* 213 */     float f8 = f - f4;
/* 214 */     float f9 = f1 - f5;
/* 215 */     float f18 = f6 * f9 - f7 * f8;
/* 216 */     if (f18 > 0.0F) {
/*     */       
/* 218 */       if (f18 < 1.0E-6F)
/* 219 */         f18 = 1.0E-6F; 
/* 220 */       float f12 = f6 * f6;
/* 221 */       float f13 = f7 * f7;
/* 222 */       float f14 = f8 * f8;
/* 223 */       float f15 = f9 * f9;
/* 224 */       float f10 = f2 - f;
/* 225 */       float f11 = f3 - f1;
/* 226 */       float f16 = f10 * f10;
/* 227 */       float f17 = f11 * f11;
/* 228 */       return (f12 + f13) * (f14 + f15) * (f16 + f17) / f18 * f18;
/*     */     } 
/*     */     
/* 231 */     return -1.0F;
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
/*     */   private static boolean insideTriangle(float f, float f1, float f2, float f3, float f4, float f5, float f6, float f7) {
/* 251 */     float f8 = f4 - f2;
/* 252 */     float f9 = f5 - f3;
/* 253 */     float f10 = f - f4;
/* 254 */     float f11 = f1 - f5;
/* 255 */     float f12 = f2 - f;
/* 256 */     float f13 = f3 - f1;
/* 257 */     float f14 = f6 - f;
/* 258 */     float f15 = f7 - f1;
/* 259 */     float f16 = f6 - f2;
/* 260 */     float f17 = f7 - f3;
/* 261 */     float f18 = f6 - f4;
/* 262 */     float f19 = f7 - f5;
/* 263 */     float f22 = f8 * f17 - f9 * f16;
/* 264 */     float f20 = f12 * f15 - f13 * f14;
/* 265 */     float f21 = f10 * f19 - f11 * f18;
/* 266 */     return (f22 >= 0.0D && f21 >= 0.0D && f20 >= 0.0D);
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
/*     */   private boolean snip(int i, int j, int k, int l) {
/* 281 */     float f = this.pointsX[this.V[i]];
/* 282 */     float f1 = this.pointsY[this.V[i]];
/* 283 */     float f2 = this.pointsX[this.V[j]];
/* 284 */     float f3 = this.pointsY[this.V[j]];
/* 285 */     float f4 = this.pointsX[this.V[k]];
/* 286 */     float f5 = this.pointsY[this.V[k]];
/* 287 */     if (1.0E-6F > (f2 - f) * (f5 - f1) - (f3 - f1) * (f4 - f))
/* 288 */       return false; 
/* 289 */     for (int i1 = 0; i1 < l; i1++) {
/* 290 */       if (i1 != i && i1 != j && i1 != k) {
/*     */         
/* 292 */         float f6 = this.pointsX[this.V[i1]];
/* 293 */         float f7 = this.pointsY[this.V[i1]];
/* 294 */         if (insideTriangle(f, f1, f2, f3, f4, f5, f6, f7))
/* 295 */           return false; 
/*     */       } 
/*     */     } 
/* 298 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float area() {
/* 308 */     float f = 0.0F;
/* 309 */     int i = this.numPoints - 1;
/* 310 */     for (int j = 0; j < this.numPoints; ) {
/*     */       
/* 312 */       f += this.pointsX[i] * this.pointsY[j] - this.pointsY[i] * this.pointsX[j];
/* 313 */       i = j++;
/*     */     } 
/*     */     
/* 316 */     return f * 0.5F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void basicTriangulation() throws InternalException {
/* 326 */     int i = this.numPoints;
/* 327 */     if (i < 3)
/*     */       return; 
/* 329 */     this.numEdges = 0;
/* 330 */     this.numTriangles = 0;
/* 331 */     this.V = new int[i];
/*     */     
/* 333 */     if (0.0D < area()) {
/*     */       
/* 335 */       for (int k = 0; k < i; k++) {
/* 336 */         this.V[k] = k;
/*     */       }
/*     */     } else {
/*     */       
/* 340 */       for (int l = 0; l < i; l++) {
/* 341 */         this.V[l] = this.numPoints - 1 - l;
/*     */       }
/*     */     } 
/* 344 */     int k1 = 2 * i;
/* 345 */     int i1 = i - 1;
/* 346 */     while (i > 2) {
/*     */       
/* 348 */       if (0 >= k1--) {
/* 349 */         throw new InternalException("Bad polygon");
/*     */       }
/*     */       
/* 352 */       int j = i1;
/* 353 */       if (i <= j)
/* 354 */         j = 0; 
/* 355 */       i1 = j + 1;
/* 356 */       if (i <= i1)
/* 357 */         i1 = 0; 
/* 358 */       int j1 = i1 + 1;
/* 359 */       if (i <= j1)
/* 360 */         j1 = 0; 
/* 361 */       if (snip(j, i1, j1, i)) {
/*     */         
/* 363 */         int l1 = this.V[j];
/* 364 */         int i2 = this.V[i1];
/* 365 */         int j2 = this.V[j1];
/* 366 */         if (this.numTriangles == this.triangles.length) {
/*     */           
/* 368 */           Triangle[] atriangle = new Triangle[this.triangles.length * 2];
/* 369 */           System.arraycopy(this.triangles, 0, atriangle, 0, this.numTriangles);
/* 370 */           this.triangles = atriangle;
/*     */         } 
/* 372 */         this.triangles[this.numTriangles] = new Triangle(l1, i2, j2);
/* 373 */         addEdge(l1, i2, this.numTriangles);
/* 374 */         addEdge(i2, j2, this.numTriangles);
/* 375 */         addEdge(j2, l1, this.numTriangles);
/* 376 */         this.numTriangles++;
/* 377 */         int k2 = i1;
/* 378 */         for (int l2 = i1 + 1; l2 < i; l2++) {
/*     */           
/* 380 */           this.V[k2] = this.V[l2];
/* 381 */           k2++;
/*     */         } 
/*     */         
/* 384 */         i--;
/* 385 */         k1 = 2 * i;
/*     */       } 
/*     */     } 
/* 388 */     this.V = null;
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
/*     */   private void optimize() throws InternalException {
/*     */     Edge edge;
/* 401 */     while ((edge = chooseSuspect()) != null) {
/*     */ 
/*     */       
/* 404 */       int i1 = edge.v0;
/* 405 */       int k1 = edge.v1;
/* 406 */       int i = edge.t0;
/* 407 */       int j = edge.t1;
/* 408 */       int j1 = -1;
/* 409 */       int l1 = -1;
/* 410 */       for (int k = 0; k < 3; ) {
/*     */         
/* 412 */         int i2 = (this.triangles[i]).v[k];
/* 413 */         if (i1 == i2 || k1 == i2) {
/*     */           k++; continue;
/*     */         } 
/* 416 */         l1 = i2;
/*     */       } 
/*     */ 
/*     */       
/* 420 */       for (int l = 0; l < 3; ) {
/*     */         
/* 422 */         int j2 = (this.triangles[j]).v[l];
/* 423 */         if (i1 == j2 || k1 == j2) {
/*     */           l++; continue;
/*     */         } 
/* 426 */         j1 = j2;
/*     */       } 
/*     */ 
/*     */       
/* 430 */       if (-1 == j1 || -1 == l1) {
/* 431 */         throw new InternalException("can't find quad");
/*     */       }
/*     */       
/* 434 */       float f = this.pointsX[i1];
/* 435 */       float f1 = this.pointsY[i1];
/* 436 */       float f2 = this.pointsX[j1];
/* 437 */       float f3 = this.pointsY[j1];
/* 438 */       float f4 = this.pointsX[k1];
/* 439 */       float f5 = this.pointsY[k1];
/* 440 */       float f6 = this.pointsX[l1];
/* 441 */       float f7 = this.pointsY[l1];
/* 442 */       float f8 = rho(f, f1, f2, f3, f4, f5);
/* 443 */       float f9 = rho(f, f1, f4, f5, f6, f7);
/* 444 */       float f10 = rho(f2, f3, f4, f5, f6, f7);
/* 445 */       float f11 = rho(f2, f3, f6, f7, f, f1);
/* 446 */       if (0.0F > f8 || 0.0F > f9) {
/* 447 */         throw new InternalException("original triangles backwards");
/*     */       }
/* 449 */       if (0.0F <= f10 && 0.0F <= f11) {
/*     */         
/* 451 */         if (f8 > f9) {
/* 452 */           f8 = f9;
/*     */         }
/* 454 */         if (f10 > f11) {
/* 455 */           f10 = f11;
/*     */         }
/* 457 */         if (f8 > f10) {
/* 458 */           deleteEdge(i1, k1);
/* 459 */           (this.triangles[i]).v[0] = j1;
/* 460 */           (this.triangles[i]).v[1] = k1;
/* 461 */           (this.triangles[i]).v[2] = l1;
/* 462 */           (this.triangles[j]).v[0] = j1;
/* 463 */           (this.triangles[j]).v[1] = l1;
/* 464 */           (this.triangles[j]).v[2] = i1;
/* 465 */           addEdge(j1, k1, i);
/* 466 */           addEdge(k1, l1, i);
/* 467 */           addEdge(l1, j1, i);
/* 468 */           addEdge(l1, i1, j);
/* 469 */           addEdge(i1, j1, j);
/* 470 */           addEdge(j1, l1, j);
/* 471 */           markSuspect(j1, l1, false);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean triangulate() {
/*     */     try {
/* 484 */       basicTriangulation();
/*     */       
/* 486 */       return true;
/*     */     }
/* 488 */     catch (InternalException e) {
/*     */       
/* 490 */       this.numEdges = 0;
/*     */       
/* 492 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addPolyPoint(float x, float y) {
/* 500 */     for (int i = 0; i < this.numPoints; i++) {
/* 501 */       if (this.pointsX[i] == x && this.pointsY[i] == y) {
/*     */         
/* 503 */         y += this.offset;
/* 504 */         this.offset += 1.0E-6F;
/*     */       } 
/*     */     } 
/*     */     
/* 508 */     if (this.numPoints == this.pointsX.length) {
/*     */       
/* 510 */       float[] af = new float[this.numPoints * 2];
/* 511 */       System.arraycopy(this.pointsX, 0, af, 0, this.numPoints);
/* 512 */       this.pointsX = af;
/* 513 */       af = new float[this.numPoints * 2];
/* 514 */       System.arraycopy(this.pointsY, 0, af, 0, this.numPoints);
/* 515 */       this.pointsY = af;
/*     */     } 
/*     */     
/* 518 */     this.pointsX[this.numPoints] = x;
/* 519 */     this.pointsY[this.numPoints] = y;
/* 520 */     this.numPoints++;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   class Triangle
/*     */   {
/*     */     int[] v;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Triangle(int i, int j, int k) {
/* 542 */       this.v = new int[3];
/* 543 */       this.v[0] = i;
/* 544 */       this.v[1] = j;
/* 545 */       this.v[2] = k;
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
/*     */   class Edge
/*     */   {
/* 572 */     int v0 = -1;
/* 573 */     int v1 = -1;
/* 574 */     int t0 = -1;
/* 575 */     int t1 = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     boolean suspect;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   class InternalException
/*     */     extends Exception
/*     */   {
/*     */     public InternalException(String msg) {
/* 591 */       super(msg);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTriangleCount() {
/* 599 */     return this.numTriangles;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] getTrianglePoint(int tri, int i) {
/* 606 */     float xp = this.pointsX[(this.triangles[tri]).v[i]];
/* 607 */     float yp = this.pointsY[(this.triangles[tri]).v[i]];
/*     */     
/* 609 */     return new float[] { xp, yp };
/*     */   }
/*     */   
/*     */   public void startHole() {}
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\geom\NeatTriangulator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */