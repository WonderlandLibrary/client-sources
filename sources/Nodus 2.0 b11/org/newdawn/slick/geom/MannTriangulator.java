/*   1:    */ package org.newdawn.slick.geom;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.lang.reflect.Array;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.List;
/*   7:    */ 
/*   8:    */ public class MannTriangulator
/*   9:    */   implements Triangulator
/*  10:    */ {
/*  11:    */   private static final double EPSILON = 1.E-005D;
/*  12:    */   protected PointBag contour;
/*  13:    */   protected PointBag holes;
/*  14:    */   private PointBag nextFreePointBag;
/*  15:    */   private Point nextFreePoint;
/*  16: 52 */   private List triangles = new ArrayList();
/*  17:    */   
/*  18:    */   public MannTriangulator()
/*  19:    */   {
/*  20: 56 */     this.contour = getPointBag();
/*  21:    */   }
/*  22:    */   
/*  23:    */   public void addPolyPoint(float x, float y)
/*  24:    */   {
/*  25: 63 */     addPoint(new Vector2f(x, y));
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void reset()
/*  29:    */   {
/*  30: 70 */     while (this.holes != null) {
/*  31: 71 */       this.holes = freePointBag(this.holes);
/*  32:    */     }
/*  33: 74 */     this.contour.clear();
/*  34: 75 */     this.holes = null;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public void startHole()
/*  38:    */   {
/*  39: 82 */     PointBag newHole = getPointBag();
/*  40: 83 */     newHole.next = this.holes;
/*  41: 84 */     this.holes = newHole;
/*  42:    */   }
/*  43:    */   
/*  44:    */   private void addPoint(Vector2f pt)
/*  45:    */   {
/*  46: 93 */     if (this.holes == null)
/*  47:    */     {
/*  48: 94 */       Point p = getPoint(pt);
/*  49: 95 */       this.contour.add(p);
/*  50:    */     }
/*  51:    */     else
/*  52:    */     {
/*  53: 97 */       Point p = getPoint(pt);
/*  54: 98 */       this.holes.add(p);
/*  55:    */     }
/*  56:    */   }
/*  57:    */   
/*  58:    */   private Vector2f[] triangulate(Vector2f[] result)
/*  59:    */   {
/*  60:110 */     this.contour.computeAngles();
/*  61:111 */     for (PointBag hole = this.holes; hole != null; hole = hole.next) {
/*  62:112 */       hole.computeAngles();
/*  63:    */     }
/*  64:    */     label243:
/*  65:116 */     while (this.holes != null)
/*  66:    */     {
/*  67:117 */       Point pHole = this.holes.first;
/*  68:    */       do
/*  69:    */       {
/*  70:119 */         if (pHole.angle <= 0.0D)
/*  71:    */         {
/*  72:120 */           Point pContour = this.contour.first;
/*  73:    */           do
/*  74:    */           {
/*  75:122 */             if ((pHole.isInfront(pContour)) && 
/*  76:123 */               (pContour.isInfront(pHole))) {
/*  77:125 */               if (!this.contour.doesIntersectSegment(pHole.pt, pContour.pt))
/*  78:    */               {
/*  79:126 */                 PointBag hole = this.holes;
/*  80:129 */                 while (!hole.doesIntersectSegment(pHole.pt, pContour.pt)) {
/*  81:132 */                   if ((hole = hole.next) == null)
/*  82:    */                   {
/*  83:134 */                     Point newPtContour = getPoint(pContour.pt);
/*  84:135 */                     pContour.insertAfter(newPtContour);
/*  85:    */                     
/*  86:137 */                     Point newPtHole = getPoint(pHole.pt);
/*  87:138 */                     pHole.insertBefore(newPtHole);
/*  88:    */                     
/*  89:140 */                     pContour.next = pHole;
/*  90:141 */                     pHole.prev = pContour;
/*  91:    */                     
/*  92:143 */                     newPtHole.next = newPtContour;
/*  93:144 */                     newPtContour.prev = newPtHole;
/*  94:    */                     
/*  95:146 */                     pContour.computeAngle();
/*  96:147 */                     pHole.computeAngle();
/*  97:148 */                     newPtContour.computeAngle();
/*  98:149 */                     newPtHole.computeAngle();
/*  99:    */                     
/* 100:    */ 
/* 101:152 */                     this.holes.first = null;
/* 102:    */                     break label243;
/* 103:    */                   }
/* 104:    */                 }
/* 105:    */               }
/* 106:    */             }
/* 107:156 */           } while ((pContour = pContour.next) != this.contour.first);
/* 108:    */         }
/* 109:158 */       } while ((pHole = pHole.next) != this.holes.first);
/* 110:161 */       this.holes = freePointBag(this.holes);
/* 111:    */     }
/* 112:165 */     int numTriangles = this.contour.countPoints() - 2;
/* 113:166 */     int neededSpace = numTriangles * 3 + 1;
/* 114:167 */     if (result.length < neededSpace) {
/* 115:168 */       result = (Vector2f[])Array.newInstance(result.getClass()
/* 116:169 */         .getComponentType(), neededSpace);
/* 117:    */     }
/* 118:173 */     int idx = 0;
/* 119:    */     for (;;)
/* 120:    */     {
/* 121:175 */       Point pContour = this.contour.first;
/* 122:177 */       if (pContour == null) {
/* 123:    */         break;
/* 124:    */       }
/* 125:181 */       if (pContour.next == pContour.prev) {
/* 126:    */         break;
/* 127:    */       }
/* 128:    */       do
/* 129:    */       {
/* 130:186 */         if (pContour.angle > 0.0D)
/* 131:    */         {
/* 132:187 */           Point prev = pContour.prev;
/* 133:188 */           Point next = pContour.next;
/* 134:190 */           if (((next.next == prev) || ((prev.isInfront(next)) && (next.isInfront(prev)))) && 
/* 135:191 */             (!this.contour.doesIntersectSegment(prev.pt, next.pt)))
/* 136:    */           {
/* 137:192 */             result[(idx++)] = pContour.pt;
/* 138:193 */             result[(idx++)] = next.pt;
/* 139:194 */             result[(idx++)] = prev.pt;
/* 140:195 */             break;
/* 141:    */           }
/* 142:    */         }
/* 143:199 */       } while ((pContour = pContour.next) != this.contour.first);
/* 144:202 */       Point prev = pContour.prev;
/* 145:203 */       Point next = pContour.next;
/* 146:    */       
/* 147:205 */       this.contour.first = prev;
/* 148:206 */       pContour.unlink();
/* 149:207 */       freePoint(pContour);
/* 150:    */       
/* 151:209 */       next.computeAngle();
/* 152:210 */       prev.computeAngle();
/* 153:    */     }
/* 154:214 */     result[idx] = null;
/* 155:    */     
/* 156:    */ 
/* 157:217 */     this.contour.clear();
/* 158:    */     
/* 159:    */ 
/* 160:220 */     return result;
/* 161:    */   }
/* 162:    */   
/* 163:    */   private PointBag getPointBag()
/* 164:    */   {
/* 165:229 */     PointBag pb = this.nextFreePointBag;
/* 166:230 */     if (pb != null)
/* 167:    */     {
/* 168:231 */       this.nextFreePointBag = pb.next;
/* 169:232 */       pb.next = null;
/* 170:233 */       return pb;
/* 171:    */     }
/* 172:235 */     return new PointBag();
/* 173:    */   }
/* 174:    */   
/* 175:    */   private PointBag freePointBag(PointBag pb)
/* 176:    */   {
/* 177:245 */     PointBag next = pb.next;
/* 178:246 */     pb.clear();
/* 179:247 */     pb.next = this.nextFreePointBag;
/* 180:248 */     this.nextFreePointBag = pb;
/* 181:249 */     return next;
/* 182:    */   }
/* 183:    */   
/* 184:    */   private Point getPoint(Vector2f pt)
/* 185:    */   {
/* 186:259 */     Point p = this.nextFreePoint;
/* 187:260 */     if (p != null)
/* 188:    */     {
/* 189:261 */       this.nextFreePoint = p.next;
/* 190:    */       
/* 191:263 */       p.next = null;
/* 192:264 */       p.prev = null;
/* 193:265 */       p.pt = pt;
/* 194:266 */       return p;
/* 195:    */     }
/* 196:268 */     return new Point(pt);
/* 197:    */   }
/* 198:    */   
/* 199:    */   private void freePoint(Point p)
/* 200:    */   {
/* 201:277 */     p.next = this.nextFreePoint;
/* 202:278 */     this.nextFreePoint = p;
/* 203:    */   }
/* 204:    */   
/* 205:    */   private void freePoints(Point head)
/* 206:    */   {
/* 207:287 */     head.prev.next = this.nextFreePoint;
/* 208:288 */     head.prev = null;
/* 209:289 */     this.nextFreePoint = head;
/* 210:    */   }
/* 211:    */   
/* 212:    */   private static class Point
/* 213:    */     implements Serializable
/* 214:    */   {
/* 215:    */     protected Vector2f pt;
/* 216:    */     protected Point prev;
/* 217:    */     protected Point next;
/* 218:    */     protected double nx;
/* 219:    */     protected double ny;
/* 220:    */     protected double angle;
/* 221:    */     protected double dist;
/* 222:    */     
/* 223:    */     public Point(Vector2f pt)
/* 224:    */     {
/* 225:319 */       this.pt = pt;
/* 226:    */     }
/* 227:    */     
/* 228:    */     public void unlink()
/* 229:    */     {
/* 230:326 */       this.prev.next = this.next;
/* 231:327 */       this.next.prev = this.prev;
/* 232:328 */       this.next = null;
/* 233:329 */       this.prev = null;
/* 234:    */     }
/* 235:    */     
/* 236:    */     public void insertBefore(Point p)
/* 237:    */     {
/* 238:338 */       this.prev.next = p;
/* 239:339 */       p.prev = this.prev;
/* 240:340 */       p.next = this;
/* 241:341 */       this.prev = p;
/* 242:    */     }
/* 243:    */     
/* 244:    */     public void insertAfter(Point p)
/* 245:    */     {
/* 246:350 */       this.next.prev = p;
/* 247:351 */       p.prev = this;
/* 248:352 */       p.next = this.next;
/* 249:353 */       this.next = p;
/* 250:    */     }
/* 251:    */     
/* 252:    */     private double hypot(double x, double y)
/* 253:    */     {
/* 254:364 */       return Math.sqrt(x * x + y * y);
/* 255:    */     }
/* 256:    */     
/* 257:    */     public void computeAngle()
/* 258:    */     {
/* 259:371 */       if (this.prev.pt.equals(this.pt)) {
/* 260:372 */         this.pt.x += 0.01F;
/* 261:    */       }
/* 262:374 */       double dx1 = this.pt.x - this.prev.pt.x;
/* 263:375 */       double dy1 = this.pt.y - this.prev.pt.y;
/* 264:376 */       double len1 = hypot(dx1, dy1);
/* 265:377 */       dx1 /= len1;
/* 266:378 */       dy1 /= len1;
/* 267:380 */       if (this.next.pt.equals(this.pt)) {
/* 268:381 */         this.pt.y += 0.01F;
/* 269:    */       }
/* 270:383 */       double dx2 = this.next.pt.x - this.pt.x;
/* 271:384 */       double dy2 = this.next.pt.y - this.pt.y;
/* 272:385 */       double len2 = hypot(dx2, dy2);
/* 273:386 */       dx2 /= len2;
/* 274:387 */       dy2 /= len2;
/* 275:    */       
/* 276:389 */       double nx1 = -dy1;
/* 277:390 */       double ny1 = dx1;
/* 278:    */       
/* 279:392 */       this.nx = ((nx1 - dy2) * 0.5D);
/* 280:393 */       this.ny = ((ny1 + dx2) * 0.5D);
/* 281:395 */       if (this.nx * this.nx + this.ny * this.ny < 1.E-005D)
/* 282:    */       {
/* 283:396 */         this.nx = dx1;
/* 284:397 */         this.ny = dy2;
/* 285:398 */         this.angle = 1.0D;
/* 286:399 */         if (dx1 * dx2 + dy1 * dy2 > 0.0D)
/* 287:    */         {
/* 288:400 */           this.nx = (-dx1);
/* 289:401 */           this.ny = (-dy1);
/* 290:    */         }
/* 291:    */       }
/* 292:    */       else
/* 293:    */       {
/* 294:404 */         this.angle = (this.nx * dx2 + this.ny * dy2);
/* 295:    */       }
/* 296:    */     }
/* 297:    */     
/* 298:    */     public double getAngle(Point p)
/* 299:    */     {
/* 300:415 */       double dx = p.pt.x - this.pt.x;
/* 301:416 */       double dy = p.pt.y - this.pt.y;
/* 302:417 */       double dlen = hypot(dx, dy);
/* 303:    */       
/* 304:419 */       return (this.nx * dx + this.ny * dy) / dlen;
/* 305:    */     }
/* 306:    */     
/* 307:    */     public boolean isConcave()
/* 308:    */     {
/* 309:428 */       return this.angle < 0.0D;
/* 310:    */     }
/* 311:    */     
/* 312:    */     public boolean isInfront(double dx, double dy)
/* 313:    */     {
/* 314:441 */       boolean sidePrev = (this.prev.pt.y - this.pt.y) * dx + (this.pt.x - this.prev.pt.x) * 
/* 315:442 */         dy >= 0.0D;
/* 316:443 */       boolean sideNext = (this.pt.y - this.next.pt.y) * dx + (this.next.pt.x - this.pt.x) * 
/* 317:444 */         dy >= 0.0D;
/* 318:    */       
/* 319:446 */       return this.angle < 0.0D ? sidePrev | sideNext : sidePrev & sideNext;
/* 320:    */     }
/* 321:    */     
/* 322:    */     public boolean isInfront(Point p)
/* 323:    */     {
/* 324:456 */       return isInfront(p.pt.x - this.pt.x, p.pt.y - this.pt.y);
/* 325:    */     }
/* 326:    */   }
/* 327:    */   
/* 328:    */   protected class PointBag
/* 329:    */     implements Serializable
/* 330:    */   {
/* 331:    */     protected MannTriangulator.Point first;
/* 332:    */     protected PointBag next;
/* 333:    */     
/* 334:    */     protected PointBag() {}
/* 335:    */     
/* 336:    */     public void clear()
/* 337:    */     {
/* 338:475 */       if (this.first != null)
/* 339:    */       {
/* 340:476 */         MannTriangulator.this.freePoints(this.first);
/* 341:477 */         this.first = null;
/* 342:    */       }
/* 343:    */     }
/* 344:    */     
/* 345:    */     public void add(MannTriangulator.Point p)
/* 346:    */     {
/* 347:487 */       if (this.first != null)
/* 348:    */       {
/* 349:488 */         this.first.insertBefore(p);
/* 350:    */       }
/* 351:    */       else
/* 352:    */       {
/* 353:490 */         this.first = p;
/* 354:491 */         p.next = p;
/* 355:492 */         p.prev = p;
/* 356:    */       }
/* 357:    */     }
/* 358:    */     
/* 359:    */     public void computeAngles()
/* 360:    */     {
/* 361:500 */       if (this.first == null) {
/* 362:501 */         return;
/* 363:    */       }
/* 364:504 */       MannTriangulator.Point p = this.first;
/* 365:    */       do
/* 366:    */       {
/* 367:506 */         p.computeAngle();
/* 368:507 */       } while ((p = p.next) != this.first);
/* 369:    */     }
/* 370:    */     
/* 371:    */     public boolean doesIntersectSegment(Vector2f v1, Vector2f v2)
/* 372:    */     {
/* 373:519 */       double dxA = v2.x - v1.x;
/* 374:520 */       double dyA = v2.y - v1.y;
/* 375:    */       
/* 376:522 */       MannTriangulator.Point p = this.first;
/* 377:    */       for (;;)
/* 378:    */       {
/* 379:523 */         MannTriangulator.Point n = p.next;
/* 380:524 */         if ((p.pt != v1) && (n.pt != v1) && (p.pt != v2) && (n.pt != v2))
/* 381:    */         {
/* 382:525 */           double dxB = n.pt.x - p.pt.x;
/* 383:526 */           double dyB = n.pt.y - p.pt.y;
/* 384:527 */           double d = dxA * dyB - dyA * dxB;
/* 385:529 */           if (Math.abs(d) > 1.E-005D)
/* 386:    */           {
/* 387:530 */             double tmp1 = p.pt.x - v1.x;
/* 388:531 */             double tmp2 = p.pt.y - v1.y;
/* 389:532 */             double tA = (dyB * tmp1 - dxB * tmp2) / d;
/* 390:533 */             double tB = (dyA * tmp1 - dxA * tmp2) / d;
/* 391:535 */             if ((tA >= 0.0D) && (tA <= 1.0D) && (tB >= 0.0D) && (tB <= 1.0D)) {
/* 392:536 */               return true;
/* 393:    */             }
/* 394:    */           }
/* 395:    */         }
/* 396:541 */         if (n == this.first) {
/* 397:542 */           return false;
/* 398:    */         }
/* 399:544 */         p = n;
/* 400:    */       }
/* 401:    */     }
/* 402:    */     
/* 403:    */     public int countPoints()
/* 404:    */     {
/* 405:554 */       if (this.first == null) {
/* 406:555 */         return 0;
/* 407:    */       }
/* 408:558 */       int count = 0;
/* 409:559 */       MannTriangulator.Point p = this.first;
/* 410:    */       do
/* 411:    */       {
/* 412:561 */         count++;
/* 413:562 */       } while ((p = p.next) != this.first);
/* 414:563 */       return count;
/* 415:    */     }
/* 416:    */     
/* 417:    */     public boolean contains(Vector2f point)
/* 418:    */     {
/* 419:573 */       if (this.first == null) {
/* 420:574 */         return false;
/* 421:    */       }
/* 422:577 */       if (this.first.prev.pt.equals(point)) {
/* 423:578 */         return true;
/* 424:    */       }
/* 425:580 */       if (this.first.pt.equals(point)) {
/* 426:581 */         return true;
/* 427:    */       }
/* 428:583 */       return false;
/* 429:    */     }
/* 430:    */   }
/* 431:    */   
/* 432:    */   public boolean triangulate()
/* 433:    */   {
/* 434:588 */     Vector2f[] temp = triangulate(new Vector2f[0]);
/* 435:590 */     for (int i = 0; i < temp.length; i++)
/* 436:    */     {
/* 437:591 */       if (temp[i] == null) {
/* 438:    */         break;
/* 439:    */       }
/* 440:594 */       this.triangles.add(temp[i]);
/* 441:    */     }
/* 442:598 */     return true;
/* 443:    */   }
/* 444:    */   
/* 445:    */   public int getTriangleCount()
/* 446:    */   {
/* 447:605 */     return this.triangles.size() / 3;
/* 448:    */   }
/* 449:    */   
/* 450:    */   public float[] getTrianglePoint(int tri, int i)
/* 451:    */   {
/* 452:612 */     Vector2f pt = (Vector2f)this.triangles.get(tri * 3 + i);
/* 453:    */     
/* 454:614 */     return new float[] { pt.x, pt.y };
/* 455:    */   }
/* 456:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.geom.MannTriangulator
 * JD-Core Version:    0.7.0.1
 */