/*     */ package org.newdawn.slick.particles;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import org.newdawn.slick.Color;
/*     */ import org.newdawn.slick.Image;
/*     */ import org.newdawn.slick.SlickException;
/*     */ import org.newdawn.slick.opengl.TextureImpl;
/*     */ import org.newdawn.slick.opengl.renderer.Renderer;
/*     */ import org.newdawn.slick.opengl.renderer.SGL;
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
/*     */ public class ParticleSystem
/*     */ {
/*  29 */   protected SGL GL = Renderer.get();
/*     */ 
/*     */   
/*     */   public static final int BLEND_ADDITIVE = 1;
/*     */ 
/*     */   
/*     */   public static final int BLEND_COMBINE = 2;
/*     */ 
/*     */   
/*     */   private static final int DEFAULT_PARTICLES = 100;
/*     */   
/*  40 */   private ArrayList removeMe = new ArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setRelativePath(String path) {
/*  49 */     ConfigurableEmitter.setRelativePath(path);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class ParticlePool
/*     */   {
/*     */     public Particle[] particles;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ArrayList available;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ParticlePool(ParticleSystem system, int maxParticles) {
/*  72 */       this.particles = new Particle[maxParticles];
/*  73 */       this.available = new ArrayList();
/*     */       
/*  75 */       for (int i = 0; i < this.particles.length; i++)
/*     */       {
/*  77 */         this.particles[i] = ParticleSystem.this.createParticle(system);
/*     */       }
/*     */       
/*  80 */       reset(system);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void reset(ParticleSystem system) {
/*  89 */       this.available.clear();
/*     */       
/*  91 */       for (int i = 0; i < this.particles.length; i++)
/*     */       {
/*  93 */         this.available.add(this.particles[i]);
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
/* 104 */   protected HashMap particlesByEmitter = new HashMap<Object, Object>();
/*     */ 
/*     */   
/*     */   protected int maxParticlesPerEmitter;
/*     */   
/* 109 */   protected ArrayList emitters = new ArrayList();
/*     */ 
/*     */   
/*     */   protected Particle dummy;
/*     */   
/* 114 */   private int blendingMode = 2;
/*     */ 
/*     */   
/*     */   private int pCount;
/*     */ 
/*     */   
/*     */   private boolean usePoints;
/*     */ 
/*     */   
/*     */   private float x;
/*     */ 
/*     */   
/*     */   private float y;
/*     */ 
/*     */   
/*     */   private boolean removeCompletedEmitters = true;
/*     */   
/*     */   private Image sprite;
/*     */   
/*     */   private boolean visible = true;
/*     */   
/*     */   private String defaultImageName;
/*     */   
/*     */   private Color mask;
/*     */ 
/*     */   
/*     */   public ParticleSystem(Image defaultSprite) {
/* 141 */     this(defaultSprite, 100);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ParticleSystem(String defaultSpriteRef) {
/* 150 */     this(defaultSpriteRef, 100);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset() {
/* 157 */     Iterator<ParticlePool> pools = this.particlesByEmitter.values().iterator();
/* 158 */     while (pools.hasNext()) {
/* 159 */       ParticlePool pool = pools.next();
/* 160 */       pool.reset(this);
/*     */     } 
/*     */     
/* 163 */     for (int i = 0; i < this.emitters.size(); i++) {
/* 164 */       ParticleEmitter emitter = this.emitters.get(i);
/* 165 */       emitter.resetState();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isVisible() {
/* 176 */     return this.visible;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVisible(boolean visible) {
/* 186 */     this.visible = visible;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRemoveCompletedEmitters(boolean remove) {
/* 195 */     this.removeCompletedEmitters = remove;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUsePoints(boolean usePoints) {
/* 204 */     this.usePoints = usePoints;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean usePoints() {
/* 213 */     return this.usePoints;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ParticleSystem(String defaultSpriteRef, int maxParticles) {
/* 223 */     this(defaultSpriteRef, maxParticles, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ParticleSystem(String defaultSpriteRef, int maxParticles, Color mask) {
/* 234 */     this.maxParticlesPerEmitter = maxParticles;
/* 235 */     this.mask = mask;
/*     */     
/* 237 */     setDefaultImageName(defaultSpriteRef);
/* 238 */     this.dummy = createParticle(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ParticleSystem(Image defaultSprite, int maxParticles) {
/* 248 */     this.maxParticlesPerEmitter = maxParticles;
/*     */     
/* 250 */     this.sprite = defaultSprite;
/* 251 */     this.dummy = createParticle(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDefaultImageName(String ref) {
/* 260 */     this.defaultImageName = ref;
/* 261 */     this.sprite = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBlendingMode() {
/* 272 */     return this.blendingMode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Particle createParticle(ParticleSystem system) {
/* 283 */     return new Particle(system);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBlendingMode(int mode) {
/* 292 */     this.blendingMode = mode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEmitterCount() {
/* 301 */     return this.emitters.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ParticleEmitter getEmitter(int index) {
/* 311 */     return this.emitters.get(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addEmitter(ParticleEmitter emitter) {
/* 320 */     this.emitters.add(emitter);
/*     */     
/* 322 */     ParticlePool pool = new ParticlePool(this, this.maxParticlesPerEmitter);
/* 323 */     this.particlesByEmitter.put(emitter, pool);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeEmitter(ParticleEmitter emitter) {
/* 332 */     this.emitters.remove(emitter);
/* 333 */     this.particlesByEmitter.remove(emitter);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeAllEmitters() {
/* 340 */     for (int i = 0; i < this.emitters.size(); i++) {
/* 341 */       removeEmitter(this.emitters.get(i));
/* 342 */       i--;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getPositionX() {
/* 352 */     return this.x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getPositionY() {
/* 361 */     return this.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPosition(float x, float y) {
/* 372 */     this.x = x;
/* 373 */     this.y = y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render() {
/* 380 */     render(this.x, this.y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(float x, float y) {
/* 390 */     if (this.sprite == null && this.defaultImageName != null) {
/* 391 */       loadSystemParticleImage();
/*     */     }
/*     */     
/* 394 */     if (!this.visible) {
/*     */       return;
/*     */     }
/*     */     
/* 398 */     this.GL.glTranslatef(x, y, 0.0F);
/*     */     
/* 400 */     if (this.blendingMode == 1) {
/* 401 */       this.GL.glBlendFunc(770, 1);
/*     */     }
/* 403 */     if (usePoints()) {
/* 404 */       this.GL.glEnable(2832);
/* 405 */       TextureImpl.bindNone();
/*     */     } 
/*     */ 
/*     */     
/* 409 */     for (int emitterIdx = 0; emitterIdx < this.emitters.size(); emitterIdx++) {
/*     */ 
/*     */       
/* 412 */       ParticleEmitter emitter = this.emitters.get(emitterIdx);
/*     */       
/* 414 */       if (emitter.isEnabled()) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 419 */         if (emitter.useAdditive()) {
/* 420 */           this.GL.glBlendFunc(770, 1);
/*     */         }
/*     */ 
/*     */         
/* 424 */         ParticlePool pool = (ParticlePool)this.particlesByEmitter.get(emitter);
/* 425 */         Image image = emitter.getImage();
/* 426 */         if (image == null) {
/* 427 */           image = this.sprite;
/*     */         }
/*     */         
/* 430 */         if (!emitter.isOriented() && !emitter.usePoints(this)) {
/* 431 */           image.startUse();
/*     */         }
/*     */         
/* 434 */         for (int i = 0; i < pool.particles.length; i++) {
/*     */           
/* 436 */           if (pool.particles[i].inUse()) {
/* 437 */             pool.particles[i].render();
/*     */           }
/*     */         } 
/* 440 */         if (!emitter.isOriented() && !emitter.usePoints(this)) {
/* 441 */           image.endUse();
/*     */         }
/*     */ 
/*     */         
/* 445 */         if (emitter.useAdditive()) {
/* 446 */           this.GL.glBlendFunc(770, 771);
/*     */         }
/*     */       } 
/*     */     } 
/* 450 */     if (usePoints()) {
/* 451 */       this.GL.glDisable(2832);
/*     */     }
/* 453 */     if (this.blendingMode == 1) {
/* 454 */       this.GL.glBlendFunc(770, 771);
/*     */     }
/*     */     
/* 457 */     Color.white.bind();
/* 458 */     this.GL.glTranslatef(-x, -y, 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void loadSystemParticleImage() {
/* 465 */     AccessController.doPrivileged(new PrivilegedAction() {
/*     */           public Object run() {
/*     */             try {
/* 468 */               if (ParticleSystem.this.mask != null) {
/* 469 */                 ParticleSystem.this.sprite = new Image(ParticleSystem.this.defaultImageName, ParticleSystem.this.mask);
/*     */               } else {
/* 471 */                 ParticleSystem.this.sprite = new Image(ParticleSystem.this.defaultImageName);
/*     */               } 
/* 473 */             } catch (SlickException e) {
/* 474 */               Log.error((Throwable)e);
/* 475 */               ParticleSystem.this.defaultImageName = null;
/*     */             } 
/* 477 */             return null;
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(int delta) {
/* 488 */     if (this.sprite == null && this.defaultImageName != null) {
/* 489 */       loadSystemParticleImage();
/*     */     }
/*     */     
/* 492 */     this.removeMe.clear();
/* 493 */     ArrayList<ParticleEmitter> emitters = new ArrayList(this.emitters);
/* 494 */     for (int i = 0; i < emitters.size(); i++) {
/* 495 */       ParticleEmitter emitter = emitters.get(i);
/* 496 */       if (emitter.isEnabled()) {
/* 497 */         emitter.update(this, delta);
/* 498 */         if (this.removeCompletedEmitters && 
/* 499 */           emitter.completed()) {
/* 500 */           this.removeMe.add(emitter);
/* 501 */           this.particlesByEmitter.remove(emitter);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 506 */     this.emitters.removeAll(this.removeMe);
/*     */     
/* 508 */     this.pCount = 0;
/*     */     
/* 510 */     if (!this.particlesByEmitter.isEmpty()) {
/*     */       
/* 512 */       Iterator<ParticleEmitter> it = this.particlesByEmitter.keySet().iterator();
/* 513 */       while (it.hasNext()) {
/*     */         
/* 515 */         ParticleEmitter emitter = it.next();
/* 516 */         if (emitter.isEnabled()) {
/* 517 */           ParticlePool pool = (ParticlePool)this.particlesByEmitter.get(emitter);
/* 518 */           for (int j = 0; j < pool.particles.length; j++) {
/* 519 */             if ((pool.particles[j]).life > 0.0F) {
/* 520 */               pool.particles[j].update(delta);
/* 521 */               this.pCount++;
/*     */             } 
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
/*     */   
/*     */   public int getParticleCount() {
/* 535 */     return this.pCount;
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
/*     */   public Particle getNewParticle(ParticleEmitter emitter, float life) {
/* 548 */     ParticlePool pool = (ParticlePool)this.particlesByEmitter.get(emitter);
/* 549 */     ArrayList<Particle> available = pool.available;
/* 550 */     if (available.size() > 0) {
/*     */       
/* 552 */       Particle p = available.remove(available.size() - 1);
/* 553 */       p.init(emitter, life);
/* 554 */       p.setImage(this.sprite);
/*     */       
/* 556 */       return p;
/*     */     } 
/*     */     
/* 559 */     Log.warn("Ran out of particles (increase the limit)!");
/* 560 */     return this.dummy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void release(Particle particle) {
/* 569 */     if (particle != this.dummy) {
/*     */       
/* 571 */       ParticlePool pool = (ParticlePool)this.particlesByEmitter.get(particle.getEmitter());
/* 572 */       pool.available.add(particle);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void releaseAll(ParticleEmitter emitter) {
/* 582 */     if (!this.particlesByEmitter.isEmpty()) {
/*     */       
/* 584 */       Iterator<ParticlePool> it = this.particlesByEmitter.values().iterator();
/* 585 */       while (it.hasNext()) {
/*     */         
/* 587 */         ParticlePool pool = it.next();
/* 588 */         for (int i = 0; i < pool.particles.length; i++) {
/* 589 */           if (pool.particles[i].inUse() && 
/* 590 */             pool.particles[i].getEmitter() == emitter) {
/* 591 */             pool.particles[i].setLife(-1.0F);
/* 592 */             release(pool.particles[i]);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void moveAll(ParticleEmitter emitter, float x, float y) {
/* 608 */     ParticlePool pool = (ParticlePool)this.particlesByEmitter.get(emitter);
/* 609 */     for (int i = 0; i < pool.particles.length; i++) {
/* 610 */       if (pool.particles[i].inUse()) {
/* 611 */         pool.particles[i].move(x, y);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ParticleSystem duplicate() throws SlickException {
/* 627 */     for (int i = 0; i < this.emitters.size(); i++) {
/* 628 */       if (!(this.emitters.get(i) instanceof ConfigurableEmitter)) {
/* 629 */         throw new SlickException("Only systems contianing configurable emitters can be duplicated");
/*     */       }
/*     */     } 
/*     */     
/* 633 */     ParticleSystem theCopy = null;
/*     */     try {
/* 635 */       ByteArrayOutputStream bout = new ByteArrayOutputStream();
/* 636 */       ParticleIO.saveConfiguredSystem(bout, this);
/* 637 */       ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
/* 638 */       theCopy = ParticleIO.loadConfiguredSystem(bin);
/* 639 */     } catch (IOException e) {
/* 640 */       Log.error("Failed to duplicate particle system");
/* 641 */       throw new SlickException("Unable to duplicated particle system", e);
/*     */     } 
/*     */     
/* 644 */     return theCopy;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\particles\ParticleSystem.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */