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
/*     */ public class BasicTriangulator
/*     */   implements Triangulator
/*     */ {
/*     */   private static final float EPSILON = 1.0E-10F;
/*  15 */   private PointList poly = new PointList();
/*     */   
/*  17 */   private PointList tris = new PointList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean tried;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addPolyPoint(float x, float y) {
/*  34 */     Point p = new Point(x, y);
/*  35 */     if (!this.poly.contains(p)) {
/*  36 */       this.poly.add(p);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPolyPointCount() {
/*  46 */     return this.poly.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] getPolyPoint(int index) {
/*  56 */     return new float[] { Point.access$000(this.poly.get(index)), Point.access$100(this.poly.get(index)) };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean triangulate() {
/*  65 */     this.tried = true;
/*     */     
/*  67 */     boolean worked = process(this.poly, this.tris);
/*  68 */     return worked;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTriangleCount() {
/*  77 */     if (!this.tried) {
/*  78 */       throw new RuntimeException("Call triangulate() before accessing triangles");
/*     */     }
/*  80 */     return this.tris.size() / 3;
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
/*     */   public float[] getTrianglePoint(int tri, int i) {
/*  92 */     if (!this.tried) {
/*  93 */       throw new RuntimeException("Call triangulate() before accessing triangles");
/*     */     }
/*     */     
/*  96 */     return this.tris.get(tri * 3 + i).toArray();
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
/*     */   private float area(PointList contour) {
/* 108 */     int n = contour.size();
/*     */     
/* 110 */     float A = 0.0F;
/*     */     int p, q;
/* 112 */     for (p = n - 1, q = 0; q < n; p = q++) {
/* 113 */       Point contourP = contour.get(p);
/* 114 */       Point contourQ = contour.get(q);
/*     */       
/* 116 */       A += contourP.getX() * contourQ.getY() - contourQ.getX() * contourP.getY();
/*     */     } 
/*     */     
/* 119 */     return A * 0.5F;
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
/*     */   private boolean insideTriangle(float Ax, float Ay, float Bx, float By, float Cx, float Cy, float Px, float Py) {
/* 141 */     float ax = Cx - Bx;
/* 142 */     float ay = Cy - By;
/* 143 */     float bx = Ax - Cx;
/* 144 */     float by = Ay - Cy;
/* 145 */     float cx = Bx - Ax;
/* 146 */     float cy = By - Ay;
/* 147 */     float apx = Px - Ax;
/* 148 */     float apy = Py - Ay;
/* 149 */     float bpx = Px - Bx;
/* 150 */     float bpy = Py - By;
/* 151 */     float cpx = Px - Cx;
/* 152 */     float cpy = Py - Cy;
/*     */     
/* 154 */     float aCROSSbp = ax * bpy - ay * bpx;
/* 155 */     float cCROSSap = cx * apy - cy * apx;
/* 156 */     float bCROSScp = bx * cpy - by * cpx;
/*     */     
/* 158 */     return (aCROSSbp >= 0.0F && bCROSScp >= 0.0F && cCROSSap >= 0.0F);
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
/*     */   private boolean snip(PointList contour, int u, int v, int w, int n, int[] V) {
/* 178 */     float Ax = contour.get(V[u]).getX();
/* 179 */     float Ay = contour.get(V[u]).getY();
/*     */     
/* 181 */     float Bx = contour.get(V[v]).getX();
/* 182 */     float By = contour.get(V[v]).getY();
/*     */     
/* 184 */     float Cx = contour.get(V[w]).getX();
/* 185 */     float Cy = contour.get(V[w]).getY();
/*     */     
/* 187 */     if (1.0E-10F > (Bx - Ax) * (Cy - Ay) - (By - Ay) * (Cx - Ax)) {
/* 188 */       return false;
/*     */     }
/*     */     
/* 191 */     for (int p = 0; p < n; p++) {
/* 192 */       if (p != u && p != v && p != w) {
/*     */ 
/*     */ 
/*     */         
/* 196 */         float Px = contour.get(V[p]).getX();
/* 197 */         float Py = contour.get(V[p]).getY();
/*     */         
/* 199 */         if (insideTriangle(Ax, Ay, Bx, By, Cx, Cy, Px, Py)) {
/* 200 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/* 204 */     return true;
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
/*     */   private boolean process(PointList contour, PointList result) {
/* 216 */     result.clear();
/*     */ 
/*     */ 
/*     */     
/* 220 */     int n = contour.size();
/* 221 */     if (n < 3) {
/* 222 */       return false;
/*     */     }
/* 224 */     int[] V = new int[n];
/*     */ 
/*     */ 
/*     */     
/* 228 */     if (0.0F < area(contour)) {
/* 229 */       for (int i = 0; i < n; i++)
/* 230 */         V[i] = i; 
/*     */     } else {
/* 232 */       for (int i = 0; i < n; i++) {
/* 233 */         V[i] = n - 1 - i;
/*     */       }
/*     */     } 
/* 236 */     int nv = n;
/*     */ 
/*     */     
/* 239 */     int count = 2 * nv;
/*     */     
/* 241 */     for (int m = 0, v = nv - 1; nv > 2; ) {
/*     */       
/* 243 */       if (0 >= count--)
/*     */       {
/* 245 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 249 */       int u = v;
/* 250 */       if (nv <= u)
/* 251 */         u = 0; 
/* 252 */       v = u + 1;
/* 253 */       if (nv <= v)
/* 254 */         v = 0; 
/* 255 */       int w = v + 1;
/* 256 */       if (nv <= w) {
/* 257 */         w = 0;
/*     */       }
/* 259 */       if (snip(contour, u, v, w, nv, V)) {
/*     */ 
/*     */ 
/*     */         
/* 263 */         int a = V[u];
/* 264 */         int b = V[v];
/* 265 */         int c = V[w];
/*     */ 
/*     */         
/* 268 */         result.add(contour.get(a));
/* 269 */         result.add(contour.get(b));
/* 270 */         result.add(contour.get(c));
/*     */         
/* 272 */         m++;
/*     */ 
/*     */         
/* 275 */         for (int s = v, t = v + 1; t < nv; s++, t++) {
/* 276 */           V[s] = V[t];
/*     */         }
/* 278 */         nv--;
/*     */ 
/*     */         
/* 281 */         count = 2 * nv;
/*     */       } 
/*     */     } 
/*     */     
/* 285 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class Point
/*     */   {
/*     */     private float x;
/*     */ 
/*     */ 
/*     */     
/*     */     private float y;
/*     */ 
/*     */ 
/*     */     
/*     */     private float[] array;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Point(float x, float y) {
/* 308 */       this.x = x;
/* 309 */       this.y = y;
/* 310 */       this.array = new float[] { x, y };
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public float getX() {
/* 319 */       return this.x;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public float getY() {
/* 328 */       return this.y;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public float[] toArray() {
/* 337 */       return this.array;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 344 */       return (int)(this.x * this.y * 31.0F);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean equals(Object other) {
/* 351 */       if (other instanceof Point) {
/* 352 */         Point p = (Point)other;
/* 353 */         return (p.x == this.x && p.y == this.y);
/*     */       } 
/*     */       
/* 356 */       return false;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class PointList
/*     */   {
/* 367 */     private ArrayList points = new ArrayList();
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
/*     */     public boolean contains(BasicTriangulator.Point p) {
/* 382 */       return this.points.contains(p);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void add(BasicTriangulator.Point point) {
/* 391 */       this.points.add(point);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void remove(BasicTriangulator.Point point) {
/* 400 */       this.points.remove(point);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int size() {
/* 409 */       return this.points.size();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public BasicTriangulator.Point get(int i) {
/* 419 */       return this.points.get(i);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void clear() {
/* 426 */       this.points.clear();
/*     */     }
/*     */   }
/*     */   
/*     */   public void startHole() {}
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\geom\BasicTriangulator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */