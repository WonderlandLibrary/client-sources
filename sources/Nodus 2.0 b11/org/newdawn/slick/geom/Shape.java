/*   1:    */ package org.newdawn.slick.geom;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ 
/*   5:    */ public abstract class Shape
/*   6:    */   implements Serializable
/*   7:    */ {
/*   8:    */   protected float[] points;
/*   9:    */   protected float[] center;
/*  10:    */   protected float x;
/*  11:    */   protected float y;
/*  12:    */   protected float maxX;
/*  13:    */   protected float maxY;
/*  14:    */   protected float minX;
/*  15:    */   protected float minY;
/*  16:    */   protected float boundingCircleRadius;
/*  17:    */   protected boolean pointsDirty;
/*  18:    */   protected transient Triangulator tris;
/*  19:    */   protected boolean trianglesDirty;
/*  20:    */   
/*  21:    */   public Shape()
/*  22:    */   {
/*  23: 42 */     this.pointsDirty = true;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public void setLocation(float x, float y)
/*  27:    */   {
/*  28: 52 */     setX(x);
/*  29: 53 */     setY(y);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public abstract Shape transform(Transform paramTransform);
/*  33:    */   
/*  34:    */   protected abstract void createPoints();
/*  35:    */   
/*  36:    */   public float getX()
/*  37:    */   {
/*  38: 77 */     return this.x;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void setX(float x)
/*  42:    */   {
/*  43: 86 */     if (x != this.x)
/*  44:    */     {
/*  45: 87 */       float dx = x - this.x;
/*  46: 88 */       this.x = x;
/*  47: 90 */       if ((this.points == null) || (this.center == null)) {
/*  48: 91 */         checkPoints();
/*  49:    */       }
/*  50: 94 */       for (int i = 0; i < this.points.length / 2; i++) {
/*  51: 95 */         this.points[(i * 2)] += dx;
/*  52:    */       }
/*  53: 97 */       this.center[0] += dx;
/*  54: 98 */       x += dx;
/*  55: 99 */       this.maxX += dx;
/*  56:100 */       this.minX += dx;
/*  57:101 */       this.trianglesDirty = true;
/*  58:    */     }
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void setY(float y)
/*  62:    */   {
/*  63:111 */     if (y != this.y)
/*  64:    */     {
/*  65:112 */       float dy = y - this.y;
/*  66:113 */       this.y = y;
/*  67:115 */       if ((this.points == null) || (this.center == null)) {
/*  68:116 */         checkPoints();
/*  69:    */       }
/*  70:119 */       for (int i = 0; i < this.points.length / 2; i++) {
/*  71:120 */         this.points[(i * 2 + 1)] += dy;
/*  72:    */       }
/*  73:122 */       this.center[1] += dy;
/*  74:123 */       y += dy;
/*  75:124 */       this.maxY += dy;
/*  76:125 */       this.minY += dy;
/*  77:126 */       this.trianglesDirty = true;
/*  78:    */     }
/*  79:    */   }
/*  80:    */   
/*  81:    */   public float getY()
/*  82:    */   {
/*  83:136 */     return this.y;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public Vector2f getLocation()
/*  87:    */   {
/*  88:145 */     return new Vector2f(getX(), getY());
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void setLocation(Vector2f loc)
/*  92:    */   {
/*  93:154 */     setX(loc.x);
/*  94:155 */     setY(loc.y);
/*  95:    */   }
/*  96:    */   
/*  97:    */   public float getCenterX()
/*  98:    */   {
/*  99:164 */     checkPoints();
/* 100:    */     
/* 101:166 */     return this.center[0];
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void setCenterX(float centerX)
/* 105:    */   {
/* 106:175 */     if ((this.points == null) || (this.center == null)) {
/* 107:176 */       checkPoints();
/* 108:    */     }
/* 109:179 */     float xDiff = centerX - getCenterX();
/* 110:180 */     setX(this.x + xDiff);
/* 111:    */   }
/* 112:    */   
/* 113:    */   public float getCenterY()
/* 114:    */   {
/* 115:189 */     checkPoints();
/* 116:    */     
/* 117:191 */     return this.center[1];
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void setCenterY(float centerY)
/* 121:    */   {
/* 122:200 */     if ((this.points == null) || (this.center == null)) {
/* 123:201 */       checkPoints();
/* 124:    */     }
/* 125:204 */     float yDiff = centerY - getCenterY();
/* 126:205 */     setY(this.y + yDiff);
/* 127:    */   }
/* 128:    */   
/* 129:    */   public float getMaxX()
/* 130:    */   {
/* 131:214 */     checkPoints();
/* 132:215 */     return this.maxX;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public float getMaxY()
/* 136:    */   {
/* 137:223 */     checkPoints();
/* 138:224 */     return this.maxY;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public float getMinX()
/* 142:    */   {
/* 143:233 */     checkPoints();
/* 144:234 */     return this.minX;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public float getMinY()
/* 148:    */   {
/* 149:243 */     checkPoints();
/* 150:244 */     return this.minY;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public float getBoundingCircleRadius()
/* 154:    */   {
/* 155:253 */     checkPoints();
/* 156:254 */     return this.boundingCircleRadius;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public float[] getCenter()
/* 160:    */   {
/* 161:263 */     checkPoints();
/* 162:264 */     return this.center;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public float[] getPoints()
/* 166:    */   {
/* 167:273 */     checkPoints();
/* 168:274 */     return this.points;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public int getPointCount()
/* 172:    */   {
/* 173:283 */     checkPoints();
/* 174:284 */     return this.points.length / 2;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public float[] getPoint(int index)
/* 178:    */   {
/* 179:294 */     checkPoints();
/* 180:    */     
/* 181:296 */     float[] result = new float[2];
/* 182:    */     
/* 183:298 */     result[0] = this.points[(index * 2)];
/* 184:299 */     result[1] = this.points[(index * 2 + 1)];
/* 185:    */     
/* 186:301 */     return result;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public float[] getNormal(int index)
/* 190:    */   {
/* 191:311 */     float[] current = getPoint(index);
/* 192:312 */     float[] prev = getPoint(index - 1 < 0 ? getPointCount() - 1 : index - 1);
/* 193:313 */     float[] next = getPoint(index + 1 >= getPointCount() ? 0 : index + 1);
/* 194:    */     
/* 195:315 */     float[] t1 = getNormal(prev, current);
/* 196:316 */     float[] t2 = getNormal(current, next);
/* 197:318 */     if ((index == 0) && (!closed())) {
/* 198:319 */       return t2;
/* 199:    */     }
/* 200:321 */     if ((index == getPointCount() - 1) && (!closed())) {
/* 201:322 */       return t1;
/* 202:    */     }
/* 203:325 */     float tx = (t1[0] + t2[0]) / 2.0F;
/* 204:326 */     float ty = (t1[1] + t2[1]) / 2.0F;
/* 205:327 */     float len = (float)Math.sqrt(tx * tx + ty * ty);
/* 206:328 */     return new float[] { tx / len, ty / len };
/* 207:    */   }
/* 208:    */   
/* 209:    */   public boolean contains(Shape other)
/* 210:    */   {
/* 211:340 */     if (other.intersects(this)) {
/* 212:341 */       return false;
/* 213:    */     }
/* 214:344 */     for (int i = 0; i < other.getPointCount(); i++)
/* 215:    */     {
/* 216:345 */       float[] pt = other.getPoint(i);
/* 217:346 */       if (!contains(pt[0], pt[1])) {
/* 218:347 */         return false;
/* 219:    */       }
/* 220:    */     }
/* 221:351 */     return true;
/* 222:    */   }
/* 223:    */   
/* 224:    */   private float[] getNormal(float[] start, float[] end)
/* 225:    */   {
/* 226:361 */     float dx = start[0] - end[0];
/* 227:362 */     float dy = start[1] - end[1];
/* 228:363 */     float len = (float)Math.sqrt(dx * dx + dy * dy);
/* 229:364 */     dx /= len;
/* 230:365 */     dy /= len;
/* 231:366 */     return new float[] { -dy, dx };
/* 232:    */   }
/* 233:    */   
/* 234:    */   public boolean includes(float x, float y)
/* 235:    */   {
/* 236:378 */     if (this.points.length == 0) {
/* 237:379 */       return false;
/* 238:    */     }
/* 239:382 */     checkPoints();
/* 240:    */     
/* 241:384 */     Line testLine = new Line(0.0F, 0.0F, 0.0F, 0.0F);
/* 242:385 */     Vector2f pt = new Vector2f(x, y);
/* 243:387 */     for (int i = 0; i < this.points.length; i += 2)
/* 244:    */     {
/* 245:388 */       int n = i + 2;
/* 246:389 */       if (n >= this.points.length) {
/* 247:390 */         n = 0;
/* 248:    */       }
/* 249:392 */       testLine.set(this.points[i], this.points[(i + 1)], this.points[n], this.points[(n + 1)]);
/* 250:394 */       if (testLine.on(pt)) {
/* 251:395 */         return true;
/* 252:    */       }
/* 253:    */     }
/* 254:399 */     return false;
/* 255:    */   }
/* 256:    */   
/* 257:    */   public int indexOf(float x, float y)
/* 258:    */   {
/* 259:410 */     for (int i = 0; i < this.points.length; i += 2) {
/* 260:411 */       if ((this.points[i] == x) && (this.points[(i + 1)] == y)) {
/* 261:412 */         return i / 2;
/* 262:    */       }
/* 263:    */     }
/* 264:416 */     return -1;
/* 265:    */   }
/* 266:    */   
/* 267:    */   public boolean contains(float x, float y)
/* 268:    */   {
/* 269:427 */     checkPoints();
/* 270:428 */     if (this.points.length == 0) {
/* 271:429 */       return false;
/* 272:    */     }
/* 273:432 */     boolean result = false;
/* 274:    */     
/* 275:    */ 
/* 276:    */ 
/* 277:    */ 
/* 278:437 */     int npoints = this.points.length;
/* 279:    */     
/* 280:439 */     float xold = this.points[(npoints - 2)];
/* 281:440 */     float yold = this.points[(npoints - 1)];
/* 282:441 */     for (int i = 0; i < npoints; i += 2)
/* 283:    */     {
/* 284:442 */       float xnew = this.points[i];
/* 285:443 */       float ynew = this.points[(i + 1)];
/* 286:    */       float y2;
/* 287:    */       float x1;
/* 288:    */       float x2;
/* 289:    */       float y1;
/* 290:    */       float y2;
/* 291:444 */       if (xnew > xold)
/* 292:    */       {
/* 293:445 */         float x1 = xold;
/* 294:446 */         float x2 = xnew;
/* 295:447 */         float y1 = yold;
/* 296:448 */         y2 = ynew;
/* 297:    */       }
/* 298:    */       else
/* 299:    */       {
/* 300:451 */         x1 = xnew;
/* 301:452 */         x2 = xold;
/* 302:453 */         y1 = ynew;
/* 303:454 */         y2 = yold;
/* 304:    */       }
/* 305:456 */       if ((xnew < x ? 1 : 0) == (x <= xold ? 1 : 0)) {
/* 306:458 */         if ((y - y1) * (x2 - x1) < (y2 - y1) * (x - x1)) {
/* 307:459 */           result = !result;
/* 308:    */         }
/* 309:    */       }
/* 310:461 */       xold = xnew;
/* 311:462 */       yold = ynew;
/* 312:    */     }
/* 313:465 */     return result;
/* 314:    */   }
/* 315:    */   
/* 316:    */   public boolean intersects(Shape shape)
/* 317:    */   {
/* 318:489 */     checkPoints();
/* 319:    */     
/* 320:491 */     boolean result = false;
/* 321:492 */     float[] points = getPoints();
/* 322:493 */     float[] thatPoints = shape.getPoints();
/* 323:494 */     int length = points.length;
/* 324:495 */     int thatLength = thatPoints.length;
/* 325:499 */     if (!closed()) {
/* 326:500 */       length -= 2;
/* 327:    */     }
/* 328:502 */     if (!shape.closed()) {
/* 329:503 */       thatLength -= 2;
/* 330:    */     }
/* 331:514 */     for (int i = 0; i < length; i += 2)
/* 332:    */     {
/* 333:515 */       int iNext = i + 2;
/* 334:516 */       if (iNext >= points.length) {
/* 335:517 */         iNext = 0;
/* 336:    */       }
/* 337:520 */       for (int j = 0; j < thatLength; j += 2)
/* 338:    */       {
/* 339:521 */         int jNext = j + 2;
/* 340:522 */         if (jNext >= thatPoints.length) {
/* 341:523 */           jNext = 0;
/* 342:    */         }
/* 343:526 */         double unknownA = ((points[iNext] - points[i]) * (thatPoints[(j + 1)] - points[(i + 1)]) - 
/* 344:527 */           (points[(iNext + 1)] - points[(i + 1)]) * (thatPoints[j] - points[i])) / (
/* 345:528 */           (points[(iNext + 1)] - points[(i + 1)]) * (thatPoints[jNext] - thatPoints[j]) - 
/* 346:529 */           (points[iNext] - points[i]) * (thatPoints[(jNext + 1)] - thatPoints[(j + 1)]));
/* 347:530 */         double unknownB = ((thatPoints[jNext] - thatPoints[j]) * (thatPoints[(j + 1)] - points[(i + 1)]) - 
/* 348:531 */           (thatPoints[(jNext + 1)] - thatPoints[(j + 1)]) * (thatPoints[j] - points[i])) / (
/* 349:532 */           (points[(iNext + 1)] - points[(i + 1)]) * (thatPoints[jNext] - thatPoints[j]) - 
/* 350:533 */           (points[iNext] - points[i]) * (thatPoints[(jNext + 1)] - thatPoints[(j + 1)]));
/* 351:535 */         if ((unknownA >= 0.0D) && (unknownA <= 1.0D) && (unknownB >= 0.0D) && (unknownB <= 1.0D))
/* 352:    */         {
/* 353:536 */           result = true;
/* 354:537 */           break;
/* 355:    */         }
/* 356:    */       }
/* 357:540 */       if (result) {
/* 358:    */         break;
/* 359:    */       }
/* 360:    */     }
/* 361:545 */     return result;
/* 362:    */   }
/* 363:    */   
/* 364:    */   public boolean hasVertex(float x, float y)
/* 365:    */   {
/* 366:556 */     if (this.points.length == 0) {
/* 367:557 */       return false;
/* 368:    */     }
/* 369:560 */     checkPoints();
/* 370:562 */     for (int i = 0; i < this.points.length; i += 2) {
/* 371:563 */       if ((this.points[i] == x) && (this.points[(i + 1)] == y)) {
/* 372:564 */         return true;
/* 373:    */       }
/* 374:    */     }
/* 375:568 */     return false;
/* 376:    */   }
/* 377:    */   
/* 378:    */   protected void findCenter()
/* 379:    */   {
/* 380:576 */     this.center = new float[] { 0.0F, 0.0F };
/* 381:577 */     int length = this.points.length;
/* 382:578 */     for (int i = 0; i < length; i += 2)
/* 383:    */     {
/* 384:579 */       this.center[0] += this.points[i];
/* 385:580 */       this.center[1] += this.points[(i + 1)];
/* 386:    */     }
/* 387:582 */     this.center[0] /= length / 2;
/* 388:583 */     this.center[1] /= length / 2;
/* 389:    */   }
/* 390:    */   
/* 391:    */   protected void calculateRadius()
/* 392:    */   {
/* 393:591 */     this.boundingCircleRadius = 0.0F;
/* 394:593 */     for (int i = 0; i < this.points.length; i += 2)
/* 395:    */     {
/* 396:594 */       float temp = (this.points[i] - this.center[0]) * (this.points[i] - this.center[0]) + 
/* 397:595 */         (this.points[(i + 1)] - this.center[1]) * (this.points[(i + 1)] - this.center[1]);
/* 398:596 */       this.boundingCircleRadius = (this.boundingCircleRadius > temp ? this.boundingCircleRadius : temp);
/* 399:    */     }
/* 400:598 */     this.boundingCircleRadius = ((float)Math.sqrt(this.boundingCircleRadius));
/* 401:    */   }
/* 402:    */   
/* 403:    */   protected void calculateTriangles()
/* 404:    */   {
/* 405:605 */     if ((!this.trianglesDirty) && (this.tris != null)) {
/* 406:606 */       return;
/* 407:    */     }
/* 408:608 */     if (this.points.length >= 6)
/* 409:    */     {
/* 410:609 */       boolean clockwise = true;
/* 411:610 */       float area = 0.0F;
/* 412:611 */       for (int i = 0; i < this.points.length / 2 - 1; i++)
/* 413:    */       {
/* 414:612 */         float x1 = this.points[(i * 2)];
/* 415:613 */         float y1 = this.points[(i * 2 + 1)];
/* 416:614 */         float x2 = this.points[(i * 2 + 2)];
/* 417:615 */         float y2 = this.points[(i * 2 + 3)];
/* 418:    */         
/* 419:617 */         area += x1 * y2 - y1 * x2;
/* 420:    */       }
/* 421:619 */       area /= 2.0F;
/* 422:620 */       clockwise = area > 0.0F;
/* 423:    */       
/* 424:622 */       this.tris = new NeatTriangulator();
/* 425:623 */       for (int i = 0; i < this.points.length; i += 2) {
/* 426:624 */         this.tris.addPolyPoint(this.points[i], this.points[(i + 1)]);
/* 427:    */       }
/* 428:626 */       this.tris.triangulate();
/* 429:    */     }
/* 430:629 */     this.trianglesDirty = false;
/* 431:    */   }
/* 432:    */   
/* 433:    */   public void increaseTriangulation()
/* 434:    */   {
/* 435:636 */     checkPoints();
/* 436:637 */     calculateTriangles();
/* 437:    */     
/* 438:639 */     this.tris = new OverTriangulator(this.tris);
/* 439:    */   }
/* 440:    */   
/* 441:    */   public Triangulator getTriangles()
/* 442:    */   {
/* 443:648 */     checkPoints();
/* 444:649 */     calculateTriangles();
/* 445:650 */     return this.tris;
/* 446:    */   }
/* 447:    */   
/* 448:    */   protected final void checkPoints()
/* 449:    */   {
/* 450:657 */     if (this.pointsDirty)
/* 451:    */     {
/* 452:658 */       createPoints();
/* 453:659 */       findCenter();
/* 454:660 */       calculateRadius();
/* 455:662 */       if (this.points.length > 0)
/* 456:    */       {
/* 457:663 */         this.maxX = this.points[0];
/* 458:664 */         this.maxY = this.points[1];
/* 459:665 */         this.minX = this.points[0];
/* 460:666 */         this.minY = this.points[1];
/* 461:667 */         for (int i = 0; i < this.points.length / 2; i++)
/* 462:    */         {
/* 463:668 */           this.maxX = Math.max(this.points[(i * 2)], this.maxX);
/* 464:669 */           this.maxY = Math.max(this.points[(i * 2 + 1)], this.maxY);
/* 465:670 */           this.minX = Math.min(this.points[(i * 2)], this.minX);
/* 466:671 */           this.minY = Math.min(this.points[(i * 2 + 1)], this.minY);
/* 467:    */         }
/* 468:    */       }
/* 469:674 */       this.pointsDirty = false;
/* 470:675 */       this.trianglesDirty = true;
/* 471:    */     }
/* 472:    */   }
/* 473:    */   
/* 474:    */   public void preCache()
/* 475:    */   {
/* 476:683 */     checkPoints();
/* 477:684 */     getTriangles();
/* 478:    */   }
/* 479:    */   
/* 480:    */   public boolean closed()
/* 481:    */   {
/* 482:693 */     return true;
/* 483:    */   }
/* 484:    */   
/* 485:    */   public Shape prune()
/* 486:    */   {
/* 487:702 */     Polygon result = new Polygon();
/* 488:704 */     for (int i = 0; i < getPointCount(); i++)
/* 489:    */     {
/* 490:705 */       int next = i + 1 >= getPointCount() ? 0 : i + 1;
/* 491:706 */       int prev = i - 1 < 0 ? getPointCount() - 1 : i - 1;
/* 492:    */       
/* 493:708 */       float dx1 = getPoint(i)[0] - getPoint(prev)[0];
/* 494:709 */       float dy1 = getPoint(i)[1] - getPoint(prev)[1];
/* 495:710 */       float dx2 = getPoint(next)[0] - getPoint(i)[0];
/* 496:711 */       float dy2 = getPoint(next)[1] - getPoint(i)[1];
/* 497:    */       
/* 498:713 */       float len1 = (float)Math.sqrt(dx1 * dx1 + dy1 * dy1);
/* 499:714 */       float len2 = (float)Math.sqrt(dx2 * dx2 + dy2 * dy2);
/* 500:715 */       dx1 /= len1;
/* 501:716 */       dy1 /= len1;
/* 502:717 */       dx2 /= len2;
/* 503:718 */       dy2 /= len2;
/* 504:720 */       if ((dx1 != dx2) || (dy1 != dy2)) {
/* 505:721 */         result.addPoint(getPoint(i)[0], getPoint(i)[1]);
/* 506:    */       }
/* 507:    */     }
/* 508:725 */     return result;
/* 509:    */   }
/* 510:    */   
/* 511:    */   public Shape[] subtract(Shape other)
/* 512:    */   {
/* 513:736 */     return new GeomUtil().subtract(this, other);
/* 514:    */   }
/* 515:    */   
/* 516:    */   public Shape[] union(Shape other)
/* 517:    */   {
/* 518:746 */     return new GeomUtil().union(this, other);
/* 519:    */   }
/* 520:    */   
/* 521:    */   public float getWidth()
/* 522:    */   {
/* 523:755 */     return this.maxX - this.minX;
/* 524:    */   }
/* 525:    */   
/* 526:    */   public float getHeight()
/* 527:    */   {
/* 528:765 */     return this.maxY - this.minY;
/* 529:    */   }
/* 530:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.geom.Shape
 * JD-Core Version:    0.7.0.1
 */