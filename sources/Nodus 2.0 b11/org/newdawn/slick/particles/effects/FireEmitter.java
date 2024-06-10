/*   1:    */ package org.newdawn.slick.particles.effects;
/*   2:    */ 
/*   3:    */ import org.newdawn.slick.Image;
/*   4:    */ import org.newdawn.slick.particles.Particle;
/*   5:    */ import org.newdawn.slick.particles.ParticleEmitter;
/*   6:    */ import org.newdawn.slick.particles.ParticleSystem;
/*   7:    */ 
/*   8:    */ public class FireEmitter
/*   9:    */   implements ParticleEmitter
/*  10:    */ {
/*  11:    */   private int x;
/*  12:    */   private int y;
/*  13: 20 */   private int interval = 50;
/*  14:    */   private int timer;
/*  15: 24 */   private float size = 40.0F;
/*  16:    */   
/*  17:    */   public FireEmitter() {}
/*  18:    */   
/*  19:    */   public FireEmitter(int x, int y)
/*  20:    */   {
/*  21: 39 */     this.x = x;
/*  22: 40 */     this.y = y;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public FireEmitter(int x, int y, float size)
/*  26:    */   {
/*  27: 51 */     this.x = x;
/*  28: 52 */     this.y = y;
/*  29: 53 */     this.size = size;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void update(ParticleSystem system, int delta)
/*  33:    */   {
/*  34: 60 */     this.timer -= delta;
/*  35: 61 */     if (this.timer <= 0)
/*  36:    */     {
/*  37: 62 */       this.timer = this.interval;
/*  38: 63 */       Particle p = system.getNewParticle(this, 1000.0F);
/*  39: 64 */       p.setColor(1.0F, 1.0F, 1.0F, 0.5F);
/*  40: 65 */       p.setPosition(this.x, this.y);
/*  41: 66 */       p.setSize(this.size);
/*  42: 67 */       float vx = (float)(-0.01999999955296516D + Math.random() * 0.03999999910593033D);
/*  43: 68 */       float vy = (float)-(Math.random() * 0.1500000059604645D);
/*  44: 69 */       p.setVelocity(vx, vy, 1.1F);
/*  45:    */     }
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void updateParticle(Particle particle, int delta)
/*  49:    */   {
/*  50: 77 */     if (particle.getLife() > 600.0F) {
/*  51: 78 */       particle.adjustSize(0.07F * delta);
/*  52:    */     } else {
/*  53: 80 */       particle.adjustSize(-0.04F * delta * (this.size / 40.0F));
/*  54:    */     }
/*  55: 82 */     float c = 0.002F * delta;
/*  56: 83 */     particle.adjustColor(0.0F, -c / 2.0F, -c * 2.0F, -c / 4.0F);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public boolean isEnabled()
/*  60:    */   {
/*  61: 90 */     return true;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void setEnabled(boolean enabled) {}
/*  65:    */   
/*  66:    */   public boolean completed()
/*  67:    */   {
/*  68:103 */     return false;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public boolean useAdditive()
/*  72:    */   {
/*  73:110 */     return false;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public Image getImage()
/*  77:    */   {
/*  78:117 */     return null;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public boolean usePoints(ParticleSystem system)
/*  82:    */   {
/*  83:124 */     return false;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public boolean isOriented()
/*  87:    */   {
/*  88:131 */     return false;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void wrapUp() {}
/*  92:    */   
/*  93:    */   public void resetState() {}
/*  94:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.particles.effects.FireEmitter
 * JD-Core Version:    0.7.0.1
 */