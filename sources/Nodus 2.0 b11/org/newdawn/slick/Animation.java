/*   1:    */ package org.newdawn.slick;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import org.lwjgl.Sys;
/*   5:    */ import org.newdawn.slick.util.Log;
/*   6:    */ 
/*   7:    */ public class Animation
/*   8:    */   implements Renderable
/*   9:    */ {
/*  10: 16 */   private ArrayList frames = new ArrayList();
/*  11: 18 */   private int currentFrame = -1;
/*  12: 20 */   private long nextChange = 0L;
/*  13: 22 */   private boolean stopped = false;
/*  14:    */   private long timeLeft;
/*  15: 26 */   private float speed = 1.0F;
/*  16: 28 */   private int stopAt = -2;
/*  17:    */   private long lastUpdate;
/*  18: 32 */   private boolean firstUpdate = true;
/*  19: 34 */   private boolean autoUpdate = true;
/*  20: 36 */   private int direction = 1;
/*  21:    */   private boolean pingPong;
/*  22: 40 */   private boolean loop = true;
/*  23: 42 */   private SpriteSheet spriteSheet = null;
/*  24:    */   
/*  25:    */   public Animation()
/*  26:    */   {
/*  27: 48 */     this(true);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public Animation(Image[] frames, int duration)
/*  31:    */   {
/*  32: 58 */     this(frames, duration, true);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public Animation(Image[] frames, int[] durations)
/*  36:    */   {
/*  37: 68 */     this(frames, durations, true);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public Animation(boolean autoUpdate)
/*  41:    */   {
/*  42: 78 */     this.currentFrame = 0;
/*  43: 79 */     this.autoUpdate = autoUpdate;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public Animation(Image[] frames, int duration, boolean autoUpdate)
/*  47:    */   {
/*  48: 91 */     for (int i = 0; i < frames.length; i++) {
/*  49: 92 */       addFrame(frames[i], duration);
/*  50:    */     }
/*  51: 94 */     this.currentFrame = 0;
/*  52: 95 */     this.autoUpdate = autoUpdate;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public Animation(Image[] frames, int[] durations, boolean autoUpdate)
/*  56:    */   {
/*  57:107 */     this.autoUpdate = autoUpdate;
/*  58:108 */     if (frames.length != durations.length) {
/*  59:109 */       throw new RuntimeException("There must be one duration per frame");
/*  60:    */     }
/*  61:112 */     for (int i = 0; i < frames.length; i++) {
/*  62:113 */       addFrame(frames[i], durations[i]);
/*  63:    */     }
/*  64:115 */     this.currentFrame = 0;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public Animation(SpriteSheet frames, int duration)
/*  68:    */   {
/*  69:127 */     this(frames, 0, 0, frames.getHorizontalCount() - 1, frames.getVerticalCount() - 1, true, duration, true);
/*  70:    */   }
/*  71:    */   
/*  72:    */   public Animation(SpriteSheet frames, int x1, int y1, int x2, int y2, boolean horizontalScan, int duration, boolean autoUpdate)
/*  73:    */   {
/*  74:144 */     this.autoUpdate = autoUpdate;
/*  75:146 */     if (!horizontalScan) {
/*  76:147 */       for (int x = x1; x <= x2; x++) {
/*  77:148 */         for (int y = y1; y <= y2; y++) {
/*  78:149 */           addFrame(frames.getSprite(x, y), duration);
/*  79:    */         }
/*  80:    */       }
/*  81:    */     } else {
/*  82:153 */       for (int y = y1; y <= y2; y++) {
/*  83:154 */         for (int x = x1; x <= x2; x++) {
/*  84:155 */           addFrame(frames.getSprite(x, y), duration);
/*  85:    */         }
/*  86:    */       }
/*  87:    */     }
/*  88:    */   }
/*  89:    */   
/*  90:    */   public Animation(SpriteSheet ss, int[] frames, int[] duration)
/*  91:    */   {
/*  92:168 */     this.spriteSheet = ss;
/*  93:169 */     int x = -1;
/*  94:170 */     int y = -1;
/*  95:172 */     for (int i = 0; i < frames.length / 2; i++)
/*  96:    */     {
/*  97:173 */       x = frames[(i * 2)];
/*  98:174 */       y = frames[(i * 2 + 1)];
/*  99:175 */       addFrame(duration[i], x, y);
/* 100:    */     }
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void addFrame(int duration, int x, int y)
/* 104:    */   {
/* 105:186 */     if (duration == 0)
/* 106:    */     {
/* 107:187 */       Log.error("Invalid duration: " + duration);
/* 108:188 */       throw new RuntimeException("Invalid duration: " + duration);
/* 109:    */     }
/* 110:191 */     if (this.frames.isEmpty()) {
/* 111:192 */       this.nextChange = ((int)(duration / this.speed));
/* 112:    */     }
/* 113:195 */     this.frames.add(new Frame(duration, x, y));
/* 114:196 */     this.currentFrame = 0;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void setAutoUpdate(boolean auto)
/* 118:    */   {
/* 119:207 */     this.autoUpdate = auto;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void setPingPong(boolean pingPong)
/* 123:    */   {
/* 124:216 */     this.pingPong = pingPong;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public boolean isStopped()
/* 128:    */   {
/* 129:226 */     return this.stopped;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void setSpeed(float spd)
/* 133:    */   {
/* 134:235 */     if (spd > 0.0F)
/* 135:    */     {
/* 136:237 */       this.nextChange = (((float)this.nextChange * this.speed / spd));
/* 137:    */       
/* 138:239 */       this.speed = spd;
/* 139:    */     }
/* 140:    */   }
/* 141:    */   
/* 142:    */   public float getSpeed()
/* 143:    */   {
/* 144:249 */     return this.speed;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void stop()
/* 148:    */   {
/* 149:257 */     if (this.frames.size() == 0) {
/* 150:258 */       return;
/* 151:    */     }
/* 152:260 */     this.timeLeft = this.nextChange;
/* 153:261 */     this.stopped = true;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public void start()
/* 157:    */   {
/* 158:268 */     if (!this.stopped) {
/* 159:269 */       return;
/* 160:    */     }
/* 161:271 */     if (this.frames.size() == 0) {
/* 162:272 */       return;
/* 163:    */     }
/* 164:274 */     this.stopped = false;
/* 165:275 */     this.nextChange = this.timeLeft;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public void restart()
/* 169:    */   {
/* 170:282 */     if (this.frames.size() == 0) {
/* 171:283 */       return;
/* 172:    */     }
/* 173:285 */     this.stopped = false;
/* 174:286 */     this.currentFrame = 0;
/* 175:287 */     this.nextChange = ((int)(((Frame)this.frames.get(0)).duration / this.speed));
/* 176:288 */     this.firstUpdate = true;
/* 177:289 */     this.lastUpdate = 0L;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public void addFrame(Image frame, int duration)
/* 181:    */   {
/* 182:299 */     if (duration == 0)
/* 183:    */     {
/* 184:300 */       Log.error("Invalid duration: " + duration);
/* 185:301 */       throw new RuntimeException("Invalid duration: " + duration);
/* 186:    */     }
/* 187:304 */     if (this.frames.isEmpty()) {
/* 188:305 */       this.nextChange = ((int)(duration / this.speed));
/* 189:    */     }
/* 190:308 */     this.frames.add(new Frame(frame, duration));
/* 191:309 */     this.currentFrame = 0;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public void draw()
/* 195:    */   {
/* 196:316 */     draw(0.0F, 0.0F);
/* 197:    */   }
/* 198:    */   
/* 199:    */   public void draw(float x, float y)
/* 200:    */   {
/* 201:326 */     draw(x, y, getWidth(), getHeight());
/* 202:    */   }
/* 203:    */   
/* 204:    */   public void draw(float x, float y, Color filter)
/* 205:    */   {
/* 206:337 */     draw(x, y, getWidth(), getHeight(), filter);
/* 207:    */   }
/* 208:    */   
/* 209:    */   public void draw(float x, float y, float width, float height)
/* 210:    */   {
/* 211:349 */     draw(x, y, width, height, Color.white);
/* 212:    */   }
/* 213:    */   
/* 214:    */   public void draw(float x, float y, float width, float height, Color col)
/* 215:    */   {
/* 216:362 */     if (this.frames.size() == 0) {
/* 217:363 */       return;
/* 218:    */     }
/* 219:366 */     if (this.autoUpdate)
/* 220:    */     {
/* 221:367 */       long now = getTime();
/* 222:368 */       long delta = now - this.lastUpdate;
/* 223:369 */       if (this.firstUpdate)
/* 224:    */       {
/* 225:370 */         delta = 0L;
/* 226:371 */         this.firstUpdate = false;
/* 227:    */       }
/* 228:373 */       this.lastUpdate = now;
/* 229:374 */       nextFrame(delta);
/* 230:    */     }
/* 231:377 */     Frame frame = (Frame)this.frames.get(this.currentFrame);
/* 232:378 */     frame.image.draw(x, y, width, height, col);
/* 233:    */   }
/* 234:    */   
/* 235:    */   public void renderInUse(int x, int y)
/* 236:    */   {
/* 237:387 */     if (this.frames.size() == 0) {
/* 238:388 */       return;
/* 239:    */     }
/* 240:391 */     if (this.autoUpdate)
/* 241:    */     {
/* 242:392 */       long now = getTime();
/* 243:393 */       long delta = now - this.lastUpdate;
/* 244:394 */       if (this.firstUpdate)
/* 245:    */       {
/* 246:395 */         delta = 0L;
/* 247:396 */         this.firstUpdate = false;
/* 248:    */       }
/* 249:398 */       this.lastUpdate = now;
/* 250:399 */       nextFrame(delta);
/* 251:    */     }
/* 252:402 */     Frame frame = (Frame)this.frames.get(this.currentFrame);
/* 253:403 */     this.spriteSheet.renderInUse(x, y, frame.x, frame.y);
/* 254:    */   }
/* 255:    */   
/* 256:    */   public int getWidth()
/* 257:    */   {
/* 258:412 */     return ((Frame)this.frames.get(this.currentFrame)).image.getWidth();
/* 259:    */   }
/* 260:    */   
/* 261:    */   public int getHeight()
/* 262:    */   {
/* 263:421 */     return ((Frame)this.frames.get(this.currentFrame)).image.getHeight();
/* 264:    */   }
/* 265:    */   
/* 266:    */   public void drawFlash(float x, float y, float width, float height)
/* 267:    */   {
/* 268:433 */     drawFlash(x, y, width, height, Color.white);
/* 269:    */   }
/* 270:    */   
/* 271:    */   public void drawFlash(float x, float y, float width, float height, Color col)
/* 272:    */   {
/* 273:446 */     if (this.frames.size() == 0) {
/* 274:447 */       return;
/* 275:    */     }
/* 276:450 */     if (this.autoUpdate)
/* 277:    */     {
/* 278:451 */       long now = getTime();
/* 279:452 */       long delta = now - this.lastUpdate;
/* 280:453 */       if (this.firstUpdate)
/* 281:    */       {
/* 282:454 */         delta = 0L;
/* 283:455 */         this.firstUpdate = false;
/* 284:    */       }
/* 285:457 */       this.lastUpdate = now;
/* 286:458 */       nextFrame(delta);
/* 287:    */     }
/* 288:461 */     Frame frame = (Frame)this.frames.get(this.currentFrame);
/* 289:462 */     frame.image.drawFlash(x, y, width, height, col);
/* 290:    */   }
/* 291:    */   
/* 292:    */   /**
/* 293:    */    * @deprecated
/* 294:    */    */
/* 295:    */   public void updateNoDraw()
/* 296:    */   {
/* 297:472 */     if (this.autoUpdate)
/* 298:    */     {
/* 299:473 */       long now = getTime();
/* 300:474 */       long delta = now - this.lastUpdate;
/* 301:475 */       if (this.firstUpdate)
/* 302:    */       {
/* 303:476 */         delta = 0L;
/* 304:477 */         this.firstUpdate = false;
/* 305:    */       }
/* 306:479 */       this.lastUpdate = now;
/* 307:480 */       nextFrame(delta);
/* 308:    */     }
/* 309:    */   }
/* 310:    */   
/* 311:    */   public void update(long delta)
/* 312:    */   {
/* 313:492 */     nextFrame(delta);
/* 314:    */   }
/* 315:    */   
/* 316:    */   public int getFrame()
/* 317:    */   {
/* 318:501 */     return this.currentFrame;
/* 319:    */   }
/* 320:    */   
/* 321:    */   public void setCurrentFrame(int index)
/* 322:    */   {
/* 323:510 */     this.currentFrame = index;
/* 324:    */   }
/* 325:    */   
/* 326:    */   public Image getImage(int index)
/* 327:    */   {
/* 328:520 */     Frame frame = (Frame)this.frames.get(index);
/* 329:521 */     return frame.image;
/* 330:    */   }
/* 331:    */   
/* 332:    */   public int getFrameCount()
/* 333:    */   {
/* 334:530 */     return this.frames.size();
/* 335:    */   }
/* 336:    */   
/* 337:    */   public Image getCurrentFrame()
/* 338:    */   {
/* 339:539 */     Frame frame = (Frame)this.frames.get(this.currentFrame);
/* 340:540 */     return frame.image;
/* 341:    */   }
/* 342:    */   
/* 343:    */   private void nextFrame(long delta)
/* 344:    */   {
/* 345:549 */     if (this.stopped) {
/* 346:550 */       return;
/* 347:    */     }
/* 348:552 */     if (this.frames.size() == 0) {
/* 349:553 */       return;
/* 350:    */     }
/* 351:556 */     this.nextChange -= delta;
/* 352:558 */     while ((this.nextChange < 0L) && (!this.stopped))
/* 353:    */     {
/* 354:559 */       if (this.currentFrame == this.stopAt)
/* 355:    */       {
/* 356:560 */         this.stopped = true;
/* 357:561 */         break;
/* 358:    */       }
/* 359:563 */       if ((this.currentFrame == this.frames.size() - 1) && (!this.loop) && (!this.pingPong))
/* 360:    */       {
/* 361:564 */         this.stopped = true;
/* 362:565 */         break;
/* 363:    */       }
/* 364:567 */       this.currentFrame = ((this.currentFrame + this.direction) % this.frames.size());
/* 365:569 */       if (this.pingPong) {
/* 366:570 */         if (this.currentFrame <= 0)
/* 367:    */         {
/* 368:571 */           this.currentFrame = 0;
/* 369:572 */           this.direction = 1;
/* 370:573 */           if (!this.loop)
/* 371:    */           {
/* 372:574 */             this.stopped = true;
/* 373:575 */             break;
/* 374:    */           }
/* 375:    */         }
/* 376:578 */         else if (this.currentFrame >= this.frames.size() - 1)
/* 377:    */         {
/* 378:579 */           this.currentFrame = (this.frames.size() - 1);
/* 379:580 */           this.direction = -1;
/* 380:    */         }
/* 381:    */       }
/* 382:583 */       int realDuration = (int)(((Frame)this.frames.get(this.currentFrame)).duration / this.speed);
/* 383:584 */       this.nextChange += realDuration;
/* 384:    */     }
/* 385:    */   }
/* 386:    */   
/* 387:    */   public void setLooping(boolean loop)
/* 388:    */   {
/* 389:594 */     this.loop = loop;
/* 390:    */   }
/* 391:    */   
/* 392:    */   private long getTime()
/* 393:    */   {
/* 394:603 */     return Sys.getTime() * 1000L / Sys.getTimerResolution();
/* 395:    */   }
/* 396:    */   
/* 397:    */   public void stopAt(int frameIndex)
/* 398:    */   {
/* 399:613 */     this.stopAt = frameIndex;
/* 400:    */   }
/* 401:    */   
/* 402:    */   public int getDuration(int index)
/* 403:    */   {
/* 404:623 */     return ((Frame)this.frames.get(index)).duration;
/* 405:    */   }
/* 406:    */   
/* 407:    */   public void setDuration(int index, int duration)
/* 408:    */   {
/* 409:633 */     ((Frame)this.frames.get(index)).duration = duration;
/* 410:    */   }
/* 411:    */   
/* 412:    */   public int[] getDurations()
/* 413:    */   {
/* 414:642 */     int[] durations = new int[this.frames.size()];
/* 415:643 */     for (int i = 0; i < this.frames.size(); i++) {
/* 416:644 */       durations[i] = getDuration(i);
/* 417:    */     }
/* 418:647 */     return durations;
/* 419:    */   }
/* 420:    */   
/* 421:    */   public String toString()
/* 422:    */   {
/* 423:655 */     String res = "[Animation (" + this.frames.size() + ") ";
/* 424:656 */     for (int i = 0; i < this.frames.size(); i++)
/* 425:    */     {
/* 426:657 */       Frame frame = (Frame)this.frames.get(i);
/* 427:658 */       res = res + frame.duration + ",";
/* 428:    */     }
/* 429:661 */     res = res + "]";
/* 430:662 */     return res;
/* 431:    */   }
/* 432:    */   
/* 433:    */   public Animation copy()
/* 434:    */   {
/* 435:672 */     Animation copy = new Animation();
/* 436:    */     
/* 437:674 */     copy.spriteSheet = this.spriteSheet;
/* 438:675 */     copy.frames = this.frames;
/* 439:676 */     copy.autoUpdate = this.autoUpdate;
/* 440:677 */     copy.direction = this.direction;
/* 441:678 */     copy.loop = this.loop;
/* 442:679 */     copy.pingPong = this.pingPong;
/* 443:680 */     copy.speed = this.speed;
/* 444:    */     
/* 445:682 */     return copy;
/* 446:    */   }
/* 447:    */   
/* 448:    */   private class Frame
/* 449:    */   {
/* 450:    */     public Image image;
/* 451:    */     public int duration;
/* 452:696 */     public int x = -1;
/* 453:698 */     public int y = -1;
/* 454:    */     
/* 455:    */     public Frame(Image image, int duration)
/* 456:    */     {
/* 457:707 */       this.image = image;
/* 458:708 */       this.duration = duration;
/* 459:    */     }
/* 460:    */     
/* 461:    */     public Frame(int duration, int x, int y)
/* 462:    */     {
/* 463:718 */       this.image = Animation.this.spriteSheet.getSubImage(x, y);
/* 464:719 */       this.duration = duration;
/* 465:720 */       this.x = x;
/* 466:721 */       this.y = y;
/* 467:    */     }
/* 468:    */   }
/* 469:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.Animation
 * JD-Core Version:    0.7.0.1
 */