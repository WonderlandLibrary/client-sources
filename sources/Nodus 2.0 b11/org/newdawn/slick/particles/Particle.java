/*   1:    */ package org.newdawn.slick.particles;
/*   2:    */ 
/*   3:    */ import org.newdawn.slick.Color;
/*   4:    */ import org.newdawn.slick.Image;
/*   5:    */ import org.newdawn.slick.opengl.TextureImpl;
/*   6:    */ import org.newdawn.slick.opengl.renderer.Renderer;
/*   7:    */ import org.newdawn.slick.opengl.renderer.SGL;
/*   8:    */ 
/*   9:    */ public class Particle
/*  10:    */ {
/*  11: 16 */   protected static SGL GL = ;
/*  12:    */   public static final int INHERIT_POINTS = 1;
/*  13:    */   public static final int USE_POINTS = 2;
/*  14:    */   public static final int USE_QUADS = 3;
/*  15:    */   protected float x;
/*  16:    */   protected float y;
/*  17:    */   protected float velx;
/*  18:    */   protected float vely;
/*  19: 34 */   protected float size = 10.0F;
/*  20: 36 */   protected Color color = Color.white;
/*  21:    */   protected float life;
/*  22:    */   protected float originalLife;
/*  23:    */   private ParticleSystem engine;
/*  24:    */   private ParticleEmitter emitter;
/*  25:    */   protected Image image;
/*  26:    */   protected int type;
/*  27: 50 */   protected int usePoints = 1;
/*  28: 52 */   protected boolean oriented = false;
/*  29: 54 */   protected float scaleY = 1.0F;
/*  30:    */   
/*  31:    */   public Particle(ParticleSystem engine)
/*  32:    */   {
/*  33: 63 */     this.engine = engine;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public float getX()
/*  37:    */   {
/*  38: 72 */     return this.x;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public float getY()
/*  42:    */   {
/*  43: 81 */     return this.y;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void move(float x, float y)
/*  47:    */   {
/*  48: 91 */     this.x += x;
/*  49: 92 */     this.y += y;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public float getSize()
/*  53:    */   {
/*  54:101 */     return this.size;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public Color getColor()
/*  58:    */   {
/*  59:110 */     return this.color;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void setImage(Image image)
/*  63:    */   {
/*  64:120 */     this.image = image;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public float getOriginalLife()
/*  68:    */   {
/*  69:129 */     return this.originalLife;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public float getLife()
/*  73:    */   {
/*  74:138 */     return this.life;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public boolean inUse()
/*  78:    */   {
/*  79:147 */     return this.life > 0.0F;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void render()
/*  83:    */   {
/*  84:154 */     if (((this.engine.usePoints()) && (this.usePoints == 1)) || 
/*  85:155 */       (this.usePoints == 2))
/*  86:    */     {
/*  87:156 */       TextureImpl.bindNone();
/*  88:157 */       GL.glEnable(2832);
/*  89:158 */       GL.glPointSize(this.size / 2.0F);
/*  90:159 */       this.color.bind();
/*  91:160 */       GL.glBegin(0);
/*  92:161 */       GL.glVertex2f(this.x, this.y);
/*  93:162 */       GL.glEnd();
/*  94:    */     }
/*  95:163 */     else if ((this.oriented) || (this.scaleY != 1.0F))
/*  96:    */     {
/*  97:164 */       GL.glPushMatrix();
/*  98:    */       
/*  99:166 */       GL.glTranslatef(this.x, this.y, 0.0F);
/* 100:168 */       if (this.oriented)
/* 101:    */       {
/* 102:169 */         float angle = (float)(Math.atan2(this.y, this.x) * 180.0D / 3.141592653589793D);
/* 103:170 */         GL.glRotatef(angle, 0.0F, 0.0F, 1.0F);
/* 104:    */       }
/* 105:174 */       GL.glScalef(1.0F, this.scaleY, 1.0F);
/* 106:    */       
/* 107:176 */       this.image.draw((int)-(this.size / 2.0F), (int)-(this.size / 2.0F), (int)this.size, 
/* 108:177 */         (int)this.size, this.color);
/* 109:178 */       GL.glPopMatrix();
/* 110:    */     }
/* 111:    */     else
/* 112:    */     {
/* 113:180 */       this.color.bind();
/* 114:181 */       this.image.drawEmbedded((int)(this.x - this.size / 2.0F), (int)(this.y - this.size / 2.0F), 
/* 115:182 */         (int)this.size, (int)this.size);
/* 116:    */     }
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void update(int delta)
/* 120:    */   {
/* 121:193 */     this.emitter.updateParticle(this, delta);
/* 122:194 */     this.life -= delta;
/* 123:196 */     if (this.life > 0.0F)
/* 124:    */     {
/* 125:197 */       this.x += delta * this.velx;
/* 126:198 */       this.y += delta * this.vely;
/* 127:    */     }
/* 128:    */     else
/* 129:    */     {
/* 130:200 */       this.engine.release(this);
/* 131:    */     }
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void init(ParticleEmitter emitter, float life)
/* 135:    */   {
/* 136:213 */     this.x = 0.0F;
/* 137:214 */     this.emitter = emitter;
/* 138:215 */     this.y = 0.0F;
/* 139:216 */     this.velx = 0.0F;
/* 140:217 */     this.vely = 0.0F;
/* 141:218 */     this.size = 10.0F;
/* 142:219 */     this.type = 0;
/* 143:220 */     this.originalLife = (this.life = life);
/* 144:221 */     this.oriented = false;
/* 145:222 */     this.scaleY = 1.0F;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public void setType(int type)
/* 149:    */   {
/* 150:232 */     this.type = type;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void setUsePoint(int usePoints)
/* 154:    */   {
/* 155:245 */     this.usePoints = usePoints;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public int getType()
/* 159:    */   {
/* 160:254 */     return this.type;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void setSize(float size)
/* 164:    */   {
/* 165:264 */     this.size = size;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public void adjustSize(float delta)
/* 169:    */   {
/* 170:274 */     this.size += delta;
/* 171:275 */     this.size = Math.max(0.0F, this.size);
/* 172:    */   }
/* 173:    */   
/* 174:    */   public void setLife(float life)
/* 175:    */   {
/* 176:285 */     this.life = life;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public void adjustLife(float delta)
/* 180:    */   {
/* 181:295 */     this.life += delta;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public void kill()
/* 185:    */   {
/* 186:303 */     this.life = 1.0F;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public void setColor(float r, float g, float b, float a)
/* 190:    */   {
/* 191:319 */     if (this.color == Color.white)
/* 192:    */     {
/* 193:320 */       this.color = new Color(r, g, b, a);
/* 194:    */     }
/* 195:    */     else
/* 196:    */     {
/* 197:322 */       this.color.r = r;
/* 198:323 */       this.color.g = g;
/* 199:324 */       this.color.b = b;
/* 200:325 */       this.color.a = a;
/* 201:    */     }
/* 202:    */   }
/* 203:    */   
/* 204:    */   public void setPosition(float x, float y)
/* 205:    */   {
/* 206:338 */     this.x = x;
/* 207:339 */     this.y = y;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public void setVelocity(float dirx, float diry, float speed)
/* 211:    */   {
/* 212:353 */     this.velx = (dirx * speed);
/* 213:354 */     this.vely = (diry * speed);
/* 214:    */   }
/* 215:    */   
/* 216:    */   public void setSpeed(float speed)
/* 217:    */   {
/* 218:363 */     float currentSpeed = (float)Math.sqrt(this.velx * this.velx + this.vely * this.vely);
/* 219:364 */     this.velx *= speed;
/* 220:365 */     this.vely *= speed;
/* 221:366 */     this.velx /= currentSpeed;
/* 222:367 */     this.vely /= currentSpeed;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public void setVelocity(float velx, float vely)
/* 226:    */   {
/* 227:377 */     setVelocity(velx, vely, 1.0F);
/* 228:    */   }
/* 229:    */   
/* 230:    */   public void adjustPosition(float dx, float dy)
/* 231:    */   {
/* 232:389 */     this.x += dx;
/* 233:390 */     this.y += dy;
/* 234:    */   }
/* 235:    */   
/* 236:    */   public void adjustColor(float r, float g, float b, float a)
/* 237:    */   {
/* 238:406 */     if (this.color == Color.white) {
/* 239:407 */       this.color = new Color(1.0F, 1.0F, 1.0F, 1.0F);
/* 240:    */     }
/* 241:409 */     this.color.r += r;
/* 242:410 */     this.color.g += g;
/* 243:411 */     this.color.b += b;
/* 244:412 */     this.color.a += a;
/* 245:    */   }
/* 246:    */   
/* 247:    */   public void adjustColor(int r, int g, int b, int a)
/* 248:    */   {
/* 249:428 */     if (this.color == Color.white) {
/* 250:429 */       this.color = new Color(1.0F, 1.0F, 1.0F, 1.0F);
/* 251:    */     }
/* 252:432 */     this.color.r += r / 255.0F;
/* 253:433 */     this.color.g += g / 255.0F;
/* 254:434 */     this.color.b += b / 255.0F;
/* 255:435 */     this.color.a += a / 255.0F;
/* 256:    */   }
/* 257:    */   
/* 258:    */   public void adjustVelocity(float dx, float dy)
/* 259:    */   {
/* 260:447 */     this.velx += dx;
/* 261:448 */     this.vely += dy;
/* 262:    */   }
/* 263:    */   
/* 264:    */   public ParticleEmitter getEmitter()
/* 265:    */   {
/* 266:457 */     return this.emitter;
/* 267:    */   }
/* 268:    */   
/* 269:    */   public String toString()
/* 270:    */   {
/* 271:464 */     return super.toString() + " : " + this.life;
/* 272:    */   }
/* 273:    */   
/* 274:    */   public boolean isOriented()
/* 275:    */   {
/* 276:473 */     return this.oriented;
/* 277:    */   }
/* 278:    */   
/* 279:    */   public void setOriented(boolean oriented)
/* 280:    */   {
/* 281:482 */     this.oriented = oriented;
/* 282:    */   }
/* 283:    */   
/* 284:    */   public float getScaleY()
/* 285:    */   {
/* 286:491 */     return this.scaleY;
/* 287:    */   }
/* 288:    */   
/* 289:    */   public void setScaleY(float scaleY)
/* 290:    */   {
/* 291:500 */     this.scaleY = scaleY;
/* 292:    */   }
/* 293:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.particles.Particle
 * JD-Core Version:    0.7.0.1
 */