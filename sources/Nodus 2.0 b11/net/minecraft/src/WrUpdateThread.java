/*   1:    */ package net.minecraft.src;
/*   2:    */ 
/*   3:    */ import java.util.LinkedList;
/*   4:    */ import java.util.List;
/*   5:    */ import net.minecraft.client.renderer.Tessellator;
/*   6:    */ import net.minecraft.client.renderer.WorldRenderer;
/*   7:    */ import org.lwjgl.opengl.Pbuffer;
/*   8:    */ 
/*   9:    */ public class WrUpdateThread
/*  10:    */   extends Thread
/*  11:    */ {
/*  12: 11 */   private Pbuffer pbuffer = null;
/*  13: 12 */   private Object lock = new Object();
/*  14: 13 */   private List updateList = new LinkedList();
/*  15: 14 */   private List updatedList = new LinkedList();
/*  16: 15 */   private int updateCount = 0;
/*  17:    */   private Tessellator mainTessellator;
/*  18:    */   private Tessellator threadTessellator;
/*  19:    */   private boolean working;
/*  20:    */   private WorldRendererThreaded currentRenderer;
/*  21:    */   private boolean canWork;
/*  22:    */   private boolean canWorkToEndOfUpdate;
/*  23:    */   private boolean terminated;
/*  24:    */   private static final int MAX_UPDATE_CAPACITY = 10;
/*  25:    */   
/*  26:    */   public WrUpdateThread(Pbuffer pbuffer)
/*  27:    */   {
/*  28: 27 */     super("WrUpdateThread");
/*  29: 28 */     this.mainTessellator = Tessellator.instance;
/*  30: 29 */     this.threadTessellator = new Tessellator(2097152);
/*  31: 30 */     this.working = false;
/*  32: 31 */     this.currentRenderer = null;
/*  33: 32 */     this.canWork = false;
/*  34: 33 */     this.canWorkToEndOfUpdate = false;
/*  35: 34 */     this.terminated = false;
/*  36: 35 */     this.pbuffer = pbuffer;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void run()
/*  40:    */   {
/*  41:    */     try
/*  42:    */     {
/*  43: 42 */       this.pbuffer.makeCurrent();
/*  44:    */     }
/*  45:    */     catch (Exception var8)
/*  46:    */     {
/*  47: 46 */       var8.printStackTrace();
/*  48:    */     }
/*  49: 49 */     ThreadUpdateListener updateListener = new ThreadUpdateListener(null);
/*  50: 51 */     while ((!Thread.interrupted()) && (!this.terminated)) {
/*  51:    */       try
/*  52:    */       {
/*  53: 55 */         WorldRendererThreaded e = getRendererToUpdate();
/*  54: 57 */         if (e == null) {
/*  55: 59 */           return;
/*  56:    */         }
/*  57: 62 */         checkCanWork(null);
/*  58:    */         try
/*  59:    */         {
/*  60: 66 */           this.currentRenderer = e;
/*  61: 67 */           Tessellator.instance = this.threadTessellator;
/*  62: 68 */           e.updateRenderer(updateListener);
/*  63:    */         }
/*  64:    */         finally
/*  65:    */         {
/*  66: 72 */           Tessellator.instance = this.mainTessellator;
/*  67:    */         }
/*  68: 75 */         rendererUpdated(e);
/*  69:    */       }
/*  70:    */       catch (Exception var9)
/*  71:    */       {
/*  72: 79 */         var9.printStackTrace();
/*  73: 81 */         if (this.currentRenderer != null)
/*  74:    */         {
/*  75: 83 */           this.currentRenderer.isUpdating = false;
/*  76: 84 */           this.currentRenderer.needsUpdate = true;
/*  77:    */         }
/*  78: 87 */         this.currentRenderer = null;
/*  79: 88 */         this.working = false;
/*  80:    */       }
/*  81:    */     }
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void addRendererToUpdate(WorldRenderer wr, boolean first)
/*  85:    */   {
/*  86: 95 */     Object var3 = this.lock;
/*  87: 97 */     synchronized (this.lock)
/*  88:    */     {
/*  89: 99 */       if (wr.isUpdating) {
/*  90:101 */         throw new IllegalArgumentException("Renderer already updating");
/*  91:    */       }
/*  92:105 */       if (first) {
/*  93:107 */         this.updateList.add(0, wr);
/*  94:    */       } else {
/*  95:111 */         this.updateList.add(wr);
/*  96:    */       }
/*  97:114 */       wr.isUpdating = true;
/*  98:115 */       this.lock.notifyAll();
/*  99:    */     }
/* 100:    */   }
/* 101:    */   
/* 102:    */   private WorldRendererThreaded getRendererToUpdate()
/* 103:    */   {
/* 104:122 */     Object var1 = this.lock;
/* 105:124 */     synchronized (this.lock)
/* 106:    */     {
/* 107:126 */       while (this.updateList.size() <= 0) {
/* 108:    */         try
/* 109:    */         {
/* 110:130 */           this.lock.wait(2000L);
/* 111:132 */           if (this.terminated)
/* 112:    */           {
/* 113:134 */             Object var10000 = null;
/* 114:135 */             return (WorldRendererThreaded)var10000;
/* 115:    */           }
/* 116:    */         }
/* 117:    */         catch (InterruptedException localInterruptedException) {}
/* 118:    */       }
/* 119:144 */       WorldRendererThreaded wrt = (WorldRendererThreaded)this.updateList.remove(0);
/* 120:145 */       this.lock.notifyAll();
/* 121:146 */       return wrt;
/* 122:    */     }
/* 123:    */   }
/* 124:    */   
/* 125:    */   public boolean hasWorkToDo()
/* 126:    */   {
/* 127:152 */     Object var1 = this.lock;
/* 128:154 */     synchronized (this.lock)
/* 129:    */     {
/* 130:156 */       return this.currentRenderer != null ? true : this.updateList.size() > 0 ? true : this.working;
/* 131:    */     }
/* 132:    */   }
/* 133:    */   
/* 134:    */   public int getUpdateCapacity()
/* 135:    */   {
/* 136:162 */     Object var1 = this.lock;
/* 137:164 */     synchronized (this.lock)
/* 138:    */     {
/* 139:166 */       return this.updateList.size() > 10 ? 0 : 10 - this.updateList.size();
/* 140:    */     }
/* 141:    */   }
/* 142:    */   
/* 143:    */   private void rendererUpdated(WorldRenderer wr)
/* 144:    */   {
/* 145:172 */     Object var2 = this.lock;
/* 146:174 */     synchronized (this.lock)
/* 147:    */     {
/* 148:176 */       this.updatedList.add(wr);
/* 149:177 */       this.updateCount += 1;
/* 150:178 */       this.currentRenderer = null;
/* 151:179 */       this.working = false;
/* 152:180 */       this.lock.notifyAll();
/* 153:    */     }
/* 154:    */   }
/* 155:    */   
/* 156:    */   private void finishUpdatedRenderers()
/* 157:    */   {
/* 158:186 */     Object var1 = this.lock;
/* 159:188 */     synchronized (this.lock)
/* 160:    */     {
/* 161:190 */       for (int i = 0; i < this.updatedList.size(); i++)
/* 162:    */       {
/* 163:192 */         WorldRendererThreaded wr = (WorldRendererThreaded)this.updatedList.get(i);
/* 164:193 */         wr.finishUpdate();
/* 165:194 */         wr.isUpdating = false;
/* 166:    */       }
/* 167:197 */       this.updatedList.clear();
/* 168:    */     }
/* 169:    */   }
/* 170:    */   
/* 171:    */   public void pause()
/* 172:    */   {
/* 173:203 */     Object var1 = this.lock;
/* 174:205 */     synchronized (this.lock)
/* 175:    */     {
/* 176:207 */       this.canWork = false;
/* 177:208 */       this.canWorkToEndOfUpdate = false;
/* 178:209 */       this.lock.notifyAll();
/* 179:211 */       while (this.working) {
/* 180:    */         try
/* 181:    */         {
/* 182:215 */           this.lock.wait();
/* 183:    */         }
/* 184:    */         catch (InterruptedException localInterruptedException) {}
/* 185:    */       }
/* 186:223 */       finishUpdatedRenderers();
/* 187:    */     }
/* 188:    */   }
/* 189:    */   
/* 190:    */   public void unpause()
/* 191:    */   {
/* 192:229 */     Object var1 = this.lock;
/* 193:231 */     synchronized (this.lock)
/* 194:    */     {
/* 195:233 */       if (this.working) {
/* 196:235 */         Config.warn("UpdateThread still working in unpause()!!!");
/* 197:    */       }
/* 198:238 */       this.canWork = true;
/* 199:239 */       this.canWorkToEndOfUpdate = false;
/* 200:240 */       this.lock.notifyAll();
/* 201:    */     }
/* 202:    */   }
/* 203:    */   
/* 204:    */   public void unpauseToEndOfUpdate()
/* 205:    */   {
/* 206:246 */     Object var1 = this.lock;
/* 207:248 */     synchronized (this.lock)
/* 208:    */     {
/* 209:250 */       if (this.working) {
/* 210:252 */         Config.warn("UpdateThread still working in unpause()!!!");
/* 211:    */       }
/* 212:255 */       if (this.currentRenderer != null)
/* 213:    */       {
/* 214:257 */         while (this.currentRenderer != null)
/* 215:    */         {
/* 216:259 */           this.canWork = false;
/* 217:260 */           this.canWorkToEndOfUpdate = true;
/* 218:261 */           this.lock.notifyAll();
/* 219:    */           try
/* 220:    */           {
/* 221:265 */             this.lock.wait();
/* 222:    */           }
/* 223:    */           catch (InterruptedException localInterruptedException) {}
/* 224:    */         }
/* 225:273 */         pause();
/* 226:    */       }
/* 227:    */     }
/* 228:    */   }
/* 229:    */   
/* 230:    */   private void checkCanWork(IWrUpdateControl uc)
/* 231:    */   {
/* 232:280 */     Thread.yield();
/* 233:281 */     Object var2 = this.lock;
/* 234:283 */     synchronized (this.lock)
/* 235:    */     {
/* 236:285 */       while ((!this.canWork) && ((!this.canWorkToEndOfUpdate) || (this.currentRenderer == null)))
/* 237:    */       {
/* 238:287 */         if (uc != null) {
/* 239:289 */           uc.pause();
/* 240:    */         }
/* 241:292 */         this.working = false;
/* 242:293 */         this.lock.notifyAll();
/* 243:    */         try
/* 244:    */         {
/* 245:297 */           this.lock.wait();
/* 246:    */         }
/* 247:    */         catch (InterruptedException localInterruptedException) {}
/* 248:    */       }
/* 249:305 */       this.working = true;
/* 250:307 */       if (uc != null) {
/* 251:309 */         uc.resume();
/* 252:    */       }
/* 253:312 */       this.lock.notifyAll();
/* 254:    */     }
/* 255:    */   }
/* 256:    */   
/* 257:    */   public void clearAllUpdates()
/* 258:    */   {
/* 259:318 */     Object var1 = this.lock;
/* 260:320 */     synchronized (this.lock)
/* 261:    */     {
/* 262:322 */       unpauseToEndOfUpdate();
/* 263:324 */       for (int i = 0; i < this.updateList.size(); i++)
/* 264:    */       {
/* 265:326 */         WorldRenderer wr = (WorldRenderer)this.updateList.get(i);
/* 266:327 */         wr.needsUpdate = true;
/* 267:328 */         wr.isUpdating = false;
/* 268:    */       }
/* 269:331 */       this.updateList.clear();
/* 270:332 */       this.lock.notifyAll();
/* 271:    */     }
/* 272:    */   }
/* 273:    */   
/* 274:    */   public int getPendingUpdatesCount()
/* 275:    */   {
/* 276:338 */     Object var1 = this.lock;
/* 277:340 */     synchronized (this.lock)
/* 278:    */     {
/* 279:342 */       int count = this.updateList.size();
/* 280:344 */       if (this.currentRenderer != null) {
/* 281:346 */         count++;
/* 282:    */       }
/* 283:349 */       return count;
/* 284:    */     }
/* 285:    */   }
/* 286:    */   
/* 287:    */   public int resetUpdateCount()
/* 288:    */   {
/* 289:355 */     Object var1 = this.lock;
/* 290:357 */     synchronized (this.lock)
/* 291:    */     {
/* 292:359 */       int count = this.updateCount;
/* 293:360 */       this.updateCount = 0;
/* 294:361 */       return count;
/* 295:    */     }
/* 296:    */   }
/* 297:    */   
/* 298:    */   public void terminate()
/* 299:    */   {
/* 300:367 */     this.terminated = true;
/* 301:    */   }
/* 302:    */   
/* 303:    */   private class ThreadUpdateListener
/* 304:    */     implements IWrUpdateListener
/* 305:    */   {
/* 306:    */     private WrUpdateThread.ThreadUpdateControl tuc;
/* 307:    */     
/* 308:    */     private ThreadUpdateListener()
/* 309:    */     {
/* 310:376 */       WrUpdateThread tmp15_14 = WrUpdateThread.this;tmp15_14.getClass();this.tuc = new WrUpdateThread.ThreadUpdateControl(tmp15_14, null);
/* 311:    */     }
/* 312:    */     
/* 313:    */     public void updating(IWrUpdateControl uc)
/* 314:    */     {
/* 315:381 */       this.tuc.setUpdateControl(uc);
/* 316:382 */       WrUpdateThread.this.checkCanWork(this.tuc);
/* 317:    */     }
/* 318:    */     
/* 319:    */     ThreadUpdateListener(WrUpdateThread.NamelessClass454412650 x1)
/* 320:    */     {
/* 321:387 */       this();
/* 322:    */     }
/* 323:    */   }
/* 324:    */   
/* 325:    */   private class ThreadUpdateControl
/* 326:    */     implements IWrUpdateControl
/* 327:    */   {
/* 328:    */     private IWrUpdateControl updateControl;
/* 329:    */     private boolean paused;
/* 330:    */     
/* 331:    */     private ThreadUpdateControl()
/* 332:    */     {
/* 333:398 */       this.updateControl = null;
/* 334:399 */       this.paused = false;
/* 335:    */     }
/* 336:    */     
/* 337:    */     public void pause()
/* 338:    */     {
/* 339:404 */       if (!this.paused)
/* 340:    */       {
/* 341:406 */         this.paused = true;
/* 342:407 */         this.updateControl.pause();
/* 343:408 */         Tessellator.instance = WrUpdateThread.this.mainTessellator;
/* 344:    */       }
/* 345:    */     }
/* 346:    */     
/* 347:    */     public void resume()
/* 348:    */     {
/* 349:414 */       if (this.paused)
/* 350:    */       {
/* 351:416 */         this.paused = false;
/* 352:417 */         Tessellator.instance = WrUpdateThread.this.threadTessellator;
/* 353:418 */         this.updateControl.resume();
/* 354:    */       }
/* 355:    */     }
/* 356:    */     
/* 357:    */     public void setUpdateControl(IWrUpdateControl updateControl)
/* 358:    */     {
/* 359:424 */       this.updateControl = updateControl;
/* 360:    */     }
/* 361:    */     
/* 362:    */     ThreadUpdateControl(WrUpdateThread.NamelessClass454412650 x1)
/* 363:    */     {
/* 364:429 */       this();
/* 365:    */     }
/* 366:    */   }
/* 367:    */   
/* 368:    */   static class NamelessClass454412650 {}
/* 369:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.src.WrUpdateThread
 * JD-Core Version:    0.7.0.1
 */