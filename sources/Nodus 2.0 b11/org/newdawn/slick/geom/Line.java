/*   1:    */ package org.newdawn.slick.geom;
/*   2:    */ 
/*   3:    */ public class Line
/*   4:    */   extends Shape
/*   5:    */ {
/*   6:    */   private Vector2f start;
/*   7:    */   private Vector2f end;
/*   8:    */   private Vector2f vec;
/*   9:    */   private float lenSquared;
/*  10: 21 */   private Vector2f loc = new Vector2f(0.0F, 0.0F);
/*  11: 23 */   private Vector2f v = new Vector2f(0.0F, 0.0F);
/*  12: 25 */   private Vector2f v2 = new Vector2f(0.0F, 0.0F);
/*  13: 27 */   private Vector2f proj = new Vector2f(0.0F, 0.0F);
/*  14: 30 */   private Vector2f closest = new Vector2f(0.0F, 0.0F);
/*  15: 32 */   private Vector2f other = new Vector2f(0.0F, 0.0F);
/*  16: 35 */   private boolean outerEdge = true;
/*  17: 37 */   private boolean innerEdge = true;
/*  18:    */   
/*  19:    */   public Line(float x, float y, boolean inner, boolean outer)
/*  20:    */   {
/*  21: 52 */     this(0.0F, 0.0F, x, y);
/*  22:    */   }
/*  23:    */   
/*  24:    */   public Line(float x, float y)
/*  25:    */   {
/*  26: 64 */     this(x, y, true, true);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public Line(float x1, float y1, float x2, float y2)
/*  30:    */   {
/*  31: 80 */     this(new Vector2f(x1, y1), new Vector2f(x2, y2));
/*  32:    */   }
/*  33:    */   
/*  34:    */   public Line(float x1, float y1, float dx, float dy, boolean dummy)
/*  35:    */   {
/*  36: 98 */     this(new Vector2f(x1, y1), new Vector2f(x1 + dx, y1 + dy));
/*  37:    */   }
/*  38:    */   
/*  39:    */   public Line(float[] start, float[] end)
/*  40:    */   {
/*  41:112 */     set(start, end);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public Line(Vector2f start, Vector2f end)
/*  45:    */   {
/*  46:126 */     set(start, end);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void set(float[] start, float[] end)
/*  50:    */   {
/*  51:138 */     set(start[0], start[1], end[0], end[1]);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public Vector2f getStart()
/*  55:    */   {
/*  56:147 */     return this.start;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public Vector2f getEnd()
/*  60:    */   {
/*  61:156 */     return this.end;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public float length()
/*  65:    */   {
/*  66:165 */     return this.vec.length();
/*  67:    */   }
/*  68:    */   
/*  69:    */   public float lengthSquared()
/*  70:    */   {
/*  71:174 */     return this.vec.lengthSquared();
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void set(Vector2f start, Vector2f end)
/*  75:    */   {
/*  76:186 */     this.pointsDirty = true;
/*  77:187 */     if (this.start == null) {
/*  78:188 */       this.start = new Vector2f();
/*  79:    */     }
/*  80:190 */     this.start.set(start);
/*  81:192 */     if (this.end == null) {
/*  82:193 */       this.end = new Vector2f();
/*  83:    */     }
/*  84:195 */     this.end.set(end);
/*  85:    */     
/*  86:197 */     this.vec = new Vector2f(end);
/*  87:198 */     this.vec.sub(start);
/*  88:    */     
/*  89:200 */     this.lenSquared = this.vec.lengthSquared();
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void set(float sx, float sy, float ex, float ey)
/*  93:    */   {
/*  94:216 */     this.pointsDirty = true;
/*  95:217 */     this.start.set(sx, sy);
/*  96:218 */     this.end.set(ex, ey);
/*  97:219 */     float dx = ex - sx;
/*  98:220 */     float dy = ey - sy;
/*  99:221 */     this.vec.set(dx, dy);
/* 100:    */     
/* 101:223 */     this.lenSquared = (dx * dx + dy * dy);
/* 102:    */   }
/* 103:    */   
/* 104:    */   public float getDX()
/* 105:    */   {
/* 106:232 */     return this.end.getX() - this.start.getX();
/* 107:    */   }
/* 108:    */   
/* 109:    */   public float getDY()
/* 110:    */   {
/* 111:241 */     return this.end.getY() - this.start.getY();
/* 112:    */   }
/* 113:    */   
/* 114:    */   public float getX()
/* 115:    */   {
/* 116:248 */     return getX1();
/* 117:    */   }
/* 118:    */   
/* 119:    */   public float getY()
/* 120:    */   {
/* 121:255 */     return getY1();
/* 122:    */   }
/* 123:    */   
/* 124:    */   public float getX1()
/* 125:    */   {
/* 126:264 */     return this.start.getX();
/* 127:    */   }
/* 128:    */   
/* 129:    */   public float getY1()
/* 130:    */   {
/* 131:273 */     return this.start.getY();
/* 132:    */   }
/* 133:    */   
/* 134:    */   public float getX2()
/* 135:    */   {
/* 136:282 */     return this.end.getX();
/* 137:    */   }
/* 138:    */   
/* 139:    */   public float getY2()
/* 140:    */   {
/* 141:291 */     return this.end.getY();
/* 142:    */   }
/* 143:    */   
/* 144:    */   public float distance(Vector2f point)
/* 145:    */   {
/* 146:302 */     return (float)Math.sqrt(distanceSquared(point));
/* 147:    */   }
/* 148:    */   
/* 149:    */   public boolean on(Vector2f point)
/* 150:    */   {
/* 151:313 */     getClosestPoint(point, this.closest);
/* 152:    */     
/* 153:315 */     return point.equals(this.closest);
/* 154:    */   }
/* 155:    */   
/* 156:    */   public float distanceSquared(Vector2f point)
/* 157:    */   {
/* 158:326 */     getClosestPoint(point, this.closest);
/* 159:327 */     this.closest.sub(point);
/* 160:    */     
/* 161:329 */     float result = this.closest.lengthSquared();
/* 162:    */     
/* 163:331 */     return result;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public void getClosestPoint(Vector2f point, Vector2f result)
/* 167:    */   {
/* 168:343 */     this.loc.set(point);
/* 169:344 */     this.loc.sub(this.start);
/* 170:    */     
/* 171:346 */     float projDistance = this.vec.dot(this.loc);
/* 172:    */     
/* 173:348 */     projDistance /= this.vec.lengthSquared();
/* 174:350 */     if (projDistance < 0.0F)
/* 175:    */     {
/* 176:351 */       result.set(this.start);
/* 177:352 */       return;
/* 178:    */     }
/* 179:354 */     if (projDistance > 1.0F)
/* 180:    */     {
/* 181:355 */       result.set(this.end);
/* 182:356 */       return;
/* 183:    */     }
/* 184:359 */     result.x = (this.start.getX() + projDistance * this.vec.getX());
/* 185:360 */     result.y = (this.start.getY() + projDistance * this.vec.getY());
/* 186:    */   }
/* 187:    */   
/* 188:    */   public String toString()
/* 189:    */   {
/* 190:367 */     return "[Line " + this.start + "," + this.end + "]";
/* 191:    */   }
/* 192:    */   
/* 193:    */   public Vector2f intersect(Line other)
/* 194:    */   {
/* 195:378 */     return intersect(other, false);
/* 196:    */   }
/* 197:    */   
/* 198:    */   public Vector2f intersect(Line other, boolean limit)
/* 199:    */   {
/* 200:391 */     Vector2f temp = new Vector2f();
/* 201:393 */     if (!intersect(other, limit, temp)) {
/* 202:394 */       return null;
/* 203:    */     }
/* 204:397 */     return temp;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public boolean intersect(Line other, boolean limit, Vector2f result)
/* 208:    */   {
/* 209:412 */     float dx1 = this.end.getX() - this.start.getX();
/* 210:413 */     float dx2 = other.end.getX() - other.start.getX();
/* 211:414 */     float dy1 = this.end.getY() - this.start.getY();
/* 212:415 */     float dy2 = other.end.getY() - other.start.getY();
/* 213:416 */     float denom = dy2 * dx1 - dx2 * dy1;
/* 214:418 */     if (denom == 0.0F) {
/* 215:419 */       return false;
/* 216:    */     }
/* 217:422 */     float ua = dx2 * (this.start.getY() - other.start.getY()) - 
/* 218:423 */       dy2 * (this.start.getX() - other.start.getX());
/* 219:424 */     ua /= denom;
/* 220:425 */     float ub = dx1 * (this.start.getY() - other.start.getY()) - 
/* 221:426 */       dy1 * (this.start.getX() - other.start.getX());
/* 222:427 */     ub /= denom;
/* 223:429 */     if ((limit) && ((ua < 0.0F) || (ua > 1.0F) || (ub < 0.0F) || (ub > 1.0F))) {
/* 224:430 */       return false;
/* 225:    */     }
/* 226:433 */     float u = ua;
/* 227:    */     
/* 228:435 */     float ix = this.start.getX() + u * (this.end.getX() - this.start.getX());
/* 229:436 */     float iy = this.start.getY() + u * (this.end.getY() - this.start.getY());
/* 230:    */     
/* 231:438 */     result.set(ix, iy);
/* 232:439 */     return true;
/* 233:    */   }
/* 234:    */   
/* 235:    */   protected void createPoints()
/* 236:    */   {
/* 237:446 */     this.points = new float[4];
/* 238:447 */     this.points[0] = getX1();
/* 239:448 */     this.points[1] = getY1();
/* 240:449 */     this.points[2] = getX2();
/* 241:450 */     this.points[3] = getY2();
/* 242:    */   }
/* 243:    */   
/* 244:    */   public Shape transform(Transform transform)
/* 245:    */   {
/* 246:457 */     float[] temp = new float[4];
/* 247:458 */     createPoints();
/* 248:459 */     transform.transform(this.points, 0, temp, 0, 2);
/* 249:    */     
/* 250:461 */     return new Line(temp[0], temp[1], temp[2], temp[3]);
/* 251:    */   }
/* 252:    */   
/* 253:    */   public boolean closed()
/* 254:    */   {
/* 255:468 */     return false;
/* 256:    */   }
/* 257:    */   
/* 258:    */   public boolean intersects(Shape shape)
/* 259:    */   {
/* 260:476 */     if ((shape instanceof Circle)) {
/* 261:478 */       return shape.intersects(this);
/* 262:    */     }
/* 263:480 */     return super.intersects(shape);
/* 264:    */   }
/* 265:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.geom.Line
 * JD-Core Version:    0.7.0.1
 */