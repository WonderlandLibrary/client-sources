/*     */ package org.newdawn.slick.particles;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import org.newdawn.slick.Color;
/*     */ import org.newdawn.slick.Image;
/*     */ import org.newdawn.slick.SlickException;
/*     */ import org.newdawn.slick.geom.Vector2f;
/*     */ import org.newdawn.slick.util.FastTrig;
/*     */ import org.newdawn.slick.util.Log;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ConfigurableEmitter
/*     */   implements ParticleEmitter
/*     */ {
/*  25 */   private static String relativePath = "";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setRelativePath(String path) {
/*  34 */     if (!path.endsWith("/")) {
/*  35 */       path = path + "/";
/*     */     }
/*  37 */     relativePath = path;
/*     */   }
/*     */ 
/*     */   
/*  41 */   public Range spawnInterval = new Range(100.0F, 100.0F);
/*     */   
/*  43 */   public Range spawnCount = new Range(5.0F, 5.0F);
/*     */   
/*  45 */   public Range initialLife = new Range(1000.0F, 1000.0F);
/*     */   
/*  47 */   public Range initialSize = new Range(10.0F, 10.0F);
/*     */   
/*  49 */   public Range xOffset = new Range(0.0F, 0.0F);
/*     */   
/*  51 */   public Range yOffset = new Range(0.0F, 0.0F);
/*     */   
/*  53 */   public RandomValue spread = new RandomValue(360.0F);
/*     */   
/*  55 */   public SimpleValue angularOffset = new SimpleValue(0.0F);
/*     */   
/*  57 */   public Range initialDistance = new Range(0.0F, 0.0F);
/*     */   
/*  59 */   public Range speed = new Range(50.0F, 50.0F);
/*     */   
/*  61 */   public SimpleValue growthFactor = new SimpleValue(0.0F);
/*     */   
/*  63 */   public SimpleValue gravityFactor = new SimpleValue(0.0F);
/*     */   
/*  65 */   public SimpleValue windFactor = new SimpleValue(0.0F);
/*     */   
/*  67 */   public Range length = new Range(1000.0F, 1000.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  73 */   public ArrayList colors = new ArrayList();
/*     */   
/*  75 */   public SimpleValue startAlpha = new SimpleValue(255.0F);
/*     */   
/*  77 */   public SimpleValue endAlpha = new SimpleValue(0.0F);
/*     */ 
/*     */   
/*     */   public LinearInterpolator alpha;
/*     */ 
/*     */   
/*     */   public LinearInterpolator size;
/*     */   
/*     */   public LinearInterpolator velocity;
/*     */   
/*     */   public LinearInterpolator scaleY;
/*     */   
/*  89 */   public Range emitCount = new Range(1000.0F, 1000.0F);
/*     */   
/*  91 */   public int usePoints = 1;
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean useOriented = false;
/*     */ 
/*     */   
/*     */   public boolean useAdditive = false;
/*     */ 
/*     */   
/*     */   public String name;
/*     */ 
/*     */   
/* 104 */   public String imageName = "";
/*     */ 
/*     */   
/*     */   private Image image;
/*     */   
/*     */   private boolean updateImage;
/*     */   
/*     */   private boolean enabled = true;
/*     */   
/*     */   private float x;
/*     */   
/*     */   private float y;
/*     */   
/* 117 */   private int nextSpawn = 0;
/*     */ 
/*     */   
/*     */   private int timeout;
/*     */ 
/*     */   
/*     */   private int particleCount;
/*     */ 
/*     */   
/*     */   private ParticleSystem engine;
/*     */ 
/*     */   
/*     */   private int leftToEmit;
/*     */ 
/*     */   
/*     */   protected boolean wrapUp = false;
/*     */ 
/*     */   
/*     */   protected boolean completed = false;
/*     */ 
/*     */   
/*     */   protected boolean adjust;
/*     */   
/*     */   protected float adjustx;
/*     */   
/*     */   protected float adjusty;
/*     */ 
/*     */   
/*     */   public ConfigurableEmitter(String name) {
/* 146 */     this.name = name;
/* 147 */     this.leftToEmit = (int)this.emitCount.random();
/* 148 */     this.timeout = (int)this.length.random();
/*     */     
/* 150 */     this.colors.add(new ColorRecord(0.0F, Color.white));
/* 151 */     this.colors.add(new ColorRecord(1.0F, Color.red));
/*     */     
/* 153 */     ArrayList<Vector2f> curve = new ArrayList();
/* 154 */     curve.add(new Vector2f(0.0F, 0.0F));
/* 155 */     curve.add(new Vector2f(1.0F, 255.0F));
/* 156 */     this.alpha = new LinearInterpolator(curve, 0, 255);
/*     */     
/* 158 */     curve = new ArrayList<Vector2f>();
/* 159 */     curve.add(new Vector2f(0.0F, 0.0F));
/* 160 */     curve.add(new Vector2f(1.0F, 255.0F));
/* 161 */     this.size = new LinearInterpolator(curve, 0, 255);
/*     */     
/* 163 */     curve = new ArrayList<Vector2f>();
/* 164 */     curve.add(new Vector2f(0.0F, 0.0F));
/* 165 */     curve.add(new Vector2f(1.0F, 1.0F));
/* 166 */     this.velocity = new LinearInterpolator(curve, 0, 1);
/*     */     
/* 168 */     curve = new ArrayList<Vector2f>();
/* 169 */     curve.add(new Vector2f(0.0F, 0.0F));
/* 170 */     curve.add(new Vector2f(1.0F, 1.0F));
/* 171 */     this.scaleY = new LinearInterpolator(curve, 0, 1);
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
/*     */   public void setImageName(String imageName) {
/* 184 */     if (imageName.length() == 0) {
/* 185 */       imageName = null;
/*     */     }
/*     */     
/* 188 */     this.imageName = imageName;
/* 189 */     if (imageName == null) {
/* 190 */       this.image = null;
/*     */     } else {
/* 192 */       this.updateImage = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getImageName() {
/* 202 */     return this.imageName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 209 */     return "[" + this.name + "]";
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
/*     */   public void setPosition(float x, float y) {
/* 221 */     setPosition(x, y, true);
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
/*     */   public void setPosition(float x, float y, boolean moveParticles) {
/* 235 */     if (moveParticles) {
/* 236 */       this.adjust = true;
/* 237 */       this.adjustx -= this.x - x;
/* 238 */       this.adjusty -= this.y - y;
/*     */     } 
/* 240 */     this.x = x;
/* 241 */     this.y = y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getX() {
/* 250 */     return this.x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getY() {
/* 259 */     return this.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEnabled() {
/* 266 */     return this.enabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEnabled(boolean enabled) {
/* 273 */     this.enabled = enabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(ParticleSystem system, int delta) {
/* 281 */     this.engine = system;
/*     */     
/* 283 */     if (!this.adjust) {
/* 284 */       this.adjustx = 0.0F;
/* 285 */       this.adjusty = 0.0F;
/*     */     } else {
/* 287 */       this.adjust = false;
/*     */     } 
/*     */     
/* 290 */     if (this.updateImage) {
/* 291 */       this.updateImage = false;
/*     */       try {
/* 293 */         this.image = new Image(relativePath + this.imageName);
/* 294 */       } catch (SlickException e) {
/* 295 */         this.image = null;
/* 296 */         Log.error((Throwable)e);
/*     */       } 
/*     */     } 
/*     */     
/* 300 */     if (this.wrapUp || (this.length.isEnabled() && this.timeout < 0) || (this.emitCount.isEnabled() && this.leftToEmit <= 0))
/*     */     {
/*     */       
/* 303 */       if (this.particleCount == 0) {
/* 304 */         this.completed = true;
/*     */       }
/*     */     }
/* 307 */     this.particleCount = 0;
/*     */     
/* 309 */     if (this.wrapUp) {
/*     */       return;
/*     */     }
/*     */     
/* 313 */     if (this.length.isEnabled()) {
/* 314 */       if (this.timeout < 0) {
/*     */         return;
/*     */       }
/* 317 */       this.timeout -= delta;
/*     */     } 
/* 319 */     if (this.emitCount.isEnabled() && 
/* 320 */       this.leftToEmit <= 0) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 325 */     this.nextSpawn -= delta;
/* 326 */     if (this.nextSpawn < 0) {
/* 327 */       this.nextSpawn = (int)this.spawnInterval.random();
/* 328 */       int count = (int)this.spawnCount.random();
/*     */       
/* 330 */       for (int i = 0; i < count; i++) {
/* 331 */         Particle p = system.getNewParticle(this, this.initialLife.random());
/* 332 */         p.setSize(this.initialSize.random());
/* 333 */         p.setPosition(this.x + this.xOffset.random(), this.y + this.yOffset.random());
/* 334 */         p.setVelocity(0.0F, 0.0F, 0.0F);
/*     */         
/* 336 */         float dist = this.initialDistance.random();
/* 337 */         float power = this.speed.random();
/* 338 */         if (dist != 0.0F || power != 0.0F) {
/* 339 */           float s = this.spread.getValue(0.0F);
/* 340 */           float ang = s + this.angularOffset.getValue(0.0F) - this.spread.getValue() / 2.0F - 90.0F;
/*     */           
/* 342 */           float xa = (float)FastTrig.cos(Math.toRadians(ang)) * dist;
/* 343 */           float ya = (float)FastTrig.sin(Math.toRadians(ang)) * dist;
/* 344 */           p.adjustPosition(xa, ya);
/*     */           
/* 346 */           float xv = (float)FastTrig.cos(Math.toRadians(ang));
/* 347 */           float yv = (float)FastTrig.sin(Math.toRadians(ang));
/* 348 */           p.setVelocity(xv, yv, power * 0.001F);
/*     */         } 
/*     */         
/* 351 */         if (this.image != null) {
/* 352 */           p.setImage(this.image);
/*     */         }
/*     */         
/* 355 */         ColorRecord start = this.colors.get(0);
/* 356 */         p.setColor(start.col.r, start.col.g, start.col.b, this.startAlpha.getValue(0.0F) / 255.0F);
/*     */         
/* 358 */         p.setUsePoint(this.usePoints);
/* 359 */         p.setOriented(this.useOriented);
/*     */         
/* 361 */         if (this.emitCount.isEnabled()) {
/* 362 */           this.leftToEmit--;
/* 363 */           if (this.leftToEmit <= 0) {
/*     */             break;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateParticle(Particle particle, int delta) {
/* 376 */     this.particleCount++;
/*     */ 
/*     */     
/* 379 */     particle.x += this.adjustx;
/* 380 */     particle.y += this.adjusty;
/*     */     
/* 382 */     particle.adjustVelocity(this.windFactor.getValue(0.0F) * 5.0E-5F * delta, this.gravityFactor.getValue(0.0F) * 5.0E-5F * delta);
/*     */ 
/*     */     
/* 385 */     float offset = particle.getLife() / particle.getOriginalLife();
/* 386 */     float inv = 1.0F - offset;
/* 387 */     float colOffset = 0.0F;
/* 388 */     float colInv = 1.0F;
/*     */     
/* 390 */     Color startColor = null;
/* 391 */     Color endColor = null;
/* 392 */     for (int i = 0; i < this.colors.size() - 1; i++) {
/* 393 */       ColorRecord rec1 = this.colors.get(i);
/* 394 */       ColorRecord rec2 = this.colors.get(i + 1);
/*     */       
/* 396 */       if (inv >= rec1.pos && inv <= rec2.pos) {
/* 397 */         startColor = rec1.col;
/* 398 */         endColor = rec2.col;
/*     */         
/* 400 */         float step = rec2.pos - rec1.pos;
/* 401 */         colOffset = inv - rec1.pos;
/* 402 */         colOffset /= step;
/* 403 */         colOffset = 1.0F - colOffset;
/* 404 */         colInv = 1.0F - colOffset;
/*     */       } 
/*     */     } 
/*     */     
/* 408 */     if (startColor != null) {
/* 409 */       float a, r = startColor.r * colOffset + endColor.r * colInv;
/* 410 */       float g = startColor.g * colOffset + endColor.g * colInv;
/* 411 */       float b = startColor.b * colOffset + endColor.b * colInv;
/*     */ 
/*     */       
/* 414 */       if (this.alpha.isActive()) {
/* 415 */         a = this.alpha.getValue(inv) / 255.0F;
/*     */       } else {
/* 417 */         a = this.startAlpha.getValue(0.0F) / 255.0F * offset + this.endAlpha.getValue(0.0F) / 255.0F * inv;
/*     */       } 
/*     */       
/* 420 */       particle.setColor(r, g, b, a);
/*     */     } 
/*     */     
/* 423 */     if (this.size.isActive()) {
/* 424 */       float s = this.size.getValue(inv);
/* 425 */       particle.setSize(s);
/*     */     } else {
/* 427 */       particle.adjustSize(delta * this.growthFactor.getValue(0.0F) * 0.001F);
/*     */     } 
/*     */     
/* 430 */     if (this.velocity.isActive()) {
/* 431 */       particle.setSpeed(this.velocity.getValue(inv));
/*     */     }
/*     */     
/* 434 */     if (this.scaleY.isActive()) {
/* 435 */       particle.setScaleY(this.scaleY.getValue(inv));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean completed() {
/* 445 */     if (this.engine == null) {
/* 446 */       return false;
/*     */     }
/*     */     
/* 449 */     if (this.length.isEnabled()) {
/* 450 */       if (this.timeout > 0) {
/* 451 */         return false;
/*     */       }
/* 453 */       return this.completed;
/*     */     } 
/* 455 */     if (this.emitCount.isEnabled()) {
/* 456 */       if (this.leftToEmit > 0) {
/* 457 */         return false;
/*     */       }
/* 459 */       return this.completed;
/*     */     } 
/*     */     
/* 462 */     if (this.wrapUp) {
/* 463 */       return this.completed;
/*     */     }
/*     */     
/* 466 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void replay() {
/* 473 */     reset();
/* 474 */     this.nextSpawn = 0;
/* 475 */     this.leftToEmit = (int)this.emitCount.random();
/* 476 */     this.timeout = (int)this.length.random();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset() {
/* 483 */     this.completed = false;
/* 484 */     if (this.engine != null) {
/* 485 */       this.engine.releaseAll(this);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void replayCheck() {
/* 493 */     if (completed() && 
/* 494 */       this.engine != null && 
/* 495 */       this.engine.getParticleCount() == 0) {
/* 496 */       replay();
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
/*     */   public ConfigurableEmitter duplicate() {
/* 508 */     ConfigurableEmitter theCopy = null;
/*     */     try {
/* 510 */       ByteArrayOutputStream bout = new ByteArrayOutputStream();
/* 511 */       ParticleIO.saveEmitter(bout, this);
/* 512 */       ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
/* 513 */       theCopy = ParticleIO.loadEmitter(bin);
/* 514 */     } catch (IOException e) {
/* 515 */       Log.error("Slick: ConfigurableEmitter.duplicate(): caught exception " + e.toString());
/* 516 */       return null;
/*     */     } 
/* 518 */     return theCopy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static interface Value
/*     */   {
/*     */     float getValue(float param1Float);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public class SimpleValue
/*     */     implements Value
/*     */   {
/*     */     private float value;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private float next;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private SimpleValue(float value) {
/* 554 */       this.value = value;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public float getValue(float time) {
/* 563 */       return this.value;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setValue(float value) {
/* 573 */       this.value = value;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public class RandomValue
/*     */     implements Value
/*     */   {
/*     */     private float value;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private RandomValue(float value) {
/* 593 */       this.value = value;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public float getValue(float time) {
/* 602 */       return (float)(Math.random() * this.value);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setValue(float value) {
/* 612 */       this.value = value;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public float getValue() {
/* 621 */       return this.value;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public class LinearInterpolator
/*     */     implements Value
/*     */   {
/*     */     private ArrayList curve;
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean active;
/*     */ 
/*     */ 
/*     */     
/*     */     private int min;
/*     */ 
/*     */ 
/*     */     
/*     */     private int max;
/*     */ 
/*     */ 
/*     */     
/*     */     public LinearInterpolator(ArrayList curve, int min, int max) {
/* 648 */       this.curve = curve;
/* 649 */       this.min = min;
/* 650 */       this.max = max;
/* 651 */       this.active = false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setCurve(ArrayList curve) {
/* 660 */       this.curve = curve;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ArrayList getCurve() {
/* 669 */       return this.curve;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public float getValue(float t) {
/* 680 */       Vector2f p0 = this.curve.get(0);
/* 681 */       for (int i = 1; i < this.curve.size(); i++) {
/* 682 */         Vector2f p1 = this.curve.get(i);
/*     */         
/* 684 */         if (t >= p0.getX() && t <= p1.getX()) {
/*     */           
/* 686 */           float st = (t - p0.getX()) / (p1.getX() - p0.getX());
/*     */           
/* 688 */           float r = p0.getY() + st * (p1.getY() - p0.getY());
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 693 */           return r;
/*     */         } 
/*     */         
/* 696 */         p0 = p1;
/*     */       } 
/* 698 */       return 0.0F;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isActive() {
/* 707 */       return this.active;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setActive(boolean active) {
/* 716 */       this.active = active;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getMax() {
/* 725 */       return this.max;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setMax(int max) {
/* 734 */       this.max = max;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getMin() {
/* 743 */       return this.min;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setMin(int min) {
/* 752 */       this.min = min;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public class ColorRecord
/*     */   {
/*     */     public float pos;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Color col;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ColorRecord(float pos, Color col) {
/* 776 */       this.pos = pos;
/* 777 */       this.col = col;
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
/*     */   
/*     */   public void addColorPoint(float pos, Color col) {
/* 790 */     this.colors.add(new ColorRecord(pos, col));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public class Range
/*     */   {
/*     */     private float max;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private float min;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean enabled = false;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Range(float min, float max) {
/* 815 */       this.min = min;
/* 816 */       this.max = max;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public float random() {
/* 825 */       return (float)(this.min + Math.random() * (this.max - this.min));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isEnabled() {
/* 834 */       return this.enabled;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setEnabled(boolean enabled) {
/* 844 */       this.enabled = enabled;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public float getMax() {
/* 853 */       return this.max;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setMax(float max) {
/* 863 */       this.max = max;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public float getMin() {
/* 872 */       return this.min;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setMin(float min) {
/* 882 */       this.min = min;
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean useAdditive() {
/* 887 */     return this.useAdditive;
/*     */   }
/*     */   
/*     */   public boolean isOriented() {
/* 891 */     return this.useOriented;
/*     */   }
/*     */   
/*     */   public boolean usePoints(ParticleSystem system) {
/* 895 */     return ((this.usePoints == 1 && system.usePoints()) || this.usePoints == 2);
/*     */   }
/*     */ 
/*     */   
/*     */   public Image getImage() {
/* 900 */     return this.image;
/*     */   }
/*     */   
/*     */   public void wrapUp() {
/* 904 */     this.wrapUp = true;
/*     */   }
/*     */   
/*     */   public void resetState() {
/* 908 */     this.wrapUp = false;
/* 909 */     replay();
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\particles\ConfigurableEmitter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */