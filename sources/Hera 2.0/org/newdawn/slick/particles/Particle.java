/*     */ package org.newdawn.slick.particles;
/*     */ 
/*     */ import org.newdawn.slick.Color;
/*     */ import org.newdawn.slick.Image;
/*     */ import org.newdawn.slick.opengl.TextureImpl;
/*     */ import org.newdawn.slick.opengl.renderer.Renderer;
/*     */ import org.newdawn.slick.opengl.renderer.SGL;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Particle
/*     */ {
/*  16 */   protected static SGL GL = Renderer.get();
/*     */ 
/*     */   
/*     */   public static final int INHERIT_POINTS = 1;
/*     */ 
/*     */   
/*     */   public static final int USE_POINTS = 2;
/*     */   
/*     */   public static final int USE_QUADS = 3;
/*     */   
/*     */   protected float x;
/*     */   
/*     */   protected float y;
/*     */   
/*     */   protected float velx;
/*     */   
/*     */   protected float vely;
/*     */   
/*  34 */   protected float size = 10.0F;
/*     */   
/*  36 */   protected Color color = Color.white;
/*     */   
/*     */   protected float life;
/*     */   
/*     */   protected float originalLife;
/*     */   
/*     */   private ParticleSystem engine;
/*     */   
/*     */   private ParticleEmitter emitter;
/*     */   
/*     */   protected Image image;
/*     */   
/*     */   protected int type;
/*     */   
/*  50 */   protected int usePoints = 1;
/*     */   
/*     */   protected boolean oriented = false;
/*     */   
/*  54 */   protected float scaleY = 1.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Particle(ParticleSystem engine) {
/*  63 */     this.engine = engine;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getX() {
/*  72 */     return this.x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getY() {
/*  81 */     return this.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void move(float x, float y) {
/*  91 */     this.x += x;
/*  92 */     this.y += y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getSize() {
/* 101 */     return this.size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getColor() {
/* 110 */     return this.color;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setImage(Image image) {
/* 120 */     this.image = image;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getOriginalLife() {
/* 129 */     return this.originalLife;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getLife() {
/* 138 */     return this.life;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean inUse() {
/* 147 */     return (this.life > 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render() {
/* 154 */     if ((this.engine.usePoints() && this.usePoints == 1) || this.usePoints == 2) {
/*     */       
/* 156 */       TextureImpl.bindNone();
/* 157 */       GL.glEnable(2832);
/* 158 */       GL.glPointSize(this.size / 2.0F);
/* 159 */       this.color.bind();
/* 160 */       GL.glBegin(0);
/* 161 */       GL.glVertex2f(this.x, this.y);
/* 162 */       GL.glEnd();
/* 163 */     } else if (this.oriented || this.scaleY != 1.0F) {
/* 164 */       GL.glPushMatrix();
/*     */       
/* 166 */       GL.glTranslatef(this.x, this.y, 0.0F);
/*     */       
/* 168 */       if (this.oriented) {
/* 169 */         float angle = (float)(Math.atan2(this.y, this.x) * 180.0D / Math.PI);
/* 170 */         GL.glRotatef(angle, 0.0F, 0.0F, 1.0F);
/*     */       } 
/*     */ 
/*     */       
/* 174 */       GL.glScalef(1.0F, this.scaleY, 1.0F);
/*     */       
/* 176 */       this.image.draw((int)-(this.size / 2.0F), (int)-(this.size / 2.0F), (int)this.size, (int)this.size, this.color);
/*     */       
/* 178 */       GL.glPopMatrix();
/*     */     } else {
/* 180 */       this.color.bind();
/* 181 */       this.image.drawEmbedded((int)(this.x - this.size / 2.0F), (int)(this.y - this.size / 2.0F), (int)this.size, (int)this.size);
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
/*     */   public void update(int delta) {
/* 193 */     this.emitter.updateParticle(this, delta);
/* 194 */     this.life -= delta;
/*     */     
/* 196 */     if (this.life > 0.0F) {
/* 197 */       this.x += delta * this.velx;
/* 198 */       this.y += delta * this.vely;
/*     */     } else {
/* 200 */       this.engine.release(this);
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
/*     */   public void init(ParticleEmitter emitter, float life) {
/* 213 */     this.x = 0.0F;
/* 214 */     this.emitter = emitter;
/* 215 */     this.y = 0.0F;
/* 216 */     this.velx = 0.0F;
/* 217 */     this.vely = 0.0F;
/* 218 */     this.size = 10.0F;
/* 219 */     this.type = 0;
/* 220 */     this.originalLife = this.life = life;
/* 221 */     this.oriented = false;
/* 222 */     this.scaleY = 1.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setType(int type) {
/* 232 */     this.type = type;
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
/*     */   public void setUsePoint(int usePoints) {
/* 245 */     this.usePoints = usePoints;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getType() {
/* 254 */     return this.type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSize(float size) {
/* 264 */     this.size = size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void adjustSize(float delta) {
/* 274 */     this.size += delta;
/* 275 */     this.size = Math.max(0.0F, this.size);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLife(float life) {
/* 285 */     this.life = life;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void adjustLife(float delta) {
/* 295 */     this.life += delta;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void kill() {
/* 303 */     this.life = 1.0F;
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
/*     */   public void setColor(float r, float g, float b, float a) {
/* 319 */     if (this.color == Color.white) {
/* 320 */       this.color = new Color(r, g, b, a);
/*     */     } else {
/* 322 */       this.color.r = r;
/* 323 */       this.color.g = g;
/* 324 */       this.color.b = b;
/* 325 */       this.color.a = a;
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
/*     */   public void setPosition(float x, float y) {
/* 338 */     this.x = x;
/* 339 */     this.y = y;
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
/*     */   public void setVelocity(float dirx, float diry, float speed) {
/* 353 */     this.velx = dirx * speed;
/* 354 */     this.vely = diry * speed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSpeed(float speed) {
/* 363 */     float currentSpeed = (float)Math.sqrt((this.velx * this.velx + this.vely * this.vely));
/* 364 */     this.velx *= speed;
/* 365 */     this.vely *= speed;
/* 366 */     this.velx /= currentSpeed;
/* 367 */     this.vely /= currentSpeed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVelocity(float velx, float vely) {
/* 377 */     setVelocity(velx, vely, 1.0F);
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
/*     */   public void adjustPosition(float dx, float dy) {
/* 389 */     this.x += dx;
/* 390 */     this.y += dy;
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
/*     */   public void adjustColor(float r, float g, float b, float a) {
/* 406 */     if (this.color == Color.white) {
/* 407 */       this.color = new Color(1.0F, 1.0F, 1.0F, 1.0F);
/*     */     }
/* 409 */     this.color.r += r;
/* 410 */     this.color.g += g;
/* 411 */     this.color.b += b;
/* 412 */     this.color.a += a;
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
/*     */   public void adjustColor(int r, int g, int b, int a) {
/* 428 */     if (this.color == Color.white) {
/* 429 */       this.color = new Color(1.0F, 1.0F, 1.0F, 1.0F);
/*     */     }
/*     */     
/* 432 */     this.color.r += r / 255.0F;
/* 433 */     this.color.g += g / 255.0F;
/* 434 */     this.color.b += b / 255.0F;
/* 435 */     this.color.a += a / 255.0F;
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
/*     */   public void adjustVelocity(float dx, float dy) {
/* 447 */     this.velx += dx;
/* 448 */     this.vely += dy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ParticleEmitter getEmitter() {
/* 457 */     return this.emitter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 464 */     return super.toString() + " : " + this.life;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOriented() {
/* 473 */     return this.oriented;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOriented(boolean oriented) {
/* 482 */     this.oriented = oriented;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getScaleY() {
/* 491 */     return this.scaleY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setScaleY(float scaleY) {
/* 500 */     this.scaleY = scaleY;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\particles\Particle.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */