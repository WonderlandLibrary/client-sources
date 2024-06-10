/*   1:    */ package org.newdawn.slick.geom;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ 
/*   5:    */ public class BasicTriangulator
/*   6:    */   implements Triangulator
/*   7:    */ {
/*   8:    */   private static final float EPSILON = 1.0E-010F;
/*   9: 15 */   private PointList poly = new PointList();
/*  10: 17 */   private PointList tris = new PointList();
/*  11:    */   private boolean tried;
/*  12:    */   
/*  13:    */   public void addPolyPoint(float x, float y)
/*  14:    */   {
/*  15: 34 */     Point p = new Point(x, y);
/*  16: 35 */     if (!this.poly.contains(p)) {
/*  17: 36 */       this.poly.add(p);
/*  18:    */     }
/*  19:    */   }
/*  20:    */   
/*  21:    */   public int getPolyPointCount()
/*  22:    */   {
/*  23: 46 */     return this.poly.size();
/*  24:    */   }
/*  25:    */   
/*  26:    */   public float[] getPolyPoint(int index)
/*  27:    */   {
/*  28: 56 */     return new float[] { this.poly.get(index).x, this.poly.get(index).y };
/*  29:    */   }
/*  30:    */   
/*  31:    */   public boolean triangulate()
/*  32:    */   {
/*  33: 65 */     this.tried = true;
/*  34:    */     
/*  35: 67 */     boolean worked = process(this.poly, this.tris);
/*  36: 68 */     return worked;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public int getTriangleCount()
/*  40:    */   {
/*  41: 77 */     if (!this.tried) {
/*  42: 78 */       throw new RuntimeException("Call triangulate() before accessing triangles");
/*  43:    */     }
/*  44: 80 */     return this.tris.size() / 3;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public float[] getTrianglePoint(int tri, int i)
/*  48:    */   {
/*  49: 92 */     if (!this.tried) {
/*  50: 93 */       throw new RuntimeException("Call triangulate() before accessing triangles");
/*  51:    */     }
/*  52: 96 */     return this.tris.get(tri * 3 + i).toArray();
/*  53:    */   }
/*  54:    */   
/*  55:    */   private float area(PointList contour)
/*  56:    */   {
/*  57:108 */     int n = contour.size();
/*  58:    */     
/*  59:110 */     float A = 0.0F;
/*  60:    */     
/*  61:112 */     int p = n - 1;
/*  62:112 */     for (int q = 0; q < n; p = q++)
/*  63:    */     {
/*  64:113 */       Point contourP = contour.get(p);
/*  65:114 */       Point contourQ = contour.get(q);
/*  66:    */       
/*  67:    */ 
/*  68:117 */       A = A + (contourP.getX() * contourQ.getY() - contourQ.getX() * contourP.getY());
/*  69:    */     }
/*  70:119 */     return A * 0.5F;
/*  71:    */   }
/*  72:    */   
/*  73:    */   private boolean insideTriangle(float Ax, float Ay, float Bx, float By, float Cx, float Cy, float Px, float Py)
/*  74:    */   {
/*  75:141 */     float ax = Cx - Bx;
/*  76:142 */     float ay = Cy - By;
/*  77:143 */     float bx = Ax - Cx;
/*  78:144 */     float by = Ay - Cy;
/*  79:145 */     float cx = Bx - Ax;
/*  80:146 */     float cy = By - Ay;
/*  81:147 */     float apx = Px - Ax;
/*  82:148 */     float apy = Py - Ay;
/*  83:149 */     float bpx = Px - Bx;
/*  84:150 */     float bpy = Py - By;
/*  85:151 */     float cpx = Px - Cx;
/*  86:152 */     float cpy = Py - Cy;
/*  87:    */     
/*  88:154 */     float aCROSSbp = ax * bpy - ay * bpx;
/*  89:155 */     float cCROSSap = cx * apy - cy * apx;
/*  90:156 */     float bCROSScp = bx * cpy - by * cpx;
/*  91:    */     
/*  92:158 */     return (aCROSSbp >= 0.0F) && (bCROSScp >= 0.0F) && (cCROSSap >= 0.0F);
/*  93:    */   }
/*  94:    */   
/*  95:    */   private boolean snip(PointList contour, int u, int v, int w, int n, int[] V)
/*  96:    */   {
/*  97:178 */     float Ax = contour.get(V[u]).getX();
/*  98:179 */     float Ay = contour.get(V[u]).getY();
/*  99:    */     
/* 100:181 */     float Bx = contour.get(V[v]).getX();
/* 101:182 */     float By = contour.get(V[v]).getY();
/* 102:    */     
/* 103:184 */     float Cx = contour.get(V[w]).getX();
/* 104:185 */     float Cy = contour.get(V[w]).getY();
/* 105:187 */     if (1.0E-010F > (Bx - Ax) * (Cy - Ay) - (By - Ay) * (Cx - Ax)) {
/* 106:188 */       return false;
/* 107:    */     }
/* 108:191 */     for (int p = 0; p < n; p++) {
/* 109:192 */       if ((p != u) && (p != v) && (p != w))
/* 110:    */       {
/* 111:196 */         float Px = contour.get(V[p]).getX();
/* 112:197 */         float Py = contour.get(V[p]).getY();
/* 113:199 */         if (insideTriangle(Ax, Ay, Bx, By, Cx, Cy, Px, Py)) {
/* 114:200 */           return false;
/* 115:    */         }
/* 116:    */       }
/* 117:    */     }
/* 118:204 */     return true;
/* 119:    */   }
/* 120:    */   
/* 121:    */   private boolean process(PointList contour, PointList result)
/* 122:    */   {
/* 123:216 */     result.clear();
/* 124:    */     
/* 125:    */ 
/* 126:    */ 
/* 127:220 */     int n = contour.size();
/* 128:221 */     if (n < 3) {
/* 129:222 */       return false;
/* 130:    */     }
/* 131:224 */     int[] V = new int[n];
/* 132:228 */     if (0.0F < area(contour)) {
/* 133:229 */       for (int v = 0; v < n; v++) {
/* 134:230 */         V[v] = v;
/* 135:    */       }
/* 136:    */     } else {
/* 137:232 */       for (int v = 0; v < n; v++) {
/* 138:233 */         V[v] = (n - 1 - v);
/* 139:    */       }
/* 140:    */     }
/* 141:236 */     int nv = n;
/* 142:    */     
/* 143:    */ 
/* 144:239 */     int count = 2 * nv;
/* 145:    */     
/* 146:241 */     int m = 0;
/* 147:241 */     for (int v = nv - 1; nv > 2;)
/* 148:    */     {
/* 149:243 */       if (count-- <= 0) {
/* 150:245 */         return false;
/* 151:    */       }
/* 152:249 */       int u = v;
/* 153:250 */       if (nv <= u) {
/* 154:251 */         u = 0;
/* 155:    */       }
/* 156:252 */       v = u + 1;
/* 157:253 */       if (nv <= v) {
/* 158:254 */         v = 0;
/* 159:    */       }
/* 160:255 */       int w = v + 1;
/* 161:256 */       if (nv <= w) {
/* 162:257 */         w = 0;
/* 163:    */       }
/* 164:259 */       if (snip(contour, u, v, w, nv, V))
/* 165:    */       {
/* 166:263 */         int a = V[u];
/* 167:264 */         int b = V[v];
/* 168:265 */         int c = V[w];
/* 169:    */         
/* 170:    */ 
/* 171:268 */         result.add(contour.get(a));
/* 172:269 */         result.add(contour.get(b));
/* 173:270 */         result.add(contour.get(c));
/* 174:    */         
/* 175:272 */         m++;
/* 176:    */         
/* 177:    */ 
/* 178:275 */         int s = v;
/* 179:275 */         for (int t = v + 1; t < nv; t++)
/* 180:    */         {
/* 181:276 */           V[s] = V[t];s++;
/* 182:    */         }
/* 183:278 */         nv--;
/* 184:    */         
/* 185:    */ 
/* 186:281 */         count = 2 * nv;
/* 187:    */       }
/* 188:    */     }
/* 189:285 */     return true;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public void startHole() {}
/* 193:    */   
/* 194:    */   private class Point
/* 195:    */   {
/* 196:    */     private float x;
/* 197:    */     private float y;
/* 198:    */     private float[] array;
/* 199:    */     
/* 200:    */     public Point(float x, float y)
/* 201:    */     {
/* 202:308 */       this.x = x;
/* 203:309 */       this.y = y;
/* 204:310 */       this.array = new float[] { x, y };
/* 205:    */     }
/* 206:    */     
/* 207:    */     public float getX()
/* 208:    */     {
/* 209:319 */       return this.x;
/* 210:    */     }
/* 211:    */     
/* 212:    */     public float getY()
/* 213:    */     {
/* 214:328 */       return this.y;
/* 215:    */     }
/* 216:    */     
/* 217:    */     public float[] toArray()
/* 218:    */     {
/* 219:337 */       return this.array;
/* 220:    */     }
/* 221:    */     
/* 222:    */     public int hashCode()
/* 223:    */     {
/* 224:344 */       return (int)(this.x * this.y * 31.0F);
/* 225:    */     }
/* 226:    */     
/* 227:    */     public boolean equals(Object other)
/* 228:    */     {
/* 229:351 */       if ((other instanceof Point))
/* 230:    */       {
/* 231:352 */         Point p = (Point)other;
/* 232:353 */         return (p.x == this.x) && (p.y == this.y);
/* 233:    */       }
/* 234:356 */       return false;
/* 235:    */     }
/* 236:    */   }
/* 237:    */   
/* 238:    */   private class PointList
/* 239:    */   {
/* 240:367 */     private ArrayList points = new ArrayList();
/* 241:    */     
/* 242:    */     public PointList() {}
/* 243:    */     
/* 244:    */     public boolean contains(BasicTriangulator.Point p)
/* 245:    */     {
/* 246:382 */       return this.points.contains(p);
/* 247:    */     }
/* 248:    */     
/* 249:    */     public void add(BasicTriangulator.Point point)
/* 250:    */     {
/* 251:391 */       this.points.add(point);
/* 252:    */     }
/* 253:    */     
/* 254:    */     public void remove(BasicTriangulator.Point point)
/* 255:    */     {
/* 256:400 */       this.points.remove(point);
/* 257:    */     }
/* 258:    */     
/* 259:    */     public int size()
/* 260:    */     {
/* 261:409 */       return this.points.size();
/* 262:    */     }
/* 263:    */     
/* 264:    */     public BasicTriangulator.Point get(int i)
/* 265:    */     {
/* 266:419 */       return (BasicTriangulator.Point)this.points.get(i);
/* 267:    */     }
/* 268:    */     
/* 269:    */     public void clear()
/* 270:    */     {
/* 271:426 */       this.points.clear();
/* 272:    */     }
/* 273:    */   }
/* 274:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.geom.BasicTriangulator
 * JD-Core Version:    0.7.0.1
 */