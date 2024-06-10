/*   1:    */ package org.newdawn.slick.geom;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ 
/*   5:    */ public class GeomUtil
/*   6:    */ {
/*   7: 12 */   public float EPSILON = 1.0E-004F;
/*   8: 14 */   public float EDGE_SCALE = 1.0F;
/*   9: 16 */   public int MAX_POINTS = 10000;
/*  10:    */   public GeomUtilListener listener;
/*  11:    */   
/*  12:    */   public Shape[] subtract(Shape target, Shape missing)
/*  13:    */   {
/*  14: 29 */     target = target.transform(new Transform());
/*  15: 30 */     missing = missing.transform(new Transform());
/*  16:    */     
/*  17: 32 */     int count = 0;
/*  18: 33 */     for (int i = 0; i < target.getPointCount(); i++) {
/*  19: 34 */       if (missing.contains(target.getPoint(i)[0], target.getPoint(i)[1])) {
/*  20: 35 */         count++;
/*  21:    */       }
/*  22:    */     }
/*  23: 39 */     if (count == target.getPointCount()) {
/*  24: 40 */       return new Shape[0];
/*  25:    */     }
/*  26: 43 */     if (!target.intersects(missing)) {
/*  27: 44 */       return new Shape[] { target };
/*  28:    */     }
/*  29: 47 */     int found = 0;
/*  30: 48 */     for (int i = 0; i < missing.getPointCount(); i++) {
/*  31: 49 */       if ((target.contains(missing.getPoint(i)[0], missing.getPoint(i)[1])) && 
/*  32: 50 */         (!onPath(target, missing.getPoint(i)[0], missing.getPoint(i)[1]))) {
/*  33: 51 */         found++;
/*  34:    */       }
/*  35:    */     }
/*  36: 55 */     for (int i = 0; i < target.getPointCount(); i++) {
/*  37: 56 */       if ((missing.contains(target.getPoint(i)[0], target.getPoint(i)[1])) && 
/*  38: 57 */         (!onPath(missing, target.getPoint(i)[0], target.getPoint(i)[1]))) {
/*  39: 59 */         found++;
/*  40:    */       }
/*  41:    */     }
/*  42: 64 */     if (found < 1) {
/*  43: 65 */       return new Shape[] { target };
/*  44:    */     }
/*  45: 68 */     return combine(target, missing, true);
/*  46:    */   }
/*  47:    */   
/*  48:    */   private boolean onPath(Shape path, float x, float y)
/*  49:    */   {
/*  50: 80 */     for (int i = 0; i < path.getPointCount() + 1; i++)
/*  51:    */     {
/*  52: 81 */       int n = rationalPoint(path, i + 1);
/*  53: 82 */       Line line = getLine(path, rationalPoint(path, i), n);
/*  54: 83 */       if (line.distance(new Vector2f(x, y)) < this.EPSILON * 100.0F) {
/*  55: 84 */         return true;
/*  56:    */       }
/*  57:    */     }
/*  58: 88 */     return false;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void setListener(GeomUtilListener listener)
/*  62:    */   {
/*  63: 97 */     this.listener = listener;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public Shape[] union(Shape target, Shape other)
/*  67:    */   {
/*  68:109 */     target = target.transform(new Transform());
/*  69:110 */     other = other.transform(new Transform());
/*  70:112 */     if (!target.intersects(other)) {
/*  71:113 */       return new Shape[] { target, other };
/*  72:    */     }
/*  73:118 */     boolean touches = false;
/*  74:119 */     int buttCount = 0;
/*  75:120 */     for (int i = 0; i < target.getPointCount(); i++)
/*  76:    */     {
/*  77:121 */       if ((other.contains(target.getPoint(i)[0], target.getPoint(i)[1])) && 
/*  78:122 */         (!other.hasVertex(target.getPoint(i)[0], target.getPoint(i)[1])))
/*  79:    */       {
/*  80:123 */         touches = true;
/*  81:124 */         break;
/*  82:    */       }
/*  83:127 */       if (other.hasVertex(target.getPoint(i)[0], target.getPoint(i)[1])) {
/*  84:128 */         buttCount++;
/*  85:    */       }
/*  86:    */     }
/*  87:131 */     for (int i = 0; i < other.getPointCount(); i++) {
/*  88:132 */       if ((target.contains(other.getPoint(i)[0], other.getPoint(i)[1])) && 
/*  89:133 */         (!target.hasVertex(other.getPoint(i)[0], other.getPoint(i)[1])))
/*  90:    */       {
/*  91:134 */         touches = true;
/*  92:135 */         break;
/*  93:    */       }
/*  94:    */     }
/*  95:140 */     if ((!touches) && (buttCount < 2)) {
/*  96:141 */       return new Shape[] { target, other };
/*  97:    */     }
/*  98:145 */     return combine(target, other, false);
/*  99:    */   }
/* 100:    */   
/* 101:    */   private Shape[] combine(Shape target, Shape other, boolean subtract)
/* 102:    */   {
/* 103:157 */     if (subtract)
/* 104:    */     {
/* 105:158 */       ArrayList shapes = new ArrayList();
/* 106:159 */       ArrayList used = new ArrayList();
/* 107:163 */       for (int i = 0; i < target.getPointCount(); i++)
/* 108:    */       {
/* 109:164 */         float[] point = target.getPoint(i);
/* 110:165 */         if (other.contains(point[0], point[1]))
/* 111:    */         {
/* 112:166 */           used.add(new Vector2f(point[0], point[1]));
/* 113:167 */           if (this.listener != null) {
/* 114:168 */             this.listener.pointExcluded(point[0], point[1]);
/* 115:    */           }
/* 116:    */         }
/* 117:    */       }
/* 118:173 */       for (int i = 0; i < target.getPointCount(); i++)
/* 119:    */       {
/* 120:174 */         float[] point = target.getPoint(i);
/* 121:175 */         Vector2f pt = new Vector2f(point[0], point[1]);
/* 122:177 */         if (!used.contains(pt))
/* 123:    */         {
/* 124:178 */           Shape result = combineSingle(target, other, true, i);
/* 125:179 */           shapes.add(result);
/* 126:180 */           for (int j = 0; j < result.getPointCount(); j++)
/* 127:    */           {
/* 128:181 */             float[] kpoint = result.getPoint(j);
/* 129:182 */             Vector2f kpt = new Vector2f(kpoint[0], kpoint[1]);
/* 130:183 */             used.add(kpt);
/* 131:    */           }
/* 132:    */         }
/* 133:    */       }
/* 134:188 */       return (Shape[])shapes.toArray(new Shape[0]);
/* 135:    */     }
/* 136:190 */     for (int i = 0; i < target.getPointCount(); i++) {
/* 137:191 */       if ((!other.contains(target.getPoint(i)[0], target.getPoint(i)[1])) && 
/* 138:192 */         (!other.hasVertex(target.getPoint(i)[0], target.getPoint(i)[1])))
/* 139:    */       {
/* 140:193 */         Shape shape = combineSingle(target, other, false, i);
/* 141:194 */         return new Shape[] { shape };
/* 142:    */       }
/* 143:    */     }
/* 144:199 */     return new Shape[] { other };
/* 145:    */   }
/* 146:    */   
/* 147:    */   private Shape combineSingle(Shape target, Shape missing, boolean subtract, int start)
/* 148:    */   {
/* 149:213 */     Shape current = target;
/* 150:214 */     Shape other = missing;
/* 151:215 */     int point = start;
/* 152:216 */     int dir = 1;
/* 153:    */     
/* 154:218 */     Polygon poly = new Polygon();
/* 155:219 */     boolean first = true;
/* 156:    */     
/* 157:221 */     int loop = 0;
/* 158:    */     
/* 159:    */ 
/* 160:224 */     float px = current.getPoint(point)[0];
/* 161:225 */     float py = current.getPoint(point)[1];
/* 162:227 */     while ((!poly.hasVertex(px, py)) || (first) || (current != target))
/* 163:    */     {
/* 164:228 */       first = false;
/* 165:229 */       loop++;
/* 166:230 */       if (loop > this.MAX_POINTS) {
/* 167:    */         break;
/* 168:    */       }
/* 169:235 */       poly.addPoint(px, py);
/* 170:236 */       if (this.listener != null) {
/* 171:237 */         this.listener.pointUsed(px, py);
/* 172:    */       }
/* 173:243 */       Line line = getLine(current, px, py, rationalPoint(current, point + dir));
/* 174:244 */       HitResult hit = intersect(other, line);
/* 175:246 */       if (hit != null)
/* 176:    */       {
/* 177:247 */         Line hitLine = hit.line;
/* 178:248 */         Vector2f pt = hit.pt;
/* 179:249 */         px = pt.x;
/* 180:250 */         py = pt.y;
/* 181:252 */         if (this.listener != null) {
/* 182:253 */           this.listener.pointIntersected(px, py);
/* 183:    */         }
/* 184:256 */         if (other.hasVertex(px, py))
/* 185:    */         {
/* 186:257 */           point = other.indexOf(pt.x, pt.y);
/* 187:258 */           dir = 1;
/* 188:259 */           px = pt.x;
/* 189:260 */           py = pt.y;
/* 190:    */           
/* 191:262 */           Shape temp = current;
/* 192:263 */           current = other;
/* 193:264 */           other = temp;
/* 194:    */         }
/* 195:    */         else
/* 196:    */         {
/* 197:268 */           float dx = hitLine.getDX() / hitLine.length();
/* 198:269 */           float dy = hitLine.getDY() / hitLine.length();
/* 199:270 */           dx *= this.EDGE_SCALE;
/* 200:271 */           dy *= this.EDGE_SCALE;
/* 201:273 */           if (current.contains(pt.x + dx, pt.y + dy))
/* 202:    */           {
/* 203:276 */             if (subtract)
/* 204:    */             {
/* 205:277 */               if (current == missing)
/* 206:    */               {
/* 207:278 */                 point = hit.p2;
/* 208:279 */                 dir = -1;
/* 209:    */               }
/* 210:    */               else
/* 211:    */               {
/* 212:281 */                 point = hit.p1;
/* 213:282 */                 dir = 1;
/* 214:    */               }
/* 215:    */             }
/* 216:285 */             else if (current == target)
/* 217:    */             {
/* 218:286 */               point = hit.p2;
/* 219:287 */               dir = -1;
/* 220:    */             }
/* 221:    */             else
/* 222:    */             {
/* 223:289 */               point = hit.p2;
/* 224:290 */               dir = -1;
/* 225:    */             }
/* 226:295 */             Shape temp = current;
/* 227:296 */             current = other;
/* 228:297 */             other = temp;
/* 229:    */           }
/* 230:298 */           else if (current.contains(pt.x - dx, pt.y - dy))
/* 231:    */           {
/* 232:299 */             if (subtract)
/* 233:    */             {
/* 234:300 */               if (current == target)
/* 235:    */               {
/* 236:301 */                 point = hit.p2;
/* 237:302 */                 dir = -1;
/* 238:    */               }
/* 239:    */               else
/* 240:    */               {
/* 241:304 */                 point = hit.p1;
/* 242:305 */                 dir = 1;
/* 243:    */               }
/* 244:    */             }
/* 245:308 */             else if (current == missing)
/* 246:    */             {
/* 247:309 */               point = hit.p1;
/* 248:310 */               dir = 1;
/* 249:    */             }
/* 250:    */             else
/* 251:    */             {
/* 252:312 */               point = hit.p1;
/* 253:313 */               dir = 1;
/* 254:    */             }
/* 255:318 */             Shape temp = current;
/* 256:319 */             current = other;
/* 257:320 */             other = temp;
/* 258:    */           }
/* 259:    */           else
/* 260:    */           {
/* 261:323 */             if (subtract) {
/* 262:    */               break;
/* 263:    */             }
/* 264:326 */             point = hit.p1;
/* 265:327 */             dir = 1;
/* 266:328 */             Shape temp = current;
/* 267:329 */             current = other;
/* 268:330 */             other = temp;
/* 269:    */             
/* 270:332 */             point = rationalPoint(current, point + dir);
/* 271:333 */             px = current.getPoint(point)[0];
/* 272:334 */             py = current.getPoint(point)[1];
/* 273:    */           }
/* 274:    */         }
/* 275:    */       }
/* 276:    */       else
/* 277:    */       {
/* 278:339 */         point = rationalPoint(current, point + dir);
/* 279:340 */         px = current.getPoint(point)[0];
/* 280:341 */         py = current.getPoint(point)[1];
/* 281:    */       }
/* 282:    */     }
/* 283:345 */     poly.addPoint(px, py);
/* 284:346 */     if (this.listener != null) {
/* 285:347 */       this.listener.pointUsed(px, py);
/* 286:    */     }
/* 287:350 */     return poly;
/* 288:    */   }
/* 289:    */   
/* 290:    */   public HitResult intersect(Shape shape, Line line)
/* 291:    */   {
/* 292:361 */     float distance = 3.4028235E+38F;
/* 293:362 */     HitResult hit = null;
/* 294:364 */     for (int i = 0; i < shape.getPointCount(); i++)
/* 295:    */     {
/* 296:365 */       int next = rationalPoint(shape, i + 1);
/* 297:366 */       Line local = getLine(shape, i, next);
/* 298:    */       
/* 299:368 */       Vector2f pt = line.intersect(local, true);
/* 300:369 */       if (pt != null)
/* 301:    */       {
/* 302:370 */         float newDis = pt.distance(line.getStart());
/* 303:371 */         if ((newDis < distance) && (newDis > this.EPSILON))
/* 304:    */         {
/* 305:372 */           hit = new HitResult();
/* 306:373 */           hit.pt = pt;
/* 307:374 */           hit.line = local;
/* 308:375 */           hit.p1 = i;
/* 309:376 */           hit.p2 = next;
/* 310:377 */           distance = newDis;
/* 311:    */         }
/* 312:    */       }
/* 313:    */     }
/* 314:382 */     return hit;
/* 315:    */   }
/* 316:    */   
/* 317:    */   public static int rationalPoint(Shape shape, int p)
/* 318:    */   {
/* 319:393 */     while (p < 0) {
/* 320:394 */       p += shape.getPointCount();
/* 321:    */     }
/* 322:396 */     while (p >= shape.getPointCount()) {
/* 323:397 */       p -= shape.getPointCount();
/* 324:    */     }
/* 325:400 */     return p;
/* 326:    */   }
/* 327:    */   
/* 328:    */   public Line getLine(Shape shape, int s, int e)
/* 329:    */   {
/* 330:412 */     float[] start = shape.getPoint(s);
/* 331:413 */     float[] end = shape.getPoint(e);
/* 332:    */     
/* 333:415 */     Line line = new Line(start[0], start[1], end[0], end[1]);
/* 334:416 */     return line;
/* 335:    */   }
/* 336:    */   
/* 337:    */   public Line getLine(Shape shape, float sx, float sy, int e)
/* 338:    */   {
/* 339:429 */     float[] end = shape.getPoint(e);
/* 340:    */     
/* 341:431 */     Line line = new Line(sx, sy, end[0], end[1]);
/* 342:432 */     return line;
/* 343:    */   }
/* 344:    */   
/* 345:    */   public class HitResult
/* 346:    */   {
/* 347:    */     public Line line;
/* 348:    */     public int p1;
/* 349:    */     public int p2;
/* 350:    */     public Vector2f pt;
/* 351:    */     
/* 352:    */     public HitResult() {}
/* 353:    */   }
/* 354:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.geom.GeomUtil
 * JD-Core Version:    0.7.0.1
 */