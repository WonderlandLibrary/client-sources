/*   1:    */ package org.newdawn.slick.particles;
/*   2:    */ 
/*   3:    */ import java.io.ByteArrayInputStream;
/*   4:    */ import java.io.ByteArrayOutputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import org.newdawn.slick.Color;
/*   8:    */ import org.newdawn.slick.Image;
/*   9:    */ import org.newdawn.slick.SlickException;
/*  10:    */ import org.newdawn.slick.geom.Vector2f;
/*  11:    */ import org.newdawn.slick.util.FastTrig;
/*  12:    */ import org.newdawn.slick.util.Log;
/*  13:    */ 
/*  14:    */ public class ConfigurableEmitter
/*  15:    */   implements ParticleEmitter
/*  16:    */ {
/*  17: 25 */   private static String relativePath = "";
/*  18:    */   
/*  19:    */   public static void setRelativePath(String path)
/*  20:    */   {
/*  21: 34 */     if (!path.endsWith("/")) {
/*  22: 35 */       path = path + "/";
/*  23:    */     }
/*  24: 37 */     relativePath = path;
/*  25:    */   }
/*  26:    */   
/*  27: 41 */   public Range spawnInterval = new Range(100.0F, 100.0F, null);
/*  28: 43 */   public Range spawnCount = new Range(5.0F, 5.0F, null);
/*  29: 45 */   public Range initialLife = new Range(1000.0F, 1000.0F, null);
/*  30: 47 */   public Range initialSize = new Range(10.0F, 10.0F, null);
/*  31: 49 */   public Range xOffset = new Range(0.0F, 0.0F, null);
/*  32: 51 */   public Range yOffset = new Range(0.0F, 0.0F, null);
/*  33: 53 */   public RandomValue spread = new RandomValue(360.0F, null);
/*  34: 55 */   public SimpleValue angularOffset = new SimpleValue(0.0F, null);
/*  35: 57 */   public Range initialDistance = new Range(0.0F, 0.0F, null);
/*  36: 59 */   public Range speed = new Range(50.0F, 50.0F, null);
/*  37: 61 */   public SimpleValue growthFactor = new SimpleValue(0.0F, null);
/*  38: 63 */   public SimpleValue gravityFactor = new SimpleValue(0.0F, null);
/*  39: 65 */   public SimpleValue windFactor = new SimpleValue(0.0F, null);
/*  40: 67 */   public Range length = new Range(1000.0F, 1000.0F, null);
/*  41: 73 */   public ArrayList colors = new ArrayList();
/*  42: 75 */   public SimpleValue startAlpha = new SimpleValue(255.0F, null);
/*  43: 77 */   public SimpleValue endAlpha = new SimpleValue(0.0F, null);
/*  44:    */   public LinearInterpolator alpha;
/*  45:    */   public LinearInterpolator size;
/*  46:    */   public LinearInterpolator velocity;
/*  47:    */   public LinearInterpolator scaleY;
/*  48: 89 */   public Range emitCount = new Range(1000.0F, 1000.0F, null);
/*  49: 91 */   public int usePoints = 1;
/*  50: 94 */   public boolean useOriented = false;
/*  51: 99 */   public boolean useAdditive = false;
/*  52:    */   public String name;
/*  53:104 */   public String imageName = "";
/*  54:    */   private Image image;
/*  55:    */   private boolean updateImage;
/*  56:111 */   private boolean enabled = true;
/*  57:    */   private float x;
/*  58:    */   private float y;
/*  59:117 */   private int nextSpawn = 0;
/*  60:    */   private int timeout;
/*  61:    */   private int particleCount;
/*  62:    */   private ParticleSystem engine;
/*  63:    */   private int leftToEmit;
/*  64:129 */   protected boolean wrapUp = false;
/*  65:131 */   protected boolean completed = false;
/*  66:    */   protected boolean adjust;
/*  67:    */   protected float adjustx;
/*  68:    */   protected float adjusty;
/*  69:    */   
/*  70:    */   public ConfigurableEmitter(String name)
/*  71:    */   {
/*  72:146 */     this.name = name;
/*  73:147 */     this.leftToEmit = ((int)this.emitCount.random());
/*  74:148 */     this.timeout = ((int)this.length.random());
/*  75:    */     
/*  76:150 */     this.colors.add(new ColorRecord(0.0F, Color.white));
/*  77:151 */     this.colors.add(new ColorRecord(1.0F, Color.red));
/*  78:    */     
/*  79:153 */     ArrayList curve = new ArrayList();
/*  80:154 */     curve.add(new Vector2f(0.0F, 0.0F));
/*  81:155 */     curve.add(new Vector2f(1.0F, 255.0F));
/*  82:156 */     this.alpha = new LinearInterpolator(curve, 0, 255);
/*  83:    */     
/*  84:158 */     curve = new ArrayList();
/*  85:159 */     curve.add(new Vector2f(0.0F, 0.0F));
/*  86:160 */     curve.add(new Vector2f(1.0F, 255.0F));
/*  87:161 */     this.size = new LinearInterpolator(curve, 0, 255);
/*  88:    */     
/*  89:163 */     curve = new ArrayList();
/*  90:164 */     curve.add(new Vector2f(0.0F, 0.0F));
/*  91:165 */     curve.add(new Vector2f(1.0F, 1.0F));
/*  92:166 */     this.velocity = new LinearInterpolator(curve, 0, 1);
/*  93:    */     
/*  94:168 */     curve = new ArrayList();
/*  95:169 */     curve.add(new Vector2f(0.0F, 0.0F));
/*  96:170 */     curve.add(new Vector2f(1.0F, 1.0F));
/*  97:171 */     this.scaleY = new LinearInterpolator(curve, 0, 1);
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setImageName(String imageName)
/* 101:    */   {
/* 102:184 */     if (imageName.length() == 0) {
/* 103:185 */       imageName = null;
/* 104:    */     }
/* 105:188 */     this.imageName = imageName;
/* 106:189 */     if (imageName == null) {
/* 107:190 */       this.image = null;
/* 108:    */     } else {
/* 109:192 */       this.updateImage = true;
/* 110:    */     }
/* 111:    */   }
/* 112:    */   
/* 113:    */   public String getImageName()
/* 114:    */   {
/* 115:202 */     return this.imageName;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public String toString()
/* 119:    */   {
/* 120:209 */     return "[" + this.name + "]";
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void setPosition(float x, float y)
/* 124:    */   {
/* 125:221 */     setPosition(x, y, true);
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void setPosition(float x, float y, boolean moveParticles)
/* 129:    */   {
/* 130:235 */     if (moveParticles)
/* 131:    */     {
/* 132:236 */       this.adjust = true;
/* 133:237 */       this.adjustx -= this.x - x;
/* 134:238 */       this.adjusty -= this.y - y;
/* 135:    */     }
/* 136:240 */     this.x = x;
/* 137:241 */     this.y = y;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public float getX()
/* 141:    */   {
/* 142:250 */     return this.x;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public float getY()
/* 146:    */   {
/* 147:259 */     return this.y;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public boolean isEnabled()
/* 151:    */   {
/* 152:266 */     return this.enabled;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void setEnabled(boolean enabled)
/* 156:    */   {
/* 157:273 */     this.enabled = enabled;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void update(ParticleSystem system, int delta)
/* 161:    */   {
/* 162:281 */     this.engine = system;
/* 163:283 */     if (!this.adjust)
/* 164:    */     {
/* 165:284 */       this.adjustx = 0.0F;
/* 166:285 */       this.adjusty = 0.0F;
/* 167:    */     }
/* 168:    */     else
/* 169:    */     {
/* 170:287 */       this.adjust = false;
/* 171:    */     }
/* 172:290 */     if (this.updateImage)
/* 173:    */     {
/* 174:291 */       this.updateImage = false;
/* 175:    */       try
/* 176:    */       {
/* 177:293 */         this.image = new Image(relativePath + this.imageName);
/* 178:    */       }
/* 179:    */       catch (SlickException e)
/* 180:    */       {
/* 181:295 */         this.image = null;
/* 182:296 */         Log.error(e);
/* 183:    */       }
/* 184:    */     }
/* 185:300 */     if (((this.wrapUp) || 
/* 186:301 */       ((this.length.isEnabled()) && (this.timeout < 0)) || (
/* 187:302 */       (this.emitCount.isEnabled()) && (this.leftToEmit <= 0))) && 
/* 188:303 */       (this.particleCount == 0)) {
/* 189:304 */       this.completed = true;
/* 190:    */     }
/* 191:307 */     this.particleCount = 0;
/* 192:309 */     if (this.wrapUp) {
/* 193:310 */       return;
/* 194:    */     }
/* 195:313 */     if (this.length.isEnabled())
/* 196:    */     {
/* 197:314 */       if (this.timeout < 0) {
/* 198:315 */         return;
/* 199:    */       }
/* 200:317 */       this.timeout -= delta;
/* 201:    */     }
/* 202:319 */     if ((this.emitCount.isEnabled()) && 
/* 203:320 */       (this.leftToEmit <= 0)) {
/* 204:321 */       return;
/* 205:    */     }
/* 206:325 */     this.nextSpawn -= delta;
/* 207:326 */     if (this.nextSpawn < 0)
/* 208:    */     {
/* 209:327 */       this.nextSpawn = ((int)this.spawnInterval.random());
/* 210:328 */       int count = (int)this.spawnCount.random();
/* 211:330 */       for (int i = 0; i < count; i++)
/* 212:    */       {
/* 213:331 */         Particle p = system.getNewParticle(this, this.initialLife.random());
/* 214:332 */         p.setSize(this.initialSize.random());
/* 215:333 */         p.setPosition(this.x + this.xOffset.random(), this.y + this.yOffset.random());
/* 216:334 */         p.setVelocity(0.0F, 0.0F, 0.0F);
/* 217:    */         
/* 218:336 */         float dist = this.initialDistance.random();
/* 219:337 */         float power = this.speed.random();
/* 220:338 */         if ((dist != 0.0F) || (power != 0.0F))
/* 221:    */         {
/* 222:339 */           float s = this.spread.getValue(0.0F);
/* 223:340 */           float ang = s + this.angularOffset.getValue(0.0F) - this.spread
/* 224:341 */             .getValue() / 2.0F - 90.0F;
/* 225:342 */           float xa = (float)FastTrig.cos(Math.toRadians(ang)) * dist;
/* 226:343 */           float ya = (float)FastTrig.sin(Math.toRadians(ang)) * dist;
/* 227:344 */           p.adjustPosition(xa, ya);
/* 228:    */           
/* 229:346 */           float xv = (float)FastTrig.cos(Math.toRadians(ang));
/* 230:347 */           float yv = (float)FastTrig.sin(Math.toRadians(ang));
/* 231:348 */           p.setVelocity(xv, yv, power * 0.001F);
/* 232:    */         }
/* 233:351 */         if (this.image != null) {
/* 234:352 */           p.setImage(this.image);
/* 235:    */         }
/* 236:355 */         ColorRecord start = (ColorRecord)this.colors.get(0);
/* 237:356 */         p.setColor(start.col.r, start.col.g, start.col.b, this.startAlpha
/* 238:357 */           .getValue(0.0F) / 255.0F);
/* 239:358 */         p.setUsePoint(this.usePoints);
/* 240:359 */         p.setOriented(this.useOriented);
/* 241:361 */         if (this.emitCount.isEnabled())
/* 242:    */         {
/* 243:362 */           this.leftToEmit -= 1;
/* 244:363 */           if (this.leftToEmit <= 0) {
/* 245:    */             break;
/* 246:    */           }
/* 247:    */         }
/* 248:    */       }
/* 249:    */     }
/* 250:    */   }
/* 251:    */   
/* 252:    */   public void updateParticle(Particle particle, int delta)
/* 253:    */   {
/* 254:376 */     this.particleCount += 1;
/* 255:    */     
/* 256:    */ 
/* 257:379 */     particle.x += this.adjustx;
/* 258:380 */     particle.y += this.adjusty;
/* 259:    */     
/* 260:382 */     particle.adjustVelocity(this.windFactor.getValue(0.0F) * 5.0E-005F * delta, this.gravityFactor
/* 261:383 */       .getValue(0.0F) * 5.0E-005F * delta);
/* 262:    */     
/* 263:385 */     float offset = particle.getLife() / particle.getOriginalLife();
/* 264:386 */     float inv = 1.0F - offset;
/* 265:387 */     float colOffset = 0.0F;
/* 266:388 */     float colInv = 1.0F;
/* 267:    */     
/* 268:390 */     Color startColor = null;
/* 269:391 */     Color endColor = null;
/* 270:392 */     for (int i = 0; i < this.colors.size() - 1; i++)
/* 271:    */     {
/* 272:393 */       ColorRecord rec1 = (ColorRecord)this.colors.get(i);
/* 273:394 */       ColorRecord rec2 = (ColorRecord)this.colors.get(i + 1);
/* 274:396 */       if ((inv >= rec1.pos) && (inv <= rec2.pos))
/* 275:    */       {
/* 276:397 */         startColor = rec1.col;
/* 277:398 */         endColor = rec2.col;
/* 278:    */         
/* 279:400 */         float step = rec2.pos - rec1.pos;
/* 280:401 */         colOffset = inv - rec1.pos;
/* 281:402 */         colOffset /= step;
/* 282:403 */         colOffset = 1.0F - colOffset;
/* 283:404 */         colInv = 1.0F - colOffset;
/* 284:    */       }
/* 285:    */     }
/* 286:408 */     if (startColor != null)
/* 287:    */     {
/* 288:409 */       float r = startColor.r * colOffset + endColor.r * colInv;
/* 289:410 */       float g = startColor.g * colOffset + endColor.g * colInv;
/* 290:411 */       float b = startColor.b * colOffset + endColor.b * colInv;
/* 291:    */       float a;
/* 292:    */       float a;
/* 293:414 */       if (this.alpha.isActive()) {
/* 294:415 */         a = this.alpha.getValue(inv) / 255.0F;
/* 295:    */       } else {
/* 296:417 */         a = this.startAlpha.getValue(0.0F) / 255.0F * offset + 
/* 297:418 */           this.endAlpha.getValue(0.0F) / 255.0F * inv;
/* 298:    */       }
/* 299:420 */       particle.setColor(r, g, b, a);
/* 300:    */     }
/* 301:423 */     if (this.size.isActive())
/* 302:    */     {
/* 303:424 */       float s = this.size.getValue(inv);
/* 304:425 */       particle.setSize(s);
/* 305:    */     }
/* 306:    */     else
/* 307:    */     {
/* 308:427 */       particle.adjustSize(delta * this.growthFactor.getValue(0.0F) * 0.001F);
/* 309:    */     }
/* 310:430 */     if (this.velocity.isActive()) {
/* 311:431 */       particle.setSpeed(this.velocity.getValue(inv));
/* 312:    */     }
/* 313:434 */     if (this.scaleY.isActive()) {
/* 314:435 */       particle.setScaleY(this.scaleY.getValue(inv));
/* 315:    */     }
/* 316:    */   }
/* 317:    */   
/* 318:    */   public boolean completed()
/* 319:    */   {
/* 320:445 */     if (this.engine == null) {
/* 321:446 */       return false;
/* 322:    */     }
/* 323:449 */     if (this.length.isEnabled())
/* 324:    */     {
/* 325:450 */       if (this.timeout > 0) {
/* 326:451 */         return false;
/* 327:    */       }
/* 328:453 */       return this.completed;
/* 329:    */     }
/* 330:455 */     if (this.emitCount.isEnabled())
/* 331:    */     {
/* 332:456 */       if (this.leftToEmit > 0) {
/* 333:457 */         return false;
/* 334:    */       }
/* 335:459 */       return this.completed;
/* 336:    */     }
/* 337:462 */     if (this.wrapUp) {
/* 338:463 */       return this.completed;
/* 339:    */     }
/* 340:466 */     return false;
/* 341:    */   }
/* 342:    */   
/* 343:    */   public void replay()
/* 344:    */   {
/* 345:473 */     reset();
/* 346:474 */     this.nextSpawn = 0;
/* 347:475 */     this.leftToEmit = ((int)this.emitCount.random());
/* 348:476 */     this.timeout = ((int)this.length.random());
/* 349:    */   }
/* 350:    */   
/* 351:    */   public void reset()
/* 352:    */   {
/* 353:483 */     this.completed = false;
/* 354:484 */     if (this.engine != null) {
/* 355:485 */       this.engine.releaseAll(this);
/* 356:    */     }
/* 357:    */   }
/* 358:    */   
/* 359:    */   public void replayCheck()
/* 360:    */   {
/* 361:493 */     if ((completed()) && 
/* 362:494 */       (this.engine != null) && 
/* 363:495 */       (this.engine.getParticleCount() == 0)) {
/* 364:496 */       replay();
/* 365:    */     }
/* 366:    */   }
/* 367:    */   
/* 368:    */   public ConfigurableEmitter duplicate()
/* 369:    */   {
/* 370:508 */     ConfigurableEmitter theCopy = null;
/* 371:    */     try
/* 372:    */     {
/* 373:510 */       ByteArrayOutputStream bout = new ByteArrayOutputStream();
/* 374:511 */       ParticleIO.saveEmitter(bout, this);
/* 375:512 */       ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
/* 376:513 */       theCopy = ParticleIO.loadEmitter(bin);
/* 377:    */     }
/* 378:    */     catch (IOException e)
/* 379:    */     {
/* 380:515 */       Log.error("Slick: ConfigurableEmitter.duplicate(): caught exception " + e.toString());
/* 381:516 */       return null;
/* 382:    */     }
/* 383:518 */     return theCopy;
/* 384:    */   }
/* 385:    */   
/* 386:    */   public class SimpleValue
/* 387:    */     implements ConfigurableEmitter.Value
/* 388:    */   {
/* 389:    */     private float value;
/* 390:    */     private float next;
/* 391:    */     
/* 392:    */     private SimpleValue(float value)
/* 393:    */     {
/* 394:554 */       this.value = value;
/* 395:    */     }
/* 396:    */     
/* 397:    */     public float getValue(float time)
/* 398:    */     {
/* 399:563 */       return this.value;
/* 400:    */     }
/* 401:    */     
/* 402:    */     public void setValue(float value)
/* 403:    */     {
/* 404:573 */       this.value = value;
/* 405:    */     }
/* 406:    */   }
/* 407:    */   
/* 408:    */   public class RandomValue
/* 409:    */     implements ConfigurableEmitter.Value
/* 410:    */   {
/* 411:    */     private float value;
/* 412:    */     
/* 413:    */     private RandomValue(float value)
/* 414:    */     {
/* 415:593 */       this.value = value;
/* 416:    */     }
/* 417:    */     
/* 418:    */     public float getValue(float time)
/* 419:    */     {
/* 420:602 */       return (float)(Math.random() * this.value);
/* 421:    */     }
/* 422:    */     
/* 423:    */     public void setValue(float value)
/* 424:    */     {
/* 425:612 */       this.value = value;
/* 426:    */     }
/* 427:    */     
/* 428:    */     public float getValue()
/* 429:    */     {
/* 430:621 */       return this.value;
/* 431:    */     }
/* 432:    */   }
/* 433:    */   
/* 434:    */   public class LinearInterpolator
/* 435:    */     implements ConfigurableEmitter.Value
/* 436:    */   {
/* 437:    */     private ArrayList curve;
/* 438:    */     private boolean active;
/* 439:    */     private int min;
/* 440:    */     private int max;
/* 441:    */     
/* 442:    */     public LinearInterpolator(ArrayList curve, int min, int max)
/* 443:    */     {
/* 444:648 */       this.curve = curve;
/* 445:649 */       this.min = min;
/* 446:650 */       this.max = max;
/* 447:651 */       this.active = false;
/* 448:    */     }
/* 449:    */     
/* 450:    */     public void setCurve(ArrayList curve)
/* 451:    */     {
/* 452:660 */       this.curve = curve;
/* 453:    */     }
/* 454:    */     
/* 455:    */     public ArrayList getCurve()
/* 456:    */     {
/* 457:669 */       return this.curve;
/* 458:    */     }
/* 459:    */     
/* 460:    */     public float getValue(float t)
/* 461:    */     {
/* 462:680 */       Vector2f p0 = (Vector2f)this.curve.get(0);
/* 463:681 */       for (int i = 1; i < this.curve.size(); i++)
/* 464:    */       {
/* 465:682 */         Vector2f p1 = (Vector2f)this.curve.get(i);
/* 466:684 */         if ((t >= p0.getX()) && (t <= p1.getX()))
/* 467:    */         {
/* 468:686 */           float st = (t - p0.getX()) / (
/* 469:687 */             p1.getX() - p0.getX());
/* 470:688 */           float r = p0.getY() + st * (
/* 471:689 */             p1.getY() - p0.getY());
/* 472:    */           
/* 473:    */ 
/* 474:    */ 
/* 475:693 */           return r;
/* 476:    */         }
/* 477:696 */         p0 = p1;
/* 478:    */       }
/* 479:698 */       return 0.0F;
/* 480:    */     }
/* 481:    */     
/* 482:    */     public boolean isActive()
/* 483:    */     {
/* 484:707 */       return this.active;
/* 485:    */     }
/* 486:    */     
/* 487:    */     public void setActive(boolean active)
/* 488:    */     {
/* 489:716 */       this.active = active;
/* 490:    */     }
/* 491:    */     
/* 492:    */     public int getMax()
/* 493:    */     {
/* 494:725 */       return this.max;
/* 495:    */     }
/* 496:    */     
/* 497:    */     public void setMax(int max)
/* 498:    */     {
/* 499:734 */       this.max = max;
/* 500:    */     }
/* 501:    */     
/* 502:    */     public int getMin()
/* 503:    */     {
/* 504:743 */       return this.min;
/* 505:    */     }
/* 506:    */     
/* 507:    */     public void setMin(int min)
/* 508:    */     {
/* 509:752 */       this.min = min;
/* 510:    */     }
/* 511:    */   }
/* 512:    */   
/* 513:    */   public class ColorRecord
/* 514:    */   {
/* 515:    */     public float pos;
/* 516:    */     public Color col;
/* 517:    */     
/* 518:    */     public ColorRecord(float pos, Color col)
/* 519:    */     {
/* 520:776 */       this.pos = pos;
/* 521:777 */       this.col = col;
/* 522:    */     }
/* 523:    */   }
/* 524:    */   
/* 525:    */   public void addColorPoint(float pos, Color col)
/* 526:    */   {
/* 527:790 */     this.colors.add(new ColorRecord(pos, col));
/* 528:    */   }
/* 529:    */   
/* 530:    */   public class Range
/* 531:    */   {
/* 532:    */     private float max;
/* 533:    */     private float min;
/* 534:804 */     private boolean enabled = false;
/* 535:    */     
/* 536:    */     private Range(float min, float max)
/* 537:    */     {
/* 538:815 */       this.min = min;
/* 539:816 */       this.max = max;
/* 540:    */     }
/* 541:    */     
/* 542:    */     public float random()
/* 543:    */     {
/* 544:825 */       return (float)(this.min + Math.random() * (this.max - this.min));
/* 545:    */     }
/* 546:    */     
/* 547:    */     public boolean isEnabled()
/* 548:    */     {
/* 549:834 */       return this.enabled;
/* 550:    */     }
/* 551:    */     
/* 552:    */     public void setEnabled(boolean enabled)
/* 553:    */     {
/* 554:844 */       this.enabled = enabled;
/* 555:    */     }
/* 556:    */     
/* 557:    */     public float getMax()
/* 558:    */     {
/* 559:853 */       return this.max;
/* 560:    */     }
/* 561:    */     
/* 562:    */     public void setMax(float max)
/* 563:    */     {
/* 564:863 */       this.max = max;
/* 565:    */     }
/* 566:    */     
/* 567:    */     public float getMin()
/* 568:    */     {
/* 569:872 */       return this.min;
/* 570:    */     }
/* 571:    */     
/* 572:    */     public void setMin(float min)
/* 573:    */     {
/* 574:882 */       this.min = min;
/* 575:    */     }
/* 576:    */   }
/* 577:    */   
/* 578:    */   public boolean useAdditive()
/* 579:    */   {
/* 580:887 */     return this.useAdditive;
/* 581:    */   }
/* 582:    */   
/* 583:    */   public boolean isOriented()
/* 584:    */   {
/* 585:891 */     return this.useOriented;
/* 586:    */   }
/* 587:    */   
/* 588:    */   public boolean usePoints(ParticleSystem system)
/* 589:    */   {
/* 590:895 */     return ((this.usePoints == 1) && (system.usePoints())) || 
/* 591:896 */       (this.usePoints == 2);
/* 592:    */   }
/* 593:    */   
/* 594:    */   public Image getImage()
/* 595:    */   {
/* 596:900 */     return this.image;
/* 597:    */   }
/* 598:    */   
/* 599:    */   public void wrapUp()
/* 600:    */   {
/* 601:904 */     this.wrapUp = true;
/* 602:    */   }
/* 603:    */   
/* 604:    */   public void resetState()
/* 605:    */   {
/* 606:908 */     this.wrapUp = false;
/* 607:909 */     replay();
/* 608:    */   }
/* 609:    */   
/* 610:    */   public static abstract interface Value
/* 611:    */   {
/* 612:    */     public abstract float getValue(float paramFloat);
/* 613:    */   }
/* 614:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.particles.ConfigurableEmitter
 * JD-Core Version:    0.7.0.1
 */