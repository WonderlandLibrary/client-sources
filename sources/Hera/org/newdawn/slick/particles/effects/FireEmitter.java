/*     */ package org.newdawn.slick.particles.effects;
/*     */ 
/*     */ import org.newdawn.slick.Image;
/*     */ import org.newdawn.slick.particles.Particle;
/*     */ import org.newdawn.slick.particles.ParticleEmitter;
/*     */ import org.newdawn.slick.particles.ParticleSystem;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FireEmitter
/*     */   implements ParticleEmitter
/*     */ {
/*     */   private int x;
/*     */   private int y;
/*  20 */   private int interval = 50;
/*     */   
/*     */   private int timer;
/*     */   
/*  24 */   private float size = 40.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FireEmitter() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FireEmitter(int x, int y) {
/*  39 */     this.x = x;
/*  40 */     this.y = y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FireEmitter(int x, int y, float size) {
/*  51 */     this.x = x;
/*  52 */     this.y = y;
/*  53 */     this.size = size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(ParticleSystem system, int delta) {
/*  60 */     this.timer -= delta;
/*  61 */     if (this.timer <= 0) {
/*  62 */       this.timer = this.interval;
/*  63 */       Particle p = system.getNewParticle(this, 1000.0F);
/*  64 */       p.setColor(1.0F, 1.0F, 1.0F, 0.5F);
/*  65 */       p.setPosition(this.x, this.y);
/*  66 */       p.setSize(this.size);
/*  67 */       float vx = (float)(-0.019999999552965164D + Math.random() * 0.03999999910593033D);
/*  68 */       float vy = (float)-(Math.random() * 0.15000000596046448D);
/*  69 */       p.setVelocity(vx, vy, 1.1F);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateParticle(Particle particle, int delta) {
/*  77 */     if (particle.getLife() > 600.0F) {
/*  78 */       particle.adjustSize(0.07F * delta);
/*     */     } else {
/*  80 */       particle.adjustSize(-0.04F * delta * this.size / 40.0F);
/*     */     } 
/*  82 */     float c = 0.002F * delta;
/*  83 */     particle.adjustColor(0.0F, -c / 2.0F, -c * 2.0F, -c / 4.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEnabled() {
/*  90 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEnabled(boolean enabled) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean completed() {
/* 103 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean useAdditive() {
/* 110 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Image getImage() {
/* 117 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean usePoints(ParticleSystem system) {
/* 124 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOriented() {
/* 131 */     return false;
/*     */   }
/*     */   
/*     */   public void wrapUp() {}
/*     */   
/*     */   public void resetState() {}
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\particles\effects\FireEmitter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */