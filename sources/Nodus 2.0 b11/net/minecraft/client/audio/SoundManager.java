/*   1:    */ package net.minecraft.client.audio;
/*   2:    */ 
/*   3:    */ import com.google.common.collect.BiMap;
/*   4:    */ import com.google.common.collect.HashBiMap;
/*   5:    */ import com.google.common.collect.HashMultimap;
/*   6:    */ import com.google.common.collect.Lists;
/*   7:    */ import com.google.common.collect.Maps;
/*   8:    */ import com.google.common.collect.Multimap;
/*   9:    */ import java.io.IOException;
/*  10:    */ import java.io.InputStream;
/*  11:    */ import java.net.MalformedURLException;
/*  12:    */ import java.net.URL;
/*  13:    */ import java.net.URLConnection;
/*  14:    */ import java.net.URLStreamHandler;
/*  15:    */ import java.util.Collection;
/*  16:    */ import java.util.HashMap;
/*  17:    */ import java.util.Iterator;
/*  18:    */ import java.util.List;
/*  19:    */ import java.util.Map;
/*  20:    */ import java.util.Map.Entry;
/*  21:    */ import java.util.Set;
/*  22:    */ import java.util.UUID;
/*  23:    */ import net.minecraft.client.Minecraft;
/*  24:    */ import net.minecraft.client.resources.IResource;
/*  25:    */ import net.minecraft.client.resources.IResourceManager;
/*  26:    */ import net.minecraft.client.settings.GameSettings;
/*  27:    */ import net.minecraft.entity.player.EntityPlayer;
/*  28:    */ import net.minecraft.util.MathHelper;
/*  29:    */ import net.minecraft.util.ResourceLocation;
/*  30:    */ import org.apache.logging.log4j.LogManager;
/*  31:    */ import org.apache.logging.log4j.Logger;
/*  32:    */ import org.apache.logging.log4j.Marker;
/*  33:    */ import org.apache.logging.log4j.MarkerManager;
/*  34:    */ import paulscode.sound.Library;
/*  35:    */ import paulscode.sound.SoundSystem;
/*  36:    */ import paulscode.sound.SoundSystemConfig;
/*  37:    */ import paulscode.sound.SoundSystemException;
/*  38:    */ import paulscode.sound.Source;
/*  39:    */ import paulscode.sound.codecs.CodecJOrbis;
/*  40:    */ import paulscode.sound.libraries.LibraryLWJGLOpenAL;
/*  41:    */ 
/*  42:    */ public class SoundManager
/*  43:    */ {
/*  44: 38 */   private static final Marker field_148623_a = MarkerManager.getMarker("SOUNDS");
/*  45: 39 */   private static final Logger logger = LogManager.getLogger();
/*  46:    */   private final SoundHandler field_148622_c;
/*  47:    */   private final GameSettings field_148619_d;
/*  48:    */   private SoundSystemStarterThread field_148620_e;
/*  49:    */   private boolean field_148617_f;
/*  50: 44 */   private int field_148618_g = 0;
/*  51: 45 */   private final Map field_148629_h = HashBiMap.create();
/*  52:    */   private final Map field_148630_i;
/*  53:    */   private Map field_148627_j;
/*  54:    */   private final Multimap field_148628_k;
/*  55:    */   private final List field_148625_l;
/*  56:    */   private final Map field_148626_m;
/*  57:    */   private final Map field_148624_n;
/*  58:    */   private static final String __OBFID = "CL_00001141";
/*  59:    */   
/*  60:    */   public SoundManager(SoundHandler p_i45119_1_, GameSettings p_i45119_2_)
/*  61:    */   {
/*  62: 56 */     this.field_148630_i = ((BiMap)this.field_148629_h).inverse();
/*  63: 57 */     this.field_148627_j = Maps.newHashMap();
/*  64: 58 */     this.field_148628_k = HashMultimap.create();
/*  65: 59 */     this.field_148625_l = Lists.newArrayList();
/*  66: 60 */     this.field_148626_m = Maps.newHashMap();
/*  67: 61 */     this.field_148624_n = Maps.newHashMap();
/*  68: 62 */     this.field_148622_c = p_i45119_1_;
/*  69: 63 */     this.field_148619_d = p_i45119_2_;
/*  70:    */     try
/*  71:    */     {
/*  72: 67 */       SoundSystemConfig.addLibrary(LibraryLWJGLOpenAL.class);
/*  73: 68 */       SoundSystemConfig.setCodec("ogg", CodecJOrbis.class);
/*  74:    */     }
/*  75:    */     catch (SoundSystemException var4)
/*  76:    */     {
/*  77: 72 */       logger.error(field_148623_a, "Error linking with the LibraryJavaSound plug-in", var4);
/*  78:    */     }
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void func_148596_a()
/*  82:    */   {
/*  83: 78 */     func_148613_b();
/*  84: 79 */     func_148608_i();
/*  85:    */   }
/*  86:    */   
/*  87:    */   private synchronized void func_148608_i()
/*  88:    */   {
/*  89: 84 */     if (!this.field_148617_f) {
/*  90:    */       try
/*  91:    */       {
/*  92: 98 */         new Thread(new Runnable()
/*  93:    */         {
/*  94:    */           private static final String __OBFID = "CL_00001142";
/*  95:    */           
/*  96:    */           public void run()
/*  97:    */           {
/*  98: 93 */             SoundManager tmp12_9 = SoundManager.this;tmp12_9.getClass();SoundManager.this.field_148620_e = new SoundManager.SoundSystemStarterThread(tmp12_9, null);
/*  99: 94 */             SoundManager.this.field_148617_f = true;
/* 100: 95 */             SoundManager.this.field_148620_e.setMasterVolume(SoundManager.this.field_148619_d.getSoundLevel(SoundCategory.MASTER));
/* 101: 96 */             SoundManager.logger.info(SoundManager.field_148623_a, "Sound engine started");
/* 102:    */           }
/* 103: 98 */         }, "Sound Library Loader").start();
/* 104:    */       }
/* 105:    */       catch (RuntimeException var2)
/* 106:    */       {
/* 107:102 */         logger.error(field_148623_a, "Error starting SoundSystem. Turning off sounds & music", var2);
/* 108:103 */         this.field_148619_d.setSoundLevel(SoundCategory.MASTER, 0.0F);
/* 109:104 */         this.field_148619_d.saveOptions();
/* 110:    */       }
/* 111:    */     }
/* 112:    */   }
/* 113:    */   
/* 114:    */   private float func_148595_a(SoundCategory p_148595_1_)
/* 115:    */   {
/* 116:111 */     return (p_148595_1_ != null) && (p_148595_1_ != SoundCategory.MASTER) ? this.field_148619_d.getSoundLevel(p_148595_1_) : 1.0F;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void func_148601_a(SoundCategory p_148601_1_, float p_148601_2_)
/* 120:    */   {
/* 121:116 */     if (this.field_148617_f) {
/* 122:118 */       if (p_148601_1_ == SoundCategory.MASTER)
/* 123:    */       {
/* 124:120 */         this.field_148620_e.setMasterVolume(p_148601_2_);
/* 125:    */       }
/* 126:    */       else
/* 127:    */       {
/* 128:124 */         Iterator var3 = this.field_148628_k.get(p_148601_1_).iterator();
/* 129:126 */         while (var3.hasNext())
/* 130:    */         {
/* 131:128 */           String var4 = (String)var3.next();
/* 132:129 */           ISound var5 = (ISound)this.field_148629_h.get(var4);
/* 133:130 */           float var6 = func_148594_a(var5, (SoundPoolEntry)this.field_148627_j.get(var5), p_148601_1_);
/* 134:132 */           if (var6 <= 0.0F) {
/* 135:134 */             func_148602_b(var5);
/* 136:    */           } else {
/* 137:138 */             this.field_148620_e.setVolume(var4, var6);
/* 138:    */           }
/* 139:    */         }
/* 140:    */       }
/* 141:    */     }
/* 142:    */   }
/* 143:    */   
/* 144:    */   public void func_148613_b()
/* 145:    */   {
/* 146:147 */     if (this.field_148617_f)
/* 147:    */     {
/* 148:149 */       func_148614_c();
/* 149:150 */       this.field_148620_e.cleanup();
/* 150:151 */       this.field_148617_f = false;
/* 151:    */     }
/* 152:    */   }
/* 153:    */   
/* 154:    */   public void func_148614_c()
/* 155:    */   {
/* 156:157 */     if (this.field_148617_f)
/* 157:    */     {
/* 158:159 */       Iterator var1 = this.field_148629_h.keySet().iterator();
/* 159:161 */       while (var1.hasNext())
/* 160:    */       {
/* 161:163 */         String var2 = (String)var1.next();
/* 162:164 */         this.field_148620_e.stop(var2);
/* 163:    */       }
/* 164:167 */       this.field_148629_h.clear();
/* 165:168 */       this.field_148626_m.clear();
/* 166:169 */       this.field_148625_l.clear();
/* 167:170 */       this.field_148628_k.clear();
/* 168:171 */       this.field_148627_j.clear();
/* 169:172 */       this.field_148624_n.clear();
/* 170:    */     }
/* 171:    */   }
/* 172:    */   
/* 173:    */   public void func_148605_d()
/* 174:    */   {
/* 175:178 */     this.field_148618_g += 1;
/* 176:179 */     Iterator var1 = this.field_148625_l.iterator();
/* 177:182 */     while (var1.hasNext())
/* 178:    */     {
/* 179:184 */       ITickableSound var2 = (ITickableSound)var1.next();
/* 180:185 */       var2.update();
/* 181:187 */       if (var2.func_147667_k())
/* 182:    */       {
/* 183:189 */         func_148602_b(var2);
/* 184:    */       }
/* 185:    */       else
/* 186:    */       {
/* 187:193 */         String var3 = (String)this.field_148630_i.get(var2);
/* 188:194 */         this.field_148620_e.setVolume(var3, func_148594_a(var2, (SoundPoolEntry)this.field_148627_j.get(var2), this.field_148622_c.func_147680_a(var2.func_147650_b()).func_148728_d()));
/* 189:195 */         this.field_148620_e.setPitch(var3, func_148606_a(var2, (SoundPoolEntry)this.field_148627_j.get(var2)));
/* 190:196 */         this.field_148620_e.setPosition(var3, var2.func_147649_g(), var2.func_147654_h(), var2.func_147651_i());
/* 191:    */       }
/* 192:    */     }
/* 193:200 */     var1 = this.field_148629_h.entrySet().iterator();
/* 194:203 */     while (var1.hasNext())
/* 195:    */     {
/* 196:205 */       Map.Entry var9 = (Map.Entry)var1.next();
/* 197:206 */       String var3 = (String)var9.getKey();
/* 198:207 */       ISound var4 = (ISound)var9.getValue();
/* 199:209 */       if (!this.field_148620_e.playing(var3))
/* 200:    */       {
/* 201:211 */         int var5 = ((Integer)this.field_148624_n.get(var3)).intValue();
/* 202:213 */         if (var5 <= this.field_148618_g)
/* 203:    */         {
/* 204:215 */           int var6 = var4.func_147652_d();
/* 205:217 */           if ((var4.func_147657_c()) && (var6 > 0)) {
/* 206:219 */             this.field_148626_m.put(var4, Integer.valueOf(this.field_148618_g + var6));
/* 207:    */           }
/* 208:222 */           var1.remove();
/* 209:223 */           logger.debug(field_148623_a, "Removed channel {} because it's not playing anymore", new Object[] { var3 });
/* 210:224 */           this.field_148620_e.removeSource(var3);
/* 211:225 */           this.field_148624_n.remove(var3);
/* 212:226 */           this.field_148627_j.remove(var4);
/* 213:    */           try
/* 214:    */           {
/* 215:230 */             this.field_148628_k.remove(this.field_148622_c.func_147680_a(var4.func_147650_b()).func_148728_d(), var3);
/* 216:    */           }
/* 217:    */           catch (RuntimeException localRuntimeException) {}
/* 218:237 */           if ((var4 instanceof ITickableSound)) {
/* 219:239 */             this.field_148625_l.remove(var4);
/* 220:    */           }
/* 221:    */         }
/* 222:    */       }
/* 223:    */     }
/* 224:245 */     Iterator var10 = this.field_148626_m.entrySet().iterator();
/* 225:247 */     while (var10.hasNext())
/* 226:    */     {
/* 227:249 */       Map.Entry var11 = (Map.Entry)var10.next();
/* 228:251 */       if (this.field_148618_g >= ((Integer)var11.getValue()).intValue())
/* 229:    */       {
/* 230:253 */         ISound var4 = (ISound)var11.getKey();
/* 231:255 */         if ((var4 instanceof ITickableSound)) {
/* 232:257 */           ((ITickableSound)var4).update();
/* 233:    */         }
/* 234:260 */         func_148611_c(var4);
/* 235:261 */         var10.remove();
/* 236:    */       }
/* 237:    */     }
/* 238:    */   }
/* 239:    */   
/* 240:    */   public boolean func_148597_a(ISound p_148597_1_)
/* 241:    */   {
/* 242:268 */     if (!this.field_148617_f) {
/* 243:270 */       return false;
/* 244:    */     }
/* 245:274 */     String var2 = (String)this.field_148630_i.get(p_148597_1_);
/* 246:275 */     return var2 != null;
/* 247:    */   }
/* 248:    */   
/* 249:    */   public void func_148602_b(ISound p_148602_1_)
/* 250:    */   {
/* 251:281 */     if (this.field_148617_f)
/* 252:    */     {
/* 253:283 */       String var2 = (String)this.field_148630_i.get(p_148602_1_);
/* 254:285 */       if (var2 != null) {
/* 255:287 */         this.field_148620_e.stop(var2);
/* 256:    */       }
/* 257:    */     }
/* 258:    */   }
/* 259:    */   
/* 260:    */   public void func_148611_c(ISound p_148611_1_)
/* 261:    */   {
/* 262:294 */     if (this.field_148617_f) {
/* 263:296 */       if (this.field_148620_e.getMasterVolume() <= 0.0F)
/* 264:    */       {
/* 265:298 */         logger.debug(field_148623_a, "Skipped playing soundEvent: {}, master volume was zero", new Object[] { p_148611_1_.func_147650_b() });
/* 266:    */       }
/* 267:    */       else
/* 268:    */       {
/* 269:302 */         SoundEventAccessorComposite var2 = this.field_148622_c.func_147680_a(p_148611_1_.func_147650_b());
/* 270:304 */         if (var2 == null)
/* 271:    */         {
/* 272:306 */           logger.warn(field_148623_a, "Unable to play unknown soundEvent: {}", new Object[] { p_148611_1_.func_147650_b() });
/* 273:    */         }
/* 274:    */         else
/* 275:    */         {
/* 276:310 */           SoundPoolEntry var3 = var2.func_148720_g();
/* 277:312 */           if (var3 == SoundHandler.field_147700_a)
/* 278:    */           {
/* 279:314 */             logger.warn(field_148623_a, "Unable to play empty soundEvent: {}", new Object[] { var2.func_148729_c() });
/* 280:    */           }
/* 281:    */           else
/* 282:    */           {
/* 283:318 */             float var4 = p_148611_1_.func_147653_e();
/* 284:319 */             float var5 = 16.0F;
/* 285:321 */             if (var4 > 1.0F) {
/* 286:323 */               var5 *= var4;
/* 287:    */             }
/* 288:326 */             SoundCategory var6 = var2.func_148728_d();
/* 289:327 */             float var7 = func_148594_a(p_148611_1_, var3, var6);
/* 290:328 */             double var8 = func_148606_a(p_148611_1_, var3);
/* 291:329 */             ResourceLocation var10 = var3.func_148652_a();
/* 292:331 */             if (var7 == 0.0F)
/* 293:    */             {
/* 294:333 */               logger.debug(field_148623_a, "Skipped playing sound {}, volume was zero.", new Object[] { var10 });
/* 295:    */             }
/* 296:    */             else
/* 297:    */             {
/* 298:337 */               boolean var11 = (p_148611_1_.func_147657_c()) && (p_148611_1_.func_147652_d() == 0);
/* 299:338 */               String var12 = UUID.randomUUID().toString();
/* 300:340 */               if (var3.func_148648_d()) {
/* 301:342 */                 this.field_148620_e.newStreamingSource(false, var12, func_148612_a(var10), var10.toString(), var11, p_148611_1_.func_147649_g(), p_148611_1_.func_147654_h(), p_148611_1_.func_147651_i(), p_148611_1_.func_147656_j().func_148586_a(), var5);
/* 302:    */               } else {
/* 303:346 */                 this.field_148620_e.newSource(false, var12, func_148612_a(var10), var10.toString(), var11, p_148611_1_.func_147649_g(), p_148611_1_.func_147654_h(), p_148611_1_.func_147651_i(), p_148611_1_.func_147656_j().func_148586_a(), var5);
/* 304:    */               }
/* 305:349 */               logger.debug(field_148623_a, "Playing sound {} for event {} as channel {}", new Object[] { var3.func_148652_a(), var2.func_148729_c(), var12 });
/* 306:350 */               this.field_148620_e.setPitch(var12, (float)var8);
/* 307:351 */               this.field_148620_e.setVolume(var12, var7);
/* 308:352 */               this.field_148620_e.play(var12);
/* 309:353 */               this.field_148624_n.put(var12, Integer.valueOf(this.field_148618_g + 20));
/* 310:354 */               this.field_148629_h.put(var12, p_148611_1_);
/* 311:355 */               this.field_148627_j.put(p_148611_1_, var3);
/* 312:357 */               if (var6 != SoundCategory.MASTER) {
/* 313:359 */                 this.field_148628_k.put(var6, var12);
/* 314:    */               }
/* 315:362 */               if ((p_148611_1_ instanceof ITickableSound)) {
/* 316:364 */                 this.field_148625_l.add((ITickableSound)p_148611_1_);
/* 317:    */               }
/* 318:    */             }
/* 319:    */           }
/* 320:    */         }
/* 321:    */       }
/* 322:    */     }
/* 323:    */   }
/* 324:    */   
/* 325:    */   private float func_148606_a(ISound p_148606_1_, SoundPoolEntry p_148606_2_)
/* 326:    */   {
/* 327:375 */     return (float)MathHelper.clamp_double(p_148606_1_.func_147655_f() * p_148606_2_.func_148650_b(), 0.5D, 2.0D);
/* 328:    */   }
/* 329:    */   
/* 330:    */   private float func_148594_a(ISound p_148594_1_, SoundPoolEntry p_148594_2_, SoundCategory p_148594_3_)
/* 331:    */   {
/* 332:380 */     return (float)MathHelper.clamp_double(p_148594_1_.func_147653_e() * p_148594_2_.func_148649_c() * func_148595_a(p_148594_3_), 0.0D, 1.0D);
/* 333:    */   }
/* 334:    */   
/* 335:    */   public void func_148610_e()
/* 336:    */   {
/* 337:385 */     Iterator var1 = this.field_148629_h.keySet().iterator();
/* 338:387 */     while (var1.hasNext())
/* 339:    */     {
/* 340:389 */       String var2 = (String)var1.next();
/* 341:390 */       logger.debug(field_148623_a, "Pausing channel {}", new Object[] { var2 });
/* 342:391 */       this.field_148620_e.pause(var2);
/* 343:    */     }
/* 344:    */   }
/* 345:    */   
/* 346:    */   public void func_148604_f()
/* 347:    */   {
/* 348:397 */     Iterator var1 = this.field_148629_h.keySet().iterator();
/* 349:399 */     while (var1.hasNext())
/* 350:    */     {
/* 351:401 */       String var2 = (String)var1.next();
/* 352:402 */       logger.debug(field_148623_a, "Resuming channel {}", new Object[] { var2 });
/* 353:403 */       this.field_148620_e.play(var2);
/* 354:    */     }
/* 355:    */   }
/* 356:    */   
/* 357:    */   public void func_148599_a(ISound p_148599_1_, int p_148599_2_)
/* 358:    */   {
/* 359:409 */     this.field_148626_m.put(p_148599_1_, Integer.valueOf(this.field_148618_g + p_148599_2_));
/* 360:    */   }
/* 361:    */   
/* 362:    */   private static URL func_148612_a(ResourceLocation p_148612_0_)
/* 363:    */   {
/* 364:414 */     String var1 = String.format("%s:%s:%s", new Object[] { "mcsounddomain", p_148612_0_.getResourceDomain(), p_148612_0_.getResourcePath() });
/* 365:415 */     URLStreamHandler var2 = new URLStreamHandler()
/* 366:    */     {
/* 367:    */       private static final String __OBFID = "CL_00001143";
/* 368:    */       
/* 369:    */       protected URLConnection openConnection(URL par1URL)
/* 370:    */       {
/* 371:420 */         new URLConnection(par1URL)
/* 372:    */         {
/* 373:    */           private static final String __OBFID = "CL_00001144";
/* 374:    */           
/* 375:    */           public void connect() {}
/* 376:    */           
/* 377:    */           public InputStream getInputStream()
/* 378:    */             throws IOException
/* 379:    */           {
/* 380:426 */             return Minecraft.getMinecraft().getResourceManager().getResource(this.val$p_148612_0_).getInputStream();
/* 381:    */           }
/* 382:    */         };
/* 383:    */       }
/* 384:    */     };
/* 385:    */     try
/* 386:    */     {
/* 387:434 */       return new URL(null, var1, var2);
/* 388:    */     }
/* 389:    */     catch (MalformedURLException var4)
/* 390:    */     {
/* 391:438 */       throw new Error("TODO: Sanely handle url exception! :D");
/* 392:    */     }
/* 393:    */   }
/* 394:    */   
/* 395:    */   public void func_148615_a(EntityPlayer p_148615_1_, float p_148615_2_)
/* 396:    */   {
/* 397:444 */     if ((this.field_148617_f) && (p_148615_1_ != null))
/* 398:    */     {
/* 399:446 */       float var3 = p_148615_1_.prevRotationPitch + (p_148615_1_.rotationPitch - p_148615_1_.prevRotationPitch) * p_148615_2_;
/* 400:447 */       float var4 = p_148615_1_.prevRotationYaw + (p_148615_1_.rotationYaw - p_148615_1_.prevRotationYaw) * p_148615_2_;
/* 401:448 */       double var5 = p_148615_1_.prevPosX + (p_148615_1_.posX - p_148615_1_.prevPosX) * p_148615_2_;
/* 402:449 */       double var7 = p_148615_1_.prevPosY + (p_148615_1_.posY - p_148615_1_.prevPosY) * p_148615_2_;
/* 403:450 */       double var9 = p_148615_1_.prevPosZ + (p_148615_1_.posZ - p_148615_1_.prevPosZ) * p_148615_2_;
/* 404:451 */       float var11 = MathHelper.cos((var4 + 90.0F) * 0.01745329F);
/* 405:452 */       float var12 = MathHelper.sin((var4 + 90.0F) * 0.01745329F);
/* 406:453 */       float var13 = MathHelper.cos(-var3 * 0.01745329F);
/* 407:454 */       float var14 = MathHelper.sin(-var3 * 0.01745329F);
/* 408:455 */       float var15 = MathHelper.cos((-var3 + 90.0F) * 0.01745329F);
/* 409:456 */       float var16 = MathHelper.sin((-var3 + 90.0F) * 0.01745329F);
/* 410:457 */       float var17 = var11 * var13;
/* 411:458 */       float var19 = var12 * var13;
/* 412:459 */       float var20 = var11 * var15;
/* 413:460 */       float var22 = var12 * var15;
/* 414:461 */       this.field_148620_e.setListenerPosition((float)var5, (float)var7, (float)var9);
/* 415:462 */       this.field_148620_e.setListenerOrientation(var17, var14, var19, var20, var16, var22);
/* 416:    */     }
/* 417:    */   }
/* 418:    */   
/* 419:    */   class SoundSystemStarterThread
/* 420:    */     extends SoundSystem
/* 421:    */   {
/* 422:    */     private static final String __OBFID = "CL_00001145";
/* 423:    */     
/* 424:    */     private SoundSystemStarterThread() {}
/* 425:    */     
/* 426:    */     public boolean playing(String p_playing_1_)
/* 427:    */     {
/* 428:474 */       Object var2 = SoundSystemConfig.THREAD_SYNC;
/* 429:476 */       synchronized (SoundSystemConfig.THREAD_SYNC)
/* 430:    */       {
/* 431:478 */         if (this.soundLibrary == null) {
/* 432:480 */           return false;
/* 433:    */         }
/* 434:484 */         Source var3 = (Source)this.soundLibrary.getSources().get(p_playing_1_);
/* 435:485 */         return var3 != null;
/* 436:    */       }
/* 437:    */     }
/* 438:    */     
/* 439:    */     SoundSystemStarterThread(Object p_i45118_2_)
/* 440:    */     {
/* 441:492 */       this();
/* 442:    */     }
/* 443:    */   }
/* 444:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.audio.SoundManager
 * JD-Core Version:    0.7.0.1
 */