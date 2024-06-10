/*   1:    */ package net.minecraft.client.multiplayer;
/*   2:    */ 
/*   3:    */ import java.util.HashSet;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.List;
/*   6:    */ import java.util.Random;
/*   7:    */ import java.util.Set;
/*   8:    */ import java.util.concurrent.Callable;
/*   9:    */ import net.minecraft.block.Block;
/*  10:    */ import net.minecraft.block.material.Material;
/*  11:    */ import net.minecraft.client.Minecraft;
/*  12:    */ import net.minecraft.client.audio.MovingSoundMinecart;
/*  13:    */ import net.minecraft.client.audio.PositionedSoundRecord;
/*  14:    */ import net.minecraft.client.audio.SoundHandler;
/*  15:    */ import net.minecraft.client.entity.EntityClientPlayerMP;
/*  16:    */ import net.minecraft.client.network.NetHandlerPlayClient;
/*  17:    */ import net.minecraft.client.particle.EffectRenderer;
/*  18:    */ import net.minecraft.client.particle.EntityFireworkStarterFX;
/*  19:    */ import net.minecraft.client.renderer.RenderGlobal;
/*  20:    */ import net.minecraft.client.renderer.entity.Render;
/*  21:    */ import net.minecraft.client.renderer.entity.RenderManager;
/*  22:    */ import net.minecraft.crash.CrashReport;
/*  23:    */ import net.minecraft.crash.CrashReportCategory;
/*  24:    */ import net.minecraft.entity.Entity;
/*  25:    */ import net.minecraft.entity.EntityLivingBase;
/*  26:    */ import net.minecraft.entity.item.EntityMinecart;
/*  27:    */ import net.minecraft.nbt.NBTTagCompound;
/*  28:    */ import net.minecraft.network.NetworkManager;
/*  29:    */ import net.minecraft.profiler.Profiler;
/*  30:    */ import net.minecraft.scoreboard.Scoreboard;
/*  31:    */ import net.minecraft.util.ChatComponentText;
/*  32:    */ import net.minecraft.util.IntHashMap;
/*  33:    */ import net.minecraft.util.ResourceLocation;
/*  34:    */ import net.minecraft.world.ChunkCoordIntPair;
/*  35:    */ import net.minecraft.world.EnumDifficulty;
/*  36:    */ import net.minecraft.world.GameRules;
/*  37:    */ import net.minecraft.world.World;
/*  38:    */ import net.minecraft.world.WorldProvider;
/*  39:    */ import net.minecraft.world.WorldSettings;
/*  40:    */ import net.minecraft.world.chunk.Chunk;
/*  41:    */ import net.minecraft.world.chunk.IChunkProvider;
/*  42:    */ import net.minecraft.world.storage.SaveHandlerMP;
/*  43:    */ 
/*  44:    */ public class WorldClient
/*  45:    */   extends World
/*  46:    */ {
/*  47:    */   private NetHandlerPlayClient sendQueue;
/*  48:    */   private ChunkProviderClient clientChunkProvider;
/*  49: 46 */   private IntHashMap entityHashSet = new IntHashMap();
/*  50: 49 */   private Set entityList = new HashSet();
/*  51: 55 */   private Set entitySpawnQueue = new HashSet();
/*  52: 56 */   private final Minecraft mc = Minecraft.getMinecraft();
/*  53: 57 */   private final Set previousActiveChunkSet = new HashSet();
/*  54:    */   private static final String __OBFID = "CL_00000882";
/*  55:    */   
/*  56:    */   public WorldClient(NetHandlerPlayClient p_i45063_1_, WorldSettings p_i45063_2_, int p_i45063_3_, EnumDifficulty p_i45063_4_, Profiler p_i45063_5_)
/*  57:    */   {
/*  58: 62 */     super(new SaveHandlerMP(), "MpServer", WorldProvider.getProviderForDimension(p_i45063_3_), p_i45063_2_, p_i45063_5_);
/*  59: 63 */     this.sendQueue = p_i45063_1_;
/*  60: 64 */     this.difficultySetting = p_i45063_4_;
/*  61: 65 */     setSpawnLocation(8, 64, 8);
/*  62: 66 */     this.mapStorage = p_i45063_1_.mapStorageOrigin;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void tick()
/*  66:    */   {
/*  67: 74 */     super.tick();
/*  68: 75 */     func_82738_a(getTotalWorldTime() + 1L);
/*  69: 77 */     if (getGameRules().getGameRuleBooleanValue("doDaylightCycle")) {
/*  70: 79 */       setWorldTime(getWorldTime() + 1L);
/*  71:    */     }
/*  72: 82 */     this.theProfiler.startSection("reEntryProcessing");
/*  73: 84 */     for (int var1 = 0; (var1 < 10) && (!this.entitySpawnQueue.isEmpty()); var1++)
/*  74:    */     {
/*  75: 86 */       Entity var2 = (Entity)this.entitySpawnQueue.iterator().next();
/*  76: 87 */       this.entitySpawnQueue.remove(var2);
/*  77: 89 */       if (!this.loadedEntityList.contains(var2)) {
/*  78: 91 */         spawnEntityInWorld(var2);
/*  79:    */       }
/*  80:    */     }
/*  81: 95 */     this.theProfiler.endStartSection("connection");
/*  82: 96 */     this.sendQueue.onNetworkTick();
/*  83: 97 */     this.theProfiler.endStartSection("chunkCache");
/*  84: 98 */     this.clientChunkProvider.unloadQueuedChunks();
/*  85: 99 */     this.theProfiler.endStartSection("blocks");
/*  86:100 */     func_147456_g();
/*  87:101 */     this.theProfiler.endSection();
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void invalidateBlockReceiveRegion(int par1, int par2, int par3, int par4, int par5, int par6) {}
/*  91:    */   
/*  92:    */   protected IChunkProvider createChunkProvider()
/*  93:    */   {
/*  94:115 */     this.clientChunkProvider = new ChunkProviderClient(this);
/*  95:116 */     return this.clientChunkProvider;
/*  96:    */   }
/*  97:    */   
/*  98:    */   protected void func_147456_g()
/*  99:    */   {
/* 100:121 */     super.func_147456_g();
/* 101:122 */     this.previousActiveChunkSet.retainAll(this.activeChunkSet);
/* 102:124 */     if (this.previousActiveChunkSet.size() == this.activeChunkSet.size()) {
/* 103:126 */       this.previousActiveChunkSet.clear();
/* 104:    */     }
/* 105:129 */     int var1 = 0;
/* 106:130 */     Iterator var2 = this.activeChunkSet.iterator();
/* 107:132 */     while (var2.hasNext())
/* 108:    */     {
/* 109:134 */       ChunkCoordIntPair var3 = (ChunkCoordIntPair)var2.next();
/* 110:136 */       if (!this.previousActiveChunkSet.contains(var3))
/* 111:    */       {
/* 112:138 */         int var4 = var3.chunkXPos * 16;
/* 113:139 */         int var5 = var3.chunkZPos * 16;
/* 114:140 */         this.theProfiler.startSection("getChunk");
/* 115:141 */         Chunk var6 = getChunkFromChunkCoords(var3.chunkXPos, var3.chunkZPos);
/* 116:142 */         func_147467_a(var4, var5, var6);
/* 117:143 */         this.theProfiler.endSection();
/* 118:144 */         this.previousActiveChunkSet.add(var3);
/* 119:145 */         var1++;
/* 120:147 */         if (var1 >= 10) {
/* 121:149 */           return;
/* 122:    */         }
/* 123:    */       }
/* 124:    */     }
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void doPreChunk(int par1, int par2, boolean par3)
/* 128:    */   {
/* 129:157 */     if (par3) {
/* 130:159 */       this.clientChunkProvider.loadChunk(par1, par2);
/* 131:    */     } else {
/* 132:163 */       this.clientChunkProvider.unloadChunk(par1, par2);
/* 133:    */     }
/* 134:166 */     if (!par3) {
/* 135:168 */       markBlockRangeForRenderUpdate(par1 * 16, 0, par2 * 16, par1 * 16 + 15, 256, par2 * 16 + 15);
/* 136:    */     }
/* 137:    */   }
/* 138:    */   
/* 139:    */   public boolean spawnEntityInWorld(Entity par1Entity)
/* 140:    */   {
/* 141:177 */     boolean var2 = super.spawnEntityInWorld(par1Entity);
/* 142:178 */     this.entityList.add(par1Entity);
/* 143:180 */     if (!var2) {
/* 144:182 */       this.entitySpawnQueue.add(par1Entity);
/* 145:184 */     } else if ((par1Entity instanceof EntityMinecart)) {
/* 146:186 */       this.mc.getSoundHandler().playSound(new MovingSoundMinecart((EntityMinecart)par1Entity));
/* 147:    */     }
/* 148:189 */     return var2;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void removeEntity(Entity par1Entity)
/* 152:    */   {
/* 153:197 */     super.removeEntity(par1Entity);
/* 154:198 */     this.entityList.remove(par1Entity);
/* 155:    */   }
/* 156:    */   
/* 157:    */   protected void onEntityAdded(Entity par1Entity)
/* 158:    */   {
/* 159:203 */     super.onEntityAdded(par1Entity);
/* 160:205 */     if (this.entitySpawnQueue.contains(par1Entity)) {
/* 161:207 */       this.entitySpawnQueue.remove(par1Entity);
/* 162:    */     }
/* 163:    */   }
/* 164:    */   
/* 165:    */   protected void onEntityRemoved(Entity par1Entity)
/* 166:    */   {
/* 167:213 */     super.onEntityRemoved(par1Entity);
/* 168:214 */     boolean var2 = false;
/* 169:216 */     if (this.entityList.contains(par1Entity)) {
/* 170:218 */       if (par1Entity.isEntityAlive())
/* 171:    */       {
/* 172:220 */         this.entitySpawnQueue.add(par1Entity);
/* 173:221 */         var2 = true;
/* 174:    */       }
/* 175:    */       else
/* 176:    */       {
/* 177:225 */         this.entityList.remove(par1Entity);
/* 178:    */       }
/* 179:    */     }
/* 180:229 */     if ((RenderManager.instance.getEntityRenderObject(par1Entity).func_147905_a()) && (!var2)) {
/* 181:231 */       this.mc.renderGlobal.onStaticEntitiesChanged();
/* 182:    */     }
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void addEntityToWorld(int par1, Entity par2Entity)
/* 186:    */   {
/* 187:240 */     Entity var3 = getEntityByID(par1);
/* 188:242 */     if (var3 != null) {
/* 189:244 */       removeEntity(var3);
/* 190:    */     }
/* 191:247 */     this.entityList.add(par2Entity);
/* 192:248 */     par2Entity.setEntityId(par1);
/* 193:250 */     if (!spawnEntityInWorld(par2Entity)) {
/* 194:252 */       this.entitySpawnQueue.add(par2Entity);
/* 195:    */     }
/* 196:255 */     this.entityHashSet.addKey(par1, par2Entity);
/* 197:257 */     if (RenderManager.instance.getEntityRenderObject(par2Entity).func_147905_a()) {
/* 198:259 */       this.mc.renderGlobal.onStaticEntitiesChanged();
/* 199:    */     }
/* 200:    */   }
/* 201:    */   
/* 202:    */   public Entity getEntityByID(int par1)
/* 203:    */   {
/* 204:268 */     return par1 == this.mc.thePlayer.getEntityId() ? this.mc.thePlayer : (Entity)this.entityHashSet.lookup(par1);
/* 205:    */   }
/* 206:    */   
/* 207:    */   public Entity removeEntityFromWorld(int par1)
/* 208:    */   {
/* 209:273 */     Entity var2 = (Entity)this.entityHashSet.removeObject(par1);
/* 210:275 */     if (var2 != null)
/* 211:    */     {
/* 212:277 */       this.entityList.remove(var2);
/* 213:278 */       removeEntity(var2);
/* 214:    */     }
/* 215:281 */     return var2;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public boolean func_147492_c(int p_147492_1_, int p_147492_2_, int p_147492_3_, Block p_147492_4_, int p_147492_5_)
/* 219:    */   {
/* 220:286 */     invalidateBlockReceiveRegion(p_147492_1_, p_147492_2_, p_147492_3_, p_147492_1_, p_147492_2_, p_147492_3_);
/* 221:287 */     return super.setBlock(p_147492_1_, p_147492_2_, p_147492_3_, p_147492_4_, p_147492_5_, 3);
/* 222:    */   }
/* 223:    */   
/* 224:    */   public void sendQuittingDisconnectingPacket()
/* 225:    */   {
/* 226:295 */     this.sendQueue.getNetworkManager().closeChannel(new ChatComponentText("Quitting"));
/* 227:    */   }
/* 228:    */   
/* 229:    */   protected void updateWeather()
/* 230:    */   {
/* 231:303 */     if (!this.provider.hasNoSky) {}
/* 232:    */   }
/* 233:    */   
/* 234:    */   public void doVoidFogParticles(int par1, int par2, int par3)
/* 235:    */   {
/* 236:311 */     byte var4 = 16;
/* 237:312 */     Random var5 = new Random();
/* 238:314 */     for (int var6 = 0; var6 < 1000; var6++)
/* 239:    */     {
/* 240:316 */       int var7 = par1 + this.rand.nextInt(var4) - this.rand.nextInt(var4);
/* 241:317 */       int var8 = par2 + this.rand.nextInt(var4) - this.rand.nextInt(var4);
/* 242:318 */       int var9 = par3 + this.rand.nextInt(var4) - this.rand.nextInt(var4);
/* 243:319 */       Block var10 = getBlock(var7, var8, var9);
/* 244:321 */       if (var10.getMaterial() == Material.air)
/* 245:    */       {
/* 246:323 */         if ((this.rand.nextInt(8) > var8) && (this.provider.getWorldHasVoidParticles())) {
/* 247:325 */           spawnParticle("depthsuspend", var7 + this.rand.nextFloat(), var8 + this.rand.nextFloat(), var9 + this.rand.nextFloat(), 0.0D, 0.0D, 0.0D);
/* 248:    */         }
/* 249:    */       }
/* 250:    */       else {
/* 251:330 */         var10.randomDisplayTick(this, var7, var8, var9, var5);
/* 252:    */       }
/* 253:    */     }
/* 254:    */   }
/* 255:    */   
/* 256:    */   public void removeAllEntities()
/* 257:    */   {
/* 258:340 */     this.loadedEntityList.removeAll(this.unloadedEntityList);
/* 259:346 */     for (int var1 = 0; var1 < this.unloadedEntityList.size(); var1++)
/* 260:    */     {
/* 261:348 */       Entity var2 = (Entity)this.unloadedEntityList.get(var1);
/* 262:349 */       int var3 = var2.chunkCoordX;
/* 263:350 */       int var4 = var2.chunkCoordZ;
/* 264:352 */       if ((var2.addedToChunk) && (chunkExists(var3, var4))) {
/* 265:354 */         getChunkFromChunkCoords(var3, var4).removeEntity(var2);
/* 266:    */       }
/* 267:    */     }
/* 268:358 */     for (var1 = 0; var1 < this.unloadedEntityList.size(); var1++) {
/* 269:360 */       onEntityRemoved((Entity)this.unloadedEntityList.get(var1));
/* 270:    */     }
/* 271:363 */     this.unloadedEntityList.clear();
/* 272:365 */     for (var1 = 0; var1 < this.loadedEntityList.size(); var1++)
/* 273:    */     {
/* 274:367 */       Entity var2 = (Entity)this.loadedEntityList.get(var1);
/* 275:369 */       if (var2.ridingEntity != null)
/* 276:    */       {
/* 277:371 */         if ((var2.ridingEntity.isDead) || (var2.ridingEntity.riddenByEntity != var2))
/* 278:    */         {
/* 279:376 */           var2.ridingEntity.riddenByEntity = null;
/* 280:377 */           var2.ridingEntity = null;
/* 281:    */         }
/* 282:    */       }
/* 283:380 */       else if (var2.isDead)
/* 284:    */       {
/* 285:382 */         int var3 = var2.chunkCoordX;
/* 286:383 */         int var4 = var2.chunkCoordZ;
/* 287:385 */         if ((var2.addedToChunk) && (chunkExists(var3, var4))) {
/* 288:387 */           getChunkFromChunkCoords(var3, var4).removeEntity(var2);
/* 289:    */         }
/* 290:390 */         this.loadedEntityList.remove(var1--);
/* 291:391 */         onEntityRemoved(var2);
/* 292:    */       }
/* 293:    */     }
/* 294:    */   }
/* 295:    */   
/* 296:    */   public CrashReportCategory addWorldInfoToCrashReport(CrashReport par1CrashReport)
/* 297:    */   {
/* 298:401 */     CrashReportCategory var2 = super.addWorldInfoToCrashReport(par1CrashReport);
/* 299:402 */     var2.addCrashSectionCallable("Forced entities", new Callable()
/* 300:    */     {
/* 301:    */       private static final String __OBFID = "CL_00000883";
/* 302:    */       
/* 303:    */       public String call()
/* 304:    */       {
/* 305:407 */         return WorldClient.this.entityList.size() + " total; " + WorldClient.this.entityList.toString();
/* 306:    */       }
/* 307:409 */     });
/* 308:410 */     var2.addCrashSectionCallable("Retry entities", new Callable()
/* 309:    */     {
/* 310:    */       private static final String __OBFID = "CL_00000884";
/* 311:    */       
/* 312:    */       public String call()
/* 313:    */       {
/* 314:415 */         return WorldClient.this.entitySpawnQueue.size() + " total; " + WorldClient.this.entitySpawnQueue.toString();
/* 315:    */       }
/* 316:417 */     });
/* 317:418 */     var2.addCrashSectionCallable("Server brand", new Callable()
/* 318:    */     {
/* 319:    */       private static final String __OBFID = "CL_00000885";
/* 320:    */       
/* 321:    */       public String call()
/* 322:    */       {
/* 323:423 */         return WorldClient.this.mc.thePlayer.func_142021_k();
/* 324:    */       }
/* 325:425 */     });
/* 326:426 */     var2.addCrashSectionCallable("Server type", new Callable()
/* 327:    */     {
/* 328:    */       private static final String __OBFID = "CL_00000886";
/* 329:    */       
/* 330:    */       public String call()
/* 331:    */       {
/* 332:431 */         return WorldClient.this.mc.getIntegratedServer() == null ? "Non-integrated multiplayer server" : "Integrated singleplayer server";
/* 333:    */       }
/* 334:433 */     });
/* 335:434 */     return var2;
/* 336:    */   }
/* 337:    */   
/* 338:    */   public void playSound(double par1, double par3, double par5, String par7Str, float par8, float par9, boolean par10)
/* 339:    */   {
/* 340:442 */     double var11 = this.mc.renderViewEntity.getDistanceSq(par1, par3, par5);
/* 341:443 */     PositionedSoundRecord var13 = new PositionedSoundRecord(new ResourceLocation(par7Str), par8, par9, (float)par1, (float)par3, (float)par5);
/* 342:445 */     if ((par10) && (var11 > 100.0D))
/* 343:    */     {
/* 344:447 */       double var14 = Math.sqrt(var11) / 40.0D;
/* 345:448 */       this.mc.getSoundHandler().playDelayedSound(var13, (int)(var14 * 20.0D));
/* 346:    */     }
/* 347:    */     else
/* 348:    */     {
/* 349:452 */       this.mc.getSoundHandler().playSound(var13);
/* 350:    */     }
/* 351:    */   }
/* 352:    */   
/* 353:    */   public void makeFireworks(double par1, double par3, double par5, double par7, double par9, double par11, NBTTagCompound par13NBTTagCompound)
/* 354:    */   {
/* 355:458 */     this.mc.effectRenderer.addEffect(new EntityFireworkStarterFX(this, par1, par3, par5, par7, par9, par11, this.mc.effectRenderer, par13NBTTagCompound));
/* 356:    */   }
/* 357:    */   
/* 358:    */   public void setWorldScoreboard(Scoreboard par1Scoreboard)
/* 359:    */   {
/* 360:463 */     this.worldScoreboard = par1Scoreboard;
/* 361:    */   }
/* 362:    */   
/* 363:    */   public void setWorldTime(long par1)
/* 364:    */   {
/* 365:471 */     if (par1 < 0L)
/* 366:    */     {
/* 367:473 */       par1 = -par1;
/* 368:474 */       getGameRules().setOrCreateGameRule("doDaylightCycle", "false");
/* 369:    */     }
/* 370:    */     else
/* 371:    */     {
/* 372:478 */       getGameRules().setOrCreateGameRule("doDaylightCycle", "true");
/* 373:    */     }
/* 374:481 */     super.setWorldTime(par1);
/* 375:    */   }
/* 376:    */   
/* 377:    */   public Material getBlockMaterial(Block block)
/* 378:    */   {
/* 379:486 */     return block.getMaterial();
/* 380:    */   }
/* 381:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.multiplayer.WorldClient
 * JD-Core Version:    0.7.0.1
 */