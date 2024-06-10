/*   1:    */ package org.newdawn.slick.particles;
/*   2:    */ 
/*   3:    */ import java.io.ByteArrayInputStream;
/*   4:    */ import java.io.ByteArrayOutputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.security.AccessController;
/*   7:    */ import java.security.PrivilegedAction;
/*   8:    */ import java.util.ArrayList;
/*   9:    */ import java.util.Collection;
/*  10:    */ import java.util.HashMap;
/*  11:    */ import java.util.Iterator;
/*  12:    */ import java.util.Set;
/*  13:    */ import org.newdawn.slick.Color;
/*  14:    */ import org.newdawn.slick.Image;
/*  15:    */ import org.newdawn.slick.SlickException;
/*  16:    */ import org.newdawn.slick.opengl.TextureImpl;
/*  17:    */ import org.newdawn.slick.opengl.renderer.Renderer;
/*  18:    */ import org.newdawn.slick.opengl.renderer.SGL;
/*  19:    */ import org.newdawn.slick.util.Log;
/*  20:    */ 
/*  21:    */ public class ParticleSystem
/*  22:    */ {
/*  23: 29 */   protected SGL GL = Renderer.get();
/*  24:    */   public static final int BLEND_ADDITIVE = 1;
/*  25:    */   public static final int BLEND_COMBINE = 2;
/*  26:    */   private static final int DEFAULT_PARTICLES = 100;
/*  27: 40 */   private ArrayList removeMe = new ArrayList();
/*  28:    */   
/*  29:    */   public static void setRelativePath(String path)
/*  30:    */   {
/*  31: 49 */     ConfigurableEmitter.setRelativePath(path);
/*  32:    */   }
/*  33:    */   
/*  34:    */   private class ParticlePool
/*  35:    */   {
/*  36:    */     public Particle[] particles;
/*  37:    */     public ArrayList available;
/*  38:    */     
/*  39:    */     public ParticlePool(ParticleSystem system, int maxParticles)
/*  40:    */     {
/*  41: 72 */       this.particles = new Particle[maxParticles];
/*  42: 73 */       this.available = new ArrayList();
/*  43: 75 */       for (int i = 0; i < this.particles.length; i++) {
/*  44: 77 */         this.particles[i] = ParticleSystem.this.createParticle(system);
/*  45:    */       }
/*  46: 80 */       reset(system);
/*  47:    */     }
/*  48:    */     
/*  49:    */     public void reset(ParticleSystem system)
/*  50:    */     {
/*  51: 89 */       this.available.clear();
/*  52: 91 */       for (int i = 0; i < this.particles.length; i++) {
/*  53: 93 */         this.available.add(this.particles[i]);
/*  54:    */       }
/*  55:    */     }
/*  56:    */   }
/*  57:    */   
/*  58:104 */   protected HashMap particlesByEmitter = new HashMap();
/*  59:    */   protected int maxParticlesPerEmitter;
/*  60:109 */   protected ArrayList emitters = new ArrayList();
/*  61:    */   protected Particle dummy;
/*  62:114 */   private int blendingMode = 2;
/*  63:    */   private int pCount;
/*  64:    */   private boolean usePoints;
/*  65:    */   private float x;
/*  66:    */   private float y;
/*  67:124 */   private boolean removeCompletedEmitters = true;
/*  68:    */   private Image sprite;
/*  69:129 */   private boolean visible = true;
/*  70:    */   private String defaultImageName;
/*  71:    */   private Color mask;
/*  72:    */   
/*  73:    */   public ParticleSystem(Image defaultSprite)
/*  74:    */   {
/*  75:141 */     this(defaultSprite, 100);
/*  76:    */   }
/*  77:    */   
/*  78:    */   public ParticleSystem(String defaultSpriteRef)
/*  79:    */   {
/*  80:150 */     this(defaultSpriteRef, 100);
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void reset()
/*  84:    */   {
/*  85:157 */     Iterator pools = this.particlesByEmitter.values().iterator();
/*  86:158 */     while (pools.hasNext())
/*  87:    */     {
/*  88:159 */       ParticlePool pool = (ParticlePool)pools.next();
/*  89:160 */       pool.reset(this);
/*  90:    */     }
/*  91:163 */     for (int i = 0; i < this.emitters.size(); i++)
/*  92:    */     {
/*  93:164 */       ParticleEmitter emitter = (ParticleEmitter)this.emitters.get(i);
/*  94:165 */       emitter.resetState();
/*  95:    */     }
/*  96:    */   }
/*  97:    */   
/*  98:    */   public boolean isVisible()
/*  99:    */   {
/* 100:176 */     return this.visible;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void setVisible(boolean visible)
/* 104:    */   {
/* 105:186 */     this.visible = visible;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void setRemoveCompletedEmitters(boolean remove)
/* 109:    */   {
/* 110:195 */     this.removeCompletedEmitters = remove;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void setUsePoints(boolean usePoints)
/* 114:    */   {
/* 115:204 */     this.usePoints = usePoints;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public boolean usePoints()
/* 119:    */   {
/* 120:213 */     return this.usePoints;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public ParticleSystem(String defaultSpriteRef, int maxParticles)
/* 124:    */   {
/* 125:223 */     this(defaultSpriteRef, maxParticles, null);
/* 126:    */   }
/* 127:    */   
/* 128:    */   public ParticleSystem(String defaultSpriteRef, int maxParticles, Color mask)
/* 129:    */   {
/* 130:234 */     this.maxParticlesPerEmitter = maxParticles;
/* 131:235 */     this.mask = mask;
/* 132:    */     
/* 133:237 */     setDefaultImageName(defaultSpriteRef);
/* 134:238 */     this.dummy = createParticle(this);
/* 135:    */   }
/* 136:    */   
/* 137:    */   public ParticleSystem(Image defaultSprite, int maxParticles)
/* 138:    */   {
/* 139:248 */     this.maxParticlesPerEmitter = maxParticles;
/* 140:    */     
/* 141:250 */     this.sprite = defaultSprite;
/* 142:251 */     this.dummy = createParticle(this);
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void setDefaultImageName(String ref)
/* 146:    */   {
/* 147:260 */     this.defaultImageName = ref;
/* 148:261 */     this.sprite = null;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public int getBlendingMode()
/* 152:    */   {
/* 153:272 */     return this.blendingMode;
/* 154:    */   }
/* 155:    */   
/* 156:    */   protected Particle createParticle(ParticleSystem system)
/* 157:    */   {
/* 158:283 */     return new Particle(system);
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void setBlendingMode(int mode)
/* 162:    */   {
/* 163:292 */     this.blendingMode = mode;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public int getEmitterCount()
/* 167:    */   {
/* 168:301 */     return this.emitters.size();
/* 169:    */   }
/* 170:    */   
/* 171:    */   public ParticleEmitter getEmitter(int index)
/* 172:    */   {
/* 173:311 */     return (ParticleEmitter)this.emitters.get(index);
/* 174:    */   }
/* 175:    */   
/* 176:    */   public void addEmitter(ParticleEmitter emitter)
/* 177:    */   {
/* 178:320 */     this.emitters.add(emitter);
/* 179:    */     
/* 180:322 */     ParticlePool pool = new ParticlePool(this, this.maxParticlesPerEmitter);
/* 181:323 */     this.particlesByEmitter.put(emitter, pool);
/* 182:    */   }
/* 183:    */   
/* 184:    */   public void removeEmitter(ParticleEmitter emitter)
/* 185:    */   {
/* 186:332 */     this.emitters.remove(emitter);
/* 187:333 */     this.particlesByEmitter.remove(emitter);
/* 188:    */   }
/* 189:    */   
/* 190:    */   public void removeAllEmitters()
/* 191:    */   {
/* 192:340 */     for (int i = 0; i < this.emitters.size(); i++)
/* 193:    */     {
/* 194:341 */       removeEmitter((ParticleEmitter)this.emitters.get(i));
/* 195:342 */       i--;
/* 196:    */     }
/* 197:    */   }
/* 198:    */   
/* 199:    */   public float getPositionX()
/* 200:    */   {
/* 201:352 */     return this.x;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public float getPositionY()
/* 205:    */   {
/* 206:361 */     return this.y;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public void setPosition(float x, float y)
/* 210:    */   {
/* 211:372 */     this.x = x;
/* 212:373 */     this.y = y;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public void render()
/* 216:    */   {
/* 217:380 */     render(this.x, this.y);
/* 218:    */   }
/* 219:    */   
/* 220:    */   public void render(float x, float y)
/* 221:    */   {
/* 222:390 */     if ((this.sprite == null) && (this.defaultImageName != null)) {
/* 223:391 */       loadSystemParticleImage();
/* 224:    */     }
/* 225:394 */     if (!this.visible) {
/* 226:395 */       return;
/* 227:    */     }
/* 228:398 */     this.GL.glTranslatef(x, y, 0.0F);
/* 229:400 */     if (this.blendingMode == 1) {
/* 230:401 */       this.GL.glBlendFunc(770, 1);
/* 231:    */     }
/* 232:403 */     if (usePoints())
/* 233:    */     {
/* 234:404 */       this.GL.glEnable(2832);
/* 235:405 */       TextureImpl.bindNone();
/* 236:    */     }
/* 237:409 */     for (int emitterIdx = 0; emitterIdx < this.emitters.size(); emitterIdx++)
/* 238:    */     {
/* 239:412 */       ParticleEmitter emitter = (ParticleEmitter)this.emitters.get(emitterIdx);
/* 240:414 */       if (emitter.isEnabled())
/* 241:    */       {
/* 242:419 */         if (emitter.useAdditive()) {
/* 243:420 */           this.GL.glBlendFunc(770, 1);
/* 244:    */         }
/* 245:424 */         ParticlePool pool = (ParticlePool)this.particlesByEmitter.get(emitter);
/* 246:425 */         Image image = emitter.getImage();
/* 247:426 */         if (image == null) {
/* 248:427 */           image = this.sprite;
/* 249:    */         }
/* 250:430 */         if ((!emitter.isOriented()) && (!emitter.usePoints(this))) {
/* 251:431 */           image.startUse();
/* 252:    */         }
/* 253:434 */         for (int i = 0; i < pool.particles.length; i++) {
/* 254:436 */           if (pool.particles[i].inUse()) {
/* 255:437 */             pool.particles[i].render();
/* 256:    */           }
/* 257:    */         }
/* 258:440 */         if ((!emitter.isOriented()) && (!emitter.usePoints(this))) {
/* 259:441 */           image.endUse();
/* 260:    */         }
/* 261:445 */         if (emitter.useAdditive()) {
/* 262:446 */           this.GL.glBlendFunc(770, 771);
/* 263:    */         }
/* 264:    */       }
/* 265:    */     }
/* 266:450 */     if (usePoints()) {
/* 267:451 */       this.GL.glDisable(2832);
/* 268:    */     }
/* 269:453 */     if (this.blendingMode == 1) {
/* 270:454 */       this.GL.glBlendFunc(770, 771);
/* 271:    */     }
/* 272:457 */     Color.white.bind();
/* 273:458 */     this.GL.glTranslatef(-x, -y, 0.0F);
/* 274:    */   }
/* 275:    */   
/* 276:    */   private void loadSystemParticleImage()
/* 277:    */   {
/* 278:465 */     AccessController.doPrivileged(new PrivilegedAction()
/* 279:    */     {
/* 280:    */       public Object run()
/* 281:    */       {
/* 282:    */         try
/* 283:    */         {
/* 284:468 */           if (ParticleSystem.this.mask != null) {
/* 285:469 */             ParticleSystem.this.sprite = new Image(ParticleSystem.this.defaultImageName, ParticleSystem.this.mask);
/* 286:    */           } else {
/* 287:471 */             ParticleSystem.this.sprite = new Image(ParticleSystem.this.defaultImageName);
/* 288:    */           }
/* 289:    */         }
/* 290:    */         catch (SlickException e)
/* 291:    */         {
/* 292:474 */           Log.error(e);
/* 293:475 */           ParticleSystem.this.defaultImageName = null;
/* 294:    */         }
/* 295:477 */         return null;
/* 296:    */       }
/* 297:    */     });
/* 298:    */   }
/* 299:    */   
/* 300:    */   public void update(int delta)
/* 301:    */   {
/* 302:488 */     if ((this.sprite == null) && (this.defaultImageName != null)) {
/* 303:489 */       loadSystemParticleImage();
/* 304:    */     }
/* 305:492 */     this.removeMe.clear();
/* 306:493 */     ArrayList emitters = new ArrayList(this.emitters);
/* 307:494 */     for (int i = 0; i < emitters.size(); i++)
/* 308:    */     {
/* 309:495 */       ParticleEmitter emitter = (ParticleEmitter)emitters.get(i);
/* 310:496 */       if (emitter.isEnabled())
/* 311:    */       {
/* 312:497 */         emitter.update(this, delta);
/* 313:498 */         if ((this.removeCompletedEmitters) && 
/* 314:499 */           (emitter.completed()))
/* 315:    */         {
/* 316:500 */           this.removeMe.add(emitter);
/* 317:501 */           this.particlesByEmitter.remove(emitter);
/* 318:    */         }
/* 319:    */       }
/* 320:    */     }
/* 321:506 */     this.emitters.removeAll(this.removeMe);
/* 322:    */     
/* 323:508 */     this.pCount = 0;
/* 324:510 */     if (!this.particlesByEmitter.isEmpty())
/* 325:    */     {
/* 326:512 */       Iterator it = this.particlesByEmitter.keySet().iterator();
/* 327:513 */       while (it.hasNext())
/* 328:    */       {
/* 329:515 */         ParticleEmitter emitter = (ParticleEmitter)it.next();
/* 330:516 */         if (emitter.isEnabled())
/* 331:    */         {
/* 332:517 */           ParticlePool pool = (ParticlePool)this.particlesByEmitter.get(emitter);
/* 333:518 */           for (int i = 0; i < pool.particles.length; i++) {
/* 334:519 */             if (pool.particles[i].life > 0.0F)
/* 335:    */             {
/* 336:520 */               pool.particles[i].update(delta);
/* 337:521 */               this.pCount += 1;
/* 338:    */             }
/* 339:    */           }
/* 340:    */         }
/* 341:    */       }
/* 342:    */     }
/* 343:    */   }
/* 344:    */   
/* 345:    */   public int getParticleCount()
/* 346:    */   {
/* 347:535 */     return this.pCount;
/* 348:    */   }
/* 349:    */   
/* 350:    */   public Particle getNewParticle(ParticleEmitter emitter, float life)
/* 351:    */   {
/* 352:548 */     ParticlePool pool = (ParticlePool)this.particlesByEmitter.get(emitter);
/* 353:549 */     ArrayList available = pool.available;
/* 354:550 */     if (available.size() > 0)
/* 355:    */     {
/* 356:552 */       Particle p = (Particle)available.remove(available.size() - 1);
/* 357:553 */       p.init(emitter, life);
/* 358:554 */       p.setImage(this.sprite);
/* 359:    */       
/* 360:556 */       return p;
/* 361:    */     }
/* 362:559 */     Log.warn("Ran out of particles (increase the limit)!");
/* 363:560 */     return this.dummy;
/* 364:    */   }
/* 365:    */   
/* 366:    */   public void release(Particle particle)
/* 367:    */   {
/* 368:569 */     if (particle != this.dummy)
/* 369:    */     {
/* 370:571 */       ParticlePool pool = (ParticlePool)this.particlesByEmitter.get(particle.getEmitter());
/* 371:572 */       pool.available.add(particle);
/* 372:    */     }
/* 373:    */   }
/* 374:    */   
/* 375:    */   public void releaseAll(ParticleEmitter emitter)
/* 376:    */   {
/* 377:582 */     if (!this.particlesByEmitter.isEmpty())
/* 378:    */     {
/* 379:584 */       Iterator it = this.particlesByEmitter.values().iterator();
/* 380:    */       ParticlePool pool;
/* 381:    */       int i;
/* 382:585 */       for (; it.hasNext(); i < pool.particles.length)
/* 383:    */       {
/* 384:587 */         pool = (ParticlePool)it.next();
/* 385:588 */         i = 0; continue;
/* 386:589 */         if ((pool.particles[i].inUse()) && 
/* 387:590 */           (pool.particles[i].getEmitter() == emitter))
/* 388:    */         {
/* 389:591 */           pool.particles[i].setLife(-1.0F);
/* 390:592 */           release(pool.particles[i]);
/* 391:    */         }
/* 392:588 */         i++;
/* 393:    */       }
/* 394:    */     }
/* 395:    */   }
/* 396:    */   
/* 397:    */   public void moveAll(ParticleEmitter emitter, float x, float y)
/* 398:    */   {
/* 399:608 */     ParticlePool pool = (ParticlePool)this.particlesByEmitter.get(emitter);
/* 400:609 */     for (int i = 0; i < pool.particles.length; i++) {
/* 401:610 */       if (pool.particles[i].inUse()) {
/* 402:611 */         pool.particles[i].move(x, y);
/* 403:    */       }
/* 404:    */     }
/* 405:    */   }
/* 406:    */   
/* 407:    */   public ParticleSystem duplicate()
/* 408:    */     throws SlickException
/* 409:    */   {
/* 410:627 */     for (int i = 0; i < this.emitters.size(); i++) {
/* 411:628 */       if (!(this.emitters.get(i) instanceof ConfigurableEmitter)) {
/* 412:629 */         throw new SlickException("Only systems contianing configurable emitters can be duplicated");
/* 413:    */       }
/* 414:    */     }
/* 415:633 */     ParticleSystem theCopy = null;
/* 416:    */     try
/* 417:    */     {
/* 418:635 */       ByteArrayOutputStream bout = new ByteArrayOutputStream();
/* 419:636 */       ParticleIO.saveConfiguredSystem(bout, this);
/* 420:637 */       ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
/* 421:638 */       theCopy = ParticleIO.loadConfiguredSystem(bin);
/* 422:    */     }
/* 423:    */     catch (IOException e)
/* 424:    */     {
/* 425:640 */       Log.error("Failed to duplicate particle system");
/* 426:641 */       throw new SlickException("Unable to duplicated particle system", e);
/* 427:    */     }
/* 428:644 */     return theCopy;
/* 429:    */   }
/* 430:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.particles.ParticleSystem
 * JD-Core Version:    0.7.0.1
 */