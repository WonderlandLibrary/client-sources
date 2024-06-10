/*   1:    */ package org.newdawn.slick.geom;
/*   2:    */ 
/*   3:    */ public class NeatTriangulator
/*   4:    */   implements Triangulator
/*   5:    */ {
/*   6:    */   static final float EPSILON = 1.0E-006F;
/*   7:    */   private float[] pointsX;
/*   8:    */   private float[] pointsY;
/*   9:    */   private int numPoints;
/*  10:    */   private Edge[] edges;
/*  11:    */   private int[] V;
/*  12:    */   private int numEdges;
/*  13:    */   private Triangle[] triangles;
/*  14:    */   private int numTriangles;
/*  15: 31 */   private float offset = 1.0E-006F;
/*  16:    */   
/*  17:    */   public NeatTriangulator()
/*  18:    */   {
/*  19: 38 */     this.pointsX = new float[100];
/*  20: 39 */     this.pointsY = new float[100];
/*  21: 40 */     this.numPoints = 0;
/*  22: 41 */     this.edges = new Edge[100];
/*  23: 42 */     this.numEdges = 0;
/*  24: 43 */     this.triangles = new Triangle[100];
/*  25: 44 */     this.numTriangles = 0;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void clear()
/*  29:    */   {
/*  30: 52 */     this.numPoints = 0;
/*  31: 53 */     this.numEdges = 0;
/*  32: 54 */     this.numTriangles = 0;
/*  33:    */   }
/*  34:    */   
/*  35:    */   private int findEdge(int i, int j)
/*  36:    */   {
/*  37:    */     int l;
/*  38:    */     int k;
/*  39:    */     int l;
/*  40: 68 */     if (i < j)
/*  41:    */     {
/*  42: 70 */       int k = i;
/*  43: 71 */       l = j;
/*  44:    */     }
/*  45:    */     else
/*  46:    */     {
/*  47: 74 */       k = j;
/*  48: 75 */       l = i;
/*  49:    */     }
/*  50: 77 */     for (int i1 = 0; i1 < this.numEdges; i1++) {
/*  51: 78 */       if ((this.edges[i1].v0 == k) && (this.edges[i1].v1 == l)) {
/*  52: 79 */         return i1;
/*  53:    */       }
/*  54:    */     }
/*  55: 81 */     return -1;
/*  56:    */   }
/*  57:    */   
/*  58:    */   private void addEdge(int i, int j, int k)
/*  59:    */   {
/*  60: 93 */     int l1 = findEdge(i, j);
/*  61:    */     Edge edge;
/*  62:    */     Edge edge;
/*  63:    */     int j1;
/*  64:    */     int k1;
/*  65: 97 */     if (l1 < 0)
/*  66:    */     {
/*  67: 99 */       if (this.numEdges == this.edges.length)
/*  68:    */       {
/*  69:101 */         Edge[] aedge = new Edge[this.edges.length * 2];
/*  70:102 */         System.arraycopy(this.edges, 0, aedge, 0, this.numEdges);
/*  71:103 */         this.edges = aedge;
/*  72:    */       }
/*  73:105 */       int j1 = -1;
/*  74:106 */       int k1 = -1;
/*  75:107 */       l1 = this.numEdges++;
/*  76:108 */       edge = this.edges[l1] =  = new Edge();
/*  77:    */     }
/*  78:    */     else
/*  79:    */     {
/*  80:111 */       edge = this.edges[l1];
/*  81:112 */       j1 = edge.t0;
/*  82:113 */       k1 = edge.t1;
/*  83:    */     }
/*  84:    */     int l;
/*  85:    */     int i1;
/*  86:117 */     if (i < j)
/*  87:    */     {
/*  88:119 */       int l = i;
/*  89:120 */       int i1 = j;
/*  90:121 */       j1 = k;
/*  91:    */     }
/*  92:    */     else
/*  93:    */     {
/*  94:124 */       l = j;
/*  95:125 */       i1 = i;
/*  96:126 */       k1 = k;
/*  97:    */     }
/*  98:128 */     edge.v0 = l;
/*  99:129 */     edge.v1 = i1;
/* 100:130 */     edge.t0 = j1;
/* 101:131 */     edge.t1 = k1;
/* 102:132 */     edge.suspect = true;
/* 103:    */   }
/* 104:    */   
/* 105:    */   private void deleteEdge(int i, int j)
/* 106:    */     throws NeatTriangulator.InternalException
/* 107:    */   {
/* 108:    */     int k;
/* 109:145 */     if ((k = findEdge(i, j)) < 0) {
/* 110:147 */       throw new InternalException("Attempt to delete unknown edge");
/* 111:    */     }
/* 112:151 */     this.edges[k] = this.edges[(--this.numEdges)];
/* 113:    */   }
/* 114:    */   
/* 115:    */   void markSuspect(int i, int j, boolean flag)
/* 116:    */     throws NeatTriangulator.InternalException
/* 117:    */   {
/* 118:    */     int k;
/* 119:167 */     if ((k = findEdge(i, j)) < 0) {
/* 120:169 */       throw new InternalException("Attempt to mark unknown edge");
/* 121:    */     }
/* 122:172 */     this.edges[k].suspect = flag;
/* 123:    */   }
/* 124:    */   
/* 125:    */   private Edge chooseSuspect()
/* 126:    */   {
/* 127:184 */     for (int i = 0; i < this.numEdges; i++)
/* 128:    */     {
/* 129:186 */       Edge edge = this.edges[i];
/* 130:187 */       if (edge.suspect)
/* 131:    */       {
/* 132:189 */         edge.suspect = false;
/* 133:190 */         if ((edge.t0 >= 0) && (edge.t1 >= 0)) {
/* 134:191 */           return edge;
/* 135:    */         }
/* 136:    */       }
/* 137:    */     }
/* 138:195 */     return null;
/* 139:    */   }
/* 140:    */   
/* 141:    */   private static float rho(float f, float f1, float f2, float f3, float f4, float f5)
/* 142:    */   {
/* 143:211 */     float f6 = f4 - f2;
/* 144:212 */     float f7 = f5 - f3;
/* 145:213 */     float f8 = f - f4;
/* 146:214 */     float f9 = f1 - f5;
/* 147:215 */     float f18 = f6 * f9 - f7 * f8;
/* 148:216 */     if (f18 > 0.0F)
/* 149:    */     {
/* 150:218 */       if (f18 < 1.0E-006F) {
/* 151:219 */         f18 = 1.0E-006F;
/* 152:    */       }
/* 153:220 */       float f12 = f6 * f6;
/* 154:221 */       float f13 = f7 * f7;
/* 155:222 */       float f14 = f8 * f8;
/* 156:223 */       float f15 = f9 * f9;
/* 157:224 */       float f10 = f2 - f;
/* 158:225 */       float f11 = f3 - f1;
/* 159:226 */       float f16 = f10 * f10;
/* 160:227 */       float f17 = f11 * f11;
/* 161:228 */       return (f12 + f13) * (f14 + f15) * (f16 + f17) / (f18 * f18);
/* 162:    */     }
/* 163:231 */     return -1.0F;
/* 164:    */   }
/* 165:    */   
/* 166:    */   private static boolean insideTriangle(float f, float f1, float f2, float f3, float f4, float f5, float f6, float f7)
/* 167:    */   {
/* 168:251 */     float f8 = f4 - f2;
/* 169:252 */     float f9 = f5 - f3;
/* 170:253 */     float f10 = f - f4;
/* 171:254 */     float f11 = f1 - f5;
/* 172:255 */     float f12 = f2 - f;
/* 173:256 */     float f13 = f3 - f1;
/* 174:257 */     float f14 = f6 - f;
/* 175:258 */     float f15 = f7 - f1;
/* 176:259 */     float f16 = f6 - f2;
/* 177:260 */     float f17 = f7 - f3;
/* 178:261 */     float f18 = f6 - f4;
/* 179:262 */     float f19 = f7 - f5;
/* 180:263 */     float f22 = f8 * f17 - f9 * f16;
/* 181:264 */     float f20 = f12 * f15 - f13 * f14;
/* 182:265 */     float f21 = f10 * f19 - f11 * f18;
/* 183:266 */     return (f22 >= 0.0D) && (f21 >= 0.0D) && (f20 >= 0.0D);
/* 184:    */   }
/* 185:    */   
/* 186:    */   private boolean snip(int i, int j, int k, int l)
/* 187:    */   {
/* 188:281 */     float f = this.pointsX[this.V[i]];
/* 189:282 */     float f1 = this.pointsY[this.V[i]];
/* 190:283 */     float f2 = this.pointsX[this.V[j]];
/* 191:284 */     float f3 = this.pointsY[this.V[j]];
/* 192:285 */     float f4 = this.pointsX[this.V[k]];
/* 193:286 */     float f5 = this.pointsY[this.V[k]];
/* 194:287 */     if (1.0E-006F > (f2 - f) * (f5 - f1) - (f3 - f1) * (f4 - f)) {
/* 195:288 */       return false;
/* 196:    */     }
/* 197:289 */     for (int i1 = 0; i1 < l; i1++) {
/* 198:290 */       if ((i1 != i) && (i1 != j) && (i1 != k))
/* 199:    */       {
/* 200:292 */         float f6 = this.pointsX[this.V[i1]];
/* 201:293 */         float f7 = this.pointsY[this.V[i1]];
/* 202:294 */         if (insideTriangle(f, f1, f2, f3, f4, f5, f6, f7)) {
/* 203:295 */           return false;
/* 204:    */         }
/* 205:    */       }
/* 206:    */     }
/* 207:298 */     return true;
/* 208:    */   }
/* 209:    */   
/* 210:    */   private float area()
/* 211:    */   {
/* 212:308 */     float f = 0.0F;
/* 213:309 */     int i = this.numPoints - 1;
/* 214:310 */     for (int j = 0; j < this.numPoints;)
/* 215:    */     {
/* 216:312 */       f += this.pointsX[i] * this.pointsY[j] - this.pointsY[i] * this.pointsX[j];
/* 217:313 */       i = j++;
/* 218:    */     }
/* 219:316 */     return f * 0.5F;
/* 220:    */   }
/* 221:    */   
/* 222:    */   public void basicTriangulation()
/* 223:    */     throws NeatTriangulator.InternalException
/* 224:    */   {
/* 225:326 */     int i = this.numPoints;
/* 226:327 */     if (i < 3) {
/* 227:328 */       return;
/* 228:    */     }
/* 229:329 */     this.numEdges = 0;
/* 230:330 */     this.numTriangles = 0;
/* 231:331 */     this.V = new int[i];
/* 232:333 */     if (0.0D < area()) {
/* 233:335 */       for (int k = 0; k < i; k++) {
/* 234:336 */         this.V[k] = k;
/* 235:    */       }
/* 236:    */     } else {
/* 237:340 */       for (int l = 0; l < i; l++) {
/* 238:341 */         this.V[l] = (this.numPoints - 1 - l);
/* 239:    */       }
/* 240:    */     }
/* 241:344 */     int k1 = 2 * i;
/* 242:345 */     int i1 = i - 1;
/* 243:346 */     while (i > 2)
/* 244:    */     {
/* 245:348 */       if (k1-- <= 0) {
/* 246:349 */         throw new InternalException("Bad polygon");
/* 247:    */       }
/* 248:352 */       int j = i1;
/* 249:353 */       if (i <= j) {
/* 250:354 */         j = 0;
/* 251:    */       }
/* 252:355 */       i1 = j + 1;
/* 253:356 */       if (i <= i1) {
/* 254:357 */         i1 = 0;
/* 255:    */       }
/* 256:358 */       int j1 = i1 + 1;
/* 257:359 */       if (i <= j1) {
/* 258:360 */         j1 = 0;
/* 259:    */       }
/* 260:361 */       if (snip(j, i1, j1, i))
/* 261:    */       {
/* 262:363 */         int l1 = this.V[j];
/* 263:364 */         int i2 = this.V[i1];
/* 264:365 */         int j2 = this.V[j1];
/* 265:366 */         if (this.numTriangles == this.triangles.length)
/* 266:    */         {
/* 267:368 */           Triangle[] atriangle = new Triangle[this.triangles.length * 2];
/* 268:369 */           System.arraycopy(this.triangles, 0, atriangle, 0, this.numTriangles);
/* 269:370 */           this.triangles = atriangle;
/* 270:    */         }
/* 271:372 */         this.triangles[this.numTriangles] = new Triangle(l1, i2, j2);
/* 272:373 */         addEdge(l1, i2, this.numTriangles);
/* 273:374 */         addEdge(i2, j2, this.numTriangles);
/* 274:375 */         addEdge(j2, l1, this.numTriangles);
/* 275:376 */         this.numTriangles += 1;
/* 276:377 */         int k2 = i1;
/* 277:378 */         for (int l2 = i1 + 1; l2 < i; l2++)
/* 278:    */         {
/* 279:380 */           this.V[k2] = this.V[l2];
/* 280:381 */           k2++;
/* 281:    */         }
/* 282:384 */         i--;
/* 283:385 */         k1 = 2 * i;
/* 284:    */       }
/* 285:    */     }
/* 286:388 */     this.V = null;
/* 287:    */   }
/* 288:    */   
/* 289:    */   private void optimize()
/* 290:    */     throws NeatTriangulator.InternalException
/* 291:    */   {
/* 292:    */     Edge edge;
/* 293:401 */     while ((edge = chooseSuspect()) != null)
/* 294:    */     {
/* 295:404 */       int i1 = edge.v0;
/* 296:405 */       int k1 = edge.v1;
/* 297:406 */       int i = edge.t0;
/* 298:407 */       int j = edge.t1;
/* 299:408 */       int j1 = -1;
/* 300:409 */       int l1 = -1;
/* 301:410 */       for (int k = 0; k < 3; k++)
/* 302:    */       {
/* 303:412 */         int i2 = this.triangles[i].v[k];
/* 304:413 */         if ((i1 != i2) && (k1 != i2))
/* 305:    */         {
/* 306:416 */           l1 = i2;
/* 307:417 */           break;
/* 308:    */         }
/* 309:    */       }
/* 310:420 */       for (int l = 0; l < 3; l++)
/* 311:    */       {
/* 312:422 */         int j2 = this.triangles[j].v[l];
/* 313:423 */         if ((i1 != j2) && (k1 != j2))
/* 314:    */         {
/* 315:426 */           j1 = j2;
/* 316:427 */           break;
/* 317:    */         }
/* 318:    */       }
/* 319:430 */       if ((-1 == j1) || (-1 == l1)) {
/* 320:431 */         throw new InternalException("can't find quad");
/* 321:    */       }
/* 322:434 */       float f = this.pointsX[i1];
/* 323:435 */       float f1 = this.pointsY[i1];
/* 324:436 */       float f2 = this.pointsX[j1];
/* 325:437 */       float f3 = this.pointsY[j1];
/* 326:438 */       float f4 = this.pointsX[k1];
/* 327:439 */       float f5 = this.pointsY[k1];
/* 328:440 */       float f6 = this.pointsX[l1];
/* 329:441 */       float f7 = this.pointsY[l1];
/* 330:442 */       float f8 = rho(f, f1, f2, f3, f4, f5);
/* 331:443 */       float f9 = rho(f, f1, f4, f5, f6, f7);
/* 332:444 */       float f10 = rho(f2, f3, f4, f5, f6, f7);
/* 333:445 */       float f11 = rho(f2, f3, f6, f7, f, f1);
/* 334:446 */       if ((0.0F > f8) || (0.0F > f9)) {
/* 335:447 */         throw new InternalException("original triangles backwards");
/* 336:    */       }
/* 337:449 */       if ((0.0F <= f10) && (0.0F <= f11))
/* 338:    */       {
/* 339:451 */         if (f8 > f9) {
/* 340:452 */           f8 = f9;
/* 341:    */         }
/* 342:454 */         if (f10 > f11) {
/* 343:455 */           f10 = f11;
/* 344:    */         }
/* 345:457 */         if (f8 > f10)
/* 346:    */         {
/* 347:458 */           deleteEdge(i1, k1);
/* 348:459 */           this.triangles[i].v[0] = j1;
/* 349:460 */           this.triangles[i].v[1] = k1;
/* 350:461 */           this.triangles[i].v[2] = l1;
/* 351:462 */           this.triangles[j].v[0] = j1;
/* 352:463 */           this.triangles[j].v[1] = l1;
/* 353:464 */           this.triangles[j].v[2] = i1;
/* 354:465 */           addEdge(j1, k1, i);
/* 355:466 */           addEdge(k1, l1, i);
/* 356:467 */           addEdge(l1, j1, i);
/* 357:468 */           addEdge(l1, i1, j);
/* 358:469 */           addEdge(i1, j1, j);
/* 359:470 */           addEdge(j1, l1, j);
/* 360:471 */           markSuspect(j1, l1, false);
/* 361:    */         }
/* 362:    */       }
/* 363:    */     }
/* 364:    */   }
/* 365:    */   
/* 366:    */   public boolean triangulate()
/* 367:    */   {
/* 368:    */     try
/* 369:    */     {
/* 370:484 */       basicTriangulation();
/* 371:    */       
/* 372:486 */       return true;
/* 373:    */     }
/* 374:    */     catch (InternalException e)
/* 375:    */     {
/* 376:490 */       this.numEdges = 0;
/* 377:    */     }
/* 378:492 */     return false;
/* 379:    */   }
/* 380:    */   
/* 381:    */   public void addPolyPoint(float x, float y)
/* 382:    */   {
/* 383:500 */     for (int i = 0; i < this.numPoints; i++) {
/* 384:501 */       if ((this.pointsX[i] == x) && (this.pointsY[i] == y))
/* 385:    */       {
/* 386:503 */         y += this.offset;
/* 387:504 */         this.offset += 1.0E-006F;
/* 388:    */       }
/* 389:    */     }
/* 390:508 */     if (this.numPoints == this.pointsX.length)
/* 391:    */     {
/* 392:510 */       float[] af = new float[this.numPoints * 2];
/* 393:511 */       System.arraycopy(this.pointsX, 0, af, 0, this.numPoints);
/* 394:512 */       this.pointsX = af;
/* 395:513 */       af = new float[this.numPoints * 2];
/* 396:514 */       System.arraycopy(this.pointsY, 0, af, 0, this.numPoints);
/* 397:515 */       this.pointsY = af;
/* 398:    */     }
/* 399:518 */     this.pointsX[this.numPoints] = x;
/* 400:519 */     this.pointsY[this.numPoints] = y;
/* 401:520 */     this.numPoints += 1;
/* 402:    */   }
/* 403:    */   
/* 404:    */   class Triangle
/* 405:    */   {
/* 406:    */     int[] v;
/* 407:    */     
/* 408:    */     Triangle(int i, int j, int k)
/* 409:    */     {
/* 410:542 */       this.v = new int[3];
/* 411:543 */       this.v[0] = i;
/* 412:544 */       this.v[1] = j;
/* 413:545 */       this.v[2] = k;
/* 414:    */     }
/* 415:    */   }
/* 416:    */   
/* 417:    */   class Edge
/* 418:    */   {
/* 419:    */     int v0;
/* 420:    */     int v1;
/* 421:    */     int t0;
/* 422:    */     int t1;
/* 423:    */     boolean suspect;
/* 424:    */     
/* 425:    */     Edge()
/* 426:    */     {
/* 427:572 */       this.v0 = -1;
/* 428:573 */       this.v1 = -1;
/* 429:574 */       this.t0 = -1;
/* 430:575 */       this.t1 = -1;
/* 431:    */     }
/* 432:    */   }
/* 433:    */   
/* 434:    */   class InternalException
/* 435:    */     extends Exception
/* 436:    */   {
/* 437:    */     public InternalException(String msg)
/* 438:    */     {
/* 439:591 */       super();
/* 440:    */     }
/* 441:    */   }
/* 442:    */   
/* 443:    */   public int getTriangleCount()
/* 444:    */   {
/* 445:599 */     return this.numTriangles;
/* 446:    */   }
/* 447:    */   
/* 448:    */   public float[] getTrianglePoint(int tri, int i)
/* 449:    */   {
/* 450:606 */     float xp = this.pointsX[this.triangles[tri].v[i]];
/* 451:607 */     float yp = this.pointsY[this.triangles[tri].v[i]];
/* 452:    */     
/* 453:609 */     return new float[] { xp, yp };
/* 454:    */   }
/* 455:    */   
/* 456:    */   public void startHole() {}
/* 457:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.geom.NeatTriangulator
 * JD-Core Version:    0.7.0.1
 */