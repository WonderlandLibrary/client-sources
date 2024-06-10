/*   1:    */ package net.minecraft.src;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import net.minecraft.client.renderer.RenderGlobal;
/*   5:    */ import net.minecraft.client.renderer.WorldRenderer;
/*   6:    */ import net.minecraft.entity.EntityLivingBase;
/*   7:    */ import net.minecraft.world.World;
/*   8:    */ import org.lwjgl.opengl.Display;
/*   9:    */ import org.lwjgl.opengl.Drawable;
/*  10:    */ import org.lwjgl.opengl.Pbuffer;
/*  11:    */ import org.lwjgl.opengl.PixelFormat;
/*  12:    */ 
/*  13:    */ public class WrUpdaterThreaded
/*  14:    */   implements IWrUpdater
/*  15:    */ {
/*  16: 15 */   private WrUpdateThread updateThread = null;
/*  17: 16 */   private float timePerUpdateMs = 10.0F;
/*  18: 17 */   private long updateStartTimeNs = 0L;
/*  19: 18 */   private boolean firstUpdate = true;
/*  20: 19 */   private int updateTargetNum = 0;
/*  21:    */   
/*  22:    */   public void terminate()
/*  23:    */   {
/*  24: 23 */     if (this.updateThread != null)
/*  25:    */     {
/*  26: 25 */       this.updateThread.terminate();
/*  27: 26 */       this.updateThread.unpauseToEndOfUpdate();
/*  28:    */     }
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void initialize() {}
/*  32:    */   
/*  33:    */   private void delayedInit()
/*  34:    */   {
/*  35: 34 */     if (this.updateThread == null) {
/*  36: 36 */       createUpdateThread(Display.getDrawable());
/*  37:    */     }
/*  38:    */   }
/*  39:    */   
/*  40:    */   public WorldRenderer makeWorldRenderer(World worldObj, List tileEntities, int x, int y, int z, int glRenderListBase, int rendererIndex)
/*  41:    */   {
/*  42: 42 */     return new WorldRendererThreaded(worldObj, tileEntities, x, y, z, glRenderListBase, rendererIndex);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public WrUpdateThread createUpdateThread(Drawable displayDrawable)
/*  46:    */   {
/*  47: 47 */     if (this.updateThread != null) {
/*  48: 49 */       throw new IllegalStateException("UpdateThread is already existing");
/*  49:    */     }
/*  50:    */     try
/*  51:    */     {
/*  52: 55 */       Pbuffer e = new Pbuffer(1, 1, new PixelFormat(), displayDrawable);
/*  53: 56 */       this.updateThread = new WrUpdateThread(e);
/*  54: 57 */       this.updateThread.setPriority(1);
/*  55: 58 */       this.updateThread.start();
/*  56: 59 */       this.updateThread.pause();
/*  57: 60 */       return this.updateThread;
/*  58:    */     }
/*  59:    */     catch (Exception var3)
/*  60:    */     {
/*  61: 64 */       throw new RuntimeException(var3);
/*  62:    */     }
/*  63:    */   }
/*  64:    */   
/*  65:    */   public boolean isUpdateThread()
/*  66:    */   {
/*  67: 71 */     return Thread.currentThread() == this.updateThread;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public static boolean isBackgroundChunkLoading()
/*  71:    */   {
/*  72: 76 */     return true;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void preRender(RenderGlobal rg, EntityLivingBase player)
/*  76:    */   {
/*  77: 81 */     this.updateTargetNum = 0;
/*  78: 83 */     if (this.updateThread != null)
/*  79:    */     {
/*  80: 85 */       if (this.updateStartTimeNs == 0L) {
/*  81: 87 */         this.updateStartTimeNs = System.nanoTime();
/*  82:    */       }
/*  83: 90 */       if (this.updateThread.hasWorkToDo())
/*  84:    */       {
/*  85: 92 */         this.updateTargetNum = Config.getUpdatesPerFrame();
/*  86: 94 */         if ((Config.isDynamicUpdates()) && (!rg.isMoving(player))) {
/*  87: 96 */           this.updateTargetNum *= 3;
/*  88:    */         }
/*  89: 99 */         this.updateTargetNum = Math.min(this.updateTargetNum, this.updateThread.getPendingUpdatesCount());
/*  90:101 */         if (this.updateTargetNum > 0) {
/*  91:103 */           this.updateThread.unpause();
/*  92:    */         }
/*  93:    */       }
/*  94:    */     }
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void postRender()
/*  98:    */   {
/*  99:111 */     if (this.updateThread != null)
/* 100:    */     {
/* 101:113 */       float sleepTimeMs = 0.0F;
/* 102:115 */       if (this.updateTargetNum > 0)
/* 103:    */       {
/* 104:117 */         long deltaTime = System.nanoTime() - this.updateStartTimeNs;
/* 105:118 */         float targetRunTime = this.timePerUpdateMs * (1.0F + (this.updateTargetNum - 1) / 2.0F);
/* 106:120 */         if (targetRunTime > 0.0F)
/* 107:    */         {
/* 108:122 */           int sleepTimeMsInt = (int)targetRunTime;
/* 109:123 */           Config.sleep(sleepTimeMsInt);
/* 110:    */         }
/* 111:126 */         this.updateThread.pause();
/* 112:    */       }
/* 113:129 */       float deltaTime1 = 0.2F;
/* 114:131 */       if (this.updateTargetNum > 0)
/* 115:    */       {
/* 116:133 */         int updateCount = this.updateThread.resetUpdateCount();
/* 117:135 */         if (updateCount < this.updateTargetNum) {
/* 118:137 */           this.timePerUpdateMs += deltaTime1;
/* 119:    */         }
/* 120:140 */         if (updateCount > this.updateTargetNum) {
/* 121:142 */           this.timePerUpdateMs -= deltaTime1;
/* 122:    */         }
/* 123:145 */         if (updateCount == this.updateTargetNum) {
/* 124:147 */           this.timePerUpdateMs -= deltaTime1;
/* 125:    */         }
/* 126:    */       }
/* 127:    */       else
/* 128:    */       {
/* 129:152 */         this.timePerUpdateMs -= deltaTime1 / 5.0F;
/* 130:    */       }
/* 131:155 */       if (this.timePerUpdateMs < 0.0F) {
/* 132:157 */         this.timePerUpdateMs = 0.0F;
/* 133:    */       }
/* 134:160 */       this.updateStartTimeNs = System.nanoTime();
/* 135:    */     }
/* 136:    */   }
/* 137:    */   
/* 138:    */   public boolean updateRenderers(RenderGlobal rg, EntityLivingBase entityliving, boolean flag)
/* 139:    */   {
/* 140:166 */     delayedInit();
/* 141:168 */     if (rg.worldRenderersToUpdate.size() <= 0) {
/* 142:170 */       return true;
/* 143:    */     }
/* 144:174 */     int num = 0;
/* 145:175 */     byte NOT_IN_FRUSTRUM_MUL = 4;
/* 146:176 */     int numValid = 0;
/* 147:177 */     WorldRenderer wrBest = null;
/* 148:178 */     float distSqBest = 3.4028235E+38F;
/* 149:179 */     int indexBest = -1;
/* 150:183 */     for (int maxUpdateNum = 0; maxUpdateNum < rg.worldRenderersToUpdate.size(); maxUpdateNum++)
/* 151:    */     {
/* 152:185 */       WorldRenderer turboMode = (WorldRenderer)rg.worldRenderersToUpdate.get(maxUpdateNum);
/* 153:187 */       if (turboMode != null)
/* 154:    */       {
/* 155:189 */         numValid++;
/* 156:191 */         if (!turboMode.isUpdating) {
/* 157:193 */           if (!turboMode.needsUpdate)
/* 158:    */           {
/* 159:195 */             rg.worldRenderersToUpdate.set(maxUpdateNum, null);
/* 160:    */           }
/* 161:    */           else
/* 162:    */           {
/* 163:199 */             float dstIndex = turboMode.distanceToEntitySquared(entityliving);
/* 164:201 */             if (dstIndex < 512.0F)
/* 165:    */             {
/* 166:203 */               if (((dstIndex < 256.0F) && (rg.isActingNow()) && (turboMode.isInFrustum)) || (this.firstUpdate))
/* 167:    */               {
/* 168:205 */                 if (this.updateThread != null) {
/* 169:207 */                   this.updateThread.unpauseToEndOfUpdate();
/* 170:    */                 }
/* 171:210 */                 turboMode.updateRenderer(entityliving);
/* 172:211 */                 turboMode.needsUpdate = false;
/* 173:212 */                 rg.worldRenderersToUpdate.set(maxUpdateNum, null);
/* 174:213 */                 num++;
/* 175:214 */                 continue;
/* 176:    */               }
/* 177:217 */               if (this.updateThread != null)
/* 178:    */               {
/* 179:219 */                 this.updateThread.addRendererToUpdate(turboMode, true);
/* 180:220 */                 turboMode.needsUpdate = false;
/* 181:221 */                 rg.worldRenderersToUpdate.set(maxUpdateNum, null);
/* 182:222 */                 num++;
/* 183:223 */                 continue;
/* 184:    */               }
/* 185:    */             }
/* 186:227 */             if (!turboMode.isInFrustum) {
/* 187:229 */               dstIndex *= NOT_IN_FRUSTRUM_MUL;
/* 188:    */             }
/* 189:232 */             if (wrBest == null)
/* 190:    */             {
/* 191:234 */               wrBest = turboMode;
/* 192:235 */               distSqBest = dstIndex;
/* 193:236 */               indexBest = maxUpdateNum;
/* 194:    */             }
/* 195:238 */             else if (dstIndex < distSqBest)
/* 196:    */             {
/* 197:240 */               wrBest = turboMode;
/* 198:241 */               distSqBest = dstIndex;
/* 199:242 */               indexBest = maxUpdateNum;
/* 200:    */             }
/* 201:    */           }
/* 202:    */         }
/* 203:    */       }
/* 204:    */     }
/* 205:249 */     maxUpdateNum = Config.getUpdatesPerFrame();
/* 206:250 */     boolean var17 = false;
/* 207:252 */     if ((Config.isDynamicUpdates()) && (!rg.isMoving(entityliving)))
/* 208:    */     {
/* 209:254 */       maxUpdateNum *= 3;
/* 210:255 */       var17 = true;
/* 211:    */     }
/* 212:258 */     if (this.updateThread != null)
/* 213:    */     {
/* 214:260 */       maxUpdateNum = this.updateThread.getUpdateCapacity();
/* 215:262 */       if (maxUpdateNum <= 0) {
/* 216:264 */         return true;
/* 217:    */       }
/* 218:    */     }
/* 219:270 */     if (wrBest != null)
/* 220:    */     {
/* 221:272 */       updateRenderer(wrBest, entityliving);
/* 222:273 */       rg.worldRenderersToUpdate.set(indexBest, null);
/* 223:274 */       num++;
/* 224:275 */       float dstIndex = distSqBest / 5.0F;
/* 225:277 */       for (int i = 0; (i < rg.worldRenderersToUpdate.size()) && (num < maxUpdateNum); i++)
/* 226:    */       {
/* 227:279 */         WorldRenderer wr = (WorldRenderer)rg.worldRenderersToUpdate.get(i);
/* 228:281 */         if ((wr != null) && (!wr.isUpdating))
/* 229:    */         {
/* 230:283 */           float distSq = wr.distanceToEntitySquared(entityliving);
/* 231:285 */           if (!wr.isInFrustum) {
/* 232:287 */             distSq *= NOT_IN_FRUSTRUM_MUL;
/* 233:    */           }
/* 234:290 */           float diffDistSq = Math.abs(distSq - distSqBest);
/* 235:292 */           if (diffDistSq < dstIndex)
/* 236:    */           {
/* 237:294 */             updateRenderer(wr, entityliving);
/* 238:295 */             rg.worldRenderersToUpdate.set(i, null);
/* 239:296 */             num++;
/* 240:    */           }
/* 241:    */         }
/* 242:    */       }
/* 243:    */     }
/* 244:302 */     if (numValid == 0) {
/* 245:304 */       rg.worldRenderersToUpdate.clear();
/* 246:    */     }
/* 247:307 */     if ((rg.worldRenderersToUpdate.size() > 100) && (numValid < rg.worldRenderersToUpdate.size() * 4 / 5))
/* 248:    */     {
/* 249:309 */       int var18 = 0;
/* 250:311 */       for (int i = 0; i < rg.worldRenderersToUpdate.size(); i++)
/* 251:    */       {
/* 252:313 */         Object var19 = rg.worldRenderersToUpdate.get(i);
/* 253:315 */         if (var19 != null)
/* 254:    */         {
/* 255:317 */           if (i != var18) {
/* 256:319 */             rg.worldRenderersToUpdate.set(var18, var19);
/* 257:    */           }
/* 258:322 */           var18++;
/* 259:    */         }
/* 260:    */       }
/* 261:326 */       for (i = rg.worldRenderersToUpdate.size() - 1; i >= var18; i--) {
/* 262:328 */         rg.worldRenderersToUpdate.remove(i);
/* 263:    */       }
/* 264:    */     }
/* 265:332 */     this.firstUpdate = false;
/* 266:333 */     return true;
/* 267:    */   }
/* 268:    */   
/* 269:    */   private void updateRenderer(WorldRenderer wr, EntityLivingBase entityLiving)
/* 270:    */   {
/* 271:339 */     WrUpdateThread ut = this.updateThread;
/* 272:341 */     if (ut != null)
/* 273:    */     {
/* 274:343 */       ut.addRendererToUpdate(wr, false);
/* 275:344 */       wr.needsUpdate = false;
/* 276:    */     }
/* 277:    */     else
/* 278:    */     {
/* 279:348 */       wr.updateRenderer(entityLiving);
/* 280:349 */       wr.needsUpdate = false;
/* 281:350 */       wr.isUpdating = false;
/* 282:    */     }
/* 283:    */   }
/* 284:    */   
/* 285:    */   public void finishCurrentUpdate()
/* 286:    */   {
/* 287:356 */     if (this.updateThread != null) {
/* 288:358 */       this.updateThread.unpauseToEndOfUpdate();
/* 289:    */     }
/* 290:    */   }
/* 291:    */   
/* 292:    */   public void resumeBackgroundUpdates()
/* 293:    */   {
/* 294:364 */     if (this.updateThread != null) {
/* 295:366 */       this.updateThread.unpause();
/* 296:    */     }
/* 297:    */   }
/* 298:    */   
/* 299:    */   public void pauseBackgroundUpdates()
/* 300:    */   {
/* 301:372 */     if (this.updateThread != null) {
/* 302:374 */       this.updateThread.pause();
/* 303:    */     }
/* 304:    */   }
/* 305:    */   
/* 306:    */   public void clearAllUpdates()
/* 307:    */   {
/* 308:380 */     if (this.updateThread != null) {
/* 309:382 */       this.updateThread.clearAllUpdates();
/* 310:    */     }
/* 311:385 */     this.firstUpdate = true;
/* 312:    */   }
/* 313:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.src.WrUpdaterThreaded
 * JD-Core Version:    0.7.0.1
 */