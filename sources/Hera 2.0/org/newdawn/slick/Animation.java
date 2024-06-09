/*     */ package org.newdawn.slick;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import org.lwjgl.Sys;
/*     */ import org.newdawn.slick.util.Log;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Animation
/*     */   implements Renderable
/*     */ {
/*  16 */   private ArrayList frames = new ArrayList();
/*     */   
/*  18 */   private int currentFrame = -1;
/*     */   
/*  20 */   private long nextChange = 0L;
/*     */   
/*     */   private boolean stopped = false;
/*     */   
/*     */   private long timeLeft;
/*     */   
/*  26 */   private float speed = 1.0F;
/*     */   
/*  28 */   private int stopAt = -2;
/*     */   
/*     */   private long lastUpdate;
/*     */   
/*     */   private boolean firstUpdate = true;
/*     */   
/*     */   private boolean autoUpdate = true;
/*     */   
/*  36 */   private int direction = 1;
/*     */   
/*     */   private boolean pingPong;
/*     */   
/*     */   private boolean loop = true;
/*     */   
/*  42 */   private SpriteSheet spriteSheet = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Animation() {
/*  48 */     this(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Animation(Image[] frames, int duration) {
/*  58 */     this(frames, duration, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Animation(Image[] frames, int[] durations) {
/*  68 */     this(frames, durations, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Animation(boolean autoUpdate) {
/*  78 */     this.currentFrame = 0;
/*  79 */     this.autoUpdate = autoUpdate;
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
/*     */   public Animation(Image[] frames, int duration, boolean autoUpdate) {
/*  91 */     for (int i = 0; i < frames.length; i++) {
/*  92 */       addFrame(frames[i], duration);
/*     */     }
/*  94 */     this.currentFrame = 0;
/*  95 */     this.autoUpdate = autoUpdate;
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
/*     */   public Animation(Image[] frames, int[] durations, boolean autoUpdate) {
/* 107 */     this.autoUpdate = autoUpdate;
/* 108 */     if (frames.length != durations.length) {
/* 109 */       throw new RuntimeException("There must be one duration per frame");
/*     */     }
/*     */     
/* 112 */     for (int i = 0; i < frames.length; i++) {
/* 113 */       addFrame(frames[i], durations[i]);
/*     */     }
/* 115 */     this.currentFrame = 0;
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
/*     */   public Animation(SpriteSheet frames, int duration) {
/* 127 */     this(frames, 0, 0, frames.getHorizontalCount() - 1, frames.getVerticalCount() - 1, true, duration, true);
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
/*     */   public Animation(SpriteSheet frames, int x1, int y1, int x2, int y2, boolean horizontalScan, int duration, boolean autoUpdate) {
/* 144 */     this.autoUpdate = autoUpdate;
/*     */     
/* 146 */     if (!horizontalScan) {
/* 147 */       for (int x = x1; x <= x2; x++) {
/* 148 */         for (int y = y1; y <= y2; y++) {
/* 149 */           addFrame(frames.getSprite(x, y), duration);
/*     */         }
/*     */       } 
/*     */     } else {
/* 153 */       for (int y = y1; y <= y2; y++) {
/* 154 */         for (int x = x1; x <= x2; x++) {
/* 155 */           addFrame(frames.getSprite(x, y), duration);
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
/*     */   
/*     */   public Animation(SpriteSheet ss, int[] frames, int[] duration) {
/* 168 */     this.spriteSheet = ss;
/* 169 */     int x = -1;
/* 170 */     int y = -1;
/*     */     
/* 172 */     for (int i = 0; i < frames.length / 2; i++) {
/* 173 */       x = frames[i * 2];
/* 174 */       y = frames[i * 2 + 1];
/* 175 */       addFrame(duration[i], x, y);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addFrame(int duration, int x, int y) {
/* 186 */     if (duration == 0) {
/* 187 */       Log.error("Invalid duration: " + duration);
/* 188 */       throw new RuntimeException("Invalid duration: " + duration);
/*     */     } 
/*     */     
/* 191 */     if (this.frames.isEmpty()) {
/* 192 */       this.nextChange = (int)(duration / this.speed);
/*     */     }
/*     */     
/* 195 */     this.frames.add(new Frame(duration, x, y));
/* 196 */     this.currentFrame = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAutoUpdate(boolean auto) {
/* 207 */     this.autoUpdate = auto;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPingPong(boolean pingPong) {
/* 216 */     this.pingPong = pingPong;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isStopped() {
/* 226 */     return this.stopped;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSpeed(float spd) {
/* 235 */     if (spd > 0.0F) {
/*     */       
/* 237 */       this.nextChange = (long)((float)this.nextChange * this.speed / spd);
/*     */       
/* 239 */       this.speed = spd;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getSpeed() {
/* 249 */     return this.speed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void stop() {
/* 257 */     if (this.frames.size() == 0) {
/*     */       return;
/*     */     }
/* 260 */     this.timeLeft = this.nextChange;
/* 261 */     this.stopped = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void start() {
/* 268 */     if (!this.stopped) {
/*     */       return;
/*     */     }
/* 271 */     if (this.frames.size() == 0) {
/*     */       return;
/*     */     }
/* 274 */     this.stopped = false;
/* 275 */     this.nextChange = this.timeLeft;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void restart() {
/* 282 */     if (this.frames.size() == 0) {
/*     */       return;
/*     */     }
/* 285 */     this.stopped = false;
/* 286 */     this.currentFrame = 0;
/* 287 */     this.nextChange = (int)(((Frame)this.frames.get(0)).duration / this.speed);
/* 288 */     this.firstUpdate = true;
/* 289 */     this.lastUpdate = 0L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addFrame(Image frame, int duration) {
/* 299 */     if (duration == 0) {
/* 300 */       Log.error("Invalid duration: " + duration);
/* 301 */       throw new RuntimeException("Invalid duration: " + duration);
/*     */     } 
/*     */     
/* 304 */     if (this.frames.isEmpty()) {
/* 305 */       this.nextChange = (int)(duration / this.speed);
/*     */     }
/*     */     
/* 308 */     this.frames.add(new Frame(frame, duration));
/* 309 */     this.currentFrame = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void draw() {
/* 316 */     draw(0.0F, 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void draw(float x, float y) {
/* 326 */     draw(x, y, getWidth(), getHeight());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void draw(float x, float y, Color filter) {
/* 337 */     draw(x, y, getWidth(), getHeight(), filter);
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
/*     */   public void draw(float x, float y, float width, float height) {
/* 349 */     draw(x, y, width, height, Color.white);
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
/*     */   public void draw(float x, float y, float width, float height, Color col) {
/* 362 */     if (this.frames.size() == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 366 */     if (this.autoUpdate) {
/* 367 */       long now = getTime();
/* 368 */       long delta = now - this.lastUpdate;
/* 369 */       if (this.firstUpdate) {
/* 370 */         delta = 0L;
/* 371 */         this.firstUpdate = false;
/*     */       } 
/* 373 */       this.lastUpdate = now;
/* 374 */       nextFrame(delta);
/*     */     } 
/*     */     
/* 377 */     Frame frame = this.frames.get(this.currentFrame);
/* 378 */     frame.image.draw(x, y, width, height, col);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderInUse(int x, int y) {
/* 387 */     if (this.frames.size() == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 391 */     if (this.autoUpdate) {
/* 392 */       long now = getTime();
/* 393 */       long delta = now - this.lastUpdate;
/* 394 */       if (this.firstUpdate) {
/* 395 */         delta = 0L;
/* 396 */         this.firstUpdate = false;
/*     */       } 
/* 398 */       this.lastUpdate = now;
/* 399 */       nextFrame(delta);
/*     */     } 
/*     */     
/* 402 */     Frame frame = this.frames.get(this.currentFrame);
/* 403 */     this.spriteSheet.renderInUse(x, y, frame.x, frame.y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWidth() {
/* 412 */     return ((Frame)this.frames.get(this.currentFrame)).image.getWidth();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHeight() {
/* 421 */     return ((Frame)this.frames.get(this.currentFrame)).image.getHeight();
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
/*     */   public void drawFlash(float x, float y, float width, float height) {
/* 433 */     drawFlash(x, y, width, height, Color.white);
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
/*     */   public void drawFlash(float x, float y, float width, float height, Color col) {
/* 446 */     if (this.frames.size() == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 450 */     if (this.autoUpdate) {
/* 451 */       long now = getTime();
/* 452 */       long delta = now - this.lastUpdate;
/* 453 */       if (this.firstUpdate) {
/* 454 */         delta = 0L;
/* 455 */         this.firstUpdate = false;
/*     */       } 
/* 457 */       this.lastUpdate = now;
/* 458 */       nextFrame(delta);
/*     */     } 
/*     */     
/* 461 */     Frame frame = this.frames.get(this.currentFrame);
/* 462 */     frame.image.drawFlash(x, y, width, height, col);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateNoDraw() {
/* 472 */     if (this.autoUpdate) {
/* 473 */       long now = getTime();
/* 474 */       long delta = now - this.lastUpdate;
/* 475 */       if (this.firstUpdate) {
/* 476 */         delta = 0L;
/* 477 */         this.firstUpdate = false;
/*     */       } 
/* 479 */       this.lastUpdate = now;
/* 480 */       nextFrame(delta);
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
/*     */   public void update(long delta) {
/* 492 */     nextFrame(delta);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFrame() {
/* 501 */     return this.currentFrame;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCurrentFrame(int index) {
/* 510 */     this.currentFrame = index;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Image getImage(int index) {
/* 520 */     Frame frame = this.frames.get(index);
/* 521 */     return frame.image;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFrameCount() {
/* 530 */     return this.frames.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Image getCurrentFrame() {
/* 539 */     Frame frame = this.frames.get(this.currentFrame);
/* 540 */     return frame.image;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void nextFrame(long delta) {
/* 549 */     if (this.stopped) {
/*     */       return;
/*     */     }
/* 552 */     if (this.frames.size() == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 556 */     this.nextChange -= delta;
/*     */     
/* 558 */     while (this.nextChange < 0L && !this.stopped) {
/* 559 */       if (this.currentFrame == this.stopAt) {
/* 560 */         this.stopped = true;
/*     */         break;
/*     */       } 
/* 563 */       if (this.currentFrame == this.frames.size() - 1 && !this.loop && !this.pingPong) {
/* 564 */         this.stopped = true;
/*     */         break;
/*     */       } 
/* 567 */       this.currentFrame = (this.currentFrame + this.direction) % this.frames.size();
/*     */       
/* 569 */       if (this.pingPong) {
/* 570 */         if (this.currentFrame <= 0) {
/* 571 */           this.currentFrame = 0;
/* 572 */           this.direction = 1;
/* 573 */           if (!this.loop) {
/* 574 */             this.stopped = true;
/*     */             
/*     */             break;
/*     */           } 
/* 578 */         } else if (this.currentFrame >= this.frames.size() - 1) {
/* 579 */           this.currentFrame = this.frames.size() - 1;
/* 580 */           this.direction = -1;
/*     */         } 
/*     */       }
/* 583 */       int realDuration = (int)(((Frame)this.frames.get(this.currentFrame)).duration / this.speed);
/* 584 */       this.nextChange += realDuration;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLooping(boolean loop) {
/* 594 */     this.loop = loop;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private long getTime() {
/* 603 */     return Sys.getTime() * 1000L / Sys.getTimerResolution();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void stopAt(int frameIndex) {
/* 613 */     this.stopAt = frameIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDuration(int index) {
/* 623 */     return ((Frame)this.frames.get(index)).duration;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDuration(int index, int duration) {
/* 633 */     ((Frame)this.frames.get(index)).duration = duration;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getDurations() {
/* 642 */     int[] durations = new int[this.frames.size()];
/* 643 */     for (int i = 0; i < this.frames.size(); i++) {
/* 644 */       durations[i] = getDuration(i);
/*     */     }
/*     */     
/* 647 */     return durations;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 655 */     String res = "[Animation (" + this.frames.size() + ") ";
/* 656 */     for (int i = 0; i < this.frames.size(); i++) {
/* 657 */       Frame frame = this.frames.get(i);
/* 658 */       res = res + frame.duration + ",";
/*     */     } 
/*     */     
/* 661 */     res = res + "]";
/* 662 */     return res;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Animation copy() {
/* 672 */     Animation copy = new Animation();
/*     */     
/* 674 */     copy.spriteSheet = this.spriteSheet;
/* 675 */     copy.frames = this.frames;
/* 676 */     copy.autoUpdate = this.autoUpdate;
/* 677 */     copy.direction = this.direction;
/* 678 */     copy.loop = this.loop;
/* 679 */     copy.pingPong = this.pingPong;
/* 680 */     copy.speed = this.speed;
/*     */     
/* 682 */     return copy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class Frame
/*     */   {
/*     */     public Image image;
/*     */ 
/*     */     
/*     */     public int duration;
/*     */ 
/*     */     
/* 696 */     public int x = -1;
/*     */     
/* 698 */     public int y = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Frame(Image image, int duration) {
/* 707 */       this.image = image;
/* 708 */       this.duration = duration;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Frame(int duration, int x, int y) {
/* 718 */       this.image = Animation.this.spriteSheet.getSubImage(x, y);
/* 719 */       this.duration = duration;
/* 720 */       this.x = x;
/* 721 */       this.y = y;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\Animation.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */